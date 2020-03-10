package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;

public class NoContentHttp implements IHttpResponse {

    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setErrorCode(Constants.OK);
        resultBo.setError(false);
        resultBo.setMessage(param.getMessageError().getMes204());

        return resultBo;
    }
}
