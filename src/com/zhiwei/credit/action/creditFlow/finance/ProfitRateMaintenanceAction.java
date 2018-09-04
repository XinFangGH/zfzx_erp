package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.finance.ProfitRateMaintenance;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.finance.ProfitRateMaintenanceService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class ProfitRateMaintenanceAction extends BaseAction{
	@Resource
	private ProfitRateMaintenanceService profitRateMaintenanceService;
	@Resource
	private AppUserService appUserService;
	private ProfitRateMaintenance profitRateMaintenance;
	
	private Long rateId;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public ProfitRateMaintenance getProfitRateMaintenance() {
		return profitRateMaintenance;
	}

	public void setProfitRateMaintenance(ProfitRateMaintenance profitRateMaintenance) {
		this.profitRateMaintenance = profitRateMaintenance;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ProfitRateMaintenance> list= profitRateMaintenanceService.getAll(filter);
		
		Type type=new TypeToken<List<ProfitRateMaintenance>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	@LogResource(description = "删除基准利率")
	public String multiDel(){
		
		
		profitRateMaintenanceService.remove(rateId);		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ProfitRateMaintenance profitRateMaintenance=profitRateMaintenanceService.get(rateId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(profitRateMaintenance));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	@LogResource(description = "添加或修改基准利率")
	public String save(){
		String profitRateMaintenanceStr=this.getRequest().getParameter("profitRateMaintenanceStr");
		if(null != profitRateMaintenanceStr && !"".equals(profitRateMaintenanceStr)) {

			String[] profitRateMaintenanceArr = profitRateMaintenanceStr.split("@");
			
			for(int i=0; i<profitRateMaintenanceArr.length; i++) {
				String str = profitRateMaintenanceArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try{
					ProfitRateMaintenance profitRateMaintenance = (ProfitRateMaintenance)JSONMapper.toJava(parser.nextValue(),ProfitRateMaintenance.class);
					if(null==profitRateMaintenance.getRateId()){
						profitRateMaintenance.setCreateTime(new Date());
						AppUser user=ContextUtil.getCurrentUser();
						profitRateMaintenance.setCreator(user.getFullname());
						profitRateMaintenance.setUpdateTime(new Date());
						profitRateMaintenance.setUpdator(user.getFullname());
						profitRateMaintenanceService.save(profitRateMaintenance);
					}else{
						ProfitRateMaintenance orgProfitRateMaintenance=profitRateMaintenanceService.get(profitRateMaintenance.getRateId());
						BeanUtil.copyNotNullProperties(orgProfitRateMaintenance, profitRateMaintenance);
						orgProfitRateMaintenance.setUpdateTime(new Date());
						AppUser user=ContextUtil.getCurrentUser();
						orgProfitRateMaintenance.setUpdator(user.getFullname());
						profitRateMaintenanceService.save(orgProfitRateMaintenance);
					}
					setJsonString("{success:true}");
				
				} catch(Exception e) {
					e.printStackTrace();
					setJsonString("{success:false}");
					logger.error("ShareequityAction:"+e.getMessage());
				}
				
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
