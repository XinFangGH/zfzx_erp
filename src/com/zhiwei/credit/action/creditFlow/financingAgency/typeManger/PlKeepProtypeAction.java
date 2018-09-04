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
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepProtypeService;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlKeepProtypeAction extends BaseAction{
	@Resource
	private PlKeepProtypeService plKeepProtypeService;
	private PlKeepProtype plKeepProtype;
	
	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public PlKeepProtype getPlKeepProtype() {
		return plKeepProtype;
	}

	public void setPlKeepProtype(PlKeepProtype plKeepProtype) {
		this.plKeepProtype = plKeepProtype;
	}
	/**
	 * 字典项
	 */
	public String loadItem(){
		   QueryFilter filter=new QueryFilter(getRequest());
		   List<PlKeepProtype> list= plKeepProtypeService.getAll(filter);
			   StringBuffer buff = new StringBuffer("[");
				for (PlKeepProtype dic : list) {
					if(dic.getIsDelete()!=null&&dic.getIsDelete().equals(Short.valueOf("1"))){
						
					}else{
						buff.append("[").append(dic.getTypeId()).append(",'")
						.append(dic.getName()).append("'],");
					}
				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
		   
		   return SUCCESS;
	   }

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		//filter.addFilter("Q_isDelete_SN_NEQ", "1");
		//filter.addFilter("Q_isDelete_SN_NULL","");
		//List<PlKeepProtype> list= plKeepProtypeService.getAll(filter);
		PagingBean pb = filter.getPagingBean();
		List<PlKeepProtype> list = plKeepProtypeService.getList((short)0,pb);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(
				",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");
		
		jsonString = buff.toString();
		
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
				//plKeepProtypeService.remove(new Long(id));
				plKeepProtypeService.deleteProtype(new Long(id));
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
		PlKeepProtype plKeepProtype=plKeepProtypeService.get(typeId);
		
		//将数据转成JSON格式
		StringBuffer buff = new StringBuffer("{success:true,data:");
		
JSONSerializer serializer = JsonUtil.getJSONSerializer();
buff.append(serializer.exclude(new String[] { "class" })
		.serialize(plKeepProtype));
buff.append("}");

jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plKeepProtype.getTypeId()==null){
			plKeepProtypeService.save(plKeepProtype);
		}else{
			PlKeepProtype orgPlKeepProtype=plKeepProtypeService.get(plKeepProtype.getTypeId());
			try{
				BeanUtil.copyNotNullProperties(orgPlKeepProtype, plKeepProtype);
				plKeepProtypeService.save(orgPlKeepProtype);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
