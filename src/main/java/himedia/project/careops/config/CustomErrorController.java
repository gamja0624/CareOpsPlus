package himedia.project.careops.config;

/**
 * @author 진혜정
 * @editDate 2024-10-10 ~ 2024-10-16
 */

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error"; // 기본 에러 경로

    @RequestMapping(PATH)
    public String error() {
        return "common/404-notFound"; // 뷰 이름 반환
    }

    public String getErrorPath() {
        return PATH;
    }
}