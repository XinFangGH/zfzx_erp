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
import com.zhiwei.core.util.ContextUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.util.JsonUtil;



import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddownstream;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntDownstreamCustomService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpanddownstreamService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpstreamCustomService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustEntUpanddownstreamAction extends BaseAction{
	@Resource
	private BpCustEntUpanddownstreamService bpCustEntUpanddownstreamService;
	@Resource
	private BpCustEntUpstreamCustomService bpCustEntUpstreamCustomService;
	@Resource
	private BpCustEntDownstreamCustomService bpCustEntDownstreamCustomService;
	@Resource
	private EnterpriseService enterpriseService;
	private BpCustEntUpanddownstream bpCustEntUpanddownstream;
	private Integer downCustomId;
	private Integer upAndDownCustomId;
	private Integer upCustomId;

	

	public BpCustEntUpanddownstream getBpCustEntUpanddownstream() {
		return bpCustEntUpanddownstream;
	}

	public void setBpCustEntUpanddownstream(BpCustEntUpanddownstream bpCustEntUpanddownstream) {
		this.bpCustEntUpanddownstream = bpCustEntUpanddownstream;
	}


	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public Integer getDownCustomId() {
		return downCustomId;
	}

	public void setDownCustomId(Integer downCustomId) {
		this.downCustomId = downCustomId;
	}

	public Integer getUpAndDownCustomId() {
		return upAndDownCustomId;
	}

	public void setUpAndDownCustomId(Integer upAndDownCustomId) {
		this.upAndDownCustomId = upAndDownCustomId;
	}

	public Integer getUpCustomId() {
		return upCustomId;
	}

	public void setUpCustomId(Integer upCustomId) {
		this.upCustomId = upCustomId;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustEntUpanddownstream> list= bpCustEntUpanddownstreamService.getAll(filter);
		
		Type type=new TypeToken<List<BpCustEntUpanddownstream>>(){}.getType();
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
				bpCustEntUpanddownstreamService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
      public String Upstreamdetele(){
	
    	 
    	  if(null!=downCustomId&&!"".equals(downCustomId)){
    		  bpCustEntUpstreamCustomService.remove(bpCustEntUpanddownstreamService.getByeupid(upCustomId));
        	}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
      public String Upstreamlist(){
  		
  		QueryFilter filter=new QueryFilter(getRequest());
  		List<BpCustEntUpstreamCustom> list= bpCustEntUpanddownstreamService.getByupAndDownCustomId(upAndDownCustomId);
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
    	if(null!=downCustomId&&!"".equals(downCustomId)){
    	  bpCustEntDownstreamCustomService.remove(bpCustEntUpanddownstreamService.getByedownid(downCustomId));
    	}
	jsonString="{success:true}";
	
	return SUCCESS;
}
	public String Downstreamlist(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustEntDownstreamCustom> list= bpCustEntUpanddownstreamService.getByupAndDownCustomId1(upAndDownCustomId);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpCustEntUpanddownstream bpCustEntUpanddownstream=bpCustEntUpanddownstreamService.getByeid(upAndDownCustomId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustEntUpanddownstream));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	public String getebyEnterpriseId(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		List<BpCustEntUpanddownstream> list=bpCustEntUpanddownstreamService.getByentpriseid(Integer.valueOf(enterpriseId));
		if(null!=list &&list.size()!=0){
			
		   bpCustEntUpanddownstream=list.get(0);
		}
			
		StringBuffer buff = new StringBuffer("{success:true,'data':");
		
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{});
		buff.append(json.serialize(bpCustEntUpanddownstream));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String getbIdyEnterpriseId(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		List<BpCustEntUpanddownstream> list=bpCustEntUpanddownstreamService.getByentpriseid(Integer.valueOf(enterpriseId));
		if(null!=list &&list.size()!=0){
			
		   bpCustEntUpanddownstream=list.get(0);
		}

		
		if(null!=list &&list.size()!=0){
			
			   bpCustEntUpanddownstream=list.get(0);
			   setJsonString("{success:true,upAndDownCustomId:"+bpCustEntUpanddownstream.getUpAndDownCustomId()+"}");
			}else{
			setJsonString("{success:true,upAndDownCustomId:null}");
			}
			
		
		return SUCCESS;
	}
	
	public String getUpAndDownCustomIdBy(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		List<BpCustEntUpanddownstream> list=bpCustEntUpanddownstreamService.getByentpriseid(Integer.valueOf(enterpriseId));
		if(null!=list &&list.size()!=0){
			
		   bpCustEntUpanddownstream=list.get(0);
		}
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustEntUpanddownstream));
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
		String UpstreamInfoData =this.getRequest().getParameter("UpstreamInfoData");
		String DownstreamInfoData =this.getRequest().getParameter("DownstreamInfoData");
		
		if(bpCustEntUpanddownstream.getUpAndDownCustomId()==null){
			
			bpCustEntUpanddownstream.setEnterprise(enterpriseService.getById(Integer.valueOf(enterpriseId)));
		
			bpCustEntUpanddownstreamService.save(bpCustEntUpanddownstream);
		}else{
			BpCustEntUpanddownstream orgBpCustEntUpanddownstream=bpCustEntUpanddownstreamService.getByeid(bpCustEntUpanddownstream.getUpAndDownCustomId());
			
				BeanUtil.copyNotNullProperties(orgBpCustEntUpanddownstream, bpCustEntUpanddownstream);
				bpCustEntUpanddownstreamService.save(orgBpCustEntUpanddownstream);
		
		}
		Integer upAnddownId=bpCustEntUpanddownstream.getUpAndDownCustomId();
		if(null!=enterpriseId && !"".equals(enterpriseId)){
		
			if(UpstreamInfoData!=null&&!"".equals(UpstreamInfoData)){
				String[] personCarInfoArr = UpstreamInfoData.split("@");
				for (int i = 0; i < personCarInfoArr.length; i++) {
					String str = personCarInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntUpstreamCustom bpCustEntUpstreamCustom = (BpCustEntUpstreamCustom) JSONMapper.toJava(
							parser.nextValue(), BpCustEntUpstreamCustom.class);
					
					bpCustEntUpstreamCustom.setBpCustEntUpanddownstream(bpCustEntUpanddownstreamService.getByeid(upAnddownId));
					if(bpCustEntUpstreamCustom.getUpCustomId()==null||"".equals(bpCustEntUpstreamCustom.getUpCustomId())){
						bpCustEntUpstreamCustomService.save(bpCustEntUpstreamCustom);
					}else{
						BpCustEntUpstreamCustom temp=bpCustEntUpanddownstreamService.getByeupid(bpCustEntUpstreamCustom.getUpCustomId());
						BeanUtil.copyNotNullProperties(temp, bpCustEntUpstreamCustom);
						bpCustEntUpstreamCustomService.save(temp);
					}
				}
				
			}
			if(DownstreamInfoData!=null&&!"".equals(DownstreamInfoData)){
				String[] downstreamInfoArr = DownstreamInfoData.split("@");
				for (int i = 0; i < downstreamInfoArr.length; i++) {
					String str = downstreamInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntDownstreamCustom bpCustEntDownstreamCustom = (BpCustEntDownstreamCustom) JSONMapper.toJava(
							parser.nextValue(), BpCustEntDownstreamCustom.class);
					bpCustEntDownstreamCustom.setBpCustEntUpanddownstream(bpCustEntUpanddownstreamService.getByeid(upAnddownId));
					if(bpCustEntDownstreamCustom.getDownCustomId()==null||"".equals(bpCustEntDownstreamCustom.getDownCustomId())){
						bpCustEntDownstreamCustomService.save(bpCustEntDownstreamCustom);
					}else{
						BpCustEntDownstreamCustom tempHouse=bpCustEntUpanddownstreamService.getByedownid(bpCustEntDownstreamCustom.getDownCustomId());
						BeanUtil.copyNotNullProperties(tempHouse, bpCustEntDownstreamCustom);
						bpCustEntDownstreamCustomService.save(tempHouse);
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