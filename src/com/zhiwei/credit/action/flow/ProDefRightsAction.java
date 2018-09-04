package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.ProDefRights;
import com.zhiwei.credit.service.flow.ProDefRightsService;
/**
 * 
 * @author 
 *
 */
public class ProDefRightsAction extends BaseAction{
	@Resource
	private ProDefRightsService proDefRightsService;
	private ProDefRights proDefRights;
	
	private Long rightsId;

	public Long getRightsId() {
		return rightsId;
	}

	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	public ProDefRights getProDefRights() {
		return proDefRights;
	}

	public void setProDefRights(ProDefRights proDefRights) {
		this.proDefRights = proDefRights;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ProDefRights> list= proDefRightsService.getAll(filter);
		
		Type type=new TypeToken<List<ProDefRights>>(){}.getType();
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
				proDefRightsService.remove(new Long(id));
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
		String proTypeId = getRequest().getParameter("proTypeId");
		if(StringUtils.isNotEmpty(proTypeId)){
			 proDefRights=proDefRightsService.findByTypeId(new Long(proTypeId));
		}else{
			String defId = getRequest().getParameter("defId");
			if(StringUtils.isNotEmpty(defId)){
				proDefRights=proDefRightsService.findByDefId(new Long(defId));
			}
			
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(proDefRights));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//IDS 前后加"," 用于查询
		proDefRights.setUserIds(splitIds(proDefRights.getUserIds()));
		proDefRights.setRoleIds(splitIds(proDefRights.getRoleIds()));
		proDefRights.setDepIds(splitIds(proDefRights.getDepIds()));
		
		if(proDefRights.getRightsId()==null){
			proDefRightsService.save(proDefRights);
		}else{
			ProDefRights orgProDefRights=proDefRightsService.get(proDefRights.getRightsId());
			try{
				BeanUtil.copyNotNullProperties(orgProDefRights, proDefRights);
				proDefRightsService.save(orgProDefRights);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 此方法用于把IDs 字符串变成 ",1,2,3,"的形式
	 * @param Ids
	 * @return
	 */
	private String splitIds(String Ids){
		if(StringUtils.isNotEmpty(Ids)){
			String[] ids = Ids.split(",");
			StringBuffer newIds = new StringBuffer(",");
			for(String id : ids){
				if(StringUtils.isNotEmpty(id)){
					newIds.append(id).append(",");
				}
				
			}
			return newIds.toString();
		}
		return "";
	}
}
