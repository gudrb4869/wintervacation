package com.ssafy.board.model;

import java.util.List;

public class BoardListDto {
	private List<BoardDto> boards;
	private int currentPage;
	private int totalPageCount;
	
	public List<BoardDto> getBoards() {
		return boards;
	}
	public void setBoards(List<BoardDto> boards) {
		this.boards = boards;
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
