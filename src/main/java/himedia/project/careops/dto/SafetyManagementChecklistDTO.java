package himedia.project.careops.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@ToString
public class SafetyManagementChecklistDTO {
	
	private int smcNo;
	private int smlNo;
	private String smlList;
	private String smcList;
	private Boolean smcCheck;
	private String smcIssue;
	
	public SafetyManagementChecklistDTO() {}

	public SafetyManagementChecklistDTO(int smcNo, int smlNo, String smlList, String smcList, Boolean smcCheck,
			String smcIssue) {
		this.smcNo = smcNo;
		this.smlNo = smlNo;
		this.smlList = smlList;
		this.smcList = smcList;
		this.smcCheck = smcCheck;
		this.smcIssue = smcIssue;
	}

	public int getSmcNo() {
		return smcNo;
	}

	public void setSmcNo(int smcNo) {
		this.smcNo = smcNo;
	}

	public int getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(int smlNo) {
		this.smlNo = smlNo;
	}

	public String getSmlList() {
		return smlList;
	}

	public void setSmlList(String smlList) {
		this.smlList = smlList;
	}

	public String getSmcList() {
		return smcList;
	}

	public void setSmcList(String smcList) {
		this.smcList = smcList;
	}

	public Boolean getSmcCheck() {
		return smcCheck;
	}

	public void setSmcCheck(Boolean smcCheck) {
		this.smcCheck = smcCheck;
	}

	public String getSmcIssue() {
		return smcIssue;
	}

	public void setSmcIssue(String smcIssue) {
		this.smcIssue = smcIssue;
	}

	@Override
	public String toString() {
		return "SafetyManagementChecklistDTO [ smcNo=" + smcNo + "smlNo=" + smlNo + "smlList=" + smlList 
				+ "smcList=" + smcList + "smcCheck=" + smcCheck + "smcIssue=" + smcIssue + "]";
	}
}
