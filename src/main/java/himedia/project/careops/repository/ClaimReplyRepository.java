package himedia.project.careops.repository;

/**
 * @author 최은지 
 * @editDate 2024-09-26
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import himedia.project.careops.entity.ClaimReply;
import himedia.project.careops.entity.ClaimReplyId;

@Repository
public interface ClaimReplyRepository extends JpaRepository<ClaimReply, ClaimReplyId> {

}
