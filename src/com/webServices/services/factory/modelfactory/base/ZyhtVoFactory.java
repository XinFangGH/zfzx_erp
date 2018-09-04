package com.webServices.services.factory.modelfactory.base;

import java.sql.Date;

import nc.vo.crd.bd.interf.zyhtvo.ZyhtVO;

public interface ZyhtVoFactory {
ZyhtVO createVo(int num);
ZyhtVO createVo(String corpno, String dateStr,int num);
ZyhtVO createVo(String corpno, String dateStart, String dateEnd);
}
