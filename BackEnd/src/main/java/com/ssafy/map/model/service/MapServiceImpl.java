package com.ssafy.map.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoGugunCodeDto;
import com.ssafy.map.model.mapper.MapMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService{

	private final MapMapper mapMapper;
	
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return mapMapper.getSido();
	}
	
	@Override
	public List<SidoGugunCodeDto> getGugun(int sidoCode) throws Exception {
		return mapMapper.getGugun(sidoCode);
	}
	
	@Override
	public List<AttractionDto> getAttractions(Map<String, Integer> map) throws Exception {
		return mapMapper.getAttractions(map);
	}

}
