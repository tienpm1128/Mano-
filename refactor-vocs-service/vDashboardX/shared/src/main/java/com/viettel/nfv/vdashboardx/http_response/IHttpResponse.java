package com.viettel.nfv.vdashboardx.http_response;

import com.viettel.nfv.vdashboardx.response.ResultBO;

public interface IHttpResponse {

    ResultBO toResultData(ResultBO resultBo, HttpResponseParam param);

}
