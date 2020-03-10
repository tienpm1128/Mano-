package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;

public class UnauthorizedHttp implements IHttpResponse {
    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setError(true);
        resultBo.setErrorCode(Constants.N_OK_401);
        resultBo.setMessage(Constants.MESS_NO_PERMISSION);

        return resultBo;
    }
}
