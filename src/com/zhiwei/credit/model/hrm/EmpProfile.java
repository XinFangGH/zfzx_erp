package com.zhiwei.credit.model.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * EmpProfile Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class EmpProfile extends com.zhiwei.core.model.BaseModel {
	public static short CHECK_FLAG_NONE = (short)0;
	public static short CHECK_FLAG_PASS = (short)1;
	public static short CHECK_FLAG_NOT_PASS = (short)2;
	
	public static short DELETE_FLAG_NOT = (short)0;
	public static short DELETE_FLAG_HAD = (short)1;
	
    protected Long profileId;
	protected String profileNo;
	protected String fullname;
	protected String address;
	protected java.util.Date birthday;
	protected String homeZip;
	protected String sex;
	protected String marriage;
	protected String designation;
	protected String position;
	protected String phone;
	protected String mobile;
	protected String openBank;
	protected String bankNo;
	protected String qq;
	protected String email;
	protected String hobby;
	protected String religion;
	protected String party;
	protected String nationality;
	protected String race;
	protected String birthPlace;
	protected String eduDegree;
	protected String eduMajor;
	protected String eduCollege;
	protected java.util.Date startWorkDate;
	protected String eduCase;
	protected String awardPunishCase;
	protected String trainingCase;
	protected String workCase;
	protected String idCard;
	protected String photo;
	protected String photoId;
	protected String standardMiNo;
	protected java.math.BigDecimal standardMoney;
	protected String standardName;
	protected String creator;
	protected java.util.Date createtime;
	protected String checkName;
	protected java.util.Date checktime;
	protected Short approvalStatus;
	protected String memo;
	protected String depName;
	protected Long depId;
	protected Short delFlag;
	protected String opprovalOpinion;
	protected Long userId;
	protected Long jobId;
	//protected com.zhiwei.credit.model.hrm.Job job;
	protected com.zhiwei.credit.model.hrm.StandSalary standSalary;


	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * Default Empty Constructor for class EmpProfile
	 */
	public EmpProfile () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class EmpProfile
	 */
	public EmpProfile (
		 Long in_profileId
        ) {
		this.setProfileId(in_profileId);
    }

	
//	public com.zhiwei.credit.model.hrm.Job getJob () {
//		return job;
//	}	
//	
//	public void setJob (com.zhiwei.credit.model.hrm.Job in_job) {
//		this.job = in_job;
//	}
	
	public com.zhiwei.credit.model.hrm.StandSalary getStandSalary () {
		return standSalary;
	}	
	
	public void setStandSalary (com.zhiwei.credit.model.hrm.StandSalary in_standSalary) {
		this.standSalary = in_standSalary;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="profileId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProfileId() {
		return this.profileId;
	}
	
	/**
	 * Set the profileId
	 */	
	public void setProfileId(Long aValue) {
		this.profileId = aValue;
	}	

	/**
	 * 档案编号	 * @return String
	 * @hibernate.property column="profileNo" type="java.lang.String" length="100" not-null="true" unique="false"
	 */
	public String getProfileNo() {
		return this.profileNo;
	}
	
	/**
	 * Set the profileNo
	 * @spring.validator type="required"
	 */	
	public void setProfileNo(String aValue) {
		this.profileNo = aValue;
	}	

	/**
	 * 员工姓名	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 * @spring.validator type="required"
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 家庭地址	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 出生日期	 * @return java.util.Date
	 * @hibernate.property column="birthday" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	/**
	 * Set the birthday
	 */	
	public void setBirthday(java.util.Date aValue) {
		this.birthday = aValue;
	}	

	/**
	 * 家庭邮编	 * @return String
	 * @hibernate.property column="homeZip" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getHomeZip() {
		return this.homeZip;
	}
	
	/**
	 * Set the homeZip
	 */	
	public void setHomeZip(String aValue) {
		this.homeZip = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sex" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 */	
	public void setSex(String aValue) {
		this.sex = aValue;
	}	

	/**
	 * 婚姻状况
            已婚
            未婚	 * @return String
	 * @hibernate.property column="marriage" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getMarriage() {
		return this.marriage;
	}
	
	/**
	 * Set the marriage
	 */	
	public void setMarriage(String aValue) {
		this.marriage = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="designation" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getDesignation() {
		return this.designation;
	}
	
	/**
	 * Set the designation
	 */	
	public void setDesignation(String aValue) {
		this.designation = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
//	public Long getJobId() {
//		return this.getJob()==null?null:this.getJob().getJobId();
//	}
	
	/**
	 * Set the jobId
	 */	
//	public void setJobId(Long aValue) {
//	    if (aValue==null) {
//	    	job = null;
//	    } else if (job == null) {
//	        job = new com.zhiwei.credit.model.hrm.Job(aValue);
//	        job.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			job.setJobId(aValue);
//	    }
//	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="position" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPosition() {
		return this.position;
	}
	
	/**
	 * Set the position
	 */	
	public void setPosition(String aValue) {
		this.position = aValue;
	}	

	/**
	 * 电话号码	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 手机号码	 * @return String
	 * @hibernate.property column="mobile" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * Set the mobile
	 */	
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}	

	/**
	 * 开户银行	 * @return String
	 * @hibernate.property column="openBank" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getOpenBank() {
		return this.openBank;
	}
	
	/**
	 * Set the openBank
	 */	
	public void setOpenBank(String aValue) {
		this.openBank = aValue;
	}	

	/**
	 * 银行账号	 * @return String
	 * @hibernate.property column="bankNo" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBankNo() {
		return this.bankNo;
	}
	
	/**
	 * Set the bankNo
	 */	
	public void setBankNo(String aValue) {
		this.bankNo = aValue;
	}	

	/**
	 * QQ号码	 * @return String
	 * @hibernate.property column="qq" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getQq() {
		return this.qq;
	}
	
	/**
	 * Set the qq
	 */	
	public void setQq(String aValue) {
		this.qq = aValue;
	}	

	/**
	 * 电子邮箱	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 爱好	 * @return String
	 * @hibernate.property column="hobby" type="java.lang.String" length="300" not-null="false" unique="false"
	 */
	public String getHobby() {
		return this.hobby;
	}
	
	/**
	 * Set the hobby
	 */	
	public void setHobby(String aValue) {
		this.hobby = aValue;
	}	

	/**
	 * 宗教信仰	 * @return String
	 * @hibernate.property column="religion" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getReligion() {
		return this.religion;
	}
	
	/**
	 * Set the religion
	 */	
	public void setReligion(String aValue) {
		this.religion = aValue;
	}	

	/**
	 * 政治面貌	 * @return String
	 * @hibernate.property column="party" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getParty() {
		return this.party;
	}
	
	/**
	 * Set the party
	 */	
	public void setParty(String aValue) {
		this.party = aValue;
	}	

	/**
	 * 国籍	 * @return String
	 * @hibernate.property column="nationality" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getNationality() {
		return this.nationality;
	}
	
	/**
	 * Set the nationality
	 */	
	public void setNationality(String aValue) {
		this.nationality = aValue;
	}	

	/**
	 * 民族	 * @return String
	 * @hibernate.property column="race" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getRace() {
		return this.race;
	}
	
	/**
	 * Set the race
	 */	
	public void setRace(String aValue) {
		this.race = aValue;
	}	

	/**
	 * 出生地	 * @return String
	 * @hibernate.property column="birthPlace" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getBirthPlace() {
		return this.birthPlace;
	}
	
	/**
	 * Set the birthPlace
	 */	
	public void setBirthPlace(String aValue) {
		this.birthPlace = aValue;
	}	

	/**
	 * 学历	 * @return String
	 * @hibernate.property column="eduDegree" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getEduDegree() {
		return this.eduDegree;
	}
	
	/**
	 * Set the eduDegree
	 */	
	public void setEduDegree(String aValue) {
		this.eduDegree = aValue;
	}	

	/**
	 * 专业	 * @return String
	 * @hibernate.property column="eduMajor" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getEduMajor() {
		return this.eduMajor;
	}
	
	/**
	 * Set the eduMajor
	 */	
	public void setEduMajor(String aValue) {
		this.eduMajor = aValue;
	}	

	/**
	 * 毕业院校	 * @return String
	 * @hibernate.property column="eduCollege" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEduCollege() {
		return this.eduCollege;
	}
	
	/**
	 * Set the eduCollege
	 */	
	public void setEduCollege(String aValue) {
		this.eduCollege = aValue;
	}	

	/**
	 * 参加工作时间	 * @return java.util.Date
	 * @hibernate.property column="startWorkDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartWorkDate() {
		return this.startWorkDate;
	}
	
	/**
	 * Set the startWorkDate
	 */	
	public void setStartWorkDate(java.util.Date aValue) {
		this.startWorkDate = aValue;
	}	

	/**
	 * 教育背景	 * @return String
	 * @hibernate.property column="eduCase" type="java.lang.String" length="2048" not-null="false" unique="false"
	 */
	public String getEduCase() {
		return this.eduCase;
	}
	
	/**
	 * Set the eduCase
	 */	
	public void setEduCase(String aValue) {
		this.eduCase = aValue;
	}	

	/**
	 * 奖惩情况	 * @return String
	 * @hibernate.property column="awardPunishCase" type="java.lang.String" length="2048" not-null="false" unique="false"
	 */
	public String getAwardPunishCase() {
		return this.awardPunishCase;
	}
	
	/**
	 * Set the awardPunishCase
	 */	
	public void setAwardPunishCase(String aValue) {
		this.awardPunishCase = aValue;
	}	

	/**
	 * 培训情况	 * @return String
	 * @hibernate.property column="trainingCase" type="java.lang.String" length="2048" not-null="false" unique="false"
	 */
	public String getTrainingCase() {
		return this.trainingCase;
	}
	
	/**
	 * Set the trainingCase
	 */	
	public void setTrainingCase(String aValue) {
		this.trainingCase = aValue;
	}	

	/**
	 * 工作经历	 * @return String
	 * @hibernate.property column="workCase" type="java.lang.String" length="2048" not-null="false" unique="false"
	 */
	public String getWorkCase() {
		return this.workCase;
	}
	
	/**
	 * Set the workCase
	 */	
	public void setWorkCase(String aValue) {
		this.workCase = aValue;
	}	

	/**
	 * 身份证号	 * @return String
	 * @hibernate.property column="idCard" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getIdCard() {
		return this.idCard;
	}
	
	/**
	 * Set the idCard
	 */	
	public void setIdCard(String aValue) {
		this.idCard = aValue;
	}	

	/**
	 * 照片	 * @return String
	 * @hibernate.property column="photo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPhoto() {
		return this.photo;
	}
	
	/**
	 * Set the photo
	 */	
	public void setPhoto(String aValue) {
		this.photo = aValue;
	}	

	/**
	 * 薪酬标准编号	 * @return String
	 * @hibernate.property column="standardMiNo" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getStandardMiNo() {
		return this.standardMiNo;
	}
	
	/**
	 * Set the standardMiNo
	 */	
	public void setStandardMiNo(String aValue) {
		this.standardMiNo = aValue;
	}	

	/**
	 * 薪酬标准金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="standardMoney" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getStandardMoney() {
		return this.standardMoney;
	}
	
	/**
	 * Set the standardMoney
	 */	
	public void setStandardMoney(java.math.BigDecimal aValue) {
		this.standardMoney = aValue;
	}	

	/**
	 * 薪酬标准单名称	 * @return String
	 * @hibernate.property column="standardName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getStandardName() {
		return this.standardName;
	}
	
	/**
	 * Set the standardName
	 */	
	public void setStandardName(String aValue) {
		this.standardName = aValue;
	}	

	/**
	 * 薪酬标准单编号	 * @return Long
	 */
	public Long getStandardId() {
		return this.getStandSalary()==null?null:this.getStandSalary().getStandardId();
	}
	
	/**
	 * Set the standardId
	 */	
	public void setStandardId(Long aValue) {
	    if (aValue==null) {
	    	standSalary = null;
	    } else if (standSalary == null) {
	        standSalary = new com.zhiwei.credit.model.hrm.StandSalary(aValue);
	        standSalary.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			standSalary.setStandardId(aValue);
	    }
	}	

	/**
	 * 建档人	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 建档时间	 * @return java.util.Date
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
	 * 审核人	 * @return String
	 * @hibernate.property column="checkName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCheckName() {
		return this.checkName;
	}
	
	/**
	 * Set the checkName
	 */	
	public void setCheckName(String aValue) {
		this.checkName = aValue;
	}	

	/**
	 * 审核时间	 * @return java.util.Date
	 * @hibernate.property column="checktime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getChecktime() {
		return this.checktime;
	}
	
	/**
	 * Set the checktime
	 */	
	public void setChecktime(java.util.Date aValue) {
		this.checktime = aValue;
	}	

	/**
	 * 核审状态
            0=未审批
            1=通过审核
            2=未通过审核	 * @return Short
	 * @hibernate.property column="approvalStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getApprovalStatus() {
		return this.approvalStatus;
	}
	
	/**
	 * Set the approvalStatus
	 */	
	public void setApprovalStatus(Short aValue) {
		this.approvalStatus = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="memo" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getMemo() {
		return this.memo;
	}
	
	/**
	 * Set the memo
	 */	
	public void setMemo(String aValue) {
		this.memo = aValue;
	}	

	/**
	 * 所属部门或公司	 * @return String
	 * @hibernate.property column="depName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getDepName() {
		return this.depName;
	}
	
	/**
	 * Set the depName
	 */	
	public void setDepName(String aValue) {
		this.depName = aValue;
	}	

	/**
	 * 所属部门Id	 * @return Long
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

	/**
	 * 删除状态
            0=未删除
            1=删除	 * @return Short
	 * @hibernate.property column="delFlag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getDelFlag() {
		return this.delFlag;
	}
	
	/**
	 * Set the delFlag
	 * @spring.validator type="required"
	 */	
	public void setDelFlag(Short aValue) {
		this.delFlag = aValue;
	}	

	public String getOpprovalOpinion() {
		return opprovalOpinion;
	}

	public void setOpprovalOpinion(String opprovalOpinion) {
		this.opprovalOpinion = opprovalOpinion;
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
		if (!(object instanceof EmpProfile)) {
			return false;
		}
		EmpProfile rhs = (EmpProfile) object;
		return new EqualsBuilder()
				.append(this.profileId, rhs.profileId)
				.append(this.profileNo, rhs.profileNo)
				.append(this.fullname, rhs.fullname)
				.append(this.address, rhs.address)
				.append(this.birthday, rhs.birthday)
				.append(this.homeZip, rhs.homeZip)
				.append(this.sex, rhs.sex)
				.append(this.marriage, rhs.marriage)
				.append(this.designation, rhs.designation)
						.append(this.position, rhs.position)
				.append(this.phone, rhs.phone)
				.append(this.mobile, rhs.mobile)
				.append(this.openBank, rhs.openBank)
				.append(this.bankNo, rhs.bankNo)
				.append(this.qq, rhs.qq)
				.append(this.email, rhs.email)
				.append(this.hobby, rhs.hobby)
				.append(this.religion, rhs.religion)
				.append(this.party, rhs.party)
				.append(this.nationality, rhs.nationality)
				.append(this.race, rhs.race)
				.append(this.birthPlace, rhs.birthPlace)
				.append(this.eduDegree, rhs.eduDegree)
				.append(this.eduMajor, rhs.eduMajor)
				.append(this.eduCollege, rhs.eduCollege)
				.append(this.startWorkDate, rhs.startWorkDate)
				.append(this.eduCase, rhs.eduCase)
				.append(this.awardPunishCase, rhs.awardPunishCase)
				.append(this.trainingCase, rhs.trainingCase)
				.append(this.workCase, rhs.workCase)
				.append(this.idCard, rhs.idCard)
				.append(this.photo, rhs.photo)
				.append(this.standardMiNo, rhs.standardMiNo)
				.append(this.standardMoney, rhs.standardMoney)
				.append(this.standardName, rhs.standardName)
						.append(this.creator, rhs.creator)
				.append(this.createtime, rhs.createtime)
				.append(this.checkName, rhs.checkName)
				.append(this.checktime, rhs.checktime)
				.append(this.approvalStatus, rhs.approvalStatus)
				.append(this.opprovalOpinion, rhs.opprovalOpinion)
				.append(this.memo, rhs.memo)
				.append(this.depName, rhs.depName)
				.append(this.depId, rhs.depId)
				.append(this.delFlag, rhs.delFlag)
				.append(this.userId,rhs.userId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.profileId) 
				.append(this.profileNo) 
				.append(this.fullname) 
				.append(this.address) 
				.append(this.birthday) 
				.append(this.homeZip) 
				.append(this.sex) 
				.append(this.marriage) 
				.append(this.designation) 
						.append(this.position) 
				.append(this.phone) 
				.append(this.mobile) 
				.append(this.openBank) 
				.append(this.bankNo) 
				.append(this.qq) 
				.append(this.email) 
				.append(this.hobby) 
				.append(this.religion) 
				.append(this.party) 
				.append(this.nationality) 
				.append(this.race) 
				.append(this.birthPlace) 
				.append(this.eduDegree) 
				.append(this.eduMajor) 
				.append(this.eduCollege) 
				.append(this.startWorkDate) 
				.append(this.eduCase) 
				.append(this.awardPunishCase) 
				.append(this.trainingCase) 
				.append(this.workCase) 
				.append(this.idCard) 
				.append(this.photo) 
				.append(this.standardMiNo) 
				.append(this.standardMoney) 
				.append(this.standardName) 
						.append(this.creator) 
				.append(this.createtime) 
				.append(this.checkName) 
				.append(this.checktime) 
				.append(this.approvalStatus) 
				.append(this.memo) 
				.append(this.depName) 
				.append(this.depId) 
				.append(this.delFlag) 
				.append(this.opprovalOpinion)
				.append(this.userId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("profileId", this.profileId) 
				.append("profileNo", this.profileNo) 
				.append("fullname", this.fullname) 
				.append("address", this.address) 
				.append("birthday", this.birthday) 
				.append("homeZip", this.homeZip) 
				.append("sex", this.sex) 
				.append("marriage", this.marriage) 
				.append("designation", this.designation) 
						.append("position", this.position) 
				.append("phone", this.phone) 
				.append("mobile", this.mobile) 
				.append("openBank", this.openBank) 
				.append("bankNo", this.bankNo) 
				.append("qq", this.qq) 
				.append("email", this.email) 
				.append("hobby", this.hobby) 
				.append("religion", this.religion) 
				.append("party", this.party) 
				.append("nationality", this.nationality) 
				.append("race", this.race) 
				.append("birthPlace", this.birthPlace) 
				.append("eduDegree", this.eduDegree) 
				.append("eduMajor", this.eduMajor) 
				.append("eduCollege", this.eduCollege) 
				.append("startWorkDate", this.startWorkDate) 
				.append("eduCase", this.eduCase) 
				.append("awardPunishCase", this.awardPunishCase) 
				.append("trainingCase", this.trainingCase) 
				.append("workCase", this.workCase) 
				.append("idCard", this.idCard) 
				.append("photo", this.photo) 
				.append("standardMiNo", this.standardMiNo) 
				.append("standardMoney", this.standardMoney) 
				.append("standardName", this.standardName) 
						.append("creator", this.creator) 
				.append("createtime", this.createtime) 
				.append("checkName", this.checkName) 
				.append("checktime", this.checktime) 
				.append("approvalStatus", this.approvalStatus) 
				.append("memo", this.memo) 
				.append("depName", this.depName) 
				.append("depId", this.depId) 
				.append("delFlag", this.delFlag) 
				.append("opprovalOpinion",this.opprovalOpinion)
				.append("userId",this.userId)
				.toString();
	}



}
