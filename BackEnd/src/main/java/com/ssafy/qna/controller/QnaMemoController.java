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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.qna.model.QnaMemoDto;
import com.ssafy.qna.model.service.QnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/qna-memo")
@Api
public class QnaMemoController {

	private static final Logger logger = LoggerFactory.getLogger(QnaMemoController.class);
	
	@Autowired
	private QnaService qnaService;
	
	@ApiOperation(value = "QnA 댓글목록", notes = "QnA 댓글들을 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "QnA 목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping()
	public ResponseEntity<?> qnaMemoList(
			@RequestParam @ApiParam(value = "QnA 댓글을 얻기위한 부가정보.", required = true) int article_no) {
		logger.info("qnaMemoList article_no - {}", article_no);
		try {
			List<QnaMemoDto> list = qnaService.qnaMemoList(article_no);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@ApiOperation(value = "QnA 댓글작성", notes = "QnA 게시글에 대한 새로운 댓글을 입력한다.")
	@PostMapping()
	public ResponseEntity<?> writeQnaMemo(
			@RequestBody @ApiParam(value = "QnA댓글 정보.", required = true) QnaMemoDto qnaMemoDto) {
		logger.info("writeQnaMemo qnaMemoDto - {}", qnaMemoDto);
		try {
			qnaService.writeQnaMemo(qnaMemoDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{memo_no}")
	public ResponseEntity<String> deleteQnaMemo(@PathVariable("memo_no") @ApiParam(value = "삭제할 댓글의 글번호.", required = true) int memo_no) throws Exception {
		logger.info("deleteQnaMemo - 호출");
		qnaService.deleteQnaMemo(memo_no);
		return ResponseEntity.ok().build();
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}

