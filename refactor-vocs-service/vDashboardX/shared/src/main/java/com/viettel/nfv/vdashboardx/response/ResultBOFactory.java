package com.viettel.nfv.vdashboardx.response;

public class ResultBOFactory {

	private ResultBOFactory(){}

	public static ResultBO createResultBO(ResultType type) {
		switch (type) {
		case VALUE_TYPE:
			return new ResultValueBO();

		case OBJECT_TYPE:
			return new ResultObjectBO();

		case ARRAY_TYPE:
			return new ResultArrayBO();

		default:
			return null;
		}
	}

}
