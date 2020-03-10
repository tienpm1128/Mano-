package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;
import com.viettel.nfv.vdashboardx.utils.DataUtils;

import java.net.HttpURLConnection;

public class NotFoundHttp implements IHttpResponse {

    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setError(true);
        resultBo.setErrorCode(Constants.N_OK);
        String messageError = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND), param.getJsonError());
        if (messageError.equals("")) {
            resultBo.setMessage(Constants.HTTP_NOT_FOUND);
        } else {
            resultBo.setMessage(messageError);
        }

        return resultBo;
    }
}
