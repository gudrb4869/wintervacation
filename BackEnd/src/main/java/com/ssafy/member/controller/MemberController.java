package com.ssafy.member.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> map,
			@RequestParam(name = "saveid", required = false) String saveid,
			Model model, HttpSession session, HttpServletResponse response) throws Exception {
		
		log.info("map: {}", map);
		MemberDto memberDto = memberService.login(map);
		if(memberDto != null) {
			session.setAttribute("userinfo", memberDto);
			
			Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
			cookie.setPath("/");
			if("ok".equals(saveid)) {
				cookie.setMaxAge(60 * 60 * 24 * 400 ); //400 일 
			} else { //아이디 저장을 해제 했다면.
				cookie.setMaxAge(0);
			}
			response.addCookie(cookie);
			return "redirect:/";
		} else {
			model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요.");
			return "member/login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join() {
		return "member/regist";
	}
	
	@PostMapping("/join")
	public String join(MemberDto memberDto, Model model) throws Exception {
		log.info("memberDto: {}", memberDto);
		memberService.join(memberDto);
		return "redirect:/member/login";
	}
	
	@GetMapping("/modify")
	public String modify() {
		return "member/myPage";
	}
	
	@PostMapping("/modify")
	public String modify(MemberDto memberDto,
			HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
        memberService.modify(memberDto);
        session.invalidate();
        redirectAttributes.addFlashAttribute("msg", "회원 정보 수정 성공! 다시 로그인해주세요!");
	    return "redirect:/";
	}
	
	@GetMapping("/modify_pw")
	public String modify_pw() {
		return "member/modify_pw";
	}
	
	@PostMapping("/modify_pw")
	public String modify_pw(@RequestParam String userid,
			@RequestParam String userpass,
			@RequestParam String salt,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession session) throws Exception {
		
    	memberService.modify_pw(userid, userpass, salt);
    	// 세션 제거
    	session.invalidate();
    	redirectAttributes.addFlashAttribute("msg", "비밀번호 수정 성공! 다시 로그인해주세요!");
    	return "redirect:/";
	}
	
	@GetMapping("/idcheck/{userid}")
	@ResponseBody
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
		String userid = memberDto.getUserid();
		memberService.delete(userid);
		session.invalidate();
		redirectAttributes.addFlashAttribute("msg", "회원삭제 성공!");
		return "redirect:/";
	}
	
}
