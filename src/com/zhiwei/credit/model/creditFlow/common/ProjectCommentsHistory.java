package com.zhiwei.credit.model.creditFlow.common;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;


/**
 * 
 * @autohorg gao
 * @description record history comments for a component(a fieldset by default ) in a project
 */
public class ProjectCommentsHistory extends BaseModel {
	
	private Long commentId;
	private Long userId;
	private Date commitDate;
	private Long projectId;
	private String businessType;
	private String componentKey;
	private String commentContent;
	
	private String userPhotoURL;// 非数据库保存字段，根据userId 取到userphotourl
	private String userName;//not persist field
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhotoURL() {
		return userPhotoURL;
	}
	public void setUserPhotoURL(String userPhotoURL) {
		this.userPhotoURL = userPhotoURL;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getComponentKey() {
		return componentKey;
	}
	public void setComponentKey(String componentKey) {
		this.componentKey = componentKey;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	
	
	
	

}
