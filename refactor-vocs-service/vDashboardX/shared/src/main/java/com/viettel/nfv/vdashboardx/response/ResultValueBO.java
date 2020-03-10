package com.viettel.nfv.vdashboardx.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultValueBO extends ResultBO {

	private String data;

	@Override
	public void setResponsedData(Object data) {
		this.data = (String) data;
	}

	@Override
	public Object toJsonObject(String value) {
		return value;
	}
}
