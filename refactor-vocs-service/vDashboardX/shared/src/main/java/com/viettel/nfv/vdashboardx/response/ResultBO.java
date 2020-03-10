package com.viettel.nfv.vdashboardx.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ResultBO {

	protected String message;

	protected String errorCode;

	protected boolean isError = false;

	public abstract void setResponsedData(Object data);

	public abstract Object toJsonObject(String value);

}
