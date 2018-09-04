package com.zhiwei.credit.dao.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.hrm.ResumeDao;
import com.zhiwei.credit.model.hrm.Resume;

public class ResumeDaoImpl extends BaseDaoImpl<Resume> implements ResumeDao{

	public ResumeDaoImpl() {
		super(Resume.class);
	}

}