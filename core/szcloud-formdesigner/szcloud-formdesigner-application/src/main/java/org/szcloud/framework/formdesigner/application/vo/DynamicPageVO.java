package org.szcloud.framework.formdesigner.application.vo;

import java.util.Date;

public class DynamicPageVO {
	private Long id;
	private String name;
	private int pageType;
	private String isLog;
	private String readonlyScript;
	private String disabledScript;
	private String hiddenScript;
	private String description;
	private String preLoadScript;
	private String afterLoadScript;
	private String dataJson;
	private Date created;
	private Date updated;
	private String styleId;
	private String workflowNodeInfo;
	private Long templateId;
	private Long systemId;
	private String lineHeight;
	private String minLineCount;
	private String maxLineCount;
	private Long pdfTemplatePage;
	private String lineHeightType;
	private Integer isCheckOut;
	private String checkOutUser;
	private String createdUser;
	
	private String updatedUser;
	//是否显示总页数，1是，0否
	private Integer showTotalCount;
	//是否分页，1是，0否
	private Integer isLimitPage;
	//每页显示数目
	private Long pageSize;
	//是否显示序号列，1是，0否
	private Integer showReverseNum;
	//序号显示的模式
	private String reverseNumMode;
	//序号列排序方式，1降序，0升序
	private String reverseSortord;
	
	public Integer getShowTotalCount() {
		return showTotalCount;
	}
	public void setShowTotalCount(Integer showTotalCount) {
		this.showTotalCount = showTotalCount;
	}
	public Integer getIsLimitPage() {
		return isLimitPage;
	}
	public void setIsLimitPage(Integer isLimitPage) {
		this.isLimitPage = isLimitPage;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getShowReverseNum() {
		return showReverseNum;
	}
	public void setShowReverseNum(Integer showReverseNum) {
		this.showReverseNum = showReverseNum;
	}
	public String getReverseNumMode() {
		return reverseNumMode;
	}
	public void setReverseNumMode(String reverseNumMode) {
		this.reverseNumMode = reverseNumMode;
	}
	public Long getSystemId() {
		return systemId;
	}
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPageType() {
		return pageType;
	}
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}	
	
	public String getIsLog() {
		return isLog;
	}
	public void setIsLog(String isLog) {
		this.isLog = isLog;
	}
	public String getReadonlyScript() {
		return readonlyScript;
	}
	public void setReadonlyScript(String readonlyScript) {
		this.readonlyScript = readonlyScript;
	}
	public String getDisabledScript() {
		return disabledScript;
	}
	public void setDisabledScript(String disabledScript) {
		this.disabledScript = disabledScript;
	}
	public String getHiddenScript() {
		return hiddenScript;
	}
	public void setHiddenScript(String hiddenScript) {
		this.hiddenScript = hiddenScript;
	}
	public String getPreLoadScript() {
		return preLoadScript;
	}
	public void setPreLoadScript(String preLoadScript) {
		this.preLoadScript = preLoadScript;
	}
	public String getAfterLoadScript() {
		return afterLoadScript;
	}
	public void setAfterLoadScript(String afterLoadScript) {
		this.afterLoadScript = afterLoadScript;
	}
	public String getDataJson() {
		return dataJson;
	}
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getWorkflowNodeInfo() {
		return workflowNodeInfo;
	}
	public void setWorkflowNodeInfo(String workflowNodeInfo) {
		this.workflowNodeInfo = workflowNodeInfo;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getLineHeight() {
		return lineHeight;
	}
	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}
	public String getMinLineCount() {
		return minLineCount;
	}
	public void setMinLineCount(String minLineCount) {
		this.minLineCount = minLineCount;
	}
	public String getMaxLineCount() {
		return maxLineCount;
	}
	public void setMaxLineCount(String maxLineCount) {
		this.maxLineCount = maxLineCount;
	}
	public Long getPdfTemplatePage() {
		return pdfTemplatePage;
	}
	public void setPdfTemplatePage(Long pdfTemplatePage) {
		this.pdfTemplatePage = pdfTemplatePage;
	}
	public String getLineHeightType() {
		return lineHeightType;
	}
	public void setLineHeightType(String lineHeightType) {
		this.lineHeightType = lineHeightType;
	}
	public Integer getIsCheckOut() {
		return isCheckOut;
	}
	public void setIsCheckOut(Integer isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	public String getCheckOutUser() {
		return checkOutUser;
	}
	public void setCheckOutUser(String checkOutUser) {
		this.checkOutUser = checkOutUser;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public String getReverseSortord() {
		return reverseSortord;
	}
	public void setReverseSortord(String reverseSortord) {
		this.reverseSortord = reverseSortord;
	}
	
	
}
