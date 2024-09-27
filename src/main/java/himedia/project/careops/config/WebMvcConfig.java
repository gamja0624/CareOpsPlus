package himedia.project.careops.config;

/**
 * @author 노태윤
 * @editDate 2024-09-26
 * 세션의 사용자 타입에 따라 접근을 제한하는 인터셉터 
 * ex) admin -> manager url 접근 불가능
 * ex) manager -> admin url 접근 불가능
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Spring MVC 설정을 위한 구성 클래스
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // WebMvcConfigurer 인터페이스의 메서드를 오버라이드하여 인터셉터를 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    // RoleBasedAccessInterceptor를 생성하고 인터셉터로 등록
        registry.addInterceptor(new RoleBasedAccessInterceptor())
        .addPathPatterns("/admin/**", "/manager/**");
        // 인터셉터가 적용될 URL 패턴을 지정
        // "/admin/**"와 "/manager/**" 경로에 대해 인터셉터가 동작함
    }
}