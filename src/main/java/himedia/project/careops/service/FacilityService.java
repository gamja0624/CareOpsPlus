package himedia.project.careops.service;

/**
 * @author 진혜정
 * @editDate 2024-10-03
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

import himedia.project.careops.dto.FacilityDTO;
import himedia.project.careops.entity.Facility;
import himedia.project.careops.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FacilityService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final FacilityRepository facilityRepository;
	private final ModelMapper modelMapper;
	
	public FacilityService(FacilityRepository facilityRepository, ModelMapper modelMapper) {
		this.facilityRepository = facilityRepository;
		this.modelMapper = modelMapper;
	}
	
	// [목록 + 페이지네이션]
	public Page<FacilityDTO> findByFacilityList(Pageable pageable) {
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("facilityNo").ascending());
		
		Page<Facility> facilityList = facilityRepository.findAll(pageable);
		
		return facilityList.map(facility -> modelMapper.map(facility, FacilityDTO.class));
	}
	
	// [부서 이름으로 예약된 시설 리스트 반환]
	public List<FacilityDTO> findByDeptNoFacilityList(String department) {

	    // 모든 시설 리스트를 가져옴
	    List<Facility> facilityList = facilityRepository.findAll();

	    // 필터링하여 DTO로 변환
	    List<FacilityDTO> filteredDtoList = facilityList
	    		.stream()
	            .filter(f -> department.equals(f.getFacilityManagerDeptName()))
	            .map(facility -> modelMapper.map(facility, FacilityDTO.class))
	            .collect(Collectors.toList());

	    return filteredDtoList;
	}
	
	// [전체 시설 객체로 리스트로 반환]
	public List<FacilityDTO> findAllDepartmentsList() {
    	
        List<Facility> facilitys = facilityRepository.findAll();
        return facilitys.stream()
                         .map(f -> modelMapper.map(f, FacilityDTO.class))
                         .collect(Collectors.toList());
    }
	
	// [층수로 시설 이름, 시설 번호 리스트로 반환]
	public List<Facility> findByFloorFacilityList(Integer facilityFloor) {
		
		return facilityRepository.findAll()
				.stream()
				.filter(f -> {
					// 층수 + 현재 예약된 시설 제외
					if (f.getFacilityFloor() == facilityFloor && ! f.isFacilityReservationStatus()) {
						return f.getFacilityFloor() == facilityFloor;
					}
					return false;
				})
				.collect(Collectors.toList()); 
	} 

	// [검색 필터]
	public List<Facility> findFilterfacilityList(String filter, String value) {

		List<Facility> facilityList = facilityRepository.findAll();
		return facilityList.stream()
				.filter(m -> {
					if (value != "") {
						if (filter.equals("facilityFloor")) { // 층별
							int floor = Integer.parseInt(value); // String -> int 변환
							return m.getFacilityFloor() == floor;
						} else if (filter.equals("facilityName")) { // 시설물 이름
							return m.getFacilityName().contains(value);
						} else if (filter.equals("facilityManagerDeptName")) { // 예약 부서
							if (m.getFacilityManagerDeptName() != null) {
								return m.getFacilityManagerDeptName().contains(value);
							}
						} 
					}
					return false;
					}).collect(Collectors.toList());
	}
	
	// [시설 번호로 객체 찾기]
	public Facility findByFacilityNo(String facilityNo) {
		
		Integer facility = Integer.parseInt(facilityNo);
		
		return facilityRepository.findById(facility)
				.orElseThrow(IllegalArgumentException::new);
	}

	// [예약 신청(수정)]
	@Transactional
	public void editFacility(FacilityDTO editFacility) {
		
		Facility facility = facilityRepository.findById(editFacility.getFacilityNo())
				.orElseThrow(IllegalArgumentException::new);
		
		// 예약 번호 / 예약 상태 / 예약 날짜
		Integer maxReservationNo = facilityRepository.findMaxReservationNo();
		facility.setFacilityReservationNo(maxReservationNo != null ? maxReservationNo + 1 : 1); // 예약 번호 증가
		facility.setFacilityReservationStatus(true);
		facility.setFacilityReservationDate(editFacility.getFacilityReservationDate());
		
		// 담당자 부서 번호/ 부서 이름 / 아이디 / 이름
		facility.setFacilityManagerDeptNo(editFacility.getFacilityManagerDeptNo());
		facility.setFacilityManagerDeptName(editFacility.getFacilityManagerDeptName());
		facility.setFacilityManagerId(editFacility.getFacilityManagerId());
		facility.setFacilityManagerName(editFacility.getFacilityManagerName());
	}
	
	// [예약 취소(수정)]
	@Transactional
	public void cancelFacility(Facility editFacility) {
		
		Facility facility = facilityRepository.findById(editFacility.getFacilityNo())
				.orElseThrow(IllegalArgumentException::new);
		
		// 예약 상태 / 예약 날짜
		facility.setFacilityReservationStatus(false);
		facility.setFacilityReservationDate(null);
		
		// 담당자 부서 번호/ 부서 이름 / 아이디 / 이름
		facility.setFacilityManagerDeptNo(null);
		facility.setFacilityManagerDeptName(null);
		facility.setFacilityManagerId(null);
		facility.setFacilityManagerName(null);
	}
	
}
