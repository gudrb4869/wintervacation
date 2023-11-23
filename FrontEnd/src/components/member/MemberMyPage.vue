<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";
import { modify, idDelete, getImg, registProfile, modify_pw, getFileInfo } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";
import FavoriteListItem from "@/components/member/item/FavoriteListItem.vue";
import { listFavorite, deleteFavorite } from "@/api/favorite";

const menuStore = useMenuStore();
const memberStore = useMemberStore();
const { userLogout } = memberStore;
const { changeMenuState } = menuStore;
const router = useRouter();

const profile = ref(null);
profile.value = memberStore.userInfo;
console.log("마이페이지 정보 : {}", profile.value);
const path = ref("");

const password = ref("");
const confirmPassword = ref("");
const Fileinfo = ref(null);

// 정규식 패턴
const namePattern = /^[가-힣]{2,4}$/;
const emailPattern = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;

// 가입일을 원하는 형식으로 가공하는 computed 프로퍼티
const formattedJoinDate = computed(() => {
  const joinDate = profile.value ? profile.value.join_date : null;

  // joinDate가 null 또는 undefined가 아니고, 비어 있지 않을 때만 처리
  if (joinDate !== null && joinDate !== undefined && joinDate.trim() !== "") {
    return joinDate.split(" ")[0];
  }
  return "";
});

const loadProfil = async () => {
  console.log("초기 프로필 로드 아이디 : ", profile.value.user_id);
  getFileInfo(
    profile.value.user_id,
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

const changeinfo = async () => {
  if (!namePattern.test(profile.value.user_name)) {
    alert("이름을 2자 이상 4자 이하의 한글로 입력하세요.");
  } else if (!emailPattern.test(profile.value.email)) {
    alert("이메일을 형식에 알맞게 입력해주세요");
  } else {
    console.log("회원정보 수정 : {}", profile.value);
    await modify(
      profile.value,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          console.log("회원정보 수정 성공");
          userLogout(profile.value.user_id);
          console.log("로그아웃!!!!");
          changeMenuState();
          router.push("/");
          alert("다시 로그인 해주세요!");
        } else {
          console.log("회원정보 변경 실패");
          alert("잠시후 다시 시도해 해주세요!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const changePass = async () => {
  if (password.value == confirmPassword.value) {
    if (password.value.length < 4 || password.value.length > 12) {
      alert("비밀번호는 4자리 이상 12자리 이하로 입력하세요.");
    } else {
      await modify_pw(profile.value.user_id, password.value, (response) => {
        if (response.status === httpStatusCode.OK) {
          console.log("비밀번호 수정 성공");
          userLogout(profile.value.user_id);
          console.log("로그아웃!!!!");
          changeMenuState();
          router.push("/");
          alert("다시 로그인 해주세요!");
        } else {
          console.log("비밀번호 변경 실패");
          alert("잠시후 다시 시도해 해주세요!");
        }
      });
    }
  } else {
    alert("비밀번호가 다릅니다.");
  }
};

const withdrawal = async () => {
  await idDelete(
    profile.value.user_id,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log("회원 탈퇴 성공");
        changeMenuState();
        router.push("/");
        alert("회원탈퇴에 성공했습니다. 감사합니다.");
      } else {
        alert("회원탈퇴에 실패했습니다. 잠시후 다시 시도해주세요");
        console.log("회원 탈퇴 실패");
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const user_id = ref(profile.value.user_id);
const attractions = ref([]);

onMounted(() => {
  console.log("찜목록 가지고오기!!!");
  getFavoriteAttractionList();
  loadProfil();
});

const getFavoriteAttractionList = () => {
  console.log("찜한 관광지목록 가져오자!!!");
  listFavorite(
    user_id.value,
    ({ data }) => {
      console.log("목록 가져오기 성공!!!");
      console.log(data);
      attractions.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onDeleteFavorite = (val) => {
  console.log("찜한 관광지 " + val + "번 없애보자!!!");
  let param = {
    user_id: user_id.value,
    content_id: val,
  };
  console.log(param);
  deleteFavorite(
    param,
    (response) => {
      console.log(response);
      console.log(val + "번 관광지 찜목록에서 제거 성공!!!");
      getFavoriteAttractionList();
    },
    (error) => {
      console.log(error);
    }
  );
};

const fileDto = ref({
  idx: 0,
  article_no: 0,
  user_id: profile.value.user_id,
  saveFolder: "",
  originalFile: "",
  saveFile: "",
});

const handleFileChange = (event) => {
  const fileInput = event.target;
  const imgInfo = fileInput.files[0];

  const params = {
    fileDto: fileDto.value,
    imgInfo: imgInfo,
  };

  registProfile(
    params,
    (response) => {
      console.log(response);
      path.value = URL.createObjectURL(imgInfo);
    },
    (error) => {
      console.log(error);
    }
  );
};

const profilImgUpload = () => {
  console.log("클릭됨");
  let myInput = document.getElementById("my-file");
  myInput.click();
};
</script>

<template>
  <div id="total">
    <div class="container rounded mt-5 mb-5">
      <div class="col">
        <div class="row">
          <div class="col-md-3 d-flex justify-content-center align-items-start">
            <div class="d-flex flex-column justify-content-center text-center p-3 py-5">
              <div
                id="profile"
                class="align-self-center text-center mx-auto"
                style="width: 150px; height: 150px"
                @click="profilImgUpload"
              >
                <img
                  class="bd-placeholder-img card-img-top"
                  id="img"
                  :src="path"
                  alt="이미지가 없습니다!"
                />
              </div>
              <input
                type="file"
                class="form-control border"
                id="my-file"
                name="my-file"
                hidden
                @change="handleFileChange"
              />
              <span class="font-weight-bold" style="margin-top: 10px">{{ profile.user_id }}</span>
              <span class="text-black-50">{{ profile.email }}</span>
              <span> </span>
            </div>
          </div>
          <div class="col-md-3">
            <div class="p-3 py-5">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-right">Profile Settings</h4>
              </div>
              <div class="col-md-6">
                <label class="labels">이름</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder="이름"
                  v-model="profile.user_name"
                />
              </div>

              <div class="col-md-6">
                <label class="labels">이메일</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder="이메일"
                  v-model="profile.email"
                />
              </div>

              <div class="col-md-6">
                <label class="labels">생일</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder="생일"
                  v-model="profile.birth_date"
                  readonly
                />
              </div>

              <div class="col-md-6">
                <label class="labels">주소</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder="주소"
                  v-model="profile.address"
                />
              </div>

              <div class="col-md-6">
                <label class="labels">성별</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder="성별"
                  v-model="profile.gender"
                  readonly
                />
              </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="p-3 py-5">
              <div class="d-flex justify-content-between align-items-center experience">
                <h4 class="text-right">비밀번호 수정</h4>
              </div>
              <br />
              <div class="col-md-6" style="margin-left: 10px">
                <label class="labels">비밀번호</label>
                <input type="text" class="form-control" placeholder="비밀번호" v-model="password" />
              </div>

              <div class="col-md-6" style="margin-left: 10px">
                <label class="labels">비밀번호 확인</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder="비밀번호 확인"
                  v-model="confirmPassword"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="mx-3 d-flex justify-content-center" style="margin-top: 0px">
          <button class="btn btn-primary profile-button" type="button" @click="changeinfo">
            Save Profile
          </button>

          <button class="btn btn-primary profile-button" type="button" @click="withdrawal">
            회원탈퇴
          </button>

          <button class="btn btn-primary profile-button" type="button" @click="changePass">
            비밀번호 변경
          </button>
        </div>
      </div>
    </div>

    <br />
    <br />
    <p style="font-size: 30px">내 작성 글</p>
    <hr style="margin-top: -12px" />

    <br />
    <br />
    <p style="font-size: 30px">찜 목록</p>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-6 g-3">
      <favorite-list-item
        v-for="(attraction, index) in attractions"
        :key="index"
        :attraction="attraction"
        @onChangeFavorite="onDeleteFavorite"
      ></favorite-list-item>
    </div>
    <hr />
  </div>
</template>

<style scoped>
#total {
  margin: 20px;
}

.container {
  display: flex;
  margin-left: auto;
  margin-right: auto;
  border: 1px solid lightgray;
  border-radius: 10px;
  width: 1000px;
  background-color: rgba(175, 221, 248, 0.466);
}

#container2 {
  display: flex;
  flex-direction: row; /* 가로 방향으로 나열 */
  align-items: flex-start; /* 상단 정렬 */
}

#container3 {
  display: flex;
  flex-direction: column; /* 세로 방향으로 나열 */
  align-items: flex-start; /* 좌측 정렬 */
  margin-left: 60px;
}

#profile {
  border: 1px solid lightgray;
  border-radius: 50%; /* 반지름이 전체의 50%이면 원형이 됨 */
  height: 100px;
  width: 100px;
  overflow: hidden; /* border-radius 적용 후 초과된 부분을 잘라냄 */
  margin-right: 20px;
}

input {
  border-radius: 10px;
  background-color: ivory;
  width: 250px;
}

button {
  margin: 10px;
}

body {
  background: rgb(99, 39, 120);
}

.form-control:focus {
  box-shadow: none;
  border-color: #ba68c8;
}

.profile-button {
  background: rgb(136, 68, 163);
  box-shadow: none;
  border: none;
}

.profile-button:hover {
  background: #6827735b;
}

.profile-button:focus {
  background: #222020;
  box-shadow: none;
}

.profile-button:active {
  background: #4d4e9b;
  box-shadow: none;
}

.back:hover {
  color: #682773;
  cursor: pointer;
}

.labels {
  font-size: 18px;
}

.add-experience:hover {
  background: #ba68c8;
  color: #fff;
  cursor: pointer;
  border: solid 1px #ba68c8;
}
</style>
