package com.webServices.services.IZwServices.impl;

import com.webServices.services.IZwServices.ILoanWS;
import com.webServices.services.datasend.DataSend;
import com.webServices.services.md.msgmd.ZwBankErroMd;
import com.webServices.services.md.zwmd.ZwMd;
import com.webServices.services.zwSerDao.ZwInfo;
import com.webServices.services.zwSerDao.impl.ZwInfoImpl;

public class ILoanWSImpl implements ILoanWS {
	private DataSend dataSend = new DataSend();
	private ZwInfo zwInfo = new ZwInfoImpl();
/*
	@Override
	public String[] bankStrm(ZwMd zwMd, ZwBankStrmMd[] bankSMdArr) {

		String[] ret = new String[3];
		if (zwInfo.userIsTrue(zwMd)) {

			if (bankSMdArr.length > 0) {
				try {
					for (int i = 0; i < bankSMdArr.length; i++) {
						//bankInfoErro.add(bankSMdArr[i]);
					}
					ret[0] = "Y";
					ret[1] = "流水编号:12345670";
					ret[2] = "保存成功！";

				} catch (Exception e) {
					e.getStackTrace();
					ret[0] = "N";
					ret[1] = "流水编号:1234567";
					ret[2] = "保存失败！";
				}
			}
		}
		return ret;
	}*/

	@Override
	public String[] sendErrMsg(ZwMd zwMd, ZwBankErroMd[] erroMdArr) {
		String[] ret = new String[2];
		try {
			if (zwInfo.userIsTrue(zwMd)) {
				
				if(dataSend.addErroMd(erroMdArr)){
					ret[0] = "Y";
					ret[1] = "已收到，等待处理中……";
					
				}else{
					ret[0] = "N";
					ret[1] = "贷款编号错误！";
				}
			}
		} catch (Exception e) {
			ret[0] = "N";
			ret[1] = "贷款编号错误！";
		}
		return ret;
	}

}
