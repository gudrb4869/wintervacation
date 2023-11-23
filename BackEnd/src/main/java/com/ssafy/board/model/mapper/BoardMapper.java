package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;

@Mapper
public interface BoardMapper {

	void writeArticle(BoardDto boardDto) throws SQLException;
	
	// 정렬기준 : 최신순
	List<BoardDto> boardList(Map<String, String> map) throws SQLException;
	// 정렬기준 : 조회수
	List<BoardDto> boardList2(Map<String, String> map) throws SQLException;
	
	// 게시글 개수
	int getTotalBoardCount(Map<String, Object> param) throws SQLException;
	
	BoardDto viewArticle(int articleNo) throws SQLException;
	
	void updateHit(int articleNo) throws SQLException;
	
	int modifyArticle(BoardDto boardDto) throws SQLException; // 글 수정
	
	int deleteArticle(int articleNo) throws SQLException; // 글 삭제
	
	List<BoardDto> searchArticle(String option,String search) throws SQLException; // 검색 
}
