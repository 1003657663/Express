package extrace.misc.model;
public class UserInfo{
	private int UID;
	private String PWD;
	private String name;
	private int URull;
	private String telCode;
	private int status;
	private String dptID;
	private String receivePackageID;
	private String delivePackageID;
	private String transPackageID;

	public int getUID() {
		return UID;
	}
	
	public int setID(int id) {
		return UID = id;
	}
	
	public void setPWD(String value) {
		this.PWD = value;
	}
	
	public String getPWD() {
		return PWD;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setURull(int value) {
		this.URull = value;
	}
	
	public int getURull() {
		return URull;
	}
	
	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setDptID(String value) {
		this.dptID = value;
	}
	
	public String getDptID() {
		return dptID;
	}
	
	public void setReceivePackageID(String value) {
		this.receivePackageID = value;
	}
	
	public String getReceivePackageID() {
		return receivePackageID;
	}
	
	public void setDelivePackageID(String value) {
		this.delivePackageID = value;
	}
	
	public String getDelivePackageID() {
		return delivePackageID;
	}
	
	public void setTransPackageID(String value) {
		this.transPackageID = value;
	}
	
	public String geTransPackageID() {
		return transPackageID;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getUID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("UserInfo[ ");
			sb.append("UID=").append(getUID()).append(" ");
			sb.append("PWD=").append(getPWD()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("URull=").append(getURull()).append(" ");
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("DptID=").append(getDptID()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
