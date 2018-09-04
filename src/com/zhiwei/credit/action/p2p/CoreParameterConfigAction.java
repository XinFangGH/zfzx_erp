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
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.p2p.CoreParameterConfig;
import com.zhiwei.credit.service.p2p.CoreParameterConfigService;
/**
 * 
 * @author 
 *
 */
public class CoreParameterConfigAction extends BaseAction{
	@Resource
	private CoreParameterConfigService coreParameterConfigService;
	private CoreParameterConfig coreParameterConfig;
	
	private Long id;
	private Integer typeId;
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CoreParameterConfig getCoreParameterConfig() {
		return coreParameterConfig;
	}

	public void setCoreParameterConfig(CoreParameterConfig coreParameterConfig) {
		this.coreParameterConfig = coreParameterConfig;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CoreParameterConfig> list= coreParameterConfigService.getAll(filter);
		
		Type type=new TypeToken<List<CoreParameterConfig>>(){}.getType();
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
				coreParameterConfigService.remove(new Long(id));
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
		CoreParameterConfig coreParameterConfig=null;
		List<CoreParameterConfig> list= coreParameterConfigService.getAll();
		if(list!=null){
			if(typeId!=null){
				if(list.size()>=typeId&&typeId>0){
					coreParameterConfig=list.get(typeId-1);
				}
			}else{
				coreParameterConfig=list.get(0);
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(coreParameterConfig));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(coreParameterConfig.getId()==null){
			coreParameterConfigService.save(coreParameterConfig);
		}else{
			CoreParameterConfig orgCoreParameterConfig=coreParameterConfigService.get(coreParameterConfig.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCoreParameterConfig, coreParameterConfig);
				coreParameterConfigService.save(orgCoreParameterConfig);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
