package com.report.OfflineRecommendReoprt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.hurong.core.util.DateUtil;
import com.report.ReportUtil;
import com.zhiwei.core.web.action.BaseAction;

public class OfflineRecommendAction extends BaseAction{

	/**
	 * 查询线下推荐明细表(一级、二级),线下业绩明细表(一级、二级)
	 * @return
	 */
	public void recommend(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String investDateGE=this.getRequest().getParameter("investDateGE");//查询日期
		String investDateLE=this.getRequest().getParameter("investDateLE");//查询日期
		String userName=this.getRequest().getParameter("userName");//客户名称
		String plainpassword=this.getRequest().getParameter("plainpassword");//推荐码
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=userName && !"".equals(userName)){
			sqlCondition=sqlCondition+" and u.fullname like \"%"+userName+"%\"";
		}
		if(null!=plainpassword && !"".equals(plainpassword)){
			sqlCondition=sqlCondition+" and bm.plainpassword like \"%"+plainpassword+"%\"";
		}
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		if(null!=investDateGE && !"".equals(investDateGE)){
			//sqlCondition=sqlCondition+"&investDateGE="+investDateGE;
			map.put("investDateGE",investDateGE);
		}else{
			//sqlCondition=sqlCondition+"&investDateGE="+sdf.format(new Date());
			map.put("investDateGE",investDateGE);
		}
		if(null!=investDateLE && !"".equals(investDateLE)){
			//sqlCondition=sqlCondition+"&investDateLE="+investDateLE;
			map.put("investDateLE",investDateLE);
		}else{
			map.put("investDateLE",sdf.format(new Date()));
			//sqlCondition=sqlCondition+"&investDateLE="+sdf.format(new Date());
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
}
