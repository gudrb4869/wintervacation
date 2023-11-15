<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>회원가입</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<!-- Bootstrap CSS -->
<!-- <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      crossorigin="anonymous"
    /> -->
<link rel="stylesheet" href="${root}/assets/css/common.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
body {
	min-height: 100vh;
	background: -webkit-gradient(linear, left bottom, right top, from(#92b5db),
		to(#1d466c));
	background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
}

.input-form {
	max-width: 680px;
	margin-top: 80px;
	padding: 32px;
	background: #fff;
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	border-radius: 10px;
	-webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
	-moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
	box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
}
</style>
</head>

<body>
	<div class="container">
		<div class="input-form-backgroud row">
			<div class="input-form col-md-12 mx-auto">
				<h4 class="mb-3">회원가입</h4>
				<form class="validation-form" novalidate action="${root}/member/join" method="POST">
					<div class="mb-3">

						<label for="name">아이디</label> <input type="text"
							class="form-control" name="userid" id="userid" placeholder="" value="" required />
						<div class="invalid-feedback" id="idError">아이디를 입력한뒤, 중복확인을
							해주세요.</div>

					</div>

					<div class="mb-3">
						<label for="pw">비밀번호</label> <input type="password"
							class="form-control" name="userpass" id="pw" placeholder="" required />
						<div class="invalid-feedback" id="pwError">4자리 이상 12자리 이하의
							비밀번호를 입력하세요.</div>
					</div>

					<div class="mb-3">
						<label for="pwconfim">비밀번호 확인</label> <input type="password"
							class="form-control" id="pwconfirm" placeholder="" required />
						<div class="invalid-feedback" id="pwConfirmError">비밀번호가 일치하지
							않습니다.</div>
					</div>

					<div class="mb-3">
						<label for="name">이름</label> <input type="text"
							class="form-control" name="username" id="username" placeholder="" required />
						<div class="invalid-feedback" id="nameError">이름을 알맞게
							입력해주세요(2~4)글자</div>
					</div>

					<div class="row">
						<div class="mb-3">
							<label for="root">이메일</label> <input type="text"
								class="form-control" name="email" id="email" placeholder="you@example.com"
								required />
							<div class="invalid-feedback">이메일을 알맞게 입력해주세요</div>
						</div>
					</div>
					<hr class="mb-4" />
					<div class="custom-control custom-checkbox">
						<input type="checkbox" class="custom-control-input" id="aggrement"
							required /> <label class="custom-control-label" for="aggrement">개인정보
							수집 및 이용에 동의합니다.</label>
						<div class="invalid-feedback">개인정보 수집 동의를 해주세요</div>
					</div>
					<div class="mb-4"></div>
					<button class="btn btn-outline-primary btn-lg btn-block"
						type="submit">가입 완료</button>
					<button class="btn btn-outline-secondary btn-lg btn-block"
						id="cancelbtn" style="margin-left: 20px">
						가입 취소</button>
				</form>
			</div>
		</div>
		<footer class="my-3 text-center text-small">
			<div class="col">
				<ul class="navbar-nav">
					<li><a href="#" class="nav-link">개인정보처리방침</a></li>
					<li><a href="#" class="nav-link">이용약관</a></li>
					<li><a href="#" class="nav-link">오시는길</a></li>
					<li><label class="nav-link">&copy; SSAFY Corp.</label></li>
				</ul>
			</div>
			<!-- 하단 footer end -->
		</footer>
	</div>

	<script>
      let isDuplicateChecked = false;
      window.addEventListener(
        "load", function() {
          const forms = document.getElementsByClassName("validation-form");

          Array.prototype.filter.call(forms, function(form) {
            form.addEventListener(
              "submit",
              function (event) {
                if (form.checkValidity() === false) {
                  event.preventDefault();
                  event.stopPropagation();
                }

                form.classList.add("was-validated");
              },
              false
            );
          });
        },
        false
      );

      // 아이디 입력 및 중복 확인검사
      let isUseId = false;
      let input = document.querySelector("#userid");
      let resultDiv = document.querySelector("#idError");
      input.addEventListener("keyup", function () {
        let checkid = input.value;
        let len = checkid.length;
        if (len < 4 || len > 16) {
          isUseId = false;
          resultDiv.setAttribute("class", "mb-3 fw-bold text-dark");
          resultDiv.innerHTML = "아이디는 4자이상 16자이하입니다.";
        } else {
          let url = "${root}/member/idcheck/" + checkid;

          console.log(url);
          
          fetch(url)
          .then((response) => response.text())
          .then((data) => resultViewText(data, checkid));
          
        }
      });
      
      function resultViewText(data, id) {
          if (data == 0) {
            isUseId = true;
            resultDiv.setAttribute("class", "mb-3 text-success");
            resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 있습니다.";
          } else {
            isUseId = false;
            resultDiv.setAttribute("class", "mb-3 text-danger");
            resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 없습니다.";
          }
        }


      document.addEventListener("DOMContentLoaded", function () {
        const pwInput = document.getElementById("pw");
        const pwError = document.getElementById("pwError");

        pwInput.addEventListener("keyup", function () {
          const pwValue = pwInput.value;
          if (pwValue.length < 4 || pwValue.length > 12) {
            pwInput.classList.add("is-invalid"); // 수정: 클래스 추가
            pwInput.classList.remove("is-valid"); // 추가: 유효한 입력 표시 제거
            pwError.style.display = "block";
          } else {
            pwInput.classList.remove("is-invalid");
            pwInput.classList.add("is-valid"); // 추가: 유효한 입력 표시
            pwError.style.display = "none";
          }

          const pwConfirmInput = document.getElementById("pwconfirm");
          const pwConfirmError = document.getElementById("pwConfirmError");

          // 비밀번호 확인 input 값이 변경될 때 비밀번호와 일치하는지 확인합니다.
          pwConfirmInput.addEventListener("keyup", function () {
            const pwValue = pwInput.value;
            const pwConfirmValue = pwConfirmInput.value;

            if (pwValue !== pwConfirmValue) {
              pwConfirmInput.classList.add("is-invalid");
              pwConfirmInput.classList.remove("is-valid"); // 추가: 유효한 입력 표시 제거
              pwConfirmError.style.display = "block";
            } else {
              pwConfirmInput.classList.remove("is-invalid");
              pwConfirmInput.classList.add("is-valid"); // 추가: 유효한 입력 표시
              pwConfirmError.style.display = "none";
            }
          });
        });
      });

      // 이름 확인
      document.addEventListener("DOMContentLoaded", function () {
        const nameInput = document.getElementById("username");
        const nameError = document.getElementById("nameError");

        nameInput.addEventListener("input", function () {
          const nameValue = nameInput.value;
          const namePattern = /^[가-힣]{2,4}$/; // 한글만 허용

          if (!namePattern.test(nameValue)) {
            nameInput.classList.remove("is-valid");
            nameInput.classList.add("is-invalid");
            nameError.style.display = "block";
          } else {
            nameInput.classList.remove("is-invalid");
            nameInput.classList.add("is-valid");
            nameError.style.display = "none";
          }
        });
      });

      // 이메일 확인
      document.addEventListener("DOMContentLoaded", function () {
        const emailInput = document.getElementById("email");
        const emailError = document.getElementById("emailError");

        emailInput.addEventListener("input", function () {
          const emailValue = emailInput.value;
          const emailPattern = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;

          if (!emailPattern.test(emailValue)) {
            emailInput.classList.remove("is-valid");
            emailInput.classList.add("is-invalid");
            emailError.style.display = "block";
          } else {
            emailInput.classList.remove("is-invalid");
            emailInput.classList.add("is-valid");
            if (emailError) {
              emailError.style.display = "none";
            }
          }
        });
      });

      document.addEventListener("DOMContentLoaded", function () {
        const submitButton = document.querySelector("button[type='submit']");
        const forms = document.getElementsByClassName("validation-form");

        let isFormSubmitted = false; // 폼이 한 번 제출되었는지 여부를 나타내는 플래그
        
        Array.prototype.filter.call(forms, (form) => {
          form.addEventListener(
            "submit",
            function (event) {
            	
                if (isFormSubmitted) {
                    // 폼이 이미 한 번 제출되었다면 다시 제출을 막음
                    event.preventDefault();
                    return;
                }
            	
              // 각 입력 필드의 유효성 검사 결과를 가져옵니다.
              const isIdValid = isFieldValid("userid");
              const isPwValid = isFieldValid("pw");
              const isPwConfirmValid = isFieldValid("pwconfirm");
              const isNameValid = isFieldValid("username");
              const isEmailValid = isFieldValid("email");

              // 초기 입력 상태를 확인합니다.
              const isIdBlank = document.getElementById("userid").value.trim() === "";
              const isPwBlank = document.getElementById("pw").value.trim() === "";
              const isPwConfirmBlank = document.getElementById("pwconfirm").value.trim() === "";
              const isNameBlank = document.getElementById("username").value.trim() === "";
              const isEmailBlank = document.getElementById("email").value.trim() === "";

              // 개인정보 수집 및 이용에 동의하는 체크박스 상태를 가져옵니다.
              const aggrementCheckbox = document.getElementById("aggrement");
              const isAggrementChecked = aggrementCheckbox.checked;

              // 필드가 공백인 경우 폼 제출을 막습니다.
              if (isIdBlank || isPwBlank || isPwConfirmBlank || isNameBlank || isEmailBlank) {
                alert("모든 입력 필드를 작성해주세요.");
                event.preventDefault(); // 폼 제출 막음
              }
              // 모든 필드가 유효한 경우에만 가입을 처리합니다.
              else if (
                isIdValid &&
                isPwValid &&
                isPwConfirmValid &&
                isNameValid &&
                isEmailValid &&
                isAggrementChecked
              ) {
            	  if (isUseId) {
            		  
            		  isFormSubmitted = true;
            		  
            		  const idInput = document.getElementById("userid").value;
            		  const pwInput = document.getElementById("pw").value;
            		  const nameInput = document.getElementById("username").value;
            		  const emailInput = document.getElementById("email").value;

            		  console.log("importante name" + nameInput);
            		  
            		  const formData = new FormData();
            		  formData.append("userid", idInput);
            		  formData.append("userpass", pwInput);
            		  formData.append("username", nameInput);
            		  formData.append("email", emailInput);
            		  
            		  console.log("idInput : " + idInput);
            		  console.log("userpass : " + pwInput);
            		  console.log("username : " + nameInput);
            		  console.log("email : " + emailInput);
            		  

            		  fetch("${root}/member/join", {
            		    method: "POST", // 또는 "GET", "PUT" 등 HTTP 메서드 선택
            		    body: formData, // FormData 객체를 전송할 데이터로 사용
            		  })
            		    .then((response) => response.text())
            		    .then((data) => {
            		      // 서버 응답을 처리하거나 필요한 동작을 수행
            		      // 예: 가입이 완료되었을 때의 처리
            		      console.log("가입 성공:", data);
            		      window.location.href = "${root}/member/login"; // 가입이 성공한 후 이동할 페이지
            		    })
            		    .catch((error) => {
            		      // 오류 처리
            		      console.error("가입 오류:", error);
            		    });
            		} else {
            		  alert("아이디 중복 확인이 필요합니다.");
            		  event.preventDefault(); // 폼 제출 막음
            		}
              } else {
                event.preventDefault(); // 유효하지 않은 경우 폼 제출을 막습니다.
              }
              form.classList.add("was-validated");
            },
            false
          );
        });
      });

      document.addEventListener("DOMContentLoaded", function () {
        const cancelButton = document.getElementById("cancelbtn");

        cancelButton.addEventListener("click", function () {
          event.preventDefault();
          window.location.href = "${root}/";
        });
      });

      // 가입완료 버튼을 눌렀을때... 확인하는 함수...
      function isFieldValid(fieldId) {
        const fieldInput = document.getElementById(fieldId);
        const fieldError = document.getElementById(`${fieldId}Error`);

        // 유효성 검사를 수행합
        if (fieldId === "userid") {
          // ID 필드의 유효성 검사 로직
          // 예: 중복확인을 했는지 검사
          if (!isDuplicateChecked) {
            fieldInput.classList.add("is-invalid");
            fieldInput.classList.remove("is-valid");
            fieldError.style.display = "block";
            return false;
          }
        } else if (fieldId === "pw") {
          // pw 필드의 유효성 검사 로직
          const pwValue = fieldInput.value;
          if (pwValue.length < 4 || pwValue.length > 12) {
            fieldInput.classList.add("is-invalid");
            fieldInput.classList.remove("is-valid");
            fieldError.style.display = "block";
            return false;
          }
        } else if (fieldId === "pwconfirm") {
          // pwconfirm 필드의 유효성 검사 로직
          const pwValue = document.getElementById("pw").value;
          const pwConfirmValue = fieldInput.value;
          if (pwValue !== pwConfirmValue) {
            fieldInput.classList.add("is-invalid");
            fieldInput.classList.remove("is-valid");
            fieldError.style.display = "block";
            return false;
          }
        } else if (fieldId === "username") {
          // name 필드의 유효성 검사 로직
          const nameValue = fieldInput.value;
          const namePattern = /^[가-힣]{2,4}$/;
          if (!namePattern.test(nameValue)) {
            fieldInput.classList.add("is-invalid");
            fieldInput.classList.remove("is-valid");
            fieldError.style.display = "block";
            return false;
          }
        } else if (fieldId === "email") {
          // email 필드의 유효성 검사 로직
          const emailValue = fieldInput.value;
          const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
          if (!emailPattern.test(emailValue)) {
            fieldInput.classList.add("is-invalid");
            fieldInput.classList.remove("is-valid");
            fieldError.style.display = "block";
            return false;
          }
        }

        // 유효성 검사 통과한 경우 유효한 필드로 표시합니다.
        fieldInput.classList.remove("is-invalid");
        fieldInput.classList.add("is-valid");
        if (fieldError) {
          fieldError.style.display = "none";
        }
        return true;
      }
    </script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
