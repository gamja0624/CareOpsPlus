package himedia.project.careops.dto;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

import java.sql.Date;

import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListMedicalDevicesDTO {
	
	private String lmdMinorCateCode;    // 장비세분류코드
	private String lmdMinorCateName;    // 장비세분류명
	private String lmdMajorCateCode;    // 장비대문류코드
	private String lmdMajorCateName;    // 장비대분류명
	
	private String lmdDevicesName;      // 모델명
	private String lmdLicenseNumber;    // 장비허가번호
	private int lmdDeviceCnt;           // 장비수
	private String lmdStatus;           // 상태
	
	private Manager lmdManagerDeptNo;   // 담당자 부서 번호
	private Manager lmdManagerDeptPart; // 담당 부서 이름
	private Manager lmdManagerId;       // 담당자 아이디
	private Manager lmdManagerName;     // 담당자 이름
	private Date lmdDate;               // 등록일
	
	private Admin lmdAdminDeptNo;       // 관리자 부서 번호
	private Admin lmdAdminId;           // 관리자 아이디
	private Admin lmdAdminName;         // 관리자 이름
	private Date lmdLastCheckDate;      // 마지막점검일
	private Integer lmdClaimNo;         // 민원 번호
	
	public ListMedicalDevicesDTO() {}
	
	public ListMedicalDevicesDTO(
			String lmdMinorCateCode, String lmdMinorCateName, String lmdMajorCateCode, String lmdMajorCateName,
			String lmdDevicesName, String lmdLicenseNumber, int lmdDeviceCnt, String lmdStatus,
			Manager lmdManagerDeptNo, Manager lmdManagerDeptPart, Manager lmdManagerId, Manager lmdManagerName, Date lmdDate) {
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
		
//		this.lmdAdminDeptNo = lmdAdminDeptNo;
//		this.lmdAdminId = lmdAdminId;
//		this.lmdAdminName = lmdAdminName;
//		this.lmdLastCheckDate = lmdLastCheckDate;
//		this.lmdClaimNo = lmdClaimNo;
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

	public Manager getLmdManagerDeptNo() {
		return lmdManagerDeptNo;
	}

	public void setLmdManagerDeptNo(Manager lmdManagerDeptNo) {
		this.lmdManagerDeptNo = lmdManagerDeptNo;
	}

	public Manager getLmdManagerDeptPart() {
		return lmdManagerDeptPart;
	}

	public void setLmdManagerDeptPart(Manager lmdManagerDeptPart) {
		this.lmdManagerDeptPart = lmdManagerDeptPart;
	}

	public Manager getLmdManageriD() {
		return lmdManagerId;
	}

	public void setLmdManagerID(Manager lmdManagerId) {
		this.lmdManagerId = lmdManagerId;
	}

	public Manager getLmdManagerName() {
		return lmdManagerName;
	}

	public void setLmdManagerName(Manager lmdManagerName) {
		this.lmdManagerName = lmdManagerName;
	}

	public Date getLmdDate() {
		return lmdDate;
	}

	public void setLmdDate(Date lmdDate) {
		this.lmdDate = lmdDate;
	}

	public Admin getLmdAdminDeptNo() {
		return lmdAdminDeptNo;
	}

	public void setLmdAdminDeptNo(Admin lmdAdminDeptNo) {
		this.lmdAdminDeptNo = lmdAdminDeptNo;
	}

	public Admin getLmdAdminId() {
		return lmdAdminId;
	}

	public void setLmdAdminId(Admin lmdAdminId) {
		this.lmdAdminId = lmdAdminId;
	}

	public Admin getLmdAdminName() {
		return lmdAdminName;
	}

	public void setLmdAdminName(Admin lmdAdminName) {
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

}