package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;

/** 게시판 기능을 위한 서비스 기능 선언 */
public interface BoardService {
	
	void writeArticle(BoardDto boardDto) throws Exception;
//  페이징 처리가 필요할때 이런식으로 사용
//	BoardListDto boardList(Map<String, String> map) throws Exception;
	List<BoardDto> boardList(Map<String, String> map) throws Exception;
	List<BoardDto> myBoardList(Map<String, String> map) throws Exception;
	BoardDto viewArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;

	int modifyArticle(BoardDto boardDto) throws Exception; // 글 수정 
	int deleteArticle(int articleNo) throws Exception; // 글 삭제
	
}
