package com.viettel.vocs.utils;

import java.util.HashMap;

import com.viettel.vocs.bo.ErrHttpCode;

public class Constants {

	public final static String GET_LST_NSD = "/nsd/v1/ns_descriptors";
	public final static String GET_TOTAL_NSD = "/nsd/v1/ns_descriptors/total";
	public final static String GET_TOTAL_VNFD = "/nsd/v1/vnf_descriptors/total";
	public final static String GET_INSTANCE_NSD = "/nslcm/v1/ns_instances/total";
	public final static String GET_INSTANCE_VNFD = "/nslcm/v1/vnf_instances/total";
	public final static String OK = "00";
	public final static String N_OK = "01";
	public final static String N_OK_401 = "02";
	public volatile static String SESSION_TOKEN = "";
	public final static String MESS_OK = "Success!";
	public final static String MESS_400 = "Invalid infomation!";
	public final static String MESS_201_NSD_OPENSTK_USER = "Created Success!";
	public final static String MESS_201_NS_INSTANCE = "Create NS instance successfully!";
	public final static String MESS_201_VIM_INSTANCE = "Successfully created Vim, returning the Vim object.";
	public final static String MESS_201_PROJECT = "Successfully creating the project, returning the project information!";
	public final static String MESS_201_ROLE = "Successfully created, returns Role ID";
	public final static String MESS_201_UPLOAD_SOFT = "Upload successfully software";
	public final static String MESS_201_IMG = "Successfully created, returns Image ID";
	public final static String MESS_201_SERVER_GROUP = "Successfully created, returns Server Group ID";
	public final static String MESS_201_FAULT_SUBSCRIPTION = "Create successful Subscription";
	public final static String MESS_201_SUCCESS = "Create successfully!";
	public final static String MESS_202_UPLOAD_NSD = "The system is in progress of uploading";
	public final static String MESS_202_VNF_INSTANCE = "Request is accepted and being processed";
	public final static String MESS_202_VIM_USER = "Successful authentication";
	public final static String MESS_202_VIM_INSTANCE = "Login successfully, return User ID information";
	public final static String MESS_202_PROJECT = "Successfully update the project, returning the project information";
	public final static String MESS_202_UPDATE = "Update successfully";
	public final static String MESS_204_DELETE = "Delete success";
	public final static String MESS_204_UPLOAD_NSD = "Upload successfully";
	public final static String MESS_400_LOGIN_VIM = "Username/password incorrect";
	public final static String MESS_404_VNF = "Error due to not finding VNF instance(because vnfInstanceId does not exist)";
	public final static String MESS_409_UPLOAD_NSD = "Error (nsdOnboardingState is not CREATED)";
	public final static String MESS_409_DOWNLOAD_NSD = "Error (nsdOnboardingState is not ONBOARDED)";
	public final static String MESS_409_DEL_NSD = "Error (operationalState is not DISABLED, usageState is not NOT_IN_USE)";
	public final static String MESS_409_INIT_VNF = "Error due to invalid VNF instance status(instantiationState is INSTANTIATED)";
	public final static String MESS_409_SCALE_VNF = "Error due to invalid VNF instance status (instantiationState is NOT_INSTANTIATED)";
	public final static String MESS_409_ALARM = "Error due to Alarm status (ackState is ACKNOWLEDGED)";
	public final static String MESS_409_CREATED_MANO = "Error of user name already exists";
	public final static String MESS_409_VIM_EXIST = "Error because authUrl already exists";
	public final static String MESS_409_NSD_OPENSTK_USER_EXIST = "Validate nsInstanceName already exists";
	public final static String MESS_409_PROJECT_EXIST = "Validate projectName already exists";
	public final static String MESS_409_USER_EXIST = "Validate username already exists";
	public final static String MESS_409_ROLE_EXIST = "Validate role already exists";
	public final static String MESS_409_IMG_EXIST = "The validate image name already exists";
	public final static String MESS_409_SERVER_GROUP = "Validate server group name already exists";
	

	public final static String MESS_303_VIM = "Registration successful";
	private static ErrHttpCode errHttpCode = new ErrHttpCode();
	private static HashMap<String, String> keyParamHeader = new HashMap<String, String>();

	public static ErrHttpCode getErrHttpCode() {
		return errHttpCode;
	}

	public static void setErrHttpCode(ErrHttpCode errHttpCode) {
		Constants.errHttpCode = errHttpCode;
	}

	public static HashMap<String, String> getKeyParamHeader() {
		return keyParamHeader;
	}

	public static void setKeyParamHeader(HashMap<String, String> keyParamHeader) {
		Constants.keyParamHeader = keyParamHeader;
	}

}
