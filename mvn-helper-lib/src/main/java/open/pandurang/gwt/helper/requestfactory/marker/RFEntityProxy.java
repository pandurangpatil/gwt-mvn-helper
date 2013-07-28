package open.pandurang.gwt.helper.requestfactory.marker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Marker to indicate entity class for which Request factory proxy interface should be generated. If you apply
 * RFEntityProxy annotation to a class which don't have @Entity annotation then respective proxy interface will extend
 * from ValueProxy interface. And it will not create corresponding RequestFactory interface.
 * 
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.TYPE })
public @interface RFEntityProxy {
	/**
	 * Fully qualified class name of the locator
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends Locator> value() default Locator.class;

	boolean generateEntityRequest() default true;
}
