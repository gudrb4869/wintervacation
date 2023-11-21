package com.ssafy.map.model.service;


import java.util.List;
import java.util.Map;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoGugunCodeDto;

public interface MapService {
	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugun(int sidoCode) throws Exception;
	// 카테고리 중복 선택 가능한 관광지 검색
	List<AttractionDto> getAttractions(Map<String, String> map) throws Exception;
	// 여행지 이름으로 관광지 검색
	List<AttractionDto> attractionList(Map<String, String> map) throws Exception;
}
