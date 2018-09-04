package com.zhiwei.credit.action.creditFlow.customer.person;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonCreditLoanHistory;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonCreditLoanHistoryService;
import com.zhiwei.credit.service.system.DictionaryService;

public class PersonCreditLoanHistoryAction extends BaseAction {
	@Resource
	private PersonCreditLoanHistoryService personCreditLoanHistoryService;
	@Resource
	private DictionaryService dictionaryService;
	private PersonCreditLoanHistory personCreditLoanHistory;
	private Long id;
	private String ids;
	private int personId;
	public void ajaxQuery(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<PersonCreditLoanHistory> list=personCreditLoanHistoryService.getListByPersonId(personId, pb);
			List<PersonCreditLoanHistory> list1=personCreditLoanHistoryService.getListByPersonId(personId, null);
			int count=0;
			if(null!=list1 && list1.size()>0){
				count=list1.size();
			}
			JsonUtil.jsonFromList(list, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void add(){
		try {
			personCreditLoanHistoryService.save(personCreditLoanHistory);
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String see(){
		try {
			personCreditLoanHistory=personCreditLoanHistoryService.get(id);
			Map map = new HashMap();
			map.put("personCreditLoanHistory", personCreditLoanHistory);
			StringBuffer buff = new StringBuffer("{success:true,data:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(map));
			buff.append("}");
			setJsonString(buff.toString());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		}
	}
	
	public void update(){
		try {
			personCreditLoanHistoryService.merge(personCreditLoanHistory);
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRs(){
		try {
			if(!StringUtils.isEmpty(ids)){
				String[] idArr = ids.split(",");
				for(String singId:idArr){
					personCreditLoanHistoryService.remove(Long.valueOf(singId.trim()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonCreditLoanHistory getPersonCreditLoanHistory() {
		return personCreditLoanHistory;
	}
	public void setPersonCreditLoanHistory(PersonCreditLoanHistory personCreditLoanHistory) {
		this.personCreditLoanHistory = personCreditLoanHistory;
	}
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
