<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import BoardMainDetailItem from './item/BoardMainDetailItem.vue';


const router = useRouter();
const route = useRoute();

const { article_no } = route.params;
const board = ref({})

onMounted(() => {
    loadDetail();
})

const loadDetail = () => {
    detailArticle(
        article_no,
        ({ data }) => {
            console.log("boardMainDetail {}",data);
            board.value = data; 
        },
        (error) => {
            console.log(error);
        }
    )
}

const moveList = () => {
  router.push({ name: "board-main" });
};

const onDeleteArticle = () => {
    deleteArticle(
        article_no,
        (response) => {
            console.log("게시글 삭제!")
            alert("게시글이 삭제 되었습니다!");
            moveList();
        },
        (error) => {
            console.log(error);
        }
    )
}


</script>

<template>
    <div id="containner">
        <div>
            <div style='display: flex; justify-content: space-between; align-items: center;'>
                <h3 style='margin: 0;'>여행후기 공유</h3>
            </div>
            <hr>
        </div>
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
    <h4 style="margin: 0;">{{ board.user_id }}</h4>
    <div style="display: flex; align-items: center;">
        <h5 style="margin-right: 10px;">조회수 :  {{ board.hit }}  |  </h5>
        <h5>{{ board.register_time }}</h5>
    </div>
</div>  
        <section class="py-5 text-center container">
            <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel" bis_skin_checked="1">
                <div class="carousel-inner" bis_skin_checked="1">
                    <BoardMainDetailItem v-for="fileInfos in board.fileInfos" :fileInfos="fileInfos" :key="fileInfos.idx">
                        
                    

                    </BoardMainDetailItem>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </section>

        <div style='margin-bottom: 40px;'>
            {{ board.content }}
        </div>
        <div style="display: flex; justify-content: flex-end;">
            <button type="button" class="btn mb-3" @click="moveList">목록</button>
            <button type="button" class="btn mb-3 ms-1" @click="onDeleteArticle">삭제</button>
        </div>


        <h3>댓글</h3>
        <hr>
        <!-- Comments section-->
        <section class="mb-5">
        <div class="card bg-light">
            <div class="card-body">
                <!-- Comment form-->
                <form class="mb-4"><textarea class="form-control" rows="3" placeholder="Join the discussion and leave a comment!"></textarea></form>
                <!-- Comment with nested comments-->
                <div class="d-flex mb-4">
                    <!-- Parent comment-->
                    <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
                    <div class="ms-3">
                        <div class="fw-bold">작성자</div>
                        댓글
                    </div>
                </div>
            </div>
        </div>
        </section>
    </div>
</template>

<style scoped>

#containner {
  margin-left: 40px;
  margin-right: 40px;
  padding: 20px; /* Add padding here */
}
</style>