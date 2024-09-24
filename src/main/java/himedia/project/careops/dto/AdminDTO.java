package himedia.project.careops.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {

    private String adminId;
    private String adminDeptNo;
    private String adminDeptName;
    private String adminPassword; 
    private String adminName;
    private String adminPhoneNumber;

    public AdminDTO() {}
    
	public AdminDTO(String adminId, String adminDeptNo, String adminDeptName, String adminPassword, 
			String adminName, String adminPhoneNumber) {
			super();
			this.adminId = adminId;
			this.adminDeptNo = adminDeptNo;
			this.adminDeptName = adminDeptName;
			this.adminPassword = adminPassword;
			this.adminName = adminName;
			this.adminPhoneNumber = adminPhoneNumber;
	}

    // mypage 내정보 수정
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
    
    public String getAdminDeptName() {
    	return adminDeptName;
    }
    
    public void setAdminDeptName(String adminDeptName) {
    	this.adminDeptName = adminDeptName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber) {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    @Override
    public String toString() {
    	return "AdminDTO [adminId=" + adminId + ", adminDeptNo=" + adminDeptNo + ", adminDeptName=" + adminDeptName + ", adminPassword=" + adminPassword 
    			+ ", adminName=" + adminName + ", adminPhoneNumber=" + adminPhoneNumber + "]";
    }
}