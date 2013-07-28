package open.pandurang.gwt.helper.overlaytype;

import java.io.File;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

/**
 * Goal which generate Overlay type classes.
 * 
 * @goal overlayType
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * @version $Id$
 * 
 * 
 * @auther Pandurang Patil 27/07/2013
 * 
 */
public class OverlayMojo extends AbstractMojo {
	private static final String						JS_ARRAY				= "JsArray";
	private static final String						JAVA_SCRIPT_OBJECT		= "com.google.gwt.core.client.*";
	private static final String						OVERLAY_TYPE_MARKER		= "open.pandurang.gwt.helper.overlaytype.marker.OverlayType";
	private static final String						OVERLAY_FIELD_MARKER	= "open.pandurang.gwt.helper.overlaytype.marker.OverlayField";

	private final static Map<String, BasicOLType>	basicDataTypes			= new HashMap<String, BasicOLType>();

	static {
		BasicOLType typeMapper = new BasicOLType("Boolean", "JsArrayBoolean");
		basicDataTypes.put("boolean", typeMapper);
		basicDataTypes.put("Boolean", typeMapper);
		typeMapper = new BasicOLType("Integer", "JsArrayInteger");
		basicDataTypes.put("byte", typeMapper);
		basicDataTypes.put("Byte", typeMapper);
		basicDataTypes.put("short", typeMapper);
		basicDataTypes.put("Short", typeMapper);
		basicDataTypes.put("int", typeMapper);
		basicDataTypes.put("Integer", typeMapper);
		typeMapper = new BasicOLType("Long", "JsArrayNumber");
		basicDataTypes.put("long", typeMapper);
		basicDataTypes.put("Long", typeMapper);
		typeMapper = new BasicOLType("Double", "JsArrayNumber");
		basicDataTypes.put("float", typeMapper);
		basicDataTypes.put("Float", typeMapper);
		basicDataTypes.put("double", typeMapper);
		basicDataTypes.put("Double", typeMapper);
		typeMapper = new BasicOLType("String", "JsArrayString");
		basicDataTypes.put("String", typeMapper);

	}

	/**
	 * @parameter expression="${plugin.version}"
	 * @required
	 * @readonly
	 */
	@SuppressWarnings("unused")
	private String									version;

	/**
	 * @parameter expression="${plugin.artifacts}"
	 * @required
	 * @readonly
	 */
	@SuppressWarnings("unused")
	private Collection<Artifact>					pluginArtifacts;

	/**
	 * @component
	 */
	protected ArtifactResolver						resolver;

	/**
	 * @component
	 */
	protected ArtifactFactory						artifactFactory;

	/**
	 * @parameter expression="${localRepository}"
	 * @required
	 * @readonly
	 */
	protected ArtifactRepository					localRepository;

	/**
	 * @parameter expression="${project.remoteArtifactRepositories}"
	 * @required
	 * @readonly
	 */
	protected List<ArtifactRepository>				remoteRepositories;

	/**
	 * @component
	 */
	protected ArtifactMetadataSource				artifactMetadataSource;

	/**
	 * The maven project descriptor
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject							project;

	// --- Plugin parameters ---------------------------------------------------

	/**
	 * Folder where generated-source will be created (automatically added to compile classpath).
	 * 
	 * @parameter default-value="${project.build.directory}/generated-sources/gwt"
	 * @required
	 */
	private File									generateDirectory;

	/**
	 * Stop the build on error
	 * 
	 * @parameter default-value="true"
	 */
	private boolean									failOnError;

	/**
	 * Path to include while scanning java classes
	 * 
	 * @parameter default-value=""
	 */
	private List<String>							includePath;

	/**
	 * Destination overlyType package
	 * 
	 * @parameter default-value=""
	 */
	private String									targetPackage;

	/**
	 * Pattern for GWT service interface
	 * 
	 * @parameter default-value="false"
	 */
	private boolean									force;

	/**
	 * @parameter expression="${project.build.sourceEncoding}"
	 */
	private String									encoding;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {
		getLog().debug("GenerateOverlayMojo#execute()");

		if ("pom".equals(project.getPackaging())) {
			getLog().info("GWT generateOverlay is skipped");
			return;
		}

		if (encoding == null) {
			getLog().warn("Encoding is not set, your build will be platform dependent");
			encoding = Charset.defaultCharset().name();
		}

		JavaDocBuilder builder = createJavaDocBuilder();

		List<String> sourceRoots = project.getCompileSourceRoots();
		boolean generated = false;
		for (String sourceRoot : sourceRoots) {
			try {
				generated |= scanAndGenerateOverlayType(new File(sourceRoot), builder);
			} catch (Throwable e) {
				getLog().error("Failed to generate Overlay class", e);
				if (failOnError) {
					throw new MojoExecutionException("Failed to generate Overlay class", e);
				}
			}
		}
		if (generated) {
			getLog().debug("add compile source root " + getGenerateDirectory());
			project.addCompileSourceRoot(getGenerateDirectory().getAbsolutePath());
		}
	}

	/**
	 * @param sourceRoot
	 *            the base directory to scan for Overlay Type to generate
	 * @return true if some file have been generated
	 * @throws Exception
	 *             generation failure
	 */
	private boolean scanAndGenerateOverlayType(File sourceRoot, JavaDocBuilder builder) throws Exception {
		getLog().info("Inside scanAndGenerateOverlayType");
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(sourceRoot);
		if (includePath != null) {
			scanner.setIncludes(includePath.toArray(new String[0]));
		}
		scanner.scan();
		String[] sources = scanner.getIncludedFiles();
		if (sources.length == 0) {
			return false;
		}
		boolean fileGenerated = false;
		for (String source : sources) {
			getLog().debug("Processing source => " + source);
			File sourceFile = new File(sourceRoot, source);
			File targetFile = getTargetFile(source);
			if (isUpToDate(sourceFile, targetFile)) {
				getLog().debug(targetFile.getAbsolutePath() + " is up to date. Generation skipped");
				// up to date, but still need to report generated-sources
				// directory as sourceRoot
				fileGenerated = true;
				continue;
			}
			getLog().debug(targetFile.getAbsolutePath() + " is not upto date so need to generated");
			String className = getTopLevelClassName(source);
			getLog().debug("Top level class name => " + className);
			JavaClass clazz = builder.getClassByName(className);
			if (isEligibleForGeneration(clazz)) {
				getLog().debug(clazz.getFullyQualifiedName() + " is eligible for overlay type generations");
				targetFile.getParentFile().mkdirs();
				generateOverlayType(clazz, targetFile);
				fileGenerated = true;
			}
		}
		return fileGenerated;
	}

	private boolean isUpToDate(File sourceFile, File targetFile) {
		return !force && targetFile.exists() && targetFile.lastModified() > sourceFile.lastModified();
	}

	private File getTargetFile(String source) {
		String targetFileName = source.substring(0, source.length() - 5) + "JS.java";
		if (targetPackage != null && !("".equals(targetPackage))) {
			int lastIndex = source.lastIndexOf(File.separatorChar);
			String className = source.substring(lastIndex, source.length() - 5) + "JS.java";
			targetFileName = targetPackage.replace('.', File.separatorChar) + className;
		}
		File targetFile = new File(getGenerateDirectory(), targetFileName);
		return targetFile;
	}

	/**
	 * @param clazz
	 *            POJO or bean class from which Overlay Type will be generated
	 * @param targetFile
	 *            Overlay Type class which will be generated
	 * @throws Exception
	 *             generation failure
	 */
	private void generateOverlayType(JavaClass clazz, File targetFile) throws Exception {
		getLog().info("Inside generateOverlayType");
		PrintWriter writer = new PrintWriter(targetFile, encoding);
		String className = clazz.getName();
		if (targetPackage != null && !("".equals(targetPackage))) {
			writer.println("package " + targetPackage + ";");
		} else if (clazz.getPackage() != null) {
			writer.println("package " + clazz.getPackageName() + ";");
		}
		writer.println("import " + JAVA_SCRIPT_OBJECT + ";");
		// TODO: Need to add logic to import any classes which will be used
		// outside source package.

		writer.println();
		writer.println("public class " + className + "JS extends JavaScriptObject{");
		writer.println("	protected " + className + "JS() {}");
		JavaField[] fields = clazz.getFields();
		for (JavaField field : fields) {
			for (Annotation flAn : field.getAnnotations()) {
				if (flAn.getType().getJavaClass().isA(OVERLAY_FIELD_MARKER)) {
					char fchar = field.getName().charAt(0);
					char cfchar = Character.toUpperCase(fchar);
					String fieldClassname = getMappedField(field);
					String fieldName4getSet = cfchar + field.getName().substring(1, field.getName().length());
					writer.println("	public final native " + fieldClassname + " get" + fieldName4getSet + "() /*-{");
					writer.println("		return this." + field.getName() + ";");
					writer.println("	}-*/;");
					writer.println("	public final native void set" + fieldName4getSet + "(" + fieldClassname + " "
							+ field.getName() + ") /*-{");
					writer.println("		this." + field.getName() + " = " + field.getName() + ";");
					writer.println("	}-*/;");
				}
			}
		}

		writer.println("}");
		writer.close();
	}

	private String getMappedField(JavaField field) throws Exception {

		JavaClass jvCls = field.getType().getJavaClass();
		String clsName = jvCls.getName();
		if (basicDataTypes.containsKey(clsName)) {
			BasicOLType basicType = basicDataTypes.get(clsName);
			/*
			 * Because some reason Thoughtworks library is not providing dimensions of the type so we are doing it on
			 * our own
			 */
			if (field.getType().getGenericValue().contains("[")) {
				StringBuffer str = new StringBuffer();
				StringBuffer closingStr = new StringBuffer();
				int index = field.getType().getGenericValue().indexOf('[', 0);
				int dimensions = 0;
				do {
					dimensions++;
					index = field.getType().getGenericValue().indexOf('[', index + 1);
				} while (index != -1);
				for (int i = 0; i < dimensions - 1; i++) {
					str.append(JS_ARRAY + "<");
					closingStr.append(">");
				}
				return str.toString() + basicType.getArrayType() + closingStr.toString();
			}
			return basicType.getOverlayType();
		} else if (isEligibleForGeneration(jvCls)) {
			String finalClsName = jvCls.getName() + "JS";
			/*
			 * Because some reason Thoughtworks library is not providing dimensions of the type so we are doing it on
			 * our own
			 */
			if (field.getType().getGenericValue().contains("[")) {
				StringBuffer str = new StringBuffer();
				StringBuffer closingStr = new StringBuffer();
				int index = field.getType().getGenericValue().indexOf('[', 0);
				do {
					str.append(JS_ARRAY + "<");
					closingStr.append(">");
					index = field.getType().getGenericValue().indexOf('[', index + 1);
				} while (index != -1);
				return str.toString() + finalClsName + closingStr.toString();
			}
			return finalClsName;
		} else {
			throw new MojoExecutionException(jvCls.getFullyQualifiedName()
					+ " is niether basic java data type nor it is OverlayType.\n"
					+ " Set,List and their implementaions are not supported instead use array.\n"
					+ " Map is not supported.");
		}
	}

	private boolean isEligibleForGeneration(JavaClass javaClass) {
		for (Annotation an : javaClass.getAnnotations()) {
			if (an.getType().getJavaClass().isA(OVERLAY_TYPE_MARKER) && javaClass.isPublic()) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private JavaDocBuilder createJavaDocBuilder() throws MojoExecutionException {
		try {
			JavaDocBuilder builder = new JavaDocBuilder();
			builder.setEncoding(encoding);
			builder.getClassLibrary().addClassLoader(getProjectClassLoader());
			for (String sourceRoot : (List<String>) project.getCompileSourceRoots()) {
				builder.getClassLibrary().addSourceFolder(new File(sourceRoot));
			}
			return builder;
		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Failed to resolve project classpath", e);
		} catch (DependencyResolutionRequiredException e) {
			throw new MojoExecutionException("Failed to resolve project classpath", e);
		}
	}

	private String getTopLevelClassName(String sourceFile) {
		String className = sourceFile.substring(0, sourceFile.length() - 5); // strip
		// ".java"
		return className.replace(File.separatorChar, '.');
	}

	/**
	 * @return the project classloader
	 * @throws DependencyResolutionRequiredException
	 *             failed to resolve project dependencies
	 * @throws MalformedURLException
	 *             configuration issue ?
	 */
	protected ClassLoader getProjectClassLoader() throws DependencyResolutionRequiredException, MalformedURLException {
		getLog().debug("AbstractMojo#getProjectClassLoader()");

		List<?> compile = project.getCompileClasspathElements();
		URL[] urls = new URL[compile.size()];
		int i = 0;
		for (Object object : compile) {
			if (object instanceof Artifact) {
				urls[i] = ((Artifact) object).getFile().toURI().toURL();
			} else {
				urls[i] = new File((String) object).toURI().toURL();
			}
			i++;
		}
		return new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
	}

	public File getGenerateDirectory() {
		if (!generateDirectory.exists()) {
			getLog().debug("Creating target directory " + generateDirectory.getAbsolutePath());
			generateDirectory.mkdirs();
		}
		return generateDirectory;
	}

}

class BasicOLType {
	private String	overlayType;
	private String	arrayType;

	public BasicOLType(String overlayType, String arrayType) {
		this.overlayType = overlayType;
		this.arrayType = arrayType;
	}

	/**
	 * @return the overlayType
	 */
	public String getOverlayType() {
		return overlayType;
	}

	/**
	 * @return the arrayType
	 */
	public String getArrayType() {
		return arrayType;
	}

}
