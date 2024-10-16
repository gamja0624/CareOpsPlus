package himedia.project.careops.service;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.ListMedicalDevicesDTO;
import himedia.project.careops.entity.ListMedicalDevices;
import himedia.project.careops.repository.MedicalRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final MedicalRepository medicalRepository;
	private final ModelMapper modelMapper;
	
	public MedicalService(MedicalRepository medicalRepository, ModelMapper modelMapper) {
		this.medicalRepository = medicalRepository;
		this.modelMapper = modelMapper;
	}
	
	// [목록 + 페이지네이션]
	public Page<ListMedicalDevicesDTO> findByMedicalDevices(Pageable pageable) {
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("lmdMinorCateCode").ascending());
		
		return medicalRepository.findAll(pageable)
				.map(medical -> modelMapper.map(medical, ListMedicalDevicesDTO.class));
	}
	
	// [검색 필터]
	public List<ListMedicalDevices> findFilterMedicalDevices(String filter, String value) {
        if (value == null || value.isEmpty()) {
        	return List.of();
        } 

        return medicalRepository.findAll()
				        		.stream()
				                .filter(m -> filterMatches(m, filter, value))
				                .collect(Collectors.toList());
    }

    private boolean filterMatches(ListMedicalDevices m, String filter, String value) {
        switch (filter) {
        	// 장비대분류명
            case "lmdMajorCateName":
                return m.getLmdMajorCateName().contains(value);
            // 장비세분류명
            case "lmdMinorCateName": 
                return m.getLmdMinorCateName().contains(value);
            // 모델명
            case "lmdDevicesName":  
                return m.getLmdDevicesName().contains(value);
            // 부서명
            case "lmdManagerDeptPart": 
                return m.getLmdManagerDeptPart().contains(value);
            default:
                return false;
        }
    }
	
	// [의료기기 장비세분류코드 찾기]
	public ListMedicalDevicesDTO findByMedicalLmdMinorCateCode(String lmdMinorCateCode) {
		
		ListMedicalDevices medicalDevice = medicalRepository.findById(lmdMinorCateCode)
				.orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(medicalDevice, ListMedicalDevicesDTO.class);
	}
	
	// [담당 부서 이름으로 의료기기 리스트 반환]
	public List<ListMedicalDevices> findByMedicalDeptName(String department) {
		return medicalRepository.findAll()
								.stream() 
								.filter(m -> m.getLmdManagerDeptPart().equals(department))
								.collect(Collectors.toList());     
	}
	
	// [의료기기 상태에 따라 리스트 반환]
	public Map<String, Integer> findByMedicalStatus() {

		// 키, 값 형태로 저장할 변수 선언
		Map<String, Integer> medicalStatusList = new HashMap<>();
		
		// 변수
		Integer normal = 0;
		Integer check = 0;
		Integer repair = 0;
		Integer old = 0;
		Integer expire = 0;
		
		// 전체 의료기기
		List<ListMedicalDevices> medicalList = medicalRepository.findAll();
		
		for (ListMedicalDevices m: medicalList) {
			if (m.getLmdStatus().equals("정상")) {
				normal ++;
			} else if (m.getLmdStatus().equals("점검요망")) {
				check ++;
			} else if (m.getLmdStatus().equals("수리필요")) {
				repair ++;
			} else if (m.getLmdStatus().equals("노후")) {
				old ++;
			} else if (m.getLmdStatus().equals("만료")) {
				expire ++;
			} 
			
			medicalStatusList.put("normal", normal);
			medicalStatusList.put("check", check);
			medicalStatusList.put("repair", repair);
			medicalStatusList.put("old", old);
			medicalStatusList.put("expire", expire);
		}
		
		return medicalStatusList;
	}
	
	// [등록]
	@Transactional
	public void addMedicalDevice(ListMedicalDevicesDTO newMedicalDevice) {
		
		medicalRepository.save(modelMapper.map(newMedicalDevice, ListMedicalDevices.class));
		
	}
	
	// [수정]
	@Transactional
	public void editMedicalDevice(ListMedicalDevicesDTO editMedical,
			// 상태, 장비수
			@Param("lmdStatus") String lmdStatus, @Param("lmdDeviceCnt") String lmdDeviceCnt, 
			// 매니저 부서 번호, 부서 이름
			@Param("lmdManagerDeptNo") String lmdManagerDeptNo, @Param("lmdManagerDeptPart") String lmdManagerDeptPart, 
			// 매니저 아이디, 이름
			@Param("lmdManagerId") String lmdManagerId, @Param("lmdManagerName") String lmdManagerName,
			// 작업자 부서 번호, 아이디
			@Param("lmdAdminDeptNo") String lmdAdminDeptNo, @Param("lmdAdminId") String lmdAdminId, 
			// 작업자 이름, 점검일
			@Param("lmdAdminName") String lmdAdminName, java.util.Date lmdLastCheckDate) {
		
		ListMedicalDevices findMedical = medicalRepository.findById(editMedical.getLmdMinorCateCode())
				.orElseThrow(IllegalArgumentException::new);
		
		// 상태, 장비수
		findMedical.setLmdStatus(lmdStatus);
		findMedical.setLmdDeviceCnt(Integer.parseInt(lmdDeviceCnt));
		
		// 매니저 부서 번호, 부서 이름, 아이디, 이름
		findMedical.setLmdManagerDeptNo(Integer.parseInt(lmdManagerDeptNo));
		findMedical.setLmdManagerDeptPart(lmdManagerDeptPart);
		findMedical.setLmdManagerId(lmdManagerId);
		findMedical.setLmdManagerName(lmdManagerName);
		
		// 작업자 부서 번호, 아이디, 작업자 이름, 점검일
		findMedical.setLmdAdminDeptNo(lmdAdminDeptNo);
		findMedical.setLmdAdminId(lmdAdminId);
		findMedical.setLmdAdminName(lmdAdminName);
		findMedical.setLmdLastCheckDate(new java.sql.Date(lmdLastCheckDate.getTime()));
		
	}
}
