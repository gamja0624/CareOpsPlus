package himedia.project.careops.dto;

/**
 * @author 이홍준 
 * @editDate 2024-09-24 ~ 2024-10-15
 */

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SafetyManagementDTO {
	
	private Integer smNo;				// 점검 번호
	private int smFacilityNo;			// 시설 번호
	private String smAdminId;			// 관리자 아이디
	private String smAdminDeptNo;		// 관리자 부서번호
	private String smAdminDeptName;		// 관리자 부서 이름
	private String smAdminName;			// 관리자 이름
	private String smList;				// 점검 항목
	private int smFacilityFloor;		// 시설물 층
	private String smFacilityName;		// 시설물 이름
	private boolean smStatus;			// 관리 상태
	private Date smDate;				// 수정 날짜
	
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
