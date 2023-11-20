<script setup>
import { ref, computed } from 'vue';
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";
import { modify_pw, idDelete } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

const menuStore = useMenuStore();
const memberStore = useMemberStore();
const { userLogout } = memberStore;
const { changeMenuState } = menuStore;
const router = useRouter();

const profile = ref(null);
profile.value = memberStore.userInfo;

const password = ref('');
const confirmPassword = ref('');

// 가입일을 원하는 형식으로 가공하는 computed 프로퍼티
const formattedJoinDate = computed(() => {
  const joinDate = profile.value ? profile.value.join_date : null;

  // joinDate가 null 또는 undefined가 아니고, 비어 있지 않을 때만 처리
  if (joinDate !== null && joinDate !== undefined && joinDate.trim() !== '') {
    return joinDate.split(' ')[0];
  }
  return '';
});


const loadProfil = async () => {
  
}

const changePass = async () => {
  if (password.value == confirmPassword.value) {
    await modify_pw(
      profile.value.user_id,
      password.value,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          console.log("비밀번호 변경 성공");
  
          userLogout(profile.value.user_id);
          console.log("로그아웃!!!!");
          changeMenuState();
          router.push("/");
          alert("다시 로그인 해주세요!");
        } else {
          console.log("비밀번호 변경 실패");
          alert("잠시후 다시 시도해 해주세요!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  } else {
    alert("비밀 번호가 같은지 확인해 주세요");
  }
}

const withdrawal = async () => {
  await idDelete(
    profile.value.user_id,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log("회원 탈퇴 성공");
        changeMenuState();
        router.push("/");
        alert("회원탈퇴에 성공했습니다. 감사합니다.");
      } else {
        alert("회원탈퇴에 실패했습니다. 잠시후 다시 시도해주세요");
        console.log("회원 탈퇴 실패");
      }
    },
    (error) => {
      console.log(error);
    }
  )
}

</script>


<template>
    <div id='total'>
  <div id="container">
    <!-- 왼쪽 사이드에 프로필 사진 -->
    <div id='container2'>
      <div id="profil">

      </div>

      <!-- 프로필 정보 -->
      <!-- 아이디, 가입일, 이름을 프로필 사진 오른쪽에 위치 -->
      <div>
        <p><strong>아이디 : </strong> {{ profile.user_id }}</p>
        <p><strong>가입일 : </strong> {{ formattedJoinDate }}</p>
        <p><strong>이름 : </strong> {{ profile.user_name }}</p>
      </div>
    </div>

    <div id='container3'>
      <div>
        <label for="password">비밀번호 변경 : </label>
        <br>
        <input type="password" id="password" v-model="password" />
      </div>

      <div>
        <label for="confirmPassword">비밀번호 확인 : </label>
        <br>
        <input type="password" id="confirmPassword" v-model="confirmPassword" />
      </div>
    </div>

    <div id='buttons'>
      <button @click="changePass">비밀번호 변경</button>
      <button @click="withdrawal">탈퇴</button>
    </div>
  </div>

  <br>
  <br>
  <p style='font-size:30px'>내 작성 글</p>
  <hr style='margin-top: -12px;'>

  <br>
  <br>
  <p style='font-size:30px'>찜 목록</p>
  <hr style='margin-top: -12px;'>
    </div>
</template>

<style scoped>
#total {
    margin: 20px;
}

#container {
  display: flex;
  margin-left: auto;
  margin-right: auto;
  border: 1px solid gray;
  border-radius: 10px;
  padding: 20px;
  width: 800px;
}

#container2 {
  display: flex;
  flex-direction: row; /* 가로 방향으로 나열 */
  align-items: flex-start; /* 상단 정렬 */
}

#container3 {
  display: flex;
  flex-direction: column; /* 세로 방향으로 나열 */
  align-items: flex-start; /* 좌측 정렬 */
  margin-left: 60px;
}

#profil {
  border: 1px solid lightgray;
  border-radius: 100%;
  height: 100px;
  width: 100px;
  margin-right: 20px; /* 프로필 사진과 아이디, 가입일, 이름(성별) 사이의 간격을 조절 */
}

input {
  border-radius: 10px;
  background-color: ivory;
  width: 250px;
}

#buttons {
  display: flex;
  flex-direction: row; /* 가로 방향으로 나열 */
  margin-top: 10px; /* 버튼과의 간격을 조절 */
  margin-left: 20px;
}

button {
    margin: 10px;
}
</style>
