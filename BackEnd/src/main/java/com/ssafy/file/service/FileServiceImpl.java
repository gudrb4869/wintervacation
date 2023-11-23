package com.ssafy.file.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.file.model.FileDto;
import com.ssafy.file.model.mapper.FileMapper;

@Service
public class FileServiceImpl implements FileService {

	private FileMapper fileMapper;
	
	@Autowired
	public FileServiceImpl(FileMapper fileMapper) {
		super();
		this.fileMapper = fileMapper;
	}
	
	@Override
	public List<FileDto> fileInfoList(int article_no) throws Exception{
		return fileMapper.fileInfoList(article_no);
	}

	@Override
	public void registerProfile(FileDto fileDto) throws Exception {
		fileMapper.registerProfile(fileDto);
	}

	@Override
	public FileDto getProfileFile(String user_id) throws Exception {
		return fileMapper.getProfileFile(user_id);
	}

}
