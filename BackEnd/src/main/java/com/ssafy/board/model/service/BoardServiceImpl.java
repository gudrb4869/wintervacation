package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.file.model.FileDto;
import com.ssafy.file.model.mapper.FileMapper;

@Service
public class BoardServiceImpl implements BoardService {

	BoardMapper boardMapper;
	FileMapper fileMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper, FileMapper fileMapper) {
		super();
		this.boardMapper = boardMapper;
		this.fileMapper = fileMapper;
	}
	
	@Override
	public void writeArticle(BoardDto boardDto) throws Exception {
		System.out.println("글입력 전 dto : " + boardDto);
		boardMapper.writeArticle(boardDto);
		System.out.println("글입력 후 dto : " + boardDto);
		List<FileDto> fileInfos = boardDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			fileMapper.registerFile(boardDto);
		}
	}
	
	@Override
	public List<BoardDto> boardList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		
		List<BoardDto> boards = boardMapper.boardList(map);
		
		return boards;
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
