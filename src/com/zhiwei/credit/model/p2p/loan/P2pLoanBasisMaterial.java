package com.zhiwei.credit.model.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * P2pLoanBasisMaterial Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p贷款基础材料
 */
public class P2pLoanBasisMaterial extends com.zhiwei.core.model.BaseModel {

    protected Long materialId;
    /**
     * 材料名称
     */
	protected String materialName;
	/**
	 * 材料状态 1=必备，2=可选，0=已删除
	 */
	protected Long materialState;
	/**
	 * 客户类型 person=个人，enterprise=企业
	 */
	protected String operationType;
	/**
	 * 备注
	 */
    protected String remark;
    
    /**
	 * 不与数据库相联系
	 */
	 private Integer shilitu1Id;
	    private String shilitu1URL;
	    private String shilitu1ExtendName;
	    
	    private Integer shilitu2Id;
	    private String shilitu2URL;
	    private String shilitu2ExtendName;

	public Integer getShilitu1Id() {
			return shilitu1Id;
		}

		public void setShilitu1Id(Integer shilitu1Id) {
			this.shilitu1Id = shilitu1Id;
		}

		public String getShilitu1URL() {
			return shilitu1URL;
		}

		public void setShilitu1URL(String shilitu1url) {
			shilitu1URL = shilitu1url;
		}

		public String getShilitu1ExtendName() {
			return shilitu1ExtendName;
		}

		public void setShilitu1ExtendName(String shilitu1ExtendName) {
			this.shilitu1ExtendName = shilitu1ExtendName;
		}

		public Integer getShilitu2Id() {
			return shilitu2Id;
		}

		public void setShilitu2Id(Integer shilitu2Id) {
			this.shilitu2Id = shilitu2Id;
		}

		public String getShilitu2URL() {
			return shilitu2URL;
		}

		public void setShilitu2URL(String shilitu2url) {
			shilitu2URL = shilitu2url;
		}

		public String getShilitu2ExtendName() {
			return shilitu2ExtendName;
		}

		public void setShilitu2ExtendName(String shilitu2ExtendName) {
			this.shilitu2ExtendName = shilitu2ExtendName;
		}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Default Empty Constructor for class P2pLoanBasisMaterial
	 */
	public P2pLoanBasisMaterial () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class P2pLoanBasisMaterial
	 */
	public P2pLoanBasisMaterial (
		 Long in_materialId
        ) {
		this.setMaterialId(in_materialId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="materialId" type="java.lang.Long" generator-class="native"
	 */
	public Long getMaterialId() {
		return this.materialId;
	}
	
	/**
	 * Set the materialId
	 */	
	public void setMaterialId(Long aValue) {
		this.materialId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="materialName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getMaterialName() {
		return this.materialName;
	}
	
	/**
	 * Set the materialName
	 * @spring.validator type="required"
	 */	
	public void setMaterialName(String aValue) {
		this.materialName = aValue;
	}	

	/**
	 * 1=必备，2=可选	 * @return Long
	 * @hibernate.property column="materialState" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getMaterialState() {
		return this.materialState;
	}
	
	/**
	 * Set the materialState
	 * @spring.validator type="required"
	 */	
	public void setMaterialState(Long aValue) {
		this.materialState = aValue;
	}	

	/**
	 * person=个人，enterprise=企业	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getOperationType() {
		return this.operationType;
	}
	
	/**
	 * Set the operationType
	 * @spring.validator type="required"
	 */	
	public void setOperationType(String aValue) {
		this.operationType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof P2pLoanBasisMaterial)) {
			return false;
		}
		P2pLoanBasisMaterial rhs = (P2pLoanBasisMaterial) object;
		return new EqualsBuilder()
				.append(this.materialId, rhs.materialId)
				.append(this.materialName, rhs.materialName)
				.append(this.materialState, rhs.materialState)
				.append(this.operationType, rhs.operationType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.materialId) 
				.append(this.materialName) 
				.append(this.materialState) 
				.append(this.operationType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("materialId", this.materialId) 
				.append("materialName", this.materialName) 
				.append("materialState", this.materialState) 
				.append("operationType", this.operationType) 
				.toString();
	}



}
