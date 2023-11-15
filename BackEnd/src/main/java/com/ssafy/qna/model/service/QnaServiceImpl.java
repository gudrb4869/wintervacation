package com.ssafy.qna.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.qna.model.QnaBoardDto;
import com.ssafy.qna.model.QnaBoardListDto;
import com.ssafy.qna.model.QnaMemoDto;
import com.ssafy.qna.model.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService {

	private QnaMapper qnaMapper;
	
	public QnaServiceImpl(QnaMapper qnaMapper) {
		super();
		this.qnaMapper = qnaMapper;
	}
	
	@Override
	public int writeQna(QnaBoardDto qnaBoardDto) throws Exception {
		return qnaMapper.writeQna(qnaBoardDto);
	}

	@Override
	public QnaBoardListDto qnaBoardList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id"); // 나중에 member테이블이랑 join할때 "user_id" => "qb.user_id" 이렇게하면될듯
		
		List<QnaBoardDto> qnaBoards = qnaMapper.qnaBoardList(param);
		int totalQnaBoardCount = qnaMapper.getTotalQnaBoardCount(param);
		int totalPageCount = (totalQnaBoardCount - 1) / sizePerPage + 1;
		
		QnaBoardListDto qnaBoardListDto = new QnaBoardListDto();
		qnaBoardListDto.setQnaBoards(qnaBoards);
		qnaBoardListDto.setCurrentPage(currentPage);
		qnaBoardListDto.setTotalPageCount(totalPageCount);
		
		return qnaBoardListDto;
	}

	@Override
	public QnaBoardDto viewQnaBoard(int article_no) throws Exception {
		return qnaMapper.viewQnaBoard(article_no);
	}

	@Override
	public void updateHit(int article_no) throws Exception {
		qnaMapper.updateHit(article_no);
	}

	@Override
	public int modifyQna(QnaBoardDto qnaBoardDto) throws Exception {
		return qnaMapper.modifyQna(qnaBoardDto);
	}

	@Override
	public int deleteQna(int article_no) throws Exception {
		return qnaMapper.deleteQna(article_no);
	}

	@Override
	public List<QnaMemoDto> qnaMemoList(int article_no) throws Exception {
		return qnaMapper.qnaMemoList(article_no);
	}

	@Override
	public int writeQnaMemo(QnaMemoDto qnaMemoDto) throws Exception {
		return qnaMapper.writeQnaMemo(qnaMemoDto);
	}

	@Override
	public int deleteQnaMemo(int memo_no) throws Exception {
		return qnaMapper.deleteQnaMemo(memo_no);
	}

}
