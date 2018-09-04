package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.system.CsDicAreaDynam;
import com.zhiwei.credit.service.system.CsDicAreaDynamService;
/**
 * 
 * @author 
 *
 */
public class CsDicAreaDynamAction extends BaseAction{
	@Resource
	private CsDicAreaDynamService csDicAreaDynamService;
	private CsDicAreaDynam csDicAreaDynam;
	private String node;
	
	private Long id;
	
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public CsDicAreaDynam getCsDicAreaDynam() {
		return csDicAreaDynam;
	}

	public void setCsDicAreaDynam(CsDicAreaDynam csDicAreaDynam) {
		this.csDicAreaDynam = csDicAreaDynam;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CsDicAreaDynam> list= csDicAreaDynamService.getAll(filter);
		
		Type type=new TypeToken<List<CsDicAreaDynam>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	
	/*
	 * 根据ID查找其一级子节点
	 */
	public String getAllItemById() {
		List<CsDicAreaDynam> list = csDicAreaDynamService.getAllItemById(id);
		StringBuffer buff = new StringBuffer("[");
		for (CsDicAreaDynam dic : list) {
			buff.append("[").append(dic.getId()).append(",'")
					.append(dic.getText()).append("|").append(dic.getParentId()).append("'],");

		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/*
	 * 根据父ID为xx的所有节点
	 */
	public String getByParentId(){
		List<CsDicAreaDynam> list = csDicAreaDynamService.getAllItemByParentId(parentId);
		StringBuffer buff = new StringBuffer("[");
		for (CsDicAreaDynam dic : list) {
			buff.append("[").append(dic.getId()).append(",'")
					.append(dic.getText()).append("'],");

		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String getByParentId1(){
		List<CsDicAreaDynam> list = csDicAreaDynamService.getAllItemByParentId(parentId);
		JsonUtil.jsonFromList(list) ;
		return SUCCESS;
	}
	public String getAllParentBanksTree(){
		String s=csDicAreaDynamService.getAllParentBanksTree(node);
		JsonUtil.responseJsonString(s);
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
				csDicAreaDynamService.remove(new Long(id));
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
		CsDicAreaDynam csDicAreaDynam=csDicAreaDynamService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csDicAreaDynam));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csDicAreaDynam.getId()==null){
			csDicAreaDynamService.save(csDicAreaDynam);
		}else{
			CsDicAreaDynam orgCsDicAreaDynam=csDicAreaDynamService.get(csDicAreaDynam.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsDicAreaDynam, csDicAreaDynam);
				csDicAreaDynamService.save(orgCsDicAreaDynam);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
