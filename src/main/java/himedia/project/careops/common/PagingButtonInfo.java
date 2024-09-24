package himedia.project.careops.common;

/**
 * @author 진혜정 
 * @editDate 2024-09-23
 */

public class PagingButtonInfo {
	
	private int currentPage;
	private int startPage;
	private int endPage;
	
	public PagingButtonInfo() {}

	public PagingButtonInfo(int currentPage, int startPage, int endPage) {
		super();
		this.currentPage = currentPage;
		this.startPage = startPage;
		this.endPage = endPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "PagingButtonInfo [currentPage=" + currentPage + ", startPage=" + startPage 
				+ ", endPage=" + endPage + "]";
	}
	
}