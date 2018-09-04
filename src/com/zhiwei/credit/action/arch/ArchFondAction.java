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
import com.zhiwei.credit.model.arch.ArchFond;
import com.zhiwei.credit.model.arch.ArchRoll;
import com.zhiwei.credit.model.arch.BorrowRecord;
import com.zhiwei.credit.model.arch.RollFile;
import com.zhiwei.credit.model.arch.RollFileList;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.arch.ArchFondService;
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
public class ArchFondAction extends BaseAction{
	@Resource
	private ArchFondService archFondService;//全宗
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
	
	
	
	private ArchFond archFond;
	
	private Long archFondId;

	public Long getArchFondId() {
		return archFondId;
	}

	public void setArchFondId(Long archFondId) {
		this.archFondId = archFondId;
	}

	public ArchFond getArchFond() {
		return archFond;
	}

	public void setArchFond(ArchFond archFond) {
		this.archFond = archFond;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ArchFond> list= archFondService.getAll(filter);
		
		
		Type type=new TypeToken<List<ArchFond>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		//Gson gson=new Gson();
		//Gson gson=new GsonBuilder().create();
		//buff.append(gson.toJson(list, type));
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"createTime","updateTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 */
	public String listRollTree(){
		
			QueryFilter filter=new QueryFilter(getRequest());
			List<com.zhiwei.credit.model.arch.ArchRoll> rollList = archRollService.getAll(filter);
			if(rollList!=null&&rollList.size()>0){
				ArchRoll archRoll = rollList.get(0);
				
				StringBuffer buff = new StringBuffer("[{id:'0',text:'"+archRoll.getAfNo()+"',expanded:true,children:[");
				
				if (rollList.size()>0) {
					for(ArchRoll roll:rollList){
						
						buff.append("{id:'"+roll.getRollNo()).append("',text:'"+roll.getRollNo()).append("',allowChildren:false,leaf :true},");
					}
					buff.deleteCharAt(buff.length() - 1);
			    }
				
				buff.append("]}]");
				jsonString=buff.toString();
			}
		
		return SUCCESS;
	}
	


	
	
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				archFond=archFondService.get(new Long(id));//全宗
				//全部借阅
				java.util.Set borrowFileList_fond = archFond.getBorrowFileList();
				Iterator borrows_fond =borrowFileList_fond.iterator();//全部借阅
				while (borrows_fond.hasNext()) {
					com.zhiwei.credit.model.arch.BorrowFileList borr_fond=(com.zhiwei.credit.model.arch.BorrowFileList)borrows_fond.next();
					borrowFileListService.remove(borr_fond);
					borrowFileListService.flush();
					//判断登记表 是否还存在文件，没有就删了它
					BorrowRecord record_fond=borr_fond.getBorrowRecord();
					java.util.Set list_fond =record_fond.getBorrowFileLists();
					if(list_fond==null||list_fond.size()==0){
						borrowRecordService.remove(record_fond);
					}
					
					
				}
				
				
				java.util.Set archRolls = archFond.getArchRolls();
				Iterator rolls=archRolls.iterator();//全部案卷
				while (rolls.hasNext()) {
					com.zhiwei.credit.model.arch.ArchRoll archRoll = (com.zhiwei.credit.model.arch.ArchRoll) rolls.next();
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
				archFondService.remove(archFond);//删除全宗
				archFondService.flush();
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ArchFond archFond=archFondService.get(archFondId);
		
		
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		//Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//sb.append(gson.toJson(archFond));
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"createTime","updateTime"});
		sb.append(json.serialize(archFond));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(archFond.getArchFondId()==null){
			archFondService.save(archFond);
		}else{
			ArchFond orgArchFond=archFondService.get(archFond.getArchFondId());
			try{
				Set archRollSet=orgArchFond.getArchRolls();
				Set borrowFileList=orgArchFond.getBorrowFileList();
				BeanUtil.copyNotNullProperties(orgArchFond, archFond);
				orgArchFond.setArchRolls(archRollSet);
				orgArchFond.setBorrowFileList(borrowFileList);
				archFondService.save(orgArchFond);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
