package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.ToString;

@Embeddable
@ToString
public class ClaimReplyId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer claimNo;            
    private String claimCategoryNo; 		
    private int claimSubCategoryNo; 	
    private String managerId; 			
    private int managerDeptNo; 		
    
    public ClaimReplyId() {}

	public ClaimReplyId(Integer claimNo, String claimCategoryNo, int claimSubCategoryNo, String managerId,
			int managerDeptNo) {
		super();
		this.claimNo = claimNo;
		this.claimCategoryNo = claimCategoryNo;
		this.claimSubCategoryNo = claimSubCategoryNo;
		this.managerId = managerId;
		this.managerDeptNo = managerDeptNo;
	}

	public Integer getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(Integer claimNo) {
		this.claimNo = claimNo;
	}

	public String getClaimCategoryNo() {
		return claimCategoryNo;
	}

	public void setClaimCategoryNo(String claimCategoryNo) {
		this.claimCategoryNo = claimCategoryNo;
	}

	public int getClaimSubCategoryNo() {
		return claimSubCategoryNo;
	}

	public void setClaimSubCategoryNo(int claimSubCategoryNo) {
		this.claimSubCategoryNo = claimSubCategoryNo;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public int getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(int managerDeptNo) {
		this.managerDeptNo = managerDeptNo;
	}

	@Override
	public String toString() {
		return "ClaimReplyId [claimNo=" + claimNo + ", claimCategoryNo=" + claimCategoryNo + ", claimSubCategoryNo="
				+ claimSubCategoryNo + ", managerId=" + managerId + ", managerDeptNo=" + managerDeptNo + "]";
	}
}