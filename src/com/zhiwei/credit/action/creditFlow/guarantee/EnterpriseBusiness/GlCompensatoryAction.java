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
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlCompensatoryService;
/**
 * 
 * @author 
 *
 */
public class GlCompensatoryAction extends BaseAction{
	@Resource
	private GlCompensatoryService glCompensatoryService;
	private GlCompensatory glCompensatory;
	
	private Long compensatoryId;
    private Long projectId;
    private String businessType;
    private String compensatory;
    
    
	public String getCompensatory() {
		return compensatory;
	}

	public void setCompensatory(String compensatory) {
		this.compensatory = compensatory;
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

	public Long getCompensatoryId() {
		return compensatoryId;
	}

	public void setCompensatoryId(Long compensatoryId) {
		this.compensatoryId = compensatoryId;
	}

	public GlCompensatory getGlCompensatory() {
		return glCompensatory;
	}

	public void setGlCompensatory(GlCompensatory glCompensatory) {
		this.glCompensatory = glCompensatory;
	}

	/**
	 * 显示列表
	 */
	public String list(){
	
		List<GlCompensatory> list= glCompensatoryService.findGlCompensatoryList(projectId, businessType);
		
		Type type=new TypeToken<List<GlCompensatory>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");	
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
	public String delete(){
		
		
		if(compensatoryId!=null){	
				glCompensatoryService.remove(compensatoryId);
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlCompensatory glCompensatory=glCompensatoryService.get(compensatoryId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glCompensatory));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(null != compensatory && !"".equals(compensatory)) {

			String[] bankLoanProgramArr = compensatory.split("@");
			
			for(int i=0; i<bankLoanProgramArr.length; i++) {
				String str = bankLoanProgramArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try{
					GlCompensatory glCompensatory = (GlCompensatory)JSONMapper.toJava(parser.nextValue(),GlCompensatory.class);
					glCompensatory.setProjectId(projectId);
					glCompensatory.setBusinessType(businessType);
					if(null==glCompensatory.getCompensatoryId() || "".equals(glCompensatory.getCompensatoryId().toString())){
						glCompensatoryService.save(glCompensatory);
					}else{
						GlCompensatory orgglCompensatory=glCompensatoryService.get(glCompensatory.getCompensatoryId());
						BeanUtil.copyNotNullProperties(orgglCompensatory, glCompensatory);
						glCompensatoryService.save(orgglCompensatory);
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
