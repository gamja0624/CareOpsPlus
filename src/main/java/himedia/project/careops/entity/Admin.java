package himedia.project.careops.entity;

/*@author 노태윤
@editDate 2024-09-23~2024-09-24*/

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name="admin_id")
    private String adminId;
    
    @Column(name="admin_dept_no")
    private String adminDeptNo;
    
    @Column(name="admin_dept_name")
    private String adminDeptName;
    
    @Column(name="admin_password")
    private String adminPassword;
    
    @Column(name="admin_name")
    private String adminName;
    
    @Column(name="admin_phone_number")
    private String adminPhoneNumber;
    
    public Admin() {}
    
    public Admin(String adminId, String adminDeptNo, String adminDeptName, String adminPassword, 
            String adminName, String adminPhoneNumber) {
    	this.adminId = adminId;
    	this.adminDeptNo = adminDeptNo;
    	this.adminDeptName = adminDeptName;
    	this.adminPassword = adminPassword;
    	this.adminName = adminName;
    	this.adminPhoneNumber = adminPhoneNumber;
    }

    // Getters and Setters
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
}
