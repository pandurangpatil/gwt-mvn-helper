package open.pandurang.gwt.requestfactory.test;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Generic @Locator for objects that extend EntityBase
 */
public class OtherEntityLocator extends Locator<OtherEntity, String> {

	@Override
	public OtherEntity create(Class<? extends OtherEntity> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OtherEntity find(Class<? extends OtherEntity> clazz, String id) {
		return null;
	}

	/**
	 * it's never called
	 */
	@Override
	public Class<OtherEntity> getDomainType() {
		throw new UnsupportedOperationException();
		// or return null;
	}

	@Override
	public String getId(OtherEntity domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(OtherEntity domainObject) {
		return null;
	}

}