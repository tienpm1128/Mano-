package com.viettel.nfv.vdashboardx.domain;

import org.json.simple.JSONArray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VNFD {

	private int id;

	private JSONArray array;

}
