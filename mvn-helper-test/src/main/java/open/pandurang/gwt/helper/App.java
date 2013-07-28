package open.pandurang.gwt.helper;

import java.util.List;

import open.pandurang.gwt.helper.overlaytype.marker.OverlayField;
import open.pandurang.gwt.helper.overlaytype.marker.OverlayType;


@OverlayType
public class App {
	@OverlayField
	private String			name;
	private int				age;
	private String			test;
	@OverlayField
	private Address			address;
	@OverlayField
	private Address[]		addressone;
	@OverlayField
	private Address[][]		addresstwo;
//	@OverlayField
//	private List<Address>	addressneg;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test
	 *            the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the addressone
	 */
	public Address[] getAddressone() {
		return addressone;
	}

	/**
	 * @param addressone
	 *            the addressone to set
	 */
	public void setAddressone(Address[] addressone) {
		this.addressone = addressone;
	}

	/**
	 * @return the addresstwo
	 */
	public Address[][] getAddresstwo() {
		return addresstwo;
	}

	/**
	 * @param addresstwo
	 *            the addresstwo to set
	 */
	public void setAddresstwo(Address[][] addresstwo) {
		this.addresstwo = addresstwo;
	}

//	/**
//	 * @return the addressneg
//	 */
//	public List<Address> getAddressneg() {
//		return addressneg;
//	}
//
//	/**
//	 * @param addressneg
//	 *            the addressneg to set
//	 */
//	public void setAddressneg(List<Address> addressneg) {
//		this.addressneg = addressneg;
//	}

}
