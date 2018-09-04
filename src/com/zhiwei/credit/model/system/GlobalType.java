package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司企业管理平台   -- http://www.hurongtime.com
 *  Copyright (C) 2008-20010 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 单级数据字典分类（左侧导航）表
 */
public class GlobalType extends com.zhiwei.core.model.BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 代表产品分类
	 */
	public static final String CAT_PRODUCT_TYPE="PT";
	/**
	 * 计量分类
	 */
	public static final String CAT_CAL_UNIT="CT";
	/**
	 * 数字字典
	 */
	public static final String CAT_DICTIONARY="DIC";
	
	/**
	 * 现金银行科目
	 */
	public static final String CAT_BANK_CASH="BKCH";
	/**
	 * 全宗分类
	 */
	public static final String CAT_ARCH_FOND="AR_FD";
	/**
	 * 案卷分类
	 */
	public static final String CAT_ARCH_ROLL="AR_RL";
	/**
	 * 卷内文件分类
	 */
	public static final String CAT_ROLL_FILE="AR_RLF";
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
	protected Integer depth;
	/**
	 * 父节点id
	 */
	protected Long parentId;
	/**
	 * 节点Key
	 */
	protected String nodeKey;
	/**
	 * 节点分类的Key，如单级数据字典为DIC
	 */
	protected String catKey;
	/**
	 * 序号
	 */
	protected Integer sn;
	/**
	 * 添加者id
	 */
    protected Long userId;
    /**
	 * 状态，0为有效，1为删除
	 */
    protected String status;
	/**
	 * Default Empty Constructor for class GlobalType
	 */
	public GlobalType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlobalType
	 */
	public GlobalType (
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
	 * 名称	 * @return String
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
	 * 路径	 * @return String
	 * @hibernate.property column="path" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set the path
	 * @spring.validator type="required"
	 */	
	public void setPath(String aValue) {
		this.path = aValue;
	}	

	/**
	 * 层次	 * @return Integer
	 * @hibernate.property column="depth" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getDepth() {
		return this.depth;
	}
	
	/**
	 * Set the depth
	 * @spring.validator type="required"
	 */	
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}	


	/**
	 * 父节点	 * @return Long
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
	 * 节点Key	 * @return Long
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
	 * 节点分类Key	 * @return Long
	 * @hibernate.property column="Catkey" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getCatKey() {
		return this.catKey;
	}
	
	/**
	 * Set the catKey
	 * @spring.validator type="required"
	 */	
	public void setCatKey(String aValue) {
		this.catKey = aValue;
	}	

	/**
	 * 序号	 * @return Integer
	 * @hibernate.property column="sn" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getSn() {
		return this.sn;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Set the sn
	 * @spring.validator type="required"
	 */	
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlobalType)) {
			return false;
		}
		GlobalType rhs = (GlobalType) object;
		return new EqualsBuilder()
				.append(this.proTypeId, rhs.proTypeId)
				.append(this.typeName, rhs.typeName)
				.append(this.path, rhs.path)
				.append(this.depth, rhs.depth)
				.append(this.parentId, rhs.parentId)
				.append(this.nodeKey, rhs.nodeKey)
				.append(this.catKey, rhs.catKey)
				.append(this.sn, rhs.sn)
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
				.toString();
	}



}
