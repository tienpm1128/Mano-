package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;

public class NoValidLoginVim implements IHttpResponse {

    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setError(true);
        resultBo.setErrorCode(Constants.N_OK);
        resultBo.setMessage(Constants.MESS_400_LOGIN_VIM);

        return resultBo;
    }

}
