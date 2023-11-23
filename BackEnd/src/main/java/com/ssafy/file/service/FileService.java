package com.ssafy.file.service;

import java.util.List;

import com.ssafy.file.model.FileDto;

public interface FileService {
	List<FileDto> fileInfoList(int article_no) throws Exception;
	void registerProfile(FileDto fileDto) throws Exception;
	FileDto getProfileFile(String user_id) throws Exception;
}
