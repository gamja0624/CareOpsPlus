package himedia.project.careops.repository;

/**
 * @auther 진혜정
 * @eidtDate 2024-09-25
 */

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import himedia.project.careops.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    
	// 작성자 : 노태윤
    // Admin ID로 관리자를 찾는 메서드
    Optional<Admin> findByAdminId(String adminId);

    // 작성자 : 노태윤
    // 부서 번호, 관리자 아이디, 비밀번호로 관리자를 찾는 메서드
    Optional<Admin> findByAdminDeptNoAndAdminIdAndAdminPassword(String adminDeptNo, String adminId, String adminPassword);
    
    // 작성자 : 노태윤
    // 부서 번호와 관리자 ID를 기반으로 관리자 정보를 조회하는 메서드
    Optional<Admin> findByAdminDeptNoAndAdminId(String adminDeptNo, String adminId);
}
