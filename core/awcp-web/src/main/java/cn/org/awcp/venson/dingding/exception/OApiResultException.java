package cn.org.awcp.venson.dingding.exception;

public class OApiResultException extends OApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ERR_RESULT_RESOLUTION = -2;

	public OApiResultException(String field) {
		super(ERR_RESULT_RESOLUTION, "Cannot resolve field " + field + " from oapi resonpse");
	}

}
