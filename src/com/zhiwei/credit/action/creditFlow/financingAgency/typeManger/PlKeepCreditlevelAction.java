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


import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepCreditlevelService;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlKeepCreditlevelAction extends BaseAction{
	@Resource
	private PlKeepCreditlevelService plKeepCreditlevelService;
	private PlKeepCreditlevel plKeepCreditlevel;
	
	private Long creditLevelId;

	public Long getCreditLevelId() {
		return creditLevelId;
	}

	public void setCreditLevelId(Long creditLevelId) {
		this.creditLevelId = creditLevelId;
	}

	public PlKeepCreditlevel getPlKeepCreditlevel() {
		return plKeepCreditlevel;
	}

	public void setPlKeepCreditlevel(PlKeepCreditlevel plKeepCreditlevel) {
		this.plKeepCreditlevel = plKeepCreditlevel;
	}
	/**
	 * 字典项
	 */
	   public String loadItem(){
		   
		   QueryFilter filter=new QueryFilter(getRequest());
		   List<PlKeepCreditlevel> list= plKeepCreditlevelService.getAll(filter);
			   StringBuffer buff = new StringBuffer("[");
				for (PlKeepCreditlevel dic : list) {
					buff.append("[").append(dic.getCreditLevelId()).append(",'")
							.append(dic.getName()).append("'],");

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
		List<PlKeepCreditlevel> list= plKeepCreditlevelService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
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
				plKeepCreditlevelService.remove(new Long(id));
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
		PlKeepCreditlevel plKeepCreditlevel=plKeepCreditlevelService.get(creditLevelId);
		
		//将数据转成JSON格式
		StringBuffer buff = new StringBuffer("{success:true,data:");
		
JSONSerializer serializer = JsonUtil.getJSONSerializer();
buff.append(serializer.exclude(new String[] { "class" })
		.serialize(plKeepCreditlevel));
buff.append("}");

jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plKeepCreditlevel.getCreditLevelId()==null){
			plKeepCreditlevelService.save(plKeepCreditlevel);
		}else{
			PlKeepCreditlevel orgPlKeepCreditlevel=plKeepCreditlevelService.get(plKeepCreditlevel.getCreditLevelId());
			try{
				BeanUtil.copyNotNullProperties(orgPlKeepCreditlevel, plKeepCreditlevel);
				plKeepCreditlevelService.save(orgPlKeepCreditlevel);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
