package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.utils.Constants;
import com.viettel.nfv.vdashboardx.utils.DataUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;

public class ConflictHttp implements IHttpResponse {

    @Override
    public ResultBO toResultData(ResultBO resultBo, HttpResponseParam param) {
        resultBo.setError(true);
        resultBo.setErrorCode(Constants.N_OK);
        String mes = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_CONFLICT), param.getJsonError());
        if (mes.equals("")) {
            if (StringUtils.isNotEmpty(param.getMessageError().getMes409())) {
                resultBo.setMessage(param.getMessageError().getMes409());
            } else {
                resultBo.setMessage("False!");
            }
        } else {
            resultBo.setMessage(mes);
        }

        return resultBo;
    }
}
