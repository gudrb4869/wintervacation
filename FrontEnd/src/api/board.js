import { localAxios, fileAxios } from "@/util/http-commons";

const local = localAxios();
const fileAxios2 = fileAxios();

const url = "/board-api";

function listArticle(param, success, fail) {
    local.get(`${url}/list`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    local.get(`${url}/view/${articleno}`).then(success).catch(fail);
}

async function registArticle(boardDto, file, success, fail) {
    
    const formData = new FormData();

    // formData.append("imgInfos", file);
    for (let i = 0; i < file.length; i++) {
        formData.append("imgInfos", file[i]);
    }

    // dto데이터를 FormData로 변경
    formData.append("boardDto", JSON.stringify(boardDto));

    await fileAxios2.post(`${url}/register`, formData).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
    local.get(`${url}/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    local.put(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}
  
function deleteArticle(articleno, success, fail) {
    local.delete(`${url}/${articleno}`).then(success).catch(fail);
}

export {
    listArticle,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
  };