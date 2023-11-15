import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/qna-memo";

function listMemo(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function registMemo(memo, success, fail) {
  local.post(`${url}`, JSON.stringify(memo)).then(success).catch(fail);
}

function deleteMemo(memo_no, success, fail) {
  local.delete(`${url}/${memo_no}`).then(success).catch(fail);
}

export { listMemo, registMemo, deleteMemo };
