package himedia.project.careops.controller.admin;

/**
 * @author 이홍준 
 * @editDate 2024-09-29 ~ 2024-10-17
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
import org.springframework.web.bind.annotation.RequestParam;

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
	
	// 일일관리보고서 Search 페이지 + 페이지네이션
	@GetMapping("/daily-report-list/search")
	public String searchReport(@PageableDefault Pageable pageable, @RequestParam String filter, @RequestParam String value, Model model) {
  
		Page<DailyManagementReportDTO> reportSearch = dailyReportService.reportSearch(filter, value, pageable); 
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(reportSearch); 
		int totalPages = reportSearch.getTotalPages();
	  
		model.addAttribute("reportAllList", reportSearch);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("filter", filter);
		model.addAttribute("value", value);
  
		return "/admin/report/report-search-list"; 
	}
	 
	
	// 일일관리보고서 상세 페이지
	@GetMapping("daily-report-detail/{dmrNo}")
	public String reportDtail(@PathVariable int dmrNo, Model model) {
		
		// 특정 보고서 번호 기준의 모든 컬럼 데이터 반환
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "admin/report/report-detail";
	}
	
	// 일일관리 보고서 수정페이지 이동
	@GetMapping("daily-report-edit/{dmrNo}")
	public String reportEdit(@PathVariable int dmrNo, Model model) {
		
		// 특정 보고서 번호 기준의 모든 컬럼 데이터 반환
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "admin/report/report-edit";
	}
	
	// 일일관리 보고서 수정 후 상세 페이지 이동
	@PostMapping("daily-report-detail/{dmrNo}")
	public String edit(@PathVariable int dmrNo, DailyManagementReportDTO editReport, HttpSession session,  Model model) {
		
		// 세션에 저장된 로그인정보에서 아이디와 이름 저장
		String adminName = (String)session.getAttribute("userName");
		String adminId = (String)session.getAttribute("userId");
		
		// 수정된 보고서 정보를 업데이트
		dailyReportService.editReportDetail(dmrNo, adminId, adminName, editReport);
		
		// 업데이트가 완료된 후 업데이트 된 보고서 데이터 반환
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "redirect:{dmrNo}" ;
	}
	
	// 일일관리 보고서 등록 전 페이지 이동
	@GetMapping("/daily-report-regist")
	public String reportRegist(Model model) {
		
		// 현재 날짜에 대한 정보 저장
		LocalDate nowDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
		
		model.addAttribute("nowDate", nowDate);
		
		return "admin/report/report-regist";
	}
	
	// 일일관리 보고서 등록 후 페이지 이동
	@PostMapping("/daily-report-list")
	public String reportSave(HttpSession session, DailyManagementReportDTO newReport) {
		
		// 세션에 저장된 로그인정보에서 아이디, 이름, 부서 정보 저장
		String adminid = (String)session.getAttribute("userId");
		String adminName = (String)session.getAttribute("userName");
		String adminDeptNo = (String)session.getAttribute("deptNo");
		String adminDeptName = (String)session.getAttribute("department");
		
		dailyReportService.reportRegistation(adminid, adminName, adminDeptNo, adminDeptName, newReport);
		
		return "redirect:/admin/daily-report-list";
	}
	
	// 사이드바의 '내 보고서' 페이지 이동
	@GetMapping("/my-report")
	public String myReport(@PageableDefault Pageable pageable, HttpSession session, Model model) {
		
		// 세션에 저장된 로그인정보에서 이름 정보 저장
		String adminName = (String)session.getAttribute("userName");
		
		// 저장된 이름 정보를 통해 작성된 보고서를 페이지로 반환
		Page<DailyManagementReportDTO> searchMyReport = dailyReportService.searchMyReport(pageable, adminName);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(searchMyReport);
		
		int totalPages = searchMyReport.getTotalPages();	
		
		model.addAttribute("searchMyReport", searchMyReport);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages", totalPages);
		
		return "admin/report/report-my-report";
	}
	
	// 내 보고서 페이지 내 검색 페이지
	@GetMapping("/my-report/search")
	public String searchInMyReport(@PageableDefault Pageable pageable, @RequestParam String filter, @RequestParam String value,HttpSession session, Model model) {
		
		// 세션에 저장된 로그인정보에서 이름 정보 저장
		String adminName = (String)session.getAttribute("userName");
		
		// 저장된 이름 정보를 통해 작성된 보고서 중 입력된 날짜에 작성된 보고서 데이터 반환
		Page<DailyManagementReportDTO> searchMyReport = dailyReportService.searchReporByDate(adminName, filter, value, pageable); 
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(searchMyReport);
		
		int totalPages = searchMyReport.getTotalPages();

		model.addAttribute("searchMyReport", searchMyReport);
		model.addAttribute("paging", paging);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("filter", filter);
		model.addAttribute("value", value);

		return "admin/report/report-my-report-search";
	}
}
