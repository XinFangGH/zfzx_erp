package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.service.creditFlow.contract.DocumentTempletService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseFinanceService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;

public class EnterpriseFinanceAction extends BaseAction{
	@Resource
	private EnterpriseFinanceService enterpriseFinanceService;
	private static final long serialVersionUID = 1L;
	private String id_xxxx_xxxx01;
	private String id_xxxx_xxxx02;
	private String id_xxxx_xxxx03;
	private String id_xxxx_xxxx04;
	private String id_xxxx_xxxx05;
	private String id_xxxx_xxxx06;
	private String id_xxxx_xxxx07;
	private String id_xxxx_xxxx08;
	private String id_xxxx_xxxx09;
	private String id_xxxx_xxxx10;
	private String id_xxxx_xxxx11;
	private String id_xxxx_xxxx12;
	private String id_xxxx_xxxx13;
	private String id_xxxx_xxxx14;
	private String id_xxxx_xxxx15;
	private String id_xxxx_xxxx16;
	private String id_xxxx_xxxx17;
	private String id_xxxx_xxxx18;
	private String id_xxxx_xxxx19;
	private String id_xxxx_xxxx20;
	private String id_xxxx_xxxx21;
	private String id_xxxx_xxxx22;
	private String id_xxxx_xxxx23;
	private String id_xxxx_xxxx24;

	private String id_xxxx_xxxx25;
	private String id_xxxx_xxxx26;
	private String id_xxxx_xxxx27;
	private String id_xxxx_xxxx28;
	private String id_xxxx_xxxx29;
	private String id_xxxx_xxxx30;
	private String id_xxxx_xxxx31;
	private String id_xxxx_xxxx32;
	private String id_xxxx_xxxx33;
	private String id_xxxx_xxxx34;
	private String id_xxxx_xxxx35;
	private String id_xxxx_xxxx36;
	private String id_xxxx_xxxx37;
	private String id_xxxx_xxxx38;
	private String id_xxxx_xxxx39;
	private String id_xxxx_xxxx40;
	private String id_xxxx_xxxx41;
	private String id_xxxx_xxxx42;
	private String id_xxxx_xxxx43;
	private String id_xxxx_xxxx44;
	private String id_xxxx_xxxx45;
	private String id_xxxx_xxxx46;
	private String id_xxxx_xxxx47;
	private String id_xxxx_xxxx48;
	private String id_xxxx_xxxx49;
	private String id_xxxx_xxxx50;
	private String id_xxxx_xxxx51;
	private String id_xxxx_xxxx52;
	private String id_xxxx_xxxx53;
	private String id_xxxx_xxxx54;
	private String id_xxxx_xxxx55;
	private String id_xxxx_xxxx56;
	private String id_xxxx_xxxx57;
	private String id_xxxx_xxxx58;
	private String id_xxxx_xxxx59;
	private String id_xxxx_xxxx60;
	private String id_xxxx_xxxx61;
	private String id_xxxx_xxxx62;
	private String id_xxxx_xxxx63;
	private String id_xxxx_xxxx64;
	private String id_xxxx_xxxx65;
	private String id_xxxx_xxxx66;
	private String id_xxxx_xxxx67;
	private String id_xxxx_xxxx68;
	private String id_xxxx_xxxx69;
	private String id_xxxx_xxxx70;
	private String id_xxxx_xxxx71;
	private String id_xxxx_xxxx72;
	private String id_xxxx_xxxx73;
	private String id_xxxx_xxxx74;
	private String id_xxxx_xxxx75;
	private String id_xxxx_xxxx76;
	private String id_xxxx_xxxx77;
	private String id_xxxx_xxxx78;
	private String id_xxxx_xxxx79;
	private String id_xxxx_xxxx80;
	private String id_xxxx_xxxx81;
	private String id_xxxx_xxxx82;
	private String id_xxxx_xxxx83;
	private String id_xxxx_xxxx84;
	private String id_xxxx_xxxx85;
	private String id_xxxx_xxxx86;
	private String id_xxxx_xxxx87;
	private String id_xxxx_xxxx88;
	private String id_xxxx_xxxx89;
	private String id_xxxx_xxxx90;
	private String id_xxxx_xxxx91;
	private String id_xxxx_xxxx92;
	private String id_xxxx_xxxx93;
	private String id_xxxx_xxxx94;
	private String id_xxxx_xxxx95;
	private String id_xxxx_xxxx96;
	private String id_xxxx_xxxx97;
	private String id_xxxx_xxxx98;
	private String id_xxxx_xxxx99;
	private String id_xxxx_xxxx100;
	private String id_xxxx_xxxx101;
	private String id_xxxx_xxxx102;
	private String id_xxxx_xxxx103;
	private String id_xxxx_xxxx104;
	private String id_xxxx_xxxx105;
	private String id_xxxx_xxxx106;
	private String id_xxxx_xxxx107;
	private String id_xxxx_xxxx108;
	private String id_xxxx_xxxx109;
	private String id_xxxx_xxxx110;
	private String id_xxxx_xxxx111;
	private String id_xxxx_xxxx112;
	private String id_xxxx_xxxx113;
	private String id_xxxx_xxxx114;
	private String id_xxxx_xxxx115;
	private String id_xxxx_xxxx116;
	private String id_xxxx_xxxx117;
	private String id_xxxx_xxxx118;
	private String id_xxxx_xxxx119;
	private String id_xxxx_xxxx120;
	private String id_xxxx_xxxx121;
	private String id_xxxx_xxxx122;
	private String id_xxxx_xxxx123;
	private String id_xxxx_xxxx124; //总资金
	private String id_xxxx_xxxx125;
	private String id_xxxx_xxxx126;
	private String id_xxxx_xxxx127;
	private String id_xxxx_xxxx128;

	// 备份信息
	private String id_xxxx_xxxx200;
	private String id_xxxx_xxxx201;
	private String id_xxxx_xxxx202;
	private String id_xxxx_xxxx203;
	private String id_xxxx_xxxx204;
	private String id_xxxx_xxxx205;
	private String id_xxxx_xxxx206;
	private String id_xxxx_xxxx207;
	private String id_xxxx_xxxx208;
	private String id_xxxx_xxxx209;
	private String id_xxxx_xxxx210;
	private String id_xxxx_xxxx211;
	private String id_xxxx_xxxx212;
	private String id_xxxx_xxxx213;
	private String id_xxxx_xxxx214;
	private String id_xxxx_xxxx215;
	private String id_xxxx_xxxx216;
	private String id_xxxx_xxxx217;
	private String id_xxxx_xxxx218;
	private String id_xxxx_xxxx219;
	private String id_xxxx_xxxx220;
	private String id_xxxx_xxxx221;
	private String id_xxxx_xxxx222;
	private String id_xxxx_xxxx223;
	private String id_xxxx_xxxx224;
	private String id_xxxx_xxxx225;
	private String id_xxxx_xxxx226;
	private String id_xxxx_xxxx227;
	private String id_xxxx_xxxx228;
	private String id_xxxx_xxxx229;
	private String id_xxxx_xxxx230;
	private String id_xxxx_xxxx231;

	private int enterpriseId;
	private File fileBatch ;
	private String fileBatchFileName ;
	public final int UPLOAD_SIZE = 1024 * 1024 * 10;
	private String mark;
	private String businessType;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private DocumentTempletService documentTempletService;
	@Resource
	private FileFormService fileFormService;
	public void saveFinance() {

		Collection<EnterpriseFinance> collection = new ArrayList<EnterpriseFinance>();

		collection = saveFirstPart(collection);
		collection = saveFourthPart(collection);
		collection = saveSecondPart(collection);
		collection = saveThirdPart(collection);
		collection = saveFifthhPart(collection);
		collection = saveSixthPart(collection);
		collection = saveSenventhPart(collection);
		collection = saveEighththPart(collection);

		try {
			creditBaseDao.saveOrUpdateAll(collection);
			JsonUtil.responseJsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.responseJsonFailure();
		}

	}

	public void getFinance() {


		List<EnterpriseFinance> list = null;

		try {
			list =enterpriseFinanceService.getListByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonUtil.jsonFromList(list);
	}
	

	

	public String getFinanceForIphone() {

		List<EnterpriseFinance> list = null;

		try {
			list =enterpriseFinanceService.getListByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JsonUtil.jsonFromListForIphone(list);
	}

	/**
	 * 1-24
	 * 
	 * @param collection
	 * @return
	 */
	private Collection<EnterpriseFinance> saveSixthPart(
			Collection<EnterpriseFinance> collection) {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id_xxxx_xxxx01", id_xxxx_xxxx01);
		map.put("id_xxxx_xxxx02", id_xxxx_xxxx02);
		map.put("id_xxxx_xxxx03", id_xxxx_xxxx03);
		map.put("id_xxxx_xxxx04", id_xxxx_xxxx04);
		map.put("id_xxxx_xxxx05", id_xxxx_xxxx05);
		map.put("id_xxxx_xxxx06", id_xxxx_xxxx06);
		map.put("id_xxxx_xxxx07", id_xxxx_xxxx07);
		map.put("id_xxxx_xxxx08", id_xxxx_xxxx08);
		map.put("id_xxxx_xxxx09", id_xxxx_xxxx09);
		map.put("id_xxxx_xxxx10", id_xxxx_xxxx10);
		map.put("id_xxxx_xxxx11", id_xxxx_xxxx11);
		map.put("id_xxxx_xxxx12", id_xxxx_xxxx12);
		map.put("id_xxxx_xxxx13", id_xxxx_xxxx13);
		map.put("id_xxxx_xxxx14", id_xxxx_xxxx14);
		map.put("id_xxxx_xxxx15", id_xxxx_xxxx15);
		map.put("id_xxxx_xxxx16", id_xxxx_xxxx16);
		map.put("id_xxxx_xxxx17", id_xxxx_xxxx17);
		map.put("id_xxxx_xxxx18", id_xxxx_xxxx18);
		map.put("id_xxxx_xxxx19", id_xxxx_xxxx19);
		map.put("id_xxxx_xxxx20", id_xxxx_xxxx20);
		map.put("id_xxxx_xxxx21", id_xxxx_xxxx21);
		map.put("id_xxxx_xxxx22", id_xxxx_xxxx22);
		map.put("id_xxxx_xxxx23", id_xxxx_xxxx23);
		map.put("id_xxxx_xxxx24", id_xxxx_xxxx24);
		this.commonData(collection, 1, 24, map);
		return collection;

	}

	private Collection<EnterpriseFinance> saveSenventhPart(
			Collection<EnterpriseFinance> collection) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id_xxxx_xxxx57", id_xxxx_xxxx57);
		map.put("id_xxxx_xxxx58", id_xxxx_xxxx58);
		map.put("id_xxxx_xxxx59", id_xxxx_xxxx59);
		map.put("id_xxxx_xxxx60", id_xxxx_xxxx60);
		map.put("id_xxxx_xxxx61", id_xxxx_xxxx61);
		map.put("id_xxxx_xxxx62", id_xxxx_xxxx62);
		map.put("id_xxxx_xxxx63", id_xxxx_xxxx63);
		map.put("id_xxxx_xxxx64", id_xxxx_xxxx64);
		map.put("id_xxxx_xxxx65", id_xxxx_xxxx65);
		map.put("id_xxxx_xxxx66", id_xxxx_xxxx66);
		map.put("id_xxxx_xxxx67", id_xxxx_xxxx67);
		map.put("id_xxxx_xxxx68", id_xxxx_xxxx68);
		map.put("id_xxxx_xxxx69", id_xxxx_xxxx69);
		map.put("id_xxxx_xxxx70", id_xxxx_xxxx70);
		map.put("id_xxxx_xxxx71", id_xxxx_xxxx71);
		map.put("id_xxxx_xxxx72", id_xxxx_xxxx72);
		map.put("id_xxxx_xxxx73", id_xxxx_xxxx73);
		map.put("id_xxxx_xxxx74", id_xxxx_xxxx74);
		map.put("id_xxxx_xxxx75", id_xxxx_xxxx75);
		map.put("id_xxxx_xxxx76", id_xxxx_xxxx76);
		map.put("id_xxxx_xxxx77", id_xxxx_xxxx77);
		map.put("id_xxxx_xxxx78", id_xxxx_xxxx78);
		map.put("id_xxxx_xxxx79", id_xxxx_xxxx79);
		map.put("id_xxxx_xxxx80", id_xxxx_xxxx80);
		map.put("id_xxxx_xxxx81", id_xxxx_xxxx81);
		map.put("id_xxxx_xxxx82", id_xxxx_xxxx82);
		map.put("id_xxxx_xxxx83", id_xxxx_xxxx83);
		map.put("id_xxxx_xxxx84", id_xxxx_xxxx84);
		this.commonData(collection, 57, 84, map);
		return collection;
	}

	/*
	 * 93-108
	 */
	private Collection<EnterpriseFinance> saveEighththPart(
			Collection<EnterpriseFinance> collection) {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id_xxxx_xxxx93", id_xxxx_xxxx93);
		map.put("id_xxxx_xxxx94", id_xxxx_xxxx94);
		map.put("id_xxxx_xxxx95", id_xxxx_xxxx95);
		map.put("id_xxxx_xxxx96", id_xxxx_xxxx96);
		map.put("id_xxxx_xxxx97", id_xxxx_xxxx97);
		map.put("id_xxxx_xxxx98", id_xxxx_xxxx98);
		map.put("id_xxxx_xxxx99", id_xxxx_xxxx99);
		map.put("id_xxxx_xxxx100", id_xxxx_xxxx100);
		map.put("id_xxxx_xxxx101", id_xxxx_xxxx101);
		map.put("id_xxxx_xxxx102", id_xxxx_xxxx102);
		map.put("id_xxxx_xxxx103", id_xxxx_xxxx103);
		map.put("id_xxxx_xxxx104", id_xxxx_xxxx104);
		map.put("id_xxxx_xxxx105", id_xxxx_xxxx105);
		map.put("id_xxxx_xxxx106", id_xxxx_xxxx106);
		map.put("id_xxxx_xxxx107", id_xxxx_xxxx107);
		map.put("id_xxxx_xxxx108", id_xxxx_xxxx108);
		this.commonData(collection, 93, 108, map);
		return collection;
	}

	@SuppressWarnings("unchecked")
	private Collection commonData(Collection<EnterpriseFinance> collection,
			int start, int end, Map<Object, Object> map) {

		try {
			for (int i = start; i <= end; i++) {
				String s = "";
				if (i < 10) {
					s = "0" + i;
				} else {
					s = String.valueOf(i);
				}
				String idField = "id_xxxx_xxxx" + s;
				EnterpriseFinance ef = new EnterpriseFinance();
				List<EnterpriseFinance> list =enterpriseFinanceService.getList(enterpriseId, idField);
				if (null != list && list.size() > 0) {
					EnterpriseFinance temp = list.get(0);
					temp.setTextFeildText(map.get(idField).toString());
					this.creditBaseDao.updateDatas(temp);
				} else {
					if (null != map.get(idField) && !"".equals(map.get(idField))) {
						if (null != map.get(idField).toString()&& !"".equals(map.get(idField).toString())) {
							ef.setTextFeildText(map.get(idField).toString());
							ef.setTextFeildId(idField);
							ef.setEnterpriseId(enterpriseId);
							collection.add(ef);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	     /**
	      * 109-124
	      * @param collection
	      * @return
	      */
// 第一段 ： 总资产--流动负载 4*4=16 【ID: 109-124】
   private Collection<EnterpriseFinance> saveFirstPart
   (
			Collection<EnterpriseFinance> collection) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id_xxxx_xxxx109", id_xxxx_xxxx109);
			map.put("id_xxxx_xxxx110", id_xxxx_xxxx110);
			map.put("id_xxxx_xxxx111", id_xxxx_xxxx111);
			map.put("id_xxxx_xxxx112", id_xxxx_xxxx112);
			map.put("id_xxxx_xxxx113", id_xxxx_xxxx113);
			map.put("id_xxxx_xxxx114", id_xxxx_xxxx114);
			map.put("id_xxxx_xxxx115", id_xxxx_xxxx115);
			map.put("id_xxxx_xxxx116", id_xxxx_xxxx116);
			map.put("id_xxxx_xxxx117", id_xxxx_xxxx117);
			map.put("id_xxxx_xxxx118", id_xxxx_xxxx118);
			map.put("id_xxxx_xxxx119", id_xxxx_xxxx119);
			map.put("id_xxxx_xxxx120", id_xxxx_xxxx120);
			map.put("id_xxxx_xxxx121", id_xxxx_xxxx121);
			map.put("id_xxxx_xxxx122", id_xxxx_xxxx122);
			map.put("id_xxxx_xxxx123", id_xxxx_xxxx123);
			map.put("id_xxxx_xxxx124", id_xxxx_xxxx124);
			this.commonData(collection,109,124,map);
			return  collection;
	}

	// 第二段：主营业务收入--净利润 4*8=32 【ID：25--56】
	/**
	 * 25-56
	 */
	private Collection<EnterpriseFinance> saveSecondPart(
			Collection<EnterpriseFinance> collection) {

	
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id_xxxx_xxxx25", id_xxxx_xxxx25);
		map.put("id_xxxx_xxxx26", id_xxxx_xxxx26);
		map.put("id_xxxx_xxxx27", id_xxxx_xxxx27);
		map.put("id_xxxx_xxxx28", id_xxxx_xxxx28);
		map.put("id_xxxx_xxxx29", id_xxxx_xxxx29);
		map.put("id_xxxx_xxxx30", id_xxxx_xxxx30);
		map.put("id_xxxx_xxxx31", id_xxxx_xxxx31);
		map.put("id_xxxx_xxxx32", id_xxxx_xxxx32);
		map.put("id_xxxx_xxxx33", id_xxxx_xxxx33);
		map.put("id_xxxx_xxxx34", id_xxxx_xxxx34);
		map.put("id_xxxx_xxxx35", id_xxxx_xxxx35);
		map.put("id_xxxx_xxxx36", id_xxxx_xxxx36);
		map.put("id_xxxx_xxxx37", id_xxxx_xxxx37);
		map.put("id_xxxx_xxxx38", id_xxxx_xxxx38);
		map.put("id_xxxx_xxxx39", id_xxxx_xxxx39);
		map.put("id_xxxx_xxxx40", id_xxxx_xxxx40);
		map.put("id_xxxx_xxxx41", id_xxxx_xxxx41);
		map.put("id_xxxx_xxxx42", id_xxxx_xxxx42);
		map.put("id_xxxx_xxxx43", id_xxxx_xxxx43);
		map.put("id_xxxx_xxxx44", id_xxxx_xxxx44);
		map.put("id_xxxx_xxxx45", id_xxxx_xxxx45);
		map.put("id_xxxx_xxxx46", id_xxxx_xxxx46);
		map.put("id_xxxx_xxxx47", id_xxxx_xxxx47);
		map.put("id_xxxx_xxxx48", id_xxxx_xxxx48);
		map.put("id_xxxx_xxxx49", id_xxxx_xxxx49);
		map.put("id_xxxx_xxxx50", id_xxxx_xxxx50);
		map.put("id_xxxx_xxxx51", id_xxxx_xxxx51);
		map.put("id_xxxx_xxxx52", id_xxxx_xxxx52);
		map.put("id_xxxx_xxxx53", id_xxxx_xxxx53);
		map.put("id_xxxx_xxxx54", id_xxxx_xxxx54);
		map.put("id_xxxx_xxxx55", id_xxxx_xxxx55);
		map.put("id_xxxx_xxxx56", id_xxxx_xxxx56);
		this.commonData(collection, 25, 56, map);
		return collection;
	}
	/**
	 * 85-92
	 * @param collection
	 * @return
	 */

	// 第三段：存货--应收账款 2*8=16 【ID：85--92】
	@SuppressWarnings("unchecked")
	private Collection<EnterpriseFinance> saveThirdPart(
			Collection<EnterpriseFinance> collection) {

		// 92-85
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id_xxxx_xxxx85", id_xxxx_xxxx85);
		map.put("id_xxxx_xxxx86", id_xxxx_xxxx86);
		map.put("id_xxxx_xxxx87", id_xxxx_xxxx87);
		map.put("id_xxxx_xxxx88", id_xxxx_xxxx88);
		map.put("id_xxxx_xxxx89", id_xxxx_xxxx89);
		map.put("id_xxxx_xxxx90", id_xxxx_xxxx90);
		map.put("id_xxxx_xxxx91", id_xxxx_xxxx91);
		map.put("id_xxxx_xxxx92", id_xxxx_xxxx92);
		this.commonData(collection, 85, 92, map);
		return collection;
	}
	/**
	 * 125-128
	 * @param collection
	 * @return
	 */

	// 第四段：报表时间 1*4=4 【ID: 125-128】
	private Collection<EnterpriseFinance> saveFourthPart(
			Collection<EnterpriseFinance> collection) {
	
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id_xxxx_xxxx125", id_xxxx_xxxx125);
		map.put("id_xxxx_xxxx126", id_xxxx_xxxx126);
		map.put("id_xxxx_xxxx127", id_xxxx_xxxx127);
		map.put("id_xxxx_xxxx128", id_xxxx_xxxx128);
		this.commonData(collection, 125, 128, map);
	    return collection;
	}
	/**
	 * 200-231
	 * @param collection
	 * @return
	 */

	// 第五段：备份
	private Collection<EnterpriseFinance> saveFifthhPart(
			Collection<EnterpriseFinance> collection) {

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id_xxxx_xxxx200", id_xxxx_xxxx200);
			map.put("id_xxxx_xxxx201", id_xxxx_xxxx201);
			map.put("id_xxxx_xxxx202", id_xxxx_xxxx202);
			map.put("id_xxxx_xxxx203", id_xxxx_xxxx203);
			map.put("id_xxxx_xxxx204", id_xxxx_xxxx204);
			map.put("id_xxxx_xxxx205", id_xxxx_xxxx205);
			map.put("id_xxxx_xxxx206", id_xxxx_xxxx206);
			map.put("id_xxxx_xxxx207", id_xxxx_xxxx207);
			map.put("id_xxxx_xxxx208", id_xxxx_xxxx208);
			map.put("id_xxxx_xxxx209", id_xxxx_xxxx209);
			map.put("id_xxxx_xxxx210", id_xxxx_xxxx210);
			map.put("id_xxxx_xxxx211", id_xxxx_xxxx211);
			map.put("id_xxxx_xxxx212", id_xxxx_xxxx212);
			map.put("id_xxxx_xxxx213", id_xxxx_xxxx213);
			map.put("id_xxxx_xxxx214", id_xxxx_xxxx214);
			map.put("id_xxxx_xxxx215", id_xxxx_xxxx215);
			map.put("id_xxxx_xxxx216", id_xxxx_xxxx216);
			map.put("id_xxxx_xxxx217", id_xxxx_xxxx217);
			map.put("id_xxxx_xxxx218", id_xxxx_xxxx218);
			map.put("id_xxxx_xxxx219", id_xxxx_xxxx219);
			map.put("id_xxxx_xxxx220", id_xxxx_xxxx220);
			map.put("id_xxxx_xxxx221", id_xxxx_xxxx221);
			map.put("id_xxxx_xxxx222", id_xxxx_xxxx222);
			map.put("id_xxxx_xxxx223", id_xxxx_xxxx223);
			map.put("id_xxxx_xxxx224", id_xxxx_xxxx224);
			map.put("id_xxxx_xxxx225", id_xxxx_xxxx225);
			map.put("id_xxxx_xxxx226", id_xxxx_xxxx226);
			map.put("id_xxxx_xxxx227", id_xxxx_xxxx227);
			map.put("id_xxxx_xxxx228", id_xxxx_xxxx228);
			map.put("id_xxxx_xxxx229", id_xxxx_xxxx229);
			map.put("id_xxxx_xxxx230", id_xxxx_xxxx230);
			map.put("id_xxxx_xxxx231", id_xxxx_xxxx231);
			this.commonData(collection, 200, 231, map);
		    return collection;
	}
	public String batchUploads(){
		File file =null ;
		String realPath = ServletActionContext.getServletContext().getRealPath("/credit/customer/enterprise");
		file = new File(realPath+"/"+fileBatchFileName);
		if(FileHelper.fileUpload(fileBatch ,file , new byte[UPLOAD_SIZE])){
			return realPath +"/"+ fileBatchFileName ;
		}else
			return "" ;
	}
	public void batchImportFinance() {
		String jsonStr = "" ;
		String path = batchUploads();
		boolean flag =true; //POIdataBaseMysql.readExcelToDB2(path);
		StringBuffer buff = new StringBuffer("");
		try{
			Workbook book = Workbook.getWorkbook(new File(path)) ;
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			for(int i = 1; i <rows; i++) {
				Cell [] cell = sheet.getRow(i);
				buff.append("'row_"+i+"':{");
				for(int j=2;j<cell.length;j++) {
					if(j != cell.length-1) {
						buff.append("'column_"+j+"':'"+sheet.getCell(j, i).getContents()+"',");
					}else {
						buff.append("'column_"+j+"':'"+sheet.getCell(j, i).getContents()+"'");
					}
				}
				if(i != rows -1){
					buff.append("},");
				}else {
					buff.append("}");
				}
			}
			book.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(flag){
			jsonStr = "{success : true,data:{"+buff.toString()+"}}";
		}else
			jsonStr = "{success : false}";
		JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
	}
	public void outputFinanceInfoExcel() throws UnsupportedEncodingException {
		String financeInfoStr = this.getRequest().getParameter("financeInfo").toString();
		String enterpriseName = this.getRequest().getParameter("enterpriseName").toString();
		String strArray[] = financeInfoStr.split(";");
		String financeInfoArray[][] = new String[strArray.length][];
		for(int i=0; i<strArray.length; i++) {
			String strArray1[] = strArray[i].split(",");
			financeInfoArray[i] = new String[strArray1.length];
			for(int j=0; j<strArray1.length;j++) {
				financeInfoArray[i][j] = strArray1[j];
			}
		}
		String [] tableHeader = {"指标类别","指标名称","前半年/季度报表","前一年报表","前两年报表","前三年报表","备注"};
		try {
			ExcelHelper.exportFinance(financeInfoArray, enterpriseName, tableHeader);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void getDocumentTempletByOnlymark(){
		DocumentTemplet dt = null;
		try {
			dt = documentTempletService.getByMarkAndBus(mark, businessType);
			if(dt != null && dt.isIsexist()){
			
				JsonUtil.jsonFromObject(dt, true);
			}else{
				JsonUtil.jsonFromObject(dt, false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			JsonUtil.jsonFromObject(dt, false);
		}
		
	}
	public void ajaxGetReportTemplate(){

		HttpServletResponse response = getResponse();
		DocumentTemplet dt = null;
		try {
			dt = documentTempletService.getByMarkAndBus(mark, businessType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dt != null){
			String onlymark = "cs_document_templet."+dt.getId();
			FileForm fileForm = null;
			fileForm = fileFormService.getFileByMark(onlymark);
			if(fileForm != null){
//				String path = ElementUtil.getUrl(fileForm.getFilepath(),fileForm.getFilename());
				String path = super.getRequest().getRealPath("/")+fileForm.getFilepath();
				ElementUtil.downloadFile(path, response);
			}
		}
		
	}
	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}


	public void setId_xxxx_xxxx25(String idXxxxXxxx25) {
		id_xxxx_xxxx25 = idXxxxXxxx25;
	}

	public void setId_xxxx_xxxx26(String idXxxxXxxx26) {
		id_xxxx_xxxx26 = idXxxxXxxx26;
	}

	public void setId_xxxx_xxxx27(String idXxxxXxxx27) {
		id_xxxx_xxxx27 = idXxxxXxxx27;
	}

	public void setId_xxxx_xxxx28(String idXxxxXxxx28) {
		id_xxxx_xxxx28 = idXxxxXxxx28;
	}

	public void setId_xxxx_xxxx29(String idXxxxXxxx29) {
		id_xxxx_xxxx29 = idXxxxXxxx29;
	}

	public void setId_xxxx_xxxx30(String idXxxxXxxx30) {
		id_xxxx_xxxx30 = idXxxxXxxx30;
	}

	public void setId_xxxx_xxxx31(String idXxxxXxxx31) {
		id_xxxx_xxxx31 = idXxxxXxxx31;
	}

	public void setId_xxxx_xxxx32(String idXxxxXxxx32) {
		id_xxxx_xxxx32 = idXxxxXxxx32;
	}

	public void setId_xxxx_xxxx33(String idXxxxXxxx33) {
		id_xxxx_xxxx33 = idXxxxXxxx33;
	}

	public void setId_xxxx_xxxx34(String idXxxxXxxx34) {
		id_xxxx_xxxx34 = idXxxxXxxx34;
	}

	public void setId_xxxx_xxxx35(String idXxxxXxxx35) {
		id_xxxx_xxxx35 = idXxxxXxxx35;
	}

	public void setId_xxxx_xxxx36(String idXxxxXxxx36) {
		id_xxxx_xxxx36 = idXxxxXxxx36;
	}

	public void setId_xxxx_xxxx37(String idXxxxXxxx37) {
		id_xxxx_xxxx37 = idXxxxXxxx37;
	}

	public void setId_xxxx_xxxx38(String idXxxxXxxx38) {
		id_xxxx_xxxx38 = idXxxxXxxx38;
	}

	public void setId_xxxx_xxxx39(String idXxxxXxxx39) {
		id_xxxx_xxxx39 = idXxxxXxxx39;
	}

	public void setId_xxxx_xxxx40(String idXxxxXxxx40) {
		id_xxxx_xxxx40 = idXxxxXxxx40;
	}

	public void setId_xxxx_xxxx41(String idXxxxXxxx41) {
		id_xxxx_xxxx41 = idXxxxXxxx41;
	}

	public void setId_xxxx_xxxx42(String idXxxxXxxx42) {
		id_xxxx_xxxx42 = idXxxxXxxx42;
	}

	public void setId_xxxx_xxxx43(String idXxxxXxxx43) {
		id_xxxx_xxxx43 = idXxxxXxxx43;
	}

	public void setId_xxxx_xxxx44(String idXxxxXxxx44) {
		id_xxxx_xxxx44 = idXxxxXxxx44;
	}

	public void setId_xxxx_xxxx45(String idXxxxXxxx45) {
		id_xxxx_xxxx45 = idXxxxXxxx45;
	}

	public void setId_xxxx_xxxx46(String idXxxxXxxx46) {
		id_xxxx_xxxx46 = idXxxxXxxx46;
	}

	public void setId_xxxx_xxxx47(String idXxxxXxxx47) {
		id_xxxx_xxxx47 = idXxxxXxxx47;
	}

	public void setId_xxxx_xxxx48(String idXxxxXxxx48) {
		id_xxxx_xxxx48 = idXxxxXxxx48;
	}

	public void setId_xxxx_xxxx49(String idXxxxXxxx49) {
		id_xxxx_xxxx49 = idXxxxXxxx49;
	}

	public void setId_xxxx_xxxx50(String idXxxxXxxx50) {
		id_xxxx_xxxx50 = idXxxxXxxx50;
	}

	public void setId_xxxx_xxxx51(String idXxxxXxxx51) {
		id_xxxx_xxxx51 = idXxxxXxxx51;
	}

	public void setId_xxxx_xxxx52(String idXxxxXxxx52) {
		id_xxxx_xxxx52 = idXxxxXxxx52;
	}

	public void setId_xxxx_xxxx53(String idXxxxXxxx53) {
		id_xxxx_xxxx53 = idXxxxXxxx53;
	}

	public void setId_xxxx_xxxx54(String idXxxxXxxx54) {
		id_xxxx_xxxx54 = idXxxxXxxx54;
	}

	public void setId_xxxx_xxxx55(String idXxxxXxxx55) {
		id_xxxx_xxxx55 = idXxxxXxxx55;
	}

	public void setId_xxxx_xxxx56(String idXxxxXxxx56) {
		id_xxxx_xxxx56 = idXxxxXxxx56;
	}

	public void setId_xxxx_xxxx57(String idXxxxXxxx57) {
		id_xxxx_xxxx57 = idXxxxXxxx57;
	}

	public void setId_xxxx_xxxx58(String idXxxxXxxx58) {
		id_xxxx_xxxx58 = idXxxxXxxx58;
	}

	public void setId_xxxx_xxxx59(String idXxxxXxxx59) {
		id_xxxx_xxxx59 = idXxxxXxxx59;
	}

	public void setId_xxxx_xxxx60(String idXxxxXxxx60) {
		id_xxxx_xxxx60 = idXxxxXxxx60;
	}

	public void setId_xxxx_xxxx61(String idXxxxXxxx61) {
		id_xxxx_xxxx61 = idXxxxXxxx61;
	}

	public void setId_xxxx_xxxx62(String idXxxxXxxx62) {
		id_xxxx_xxxx62 = idXxxxXxxx62;
	}

	public void setId_xxxx_xxxx63(String idXxxxXxxx63) {
		id_xxxx_xxxx63 = idXxxxXxxx63;
	}

	public void setId_xxxx_xxxx64(String idXxxxXxxx64) {
		id_xxxx_xxxx64 = idXxxxXxxx64;
	}

	public void setId_xxxx_xxxx65(String idXxxxXxxx65) {
		id_xxxx_xxxx65 = idXxxxXxxx65;
	}

	public void setId_xxxx_xxxx66(String idXxxxXxxx66) {
		id_xxxx_xxxx66 = idXxxxXxxx66;
	}

	public void setId_xxxx_xxxx67(String idXxxxXxxx67) {
		id_xxxx_xxxx67 = idXxxxXxxx67;
	}

	public void setId_xxxx_xxxx68(String idXxxxXxxx68) {
		id_xxxx_xxxx68 = idXxxxXxxx68;
	}

	public void setId_xxxx_xxxx69(String idXxxxXxxx69) {
		id_xxxx_xxxx69 = idXxxxXxxx69;
	}

	public void setId_xxxx_xxxx70(String idXxxxXxxx70) {
		id_xxxx_xxxx70 = idXxxxXxxx70;
	}

	public void setId_xxxx_xxxx71(String idXxxxXxxx71) {
		id_xxxx_xxxx71 = idXxxxXxxx71;
	}

	public void setId_xxxx_xxxx72(String idXxxxXxxx72) {
		id_xxxx_xxxx72 = idXxxxXxxx72;
	}

	public void setId_xxxx_xxxx73(String idXxxxXxxx73) {
		id_xxxx_xxxx73 = idXxxxXxxx73;
	}

	public void setId_xxxx_xxxx74(String idXxxxXxxx74) {
		id_xxxx_xxxx74 = idXxxxXxxx74;
	}

	public void setId_xxxx_xxxx75(String idXxxxXxxx75) {
		id_xxxx_xxxx75 = idXxxxXxxx75;
	}

	public void setId_xxxx_xxxx76(String idXxxxXxxx76) {
		id_xxxx_xxxx76 = idXxxxXxxx76;
	}

	public void setId_xxxx_xxxx77(String idXxxxXxxx77) {
		id_xxxx_xxxx77 = idXxxxXxxx77;
	}

	public void setId_xxxx_xxxx78(String idXxxxXxxx78) {
		id_xxxx_xxxx78 = idXxxxXxxx78;
	}

	public void setId_xxxx_xxxx79(String idXxxxXxxx79) {
		id_xxxx_xxxx79 = idXxxxXxxx79;
	}

	public void setId_xxxx_xxxx80(String idXxxxXxxx80) {
		id_xxxx_xxxx80 = idXxxxXxxx80;
	}

	public void setId_xxxx_xxxx81(String idXxxxXxxx81) {
		id_xxxx_xxxx81 = idXxxxXxxx81;
	}

	public void setId_xxxx_xxxx82(String idXxxxXxxx82) {
		id_xxxx_xxxx82 = idXxxxXxxx82;
	}

	public void setId_xxxx_xxxx83(String idXxxxXxxx83) {
		id_xxxx_xxxx83 = idXxxxXxxx83;
	}

	public void setId_xxxx_xxxx84(String idXxxxXxxx84) {
		id_xxxx_xxxx84 = idXxxxXxxx84;
	}

	public void setId_xxxx_xxxx85(String idXxxxXxxx85) {
		id_xxxx_xxxx85 = idXxxxXxxx85;
	}

	public void setId_xxxx_xxxx86(String idXxxxXxxx86) {
		id_xxxx_xxxx86 = idXxxxXxxx86;
	}

	public void setId_xxxx_xxxx87(String idXxxxXxxx87) {
		id_xxxx_xxxx87 = idXxxxXxxx87;
	}

	public void setId_xxxx_xxxx88(String idXxxxXxxx88) {
		id_xxxx_xxxx88 = idXxxxXxxx88;
	}

	public void setId_xxxx_xxxx89(String idXxxxXxxx89) {
		id_xxxx_xxxx89 = idXxxxXxxx89;
	}

	public void setId_xxxx_xxxx90(String idXxxxXxxx90) {
		id_xxxx_xxxx90 = idXxxxXxxx90;
	}

	public void setId_xxxx_xxxx91(String idXxxxXxxx91) {
		id_xxxx_xxxx91 = idXxxxXxxx91;
	}

	public void setId_xxxx_xxxx92(String idXxxxXxxx92) {
		id_xxxx_xxxx92 = idXxxxXxxx92;
	}

	public void setId_xxxx_xxxx93(String idXxxxXxxx93) {
		id_xxxx_xxxx93 = idXxxxXxxx93;
	}

	public void setId_xxxx_xxxx94(String idXxxxXxxx94) {
		id_xxxx_xxxx94 = idXxxxXxxx94;
	}

	public void setId_xxxx_xxxx95(String idXxxxXxxx95) {
		id_xxxx_xxxx95 = idXxxxXxxx95;
	}

	public void setId_xxxx_xxxx96(String idXxxxXxxx96) {
		id_xxxx_xxxx96 = idXxxxXxxx96;
	}

	public void setId_xxxx_xxxx97(String idXxxxXxxx97) {
		id_xxxx_xxxx97 = idXxxxXxxx97;
	}

	public void setId_xxxx_xxxx98(String idXxxxXxxx98) {
		id_xxxx_xxxx98 = idXxxxXxxx98;
	}

	public void setId_xxxx_xxxx99(String idXxxxXxxx99) {
		id_xxxx_xxxx99 = idXxxxXxxx99;
	}

	public void setId_xxxx_xxxx100(String idXxxxXxxx100) {
		id_xxxx_xxxx100 = idXxxxXxxx100;
	}

	public void setId_xxxx_xxxx101(String idXxxxXxxx101) {
		id_xxxx_xxxx101 = idXxxxXxxx101;
	}

	public void setId_xxxx_xxxx102(String idXxxxXxxx102) {
		id_xxxx_xxxx102 = idXxxxXxxx102;
	}

	public void setId_xxxx_xxxx103(String idXxxxXxxx103) {
		id_xxxx_xxxx103 = idXxxxXxxx103;
	}

	public void setId_xxxx_xxxx104(String idXxxxXxxx104) {
		id_xxxx_xxxx104 = idXxxxXxxx104;
	}

	public void setId_xxxx_xxxx105(String idXxxxXxxx105) {
		id_xxxx_xxxx105 = idXxxxXxxx105;
	}

	public void setId_xxxx_xxxx106(String idXxxxXxxx106) {
		id_xxxx_xxxx106 = idXxxxXxxx106;
	}

	public void setId_xxxx_xxxx107(String idXxxxXxxx107) {
		id_xxxx_xxxx107 = idXxxxXxxx107;
	}

	public void setId_xxxx_xxxx108(String idXxxxXxxx108) {
		id_xxxx_xxxx108 = idXxxxXxxx108;
	}

	public void setId_xxxx_xxxx109(String idXxxxXxxx109) {
		id_xxxx_xxxx109 = idXxxxXxxx109;
	}

	public void setId_xxxx_xxxx110(String idXxxxXxxx110) {
		id_xxxx_xxxx110 = idXxxxXxxx110;
	}

	public void setId_xxxx_xxxx111(String idXxxxXxxx111) {
		id_xxxx_xxxx111 = idXxxxXxxx111;
	}

	public void setId_xxxx_xxxx112(String idXxxxXxxx112) {
		id_xxxx_xxxx112 = idXxxxXxxx112;
	}

	public void setId_xxxx_xxxx113(String idXxxxXxxx113) {
		id_xxxx_xxxx113 = idXxxxXxxx113;
	}

	public void setId_xxxx_xxxx114(String idXxxxXxxx114) {
		id_xxxx_xxxx114 = idXxxxXxxx114;
	}

	public void setId_xxxx_xxxx115(String idXxxxXxxx115) {
		id_xxxx_xxxx115 = idXxxxXxxx115;
	}

	public void setId_xxxx_xxxx116(String idXxxxXxxx116) {
		id_xxxx_xxxx116 = idXxxxXxxx116;
	}

	public void setId_xxxx_xxxx117(String idXxxxXxxx117) {
		id_xxxx_xxxx117 = idXxxxXxxx117;
	}

	public void setId_xxxx_xxxx118(String idXxxxXxxx118) {
		id_xxxx_xxxx118 = idXxxxXxxx118;
	}

	public void setId_xxxx_xxxx119(String idXxxxXxxx119) {
		id_xxxx_xxxx119 = idXxxxXxxx119;
	}

	public void setId_xxxx_xxxx120(String idXxxxXxxx120) {
		id_xxxx_xxxx120 = idXxxxXxxx120;
	}

	public void setId_xxxx_xxxx121(String idXxxxXxxx121) {
		id_xxxx_xxxx121 = idXxxxXxxx121;
	}

	public void setId_xxxx_xxxx122(String idXxxxXxxx122) {
		id_xxxx_xxxx122 = idXxxxXxxx122;
	}

	public void setId_xxxx_xxxx123(String idXxxxXxxx123) {
		id_xxxx_xxxx123 = idXxxxXxxx123;
	}

	public void setId_xxxx_xxxx124(String idXxxxXxxx124) {
		id_xxxx_xxxx124 = idXxxxXxxx124;
	}

	public void setId_xxxx_xxxx125(String idXxxxXxxx125) {
		id_xxxx_xxxx125 = idXxxxXxxx125;
	}

	public void setId_xxxx_xxxx126(String idXxxxXxxx126) {
		id_xxxx_xxxx126 = idXxxxXxxx126;
	}

	public void setId_xxxx_xxxx127(String idXxxxXxxx127) {
		id_xxxx_xxxx127 = idXxxxXxxx127;
	}

	public void setId_xxxx_xxxx128(String idXxxxXxxx128) {
		id_xxxx_xxxx128 = idXxxxXxxx128;
	}

	public void setId_xxxx_xxxx200(String idXxxxXxxx200) {
		id_xxxx_xxxx200 = idXxxxXxxx200;
	}

	public void setId_xxxx_xxxx201(String idXxxxXxxx201) {
		id_xxxx_xxxx201 = idXxxxXxxx201;
	}

	public void setId_xxxx_xxxx202(String idXxxxXxxx202) {
		id_xxxx_xxxx202 = idXxxxXxxx202;
	}

	public void setId_xxxx_xxxx203(String idXxxxXxxx203) {
		id_xxxx_xxxx203 = idXxxxXxxx203;
	}

	public void setId_xxxx_xxxx204(String idXxxxXxxx204) {
		id_xxxx_xxxx204 = idXxxxXxxx204;
	}

	public void setId_xxxx_xxxx205(String idXxxxXxxx205) {
		id_xxxx_xxxx205 = idXxxxXxxx205;
	}

	public void setId_xxxx_xxxx206(String idXxxxXxxx206) {
		id_xxxx_xxxx206 = idXxxxXxxx206;
	}

	public void setId_xxxx_xxxx207(String idXxxxXxxx207) {
		id_xxxx_xxxx207 = idXxxxXxxx207;
	}

	public void setId_xxxx_xxxx208(String idXxxxXxxx208) {
		id_xxxx_xxxx208 = idXxxxXxxx208;
	}

	public void setId_xxxx_xxxx209(String idXxxxXxxx209) {
		id_xxxx_xxxx209 = idXxxxXxxx209;
	}

	public void setId_xxxx_xxxx210(String idXxxxXxxx210) {
		id_xxxx_xxxx210 = idXxxxXxxx210;
	}

	public void setId_xxxx_xxxx211(String idXxxxXxxx211) {
		id_xxxx_xxxx211 = idXxxxXxxx211;
	}

	public void setId_xxxx_xxxx212(String idXxxxXxxx212) {
		id_xxxx_xxxx212 = idXxxxXxxx212;
	}

	public void setId_xxxx_xxxx213(String idXxxxXxxx213) {
		id_xxxx_xxxx213 = idXxxxXxxx213;
	}

	public void setId_xxxx_xxxx214(String idXxxxXxxx214) {
		id_xxxx_xxxx214 = idXxxxXxxx214;
	}

	public void setId_xxxx_xxxx215(String idXxxxXxxx215) {
		id_xxxx_xxxx215 = idXxxxXxxx215;
	}

	public void setId_xxxx_xxxx216(String idXxxxXxxx216) {
		id_xxxx_xxxx216 = idXxxxXxxx216;
	}

	public void setId_xxxx_xxxx217(String idXxxxXxxx217) {
		id_xxxx_xxxx217 = idXxxxXxxx217;
	}

	public void setId_xxxx_xxxx218(String idXxxxXxxx218) {
		id_xxxx_xxxx218 = idXxxxXxxx218;
	}

	public void setId_xxxx_xxxx219(String idXxxxXxxx219) {
		id_xxxx_xxxx219 = idXxxxXxxx219;
	}

	public void setId_xxxx_xxxx220(String idXxxxXxxx220) {
		id_xxxx_xxxx220 = idXxxxXxxx220;
	}

	public void setId_xxxx_xxxx221(String idXxxxXxxx221) {
		id_xxxx_xxxx221 = idXxxxXxxx221;
	}

	public void setId_xxxx_xxxx222(String idXxxxXxxx222) {
		id_xxxx_xxxx222 = idXxxxXxxx222;
	}

	public void setId_xxxx_xxxx223(String idXxxxXxxx223) {
		id_xxxx_xxxx223 = idXxxxXxxx223;
	}

	public void setId_xxxx_xxxx224(String idXxxxXxxx224) {
		id_xxxx_xxxx224 = idXxxxXxxx224;
	}

	public void setId_xxxx_xxxx225(String idXxxxXxxx225) {
		id_xxxx_xxxx225 = idXxxxXxxx225;
	}

	public void setId_xxxx_xxxx226(String idXxxxXxxx226) {
		id_xxxx_xxxx226 = idXxxxXxxx226;
	}

	public void setId_xxxx_xxxx227(String idXxxxXxxx227) {
		id_xxxx_xxxx227 = idXxxxXxxx227;
	}

	public void setId_xxxx_xxxx228(String idXxxxXxxx228) {
		id_xxxx_xxxx228 = idXxxxXxxx228;
	}

	public void setId_xxxx_xxxx229(String idXxxxXxxx229) {
		id_xxxx_xxxx229 = idXxxxXxxx229;
	}

	public void setId_xxxx_xxxx01(String idXxxxXxxx01) {
		id_xxxx_xxxx01 = idXxxxXxxx01;
	}

	public void setId_xxxx_xxxx02(String idXxxxXxxx02) {
		id_xxxx_xxxx02 = idXxxxXxxx02;
	}

	public void setId_xxxx_xxxx03(String idXxxxXxxx03) {
		id_xxxx_xxxx03 = idXxxxXxxx03;
	}

	public void setId_xxxx_xxxx04(String idXxxxXxxx04) {
		id_xxxx_xxxx04 = idXxxxXxxx04;
	}

	public void setId_xxxx_xxxx05(String idXxxxXxxx05) {
		id_xxxx_xxxx05 = idXxxxXxxx05;
	}

	public void setId_xxxx_xxxx06(String idXxxxXxxx06) {
		id_xxxx_xxxx06 = idXxxxXxxx06;
	}

	public void setId_xxxx_xxxx07(String idXxxxXxxx07) {
		id_xxxx_xxxx07 = idXxxxXxxx07;
	}

	public void setId_xxxx_xxxx08(String idXxxxXxxx08) {
		id_xxxx_xxxx08 = idXxxxXxxx08;
	}

	public void setId_xxxx_xxxx09(String idXxxxXxxx09) {
		id_xxxx_xxxx09 = idXxxxXxxx09;
	}

	public void setId_xxxx_xxxx10(String idXxxxXxxx10) {
		id_xxxx_xxxx10 = idXxxxXxxx10;
	}

	public void setId_xxxx_xxxx11(String idXxxxXxxx11) {
		id_xxxx_xxxx11 = idXxxxXxxx11;
	}

	public void setId_xxxx_xxxx12(String idXxxxXxxx12) {
		id_xxxx_xxxx12 = idXxxxXxxx12;
	}

	public void setId_xxxx_xxxx13(String idXxxxXxxx13) {
		id_xxxx_xxxx13 = idXxxxXxxx13;
	}

	public void setId_xxxx_xxxx14(String idXxxxXxxx14) {
		id_xxxx_xxxx14 = idXxxxXxxx14;
	}

	public void setId_xxxx_xxxx15(String idXxxxXxxx15) {
		id_xxxx_xxxx15 = idXxxxXxxx15;
	}

	public void setId_xxxx_xxxx16(String idXxxxXxxx16) {
		id_xxxx_xxxx16 = idXxxxXxxx16;
	}

	public void setId_xxxx_xxxx17(String idXxxxXxxx17) {
		id_xxxx_xxxx17 = idXxxxXxxx17;
	}

	public void setId_xxxx_xxxx18(String idXxxxXxxx18) {
		id_xxxx_xxxx18 = idXxxxXxxx18;
	}

	public void setId_xxxx_xxxx19(String idXxxxXxxx19) {
		id_xxxx_xxxx19 = idXxxxXxxx19;
	}

	public void setId_xxxx_xxxx20(String idXxxxXxxx20) {
		id_xxxx_xxxx20 = idXxxxXxxx20;
	}

	public void setId_xxxx_xxxx21(String idXxxxXxxx21) {
		id_xxxx_xxxx21 = idXxxxXxxx21;
	}

	public void setId_xxxx_xxxx22(String idXxxxXxxx22) {
		id_xxxx_xxxx22 = idXxxxXxxx22;
	}

	public void setId_xxxx_xxxx23(String idXxxxXxxx23) {
		id_xxxx_xxxx23 = idXxxxXxxx23;
	}

	public void setId_xxxx_xxxx24(String idXxxxXxxx24) {
		id_xxxx_xxxx24 = idXxxxXxxx24;
	}

	public void setId_xxxx_xxxx230(String idXxxxXxxx230) {
		id_xxxx_xxxx230 = idXxxxXxxx230;
	}

	public void setId_xxxx_xxxx231(String idXxxxXxxx231) {
		id_xxxx_xxxx231 = idXxxxXxxx231;
	}

	public File getFileBatch() {
		return fileBatch;
	}

	public void setFileBatch(File fileBatch) {
		this.fileBatch = fileBatch;
	}

	public String getFileBatchFileName() {
		return fileBatchFileName;
	}

	public void setFileBatchFileName(String fileBatchFileName) {
		this.fileBatchFileName = fileBatchFileName;
	}

	public int getEnterpriseId() {
		return enterpriseId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}