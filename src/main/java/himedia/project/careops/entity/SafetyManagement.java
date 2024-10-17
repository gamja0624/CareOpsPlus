package himedia.project.careops.entity;

/**
 * @author 이홍준 
 * @editDate 2024-09-24 ~ 2024-10-15
 */

import java.sql.Date;
/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "safety_management")
@ToString
public class SafetyManagement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sm_no", nullable = false)
	private Integer smNo;
	
	@Column(name="sm_facility_no")
	private int smFacilityNo;
	
	@Column(name="sm_admin_id", nullable = true)
	private String smAdminId;

	@Column(name="sm_admin_dept_no", nullable = true)
	private String smAdminDeptNo;
	
	@Column(name="sm_admin_dept_name", nullable = true)
	private String smAdminDeptName;
	
	@Column(name="sm_admin_name", nullable = true)
	private String smAdminName;
	
	@Column(name="sm_list")
	private String smList;
	
	@Column(name="sm_facility_floor")
	private int smFacilityFloor;
	
	@Column(name="sm_facility_name")
	private String smFacilityName;
	
	@Column(name="sm_status")
	private boolean smStatus;
	
	@Column(name="sm_date", nullable = true)
	private Date smDate;
	
	
	public SafetyManagement() {}

	public SafetyManagement(Integer smNo, int smFacilityNo, String smAdminId, String smAdminDeptNo,
			String smAdminDeptName, String smAdminName, String smList, int smFacilityFloor, String smFacilityName,
			boolean smStatus, Date smDate) {
		this.smNo = smNo;
		this.smFacilityNo = smFacilityNo;
		this.smAdminId = smAdminId;
		this.smAdminDeptNo = smAdminDeptNo;
		this.smAdminDeptName = smAdminDeptName;
		this.smAdminName = smAdminName;
		this.smList = smList;
		this.smFacilityFloor = smFacilityFloor;
		this.smFacilityName = smFacilityName;
		this.smStatus = smStatus;
		this.smDate = smDate;
	}

	public Integer getSmNo() {
		return smNo;
	}

	public void setSmNo(Integer smNo) {
		this.smNo = smNo;
	}

	public int getSmFacilityNo() {
		return smFacilityNo;
	}

	public void setSmFacilityNo(int smFacilityNo) {
		this.smFacilityNo = smFacilityNo;
	}

	public String getSmAdminId() {
		return smAdminId;
	}

	public void setSmAdminId(String smAdminId) {
		this.smAdminId = smAdminId;
	}

	public String getSmAdminDeptNo() {
		return smAdminDeptNo;
	}

	public void setSmAdminDeptNo(String smAdminDeptNo) {
		this.smAdminDeptNo = smAdminDeptNo;
	}

	public String getSmAdminDeptName() {
		return smAdminDeptName;
	}

	public void setSmAdminDeptName(String smAdminDeptName) {
		this.smAdminDeptName = smAdminDeptName;
	}

	public String getSmAdminName() {
		return smAdminName;
	}

	public void setSmAdminName(String smAdminName) {
		this.smAdminName = smAdminName;
	}

	public String getSmList() {
		return smList;
	}

	public void setSmList(String smList) {
		this.smList = smList;
	}

	public int getSmFacilityFloor() {
		return smFacilityFloor;
	}

	public void setSmFacilityFloor(int smFacilityFloor) {
		this.smFacilityFloor = smFacilityFloor;
	}

	public String getSmFacilityName() {
		return smFacilityName;
	}

	public void setSmFacilityName(String smFacilityName) {
		this.smFacilityName = smFacilityName;
	}

	public boolean isSmStatus() {
		return smStatus;
	}

	public void setSmStatus(boolean smStatus) {
		this.smStatus = smStatus;
	}

	public Date getSmDate() {
		return smDate;
	}

	public void setSmDate(Date smDate) {
		this.smDate = smDate;
	}
	
	@Override
	public String toString() {
		return "SafetyManagement [ smNo= " + smNo + ", smFacilityNo= " + smFacilityNo 
				+ ", smAdminId= " + smAdminId + ", smAdminName=" + smAdminName + ", smAdminDeptNo=" 
				+ smAdminDeptNo + ", smAdminDeptName=" + smAdminDeptName + ", smList=" + smList + ", smFacilityFloor=" 
				+ smFacilityFloor + ", smFacilityName=" + smFacilityName + ", smStatus=" + smStatus + ", smDate=" + smDate + "]";
	}
	
}
