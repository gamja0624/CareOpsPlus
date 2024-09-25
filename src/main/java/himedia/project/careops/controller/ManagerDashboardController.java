package himedia.project.careops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerDashboardController.class);

    @GetMapping("/dash-board")
    public String showManagerDashboard(Model model, HttpSession session) {
        String managerName = (String) session.getAttribute("manager_name");
        logger.info("Manager dashboard accessed. Manager name: {}", managerName);
        model.addAttribute("managerName", managerName);
        return "manager/dash-board";
    }
}