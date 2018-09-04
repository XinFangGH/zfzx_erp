package com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlConferenceRecordDaoImpl extends BaseDaoImpl<SlConferenceRecord> implements SlConferenceRecordDao{

	public SlConferenceRecordDaoImpl() {
		super(SlConferenceRecord.class);
	}
	
	public SlConferenceRecord getByProjectId(Long projId, String businessType){
		String hql = "from SlConferenceRecord AS cr where cr.projectId="+projId+" and cr.businessType='"+businessType+"'";
		List list = super.findByHql(hql);
		SlConferenceRecord scr = null;
		if(list!=null && list.size()>0){
			scr = (SlConferenceRecord)list.get(0);
		}
		return scr;
	}

}