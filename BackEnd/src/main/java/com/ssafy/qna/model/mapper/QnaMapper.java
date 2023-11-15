package com.ssafy.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.qna.model.QnaBoardDto;
import com.ssafy.qna.model.QnaMemoDto;

@Mapper
public interface QnaMapper {

	// QnaBoard
	// 작성
	int writeQna(QnaBoardDto qnaBoardDto) throws SQLException;
	// 목록
	List<QnaBoardDto> qnaBoardList(Map<String, Object> param) throws SQLException;
	// 상세보기
	QnaBoardDto viewQnaBoard(int article_no) throws SQLException;
	// 조회수 증가
	void updateHit(int article_no) throws SQLException;
	// 수정
	int modifyQna(QnaBoardDto qnaBoardDto) throws SQLException;
	// 삭제
	int deleteQna(int article_no) throws SQLException;
	// QnA게시글 개수
	int getTotalQnaBoardCount(Map<String, Object> param) throws SQLException;
	
	
	// Qnamemo(댓글)
	// 전체 댓글
	List<QnaMemoDto> qnaMemoList(int article_no) throws SQLException;
	// 댓글 작성
	int writeQnaMemo(QnaMemoDto qnaMemoDto) throws SQLException;
	// 댓글 삭제
	int deleteQnaMemo(int memo_no) throws SQLException;
	
}
