package cn.org.awcp.venson.dingding.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String type();
}
