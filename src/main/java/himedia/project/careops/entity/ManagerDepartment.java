package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-25
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="manager_department")
public class ManagerDepartment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "manager_dept_no", nullable = false)
	private int managerDeptNo;
	
	@Column(name = "manager_dept_part",  nullable = false)
	private String managerDeptPart;
	
	@Column(name = "manager_dept_name",  nullable = false)
	private String managerDeptName;

	public ManagerDepartment() {}
	
	public ManagerDepartment(int managerDeptNo, String managerDeptPart, String managerDeptName) {
		this.managerDeptNo = managerDeptNo;
		this.managerDeptPart = managerDeptPart;
		this.managerDeptName = managerDeptName;
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

	public String getManagerDeptName() {
		return managerDeptName;
	}

	public void setManagerDeptName(String managerDeptName) {
		this.managerDeptName = managerDeptName;
	}
	
	@Override
	public String toString() {
		return "ManagerDepartment [managerDeptNo=" + managerDeptNo + ", managerDeptPart=" + managerDeptPart
				+ ", managerDeptName=" + managerDeptName + ", getManagerDeptNo()=" + getManagerDeptNo()
				+ ", getManagerDeptPart()=" + getManagerDeptPart() + ", getManagerDeptName()=" + getManagerDeptName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
