package com.ssafy.board.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.config.FileUtil;
import com.ssafy.file.model.FileDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/board-api")
@Api
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	BoardService boardService;
	FileUtil fileUtil;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

//	@ApiOperation(value = "여행후기 게시판 글작성", notes = "새로운 여행후기 게시글 정보를 입력한다.")
//	@PostMapping("/register")
//	public ResponseEntity<?> writeArticle(@RequestBody @ApiParam(value = "여행후기 게시글 정보.", required = true)
//	Map<String, String> map) throws SQLException {
//		logger.info("writeArticle map - {}", map);
//		
//		BoardDto boardDto = new BoardDto();
//		boardDto.setUser_id(map.get("user_id"));
//		boardDto.setSubject(map.get("subject"));
//		boardDto.setContent(map.get("content"));
//
//		try {
//			boardService.writeArticle(boardDto);
//			return new ResponseEntity<Void>(HttpStatus.CREATED);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//		
//	}
	
	
	@PostMapping("/register")
	   public ResponseEntity<?> write(@RequestPart("boardDto") BoardDto boardDto, @RequestPart("imgInfos") MultipartFile[] files) {
        try {
            // boardDto(JSON문자열)를 BoardDto객체로 변환(jackson)
            ObjectMapper objectMapper = new ObjectMapper();
            BoardDto boardDto = objectMapper.readValue(formData.getParameter("boardDto"), BoardDto.class);
            // 파일들 받아오기
            List<MultipartFile> multipartFiles = formData.getFiles("imgInfos");
            
            // 파일들 저장 및 dto 리스트로 변환
            List<FileDto> imgInfos = fileUtil.storeImgs(multipartFiles, boardDto);
            
            // dto에 파일 dto 리스트 추가
            boardDto.setFileInfos(imgInfos);
            
            // 서비스 호출
            boardService.writeArticle(boardDto);
    
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch(Exception e) {
            System.out.println("board uploadImg Controller Error");
            e.printStackTrace();
            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@ApiOperation(value = "여행후기 게시판 글목록", notes = "모든 여행후기 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "여행후기 목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/list")
	public ResponseEntity<?> listArticle(@RequestParam @ApiParam(value = "QnA 게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		logger.info("listArticle map - {}", map);
		
		try {
			BoardListDto boardListDto = boardService.boardList(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);			
		} catch (Exception e) {
			return exceptionHandling(e);
		} 
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/view/{article_no}")
	public ResponseEntity<BoardDto> viewArticle(@PathVariable("article_no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int article_no) 
			throws Exception {
		System.out.println("view 들어옴 + articleNo " + article_no);
		boardService.updateHit(article_no);

		return new ResponseEntity<BoardDto>(boardService.viewArticle(article_no), HttpStatus.OK);
	}

//	@GetMapping("/serch")
//	public ModelAndView search(@RequestParam Map<String, String> map) throws SQLException {
//
//		ModelAndView mav = new ModelAndView();
//		// 1. 검색 옵션 및 검색어
//		String option = map.get("option");
//		String search = map.get("search");
//		List<BoardDto> list = null;
//
//		// 2. 전체 조회 / 그 외 나누어서 DB 에서 select
//		if (option.equals("all")) {
//			list = boardService.listArticle();
//		} // 전체 조회 -> 전체 리스트 보여주면 됨
//
//		else {
//			list = boardService.searchArticle(option, search);
//			if (list.size() == 0) {
//				list = boardService.listArticle();
//			} // 조회 결과가 없다면 전체 리스트 보여주게
//		}
//
//		mav.addObject("list", list);
//		mav.setViewName("/board/list");
//
//		return mav;
//	}
	
	@ApiOperation(value = "여행후기 수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/modify/{article_no}")
	public ResponseEntity<BoardDto> modifyView(@PathVariable("article_no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int article_no) 
			throws Exception {

		logger.info("modifyView - 호출 : " + article_no);
		return new ResponseEntity<BoardDto>(boardService.viewArticle(article_no), HttpStatus.OK);
	}

	@ApiOperation(value = "여행후기 게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping()
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 글정보.", required = true)BoardDto boardDto) 
			throws Exception {

		logger.info("modify - 호출 {}", boardDto);
		boardService.modifyArticle(boardDto);
		
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "여행후기 게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{article_no}")
	public ResponseEntity<String> delete(@PathVariable("article_no") @ApiParam(value = "삭제할 글의 글번호.", required = true) int article_no) throws Exception {
		logger.info("delete(board) - 호출");
		boardService.deleteArticle(article_no);

		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
