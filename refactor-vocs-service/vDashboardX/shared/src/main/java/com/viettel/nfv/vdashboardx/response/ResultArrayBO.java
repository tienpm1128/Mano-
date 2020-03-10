package com.viettel.nfv.vdashboardx.response;

import com.viettel.nfv.vdashboardx.utils.JsonUtils;
import org.json.simple.JSONArray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultArrayBO extends ResultBO {

	private JSONArray data;

	@Override
	public void setResponsedData(Object data) {
		this.data = (JSONArray) data;
	}

	@Override
	public Object toJsonObject(String value) {
		return JsonUtils.convertStringToJsonArray(value);
	}
}
