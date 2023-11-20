package com.ssafy.map.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoGugunCodeDto;
import com.ssafy.map.model.service.MapService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class MapController {
	
	private final MapService mapService;
	
	// 지도 
	@ApiOperation(value = "지도 검색", notes = "지도 검색", response=List.class)
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam @ApiParam(value = "지도검색을 위한 부가 정보", required = true)
			Map<String, String> params) {
		
		try {
			log.info("/map/search, map - {}", params);
			
			return new ResponseEntity<List<AttractionDto>>(mapService.attractionList(params), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@ApiOperation(value = "구/군 코드", notes = "구/군 코드를 리스트로 반환한다.")
	@GetMapping("/gugun")
	public ResponseEntity<?> gugun(@RequestParam("sido_code") 
		@ApiParam(value = "시/도 코드", required = true)int sido_code) {
		try {
			log.info("/map/gugun?sido_code={}", sido_code);
			List<SidoGugunCodeDto> list = mapService.getGugun(sido_code);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<SidoGugunCodeDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		 
	}
	
	@ApiOperation(value = "시/도 코드", notes = "시/도 코드를 리스트로 반환한다.")
	@GetMapping("/sido")
	public ResponseEntity<?> sido() {
		try {
			List<SidoGugunCodeDto> list = mapService.getSido();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<SidoGugunCodeDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
