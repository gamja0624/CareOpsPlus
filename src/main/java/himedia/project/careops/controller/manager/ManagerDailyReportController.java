package himedia.project.careops.controller.manager;

/**
 * @author 이홍준
 * @editDate 2024-10-01 ~ 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himedia.project.careops.common.Pagenation;
import himedia.project.careops.common.PagingButtonInfo;
import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.service.DailyReportService;

@Controller
@RequestMapping("/manager")
public class ManagerDailyReportController {

	// 로그
		private Logger log = LoggerFactory.getLogger(this.getClass());
		
		private final DailyReportService dailyReportService;
		
		public ManagerDailyReportController(DailyReportService dailyReportService) {
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
		
		// 일일관리보고서 Search 페이지
		@GetMapping("/daily-report-list/search")
		public String reportSearch(@PageableDefault Pageable pageable, @RequestParam String filter, @RequestParam String value, Model model) {
  
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
			
			//log.info("넘어온 dmrNo {}", dmrNo);
			DailyManagementReportDTO result = dailyReportService.findByReportNo(dmrNo);
			
			model.addAttribute("result", result);
			
			return "admin/report/report-detail";
		}
}
