package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseDebt;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseDebtService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author 刘俊
 *
 */
@SuppressWarnings("all")
public class EnterpriseDebtAction extends BaseAction {
	
	private EnterpriseDebt enterpriseDebt ;
	@Resource
	private EnterpriseDebtService enterpriseDebtService ;
	@Resource
	private DictionaryService dictionaryService;
	private int id ;
	
	public void add(){
		try{
			enterpriseDebtService.save(enterpriseDebt) ;
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void deleteRs(){
		try {
			EnterpriseDebt e=enterpriseDebtService.getById(id);
			if(null!=e){
				enterpriseDebtService.remove(e);
			}
			JsonUtil.jsonFromObject(null, true);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false);
		}
	}
	
	public void update(){
		try{
			enterpriseDebtService.merge(enterpriseDebt) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void find(){
		try{
			EnterpriseDebt e=enterpriseDebtService.getById(id);
			JsonUtil.jsonFromObject(e, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public String findForIphone(){
		String jsonString = "";
		try{
			EnterpriseDebt e=enterpriseDebtService.getById(id);
			jsonString = JsonUtil.jsonFromObjectForIphone(e, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return jsonString ;
	}
	
	public void query(){
		try{
			int totalProperty = 0;
			PagingBean pb=new PagingBean(start,limit);
			List<EnterpriseDebt> list=enterpriseDebtService.getListByEnterpriseId(id, pb);
			for(EnterpriseDebt e :list){
				if(null!=e.getRepaymentway()){
					Dictionary dic=dictionaryService.get(e.getRepaymentway().longValue());
					if(null!=dic){
						e.setRepaymentwayValue(dic.getItemValue());
					}
				}
				if(null!=e.getVoucherway()){
					Dictionary dic=dictionaryService.get(Long.valueOf(e.getVoucherway()));
					if(null!=dic){
						e.setVoucherwayValue(dic.getItemValue());
					}
				}
			}
			List<EnterpriseDebt> list1=enterpriseDebtService.getListByEnterpriseId(id, null);
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			JsonUtil.jsonFromList(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public String queryForIphone(){
		String jsonString="";
		try{
			int totalProperty = 0;
			PagingBean pb=new PagingBean(start,limit);
			List<EnterpriseDebt> list=enterpriseDebtService.getListByEnterpriseId(id, pb);
			List<EnterpriseDebt> list1=enterpriseDebtService.getListByEnterpriseId(id, null);
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			jsonString = JsonUtil.jsonFromListForIphone(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return jsonString ;
	}
	
	public EnterpriseDebt getEnterpriseDebt() {
		return enterpriseDebt;
	}

	public void setEnterpriseDebt(EnterpriseDebt enterpriseDebt) {
		this.enterpriseDebt = enterpriseDebt;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
