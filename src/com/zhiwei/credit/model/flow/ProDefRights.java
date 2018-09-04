package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProDefRights Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProDefRights extends com.zhiwei.core.model.BaseModel {

    protected Long rightsId;
	protected String roleNames;
	protected String depNames;
	protected String userNames;
	protected String userIds;
	protected String roleIds;
	protected String depIds;
	protected com.zhiwei.credit.model.flow.ProDefinition proDefinition;
	protected com.zhiwei.credit.model.system.GlobalType globalType;


	/**
	 * Default Empty Constructor for class ProDefRights
	 */
	public ProDefRights () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProDefRights
	 */
	public ProDefRights (
		 Long in_rightsId
        ) {
		this.setRightsId(in_rightsId);
    }

	
	public com.zhiwei.credit.model.flow.ProDefinition getProDefinition () {
		return proDefinition;
	}	
	
	public void setProDefinition (com.zhiwei.credit.model.flow.ProDefinition in_proDefinition) {
		this.proDefinition = in_proDefinition;
	}
	
	public com.zhiwei.credit.model.system.GlobalType getGlobalType () {
		return globalType;
	}	
	
	public void setGlobalType (com.zhiwei.credit.model.system.GlobalType in_globalType) {
		this.globalType = in_globalType;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="rightsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRightsId() {
		return this.rightsId;
	}
	
	/**
	 * Set the rightsId
	 */	
	public void setRightsId(Long aValue) {
		this.rightsId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getProTypeId() {
		return this.getGlobalType()==null?null:this.getGlobalType().getProTypeId();
	}
	
	/**
	 * Set the proTypeId
	 */	
	public void setProTypeId(Long aValue) {
	    if (aValue==null) {
	    	globalType = null;
	    } else if (globalType == null) {
	        globalType = new com.zhiwei.credit.model.system.GlobalType(aValue);
	        globalType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			globalType.setProTypeId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getDefId() {
		return this.getProDefinition()==null?null:this.getProDefinition().getDefId();
	}
	
	/**
	 * Set the defId
	 */	
	public void setDefId(Long aValue) {
	    if (aValue==null) {
	    	proDefinition = null;
	    } else if (proDefinition == null) {
	        proDefinition = new com.zhiwei.credit.model.flow.ProDefinition(aValue);
	        proDefinition.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			proDefinition.setDefId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="roleNames" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getRoleNames() {
		return this.roleNames;
	}
	
	/**
	 * Set the roleNames
	 */	
	public void setRoleNames(String aValue) {
		this.roleNames = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="depNames" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getDepNames() {
		return this.depNames;
	}
	
	/**
	 * Set the depNames
	 */	
	public void setDepNames(String aValue) {
		this.depNames = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userNames" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getUserNames() {
		return this.userNames;
	}
	
	/**
	 * Set the userNames
	 */	
	public void setUserNames(String aValue) {
		this.userNames = aValue;
	}	

	/**
	 * 用户IDS
            格式如下，以方便使用like操作
            ,1,2,	 * @return String
	 * @hibernate.property column="userIds" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getUserIds() {
		return this.userIds;
	}
	
	/**
	 * Set the userIds
	 */	
	public void setUserIds(String aValue) {
		this.userIds = aValue;
	}	

	/**
	 * 角色IDS
            格式如下，以方便使用like操作
            ,1,2,	 * @return String
	 * @hibernate.property column="roleIds" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getRoleIds() {
		return this.roleIds;
	}
	
	/**
	 * Set the roleIds
	 */	
	public void setRoleIds(String aValue) {
		this.roleIds = aValue;
	}	

	/**
	 * 部门IDS
            格式如下，以方便使用like操作
            ,1,2,	 * @return String
	 * @hibernate.property column="depIds" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getDepIds() {
		return this.depIds;
	}
	
	/**
	 * Set the depIds
	 */	
	public void setDepIds(String aValue) {
		this.depIds = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProDefRights)) {
			return false;
		}
		ProDefRights rhs = (ProDefRights) object;
		return new EqualsBuilder()
				.append(this.rightsId, rhs.rightsId)
								.append(this.roleNames, rhs.roleNames)
				.append(this.depNames, rhs.depNames)
				.append(this.userNames, rhs.userNames)
				.append(this.userIds, rhs.userIds)
				.append(this.roleIds, rhs.roleIds)
				.append(this.depIds, rhs.depIds)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.rightsId) 
								.append(this.roleNames) 
				.append(this.depNames) 
				.append(this.userNames) 
				.append(this.userIds) 
				.append(this.roleIds) 
				.append(this.depIds) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("rightsId", this.rightsId) 
								.append("roleNames", this.roleNames) 
				.append("depNames", this.depNames) 
				.append("userNames", this.userNames) 
				.append("userIds", this.userIds) 
				.append("roleIds", this.roleIds) 
				.append("depIds", this.depIds) 
				.toString();
	}



}
