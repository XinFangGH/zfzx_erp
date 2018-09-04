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


import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;
import com.zhiwei.credit.service.creditFlow.finance.SlChargeDetailService;
/**
 * 
 * @author 
 *
 */
public class SlChargeDetailAction extends BaseAction{
	@Resource
	private SlChargeDetailService slChargeDetailService;
	private SlChargeDetail slChargeDetail;
	
	private Long chargeDetailId;

	public Long getChargeDetailId() {
		return chargeDetailId;
	}

	public void setChargeDetailId(Long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	public SlChargeDetail getSlChargeDetail() {
		return slChargeDetail;
	}

	public void setSlChargeDetail(SlChargeDetail slChargeDetail) {
		this.slChargeDetail = slChargeDetail;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlChargeDetail> list= slChargeDetailService.getAll(filter);
		
		Type type=new TypeToken<List<SlChargeDetail>>(){}.getType();
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
				slChargeDetailService.remove(new Long(id));
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
		SlChargeDetail slChargeDetail=slChargeDetailService.get(chargeDetailId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slChargeDetail));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slChargeDetail.getChargeDetailId()==null){
			slChargeDetailService.save(slChargeDetail);
		}else{
			SlChargeDetail orgSlChargeDetail=slChargeDetailService.get(slChargeDetail.getChargeDetailId());
			try{
				BeanUtil.copyNotNullProperties(orgSlChargeDetail, slChargeDetail);
				slChargeDetailService.save(orgSlChargeDetail);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
