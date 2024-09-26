//package himedia.project.careops.entity;
//
///**
// * @author 최은지 
// * @editDate 2024-09-25
// */
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "claim_category")
//public class ClaimCategory {
//	
//	@Id
//	@Column(name="claim_category_no", nullable = false)
//	private String claimCategoryNo;
//	
//	@Column(name="claim_category_name", nullable = false)
//	private String claimCategoryName;
//	
//	public ClaimCategory() {}
//
//	public ClaimCategory(String claimCategoryNo, String claimCategoryName) {
//		super();
//		this.claimCategoryNo = claimCategoryNo;
//		this.claimCategoryName = claimCategoryName;
//	}
//
//	public String getClaimCategoryNo() {
//		return claimCategoryNo;
//	}
//
//	public void setClaimCategoryNo(String claimCategoryNo) {
//		this.claimCategoryNo = claimCategoryNo;
//	}
//
//	public String getClaimCategoryName() {
//		return claimCategoryName;
//	}
//
//	public void setClaimCategoryName(String claimCategoryName) {
//		this.claimCategoryName = claimCategoryName;
//	}
//
//	@Override
//	public String toString() {
//		return "ClaimCategory [claimCategoryNo=" + claimCategoryNo + ", claimCategoryName=" + claimCategoryName + "]";
//	}
//	
//}
