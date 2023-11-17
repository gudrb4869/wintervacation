package com.ssafy.map.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoGugunCodeDto;

@Mapper
public interface MapMapper {
	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugun(int sidoCode) throws SQLException;
	List<AttractionDto> getAttractions(Map<String, Integer> map) throws SQLException;
}
