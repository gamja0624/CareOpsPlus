package himedia.project.careops.config;

/**
 * @author 진혜정
 * @editDate 2024-10-10
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// 커스텀 페이지를 위해 설정 파일에 정적 리소스 매핑 false 해서,
	// 정적 리소스 경로 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
        		.addResourceLocations("/*.png")
                .addResourceLocations("classpath:/static/")
        		.addResourceLocations("classpath:/templates/");
    }
}