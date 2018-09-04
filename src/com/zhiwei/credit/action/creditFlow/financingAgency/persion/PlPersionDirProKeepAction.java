package com.zhiwei.credit.action.creditFlow.financingAgency.persion;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.StringUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.PlPersionDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepCreditlevelService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.util.Common;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlPersionDirProKeepAction extends BaseAction{
	@Resource
	private PlPersionDirProKeepService plPersionDirProKeepService;
	@Resource
	private BpPersionDirProService bpPersionDirProService;
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private PersonService personService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private PlKeepCreditlevelService plKeepCreditlevelService;
	private PlPersionDirProKeep plPersionDirProKeep;
	
	
	@Resource
	private FileFormService fileFormService;
	public String tablename;//表名
	public File myUpload;//上传的文件信息
	public String extendname;//文件后缀名
	public String setname;//使用 类型
	public Integer creatorid;//创建人
	public String  proIdupload;//标的主键
	//songwj
	@Resource
	private FTPIoadFileService fTPIoadFileService;
	
	private Long keepId;

	public Long getKeepId() {
		return keepId;
	}

	public void setKeepId(Long keepId) {
		this.keepId = keepId;
	}

	public PlPersionDirProKeep getPlPersionDirProKeep() {
		return plPersionDirProKeep;
	}

	public void setPlPersionDirProKeep(PlPersionDirProKeep plPersionDirProKeep) {
		this.plPersionDirProKeep = plPersionDirProKeep;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlPersionDirProKeep> list= plPersionDirProKeepService.getAll(filter);
		

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("createDate",
				"updateDate");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
		
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
				plPersionDirProKeepService.remove(new Long(id));
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
		PlPersionDirProKeep plPersionDirProKeep=plPersionDirProKeepService.get(keepId);
		//针对已维护的没有用户名的信息补充
		if(null!=plPersionDirProKeep.getBpPersionDirPro() && !"".equals(plPersionDirProKeep.getBpPersionDirPro())){
			BpPersionDirPro  bpPersionDirPro=plPersionDirProKeep.getBpPersionDirPro();
			 Long personId=bpPersionDirPro.getPersionId();
			 Person person=personService.getById(Integer.valueOf(personId.toString()));
			if(null==bpPersionDirPro.getUserName() || "".equals(bpPersionDirPro.getUserName())){
			    BpCustRelation relation=bpCustRelationService.getByLoanTypeAndId("p_loan", personId);
			    if(null!=relation && !"".equals(relation)){
				    BpCustMember member=bpCustMemberService.get(relation.getP2pCustId());
				    bpPersionDirPro.setUserName(member.getLoginname());
				    }
			     }
			if(null==bpPersionDirPro.getAge()|| "".equals(bpPersionDirPro.getAge())){
				bpPersionDirPro.setAge(person.getAge());
			}
			if(null==bpPersionDirPro.getSex()|| "".equals(bpPersionDirPro.getSex())){
				if(null != person.getSex()){
					bpPersionDirPro.setSex(dictionaryService.get(Long.valueOf(person.getSex())).getItemValue());//性别
				}
			}
			if(null==bpPersionDirPro.getMarriage()|| "".equals(bpPersionDirPro.getMarriage())){
				if(null!=person.getMarry() && !"".equals(person.getMarry())){
				Dictionary dictionary2=dictionaryService.get(Long.valueOf(person.getMarry()));
				if(null!=dictionary2){
					bpPersionDirPro.setMarriage(dictionary2.getItemValue());//婚姻
				}
				}
			}
			if(null==bpPersionDirPro.getEducation()|| "".equals(bpPersionDirPro.getEducation())){
				if(null!=person.getDgree() && !"".equals(person.getDgree())){
				Dictionary dictionary1=dictionaryService.get(Long.valueOf(person.getDgree()));
				if(null!=dictionary1){
					bpPersionDirPro.setEducation(dictionary1.getItemValue());//学历
				}
			 }
			}
			if(null==bpPersionDirPro.getMonthIncome()|| "".equals(bpPersionDirPro.getMonthIncome())){
				if(person.getJobincome()!=null){//月收入
					bpPersionDirPro.setMonthIncome(new BigDecimal(person.getJobincome().toString()));
				}
			}
			if(null==bpPersionDirPro.getAddress()|| "".equals(bpPersionDirPro.getAddress())){
				bpPersionDirPro.setAddress(person.getFamilyaddress());//现居住地
			}
			if(null==bpPersionDirPro.getWorkTime()|| "".equals(bpPersionDirPro.getWorkTime())){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				if(person.getJobstarttime()==null||"".equals(person.getJobstarttime())){
					bpPersionDirPro.setWorkTime(null);//工作时间
				}else{
					bpPersionDirPro.setWorkTime(format.format(person.getJobstarttime()));//工作时间
				}
			}
			if(null==bpPersionDirPro.getWorkCity()|| "".equals(bpPersionDirPro.getWorkCity())){
				bpPersionDirPro.setWorkCity(person.getUnitaddress());//工作城市
			}
			if(null==bpPersionDirPro.getCompanyIndustry()|| "".equals(bpPersionDirPro.getCompanyIndustry())){
				bpPersionDirPro.setCompanyIndustry(person.getHangyeName());//行业
			}
			if(null==bpPersionDirPro.getCompanyScale()|| "".equals(bpPersionDirPro.getCompanyScale())){
				if(null !=person.getCompanyScale()&&!"".equals(person.getCompanyScale()) && !"null".equals(person.getCompanyScale())){
					Dictionary dictionary=dictionaryService.get(Long.valueOf(person.getCompanyScale()));
					bpPersionDirPro.setCompanyScale(dictionary.getItemValue());//公司规模
				}
			}
			if(null==bpPersionDirPro.getPosition() || "".equals(bpPersionDirPro.getPosition())){
				if(null!=person.getJob()){
					Dictionary dictionary3=dictionaryService.get(Long.valueOf(person.getJob()));
					if(null!=dictionary3){
						bpPersionDirPro.setPosition(dictionary3.getItemValue());//职务
					}
				}
			}
		    bpPersionDirProService.merge(bpPersionDirPro);
		    plPersionDirProKeep.setBpPersionDirPro(bpPersionDirPro);
		}
		if(null!=plPersionDirProKeep.getBpPersionOrPro() && !"".equals(plPersionDirProKeep.getBpPersionOrPro()) ){
			BpPersionOrPro bpPersionOrPro=plPersionDirProKeep.getBpPersionOrPro();
			 Long personId=bpPersionOrPro.getPersionId();
			 Person person=personService.getById(Integer.valueOf(personId.toString()));
			if(null==bpPersionOrPro.getUserName() || "".equals(bpPersionOrPro.getUserName())){
			    BpCustRelation relation=bpCustRelationService.getByLoanTypeAndId("p_loan", personId);
			    if(null!=relation && !"".equals(relation)){
				    BpCustMember member=bpCustMemberService.get(relation.getP2pCustId());
				    bpPersionOrPro.setUserName(member.getLoginname());
			       }
			     }
			if(null==bpPersionOrPro.getAge()||"".equals(bpPersionOrPro.getAge())){
				bpPersionOrPro.setAge(person.getAge());
			}
			if(null==bpPersionOrPro.getSex()||"".equals(bpPersionOrPro.getSex())){
				if(null != person.getSex()){
					bpPersionOrPro.setSex(dictionaryService.get(Long.valueOf(person.getSex())).getItemValue());//性别
				}
			}
			if(null==bpPersionOrPro.getMarriage()||"".equals(bpPersionOrPro.getMarriage())){
				if(null!=person.getMarry() && !"".equals(person.getMarry())){
				Dictionary dictionary2=dictionaryService.get(Long.valueOf(person.getMarry()));
				if(null!=dictionary2){
					bpPersionOrPro.setMarriage(dictionary2.getItemValue());//婚姻
				}
			 }
			}
			if(null==bpPersionOrPro.getEducation()||"".equals(bpPersionOrPro.getEducation())){
				if(null!=person.getDgree() && !"".equals(person.getDgree())){
				Dictionary dictionary1=dictionaryService.get(Long.valueOf(person.getDgree()));
				if(null!=dictionary1){
					bpPersionOrPro.setEducation(dictionary1.getItemValue());//学历
				}
			 }
			}
			if(null==bpPersionOrPro.getMonthIncome()||"".equals(bpPersionOrPro.getMonthIncome())){
				if(person.getJobincome()!=null){//月收入
					bpPersionOrPro.setMonthIncome(new BigDecimal(person.getJobincome().toString()));
				}
			}
			if(null==bpPersionOrPro.getAddress()||"".equals(bpPersionOrPro.getAddress())){
				bpPersionOrPro.setAddress(person.getFamilyaddress());//现居住地
			}
			if(null==bpPersionOrPro.getWorkTime()||"".equals(bpPersionOrPro.getWorkTime())){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				if(person.getJobstarttime()==null||"".equals(person.getJobstarttime())){
					bpPersionOrPro.setWorkTime(null);//工作时间
				}else{
					bpPersionOrPro.setWorkTime(format.format(person.getJobstarttime()));//工作时间
				}
			}
			if(null==bpPersionOrPro.getWorkCity()||"".equals(bpPersionOrPro.getWorkCity())){
				bpPersionOrPro.setWorkCity(person.getUnitaddress());//工作城市
			}
			if(null==bpPersionOrPro.getCompanyIndustry()||"".equals(bpPersionOrPro.getCompanyIndustry())){
				bpPersionOrPro.setCompanyIndustry(person.getHangyeName());//行业
			}
			if(null==bpPersionOrPro.getCompanyScale()||"".equals(bpPersionOrPro.getCompanyScale())){
				if(null !=person.getCompanyScale()&&!"".equals(person.getCompanyScale()) && !"null".equals(person.getCompanyScale())){
					Dictionary dictionary=dictionaryService.get(Long.valueOf(person.getCompanyScale()));
					bpPersionOrPro.setCompanyScale(dictionary.getItemValue());//公司规模
				}
			}
			if(null==bpPersionOrPro.getPosition()||"".equals(bpPersionOrPro.getPosition())){
				if(null!=person.getJob()){
					Dictionary dictionary3=dictionaryService.get(Long.valueOf(person.getJob()));
					if(null!=dictionary3){
						bpPersionOrPro.setPosition(dictionary3.getItemValue());//职务
					}
				}
			}
			  bpPersionOrProService.merge(bpPersionOrPro);
			  plPersionDirProKeep.setBpPersionOrPro(bpPersionOrPro);
		}
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer( "createDate",
				"updateDate") ;
		sb.append(serializer.exclude(new String[] { "class" }).serialize(
				plPersionDirProKeep));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	public String getInfo(){
		try{
			String type=this.getRequest().getParameter("type");
			String id=this.getRequest().getParameter("id");
			PlPersionDirProKeep keep=plPersionDirProKeepService.getByType(type, Long.valueOf(id));
			jsonString="{success:true,keepId:"+keep.getKeepId()+"}";
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String proType=this.getRequest().getParameter("proType");
		String bpPersonId=this.getRequest().getParameter("bpPersonId");
		Long proLevelId = Long.valueOf(this.getRequest().getParameter(
		"proLevelId"));
		plPersionDirProKeep = setProPack(plPersionDirProKeep);
		PlPersionDirProKeep dirKeep=null;
		//防止页面刷新失败，在数据库插入相同的数据 start
		if(null != proType && !"".equals(proType) && null != bpPersonId && !"".equals(bpPersonId)){
			if(proType.equals("P_Dir")){
				dirKeep = plPersionDirProKeepService.getByType("P_Dir", Long.valueOf(bpPersonId));
				
			}else if(proType.equals("P_Or")){
				dirKeep = plPersionDirProKeepService.getByType("P_Or", Long.valueOf(bpPersonId));
			}
			
		}
		if(null == dirKeep){
			dirKeep = new PlPersionDirProKeep();
		}
		try {
			BeanUtil.copyNotNullProperties(dirKeep, plPersionDirProKeep);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//end
		if(dirKeep.getKeepId()==null){
			dirKeep.setCreateDate(new Date());
			dirKeep.setUpdateDate(new Date());
			if(null!=proLevelId && !"".equals(proLevelId)){
				PlKeepCreditlevel pl=plKeepCreditlevelService.get(proLevelId);
				dirKeep.setPlKeepCreditlevel(pl);
			}
			plPersionDirProKeepService.save(dirKeep);
			updateProState(dirKeep.getProType());
		}else{
			PlPersionDirProKeep orgPlPersionDirProKeep=plPersionDirProKeepService.get(plPersionDirProKeep.getKeepId());
			if(null!=proLevelId && !"".equals(proLevelId)){
				PlKeepCreditlevel pl=plKeepCreditlevelService.get(proLevelId);
				orgPlPersionDirProKeep.setPlKeepCreditlevel(pl);
			}
			
			try{
				BeanUtil.copyNotNullProperties(orgPlPersionDirProKeep, plPersionDirProKeep);
				plPersionDirProKeepService.save(orgPlPersionDirProKeep);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	// 设置pack 保存和更新的值
	public PlPersionDirProKeep setProPack(PlPersionDirProKeep pack) {
		Long proTypeId = Long.valueOf(this.getRequest().getParameter(
				"proTypeId"));
		Long proLevelId = Long.valueOf(this.getRequest().getParameter(
				"proLevelId"));
		// 抵质押 展示字符串
		String llimitsIds = this.getRequest().getParameter("llimitsIds");
		// 保证担保 展示字符串
		String levelIds = this.getRequest().getParameter("levelIds");
		// 贷款材料清单 展示字符串
		String materialsIds = this.getRequest().getParameter("materialsIds");
		if (materialsIds.length() > 0) {
			pack.setProLoanMaterShow(materialsIds.substring(0, materialsIds
					.length() - 1));
		} else {
			pack.setProLoanMaterShow(materialsIds);
		}
		if (levelIds.length() > 0) {
			pack.setProLoanLevelShow(levelIds.substring(0,
					levelIds.length() - 1));
		} else {
			pack.setProLoanLevelShow(levelIds);
		}
		if (llimitsIds.length() > 0) {
			pack.setProLoanLlimitsShow(llimitsIds.substring(0, llimitsIds
					.length() - 1));
		} else {
			pack.setProLoanLlimitsShow(llimitsIds);
		}
	//	pack.setCreditLevelId(proLevelId);
	
		
		pack.setTypeId(proTypeId);

		return pack;
	}

	// 更新贷款项目状态为已经打包项目 设置为1
	public void updateProState(String proType) {
		// 更新贷款项目状态为已经打包项目 设置为1
		if (proType.equals("P_Dir")) {
			BpPersionDirPro sl = bpPersionDirProService
					.get(plPersionDirProKeep.getPDirProId());
			sl.setKeepStat(1);
			bpPersionDirProService.save(sl);
		} else if (proType.equals("P_Or")) {
			BpPersionOrPro sl = bpPersionOrProService
					.get(plPersionDirProKeep.getPOrProId());
			sl.setKeepStat(1);
			bpPersionOrProService.save(sl);
		}
	}
	
	//贷款材料上传
	public String upLoadFiles(){
		//判断文件上传的类型
		 extendname = StringUtil.checkExtName(extendname);
		 boolean extType = StringUtil.checkExtType(extendname);
		if(extType){
			String tablenameFirst = tablename;//获得第一个表的名称
			String tablenameTwo = "";
			String appointFileSetFolder ="subjectLogo";//指定文件存放的路径
			String selectId =Common.getRandomNum(32).toString();
			//查询原来logo的数据集合
			@SuppressWarnings("unused") 
			Map<String ,String > map = new  HashMap<String ,String >();
			map=fTPIoadFileService.ftpUploadFile(myUpload,appointFileSetFolder, tablenameFirst,tablenameTwo, proIdupload, extendname, setname, creatorid);
			
			String  mark = map.get("mark");
			String 	field = map.get("filedId");
			if(field!=null  && !"".equals(field)){
				List<FileForm> list = fileFormService.listByMark(mark);//查询所有的含有相同的mark的路径值
				//由于标的logo只有一个 再上传logo后要删除原来的logo的路径
				if(list!= null ){
					for(int i=0;i<list.size();i++){
						if(! field.equals(list.get(i).getFileid().toString())){
							fileFormService.remove(list.get(i));
						}
					}  
				}
			}
		}
		return SUCCESS;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public File getMyUpload() {
		return myUpload;
	}

	public void setMyUpload(File myUpload) {
		this.myUpload = myUpload;
	}

	public String getExtendname() {
		return extendname;
	}

	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}

	public String getSetname() {
		return setname;
	}

	public void setSetname(String setname) {
		this.setname = setname;
	}

	public Integer getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}

	public String getProIdupload() {
		return proIdupload;
	}

	public void setProIdupload(String proIdupload) {
		this.proIdupload = proIdupload;
	}
}
