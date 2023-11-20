package com.ssafy.board.model;

import java.util.List;

import com.ssafy.file.model.FileDto;


public class BoardDto {

	private int article_no;
	private String user_id;
	private String subject;
	private String content;
	private int hit;
	private String register_time;
	private List<FileDto> fileInfos;
	

	public int getArticle_no() {
		return article_no;
	}


	public void setArticle_no(int article_no) {
		this.article_no = article_no;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getHit() {
		return hit;
	}


	public void setHit(int hit) {
		this.hit = hit;
	}


	public String getRegister_time() {
		return register_time;
	}


	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}
	
	public List<FileDto> getFileInfos() {
		return fileInfos;
	}


	public void setFileInfos(List<FileDto> fileInfos) {
		this.fileInfos = fileInfos;
	}


	@Override
	public String toString() {
		return "BoardDto [article_no=" + article_no + ", user_id=" + user_id + ", subject=" + subject + ", content="
				+ content + ", hit=" + hit + ", register_time=" + register_time + ", fileInfos=" + fileInfos + "]";
	}



}
