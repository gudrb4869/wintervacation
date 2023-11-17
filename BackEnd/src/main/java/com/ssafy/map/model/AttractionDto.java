package com.ssafy.map.model;
import java.math.BigDecimal;

public class AttractionDto {
	
	private int content_id;
	private int content_type_id;
	private String title;
	private String addr;
	private String image;
	private int sido_code;
	private int gugun_code;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String overview;
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public int getContent_type_id() {
		return content_type_id;
	}
	public void setContent_type_id(int content_type_id) {
		this.content_type_id = content_type_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSido_code() {
		return sido_code;
	}
	public void setSido_code(int sido_code) {
		this.sido_code = sido_code;
	}
	public int getGugun_code() {
		return gugun_code;
	}
	public void setGugun_code(int gugun_code) {
		this.gugun_code = gugun_code;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	@Override
	public String toString() {
		return "AttractionDto [content_id=" + content_id + ", content_type_id=" + content_type_id + ", title=" + title
				+ ", addr=" + addr + ", image=" + image + ", sido_code=" + sido_code + ", gugun_code=" + gugun_code
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", overview=" + overview + "]";
	}
	
}