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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "list_medical_devices")
public class ListMedicalDevices {
	
	@Id
	@Column(name="lmd_minor_cate_code", nullable = false) // 장비세분류코드 - 1
	private String lmdMinorCateCode;
	
	@Column(name="lmd_minor_cate_name", nullable = false) // 장비세분류명
	private String lmdMinorCateName;
	
	@Column(name="lmd_major_cate_code", nullable = false) // 장비대문류코드
	private String lmdMajorCateCode;
	
	@Column(name="lmd_major_cate_name", nullable = false) // 장비대분류명
	private String lmdMajorCateName;
	
	@Column(name="lmd_devices_name", nullable = false) // 모델명 - 2
	private String lmdDevicesName; 
	
	@Column(name="lmd_license_number", nullable = false) // 장비허가번호
	private String lmdLicenseNumber;
	
	@Column(name="lmd_device_cnt", nullable = false) // 장비수
	private int lmdDeviceCnt;
	
	@Column(name="lmd_status", nullable = false) // 상태
	private String lmdStatus;
	
	@ManyToOne
	@JoinColumn(name="lmd_manager_dept_no", nullable = false) // 담당자 부서 번호 - 3
	private Manager lmdManagerDeptNo;
	
	@Column(name="lmd_manager_dept_part", nullable = false) // 담당 부서 이름
	private String lmdManagerDeptPart;
	
	@ManyToOne
	@JoinColumn(name="lmd_manager_id", nullable = false) // 담당자 아이디
	private Manager lmdManagerId;
	
	@Column(name="lmd_manager_name", nullable = false) // 담당자 이름
	private String lmdManagerName;
	
	@Column(name="lmd_date", nullable = false) // 등록일 
	private Date lmdDate;
	
	@ManyToOne
	@JoinColumn(name="lmd_admin_dept_no", referencedColumnName = "admin_dept_no") // 관리자 부서 번호 - 4
	private Admin lmdAdminDeptNo;
	
	@ManyToOne
	@JoinColumn(name="lmd_admin_id", referencedColumnName = "admin_id") // 관리자 아이디
	private Admin lmdAdminId;
	
	@Column(name="lmd_admin_name") // 관리자 이름
	private String lmdAdminName;
	
	@Column(name="lmd_last_check_date") // 마지막점검일
	private Date lmdLastCheckDate;
	
	@Column(name="lmd_claim_no") // 민원 번호
	private Integer lmdClaimNo;
	
	public ListMedicalDevices() {}
	
	public ListMedicalDevices(
	        String lmdMinorCateCode, String lmdMinorCateName, String lmdMajorCateCode, String lmdMajorCateName,
	        String lmdDevicesName, String lmdLicenseNumber, int lmdDeviceCnt, String lmdStatus,
	        Manager lmdManagerDeptNo, String lmdManagerDeptPart, Manager lmdManagerId, String lmdManagerName ) {
	        //Date lmdDate, Admin lmdAdminDeptNo, Admin lmdAdminId, String lmdAdminName, Date lmdLastCheckDate, Integer lmdClaimNo
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
}
