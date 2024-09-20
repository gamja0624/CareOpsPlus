package himedia.project.careops.controller.manager;

/**
 * @author 진혜정
 * @editDate 2024-09-13 ~ 
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashBoardCotroller {
	
	@GetMapping("/manager/dash-board")
	public String mdashBoard() {
		return "manager/dash-board";
	}
	@GetMapping("/admin/dash-board")
	public String adashBoard() {
		return "admin/dash-board";
	}
	
	@GetMapping("/test")
	public String test() {
		return "common/index";
	}
}
