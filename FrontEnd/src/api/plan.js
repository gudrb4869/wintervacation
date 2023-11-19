import { localAxios } from '@/util/http-commons';

const local = localAxios();

const url = '/plan';

function listPlan(param, success, fail) {
    local.get(`${url}`, { params: param }).then(success).catch(fail);
}

export {
    listPlan,
}