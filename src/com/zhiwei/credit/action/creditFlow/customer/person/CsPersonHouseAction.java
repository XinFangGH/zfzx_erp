package com.zhiwei.credit.action.creditFlow.customer.person;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.proxy.HibernateProxyHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonHouseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class CsPersonHouseAction extends BaseAction{
	@Resource
	private CsPersonHouseService csPersonHouseService;
	private CsPersonHouse csPersonHouse;
	@Resource
	private PersonService personService;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsPersonHouse getCsPersonHouse() {
		return csPersonHouse;
	}

	public void setCsPersonHouse(CsPersonHouse csPersonHouse) {
		this.csPersonHouse = csPersonHouse;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String personId =this.getRequest().getParameter("personId");
		if(personId!=null&&!"".equals(personId)&& !"0".equals(personId) && !"null".equals(personId)){
			Person person = personService.getById(Integer.valueOf(personId));
			List<CsPersonHouse> list = new ArrayList<CsPersonHouse>(person.getPersonHouses());
//			List<CsPersonHouse> list= csPersonHouseService.getByPersonId(PersonId);
			
//			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//			.append(list!=null?list.size():0).append(",result:");
//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
//					.create();
//			if(null!=list){
//				buff.append(gson.toJson(list));
//			}
//			buff.append("}");
//			jsonString = buff.toString();
			
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("purchaseTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"purchaseTime"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
		}else{
			jsonString="{success:true}";
		}
		
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
				csPersonHouseService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String delete(){
		String personHouseId=this.getRequest().getParameter("personHouseId");
		if(personHouseId!=null&&!"".equals(personHouseId)){
			csPersonHouseService.remove(new Long(personHouseId));
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		CsPersonHouse csPersonHouse=csPersonHouseService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		
		JSONSerializer json = new JSONSerializer(); 
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.serialize(csPersonHouse));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csPersonHouse.getId()==null){
			csPersonHouseService.save(csPersonHouse);
		}else{
			CsPersonHouse orgCsPersonHouse=csPersonHouseService.get(csPersonHouse.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsPersonHouse, csPersonHouse);
				csPersonHouseService.save(orgCsPersonHouse);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
