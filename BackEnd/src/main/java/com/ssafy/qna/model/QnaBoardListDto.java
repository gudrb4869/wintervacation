package com.ssafy.qna.model;

import java.util.List;

public class QnaBoardListDto {
	
	private List<QnaBoardDto> qnaBoards;
	private int currentPage;
	private int totalPageCount;
	public List<QnaBoardDto> getQnaBoards() {
		return qnaBoards;
	}
	public void setQnaBoards(List<QnaBoardDto> qnaBoards) {
		this.qnaBoards = qnaBoards;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

}
