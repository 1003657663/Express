/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package extrace.misc.model;

import java.io.Serializable;

public class UsersPackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753829022427770282L;

	public UsersPackage() {
	}
		
	private int SN;
	private UserInfo userU;
	private TransPackage pkg;
	
	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setUserU(UserInfo value) {
		this.userU = value;
	}
	
	public UserInfo getUserU() {
		return userU;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSN());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("UsersPackage[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getUserU() != null)
				sb.append("UserU.Persist_ID=").append(getUserU().toString(true)).append(" ");
			else
				sb.append("UserU=null ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
