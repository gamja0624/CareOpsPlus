package himedia.project.careops.common;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

import org.springframework.data.domain.Page;

public class Pagenation {

    public static PagingButtonInfo getPagingButtonInfo(Page page) {
        int currentPage = page.getNumber() + 1;	
        int defaultButtonCount = 10; // 보여줄 버튼 개수
        int totalPages = page.getTotalPages();
        int startPage;
        int endPage;

        // 시작 페이지 계산
        startPage = ((currentPage - 1) / defaultButtonCount) * defaultButtonCount + 1;
        endPage = startPage + defaultButtonCount - 1;

        // endPage가 totalPages보다 크면 totalPages로 조정
        if (endPage > totalPages) {
            endPage = totalPages;
        }

        // startPage가 0 이하일 경우 1로 조정
        if (startPage <= 0) {
            startPage = 1;
        }

        return new PagingButtonInfo(currentPage, startPage, endPage);
    }
}

