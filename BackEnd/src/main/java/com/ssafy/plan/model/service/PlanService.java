package com.ssafy.plan.model.service;

import java.util.Map;

import com.ssafy.plan.model.PlanDto;
import com.ssafy.plan.model.PlanListDto;

public interface PlanService {
	
	void writePlan(PlanDto planDto) throws Exception;
	PlanListDto listPlan(Map<String, String> map) throws Exception;
	PlanDto getPlan(int plan_no) throws Exception;
	void modifyPlan(PlanDto planDto) throws Exception;
	void deletePlan(int plan_no) throws Exception;

}
