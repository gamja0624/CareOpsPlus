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
	private String adminId;
	private String adminName;
	private Boolean smCheck;
	private Date smDate;
	
	public SafetyManagementDTO() {}
	
	public SafetyManagementDTO(Integer smNo, String adminId, String adminName, Boolean smCheck, Date smDate) {
		this.smNo = smNo;
		this.adminId = adminId;
		this.adminName = adminName;
		this.smCheck = smCheck;
		this.smDate = smDate;
	}

	public Integer getSmNo() {
		return smNo;
	}

	public void setSmNo(Integer smNo) {
		this.smNo = smNo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Boolean getSmCheck() {
		return smCheck;
	}

	public void setSmCheck(Boolean smCheck) {
		this.smCheck = smCheck;
	}

	public Date getSmDate() {
		return smDate;
	}

	public void setSmDate(Date smDate) {
		this.smDate = smDate;
	}

	@Override
	public String toString() {
		return "SafetyManagementDTO [ smNo=" + smNo + ", adminId=" + adminId 
				+ ", adminName=" + adminName + ", smCheck=" + smCheck + ", smDate=" + smDate + "]";
	}
}
