<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="${root}/assets/css/index.css" />
</head>
<body>
   	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
      <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">후기 공유</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row align-self-center mb-2">
            <div class="col-md-2 text-start">
              <button type="button" id="btn-mv-register" class="btn btn-sm">
                		후기 쓰기
              </button>
            </div>
            <div class="col-md-7 offset-3">
              <form class="d-flex">
                <select
                  id="key"
                  class="form-select form-select-sm ms-5 me-1 w-50"
                  aria-label="검색조건"
                >
                  <option value="all" selected>검색조건</option>
                  <option value="article_no">글번호</option>
                  <option value="subject">제목</option>
                  <option value="user_id">작성자</option>
                </select>
                <div class="input-group input-group-sm">
                  <input type="text" class="form-control" id="searchText" placeholder="검색어..." />
                  <button class="btn btn-sm" id="search" type="button">검색</button>
                </div>
              </form>
            </div>
          </div>
          <table class="table table-hover">
            <thead class="table-dark">
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${list}">
              <tr class="text-center">
                <th scope="row">${list.article_no}</th>
                <td class="text-start">
                  <a
                  	href="#" 
                    class="article-title link-dark"
                    data-no="${list.article_no}"
                    style="text-decoration: none"
                  >
                    ${list.subject}
                  </a>
                </td>
                <td>${list.user_id}</td>
                <td>${list.hit}</td>
                <td>${list.registerTime}</td>
              </tr>
              </c:forEach>
 
              
            </tbody>
          </table>
        </div>
        <div class="row">
          <ul class="pagination justify-content-center">
            <li class="page-item">
              <a class="page-link" href="#">이전</a>
            </li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">4</a></li>
            <li class="page-item"><a class="page-link" href="#">5</a></li>
            <li class="page-item"><a class="page-link" href="#">다음</a></li>
          </ul>
        </div>
      </div>
      

	<form id="form-param" method="get" action="${root}/board/search">
		<input type="hidden" name="option" id="option" value="">
		<input type="hidden" name="search" id="search" value="">
	</form>
		<form id="form-param2" method="get" action="${root}/board/view">
		<input type="hidden" name="articleNo" id="articleNo" value="">
	</form>
    </div>
    <script>
      <%-- 검색 이벤트 -> 버튼에 이벤트 생성하기  --%>
      document.querySelector("#search").addEventListener("click",function(){
    	  <%-- 1. 선택된 옵션 가져오기   --%>
    	  let selectBox=document.getElementById("key");
    	  let selectedOption=selectBox.options[selectBox.selectedIndex].value;
    	  document.querySelector("#option").value = selectedOption;
    	  
    	  <%-- 2. 입력한 검색어 가져오기 --%>
    	  let searchText=document.getElementById("searchText").value;
    	  document.querySelector("#search").value = searchText;
    	  
    	  console.log(selectedOption);
    	  console.log(searchText);

    	  document.querySelector("#form-param").submit();
      }); 
      
      let titles = document.querySelectorAll(".article-title");
      titles.forEach(function (title) {
        title.addEventListener("click", function () {
      	console.log("게시글 번호 "+this.getAttribute("data-no"))
          document.querySelector("#articleNo").value = this.getAttribute("data-no");
          
          document.querySelector("#form-param2").submit();
        });
      });
      
      document.querySelector("#btn-mv-register").addEventListener("click",function(){
    	  location.href = "${root}/board/register";
      });
    </script>
   
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
