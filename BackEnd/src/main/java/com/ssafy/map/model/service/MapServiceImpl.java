package com.ssafy.map.model.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public List<AttractionDto> attractionList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<>();
		
		int sido_code = Integer.parseInt(map.get("sido_code") == null ? "0" : map.get("sido_code"));
		int gugun_code = Integer.parseInt(map.get("gugun_code") == null ? "0" : map.get("gugun_code"));
		
		param.put("sido_code", sido_code);
		param.put("gugun_code", gugun_code);
		
		String contentTypeId = map.get("content_type_id").isEmpty() ? "0" : map.get("content_type_id"); 
		
		List<Integer> content_type_id = Arrays.stream(contentTypeId.split(","))
		                .map(Integer::parseInt)
		                .collect(Collectors.toList());
		
		param.put("content_type_id", content_type_id);
		
		param.put("title", map.get("title"));
		
		return mapMapper.attractionList(param);
	}

}
