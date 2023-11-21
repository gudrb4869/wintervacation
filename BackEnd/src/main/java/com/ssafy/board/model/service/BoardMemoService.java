package com.ssafy.board.model.service;

import java.util.List;

import com.ssafy.board.model.BoardMemoDto;


public interface BoardMemoService {
	// 전체 댓글
	List<BoardMemoDto> MemoList(int article_no) throws Exception;
	// 댓글 작성
	int writeMemo(BoardMemoDto MemoDto) throws Exception;
	// 댓글 삭제
	int deleteMemo(int memo_no) throws Exception;
}
