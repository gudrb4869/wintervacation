<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="sky">글쓰기</mark>
				</h2>
			</div>
			<div class="col-lg-8 col-md-10 col-sm-12">
				<form id="form-register" method="POST" action="">
					<div class="mb-3">
						<label for="userid" class="form-label">작성자 ID : </label> <input
							type="text" class="form-control" id="userid" name="userid"
							value="${userinfo.userid}" placeholder="작성자ID..." readonly />
					</div>
					<div class="mb-3">
						<label for="subject" class="form-label">제목 : </label> <input
							type="text" class="form-control" id="subject" name="subject"
							placeholder="제목..." />
					</div>
					<div class="mb-3">
						<label for="content" class="form-label">내용 : </label>
						<textarea class="form-control" id="content" name="content"
							rows="7"></textarea>
					</div>
					<div class="col-auto text-center">
						<button type="button" id="btn-register"
							class="btn btn-outline-primary mb-3">글작성</button>
						<button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
    
 	// 서버로부터 CSV 형식의 비속어 목록을 가져오는 함수
    function fetchBadWords() {
    	  return fetch("${root}/board/readBadword")
    	    .then(response => response.text())
    	    .then(data => {
    	      // CSV 문자열을 줄 단위로 분리하여 배열로 변환
    	      var lines = data.split('\n');
    	      var badWords = [];

    	      for (var i = 0; i < lines.length; i++) {
    	    	  var words = lines[i].split(',');
    	    	  for (var j = 0; j < words.length; j++) {
    	    	    badWords.push(words[j].trim()); // 각 열의 데이터를 가져옴
    	    	  }
    	      }

    	      return badWords;
    	    })
    	    .catch(error => {
    	      console.error("비속어 목록을 가져오는 데 오류 발생:", error);
    	      return [];
    	    });
    }

    document.querySelector("#btn-register").addEventListener("click", async function () {
    	  // 제목과 내용 입력값 가져오기
    	  var subjectValue = document.querySelector("#subject").value;
    	  var contentValue = document.querySelector("#content").value;

    	  console.log("비속어 확인 들어옴");

    	  // 비속어 목록을 서버에서 가져오기
    	  var badWords = await fetch("/board/badwords")
    	    .then(response => response.json());

    	  console.log("비속어 목록 받아옴");

    	  // 비속어 체크 함수
    	  function containsBadWord(text) {
    	    for (var i = 0; i < badWords.length; i++) {
    	      console.log("i: " + i + " Badword: " + badWords[i]);
    	      if (text.includes(badWords[i])) {
    	        console.log("text: " + text);
    	        return true;
    	      }
    	    }
    	    return false;
    	  }

      if (!subjectValue) {
        alert("제목 입력!!");
      } else if (!contentValue) {
        alert("내용 입력!!");
      } else if (containsBadWord(subjectValue) || containsBadWord(contentValue)) {
        alert("제목 또는 내용에 비속어가 포함되어 있습니다. 등록할 수 없습니다.");
      } else {
        let form = document.querySelector("#form-register");
        form.setAttribute("action", "${root}/board/register");
        form.submit();
      }
    });

    </script>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>