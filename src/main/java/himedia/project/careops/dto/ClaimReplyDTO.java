package himedia.project.careops.dto;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import java.sql.Date;

import himedia.project.careops.entity.Claim;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClaimReplyDTO {
	
	private Claim claimNo;				// 민원 번호 ( 외래키 )
	private Claim claimCategoryNo;		// 민원 대분류 번호 ( 외래키 )
	private Claim claimSubCategoryNo;	// 민원 소분류 번호 ( 외래키 )
	private Claim managerId;			// 민원 담당자 아이디 ( 외래키 ) 
	private Claim managerDeptNo;		// 민원 담당자 부서번호 ( 외래키 )
	private String adminId;				// 관리자 아이디 ( 외래키 )
	private String adminDeptNo;			// 관리자 부서번호 ( 외래키 )
	private String admin_name;			// 관리자 이름
	private String claimReTitle;		// 민원 답변 제목
	private String claimReContent;		// 민원 답변 내용
	private Date claimReDate;			// 민원 답변 일자
	
	public ClaimReplyDTO() {}

	public ClaimReplyDTO(Claim claimNo, Claim claimCategoryNo, Claim claimSubCategoryNo, Claim managerId,
			Claim managerDeptNo, String adminId, String adminDeptNo, String admin_name, String claimReTitle,
			String claimReContent, Date claimReDate) {
		super();
		this.claimNo = claimNo;
		this.claimCategoryNo = claimCategoryNo;
		this.claimSubCategoryNo = claimSubCategoryNo;
		this.managerId = managerId;
		this.managerDeptNo = managerDeptNo;
		this.adminId = adminId;
		this.adminDeptNo = adminDeptNo;
		this.admin_name = admin_name;
		this.claimReTitle = claimReTitle;
		this.claimReContent = claimReContent;
		this.claimReDate = claimReDate;
	}

	public Claim getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(Claim claimNo) {
		this.claimNo = claimNo;
	}

	public Claim getClaimCategoryNo() {
		return claimCategoryNo;
	}

	public void setClaimCategoryNo(Claim claimCategoryNo) {
		this.claimCategoryNo = claimCategoryNo;
	}

	public Claim getClaimSubCategoryNo() {
		return claimSubCategoryNo;
	}

	public void setClaimSubCategoryNo(Claim claimSubCategoryNo) {
		this.claimSubCategoryNo = claimSubCategoryNo;
	}

	public Claim getManagerId() {
		return managerId;
	}

	public void setManagerId(Claim managerId) {
		this.managerId = managerId;
	}

	public Claim getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(Claim managerDeptNo) {
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

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
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
				+ adminId + ", adminDeptNo=" + adminDeptNo + ", admin_name=" + admin_name + ", claimReTitle="
				+ claimReTitle + ", claimReContent=" + claimReContent + ", claimReDate=" + claimReDate + "]";
	}
	
}
