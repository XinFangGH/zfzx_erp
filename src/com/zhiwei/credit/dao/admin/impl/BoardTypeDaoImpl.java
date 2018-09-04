package com.zhiwei.credit.dao.admin.impl;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.BoardTypeDao;
import com.zhiwei.credit.model.admin.BoardType;

/**
 * @description BoardTypeDaoImpl
 * @author 智维软件
 * @date 2010-9-25 PM
 * 
 */
@SuppressWarnings("unchecked")
public class BoardTypeDaoImpl extends BaseDaoImpl<BoardType> implements
		BoardTypeDao {
	public BoardTypeDaoImpl() {
		super(BoardType.class);
	}

}
