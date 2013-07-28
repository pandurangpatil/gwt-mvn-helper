package open.pandurang.gwt.helper.requestfactory.marker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker to indicate entity class for which Request factory proxy interface should be generated. If you apply
 * RFEntityProxy annotation to a class which don't have @Entity annotation then respective proxy interface will
 * extend from ValueProxy interface. And it will not create corresponding RequestFactory interface.
 * 
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = { ElementType.TYPE })
public @interface RFValueProxy {

}
