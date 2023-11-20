package com.ssafy.file.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;

@Mapper
public interface FileMapper {
	void registerFile(BoardDto boardDto) throws Exception;
}
