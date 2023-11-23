<script setup>
import { ref, watch, watchEffect } from "vue";
import { useRouter } from "vue-router";
import { join, idCheck } from "@/api/user";

const router = useRouter();

// 유저 정보
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
});

// 비밀번호 확인
const confirmPassword = ref("");

const idErrorMessage = ref("");
const pwErrorMessage = ref("");
const pwConfirmErrorMessage = ref("");
const nameErrorMessage = ref("");
const emailErrorMessage = ref("");
const genderErrorMessage = ref("");
const addressErrorMessage = ref("");
const birthdayErrorMessage = ref("");

// 정규식 패턴
const namePattern = /^[가-힣]{2,4}$/;
const emailPattern = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;

watch(
  () => user.value.user_id,
  (value) => {
    let len = value.trim().length;
    if (len < 4 || len >= 16) {
      idErrorMessage.value = "아이디는 4자리 이상, 16자리 이하입니다.";
    } else {
      idCheck(
        value,
        (response) => {
          console.log(response.data);
          if (response.data === 0) {
            idErrorMessage.value = "";
          } else {
            idErrorMessage.value = "이미 존재하는 아이디 입니다.";
          }
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
);

watch(
  () => user.value.user_pass,
  (value) => {
    let len = value.trim().length;
    if (len < 4 || len > 12) {
      pwErrorMessage.value = "비밀번호는 4자리 이상 12자리 이하로 입력하세요.";
    } else {
      pwErrorMessage.value = "";
    }
  }
);

watch(
  () => confirmPassword.value,
  (value) => {
    if (user.value.user_pass !== value) {
      pwConfirmErrorMessage.value = "비밀번호가 일치하지 않습니다.";
    } else {
      pwConfirmErrorMessage.value = "";
    }
  }
);

watch(
  () => user.value.user_name,
  (value) => {
    if (!namePattern.test(value)) {
      nameErrorMessage.value = "이름을 2자 이상 4자 이하의 한글로 입력하세요.";
    } else {
      nameErrorMessage.value = "";
    }
  }
);

watch(
  () => user.value.gender,
  (value) => {
    if (!value) {
      genderErrorMessage.value = "성별을 선택하세요.";
    } else {
      genderErrorMessage.value = "";
    }
  }
);

watch(
  () => user.value.address,
  (value) => {
    if (!value) {
      addressErrorMessage.value = "주소를 입력하세요.";
    } else {
      addressErrorMessage.value = "";
    }
  }
);

watch(
  () => user.value.birth_date,
  (value) => {
    if (!value) {
      birthdayErrorMessage.value = "생일을 입력하세요.";
    } else {
      birthdayErrorMessage.value = "";
    }
  }
);

watch(
  () => user.value.email,
  (value) => {
    if (!emailPattern.test(value)) {
      emailErrorMessage.value = "이메일을 형식에 알맞게 입력해주세요";
    } else {
      emailErrorMessage.value = "";
    }
  }
);

const userJoin = () => {
  // 오류 메시지가 하나라도 있는지 확인
  if (
    idErrorMessage.value ||
    pwErrorMessage.value ||
    pwConfirmErrorMessage.value ||
    nameErrorMessage.value ||
    emailErrorMessage.value ||
    genderErrorMessage.value ||
    addressErrorMessage.value ||
    birthdayErrorMessage.value
  ) {
    // 오류 메시지가 있으면 가입을 중단하고 오류를 표시
    console.log("회원가입 실패: 입력값에 오류가 있습니다.");
  } else {
    // 오류 메시지가 없으면 회원가입을 진행
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
    );
  }
};

const moveLogin = () => {
  router.push({ name: "member-login" });
};

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
            <input
              type="text"
              class="form-control"
              placeholder=""
              v-model="user.user_id"
              required
            />
            <p>{{ idErrorMessage }}</p>
          </div>

          <div class="mb-3">
            <label for="pw">비밀번호</label>
            <input
              type="password"
              class="form-control"
              placeholder=""
              v-model="user.user_pass"
              required
            />
            <p>{{ pwErrorMessage }}</p>
          </div>

          <div class="mb-3">
            <label for="pwconfim">비밀번호 확인</label>
            <input
              type="password"
              class="form-control"
              placeholder=""
              v-model="confirmPassword"
              required
            />
            <p>{{ pwConfirmErrorMessage }}</p>
          </div>

          <div class="row">
            <!-- 왼쪽에 이름 입력란 -->
            <div class="col-md-6 mb-3">
              <label for="name">이름</label>
              <input
                type="text"
                class="form-control"
                id="name"
                placeholder=""
                v-model="user.user_name"
                required
              />
              <p>{{ nameErrorMessage }}</p>
            </div>

            <!-- 오른쪽에 성별 라벨과 라디오 버튼 -->
            <div class="col-md-6 mb-3">
              <label>성별</label>
              <div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="male"
                    value="male"
                    v-model="user.gender"
                  />
                  <label class="form-check-label" for="male">남성</label>
                </div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="female"
                    value="female"
                    v-model="user.gender"
                  />
                  <label class="form-check-label" for="female">여성</label>
                </div>
              </div>
              <p>{{ genderErrorMessage }}</p>
            </div>
          </div>
          <div class="mb-3">
            <label for="address">주소</label>
            <input
              type="text"
              class="form-control"
              placeholder="주소를 입력하세요"
              v-model="user.address"
              required
            />
            <p>{{ addressErrorMessage }}</p>
          </div>
          <div class="mb-3">
            <label for="birthday">생일</label>
            <input
              type="date"
              class="form-control"
              id="birthday"
              v-model="user.birth_date"
              required
            />
            <p>{{ birthdayErrorMessage }}</p>
          </div>
          <div class="row">
            <div class="mb-3">
              <label for="root">이메일</label>
              <input
                type="text"
                class="form-control"
                placeholder="you@example.com"
                v-model="user.email"
                required
              />
              <p>{{ emailErrorMessage }}</p>
            </div>
          </div>
          <hr class="mb-4" />
          <div class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" id="aggrement" required />
            <label class="custom-control-label" for="aggrement"
              >개인정보 수집 및 이용에 동의합니다.</label
            >
            <div class="invalid-feedback">개인정보 수집 동의를 해주세요</div>
          </div>
          <div class="mb-4"></div>
          <button class="btn btn-outline-primary btn-lg btn-block" type="submit">가입 완료</button>
          <button
            class="btn btn-outline-secondary btn-lg btn-block"
            @click="goToIndex"
            id="cancelbtn"
            style="margin-left: 20px"
          >
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
  background: -webkit-gradient(linear, left bottom, right top, from(#92b5db), to(#1d466c));
  background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
}

p {
  color: rgb(250, 75, 75);
}

.input-form {
  max-width: 680px;
  margin-top: 60px;
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
