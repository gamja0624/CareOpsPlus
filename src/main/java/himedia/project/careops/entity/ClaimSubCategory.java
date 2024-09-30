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
	private int claimSubCategoryNo;                 // 민원 소분류 번호 ( 기본키 auto_increment)
	
	@Column(name="lmd_minor_cate_code")
	private String lmdMinorCateCode;                // 장비 세분류 코드 ( 의료기기 관리 )
	
	@Column(name="lmd_minor_cate_name")
	private String lmdMinorCateName;                // 장비 세분류명 ( 의료기기 관리 )
	
	@Column(name="sml_no", nullable = true)
	private Integer smlNo;                              // 세부항목 번호 ( 안전관리 )
	
	@Column(name="sml_list")
	private String smlList;                         // 세부항목 ( 안전관리 )

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

	public Integer getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(Integer smlNo) {
		this.smlNo = smlNo;
	}

	public String getSmlList() {
		return smlList;
	}

	public void setSmlList(String smlList) {
		this.smlList = smlList;
	}

	@Override
	public String toString() {
		return "ClaimSubCategory [claimSubCategoryNo=" + claimSubCategoryNo + ", lmdMinorCateCode=" + lmdMinorCateCode
				+ ", lmdMinorCateName=" + lmdMinorCateName + ", smlNo=" + smlNo + ", smlList=" + smlList + "]";
	}
	
}
