<script setup>
import BoardFormItem from './item/BoardFormItem.vue';
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">여행후기 글수정</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12 text-start">
        <board-form-item type="modify"></board-form-item>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
