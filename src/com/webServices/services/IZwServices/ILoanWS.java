package com.webServices.services.IZwServices;

import com.webServices.services.md.bankmd.ZwBankStrmMd;
import com.webServices.services.md.msgmd.ZwBankErroMd;
import com.webServices.services.md.zwmd.ZwMd;

public interface ILoanWS {
	String[] sendErrMsg(ZwMd zwMd,ZwBankErroMd[] erroMdArr);
	//String[] bankStrm(ZwMd zwMd,ZwBankStrmMd[] bankSMdArr);
}
