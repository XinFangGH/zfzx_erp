package com.zhiwei.credit.action.creditFlow.financingAgency.typeManger;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepGtorz;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepGtorzService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlKeepGtorzAction extends BaseAction{
	@Resource
	private PlKeepGtorzService plKeepGtorzService;
	private PlKeepGtorz plKeepGtorz;
	
	private Long projGtOrzId;

	public Long getProjGtOrzId() {
		return projGtOrzId;
	}

	public void setProjGtOrzId(Long projGtOrzId) {
		this.projGtOrzId = projGtOrzId;
	}

	public PlKeepGtorz getPlKeepGtorz() {
		return plKeepGtorz;
	}

	public void setPlKeepGtorz(PlKeepGtorz plKeepGtorz) {
		this.plKeepGtorz = plKeepGtorz;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlKeepGtorz> list= plKeepGtorzService.getAll(filter);
		
		Type type=new TypeToken<List<PlKeepGtorz>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();

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
				plKeepGtorzService.remove(new Long(id));
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
		PlKeepGtorz plKeepGtorz=plKeepGtorzService.get(projGtOrzId);
		
		//将数据转成JSON格式
		StringBuffer buff = new StringBuffer("{success:true,data:");
		
JSONSerializer serializer = JsonUtil.getJSONSerializer();
buff.append(serializer.exclude(new String[] { "class" })
		.serialize(plKeepGtorz));
buff.append("}");

jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plKeepGtorz.getProjGtOrzId()==null){
			plKeepGtorzService.save(plKeepGtorz);
		}else{
			PlKeepGtorz orgPlKeepGtorz=plKeepGtorzService.get(plKeepGtorz.getProjGtOrzId());
			try{
				BeanUtil.copyNotNullProperties(orgPlKeepGtorz, plKeepGtorz);
				plKeepGtorzService.save(orgPlKeepGtorz);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
