<script setup>
/* global kakao */
import { ref, watch, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { registerFavorite, deleteFavorite } from "@/api/favorite";
import { listSido, listGugun, listAttractions } from "@/api/map";

import VSelect from "@/components/common/VSelect.vue";
import VSwitch from "@/components/common/VSwitch.vue";

const memberStore = useMemberStore();
const userInfo = ref(memberStore.userInfo);
const user_id = ref(userInfo.value == null ? null : userInfo.value.user_id);

var map;
var polyline;
const positions = ref([]);
const coursePositions = ref([]);
const lines = ref([]);
const markers = ref([]);
const courseMarkers = ref([]);
const overlays = ref([]);

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const contentTypeList = ref([
  { text: "관광지", value: "12" },
  { text: "문화시설", value: "14" },
  { text: "축제공연행사", value: "15" },
  { text: "여행코스", value: "25" },
  { text: "레포츠", value: "28" },
  { text: "숙박", value: "32" },
  { text: "쇼핑", value: "38" },
  { text: "음식점", value: "39" },
]);
const attractions = ref([]);

const props = defineProps({
  search: Boolean,
  courses: Array,
  attractions: Array,
  selectAttraction: Object,
});

const emit = defineEmits(["onChangeAttractions"]);

const param = ref({
  sido_code: 0,
  gugun_code: 0,
  content_type_id: [],
  user_id: user_id.value,
});

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    // autoload=false 꼭 설정해주기
    // clusterer -> 마커많을때 하나로 합쳐주는기능
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
  getSidoList();
});

const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sido_name, value: sido.sido_code });
      });
      sidoList.value = options;
      param.value.gugun_code = 0;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeSido = (val) => {
  listGugun(
    { sido_code: val },
    ({ data }) => {
      param.value.gugun_code = 0;
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugun_name, value: gugun.gugun_code });
      });
      gugunList.value = options;
      getAttractions();
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeGugun = (val) => {
  console.log("구군 변경!");
  getAttractions();
};

const onChangeContentType = (val) => {
  console.log("콘텐츠 변경!");
  console.log(val);
  param.value.content_type_id = val;
  console.log(param.value);
  getAttractions();
};

const getAttractions = () => {
  console.log("관광지 정보 api 호출!");
  listAttractions(
    param.value,
    ({ data }) => {
      attractions.value = data;
      emit("onChangeAttractions", attractions.value);
      console.log(attractions.value);
    },
    (err) => {
      console.log(err);
    }
  );
};

const onAddFavorite = (content_id) => {
  if (!user_id.value) {
    alert("로그인한 사용자만 찜 등록 가능!");
    return false;
  }
  let favorite = { user_id: user_id.value, content_id };
  registerFavorite(
    favorite,
    (response) => {
      console.log(response);
      console.log("찜목록 추가 성공!!!");
    },
    (error) => {
      console.log(error);
      console.log("찜목록 추가 실패!!!");
    }
  );
  return true;
};

const onRemoveFavorite = (content_id) => {
  if (!user_id.value) {
    alert("로그인한 사용자만 찜 제거 가능!");
    return false;
  }
  let favorite = { user_id: user_id.value, content_id };
  deleteFavorite(
    favorite,
    (response) => {
      console.log(response);
      console.log("찜목록 제거 성공!!!");
    },
    (error) => {
      console.log(error);
      console.log("찜목록 제거 실패!!!");
    }
  );
  return true;
};

watch(
  () => props.selectAttraction.value,
  () => {
    console.log("VKakaoMap 선택여행지 변경!!!");
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(
      props.selectAttraction.latitude,
      props.selectAttraction.longitude
    );

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true } // 객체나 배열 안에 있는 내용들이 바뀔때는 깊은 감시를 해야함.
);

watch(
  () => props.attractions.value,
  () => {
    console.log("VKakaoMap 여행지목록 변경!!!");
    positions.value = [];
    console.log(props.attractions);
    if (props.attractions.length > 0) {
      props.attractions.forEach((attraction) => {
        let obj = {};
        obj.content_id = attraction.content_id;
        obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
        obj.title = attraction.title;
        obj.content_type_id = attraction.content_type_id;
        obj.addr = attraction.addr;
        obj.image = attraction.image;
        obj.overview = attraction.overview;
        obj.favorite_date = attraction.favorite_date;
        positions.value.push(obj);
      });
    }
    loadMarkers(positions, markers);
  },
  { deep: true } // 객체나 배열 안에 있는 내용들이 바뀔때는 깊은 감시를 해야함.
);

watch(
  () => props.courses.value,
  () => {
    console.log("VKakaoMap 여행경로 변경!!!");
    lines.value = [];
    coursePositions.value = [];
    deleteLines();
    console.log(props.courses);
    if (props.courses.length > 0) {
      props.courses.forEach((course) => {
        let obj = {};
        obj.content_id = course.attraction.content_id;
        obj.latlng = new kakao.maps.LatLng(course.attraction.latitude, course.attraction.longitude);
        obj.title = course.attraction.title;
        obj.content_type_id = course.attraction.content_type_id;
        obj.addr = course.attraction.addr;
        obj.image = course.attraction.image;
        obj.overview = course.attraction.overview;
        obj.favorite_date = course.attraction.favorite_date;
        coursePositions.value.push(obj);
        lines.value.push(
          new kakao.maps.LatLng(course.attraction.latitude, course.attraction.longitude)
        );
      });
    }
    loadMarkers(coursePositions, courseMarkers);
    loadLines();
  },
  { deep: true }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};

const loadMarkers = (positions, markers) => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers(markers);

  // 마커를 생성합니다
  markers.value = [];
  if (positions.value.length === 0) return;
  positions.value.forEach((position, idx) => {
    var color;
    switch (position.content_type_id) {
      case 12: // 관광지
        color = "skyblue";
        break;
      case 14: // 문화시설
        color = "orange";
        break;
      case 15: // 축제공연행사
        color = "blue";
        break;
      case 25: // 여행코스
        color = "green";
        break;
      case 28: // 레포츠
        color = "purple";
        break;
      case 32: // 숙박
        color = "yellow";
        break;
      case 38: // 쇼핑
        color = "pink";
        break;
      case 39: // 음식점
      default:
        color = "red";
    }
    // 마커 이미지를 생성합니다
    var imgSrc = window.location.origin + "/src/assets/img/marker-" + color + ".png";
    // 마커 이미지의 이미지 크기 입니다
    var imgSize = new kakao.maps.Size(27, 30);
    var markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);

    // 마커 위에 커스텀오버레이를 표시합니다
    // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
    var overlay = new kakao.maps.CustomOverlay({
      map: map,
      position: marker.getPosition(),
    });

    var content = document.createElement("div");
    content.className = "wrap";

    var info = document.createElement("div");
    info.className = "info";

    var title = document.createElement("div");
    title.className = "title";
    var name = document.createTextNode(position.title);
    var closeBtn = document.createElement("div");
    closeBtn.className = "close";
    closeBtn.title = "닫기";
    closeBtn.onclick = () => {
      overlay.setMap(null);
    };
    title.appendChild(name);
    title.appendChild(closeBtn);
    info.appendChild(title);

    var body = document.createElement("div");
    body.className = "body";

    var img = document.createElement("div");
    img.className = "img";
    var image = document.createElement("img");
    image.src = position.image
      ? position.image
      : window.location.origin + "/src/assets/img/no_image.png";
    image.width = "73";
    image.height = "70";
    img.appendChild(image);
    body.appendChild(img);

    var desc = document.createElement("div");
    desc.className = "desc";

    var ellipsis = document.createElement("div");
    ellipsis.className = "ellipsis";
    var address = document.createTextNode(position.addr);
    ellipsis.appendChild(address);
    desc.appendChild(ellipsis);

    body.appendChild(desc);
    info.appendChild(body);

    var overview = document.createElement("div");
    overview.className = "overview";

    var ellipsis2 = document.createElement("div");
    ellipsis2.className = "ellipsis";
    var description = document.createTextNode(position.overview);
    ellipsis2.appendChild(description);
    overview.appendChild(ellipsis2);

    info.appendChild(overview);

    var favorite = document.createElement("div");
    favorite.className = "favorite";

    var heartWhite = document.createElement("img");
    heartWhite.src = window.location.origin + "/src/assets/img/heart-white.png";
    heartWhite.width = "20";
    heartWhite.style.cursor = "pointer";

    var heartRed = document.createElement("img");
    heartRed.src = window.location.origin + "/src/assets/img/heart-red.png";
    heartRed.width = "20";
    heartRed.style.cursor = "pointer";

    if (position.favorite_date) {
      heartWhite.style.display = "none";
    } else {
      heartRed.style.display = "none";
    }

    heartWhite.onclick = () => {
      console.log("찜추가 버튼 클릭!!!");
      let flag = onAddFavorite(position.content_id);
      console.log(flag);
      if (flag) {
        console.log("하트 빨강으로 변경 성공");
        heartWhite.style.display = "none";
        heartRed.style.display = "inline-block";
      } else {
        console.log("하트 빨강으로 변경 실패");
      }
    };

    heartRed.onclick = () => {
      console.log("찜제거 버튼 클릭!!!");
      let flag = onRemoveFavorite(position.content_id);
      console.log(flag);
      if (flag) {
        console.log("하트 하양으로 변경 성공");
        heartRed.style.display = "none";
        heartWhite.style.display = "inline-block";
      } else {
        console.log("하트 빨강으로 변경 실패");
      }
    };

    favorite.appendChild(heartWhite);
    favorite.appendChild(heartRed);

    info.appendChild(favorite);

    content.appendChild(info);

    overlay.setContent(content);

    overlay.setMap(null);
    overlays.value.push(overlay);

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "click", () => {
      overlays.value.forEach((overlay) => overlay.setMap(null));
      overlay.setMap(map);
    });
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = (markers) => {
  console.log("marker 삭제");
  console.log(markers.value.length);
  if (markers.value.length === 0) return;
  markers.value.forEach((marker) => marker.setMap(null));
  console.log("marker제거완료");
};

const loadLines = () => {
  console.log("여행경로생성!!!");
  console.log(lines.value);

  if (lines.value.length === 0) return;

  polyline = new kakao.maps.Polyline({
    path: lines.value, // 선을 구성하는 좌표배열 입니다
    strokeWeight: 4, // 선의 두께 입니다
    strokeColor: "#0d6efd", // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: "solid", // 선의 스타일입니다
  });
  // // 지도에 선을 표시합니다
  polyline.setMap(map);

  console.log("sdfsdfds");

  const bounds = lines.value.reduce(
    (bounds, line) => bounds.extend(line),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteLines = () => {
  console.log("여행경로삭제이동!!!");
  if (polyline) {
    polyline.setMap(null);
    console.log("여행경로삭제완료!!!");
  }
};
</script>

<template>
  <div class="map_wrap">
    <div id="map"></div>
    <div v-if="search" class="search-box rounded-3">
      <span style="font-size: 1.25rem">관광지 검색</span>
      <div class="d-flex justify-content-center">
        <VSelect v-model="param.sido_code" :selectOption="sidoList" @onKeySelect="onChangeSido" />
        <VSelect
          v-model="param.gugun_code"
          :selectOption="gugunList"
          @onKeySelect="onChangeGugun"
        />
      </div>
      <div class="d-flex justify-content-evenly mb-3 row row-cols-4 px-4">
        <VSwitch :switchOption="contentTypeList" @onChangeSwitch="onChangeContentType"></VSwitch>
      </div>
    </div>
  </div>
</template>

<style>
/* style scoped로 설정하면 안됨 */
.map_wrap {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 700px;
}
#map {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 20px;
  width: 288px;
  height: 264px;
  margin-left: -144px;
  overflow: hidden;
  line-height: 1.5;
}

.wrap * {
  padding: 0;
  margin: 0;
}

.wrap .info {
  width: 286px;
  height: 250px;
  border: 1px solid #8493c9;
  border-radius: 5%;
  overflow: hidden;
  background: #fff;
}

.info .title {
  padding: 10px 0;
  height: 40px;
  background: #fff;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}

.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}

.info .close:hover {
  cursor: pointer;
}

.info .body {
  position: relative;
  overflow: hidden;
}

.info .desc {
  position: relative;
  margin: 13px 10px 0 90px;
  height: 75px;
}

.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}

.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}

.info .overview {
  position: relative;
  margin: 5px 10px;
  height: 75px;
  overflow-y: scroll;
}

.overview .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

.info .favorite {
  height: 40px;
  background: #fff;
  text-align: center;
}

.info .link {
  color: #5085bb;
}

.search-box {
  position: absolute;
  top: 10px;
  left: 10px;
  overflow: hidden;
  width: 600px;
  height: 150px;
  margin: 0;
  padding: 0;
  z-index: 1;
  background-color: #b9d3ed87;
  border: 1px solid #99b4cf;
}
</style>
