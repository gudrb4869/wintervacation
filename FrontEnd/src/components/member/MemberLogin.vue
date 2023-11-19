<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  user_id: "",
  user_pass: "",
});

const login = async () => {
  await userLogin(loginUser.value);
  if (memberStore.isLogin) {
	let token = sessionStorage.getItem("accessToken");
    getUserInfo(token);
    changeMenuState();
	router.push("/");
  } else {
    alert("아이디와 비밀번호를 확인해 주세요");
  }
};

const goToJoin = () => {
  router.push("/member/join");
};


</script>

<template>
	<div class="text-center">
		<div
			style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1;">
			<!--  html 전체 영역을 지정하는 container -->
			<div id="container">
				<!--  login 폼 영역을 : loginBox -->
				<form id="form-login" method="POST" action="">
					<input type="hidden" name="action" value="member">
					<div id="loginBox"
						class="border border-2 border-secondary border-radius"
						style="padding: 20px; border-radius: 10px">
						<!-- 로그인 페이지 타이틀 -->
						<div id="loginBoxTitle">Login</div>
						<!-- 아이디, 비번, 버튼 박스 -->
						<div id="inputBox">
							<div class="input-form-box">
								<span>아이디 </span>
                                <input type="text" name="user_id" id="user_id"
                                    v-model="loginUser.user_id"
                                    placeholder="  아이디..."
									class="form-control" />
							</div>
							<div class="input-form-box">
								<span>비밀번호 </span><input type="password" name="userpwd"
									id="userpwd" class="form-control" 
                                    v-model="loginUser.user_pass"
									placeholder="  비밀번호..."
                                    @keyup.enter="login"/>
							</div>
							<div class="button-login-box">
								<button type="button" class="btn btn btn-outline-primary btn-xs"
									id="login_btn" style="width: 100%; margin-top: 10px" @click="login">
									로그인</button>
								<button type="button"
										class="btn btn btn-outline-secondary btn-xs"
										style="width: 100%; margin-top: 10px"
										@click="goToJoin">
									회원가입
								</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

  </template>

<style scoped>
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