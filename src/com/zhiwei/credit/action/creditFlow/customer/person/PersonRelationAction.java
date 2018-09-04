package com.zhiwei.credit.action.creditFlow.customer.person;


import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonRelationService;
import com.zhiwei.credit.service.system.DictionaryService;

public class PersonRelationAction extends BaseAction {
	@Resource
	private PersonRelationService personRelationService;
	@Resource
	private DictionaryService dictionaryService;
	private PersonRelation personRelation;
	private int id;
	private int personId;
	public void ajaxQuery(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<PersonRelation> list=personRelationService.getListByPersonId(personId, pb);
			for(PersonRelation p:list){
				if(null!=p.getRelationShip()){
					Dictionary dic=dictionaryService.get(p.getRelationShip().longValue());
					if(null!=dic){
						p.setRelationShipValue(dic.getItemValue());
					}
				}
			}
			List<PersonRelation> list1=personRelationService.getListByPersonId(personId, null);
			int count=0;
			if(null!=list1 && list1.size()>0){
				count=list1.size();
			}
			JsonUtil.jsonFromList(list, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String add(){
		try {
			personRelationService.save(personRelation);
			jsonString="{\"success\":true}";
		} catch (Exception e) {
			e.printStackTrace();
			jsonString="{\"success\":false}";
		}
		return SUCCESS;
	}

	//jiang	--
	public void see(){
		try {
			personRelation=personRelationService.getById(id);
			JsonUtil.jsonFromObject(personRelation, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String update(){
		try {
			personRelationService.merge(personRelation);
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			jsonString="{success:false}";
		}
		return SUCCESS;
		
	}
	
	public void deleteRs(){
		try {
			personRelation=personRelationService.getById(id);
			personRelationService.remove(personRelation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public PersonRelation getPersonRelation() {
		return personRelation;
	}
	public void setPersonRelation(PersonRelation personRelation) {
		this.personRelation = personRelation;
	}
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
}
