package himedia.project.careops.dto;

import java.sql.Date;

import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.AdminDepartment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 이홍준 
 * @editDate 2024-09-25
 */

@Getter
@Setter
@ToString
public class DailyManagementReportDTO {

	private int dmrNo;
	private Admin adminId;
	private AdminDepartment adminDeptNo;
	private String adminName;
	private String adminDeptName;
	private String dmrReportDetail;
	private String dmrIssue;
	private Date dmrDate;
	
	public DailyManagementReportDTO() {}
	
	public DailyManagementReportDTO(int dmrNo, Admin adminId, AdminDepartment adminDeptNo, String adminName,
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

	public int getDmrNo() {
		return dmrNo;
	}

	public void setDmrNo(int dmrNo) {
		this.dmrNo = dmrNo;
	}

	public Admin getAdminId() {
		return adminId;
	}

	public void setAdminId(Admin adminId) {
		this.adminId = adminId;
	}

	public AdminDepartment getAdminDeptNo() {
		return adminDeptNo;
	}

	public void setAdminDeptNo(AdminDepartment adminDeptNo) {
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
		return "DailyManagementReportDTO [ dmrNo=" + dmrNo + "adminId=" + adminId + "adminDeptNo=" + adminDeptNo + "adminName=" + adminName 
				+ "adminDeptName" + adminDeptName + "dmrReportDetail=" + dmrReportDetail + "dmrIssue=" + dmrIssue + "dmrDate=" + dmrDate;
	}
}
