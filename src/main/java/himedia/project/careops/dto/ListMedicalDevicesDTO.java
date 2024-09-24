package himedia.project.careops.dto;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

import java.sql.Date;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListMedicalDevicesDTO {
	
	private String lmdMinorCateCode;   // 장비세분류코드
	private String lmdMinorCateName;   // 장비세분류명
	private String lmdMajorCateCode;   // 장비대문류코드
	private String lmdMajorCateName;   // 장비대분류명
	
	private String lmdDevicesName;     // 모델명
	private String lmdLicenseNumber;   // 장비허가번호
	private int lmdDeviceCnt;          // 장비수
	private String lmdStatus;          // 상태
	
	private int lmdManagerDeptNo;      // 담당자 부서 번호
	private String lmdManagerDeptPart; // 담당 부서 이름
	private String lmdManagerId;       // 담당자 아이디
	private String lmdManagerName;     // 담당자 이름
	private Date lmdDate;              // 등록일
	
	private String lmdAdminDeptNo;     // 관리자 부서 번호
	private String lmdAdminId;         // 관리자 아이디
	private String lmdAdminName;       // 관리자 이름
	private Date lmdLastCheckDate;     // 마지막점검일
	private Integer lmdClaimNo;        // 민원 번호
	
	public ListMedicalDevicesDTO() {}
	
	public ListMedicalDevicesDTO(
			String lmdMinorCateCode, String lmdMinorCateName, String lmdMajorCateCode, String lmdMajorCateName,
			String lmdDevicesName, String lmdLicenseNumber, int lmdDeviceCnt, String lmdStatus,
			int lmdManagerDeptNo, String lmdManagerDeptPart, String lmdManagerId, String lmdManagerName, Date lmdDate, 
			String lmdAdminDeptNo, String lmdAdminId, String lmdAdminName, Date lmdLastCheckDate, Integer lmdClaimNo) {
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
		
		this.lmdAdminDeptNo = lmdAdminDeptNo;
		this.lmdAdminId = lmdAdminId;
		this.lmdAdminName = lmdAdminName;
		this.lmdLastCheckDate = lmdLastCheckDate;
		this.lmdClaimNo = lmdClaimNo;
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

}
