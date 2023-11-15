<script setup>
defineProps({
  article: Object,
});
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.article_no }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'qna-board-view', params: { article_no: article.article_no } }"
        class="article-title link-dark"
        :data-no="article.article_no"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.user_id }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.register_time }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
