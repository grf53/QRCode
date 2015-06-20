package software.experiment.qrcode.network;

public interface SocketInterface {
	public static final int TYPE_SIGNUP_REQ = 0;
	public static final int TYPE_SIGNUP_CONF = 1;
	public static final int TYPE_LOGIN_REQ = 2;
	public static final int TYPE_LOGIN_CONF = 3;
	public static final int TYPE_ADD_ITEM_REQ = 4;
	public static final int TYPE_ADD_ITEM_CONF = 5;
	public static final int TYPE_QR_EVENT = 7;
	
	public static final String KEY_JSON_TYPE = "type";
	public static final String KEY_JSON_CONTENTS = "contents";
	public static final String KEY_JSON_SUCCESS = "success";
	public static final String KEY_JSON_ID = "id";
	public static final String KEY_JSON_PW = "pw";
	public static final String KEY_JSON_EMAIL = "email";
	public static final String KEY_JSON_PHONE = "phone";
	
	public static final String serverAddress = "203.252.53.28";
	public static final int appPort = 10000;
	public static final int QRPort = 10001;
}