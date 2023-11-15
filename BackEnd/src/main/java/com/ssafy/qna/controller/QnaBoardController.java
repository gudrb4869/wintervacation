package com.ssafy.qna.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.qna.model.QnaBoardDto;
import com.ssafy.qna.model.QnaBoardListDto;
import com.ssafy.qna.model.service.QnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/qna-board")
@Api
public class QnaBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(QnaBoardController.class);
	
	private QnaService qnaService;
	
	public QnaBoardController(QnaService qnaService) {
		this.qnaService = qnaService;
	}

	@ApiOperation(value = "QnA 게시판 글작성", notes = "새로운 QnA 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writeQna(
			@RequestBody @ApiParam(value = "QnA게시글 정보.", required = true) QnaBoardDto qnaBoardDto) {
		logger.info("writeArticle boardDto - {}", qnaBoardDto);
		try {
			qnaService.writeQna(qnaBoardDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@ApiOperation(value = "QnA 게시판 글목록", notes = "모든 QnA 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "QnA 목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> qnaBoardList(
			@RequestParam @ApiParam(value = "QnA 게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		logger.info("qnaBoardList map - {}", map);
		try {
			QnaBoardListDto qnaBoardListDto = qnaService.qnaBoardList(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(qnaBoardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@ApiOperation(value = "QnA 게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = QnaBoardDto.class)
	@GetMapping("/{article_no}")
	public ResponseEntity<QnaBoardDto> viewQnaBoard (@PathVariable("article_no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int article_no)
			throws Exception { 
		logger.info("viewQnaBoard - 호출 : " + article_no);
		qnaService.updateHit(article_no);
		return new ResponseEntity<QnaBoardDto>(qnaService.viewQnaBoard(article_no), HttpStatus.OK);
	}
			
	
	@ApiOperation(value = "QnA 수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = QnaBoardDto.class)
	@GetMapping("/modify/{article_no}")
	public ResponseEntity<QnaBoardDto> getModifyQna(
			@PathVariable("article_no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int article_no)
					throws Exception {
		logger.info("getModifyArticle - 호출 : " + article_no);
		return new ResponseEntity<QnaBoardDto>(qnaService.viewQnaBoard(article_no), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyQna(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) QnaBoardDto qnaBoardDto) throws Exception {
		logger.info("modifyQna - 호출 {}", qnaBoardDto);
		qnaService.modifyQna(qnaBoardDto);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{article_no}")
	public ResponseEntity<String> deleteQna(@PathVariable("article_no") @ApiParam(value = "삭제할 글의 글번호.", required = true) int article_no) throws Exception {
		logger.info("deleteQna - 호출");
		qnaService.deleteQna(article_no);
		return ResponseEntity.ok().build();
	}
	
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
