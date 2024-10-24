package himedia.project.careops.dto;

/**
 * @author 최은지 
 * @editDate 2024-09-25
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClaimCategoryDTO {
	
	private String claimCategoryNo;         // 민원 대분류 번호 
	private String claimCategoryName;       // 민원 대분류 이름 
	
	public ClaimCategoryDTO() {}

	public ClaimCategoryDTO(String claimCategoryNo, String claimCategoryName) {
		super();
		this.claimCategoryNo = claimCategoryNo;
		this.claimCategoryName = claimCategoryName;
	}

	public String getClaimCategoryNo() {
		return claimCategoryNo;
	}

	public void setClaimCategoryNo(String claimCategoryNo) {
		this.claimCategoryNo = claimCategoryNo;
	}

	public String getClaimCategoryName() {
		return claimCategoryName;
	}

	public void setClaimCategoryName(String claimCategoryName) {
		this.claimCategoryName = claimCategoryName;
	}

	@Override
	public String toString() {
		return "ClaimCategoryDTO [claimCategoryNo=" + claimCategoryNo + ", claimCategoryName=" + claimCategoryName
				+ "]";
	}
	
}
