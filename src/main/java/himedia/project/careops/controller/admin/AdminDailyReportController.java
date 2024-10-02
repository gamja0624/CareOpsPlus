package himedia.project.careops.controller.admin;

/**
 * @author 이홍준 
 * @editDate 2024-09-29 ~
 */

import java.time.LocalDate;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.service.DailyReportService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminDailyReportController {
	
	// 로그
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final DailyReportService dailyReportService;
	
	public AdminDailyReportController(DailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}
	
	// 일일관리보고서 목록 페이지 + 페이지네이션
	@GetMapping("/daily-report-list")
	public String reportList(@PageableDefault Pageable pageable, Model model) {
		
		Page<DailyManagementReportDTO> reportAllList = dailyReportService.reportAllList(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(reportAllList);
		int totalPages = reportAllList.getTotalPages();
		
		model.addAttribute("reportAllList", reportAllList);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);
		
		return "admin/report/report-list";
	}
	
	// 일일관리보고서 상세 페이지
	@GetMapping("daily-report-detail/{dmrNo}")
	public String reportDtail(@PathVariable int dmrNo, Model model) {
		
		//log.info("넘어온 dmrNo {}", dmrNo);
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "admin/report/report-detail";
	}
	
	// 일일관리 보고서 수정페이지 이동
	@GetMapping("daily-report-edit/{dmrNo}")
	public String reportEdit(@PathVariable int dmrNo, Model model) {
		
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "admin/report/report-edit";
	}
	
	// 일일관리 보고서 수정 후 상세 페이지 이동
	@PostMapping("daily-report-detail/{dmrNo}")
	public String edit(@PathVariable int dmrNo, DailyManagementReportDTO editReport, HttpSession session,  Model model) {
		
		//log.info("모델로 넘어온 모델 {}", editReport);
		
		String adminName = (String)session.getAttribute("userName");
		String adminId = (String)session.getAttribute("userId");
		
		dailyReportService.editReportDetail(dmrNo, adminId, adminName, editReport);
		
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "redirect:{dmrNo}" ;
	}
	
	// 일일관리 보고서 등록 전 페이지 이동
	@GetMapping("/daily-report-regist")
	public String reportRegist(Model model) {
		LocalDate nowDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
		//log.info("오늘날짜{}", nowDate);
		model.addAttribute("nowDate", nowDate);
		return "admin/report/report-regist";
	}
	
	// 일일관리 보고서 등록 후 페이지 이동
	@PostMapping("/daily-report-list")
	public String reportSave(HttpSession session, DailyManagementReportDTO newReport) {
		
		String adminid = (String)session.getAttribute("userId");
		String adminName = (String)session.getAttribute("userName");
		String adminDeptNo = (String)session.getAttribute("deptNo");
		String adminDeptName = (String)session.getAttribute("department");
		
		
		//log.info("제출된 리포트 =={}", newReport);
		//log.info("작성자(관리자이름) =={}", adminName);
		//log.info("작성자 부서(관리자 부서) =={}", adminDeptName);
		
		dailyReportService.reportRegistation(adminid, adminName, adminDeptNo, adminDeptName, newReport);
		
		return "redirect:/admin/daily-report-list";
	}
	
	// 내가 쓴 보고서 보기 페이지 이동
	@GetMapping("/my-report")
	public String myReport(@PageableDefault Pageable pageable, HttpSession session, Model model) {
		
		String adminName = (String)session.getAttribute("userName");
		
		Page<DailyManagementReportDTO> searchMyReport = dailyReportService.searchMyReport(pageable, adminName);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(searchMyReport);
		int totalPages = searchMyReport.getTotalPages();	
		
		model.addAttribute("searchMyReport", searchMyReport);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);
		
		return "admin/report/report-my-report";
	}
	
}
