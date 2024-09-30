package himedia.project.careops.controller;

/**
 * @author 진혜정
 * @editDate 2024-09-24 ~ 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@SessionAttributes({"department", "name"})
public class DashBoardController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @GetMapping("/manager/dash-board")
    public String DashBoard(HttpSession session) {
    	
    	log.info("대시보드 컨트롤러");
    	String userType = (String) session.getAttribute("user_type");
    	log.info("session : {}", userType);
    	
    	return "manager/dash-board";
    }
    
    @GetMapping("/admin/dash-board")
    public String adminDashBoard(HttpSession session) {
    	return "admin/dash-board";
    }
    
}