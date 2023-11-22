package com.ssafy.map.model;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
	private String favorite_date;
	
}