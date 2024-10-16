package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-10-15 
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

@Service
public class SafetyService {

	private final SafetyManagementRepository safetyManagementRepository;
	private final ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public SafetyService(SafetyManagementRepository safetyManagementRepository, ModelMapper modelMapper) {
		this.safetyManagementRepository = safetyManagementRepository;
		this.modelMapper = modelMapper;
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
	public List<SafetyManagementDTO> safetyListAll() {

		List<SafetyManagement> safetyListAll = safetyManagementRepository.findAll();

		return safetyListAll.stream().distinct()
				.map(safetyManagementList -> modelMapper.map(safetyManagementList, SafetyManagementDTO.class))
				.collect(Collectors.toList());
	}

	// 전체 조회중 1층의 소화기인 리스트
	public List<SafetyManagementDTO> safetyListFirstPage() {

		List<SafetyManagementDTO> safetyListAll = safetyListAll();

		return safetyListAll.stream().filter(f -> f.getSmFacilityFloor() == 1 && f.getSmList().equals("소화기"))
				.map(list -> modelMapper.map(list, SafetyManagementDTO.class)).collect(Collectors.toList());
	}

	// smList만 보내는 메소드
	public List<String> findSmList() {
		List<String> smList = safetyListAll().stream().map(SafetyManagementDTO::getSmList) // smList 추출
				.distinct() // 중복 제거
				.collect(Collectors.toList()); // 결과를 리스트로 수집
		return smList;
	}

	public List<SafetyManagementDTO> findStatusList(String smList, int smFacilityFloor) {
		List<SafetyManagement> findbySmListandSmFacilityFloor = safetyManagementRepository
				.findBySmListAndSmFacilityFloor(smList, smFacilityFloor);
		return findbySmListandSmFacilityFloor.stream().map(list -> modelMapper.map(list, SafetyManagementDTO.class))
				.collect(Collectors.toList());
	}

	/*
	 * // SafetyManagementChecklist테이블 전체조회 메소드 + 페이지네이션 public
	 * List<SafetyManagementDTO> findAllChecklist(String smlList) {
	 * 
	 * List<SafetyManagement> allList =
	 * safetyManagementRepository.findBySmlList(smlList); return
	 * allList.stream().distinct() .map(checklist -> modelMapper.map(checklist,
	 * SafetyManagementChecklistDTO.class)) .collect(Collectors.toList()); }
	 * 
	 * // 층별 체크리스트 조회 public List<SafetyManagementDTO> findCheckList(String smlList,
	 * int smcFloor) {
	 * 
	 * List<SafetyManagement> bySmcFloor =
	 * SafetyManagementRepository.findBySmcFloor(smcFloor);
	 * 
	 * return bySmcFloor.stream().distinct().filter(list ->
	 * list.getSmlList().equals(smlList)) .map(list -> modelMapper.map(list,
	 * SafetyManagementDTO.class)).collect(Collectors.toList()); }
	 */

	// 점검표 세부 항목(smcList) 수정
	/*
	 * @Transactional public void updateList(String smlList, char smcFloor,
	 * Map<String, String> checklistMap) { //log.info("안전관리 서비스 실행");
	 * //log.info("대상 : {}", smlList); //log.info("층 : {}", smcFloor);
	 * 
	 * // char를 int로 변환 (예: '1' -> 1) int floorAsInt =
	 * Character.getNumericValue(smcFloor);
	 * 
	 * // 층별 데이터 List<SafetyManagement> bySmcFloor =
	 * safetyManagementRepository.findBySmcFloor(floorAsInt);
	 * 
	 * //log.info("서비스단 : 층별 데이터 {} ", bySmcFloor.toString());
	 * 
	 * List<SafetyManagementDTO> filteredChecklists = bySmcFloor.stream().distinct()
	 * .filter(list -> list.getSmlList().equals(smlList)) .map(list ->
	 * modelMapper.map(list,
	 * SafetyManagementDTO.class)).collect(Collectors.toList());
	 * 
	 * //log.info("서비스단 : 점검사항별 층별 데이터 {}", filteredChecklists);
	 * 
	 * // checklistMap의 key와 value를 리스트화 List<String> keyList = new
	 * ArrayList<>(checklistMap.keySet()); List<String> valueList = new
	 * ArrayList<String>(checklistMap.values());
	 * 
	 * for (int i = 0; i < keyList.size(); i++) { String key = keyList.get(i);
	 * String value = valueList.get(i);
	 * 
	 * // 해당 smcNo의 엔티티를 찾아서 업데이트 filteredChecklists.stream().filter(checklist ->
	 * Integer.toString(checklist.getSmcNo()).equals(key)) .forEach(checklist -> {
	 * checklist.setSmcList(value); //log.info("서비스 단 : 수정된 checklist {} ",
	 * checklist); });
	 * 
	 * } //log.info("서비스 단 : 수정된 filteredChecklists {} ", filteredChecklists);
	 * 
	 * for (SafetyManagementDTO checklistDTO : filteredChecklists) {
	 * safetyManagementRepository .updateSmcList(checklistDTO.getSmcNo(),
	 * checklistDTO.getSmlNo(), checklistDTO.getSmcFloor(),
	 * checklistDTO.getSmcList() ); } }
	 */

// 등록 수정 삭제 시 @Transactional 설정 필요
}
