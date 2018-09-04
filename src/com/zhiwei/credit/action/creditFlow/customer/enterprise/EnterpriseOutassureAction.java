package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutassure;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseOutassureService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author 刘俊
 *
 */
@SuppressWarnings("all")
public class EnterpriseOutassureAction extends BaseAction {

	private EnterpriseOutassure enterpriseOutassure ;
	@Resource
	private EnterpriseOutassureService enterpriseOutassureService ;
	@Resource
	private DictionaryService dictionaryService;
	private int id ;
	
	public void add(){
		try{
			enterpriseOutassureService.save(enterpriseOutassure);
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void deleteRs(){
		try {
			enterpriseOutassure=enterpriseOutassureService.getById(id);
			if(null!=enterpriseOutassure){
				enterpriseOutassureService.remove(enterpriseOutassure);
			}
			JsonUtil.jsonFromObject(null, true);
		} catch (Exception e) {
			e.printStackTrace();
				JsonUtil.jsonFromObject(null, false);
		}
	}
	
	public void update(){
		try{
			enterpriseOutassureService.merge(enterpriseOutassure) ;
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void find(){
		try{
			EnterpriseOutassure e = enterpriseOutassureService.getById(id) ;
			JsonUtil.jsonFromObject(e, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}

	public String findForIphone(){
		String jsonString = "";
		try{
			EnterpriseOutassure e = enterpriseOutassureService.getById(id) ;
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
			List<EnterpriseOutassure> list=enterpriseOutassureService.getListByEnterpriseId(id, pb);
			for(EnterpriseOutassure e:list){
				if(null!=e.getAssureway() && !e.getAssureway().equals("")){
					Dictionary dic=dictionaryService.get(Long.valueOf(e.getAssureway()));
					if(null!=dic){
						e.setAssurewayValue(dic.getItemValue());
					}
				}
			}
			List<EnterpriseOutassure> list1=enterpriseOutassureService.getListByEnterpriseId(id, null);
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			JsonUtil.jsonFromList(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public String queryForIphone(){
		String jsonString = "";
		try{
			int totalProperty = 0;
			PagingBean pb=new PagingBean(start,limit);
			List<EnterpriseOutassure> list=enterpriseOutassureService.getListByEnterpriseId(id, pb);
			List<EnterpriseOutassure> list1=enterpriseOutassureService.getListByEnterpriseId(id, null);
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			jsonString = JsonUtil.jsonFromListForIphone(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return jsonString ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public EnterpriseOutassure getEnterpriseOutassure() {
		return enterpriseOutassure;
	}

	public void setEnterpriseOutassure(EnterpriseOutassure enterpriseOutassure) {
		this.enterpriseOutassure = enterpriseOutassure;
	}


	
}
