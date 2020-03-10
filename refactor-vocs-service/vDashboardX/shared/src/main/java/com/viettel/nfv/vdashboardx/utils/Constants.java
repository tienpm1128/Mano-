package com.viettel.nfv.vdashboardx.utils;

public class Constants {

	public static final  String OK = "00";
	public static final  String N_OK = "01";
	public static final  String N_OK_401 = "02";
	public static final  String MESS_400 = "Invalid infomation!";
	public static final  String MESS_201_NSD_OPENSTK_USER = "Created Success!";
	public static final  String MESS_201_VIM_INSTANCE = "Successfully created Vim, returning the Vim object.";
	public static final  String MESS_201_PROJECT = "Successfully creating the project, returning the project information!";
	public static final  String MESS_201_ROLE = "Successfully created, returns Role ID";
	public static final  String MESS_201_UPLOAD_SOFT = "Upload successfully software";
	public static final  String MESS_201_IMG = "Successfully created, returns Image ID";
	public static final  String MESS_201_SERVER_GROUP = "Successfully created, returns Server Group ID";
	public static final  String MESS_201_FAULT_SUBSCRIPTION = "Create successful Subscription";
	public static final  String MESS_201_SUCCESS = "Create successfully!";
	public static final  String MESS_202_UPLOAD_NSD = "The system is in progress of uploading";
	public static final  String MESS_202_VNF_INSTANCE = "Request is accepted and being processed";
	public static final  String MESS_202_VIM_USER = "Successful authentication";
	public static final  String MESS_202_VIM_INSTANCE = "Login successfully, return User ID information";
	public static final  String MESS_202_PROJECT = "Successfully update the project, returning the project information";
	public static final  String MESS_202_UPDATE = "Update successfully";
	public static final  String MESS_204_DELETE = "Delete success";
	public static final  String MESS_204_UPLOAD_NSD = "Upload successfully";
	public static final  String MESS_400_LOGIN_VIM = "Username/password incorrect";
	public static final  String MESS_404_VNF = "Error due to not finding VNF instance(because vnfInstanceId does not exist)";
	public static final  String MESS_409_UPLOAD_NSD = "Error (nsdOnboardingState is not CREATED)";
	public static final  String MESS_409_DOWNLOAD_NSD = "Error (nsdOnboardingState is not ONBOARDED)";
	public static final  String MESS_409_DEL_NSD = "Error (operationalState is not DISABLED, usageState is not NOT_IN_USE)";
	public static final  String MESS_409_INIT_VNF = "Error due to invalid VNF instance status(instantiationState is INSTANTIATED)";
	public static final  String MESS_409_SCALE_VNF = "Error due to invalid VNF instance status (instantiationState is NOT_INSTANTIATED)";
	public static final  String MESS_409_ALARM = "Error due to Alarm status (ackState is ACKNOWLEDGED)";
	public static final  String MESS_409_VIM_EXIST = "Auth URL already exists";
	public static final  String MESS_409_NSD_OPENSTK_USER_EXIST = "Name already exists";
	public static final  String MESS_409_PROJECT_EXIST = "Project name already exist";
	public static final  String MESS_409_USER_EXIST = "Username already exist";
	public static final  String MESS_409_ROLE_EXIST = "Role Name already exists";
	public static final  String MESS_409_IMG_EXIST = "Image name already exists";
	public static final  String MESS_409_SERVER_GROUP = "Server Group name already exists";
	public static final  String MESS_409_TENANT_EXIST = "Tenant Name already exists";
	public static final  String MESS_303_VIM = "Registration successful";
	public static final  String MESS_NO_PERMISSION = "Not permission! Please login again.";
	public static final  String HTTP_NOT_FOUND = "Http not found";
	public static final  String MESS_401_VALIDATE_VIM_USER = "Verify Vim User false";

	public static final String OPENSTACK_USERID = "openStackUserId";
	public static final String PROJECT_ID = "projectId";
	public static final String FILEPATH = "file-path";
	public static final String VIMID = "vimId";

	private Constants(){}

}
