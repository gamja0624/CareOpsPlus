package himedia.project.careops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
	
    // 로그인
	@GetMapping("")
	public String login() {
		return "common/login";
	}
}
