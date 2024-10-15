package himedia.project.careops.dto;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SafetyManagementDTO {
	
	private Integer smNo;
	private int smFacilityNo;
	private String smAdminId;
	private String smAdminDeptNo;
	private String smAdminDeptName;
	private String smAdminName;
	private String smList;
	private int smFacilityFloor;
	private String smFacilityName;
	private boolean smStatus;
	private Date smDate;
	
	public SafetyManagementDTO() {}
	
	public SafetyManagementDTO(Integer smNo, int smFacilityNo, String smAdminId, String smAdminDeptNo,
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
		return "SafetyManagementDTO [ smNo= " + smNo + ", smFacilityNo= " + smFacilityNo 
				+ ", smAdminId= " + smAdminId + ", smAdminName=" + smAdminName + ", smAdminDeptNo=" 
				+ smAdminDeptNo + ", smAdminDeptName=" + smAdminDeptName + ", smList=" + smList + ", smFacilityFloor=" 
				+ smFacilityFloor + ", smFacilityName=" + smFacilityName + ", smStatus=" + smStatus + ", smDate=" + smDate + "]";
	}
}
