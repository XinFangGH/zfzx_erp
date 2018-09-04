package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.OfficeGoods;
import com.zhiwei.credit.model.admin.OfficeGoodsType;
import com.zhiwei.credit.service.admin.OfficeGoodsService;
import com.zhiwei.credit.service.admin.OfficeGoodsTypeService;
/**
 * 
 * @author csx
 *
 */
public class OfficeGoodsTypeAction extends BaseAction{
	@Resource
	private OfficeGoodsTypeService officeGoodsTypeService;
	private OfficeGoodsType officeGoodsType;
	@Resource
	private OfficeGoodsService officeGoodsService;
	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public OfficeGoodsType getOfficeGoodsType() {
		return officeGoodsType;
	}

	public void setOfficeGoodsType(OfficeGoodsType officeGoodsType) {
		this.officeGoodsType = officeGoodsType;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<OfficeGoodsType> list= officeGoodsTypeService.getAll(filter);
		Type type=new TypeToken<List<OfficeGoodsType>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 类别树
	 * 
	 */
	
	public String tree(){
		String method=getRequest().getParameter("method");
		List<OfficeGoodsType> list=officeGoodsTypeService.getAll();
		StringBuffer sb=new StringBuffer();
		int i=0;
		if(StringUtils.isNotEmpty(method)){
			sb.append("[");
		}else{
			i++;
			sb.append("[{id:'"+0+"',text:'办公用品分类',expanded:true,children:[");
		}
		for(OfficeGoodsType officeGoodsType:list){
			sb.append("{id:'"+officeGoodsType.getTypeId()+"',text:'"+officeGoodsType.getTypeName()+"',leaf:true},");
		}
		if(list.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		if(i==0){
			sb.append("]");
		}else{
			sb.append("]}]");
		}
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
				filter.addFilter("Q_officeGoodsType.typeId_L_EQ",id);
				List<OfficeGoods> list=officeGoodsService.getAll(filter);
				if(list.size()>0){
					jsonString="{success:false,message:'该类型下还有用品，请转移后再删除！'}";
					return SUCCESS;
				}
				officeGoodsTypeService.remove(new Long(id));
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
		OfficeGoodsType officeGoodsType=officeGoodsTypeService.get(typeId);
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(officeGoodsType));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		officeGoodsTypeService.save(officeGoodsType);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
