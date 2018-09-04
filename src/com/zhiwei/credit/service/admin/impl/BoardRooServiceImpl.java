package com.zhiwei.credit.service.admin.impl;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.BoardRooDao;
import com.zhiwei.credit.model.admin.BoardRoo;
import com.zhiwei.credit.service.admin.BoardRooService;

/**
 * @description boardRooServiceImpl
 * @author YHZ
 * @data 2010-9-20 PM
 * 
 */
public class BoardRooServiceImpl extends BaseServiceImpl<BoardRoo> implements
		BoardRooService {

	@SuppressWarnings("unused")
	private BoardRooDao dao;

	public BoardRooServiceImpl(BoardRooDao dao) {
		super(dao);
		this.dao = dao;
	}
	
}