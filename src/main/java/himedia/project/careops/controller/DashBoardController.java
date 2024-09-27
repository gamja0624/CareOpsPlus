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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes({"department", "name"})
public class DashBoardController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    // 작성자 : 노태윤
    // manager/dash-board.html 을 return하는 메서드
    // 세션에서 부서와 이름 정보를 확인하고, 없으면 login.html로 redirect
    @GetMapping("/manager/dash-board")
    public String mdashBoard(Model model) {
        // 세션에서 department와 name 정보를 가져옵니다.
        String department = (String) model.getAttribute("department");
        String name = (String) model.getAttribute("name");
        
        if (department == null || name == null) {
            // 세션에 정보가 없는 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        
        log.info("Manager Dashboard - Department: {}, Name: {}", department, name);
        
        return "manager/dash-board";
    }
    
    // 작성자 : 노태윤
    // manager/dash-board.html 을 return하는 메서드
    // 세션에서 부서와 이름 정보를 확인하고, 없으면 login.html로 redirect
    @GetMapping("/admin/dash-board")
    public String adashBoard(Model model) {
        // 세션에서 department와 name 정보를 가져옵니다.
        String department = (String) model.getAttribute("department");
        String name = (String) model.getAttribute("name");
        
        if (department == null || name == null) {
            // 세션에 정보가 없는 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        
        log.info("Admin Dashboard - Department: {}, Name: {}", department, name);
        
        return "admin/dash-board";
    }
    
}