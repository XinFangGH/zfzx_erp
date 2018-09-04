package com.webServices.services.factory.modelfactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import nc.vo.crd.bd.interf.zyhtvo.ZyhtVO;

import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;

public class ZyVo implements ZyhtVoFactory {
	private  ZyhtVO zyVO = ZyhtVO.Factory.newInstance();
	public ZyVo(){};
	@Override
	public ZyhtVO createVo(int num) {
			zyVO.setAppcode("001");
			zyVO.setPassword("1");
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			zyVO.setDate(sd.format(new Date()));
			zyVO.setNum(num);
		   return zyVO;
	}
	@Override
	public ZyhtVO createVo(String corpno, String dateStr,int num) {
		zyVO.setAppcode("001");
		zyVO.setPassword("1");
		zyVO.setCorpno(corpno);
		zyVO.setDate(dateStr);
		zyVO.setNum(num);
	   return zyVO;
	}
	@Override
	public ZyhtVO createVo(String corpno, String dateStart, String dateEnd) {
		zyVO.setAppcode("001");
		zyVO.setPassword("1");
		zyVO.setCorpno(corpno);
		zyVO.setDateStart(dateStart);
		zyVO.setDateEnd(dateEnd);
		return zyVO;
	}

}
