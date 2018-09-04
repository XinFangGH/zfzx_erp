package com.zhiwei.credit.service.pay.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.pay.BankCodeDao;
import com.zhiwei.credit.model.pay.BankCode;
import com.zhiwei.credit.service.pay.BankCodeService;

/**
 * 
 * @author 
 *
 */
public class BankCodeServiceImpl extends BaseServiceImpl<BankCode> implements BankCodeService{
	@SuppressWarnings("unused")
	private BankCodeDao dao;
	
	public BankCodeServiceImpl(BankCodeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Map<String, String> getAreaList(String ParentCode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		List<BankCode> list = dao.getAreaList(ParentCode);
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				BankCode bankCode = list.get(i);
				map.put(bankCode.getCode(), bankCode.getName());
			}
		}
		
		return map;
	}

	@Override
	public BankCode getBycityName(String cityname, String thirdPayConfig) {
		// TODO Auto-generated method stub
		return dao.getBycityName(cityname,thirdPayConfig);
	}

}