package himedia.project.careops.repository;
/*@author 노태윤
@editDate 2024-09-13~2024-09-19*/
import himedia.project.careops.dto.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Spring Data JPA는 인터페이스에 추상 메서드(findByIdAndPassword)만 정의하면 메서드의 이름을 분석하여 자동으로 쿼리를 만들어 사용할 수 있게 해준다.
//public interface LoginRepository extends JpaRepository<Admin, String> {
//    Optional<Admin> findByIdAndPassword(String admin_id, String admin_password); // admin의 admin_id, admin_password 필드로 조회
//}

public interface LoginRepository extends CrudRepository<Admin, String> {

	 @Query("SELECT a FROM Admin a WHERE a.admin_dept_no = ?1 AND a.admin_id = ?2 AND a.admin_password = ?3")
	 Optional<Admin> findByAdminDeptNoAndAdminIdAndAdminPassword(String adminDeptNo, String adminId, String adminPassword);
	}