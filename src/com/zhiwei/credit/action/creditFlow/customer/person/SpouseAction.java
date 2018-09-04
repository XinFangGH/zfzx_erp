package com.zhiwei.credit.action.creditFlow.customer.person;

import javax.annotation.Resource;

import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
@SuppressWarnings("all")
public class SpouseAction extends BaseAction{
	@Resource
	private SpouseService spouseService;
	private Spouse spouse;
	public void add(){
		try {
			if(null==spouse.getSpouseId()){
				spouseService.save(spouse);
			}else{
				Spouse s=spouseService.get(spouse.getSpouseId());
				BeanUtil.copyNotNullProperties(s, spouse);
				spouseService.merge(s);
			}
			JsonUtil.responseJsonSuccess();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public void getInfo(){
		String personId=this.getRequest().getParameter("personId");
		spouse=spouseService.getByPersonId(Integer.valueOf(personId));
		
		JsonUtil.jsonFromObject(spouse, true);
		
	}
	public Spouse getSpouse() {
		return spouse;
	}
	public void setSpouse(Spouse spouse) {
		this.spouse = spouse;
	}
	public SpouseService getSpouseService() {
		return spouseService;
	}
	public void setSpouseService(SpouseService spouseService) {
		this.spouseService = spouseService;
	}
	
}
