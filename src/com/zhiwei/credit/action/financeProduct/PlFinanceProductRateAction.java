package com.zhiwei.credit.action.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductRateService;
/**
 * 
 * @author 
 *
 */
public class PlFinanceProductRateAction extends BaseAction{
	@Resource
	private PlFinanceProductRateService plFinanceProductRateService;
	private PlFinanceProductRate plFinanceProductRate;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlFinanceProductRate getPlFinanceProductRate() {
		return plFinanceProductRate;
	}

	public void setPlFinanceProductRate(PlFinanceProductRate plFinanceProductRate) {
		this.plFinanceProductRate = plFinanceProductRate;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		PagingBean pb=new PagingBean(start,limit);
		List<PlFinanceProductRate> list= plFinanceProductRateService.getAllRateAndOrder(getRequest(), pb);
		Type type=new TypeToken<List<PlFinanceProductRate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	//理财专户产品费率
	public void exportAllProductRate(){
		try {
			PagingBean pb = null;
			List<PlFinanceProductRate> list= plFinanceProductRateService.getAllRateAndOrder(this.getRequest(),pb);
			String [] tableHeader = {"序号","产品名称","执行日期","年化利率","七日平均年化利率","设置日期","修改日期","修改人"};
			ExcelHelper.exportAllProductRate(list,tableHeader,"理财专户产品每日年化利率");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				PlFinanceProductRate plFinanceProductRate=plFinanceProductRateService.get(new Long(id));
				if(plFinanceProductRate!=null){//判断费率是否为空
					if(plFinanceProductRate.getIntentDate()!=null){
						Date nowDay=DateUtil.getDateFirstDate(new Date(), "first");
						if(plFinanceProductRate.getIntentDate().compareTo(nowDay)>=0){
							plFinanceProductRateService.remove(new Long(id));
						}
					}else{
						plFinanceProductRateService.remove(new Long(id));
					}
				}else{
					plFinanceProductRateService.remove(new Long(id));
				}
				
				
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
		PlFinanceProductRate plFinanceProductRate=plFinanceProductRateService.get(id);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plFinanceProductRate));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 保存更新操作方法
	 */
	public String save(){
		if(plFinanceProductRate.getId()==null){
			plFinanceProductRate.setCreateDate(new Date());
			plFinanceProductRate.setModifyDate(new Date());
			plFinanceProductRate.setCreateUserId(ContextUtil.getCurrentUserId());
			plFinanceProductRate.setCreateUserName(ContextUtil.getCurrentUser().getFullname());
			plFinanceProductRate=plFinanceProductRateService.setSevenRate(plFinanceProductRate);
			plFinanceProductRateService.save(plFinanceProductRate);
		}else{
			PlFinanceProductRate orgPlFinanceProductRate=plFinanceProductRateService.get(plFinanceProductRate.getId());
			try{
				plFinanceProductRate.setModifyDate(new Date());
				plFinanceProductRate.setCreateUserId(ContextUtil.getCurrentUserId());
				plFinanceProductRate.setCreateUserName(ContextUtil.getCurrentUser().getFullname());
				plFinanceProductRate=plFinanceProductRateService.setSevenRate(plFinanceProductRate);
				BeanUtil.copyNotNullProperties(orgPlFinanceProductRate, plFinanceProductRate);
				plFinanceProductRateService.save(orgPlFinanceProductRate);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 批量添加
	 * @return
	 */
	public String update(){
		try{
			String productId=this.getRequest().getParameter("productId");
			String firstDay=this.getRequest().getParameter("firstDay");
			String endDay=this.getRequest().getParameter("endDay");
			String yearRate=this.getRequest().getParameter("yearRate");
			String msg="添加成功,请刷新页面!";
			if(productId!=null&&!"".equals(productId)){//选择产品类型
				if(firstDay!=null&&!"".equals(firstDay)){
					if(endDay!=null&&!"".equals(endDay)){
						SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
						//获取两个日期之间的天数
						int days =DateUtil.getDaysBetweenDate(firstDay, endDay);
						if(days==0){
							PlFinanceProductRate plFinanceProductRate=new PlFinanceProductRate();
							plFinanceProductRate.setProductId(Long.valueOf(productId));
							plFinanceProductRate.setYearRate(new BigDecimal(yearRate));
							plFinanceProductRate.setIntentDate(formatDate.parse(firstDay));
							plFinanceProductRate.setCreateDate(new Date());
							plFinanceProductRate.setModifyDate(new Date());
							plFinanceProductRate.setCreateUserId(ContextUtil.getCurrentUserId());
							plFinanceProductRate.setCreateUserName(ContextUtil.getCurrentUser().getFullname());
							plFinanceProductRate=plFinanceProductRateService.setSevenRate(plFinanceProductRate);
							plFinanceProductRateService.save(plFinanceProductRate);
						}else if(days>0){
							for(int i=0;i<=days;i++){
								PlFinanceProductRate plFinanceProductRate=new PlFinanceProductRate();
								plFinanceProductRate.setProductId(Long.valueOf(productId));
								plFinanceProductRate.setYearRate(new BigDecimal(yearRate));
								plFinanceProductRate.setIntentDate(DateUtil.addDaysToDate(formatDate.parse(firstDay), i));
								plFinanceProductRate.setCreateDate(new Date());
								plFinanceProductRate.setModifyDate(new Date());
								plFinanceProductRate.setCreateUserId(ContextUtil.getCurrentUserId());
								plFinanceProductRate.setCreateUserName(ContextUtil.getCurrentUser().getFullname());
								plFinanceProductRate=plFinanceProductRateService.setSevenRate(plFinanceProductRate);
								plFinanceProductRateService.save(plFinanceProductRate);
							}
						}else{
							msg="请填写正确的起止日期";
						}
					}else{
						msg="请选择利率设置结束日期，结束日期不允许为空";
					}
				}else{
					msg="请选择利率设置开始日期，开始日期不允许为空";
				}
			}else{
				msg="请选择产品类型，产品类型不允许为空";
			}
			setJsonString("{success:true,msg:'"+msg+"'}");
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,msg:'系统报错，请联系管理员'}");
		}
		return SUCCESS;
	}
	/**
	 * 获取利率设置的最新的日期，如果没有最新日期默认为当前日期
	 * @return
	 */
	public String lastDate(){
		String lastDateStr=DateUtil.dateToStr(new Date(), "yyyy-MM-dd");
		try{
			String productId=this.getRequest().getParameter("productId");
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date lastDate=formatDate.parse(DateUtil.dateToStr(new Date(), "yyyy-MM-dd 00:00:00"));
			
			if(productId!=null){
				Date rlastDate=plFinanceProductRateService.getLastDay(productId);
				if(rlastDate!=null&&rlastDate.compareTo(lastDate)>0){
					lastDateStr=DateUtil.dateToStr(DateUtil.addDaysToDate(rlastDate, 1),"yyyy-MM-dd");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			//setJsonString("{success:true,date:'系统报错，请联系管理员'}");
		}
		setJsonString("{success:true,date:'"+lastDateStr+"'}");
		return SUCCESS;
	}
}
