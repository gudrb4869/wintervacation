package com.ssafy.map.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.BoardDto;
import com.ssafy.map.model.GugunDto;
import com.ssafy.map.model.MapDto;
import com.ssafy.map.model.MapService;
import com.ssafy.map.model.SidoDto;

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
			@RequestParam  @ApiParam(value = "지도검색을 위한 부가 정보", required = true)
			Map<String, Integer> map) {
		
		try {
			log.info("/map/serach, map={}", map);
			List<MapDto> list = mapService.getMap(map);
			
			log.info("map list : {}", list);
			
//			HttpHeaders header = new HttpHeaders();
//			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//			return ResponseEntity.ok().headers(header).body(list);	
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<MapDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
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
			List<GugunDto> list = mapService.getGugun(sido_code);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<GugunDto>>(list, HttpStatus.OK);
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
			List<SidoDto> list = mapService.getSido();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<SidoDto>>(list, HttpStatus.OK);
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
