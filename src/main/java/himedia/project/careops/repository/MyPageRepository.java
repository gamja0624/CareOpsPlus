package himedia.project.careops.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import himedia.project.careops.dto.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MyPageRepository {

    // EntityManager 주입
    @PersistenceContext
    private EntityManager entityManager;

    // AdminID로 관리자 정보 가져오기
    public Optional<Admin> getMyInfoByAdminId(String admin_id) {
        try {
            Admin admin = entityManager.createQuery("SELECT a FROM Admin a WHERE a.admin_id = :adminId", Admin.class)
                                       .setParameter("adminId", admin_id)
                                       .getSingleResult();
            return Optional.of(admin);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // 내 정보 수정 
    // @Transactional : 모든 작업들이 성공해야만 최종적으로 데이터베이스에 반영하도록 한다. 
    @Transactional
    public int updateMyInfo(String admin_id, Admin updateAdmin) {
        String jpql = "UPDATE Admin a SET a.admin_dept_no =:admin_dept_no, a.admin_name =:admin_name, a.admin_phonenumber =:admin_phonenumber WHERE a.admin_id=:admin_id";
        return entityManager.createQuery(jpql)
                            .setParameter("admin_dept_no", updateAdmin.getadmin_dept_no())
                            .setParameter("admin_name", updateAdmin.getadmin_name())
                            .setParameter("admin_phonenumber", updateAdmin.getadmin_phonenumber())
                            .setParameter("admin_id", admin_id)
                            .executeUpdate();
    }

    // 내 비밀번호 변경 (작성 예정)

}
