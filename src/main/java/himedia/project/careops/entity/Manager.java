package himedia.project.careops.entity;

/**
 * @author 노태윤 -> 진혜정
 * @editDate 2024-09-25
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "manager")
@ToString
public class Manager {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private String managerId;

    @ManyToOne
    @JoinColumn(name="manager_dept_no", nullable = false)
    private ManagerDepartment managerDeptNo;

    @Column(name = "manager_dept_part")
    private String managerDeptPart;

    @Column(name = "manager_dept_name")
    private String managerDeptName;

    @Column(name = "manager_password")
    private String managerPassword;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "manager_phone_number")
    private String managerPhoneNumber;

    public Manager() {}

    public Manager(String managerId, ManagerDepartment managerDeptNo, String managerDeptPart,
                         String managerDeptName, String managerPassword, String managerName,
                         String managerPhoneNumber) {
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
	    return "Manager{" +
	            "managerId='" + managerId + '\'' +
	            ", managerDeptNo=" + managerDeptNo +
	            ", managerDeptPart='" + managerDeptPart + '\'' +
	            ", managerDeptName='" + managerDeptName + '\'' +
	            ", managerPassword='" + managerPassword + '\'' +
	            ", managerName='" + managerName + '\'' +
	            ", managerPhoneNumber='" + managerPhoneNumber + '\'' +
	            '}';
	}

}
