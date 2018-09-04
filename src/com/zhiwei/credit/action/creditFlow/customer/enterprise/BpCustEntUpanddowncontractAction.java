package com.zhiwei.credit.action.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.io.StringReader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddowncontract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddownstream;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntDownstreamContractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntDownstreamCustomService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpanddowncontractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpstreamContractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpstreamCustomService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustEntUpanddowncontractAction extends BaseAction{
	@Resource
	private BpCustEntUpanddowncontractService bpCustEntUpanddownContractService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private BpCustEntUpstreamContractService bpCustEntUpstreamContractService;
	@Resource
	private BpCustEntDownstreamContractService bpCustEntDownstreamContractService;
	private BpCustEntUpanddowncontract bpCustEntUpanddownContract;
	private Integer upContractId;

	private Integer downContractId;
	private Integer upAndDownContractId;



	

	public Integer getUpAndDownContractId() {
		return upAndDownContractId;
	}

	public void setUpAndDownContractId(Integer upAndDownContractId) {
		this.upAndDownContractId = upAndDownContractId;
	}

	public BpCustEntUpanddowncontract getBpCustEntUpanddownContract() {
		return bpCustEntUpanddownContract;
	}

	public void setBpCustEntUpanddownContract(
			BpCustEntUpanddowncontract bpCustEntUpanddownContract) {
		this.bpCustEntUpanddownContract = bpCustEntUpanddownContract;
	}

	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}



	public Integer getUpContractId() {
		return upContractId;
	}

	public void setUpContractId(Integer upContractId) {
		this.upContractId = upContractId;
	}

	public Integer getDownContractId() {
		return downContractId;
	}

	public void setDownContractId(Integer downContractId) {
		this.downContractId = downContractId;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustEntUpanddowncontract> list= bpCustEntUpanddownContractService.getAll(filter);
		
		Type type=new TypeToken<List<BpCustEntUpanddowncontract>>(){}.getType();
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
				bpCustEntUpanddownContractService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	
	  public String Upstreamdetele(){
			
	    	 
    	  if(null!=upContractId&&!"".equals(upContractId)){
    		  bpCustEntUpstreamContractService.remove(bpCustEntUpanddownContractService.getByeupid(upContractId));
        	}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
      public String Upstreamlist(){
  		
  		QueryFilter filter=new QueryFilter(getRequest());
  		List<BpCustEntUpstreamContract> list= bpCustEntUpanddownContractService.getByupAndDownContractId(upAndDownContractId);
  		int size=list.size();
  		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
  		
  		return SUCCESS;
  	}
      public String Downstreamdetele(){
    	if(null!=downContractId&&!"".equals(downContractId)){
    	  bpCustEntDownstreamContractService.remove(bpCustEntUpanddownContractService.getByedownid(downContractId));
    	}
	jsonString="{success:true}";
	
	return SUCCESS;
}
	public String Downstreamlist(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustEntDownstreamContract> list= bpCustEntUpanddownContractService.getByupAndDownContractId1(upAndDownContractId);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}

	public String getebyEnterpriseId(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		List<BpCustEntUpanddowncontract> list=bpCustEntUpanddownContractService.getByentpriseid(Integer.valueOf(enterpriseId));
		if(null!=list &&list.size()!=0){
			
		   bpCustEntUpanddownContract=list.get(0);
		}
			
		StringBuffer buff = new StringBuffer("{success:true,'data':");
		
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{});
		buff.append(json.serialize(bpCustEntUpanddownContract));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String getbIdyEnterpriseId(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		List<BpCustEntUpanddowncontract> list=bpCustEntUpanddownContractService.getByentpriseid(Integer.valueOf(enterpriseId));
		if(null!=list &&list.size()!=0){
			
		   bpCustEntUpanddownContract=list.get(0);
		}

		
		if(null!=list &&list.size()!=0){
			
			   bpCustEntUpanddownContract=list.get(0);
			   setJsonString("{success:true,upAndDownContractId:"+bpCustEntUpanddownContract.getUpAndDownContractId()+"}");
			}else{
			setJsonString("{success:true,upAndDownContractId:null}");
			}
			
		
		return SUCCESS;
	}
	
	public String getUpAndDownCustomIdBy(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		List<BpCustEntUpanddowncontract> list=bpCustEntUpanddownContractService.getByentpriseid(Integer.valueOf(enterpriseId));
		if(null!=list &&list.size()!=0){
			
		   bpCustEntUpanddownContract=list.get(0);
		}
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustEntUpanddownContract));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
	
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		String UpstreamInfoData =this.getRequest().getParameter("UpContractInfoData");
		String DownstreamInfoData =this.getRequest().getParameter("DownContractInfoData");
		
		if(bpCustEntUpanddownContract.getUpAndDownContractId()==null){
			
			bpCustEntUpanddownContract.setEnterprise(enterpriseService.getById(Integer.valueOf(enterpriseId)));
		
			bpCustEntUpanddownContractService.save(bpCustEntUpanddownContract);
		}else{
		BpCustEntUpanddowncontract orgBpCustEntUpanddownContract=bpCustEntUpanddownContractService.getByeid(bpCustEntUpanddownContract.getUpAndDownContractId());
		orgBpCustEntUpanddownContract.setUpremarks(bpCustEntUpanddownContract.getUpremarks());
		orgBpCustEntUpanddownContract.setDownremarks(bpCustEntUpanddownContract.getDownremarks());
		//		BeanUtil.copyNotNullProperties(orgBpCustEntUpanddownContract, bpCustEntUpanddownContract);
				bpCustEntUpanddownContractService.save(orgBpCustEntUpanddownContract);
		
		}
		Integer upAnddownId=bpCustEntUpanddownContract.getUpAndDownContractId();
		if(null!=enterpriseId && !"".equals(enterpriseId)){
		
			if(UpstreamInfoData!=null&&!"".equals(UpstreamInfoData)){
				String[] personCarInfoArr = UpstreamInfoData.split("@");
				for (int i = 0; i < personCarInfoArr.length; i++) {
					String str = personCarInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntUpstreamContract bpCustEntUpstreamContract = (BpCustEntUpstreamContract) JSONMapper.toJava(
							parser.nextValue(), BpCustEntUpstreamContract.class);
					
					bpCustEntUpstreamContract.setBpCustEntUpanddowncontract(bpCustEntUpanddownContractService.getByeid(upAnddownId));
					if(bpCustEntUpstreamContract.getUpContractId()==null||"".equals(bpCustEntUpstreamContract.getUpContractId())){
						bpCustEntUpstreamContractService.save(bpCustEntUpstreamContract);
					}else{
						BpCustEntUpstreamContract temp=bpCustEntUpanddownContractService.getByeupid(bpCustEntUpstreamContract.getUpContractId());
						BeanUtil.copyNotNullProperties(temp, bpCustEntUpstreamContract);
						bpCustEntUpstreamContractService.save(temp);
					}
				}
				
			}
			if(DownstreamInfoData!=null&&!"".equals(DownstreamInfoData)){
				String[] downstreamInfoArr = DownstreamInfoData.split("@");
				for (int i = 0; i < downstreamInfoArr.length; i++) {
					String str = downstreamInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntDownstreamContract bpCustEntDownstreamContract = (BpCustEntDownstreamContract) JSONMapper.toJava(
							parser.nextValue(), BpCustEntDownstreamContract.class);
					bpCustEntDownstreamContract.setBpCustEntUpanddowncontract(bpCustEntUpanddownContractService.getByeid(upAnddownId));
					if(bpCustEntDownstreamContract.getDownContractId()==null||"".equals(bpCustEntDownstreamContract.getDownContractId())){
						bpCustEntDownstreamContractService.save(bpCustEntDownstreamContract);
					}else{
						BpCustEntDownstreamContract tempHouse=bpCustEntUpanddownContractService.getByedownid(bpCustEntDownstreamContract.getDownContractId());
						BeanUtil.copyNotNullProperties(tempHouse, bpCustEntDownstreamContract);
						bpCustEntDownstreamContractService.save(tempHouse);
					}
				}
				
			}
			
		setJsonString("{success:true}");
		return SUCCESS;
		
		
	}
		}catch(Exception ex){
		logger.error(ex.getMessage());
	}
		return SUCCESS;
}
}
