package himedia.project.careops.dto;

/**
 * @author 최은지 
 * @editDate 2024-09-24
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AdminDepartmentDTO {
	
	private String adminDeptNo;    // 관리자 부서 번호
	private String adminDeptName;  // 관리자 부서 이름
	
	public AdminDepartmentDTO() {}

	public AdminDepartmentDTO(String adminDeptNo, String adminDeptName) {
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
		return "AdminDepartmentDTO [adminDeptNo=" + adminDeptNo + ", adminDeptName=" + adminDeptName + "]";
	}
	
}
