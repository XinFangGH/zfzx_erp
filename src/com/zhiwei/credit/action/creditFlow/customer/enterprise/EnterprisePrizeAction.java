package com.zhiwei.credit.action.creditFlow.customer.enterprise;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterprisePrize;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterprisePrizeService;

public class EnterprisePrizeAction extends BaseAction {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private EnterprisePrize enPrize ;
	@Resource
	private EnterprisePrizeService enterprisePrizeServive;
	private int eid; //企业id
	private int id;  //获奖人id
	
	
	public EnterprisePrize getModel() {
		return enPrize;
	}
	public EnterprisePrize getEnPrize() {
		return enPrize;
	}
	public void setEnPrize(EnterprisePrize enPrize) {
		this.enPrize = enPrize;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public void add(){
		try {
			enterprisePrizeServive.save(enPrize);
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void query(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<EnterprisePrize> list=enterprisePrizeServive.getListByEnterpriseId(eid, pb);
			List<EnterprisePrize> list1=enterprisePrizeServive.getListByEnterpriseId(eid, null);
			int count=0;
			if(null!=list1 && list1.size()>0){
				count=list1.size();
			}
			JsonUtil.jsonFromList(list, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void get(){
		try {
			enPrize=enterprisePrizeServive.getById(id);
			JsonUtil.jsonFromObject(enPrize, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		try {
			enterprisePrizeServive.merge(enPrize);
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteRs(){
		try {
			enPrize=enterprisePrizeServive.getById(id);
			if(null!=enPrize){
				enterprisePrizeServive.remove(enPrize);
			}
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
