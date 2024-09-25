package himedia.project.careops.dto;

/**
 * @author 최은지 
 * @editDate 2024-09-25
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ManagerDepartmentDTO {
	
	private Integer managerDeptNo;            // 담당자 부서 번호
	private String managerDeptPart;           // 담당자 부서 파트
	private String managerDeptName;           // 담당자 부서 이름 
	
	public ManagerDepartmentDTO() {}
	
	public ManagerDepartmentDTO(Integer managerDeptNo, String managerDeptPart, String managerDeptName) {
		this.managerDeptNo = managerDeptNo;
		this.managerDeptPart = managerDeptPart;
		this.managerDeptName = managerDeptName;
	}
	

	public Integer getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(Integer managerDeptNo) {
		this.managerDeptNo = managerDeptNo;
	}

	public void setManagerDeptNo(int managerDeptNo) {
		this.managerDeptNo = managerDeptNo;
	}

	public String getManagerDeptPart() {
		return managerDeptPart;
	}

	public void setManagerDeptPart(String managerDeptPart) {
		this.managerDeptPart = managerDeptPart;
	}

	public String getManagerDeptName() {
		return managerDeptName;
	}

	public void setManagerDeptName(String managerDeptName) {
		this.managerDeptName = managerDeptName;
	}

	@Override
	public String toString() {
		return "ManagerDepartmentDTO [managerDeptNo=" + managerDeptNo + ", managerDeptPart=" + managerDeptPart
				+ ", managerDeptName=" + managerDeptName + "]";
	}
}
