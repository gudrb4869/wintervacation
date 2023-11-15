<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>  
	<!-- 상단 navbar start -->
	<nav class="navbar navbar-expand-lg bg-light navbar-light shadow fixed-top">
	    <div class="container">
	    <a class="navbar-brand " href="${root}/">
	        <img src='${root}/assets/img/logo.png' style='width:150px; height:60px;' />
	    </a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
	        <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="collapsibleNavbar">
		    <ul class="navbar-nav ms-auto">
		        <li class="nav-item">
		            <a class="nav-link" href="${root}/map/map">지역별여행지</a>
		        </li>
		        <li class="nav-item">
		            <a class="nav-link" href="${root}/board/list">여행후기공유</a>
		        </li>
		        <c:if test="${empty userinfo}">
	            <li class="nav-item">
	              <a class="nav-link" href="${root}/member/login">로그인</a>
	            </li>
	            </c:if>
	            <c:if test="${not empty userinfo}">
	            <li class="nav-item">
	              <a class="nav-link" href="${root}/member/logout">로그아웃</a>
	            </li>
	            <li class="nav-item dropdown" id="myPage">
	              <a
	                class="nav-link dropdown-toggle"
	                href="${root}/member/myPage.jsp"
	                role="button"
	                data-bs-toggle="dropdown"
	                aria-expanded="false"
	              >
	                		마이페이지
	              </a>
	              <ul class="dropdown-menu">
	                <li><a class="dropdown-item" href="${root}/member/modify">회원정보 수정</a></li>
	              </ul>
	            </li>
	            </c:if>
		    </ul>
	   	</div>
	   	</div>
	</nav>
</header>
<!-- 상단 navbar end -->