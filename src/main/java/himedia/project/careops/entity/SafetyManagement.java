package himedia.project.careops.entity;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

import java.sql.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

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
	private int smNo;				// 점검 번호
	
	@Column(name="admin_id", nullable = false)
	private String adminId;			// 관리자 아이디
	
	@Column(name="admin_name", nullable = false)
	private String adminName;		// 관리자 이름
	
	@Column(name="sm_check", nullable = false)
	@ColumnDefault("0")
	private Boolean smCheck;		// 확인
	
	@Column(name="sm_date", nullable = false)
	private Date smDate;			// 등록날짜
	
	public SafetyManagement() {}
	
	public SafetyManagement(int smNo, String adminId, String adminName, Boolean smCheck, Date smDate) {
		this.smNo = smNo;
		this.adminId = adminId;
		this.adminName = adminName;
		this.smCheck = smCheck;
		this.smDate = smDate;
	}
	public int getSmNo() {
		return smNo;
	}
	public void setSmNo(int smNo) {
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
		return "SafetyManagement [ smNo=" + smNo + ", adminId=" + adminId 
				+ ", adminName=" + adminName + ", smCheck=" + smCheck + ", smDate=" + smDate + "]";
	}
}
