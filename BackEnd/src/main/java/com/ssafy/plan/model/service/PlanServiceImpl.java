package com.ssafy.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.plan.model.CourseDto;
import com.ssafy.plan.model.PlanDto;
import com.ssafy.plan.model.PlanListDto;
import com.ssafy.plan.model.mapper.PlanMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
	
	private final PlanMapper planMapper;

	@Override
	@Transactional
	public void writePlan(PlanDto planDto) throws Exception {
		planMapper.writePlan(planDto);
		List<CourseDto> courses = planDto.getCourses();
		if (courses != null && !courses.isEmpty()) {
			planMapper.registerCourse(planDto);
		}
	}

	@Override
	public PlanListDto listPlan(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		// *** //
		param.put("user_id", map.get("user_id"));
		// *** //
		
//		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
//		String key = map.get("key");
//		param.put("key", key == null ? "" : key);
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "b.user_id");
		List<PlanDto> list = planMapper.listPlan(param);
		
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "user_id");
		int totalPlanCount = planMapper.getTotalPlanCount(param);
		int totalPageCount = (totalPlanCount - 1) / sizePerPage + 1;

		PlanListDto planListDto = new PlanListDto();
		planListDto.setPlans(list);
		planListDto.setCurrentPage(currentPage);
		planListDto.setTotalPageCount(totalPageCount);
		
		return planListDto;
	}

	@Override
	public PlanDto getPlan(int plan_no) throws Exception {
		return planMapper.getPlan(plan_no);
	}

	@Override
	@Transactional
	public void modifyPlan(PlanDto planDto) throws Exception {
		// ???
		planMapper.deleteCourse(planDto.getPlan_no());
		planMapper.modifyPlan(planDto);
		planMapper.registerCourse(planDto);
		// ???
	}

	@Override
	@Transactional
	public void deletePlan(int plan_no) throws Exception {
		// TODO Auto-generated method stub
		planMapper.deleteCourse(plan_no);
		planMapper.deletePlan(plan_no);
	}

}
