package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;

public class OkHttp implements IHttpResponse {

    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setErrorCode(Constants.OK);
        resultBo.setError(false);
        resultBo.setMessage("Success!");
        resultBo.setResponsedData(param.getTruylyResponseJson());

        return resultBo;
    }

}
