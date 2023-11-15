<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" href="${root}/assets/css/index.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp"%>
<div class="container">
	<div class="row justify-content-center">
		<div class="col-lg-8 col-md-10 col-sm-12">
			<h2 class="my-3 py-3 shadow-sm bg-light text-center">
				<mark class="sky">후기</mark>
			</h2>
		</div>

		<div class="col-lg-8 col-md-10 col-sm-12">
			<div class="row my-2">
				<h2 class="text-secondary px-5">${boardDto.subject}</h2>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="clearfix align-content-center">
						<img class="avatar me-2 float-md-start bg-light p-2"
							src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
						<p>
							<span class="fw-bold">${boardDto.user_id}</span> <br /> <span
								class="text-secondary fw-light"> ${boardDto.registerTime}
								조회 : ${boardDto.hit} </span>
						</p>
					</div>
				</div>
				<!-- <div class="col-md-4 align-self-center text-end">댓글 : 0</div>  -->
				<div class="divider mb-3"></div>
				<div class="text-secondary">${boardDto.content}</div>
				<div class="divider mt-3 mb-3"></div>
				<div class="d-flex justify-content-end">
					<button type="button" id="btn-list" class="btn mb-3">목록</button>
					<c:if test="${userinfo.userid eq boardDto.user_id}">
					<button type="button" id="btn-mv-modify" class="btn mb-3 ms-1">
						수정</button>
					<button type="button" id="btn-delete" class="btn mb-3 ms-1">
						삭제</button>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<form id="form-param" method="get" action="${root}/board/modify">
	<input type="hidden" name="articleNo" id="articleNo" value="">
</form>

<form id="form-param2" method="post" action="${root}/board/delete">
	<input type="hidden" name="articleNo" id="articleNo2" value="">
</form>
<script>
      document.querySelector("#btn-list").addEventListener("click", function () {
        location.href = "${root}/board/list";
      });
      
      document.querySelector("#btn-mv-modify").addEventListener("click", function () {
    	  let form = document.querySelector("#form-param");
          document.querySelector("#articleNo").value = ${boardDto.article_no};
          document.querySelector("#form-param").submit();
        });
        
      document.querySelector("#btn-delete").addEventListener("click", function () {
          document.querySelector("#articleNo2").value = ${boardDto.article_no};
          document.querySelector("#form-param2").submit();
        });
        

</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>