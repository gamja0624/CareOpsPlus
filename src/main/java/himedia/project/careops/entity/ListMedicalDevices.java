package himedia.project.careops.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

@Entity
@Table(name = "list_medical_devices")
public class ListMedicalDevices {
	
	@Id
	@Column(name="lmd_minor_cate_code")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String lmdMinorCateCode;
	
	@Column(name="lmd_minor_cate_name")
	private String lmdMinorCateName;
	
	@Column(name="lmd_major_cate_code")
	private String lmdMajorCateCode;
	
	@Column(name="lmd_major_cate_name")
	private String lmdMajorCateName;
	
	@Column(name="lmd_devices_name")
	private String lmdDevicesName;
	
	@Column(name="lmd_license_number")
	private String lmdLicenseNumber;
	
	@Column(name="lmd_device_cnt")
	private int lmdDeviceCnt;
	
	@Column(name="lmd_status")
	private String lmdStatus;
	
	@Column(name="lmd_manager_dept_no")
	private int lmdManagerDeptNo;
	
	@Column(name="lmd_manager_dept_part")
	private String lmdManagerDeptPart;
	
	@Column(name="lmd_manager_id")
	private String lmdManagerId;
	
	@Column(name="lmd_manager_name")
	private String lmdManagerName;
	
	@Column(name="lmd_date")
	private Date lmdDate;
	
	@Column(name="lmd_admin_dept_no")
	private String lmdAdminDeptNo;
	
	@Column(name="lmd_admin_id")
	private String lmdAdminId;
	
	@Column(name="lmd_amdin_name")
	private String lmdAdminName;
	
	@Column(name="lmd_last_check_date")
	private Date lmdLastCheckDate;
	
	@Column(name="lmd_claim_no")
	private int lmdDlaimNo;
	
	public ListMedicalDevices() {}
	
	public ListMedicalDevices(
			String lmdMinorCateCode, String lmdMinorCateName, String lmdMajorCateCode, String lmdMajorCateName,
			String lmdDevicesName, String lmdLicenseNumber, int lmdDeviceCnt, String lmdStatus,
			int lmdManagerDeptNo, String lmdManagerDeptPart, String lmdManagerId, String lmdManagerName, Date lmdDate) {
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
		this.lmdDate = lmdDate;
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

	public int getLmdDlaimNo() {
		return lmdDlaimNo;
	}

	public void setLmdDlaimNo(int lmdDlaimNo) {
		this.lmdDlaimNo = lmdDlaimNo;
	}
	
}
