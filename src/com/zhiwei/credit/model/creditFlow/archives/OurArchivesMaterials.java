package com.zhiwei.credit.model.creditFlow.archives;
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
 * OurArchivesMaterials Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OurArchivesMaterials extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long materialsId;
	/**
	 * 材料名称
	 */
	protected String materialsName;
	/**
	 * 业务种类key
	 */
	protected String operationTypeKey;
	/**
	 * 是否为公共的，1为公共
	 */
	protected Short isPublic;
	/**
	 * 业务种类名称
	 */
	protected String operationTypeName;
	/**
	 * 业务类别key
	 */
	protected String businessTypeKey; 
	/**
	 * 业务类别名称
	 */
    protected String businessTypeName;
    protected Long businessTypeGlobalId;
    protected Long operationTypeGlobalId;
    /**
	 * 产品id
	 */
    protected Long productId;
    /**
	 * 父归档材料id(产品配置中添加时会复制一条，这个字段标记由哪条数据复制而来)
	 */
    protected Long settingId;


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSettingId() {
		return settingId;
	}

	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}

	/**
	 * Default Empty Constructor for class OurArchivesMaterials
	 */
	public OurArchivesMaterials () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OurArchivesMaterials
	 */
	public OurArchivesMaterials (
		 Long in_materialsId
        ) {
		this.setMaterialsId(in_materialsId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="materialsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getMaterialsId() {
		return this.materialsId;
	}
	
	/**
	 * Set the materialsId
	 */	
	public void setMaterialsId(Long aValue) {
		this.materialsId = aValue;
	}	

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="materialsName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMaterialsName() {
		return this.materialsName;
	}
	
	/**
	 * Set the materialsName
	 */	
	public void setMaterialsName(String aValue) {
		this.materialsName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="operationTypeKey" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOperationTypeKey() {
		return this.operationTypeKey;
	}
	
	/**
	 * Set the operationTypeKey
	 */	
	public void setOperationTypeKey(String aValue) {
		this.operationTypeKey = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isPublic" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsPublic() {
		return this.isPublic;
	}
	
	/**
	 * Set the isPublic
	 */	
	public void setIsPublic(Short aValue) {
		this.isPublic = aValue;
	}	

	public String getBusinessTypeKey() {
		return businessTypeKey;
	}

	public void setBusinessTypeKey(String businessTypeKey) {
		this.businessTypeKey = businessTypeKey;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public Long getBusinessTypeGlobalId() {
		return businessTypeGlobalId;
	}

	public void setBusinessTypeGlobalId(Long businessTypeGlobalId) {
		this.businessTypeGlobalId = businessTypeGlobalId;
	}

	public Long getOperationTypeGlobalId() {
		return operationTypeGlobalId;
	}

	public void setOperationTypeGlobalId(Long operationTypeGlobalId) {
		this.operationTypeGlobalId = operationTypeGlobalId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OurArchivesMaterials)) {
			return false;
		}
		OurArchivesMaterials rhs = (OurArchivesMaterials) object;
		return new EqualsBuilder()
				.append(this.materialsId, rhs.materialsId)
				.append(this.materialsName, rhs.materialsName)
				.append(this.operationTypeKey, rhs.operationTypeKey)
				.append(this.isPublic, rhs.isPublic)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.materialsId) 
				.append(this.materialsName) 
				.append(this.operationTypeKey) 
				.append(this.isPublic) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("materialsId", this.materialsId) 
				.append("materialsName", this.materialsName) 
				.append("operationTypeKey", this.operationTypeKey) 
				.append("isPublic", this.isPublic) 
				.toString();
	}



}
