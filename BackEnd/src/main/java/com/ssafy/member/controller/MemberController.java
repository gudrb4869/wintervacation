package com.ssafy.member.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.board.model.BoardDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final JWTUtil jwtUtil;

	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam Map<String, String> map,
			@RequestParam(name = "saveid", required = false) String saveid, Model model, HttpSession session,
			HttpServletResponse response) throws Exception {

		log.info("map: {}", map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			MemberDto memberDto = memberService.login(map);

			if (memberDto != null) {
				session.setAttribute("userinfo", memberDto);

				String accessToken = jwtUtil.createAccessToken(memberDto.getUser_id());
				String refreshToken = jwtUtil.createRefreshToken(memberDto.getUser_id());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);

//			발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(memberDto.getUser_id(), refreshToken);

//			JSON으로 token 전달.
				resultMap.put("access-token", accessToken);

				// 나중에 Cookie로 변경
				resultMap.put("refresh-token", refreshToken);

				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}

		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "회원가입", notes = "회원가입 한다.", response = Map.class)
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody @ApiParam(value = "회원가입을 위한 정보.", required = true) MemberDto memberDto) throws Exception {
		log.info("회원 join | memberDto: {}", memberDto);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return ResponseEntity.ok().headers(header).body(memberService.join(memberDto));
		
	}

	@ApiOperation(value = "회원정보 수정 화면에 뿌릴 값 얻기", notes = "아이디에 맞는 회원 정보를 반환 한다.", response = MemberDto.class)
	@GetMapping("/modify/{user_id}")
	public ResponseEntity<MemberDto> userModifyView(@PathVariable("user_id") @ApiParam(value = "얻어올 회원 아이디", required = true) String user_id) throws Exception {
	
		log.info("userModifyView - 호출 : " + user_id);
		return new ResponseEntity<MemberDto>(memberService.userInfo(user_id), HttpStatus.OK);
	}
	
	//TODO Response 추가할지 말지
	@ApiOperation(value = "회원정보 수정", notes = "회원 정보를 수정한다. DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환")
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 유저.", required = true) MemberDto memberDto)
			throws Exception {
		
		log.info("userModify - 호출 : " + memberDto);
		memberService.modify(memberDto);

		return ResponseEntity.ok().build();
	}

	
	@ApiOperation(value = "비밀번호 수정", notes = "비밀번호를 수정한다. DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환")
	@PostMapping("/modify_pw")
	public String modify_pw(@RequestParam String userid, @RequestParam String userpass, @RequestParam String salt,
			Model model, RedirectAttributes redirectAttributes, HttpSession session) throws Exception {

		memberService.modify_pw(userid, userpass, salt);
		// 세션 제거
		session.invalidate();
		redirectAttributes.addFlashAttribute("msg", "비밀번호 수정 성공! 다시 로그인해주세요!");
		return "redirect:/";
	}

	@GetMapping("/idcheck/{userid}")
	public ResponseEntity<?> idcheck(@PathVariable("userid") String userId) {
		log.info("idcheck: " + userId);
		try {
			int cnt = memberService.idCheck(userId);
			log.info("cnt: {}", cnt);
			if (cnt == 0) {
				return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
			} else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/delete")
	public String delete(HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		String userid = memberDto.getUser_id();
		memberService.delete(userid);
		session.invalidate();
		redirectAttributes.addFlashAttribute("msg", "회원삭제 성공!");
		return "redirect:/";
	}

}
