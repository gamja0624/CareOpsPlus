package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-25 ~ 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import jakarta.transaction.Transactional;

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

	// 점검표 세부 항목(smcList) 수정
	@Transactional
	public void updateList(String smlList, char smcFloor, Map<String, String> checklistMap) {
		//log.info("안전관리 서비스 실행");
		//log.info("대상 : {}", smlList);
		//log.info("층 : {}", smcFloor);

		// char를 int로 변환 (예: '1' -> 1)
		int floorAsInt = Character.getNumericValue(smcFloor);

		// 층별 데이터
		List<SafetyManagementChecklist> bySmcFloor = safetyManagementChecklistRepository.findBySmcFloor(floorAsInt);

		//log.info("서비스단 : 층별 데이터 {} ", bySmcFloor.toString());

		List<SafetyManagementChecklistDTO> filteredChecklists = bySmcFloor.stream().distinct()
				.filter(list -> list.getSmlList().equals(smlList))
				.map(list -> modelMapper.map(list, SafetyManagementChecklistDTO.class)).collect(Collectors.toList());

		//log.info("서비스단 : 점검사항별 층별 데이터 {}", filteredChecklists);

		// checklistMap의 key와 value를 리스트화
		List<String> keyList = new ArrayList<>(checklistMap.keySet());
		List<String> valueList = new ArrayList<String>(checklistMap.values());

		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);

			// 해당 smcNo의 엔티티를 찾아서 업데이트
			filteredChecklists.stream().filter(checklist -> Integer.toString(checklist.getSmcNo()).equals(key))
					.forEach(checklist -> { checklist.setSmcList(value);
						//log.info("서비스 단 : 수정된 checklist {} ", checklist);
					});

		}
		//log.info("서비스 단 : 수정된 filteredChecklists {} ", filteredChecklists);

		for (SafetyManagementChecklistDTO checklistDTO : filteredChecklists) {
			safetyManagementChecklistRepository
			.updateSmcList(checklistDTO.getSmcNo(), checklistDTO.getSmlNo(), 
					checklistDTO.getSmcFloor(), checklistDTO.getSmcList()
			    );
		}
	}

// 등록 수정 삭제 시 @Transactional 설정 필요
}
