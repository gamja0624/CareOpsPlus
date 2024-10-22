package himedia.project.careops.repository;

/**
 * @author 노태윤
 * @editDate 2024-09-25
 */

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
	
	// 작성자 : 노태윤
	// 부서 번호, 매니저 아이디, 비밀번호로 매니저를 찾는 메서드
    Optional<Manager> findByManagerDeptNoAndManagerIdAndManagerPassword(int managerDeptNo, String managerId, String managerPassword);

    // 작성자 : 노태윤
    // 매니저 id 찾는 메서드
    Optional<Manager> findByManagerId(String managerId);

    // 작성자 : 노태윤
    // 부서 번호와 매니저 ID를 기반으로 매니저 정보를 조회하는 메서드
    Optional<Manager> findByManagerDeptNoAndManagerId(int managerDeptNo, String managerId);
}
