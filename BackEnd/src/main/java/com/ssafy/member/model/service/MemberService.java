package com.ssafy.member.model.service;


import java.util.Map;

import com.ssafy.member.model.MemberDto;

public interface MemberService {
	// 로그인
//	public MemberDto login(Map<String, String> map) throws Exception;
	MemberDto login(MemberDto memberDto) throws Exception;
	
	// 로그인 회원 정보
	MemberDto userInfo(String userId) throws Exception;
	
	// 회원가입
	public int join(MemberDto dto) throws Exception;
	
	// 내정보 수정
	public int modify(MemberDto dto) throws Exception;
	
	// 아이디 중복 체크
	public int idCheck(String userId) throws Exception;
	
	// 아이디 삭제
	public int delete(String userId) throws Exception;
	
	// 비밀번호 변경
	public int modify_pw(String userId, String newPw, String salt) throws Exception;
	
	// 토큰 저장
	public void saveRefreshToken(String userId, String refreshToken) throws Exception;
	
	// 토큰 얻기(재발급)
	Object getRefreshToken(String userId) throws Exception;
	
	// 토큰 삭제
	void deleRefreshToken(String userId) throws Exception;
}
