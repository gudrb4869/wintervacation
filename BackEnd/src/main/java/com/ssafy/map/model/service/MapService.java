package com.ssafy.map.model.service;


import java.util.List;
import java.util.Map;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoGugunCodeDto;

public interface MapService {
	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugun(int sidoCode) throws Exception;
	List<AttractionDto> getAttractions(Map<String, Integer> map) throws Exception;
}
