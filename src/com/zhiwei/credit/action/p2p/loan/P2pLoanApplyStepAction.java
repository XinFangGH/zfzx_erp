package com.zhiwei.credit.action.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.p2p.loan.P2pLoanApplyStep;
import com.zhiwei.credit.model.p2p.loan.P2pLoanProduct;
import com.zhiwei.credit.service.p2p.loan.P2pLoanApplyStepService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanProductService;
/**
 * 
 * @author 
 *
 */
public class P2pLoanApplyStepAction extends BaseAction{
	@Resource
	private P2pLoanApplyStepService p2pLoanApplyStepService;
	@Resource
	private P2pLoanProductService p2pLoanProductService;
	
	private P2pLoanApplyStep p2pLoanApplyStep;
	
	private Long stepId;

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public P2pLoanApplyStep getP2pLoanApplyStep() {
		return p2pLoanApplyStep;
	}

	public void setP2pLoanApplyStep(P2pLoanApplyStep p2pLoanApplyStep) {
		this.p2pLoanApplyStep = p2pLoanApplyStep;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<P2pLoanApplyStep> list= p2pLoanApplyStepService.getAll(filter);
		
		Type type=new TypeToken<List<P2pLoanApplyStep>>(){}.getType();
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
				p2pLoanApplyStepService.remove(new Long(id));
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
		P2pLoanApplyStep p2pLoanApplyStep=p2pLoanApplyStepService.get(stepId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(p2pLoanApplyStep));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(p2pLoanApplyStep.getStepId()==null){
			p2pLoanApplyStepService.save(p2pLoanApplyStep);
		}else{
			P2pLoanApplyStep orgP2pLoanApplyStep=p2pLoanApplyStepService.get(p2pLoanApplyStep.getStepId());
			try{
				BeanUtil.copyNotNullProperties(orgP2pLoanApplyStep, p2pLoanApplyStep);
				p2pLoanApplyStepService.save(orgP2pLoanApplyStep);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String saveStep(){
		boolean flag=true;
		String stepGridJson=this.getRequest().getParameter("stepGridJson");
		String state=this.getRequest().getParameter("state");
		String productId=this.getRequest().getParameter("productId");
		if(null!=stepGridJson && !"".equals(stepGridJson)){
			String[] stepArr = stepGridJson.split("@");
			for (int k = 0; k < stepArr.length; k++) {
				JSONParser parser = new JSONParser(new StringReader(stepArr[k]));
				try {
					P2pLoanApplyStep step=(P2pLoanApplyStep)JSONMapper.toJava(parser.nextValue(), P2pLoanApplyStep.class);
					p2pLoanApplyStepService.save(step);
				} catch (Exception e) {
					flag=false;
					e.printStackTrace();
				}
			}
			if(null!=state && !"".equals(state)){
				//修改申请状态
				P2pLoanProduct product=p2pLoanProductService.get(Long.valueOf(productId));
				product.setProductState(Long.valueOf(state));
				p2pLoanProductService.merge(product);
			}
		}
		setJsonString("{success:"+flag+"}");
		return SUCCESS;
	}
}
