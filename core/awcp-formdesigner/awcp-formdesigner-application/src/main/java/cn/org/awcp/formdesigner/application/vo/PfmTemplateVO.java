package cn.org.awcp.formdesigner.application.vo;

public class PfmTemplateVO {
	private String id;
	private String fileName;
	private Long sysId;
	private String description;
	private String fileLocation;
	private String content;

	public PfmTemplateVO() {
	}

	public PfmTemplateVO(String id) {
		this.id = id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}

	public void setFileName(String value) {
		this.fileName = value;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setSysId(Long value) {
		this.sysId = value;
	}

	public Long getSysId() {
		return this.sysId;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}

	public void setFileLocation(String value) {
		this.fileLocation = value;
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
