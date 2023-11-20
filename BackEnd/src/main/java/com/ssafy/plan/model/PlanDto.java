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
@ApiModel(value = "PlanDto : 여행계획정보", description = "여행계획의 상세정보를 나타낸다.")
public class PlanDto {
	
	@ApiModelProperty(value = "계획 번호")
	private int plan_no;
	@ApiModelProperty(value = "계획제목")
	private String title;
	@ApiModelProperty(value = "계획시작일")
	private String start_date;
	@ApiModelProperty(value = "계획종료일")
	private String end_date;
	@ApiModelProperty(value = "여행 코스정보")
	private List<CourseDto> courses;
	@ApiModelProperty(value = "사용자아이디")
	private String user_id;

}
