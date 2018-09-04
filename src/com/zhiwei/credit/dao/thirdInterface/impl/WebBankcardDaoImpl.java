package com.zhiwei.credit.dao.thirdInterface.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;


import com.hurong.credit.model.user.BpCustMember;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.thirdInterface.WebBankcardDao;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;


/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class WebBankcardDaoImpl extends BaseDaoImpl<WebBankcard> implements WebBankcardDao{

	public WebBankcardDaoImpl() {
		super(WebBankcard.class);
	}

	@Override
	public WebBankcard getByCardNum(String openAcctId) {
		String hql="from WebBankcard bc where bc.cardNum=?";
		Object[] params={openAcctId};
		return (WebBankcard)findUnique(hql, params);
	}

	@Override
	public WebBankcard checkCardExit(WebBankcard webBankcard, BpCustMember mem) {
		// TODO Auto-generated method stub
		WebBankcard card=null;
		String hql =" from WebBankcard as bc where bc.cardNum=? and bc.customerId=? and bc.bankId=?";
		if(webBankcard.getCardNum()!=null&&mem.getId()!=null&&webBankcard.getCardId()!=null){
			List<WebBankcard> list= this.getSession().createQuery(hql).setParameter(0, webBankcard.getCardNum()).setParameter(0, mem.getId()).setParameter(2, webBankcard.getBankId()).list();
			if(list!=null){
				card=list.get(0);
			}
		}
		return card;
	}

	@Override
	public List<WebBankcard> getBycusterId(Long id) {
		// TODO Auto-generated method stub
		String hql =" from WebBankcard as bc where  bc.customerId=? and (bc.bindCardStatus is not null and  bc.bindCardStatus in ('bindCard_status_success','bindCard_status_accept'))";
		List<WebBankcard> list= this.getSession().createQuery(hql).setParameter(0, id).list();
		return list;
	}

	@Override
	public WebBankcard getByRequestNo(String requestNo) {
		// TODO Auto-generated method stub
		WebBankcard card=null;
		String hql =" from WebBankcard as bc where  bc.requestNo=?";
		List<WebBankcard> list= this.getSession().createQuery(hql).setParameter(0, requestNo).list();
		if(list!=null){
			card=list.get(0);
		}
		return card;
	}

}