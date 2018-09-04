package com.zhiwei.credit.action.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.credit.proj.entity.Content;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.common.AreaManagement;
import com.zhiwei.credit.service.creditFlow.common.AreaManagementService;
/**
 * 
 * @author 
 *
 */
public class AreaManagementAction extends BaseAction{
	@Resource
	private AreaManagementService areaManagementService;
	private AreaManagement areaManagement;
	
	private Long areaId;

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public AreaManagement getAreaManagement() {
		return areaManagement;
	}

	public void setAreaManagement(AreaManagement areaManagement) {
		this.areaManagement = areaManagement;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		Long node=Long.parseLong(this.getRequest().getParameter("node"));
		
		List<AreaManagement> list= areaManagementService.getListByParentId(node);
		JsonUtil.responseJsonString(JSONArray.fromObject(list).toString());
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String id=getRequest().getParameter("id");
		if(id!=null){
			areaManagementService.remove(new Long(id));
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		AreaManagement areaManagement=areaManagementService.get(areaId);
		AreaManagement area=areaManagementService.get(areaManagement.getParentId());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("areaManagement", areaManagement);
		map.put("parentArea", area);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(map));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(areaManagement.getId()==null){
			areaManagement.setLeaf(true);
			areaManagement.setRemarks(areaManagement.getText());
			AreaManagement area=areaManagementService.get(areaManagement.getParentId());
			area.setLeaf(false);
			areaManagementService.save(area);
			areaManagementService.save(areaManagement);
		}else{
			AreaManagement orgAreaManagement=areaManagementService.get(areaManagement.getId());
			try{
				BeanUtil.copyNotNullProperties(orgAreaManagement, areaManagement);
				orgAreaManagement.setRemarks(areaManagement.getText());
				areaManagementService.save(orgAreaManagement);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String getCount(){
		String parentId=this.getRequest().getParameter("parentId");
		List<AreaManagement> list= areaManagementService.getListByParentId(Long.valueOf(parentId));
		if(null!=list){
			JsonUtil.responseJsonString("{recordnum:"+list.size()+"}");
		}
		return SUCCESS;
	}
	public String getAreaTree(){
		String node=this.getRequest().getParameter("node");
		if(node.equals("-1")){
			node="1";
		}
		List<AreaManagement> list= areaManagementService.getListByParentId(Long.valueOf(node));
		List<Content> contentList = new ArrayList<Content>();
		if(list != null && list.size()>0){
			
			for (AreaManagement area : list) {
				Content c = new Content();
				c.setId(area.getId().intValue());
				c.setText(area.getText());
				c.setRemarks(area.getRemarks());
				c.setLeaf(area.getLeaf());
				contentList.add(c);
				/*List list_leaf = queryIsLeaf(areaDic.getId());
				if(null ==list_leaf || list_leaf.size() == 0) {
					c.setLeaf(true);
					
				} else {
					c.setLeaf(false);
					contentList.add(c);
				}*/
			}
		}
		
		JSONArray jsonObject = JSONArray.fromObject(contentList);
		jsonString = jsonObject.toString();
		return SUCCESS;
	}
}
