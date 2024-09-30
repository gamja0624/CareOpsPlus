package himedia.project.careops.controller.admin;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

/**
 * @author 이홍준 
 * @editDate 2024-09-29
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.entity.DailyManagementReport;
import himedia.project.careops.service.DailyReportService;
import org.springframework.web.bind.annotation.RequestParam;


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
		DailyManagementReport result = dailyReportService.findByReportNo(dmrNo).get();
		
		model.addAttribute("result", result);
		
		return "admin/report/report-detail";
	}
	
	
}
