package extrace.misc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class CustomerInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3267943602377867497L;

	public CustomerInfo() {
	}
	
	@Expose private int ID;
	@Expose private String name;
	@Expose private String telCode;
	@Expose private String department;
	@Expose private String regionCode;
	@Expose private String address;
	@Expose private int postCode;

	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setDepartment(String value) {
		this.department = value;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setPostCode(int value) {
		this.postCode = value;
	}
	
	public int getPostCode() {
		return postCode;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("CustomerInfo[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("Department=").append(getDepartment()).append(" ");
			sb.append("RegionCode=").append(getRegionCode()).append(" ");
			sb.append("RegionString=").append(getRegionString()).append(" ");
			sb.append("Address=").append(getAddress()).append(" ");
			sb.append("PostCode=").append(getPostCode()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	private String regionString;

	public String getRegionString() {
		return regionString;
	}
	public void setRegionString(String value) {
		this.regionString = value;
	}
		
	private boolean _saved = false;
	
	public void onSave() {
		_saved=true;
	}
	
	public void onLoad() {
		_saved=true;
	}
	
	public boolean isSaved() {
		return _saved;
	}	
}
