package himedia.project.careops.dto;

import java.sql.Date;

/**
 * @author 진혜정
 * @editDate 2024-10-02
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FacilityDTO {

	private Integer facilityNo; 				// 시설물 번호
	private int facilityFloor;					// 층별
	private String facilityName;				// 시설물 이름
	
	private Integer facilityReservationNo;		// 예약 번호
	private boolean facilityReservationStatus;	// 예약 상태
	private Date facilityReservationDate;		// 예약 날짜
	
	private Integer facilityManagerDeptNo;		// 담당자 부서 번호
	private String facilityManagerDeptName;		// 담당자 부서 이름
	private String facilityManagerId;			// 담당자 아이디
	private String facilityManagerName;			// 담당자 이름
	
	public FacilityDTO() {}
	
	public FacilityDTO(int facilityFloor, String facilityName, boolean facilityReservationStatus) {
		this.facilityFloor = facilityFloor;
		this.facilityName = facilityName;
		this.facilityReservationStatus = facilityReservationStatus;
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

	public Integer getFacilityReservationNo() {
		return facilityReservationNo;
	}

	public void setFacilityReservationNo(Integer facilityReservationNo) {
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

	public Integer getFacilityManagerDeptNo() {
		return facilityManagerDeptNo;
	}

	public void setFacilityManagerDeptNo(Integer facilityManagerDeptNo) {
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
		return "FacilityDTO : [ " +
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
