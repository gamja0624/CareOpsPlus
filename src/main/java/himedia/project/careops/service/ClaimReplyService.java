package himedia.project.careops.service;
/**
 * @author 최은지
 * @editDate 2024-10-07 ~ 
 */

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ClaimReplyDTO;
import himedia.project.careops.entity.ClaimReply;
import himedia.project.careops.entity.ClaimReplyId;
import himedia.project.careops.repository.ClaimReplyRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ClaimReplyService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final ClaimReplyRepository claimReplyRepository;
	private final ModelMapper modelMapper;
	
	public ClaimReplyService(ClaimReplyRepository claimReplyRepository, ModelMapper modelMapper) {
		this.claimReplyRepository = claimReplyRepository;
		this.modelMapper = modelMapper;
	}
	
	// [ 작업 관리자 ] ================================================================
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
		log.info("답변  상세 조회 서비스");	
	    // ClaimReplyId 객체 생성 및 필드 설정
	    ClaimReplyId claimReplyId = new ClaimReplyId();
	    claimReplyId.setClaimNo(claimNo);
	    
	    // ClaimReply 조회
	    ClaimReply claimReply = claimReplyRepository.findById(claimReplyId)
	        .orElseThrow(() -> new IllegalArgumentException("해당 claimReply에 대한 답변이 존재하지 않습니다: " + claimNo));

	    // ClaimReply 엔티티를 ClaimReplyDTO로 변환
	    return modelMapper.map(claimReply, ClaimReplyDTO.class);
	}

}
