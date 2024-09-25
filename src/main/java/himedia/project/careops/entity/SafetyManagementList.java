package himedia.project.careops.entity;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "safety_management_list")
public class SafetyManagementList {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sml_no", nullable = false)
	private int smlNo;				// 세부항목 번호
	
	@ManyToOne
	@JoinColumn(name="sm_no", nullable = false)
	private SafetyManagement smNo;	// 점검 번호
	
	@Column(name="sm_list", nullable = false)
	private String smlList;			// 세부항목
	
	@Column(name="sml_check", nullable = false)
	@ColumnDefault("0")
	private Boolean smlCheck;		// 세부항목 체크
	
	public SafetyManagementList() {}

	public SafetyManagementList(int smlNo, SafetyManagement smNo, String smlList, Boolean smlCheck) {
		this.smlNo = smlNo;
		this.smNo = smNo;
		this.smlList = smlList;
		this.smlCheck = smlCheck;
	}

	public int getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(int smlNo) {
		this.smlNo = smlNo;
	}

	public SafetyManagement getSmNo() {
		return smNo;
	}

	public void setSmNo(SafetyManagement smNo) {
		this.smNo = smNo;
	}

	public String getSmlList() {
		return smlList;
	}

	public void setSmlList(String smlList) {
		this.smlList = smlList;
	}

	public Boolean getSmlCheck() {
		return smlCheck;
	}

	public void setSmlCheck(Boolean smlCheck) {
		this.smlCheck = smlCheck;
	}
	
	@Override
	public String toString() {
		return "SafetyManagementListDTO [ smlNo=" + smlNo + "smNo=" + smNo 
				+ "smList=" + smlList + "smlCheck=" + smlCheck + "]";
	}
}
