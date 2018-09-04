package com.zhiwei.credit.action.creditFlow.customer.person;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonCarService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class CsPersonCarAction extends BaseAction{
	@Resource
	private CsPersonCarService csPersonCarService;
	private CsPersonCar csPersonCar;
	@Resource
	private PersonService personService;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsPersonCar getCsPersonCar() {
		return csPersonCar;
	}

	public void setCsPersonCar(CsPersonCar csPersonCar) {
		this.csPersonCar = csPersonCar;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String personId =this.getRequest().getParameter("personId");
		if(personId!=null&&!"".equals(personId)&& !"0".equals(personId) && !"null".equals(personId)){
			Person person = personService.getById(Integer.valueOf(personId));
			List<CsPersonCar> list = new ArrayList<CsPersonCar>(person.getPersonCars());
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("purchaseTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"purchaseTime"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			
//			List<CsPersonCar> list= csPersonCarService.getByPersonId(PersonId);
//			
//			Type type=new TypeToken<List<CsPersonCar>>(){}.getType();
//			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//			.append(list!=null?list.size():0).append(",result:");
//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
//					.create();
//			if(null!=list){
//				buff.append(gson.toJson(list));
//			}
//			buff.append("}");
//			jsonString = buff.toString();
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
				csPersonCarService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String delete(){
		String personCarId=this.getRequest().getParameter("personCarId");
		if(personCarId!=null&&!"".equals(personCarId)){
			csPersonCarService.remove(new Long(personCarId));
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		CsPersonCar csPersonCar=csPersonCarService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		JSONSerializer json = new JSONSerializer();
		
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.serialize(csPersonCar));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csPersonCar.getId()==null){
			csPersonCarService.save(csPersonCar);
		}else{
			CsPersonCar orgCsPersonCar=csPersonCarService.get(csPersonCar.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsPersonCar, csPersonCar);
				csPersonCarService.save(orgCsPersonCar);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
