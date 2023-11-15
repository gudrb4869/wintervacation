package com.ssafy.map.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.map.model.mapper.MapMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService{

	private final MapMapper mapMapper;
	
	@Override
	public List<SidoDto> getSido() throws Exception {
		return mapMapper.getSido();
	}
	
	@Override
	public List<GugunDto> getGugun(int sidoCode) throws Exception {
		return mapMapper.getGugun(sidoCode);
	}
	
	@Override
	public SidoDto getSidoByCode(int sidoCode) throws Exception {
		return mapMapper.getSidoByCode(sidoCode);
	}
	
	@Override
	public List<MapDto> getMap(Map<String, Integer> map) throws Exception {
		return mapMapper.getMap(map);
	}

}
