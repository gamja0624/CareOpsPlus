package himedia.project.careops.service;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

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
	
	// [의료기기 장비세분류코드 찾기]
	public ListMedicalDevicesDTO findByMedicalLmdMinorCateCode(String lmdMinorCateCode) {
		
		ListMedicalDevices medicalDevice = medicalRepository.findById(lmdMinorCateCode).orElseThrow(IllegalArgumentException::new);
		// log.info("medicalDevice : {}", medicalDevice);
		
		return modelMapper.map(medicalDevice, ListMedicalDevicesDTO.class);
	}
	
	// [목록 + 페이지네이션]
	public Page<ListMedicalDevicesDTO> findByMedicalDevices(Pageable pageable) {
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("lmdMinorCateCode").ascending());
		
		Page<ListMedicalDevices> medicalDevicesList = medicalRepository.findAll(pageable);
		// log.info("조회된 엔티티 데이터: {}", medicalDevicesList.getContent());
		
		return medicalDevicesList.map(medical -> modelMapper.map(medical, ListMedicalDevicesDTO.class));
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
