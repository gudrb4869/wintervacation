package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;

/** 게시판 기능을 위한 서비스 기능 선언 */
public interface BoardService {
	
	int writeArticle(BoardDto boardDto) throws Exception;
	BoardListDto boardList(Map<String, String> map) throws Exception;
	BoardDto viewArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;

	int modifyArticle(BoardDto boardDto) throws Exception; // 글 수정 
	int deleteArticle(int articleNo) throws Exception; // 글 삭제
	
}
