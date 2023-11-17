<script setup>
import { ref, watch } from "vue";
const props = defineProps({ switchOption: Array });
const emit = defineEmits(["onChangeSwitch"]);

const checkedOptions = ref([]);

watch(
  () => checkedOptions.value,
  () => {
    console.log("클릭!!!");
    console.log(checkedOptions.value);
    emit("onChangeSwitch", checkedOptions.value);
  },
  { deep: true }
);
</script>

<template>
  <div v-for="option in switchOption" :key="option.value" class="form-check form-switch me-4">
    <input
      class="form-check-input"
      type="checkbox"
      role="switch"
      :value="option.value"
      v-model="checkedOptions"
    />
    <label class="form-check-label" for="flexSwitchCheckDefault">{{ option.text }}</label>
  </div>
</template>

<style scoped></style>
