package himedia.project.careops;
/*@author 노태윤
@editDate 2024-09-13~2024-09-19*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "himedia.project.careops.dto") // 엔티티가 위치한 패키지
public class CareopsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CareopsApplication.class, args);
    }
}