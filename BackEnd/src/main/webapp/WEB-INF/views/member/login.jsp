<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<style>
* {
	padding: 0;
	margin: 0;
}

html, body {
	height: 100%;
	background: #ffffff;
}

#container {
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	height: 100%;
}

#loginBox {
	width: 300px;
	text-align: center;
	background-color: #ffffff;
}

.input-form-box {
	border: 0px solid #ff0000;
	display: flex;
	margin-bottom: 5px;
}

.input-form-box>span {
	display: block;
	text-align: left;
	padding-top: 5px;
	margin-right: 10px;
	min-width: 65px;
}

.button-login-box {
	margin: 10px 0;
}

#loginBoxTitle {
	color: #000000;
	font-weight: bold;
	font-size: 32px;
	text-transform: uppercase;
	padding: 5px;
	margin-bottom: 20px;
	background: linear-gradient(to right, #270a09, #8ca6ce);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}

#inputBox {
	margin: 10px;
}

#inputBox button {
	padding: 3px 5px;
}
</style>
</head>
<body>
   	<%@ include file="/WEB-INF/views/include/nav.jsp" %>

	<div class="text-center">
		<div
			style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1;">
			<!--  html 전체 영역을 지정하는 container -->
			<div id="container">
				<!--  login 폼 영역을 : loginBox -->
				<form id="form-login" method="POST" action="${root}/member/login">
					<div id="loginBox"
						class="border border-2 border-secondary border-radius"
						style="padding: 20px; border-radius: 10px">
						<!-- 로그인 페이지 타이틀 -->
						<div id="loginBoxTitle">Login</div>
						<!-- 아이디, 비번, 버튼 박스 -->
						<div id="inputBox">
							<div class="input-form-box">
								<span>아이디 </span><input type="text" name="userid" id="userid"
									class="form-control" />
							</div>
							<div class="input-form-box">
								<span>비밀번호 </span><input type="password" name="userpass"
									id="userpass" class="form-control" />
							</div>
							<div class="button-login-box">
								<button type="button" class="btn btn btn-outline-primary btn-xs"
									id="login_btn" style="width: 100%; margin-top: 10px">
									로그인</button>
								<button type="button"
									class="btn btn btn-outline-secondary btn-xs"
									style="width: 100%; margin-top: 10px"
									onClick="location.href='${root}/member/join'">
									회원가입</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
		
	<!-- js: addEventListener() 이벤트 처리 위치: body 가 모두 로드 후에 바인딩 처리 -->
	<script type="text/javascript">
    document.querySelector("#login_btn").addEventListener("click", function () {
        if (!document.querySelector("#userid").value) {
          alert("아이디 입력!!");
          return;
        } else if (!document.querySelector("#userpass").value) {
          alert("비밀번호 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-login");
          form.setAttribute("action", "${root}/member/login");
          form.submit();
        }
      });
	</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
