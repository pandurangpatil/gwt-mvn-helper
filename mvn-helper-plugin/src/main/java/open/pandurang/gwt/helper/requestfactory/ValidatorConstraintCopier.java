package open.pandurang.gwt.helper.requestfactory;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.qdox.model.Annotation;

/**
 * To copy javax constraint applied on entity
 * 
 * 
 * @auther Pandurang Patil 27/07/2013
 * 
 */
public class ValidatorConstraintCopier {
	protected final static String			JAVAX_CONSTRAINT_MARKER_PKG	= "javax.validation.constraints";
	private static Map<String, Constraints>	constraintsMap				= new HashMap<String, ValidatorConstraintCopier.Constraints>();

	static {
		for (Constraints constraint : Constraints.values()) {
			constraintsMap.put(constraint.getName(), constraint);
		}
	}

	public static String getConstraints(Annotation[] annotations) {
		StringBuffer anns = new StringBuffer();
		if (annotations != null && annotations.length > 0) {
			for (Annotation annotation : annotations) {
				if (annotation.getType().getJavaClass().getFullyQualifiedName().startsWith(JAVAX_CONSTRAINT_MARKER_PKG)) {
					Constraints constraint = constraintsMap.get(annotation.getType().getJavaClass().getName());
					if (constraint == null) {
						throw new RuntimeException("Constrain '" + annotation.getType().getJavaClass().getName()
								+ "' is not supported");
					}
					anns.append(getAnnotation(annotation, constraint) + "\n");
				}
			}
			return anns.toString();
		}
		return null;
	}

	private static String getAnnotation(Annotation annotation, Constraints constraint) {
		StringBuffer string = new StringBuffer("@" + annotation.getType().getJavaClass().getFullyQualifiedName());
		if (annotation.getNamedParameterMap() != null && annotation.getNamedParameterMap().size() > 0) {
			boolean firstParamAdded = false;
			for (AnnotationParam param : constraint.getParams()) {
				Object value = annotation.getNamedParameter(param.getKey());
				if (value != null) {
					if (!firstParamAdded) {
						string.append("(");
						firstParamAdded = true;
					} else {
						string.append(",");
					}
					string.append(param.getKey() + "=" + value.toString());
				}
			}
			if (firstParamAdded) {
				string.append(")");
			}
		}
		return string.toString();
	}

	public static enum Constraints {
		SIZE_CONSTRAINT("Size", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.NON_STRING, "max"),
				new AnnotationParam(AnnotationParamType.NON_STRING, "min"),
				new AnnotationParam(AnnotationParamType.STRING, "message") }), PATTERN_CONSTRAINT("Pattern",
				new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "regexp"),
						new AnnotationParam(AnnotationParamType.STRING, "message") }), PAST_CONSTRAINT("Past",
				new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "message") }), NULL_CONSTRAINT(
				"Null", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "message") }), NOT_NULL_CONSTRAINT(
				"NotNull", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "message") }), MIN_CONSTRAINT(
				"Min", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.NON_STRING, "value"),
						new AnnotationParam(AnnotationParamType.STRING, "message") }), MAX_CONSTRAINT("Max",
				new AnnotationParam[] { new AnnotationParam(AnnotationParamType.NON_STRING, "value"),
						new AnnotationParam(AnnotationParamType.STRING, "message") }), FUTURE_CONSTRAINT("Future",
				new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "message") }), DIGITS_CONSTRAINT(
				"Digits", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.NON_STRING, "integer"),
						new AnnotationParam(AnnotationParamType.NON_STRING, "fraction"),
						new AnnotationParam(AnnotationParamType.STRING, "message") }), DECIMAL_MIN_CONSTRAINT(
				"DecimalMin", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.NON_STRING, "value"),
						new AnnotationParam(AnnotationParamType.STRING, "message") }), DECIMAL_MAX_CONSTRAINT(
				"DecimalMax", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.NON_STRING, "value"),
						new AnnotationParam(AnnotationParamType.STRING, "message") }), ASSERT_TRUE_CONSTRAINT(
				"AssertTrue", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "message") }), ASSERT_FALSE_CONSTRAINT(
				"AssertFalse", new AnnotationParam[] { new AnnotationParam(AnnotationParamType.STRING, "message") });
		private String				name;

		private AnnotationParam[]	params;

		private Constraints(String name, AnnotationParam[] params) {
			this.name = name;
			this.params = params;
		}

		public String getName() {
			return name;
		}

		public AnnotationParam[] getParams() {
			return params;
		}

	}

	public static class AnnotationParam {
		private AnnotationParamType	type;
		private String				key;

		public AnnotationParam(AnnotationParamType type, String key) {
			super();
			this.type = type;
			this.key = key;
		}

		public AnnotationParamType getType() {
			return type;
		}

		public String getKey() {
			return key;
		}

	}

	public static enum AnnotationParamType {
		STRING, NON_STRING;
	}
}
