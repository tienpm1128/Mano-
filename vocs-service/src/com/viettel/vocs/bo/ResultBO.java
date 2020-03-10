package com.viettel.vocs.bo;

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

}
