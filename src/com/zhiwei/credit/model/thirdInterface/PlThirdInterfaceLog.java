package com.zhiwei.credit.model.thirdInterface;
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
 * PlThirdInterfaceLog Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlThirdInterfaceLog extends com.hurong.core.model.BaseModel {
	/**
	 * 操作人类型 0 线下 1线上
	 */
    public static  final int MEMTYPE0=0; 
    /**
	 * 操作人类型 0 线下 1线上
	 */
    public static  final int MEMTYPE1=1; 
  /**
   * 接口类型 1第三方支付 2 短信
   */
    public static  final Long TYPE1=Long.valueOf(1); 
    /**
     * 接口类型 1第三方支付 2 短信
     */
    public static  final Long TYPE2=Long.valueOf(2);  
    /**
     * 接口类型 1第三方支付 2 短信  3 邮件
     */
    public static  final Long TYPE3=Long.valueOf(3);  
    /**
     * 接口类型 1第三方支付 2 短信
     */
      public static  final String TYPENAME1="第三方支付"; 
      /**
       * 接口类型 1第三方支付 2 短信
       */
      public static  final String TYPENAME2="短信";  
      /**
       * 接口类型 1第三方支付 2 短信  3邮件
       */
      public static  final String TYPENAME3="邮件"; 
    protected Long id;
	protected String typeName;
	protected Long typeId;
	protected String code;
	protected String codeMsg;
	protected String bigMsg;
	protected java.util.Date createTime;
	protected String interfaceName;
	protected Long memberId;
	protected String memberName;
	protected String remark1;
	protected String remark2;
	protected String remark3;
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	protected Integer memberType;


	/**
	 * Default Empty Constructor for class PlThirdInterfaceLog
	 */
	public PlThirdInterfaceLog () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlThirdInterfaceLog
	 */
	public PlThirdInterfaceLog (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 接口类型 1 第三方支付 2 短信 	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	/**
	 * 类型id 1第三方支付 2 短信 	 * @return Long
	 * @hibernate.property column="typeId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getTypeId() {
		return this.typeId;
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
		this.typeId = aValue;
	}	

	/**
	 * 返回代码	 * @return String
	 * @hibernate.property column="code" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Set the code
	 */	
	public void setCode(String aValue) {
		this.code = aValue;
	}	

	/**
	 * 代码对应 说明	 * @return String
	 * @hibernate.property column="codeMsg" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCodeMsg() {
		return this.codeMsg;
	}
	
	/**
	 * Set the codeMsg
	 */	
	public void setCodeMsg(String aValue) {
		this.codeMsg = aValue;
	}	

	/**
	 * 返回的完整信息	 * @return String
	 * @hibernate.property column="bigMsg" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getBigMsg() {
		return this.bigMsg;
	}
	
	/**
	 * Set the bigMsg
	 */	
	public void setBigMsg(String aValue) {
		this.bigMsg = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 接口名称	 * @return String
	 * @hibernate.property column="interfaceName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInterfaceName() {
		return this.interfaceName;
	}
	
	/**
	 * Set the interfaceName
	 */	
	public void setInterfaceName(String aValue) {
		this.interfaceName = aValue;
	}	

	/**
	 * 操作人	 * @return Long
	 * @hibernate.property column="memberId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMemberId() {
		return this.memberId;
	}
	
	/**
	 * Set the memberId
	 */	
	public void setMemberId(Long aValue) {
		this.memberId = aValue;
	}	

	/**
	 * 操作人类型 0 线下 1 线上	 * @return Integer
	 * @hibernate.property column="memberType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMemberType() {
		return this.memberType;
	}
	
	/**
	 * Set the memberType
	 */	
	public void setMemberType(Integer aValue) {
		this.memberType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlThirdInterfaceLog)) {
			return false;
		}
		PlThirdInterfaceLog rhs = (PlThirdInterfaceLog) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.typeName, rhs.typeName)
				.append(this.typeId, rhs.typeId)
				.append(this.code, rhs.code)
				.append(this.codeMsg, rhs.codeMsg)
				.append(this.bigMsg, rhs.bigMsg)
				.append(this.createTime, rhs.createTime)
				.append(this.interfaceName, rhs.interfaceName)
				.append(this.memberId, rhs.memberId)
				.append(this.memberType, rhs.memberType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.typeName) 
				.append(this.typeId) 
				.append(this.code) 
				.append(this.codeMsg) 
				.append(this.bigMsg) 
				.append(this.createTime) 
				.append(this.interfaceName) 
				.append(this.memberId) 
				.append(this.memberType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("typeName", this.typeName) 
				.append("typeId", this.typeId) 
				.append("code", this.code) 
				.append("codeMsg", this.codeMsg) 
				.append("bigMsg", this.bigMsg) 
				.append("createTime", this.createTime) 
				.append("interfaceName", this.interfaceName) 
				.append("memberId", this.memberId) 
				.append("memberType", this.memberType) 
				.toString();
	}



}
