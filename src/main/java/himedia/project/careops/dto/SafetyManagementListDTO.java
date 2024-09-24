package himedia.project.careops.dto;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@ToString
public class SafetyManagementListDTO {
	
	private int smlNo;
	private int smNo;
	private String smlList;
	private Boolean smlCheck;
	
	public SafetyManagementListDTO() {}

	public SafetyManagementListDTO(int smlNo, int smNo, String smlList, Boolean smlCheck) {
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

	public int getSmNo() {
		return smNo;
	}

	public void setSmNo(int smNo) {
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
