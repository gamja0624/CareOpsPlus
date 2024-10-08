package himedia.project.careops.controller;

/**
 * @author 노태윤 
 * @editDate 2024-09-27
 */

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import himedia.project.careops.dto.AdminDTO;
import himedia.project.careops.dto.ManagerDTO;
import himedia.project.careops.service.MyPageService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @Autowired
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }
    
    // 마이페이지 정보를 조회하는 메서드 
    // 사용자 타입에 따라 관리자 또는 매니저 정보를 가져와 MODEL에 추가
    @GetMapping("/mypage")
    public String getMyInfo(Model model, HttpSession session) {
        String userType = (String) session.getAttribute("userType");
        String userId = (String) session.getAttribute("userId");

        if ("admin".equals(userType)) {
            myPageService.getMyInfoByAdminId(userId).ifPresentOrElse(
                admin -> model.addAttribute("user", admin),
                () -> model.addAttribute("error", "관리자를 찾을 수 없습니다.")
            );
        } else if ("manager".equals(userType)) {
            myPageService.getMyInfoByManagerId(userId).ifPresentOrElse(
                manager -> model.addAttribute("user", manager),
                () -> model.addAttribute("error", "매니저를 찾을 수 없습니다.")
            );
        }
        
        // userType을 모델에 추가합니다.
        model.addAttribute("userType", userType);
        
        // 비밀번호 변경 메시지 처리
        String passwordChangeMessage = (String) session.getAttribute("passwordChangeMessage");
        if (passwordChangeMessage != null) {
            model.addAttribute("message", passwordChangeMessage);
            session.removeAttribute("passwordChangeMessage");
        }

        String passwordChangeError = (String) session.getAttribute("passwordChangeError");
        if (passwordChangeError != null) {
            model.addAttribute("error", passwordChangeError);
            session.removeAttribute("passwordChangeError");
        }

        return "common/mypage/mypage";
    }

    // 마이페이지 수정 폼을 보여주는 메서드
    // 사용자 타입에 따라 관리자 또는 매니저 정보를 가져와 모델에 추가
    @GetMapping("/mypage-edit")
    public String showEditForm(Model model, HttpSession session) {
        String userType = (String) session.getAttribute("userType");
        String userId = (String) session.getAttribute("userId");

        if ("admin".equals(userType)) {
            Optional<AdminDTO> admin = myPageService.getMyInfoByAdminId(userId);
            if (admin.isPresent()) {
                model.addAttribute("user", admin.get());
                model.addAttribute("userType", "admin");
            } else {
                model.addAttribute("error", "관리자를 찾을 수 없습니다.");
            }
        } else if ("manager".equals(userType)) {
            Optional<ManagerDTO> manager = myPageService.getMyInfoByManagerId(userId);
            if (manager.isPresent()) {
                model.addAttribute("user", manager.get());
                model.addAttribute("userType", "manager");
            } else {
                model.addAttribute("error", "매니저를 찾을 수 없습니다.");
            }
        }

        return "common/mypage/mypage-edit";
    }

    // 사용자 정보를 업데이트하는 메서드
    // 관리자 또는 매니저 정보를 업데이트하고 결과에 따라 메시지를 설정
    @PostMapping("/update-info")
    public String updateUserInfo(@RequestParam("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("userType") String userType,
                                 RedirectAttributes redirectAttributes) {
    						     // RedirectAttributes를 통해 일회성 데이터 전달 설정
        try {
            boolean updated = false;
            if ("admin".equals(userType)) {
                AdminDTO adminDTO = new AdminDTO();
                adminDTO.setAdminId(id);
                adminDTO.setAdminName(name);
                adminDTO.setAdminPhoneNumber(phoneNumber);
                updated = myPageService.updateAdminInfo(adminDTO);
                System.out.println("Admin update result: " + updated + ", ID: " + id + ", Name: " + name + ", Phone: " + phoneNumber);
            } else if ("manager".equals(userType)) {
                ManagerDTO managerDTO = new ManagerDTO();
                managerDTO.setManagerId(id);
                managerDTO.setManagerName(name);
                managerDTO.setManagerPhoneNumber(phoneNumber);
                updated = myPageService.updateManagerInfo(managerDTO);
                System.out.println("Manager update result: " + updated + ", ID: " + id + ", Name: " + name + ", Phone: " + phoneNumber);
            }
            // addAttribute는 값을 지속적으로 사용해야할때 addFlashAttribute는 일회성으로 사용해야할때 사용.
            if (updated) {
                redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "회원 정보 수정에 실패했습니다.");
            }
        } catch (Exception e) {
        	// err는 대부분 에러 메시지를 출력하기 위해 사용한다.
            System.err.println("Error updating user info: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "회원 정보 수정 중 오류가 발생했습니다.");
        }

        return "redirect:/mypage";
    }
    
    // 비밀번호를 업데이트하는 메서드
    // 새 비밀번호 확인, 비밀번호 검증하고 UPDATE 수행
    @PostMapping("/mypage-change-pw")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwordData, HttpSession session) {
        System.out.println("updatePassword 메서드 호출됨");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");
        System.out.println("newPassword: " + newPassword);
        System.out.println("confirmPassword: " + confirmPassword);

        String userId = (String) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");

        if (userId == null || userType == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "세션 정보가 유효하지 않습니다."));
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다."));
        }

        try {
            boolean success = myPageService.changePassword(userId, newPassword, userType);
            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 변경되었습니다."));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "비밀번호 변경에 실패했습니다."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "서버 오류: " + e.getMessage()));
        }
    }
    
    // 비밀번호 변경폼을 보여주는 메서드 (비밀번호 조회)
    // 사용자 타입에 따라 현재 비밀번호를 가져와 모델에 추가
    @GetMapping("/mypage-change-pw")
    public String showChangePasswordForm(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");

        if ("admin".equals(userType)) {
            // 관리자 비밀번호 가져오기
            String currentPassword = myPageService.getAdminPassword(userId);
            model.addAttribute("currentPassword", currentPassword);
        } else if ("manager".equals(userType)) {
            // 매니저 비밀번호 가져오기
            String currentPassword = myPageService.getManagerPassword(userId);
            model.addAttribute("currentPassword", currentPassword);
        }

        // userType을 모델에 추가
        model.addAttribute("userType", userType);

        return "common/mypage/mypage-change-pw"; // 비밀번호 변경 페이지로 이동
    }
}