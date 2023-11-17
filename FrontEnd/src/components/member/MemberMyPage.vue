<script setup>
import { ref } from 'vue';

const profile = {
  id: 'your_id',
  joinDate: '2023-11-17', // 가입일자를 실제로 사용하는 데이터로 변경하세요
  name: 'Your Name',
  gender: 'Male', // 성별을 실제로 사용하는 데이터로 변경하세요
};

const password = ref('');
const confirmPassword = ref('');
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
      <!-- 아이디, 가입일, 이름(성별)을 프로필 사진 오른쪽에 위치 -->
      <div>
        <p><strong>아이디 : </strong> {{ profile.id }}</p>
        <p><strong>가입일 : </strong> {{ profile.joinDate }}</p>
        <p><strong>이름(성별) : </strong> {{ profile.name }} ({{ profile.gender }})</p>
      </div>
    </div>

    <!-- 비밀번호 변경 입력 창 -->
    <div id='container3'>
      <div>
        <label for="password">비밀번호 변경 : </label>
        <br>
        <input type="password" id="password" v-model="password" />
      </div>

      <!-- 비밀번호 확인 입력 창 -->
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
  margin-left: 20px; /* container2와의 간격을 조절 */
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
