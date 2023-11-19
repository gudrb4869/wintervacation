package com.ssafy.plan.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "CourseDto : 여행 코스정보", description = "여행계획에 추가되어있는 여행 코스를 나타낸다.")
public class CourseDto {
	
	@ApiModelProperty(value = "몇일차")
	private int date;
	@ApiModelProperty(value = "몇번째")
	private int orders;
	@ApiModelProperty(value = "여행지 번호")
	private int course_id;
	
}
