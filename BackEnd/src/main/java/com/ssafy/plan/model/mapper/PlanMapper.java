package com.ssafy.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.plan.model.CourseDto;
import com.ssafy.plan.model.PlanDto;

@Mapper
public interface PlanMapper {
	
	void writePlan(PlanDto planDto) throws SQLException;
	
	void registerCourse(PlanDto planDto) throws SQLException;
	
	List<PlanDto> listPlan(Map<String, Object> param) throws SQLException;
	
	int getTotalPlanCount(Map<String, Object> param) throws SQLException;
	
	PlanDto getPlan(int plan_no) throws SQLException;
	
	void modifyPlan(PlanDto planDto) throws SQLException;
	
	void deleteCourse(int plan_no) throws SQLException;
	
	void deletePlan(int plan_no) throws SQLException;
	
	List<CourseDto> courseList(Map<String, String> map) throws SQLException;

}
