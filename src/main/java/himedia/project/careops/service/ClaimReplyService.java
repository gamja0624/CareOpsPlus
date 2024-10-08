package himedia.project.careops.service;
/**
 * @author 최은지
 * @editDate 2024-10-07 ~ 
 */

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ClaimReplyDTO;
import himedia.project.careops.entity.Claim;
import himedia.project.careops.entity.ClaimReply;
import himedia.project.careops.entity.ClaimReplyId;
import himedia.project.careops.repository.ClaimReplyRepository;
import himedia.project.careops.repository.ClaimRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ClaimReplyService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private List <ClaimReplyId> claimReplyIdList = new ArrayList<>(); // 인덱스 설정을 위한 리스트 생성
	private final ClaimRepository claimRepository;
	private final ClaimReplyRepository claimReplyRepository;
	private final ModelMapper modelMapper;
	
	public ClaimReplyService(ClaimRepository claimRepository, ClaimReplyRepository claimReplyRepository, ModelMapper modelMapper) {
		this.claimRepository = claimRepository;
		this.claimReplyRepository = claimReplyRepository;
		this.modelMapper = modelMapper;
	}
	
	// [ 작업 관리자 ] ================================================================
	// 인덱스 설정 
	public ClaimReplyId saveClaimReplyId (Claim claim) {
		
		ClaimReplyId claimReplyId = new ClaimReplyId();
		
		claimReplyId.setClaimNo(claim.getClaimNo());
		claimReplyId.setClaimCategoryNo(claim.getClaimCategoryNo());
		claimReplyId.setClaimSubCategoryNo(claim.getClaimSubCategoryNO());
		claimReplyId.setManagerId(claim.getManagerId());
		claimReplyId.setManagerDeptNo(claim.getManagerDeptNo());
		
		claimReplyIdList.add(claimReplyId);
		
		return claimReplyId; 
	}
	
	// 답변 목록 전체 조회
	public List<ClaimReplyDTO> claimReplyList() {
		
		log.info("답변 조회 목록 서비스");	
		List<ClaimReply> claimReplyList = claimReplyRepository.findAll();
		log.info("답변 목록 : {}" , claimReplyList);
		return claimReplyList.stream()
					.map(claimReply -> modelMapper.map(claimReply, ClaimReplyDTO.class))
					.collect(Collectors.toList());		
	}

	// 답변 상세 조회
	public ClaimReplyDTO findClaimReply(Integer claimNo) {
		log.info("답변 상세 조회 서비스");
		
		// 인덱스 정보 
		Claim claim = claimRepository.findById(claimNo).get();
		ClaimReplyId claimReplyId = saveClaimReplyId(claim);
	    
	    // ClaimReply 조회
	    ClaimReply claimReply = claimReplyRepository.findById(claimReplyId)
	        .orElseThrow(() -> new IllegalArgumentException("해당 claimReply에 대한 답변이 존재하지 않습니다: " + claimNo));

	    return modelMapper.map(claimReply, ClaimReplyDTO.class);
	}
	

	// 답변 저장 
	public void saveClaimReply(ClaimReplyDTO claimReplyDTO, Integer claimNo, HttpSession session) {
		
		// 답변 객체 객체 생성
		ClaimReply claimReply = new ClaimReply();
		Date currentDate = new Date(System.currentTimeMillis()); // 현재 날짜 가져오기 ( 임시.. )
		
		// 민원 번호로 해당 내용 조회 후, 저장
		// 저장해야 할 정보 1) 민원 번호, 대분류 번호, 소분류 번호, 담당자 아이디, 담당자 부서번호, 담당자 이름 ( 인덱스 키 -> 민원의 정보 가져옴 )
		Claim claim = claimRepository.findById(claimNo).get();
		ClaimReplyId claimReplyId = saveClaimReplyId(claim);
		claimReply.setClaimReplyId(claimReplyId);
		
		log.info("[답변 저장 서비스] claimReplyId : {}", claimReplyId);
		// 세션 정보 가져온 후 , 저장
		// 저장해야 할 정보 2) 관리자 아이디, 관리자 부서번호, 관리자 이름  ( 세션 )
		String adminId = (String) session.getAttribute("userId");
	    String adminDeptNo = (String) session.getAttribute("deptNo"); 
	    String adminName = (String) session.getAttribute("userName");
	    log.info("[답변 저장 서비스]  adminName : {}", adminName);	    
	    claimReply.setAdminId(adminId);
	    claimReply.setAdminDeptNo(adminDeptNo);
	    claimReply.setAdminName(adminName);
		
	    // 입력 받은 정보 가져오기
		// 저장해야 할 정보 3) 답변 제목, 답변 내용 
		claimReply.setClaimReTitle(claimReplyDTO.getClaimReTitle());
		claimReply.setClaimReContent(claimReplyDTO.getClaimReContent());	
		claimReply.setClaimReDate(currentDate);
		log.info("[답변 저장 서비스 완료 ] claimReply : {}", claimReply);
		
		// 최종 저장
		claimReplyRepository.save(claimReply);
	}
}
