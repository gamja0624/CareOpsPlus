package himedia.project.careops.entity;

/**
 * @author 진혜정
 * @editDate 2024-10-02
 */

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "facility")
@ToString
public class Facility {

	@Id
	@Column(name = "facility_no", nullable = false)	// 시설물 번호
	private Integer facilityNo; 
	
	@Column(name = "facility_floor", nullable = false) // 층별
	private int facilityFloor;
	
	@Column(name = "facility_name", nullable = false) // 시설물 이름
	private String facilityName;
	
	@Column(name = "facility_reservation_no") // 예약 번호
	private int facilityReservationNo;
	
	@Column(name = "facility_reservation_status", nullable = false) // 예약 상태
	private boolean facilityReservationStatus;
	
	@Column(name = "facility_reservation_date") // 예약 날짜
	private Date facilityReservationDate;
	
	@Column(name = "facility_manager_dept_no") // 담당자 부서 번호
	private int facilityManagerDeptNo;
	
	@Column(name = "facility_manager_dept_name") // 담당자 부서 이름
	private String facilityManagerDeptName;
	
	@Column(name = "facility_manager_id") // 담당자 아이디
	private String facilityManagerId;
	
	@Column(name = "facility_manager_name") // 담당자 이름
	private String facilityManagerName;
	
	public Facility() {}
	
	public Facility(int facilityFloor, String facilityName, int facilityReservationNo, boolean facilityReservationStatus, Date facilityReservationDate,
			int facilityManagerDeptNo, String facilityManagerDeptName, String facilityManagerId, String facilityManagerName) {
		this.facilityFloor = facilityFloor;
		this.facilityName = facilityName;
		
		this.facilityReservationNo = facilityReservationNo;
		this.facilityReservationStatus = facilityReservationStatus;
		this.facilityReservationDate = facilityReservationDate;
		
		this.facilityManagerDeptNo = facilityManagerDeptNo;
		this.facilityManagerDeptName = facilityManagerDeptName;
		this.facilityManagerId = facilityManagerId;
		this.facilityManagerName = facilityManagerName;
	}
	
	public Integer getFacilityNo() {
		return facilityNo;
	}

	public void setFacilityNo(Integer facilityNo) {
		this.facilityNo = facilityNo;
	}

	public int getFacilityFloor() {
		return facilityFloor;
	}

	public void setFacilityFloor(int facilityFloor) {
		this.facilityFloor = facilityFloor;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public int getFacilityReservationNo() {
		return facilityReservationNo;
	}

	public void setFacilityReservationNo(int facilityReservationNo) {
		this.facilityReservationNo = facilityReservationNo;
	}

	public boolean isFacilityReservationStatus() {
		return facilityReservationStatus;
	}

	public void setFacilityReservationStatus(boolean facilityReservationStatus) {
		this.facilityReservationStatus = facilityReservationStatus;
	}

	public Date getFacilityReservationDate() {
		return facilityReservationDate;
	}

	public void setFacilityReservationDate(Date facilityReservationDate) {
		this.facilityReservationDate = facilityReservationDate;
	}

	public int getFacilityManagerDeptNo() {
		return facilityManagerDeptNo;
	}

	public void setFacilityManagerDeptNo(int facilityManagerDeptNo) {
		this.facilityManagerDeptNo = facilityManagerDeptNo;
	}

	public String getFacilityManagerDeptName() {
		return facilityManagerDeptName;
	}

	public void setFacilityManagerDeptName(String facilityManagerDeptName) {
		this.facilityManagerDeptName = facilityManagerDeptName;
	}

	public String getFacilityManagerId() {
		return facilityManagerId;
	}

	public void setFacilityManagerId(String facilityManagerId) {
		this.facilityManagerId = facilityManagerId;
	}

	public String getFacilityManagerName() {
		return facilityManagerName;
	}

	public void setFacilityManagerName(String facilityManagerName) {
		this.facilityManagerName = facilityManagerName;
	}

	@Override
	public String toString() {
		return "Facility : [ " +
		// 시설물 번호, 층별, 이름
		"facilityNo= " + facilityNo + ", " + "facilityFloor= " + facilityFloor + ", "
		+ "facilityName= " + facilityName + ", \n" +

		// 예약 번호, 상태, 날짜
		"facilityReservationNo= " + facilityReservationNo + ", " + "facilityReservationStatus= " + facilityReservationStatus + ", "
		+ "facilityReservationDate= " + facilityReservationDate + ", \n" +

		// 담당자 부서 번호, 부서 이름, 담당자 아이디, 이름
		"facilityManagerDeptNo= " + facilityManagerDeptNo + ", " + "facilityManagerDeptName= " + facilityManagerDeptName + ", "
		+ "facilityManagerId= " + facilityManagerId + ", " + "facilityManagerName= " + facilityManagerName + " ]";
	}
	
}
