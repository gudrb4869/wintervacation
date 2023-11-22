import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/favorite";

function listFavorite(user_id, success, fail) {
  console.log(user_id);
  local.get(`${url}/${user_id}`).then(success).catch(fail);
}

function getFavorite(param, success, fail) {
  local.get(`${url}/${plan_no}`, { params: param }).then(success).catch(fail);
}

function registerFavorite(favorite, success, fail) {
  local.post(`${url}`, JSON.stringify(favorite)).then(success).catch(fail);
}

function deleteFavorite(param, success, fail) {
  local.delete(`${url}`, { params: param }).then(success).catch(fail);
}

export { listFavorite, getFavorite, registerFavorite, deleteFavorite };
