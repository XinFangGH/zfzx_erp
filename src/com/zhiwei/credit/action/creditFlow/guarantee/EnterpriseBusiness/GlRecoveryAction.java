package com.zhiwei.credit.action.creditFlow.guarantee.EnterpriseBusiness;
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

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlCompensatory;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlRecovery;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlRecoveryService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class GlRecoveryAction extends BaseAction{
	@Resource
	private GlRecoveryService glRecoveryService;
	@Resource
	private DictionaryService dictionaryService;
	private GlRecovery glRecovery;
	
	private Long recoveryId;
    private Long projectId;
    private String businessType;
    private String recovery;
    
    
	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getRecoveryId() {
		return recoveryId;
	}

	public void setRecoveryId(Long recoveryId) {
		this.recoveryId = recoveryId;
	}

	public GlRecovery getGlRecovery() {
		return glRecovery;
	}

	public void setGlRecovery(GlRecovery glRecovery) {
		this.glRecovery = glRecovery;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		
		List<GlRecovery> list= glRecoveryService.findGlRecoveryList(projectId, businessType);
		
		Type type=new TypeToken<List<GlRecovery>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		int i=0;
		for(GlRecovery glRecovery:list){
			i++;
			buff.append("{\"recoveryId\":");
			buff.append(glRecovery.getRecoveryId());
			buff.append(",\"compensatoryAmount\":");
			buff.append(glRecovery.getCompensatoryAmount());
			buff.append(",\"compensatoryBalance\":");
			buff.append(glRecovery.getCompensatoryBalance());
			buff.append(",\"compensatoryDate\":'");
			String date=glRecovery.getCompensatoryDate().toString();
			date=date.trim();
			date=date.substring(0,date.lastIndexOf(" "));
			buff.append(date);
			buff.append("',\"recoveryMoney\":");
			buff.append(glRecovery.getRecoveryMoney());
			buff.append(",\"recoveryDate\":'");
			String rdate=glRecovery.getRecoveryDate().toString();
			rdate=rdate.trim();
			rdate=rdate.substring(0,rdate.lastIndexOf(" "));
			buff.append(rdate);
			buff.append("',\"recoverySource\":'");
			buff.append(glRecovery.getRecoverySource());
			buff.append("',\"recoverySourceName\":'");
			if(glRecovery.getRecoverySource()!=null && !"".equals(glRecovery.getRecoverySource())){
		     	Dictionary dic=dictionaryService.get(Long.parseLong(glRecovery.getRecoverySource()));
			    buff.append(dic.getItemValue());
			}else{
				buff.append("");
			}
			buff.append("',\"recoveryRemarks\":'");
			buff.append(glRecovery.getRecoveryRemarks());
			if(i<list.size()){
			    buff.append("'},");
			}else{
				buff.append("'}");
			}
		}
		buff.append("]}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		
		
		if(recoveryId!=null){
		
				glRecoveryService.remove(recoveryId);
		
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlRecovery glRecovery=glRecoveryService.get(recoveryId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glRecovery));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(null != recovery && !"".equals(recovery)) {

			String[] recoveryArr = recovery.split("@");
			
			for(int i=0; i<recoveryArr.length; i++) {
				String str = recoveryArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try{
					GlRecovery glRecovery = (GlRecovery)JSONMapper.toJava(parser.nextValue(),GlRecovery.class);
					glRecovery.setProjectId(projectId);
					glRecovery.setBusinessType(businessType);
					if(null==glRecovery.getRecoveryId() || "".equals(glRecovery.getRecoveryId().toString())){
						glRecoveryService.save(glRecovery);
					}else{
						GlRecovery orgglRecovery=glRecoveryService.get(glRecovery.getRecoveryId());
						BeanUtil.copyNotNullProperties(orgglRecovery, glRecovery);
						glRecoveryService.save(orgglRecovery);
					}
					setJsonString("{success:true}");
				
				} catch(Exception e) {
					logger.error("GlCompensatoryAction:"+e.getMessage());
					setJsonString("{success:false}");
					e.printStackTrace();
				
				}
				
			}
		}

		
		return SUCCESS;
		
	}
}
