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
	
	private int managerDeptNo;        // 담당자 부서 번호
	private String managerDeptPart;       // 담당자 부서 파트
	private String managerDeptname;       // 담당자 부서 이름 
	
	public ManagerDepartmentDTO() {}
	
	public ManagerDepartmentDTO(int managerDeptNo, String managerDeptPart, String managerDeptname) {
		this.managerDeptNo = managerDeptNo;
		this.managerDeptPart = managerDeptPart;
		this.managerDeptname = managerDeptname;
	}

	public int getManagerDeptNo() {
		return managerDeptNo;
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

	public String getManagerDeptname() {
		return managerDeptname;
	}

	public void setManagerDeptname(String managerDeptname) {
		this.managerDeptname = managerDeptname;
	}

	@Override
	public String toString() {
		return "ManagerDepartmentDTO [managerDeptNo=" + managerDeptNo + ", managerDeptPart=" + managerDeptPart
				+ ", managerDeptname=" + managerDeptname + "]";
	}
}
