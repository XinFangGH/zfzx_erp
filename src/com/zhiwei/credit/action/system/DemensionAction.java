package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;
import javax.annotation.Resource;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.Demension;
import com.zhiwei.credit.service.system.DemensionService;
import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class DemensionAction extends BaseAction{
	@Resource
	private DemensionService demensionService;
	private Demension demension;
	
	private Long demId;

	public Long getDemId() {
		return demId;
	}

	public void setDemId(Long demId) {
		this.demId = demId;
	}

	public Demension getDemension() {
		return demension;
	}

	public void setDemension(Demension demension) {
		this.demension = demension;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Demension> list= demensionService.getAll(filter);
		
		Type type=new TypeToken<List<Demension>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { }).serialize(list));
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
				demensionService.remove(new Long(id));
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
		Demension demension=demensionService.get(demId);
		
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(JsonUtil.getJSONSerializer(new String[] {  }).serialize(demension));
		sb.append("}");
		
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(demension.getDemId()==null){
			demensionService.save(demension);
		}else{
			Demension orgDemension=demensionService.get(demension.getDemId());
			try{
				BeanUtil.copyNotNullProperties(orgDemension, demension);
				demensionService.save(orgDemension);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 下拉维度列表
	 * @return
	 */
	public String combo(){
		String idUpdate = getRequest().getParameter("idUpdate");
		List<Demension> list=demensionService.getAll();
		StringBuffer sb=new StringBuffer();
		if("true".equals(idUpdate)){
			sb=new StringBuffer("[");
		}else{
			sb=new StringBuffer("[['0','全部..']");
		} 
		for(Demension dem:list){
			if(sb.length()>1){
				sb.append(",");
			}
			sb.append("['").append(dem.getDemId()).append("','").append(dem.getDemName()).append("']");
		}
		sb.append("]");
		jsonString=sb.toString();
		return SUCCESS;
	}
}
