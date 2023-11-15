package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	@Override
	public int writeArticle(BoardDto boardDto) throws Exception {
		return boardMapper.writeArticle(boardDto);
	}

	@Override
	public BoardListDto boardList(Map<String, String> map) throws Exception {
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
			param.put("key", key == null ? "" : "user_id");
		
		
		List<BoardDto> boards = boardMapper.boardList(map);
		int totalBoardCount = boardMapper.getTotalBoardCount(param);
		int totalPageCount = (totalBoardCount - 1) / sizePerPage + 1;
		
		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setBoards(boards);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);
		
		
		return boardListDto;
	}

	@Override
	public BoardDto viewArticle(int articleNo) throws Exception {
		return boardMapper.viewArticle(articleNo);
	}

	@Override
	public int modifyArticle(BoardDto boardDto) throws Exception {
		return boardMapper.modifyArticle(boardDto);
	}

	@Override
	public int deleteArticle(int articleNo) throws Exception {
		return boardMapper.deleteArticle(articleNo);
	}


	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
		
	}

}
