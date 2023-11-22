import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/board-memo";

function memoList(article_no, success, fail) {
    local.get(`${url}`, { params: { article_no } }).then(success).catch(fail);
}


function writeMemo(memoDto, success, fail) {
    local.post(`${url}`, JSON.stringify(memoDto)).then(success).catch(fail);
}

function deleteMemo(memo_no, success, fail) {
    local.delete(`${url}/${memo_no}`).then(success).catch(fail);
}

export {
    memoList,
    writeMemo,
    deleteMemo
};