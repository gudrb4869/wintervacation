package com.ssafy.qna.model;

public class QnaMemoDto {
	private int memo_no;
	private String user_id;
	private String comment;
	private String memo_time;
	private int article_no;
	
	public int getMemo_no() {
		return memo_no;
	}
	public void setMemo_no(int memo_no) {
		this.memo_no = memo_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMemo_time() {
		return memo_time;
	}
	public void setMemo_time(String memo_time) {
		this.memo_time = memo_time;
	}
	public int getArticle_no() {
		return article_no;
	}
	public void setArticle_no(int article_no) {
		this.article_no = article_no;
	}
	@Override
	public String toString() {
		return "QnaMemoDto [memo_no=" + memo_no + ", user_id=" + user_id + ", comment=" + comment + ", memo_time="
				+ memo_time + ", article_no=" + article_no + "]";
	}
	
	
}
