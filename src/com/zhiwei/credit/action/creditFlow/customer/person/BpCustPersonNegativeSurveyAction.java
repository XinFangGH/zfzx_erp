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

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonNegativeSurvey;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonNegativeSurveyService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustPersonNegativeSurveyAction extends BaseAction{
	@Resource
	private BpCustPersonNegativeSurveyService bpCustPersonNegativeSurveyService;
	private BpCustPersonNegativeSurvey bpCustPersonNegativeSurvey;
	@Resource
	private PersonService personService;
	
	private Long negativeId;

	public Long getNegativeId() {
		return negativeId;
	}

	public void setNegativeId(Long negativeId) {
		this.negativeId = negativeId;
	}

	public BpCustPersonNegativeSurvey getBpCustPersonNegativeSurvey() {
		return bpCustPersonNegativeSurvey;
	}

	public void setBpCustPersonNegativeSurvey(BpCustPersonNegativeSurvey bpCustPersonNegativeSurvey) {
		this.bpCustPersonNegativeSurvey = bpCustPersonNegativeSurvey;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		//根据个人客户ID查询出该客户所有的负面信息，返回字符串成功或失败
		int personId = Integer.valueOf(getRequest().getParameter("personId"));
		Person person = personService.getById(personId);
		List<BpCustPersonNegativeSurvey> list = new ArrayList<BpCustPersonNegativeSurvey>(person.getNegativeInfos());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("negativeTime","negativeEnteringTime");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"negativeTime","negativeEnteringTime"});
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
				bpCustPersonNegativeSurveyService.remove(new Long(id));
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
				bpCustPersonNegativeSurveyService.remove(new Long(id));
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
		BpCustPersonNegativeSurvey bpCustPersonNegativeSurvey=bpCustPersonNegativeSurveyService.get(negativeId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustPersonNegativeSurvey));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 * @throws MapperException 
	 * @throws RecognitionException 
	 * @throws TokenStreamException 
	 */
	public String save() {
		//获取传过来的参数
		try{
			HttpServletRequest request = getRequest();
			int personId = Integer.valueOf(request.getParameter("personId"));
			Long createrId = Long.valueOf(request.getParameter("createrId"));
			String negativeInfos = request.getParameter("negativeInfos");
			if(negativeInfos!=null&&!"".equals(negativeInfos)){
				String[] negativeInfoArr = negativeInfos.split("@");
				for (int i = 0; i < negativeInfoArr.length; i++) {
					String str = negativeInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustPersonNegativeSurvey negativeInfo = (BpCustPersonNegativeSurvey) JSONMapper.toJava(parser.nextValue(), BpCustPersonNegativeSurvey.class);
					if(null == negativeInfo.getNegativeId() || "".equals(negativeInfo.getNegativeId())){
						bpCustPersonNegativeSurveyService.save(negativeInfo);
					}else{
						BpCustPersonNegativeSurvey temp = bpCustPersonNegativeSurveyService.get(negativeInfo.getNegativeId());
						BeanUtil.copyNotNullProperties(temp, negativeInfo);
						bpCustPersonNegativeSurveyService.save(temp);
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
