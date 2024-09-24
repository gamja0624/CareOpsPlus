package himedia.project.careops.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDTO {
	
    private String managerId;
    private Integer managerDeptNo;
    private String managerDeptPart;
    private String managerDeptName;
    private String managerPassword;
    private String managerName;
    private String managerPhoneNumber;
    
    public ManagerDTO() {} 
    
	public ManagerDTO(String managerId, Integer managerDeptNo, String managerDeptPart, String managerDeptName, 
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

	public Integer getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(Integer managerDeptNo) {
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
