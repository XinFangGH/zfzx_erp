package com.zhiwei.credit.dao.creditFlow.smallLoan.meeting;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;

/**
 * 
 * @author 
 *
 */
public interface SlConferenceRecordDao extends BaseDao<SlConferenceRecord>{
	
	public SlConferenceRecord getByProjectId(Long projId, String businessType);
}