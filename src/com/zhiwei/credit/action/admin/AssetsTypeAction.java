package com.zhiwei.credit.action.admin;
/*
 *   OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.AssetsType;
import com.zhiwei.credit.model.admin.FixedAssets;
import com.zhiwei.credit.service.admin.AssetsTypeService;
import com.zhiwei.credit.service.admin.FixedAssetsService;
/**
 * 
 * @author csx
 *
 */
public class AssetsTypeAction extends BaseAction{
	@Resource
	private AssetsTypeService assetsTypeService;
	private AssetsType assetsType;
	@Resource
	private FixedAssetsService fixedAssetsService;
	
	private Long assetsTypeId;

	public Long getAssetsTypeId() {
		return assetsTypeId;
	}

	public void setAssetsTypeId(Long assetsTypeId) {
		this.assetsTypeId = assetsTypeId;
	}

	public AssetsType getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(AssetsType assetsType) {
		this.assetsType = assetsType;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<AssetsType> list= assetsTypeService.getAll(filter);
		
		Type type=new TypeToken<List<AssetsType>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	
	/**
	 * 树
	 * 
	 */
	
	public String tree(){
		List<AssetsType> list= assetsTypeService.getAll();
		StringBuffer sb=new StringBuffer("[{id:'"+0+"',text:'资产类型',expanded:true,children:[");
		for(AssetsType type:list){
			sb.append("{id:'"+type.getAssetsTypeId()+"',text:'"+type.getTypeName()+"',leaf:true},");
		}
		if(list.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]}]");
		setJsonString(sb.toString());
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
				QueryFilter filter=new QueryFilter(getRequest());
				filter.addFilter("Q_assetsType.assetsTypeId_L_EQ",id);
				List<FixedAssets> list=fixedAssetsService.getAll(filter);
				if(list.size()>0){
					jsonString="{success:false,message:'该类型下还有资产，请将资产移走后再进行删除！'}";
					return SUCCESS;
				}
				assetsTypeService.remove(new Long(id));
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
		AssetsType assetsType=assetsTypeService.get(assetsTypeId);
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(assetsType));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		assetsTypeService.save(assetsType);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 获取类型下拉框
	 */
	public String combox(){
		List<AssetsType> list= assetsTypeService.getAll();
		StringBuffer buff=new StringBuffer("[");
		for(AssetsType assetsType:list){
			buff.append("['"+assetsType.getAssetsTypeId()+"','"+assetsType.getTypeName()+"'],");
		}
		if(list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
}
