package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutinvest;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseOutinvestService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author 刘俊
 *
 */
@SuppressWarnings("all")
public class EnterpriseOutinvestAction extends BaseAction {

	private EnterpriseOutinvest enterpriseOutinvest ;
	@Resource
	private EnterpriseOutinvestService enterpriseOutinvestService ;
	@Resource
	private DictionaryService dictionaryService;
	private int id ;
	
	public void add(){
		try{
			enterpriseOutinvestService.save(enterpriseOutinvest);
			jsonString="{success:true}" ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void deleteRs(){
		try {
			enterpriseOutinvest=enterpriseOutinvestService.getById(id);
			if(null!=enterpriseOutinvest){
				enterpriseOutinvestService.remove(enterpriseOutinvest);
			}
			JsonUtil.jsonFromObject(null, true);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false);
		}
	}
	
	public void update(){
		try{
			enterpriseOutinvestService.merge(enterpriseOutinvest);
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void find(){
		try{
			enterpriseOutinvest=enterpriseOutinvestService.getById(id)  ;
			JsonUtil.jsonFromObject(enterpriseOutinvest, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}

	public String findForIphone(){
		String jsonString = "";
		try{
			enterpriseOutinvest=enterpriseOutinvestService.getById(id)  ;
			jsonString = JsonUtil.jsonFromObjectForIphone(enterpriseOutinvest, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return jsonString;
	}
	public void query(){
		try{
			int totalProperty = 0;
			PagingBean pb=new PagingBean(start,limit);
			List<EnterpriseOutinvest> list=enterpriseOutinvestService.getListByEnterpriseId(id, pb);
			for(EnterpriseOutinvest e:list){
				if(null!=e.getInvestway()){
					Dictionary dic=dictionaryService.get(e.getInvestway().longValue());
					if(null!=dic){
						e.setInvestwayValue(dic.getItemValue());
					}
				}
			}
			List<EnterpriseOutinvest> list1=enterpriseOutinvestService.getListByEnterpriseId(id, null);
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			JsonUtil.jsonFromList(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public EnterpriseOutinvest getEnterpriseOutinvest() {
		return enterpriseOutinvest;
	}

	public void setEnterpriseOutinvest(EnterpriseOutinvest enterpriseOutinvest) {
		this.enterpriseOutinvest = enterpriseOutinvest;
	}


	
}
