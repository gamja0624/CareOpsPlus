package himedia.project.careops.repository;
/*@author 노태윤
@editDate 2024-09-13~2024-09-19*/
import himedia.project.careops.dto.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Spring Data JPA는 인터페이스에 추상 메서드(findByIdAndPassword)만 정의하면 메서드의 이름을 분석하여 자동으로 쿼리를 만들어 사용할 수 있게 해준다.
public interface LoginRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByIdAndPassword(String admin_id, String admin_password); // admin의 admin_id, admin_password 필드로 조회
}