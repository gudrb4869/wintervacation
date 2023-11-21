package com.ssafy.favorite.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FavoriteDto {

	private String user_id;
	private int content_id;
	private String favorite_date;
	
}
