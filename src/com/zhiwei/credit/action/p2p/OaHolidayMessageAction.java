package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.p2p.OaHolidayMessage;
import com.zhiwei.credit.service.p2p.OaHolidayMessageService;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;
import flexjson.JSONSerializer;

/**
 * 
 * @author 
 *
 */
public class OaHolidayMessageAction extends BaseAction{
	@Resource
	private OaHolidayMessageService oaHolidayMessageService;
	private OaHolidayMessage oaHolidayMessage;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OaHolidayMessage getOaHolidayMessage() {
		return oaHolidayMessage;
	}

	public void setOaHolidayMessage(OaHolidayMessage oaHolidayMessage) {
		this.oaHolidayMessage = oaHolidayMessage;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("holidayDate",QueryFilter.ORDER_DESC);
		List<OaHolidayMessage> list= oaHolidayMessageService.getAll(filter);
		
		Type type=new TypeToken<List<OaHolidayMessage>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer serializer = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
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
				oaHolidayMessageService.remove(new Long(id));
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
		OaHolidayMessage oaHolidayMessage=oaHolidayMessageService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(oaHolidayMessage));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		boolean flag = true;
		if(oaHolidayMessage.getId()==null){
			if (oaHolidayMessage.getType() == 2) {
				List<OaHolidayMessage> list = oaHolidayMessageService.getMessageByType(2);
				if (null != list && list.size() > 0) {
					flag = false;
				}
			}
			if (flag) {
				oaHolidayMessageService.save(oaHolidayMessage);
			}else {
				setJsonString("{success:false,message:\"已经存在生日祝福短信\"}");
				return SUCCESS;
			}
		}else{
			OaHolidayMessage orgOaHolidayMessage=oaHolidayMessageService.get(oaHolidayMessage.getId());
			try{
				BeanUtil.copyNotNullProperties(orgOaHolidayMessage, oaHolidayMessage);
				oaHolidayMessageService.save(orgOaHolidayMessage);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}


	public void test() {
		oaHolidayMessageService.messageSending();
	}
}
