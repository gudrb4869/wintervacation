<script setup>
import { ref, onMounted } from "vue";
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

onMounted(() => {
  getSeason();
})

const state = ref(0);

const getSeason = () => {
  let current = new Date();

let monthDate = [0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365];
for (let i = 1; i <= 12; i++) {
  monthDate[i] += monthDate[i - 1];
}
// 2월 4일 (입춘) => 34
// 5월 5일 (입하) => 124
// 8월 7일 (입추) => 218
// 11월 7일 (입동) => 310
let currentDate = monthDate[current.getMonth()] + (current.getDate() - 1);
if (currentDate >= 34 && currentDate < 124) {
  state.value = 1
} else if (currentDate >= 124 && currentDate < 218) {
  state.value = 2
} else if (currentDate >= 218 && currentDate < 310) {
  state.value = 3
} else {
  state.value = 4
}
}

</script>

<template>
  <!-- 상단 navbar start -->
  <nav class="navbar navbar-expand-lg bg-light navbar-light shadow fixed-top">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'main' }">
        <div v-if="state === 1">
          <img src="@/assets/img/spring_logo.png" style="width: 200px; height: 80px" />
        </div>
        <div v-else-if='state === 2'>
          <img src="@/assets/img/summer_logo.png" style="width: 200px; height: 80px" />
        </div>
        <div v-else-if='state === 3'>
          <img src="@/assets/img/autom_logo.png" style="width: 200px; height: 80px" />
        </div>
        <div v-else-if='state === 4'>
          <img src="@/assets/img/winter_logo.png" style="width: 200px; height: 80px" />
        </div>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
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
            <router-link class="nav-link" :to="{ name: 'plan' }">Plan</router-link>
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
