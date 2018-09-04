package com.thirdPayInterface.ThirdPayLog.action;

import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hurong.core.web.action.BaseAction;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayLog;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayLogService;
import com.zhiwei.core.command.QueryFilter;

public class ThirdPayLogAction extends BaseAction{
	@Resource
	private ThirdPayLogService thirdPayLogService;
	private Long logId;
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String list(){
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<ThirdPayLog> list = thirdPayLogService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	public String update(){
		ThirdPayLog log = thirdPayLogService.get(logId);
		if(log!=null){
			log.setStatus(3);
			thirdPayLogService.merge(log);
		}
		return SUCCESS;
	}
}
