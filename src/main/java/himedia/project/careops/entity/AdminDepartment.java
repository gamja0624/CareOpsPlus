package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-24
 */


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_department")
public class AdminDepartment {
	
	@Id
	@Column(name="admin_dept_no", nullable = false)
	private String adminDeptNo;  // 작업관리자 부서 번호
	
	@Column(name="admin_dept_name", nullable = false)
	private String adminDeptName; // 작업 관리자 부서 이름


	public AdminDepartment() {}


	public AdminDepartment(String adminDeptNo, String adminDeptName) {
		this.adminDeptNo = adminDeptNo;
		this.adminDeptName = adminDeptName;
	}


	public String getAdminDeptNo() {
		return adminDeptNo;
	}


	public void setAdminDeptNo(String adminDeptNo) {
		this.adminDeptNo = adminDeptNo;
	}


	public String getAdminDeptName() {
		return adminDeptName;
	}


	public void setAdminDeptName(String adminDeptName) {
		this.adminDeptName = adminDeptName;
	}


	@Override
	public String toString() {
		return "AdminDepartment [adminDeptNo=" + adminDeptNo + ", adminDeptName=" + adminDeptName + "]";
	}
	
}
