package com.viettel.vocs.bo;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultObjectBO extends ResultBO {

	private JSONObject data;

}
