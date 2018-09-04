package com.zhiwei.credit.service.creditFlow.smallLoan.meeting;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;

/**
 * 
 * @author 
 *
 */
public interface SlConferenceRecordService extends BaseService<SlConferenceRecord>{
	public Integer saveAfterFlow(FlowRunInfo flowRunInfo);
	
	public SlConferenceRecord getByProjectId(Long projId, String businessType);
	
	/**
	 * 通过projectId和businessType删除所有项目相关的会议纪要
	 * */
	public Boolean deleteAllConferenceRecordByProjectIdAndBusinessType(Long projId, String businessType);
}


