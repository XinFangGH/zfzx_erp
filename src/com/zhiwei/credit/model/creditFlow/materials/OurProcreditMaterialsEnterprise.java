package com.zhiwei.credit.model.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class OurProcreditMaterialsEnterprise extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	protected Long materialsId;
	/**
	 * 贷款材料名称
	 */
	protected String materialsName;
	/**
	 * 父节点的materialsId，根节点为0
	 */
	protected Integer parentId;
	/**
	 * 是否为叶子节点，0为否，1为是
	 */
	protected Integer leaf;
	/**
	 * 备注
	 */
	protected String remarks;
	/**
	 * 业务种类key
	 */
	protected String operationTypeKey;  

	/**
	 * 合规说明
	 */
	protected String ruleExplain;
	/**
	 * 线上份数
	 */
	protected Integer datumNums; 
	/**
	 * 线下份数
	 */
	protected Integer xxnums;
	/**
	 * 产品Id
	 */
	protected Long productId;
	/**
	 * 父类材料Id
	 */
	protected Long projectId;
	/**
	 * 是否归档
	 */
	protected Boolean isfile;
	/**
	 * 归档备注
	 */
    protected String gdremark;
	/**
	 * 以下字段 不与数据库映射
	 */
	/**
	 * 父节点名称
	 */
	protected String parentName;
	/**
	 * 业务种类名称
	 */
	protected String operationTypeName;
	/**
	 * 父节点文本
	 */
	protected String parentText;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

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
	public OurProcreditMaterialsEnterprise () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OurProcreditMaterialsEnterprise
	 */
	public OurProcreditMaterialsEnterprise (
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
		if (!(object instanceof OurProcreditMaterialsEnterprise)) {
			return false;
		}
		OurProcreditMaterialsEnterprise rhs = (OurProcreditMaterialsEnterprise) object;
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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getDatumNums() {
		return datumNums;
	}

	public void setDatumNums(Integer datumNums) {
		this.datumNums = datumNums;
	}

	public Integer getXxnums() {
		return xxnums;
	}

	public void setXxnums(Integer xxnums) {
		this.xxnums = xxnums;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Boolean getIsfile() {
		return isfile;
	}

	public void setIsfile(Boolean isfile) {
		this.isfile = isfile;
	}

	public String getGdremark() {
		return gdremark;
	}

	public void setGdremark(String gdremark) {
		this.gdremark = gdremark;
	}

}
