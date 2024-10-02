package himedia.project.careops.entity;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 * 민원 엔티티 생성 후 수정 예정
 */

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "list_medical_devices")
@ToString
public class ListMedicalDevices {

	@Id
	@Column(name = "lmd_minor_cate_code", nullable = false) // 장비세분류코드 - 1
	private String lmdMinorCateCode;

	@Column(name = "lmd_minor_cate_name", nullable = false) // 장비세분류명
	private String lmdMinorCateName;

	@Column(name = "lmd_major_cate_code", nullable = false) // 장비대문류코드
	private String lmdMajorCateCode;

	@Column(name = "lmd_major_cate_name", nullable = false) // 장비대분류명
	private String lmdMajorCateName;

	@Column(name = "lmd_devices_name", nullable = false) // 모델명 - 2
	private String lmdDevicesName;

	@Column(name = "lmd_license_number", nullable = false) // 장비허가번호
	private String lmdLicenseNumber;

	@Column(name = "lmd_device_cnt", nullable = false) // 장비수
	private int lmdDeviceCnt;

	@Column(name = "lmd_status", nullable = false) // 상태
	private String lmdStatus;

	@Column(name = "lmd_manager_dept_no", nullable = false) // 담당자 부서 번호 - 3
	private int lmdManagerDeptNo;

	@Column(name = "lmd_manager_dept_part", nullable = false) // 담당 부서 이름
	private String lmdManagerDeptPart;

	@Column(name = "lmd_manager_id", nullable = false) // 담당자 아이디
	private String lmdManagerId;

	@Column(name = "lmd_manager_name", nullable = false) // 담당자 이름
	private String lmdManagerName;

	@Column(name = "lmd_date", nullable = false) // 등록일
	private Date lmdDate;

	@Column(name = "lmd_admin_dept_no") // 관리자 부서 번호 - 4
	private String lmdAdminDeptNo;

	@Column(name = "lmd_admin_id") // 관리자 아이디
	private String lmdAdminId;

	@Column(name = "lmd_admin_name") // 관리자 이름
	private String lmdAdminName;

	@Column(name = "lmd_last_check_date") // 마지막점검일
	private Date lmdLastCheckDate;

	@Column(name = "lmd_claim_no") // 민원 번호
	private Integer lmdClaimNo;

	public ListMedicalDevices() {
	}

	public ListMedicalDevices(String lmdMinorCateCode, String lmdMinorCateName, String lmdMajorCateCode,
			String lmdMajorCateName, String lmdDevicesName, String lmdLicenseNumber, int lmdDeviceCnt, String lmdStatus,
			int lmdManagerDeptNo, String lmdManagerDeptPart, String lmdManagerId, String lmdManagerName) {
		super();
		this.lmdMinorCateCode = lmdMinorCateCode;
		this.lmdMinorCateName = lmdMinorCateName;
		this.lmdMajorCateCode = lmdMajorCateCode;
		this.lmdMajorCateName = lmdMajorCateName;

		this.lmdDevicesName = lmdDevicesName;
		this.lmdLicenseNumber = lmdLicenseNumber;
		this.lmdDeviceCnt = lmdDeviceCnt;
		this.lmdStatus = lmdStatus;

		this.lmdManagerDeptNo = lmdManagerDeptNo;
		this.lmdManagerDeptPart = lmdManagerDeptPart;
		this.lmdManagerId = lmdManagerId;
		this.lmdManagerName = lmdManagerName;
	}
	
	public String getLmdMinorCateCode() {
		return lmdMinorCateCode;
	}

	public void setLmdMinorCateCode(String lmdMinorCateCode) {
		this.lmdMinorCateCode = lmdMinorCateCode;
	}

	public String getLmdMinorCateName() {
		return lmdMinorCateName;
	}

	public void setLmdMinorCateName(String lmdMinorCateName) {
		this.lmdMinorCateName = lmdMinorCateName;
	}

	public String getLmdMajorCateCode() {
		return lmdMajorCateCode;
	}

	public void setLmdMajorCateCode(String lmdMajorCateCode) {
		this.lmdMajorCateCode = lmdMajorCateCode;
	}

	public String getLmdMajorCateName() {
		return lmdMajorCateName;
	}

	public void setLmdMajorCateName(String lmdMajorCateName) {
		this.lmdMajorCateName = lmdMajorCateName;
	}

	public String getLmdDevicesName() {
		return lmdDevicesName;
	}

	public void setLmdDevicesName(String lmdDevicesName) {
		this.lmdDevicesName = lmdDevicesName;
	}

	public String getLmdLicenseNumber() {
		return lmdLicenseNumber;
	}

	public void setLmdLicenseNumber(String lmdLicenseNumber) {
		this.lmdLicenseNumber = lmdLicenseNumber;
	}

	public int getLmdDeviceCnt() {
		return lmdDeviceCnt;
	}

	public void setLmdDeviceCnt(int lmdDeviceCnt) {
		this.lmdDeviceCnt = lmdDeviceCnt;
	}

	public String getLmdStatus() {
		return lmdStatus;
	}

	public void setLmdStatus(String lmdStatus) {
		this.lmdStatus = lmdStatus;
	}

	public int getLmdManagerDeptNo() {
		return lmdManagerDeptNo;
	}

	public void setLmdManagerDeptNo(int lmdManagerDeptNo) {
		this.lmdManagerDeptNo = lmdManagerDeptNo;
	}

	public String getLmdManagerDeptPart() {
		return lmdManagerDeptPart;
	}

	public void setLmdManagerDeptPart(String lmdManagerDeptPart) {
		this.lmdManagerDeptPart = lmdManagerDeptPart;
	}

	public String getLmdManagerId() {
		return lmdManagerId;
	}

	public void setLmdManagerId(String lmdManagerId) {
		this.lmdManagerId = lmdManagerId;
	}

	public String getLmdManagerName() {
		return lmdManagerName;
	}

	public void setLmdManagerName(String lmdManagerName) {
		this.lmdManagerName = lmdManagerName;
	}

	public Date getLmdDate() {
		return lmdDate;
	}

	public void setLmdDate(Date lmdDate) {
		this.lmdDate = lmdDate;
	}

	public String getLmdAdminDeptNo() {
		return lmdAdminDeptNo;
	}

	public void setLmdAdminDeptNo(String lmdAdminDeptNo) {
		this.lmdAdminDeptNo = lmdAdminDeptNo;
	}

	public String getLmdAdminId() {
		return lmdAdminId;
	}

	public void setLmdAdminId(String lmdAdminId) {
		this.lmdAdminId = lmdAdminId;
	}

	public String getLmdAdminName() {
		return lmdAdminName;
	}

	public void setLmdAdminName(String lmdAdminName) {
		this.lmdAdminName = lmdAdminName;
	}

	public Date getLmdLastCheckDate() {
		return lmdLastCheckDate;
	}

	public void setLmdLastCheckDate(Date lmdLastCheckDate) {
		this.lmdLastCheckDate = lmdLastCheckDate;
	}

	public Integer getLmdClaimNo() {
		return lmdClaimNo;
	}

	public void setLmdClaimNo(Integer lmdClaimNo) {
		this.lmdClaimNo = lmdClaimNo;
	}

	@Override
	public String toString() {
		return "ListMedicalDevices : [ " +
		// 장비세분류코드, 장비세분류명, 장비대문류코드, 장비대분류명
		"lmdMinorCateCode= " + lmdMinorCateCode + ", " + "lmdMinorCateName= " + lmdMinorCateName + ", "
		+ "lmdMajorCateCode= " + lmdMajorCateCode + ", " + "lmdDevicesName= " + lmdDevicesName + ", \n" +

		// 모델명, 장비허가번호, 장비수, 상태
		"lmdDevicesName= " + lmdDevicesName + ", " + "lmdLicenseNumber= " + lmdLicenseNumber + ", "
		+ "lmdDeviceCnt= " + lmdDeviceCnt + ", " + "lmdStatus= " + lmdStatus + ", \n" +

		// 담당자 부서 번호, 담당 부서 이름, 담당자 아이디, 등록일
		"lmdManagerDeptNo= " + lmdManagerDeptNo + ", " + "lmdManagerDeptPart= " + lmdManagerDeptPart + ", "
		+ "lmdManagerId= " + lmdManagerId + ", " + "lmdManagerName= " + lmdManagerName + ", " + "lmdDate= "
		+ lmdDate + ", \n" +

		// 관리자 부서 번호, 관리자 아이디, 관리자 이름, 마지막점검일, 민원 번호
		"lmdAdminDeptNo= " + lmdAdminDeptNo + "," + "lmdAdminId= " + lmdAdminId + "," + "lmdAdminName= "
		+ lmdAdminName + ", " + "lmdLastCheckDate= " + lmdLastCheckDate + ", " + "lmdClaimNo= " + lmdClaimNo
		+ " ]";
	}

}
