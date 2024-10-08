package himedia.project.careops.dto;

import himedia.project.careops.entity.SafetyManagement;

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
public class SafetyManagementListDTO {
	
	private Integer smlNo;
	private Integer smNo;
	private String smlList;
	private Boolean smlCheck;
	
	public SafetyManagementListDTO() {}

	public SafetyManagementListDTO(Integer smlNo, Integer smNo, String smlList, Boolean smlCheck) {
		this.smlNo = smlNo;
		this.smNo = smNo;
		this.smlList = smlList;
		this.smlCheck = smlCheck;
	}

	public Integer getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(Integer smlNo) {
		this.smlNo = smlNo;
	}

	public Integer getSmNo() {
		return smNo;
	}

	public void setSmNo(Integer smNo) {
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
