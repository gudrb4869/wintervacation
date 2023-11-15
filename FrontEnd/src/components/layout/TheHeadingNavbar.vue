<script setup>
import { ref } from "vue";
const userinfo = ref(null);
</script>

<template>
  <!-- 상단 navbar start -->
  <nav class="navbar navbar-expand-lg bg-light navbar-light shadow fixed-top">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'main' }">
        <img src="@/assets/img/logo.png" style="width: 150px; height: 60px" />
      </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#collapsibleNavbar"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <a class="nav-link" href="/map/map">지역별여행지</a>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'board' }">여행후기공유</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'qna' }">Q&A게시판</router-link>
          </li>
          <template v-if="userinfo == null">
            <li class="nav-item">
              <a class="nav-link" href="/member/login">로그인</a>
            </li>
          </template>
          <template v-if="userinfo != null">
            <li class="nav-item">
              <a class="nav-link" href="/member/logout">로그아웃</a>
            </li>
            <li class="nav-item dropdown" id="myPage">
              <a
                class="nav-link dropdown-toggle"
                href="/member/myPage.jsp"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                마이페이지
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="/member/modify">회원정보 수정</a></li>
              </ul>
            </li>
          </template>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
