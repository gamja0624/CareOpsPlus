package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-10-15 ~ 2024-10-17
 */

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.entity.SafetyManagement;
import himedia.project.careops.repository.SafetyManagementRepository;
import jakarta.transaction.Transactional;

@Service
public class SafetyService {

	private final SafetyManagementRepository safetyManagementRepository;
	private final ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public SafetyService(SafetyManagementRepository safetyManagementRepository, ModelMapper modelMapper) {
		this.safetyManagementRepository = safetyManagementRepository;
		this.modelMapper = modelMapper;
	}

	// SafetyManagement테이블 전체조회 메소드
	public List<SafetyManagementDTO> safetyListAll() {

		List<SafetyManagement> safetyListAll = safetyManagementRepository.findAll();

		return safetyListAll.stream().distinct()
				.map(safetyManagementList -> modelMapper.map(safetyManagementList, SafetyManagementDTO.class))
				.collect(Collectors.toList());
	}

	// 점검항목(smList)만 보내는 메소드
	public List<String> findSmList() {
		
		List<String> smList = safetyListAll().stream().map(SafetyManagementDTO::getSmList) 	// smList 추출
				.distinct() 																// 중복 제거
				.collect(Collectors.toList()); 												// 결과를 리스트로 수집
		
		return smList;
	}

	// 해당 층수에 대한 점검항목(smList)의 데이터를 불러오는 메소드
	public List<SafetyManagementDTO> findStatusList(String smList, int smFacilityFloor) {
		
		List<SafetyManagement> findbySmListandSmFacilityFloor = safetyManagementRepository
				.findBySmListAndSmFacilityFloor(smList, smFacilityFloor);
		
		return findbySmListandSmFacilityFloor.stream().map(list -> modelMapper.map(list, SafetyManagementDTO.class))
				.collect(Collectors.toList());
	}
	
	// [수정된 안전관리 현황 실시간 Update]
	@Transactional
	public void updateStatus(SafetyManagementDTO updateData) {
		
	    // SafetyManagement 엔티티 조회
	    SafetyManagement entity = safetyManagementRepository.findBySmListAndSmFacilityNoAndSmFacilityFloor(
	    		updateData.getSmList(), updateData.getSmFacilityNo(), updateData.getSmFacilityFloor());

	    if (entity != null) {
	        // 엔티티에서 DTO로 변환
	        entity.setSmStatus(updateData.isSmStatus());
	        entity.setSmAdminId(updateData.getSmAdminId());
	        entity.setSmAdminDeptNo(updateData.getSmAdminDeptNo());
	        entity.setSmAdminDeptName(updateData.getSmAdminDeptName());
	        entity.setSmAdminName(updateData.getSmAdminName());
	        entity.setSmDate(updateData.getSmDate());

	        // 엔티티 저장
	        safetyManagementRepository.save(entity);
	    } else {
	        throw new IllegalArgumentException("해당 시설을 찾을 수 없습니다.");
	    }
	}
}