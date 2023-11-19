package com.ssafy.plan.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "PlanListDto : 나의여행계획 목록 & 페이지 정보", description = "나의여행계획 목록과 현재 페이지와 전체 페이지 정보를 나타낸다.")
public class PlanListDto {
	
	@ApiModelProperty(value = "나의여행계획 목록")
	private List<PlanDto> plans;
	@ApiModelProperty(value = "현재 페이지번호")
	private int currentPage;
	@ApiModelProperty(value = "전체 페이지 수")
	private int totalPageCount;

}
