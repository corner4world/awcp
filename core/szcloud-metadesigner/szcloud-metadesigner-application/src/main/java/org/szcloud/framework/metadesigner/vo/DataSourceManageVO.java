package org.szcloud.framework.metadesigner.vo;

import java.sql.Date;


public class DataSourceManageVO{
	
	private Long id;

	private String name;
	
	private String sourceType;
	
	private String sourceUrl;
	
	private String sourceDriver;
	/**
	 *  数据库连接别名
	 */
	private String alias;
	
	private String userName;
	
	private String userPwd;
	/**
	 * 最大活动时间
	 */
	private Integer maximumActiveTime;
	/**
	 * 连接池保持的最小空闲连接数
	 */
	private Integer prototypeCount;
	/**
	 * 最大连接数
	 */
	private Integer maximumConnectionCount;
	/**
	 * 最小连接数
	 */
	private Integer minimumConnectionCount;
	/**
	 * 同时执行的最大连接数
	 */
	private Integer simultaneousBuildThrottle;
	/**
	 * true:sql执行时log(debug level)
	 */
	private Boolean trace;
	/**
	 * 数据源领域
	 */
	private String domain;
	/**
	 * 分组
	 */
	private Long groupId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	/**
	 * 最后修改时间
	 */
	private String lastModifier;
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getMaximumActiveTime() {
		return maximumActiveTime;
	}

	public void setMaximumActiveTime(Integer maximumActiveTime) {
		this.maximumActiveTime = maximumActiveTime;
	}

	public Integer getPrototypeCount() {
		return prototypeCount;
	}

	public void setPrototypeCount(Integer prototypeCount) {
		this.prototypeCount = prototypeCount;
	}

	public Integer getMaximumConnectionCount() {
		return maximumConnectionCount;
	}

	public void setMaximumConnectionCount(Integer maximumConnectionCount) {
		this.maximumConnectionCount = maximumConnectionCount;
	}

	public Integer getMinimumConnectionCount() {
		return minimumConnectionCount;
	}

	public void setMinimumConnectionCount(Integer minimumConnectionCount) {
		this.minimumConnectionCount = minimumConnectionCount;
	}

	public Integer getSimultaneousBuildThrottle() {
		return simultaneousBuildThrottle;
	}

	public void setSimultaneousBuildThrottle(Integer simultaneousBuildThrottle) {
		this.simultaneousBuildThrottle = simultaneousBuildThrottle;
	}

	public Boolean getTrace() {
		return trace;
	}

	public void setTrace(Boolean trace) {
		this.trace = trace;
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

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceDriver() {
		return sourceDriver;
	}

	public void setSourceDriver(String sourceDriver) {
		this.sourceDriver = sourceDriver;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
}
