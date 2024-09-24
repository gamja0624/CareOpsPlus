package himedia.project.careops.controller;

/*@author 노태윤
@editDate 2024-09-23~2024-09-24*/

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ManagerDTO; // ManagerDTO import
import himedia.project.careops.repository.MyPageRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @Autowired
    private MyPageRepository myPageRepository;

    @GetMapping("/mypage")
    public String getMyInfo(Model model, HttpSession session) {
        // 로그인 상태 확인
        String userType = (String) session.getAttribute("user_type"); // "admin" 또는 "manager"
        String userId = (String) session.getAttribute("user_id");

        /*
        if (userId == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        */

        if ("admin".equals(userType)) {
            Optional<AdminDTO> admin = myPageRepository.getMyInfoByAdminId(userId);
            if (admin.isPresent()) {
                model.addAttribute("admin", admin.get());
            } else {
                model.addAttribute("error", "관리자를 찾을 수 없습니다.");
            }
        } else if ("manager".equals(userType)) {
            Optional<ManagerDTO> manager = myPageRepository.getMyInfoByManagerId(userId);
            if (manager.isPresent()) {
                model.addAttribute("manager", manager.get());
            } else {
                model.addAttribute("error", "매니저를 찾을 수 없습니다.");
            }
        }

        return "common/mypage/mypage2"; // mypage.html 뷰 반환
    }

    @GetMapping("/mypage-edit")
    public String showUpdateInfoForm(Model model, HttpSession session) {
        // 로그인 상태 확인
        String userType = (String) session.getAttribute("user_type"); // "admin" 또는 "manager"
        String userId = (String) session.getAttribute("user_id");

        /*
        if (userId == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        */

        if ("admin".equals(userType)) {
            Optional<AdminDTO> admin = myPageRepository.getMyInfoByAdminId(userId);
            if (admin.isPresent()) {
                model.addAttribute("admin", admin.get());
                return "common/mypage/mypage-edit2"; // 수정 페이지로 이동
            }
        } else if ("manager".equals(userType)) {
            Optional<ManagerDTO> manager = myPageRepository.getMyInfoByManagerId(userId);
            if (manager.isPresent()) {
                model.addAttribute("manager", manager.get());
                return "common/mypage/mypage-edit2"; // 수정 페이지로 이동
            }
        }

        model.addAttribute("error", "정보를 찾을 수 없습니다.");
        return "common/mypage/mypage2"; // 정보를 찾을 수 없는 경우 내 정보 페이지로 리다이렉트
    }

    @PostMapping("/mypage-edit")
    public String updateMyInfo(@ModelAttribute AdminDTO admin, @ModelAttribute ManagerDTO manager, Model model, HttpSession session) {
        String userType = (String) session.getAttribute("user_type");
        String userId = (String) session.getAttribute("user_id");

        /*
        if (userId == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        */

        try {
            if ("admin".equals(userType)) {
                admin.setAdminId(userId); // 세션에서 가져온 ID로 설정
                int updatedAdmin = myPageRepository.updateMyInfo(userId, admin);
                model.addAttribute("message", "정보가 성공적으로 업데이트 되었습니다.");
            } else if ("manager".equals(userType)) {
                manager.setManagerId(userId); // 세션에서 가져온 ID로 설정
                int updatedManager = myPageRepository.updateMyInfo(userId, manager);
                model.addAttribute("message", "정보가 성공적으로 업데이트 되었습니다.");
            }
            return "common/mypage/mypage2"; // 성공적으로 업데이트 후 mypage로 이동
        } catch (Exception e) {
            model.addAttribute("error", "정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
            return "common/mypage/mypage-edit2"; // 오류 발생 시 수정 페이지로 돌아가기
        }
    }

    @PostMapping("/mypage-change-pw")
    public String updatePassword(@RequestParam("newPassword") String newPassword, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("user_id");
        String userType = (String) session.getAttribute("user_type");
        /*
        if (userId == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        */

        try {
            int updatedRows = myPageRepository.changePassword(userId, newPassword, userType);
            if (updatedRows > 0) {
                model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            } else {
                model.addAttribute("error", "비밀번호 변경 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 변경 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "common/mypage/mypage-change-pw2"; // 비밀번호 변경 페이지로 리다이렉트
    }
}