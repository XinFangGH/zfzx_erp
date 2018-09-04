package com.zhiwei.credit.service.admin.impl;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.BoardTypeDao;
import com.zhiwei.credit.model.admin.BoardType;
import com.zhiwei.credit.service.admin.BoardTypeService;

/**
 * @description BoardTypeServiceImpl
 * @author YHZ
 * @date 2010-5-25 PM
 * 
 */
public class BoardTypeServiceImpl extends BaseServiceImpl<BoardType> implements
		BoardTypeService {

	@SuppressWarnings("unused")
	private BoardTypeDao dao;

	public BoardTypeServiceImpl(BoardTypeDao dao) {
		super(dao);
		this.dao = dao;
	}

}
