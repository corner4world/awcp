package org.szcloud.framework.unit.vo;


public class PunResourceVO{
	/**
	 * 
	 */
	private Long resourceId;
	private Long sysId;
	private String resouType;
	private String relateResoId;
	private String whichEnd;
	private String resourceName;

	public PunResourceVO(){
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getResouType() {
		return resouType;
	}

	public void setResouType(String resouType) {
		this.resouType = resouType;
	}

	public String getRelateResoId() {
		return relateResoId;
	}

	public void setRelateResoId(String relateResoId) {
		this.relateResoId = relateResoId;
	}

	public String getWhichEnd() {
		return whichEnd;
	}

	public void setWhichEnd(String whichEnd) {
		this.whichEnd = whichEnd;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	
}

