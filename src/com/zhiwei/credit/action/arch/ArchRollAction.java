package com.zhiwei.credit.action.arch;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.arch.ArchRoll;
import com.zhiwei.credit.model.arch.BorrowRecord;
import com.zhiwei.credit.model.arch.RollFile;
import com.zhiwei.credit.model.arch.RollFileList;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.arch.ArchRollService;
import com.zhiwei.credit.service.arch.BorrowFileListService;
import com.zhiwei.credit.service.arch.BorrowRecordService;
import com.zhiwei.credit.service.arch.RollFileListService;
import com.zhiwei.credit.service.arch.RollFileService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author
 * 
 */
public class ArchRollAction extends BaseAction {
	@Resource
	private ArchRollService archRollService;//案卷
	@Resource
	private RollFileService rollFileService;//文件
	@Resource
	private RollFileListService rollFileListService;//附件
	@Resource
	private FileAttachService fileAttachService;//硬盘件
	@Resource
	private BorrowRecordService borrowRecordService;//借阅
	@Resource
	private BorrowFileListService borrowFileListService;//借阅
	
	private ArchRoll archRoll;

	private Long rollId;

	public Long getRollId() {
		return rollId;
	}

	public void setRollId(Long rollId) {
		this.rollId = rollId;
	}

	public ArchRoll getArchRoll() {
		return archRoll;
	}

	public void setArchRoll(ArchRoll archRoll) {
		this.archRoll = archRoll;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<ArchRoll> list = archRollService.getAll(filter);

		Type type = new TypeToken<List<ArchRoll>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

//		Gson gson = new Gson();
//		buff.append(gson.toJson(list, type));
		JSONSerializer json = new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
				"createTime", "updateTime","setupTime","endTime","startTime" });
		buff.append(json.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				archRoll=archRollService.get(new Long(id));
				java.util.Set borrowFileList_roll = archRoll.getBorrowFileList();
				Iterator borrows_roll =borrowFileList_roll.iterator();//全部借阅
				while (borrows_roll.hasNext()) {
					com.zhiwei.credit.model.arch.BorrowFileList borr_roll=(com.zhiwei.credit.model.arch.BorrowFileList)borrows_roll.next();
					borrowFileListService.remove(borr_roll);
					borrowFileListService.flush();
					//判断登记表 是否还存在文件，没有就删了它
					BorrowRecord record_roll=borr_roll.getBorrowRecord();
					java.util.Set list_roll =record_roll.getBorrowFileLists();
					if(list_roll==null||list_roll.size()==0){
						borrowRecordService.remove(record_roll);
					}
					
					
				}
				
				
				java.util.Set rollFiles = archRoll.getRollFiles();
				Iterator files =rollFiles.iterator();//全部文件
				while (files.hasNext()) {
					RollFile file=(RollFile)files.next();
					
					java.util.Set rollFileLists = file.getRollFileLists(); //全部附件
					Iterator lists = rollFileLists.iterator();
					while (lists.hasNext()) {
						RollFileList list=(RollFileList)lists.next();
						FileAttach fileAttach=list.getFileAttach();  //全部硬件
						rollFileListService.remove(list);
						rollFileListService.flush();
						fileAttachService.removeByPath(fileAttach.getFilePath());
						
					}

					//全部借阅
					//全部借阅
					java.util.Set borrowFileList_file = file.getBorrowFileList();
					Iterator borrows_file =borrowFileList_file.iterator();//全部借阅
					while (borrows_file.hasNext()) {
						com.zhiwei.credit.model.arch.BorrowFileList borr_file=(com.zhiwei.credit.model.arch.BorrowFileList)borrows_file.next();
						borrowFileListService.remove(borr_file);
						borrowFileListService.flush();
						//判断登记表 是否还存在文件，没有就删了它
						BorrowRecord record_file=borr_file.getBorrowRecord();
						java.util.Set list_file =record_file.getBorrowFileLists();
						if(list_file==null||list_file.size()==0){
							borrowRecordService.remove(record_file);
						}
						
						
					}
					rollFileService.remove(file);
					rollFileService.flush();
				}
				archRollService.remove(archRoll);//删除案卷
				archRollService.flush();
				
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		ArchRoll archRoll = archRollService.get(rollId);

		// 将数据转成JSON格式
		
		StringBuffer sb = new StringBuffer("{success:true,data:");
		// Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// sb.append(gson.toJson(archRoll));
		JSONSerializer json = new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
				"createTime", "updateTime","setupTime","endTime","startTime" });

		sb.append(json.serialize(archRoll));

		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (archRoll.getRollId() == null) {
			archRollService.save(archRoll);
		} else {
			ArchRoll orgArchRoll = archRollService.get(archRoll.getRollId());
			try {
				Set rollFileSet=orgArchRoll.getRollFiles();
				Set borrowFileList=orgArchRoll.getBorrowFileList();
				BeanUtil.copyNotNullProperties(orgArchRoll, archRoll);
				orgArchRoll.setRollFiles(rollFileSet);
				orgArchRoll.setBorrowFileList(borrowFileList);
				archRollService.save(orgArchRoll);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}
}
