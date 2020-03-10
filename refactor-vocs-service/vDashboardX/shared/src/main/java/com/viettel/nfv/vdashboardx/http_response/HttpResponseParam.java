package com.viettel.nfv.vdashboardx.http_response;


import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.simple.JSONObject;

@Data
@AllArgsConstructor
public class HttpResponseParam {

    private Object truylyResponseJson;

    private JSONObject jsonError;

    private ErrHttpCode messageError;

}
