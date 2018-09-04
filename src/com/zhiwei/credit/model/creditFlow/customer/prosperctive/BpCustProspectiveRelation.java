package com.zhiwei.credit.model.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * BpCustProspectiveRelation Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustProspectiveRelation extends com.zhiwei.core.model.BaseModel {

    protected Long relateId;
	protected String relateName;
	protected Integer relateship;
	protected Integer relateSex;
	protected String relatejob;
	protected Integer isMain;
	protected String relateHouseDress;
	protected String relationShipValue;
	protected String relationCellPhone;
	protected com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive bpCustProsperctive;


	public String getRelationCellPhone() {
		return relationCellPhone;
	}

	public void setRelationCellPhone(String relationCellPhone) {
		this.relationCellPhone = relationCellPhone;
	}

	/**
	 * Default Empty Constructor for class BpCustProspectiveRelation
	 */
	public BpCustProspectiveRelation () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustProspectiveRelation
	 */
	public BpCustProspectiveRelation (
		 Long in_relateId
        ) {
		this.setRelateId(in_relateId);
    }

	
	public com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive getBpCustProsperctive () {
		return bpCustProsperctive;
	}	
	
	public void setBpCustProsperctive (com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive in_bpCustProsperctive) {
		this.bpCustProsperctive = in_bpCustProsperctive;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="relateId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRelateId() {
		return this.relateId;
	}
	
	/**
	 * Set the relateId
	 */	
	public void setRelateId(Long aValue) {
		this.relateId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="relateName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelateName() {
		return this.relateName;
	}
	
	/**
	 * Set the relateName
	 */	
	public void setRelateName(String aValue) {
		this.relateName = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="relateship" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRelateship() {
		return this.relateship;
	}
	
	/**
	 * Set the relateship
	 */	
	public void setRelateship(Integer aValue) {
		this.relateship = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="relateSex" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRelateSex() {
		return this.relateSex;
	}
	
	/**
	 * Set the relateSex
	 */	
	public void setRelateSex(Integer aValue) {
		this.relateSex = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="relatejob" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelatejob() {
		return this.relatejob;
	}
	
	/**
	 * Set the relatejob
	 */	
	public void setRelatejob(String aValue) {
		this.relatejob = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="isMain" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsMain() {
		return this.isMain;
	}
	
	/**
	 * Set the isMain
	 */	
	public void setIsMain(Integer aValue) {
		this.isMain = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="relateHouseDress" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelateHouseDress() {
		return this.relateHouseDress;
	}
	
	/**
	 * Set the relateHouseDress
	 */	
	public void setRelateHouseDress(String aValue) {
		this.relateHouseDress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="relationShipValue" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelationShipValue() {
		return this.relationShipValue;
	}
	
	/**
	 * Set the relationShipValue
	 */	
	public void setRelationShipValue(String aValue) {
		this.relationShipValue = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getPerId() {
		return this.getBpCustProsperctive()==null?null:this.getBpCustProsperctive().getPerId();
	}
	
	/**
	 * Set the perId
	 */	
	public void setPerId(Long aValue) {
	    if (aValue==null) {
	    	bpCustProsperctive = null;
	    } else if (bpCustProsperctive == null) {
	        bpCustProsperctive = new com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive(aValue);
	        bpCustProsperctive.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			bpCustProsperctive.setPerId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustProspectiveRelation)) {
			return false;
		}
		BpCustProspectiveRelation rhs = (BpCustProspectiveRelation) object;
		return new EqualsBuilder()
				.append(this.relateId, rhs.relateId)
				.append(this.relateName, rhs.relateName)
				.append(this.relateship, rhs.relateship)
				.append(this.relateSex, rhs.relateSex)
				.append(this.relatejob, rhs.relatejob)
				.append(this.isMain, rhs.isMain)
				.append(this.relateHouseDress, rhs.relateHouseDress)
				.append(this.relationShipValue, rhs.relationShipValue)
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.relateId) 
				.append(this.relateName) 
				.append(this.relateship) 
				.append(this.relateSex) 
				.append(this.relatejob) 
				.append(this.isMain) 
				.append(this.relateHouseDress) 
				.append(this.relationShipValue) 
						.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("relateId", this.relateId) 
				.append("relateName", this.relateName) 
				.append("relateship", this.relateship) 
				.append("relateSex", this.relateSex) 
				.append("relatejob", this.relatejob) 
				.append("isMain", this.isMain) 
				.append("relateHouseDress", this.relateHouseDress) 
				.append("relationShipValue", this.relationShipValue) 
						.toString();
	}



}
