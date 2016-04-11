package extrace.misc.model;

import java.io.Serializable;

public class Region implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2531774702426085013L;

	public Region() {
	}
	
	private String regionCode;
	
	private String prv;
	
	private String cty;
	
	private String twn;
	
	private int stage;
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public String getORMID() {
		return getRegionCode();
	}
	
	public void setPrv(String value) {
		this.prv = value;
	}
	
	public String getPrv() {
		return prv;
	}
	
	public void setCty(String value) {
		this.cty = value;
	}
	
	public String getCty() {
		return cty;
	}
	
	public void setTwn(String value) {
		this.twn = value;
	}
	
	public String getTwn() {
		return twn;
	}
	
	public void setStage(int value) {
		this.stage = value;
	}
	
	public int getStage() {
		return stage;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getRegionCode());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Region[ ");
			sb.append("RegionCode=").append(getRegionCode()).append(" ");
			sb.append("Prv=").append(getPrv()).append(" ");
			sb.append("Cty=").append(getCty()).append(" ");
			sb.append("Twn=").append(getTwn()).append(" ");
			sb.append("Stage=").append(getStage()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
