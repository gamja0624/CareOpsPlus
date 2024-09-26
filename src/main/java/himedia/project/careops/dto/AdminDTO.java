package himedia.project.careops.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminDTO {

    private String adminId;					// 관리자 아이디
    private String adminDeptNo;	            // 관리자 부서 번호
    private String adminDeptName;			// 관리자 부서 이름
    private String adminPassword; 			// 관리자 비밀번
    private String adminName;				// 관리자 이름
    private String adminPhoneNumber;		// 관리자 연락처

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