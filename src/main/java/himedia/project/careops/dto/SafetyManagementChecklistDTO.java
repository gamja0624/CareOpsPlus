package himedia.project.careops.dto;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SafetyManagementChecklistDTO {
	
	private Integer smcNo;
	private int smlNo;
	private String smlList;
	private int smcFloor;
	private String smcList;
	private Boolean smcCheck;
	private String smcIssue;
	
	public SafetyManagementChecklistDTO() {}

	public SafetyManagementChecklistDTO(Integer smcNo, int smlNo, String smlList, int smcFloor, String smcList,
			Boolean smcCheck, String smcIssue) {
		super();
		this.smcNo = smcNo;
		this.smlNo = smlNo;
		this.smlList = smlList;
		this.smcFloor = smcFloor;
		this.smcList = smcList;
		this.smcCheck = smcCheck;
		this.smcIssue = smcIssue;
	}

	public Integer getSmcNo() {
		return smcNo;
	}

	public void setSmcNo(Integer smcNo) {
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

	public int getSmcFloor() {
		return smcFloor;
	}

	public void setSmcFloor(int smcFloor) {
		this.smcFloor = smcFloor;
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
		return "SafetyManagementChecklistDTO [ smcNo=" + smcNo + "smlNo=" + smlNo + "smlList=" + smlList + "smcFloor = " + smcFloor 
				+ "smcList=" + smcList + "smcCheck=" + smcCheck + "smcIssue=" + smcIssue + "]";
	}
}
