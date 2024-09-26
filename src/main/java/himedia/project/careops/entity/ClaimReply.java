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
@Table(name="claim_re")
@ToString
public class ClaimReply {
	
	@EmbeddedId
    private ClaimReplyId claimReplyId;   // 민원 외래키 인덱스 설정 -> 기본키로 Embedded
   
	@Column(name="admin_id", nullable = false)
	private String adminId;				// 관리자 아이디 ( 외래키 )
	
	@Column(name="admin_dept_no", nullable = false)
	private String adminDeptNo;			// 관리자 부서번호 ( 외래키 )
	
	@Column(name="admin_name", nullable = false)
	private String admin_name;			// 관리자 이름
	
	@Column(name="claim_re_title", nullable = false)
	private String claimReTitle;		// 민원 답변 제목
	
	@Column(name="claim_re_content", nullable = false)
	private String claimReContent;		// 민원 답변 내용
	
	@Column(name="claim_re_date", nullable = false)
	private Date claimReDate;			// 민원 답변 일자

	public ClaimReply() {}
	
	public ClaimReply(ClaimReplyId claimReplyId, String adminId, String adminDeptNo, String admin_name,
			String claimReTitle, String claimReContent, Date claimReDate) {
		super();
		this.claimReplyId = claimReplyId;
		this.adminId = adminId;
		this.adminDeptNo = adminDeptNo;
		this.admin_name = admin_name;
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
		return "ClaimReply [claimReplyId=" + claimReplyId + ", adminId=" + adminId + ", adminDeptNo=" + adminDeptNo
				+ ", admin_name=" + admin_name + ", claimReTitle=" + claimReTitle + ", claimReContent=" + claimReContent
				+ ", claimReDate=" + claimReDate + "]";
	}
	
}
