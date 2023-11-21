<script setup>
import { ref, watch, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const userInfo = ref(memberStore.userInfo);
const user_id = ref(userInfo.value.user_id);

var map;
var polyline;
const positions = ref([]);
const coursePositions = ref([]);
const lines = ref([]);
const markers = ref([]);
const courseMarkers = ref([]);
const overlays = ref([]);
// const courseOverlays = ref([]);

const props = defineProps({
  search: Boolean,
  courses: Array,
  attractions: Array,
  selectAttraction: Object,
});

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
    // deleteMarkers(markers.value);
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
    // deleteMarkers(courseMarkers.value);
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
});

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
  positions.value.forEach((position) => {
    // 마커 이미지를 생성합니다
    // const imgSrc = require("@/assets/map/markerStar.png");
    // 마커 이미지의 이미지 크기 입니다
    // const imgSrc = "https://t1.daumcdn.net/localimg/ocalimages/07/mapapidoc/markerStar.png";
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
        color = "red";
        break;
      default:
        color = "red";
        break;
    }
    var imgSrc = window.location.origin + "/src/assets/img/marker-" + color + ".png";
    // const imgSrc = "src/assets/img/marker.png";
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

    // 커스텀 오버레이에 표시할 컨텐츠 입니다
    // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
    // 별도의 이벤트 메소드를 제공하지 않습니다
    // const content = '<div class="wrap">' +
    //   '    <div class="info">' +
    //   '        <div class="title">' +
    //   '            카카오 스페이스닷원' +
    //   '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
    //   '        </div>' +
    //   '        <div class="body">' +
    //   '            <div class="img">' +
    //   '                <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
    //   '           </div>' +
    //   '            <div class="desc">' +
    //   '                <div class="ellipsis">제주특별자치도 제주시 첨단로 242</div>' +
    //   '                <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
    //   '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +
    //   '            </div>' +
    //   '        </div>' +
    //   '    </div>' +
    //   '</div>';
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

    // var link = document.createElement("div");
    // var a = document.createElement("a");
    // a.href = "https://www.kakaocorp.com/main";
    // a.target = "_blank";
    // a.className = "link";
    // a.appendChild(document.createTextNode("홈페이지"));
    // link.appendChild(a);
    // desc.appendChild(link);

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
    var name = document.createTextNode("찜하기");

    `<img
      :src="product.isFavorite ? 'src/assets/img/heart-red.png' : 'src/assets/img/heart-white.png'"
      :width="20"
      @click="changeProductFavorite(product.name)"
    />`;

    var heart = document.createElement("img");
    heart.src = window.location.origin + "/src/assets/img/heart-red.png";
    heart.width = "30";
    heart.height = "30";
    heart.onclick = () => {
      console.log("찜하기 버튼 클릭!!!");
      changeAttractionFavorite(position.content_id);
    };
    heart.style.cursor = "pointer";
    favorite.appendChild(heart);
    favorite.appendChild(name);

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
    <div v-if="search" class="search-box rounded-3 border border-dark">
      <span>관광지 검색</span>
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
  border: 1px solid black;
  /* box-shadow: 0px 1px 2px #888; */
  border-radius: 0px;
  /* border-bottom: 2px solid #ccc; */
  /* border-right: 1px solid #ccc; */
  overflow: hidden;
  background: #fff;
}

/* .wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
} */

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

/* .info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
} */

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
  background-color: #fff700;
}
</style>
