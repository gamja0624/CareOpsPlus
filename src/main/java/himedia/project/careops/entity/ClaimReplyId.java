package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class ClaimReplyId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long claimNo;			 	// 민원 번호
    private Long claimCategoryNo; 		// 민원 대분류 번호
    private Long claimSubCategoryNo; 	// 민원 소분류 번호
    private String managerId; 			// 민원 담당자 아이디
    private String managerDeptNo; 		// 민원 담당자 부서번호

    public ClaimReplyId() {}

	public ClaimReplyId(Long claimNo, Long claimCategoryNo, Long claimSubCategoryNo, String managerId,
			String managerDeptNo) {
		super();
		this.claimNo = claimNo;
		this.claimCategoryNo = claimCategoryNo;
		this.claimSubCategoryNo = claimSubCategoryNo;
		this.managerId = managerId;
		this.managerDeptNo = managerDeptNo;
	}

	public Long getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(Long claimNo) {
		this.claimNo = claimNo;
	}

	public Long getClaimCategoryNo() {
		return claimCategoryNo;
	}

	public void setClaimCategoryNo(Long claimCategoryNo) {
		this.claimCategoryNo = claimCategoryNo;
	}

	public Long getClaimSubCategoryNo() {
		return claimSubCategoryNo;
	}

	public void setClaimSubCategoryNo(Long claimSubCategoryNo) {
		this.claimSubCategoryNo = claimSubCategoryNo;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(String managerDeptNo) {
		this.managerDeptNo = managerDeptNo;
	}

	@Override
	public String toString() {
		return "ClaimReplyId [claimNo=" + claimNo + ", claimCategoryNo=" + claimCategoryNo + ", claimSubCategoryNo="
				+ claimSubCategoryNo + ", managerId=" + managerId + ", managerDeptNo=" + managerDeptNo + "]";
	}

    
}