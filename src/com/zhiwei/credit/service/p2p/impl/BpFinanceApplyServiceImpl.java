package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;

import javax.jws.WebService;

import jodd.util.URLDecoder;

import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.BpFinanceApplyDao;
import com.zhiwei.credit.model.p2p.BpFinanceApply;
import com.zhiwei.credit.service.p2p.BpFinanceApplyService;

/**
 * 
 * @author 
 *
 */
@WebService(targetNamespace="http://p2p.service.credit.zhiwei.com/", endpointInterface = "com.zhiwei.credit.service.p2p.BpFinanceApplyService") 
public class BpFinanceApplyServiceImpl extends BaseServiceImpl<BpFinanceApply> implements BpFinanceApplyService{
	@SuppressWarnings("unused")
	private BpFinanceApplyDao dao;
	
	public BpFinanceApplyServiceImpl(BpFinanceApplyDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String addFinanceApply(String productId, String linkPersion,
			String phone, String loanMoney, String isOnline,
			String loanTimeLen, String area, String remark,String productName) {
		String ret="";
		try{
			
			
		BpFinanceApply bpFinanceApply=new BpFinanceApply();
		bpFinanceApply.setArea(StringUtil.stringURLDecoderByUTF8(area));
		bpFinanceApply.setCreateTime(new Date());
		bpFinanceApply.setIsOnline(Short.valueOf(isOnline));
		bpFinanceApply.setLinkPersion(StringUtil.stringURLDecoderByUTF8(linkPersion));
		bpFinanceApply.setLoanMoney(new BigDecimal(loanMoney));
		bpFinanceApply.setLoanTimeLen(StringUtil.stringURLDecoderByUTF8(loanTimeLen));
		bpFinanceApply.setPhone(StringUtil.stringURLDecoderByUTF8(phone));
		bpFinanceApply.setProductId(Long.valueOf(productId));
		bpFinanceApply.setState(Short.valueOf("0"));
		bpFinanceApply.setRemark(StringUtil.stringURLDecoderByUTF8(remark));
		bpFinanceApply.setProductName(StringUtil.stringURLDecoderByUTF8(productName));
		dao.save(bpFinanceApply);
		ret="success";
		}catch (Exception e) {
			ret="faild";
		}
		return ret;
	}

}