package com.ssafy.map.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.map.model.GugunDto;
import com.ssafy.map.model.MapDto;
import com.ssafy.map.model.SidoDto;

@Mapper
public interface MapMapper {
	List<SidoDto> getSido() throws SQLException;
	List<GugunDto> getGugun(int sidoCode) throws SQLException;
	SidoDto getSidoByCode(int sidoCode) throws SQLException;
	List<MapDto> getMap(Map<String, Integer> map) throws SQLException;
}
