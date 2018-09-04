package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.finance.SlAccrued;
import com.zhiwei.credit.service.creditFlow.finance.SlAccruedService;
/**
 * 
 * @author 
 *
 */
public class SlAccruedAction extends BaseAction{
	@Resource
	private SlAccruedService slAccruedService;
	private SlAccrued slAccrued;
	
	private Long accruedId;

	public Long getAccruedId() {
		return accruedId;
	}

	public void setAccruedId(Long accruedId) {
		this.accruedId = accruedId;
	}

	public SlAccrued getSlAccrued() {
		return slAccrued;
	}

	public void setSlAccrued(SlAccrued slAccrued) {
		this.slAccrued = slAccrued;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlAccrued> list= slAccruedService.getAll(filter);
		
		Type type=new TypeToken<List<SlAccrued>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				slAccruedService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlAccrued slAccrued=slAccruedService.get(accruedId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slAccrued));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slAccrued.getAccruedId()==null){
			slAccruedService.save(slAccrued);
		}else{
			SlAccrued orgSlAccrued=slAccruedService.get(slAccrued.getAccruedId());
			try{
				BeanUtil.copyNotNullProperties(orgSlAccrued, slAccrued);
				slAccruedService.save(orgSlAccrued);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
