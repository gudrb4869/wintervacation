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
					<div id="loginBoxTitle">회원 정보</div>
					<!-- 아이디, 비번, 버튼 박스 -->
					<div id="inputBox">
						<div id="inputBox">
							<form action="${root}/member/modify" method="post">
								<div class="input-form-box">
									<span>아이디 </span><span id="idSpan">${userinfo.userid}</span>
									<input type="hidden" name="userid" value="${userinfo.userid}">
								</div>

								<div class="input-form-box">
									<span>이름 </span><span id="nameSpan">${userinfo.username}</span> <input
										type="text" name="username" id="nameInput" value="${userinfo.username}"
										style="display: none" />
								</div>
								<div class="input-form-box">
									<span>이메일 </span><span id="emailSpan">${userinfo.email}</span> <input
										type="text" name="email" id="emailInput" value="${userinfo.email}"
										style="display: none" />
								</div>
								<div class="button-login-box">
									<button type="button"
										class="btn btn btn-outline-primary btn-xs" id="modify_btn"
										style="width: 100%; margin-top: 10px" onClick="modifyForm()">회원정보
										수정</button>

									<button type="button"
										class="btn btn btn-outline-primary btn-xs" id="modify_btn"
										style="width: 100%; margin-top: 10px"
										onclick="window.location.href='${root}/member/modify_pw'">비밀번호 수정</button>


									<button type="submit"
										class="btn btn btn-outline-primary btn-xs" id="save_btn"
										style="width: 100%; margin-top: 10px; display: none">저장</button>

								</div>
							</form>
							<button type="button"
								class="btn btn btn-outline-secondary btn-xs"
								style="width: 100%; margin-top: 10px"
								onclick="window.location.href='${root}/member/delete'">
								회원 탈퇴</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

		<!-- js: addEventListener() 이벤트 처리 위치: body 가 모두 로드 후에 바인딩 처리 -->

		<script>
	      function modifyForm() {
	          // 비밀번호, 이름, 이메일 span 태그와 input 태그를 가져옵니다.

	          const nameSpan = document.getElementById("nameSpan");
	          const emailSpan = document.getElementById("emailSpan");
	          const nameInput = document.getElementById("nameInput");
	          const emailInput = document.getElementById("emailInput");
	          const saveBtn = document.getElementById("modify_btn");
	          const modifyBtn = document.getElementById("save_btn");
	          


	          // 수정 모드 여부를 확인합니다.
	          const isEditMode = nameInput.style.display === "block";

	          if (isEditMode) {
	            // 수정 모드에서 저장 모드로 변경합니다.
	            nameSpan.textContent = nameInput.value;
	            emailSpan.textContent = emailInput.value;
	            modifyBtn.style.display = "none";
	            saveBtn.style.display = "inline-block";
	          } else {
	            // 저장 모드에서 수정 모드로 변경합니다.
	            nameInput.value = nameSpan.textContent;
	            emailInput.value = emailSpan.textContent;
	            saveBtn.style.display = "none";
	            modifyBtn.style.display = "inline-block";
	          }

	          // 수정 모드와 저장 모드를 토글합니다.
	          nameSpan.style.display = isEditMode ? "inline" : "none";
	          emailSpan.style.display = isEditMode ? "inline" : "none";
	          nameInput.style.display = isEditMode ? "none" : "block";
	          emailInput.style.display = isEditMode ? "none" : "block";
	        }
		      
    </script>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
