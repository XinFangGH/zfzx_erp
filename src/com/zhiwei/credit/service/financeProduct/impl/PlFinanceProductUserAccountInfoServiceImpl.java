package com.zhiwei.credit.service.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductRateDao;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductUserAccountInfoDao;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductUseraccountDao;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUserAccountInfo;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductUserAccountInfoService;

/**
 * 
 * @author 
 *
 */
public class PlFinanceProductUserAccountInfoServiceImpl extends BaseServiceImpl<PlFinanceProductUserAccountInfo> implements PlFinanceProductUserAccountInfoService{
	@SuppressWarnings("unused")
	private PlFinanceProductUserAccountInfoDao dao;
	@Resource
	private PlFinanceProductUseraccountDao plFinanceProductUserAccountDao;
	@Resource
	private PlFinanceProductRateDao plFinanceProductRateDao;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	
	public PlFinanceProductUserAccountInfoServiceImpl(PlFinanceProductUserAccountInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlFinanceProductUserAccountInfo> getListByParamet(
			HttpServletRequest request, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getListByParamet(request,pb);
	}

	//正常生成每日派息数据(不考虑异常情况)
	@Override
	public boolean creatYestDayIntent(Date date) {
		try{
			//更新用户的在途金额的状态
			this.updateAccountInfo();
			//添加当日的年化利率
			plFinanceProductRateDao.addNowDayRate(date);
			if(date==null){
				date=DateUtil.addDaysToDate(new Date(), -1);
			}
			List<PlFinanceProductUseraccount> list=plFinanceProductUserAccountDao.getUserAccountList(null,null);
			 //跑批记录 
			AppUser appUser = ContextUtil.getCurrentUser();
			String pushUserName = "定时跑批";
			Long pushUserId = null;
			if(null != appUser && !"".equals(appUser)){
				pushUserName = appUser.getFullname();
				pushUserId = appUser.getUserId();
			}
			BatchRunRecord batchRunRecord = new BatchRunRecord();
			batchRunRecord.setRunType(BatchRunRecord.HRY_1004);
			batchRunRecord.setAppUserId(pushUserId);
			batchRunRecord.setAppUserName(pushUserName);
			batchRunRecord.setStartRunDate(new Date());
			batchRunRecord.setTotalNumber(Long.valueOf(list != null ?list.size() : 0));
			batchRunRecord.setHappenAbnorma("正常");
			
		    if(list!=null&&list.size()>0){
		    	for(PlFinanceProductUseraccount temp:list){
		    		try {
		    			if(temp.getIntestMoney().compareTo(new BigDecimal(0))>0){
			    			//获得该产品的当日利率
			    			PlFinanceProductRate prate=plFinanceProductRateDao.getRate(temp.getProductId(),date);
			    			this.doUserAccountInfo(prate,temp,date,null);
			    		}
					} catch (Exception e) {
						String ids="";
						if(null != batchRunRecord.getIds()){
							ids =batchRunRecord.getIds() + "," + temp.getId();
						}else{
							ids = temp.getId().toString();
						}
						batchRunRecord.setIds(ids);
						batchRunRecord.setHappenAbnorma("异常");
						e.printStackTrace();
					}
		    		
		    	}
		    }
		    batchRunRecord.setEndRunDate(new Date());
			batchRunRecordService.save(batchRunRecord);
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	//补派昨日之前任意一天的利息
	@Override
	public Boolean creatBeforeIntentRecord(String date) {
		try{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			//更新用户的在途金额的状态
			this.updateAccountInfo();
			//添加当日的年化利率
			Date dates=sd.parse(date);
			plFinanceProductRateDao.addNowDayRate(dates);
			Map<String,String> request=new HashMap<String,String>();
			request.put("Intentday", date);
			List<PlFinanceProductUseraccount> list=plFinanceProductUserAccountDao.getUserAccountList(request,null);
		    if(list!=null&&list.size()>0){
		    	for(PlFinanceProductUseraccount temp:list){
		    		System.out.println(temp.getIntestMoney());
		    		if(temp.getIntestMoney().compareTo(new BigDecimal(0))>0){
		    			//获得该产品的当日利率
		    			PlFinanceProductRate prate=plFinanceProductRateDao.getRate(temp.getProductId(),dates);
		    			this.doUserAccountInfo(prate,temp,dates,"before");
		    		}
		    	}
		    }
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 生成计息记录
	 * @param prate
	 * @param temp
	 */
	private void doUserAccountInfo(PlFinanceProductRate prate, PlFinanceProductUseraccount temp,Date date,String intentType) {
		// TODO Auto-generated method stub
		try{
			if(prate!=null){
				BigDecimal intestMoney=temp.getIntestMoney().multiply(prate.getYearRate()).divide(new BigDecimal("36500"),2,BigDecimal.ROUND_HALF_UP);
				//判断派息金额是否小于零
				if(intestMoney.compareTo(new BigDecimal(0))>0){
					//判断是否生成了派息记录
					Boolean flag=dao.getRecord(temp.getUserId(),prate.getProductId(),date);
					if(flag){
						Map<String,String> request=new HashMap<String,String>();
						request.put("userId", temp.getUserId().toString());
						List<PlFinanceProductUseraccount> list=plFinanceProductUserAccountDao.getUserAccountList(request,null);
						String requestNo=ContextUtil.createRuestNumber();
						PlFinanceProductUserAccountInfo pfui=new PlFinanceProductUserAccountInfo();
						pfui.setUserId(temp.getUserId());
						pfui.setProductId(prate.getProductId());
						pfui.setYearRate(prate.getYearRate());
						pfui.setAmont(intestMoney);
						pfui.setDealtype("3");
						pfui.setDealtypeName("收益");
						pfui.setDealStatus(Short.valueOf("2"));
						pfui.setDealStatusName("正常交易");
						pfui.setDealDate(prate.getIntentDate());
						pfui.setIntentDate(new Date());
						if(intentType!=null){
							pfui.setRemark("补派之前的产品派息");
							pfui.setUpdateDate(new Date());
						}else{
							pfui.setRemark("产品派息");
						}
						pfui.setCurrentMoney(list.get(0).getCurrentMoney().add(intestMoney));
						pfui.setRequestNo(requestNo+prate.getProductId()+temp.getUserId());
						pfui.setCompanyId(1L);
						dao.save(pfui);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	//@Override
	public void updateAccountInfo(){
		dao.updateAccountInfo();
	}

	@Override
	public void creatYestDayIntent() {
		// TODO Auto-generated method stub
		creatYestDayIntent(null);
	}


}