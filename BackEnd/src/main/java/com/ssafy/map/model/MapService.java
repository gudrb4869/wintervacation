package com.ssafy.map.model;


import java.util.List;
import java.util.Map;

public interface MapService {
	List<SidoDto> getSido() throws Exception;
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	SidoDto getSidoByCode(int sidoCode) throws Exception;
	List<MapDto> getMap(Map<String, Integer> map) throws Exception;
}
