package com.zhiwei.credit.action.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.thirdInterface.WebBankCode;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.thirdInterface.WebBankCodeService;
/**
 * 
 * @author 
 *
 */
public class CsBankAction extends BaseAction{
	@Resource
	private CsBankService csBankService;
	private CsBank csBank;
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	private Long bankid;
	
	public  String thirdBankType;// 第三方支付标志字段svn:songwj
	
	@Resource
	private WebBankCodeService webBankCodeService;//银行logo，svn：songwj

	public Long getBankid() {
		return bankid;
	}

	public void setBankid(Long bankid) {
		this.bankid = bankid;
	}

	public CsBank getCsBank() {
		return csBank;
	}

	public void setCsBank(CsBank csBank) {
		this.csBank = csBank;
	}
	
	/**
	 * 根据第三方查询银行信息
	 * svn:songwj
	 * @return
	 */
	public String listByThirdBandType(){
		QueryFilter filter=new QueryFilter(getRequest());
		PagingBean pagingBean=filter.getPagingBean();
		filter.addFilter("Q_typeKey_S_EQ", getKey(thirdBankType));
//		List<CsBank> list= csBankService.listByType(getKey(thirdBankType),pagingBean,"1");
		List<CsBank> list=csBankService.getAll(filter);
		if(list !=null){
			for(int i=0;i<list.size();i++){
				String bankCodeId = list.get(i).getBankCodeId();
				if(bankCodeId != null && !"".equals(bankCodeId)){
					WebBankCode webBankCode = new WebBankCode();
					webBankCode=webBankCodeService.get(Long.valueOf(list.get(i).getBankCodeId().toString()));
					list.get(i).setLogoURL(AppUtil.getConfigMap().get("fileURL")+webBankCode.getBankLogo());
					System.out.println("di san fang yin hang logo is    "+list.get(i).getLogoURL());
				}
			}
		}
		
//		csBankService.listByType(getKey(thirdBankType),pagingBean,"2");
		Type type=new TypeToken<List<CsBank>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pagingBean.getTotalItems())
		.append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	//返回第三方key
	public  String getKey(String thirdBankType){
		
		System.out.println("thirdBankType---"+thirdBankType);
		
		String key="";
		if(thirdBankType.equals("1")){
//			key ="hurongConfig";//正式
			key ="zhiweiConfig";//测试
		}else if(thirdBankType.equals("2")){
			key ="gopayConfig";
		}else if(thirdBankType.equals("3")){
			key ="fuiouConfig";
		}else if(thirdBankType.equals("4")){
			key ="huifuConfig";
		}else if(thirdBankType.equals("5")){
			key ="moneyMoreMoreConfig";
		}else if(thirdBankType.equals("6")){
			key ="easypayConfig";
		}else if(thirdBankType.equals("7")){
			key ="yeepayConfig";
		}else if(thirdBankType.equals("8")){
			key ="baoFooConfig";
		}
		return key;
	}

	

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CsBank> list= csBankService.getAll(filter);
		
		Type type=new TypeToken<List<CsBank>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	public String getBankList(){
		QueryFilter filter=new QueryFilter(this.getRequest());
		String thirdPayConfig=configMap.get("thirdPayConfig").toString();
		if(thirdPayConfig!=null&&thirdPayConfig.equals("")){
			filter.addFilter("Q_typeKey_S_EQ", thirdPayConfig); //获取 默认 配置项
		}else{
			filter.addFilter("Q_typeKey_S_EQ", "zhiweiConfig"); //获取 默认 配置项
		}
		filter.getPagingBean().setPageSize(1000000000);
		List<CsBank> list=csBankService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(CsBank bank:list){
			buff.append("[").append(bank.getBankid()).append(",'")
			.append(bank.getBankname()).append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	public String getBankListQuery(){
		QueryFilter filter=new QueryFilter(this.getRequest());
		String thirdPayConfig=configMap.get("thirdPayConfig").toString();
		if(thirdPayConfig!=null&&thirdPayConfig.equals("")){
			filter.addFilter("Q_typeKey_S_EQ", thirdPayConfig); //获取 默认 配置项
		}else{
			filter.addFilter("Q_typeKey_S_EQ", "zhiweiConfig"); //获取 默认 配置项
		}
		
		List<CsBank> list=csBankService.getAll(filter);
		JsonUtil.jsonFromList(list, filter.getPagingBean().getTotalItems());
		return SUCCESS;
	}	
	/**
	 * 批量删除
	 * @return
	 */
	@LogResource(description = "删除银行")
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				csBankService.remove(new Long(id));
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
		CsBank csBank=csBankService.get(bankid);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csBank));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	@LogResource(description = "添加或修改银行")
	public String save(){
		if(csBank.getBankid()==null){
			List<CsBank> list=csBankService.getListByBankName(csBank.getBankname());
			if(null!=list && list.size()>0){
				setJsonString("{success:true,msg:false}");
			}else{
				csBank.setTypeKey("zhiweiConfig");
				csBankService.save(csBank);
				setJsonString("{success:true}");
			}
		}else{
			CsBank orgCsBank=csBankService.get(csBank.getBankid());
			csBank.setTypeKey("zhiweiConfig");
			if(orgCsBank.getBankname().equals(csBank.getBankname())){
				if(orgCsBank.getBankid().equals(csBank.getBankid())){
					try{
						BeanUtil.copyNotNullProperties(orgCsBank, csBank);
						csBankService.save(orgCsBank);
						setJsonString("{success:true}");
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}else{
					setJsonString("{success:true,msg:false}");
				}
			}else{
				try{
					if(!orgCsBank.getBankid().equals(csBank.getBankid())){
						BeanUtil.copyNotNullProperties(orgCsBank, csBank);
						csBankService.save(orgCsBank);
						setJsonString("{success:true}");
					}else{
						setJsonString("{success:true,msg:false}");
					}
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
			
		}
		
		return SUCCESS;
		
	}
	
	public String getThirdBankType() {
		return thirdBankType;
	}

	public void setThirdBankType(String thirdBankType) {
		this.thirdBankType = thirdBankType;
	}

}
