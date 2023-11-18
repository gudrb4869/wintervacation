import { localAxios } from "@/util/http-commons";

const local = localAxios();
const url = "/member";

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

async function getModify(user_id, success, fail) {
  await local.get(`${url}/modify/${user_id}`).then(success).catch(fail);
}

async function modify(user, success, fail) {
  await local.put(`${url}/modify`, JSON.stringify(user)).then(success).catch(fail);
}

async function join(user, success, fail) {
  await local.post(`${url}/join`, JSON.stringify(user)).then(success).catch(fail);
}


// async function modify_pw(user_id, user_pass, success, fail) {
//   await local.put(`${url}`).then(success).catch(fail);
// }

async function idCheck(user_id, success, fail) {
  await local.get(`${url}/idcheck/${user_id}`).then(success).catch(fail);
}

async function idDelete(user_id, success, fail) {
  await local.delete(`${url}/delete/${user_id}`).then(success).catch(fail);
}

export {
  userConfirm, findById, tokenRegeneration, logout, getModify, join,
modify, idCheck, idDelete};
