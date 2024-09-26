//package himedia.project.careops.dto;
//
///**
// * @author 최은지 
// * @editDate 2024-09-25
// */
//
//import himedia.project.careops.entity.ListMedicalDevices;
//import himedia.project.careops.entity.SafetyManagementList;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@ToString
//public class ClaimSubCategoryDTO {
//
//	private int claimSubCategoryNo;                 // 민원 소분류 번호 ( 기본키 auto_increment)
//	private ListMedicalDevices lmdMinorCateCode;    // 장비 세분류 코드 ( 의료기기 관리 )
//	private String lmdMinorCateName;                // 장비 세분류명 ( 의료기기 관리 )
//	private SafetyManagementList smlNo;             // 세부항목 번호 ( 안전관리 )
//	private String smlList;                         // 세부항목 ( 안전관리 )
//	
//	public ClaimSubCategoryDTO() {}
//
//	public ClaimSubCategoryDTO(int claimSubCategoryNo, ListMedicalDevices lmdMinorCateCode, String lmdMinorCateName,
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
//		return "ClaimSubCategoryDTO [claimSubCategoryNo=" + claimSubCategoryNo + ", lmdMinorCateCode="
//				+ lmdMinorCateCode + ", lmdMinorCateName=" + lmdMinorCateName + ", smlNo=" + smlNo + ", smlList="
//				+ smlList + "]";
//	}
//}
