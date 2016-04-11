package extrace.misc.model;

public class CodeNamePair {

	private String Code;
	public String getCode() {
		return Code;
	}

	private String Name;
	public String getName() {
		return Name;
	}
	
	public CodeNamePair(){}
	
	public CodeNamePair(String code, String name){
		Code = code;
		Name = name;
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return getCode();
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("CodeNamePair[ ");
			sb.append("Code=").append(getCode()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
}
