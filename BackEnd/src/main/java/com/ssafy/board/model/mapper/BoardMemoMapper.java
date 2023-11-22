package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.BoardMemoDto;

public interface BoardMemoMapper {
	// 전체 댓글
	List<BoardMemoDto> MemoList(int article_no) throws SQLException;
	// 댓글 작성
	int writeMemo(BoardMemoDto MemoDto) throws SQLException;
	// 댓글 삭제
	int deleteMemo(int memo_no) throws SQLException;
	
	int deleteMemoAll(int article_no) throws SQLException;
}
