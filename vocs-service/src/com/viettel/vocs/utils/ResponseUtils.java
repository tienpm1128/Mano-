package com.viettel.vocs.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.viettel.vocs.bo.ErrHttpCode;
import com.viettel.vocs.bo.ResponseBO;
import com.viettel.vocs.bo.ResultArrayBO;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.bo.ResultValueBO;

@Component
public class ResponseUtils {

	ErrHttpCode errMessCode;

	public ResultArrayBO resolveArray(ResponseBO reponse) {
		ResultArrayBO objectDTO = new ResultArrayBO();
		errMessCode = Constants.getErrHttpCode();
		String responseResult = "";
		if (reponse.getConnect() != null) {
			responseResult = getResponse(reponse);
		} else {
			if (reponse.getBody() != null) {
				responseResult = reponse.getBody();
			} else {
				responseResult = "";
			}
		}

		JSONArray jsonArr = null;
		JSONObject jsonObj = null;
		if (reponse.getErrorCode() != HttpURLConnection.HTTP_OK) {
			jsonObj = JsonUtils.ConvertStringToObject(responseResult);
		} else {
			jsonArr = JsonUtils.ConvertStringToJsonArray(responseResult);
		}

		switch (reponse.getErrorCode()) {
		case HttpURLConnection.HTTP_OK:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage("Success!");
			objectDTO.setData(jsonArr);
			break;
		case HttpURLConnection.HTTP_CREATED:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes201());
			objectDTO.setData(jsonArr);
			break;
		case HttpURLConnection.HTTP_ACCEPTED:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes202());
			break;
		case HttpURLConnection.HTTP_NO_CONTENT:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes204());
			break;
		case HttpURLConnection.HTTP_SEE_OTHER:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes303());
			break;
		case HttpURLConnection.HTTP_UNAUTHORIZED:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK_401);
			objectDTO.setMessage("Not permission! Please login again.");
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			String mess_bad = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST), jsonObj);
			if (StringUtils.isNotEmpty(mess_bad)) {
				objectDTO.setMessage(mess_bad);
			} else {
				if (StringUtils.isNotEmpty(errMessCode.getMes400())) {
					objectDTO.setMessage(errMessCode.getMes400());
				} else {
					objectDTO.setMessage("false!");
				}
			}
			break;
		case HttpURLConnection.HTTP_NOT_FOUND:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			String mes1 = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND), jsonObj);
			if (mes1.equals("")) {
				objectDTO.setMessage("Http not found");
			} else {
				objectDTO.setMessage(mes1);
			}
			break;
		case HttpURLConnection.HTTP_CONFLICT:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			String mes = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_CONFLICT), jsonObj);
			if (mes.equals("")) {
				if (StringUtils.isNotEmpty(errMessCode.getMes409())) {
					objectDTO.setMessage(errMessCode.getMes409());
				} else {
					objectDTO.setMessage("false!");
				}

			} else {
				objectDTO.setMessage(mes);
			}
			break;
		default:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			objectDTO.setMessage("false!");
			break;
		}
		return objectDTO;
	}

	public ResultObjectBO resolveObject(ResponseBO reponse) {
		ResultObjectBO objectDTO = new ResultObjectBO();
		errMessCode = Constants.getErrHttpCode();
		String responseResult = "";

		if (reponse.getConnect() != null) {
			responseResult = getResponse(reponse);
		} else {
			if (reponse.getBody() != null) {
				responseResult = reponse.getBody();
			} else {
				responseResult = "";
			}
		}

		JSONObject jsonObj = JsonUtils.ConvertStringToObject(responseResult);
		switch (reponse.getErrorCode()) {
		case HttpURLConnection.HTTP_OK:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage("Success!");
			objectDTO.setData(jsonObj);
			break;

		case HttpURLConnection.HTTP_CREATED:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes201());
			objectDTO.setData(jsonObj);
			break;

		case HttpURLConnection.HTTP_ACCEPTED:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes202());
			if (jsonObj != null) {
				objectDTO.setData(jsonObj);
			}
			break;

		case HttpURLConnection.HTTP_NO_CONTENT:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes204());
			break;

		case HttpURLConnection.HTTP_SEE_OTHER:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes303());
			break;

		case HttpURLConnection.HTTP_UNAUTHORIZED:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK_401);
			objectDTO.setMessage("Not permission! Please login again.");
			break;

		case HttpURLConnection.HTTP_BAD_REQUEST:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			String mess_bad = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST), jsonObj);
			if (StringUtils.isNotEmpty(mess_bad)) {
				objectDTO.setMessage(mess_bad);
			} else {
				if (StringUtils.isNotEmpty(errMessCode.getMes400())) {
					objectDTO.setMessage(errMessCode.getMes400());
				} else {
					objectDTO.setMessage("false!");
				}
			}
			break;

		case HttpURLConnection.HTTP_NOT_FOUND:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			String mes1 = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND), jsonObj);

			if (mes1.equals("")) {
				objectDTO.setMessage("Http not found");
			} else {
				objectDTO.setMessage(mes1);
			}

			break;

		case HttpURLConnection.HTTP_CONFLICT:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			String mes = DataUtils.getMessage(String.valueOf(HttpURLConnection.HTTP_CONFLICT), jsonObj);

			if (mes.equals("")) {
				objectDTO.setMessage(errMessCode.getMes409());
			} else {
				objectDTO.setMessage(mes);
			}

			break;

		default:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			objectDTO.setMessage("false!");
			break;
		}

		return objectDTO;
	}

	public ResultValueBO resolveValue(ResponseBO reponse) {
		ResultValueBO objectDTO = new ResultValueBO();
		errMessCode = Constants.getErrHttpCode();
		String responseResult = "";

		if (reponse.getConnect() != null) {
			responseResult = getResponse(reponse);
		} else {
			if (reponse.getBody() != null) {
				responseResult = reponse.getBody();
			} else {
				responseResult = "";
			}
		}

		switch (reponse.getErrorCode()) {
		case HttpURLConnection.HTTP_OK:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage("Success!");
			if (!responseResult.equals("")) {
				objectDTO.setData(responseResult);
			}

			break;
		case HttpURLConnection.HTTP_CREATED:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes201());
			if (!responseResult.equals("")) {
				objectDTO.setData(responseResult);
			}

			break;
		case HttpURLConnection.HTTP_ACCEPTED:

			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes202());
			if (!responseResult.equals("")) {
				objectDTO.setData(responseResult);
			}

			break;
		case HttpURLConnection.HTTP_NO_CONTENT:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes204());
			break;

		case HttpURLConnection.HTTP_SEE_OTHER:
			objectDTO.setErrorCode(Constants.OK);
			objectDTO.setError(false);
			objectDTO.setMessage(errMessCode.getMes303());
			break;
		case HttpURLConnection.HTTP_UNAUTHORIZED:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK_401);
			objectDTO.setMessage("Not permission! Please login again.");
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			if (StringUtils.isNotEmpty(errMessCode.getMes400())) {
				objectDTO.setMessage(errMessCode.getMes400());
			} else {
				objectDTO.setMessage("False!");
			}
			break;
		case HttpURLConnection.HTTP_CONFLICT:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			objectDTO.setMessage(errMessCode.getMes409());
			break;
		default:
			objectDTO.setError(true);
			objectDTO.setErrorCode(Constants.N_OK);
			objectDTO.setMessage("false!");
			break;
		}

		return objectDTO;
	}

	public static String getResponse(ResponseBO dto) {
		InputStream is = null;
		StringBuffer response = new StringBuffer();
		BufferedReader in = null;

		try {
			if (dto.getErrorCode() == HttpURLConnection.HTTP_OK || dto.getErrorCode() == HttpURLConnection.HTTP_CREATED
					|| dto.getErrorCode() == HttpURLConnection.HTTP_ACCEPTED
					|| dto.getErrorCode() == HttpURLConnection.HTTP_NO_CONTENT) {
				in = new BufferedReader(new InputStreamReader(dto.getConnect().getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				System.out.println(response.toString());
			} else if (dto.getErrorCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				return "";
			} else {
				if (dto.getConnect().getErrorStream() != null) {
					is = new BufferedInputStream(dto.getConnect().getErrorStream());
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String inputLine = "";

					while ((inputLine = br.readLine()) != null) {
						response.append(inputLine);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response.toString();
	}
}
