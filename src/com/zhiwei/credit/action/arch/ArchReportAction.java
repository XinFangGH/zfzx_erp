package com.zhiwei.credit.action.arch;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.arch.ArchFond;
import com.zhiwei.credit.model.arch.ArchRoll;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.arch.ArchFondService;
import com.zhiwei.credit.service.arch.ArchRollService;
import com.zhiwei.credit.service.arch.BorrowFileListService;
import com.zhiwei.credit.service.arch.BorrowRecordService;
import com.zhiwei.credit.service.arch.RollFileListService;
import com.zhiwei.credit.service.arch.RollFileService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class ArchReportAction extends BaseAction {
	@Resource
	private ArchFondService archFondService;// 全宗
	@Resource
	private ArchRollService archRollService;// 案卷
	@Resource
	private RollFileService rollFileService;// 文件
	@Resource
	private RollFileListService rollFileListService;// 附件
	@Resource
	private FileAttachService fileAttachService;// 硬盘件
	@Resource
	private BorrowRecordService borrowRecordService;// 借阅
	@Resource
	private BorrowFileListService borrowFileListService;// 借阅
	@Resource
	private DictionaryService dictionaryService;

	private String year;
	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String yearReportArch() {

		try {
			java.text.SimpleDateFormat yearToDate = new SimpleDateFormat("yyyy");
			java.text.SimpleDateFormat yearToString = new SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Date yearStart = yearToDate.parse(year);
			long afterTime = (yearStart.getTime() / 1000) + 60 * 60 * 24 * 364;
			java.util.Date yearEnd = new java.util.Date(afterTime * 1000);

			String yearStartStr = yearToString.format(yearStart);
			String yearEndStr = yearToString.format(yearEnd);

			// 全宗总数fondTotal
			int fondTotal = 0;
			// 馆内档案总数archTotal
			int archTotal = 0;
			// 卷次rollTotal
			int rollTotal = 0;
			// 件次fileTotal
			int fileTotal = 0;
			// 今年卷次thisYearRollTotal
			int thisYearRollTotal = 0;
			// 今年件次thisYearFileTotal
			int thisYearFileTotal = 0;

			int start = 0;
			int limit = 0;

			QueryFilter fondTotalFilter = new QueryFilter(getRequest());
			fondTotalFilter.getPagingBean().setStart(start);
			fondTotalFilter.getPagingBean().setPageSize(limit);
			fondTotalFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			archFondService.getAll(fondTotalFilter);
			fondTotal = fondTotalFilter.getPagingBean().getTotalItems();

			QueryFilter rollTotalFilter = new QueryFilter(getRequest());
			rollTotalFilter.getPagingBean().setStart(start);
			rollTotalFilter.getPagingBean().setPageSize(limit);
			rollTotalFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			archRollService.getAll(rollTotalFilter);
			rollTotal = rollTotalFilter.getPagingBean().getTotalItems();

			QueryFilter fileTotalFilter = new QueryFilter(getRequest());
			fileTotalFilter.getPagingBean().setStart(start);
			fileTotalFilter.getPagingBean().setPageSize(limit);
			fileTotalFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			rollFileService.getAll(fileTotalFilter);
			fileTotal = fileTotalFilter.getPagingBean().getTotalItems();

			QueryFilter thisYearRollTotalFilter = new QueryFilter(getRequest());
			thisYearRollTotalFilter.getPagingBean().setStart(start);
			thisYearRollTotalFilter.getPagingBean().setPageSize(limit);
			thisYearRollTotalFilter
					.addFilter("Q_createTime_D_GE", yearStartStr);
			thisYearRollTotalFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			archRollService.getAll(thisYearRollTotalFilter);
			thisYearRollTotal = thisYearRollTotalFilter.getPagingBean()
					.getTotalItems();

			QueryFilter thisYearFileTotalFilter = new QueryFilter(getRequest());
			thisYearFileTotalFilter.getPagingBean().setStart(start);
			thisYearFileTotalFilter.getPagingBean().setPageSize(limit);
			thisYearFileTotalFilter
					.addFilter("Q_createTime_D_GE", yearStartStr);
			thisYearFileTotalFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			rollFileService.getAll(thisYearFileTotalFilter);
			thisYearFileTotal = thisYearFileTotalFilter.getPagingBean()
					.getTotalItems();

			Map data = new HashMap();
			data.put("fondTotal", fondTotal);
			data.put("archTotal", (rollTotal + fileTotal));
			data.put("rollTotal", rollTotal);
			data.put("fileTotal", fileTotal);
			data.put("thisYearRollTotal", thisYearRollTotal);
			data.put("thisYearFileTotal", thisYearFileTotal);

			StringBuffer sb = new StringBuffer("{success:true,data:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			sb.append(gson.toJson(data));
			sb.append("}");
			setJsonString(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String yearReportFile() {
		try {
			java.text.SimpleDateFormat yearToDate = new SimpleDateFormat("yyyy");
			java.text.SimpleDateFormat yearToString = new SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Date yearStart = yearToDate.parse(year);
			long afterTime = (yearStart.getTime() / 1000) + 60 * 60 * 24 * 364;
			java.util.Date yearEnd = new java.util.Date(afterTime * 1000);
			String yearStartStr = yearToString.format(yearStart);
			String yearEndStr = yearToString.format(yearEnd);
			//全宗
			QueryFilter fondFilter = new QueryFilter(getRequest());
			fondFilter.addFilter("Q_createTime_D_GE", yearStartStr);
			fondFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			List<ArchFond> fondList = archFondService.getAll(fondFilter);
			//数据字典
			//logger.debug("itemName="+itemName);
			List<Dictionary> itemList= dictionaryService.getByItemName(itemName);
			//文件数量
			int start = 0;
			int limit = 0;
			List<Map> list = new ArrayList();
			for(ArchFond fond:fondList){//行
				Map fond_Map=new HashMap();
				fond_Map.put("afNo", fond.getAfNo());//第0列：全宗号
				for(Dictionary d:itemList){
							//第i列,itemName
					
					QueryFilter thisYearFileTotalFilter = new QueryFilter(getRequest());
					thisYearFileTotalFilter.getPagingBean().setStart(start);
					thisYearFileTotalFilter.getPagingBean().setPageSize(limit);
					thisYearFileTotalFilter
							.addFilter("Q_createTime_D_GE", yearStartStr);
					thisYearFileTotalFilter.addFilter("Q_createTime_D_LE", yearEndStr);//日期
					thisYearFileTotalFilter.addFilter("Q_timeLimit_S_LK", d.getItemValue());//数据字典
					thisYearFileTotalFilter.addFilter("Q_archRoll.archFondId_L_EQ", fond.getArchFondId().toString());//全宗号
					rollFileService.getAll(thisYearFileTotalFilter);
					int thisYearAndDicFileTotal = thisYearFileTotalFilter.getPagingBean()
							.getTotalItems();
					fond_Map.put(d.getDicId().toString(), thisYearAndDicFileTotal);
					
				}
				list.add(fond_Map);
			}
			
			
			
			
			
			
			
			

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(fondFilter.getPagingBean().getTotalItems()).append(
							",result:");

			Gson gson = new Gson();

			buff.append(gson.toJson(list));

			buff.append("}");
			jsonString=buff.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;

	}
	

	public String yearReportTidy() {
		try {
			java.text.SimpleDateFormat yearToDate = new SimpleDateFormat("yyyy");
			java.text.SimpleDateFormat yearToString = new SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Date yearStart = yearToDate.parse(year);
			long afterTime = (yearStart.getTime() / 1000) + 60 * 60 * 24 * 364;
			java.util.Date yearEnd = new java.util.Date(afterTime * 1000);
			String yearStartStr = yearToString.format(yearStart);
			String yearEndStr = yearToString.format(yearEnd);
			//全宗
			QueryFilter fondFilter = new QueryFilter(getRequest());
			fondFilter.addFilter("Q_createTime_D_GE", yearStartStr);
			fondFilter.addFilter("Q_createTime_D_LE", yearEndStr);
			List<ArchFond> fondList = archFondService.getAll(fondFilter);
			//数据字典
			//logger.debug("itemName="+itemName);
			List<Dictionary> itemList= dictionaryService.getByItemName(itemName);
			//文件数量
			int start = 0;
			int limit = 0;
			List<Map> list = new ArrayList();
			for(ArchFond fond:fondList){//行
				Map fond_Map=new HashMap();
				fond_Map.put("afNo", fond.getAfNo());//第0列：全宗号
				boolean add=false;
				for(Dictionary d:itemList){
							//第i列,itemName
					
					QueryFilter thisYearFileTotalFilter = new QueryFilter(getRequest());
					thisYearFileTotalFilter.getPagingBean().setStart(start);
					thisYearFileTotalFilter.getPagingBean().setPageSize(limit);
					thisYearFileTotalFilter
							.addFilter("Q_tidyTime_D_GE", yearStartStr);
					thisYearFileTotalFilter.addFilter("Q_tidyTime_D_LE", yearEndStr);//日期
					thisYearFileTotalFilter.addFilter("Q_timeLimit_S_LK", d.getItemValue());//数据字典
					thisYearFileTotalFilter.addFilter("Q_archRoll.archFondId_L_EQ", fond.getArchFondId().toString());//全宗号
					thisYearFileTotalFilter.addFilter("Q_archStatus_SN_EQ", "1");//归档
					
					rollFileService.getAll(thisYearFileTotalFilter);
					int thisYearAndDicFileTotal = thisYearFileTotalFilter.getPagingBean()
							.getTotalItems();
					fond_Map.put(d.getDicId().toString(), thisYearAndDicFileTotal);
					if(thisYearAndDicFileTotal>0)add=true;
					
				}
				if(add)
				list.add(fond_Map);
			}
			
			
			
			
			
			
			
			

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(fondFilter.getPagingBean().getTotalItems()).append(
							",result:");

			Gson gson = new Gson();

			buff.append(gson.toJson(list));

			buff.append("}");
			jsonString=buff.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;

	}
	
	public String yearReportBorrowMain() {//文件数量
		int start = 0;
		int limit = 0;
		
		QueryFilter filter = new QueryFilter(getRequest());
		filter.getPagingBean().setStart(start);
		filter.getPagingBean().setPageSize(limit);
		borrowFileListService.getAll(filter);
		
		int totalCount=filter.getPagingBean().getTotalItems();
		
		Map data = new HashMap();
		data.put("totalCount", totalCount);
		
		StringBuffer sb = new StringBuffer("{success:true,data:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		sb.append(gson.toJson(data));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;}
	
	public String yearReportBorrowYear() {//
		try{
			java.text.SimpleDateFormat yearToDate = new SimpleDateFormat("yyyy");
			java.text.SimpleDateFormat yearToString = new SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Date yearStart = yearToDate.parse(year);
			long afterTime = (yearStart.getTime() / 1000) + 60 * 60 * 24 * 364;
			java.util.Date yearEnd = new java.util.Date(afterTime * 1000);
			String yearStartStr = yearToString.format(yearStart);
			String yearEndStr = yearToString.format(yearEnd);
			
			int start = 0;
			int limit = 0;
			
			QueryFilter totalCountFilter = new QueryFilter(getRequest());
			totalCountFilter.getPagingBean().setStart(start);
			totalCountFilter.getPagingBean().setPageSize(limit);
			totalCountFilter
			.addFilter("Q_borrowRecord.borrowDate_D_GE", yearStartStr);
			totalCountFilter.addFilter("Q_borrowRecord.borrowDate_D_LE", yearEndStr);
			borrowFileListService.getAll(totalCountFilter);
			int totalCount=totalCountFilter.getPagingBean().getTotalItems();
			
			QueryFilter rollTotalFilter = new QueryFilter(getRequest());
			rollTotalFilter.getPagingBean().setStart(start);
			rollTotalFilter.getPagingBean().setPageSize(limit);
			rollTotalFilter
			.addFilter("Q_borrowRecord.borrowDate_D_GE", yearStartStr);
			rollTotalFilter.addFilter("Q_borrowRecord.borrowDate_D_LE", yearEndStr);
			rollTotalFilter.addFilter("Q_listType_S_EQ", "案卷");
			borrowFileListService.getAll(rollTotalFilter);
			int rollTotal=rollTotalFilter.getPagingBean().getTotalItems();
			
			
			QueryFilter fileTotalFilter = new QueryFilter(getRequest());
			fileTotalFilter.getPagingBean().setStart(start);
			fileTotalFilter.getPagingBean().setPageSize(limit);
			fileTotalFilter
			.addFilter("Q_borrowRecord.borrowDate_D_GE", yearStartStr);
			fileTotalFilter.addFilter("Q_borrowRecord.borrowDate_D_LE", yearEndStr);
			fileTotalFilter.addFilter("Q_listType_S_EQ", "文件");
			borrowFileListService.getAll(fileTotalFilter);
			int fileTotal=fileTotalFilter.getPagingBean().getTotalItems();
			
			
			
			
			
			
			
			Map data = new HashMap();
			data.put("totalCount", ""+totalCount+"");
			data.put("rollTotal", rollTotal);data.put("fileTotal", ""+fileTotal+"");
			
			StringBuffer sb = new StringBuffer("{success:true,data:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			sb.append(gson.toJson(data));
			sb.append("}");
			setJsonString(sb.toString());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return SUCCESS;}
	
	public String yearReportBorrowDetail() {
		try {
			java.text.SimpleDateFormat yearToDate = new SimpleDateFormat("yyyy");
			java.text.SimpleDateFormat yearToString = new SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Date yearStart = yearToDate.parse(year);
			long afterTime = (yearStart.getTime() / 1000) + 60 * 60 * 24 * 364;
			java.util.Date yearEnd = new java.util.Date(afterTime * 1000);
			String yearStartStr = yearToString.format(yearStart);
			String yearEndStr = yearToString.format(yearEnd);
			//数据字典
			//itemName=new String(itemName.getBytes("ISO-8859-1"), "gb2312");
			logger.debug(itemName);
			List<Dictionary> itemList= dictionaryService.getByItemName(itemName);
			int start = 0;
			int limit = 0;
			Map map=new HashMap();
			for(Dictionary d:itemList){
				
				
				QueryFilter rollTotalFilter = new QueryFilter(getRequest());
				rollTotalFilter.getPagingBean().setStart(start);
				rollTotalFilter.getPagingBean().setPageSize(limit);
				rollTotalFilter.addFilter("Q_borrowRecord.borrowDate_D_GE", yearStartStr);
				rollTotalFilter.addFilter("Q_borrowRecord.borrowDate_D_LE", yearEndStr);
				rollTotalFilter.addFilter("Q_listType_S_EQ", "案卷");
				rollTotalFilter.addFilter("Q_borrowRecord.borrowReason_S_EQ", d.getItemValue());
				
				borrowFileListService.getAll(rollTotalFilter);
				int rollTotal=rollTotalFilter.getPagingBean().getTotalItems();
				
				
				QueryFilter fileTotalFilter = new QueryFilter(getRequest());
				fileTotalFilter.getPagingBean().setStart(start);
				fileTotalFilter.getPagingBean().setPageSize(limit);
				fileTotalFilter.addFilter("Q_borrowRecord.borrowDate_D_GE", yearStartStr);
				fileTotalFilter.addFilter("Q_borrowRecord.borrowDate_D_LE", yearEndStr);
				fileTotalFilter.addFilter("Q_listType_S_EQ", "文件");
				fileTotalFilter.addFilter("Q_borrowRecord.borrowReason_S_EQ", d.getItemValue());
				borrowFileListService.getAll(fileTotalFilter);
				int fileTotal=fileTotalFilter.getPagingBean().getTotalItems();
				
				
				map.put("rollTotal"+d.getDicId(), rollTotal);
				map.put("fileTotal"+d.getDicId(), fileTotal);
				
				
			}
			
			
			
			
			
			

			StringBuffer buff = new StringBuffer("{success:true")
					.append(
							",data:");

			Gson gson = new Gson();

			buff.append(gson.toJson(map));

			buff.append("}");
			jsonString=buff.toString();
			logger.debug("jsonString="+jsonString);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;

	}
	
	public String rollReportByFond(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ArchFond> fondList= archFondService.getAll(filter);
		List<Map> list = new ArrayList<Map>();
		
		for(ArchFond af:fondList){
			
			int caseNums=af.getArchRolls().size();
			af.setCaseNums(caseNums);
			if(af.getCaseNums()==null||af.getCaseNums().equals("")){
				archFondService.save(af);
			}
			Map m=new HashMap();
			m.put("name", af.getAfNo());
			m.put("num", caseNums);
			list.add(m);
			
		}
		
		
		Type type=new TypeToken<List<ArchFond>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"createTime","updateTime"});
		buff.append(json.serialize(list));
		
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	
	public String rollReportByTimeLimit(){
		logger.debug("itemName="+itemName);
		
		List<Dictionary> itemList= dictionaryService.getByItemName(itemName);
		int start = 0;
		int limit = 0;
		List list = new ArrayList();
		int d_total=0;
		for(Dictionary d:itemList){
			QueryFilter rollTotalFilter = new QueryFilter(getRequest());
			rollTotalFilter.getPagingBean().setStart(start);
			rollTotalFilter.getPagingBean().setPageSize(limit);
			rollTotalFilter.addFilter("Q_timeLimit_S_LK", d.getItemValue().trim());
			
			archRollService.getAll(rollTotalFilter);
			int rollTotal=rollTotalFilter.getPagingBean().getTotalItems();
			d_total=d_total+rollTotal;
			Map map=new HashMap();
			map.put("name", d.getItemValue());
			map.put("num", rollTotal);
			map.put("isTotal", false);
			list.add(map);
		}
		
		
		QueryFilter allFilter = new QueryFilter(getRequest());
		allFilter.getPagingBean().setStart(start);
		allFilter.getPagingBean().setPageSize(limit);
		archRollService.getAll(allFilter);
		Map map=new HashMap();
		map.put("name", "其它");
		map.put("num", (allFilter.getPagingBean().getTotalItems()-d_total));
		map.put("isTotal", false);
		list.add(map);
		
		
		

		StringBuffer buff = new StringBuffer("{success:true")
				.append(
						",result:");

		Gson gson = new Gson();

		buff.append(gson.toJson(list));

		buff.append("}");
		jsonString=buff.toString();
		
		
		
		return SUCCESS;}
	
	
	public String fileReportByRoll(){
		List<Map> allList=new ArrayList<Map>();
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ArchRoll> list= archRollService.getAll(filter);
		for(ArchRoll ar:list){
			int fileNums=ar.getRollFiles().size();
			
			
			Map m= new HashMap();
			m.put("name", ar.getRollNo());
			m.put("nums", fileNums);
			m.put("isTotal", false);
			allList.add(m);
		}
		
		
		int start = 0;
		int limit = 0;
		
		QueryFilter fileFilter = new QueryFilter(getRequest());
		fileFilter.getPagingBean().setStart(start);
		fileFilter.getPagingBean().setPageSize(limit);
		fileFilter.addFilter("Q_archRoll_NULL", "");
		rollFileService.getAll(fileFilter);
		
		Map m= new HashMap();
		m.put("name", "其它");
		m.put("nums", fileFilter.getPagingBean().getTotalItems());
		m.put("isTotal", false);
		allList.add(m);
	
		
		
		
		Type type=new TypeToken<List<ArchFond>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
			"createTime", "updateTime","setupTime","endTime","startTime" });
		buff.append(json.serialize(allList));
		
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	public String fileReportByTimeLimit(){
		logger.debug("itemName="+itemName);
		
		List<Dictionary> itemList= dictionaryService.getByItemName(itemName);
		int start = 0;
		int limit = 0;
		List list = new ArrayList();
		int d_total=0;
		for(Dictionary d:itemList){
			Map map=new HashMap();
				
			
			QueryFilter rollTotalFilter = new QueryFilter(getRequest());
			rollTotalFilter.getPagingBean().setStart(start);
			rollTotalFilter.getPagingBean().setPageSize(limit);
			rollTotalFilter.addFilter("Q_timeLimit_S_LK", d.getItemValue().trim());
			
			rollFileService.getAll(rollTotalFilter);
			int rollTotal=rollTotalFilter.getPagingBean().getTotalItems();
			d_total=d_total+rollTotal;
			map.put("name", d.getItemValue());
			map.put("nums", rollTotal);
			map.put("isTotal", false);
			list.add(map);
		}
		
		QueryFilter allFilter = new QueryFilter(getRequest());
		allFilter.getPagingBean().setStart(start);
		allFilter.getPagingBean().setPageSize(limit);
		rollFileService.getAll(allFilter);
		Map map=new HashMap();
		map.put("name", "其它");
		map.put("nums", (allFilter.getPagingBean().getTotalItems()-d_total));
		map.put("isTotal", false);
		list.add(map);
		
		
		
		

		StringBuffer buff = new StringBuffer("{success:true")
				.append(
						",result:");

		Gson gson = new Gson();

		buff.append(gson.toJson(list));

		buff.append("}");
		jsonString=buff.toString();
		
		
		
		return SUCCESS;}
	
	
	
}
