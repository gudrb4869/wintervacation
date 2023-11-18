<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { join } from "@/api/user"

const router = useRouter();
const user_id = ref("");
const user_pass = ref("");
const user_name = ref("");
const email = ref("");

const user = ref({
    user_id: "",
    user_name: "",
    user_pass: "",
    email: "",
    birth_date: "",
    gender: "",
    address: "",
    join_date: "",
    salt: "",
    token: "",
})

const userJoin = () => {
    console.log("회원가입! ", user.value);
    join(
        user.value,
        (response) => {
            console.log(response);
            moveLogin();
        },
        (error) => {
            console.log(error);
        }
    )
}
const moveLogin = () => {
    router.push({ name: "member-login" });
}

const goToIndex = () => {
  router.push("/");
};

</script>

<template>
	<div class="container">
		<div class="input-form-backgroud row">
			<div class="input-form col-md-12 mx-auto">
				<h4 class="mb-3">회원가입</h4>
				<form @submit.prevent="userJoin">
					<div class="mb-3">
                        <label for="name">아이디</label>
                        <input type="text" class="form-control" name="userid" id="userid" placeholder="" v-model="user.user_id" required />
                        <div class="invalid-feedback" id="idError"></div>
					</div>

					<div class="mb-3">
						<label for="pw">비밀번호</label> <input type="password"
							class="form-control" name="userpass" id="pw" placeholder="" v-model="user.user_pass" required />
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
							class="form-control" name="username" id="username" placeholder="" v-model="user.user_name" required />
						<div class="invalid-feedback" id="nameError">이름을 알맞게
							입력해주세요(2~4)글자</div>
					</div>

					<div class="row">
						<div class="mb-3">
							<label for="root">이메일</label> <input type="text"
								class="form-control" name="email" id="email" placeholder="you@example.com"
								v-model="user.email" required />
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
                    <button class="btn btn-outline-secondary btn-lg btn-block" @click="goToIndex" id="cancelbtn" style="margin-left: 20px">
                        가입 취소
                    </button>
				</form>
			</div>
		</div>
	</div>

</template>

<style scoped>
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