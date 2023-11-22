package com.ssafy.favorite.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.favorite.model.FavoriteDto;
import com.ssafy.favorite.model.service.FavoriteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Api("찜 컨트롤러 API V1")
@RequestMapping("/favorite")
@Slf4j
public class FavoriteController {
	
	private final FavoriteService favoriteService;
	
	@ApiOperation(value = "찜등록", notes = "관광지를 찜한다.")
	@PostMapping
	public ResponseEntity<?> registFavorite(
			@RequestBody @ApiParam(value = "찜정보", required = true) FavoriteDto favoriteDto) {
		log.info("registFavorite favoriteDto - {}", favoriteDto);
		try {
			favoriteService.registFavorite(favoriteDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
 		} catch (Exception e) {
 			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "찜목록", notes = "사용자가 찜한 관광지 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "찜목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/{user_id}")
	public ResponseEntity<?> favoriteAttractionList(
			@PathVariable("user_id") @ApiParam(value = "찜목록 얻기위한 회원아이디.", required = true) String user_id) {
		log.info("favoriteAttractionList user_id - {}", user_id);
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(favoriteService.listFavoriteAttraction(user_id));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "나의 여행 계획 plan 보기", notes = "plan번호에 해당하는 plan의 정보를 반환한다.")
	@GetMapping
	public ResponseEntity<Integer> getFavorite(
			@ApiParam(value = "사용자아이디", required = true) FavoriteDto favoriteDto)
					throws Exception {
		log.info("getFavorite 호출: favoriteDto - {}", favoriteDto);
		return new ResponseEntity<Integer>(favoriteService.getFavorite(favoriteDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "찜삭제", notes = "관광지번호에 해당하는 찜정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteFavorite(
			@ApiParam(value = "찜정보", required = true) FavoriteDto favoriteDto)
					throws Exception {
		log.info("deletePlan - 호출");
		favoriteService.deleteFavorite(favoriteDto);
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
