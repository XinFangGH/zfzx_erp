package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseCreditor;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseCreditorService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * @author tianhuiguo
 * @time   2010-3-31
 * @description 企业债务情况
 * @class EnterCreditorAction
 *
 * */
public class EnterpriseCreditorAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//获得企业所对应的企业债务信息
	private EnterpriseCreditor enCreditor;
	@Resource
	private EnterpriseCreditorService enterpriseCreditorService;
	@Resource
	private DictionaryService dictionaryService;
	private int eid; 
	private int id ;
	public EnterpriseCreditor getEnCreditor() {
		return enCreditor;
	}
	public void setEnCreditor(EnterpriseCreditor enCreditor) {
		this.enCreditor = enCreditor;
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
		try{
			enterpriseCreditorService.save(enCreditor);
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void getAll(){
		try{
			PagingBean pb=new PagingBean(start,limit);
			List<EnterpriseCreditor> list=enterpriseCreditorService.getListByEnterpriseId(eid, pb);
			for(EnterpriseCreditor e :list){
				if(null!=e.getRepayway() && !e.getRepayway().equals("")){
					Dictionary dic=dictionaryService.get(Long.valueOf(e.getRepayway()));
					if(null!=dic){
						e.setRepaywayValue(dic.getItemValue());
					}
				}
				if(null!=e.getVoucherway() && !e.getVoucherway().equals("")){
					Dictionary dic=dictionaryService.get(Long.valueOf(e.getVoucherway()));
					if(null!=dic){
						e.setVoucherwayValue(dic.getItemValue());
					}
				}
			}
			List<EnterpriseCreditor> list1=enterpriseCreditorService.getListByEnterpriseId(eid, null);
			int count=0;
			if(null!=list1 && list1.size()>0){
				count=list1.size();
			}
			JsonUtil.jsonFromList(list, count);
		}catch(Exception ie){
			ie.printStackTrace();
		}

	}
	

	public void deleteRs(){
		try {
			EnterpriseCreditor e=enterpriseCreditorService.getById(id);
			if(null!=e){
				enterpriseCreditorService.remove(e);
			}
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		try{
			enterpriseCreditorService.merge(enCreditor);
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void findById(){
		try{
			EnterpriseCreditor e=enterpriseCreditorService.getById(id);
			JsonUtil.jsonFromObject(e, true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
