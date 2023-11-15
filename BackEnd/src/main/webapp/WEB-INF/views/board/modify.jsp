<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="${root}/assets/css/index.css" />
</head>
<body>
   	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">후기 수정</mark>
          </h2>
        </div>
        
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-modify" method="POST" action="">
          
        <%-- 게시글 번호 숨김 처리 --%>  
		<div class="mb-3">
			<input type="text" class="form-control" id="article_no" name="article_no" value="${boardDto.article_no}" style="display:none"/> 
			<input type="text" class="form-control" id="user_id" name="user_id" value="${boardDto.user_id}" style="display:none"/> 
		</div>
          
            <div class="mb-3">
              <label for="userid" class="form-label">작성자 ID : </label>
              <input type="text" class="form-control" id="userid" value="${boardDto.user_id}" name="${boardDto.user_id}" readonly />
            </div>
            <div class="mb-3">
              <label for="subject" class="form-label">제목 : </label>
              <input type="text" class="form-control" id="subject" name="subject" value="${boardDto.subject}" />
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용 : </label>
              <textarea class="form-control" id="content" name="content" rows="7">${boardDto.content}</textarea>
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-modify" class="btn mb-3">
                글수정
              </button>
              <button type="button" id="btn-list" class="btn mb-3">
                목록
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <script>
      document.querySelector("#btn-modify").addEventListener("click", function () {
        if (!document.querySelector("#subject").value) {
          alert("제목 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("내용 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-modify");
          form.setAttribute("action", "${root}/board/modify");
          form.submit();
        }
      });
      
      document.querySelector("#btn-list").addEventListener("click", function () {
          location.href = "${root}/board/list";
        });
    </script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
