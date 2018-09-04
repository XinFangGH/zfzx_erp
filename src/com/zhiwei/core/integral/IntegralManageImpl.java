package com.zhiwei.core.integral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.setting.WebBonusSettingDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.creditFlow.bonusSystem.WebBonusConstant;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;
import com.zhiwei.credit.model.creditFlow.bonusSystem.record.WebBonusRecord;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet.MemberGradeSetService;
import com.zhiwei.credit.service.creditFlow.bonusSystem.record.WebBonusRecordService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.util.MyUserSession;

public class IntegralManageImpl implements IntegralManage {
	

	
	@Override
	public boolean addScore(Long userId,Long score,String settingFlag) {
		BpCustMemberService bpCustMemberService =(BpCustMemberService) AppUtil.getBean("bpCustMemberService");
		BpCustMemberDao bpCustMemberDao =(BpCustMemberDao) AppUtil.getBean("bpCustMemberDao");
		WebBonusRecordService webBonusRecordService =(WebBonusRecordService) AppUtil.getBean("webBonusRecordService");
		WebBonusSettingDao webBonusSettingDao = (WebBonusSettingDao) AppUtil.getBean("webBonusSettingDao");
		Long settingId = null ;
		WebBonusSetting webBonusSetting = null;
		try {//捕捉非积分规则标识
			settingId = Long.valueOf(settingFlag);
		} catch (Exception e) {
		}
		if(settingId!=null){
			webBonusSetting = webBonusSettingDao.get(settingId);
		}
		
		
		
		//判断是否大于0
		int compareTo = score.compareTo(Long.valueOf(0));
		//收支方法
		String director = compareTo>0?WebBonusConstant.DIRECTION_ADD:WebBonusConstant.DIRECTION_REDUCE;
		try {
			synchronized (obj) {
				BpCustMember bpCustMember = bpCustMemberService.get(userId);
				if(bpCustMember.getScore()==null||bpCustMember.getScore().compareTo(new Long(0))==0){
					bpCustMember.setScore(score);
				}else{
					bpCustMember.setScore(new Long((bpCustMember.getScore().intValue()+score.intValue())));
				}
			//	BpCustMember save = bpCustMemberService.save(bpCustMember);
				bpCustMemberDao.executeSql(" UPDATE bp_cust_member SET score = "+bpCustMember.getScore()+" WHERE id = "+bpCustMember.getId()+" ");
				
				//保存积分记录
				WebBonusRecord webBonusRecord = new WebBonusRecord();
				//用户ID
				webBonusRecord.setCustomerId(bpCustMember.getId().toString());
				//用户名称
//				if(bpCustMember.getTruename()!=null&&!"".equals(bpCustMember.getTruename())){
//					webBonusRecord.setCustomerName(bpCustMember.getTruename());
//				}else{
					webBonusRecord.setCustomerName(bpCustMember.getLoginname());
//				}
				//收支方向
				webBonusRecord.setRecordDirector(director);
				webBonusRecord.setRecordNumber(score);
				
				
				//积分详细值
				webBonusRecord.setRecordNumber(score);
				//积分系统日志
				webBonusRecord.setRecordMsg((director.equals("1")?"增加":"减少")+"【"+score.toString()+"】积分,当前总积分为：【"+bpCustMember.getScore().toString()+"】");
				//用户当前积分
				webBonusRecord.setTotalNumber(bpCustMember.getScore());
				
				//如果不是通过积分规则计算的积分
				if(webBonusSetting==null){
					//普通积分规则--ID
					webBonusRecord.setBonusId(settingFlag);
					//积分记录
					webBonusRecord.setBonusKey(settingFlag);
				}else{
					
//					protected String operationType;//积分类型   
//					protected String bonusKey;//      //积分规则的Key值 --操作类型
//					protected String bonusIntention;  //积分用意
//					protected String bonusDescription; //积分规则说明
					
					//普通积分规则--ID
					webBonusRecord.setBonusId(settingFlag);
					//积分规则的Key值 --操作类型
					webBonusRecord.setBonusKey(webBonusSetting.getFlagKey());
					//积分类型
					webBonusRecord.setOperationType(webBonusSetting.getBonusType());
					//积分用意
					webBonusRecord.setBonusIntention(webBonusSetting.getBonusIntention());
					//积分规则说明
					webBonusRecord.setBonusDescription(webBonusSetting.getDescription());
					
				}
				
			//	protected String activityNumber; //活动编号
				
				webBonusRecordService.save(webBonusRecord);
				
			}
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}
	
	
	
	
	@Override
	public boolean addSocreBpActivityManage(Long userId,Long score,BpActivityManage bpActivityManage) {
		BpCustMemberService bpCustMemberService =(BpCustMemberService) AppUtil.getBean("bpCustMemberService");
		BpCustMemberDao bpCustMemberDao =(BpCustMemberDao) AppUtil.getBean("bpCustMemberDao");
		WebBonusRecordService webBonusRecordService =(WebBonusRecordService) AppUtil.getBean("webBonusRecordService");
	//	DictionaryService dictionaryService =(DictionaryService) AppUtil.getBean("dictionaryService");
	//	Dictionary dictionary = dictionaryService.get(bpActivityManage.getSendType());
		
		//判断是否大于0
		int compareTo = score.compareTo(Long.valueOf(0));
		//收支方法
		String director = compareTo>0?WebBonusConstant.DIRECTION_ADD:WebBonusConstant.DIRECTION_REDUCE;
		//保存积分记录
		WebBonusRecord webBonusRecord = new WebBonusRecord();
		try {
			synchronized (obj) {
				BpCustMember bpCustMember = bpCustMemberService.get(userId);
				if(compareTo<0){
					score=-score;
				}
				if(bpCustMember.getScore()==null||bpCustMember.getScore().compareTo(new Long(0))==0){
					bpCustMember.setScore(score);
				}else if(WebBonusConstant.DIRECTION_ADD.equals(director)){//增加---
					bpCustMember.setScore(new Long((bpCustMember.getScore().intValue()+score.intValue())));
				}else if(WebBonusConstant.DIRECTION_REDUCE.equals(director)){//减少---
					bpCustMember.setScore(new Long((bpCustMember.getScore().intValue()-score.intValue())));
				}
			//	BpCustMember save = bpCustMemberService.save(bpCustMember);
				bpCustMemberDao.executeSql(" UPDATE bp_cust_member SET score = "+bpCustMember.getScore()+" WHERE id = "+bpCustMember.getId()+" ");
				
				
				//用户ID
				webBonusRecord.setCustomerId(bpCustMember.getId().toString());
				//用户名称
				if(bpCustMember.getTruename()!=null&&!"".equals(bpCustMember.getTruename())){
					webBonusRecord.setCustomerName(bpCustMember.getTruename());
				}else{
					webBonusRecord.setCustomerName(bpCustMember.getLoginname());
				}
				//收支方向
				webBonusRecord.setRecordDirector(director);
				
				
				//积分详细值
				webBonusRecord.setRecordNumber(score);
				//积分系统日志
				webBonusRecord.setRecordMsg((director.equals("1")?"增加":"减少")+"【"+score.toString()+"】积分,当前总积分为：【"+bpCustMember.getScore().toString()+"】");
				//用户当前积分
				webBonusRecord.setTotalNumber(bpCustMember.getScore());
					
				//普通积分规则--ID
				webBonusRecord.setBonusId(bpActivityManage.getActivityId().toString());
				//积分规则的Key值 --操作类型
				if(bpActivityManage.getSendType()!=null){
					webBonusRecord.setBonusKey(bpActivityManage.getSendType().toString());
				}
				//积分类型
				webBonusRecord.setOperationType("2");
				//积分用意
				webBonusRecord.setBonusIntention(bpActivityManage.findSendType());
				//积分规则说明
				webBonusRecord.setBonusDescription(bpActivityManage.getActivityExplain());
				//活动编号
				webBonusRecord.setActivityNumber(bpActivityManage.getActivityNumber().toString());
				
				webBonusRecordService.save(webBonusRecord);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			webBonusRecordService.remove(webBonusRecord);
			return false;
		}
		return true;
		
	}

	
	
	
	
	
	
	

	@Override
	public void integralEngine(String userAction, String userActionKey) {
		WebBonusSettingDao webBonusSettingDao = (WebBonusSettingDao) AppUtil.getBean("webBonusSettingDao");
		Map<String, Object> session = ActionContext.getContext().getSession();
		Object object = session.get(MyUserSession.MEMBEER_SESSION);
		if(object==null){
			return ;
		}
		BpCustMember bpCustMember = (BpCustMember) object;
		//1丶如果session不存在登录用户信息则直接返回，不做后续操作
	
		//2丶查找是否有对应方法名规则,如果没有则直接返回
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("userAction", userAction.substring(userAction.lastIndexOf(".")+1));
		map.put("userActionKey", userActionKey);
		List<WebBonusSetting> list = webBonusSettingDao.excludeALike(map);
		if(list==null||list.size()<1){
			return ;
		}
		
		//3丶将规则进行计算，得出应该加多少分
		addScordBySetting(bpCustMember.getId(), list.get(0));
		
	}

	@Override
	public boolean addScordByMoney(Long userId, BigDecimal money,BigDecimal rate) {
		try {
			BigDecimal divide = money.divide(rate).setScale(0,BigDecimal.ROUND_HALF_UP);
			return addScore(userId, divide.longValue(),"addScordByMoney");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addScordBySetting(Long userId,WebBonusSetting webBonusSetting) {
		
		//一丶如果规则开关为关闭 ,返回false
		if("1".equals(webBonusSetting.getBonusswitch())){
			return false;
		}
		//二丶判断积分等级达不达标
		BpCustMemberService bpCustMemberService =(BpCustMemberService) AppUtil.getBean("bpCustMemberService");
		BpCustMember bpCustMember = bpCustMemberService.get(userId);
		//计算积分等级开始
		MemberGradeSetService memberGradeSetService =(MemberGradeSetService) AppUtil.getBean("memberGradeSetService");
		
		//如果等级要求不是无
		if(!"0".equals(webBonusSetting.getMemberLevel())){
			MemberGradeSet memberGradeSet = memberGradeSetService.get(Long.valueOf(webBonusSetting.getMemberLevel()));
			
			Long bpScore = null;
			if(bpCustMember.getScore()!=null){
				bpScore = bpCustMember.getScore();
			}else {
				bpScore = new Long(0);
			}
			//如果用户的积分小于积分规则要求的会员等级所对应的分数，则返回
			if(bpScore.compareTo(Long.valueOf(memberGradeSet.getLevelMinBonus() ))<0){
				return false;
			}
			
		}
		//计算积分等级结束
		
		//三丶符合条件后进行对积分规则的计算是否要加分
		WebBonusRecordService webBonusRecordService = (WebBonusRecordService) AppUtil.getBean("webBonusRecordService");
		Long score = webBonusRecordService.findBySetting(userId,webBonusSetting);
		if(score.compareTo(Long.valueOf(0))!=0){
			//增加积分
			addScore(userId, score, webBonusSetting.getBonusId().toString());
		}
		
		return true;
	}

	@Override
	public boolean addScordByFlagKey(Long userId, String flagKey) {
		
		
		WebBonusSettingDao webBonusSettingDao = (WebBonusSettingDao) AppUtil.getBean("webBonusSettingDao");
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("flagKey", flagKey);
		//查出所有相同的FlagKey值
		List<WebBonusSetting> listSetting = webBonusSettingDao.excludeALike(map);
		//带会员积分的list
		ArrayList<WebBonusSetting> arrList = new ArrayList<WebBonusSetting>();
		//积分规则找对应积分
		if(listSetting!=null&&listSetting.size()>0){
			MemberGradeSetService memberGradeSetService =(MemberGradeSetService) AppUtil.getBean("memberGradeSetService");
			for(int i = 0 ; i<listSetting.size() ; i++){
				WebBonusSetting webBonusSetting = listSetting.get(i);
				webBonusSettingDao.evict(webBonusSetting);
				//如果会员要求不是无则进行查找会员要求所对应的分数
				if(!"0".equals(webBonusSetting.getMemberLevel())){
					MemberGradeSet memberGradeSet = memberGradeSetService.get(Long.valueOf(webBonusSetting.getMemberLevel()));
					webBonusSetting.setMemberLevelValue(memberGradeSet.getLevelMinBonus().toString());
					arrList.add(webBonusSetting);
				}else{//否则无要求直接设置为0
					webBonusSetting.setMemberLevelValue("0");
					arrList.add(webBonusSetting);
				}
			}
		}
		
		//List排序---按会员积分倒序排序   ----排序方式，选择排序
		//排序后的List
		for(int i=0 ; i < arrList.size()-1 ; i++){
			for(int j=i+1 ; j<arrList.size(); j++ ){
				WebBonusSetting temp = arrList.get(i);
				WebBonusSetting nextTemp = arrList.get(j);
				if(Integer.valueOf(temp.getMemberLevelValue()).compareTo(Integer.valueOf(nextTemp.getMemberLevelValue()))<0){
					arrList.set(i, nextTemp);
					arrList.set(j, temp);
				}
			}
		}
		//排序完成
		
		//查出会员积分，并且匹配最接近的积分规则,且积分规则已开启
		WebBonusSetting webBonusSetting = null;
		BpCustMemberService bpCustMemberService =(BpCustMemberService) AppUtil.getBean("bpCustMemberService");
		BpCustMember bpCustMember = bpCustMemberService.get(userId);
		bpCustMemberService.evict(bpCustMember);
		if(bpCustMember.getScore()==null){
			bpCustMember.setScore(Long.valueOf(0));
		}
		for(WebBonusSetting setting : arrList){
			//如果用户积分大于等于  并且  积分规则已开启则 返回这个规则
			if((bpCustMember.getScore().compareTo(Long.valueOf(setting.getMemberLevelValue()))>=0)
			   &&"0".equals(setting.getBonusswitch())
			){
				webBonusSetting = setting;
				break;
			}	
		}
		//如果积分规则不等于空,否则返回false
		if(webBonusSetting!=null){
			//查出积分规则后进行积分规则计算加分
			return addScordBySetting(userId, webBonusSetting);
		}
		
		return false;
		
		
	}

}
