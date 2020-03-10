package com.viettel.vocs.bo;

import org.json.simple.JSONArray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultArrayBO extends ResultBO {

	private JSONArray data;

}
