import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

// Axios로 배열 파라미터 전송시 [] 빼고 보내는 기본 설정
axios.defaults.paramsSerializer = function (paramObj) {
  const params = new URLSearchParams();
  for (const key in paramObj) {
    params.append(key, paramObj[key]);
  }

  return params.toString();
};

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

function fileAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "multipart/form-data;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios, fileAxios };
