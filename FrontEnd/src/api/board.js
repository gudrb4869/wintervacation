import { localAxios, fileAxios } from "@/util/http-commons";

const local = localAxios();
const file = fileAxios();

const url = "/board-api";

function listArticle(param, success, fail) {
    local.get(`${url}/list`, { params: param }).then(success).catch(fail);
}

function getImg( saveFolder, originalName, saveFile, success, fail) {
    local.get(`${url}/getImg/${saveFolder}/${originalName}/${saveFile}`)
        .then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    local.get(`${url}/view/${articleno}`).then(success).catch(fail);
}

function registArticle(params, success, fail) {
    // 파일 데이터를 FormData로 변경
    const formData = new FormData();
    for (let i = 0; i < params.imgInfos.length; i++) {
      formData.append("imgInfos", params.imgInfos[i]);
    }
    // dto데이터를 FormData로 변경
    formData.append("boardDto", JSON.stringify(params.boardDto));
  
    // axios
    file.post(`${url}/register`, formData).then(success).catch(fail);
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
    getImg,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
  };