package himedia.project.careops.entity;

/**
 * @author 이홍준 
 * @editDate 2024-09-24
 */

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
@Table(name = "safety_management_checklist")
public class SafetyManagementChecklist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="smc_no", nullable = false)
	private int smcNo;
	
	@ManyToOne
	@JoinColumn(name="sml_no", nullable = false)
	private SafetyManagementList smlNo;
	
	@Column(name="sml_list", nullable = false)
	private String smlList;
	
	@Column(name="smc_list", nullable = false)
	private String smcList;
	
	@Column(name="smc_check", nullable = false)
	@ColumnDefault("0")
	private Boolean smcCheck;
	
	@Column(name="smc_issue", nullable = true)
	private String smcIssue;

	public SafetyManagementChecklist() {}
	
	public SafetyManagementChecklist(int smcNo, SafetyManagementList smlNo, String smlList, String smcList,
			Boolean smcCheck, String smcIssue) {
		super();
		this.smcNo = smcNo;
		this.smlNo = smlNo;
		this.smlList = smlList;
		this.smcList = smcList;
		this.smcCheck = smcCheck;
		this.smcIssue = smcIssue;
	}

	public int getSmcNo() {
		return smcNo;
	}

	public void setSmcNo(int smcNo) {
		this.smcNo = smcNo;
	}

	public SafetyManagementList getSmlNo() {
		return smlNo;
	}

	public void setSmlNo(SafetyManagementList smlNo) {
		this.smlNo = smlNo;
	}

	public String getSmlList() {
		return smlList;
	}

	public void setSmlList(String smlList) {
		this.smlList = smlList;
	}

	public String getSmcList() {
		return smcList;
	}

	public void setSmcList(String smcList) {
		this.smcList = smcList;
	}

	public Boolean getSmcCheck() {
		return smcCheck;
	}

	public void setSmcCheck(Boolean smcCheck) {
		this.smcCheck = smcCheck;
	}

	public String getSmcIssue() {
		return smcIssue;
	}

	public void setSmcIssue(String smcIssue) {
		this.smcIssue = smcIssue;
	}
	
	@Override
	public String toString() {
		return "SafetyManagementChecklistDTO [ smcNo=" + smcNo + "smlNo=" + smlNo + "smlList=" + smlList 
				+ "smcList=" + smcList + "smcCheck=" + smcCheck + "smcIssue=" + smcIssue + "]";
	}
}
