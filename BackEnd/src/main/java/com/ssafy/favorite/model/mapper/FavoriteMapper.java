package com.ssafy.favorite.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.favorite.model.FavoriteDto;
import com.ssafy.map.model.AttractionDto;

@Mapper
public interface FavoriteMapper {
	
	List<AttractionDto> listFavoriteAttraction(String user_id) throws SQLException;
	
	void registFavorite(FavoriteDto favoriteDto) throws SQLException;
	
	int getFavorite(FavoriteDto favoriteDto) throws SQLException;
	
	void deleteFavorite(FavoriteDto favoriteDto) throws SQLException;

}
