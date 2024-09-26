//package himedia.project.careops.entity;
//
///**
// * @author 최은지 
// * @editDate 2024-09-25
// */
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="claim_sub_category")
//public class ClaimSubCategory {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="claim_sub_category_no", nullable = false)
//	private int claimSubCategoryNo;                 // 민원 소분류 번호 ( 기본키 auto_increment)
//	
//	@ManyToOne
//	@JoinColumn(name="lmd_minor_cate_code")
//	private ListMedicalDevices lmdMinorCateCode;    // 장비 세분류 코드 ( 의료기기 관리 )
//	
//	@Column(name="lmd_minor_cate_name")
//	private String lmdMinorCateName;                // 장비 세분류명 ( 의료기기 관리 )
//	
//	@ManyToOne
//	@JoinColumn(name = "sml_no")
//	private SafetyManagementList smlNo;             // 세부항목 번호 ( 안전관리 )
//	
//	@Column(name="sml_list")
//	private String smlList;                         // 세부항목 ( 안전관리 )
//
//	public ClaimSubCategory() {}
//
//	public ClaimSubCategory(int claimSubCategoryNo, ListMedicalDevices lmdMinorCateCode, String lmdMinorCateName,
//			SafetyManagementList smlNo, String smlList) {
//		super();
//		this.claimSubCategoryNo = claimSubCategoryNo;
//		this.lmdMinorCateCode = lmdMinorCateCode;
//		this.lmdMinorCateName = lmdMinorCateName;
//		this.smlNo = smlNo;
//		this.smlList = smlList;
//	}
//
//	public int getClaimSubCategoryNo() {
//		return claimSubCategoryNo;
//	}
//
//	public void setClaimSubCategoryNo(int claimSubCategoryNo) {
//		this.claimSubCategoryNo = claimSubCategoryNo;
//	}
//
//
//	public ListMedicalDevices getLmdMinorCateCode() {
//		return lmdMinorCateCode;
//	}
//
//	public void setLmdMinorCateCode(ListMedicalDevices lmdMinorCateCode) {
//		this.lmdMinorCateCode = lmdMinorCateCode;
//	}
//
//	public String getLmdMinorCateName() {
//		return lmdMinorCateName;
//	}
//
//	public void setLmdMinorCateName(String lmdMinorCateName) {
//		this.lmdMinorCateName = lmdMinorCateName;
//	}
//
//	public SafetyManagementList getSmlNo() {
//		return smlNo;
//	}
//
//	public void setSmlNo(SafetyManagementList smlNo) {
//		this.smlNo = smlNo;
//	}
//
//	public String getSmlList() {
//		return smlList;
//	}
//
//	public void setSmlList(String smlList) {
//		this.smlList = smlList;
//	}
//
//	@Override
//	public String toString() {
//		return "ClaimSubCategory [claimSubCategoryNo=" + claimSubCategoryNo + ", lmdMinorCateCode=" + lmdMinorCateCode
//				+ ", lmdMinorCateName=" + lmdMinorCateName + ", smlNo=" + smlNo + ", smlList=" + smlList + "]";
//	}
//	
//}
