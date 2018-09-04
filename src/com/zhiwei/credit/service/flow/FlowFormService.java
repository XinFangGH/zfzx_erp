package com.zhiwei.credit.service.flow;


import com.zhiwei.core.model.DynaModel;
import com.zhiwei.credit.action.flow.FlowRunInfo;
/**
 * 流程表单与实体表操作类
 * <B><P>EST-BPM -- http://www.hurongtime.com</P></B>
 * <B><P>Copyright (C)  JinZhi WanWei Software Company (北京互融时代软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P></P> 
 * @see com.zhiwei.credit.service.flow.FlowFormService
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-12-23下午05:43:16
 */
public interface FlowFormService {
	/**
	 * 保存业务数据至实体表
	 * @param flowRunInfo
	 */
	public DynaModel doSaveData(FlowRunInfo flowRunInfo);
	public boolean deleteItems(String strIds,Long tableId);
}
