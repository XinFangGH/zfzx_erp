package com.zhiwei.credit.service.creditFlow.bonusSystem.setting.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.setting.WebBonusSettingDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;
import com.zhiwei.credit.service.creditFlow.bonusSystem.setting.WebBonusSettingService;

/**
 * 
 * @author 
 *
 */
public class WebBonusSettingServiceImpl extends BaseServiceImpl<WebBonusSetting> implements WebBonusSettingService{
	@SuppressWarnings("unused")
	private WebBonusSettingDao dao;
	
	public WebBonusSettingServiceImpl(WebBonusSettingDao dao) {
		super(dao);
		this.dao=dao;
	}


	@Override
	public void initDate() {
		
		//初始化一条登录涨积分规则
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("flagKey", "Login");
		List<WebBonusSetting> Login = dao.excludeALike(map);
		if(Login==null||Login.size()==0){
			System.out.println("***********初始化登录积分规则信息*************");
			WebBonusSetting w = new WebBonusSetting();
			w.setUserAction("LoginAction");
			w.setBonusType("1");
			w.setBonus("1");
			w.setIsBonus("1");
			w.setUserActionKey("to");
			w.setBomusPeriod("1");
			w.setPeriodBonusLimit("1");
			w.setMemberLevel("0");
			w.setBomusPeriodType("date");
			w.setBonusswitch("1");
			w.setBonusIntention("登录");
			w.setFlagKey("Login");
			w.setDescription("登录一天涨一分，无会员等级要求");
			dao.save(w);
		}
		
		//初始化一条注册涨积分规则
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("flagKey", "register");
		List<WebBonusSetting> list1 = dao.excludeALike(map1);
		if(list1==null||list1.size()==0){
			System.out.println("***********初始化注册积分规则信息*************");
			WebBonusSetting w = new WebBonusSetting();
			w.setUserAction("register");
			w.setBonusType("1");
			w.setBonus("1");
			w.setIsBonus("1");
			w.setUserActionKey("to");
			w.setBomusPeriod("1");
			w.setPeriodBonusLimit("1");
			w.setMemberLevel("0");
			w.setBomusPeriodType("once");
			w.setBonusswitch("1");  //关闭
			w.setBonusIntention("注册");
			w.setFlagKey("register");
			w.setDescription("注册送一积分");
			dao.save(w);
		}
		
		
		//初始化一条投标涨积分规则
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("flagKey", "tender");
		List<WebBonusSetting> list2 = dao.excludeALike(map2);
		if(list2==null||list2.size()==0){
			System.out.println("***********初始化投标积分规则信息*************");
			WebBonusSetting w = new WebBonusSetting();
			w.setUserAction("tender");
			w.setBonusType("1");
			w.setBonus("1");
			w.setIsBonus("1");
			w.setUserActionKey("to");
			w.setBomusPeriod("1");
			w.setPeriodBonusLimit("1");
			w.setMemberLevel("0");
			w.setBomusPeriodType("once");
			w.setBonusswitch("1");  //关闭
			w.setBonusIntention("投标");
			w.setFlagKey("tender");
			w.setDescription("投标送一积分");
			dao.save(w);
		}
		
		//初始化一条充值涨积分规则
		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("flagKey", "recharge");
		List<WebBonusSetting> list3 = dao.excludeALike(map3);
		if(list3==null||list3.size()==0){
			System.out.println("***********初始化充值积分规则信息*************");
			WebBonusSetting w = new WebBonusSetting();
			w.setUserAction("recharge");
			w.setBonusType("1");
			w.setBonus("1");
			w.setIsBonus("1");
			w.setUserActionKey("to");
			w.setBomusPeriod("1");
			w.setPeriodBonusLimit("1");
			w.setMemberLevel("0");
			w.setBomusPeriodType("once");
			w.setBonusswitch("1");  //关闭
			w.setBonusIntention("充值");
			w.setFlagKey("recharge");
			w.setDescription("充值送一积分");
			dao.save(w);
		}
		
		//初始化一条推荐涨积分规则
		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put("flagKey", "invite");
		List<WebBonusSetting> list4 = dao.excludeALike(map4);
		if(list4==null||list4.size()==0){
			System.out.println("***********初始化推荐积分规则信息*************");
			WebBonusSetting w = new WebBonusSetting();
			w.setUserAction("invite");
			w.setBonusType("1");
			w.setBonus("1");
			w.setIsBonus("1");
			w.setUserActionKey("to");
			w.setBomusPeriod("1");
			w.setPeriodBonusLimit("1");
			w.setMemberLevel("0");
			w.setBomusPeriodType("once");
			w.setBonusswitch("1");  //关闭
			w.setBonusIntention("推荐");
			w.setFlagKey("invite");
			w.setDescription("推荐送一积分");
			dao.save(w);
		}
		
		
		//初始化一条推荐第一次投标涨积分规则
		HashMap<String, String> map5 = new HashMap<String, String>();
		map5.put("flagKey", "inviteOnce");
		List<WebBonusSetting> list5 = dao.excludeALike(map5);
		if(list5==null||list5.size()==0){
			System.out.println("***********初始化推荐第一次投标积分规则信息*************");
			WebBonusSetting w = new WebBonusSetting();
			w.setUserAction("inviteOnce");
			w.setBonusType("1");
			w.setBonus("1");
			w.setIsBonus("1");
			w.setUserActionKey("to");
			w.setBomusPeriod("1");
			w.setPeriodBonusLimit("1");
			w.setMemberLevel("0");
			w.setBomusPeriodType("once");
			w.setBonusswitch("1");  //关闭
			w.setBonusIntention("推荐第一次投标");
			w.setFlagKey("inviteOnce");
			w.setDescription("推荐第一次投标送一积分");
			dao.save(w);
		}
		
	}

	@Override
	public WebBonusSetting excludeALike(Map<String, String> queryMap) {
		
		List<WebBonusSetting> list = dao.excludeALike(queryMap);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
		
	}


	@Override
	public WebBonusSetting hasWebBonusSetting(String userAction,String userActionKey, String memberLevel) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("userAction", userAction);
		map.put("userActionKey", userActionKey);
		map.put("memberLevel", memberLevel);
		List<WebBonusSetting> list = dao.excludeALike(map);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}




}