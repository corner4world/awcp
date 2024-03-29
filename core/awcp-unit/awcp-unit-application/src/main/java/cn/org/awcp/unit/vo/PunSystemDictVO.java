package cn.org.awcp.unit.vo;

import java.io.Serializable;

public class PunSystemDictVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8981578533335241064L;
	private Long id;
	private String code;
	private String key;
	private String value;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "PunSystemDictVO [id=" + id + ", code=" + code + ", key=" + key + ", value=" + value + "]";
	}
	
}
