package com.zhiwei.credit.service.thirdInterface.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.thirdInterface.PlThirdInterfaceLogDao;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;

/**
 * 
 * @author 
 *
 */
public class PlThirdInterfaceLogServiceImpl extends BaseServiceImpl<PlThirdInterfaceLog> implements PlThirdInterfaceLogService{
	@SuppressWarnings("unused")
	private PlThirdInterfaceLogDao dao;
	
	public PlThirdInterfaceLogServiceImpl(PlThirdInterfaceLogDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void saveLog(String code, String msg, String bigMsg,
			String interfaceName, Long memberId, int memberType, Long typeId,
			String typeName,String userName,String rem1,String rem2,String rem3) {
		try {
			PlThirdInterfaceLog plThirdInterfaceLog=new PlThirdInterfaceLog();
			plThirdInterfaceLog.setBigMsg(bigMsg);
			plThirdInterfaceLog.setCode(code);
			plThirdInterfaceLog.setCodeMsg(msg);
			plThirdInterfaceLog.setCreateTime(new Date());
			plThirdInterfaceLog.setInterfaceName(interfaceName);
			plThirdInterfaceLog.setMemberId(memberId);
			plThirdInterfaceLog.setMemberType(memberType);
			plThirdInterfaceLog.setTypeId(typeId);
			plThirdInterfaceLog.setTypeName(typeName);
			plThirdInterfaceLog.setMemberName(userName);
			plThirdInterfaceLog.setRemark1(rem1);
			plThirdInterfaceLog.setRemark2(rem2);
			plThirdInterfaceLog.setRemark3(rem3);
			dao.save(plThirdInterfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public PlThirdInterfaceLog getByOrdId(String orderNo) {
		return dao.getByOrdId(orderNo);
	}

	@Override
	public PlThirdInterfaceLog getByLoanOrdId(String orderNo) {
		return dao.getByLoanOrdId(orderNo);
	}

	
	//首先添加一条信息
	//svn：songwj
	@Override
	public String saveLog1(String code, String msg, String bigMsg,
			String interfaceName, Long memberId, int memberType, Long typeId,
			String typeName, String userName, String rem1, String rem2,
			String rem3) {
		String idStr ="";
		try {
			PlThirdInterfaceLog plThirdInterfaceLog = new PlThirdInterfaceLog();
	 
			plThirdInterfaceLog.setBigMsg(bigMsg);
			plThirdInterfaceLog.setCode(code);
			plThirdInterfaceLog.setCodeMsg(msg);
			plThirdInterfaceLog.setCreateTime(new Date());
			plThirdInterfaceLog.setInterfaceName(interfaceName);
			plThirdInterfaceLog.setMemberId(memberId);
			plThirdInterfaceLog.setMemberType(memberType);
			plThirdInterfaceLog.setTypeId(typeId);
			plThirdInterfaceLog.setTypeName(typeName);
			plThirdInterfaceLog.setMemberName(userName);
			plThirdInterfaceLog.setRemark1(rem1);
			plThirdInterfaceLog.setRemark2(rem2);
			plThirdInterfaceLog.setRemark3(rem3);
			PlThirdInterfaceLog aa = dao.save(plThirdInterfaceLog);
		    idStr = aa.getId().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idStr;
	}
	
	//更新日志
	//svn：songwj
	@Override
	public void updateLog(String code, String msg, String bigMsg,
			String interfaceName, Long memberId, int memberType, Long typeId,
			String typeName, String userName, String rem1, String rem2,
			String rem3, String id) {
		PlThirdInterfaceLog plThirdInterfaceLog = new PlThirdInterfaceLog();
		System.out.println("list.get(0).getId()===="+Long.valueOf(id));
		
		plThirdInterfaceLog.setId(Long.valueOf(id));
		plThirdInterfaceLog.setBigMsg(bigMsg);
		plThirdInterfaceLog.setCode(code);
		plThirdInterfaceLog.setCodeMsg(msg);
		plThirdInterfaceLog.setCreateTime(new Date());
		plThirdInterfaceLog.setInterfaceName(interfaceName);
		plThirdInterfaceLog.setMemberId(memberId);
		plThirdInterfaceLog.setMemberType(memberType);
		plThirdInterfaceLog.setTypeId(typeId);
		plThirdInterfaceLog.setTypeName(typeName);
		plThirdInterfaceLog.setMemberName(userName);
		plThirdInterfaceLog.setRemark1(rem1);
		plThirdInterfaceLog.setRemark2(rem2);
		plThirdInterfaceLog.setRemark3(rem3);
		dao.merge(plThirdInterfaceLog);
	}

}
