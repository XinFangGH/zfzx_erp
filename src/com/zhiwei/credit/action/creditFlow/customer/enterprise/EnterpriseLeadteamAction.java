package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseLeadteam;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseLeadteamService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 刘俊
 *
 */
@SuppressWarnings("all")
public class EnterpriseLeadteamAction extends BaseAction {

	private EnterpriseLeadteam enterpriseLeadteam ;
	@Resource
	private EnterpriseLeadteamService enterpriseLeadteamService ;
	@Resource
	private DictionaryService dictionaryService;
	private int id ;
	
	public void add(){
		try{
			enterpriseLeadteamService.save(enterpriseLeadteam) ;
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void deleteRs(){
		try {
			EnterpriseLeadteam e=enterpriseLeadteamService.getById(id);
			if(null!=e){
				enterpriseLeadteamService.remove(e);
			}
			JsonUtil.jsonFromObject(null, true);
		} catch (Exception e) {
			e.printStackTrace();
				JsonUtil.jsonFromObject(null, false);
		}
	}
	
	public void update(){
		try{
			enterpriseLeadteamService.merge(enterpriseLeadteam) ;
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void find(){
		try{
			EnterpriseLeadteam e = enterpriseLeadteamService.getById(id); 
			JsonUtil.jsonFromObject(e, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public String findForIphone(){
		String jsonString = "";
		try{
			EnterpriseLeadteam e = enterpriseLeadteamService.getById(id);
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
			List<EnterpriseLeadteam> list = enterpriseLeadteamService.getListByEnterpriseId(id, pb) ;
			for(EnterpriseLeadteam e :list){
				if(null!=e.getCardtype()){
					Dictionary dic=dictionaryService.get(e.getCardtype().longValue());
					if(null!=dic){
						e.setCardtypeValue(dic.getItemValue());
					}
				}
				if(null!=e.getTechtitle() && !e.getTechtitle().equals("")){
					Dictionary dic=dictionaryService.get(Long.valueOf(e.getTechtitle()));
					if(null!=dic){
						e.setTechtitleValue(dic.getItemValue());
					}
				}
			}
			List<EnterpriseLeadteam> list1 = enterpriseLeadteamService.getListByEnterpriseId(id, null) ;
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
		
			JsonUtil.jsonFromList(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	

	public EnterpriseLeadteam getEnterpriseLeadteam() {
		return enterpriseLeadteam;
	}

	public void setEnterpriseLeadteam(EnterpriseLeadteam enterpriseLeadteam) {
		this.enterpriseLeadteam = enterpriseLeadteam;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}
