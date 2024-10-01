package himedia.project.careops.controller;

/**
 * @author 진혜정
 * @editDate 2024-09-24 ~ 
 */

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ListMedicalDevices;
import himedia.project.careops.service.ClaimService;
import himedia.project.careops.service.MedicalService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@SessionAttributes({"department", "name"})
public class DashBoardController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final ClaimService claimService;
    private final MedicalService medicalService;
    
    public DashBoardController(ClaimService claimService, MedicalService medicalService) {
    	this.claimService = claimService;
    	this.medicalService = medicalService;
    }
    
    @GetMapping("/manager/dash-board")
    public String DashBoard(HttpSession session, Model model) {
    	
    	// session 받아온 부서 번호, 이름
    	String departmentNo = (String) session.getAttribute("deptNo");
    	String department = (String) session.getAttribute("department");

    	// [서비스 미리보기]
    	// 담당 부서 민원 개수 반환
    	Integer deptNo = Integer.parseInt(departmentNo); // 부서 번호 Integer 형변환
    	List<Claim> claimList = claimService.findByClaimDeptNo(deptNo);
    	model.addAttribute("claimCnt", claimList.size());
    	
    	// 담당 부서 의료기기 개수 반환
    	List<ListMedicalDevices> medicalList = medicalService.findByMedicalDeptName(department);
    	model.addAttribute("medicalCnt", medicalList.size());
    	
    	// [의료기기 상태 현황]
    	// List<ListMedicalDevices> medical findByMedicalStatus(String lmdStatus)
    	Map<String, Integer> MedicalStatus = medicalService.findByMedicalStatus();
    	model.addAttribute("MedicalStatus", MedicalStatus);
    	
    	return "manager/dash-board";
    }
    
    @GetMapping("/admin/dash-board")
    public String adminDashBoard(HttpSession session) {
    	return "admin/dash-board";
    }
    
}