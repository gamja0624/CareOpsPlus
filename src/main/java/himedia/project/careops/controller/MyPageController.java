package himedia.project.careops.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import himedia.project.careops.dto.Admin;
import himedia.project.careops.repository.MyPageRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @Autowired
    private MyPageRepository myPageRepository;

    // 관리자 버전 로그인
    @GetMapping("/mypage")
    public String getMyInfo(Model model, HttpSession session) {
        // 로그인 상태를 강제로 설정
        String adminId = (String) session.getAttribute("admin_id");
        if (adminId == null) {
            adminId = "testAdminId"; // 임시로 강제 로그인 상태 설정
            session.setAttribute("admin_id", adminId);
        }

        Optional<Admin> admin = myPageRepository.getMyInfoByAdminId(adminId);

        if (admin.isPresent()) {
            model.addAttribute("admin", admin.get()); // Admin 객체 그대로 사용
        } else {
            model.addAttribute("error", "관리자를 찾을 수 없습니다.");
        }

        return "common/mypage/mypage2"; // my-page.html 뷰 반환
    }
    
    // 내 정보 수정 페이지로 이동
    @GetMapping("/mypage-edit")
    public String showUpdateInfoForm(Model model, HttpSession session) {
        // 로그인 상태를 강제로 설정
        String adminId = (String) session.getAttribute("admin_id");
        if (adminId == null) {
            adminId = "testAdminId"; // 임시로 강제 로그인 상태 설정
            session.setAttribute("admin_id", adminId);
        }

        Optional<Admin> admin = myPageRepository.getMyInfoByAdminId(adminId);

        if (admin.isPresent()) {
            model.addAttribute("admin", admin.get()); // 수정할 관리자 정보를 모델에 추가
            return "common/mypage/mypage-edit2"; // 수정 페이지 (mypage-edit.html)으로 이동
        } else {
            model.addAttribute("error", "관리자를 찾을 수 없습니다.");
            return "common/mypage/mypage-edit2"; // 정보를 찾을 수 없는 경우 다시 내 정보 페이지로 리다이렉트
        }
    }
    
    @PostMapping("/mypage-edit")
    public String updateMyInfo(@ModelAttribute Admin admin, Model model, HttpSession session) {
        String admin_id = (String) session.getAttribute("admin_id");
        if (admin_id == null) {
            return "redirect:/mypage-change-pw";
        }

        try {
            admin.setAdmin_id(admin_id); // 세션에서 가져온 ID로 설정
            int updatedAdmin = myPageRepository.updateMyInfo(admin_id, admin);
            model.addAttribute("admin", updatedAdmin);
            model.addAttribute("message", "정보가 성공적으로 업데이트 되었습니다.");
            return "common/mypage/mypage";
        } catch (Exception e) {
            model.addAttribute("error", "정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
            return "common/mypage/mypage-edit";
        }
    }
    
    // 비밀번호 변경 페이지 controller
    @GetMapping("/mypage-change-pw")
    public String myPageChangePw() {
        return "common/mypage/mypage-change-pw2";
    }
}
