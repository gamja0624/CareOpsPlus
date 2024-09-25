package himedia.project.careops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    @GetMapping("/dash-board")
    public String showAdminDashboard(Model model, HttpSession session) {
        String adminName = (String) session.getAttribute("admin_name");
        logger.info("Admin dashboard accessed. Admin name: {}", adminName);
        model.addAttribute("adminName", adminName);
        return "admin/dash-board";
    }
}