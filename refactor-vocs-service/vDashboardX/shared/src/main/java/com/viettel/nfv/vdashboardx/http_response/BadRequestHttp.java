package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;
import com.viettel.nfv.vdashboardx.utils.DataUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;

public class BadRequestHttp implements IHttpResponse {

    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setError(true);
        resultBo.setErrorCode(Constants.N_OK);
        String messBad = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST), param.getJsonError());
        if (StringUtils.isNotEmpty(messBad)) {
            resultBo.setMessage(messBad);
        } else {
            if (StringUtils.isNotEmpty(param.getMessageError().getMes400())) {
                resultBo.setMessage(param.getMessageError().getMes400());
            } else {
                resultBo.setMessage("False!");
            }
        }

        return resultBo;
    }
}
