var ownershipDicId = 153 ;//所有制性质
var tradeDicId = 152 ;//行业类别
var cardtypeDicId = 144;//证件类别
var marryDicId = 150;//婚否
var peopletypeDicId = 145 ;//人员类别
var dgreeDicId = 147 ;//学历
var peopletypeDicId = 145 ;//公民类型
var sexDicId = 149;//性别
var techpersonnelDicId = 148;//技术职称
var nationalityDicId = 146;//民族
var chfsDicId = 171;//偿还方式
var cdgDicId = 187;//担保情况
var businessTypeId=6582; //业务类别  
var AffiliatedcompaniesDid = 163;//企业关联关系
var BankATDicId = 158;
var BankAccountTypeDicId = 157; 
var CorporationservicetypeDicId = 206;//企业业务种类
var shareholdertypeDicId = 159; //股东类别
var capitaltypeDicId = 160; //出资方式
var DatatypeDicId = 161 ;//数据类型  如电费，水费==
var unitofquantityDicId = 162;//数量单位
var currencytypeDicId = 169;//币种
var lampbDicId = 189; //担保材料
var assuretypeDicId = 184 ; //担保类型
var Corporationservicetype=206;//业务品种
//var dutyDicId = ;//职务
var CostType = 165;//台账审核预收类型
var shStates = 166;//财务审核
var ActualuseoffundsDicId = 207;//实际贷款用途
var creditkindDicId = 172;//信贷种类
var FpplDicId = 264;//定额贷款保费支付方式
var premchargmodeDicId = 173;//保证金收取方式
var baosiDicId = 179;//银行审批人员意向
var pdofoblDicId = 174;//银行贷款流程可行度
var datumCollectModeDicId = 1122;//材料收集方式
/**反担保字段**/
var personType = 1134;//所有人类型
var Rsc = 191;//登记情况
var Controlmode = 190;//控制权方式
var assuremodeid = 185;//保证方式
var mortgagepersontypeid = 216;//反担保人类型
var commongradevalue = 194;//通用程度
var cashabilityvlalue = 195;//变现能力
var propertyrightid = 223;//产权性质
var constructiontypeid = 197;//建筑式样
var constructionframeid = 198;//建筑结构
var housetypeid = 199;//户型结构
var groundcharactervalue = 200;//教育用地土地性质
var enregisterStatusDic = 1119;//登记状态
var isAuditingPassDic = 1118;//反担保手续是否审验通过
var societynexusvalue = 193;//社会关系
var repaymentway = 171;//还款方式

var capitalTypeDic = 1144;//授信银行资金类型

//车贷
var assuretypeid =184;//担保类型
var Property = 217;//车况
var Personalservicetype=205 ; //个人业务品种
var carcolor = 249;//车辆颜色
var Seats = 271;//车辆座位
var Displacement = 270;//车辆排量
var cardealer = 228;//车辆经销商
var carProperties = 247;//车辆性质
var Losspayee = 276;//保险受益人
var relationtype = 218;//客户关联类型
var relationPersonType = 267;//关系人关系
var unitPropertiesDicId=243;  //单位性质
var degreeDicId=242;   //学位
var employwayDicId = 241;  //占用方式
var positionDicId=245; //职务
var holderofanofficeDicId = 244; //职称 
var homeshapeDicId = 252;  //住宅形式
var projSource = 285; //车贷项目来源
var projSort = 284; //车贷项目类别
var carrepaymentway = 1133;//车贷还款方式
var isHave = 273;//有无
var meetingDicId = 274;//会议意见
var vwtcoaDicId = 151; //个人旗下公司关联关系
var projectfromDicId = 168 ;//项目来源
var selectRelation=286;//关系人调查结果
var checkAnswer = 1143;
var advanceStateDic=288 ;//垫款状态
var advanceRepaymentFormDic=287 ;//还款来源
var bzjgztDic=1137 ;//保中监管状态
var khfxcdDic=1138 ;//客户风险程度
var ApprovalConclusionCarDic=1142;//车贷的审批结论
var isHaveAll = 1243;//文件是否齐全
var chaseSource = 1147;//追偿资金来源

var ApprovalConclusionOneDic = 277 ;//审批结论1
var ApprovalConclusionTwoDic = 278 ;//审批结论2
var ApprovalConclusionThreeDic = 279 ;//审批结论3
var ApprovalConclusionFourDic = 280 ;//审批结论4
var isprojectcontinueDic = 1116;//项目是否可行
var resolutionDicId = 178;//决议方式
var SessionModeDic = 177 ;//会议模式
var DocumentRequirements = 1113;//文件要求
var Itmustbe = 1114;//是否必须有

var entrustGuaranteeContractDicId = 180;  //委托担保合同
var reverseGuaranteeContractDicId = 283;   //反担保合同类型
var documentRequirementsDicId = 1113;   //文件上传——是否必须有
var itmustbeDicId = 1114;               //文件上传——文件要求
var voucherwayDicId = 188;         //凭证方式
var busfactorDicId = 154 ;          //业务影响因素
//var marketareaDicId= 155             //市场区域
var handsOnSurveyDicId = 1148 ;			//调查方式
var dicId_custmorType = 167;//客户类别
var dicId_projType = 1120;//项目类别
var contractType = 1136; // 合同种类

var slbusinesstype='6570';//小额贷项目
var shareDicId = '159'; //股东类别

var ZQHTLX = 133;//展期合同类型

// 数据字典store
var mdictore = new Ext.data.ArrayStore({
	autoLoad : true,
	baseParams : {
		parentId : '6570'
	},
	url : __ctxPath + '/system/getByParentIdDicAreaDynam.do',
	fields : ['itemId', 'itemName']
});

//反担保类型
var mortgageCarDic = 6547;//车辆
var mortgageStockownershipDic = 6546;//股权
var mortgageCompanyDic = 6544;//无限连带责任-公司
var mortgagePersonDic = 6543;//无限连带责任-个人
var mortgageMachineinfoDic = 6545;//机器设备
var mortgageProductDic = 6539;//存货/商品
var mortgageHouseDic = 6534;//住宅
var mortgageOfficebuildingDic = 6538;//商铺写字楼
var mortgageHousegroundDic = 6535;//住宅用地
var mortgageBusinessDic = 6536;//商业用地
var mortgageBusinessandliveDic = 6537;//商住用地
var mortgageEducationDic = 6541;//教育用地
var mortgageIndustryDic = 6540;//工业用地
var mortgageDdroitDic = 6542;//无形权利

/*节---点*/
 var projectSubmitCHINESE = '项目上报';
 var publicAffairsDepartmentAssignProjCHINESE = '公共事务部调配项目';
 var businessDepartmentAssignProjCHINESE = '业务部调配项目';
 var identifyCustomerCHINESE = '区分授信客户';
 var projectManagerSurveyedCHINESE = '项目初步审理';
 var projectManagerSubmitCHINESE = '项目经理上报项目信息';
 var businessDepartmentApproveCHINESE = '业务部审批';
 var RiskManageDepartmentAssignProjCHINESE = '风险管理部调配项目';
 var bankWillCHINESE = '银行房贷意愿';
 var dutyExaminationAppointmentCHINESE = '保前尽职调查时间预约';
 var dutyExaminationConfirmationCHINESE = '保前尽职调查时间确定';
 var risksManagerSubmitCHINESE = '风险经理上报风险信息';
 var projectManagerReplenishCHINESE = '项目经理补充项目信息';
 var risksManageDepartmentApproveCHINESE = '风险管理部审批';
 var prepareArrangementCHINESE = '安排审保会';
 var arrangementApproveCHINESE = '审保会审批';
 var submitGuaranteeIntentCHINESE = '提交担保意向书';
///////////////////////////////
 var ceoApproveCHINESE = '总裁审批';
 var binkApproveCHINESE = '银行审贷会审批';
 var draftAgreementCHINESE = '合同起草 ';
 var checkupAgreementCHINESE = '合同审核';
 var draftAgreementCHINESE = '合同制作';
 var signAgreementCHINESE = '合同签订';
 var registerAgreementCHINESE = '合同登记';
 var counterGuaranteeBookCHINESE = '反担保措施登记';
 var recheckAgreementAndCounterGuaranteeCHINESE = '复核合同和反担保措施';
 var applyGuaranteeCHINESE = '出保申请';
 var recheckGuaranteeCHINESE = '业务部出保复核';
 var risksSupervisorGuaranteeOpinionCHINESE = '风险管理部出保意见';
 var ceoGuaranteeOpinionCHINESE = '总裁出保意见';
 var securityBondConfirmationCHINESE = '担保函盖章确认';
 var projectEndCHINESE = '项目归档';
 var confirmProjectEndCHINESE = '归档确定';


 var PROJECTSUBMIT = 'projectSubmit';
 var PUBLICAFFAIRSDEPARTMENTASSIGNPROJ = 'AffairsDepartmentAssignProj';
 var BUSINESSDEPARTMENTASSIGNPROJ = 'businessDepartmentAssignProj';
 var IDENTIFYCUSTOMER = 'identifyCustomer';
 var PROJECTMANAGERSURVEYED = 'projectManagerSurveyed';
 var PROJECTMANAGERSUBMIT = 'projectManagerSubmit';
 var BUSINESSDEPARTMENTAPPROVE = 'businessDepartmentApprove';
 var RISKMANAGEDEPARTMENTASSIGNPROJ = 'RiskManageDepartmentAssignProj';
 var BANKWILL = 'bankWill';
 var DUTYEXAMINATIONAPPOINTMENT = 'dutyExaminationAppointment';
 var DUTYEXAMINATIONCONFIRMATION = 'dutyExaminationConfirmation';
 var RISKSMANAGERSUBMIT = 'risksManagerSubmit';
 var PROJECTMANAGEREPLENISH = 'projectManagerReplenish';
 var RSIKSMANAGEDEPARTMENTAPPROVE = 'risksManageDepartmentApprove';
 var PREPAREARRANGEMENT = 'prepareArrangement';
 var ARRANGEMENTAPPROVE = 'arrangementApprove';
 var SUBMITGUARANTEEINTENT = 'submitGuaranteeIntent';
/////////////////17
 var CEOAPPROVE = 'ceoApprove';
 var BINKAPPROVE = 'binkApprove';
 var DRAFTAGREEMENT = 'draftAgreement';
 var CHECKUPAGREEMENT = 'checkupAgreement';
 var MAKEAGREEMENT = 'makeAgreement';
 var SIGNAGREEMENT = 'signAgreement';
 var REGISTERAGREEMENT = 'registerAgreement';
 var COUNTERGUARANTEEBOOK = 'counterGuaranteeBook';
 var RECHECKAGREEMENTANDCOUNTERGUARANTEE = 'recheckAgreementAndCounterGuarantee';
 var APPLYGUARANTEE = 'applyGuarantee';
 var RECHECKGUARANTEE = 'recheckGuarantee';
 var RISKSSUPERVISORGUARANTEEOPINION = 'risksSupervisorGuaranteeOpinion';
 var CEOGUARANTEEOPONION = 'ceoGuaranteeOpinion';
 var SECURITYBONDCONFIRMATION = 'securityBondConfirmation';
 var PROJECTEND = 'projectEnd';
 var CONFIRMPROJECTEND = 'confirmProjectEnd';

/*###############   role    ##############*/
 var ROLE_ADMINISTRATOR='role_administrator';
 var ROLE_BUSINESS_DEPARTMENT='role_businessDepartment';
 var ROLE_PUBLIC_AFFAIRS_CONTROLLER='role_AffairsController';
 var ROLE_BUSINESS_SUPERVISOR='role_businessSupervisor';
 var ROLE_PROJECT_MANAGER='role_projectManager';
 var ROLE_RISKS_SUPERVISOR='ROLE_risksApproveSupervisor';
 var ROLE_CUSTOMER_MANAGER='ROLE_customerManager';
 var ROLE_RISKS_MANAGER='ROLE_ risksManager';
 var ROLE_ADMINISTRATION_SUPERVISOR='ROLE_administrationSupervisor';
 var ROLE_CEO='ROLE_CEO';
 var ROLE_AGREEMENT_COMMISSIONER='ROLE_agreementCommissioner';
 var ROLE_FINANCE_SUPERVISOR='ROLE_financeSupervisor';
 var ROLE_MORTGAGE_COMMISSIONER='ROLE_mortgageCommissioner';
 var ROLE_RECORD_MANAGER='ROLE_recordManager';