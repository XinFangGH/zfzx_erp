package com.zhiwei.credit.action.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObShopintentStatistic;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObShopintentStatisticService;
/**
 * 
 * @author 
 *
 */
public class ObShopintentStatisticAction extends BaseAction{
	@Resource
	private ObShopintentStatisticService obShopintentStatisticService;
	private ObShopintentStatistic obShopintentStatistic;
	
	private Long shopIntentId;

	public Long getShopIntentId() {
		return shopIntentId;
	}

	public void setShopIntentId(Long shopIntentId) {
		this.shopIntentId = shopIntentId;
	}

	public ObShopintentStatistic getObShopintentStatistic() {
		return obShopintentStatistic;
	}

	public void setObShopintentStatistic(ObShopintentStatistic obShopintentStatistic) {
		this.obShopintentStatistic = obShopintentStatistic;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ObShopintentStatistic> list= obShopintentStatisticService.getAll(filter);
		
		Type type=new TypeToken<List<ObShopintentStatistic>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				obShopintentStatisticService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ObShopintentStatistic obShopintentStatistic=obShopintentStatisticService.get(shopIntentId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(obShopintentStatistic));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(obShopintentStatistic.getShopIntentId()==null){
			obShopintentStatisticService.save(obShopintentStatistic);
		}else{
			ObShopintentStatistic orgObShopintentStatistic=obShopintentStatisticService.get(obShopintentStatistic.getShopIntentId());
			try{
				BeanUtil.copyNotNullProperties(orgObShopintentStatistic, obShopintentStatistic);
				obShopintentStatisticService.save(orgObShopintentStatistic);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
public String listshopIntentStatistic(){
		
		int count =0;
		String shopName=this.getRequest().getParameter("shopName");
		String startDate=this.getRequest().getParameter("startDate");
		String endDate =this.getRequest().getParameter("endDate");
		List<ObShopintentStatistic> list= obShopintentStatisticService.listShopIntentStatistic(shopName,startDate,endDate,start,limit);
		if(list!=null&&list.size()>0){
			for(ObShopintentStatistic temp:list){
				if(temp.getShopId()!=null){
					BigDecimal totalMoney =new BigDecimal(0);
					List<ObShopintentStatistic> listemp =obShopintentStatisticService.getGroupByShopId(temp.getShopId());
					if(listemp!=null&&listemp.size()>0){
						for(ObShopintentStatistic tempg:listemp){
							totalMoney=totalMoney.add(tempg.getShopIntent());
						}
					}
					temp.setCompanyTotalIntent(totalMoney);
				}
				
			}
		}
		if(list!=null){
			count=list.size();
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(count).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
}
