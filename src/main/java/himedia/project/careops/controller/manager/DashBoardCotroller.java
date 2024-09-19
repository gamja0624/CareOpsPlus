package himedia.project.careops.controller.manager;

/**
 * @author 진혜정
 * @editDate 2024-09-13 ~ 
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/dash-board")
public class DashBoardCotroller {
	
	@GetMapping("")
	public String dashBoard() {
		return "manager/dash-board";
	}
}
