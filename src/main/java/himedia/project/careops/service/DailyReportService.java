package himedia.project.careops.service;

/**
 * @author 이홍준
 * @editDate 2024-09-26 ~ 
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

import himedia.project.careops.dto.DailyManagementReportDTO;
import himedia.project.careops.entity.DailyManagementReport;
import himedia.project.careops.repository.DailyManagementReportRepository;
import jakarta.transaction.Transactional;

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
				Sort.by("dmrNo").descending()
				);
		
		Page<DailyManagementReport> allList = dailyManagementReportRepository.findAll(pageable);
		
		return allList.map(reportList -> modelMapper.map(reportList, DailyManagementReportDTO.class));
	}

	// [1개 데이터 조회]
	public DailyManagementReportDTO findByReportNo(int dmrNo) {
		
		DailyManagementReport resultReport = dailyManagementReportRepository.findById(dmrNo).orElseThrow(IllegalArgumentException::new);
		
		return modelMapper.map(resultReport, DailyManagementReportDTO.class);
	}

	// [수정] 수정목록 : adminId, adminName, dmrReportDetail, DmrIssue
	@Transactional
	public void editReportDetail(int dmrNo, String adminId, String adminName, DailyManagementReportDTO editReport) {
		
		DailyManagementReport beforeReport = dailyManagementReportRepository.findById(dmrNo).get();
		
		beforeReport.setDmrReportDetail(editReport.getDmrReportDetail());
		beforeReport.setAdminId(adminId);
		beforeReport.setAdminName(adminName);
		beforeReport.setDmrIssue(editReport.getDmrIssue());
	}
	
	// [등록] 
	@Transactional
	public void reportRegistation(String adminid, String adminName, String adminDeptNo, String adminDeptName, DailyManagementReportDTO newReport) {
		
		newReport.setAdminId(adminid);
		newReport.setAdminDeptNo(adminDeptNo);
		newReport.setAdminName(adminName);
		newReport.setAdminDeptName(adminDeptName);
		
		dailyManagementReportRepository.save(modelMapper.map(newReport, DailyManagementReport.class));
	}

	// [adminName별 조회 + 페이지네이션]
	public Page<DailyManagementReportDTO> searchMyReport(Pageable pageable, String adminName) {

	    pageable = PageRequest.of(
	        pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
	        pageable.getPageSize(),
	        Sort.by("dmrNo").descending()
	        );

	    // Pageable을 사용하여 데이터를 가져옴
	    Page<DailyManagementReport> allList = dailyManagementReportRepository.findByAdminName(adminName ,pageable);

	    return allList.map(list -> modelMapper.map(list, DailyManagementReportDTO.class));
	}
	// 등록 수정 삭제 시 @Transactional 설정 필요

	// 목록뷰 검색(카테고리 : dmrNo, adminDeptName, adminName, dmrDate)
	/*
	 * public Page<DailyManagementReportDTO> reportSearch(String filter, String
	 * value, Pageable pageable) {
	 * 
	 * pageable = PageRequest.of( pageable.getPageNumber() <= 0 ? 0 :
	 * pageable.getPageNumber() - 1, pageable.getPageSize(),
	 * Sort.by("dmrNo").descending() );
	 * 
	 * switch (value) { case "dmrNo": { Integer searchId = Integer.parseInt(value);
	 * Page<DailyManagementReport> findById =
	 * dailyManagementReportRepository.findAllById(searchId, pageable); return
	 * findById.map(list -> modelMapper.map(list, DailyManagementReportDTO.class));
	 * } default: throw new IllegalArgumentException("해당 검색어는 존재하지 않습니다. 검색어 : " +
	 * value); } }
	 */
	
	// 작성자 : 진혜정
	// [전체 보고서 List 로 반환]
	public List<DailyManagementReport> findBydailyReportList() {
		
		return dailyManagementReportRepository.findAll()									
				.stream() 
				.collect(Collectors.toList());
	}
}
