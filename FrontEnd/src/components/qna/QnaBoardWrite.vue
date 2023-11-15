<script setup>
import QnaBoardFormItem from "@/components/qna/item/QnaBoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">Q&A 글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12 text-start">
        <qna-board-form-item type="regist"></qna-board-form-item>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
