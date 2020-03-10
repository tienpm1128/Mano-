package com.viettel.vocs.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.utils.Constants;

public class ResultException {

	public static ResponseEntity<ResultObjectBO> responseUnauthorized() {
		ResultObjectBO object = new ResultObjectBO();
		object.setError(true);
		object.setErrorCode(Constants.N_OK);
		object.setMessage("Not permission! Please login again");

		return ResponseEntity.ok(object);
	}

}
