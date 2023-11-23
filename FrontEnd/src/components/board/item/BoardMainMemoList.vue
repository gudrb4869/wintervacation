<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { getImg, getFileInfo } from "@/api/user";

const memberStore = useMemberStore();
const profile = ref(null);

const props = defineProps({
  memo: Object,
});

const { memo } = props;
const Fileinfo = ref(null);
const path = ref("");

const emits = defineEmits(["deleteMemo"]);

const deleteMemo = () => {
  console.log(memo.memo_no + "번 댓글 삭제 및 부모 컴포넌트 호출");
  emits("deleteMemo", memo.memo_no);
};

const loadProfil = async () => {
  console.log("초기 프로필 로드 아이디 : ", memo.user_id);
  getFileInfo(
    memo.user_id,
    ({ data }) => {
      console.log("불러온 프로필 정보 : {}", data);
      Fileinfo.value = data;
      if (data.saveFile === undefined) {
        path.value = window.location.origin + "/src/assets/img/no_img.jpg";
      } else {
        console.log("프로필 가져오기 : ", Fileinfo.value.saveFile);
        getImg(
          Fileinfo.value.saveFolder,
          Fileinfo.value.originalFile,
          Fileinfo.value.saveFile,
          ({ data }) => {
            path.value =
              "http://localhost/file/getImg/" +
              Fileinfo.value.saveFolder +
              "/" +
              Fileinfo.value.originalFile +
              "/" +
              Fileinfo.value.saveFile;
          },
          (error) => {
            console.log("프로필 이미지 로딩 에러");
            console.log(error);
          }
        );
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

onMounted(() => {
    console.log(memberStore.userInfo);
    profile.value = memberStore.userInfo;
    loadProfil();
})

</script>

<template>
    <div class="d-flex mb-4">
        <!-- Parent comment-->
        <div
                id="profile"
                style="width: 50px; height: 50px"
              >
                <img
                  class="bd-placeholder-img card-img-top"
                  id="img"
                  :src="path"
                  alt="이미지가 없습니다!"
                />
              </div>
        <div class="ms-3">
            <div class="fw-bold">{{ memo.user_id }} | {{ memo.memo_time }}</div> 
            
            {{ memo.comment }}
        </div>
        <div class="ms-3" v-if="profile && memo.user_id === profile.user_id">
            <p @click="deleteMemo">삭제</p>
        </div>

    </div>
</template>

<style scoped>
#profile {
  border: 1px solid lightgray;
  border-radius: 50%; /* 반지름이 전체의 50%이면 원형이 됨 */
  height: 100px;
  width: 100px;
  overflow: hidden; /* border-radius 적용 후 초과된 부분을 잘라냄 */
  margin-right: 20px;
}
</style>