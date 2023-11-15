package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	
	// 아이디로 정보조회
	public String findSaltByUserId(String userid) throws SQLException;
	
	// 로그인
	public MemberDto login(Map<String, String> map) throws SQLException;
	
	// 로그인 회원 정보
	MemberDto userInfo(String userId) throws SQLException;
	
	// 회원가입
	public int join(MemberDto dto) throws SQLException;
	
	// 내정보 수정
	public int modify(MemberDto dto) throws SQLException;
	
	// 아이디 중복 체크
	public int idCheck(String userId) throws SQLException;
	
	// 아이디 삭제
	public int delete(String userId) throws SQLException;
	
	// 비밀번호 변경
	public int modify_pw(Map<String, String> map) throws SQLException;
	
	// 토큰 저장
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	
	// 토큰 얻기(재발급)
	Object getRefreshToken(String userid) throws SQLException;
	
	// 토큰 삭제
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
}
