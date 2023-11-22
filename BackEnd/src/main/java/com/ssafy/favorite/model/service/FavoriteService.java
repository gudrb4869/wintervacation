package com.ssafy.favorite.model.service;

import java.util.List;

import com.ssafy.favorite.model.FavoriteDto;
import com.ssafy.map.model.AttractionDto;

public interface FavoriteService {
	
	List<AttractionDto> listFavoriteAttraction(String user_id) throws Exception;
	
	void registFavorite(FavoriteDto favoriteDto) throws Exception;
	
	int getFavorite(FavoriteDto favoriteDto) throws Exception;
	
	void deleteFavorite(FavoriteDto favoriteDto) throws Exception;

}
