package open.pandurang.gwt.requestfactory.test;

import java.util.Date;

import open.pandurang.gwt.helper.requestfactory.marker.RFEntityProxy;
import open.pandurang.gwt.helper.requestfactory.marker.RFProxyMethod;
import open.pandurang.gwt.requestfactory.helper.Status;
import open.pandurang.gwt.requestfactory.helper.UserStatus;


@RFEntityProxy(value = OtherEntityLocator.class, generateEntityRequest = false)
public class OtherEntity extends BaseEntity {

	private String id;
	private double salary;
	private String location;
	private Status status;
	private Date date;
	private UserStatus userStatus;

	// //Negative test case to test it uncomment
	// private java.sql.Date sqlDate;

	/**
	 * @return the id
	 */
	@RFProxyMethod
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@RFProxyMethod
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the salary
	 */
	@RFProxyMethod
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	@RFProxyMethod
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * @return the location
	 */
	@RFProxyMethod
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	@RFProxyMethod
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the status
	 */
	@RFProxyMethod
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	@RFProxyMethod
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the date
	 */
	@RFProxyMethod
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	@RFProxyMethod
	public void setDate(Date date) {
		this.date = date;
	}

	// /**
	// * Negative test case to test it uncomment
	// * @return the sqlDate
	// */
	// @RFProxyMethod
	// public java.sql.Date getSqlDate() {
	// return sqlDate;
	// }
	//
	// /**
	// * Negative test case to test it uncomment
	// * @param sqlDate
	// * the sqlDate to set
	// */
	// @RFProxyMethod
	// public void setSqlDate(java.sql.Date sqlDate) {
	// this.sqlDate = sqlDate;
	// }

	/**
	 * @return the userStatus
	 */
	@RFProxyMethod
	public UserStatus getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            the userStatus to set
	 */
	@RFProxyMethod
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

}
