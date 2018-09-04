package com.zhiwei.credit.model.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class GlobalGuaranteeMaterialsExt extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;
	protected Long materialsId;
	protected String materialsName;
	protected Integer parentId;
	protected Integer leaf;
	protected String remarks;
	protected String parentText;
	protected String operationTypeKey;  //业务种类key
	protected String operationTypeName;
	protected String ruleExplain;
	

	public String getRuleExplain() {
		return ruleExplain;
	}

	public void setRuleExplain(String ruleExplain) {
		this.ruleExplain = ruleExplain;
	}

	public String getOperationTypeKey() {
		return operationTypeKey;
	}

	public void setOperationTypeKey(String operationTypeKey) {
		this.operationTypeKey = operationTypeKey;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Default Empty Constructor for class OurProcreditMaterialsEnterprise
	 */
	public GlobalGuaranteeMaterialsExt () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlobalGuaranteeMaterialsExt
	 */
	public GlobalGuaranteeMaterialsExt (
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
	 * 	 * @return Integer
	 * @hibernate.property column="parentId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Integer aValue) {
		this.parentId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="leaf" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLeaf() {
		return this.leaf;
	}
	
	/**
	 * Set the leaf
	 */	
	public void setLeaf(Integer aValue) {
		this.leaf = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlobalGuaranteeMaterialsExt)) {
			return false;
		}
		GlobalGuaranteeMaterialsExt rhs = (GlobalGuaranteeMaterialsExt) object;
		return new EqualsBuilder()
				.append(this.materialsId, rhs.materialsId)
				.append(this.materialsName, rhs.materialsName)
				.append(this.parentId, rhs.parentId)
				.append(this.leaf, rhs.leaf)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.materialsId) 
				.append(this.materialsName) 
				.append(this.parentId) 
				.append(this.leaf) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("materialsId", this.materialsId) 
				.append("materialsName", this.materialsName) 
				.append("parentId", this.parentId) 
				.append("leaf", this.leaf) 
				.toString();
	}



}
