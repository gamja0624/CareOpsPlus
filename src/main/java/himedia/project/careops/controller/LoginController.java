package himedia.project.careops.controller;

/**
 * @author 노태윤 
 * @editDate 2024-09-27
 */

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import himedia.project.careops.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	// 작성자 : 진혜정
	// 파라미터 값으로 favicon.ico 들어오는 거 방지
	@GetMapping("favicon.ico")
	@ResponseBody
	public void returnNoFavicon() {
	}

	// 작성자 : 노태윤
	// 로그인 페이지를 표시해줌
	@GetMapping("")
	public String showLoginPage() {
		return "common/login"; // 로그인 페이지 반환
	}

	// 작성자 : 노태윤
	// 로그인 처리
	// @return 로그인 결과 및 리다이렉트 url을 포함한 ResponseEntity
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> login(@RequestParam("departmentDeptNo") String deptNo,
	        @RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword,
	        HttpSession session) {

	    logger.info("로그인 시도: deptNo={}, userId={}", deptNo, userId);

	    Map<String, Object> loginResult = loginService.login(deptNo, userId, userPassword);
	    Map<String, Object> response = new HashMap<>();

	    if (loginResult.get("success") != null && (Boolean) loginResult.get("success")) {
	        String userType = (String) loginResult.get("userType");
	        String userName = (String) loginResult.get("userName");
	        String departmentName = (String) loginResult.get("departmentName");

	        logger.info("{} 로그인 성공: {}", deptNo, userType);

	        session.setAttribute("userType", userType);
	        session.setAttribute("deptNo", deptNo);
	        session.setAttribute("department", departmentName);
	        session.setAttribute("userId", userId);
	        session.setAttribute("name", userName);

	        String redirectUrl = "/" + userType + "/dash-board";
	        response.put("success", true);
	        response.put("redirectUrl", redirectUrl);
	        response.put("userType", userType);
	        response.put("deptNo", deptNo);
	        response.put("userId", userId);
	        response.put("userName", userName);
	        response.put("department", departmentName);

	        logger.info("세션 스토리지에 저장할 사용자 정보: userType={}, deptNo={}, userId={}, userName={}, department={}", 
	            userType, deptNo, userId, userName, departmentName);
	    } else {
	        logger.warn("로그인 실패: deptNo={}, userId={}", deptNo, userId);
	        response.put("success", false);
	        response.put("message", loginResult.get("message")); 
	    }
		// JSON 형식으로 응답데이터를 변환하여 HTTP 200 OK 상태 코드와 함께 응답 반환
		// JSON 데이터를 클라이언트에게 반환함 클라이언트는 서버로 JSON 형식의 응답을 받게된다.
		// 이를통해 로그인 성공 여부, 리다이렉트 URL, 에러 메시지 등의 정보 쉽게 처리 가능
	    return ResponseEntity.ok(response);
	}
	
	// 작성자 : 노태윤
    // 로그아웃 처리
	// cookie삭제를 통해 로그아웃하면 다시 로그인하게 설정
    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        
        return "redirect:/";
    }	
}