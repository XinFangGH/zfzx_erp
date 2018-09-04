package com.zhiwei.credit.model.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.zhiwei.credit.model.system.FileAttach;

/**
 * Resume Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class Resume extends com.zhiwei.core.model.BaseModel {


	public static String PASS="通过";
	public static String NOTPASS="不通过";
	public static String READY_INTERVIEW="准备安排面试";
	public static String PASS_INTERVIEW="通过面试";
    protected Long resumeId;
	protected String fullname;
	protected Integer age;
	protected java.util.Date birthday;
	protected String address;
	protected String zip;
	protected String sex;
	protected String position;
	protected String phone;
	protected String mobile;
	protected String email;
	protected String hobby;
	protected String religion;
	protected String party;
	protected String nationality;
	protected String race;
	protected String birthPlace;
	protected String eduCollege;
	protected String eduDegree;
	protected String eduMajor;
	protected java.util.Date startWorkDate;
	protected String idNo;
	protected String photo;
	protected String photoId;
	protected String status;
	protected String memo;
	protected String registor;
	protected java.util.Date regTime;
	protected String workCase;
	protected String trainCase;
	protected String projectCase;

	protected java.util.Set<FileAttach> resumeFiles = new java.util.HashSet<FileAttach>();

	
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	/**
	 * Default Empty Constructor for class Resume
	 */
	public Resume () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Resume
	 */
	public Resume (
		 Long in_resumeId
        ) {
		this.setResumeId(in_resumeId);
    }


	public java.util.Set getResumeFiles () {
		return resumeFiles;
	}	
	
	public void setResumeFiles (java.util.Set in_resumeFiles) {
		this.resumeFiles = in_resumeFiles;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="resumeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getResumeId() {
		return this.resumeId;
	}
	
	/**
	 * Set the resumeId
	 */	
	public void setResumeId(Long aValue) {
		this.resumeId = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 	 * @return Integer
	 * @hibernate.property column="age" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getAge() {
		return this.age;
	}
	
	/**
	 * Set the age
	 */	
	public void setAge(Integer aValue) {
		this.age = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
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
	 * 	 * @return String
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
	 * 	 * @return String
	 * @hibernate.property column="zip" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getZip() {
		return this.zip;
	}
	
	/**
	 * Set the zip
	 */	
	public void setZip(String aValue) {
		this.zip = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="position" type="java.lang.String" length="64" not-null="false" unique="false"
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
	 * 	 * @return String
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
	 * 	 * @return String
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
	 * 	 * @return String
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
	 * 	 * @return String
	 * @hibernate.property column="hobby" type="java.lang.String" length="256" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="religion" type="java.lang.String" length="128" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="party" type="java.lang.String" length="128" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="nationality" type="java.lang.String" length="32" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="race" type="java.lang.String" length="32" not-null="false" unique="false"
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
	 * 	 * @return String
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
	 * 	 * @return String
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
	 * 	 * @return String
	 * @hibernate.property column="eduDegree" type="java.lang.String" length="128" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="eduMajor" type="java.lang.String" length="128" not-null="false" unique="false"
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
	 * 	 * @return java.util.Date
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
	 * 	 * @return String
	 * @hibernate.property column="idNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getIdNo() {
		return this.idNo;
	}
	
	/**
	 * Set the idNo
	 */	
	public void setIdNo(String aValue) {
		this.idNo = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 状态
            
            通过
            未通过
            准备安排面试
            面试通过
            
            	 * @return String
	 * @hibernate.property column="status" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(String aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 	 * @return String
	 * @hibernate.property column="registor" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getRegistor() {
		return this.registor;
	}
	
	/**
	 * Set the registor
	 */	
	public void setRegistor(String aValue) {
		this.registor = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="regTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRegTime() {
		return this.regTime;
	}
	
	/**
	 * Set the regTime
	 */	
	public void setRegTime(java.util.Date aValue) {
		this.regTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="workCase" type="java.lang.String" length="65535" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="trainCase" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getTrainCase() {
		return this.trainCase;
	}
	
	/**
	 * Set the trainCase
	 */	
	public void setTrainCase(String aValue) {
		this.trainCase = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectCase" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProjectCase() {
		return this.projectCase;
	}
	
	/**
	 * Set the projectCase
	 */	
	public void setProjectCase(String aValue) {
		this.projectCase = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Resume)) {
			return false;
		}
		Resume rhs = (Resume) object;
		return new EqualsBuilder()
				.append(this.resumeId, rhs.resumeId)
				.append(this.fullname, rhs.fullname)
				.append(this.age, rhs.age)
				.append(this.birthday, rhs.birthday)
				.append(this.address, rhs.address)
				.append(this.zip, rhs.zip)
				.append(this.sex, rhs.sex)
				.append(this.position, rhs.position)
				.append(this.phone, rhs.phone)
				.append(this.mobile, rhs.mobile)
				.append(this.email, rhs.email)
				.append(this.hobby, rhs.hobby)
				.append(this.religion, rhs.religion)
				.append(this.party, rhs.party)
				.append(this.nationality, rhs.nationality)
				.append(this.race, rhs.race)
				.append(this.birthPlace, rhs.birthPlace)
				.append(this.eduCollege, rhs.eduCollege)
				.append(this.eduDegree, rhs.eduDegree)
				.append(this.eduMajor, rhs.eduMajor)
				.append(this.startWorkDate, rhs.startWorkDate)
				.append(this.idNo, rhs.idNo)
				.append(this.photo, rhs.photo)
				.append(this.status, rhs.status)
				.append(this.memo, rhs.memo)
				.append(this.registor, rhs.registor)
				.append(this.regTime, rhs.regTime)
				.append(this.workCase, rhs.workCase)
				.append(this.trainCase, rhs.trainCase)
				.append(this.projectCase, rhs.projectCase)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.resumeId) 
				.append(this.fullname) 
				.append(this.age) 
				.append(this.birthday) 
				.append(this.address) 
				.append(this.zip) 
				.append(this.sex) 
				.append(this.position) 
				.append(this.phone) 
				.append(this.mobile) 
				.append(this.email) 
				.append(this.hobby) 
				.append(this.religion) 
				.append(this.party) 
				.append(this.nationality) 
				.append(this.race) 
				.append(this.birthPlace) 
				.append(this.eduCollege) 
				.append(this.eduDegree) 
				.append(this.eduMajor) 
				.append(this.startWorkDate) 
				.append(this.idNo) 
				.append(this.photo) 
				.append(this.status) 
				.append(this.memo) 
				.append(this.registor) 
				.append(this.regTime) 
				.append(this.workCase) 
				.append(this.trainCase) 
				.append(this.projectCase) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("resumeId", this.resumeId) 
				.append("fullname", this.fullname) 
				.append("age", this.age) 
				.append("birthday", this.birthday) 
				.append("address", this.address) 
				.append("zip", this.zip) 
				.append("sex", this.sex) 
				.append("position", this.position) 
				.append("phone", this.phone) 
				.append("mobile", this.mobile) 
				.append("email", this.email) 
				.append("hobby", this.hobby) 
				.append("religion", this.religion) 
				.append("party", this.party) 
				.append("nationality", this.nationality) 
				.append("race", this.race) 
				.append("birthPlace", this.birthPlace) 
				.append("eduCollege", this.eduCollege) 
				.append("eduDegree", this.eduDegree) 
				.append("eduMajor", this.eduMajor) 
				.append("startWorkDate", this.startWorkDate) 
				.append("idNo", this.idNo) 
				.append("photo", this.photo) 
				.append("status", this.status) 
				.append("memo", this.memo) 
				.append("registor", this.registor) 
				.append("regTime", this.regTime) 
				.append("workCase", this.workCase) 
				.append("trainCase", this.trainCase) 
				.append("projectCase", this.projectCase) 
				.toString();
	}



}
