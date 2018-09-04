package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseRelationPerson;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseRelationPersonService;

public class EnterpriseRelationPersonAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private EnterpriseRelationPersonService enterpriseRelationPersonService ;
	private EnterpriseRelationPerson relationPerson ;
	private int eid ;
	private int id ;
	public EnterpriseRelationPerson getRelationPerson() {
		return relationPerson;
	}
	public void setRelationPerson(EnterpriseRelationPerson relationPerson) {
		this.relationPerson = relationPerson;
	}
	public int getEid() {
		return eid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	

	//kaishi 
	public void addRelationPerson(){
		try {
			enterpriseRelationPersonService.add(relationPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	public void updateRelationPerson(){
		try {
			enterpriseRelationPersonService.update(relationPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	public void seeRelationPerson(){
		try {
			relationPerson=enterpriseRelationPersonService.getById(id);
			JsonUtil.jsonFromObject(relationPerson, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	public void queryListRelationPerson(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<EnterpriseRelationPerson> list=enterpriseRelationPersonService.getList(eid, pb);
			List<EnterpriseRelationPerson> list1=enterpriseRelationPersonService.getList(eid, null);
			//if(null!=list && list.size()>0){
				JsonUtil.jsonFromList(list, list1.size());
			/*}else{
				JsonUtil.jsonFromList(null, 0);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	public void deleteRelationPerson(){
		try {
			EnterpriseRelationPerson person=enterpriseRelationPersonService.getById(id);
			if(null!=person){
				enterpriseRelationPersonService.remove(person);
			}
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
}
