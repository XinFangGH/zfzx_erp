package com.zhiwei.credit.action.creditFlow.customer.person;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonNegativeSurvey;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonWorkExperience;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonWorkExperienceService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustPersonWorkExperienceAction extends BaseAction{
	@Resource
	private BpCustPersonWorkExperienceService bpCustPersonWorkExperienceService;
	private BpCustPersonWorkExperience bpCustPersonWorkExperience;
	@Resource
	private PersonService personService;
	
	private Long workId;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public BpCustPersonWorkExperience getBpCustPersonWorkExperience() {
		return bpCustPersonWorkExperience;
	}

	public void setBpCustPersonWorkExperience(BpCustPersonWorkExperience bpCustPersonWorkExperience) {
		this.bpCustPersonWorkExperience = bpCustPersonWorkExperience;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		//根据个人客户ID查询出该客户所有的工作经历信息，返回字符串成功或失败
		int personId = Integer.valueOf(getRequest().getParameter("personId"));
		Person person = personService.getById(personId);
		List<BpCustPersonWorkExperience> list = new ArrayList<BpCustPersonWorkExperience>(person.getWorkExperienceInfos());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("workStartTime","workEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"workStartTime","workEndTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
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
				bpCustPersonWorkExperienceService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 删除
	 */
	public String delete(){
		String[] ids = this.getRequest().getParameterValues("ids");
		if(null!=ids && ids.length>0){
			for(String id:ids){
				bpCustPersonWorkExperienceService.remove(new Long(id));
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
		BpCustPersonWorkExperience bpCustPersonWorkExperience=bpCustPersonWorkExperienceService.get(workId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustPersonWorkExperience));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//获取传过来的参数
		try{
			HttpServletRequest request = getRequest();
//			int personId = Integer.valueOf(request.getParameter("personId"));
			String workExperienceInfos = request.getParameter("workExperienceInfos");
			if(workExperienceInfos!=null&&!"".equals(workExperienceInfos)){
				String[] workExperienceInfoArr = workExperienceInfos.split("@");
				for (int i = 0; i < workExperienceInfoArr.length; i++) {
					String str = workExperienceInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustPersonWorkExperience workExperienceInfo = (BpCustPersonWorkExperience) JSONMapper.toJava(parser.nextValue(), BpCustPersonWorkExperience.class);
					if(null == workExperienceInfo.getWorkId() || "".equals(workExperienceInfo.getWorkId())){
						bpCustPersonWorkExperienceService.save(workExperienceInfo);
					}else{
						BpCustPersonWorkExperience temp = bpCustPersonWorkExperienceService.get(workExperienceInfo.getWorkId());
						BeanUtil.copyNotNullProperties(temp, workExperienceInfo);
						bpCustPersonWorkExperienceService.save(temp);
					}
				}
				setJsonString("{success:true}");
			}
		}catch (Exception ex) {
			setJsonString("{success:false}");
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		return SUCCESS;
	}
}
