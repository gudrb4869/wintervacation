package com.ssafy.plan.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.plan.model.PlanDto;
import com.ssafy.plan.model.PlanListDto;
import com.ssafy.plan.model.service.PlanService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/plan")
@Api("Plan 컨트롤러 API V1")
@RequiredArgsConstructor
@Slf4j
public class PlanController {
	
	private final JWTUtil jwtUtil;
	private final PlanService planService;
	
	@ApiOperation(value = "Plan등록", notes = "새로운 Plan 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writePlan(
			@RequestBody @ApiParam(value = "plan 정보", required = true) PlanDto planDto) {
		log.info("writePlan planDto - {}", planDto);
		try {
			planService.writePlan(planDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
 		} catch (Exception e) {
 			return exceptionHandling(e);
		}
	}
	@ApiOperation(value = "Plan목록", notes = "로그인한 사용자의 모든 Plan 정보를 반환한다.", response = List.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Plan목록 OK!!"), @ApiResponse(code = 404, message = "페이지 없어!!!"),
		@ApiResponse(code = 500, message = "서버에러!!")})
	@GetMapping
	public ResponseEntity<?> myPlanList(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		log.info("myPlanList map - {}", map);
		try {
			log.info("회원 정보 {}");
			PlanListDto planListDto = planService.listPlan(map);
			HttpHeaders header = new HttpHeaders();
			log.info("myPlanList planListDto - {}", planListDto);
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(planListDto);			
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
