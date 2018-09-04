package com.zhiwei.credit.action.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUserAccountInfo;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductUserAccountInfoService;
/**
 * 
 * @author 
 *
 */
public class PlFinanceProductUserAccountInfoAction extends BaseAction{
	@Resource
	private PlFinanceProductUserAccountInfoService plFinanceProductUserAccountInfoService;
	private PlFinanceProductUserAccountInfo plFinanceProductUserAccountInfo;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlFinanceProductUserAccountInfo getPlFinanceProductUserAccountInfo() {
		return plFinanceProductUserAccountInfo;
	}

	public void setPlFinanceProductUserAccountInfo(PlFinanceProductUserAccountInfo plFinanceProductUserAccountInfo) {
		this.plFinanceProductUserAccountInfo = plFinanceProductUserAccountInfo;
	}

	/**
	 * 查询交易记录列表
	 */
	public String list(){
		PagingBean  pb=new PagingBean(start,limit);
		List<PlFinanceProductUserAccountInfo> list= plFinanceProductUserAccountInfoService.getListByParamet(this.getRequest(),pb);
		Type type=new TypeToken<List<PlFinanceProductUserAccountInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SS").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	//专户交易导出excel
	public void exportAllToExcel(){
		try {
			PagingBean pb = null;
			List<PlFinanceProductUserAccountInfo> list= plFinanceProductUserAccountInfoService.getListByParamet(this.getRequest(),pb);
			String [] tableHeader = {"序号","交易时间","会员账号","开户名","产品名称","交易金额","账户金额","交易类型","交易状态","流水号","备注"};
			ExcelHelper.exportAllToExcel(list,tableHeader,"专户交易台账");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//专户异常记录导出excel
	public void exportExceptionToExcel(){
		try {
			PagingBean pb = null;
			List<PlFinanceProductUserAccountInfo> list= plFinanceProductUserAccountInfoService.getListByParamet(this.getRequest(),pb);
			String [] tableHeader = {"序号","交易时间","会员账号","开户名","产品名称","交易金额","交易类型","流水号","备注"};
			ExcelHelper.exportExceptionToExcel(list,tableHeader,"异常交易台账");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//专户派息记录导出excel
	public void exportIntentToExcel(){
		try {
			PagingBean pb = null;
			List<PlFinanceProductUserAccountInfo> list= plFinanceProductUserAccountInfoService.getListByParamet(this.getRequest(),pb);
			String [] tableHeader = {"序号","派息时间","会员账号","开户名","产品名称","交易金额","账户金额","交易状态","流水号","备注"};
			ExcelHelper.exportIntentToExcel(list,tableHeader,"派息交易台账");
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
				plFinanceProductUserAccountInfoService.remove(new Long(id));
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
		PlFinanceProductUserAccountInfo plFinanceProductUserAccountInfo=plFinanceProductUserAccountInfoService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plFinanceProductUserAccountInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plFinanceProductUserAccountInfo.getId()==null){
			plFinanceProductUserAccountInfoService.save(plFinanceProductUserAccountInfo);
		}else{
			PlFinanceProductUserAccountInfo orgPlFinanceProductUserAccountInfo=plFinanceProductUserAccountInfoService.get(plFinanceProductUserAccountInfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlFinanceProductUserAccountInfo, plFinanceProductUserAccountInfo);
				plFinanceProductUserAccountInfoService.save(orgPlFinanceProductUserAccountInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 系统自动派息
	 */
	public String creteIntentRecord(){
		try{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			String Date=this.getRequest().getParameter("days");
			Boolean flag=false;
			if(Date!=null){
				flag=plFinanceProductUserAccountInfoService.creatYestDayIntent(sd.parse(Date));
			}else{
				//flag=plFinanceProductUserAccountInfoService.creatYestDayIntent(null);
			}
			setJsonString("{success:true}");
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
		
	}
	/**
	 * 当日派息方法（用来补今日未派息记录）
	 * @return
	 */
	public String createLateDayIntentRecord(){
		try{
			Boolean flag=plFinanceProductUserAccountInfoService.creatYestDayIntent(null);
			setJsonString("{success:true}");
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
	}
	
	public String beforeIntentRecord(){
		try{
			String msg="";
			String Date=this.getRequest().getParameter("days");
			Boolean flag=false;
			if(Date!=null){
				flag=plFinanceProductUserAccountInfoService.creatBeforeIntentRecord(Date);
			}else{
				msg="请填写补派息日期";
			}
			setJsonString("{success:true}");
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
	}
}
