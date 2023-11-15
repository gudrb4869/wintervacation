<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
   	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<div class="text-center">
		<div
			style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1;">
			<!--  html 전체 영역을 지정하는 container -->
			<div id="container">
				<!--  login 폼 영역을 : loginBox -->
				<div id="loginBox">
					<!-- 로그인 페이지 타이틀 -->
					<div id="loginBoxTitle">비밀번호 변경</div>
					<br>
					<!-- 아이디, 비번, 버튼 박스 -->
					<div id="inputBox">
						<div id="inputBox">
							<form action="${root}/member/modify_pw" method="post">
								<input type="hidden" name="userid" value="${userinfo.userid}">
								<input type="hidden" name="salt" value="${userinfo.salt}">
								<div class="input-form-box">
									<span>비밀번호 </span><span id="passwordSpan"></span> 
									<input type="text" name="userpass" value="" />
								</div>

								<div class="button-login-box">
									<button type="submit"
										class="btn btn btn-outline-primary btn-xs" id="save_btn"
										style="width: 100%; margin-top: 10px;">비밀번호 변경</button>

								</div>
							</form>
							<button type="button"
								class="btn btn btn-outline-secondary btn-xs"
								style="width: 100%; margin-top: 10px"
								onclick="window.location.href='${root}/member/modify'">
								변경취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
