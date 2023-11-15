package com.ssafy.map.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.map.model.GugunDto;
import com.ssafy.map.model.MapDto;
import com.ssafy.map.model.MapService;
import com.ssafy.map.model.SidoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class MapController {
	
	private final MapService mapService;
	
	@GetMapping("/map")
	public String map() {
		return "map/map";
	}
	
	// 지도 
	@GetMapping("/search")
	@ResponseBody
	public ResponseEntity<?> search(
			@RequestParam Map<String, Integer> map) {
		
		try {
			log.info("/map/serach, map={}", map);
			List<MapDto> list = mapService.getMap(map);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<MapDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}


	@GetMapping("/gugun")
	@ResponseBody
	public ResponseEntity<?> gugun(@RequestParam("sido_code") int sido_code) {
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

	@GetMapping("/sido")
	@ResponseBody
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
