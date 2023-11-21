package com.ssafy.file.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.file.model.FileDto;

@Mapper
public interface FileMapper {
	void registerFile(BoardDto boardDto) throws Exception;
	List<FileDto> fileInfoList(int article_no) throws Exception;
}
