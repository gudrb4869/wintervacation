package com.ssafy.favorite.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.favorite.model.FavoriteDto;
import com.ssafy.favorite.model.mapper.FavoriteMapper;
import com.ssafy.map.model.AttractionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	
	private final FavoriteMapper favoriteMapper;

	@Override
	public List<AttractionDto> listFavoriteAttraction(String user_id) throws Exception {
		return favoriteMapper.listFavoriteAttraction(user_id);
	}

	@Override
	public void registFavorite(FavoriteDto favoriteDto) throws Exception {
		favoriteMapper.registFavorite(favoriteDto);
	}

	@Override
	public int getFavorite(FavoriteDto favoriteDto) throws Exception {
		return favoriteMapper.getFavorite(favoriteDto);
	}

	@Override
	public void deleteFavorite(FavoriteDto favoriteDto) throws Exception {
		favoriteMapper.deleteFavorite(favoriteDto);
	}

}
