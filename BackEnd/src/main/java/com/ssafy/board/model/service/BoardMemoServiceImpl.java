package com.ssafy.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardMemoDto;
import com.ssafy.board.model.mapper.BoardMemoMapper;

@Service
public class BoardMemoServiceImpl implements BoardMemoService {

	private BoardMemoMapper boardMemoMapper;
	
	public BoardMemoServiceImpl(BoardMemoMapper boardMemoMapper) {
		super();
		this.boardMemoMapper = boardMemoMapper;
	}
	
	@Override
	public List<BoardMemoDto> MemoList(int article_no) throws Exception {
		return boardMemoMapper.MemoList(article_no);
	}

	@Override
	public int writeMemo(BoardMemoDto MemoDto) throws Exception {
		return boardMemoMapper.writeMemo(MemoDto);
	}

	@Override
	public int deleteMemo(int memo_no) throws Exception {
		return boardMemoMapper.deleteMemo(memo_no);
	}

}
