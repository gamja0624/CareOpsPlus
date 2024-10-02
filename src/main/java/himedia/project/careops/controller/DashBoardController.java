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

import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ListMedicalDevices;
import himedia.project.careops.service.ClaimService;
import himedia.project.careops.service.MedicalService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
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
    	String userId = (String) session.getAttribute("user_id");
    	String userName = (String) session.getAttribute("name");

    	log.info("deptNo : {}", departmentNo);
    	log.info("department : {}", department);
    	log.info("userId : {}", userId);
    	log.info("userName : {}", userName);
    	
    	// [서비스 미리보기]
    	// 담당 부서 민원 개수 반환
    	Integer deptNo = Integer.parseInt(departmentNo); // 부서 번호 Integer 형변환
    	List<Claim> claimCnt = claimService.findByClaimDeptNo(deptNo);
    	model.addAttribute("claimCnt", claimCnt.size());
    	
    	// 담당 부서 의료기기 개수 반환
    	List<ListMedicalDevices> medicalList = medicalService.findByMedicalDeptName(department);
    	model.addAttribute("medicalCnt", medicalList.size());
    	
    	// [의료기기 상태 현황]
    	Map<String, Integer> MedicalStatus = medicalService.findByMedicalStatus();
    	model.addAttribute("MedicalStatus", MedicalStatus);
    	
    	// [민원 최신순 3개 정렬]
    	List<Claim> claimList = claimService.findByClaimListManagerName(userName);
    	log.info("claimList : {}", claimList);
    	
    	return "manager/dash-board";
    }
    
    @GetMapping("/admin/dash-board")
    public String adminDashBoard(HttpSession session, Model model) {
    	
    	// session 받아온 부서 번호, 이름
//    	String departmentNo = (String) session.getAttribute("deptNo");
//    	String department = (String) session.getAttribute("department");
//    	String userName = (String) session.getAttribute("name");

    	// [서비스 미리보기]
    	// 민원 접수 대기 / 접수 진행건
    	Map<String, Integer> claimStatus = claimService.findByClaimStatus();
    	model.addAttribute("claimStatus", claimStatus);
    	
    	// 담당 부서 의료기기 개수 반환
    	
    	// [민원 현황]
//    	List<Claim> ClaimList = claimService.findByMyClaim(userName);
//    	model.addAttribute("ClaimList", ClaimList);
    	
    	return "admin/dash-board";
    }
    
}