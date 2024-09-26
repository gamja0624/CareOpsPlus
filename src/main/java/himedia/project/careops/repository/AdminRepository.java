package himedia.project.careops.repository;

/**
 * @author 노태윤 
 * @editDate 2024-09-26
 */

import java.util.Optional;

/**
 * @author 진혜정
 * @editDate 2024-09-25
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.Admin;
import himedia.project.careops.entity.AdminDepartment;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	
	 // 부서 번호, 관리자 아이디, 비밀번호로 관리자를 찾는 메서드
     Optional<Admin> findByAdminDeptNoAndAdminIdAndAdminPassword(AdminDepartment adminDeptNo, String adminId, String adminPassword);
     
     // Mypage에 쓸 메서드 
     Optional<Admin> findByAdminId(String adminId);
     
     
}
