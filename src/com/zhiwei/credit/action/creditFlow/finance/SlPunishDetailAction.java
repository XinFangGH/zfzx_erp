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


import com.zhiwei.credit.model.creditFlow.finance.SlPunishDetail;
import com.zhiwei.credit.service.creditFlow.finance.SlPunishDetailService;
/**
 * 
 * @author 
 *
 */
public class SlPunishDetailAction extends BaseAction{
	@Resource
	private SlPunishDetailService slPunishDetailService;
	private SlPunishDetail slPunishDetail;
	
	private Long punishDetailId;
	private Long fundIntentId;

	public Long getPunishDetailId() {
		return punishDetailId;
	}

	public void setPunishDetailId(Long punishDetailId) {
		this.punishDetailId = punishDetailId;
	}

	public SlPunishDetail getSlPunishDetail() {
		return slPunishDetail;
	}

	public void setSlPunishDetail(SlPunishDetail slPunishDetail) {
		this.slPunishDetail = slPunishDetail;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlPunishDetail> list= slPunishDetailService.getAll(filter);
		
		Type type=new TypeToken<List<SlPunishDetail>>(){}.getType();
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
				slPunishDetailService.remove(new Long(id));
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
		SlPunishDetail slPunishDetail=slPunishDetailService.get(punishDetailId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slPunishDetail));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slPunishDetail.getPunishDetailId()==null){
			slPunishDetailService.save(slPunishDetail);
		}else{
			SlPunishDetail orgSlPunishDetail=slPunishDetailService.get(slPunishDetail.getPunishDetailId());
			try{
				BeanUtil.copyNotNullProperties(orgSlPunishDetail, slPunishDetail);
				slPunishDetailService.save(orgSlPunishDetail);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
