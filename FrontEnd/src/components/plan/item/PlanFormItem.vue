<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { listAttractions } from "@/api/map";
import { registerPlan, modifyPlan, detailPlan } from "@/api/plan";

import VKakaoMap from "@/components/common/VKakaoMap.vue";

import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import draggable from "vuedraggable";

const memberStore = useMemberStore();
const userInfo = ref(memberStore.userInfo);
const user_id = ref(userInfo.value == null ? null : userInfo.value.user_id);

const router = useRouter();
const route = useRoute();

const props = defineProps({
  type: String,
});

const attractions = ref([]);
const selectAttraction = ref({});

const days = ref(0);

const courses = ref([]);

const param = ref({
  sido_code: 0,
  gugun_code: 0,
  content_type_id: [12, 14, 15, 25, 28, 32, 38, 39],
  title: "",
  user_id: user_id,
});

const plan = ref({
  plan_no: 0,
  title: "",
  start_date: "",
  end_date: "",
  user_id: userInfo.value.user_id,
  courses: [],
});

onMounted(() => {});

if (props.type === "modify") {
  let { plan_no } = route.params;
  detailPlan(
    plan_no,
    ({ data }) => {
      plan.value = data;
      days.value = getDateDiff(new Date(plan.value.start_date), new Date(plan.value.end_date));
      let c = Array.from(Array(days.value), () => []);
      plan.value.courses.forEach((course) => {
        c[course.date].push(course.attraction);
      });
      console.log("zzzzzzzz");
      console.log(c);
      courses.value = c;
      console.log(courses.value);
    },
    (error) => {
      console.log(error);
    }
  );
}

const selectedStartDate = ref("");
const selectedEndDate = ref("");

const getAttractions = () => {
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
    return;
  }
  //
  console.log("관광지 정보 api 호출!");
  console.log(param.value);

  listAttractions(
    param.value,
    ({ data }) => {
      attractions.value = data;
      console.log(attractions.value);
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeStartDate = () => {
  console.log("시작일 변경!!!");

  if (plan.value.start_date === "") {
    days.value = 0;
    return;
  }

  if (plan.value.end_date === "") {
    return;
  }

  let start_date = new Date(plan.value.start_date);
  let end_date = new Date(plan.value.end_date);

  if (start_date > end_date) {
    alert("여행 시작일은 여행 종료일보다 느릴 수 없습니다.");
    plan.value.start_date = selectedStartDate.value;
    return;
  }
  days.value = getDateDiff(start_date, end_date);
  courses.value = Array.from(Array(days.value), () => []);
  selectedStartDate.value = plan.value.start_date;
};

const onChangeEndDate = () => {
  console.log("종료일 변경!!!");
  if (plan.value.end_date === "") {
    days.value = 0;
    return;
  }

  if (plan.value.start_date === "") {
    return;
  }

  let start_date = new Date(plan.value.start_date);
  let end_date = new Date(plan.value.end_date);

  if (start_date > end_date) {
    alert("여행 종료일은 여행 시작일보다 빠를 수 없습니다.");
    plan.value.end_date = selectedEndDate.value;
    return;
  }
  days.value = getDateDiff(start_date, end_date);
  courses.value = Array.from(Array(days.value), () => []);
  selectedEndDate.value = plan.value.end_date;
};

const getDateDiff = (start_date, end_date) => {
  let diff = end_date.getTime() - start_date.getTime();
  diff = Math.ceil(diff / (1000 * 60 * 60 * 24) + 1);
  console.log(diff);
  return diff;
};

// 여행지 검색 조건
const titleErrMsg = ref("");
watch(
  () => param.value.title,
  (value) => {
    let len = value.trim().length;
    if (len == 0) {
      titleErrMsg.value = "여행지 검색어를 확인해 주세요!!!";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);

const convert = computed(() => {
  console.log("convert!!!!!!!!");
  let result = [];
  courses.value.forEach((course, i) => {
    course.forEach((c, j) => {
      console.log("i=", i, "j=", j);
      console.log(c);
      let obj = {};
      obj.date = i;
      obj.orders = j;
      obj.attraction = c;
      result.push(obj);
    });
  });
  console.log(result);
  return result;
});

const onSubmit = () => {
  plan.value.courses = convert;
  props.type === "regist" ? writePlan() : updatePlan();
};

const writePlan = () => {
  console.log("Plan 등록!!!", plan.value);
  registerPlan(
    plan.value,
    (response) => {
      console.log(response);
      alert("plan 등록 성공!");
      moveList();
    },
    (error) => {
      console.log(error);
      console.log("plan 등록 실패!");
    }
  );
};

const updatePlan = () => {
  console.log(plan.value.plan_no + "번 Plan 수정!!!");
  console.log(plan.value);
  modifyPlan(
    plan.value,
    (response) => {
      console.log(response);
      alert("plan 수정 성공!");
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveList = () => {
  router.push({ name: "plan-list" });
};

const removeAt = (i, j) => {
  console.log(i, j);
  courses.value[i].splice(j, 1);
};

const dragOptions = computed(() => {
  return { animation: 200, group: "course", disabled: false, ghostClass: "ghost" };
});

const onChangeAttractions = (val) => {
  attractions.value = val;
};
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <div class="row">
        <div class="col-2 mh-100">
          <div class="mb-3">
            <h4 class="my-0 py-0 shadow-sm bg-light">
              <mark class="sky">여행 Plan {{ type === "regist" ? "등록" : "수정" }}하기</mark>
            </h4>
          </div>
          <div class="mb-3">
            <div class="row g-3 align-items-center">
              <div class="col-auto">
                <label for="title" class="form-label">제목</label>
              </div>
              <div class="col">
                <input
                  type="text"
                  class="form-control"
                  id="title"
                  v-model="plan.title"
                  placeholder="제목..."
                />
              </div>
            </div>
          </div>
          <div class="mb-3">
            <div class="row g-3 align-items-center">
              <div class="col-auto">
                <label for="start_date" class="form-label">여행 시작일</label>
              </div>
              <div class="col">
                <input
                  type="date"
                  class="form-control"
                  id="start_date"
                  @change="onChangeStartDate"
                  v-model="plan.start_date"
                />
              </div>
            </div>
          </div>
          <div class="mb-3">
            <div class="row g-3 align-items-center">
              <div class="col-auto">
                <label for="end_date" class="form-label">여행 종료일</label>
              </div>
              <div class="col">
                <input
                  type="date"
                  class="form-control"
                  id="end_date"
                  @change="onChangeEndDate"
                  v-model="plan.end_date"
                />
              </div>
            </div>
          </div>
          <div class="mb-3" style="height: 450px">
            <div class="overflow-auto mh-100">
              <template v-for="day in days" :key="day">
                <div class="border p-3">
                  <h3>{{ day }}일차</h3>
                  <draggable
                    tag="ul"
                    :list="courses[day - 1]"
                    class="list-group"
                    group="course"
                    v-bind="dragOptions"
                    item-key="content_id"
                  >
                    <template #item="{ element, index }">
                      <li class="list-group-item">
                        <h6>{{ element.title }}</h6>
                        <span>{{ element.addr }}</span>
                        <button type="button" class="btn ms-2" @click="removeAt(day - 1, index)">
                          X
                        </button>
                      </li>
                    </template>
                  </draggable>
                </div>
              </template>
            </div>
          </div>
        </div>
        <div class="col-8 p-0 mh-100">
          <v-kakao-map
            :search="true"
            :courses="convert"
            :attractions="attractions"
            :selectAttraction="selectAttraction"
            @onChangeAttractions="onChangeAttractions"
          ></v-kakao-map>
        </div>
        <div class="col-2 mh-100">
          <div class="m-3">
            <form class="d-flex" @submit.prevent="getAttractions">
              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.title"
                  placeholder="여행지 검색..."
                />
                <button class="btn" type="button" @click="getAttractions">검색</button>
              </div>
            </form>
          </div>
          <div class="mh-100 overflow-auto">
            <draggable
              class="list-group"
              :list="attractions"
              :group="{ name: 'course', pull: 'clone', put: false }"
              item-key="content_id"
            >
              <template #item="{ element }">
                <div class="list-group-item">
                  <h6>{{ element.title }}</h6>
                  <span>{{ element.addr }}</span>
                </div>
              </template>
            </draggable>
          </div>
        </div>
      </div>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn mb-3" v-if="type === 'regist'">등록</button>
      <button type="submit" class="btn mb-3" v-if="type === 'modify'">수정</button>
      <button type="button" class="btn mb-3 ms-1" @click="moveList">목록으로이동</button>
    </div>
  </form>
</template>

<style scoped></style>
