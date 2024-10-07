package himedia.project.careops.dto;

/**
 * @author 최은지 
 * @editDate 2024-09-26 / 2024-10-07 타입 수정
 */

import java.sql.Date;

import himedia.project.careops.entity.Claim;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*@Getter
@Setter
@ToString*/
public class ClaimReplyDTO {
	
	private Integer claimNo;				// 민원 번호 ( 외래키 )
	private String claimCategoryNo;		// 민원 대분류 번호 ( 외래키 )
	private int claimSubCategoryNo;	// 민원 소분류 번호 ( 외래키 )
	private String managerId;			// 민원 담당자 아이디 ( 외래키 ) 
	private int managerDeptNo;		// 민원 담당자 부서번호 ( 외래키 )
	private String adminId;				// 관리자 아이디 ( 외래키 )
	private String adminDeptNo;			// 관리자 부서번호 ( 외래키 )
	private String adminName;			// 관리자 이름
	private String claimReTitle;		// 민원 답변 제목
	private String claimReContent;		// 민원 답변 내용
	private Date claimReDate;			// 민원 답변 일자
	
	

	public ClaimReplyDTO() {}
	

	public ClaimReplyDTO(Integer claimNo, String claimCategoryNo, int claimSubCategoryNo, String managerId,
			int managerDeptNo, String adminId, String adminDeptNo, String adminName, String claimReTitle,
			String claimReContent, Date claimReDate) {
		this.claimNo = claimNo;
		this.claimCategoryNo = claimCategoryNo;
		this.claimSubCategoryNo = claimSubCategoryNo;
		this.managerId = managerId;
		this.managerDeptNo = managerDeptNo;
		this.adminId = adminId;
		this.adminDeptNo = adminDeptNo;
		this.adminName = adminName;
		this.claimReTitle = claimReTitle;
		this.claimReContent = claimReContent;
		this.claimReDate = claimReDate;
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

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminDeptNo() {
		return adminDeptNo;
	}

	public void setAdminDeptNo(String adminDeptNo) {
		this.adminDeptNo = adminDeptNo;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getClaimReTitle() {
		return claimReTitle;
	}

	public void setClaimReTitle(String claimReTitle) {
		this.claimReTitle = claimReTitle;
	}
	
	public String getClaimReContent() {
		return claimReContent;
	}

	public void setClaimReContent(String claimReContent) {
		this.claimReContent = claimReContent;
	}

	public Date getClaimReDate() {
		return claimReDate;
	}

	public void setClaimReDate(Date claimReDate) {
		this.claimReDate = claimReDate;
	}

	@Override
	public String toString() {
		return "ClaimReplyDTO [claimNo=" + claimNo + ", claimCategoryNo=" + claimCategoryNo + ", claimSubCategoryNo="
				+ claimSubCategoryNo + ", managerId=" + managerId + ", managerDeptNo=" + managerDeptNo + ", adminId="
				+ adminId + ", adminDeptNo=" + adminDeptNo + ", adminName=" + adminName + ", claimReTitle="
				+ claimReTitle + ", claimReContent=" + claimReContent + ", claimReDate=" + claimReDate + "]";
	}
	
}
