package com.credit.proj.mortgage.morservice.action;

import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
@SuppressWarnings("all")
public class RedirectAction extends CreditBaseAction{
	private int nextpage ;//ext提交过来的值的变量

	public void redirect(){
		String url=null;  //跳转的URL
		
		/*  
		 *  根据EXT提交过来的值进行页面的跳转,参照
		 *	com.credit.proj.mortgage.morservice.service中的pageconfig类
		*/
		if(nextpage==1){
			//url = "../vehicle/cs_proj_vehicle.jsp" ;
			//url = "/creditBusiness1.0/credit/mortgage/testUpdate.jsp" ;
		}else if(nextpage==2){
			//url = "../stockownership/cs_proj_stockownership.jsp";
		}else if(nextpage==3){
			//url = "../company/cs_proj_company.jsp";
		}else if(nextpage==4){
			//url = "../dutyperson/cs_proj_dutyperson.jsp";
		}else if(nextpage==5){
			//url = "../machineinfo/cs_proj_machineInfo.jsp";
		}else if(nextpage==6){
			//url = "../product/cs_proj_product.jsp";
		}else if(nextpage==7){
			//url = "../house/cs_proj_house.jsp";
		}else if(nextpage==8){
			//url = "../officebuilding/cs_proj_officeBuilding.jsp";
		}else if(nextpage==9){
			//url = "../houseground/cs_proj_houseground.jsp";
		}else if(nextpage==10){
			//url = "../business/cs_proj_business.jsp";
		}else if(nextpage==11){
			//url = "../businessandlive/cs_proj_businessAndLive.jsp";
		}else if(nextpage==12){
			//url = "../education/cs_proj_education.jsp";
		}else if(nextpage==13){
			//url = "../industry/cs_proj_industry.jsp";
		}else if(nextpage==14){
			//url = "../droit/cs_proj_droit.jsp";
		}else{
			url = "../error.jsp" ;
		}
		try {
			JsonUtil.jsonFromObject(url, true) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

}
