package open.pandurang.gwt.requestfactory.test;

import open.pandurang.gwt.helper.requestfactory.marker.RFProxyMethod;
import open.pandurang.gwt.helper.requestfactory.marker.RFValueProxy;

@RFValueProxy
public class Value {

	private String name;
	private int age;
	/**
	 * @return the name
	 */
	@RFProxyMethod
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	@RFProxyMethod
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	@RFProxyMethod
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	@RFProxyMethod
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
