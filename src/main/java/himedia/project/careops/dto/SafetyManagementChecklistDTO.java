package himedia.project.careops.dto;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

import himedia.project.careops.entity.SafetyManagementList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SafetyManagementChecklistDTO {
	
	private int smcNo;
	private SafetyManagementList smlNo;
	private String smlList;
	private String smcList;
	private Boolean smcCheck;
	private String smcIssue;
	
	public SafetyManagementChecklistDTO() {}

	public SafetyManagementChecklistDTO(int smcNo, SafetyManagementList smlNo, String smlList, String smcList, Boolean smcCheck,
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

	public SafetyManagementList getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(SafetyManagementList smlNo) {
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
