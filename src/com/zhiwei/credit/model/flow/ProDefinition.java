package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import com.zhiwei.credit.model.system.GlobalType;

import flexjson.JSON;

/**
 * ProDefinition Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 流程定义
 */
public class ProDefinition extends com.zhiwei.core.model.BaseModel {
	/**
	 * 激活
	 */
	public final static Short STATUS_ENABLE = 1;
	/**
	 * 禁用
	 */
	public final static Short STATUS_DISABLE = 0;
	
	public final static Short IS_DEFAULT=1;
	public final static Short IS_NOT_DEFAULT=0;
	
    protected Long defId;
    protected String processName;
	protected String name;
	protected String description;
	protected java.util.Date createtime;
	protected String deployId;
	protected String defXml;
	
	protected Integer newVersion; 
	protected String drawDefXml;
	protected Short status;
	//是否为系统缺省流程
	protected Short isDefault=IS_NOT_DEFAULT;
	
	protected GlobalType proType;
	
	//根据用户id查询相应业务流程的任务需要的字段 add by lu 2012.02.29
	protected String pdId;
	protected String DEFKEY;
	
	//根据分公司id查询相应业务流程 add by lu 2012.09.11
	protected Long branchCompanyId;
	@JSON
	public String getDefXml() {
		return defXml;
	}

	public void setDefXml(String defXml) {
		this.defXml = defXml;
	}

	/**
	 * Default Empty Constructor for class ProDefinition
	 */
	public ProDefinition () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProDefinition
	 */
	public ProDefinition (
		 Long in_defId
        ) {
		this.setDefId(in_defId);
    }

	
	public GlobalType getProType () {
		return proType;
	}	
	
	public void setProType (GlobalType in_proType) {
		this.proType = in_proType;
	}
	
	public void setProTypeId(Long proTypeId){
		if(proType==null){
			proType=new GlobalType();
		}
		proType.setProTypeId(proTypeId);
	}
	
	public Long getProTypeId(){
		return proType==null?null:proType.getProTypeId();
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="defId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDefId() {
		return this.defId;
	}
	
	/**
	 * Set the defId
	 */	
	public void setDefId(Long aValue) {
		this.defId = aValue;
	}	

	/**
	 * 分类ID	 * @return Long
	 */
	public Long getTypeId() {
		return this.getProType()==null?null:this.getProType().getProTypeId();
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
	    if (aValue==null) {
	    	proType = null;
	    } else if (proType == null) {
	        proType = new GlobalType(aValue);
	        //proType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			proType.setProTypeId(aValue);
	    }
	}	

	/**
	 * 流程的名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 * @spring.validator type="required"
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 描述	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set the description
	 */	
	public void setDescription(String aValue) {
		this.description = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * Jbpm 工作流id	 * @return String
	 * @hibernate.property column="deployId" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getDeployId() {
		return this.deployId;
	}
	
	/**
	 * Set the deployId
	 * @spring.validator type="required"
	 */	
	public void setDeployId(String aValue) {
		this.deployId = aValue;
	}	
	

	public String getDrawDefXml() {
		return drawDefXml;
	}

	public void setDrawDefXml(String drawDefXml) {
		this.drawDefXml = drawDefXml;
	}



	public Short getIsDefault() {
		return isDefault;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(Integer newVersion) {
		this.newVersion = newVersion;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}


	public String getPdId() {
		return pdId;
	}

	public void setPdId(String pdId) {
		this.pdId = pdId;
	}

	public String getDEFKEY() {
		return DEFKEY;
	}

	public void setDEFKEY(String dEFKEY) {
		DEFKEY = dEFKEY;
	}

	public Long getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(Long branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
}
