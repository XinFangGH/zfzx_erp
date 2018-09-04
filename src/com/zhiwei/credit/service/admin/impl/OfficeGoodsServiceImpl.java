package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.admin.OfficeGoodsDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.model.admin.OfficeGoods;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.admin.OfficeGoodsService;
import com.zhiwei.credit.service.info.ShortMessageService;

public class OfficeGoodsServiceImpl extends BaseServiceImpl<OfficeGoods> implements OfficeGoodsService{
	private static Log logger=LogFactory.getLog(OfficeGoodsServiceImpl.class);
	private OfficeGoodsDao dao;
	@Resource
	private AppUserDao appUserDao;
	@Resource
	private ShortMessageService shortMessageService;
	
	public OfficeGoodsServiceImpl(OfficeGoodsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void sendWarmMessage() {
		List<OfficeGoods> list=dao.findByWarm();
		if(list.size()>0){
			StringBuffer sb=new StringBuffer("办公用品：");
			for(OfficeGoods goods:list){
			   if(goods.getIsWarning()==1){
				 sb.append(goods.getGoodsName()+"已经低于警报库存量"+goods.getWarnCounts()+"了.");
			   }else{
				 sb.append(goods.getGoodsName()+"已经没有库存了.");
			   }
			}
			sb.append("请尽快购买相应的用品");
		Map map=AppUtil.getSysConfig();
		String username=(String)map.get("goodsStockUser");
		if(StringUtils.isNotEmpty(username)){
			AppUser user=appUserDao.findByUserName(username);
			if(user!=null){
				shortMessageService.save(AppUser.SYSTEM_USER,user.getUserId().toString(), sb.toString(),ShortMessage.MSG_TYPE_SYS);
				logger.info("messages had sent to the manager!"+user.getUsername());
			}else{
				logger.info("can not find the user in the system.");
			}
		}else{
			logger.info("can not find the name in the map.");
		}
		logger.info(sb.toString());
		}else{
			logger.info("没有产品要补仓.");
		}
	
	}
}