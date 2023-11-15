package com.ssafy.qna.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.qna.model.QnaBoardDto;
import com.ssafy.qna.model.QnaBoardListDto;
import com.ssafy.qna.model.QnaMemoDto;

public interface QnaService {
	
	// QnaBoard
	// 작성
	int writeQna(QnaBoardDto qnaBoardDto) throws Exception;
	// 목록
	QnaBoardListDto qnaBoardList(Map<String, String> map) throws Exception;
	// 상세보기
	QnaBoardDto viewQnaBoard(int article_no) throws Exception;
	// 조회수 증가
	void updateHit(int article_no) throws Exception;
	// 수정
	int modifyQna(QnaBoardDto qnaBoardDto) throws Exception;
	// 삭제
	int deleteQna(int article_no) throws Exception;
	
	
	// Qnamemo(댓글)
	// 전체 댓글
	List<QnaMemoDto> qnaMemoList(int article_no) throws Exception;
	// 댓글 작성
	int writeQnaMemo(QnaMemoDto qnaMemoDto) throws Exception;
	// 댓글 삭제
	int deleteQnaMemo(int memo_no) throws Exception;
	
}
