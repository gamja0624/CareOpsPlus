package himedia.project.careops.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 진혜정
 * @editDate 2024-10-03
 */

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

	// [검색 필터]
	public List<Facility> findFilterfacilityList(String filter, String value) {

		List<Facility> facilityList = facilityRepository.findAll();
		return facilityList.stream()
				.filter(m -> {
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
					return false;
					}).collect(Collectors.toList());
	}
	
}
