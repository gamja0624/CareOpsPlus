package himedia.project.careops.controller.admin;

/**
 * @author 이홍준 
 * @editDate 2024-09-29 ~
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.entity.DailyManagementReport;
import himedia.project.careops.service.DailyReportService;

@Controller
@RequestMapping("/admin")
public class DailyReportController {
	
	// 로그
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final DailyReportService dailyReportService;
	
	public DailyReportController(DailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}
	
	// 일일관리보고서 목록 페이지 + 페이지네이션
	@GetMapping("/daily-report-list")
	public String reportList(@PageableDefault Pageable pageable, Model model) {
		
		Page<DailyManagementReportDTO> reportAllList = dailyReportService.reportAllList(pageable);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(reportAllList);
		
		model.addAttribute("reportAllList", reportAllList);
		model.addAttribute("paging", paging);
		
		return "admin/report/report-list";
	}
	
	// 일일관리보고서 상세 페이지
	@GetMapping("daily-report-detail/{dmrNo}")
	public String reportDtail(@PathVariable int dmrNo, Model model) {
		
		log.info("넘어온 dmrNo {}", dmrNo);
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "admin/report/report-detail";
	}
	
	// 일일관리 보고서 수정페이지
	@GetMapping("daily-report-edit/{dmrNo}")
	public String reportEdit(@PathVariable int dmrNo, Model model) {
		
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "admin/report/report-edit";
	}
	
	// 일일관리 보고서 수정 후 상세 페이지 이동
	@PostMapping("daily-report-detail/{dmrNo}")
	public String edit(@PathVariable int dmrNo, DailyManagementReportDTO editReport,  Model model) {
		
		log.info("모델로 넘어온 모델 {}", editReport);
		dailyReportService.editReportDetail(dmrNo, editReport);
		
		DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
		
		model.addAttribute("result", result);
		
		return "redirect:{dmrNo}" ;
	}
	
}
