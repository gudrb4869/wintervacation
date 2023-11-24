package com.ssafy.file.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.file.model.FileDto;

@Mapper
public interface FileMapper {
	void registerFile(BoardDto boardDto) throws Exception;
	void registerProfile(FileDto fileDto) throws Exception;
	FileDto getProfileFile(String user_id) throws Exception;
	List<FileDto> fileInfoList(int article_no) throws Exception;
	int deleteFile(int article_no) throws Exception;
	int deleteFile2(String user_id) throws Exception;
}
