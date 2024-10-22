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
public class ClaimSubCategoryDTO {

	private int claimSubCategoryNo;                 // 민원 소분류 번호 ( 기본키 auto_increment)
	private String lmdMinorCateCode;                // 장비 세분류 코드 ( 의료기기 관리 )
	private String lmdMinorCateName;                // 장비 세분류명 ( 의료기기 관리 )
	private Integer subCategorySort;                // 세부항목 번호 
	private String subCategoryName;					// 세부항목 번호 	
	
	public ClaimSubCategoryDTO() {}

	public int getClaimSubCategoryNo() {
		return claimSubCategoryNo;
	}

	public void setClaimSubCategoryNo(int claimSubCategoryNo) {
		this.claimSubCategoryNo = claimSubCategoryNo;
	}

	public String getLmdMinorCateCode() {
		return lmdMinorCateCode;
	}

	public void setLmdMinorCateCode(String lmdMinorCateCode) {
		this.lmdMinorCateCode = lmdMinorCateCode;
	}

	public String getLmdMinorCateName() {
		return lmdMinorCateName;
	}

	public void setLmdMinorCateName(String lmdMinorCateName) {
		this.lmdMinorCateName = lmdMinorCateName;
	}

	public Integer getSubCategorySort() {
		return subCategorySort;
	}

	public void setSubCategorySort(Integer subCategorySort) {
		this.subCategorySort = subCategorySort;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	@Override
	public String toString() {
		return "ClaimSubCategoryDTO [claimSubCategoryNo=" + claimSubCategoryNo + ", lmdMinorCateCode="
				+ lmdMinorCateCode + ", lmdMinorCateName=" + lmdMinorCateName + ", subCategorySort=" + subCategorySort
				+ ", subCategoryName=" + subCategoryName + "]";
	}
	
	
	
}
