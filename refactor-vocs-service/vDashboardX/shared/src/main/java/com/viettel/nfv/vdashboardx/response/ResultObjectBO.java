package com.viettel.nfv.vdashboardx.response;

import com.viettel.nfv.vdashboardx.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObjectBO extends ResultBO {

	private static final Logger logger = LoggerFactory.getLogger(ResultObjectBO.class.getName());

	private JSONObject data;

	@Override
	public void setResponsedData(Object data) {
		try {
			this.data = (JSONObject) data;
		} catch (ClassCastException e) {
			logger.info(e.toString());
			this.data = null;
		}
	}

	@Override
	public Object toJsonObject(String value) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}

		return JsonUtils.convertStringToObject(value);
	}
}
