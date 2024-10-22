package himedia.project.careops.entity;

/**
 * @author 이홍준 
 * @editDate 2024-09-25 ~ 2024-10-04
 */

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "daily_management_report")
@ToString
public class DailyManagementReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dmr_no", nullable = false)
	private Integer dmrNo;
	
	@Column(name="admin_id", nullable = false)
	private String adminId;
	
	@Column(name="admin_dept_no", nullable = false)
	private String adminDeptNo;
	
	@Column(name="admin_name", nullable = false)
	private String adminName;
	
	@Column(name="admin_dept_name", nullable = false)
	private String adminDeptName;
	
	@Column(name="dmr_report_detail", nullable = false)
	private String dmrReportDetail;
	
	@Column(name="dmr_issue", nullable = false)
	private String dmrIssue;
	
	@Column(name="dmr_date", nullable = false)
	private Date dmrDate;
	
	public DailyManagementReport() {}
	
	public DailyManagementReport(Integer dmrNo, String adminId, String adminDeptNo, String adminName,
			String adminDeptName, String dmrReportDetail, String dmrIssue, Date dmrDate) {
		this.dmrNo = dmrNo;
		this.adminId = adminId;
		this.adminDeptNo = adminDeptNo;
		this.adminName = adminName;
		this.adminDeptName = adminDeptName;
		this.dmrReportDetail = dmrReportDetail;
		this.dmrIssue = dmrIssue;
		this.dmrDate = dmrDate;
	}

	public Integer getDmrNo() {
		return dmrNo;
	}

	public void setDmrNo(Integer dmrNo) {
		this.dmrNo = dmrNo;
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

	public String getAdminDeptName() {
		return adminDeptName;
	}

	public void setAdminDeptName(String adminDeptName) {
		this.adminDeptName = adminDeptName;
	}

	public String getDmrReportDetail() {
		return dmrReportDetail;
	}

	public void setDmrReportDetail(String dmrReportDetail) {
		this.dmrReportDetail = dmrReportDetail;
	}

	public String getDmrIssue() {
		return dmrIssue;
	}

	public void setDmrIssue(String dmrIssue) {
		this.dmrIssue = dmrIssue;
	}

	public Date getDmrDate() {
		return dmrDate;
	}

	public void setDmrDate(Date dmrDate) {
		this.dmrDate = dmrDate;
	}
	
	@Override
	public String toString() {
		return "DailyManagementReport [ dmrNo=" + dmrNo + "adminId=" + adminId + "adminDeptNo=" + adminDeptNo + "adminName=" + adminName 
				+ "adminDeptName" + adminDeptName + "dmrReportDetail=" + dmrReportDetail + "dmrIssue=" + dmrIssue + "dmrDate=" + dmrDate;
	}
}
