<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
  article_no: String,
});

const emits = defineEmits(['registMemo']);

const article_no = props.article_no;

const memo = ref({
  article_no: article_no,
  comment: "",
  memo_no: 0,
  memo_time: "",
  user_id: "",
});

const commentErrMsg = ref("");
watch(
  () => memo.value.comment,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      commentErrMsg.value = "내용을 확인해 주세요!!!";
    } else commentErrMsg.value = "";
  },
  { immediate: true }
);

const onSubmit = () => {
  if (commentErrMsg.value) {
    alert(commentErrMsg.value);
  } else {
    console.log('댓글 쓰러 가자!!');
    console.log(memo.value);
    emits('registMemo', memo.value);
    memo.value.user_id = '';
    memo.value.comment = '';
  }
};
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class='d-flex flex-column mb-3'>
      <label class="form-label">댓글 쓰기</label>
      <input type="text" class="form-control mb-3" v-model="memo.user_id" placeholder="작성자ID..." />
      <textarea class="form-control mb-3" v-model='memo.comment' rows="7"></textarea>
      <button type="submit" class="btn mb-3">등록</button>
    </div>
  </form>
</template>

<style scoped></style>
