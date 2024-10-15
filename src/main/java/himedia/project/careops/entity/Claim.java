package himedia.project.careops.entity;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import java.sql.Date;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "claim")
@ToString
public class Claim {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="claim_no", nullable = false)
	private Integer claimNo;					// 민원 번호 ( 고유키 )
	
	@Column(name="manager_id", nullable = false)
	private String managerId;					// 담당자 아이디 ( 외래키 )         				
	
	@Column(name="manager_dept_no", nullable = false)
	private int managerDeptNo;				    // 담당자 부서번호 ( 외래키 ) 
	
	@Column(name="claim_category_no", nullable = false)
	private String claimCategoryNo;				// 민원 대분류 번호 ( 외래키 )
	
	@Column(name="claim_sub_category_no", nullable = false)
	private int claimSubCategoryNo;     		// 민원 소뷴류 번호 ( 외래키 )	
	
	@Column(name="claim_category_name", nullable = false)
	private String claimCategoryName;			// 민원 대분류 이름 
	
	@Column(name="claim_sub_category_name")
	private String claimSubCategoryName;		// 민원 소분류 이름
	
	@Column(name="claim_cateogory_status")
	private String claimCategoryStatus;			// 민원 요청 구분 ( * 의료 기기 관리 ) 
	
	@Column(name="claim_manager_name", nullable = false)
	private String claimManagerName;			// 담당자 이름
	
	@Column(name="claim_date", nullable = false)
	private Date claimDate;						// 민원 신청일
	
	@Column(name="claim_title", nullable = false)
	private String claimTitle;					// 민원 제목
	
	@Column(name="claim_content", nullable = false)
	private String claimContent;				// 민원 내용
	
	@Column(name="claim_approve", nullable = false)
	private Boolean claimApprove;				// 민원 승인 여부
	
	@Column(name="claim_complete", nullable = false)
	private Boolean claimComplete;				// 민원 처리 여부
	
	@Column(name="claim_attachment")
	private String claimAttachment;				// 민원 첨부파일
   
	@Lob                                        // LONGBLOB을 나타내는 어노테이션
    @Column(name = "claim_image_data")          // 이진 데이터 컬럼 이름
    private byte[] claimImageData;              // 민원 이진 데이터 (LONGBLOB 추가)

	public Claim() {}

	public Claim(Integer claimNo, String managerId, int managerDeptNo, String claimCategoryNo,
			int claimSubCategoryNo, String claimCategoryName, String claimSubCategoryName,
			String claimCategoryStatus, String claimManagerName, Date claimDate, String claimTitle, String claimContent,
			Boolean claimApprove, Boolean claimComplete, String claimAttachment,  byte[] claimImageData) {
		super();
		this.claimNo = claimNo;
		this.managerId = managerId;
		this.managerDeptNo = managerDeptNo;
		this.claimCategoryNo = claimCategoryNo;
		this.claimSubCategoryNo = claimSubCategoryNo;
		this.claimCategoryName = claimCategoryName;
		this.claimSubCategoryName = claimSubCategoryName;
		this.claimCategoryStatus = claimCategoryStatus;
		this.claimManagerName = claimManagerName;
		this.claimDate = claimDate;
		this.claimTitle = claimTitle;
		this.claimContent = claimContent;
		this.claimApprove = claimApprove;
		this.claimComplete = claimComplete;
		this.claimAttachment = claimAttachment;
		this.claimImageData = claimImageData;
	}

	public Integer getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(Integer claimNo) {
		this.claimNo = claimNo;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public int getManagerDeptNo() {
		return managerDeptNo;
	}

	public void setManagerDeptNo(int managerDeptNo) {
		this.managerDeptNo = managerDeptNo;
	}

	public String getClaimCategoryNo() {
		return claimCategoryNo;
	}

	public void setClaimCategoryNo(String claimCategoryNo) {
		this.claimCategoryNo = claimCategoryNo;
	}

	public int getClaimSubCategoryNo() {
		return claimSubCategoryNo;
	}

	public void setClaimSubCategoryNo(int claimSubCategoryNo) {
		this.claimSubCategoryNo = claimSubCategoryNo;
	}

	public String getClaimCategoryName() {
		return claimCategoryName;
	}

	public void setClaimCategoryName(String claimCategoryName) {
		this.claimCategoryName = claimCategoryName;
	}

	public String getClaimSubCategoryName() {
		return claimSubCategoryName;
	}

	public void setClaimSubCategoryName(String claimSubCategoryName) {
		this.claimSubCategoryName = claimSubCategoryName;
	}

	public String getClaimCategoryStatus() {
		return claimCategoryStatus;
	}

	public void setClaimCategoryStatus(String claimCategoryStatus) {
		this.claimCategoryStatus = claimCategoryStatus;
	}

	public String getClaimManagerName() {
		return claimManagerName;
	}

	public void setClaimManagerName(String claimManagerName) {
		this.claimManagerName = claimManagerName;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getClaimTitle() {
		return claimTitle;
	}

	public void setClaimTitle(String claimTitle) {
		this.claimTitle = claimTitle;
	}

	public String getClaimContent() {
		return claimContent;
	}

	public void setClaimContent(String claimContent) {
		this.claimContent = claimContent;
	}

	public Boolean getClaimApprove() {
		return claimApprove;
	}

	public void setClaimApprove(Boolean claimApprove) {
		this.claimApprove = claimApprove;
	}

	public Boolean getClaimComplete() {
		return claimComplete;
	}

	public void setClaimComplete(Boolean claimComplete) {
		this.claimComplete = claimComplete;
	}

	public String getClaimAttachment() {
		return claimAttachment;
	}

	public void setClaimAttachment(String claimAttachment) {
		this.claimAttachment = claimAttachment;
	}

	public byte[] getClaimImageData() {
		return claimImageData;
	}

	public void setClaimImageData(byte[] claimImageData) {
		this.claimImageData = claimImageData;
	}

	@Override
	public String toString() {
		return "Claim [claimNo=" + claimNo + ", managerId=" + managerId + ", managerDeptNo=" + managerDeptNo
				+ ", claimCategoryNo=" + claimCategoryNo + ", claimSubCategoryNo=" + claimSubCategoryNo
				+ ", claimCategoryName=" + claimCategoryName + ", claimSubCategoryName=" + claimSubCategoryName
				+ ", claimCategoryStatus=" + claimCategoryStatus + ", claimManagerName=" + claimManagerName
				+ ", claimDate=" + claimDate + ", claimTitle=" + claimTitle + ", claimContent=" + claimContent
				+ ", claimApprove=" + claimApprove + ", claimComplete=" + claimComplete + ", claimAttachment="
				+ claimAttachment + ", claimImageData=" + Arrays.toString(claimImageData) + "]";
	}

}
