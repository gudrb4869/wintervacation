package com.ssafy.file.model;

public class FileDto {
	
	private int index;
	private int article_no;
	private String user_id;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
	
	public String getSaveFolder() {
		return saveFolder;
	}
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
	public String getOriginalFile() {
		return originalFile;
	}
	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
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
	@Override
	public String toString() {
		return "FileDto [index=" + index + ", article_no=" + article_no + ", user_id=" + user_id + ", saveFolder="
				+ saveFolder + ", originalFile=" + originalFile + ", saveFile=" + saveFile + "]";
	}

	
}
