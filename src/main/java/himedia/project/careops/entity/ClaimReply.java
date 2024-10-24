package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name="claim_reply")
@ToString
public class ClaimReply {
	
	@EmbeddedId
    private ClaimReplyId claimReplyId;   // 민원 외래키 인덱스 설정 -> 기본키로 Embedded
   
	@Column(name="admin_id", nullable = false)
	private String adminId;				
	
	@Column(name="admin_dept_no", nullable = false)
	private String adminDeptNo;			
	
	@Column(name="admin_name", nullable = false)
	private String adminName;			
	
	@Column(name="claim_re_title", nullable = false)
	private String claimReTitle;		
	
	@Column(name="claim_re_content", nullable = false)
	private String claimReContent;		
	
	@Column(name="claim_re_date", nullable = false)
	private Date claimReDate;			

	public ClaimReply() {}
	
	public ClaimReply(ClaimReplyId claimReplyId, String adminId, String adminDeptNo, String adminName,
			String claimReTitle, String claimReContent, Date claimReDate) {
		super();
		this.claimReplyId = claimReplyId;
		this.adminId = adminId;
		this.adminDeptNo = adminDeptNo;
		this.adminName = adminName;
		this.claimReTitle = claimReTitle;
		this.claimReContent = claimReContent;
		this.claimReDate = claimReDate;
	}
	
	public ClaimReplyId getClaimReplyId() {
		return claimReplyId;
	}

	public void setClaimReplyId(ClaimReplyId claimReplyId) {
		this.claimReplyId = claimReplyId;
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
		return "ClaimReply [claimReplyId=" + claimReplyId + ", adminId=" + adminId + ", adminDeptNo=" + adminDeptNo
				+ ", adminName=" + adminName + ", claimReTitle=" + claimReTitle + ", claimReContent=" + claimReContent
				+ ", claimReDate=" + claimReDate + "]";
	}
	
}
