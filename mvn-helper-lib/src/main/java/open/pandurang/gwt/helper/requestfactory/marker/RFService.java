package open.pandurang.gwt.helper.requestfactory.marker;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Marker interface to mark Service class for which Request Factory Service proxy interface need to be generated
 * 
 */
public @interface RFService {
	/**
	 * Fully qualified class name of the locator
	 * 
	 * @return
	 */
	Class<? extends ServiceLocator> value();
}
