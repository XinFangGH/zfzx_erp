package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class ClassType extends BaseModel {
    /**
     * 主键
     */
	private Long classId;
	 /**
     * 信用评级标准名称
     */
	private String className;
	 /**
     * 说明
     */
	private String remarks;
	 /**
     * 创建人Id
     */
	private Long createPersonId;
	 /**
     * 创建人姓名
     */
	private String cretePerson;
	 /**
     * 创建时间
     */
	private Date createTime;
	 /**
     * 最后更新人的Id
     */
	private Long updatePersonId;
	 /**
     * 最后更新人
     */
	private String updatePerson;
	 /**
     * 最后更新时间
     */
	private Date updateTime;
	
	public ClassType(){
		super();
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long id) {
		this.classId = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(Long createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getCretePerson() {
		return cretePerson;
	}

	public void setCretePerson(String cretePerson) {
		this.cretePerson = cretePerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Long updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
