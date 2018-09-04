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


import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.service.creditFlow.finance.SlFundDetailService;
/**
 * 
 * @author 
 *
 */
public class SlFundDetailAction extends BaseAction{
	@Resource
	private SlFundDetailService slFundDetailService;
	private SlFundDetail slFundDetail;
	
	private Long fundDetailId;

	public Long getFundDetailId() {
		return fundDetailId;
	}

	public void setFundDetailId(Long fundDetailId) {
		this.fundDetailId = fundDetailId;
	}

	public SlFundDetail getSlFundDetail() {
		return slFundDetail;
	}

	public void setSlFundDetail(SlFundDetail slFundDetail) {
		this.slFundDetail = slFundDetail;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlFundDetail> list= slFundDetailService.getAll(filter);
		
		Type type=new TypeToken<List<SlFundDetail>>(){}.getType();
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
				slFundDetailService.remove(new Long(id));
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
		SlFundDetail slFundDetail=slFundDetailService.get(fundDetailId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slFundDetail));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slFundDetail.getFundDetailId()==null){
			slFundDetailService.save(slFundDetail);
		}else{
			SlFundDetail orgSlFundDetail=slFundDetailService.get(slFundDetail.getFundDetailId());
			try{
				BeanUtil.copyNotNullProperties(orgSlFundDetail, slFundDetail);
				slFundDetailService.save(orgSlFundDetail);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
