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
	
	@Column(name="smc_issue", nullable = false)
	private String smcIssue;
}
