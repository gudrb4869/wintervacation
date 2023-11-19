<script setup>
import { ref } from "vue";
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const { userLogout } = memberStore;

const toLogout = () => {
  userLogout(memberStore.userInfo.user_id);
  console.log("로그아웃!!!!");
  changeMenuState();
};

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
            <router-link class="nav-link" :to="{ name: 'attractions' }">지역별여행지</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'board' }">여행후기공유</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'qna' }">Plan</router-link>
          </li>
          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'member-logout'">
                <li class="nav-item">
                  <router-link to="/" @click.prevent="toLogout" class="nav-link">{{
                    menu.name
                  }}</router-link>
                </li>
              </template>
              <template v-else>
                <li class="nav-item">
                  <router-link :to="{ name: menu.routeName }" class="nav-link">{{
                    menu.name
                  }}</router-link>
                </li>
              </template>
            </template>
          </template>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
