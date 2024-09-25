package himedia.project.careops.dto;

import himedia.project.careops.entity.ManagerDepartment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDTO {
	
    private String managerId;					// 담당자 아이디
    private ManagerDepartment managerDeptNo; 	// 담당자 부서 번호 
    private String managerDeptPart;				// 담당자 부서 파트
    private String managerDeptName;				// 담당자 부서 이름
    private String managerPassword;				// 담당자 비밀번호
    private String managerName;					// 담당자 이름
    private String managerPhoneNumber;			// 담당자 연락처
    
    public ManagerDTO() {} 
    
	public ManagerDTO(String managerId, ManagerDepartment managerDeptNo, String managerDeptPart, String managerDeptName, 
			String managerPassword, String managerName, String managerPhoneNumber) {
			super();
			this.managerId = managerId;
			this.managerDeptNo = managerDeptNo;
			this.managerDeptPart = managerDeptPart;
			this.managerDeptName = managerDeptName;
			this.managerPassword = managerPassword;
			this.managerName = managerName;
			this.managerPhoneNumber = managerPhoneNumber;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public ManagerDepartment getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(ManagerDepartment managerDeptNo) {
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

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}
	
	@Override
	public String toString() {
		return "ManagerDTO [managerId=" + managerId + ", managerDeptNo=" + managerDeptNo 
				+ ", managerDeptPart=" + managerDeptPart + ", managerDeptName=" + managerDeptName 
				+ ", managerPassword=" + managerPassword + ", managerName=" + managerName 
				+ ", managerPhoneNumber=" + managerPhoneNumber + "]";
	}

}
