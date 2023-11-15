//package com.ssafy.map.controller;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.ssafy.map.model.GugunDto;
//import com.ssafy.map.model.MapDto;
//import com.ssafy.map.model.MapServiceImpl;
//import com.ssafy.map.model.SidoDto;
//
//
//@WebServlet("/map2")
//public class MapController_archive extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	private String contextPath = null;
//	
//	
//	/** 서블릿 초기화 메서드: contextPath 가져와서 멤버변수 설정 초기화 */
//	public void init() {
//		// 현재 서블릿이 속해 있는 웹 어플리케이션의 ServletContext 객체 가져오기
//		ServletContext application = getServletContext();
//		String servletName = getServletConfig().getServletName();
//		// 현재 웹어플리케이션의 contextPath를 가져와서 멤버변수에 설정하기
//		// 요청서비스에서 반복적으로 사용하는 코드 request.getContextPath()를 대체 사용하기 위함: 편리
//		contextPath = application.getContextPath();
//		System.out.printf("\n[INFO] %s 의 %s 서블릿이 로딩 초기화 되었습니다.%n", contextPath, servletName);
//	}
//	
//	private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
//		String action = request.getParameter("action");
//		System.out.printf("\n[INFO] action = %s 요청이 발생했습니다.%n", action);
//		
//		switch (action) {
//			case "sido": // 게시글 등록화면 요청 서비스
//				sido(request, response);
//				break;
//			case "gugun": // 게시글 등록화면 요청 서비스
//				gugun(request, response);
//				break;
//			case "search": // 지도 정보 검색 
//				search(request, response);
//				break;	
////		default:
////			unknown(request, response);
////			break;
//		}
//	}
//
//	
//	// 지도 
//	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int sido=Integer.parseInt(request.getParameter("sido"));
//		int gugun=Integer.parseInt(request.getParameter("gugun"));
//		int contentTypeId=Integer.parseInt(request.getParameter("contentTypeId"));
//	//	String selectedText=request.getParameter("selectedText");
//		
//		List<MapDto> map=MapServiceImpl.getInstance().getMap(sido,gugun,contentTypeId);
//		request.setAttribute("result", map); // 지도 정보 가져옴 
//	//	request.setAttribute("selectedText", selectedText);
//		
//		List<SidoDto> sidoList=MapServiceImpl.getInstance().getSido(); // 시도 리스트 가져오기 
//		request.setAttribute("sido",sidoList);
//		request.setAttribute("selectedSido", sido); // 선택된 시도 번호 저장 
//		
//		List<GugunDto> gugunList=MapServiceImpl.getInstance().getGugun(sido); /// 구군 리스트 가져오기 
//		request.setAttribute("gugun", gugunList);
//		request.setAttribute("selectedGugun", gugun); // 선택된 구군 번호 저장 
//		
//	//	forward(request, response, "/map/map.jsp");
//	}
//
//	
//	
//	
//	
//	private void gugun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int code=Integer.parseInt(request.getParameter("code")); // 고를 시도 코드 
//		List<GugunDto> gugun=MapServiceImpl.getInstance().getGugun(code);
//		request.setAttribute("gugun", gugun);
//		
//		List<SidoDto> sido=MapServiceImpl.getInstance().getSido();
//		request.setAttribute("sido",sido);
//		request.setAttribute("selectedSido", code);
//		
//		forward(request, response, "/map/map.jsp");
//	}
//
//	//select * from sido;
//	// 시도 정보 가져와서 request에 속성 추가 후 , 지도 페이지로 포워딩
//	private void sido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		List<SidoDto> sido=MapServiceImpl.getInstance().getSido();
//		request.setAttribute("sido", sido);
//		forward(request, response,"/map/map.jsp");
//	}
//
//	
//	// forward를 위한 메서드 분리 설계
//	protected void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
//		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//		dispatcher.forward(request, response);
//	}
//
//	// redirect를 위한 메서드 분리 설계
//	protected void redirect(HttpServletResponse response, String path) throws ServletException, IOException {
//		response.sendRedirect(contextPath + path);
//	}
//   
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		process(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		process(request, response);
//	}
//
//}
