package com.zhiwei.credit.service.creditFlow.ourmain.impl;
/*
 *  北京互融时代软件有限公司

*/
import java.util.List;


import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.ourmain.SlPersonMainDao;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;


public class SlPersonMainServiceImpl extends BaseServiceImpl<SlPersonMain> implements SlPersonMainService{

	private SlPersonMainDao dao;
	
	public SlPersonMainServiceImpl(SlPersonMainDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlPersonMain> selectCardNum(String cardNum) {
		
		return dao.selectCardNum(cardNum);
	}
	
	/**
	 * 获取我方个人主体
	 * @param name
	 * @param cardnum
	 * @param PagingBean
	 * @return
	 */
	public List<SlPersonMain> findPersonListByIsPledge(String name,String cardnum,PagingBean pb,String companyId){
		return dao.findPersonListByIsPledge(name, cardnum, pb,companyId);
	}
	
	/**
	 * 获取我方个人主体--参照
	 * @param name
	 * @param PagingBean
	 * @return
	 */
	public List<SlPersonMain> findPersonListReference(String name,PagingBean pb){
		return dao.findPersonListReference(name, pb);
	}
	
	/**
	 * 获取我方个人主体-添加抵质押物索引匹配信息
	 * @param query
	 * @return
	 */
	public List<SlPersonMain> queryListForCombo(String query){
		return dao.queryListForCombo(query);
	}

}