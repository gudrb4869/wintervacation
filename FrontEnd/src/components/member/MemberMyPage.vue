<script setup>
import { ref, computed } from 'vue';
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const profile = ref(null);
profile.value = memberStore.userInfo;

const password = ref('');
const confirmPassword = ref('');

// 가입일을 원하는 형식으로 가공하는 computed 프로퍼티
const formattedJoinDate = computed(() => {
  const joinDate = profile.value ? profile.value.join_date : null;

  // joinDate가 null 또는 undefined가 아니고, 비어 있지 않을 때만 처리
  if (joinDate !== null && joinDate !== undefined && joinDate.trim() !== '') {
    // 예시: '2023-11-18 00:11:20'에서 '2023-11-18' 부분만 추출
    return joinDate.split(' ')[0];
  }
  return ''; // joinDate가 null이거나 undefined이거나 빈 문자열이면 빈 문자열 반환
});
</script>


<template>
    <div id='total'>
  <div id="container">
    <!-- 왼쪽 사이드에 프로필 사진 -->
    <div id='container2'>
      <div id="profil">
        <!-- 여기에 프로필 사진을 넣거나 스타일을 추가하세요 -->
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
      <button @click="handleButton1">비밀번호 변경</button>
      <button @click="handleButton2">탈퇴</button>
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
