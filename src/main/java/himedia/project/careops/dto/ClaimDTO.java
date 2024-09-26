package himedia.project.careops.dto;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import java.sql.Date;

import himedia.project.careops.entity.ClaimCategory;
import himedia.project.careops.entity.ClaimSubCategory;
import himedia.project.careops.entity.Manager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClaimDTO {

	private int claimNo;						// 민원 번호 ( 고유키 )
	private Manager managerId;					// 담당자 아이디 ( 외래키 )         				
	private Manager managerDeptNo;				// 담당자 부서번호 ( 외래키 ) 
	private ClaimCategory claimCategoryNo;		// 민원 대분류 번호 ( 외래키 )
	private ClaimSubCategory claimSubCategoryNO;// 민원 소뷴류 번호 ( 외래키 )	
	private String claimCategoryName;			// 민원 대분류 이름 
	private String claimSubCategoryName;		// 민원 소분류 이름
	private String claimCategoryStatus;			// 민원 요청 구분 ( * 의료 기기 관리 ) 
	private String claimManagerName;			// 담당자 이름
	private Date claimDate;						// 민원 신청일
	private String claimTitle;					// 민원 제목
	private String claimContent;				// 민원 내용
	private Boolean claimApprove;				// 민원 승인 여부
	private Boolean claimComplete;				// 민원 처리 여부
	private String claimAttachment;				// 민원 첨부파일
	
	public ClaimDTO() {}

	public ClaimDTO(int claimNo, Manager managerId, Manager managerDeptNo, ClaimCategory claimCategoryNo,
			ClaimSubCategory claimSubCategoryNO, String claimCategoryName, String claimSubCategoryName,
			String claimCategoryStatus, String claimManagerName, Date claimDate, String claimTitle, String claimContent,
			Boolean claimApprove, Boolean claimComplete, String claimAttachment) {
		super();
		this.claimNo = claimNo;
		this.managerId = managerId;
		this.managerDeptNo = managerDeptNo;
		this.claimCategoryNo = claimCategoryNo;
		this.claimSubCategoryNO = claimSubCategoryNO;
		this.claimCategoryName = claimCategoryName;
		this.claimSubCategoryName = claimSubCategoryName;
		this.claimCategoryStatus = claimCategoryStatus;
		this.claimManagerName = claimManagerName;
		this.claimDate = claimDate;
		this.claimTitle = claimTitle;
		this.claimContent = claimContent;
		this.claimApprove = claimApprove;
		this.claimComplete = claimComplete;
		this.claimAttachment = claimAttachment;
	}

	public int getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(int claimNo) {
		this.claimNo = claimNo;
	}

	public Manager getManagerId() {
		return managerId;
	}

	public void setManagerId(Manager managerId) {
		this.managerId = managerId;
	}

	public Manager getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(Manager managerDeptNo) {
		this.managerDeptNo = managerDeptNo;
	}

	public ClaimCategory getClaimCategoryNo() {
		return claimCategoryNo;
	}

	public void setClaimCategoryNo(ClaimCategory claimCategoryNo) {
		this.claimCategoryNo = claimCategoryNo;
	}

	public ClaimSubCategory getClaimSubCategoryNO() {
		return claimSubCategoryNO;
	}

	public void setClaimSubCategoryNO(ClaimSubCategory claimSubCategoryNO) {
		this.claimSubCategoryNO = claimSubCategoryNO;
	}

	public String getClaimCategoryName() {
		return claimCategoryName;
	}

	public void setClaimCategoryName(String claimCategoryName) {
		this.claimCategoryName = claimCategoryName;
	}

	public String getClaimSubCategoryName() {
		return claimSubCategoryName;
	}

	public void setClaimSubCategoryName(String claimSubCategoryName) {
		this.claimSubCategoryName = claimSubCategoryName;
	}

	public String getClaimCategoryStatus() {
		return claimCategoryStatus;
	}

	public void setClaimCategoryStatus(String claimCategoryStatus) {
		this.claimCategoryStatus = claimCategoryStatus;
	}

	public String getClaimManagerName() {
		return claimManagerName;
	}

	public void setClaimManagerName(String claimManagerName) {
		this.claimManagerName = claimManagerName;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getClaimTitle() {
		return claimTitle;
	}

	public void setClaimTitle(String claimTitle) {
		this.claimTitle = claimTitle;
	}

	public String getClaimContent() {
		return claimContent;
	}

	public void setClaimContent(String claimContent) {
		this.claimContent = claimContent;
	}

	public Boolean getClaimApprove() {
		return claimApprove;
	}

	public void setClaimApprove(Boolean claimApprove) {
		this.claimApprove = claimApprove;
	}

	public Boolean getClaimComplete() {
		return claimComplete;
	}

	public void setClaimComplete(Boolean claimComplete) {
		this.claimComplete = claimComplete;
	}

	public String getClaimAttachment() {
		return claimAttachment;
	}

	public void setClaimAttachment(String claimAttachment) {
		this.claimAttachment = claimAttachment;
	}

	@Override
	public String toString() {
		return "ClaimDTO [claimNo=" + claimNo + ", managerId=" + managerId + ", managerDeptNo=" + managerDeptNo
				+ ", claimCategoryNo=" + claimCategoryNo + ", claimSubCategoryNO=" + claimSubCategoryNO
				+ ", claimCategoryName=" + claimCategoryName + ", claimSubCategoryName=" + claimSubCategoryName
				+ ", claimCategoryStatus=" + claimCategoryStatus + ", claimManagerName=" + claimManagerName
				+ ", claimDate=" + claimDate + ", claimTitle=" + claimTitle + ", claimContent=" + claimContent
				+ ", claimApprove=" + claimApprove + ", claimComplete=" + claimComplete + ", claimAttachment="
				+ claimAttachment + "]";
	}
}
