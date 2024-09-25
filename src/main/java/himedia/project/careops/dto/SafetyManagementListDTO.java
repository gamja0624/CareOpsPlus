package himedia.project.careops.dto;

import himedia.project.careops.entity.SafetyManagement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SafetyManagementListDTO {
	
	private int smlNo;
	private SafetyManagement smNo;
	private String smlList;
	private Boolean smlCheck;
	
	public SafetyManagementListDTO() {}

	public SafetyManagementListDTO(int smlNo, SafetyManagement smNo, String smlList, Boolean smlCheck) {
		this.smlNo = smlNo;
		this.smNo = smNo;
		this.smlList = smlList;
		this.smlCheck = smlCheck;
	}

	public int getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(int smlNo) {
		this.smlNo = smlNo;
	}

	public SafetyManagement getSmNo() {
		return smNo;
	}

	public void setSmNo(SafetyManagement smNo) {
		this.smNo = smNo;
	}

	public String getSmlList() {
		return smlList;
	}

	public void setSmlList(String smlList) {
		this.smlList = smlList;
	}

	public Boolean getSmlCheck() {
		return smlCheck;
	}

	public void setSmlCheck(Boolean smlCheck) {
		this.smlCheck = smlCheck;
	}
	
	@Override
	public String toString() {
		return "SafetyManagementListDTO [ smlNo=" + smlNo + "smNo=" + smNo 
				+ "smList=" + smlList + "smlCheck=" + smlCheck + "]";
	}
}
