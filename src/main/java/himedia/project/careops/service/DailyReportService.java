package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-26 ~ 
 */

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.entity.DailyManagementReport;
import himedia.project.careops.repository.DailyManagementReportRepository;

@Service
public class DailyReportService {

	private final DailyManagementReportRepository dailyManagementReportRepository;
	private final ModelMapper modelMapper;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public DailyReportService(DailyManagementReportRepository dailyManagementReportRepository,
			ModelMapper modelMapper) {
		this.dailyManagementReportRepository = dailyManagementReportRepository;
		this.modelMapper = modelMapper;
	}

	public Page<DailyManagementReportDTO> reportAllList(Pageable pageable) {
		
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1,
				pageable.getPageSize(),
				Sort.by("dmrNo").descending());
		
		Page<DailyManagementReport> allList = dailyManagementReportRepository.findAll(pageable);
		
		return allList.map(reportList -> modelMapper.map(reportList, DailyManagementReportDTO.class));
	}

	public Optional<DailyManagementReport> findByReportNo(int dmrNo) {
		Optional<DailyManagementReport> findByDmrNo = dailyManagementReportRepository.findById(dmrNo);
		return findByDmrNo;
	}



	
	// 등록 수정 삭제 시 @Transactional 설정 필요
}
