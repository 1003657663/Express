package extrace.misc.model;

import java.io.Serializable;

public class TransPackageContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2696910600791838998L;

	public TransPackageContent() {
	}
	
	private int SN;
	private ExpressSheet express;
	private TransPackage pkg;
	
	public int getSN() {
		return SN;
	}

	public ExpressSheet getExpress() {
		return express;
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
			sb.append("TransPackageContent[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getExpress() != null)
				sb.append("Express.Persist_ID=").append(getExpress().toString(true)).append(" ");
			else
				sb.append("Express=null ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("]");
			return sb.toString();
		}
	}
	public static final class STATUS{
		public static final int STATUS_ACTIVE = 0;
		public static final int STATUS_OUTOF_PACKAGE = 1;
	}	
}
