package himedia.project.careops.controller;

/**
 * @author 진혜정
 * @editDate 2024-09-24 ~ 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashBoardController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/manager/dash-board")
	public String mdashBoard(Model model) {
		
		return "manager/dash-board";
	}
	
	@GetMapping("/admin/dash-board")
	public String adashBoard() {
		return "admin/dash-board";
	}
	
}
