import { localAxios, fileAxios } from "@/util/http-commons";

const local = localAxios();
const url = "/member";

const file = fileAxios();
const file_url = "/file";

async function userConfirm(param, success, fail) {
  await local.post(`${url}/login`, param).then(success).catch(fail);
}

async function findById(user_id, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`${url}/info/${user_id}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`${url}/refresh`, user).then(success).catch(fail);
}

async function logout(user_id, success, fail) {
  await local.get(`${url}/logout/${user_id}`).then(success).catch(fail);
}

async function registProfile(params, success, fail) {
  // 파일 데이터를 FormData로 변경
  const formData = new FormData();
  formData.append("imgInfo", params.imgInfo);

  // dto데이터를 FormData로 변경
  formData.append("fileDto", JSON.stringify(params.fileDto));

  // axios
  file.post(`${file_url}/register`, formData).then(success).catch(fail);
}

async function getImg(saveFolder, originalName, saveFile, success, fail) {
  await local
    .get(`${url}/getImg/${saveFolder}/${originalName}/${saveFile}`)
    .then(success)
    .catch(fail);
}

async function getFileInfo(user_id, success, fail) {
  await local.get(`${file_url}/getFileInfo/${user_id}`).then(success).catch(fail);
}

// 미사용
async function getModify(user_id, success, fail) {
  await local.get(`${url}/modify/${user_id}`).then(success).catch(fail);
}

async function modify(memberDto, success, fail) {
  console.log("호출됨");
  await local.put(`${url}/modify`, JSON.stringify(memberDto)).then(success).catch(fail);
}

async function join(user, success, fail) {
  await local.post(`${url}/join`, JSON.stringify(user)).then(success).catch(fail);
}

async function modify_pw(user_id, user_pass, success, fail) {
  await local
    .put(`${url}/modify_pw`, null, {
      params: {
        user_id: user_id,
        user_pass: user_pass,
      },
    })
    .then(success)
    .catch(fail);
}

async function idCheck(user_id, success, fail) {
  await local.get(`${url}/idcheck/${user_id}`).then(success).catch(fail);
}

async function idDelete(user_id, success, fail) {
  await local.delete(`${url}/delete/${user_id}`).then(success).catch(fail);
}

export {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  getModify,
  join,
  modify_pw,
  modify,
  idCheck,
  idDelete,
  registProfile,
  getImg,
  getFileInfo,
};
