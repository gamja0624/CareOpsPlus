package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-25 ~ 
 */

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.SafetyManagementChecklistDTO;
import himedia.project.careops.dto.SafetyManagementDTO;
import himedia.project.careops.dto.SafetyManagementListDTO;
import himedia.project.careops.entity.SafetyManagement;
import himedia.project.careops.entity.SafetyManagementChecklist;
import himedia.project.careops.entity.SafetyManagementList;
import himedia.project.careops.repository.SafetyManagementChecklistRepository;
import himedia.project.careops.repository.SafetyManagementListRepository;
import himedia.project.careops.repository.SafetyManagementRepository;

@Service
public class SafetyService {

	private final SafetyManagementRepository safetyManagementRepository;
	private final SafetyManagementListRepository safetyManagementListRepository;
	private final SafetyManagementChecklistRepository safetyManagementChecklistRepository;
	private final ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public SafetyService(SafetyManagementRepository safetyManagementRepository,
			SafetyManagementListRepository safetyManagementListRepository,
			SafetyManagementChecklistRepository safetyManagementChecklistRepository, ModelMapper modelMapper) {
		this.safetyManagementRepository = safetyManagementRepository;
		this.safetyManagementListRepository = safetyManagementListRepository;
		this.safetyManagementChecklistRepository = safetyManagementChecklistRepository;
		this.modelMapper = modelMapper;
	}

	// smlRepository 사용 메소드(전체조회 네이티브쿼리)
	public List<SafetyManagementListDTO> findAllList() {
		List<SafetyManagementList> allList = safetyManagementListRepository.findAllList(); // 타입을 Object로 변경했지만 안 됨
		return allList.stream().distinct()
				.map(SafetyManagementList -> modelMapper.map(SafetyManagementList, SafetyManagementListDTO.class))
				.collect(Collectors.toList());
	}

	// SafetyManagement 테이블 전체조회
	public List<SafetyManagementDTO> safetyResultList() {
		log.info("safetyResultList 실행");
		List<SafetyManagement> safetyResultList = safetyManagementRepository.findAll();
		return safetyResultList.stream()
				.map(safetyManagement -> modelMapper.map(safetyManagement, SafetyManagementDTO.class))
				.collect(Collectors.toList());
	}

	// SafetyManagementChecklist테이블 전체조회 메소드
	public List<SafetyManagementListDTO> safetyListAll() {

		List<SafetyManagementList> safetyListAll = safetyManagementListRepository.findAll();

		/*
		 * List<SafetyManagementList> safetyListAll =
		 * safetyManagementListRepository.findAllList();
		 */
		return safetyListAll.stream().distinct()
				.map(safetyManagementList -> modelMapper.map(safetyManagementList, SafetyManagementListDTO.class))
				.collect(Collectors.toList());
	}

	// SafetyManagementChecklist테이블 전체조회 메소드 + 페이지네이션
	public List<SafetyManagementChecklistDTO> findAllChecklist(String smlList) {

		List<SafetyManagementChecklist> allList = safetyManagementChecklistRepository.findBySmlList(smlList);
		return allList.stream().distinct()
				.map(checklist -> modelMapper.map(checklist, SafetyManagementChecklistDTO.class))
				.collect(Collectors.toList());
	}

	// 층별 체크리스트 조회
	public List<SafetyManagementChecklistDTO> findCheckList(String smlList, int smcFloor) {

		List<SafetyManagementChecklist> bySmcFloor = safetyManagementChecklistRepository.findBySmcFloor(smcFloor);

		return bySmcFloor.stream().distinct().filter(list -> list.getSmlList().equals(smlList))
				.map(list -> modelMapper.map(list, SafetyManagementChecklistDTO.class)).collect(Collectors.toList());
	}

	// 등록 수정 삭제 시 @Transactional 설정 필요
}
