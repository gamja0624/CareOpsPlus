package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-26 ~ 
 */

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	
	// 등록 수정 삭제 시 @Transactional 설정 필요
}
