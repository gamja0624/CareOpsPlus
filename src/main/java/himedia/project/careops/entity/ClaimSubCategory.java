package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-25
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name="claim_sub_category")
@ToString
public class ClaimSubCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="claim_sub_category_no", nullable = false)
	private int claimSubCategoryNo;               
	
	@Column(name="lmd_minor_cate_code")
	private String lmdMinorCateCode;                
	
	@Column(name="lmd_minor_cate_name")
	private String lmdMinorCateName;               
	
	@Column(name="sub_category_sort")
	private Integer subCategorySort;                              
	
	@Column(name="sub_category_name")
	private String subCategoryName;                         

	public ClaimSubCategory() {}

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
		return "ClaimSubCategory [claimSubCategoryNo=" + claimSubCategoryNo + ", lmdMinorCateCode=" + lmdMinorCateCode
				+ ", lmdMinorCateName=" + lmdMinorCateName + ", subCategorySort=" + subCategorySort
				+ ", subCategoryName=" + subCategoryName + "]";
	}

	
}
