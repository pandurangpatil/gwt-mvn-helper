package open.pandurang.gwt.helper;

import open.pandurang.gwt.helper.overlaytype.marker.OverlayField;
import open.pandurang.gwt.helper.overlaytype.marker.OverlayType;

@OverlayType
public class Address {

	@OverlayField
	private String[]	add1;
	@OverlayField
	private String		city;
	@OverlayField
	private String		state;
	@OverlayField
	private Integer[][]	integerMatrix;

	public String[] getAdd1() {
		return add1;
	}

	public void setAdd1(String[] add1) {
		this.add1 = add1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the integerMatrix
	 */
	public Integer[][] getIntegerMatrix() {
		return integerMatrix;
	}

	/**
	 * @param integerMatrix
	 *            the integerMatrix to set
	 */
	public void setIntegerMatrix(Integer[][] integerMatrix) {
		this.integerMatrix = integerMatrix;
	}

}
