<script setup>
import { ref, onMounted } from "vue";
import { detailPlan, deletePlan } from "@/api/plan";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import IconClipBoard from "@/components/icons/IconClipBoard.vue";

import { useRoute, useRouter } from "vue-router";

const router = useRouter();
const route = useRoute();

const currentUrl = ref(window.location.href);

const { plan_no } = route.params;

const attractions = ref([]);
const selectAttraction = ref({});

const days = ref(0);

const courses = ref([]);

const plan = ref({
  plan_no: 0,
  title: "",
  start_date: "",
  end_date: "",
  courses: [],
});

onMounted(() => {
  getPlan();
});

const getDateDiff = (start_date, end_date) => {
  let diff = end_date.getTime() - start_date.getTime();
  diff = Math.ceil(diff / (1000 * 60 * 60 * 24) + 1);
  console.log(diff);
  return diff;
};

const getPlan = () => {
  console.log(plan_no + "번 plan 조회!!!");
  detailPlan(
    plan_no,
    ({ data }) => {
      console.log(data);
      plan.value = data;
      days.value = getDateDiff(new Date(plan.value.start_date), new Date(plan.value.end_date));
      courses.value = Array.from(Array(days.value), () => []);
      plan.value.courses.forEach((course) => {
        courses.value[course.date].push(course);
      });
      console.log("zzzzzzzz");
      console.log(plan.value);
      console.log("zzzzzzzz");
      console.log(courses.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveList = () => {
  router.push({ name: "plan-list" });
};

const moveModify = () => {
  console.log(plan_no + "번 여행 계획글 수정 페이지로 이동!!!");
  router.push({ name: "plan-modify", params: { plan_no } });
};

const onDeletePlan = () => {
  console.log(plan_no + "번 여행 계획글 삭제!!!");
  // API 호출
  deletePlan(
    plan_no,
    (response) => {
      console.log(response);
      alert("삭제 성공!");
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const handleCopy = (e) => {
  navigator.clipboard
    .writeText(currentUrl.value)
    .then(() => alert("웹링크 복사에 성공했습니다!"))
    .catch(() => alert("웹링크 복사에 실패했습니다!"));
};
</script>

<template>
  <div class="container-fluid mt-4">
    <div class="mt-3 text-center">
      <div class="mb-3">
        <div class="row">
          <div class="col-3 mh-100">
            <div class="mb-3">
              <div class="row g-3 align-items-center">
                <div class="col-auto">
                  <label for="title" class="form-label">제목 : </label>
                </div>
                <div class="col">
                  <input
                    type="text"
                    class="form-control"
                    id="title"
                    v-model="plan.title"
                    placeholder="제목..."
                    readonly="readOnly"
                  />
                </div>
              </div>
            </div>
            <div class="mb-3">
              <div class="row g-3 align-items-center">
                <div class="col-auto">
                  <label for="start_date" class="form-label">여행 시작일 : </label>
                </div>
                <div class="col">
                  <input
                    type="date"
                    class="form-control"
                    id="start_date"
                    v-model="plan.start_date"
                    readonly="readOnly"
                  />
                </div>
              </div>
            </div>
            <div class="mb-3">
              <div class="row g-3 align-items-center">
                <div class="col-auto">
                  <label for="end_date" class="form-label">여행 종료일 : </label>
                </div>
                <div class="col">
                  <input
                    type="date"
                    class="form-control"
                    id="end_date"
                    v-model="plan.end_date"
                    readonly="readonly"
                  />
                </div>
              </div>
            </div>
            <div class="mb-3">
              <div class="row g-3 align-items-center">
                <div class="col-auto">
                  <icon-clip-board />
                </div>
                <div class="col">
                  <input class="form-control" type="url" v-model="currentUrl" readonly="readOnly" />
                </div>
                <div class="col-auto">
                  <button type="button" class="btn" @click="handleCopy">웹 링크 복사</button>
                </div>
              </div>
            </div>
            <div class="mb-3" style="height: 480px">
              <div class="overflow-auto mh-100">
                <template v-for="day in days" :key="day">
                  <div class="border p-3">
                    <h3>{{ day }}일차</h3>
                    <ul class="list-group">
                      <li class="list-group-item" v-for="course in courses[day - 1]">
                        <h6>{{ course.attraction.title }}</h6>
                        <span>{{ course.attraction.addr }}</span>
                      </li>
                    </ul>
                  </div>
                </template>
              </div>
            </div>
          </div>
          <div class="col-9 p-0 mh-100">
            <v-kakao-map
              :search="false"
              :courses="plan.courses"
              :attractions="attractions"
              :selectAttraction="selectAttraction"
            ></v-kakao-map>
          </div>
        </div>
      </div>
      <div class="col-auto text-center">
        <button type="button" class="btn mb-3" @click="moveModify">수정</button>
        <button type="button" class="btn mb-3 ms-1" @click="onDeletePlan">삭제</button>
        <button type="button" class="btn mb-3 ms-1" @click="moveList">목록으로이동</button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
