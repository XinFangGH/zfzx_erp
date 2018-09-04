package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * GlobalTypeIndependent Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
/**
 * 独立数据字典分类（左侧导航）表
 */
public class GlobalTypeIndependent extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long proTypeId;
	/**
	 * 名称
	 */
	protected String typeName;
	/**
	 * 路径
	 */
	protected String path;
	/**
	 * 层次
	 */
	protected Long depth;
	/**
	 * 父节点id
	 */
	protected Long parentId;
	/**
	 * 节点Key
	 */
	protected String nodeKey;
	/**
	 * 节点分类的Key，如独立数据字典为DIC
	 */
	protected String catKey;
	/**
	 * 序号
	 */
	protected Long sn;
	/**
	 * 添加者id
	 */
	protected Long userId;
	/**
	 * 
	 */
	protected Long depId;
	/**
	 * 状态，0为有效，1为删除
	 */
	protected String status;

	/**
	 * Default Empty Constructor for class GlobalTypeIndependent
	 */
	public GlobalTypeIndependent () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlobalTypeIndependent
	 */
	public GlobalTypeIndependent (
		 Long in_proTypeId
        ) {
		this.setProTypeId(in_proTypeId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="proTypeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProTypeId() {
		return this.proTypeId;
	}
	
	/**
	 * Set the proTypeId
	 */	
	public void setProTypeId(Long aValue) {
		this.proTypeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 * @spring.validator type="required"
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="path" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set the path
	 */	
	public void setPath(String aValue) {
		this.path = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="depth" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getDepth() {
		return this.depth;
	}
	
	/**
	 * Set the depth
	 */	
	public void setDepth(Long aValue) {
		this.depth = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="parentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Long aValue) {
		this.parentId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="nodeKey" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getNodeKey() {
		return this.nodeKey;
	}
	
	/**
	 * Set the nodeKey
	 * @spring.validator type="required"
	 */	
	public void setNodeKey(String aValue) {
		this.nodeKey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="catKey" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCatKey() {
		return this.catKey;
	}
	
	/**
	 * Set the catKey
	 */	
	public void setCatKey(String aValue) {
		this.catKey = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="sn" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getSn() {
		return this.sn;
	}
	
	/**
	 * Set the sn
	 */	
	public void setSn(Long aValue) {
		this.sn = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="depId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getDepId() {
		return this.depId;
	}
	
	/**
	 * Set the depId
	 */	
	public void setDepId(Long aValue) {
		this.depId = aValue;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlobalTypeIndependent)) {
			return false;
		}
		GlobalTypeIndependent rhs = (GlobalTypeIndependent) object;
		return new EqualsBuilder()
				.append(this.proTypeId, rhs.proTypeId)
				.append(this.typeName, rhs.typeName)
				.append(this.path, rhs.path)
				.append(this.depth, rhs.depth)
				.append(this.parentId, rhs.parentId)
				.append(this.nodeKey, rhs.nodeKey)
				.append(this.catKey, rhs.catKey)
				.append(this.sn, rhs.sn)
				.append(this.userId, rhs.userId)
				.append(this.depId, rhs.depId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.proTypeId) 
				.append(this.typeName) 
				.append(this.path) 
				.append(this.depth) 
				.append(this.parentId) 
				.append(this.nodeKey) 
				.append(this.catKey) 
				.append(this.sn) 
				.append(this.userId) 
				.append(this.depId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("proTypeId", this.proTypeId) 
				.append("typeName", this.typeName) 
				.append("path", this.path) 
				.append("depth", this.depth) 
				.append("parentId", this.parentId) 
				.append("nodeKey", this.nodeKey) 
				.append("catKey", this.catKey) 
				.append("sn", this.sn) 
				.append("userId", this.userId) 
				.append("depId", this.depId) 
				.toString();
	}



}
