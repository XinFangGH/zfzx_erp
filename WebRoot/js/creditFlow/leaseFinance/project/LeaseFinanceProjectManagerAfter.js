/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷贷后项目管理
 * @company 智维软件
 * @createtime:
 */
LeaseFinanceProjectManagerAfter = Ext.extend(Ext.Panel, {

	titlePrefix : "",
	tabIconCls : "",
	isCapitalUnexpired : "",
	businessType:"LeaseFinance",
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.managerType) != "undefined") {
			this.managerType = parseInt(_cfg.managerType);
		}
		if(typeof(_cfg.businessType)!="undefined"){
			this.businessType = _cfg.businessType;
		}
		this.isGrantedShowAllProjects = isGranted('_seeAllPro_p1');// 是否授权显示所有项目记录
		switch (this.managerType) {
			case 1 :
				this.titlePrefix = "贷后监管";
				this.tabIconCls = "btn-tree-team12";
				break;
			case 13 :
				this.titlePrefix = "利率变更";
				this.tabIconCls = "btn-tree-team58";
				break;
			case 2 :
				this.titlePrefix = "贷款展期";
				this.tabIconCls = "btn-tree-team14";
				break;
			case 3 :
				this.titlePrefix = "提前还款";
				this.isCapitalUnexpired = true;
				this.tabIconCls = "btn-tree-team15";
				break;
			case 4 :
				this.titlePrefix = "违约处理";
				this.tabIconCls = "btn-tree-team59";
				break;
			case 5 :
				this.titlePrefix = "贷款结清";
				this.tabIconCls = "";
				break;
			
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		LeaseFinanceProjectManagerAfter.superclass.constructor.call(this, {
			id : 'LeaseFinanceProjectManager_' + this.managerType,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : this.tabIconCls,
			items : [{
				xtype : 'label',
//				text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看你的上下级关系是否设置正确!(查询授权规律为：你可以看你自己或你下属的项目)',
				hidden : true
			}, this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var jsArr = [
				//add by gao start 
				__ctxPath + '/js/creditFlow/leaseFinance/project/flDesignSupervisionManagePlan.js',// 监管计划
				__ctxPath + '/js/creditFlow/leaseFinance/project/GlobalSupervisonRecordView.js',
				__ctxPath + '/js/creditFlow/leaseFinance/project/FlProjectFinished.js' ,//贷款结项
				/*租赁标的清单start*/
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseObjectList.js',//租赁标的清单
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseObjectInsuranceInfo.js',//租赁物保险信息
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/AddLeaseObjectWin.js',//租赁标的增加
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceSuppliorInfo.js',//供货方信息   必须要加载  在win中延迟加载，第一次访问不了  add  by gao
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/selectSupplior.js',//供货方信息  弹窗
//				__ctxPath + '/publicmodel/uploads/js/upload.js',
//				__ctxPath + '/js/fileupload/FlexUploadDialog.js',//供货方信息  弹窗
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceUploads.js',//供货方信息  弹窗
				/*租赁标的清单end*/
				//add by gao end
				__ctxPath + '/js/creditFlow/smallLoan/contract/OperateContractWindow.js',
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				__ctxPath + '/js/creditFlow/finance/detailView.js',// 资金流水详细
				__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js',// 款项信息
				__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/finance/SlActualToCharge.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/finance/CashQlideAndCheckForm.js',// 经办费用清单
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceExtensionlPanel.js',// 展期panel
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceAlterAccrualPanel.js',// 利率变更panel
				__ctxPath + '/js/creditFlow/smallLoan/finance/FinanceEarlyRepaymentPanel.js',// 提前还款panel
				__ctxPath + '/js/creditFlow/smallLoan/finance/loadDataAlterAccrual.js',// 展期loaddata
				__ctxPath + '/js/creditFlow/smallLoan/finance/loadDataEarlyRepay.js',// 利率变更loaddata
				__ctxPath + '/js/creditFlow/smallLoan/finance/loadDataExtension.js',// 提前还款loaddata
				__ctxPath + '/js/creditFlow/smallLoan/finance/SmallLoanExtensionView.js',// 展期页面
				__ctxPath + '/js/creditFlow/smallLoan/finance/SmallLoanAlterAccrualView.js',// 利率变更页面
				__ctxPath + '/js/creditFlow/smallLoan/finance/SmallLoanEarlyRepaymentView.js',// 提前还款页面
				__ctxPath + '/js/creditFlow/smallLoan/finance/EarlyRepaymentSlFundIntent.js',// 提前还款，
				__ctxPath + '/js/creditFlow/smallLoan/finance/AlterAccrualSlFundIntent.js',// 利率变更款项
				__ctxPath + '/js/creditFlow/finance/superviseSlFundIntent.js',// 展期款项
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlClauseContractView.js',// 展期合同
				__ctxPath + '/js/creditFlow/smallLoan/project/SlSupervisonRecordView.js'// 监管记录
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		var anchor = '100%';
		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 70,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
            bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
				name : 'projectStatus',
				xtype : 'hidden',
				value : 1
			}, {
				name : 'isGrantedShowAllProjects',
				xtype : 'hidden',
				value : this.isGrantedShowAllProjects
			}, this.isHiddenBranch == false?{
				columnWidth : .25,
				layout : 'form',
				hidden : this.isHiddenBranch,
				labelAlign : 'right',
				labelWidth : 80,
				border : false,
				items : [{
					fieldLabel : '所属分公司',
					xtype : "combo",
					anchor : "100%",
					hiddenName : "companyId",
					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/system/getControlNameOrganization.do',
						fields : ['companyId', 'companyName']
					})
				}]
			}:{
		     	columnWidth:0.001,
		     	border:false
		   	},{
		   		columnWidth : .25,
		   		layout : 'form',
		   		labelWidth : 80,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			fieldLabel : '项目编号',
					name : 'Q_projectNumber_S_LK',
					anchor : "100%",
					xtype : 'textfield'
		   		}]
		   	},{
		   		columnWidth : .25,
		   		layout : 'form',
		   		labelWidth : 80,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			fieldLabel : '项目名称',
					name : 'Q_projectName_S_LK',
					anchor : "98%",
					xtype : 'textfield'
		   		}]
		   	},{
		   		columnWidth : .25,
		   		layout : 'form',
		   		labelWidth : 80,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			xtype : 'combo',
					mode : 'local',
					anchor : "100%",
					valueField : 'value',
					editable : false,
					displayField : 'value',
					store : new Ext.data.SimpleStore({
						fields : ["value"],
						data : [["企业"], ["个人"]]
					}),
					triggerAction : "all",
					name : 'Q_oppositeTypeValue_S_EQ',
					fieldLabel : '客户类别'
		   		}]
		   	},{
		   		columnWidth : .25,
		   		layout : 'form',
		   		labelWidth : 80,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			fieldLabel : '客户',
					name : 'Q_customerName_S_LK',
					anchor : "100%",
					xtype : 'textfield'
		   		}]
		   	},{
		   		columnWidth : .15,
		   		layout : 'form',
		   		labelWidth : 80,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			fieldLabel : '贷款金额',
					name : 'Q_projectMoney_BD_GE',
					anchor : "100%",
					xtype : 'numberfield'
		   		}]
		   	},{
		   		columnWidth : .1,
		   		layout : 'form',
		   		labelWidth : 20,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			fieldLabel : '到',
					labelSeparator : "",
					name : 'Q_projectMoney_BD_LE',
					anchor : "100%",
					xtype : 'numberfield'
		   		}]
		   	},/*{
		   		columnWidth : .25,
		   		layout : 'form',
		   		labelWidth : 80,
		   		labelAlign : 'right',
		   		border : false,
		   		items : [{
		   			xtype : 'combo',
					mode : 'local',
					anchor : "98%",
					valueField : 'key',
					editable : false,
					displayField : 'value',
					store : new Ext.data.SimpleStore({
						fields : ["value", "key"],
						data : [["正常贷款", "zhengchang"],
								["关注贷款", "guanzhu"], ["次级贷款", "ciji"],
								["可疑贷款", "keyi"], ["损失贷款", "sunshi"]]
					}),
					triggerAction : "all",
					hiddenName : "superviseOpinion",
					name : 'superviseOpinion',
					fieldLabel : '贷款五级分类'

		   		}]
		   	},*/{
		   		columnWidth : .1,
		   		layout : 'form',
		   		border : false,
		   		items: [{
		   			text : '查询',
					xtype : 'button',	
					scope : this,
					style : 'margin-left:30px',
					anchor : "85%",
					iconCls : 'btn-search',
					handler : this.search
		   		}]
		   	},{
		   		columnWidth : .1,
		   		layout : 'form',
		   		border : false,
		   		items : [{
		   			text : '重置',
		   			xtype : 'button',
					scope : this,
					anchor : "60%",
					iconCls : 'btn-reset',
					handler : this.reset
		   		}]
		   	}]
		});
		if (this.managerType == 1) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '监管计划',
					xtype : 'button',
					scope : this,
					hidden : isGranted('addSuperviseRecord') ? false : true,
					handler : function() {
						var selRs = this.gridPanel.getSelectionModel()
								.getSelections();
						if (selRs.length == 0) {
							Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
							return;
						}
						if (selRs.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
							return;
						}
						var record = this.gridPanel.getSelectionModel()
								.getSelected();
						var projectId = record.data.projectId;
						new flDesignSupervisionManagePlan({
							projectId : projectId,
							businessType:this.businessType
						}).setVisible(true);
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('addSuperviseRecord') ? false : true
				}), {
					iconCls : 'btn-edit',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'LeaseFinanceProjectInfo_')
					}
				}]
			});
		} else if (this.managerType == 2) {
			this.topbar = new Ext.Toolbar({
				items : [/*{
					iconCls : 'menu-flowWait',
					text : '展期办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('handleExtension') ? false : true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var tabs = Ext.getCmp('centerTabPanel');
							var gpObj = document
									.getElementById('LeaseFinanceExtensionView'
											+ record.data.projectId);
							if (gpObj == null) {

								gpObj = new LeaseFinanceExtensionView({
									surplusnotMoney : (record.data.projectMoney - ((record.data.payProjectMoney == null)
											? 0
											: record.data.payProjectMoney)),
									businessType : record.data.businessType,
									projectId : record.data.projectId
								});
								tabs.add(gpObj);
							}
							tabs.setActiveTab("LeaseFinanceExtensionView"
									+ record.data.projectId);
						}
					}
				},*/ {
					xtype:'button',
					text:'启动展期流程',
					iconCls : 'menu-flowWait',
					scope:this,
					hidden : isGranted('LeaseFinancehandleExtension') ? false : true,
					handler : function(){
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							//用来判断当前项目是否已经办理贷后流程，来决定是否能启动新的展期流程
							var isOtherFlow =record.data.isOtherFlow;
							if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
								Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的展期流程！');
								return false;
							}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
								Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的展期流程！');
								return false;
							}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
								Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的展期流程！');
								return false;
							}else{//isOtherFlow==0表示当前没有办理任何贷后流程
								//启动展期流程且打开第一个节点vm方法开始
								Ext.Ajax.request( {
									url : __ctxPath +"/supervise/startRenewalProcessSlSuperviseRecord.do",
									params : {projectId:record.data.projectId,businessType:record.data.businessType},
									method : 'POST',
									success : function(response) {
												var obj=Ext.util.JSON.decode(response.responseText)
												var contentPanel = App.getContentPanel();
												if(obj.taskId==1){
													Ext.ux.Toast.msg('操作信息','您不是展期流程中任务<展期申请>的处理人!');
													return;
												}else{
													var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
													if (formView == null) {
														formView = new ProcessNextForm({
																	taskId : obj.taskId,
																	activityName : obj.activityName,
																	projectName : obj.projectName,
																	agentTask : true
																});
														contentPanel.add(formView);
													}
													contentPanel.activate(formView);
												}
											},
								   failure : function(fp, action) {
											Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									}
								});
								//启动展期流程且打开第一个节点vm方法结束
							}
						}
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('LeaseFinancehandleExtension') ? false : true
				}),{
					iconCls : 'btn-edit',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'LeaseFinanceProjectInfo_')
					}
				}]
			});
		} else if (this.managerType == 4) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-tree-illegal',
					text : '违约处理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('flstartBreach') ? false : true,
					handler : function() {
						var selRs = this.gridPanel.getSelectionModel()
								.getSelections();
						var gridPanel = this.gridPanel;
						if (selRs.length == 0) {
							Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
							return;
						}
						if (selRs.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
							return;
						}
						var record = selRs[0];
						var projectName = record.data.projectName;
						var projectId = record.data.projectId;
						var businessType = record.data.businessType;
						var leaseObjectList = new LeaseObjectList({projectId:projectId,readOnly:true,isBuyBackHidden:false});//,readOnly:true 只查看   暂不支持查看
						var window_add = new Ext.Window({
							title : '【' + projectName + '】违约处理',
							height : 428,
							autoScroll : true,
							frame : true,
							buttonAlign : 'center',
							width : 1125,
							maximizable : true,
							modal : true,
							items : [new Ext.form.FormPanel({
								frame : true,
								border : false,
								url : 'dispatch',
								plain : true,
								labelWidth : 120,
								bodyStyle : "padding:5px 5px 0",
								monitorValid : true,
								defaults : {
									xtype : 'textfield',
									width : 200
								},
								items : [new Ext.form.RadioGroup({
									fieldLabel : "项目是否被终止",
									items : [{
										boxLabel : '项目终止',
										inputValue : "1",
										name : "rg",
										checked : true
									}]
								}), {
									xtype : 'hidden',
									name : 'projectId_',
									value : projectId
								}, {
									xtype : 'hidden',
									name : 'bussinessType_',
									value : businessType
								}, {
									fieldLabel : "是否将此客户",
									name : "fcbm_ip",
									xtype : 'checkboxgroup',
									ctCls : "data_tab_tdr2",
									items : [{
										boxLabel : '加入黑名单',
										name : 'cb-col-1'
									}]
								} ,{
									xtype : 'panel',
									border : false,
									anchor : "100%",
									bodyStyle : 'margin-bottom:5px',
									html : '<B><font class="x-myZW-fieldset-title">'
											+ '【租赁标的信息】</font></B><font class="x-myZW-fieldset-title">'
								},leaseObjectList,{
									xtype : 'panel',
									border : false,
									anchor : "100%",
									bodyStyle : 'margin-bottom:5px',
									html : '<B><font class="x-myZW-fieldset-title">'
											+ '【放款收息信息处理】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
								} , new SlFundIntentViewVM({
									projectId : projectId,
									isHidden1 : true,
									businessType : 'LeaseFinance',
									isHiddenCanBtn : false,
									isHiddenResBtn : false,
									enableEdit : true,
									isFinbtn:true,
									isHiddenautocreateBtn:true,
									isHiddenResBtn1:true,
									isHiddenTitle : true
								}),new DZYMortgageView({
										projectId : projectId,
										titleText : '抵质押物处理',
										businessType : 'LeaseFinance',
										isHiddenAddBtn : true,
										isHiddenDelBtn : true,
										isHiddenEdiBtn : true,
										isHiddenAddContractBtn:true,
										isHiddenDelContractBtn:true,
										isHiddenEdiContractBtn:true,
										isHiddenRelieve:true,
										isblHidden:true,
										isgdHidden:true,
										isSeeContractHidden : false,
										isHandleHidden : false,
										isHandleEdit : true
									}) 
								, {
									xtype : 'panel',
									border : false,
									anchor : "100%",
									bodyStyle : 'margin-bottom:5px;margin-top:15px',
									html : '<B><font class="x-myZW-fieldset-title">'
											+ '【违约处理说明文档】</font></B>'
								} , new uploads({
									anchor : "100%",
									title_c : '上传违约处理说明文档',
									businessType : businessType,
									typeisfile : 'breachLeaseFinance',
									projectId : projectId,
									uploadsSize : 15
								}), {
									xtype : "textarea",
									fieldLabel : "违约说明",
									name : "comments",
									style : 'margin-top:10px;',
									anchor : "100%"
								}]
								
							})],
							buttons : [{
									text : "确定",
									iconCls : "btn-ok",
									handler : function() {
										var myCheckboxGroup = window_add.get(0)
												.getCmpByName('fcbm_ip');
										var comments = window_add.get(0)
												.getCmpByName('comments');
										var cbitems = myCheckboxGroup.items;
										var isCheck = cbitems.itemAt(0).checked;
										var listed = 0;
										if (isCheck) {
											listed = 1;
										}
										Ext.Ajax.request({
											url : __ctxPath
													+ '/creditFlow/leaseFinance/project/startBreachFlLeaseFinanceProject.do',
											method : 'post',
											params : {
												"projectID_" : projectId,
												"listed_" : listed,
												"comments" : comments
														.getValue(),
												"businessType_" : businessType
											},
											success : function(response,options) {
												Ext.ux.Toast.msg('操作信息','违约处理成功！');
												window_add.destroy();
												gridPanel.getStore().reload();
											}
										});
									},
									scope : this,
									formBind : true
								}, {
									text : "取消",
									iconCls : "btn-cancel",
									scope : this,
									handler : function() {
										this.reset;
										window_add.destroy();
									}
								}],
							listeners : {
								'beforeclose' : function(panel) {
									window_add.destroy();
								}
							}
						});
						window_add.show();
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('flstartBreach') ? false : true
				}), {
					iconCls : 'btn-edit',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'LeaseFinanceProjectInfo_')
					}
				}]
			});
		} else if (this.managerType == 3) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWait',
					text : '提前还款办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('LeaseFinancehandleAdvanceRepayment') ? false : true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var isOtherFlow =record.data.isOtherFlow;
							if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
								Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的提前还款流程！');
								return false;
							}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
								Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的提前还款流程！');
								return false;
							}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
								Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的提前还款流程！');
								return false;
							}else{//isOtherFlow==0表示当前没有办理任何贷后流程
								Ext.Ajax.request( {
									url : __ctxPath +"/smallLoan/finance/startSlEarlyRepaymentProcessSlEarlyRepaymentRecord.do",
									params : {projectId:record.data.projectId,businessType:record.data.businessType},
									method : 'POST',
									success : function(response) {
												var obj=Ext.util.JSON.decode(response.responseText)
												var contentPanel = App.getContentPanel();
												if(obj.taskId==1){
													Ext.ux.Toast.msg('操作信息','您不是提前还款流程中任务<提前还款申请>的处理人!');
													return;
												}else{
													var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
													if (formView == null) {
														formView = new ProcessNextForm({
															taskId : obj.taskId,
															activityName : obj.activityName,
															projectName : obj.projectName,
															agentTask : true
														});
														contentPanel.add(formView);
													}
													contentPanel.activate(formView);
												}
											},
								   failure : function(fp, action) {
											Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									}
								});
							}
							/*Ext.Ajax.request({
								url : __ctxPath
										+ '/project/getEarlyMoneySlSmallloanProject.do',
								method : 'post',
								params : {
									"projectId" : record.data.projectId
								},
								success : function(response,options) {
									var obj=Ext.util.JSON.decode(response.responseText);
									//用来判断当前项目是否已经办理贷后流程，来决定是否能启动新的提前还款流程
								var isOtherFlow =record.data.isOtherFlow;
								if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
									Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的提前还款流程！');
									return false;
								}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
									Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的提前还款流程！');
									return false;
								}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
									Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的提前还款流程！');
									return false;
								}else{//isOtherFlow==0表示当前没有办理任何贷后流程
									//在这里写提前还款流程申请功能页面方法或者直接启动提前还款流程开始
									var tabs = Ext.getCmp('centerTabPanel');
									var gpObj = document
											.getElementById('LeaseFinanceEarlyRepaymentView'
													+ record.data.projectId);
									if (gpObj == null) {
										gpObj = new LeaseFinanceEarlyRepaymentView({
											surplusnotMoney : (record.data.projectMoney - obj.payMoney),
											intentDate : record.data.expectedRepaymentDate,
											businessType : record.data.businessType,
											projectId : record.data.projectId,
											projectName : record.data.projectName
										});
										tabs.add(gpObj);
									}
									tabs.setActiveTab("LeaseFinanceEarlyRepaymentView"+ record.data.projectId);
									//在这里写提前还款流程申请功能页面方法或者直接启动提前还款流程结束
								}
								}
							});*/
							
							}
						

					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('handleAdvanceRepayment') ? false : true
				}), {

					iconCls : 'btn-edit',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'LeaseFinanceProjectInfo_')
					}
				}]
			})
		} else if (this.managerType == 5) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWaitb',
					text : '办理结项',
					xtype : 'button',
					scope : this,
					hidden : isGranted('LeaseFinanceProjectFinished') ? false : true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							var tabs = Ext.getCmp('centerTabPanel');
							var gpObj = document.getElementById('FlProjectFinished_'	+ record.data.projectId);
							if (gpObj == null) {
								gpObj = new FlProjectFinished({
									record : record
								});
								tabs.add(gpObj);
							}
							tabs.setActiveTab("FlProjectFinished_"+ record.data.projectId);
						}
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('') ? false : true
				}), {

					iconCls : 'btn-edit',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'LeaseFinanceProjectInfo_')
					}
				}]
			});		
		}else {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'menu-flowWait',
					text : '利率变更',
					xtype : 'button',
					scope : this,
					hidden : isGranted('LeaseFinanceInterestRateChanges') ? false : true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel()
								.getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
							return false;
						} else if (s.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
							return false;
						} else {
							var record = s[0];
							//用来判断当前项目是否已经办理贷后流程，来决定是否能启动新的利率变更流程
							var isOtherFlow =record.data.isOtherFlow;
							if(isOtherFlow==1){//isOtherFlow==1表示当前项目已经启动了展期流程
								Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的利率变更流程！');
								return false;
							}else if(isOtherFlow==2){//isOtherFlow==1表示当前项目已经启动了提前还款流程
								Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的利率变更流程！');
								return false;
							}else if(isOtherFlow==3){//isOtherFlow==1表示当前项目已经启动了利率变更流程
								Ext.ux.Toast.msg('操作信息', '正在进行利率变更流程，不能启动新的利率变更流程！');
								return false;
							}else{//isOtherFlow==0表示当前没有办理任何贷后流程
								//在这里写利率变更流程申请功能页面方法或者直接启动利率变更流程开始
								Ext.Ajax.request( {
									url : __ctxPath +"/smallLoan/finance/startFLSlAlteraccrualProcessSlAlterAccrualRecord.do",
									params : {projectId:record.data.projectId},
									method : 'POST',
									success : function(response) {
												var obj=Ext.util.JSON.decode(response.responseText)
												var contentPanel = App.getContentPanel();
												if(obj.taskId==1){
													Ext.ux.Toast.msg('操作信息','您不是利率变更流程中任务<利率变更申请>的处理人!');
													return;
												}else{
													var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
													if (formView == null) {
														formView = new ProcessNextForm({
															taskId : obj.taskId,
															activityName : obj.activityName,
															projectName : obj.projectName,
															agentTask : true
														});
														contentPanel.add(formView);
													}
													contentPanel.activate(formView);
												}
											},
								   failure : function(fp, action) {
											Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									}
								});
/*								Ext.Ajax.request({
								method : 'POST',
								scope : this,
								url : __ctxPath+ '/smallLoan/finance/initAlteraccrualPanelSlAlterAccrualRecord.do',
								params : {
									projectId : record.data.projectId
								},
								success : function(response, request) {
									var obj = Ext.util.JSON.decode(response.responseText);
									var slAlterAccrualRecord = obj.data;
									var tabs = Ext.getCmp('centerTabPanel');
									var gpObj = document.getElementById('LeaseFinanceAlterAccrualView'+ record.data.projectId);
									if (gpObj == null) {
										gpObj = new LeaseFinanceAlterAccrualView({
											surplusnotMoney : (record.data.projectMoney - ((record.data.payProjectMoney == null)
													? 0
													: record.data.payProjectMoney)),
											intentDate : record.data.expectedRepaymentDate,
											businessType : record.data.businessType,
											projectId : record.data.projectId,
											projectName : record.data.projectName,
											accrualtype : record.data.accrualtype,
											payintentPeriod : record.data.payintentPeriod,
											slAlterAccrualRecord : slAlterAccrualRecord
										});
										tabs.add(gpObj);
									}
									tabs.setActiveTab("LeaseFinanceAlterAccrualView"+ record.data.projectId);
								}
							})*/
								//在这里写利率变更流程申请功能页面方法或者直接启动利率变更流程结束
							}
						}

					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('LeaseFinanceInterestRateChanges') ? false : true
				}), {

					iconCls : 'btn-edit',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'LeaseFinanceProjectInfo_')
					}
				}]
			});
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,

			rowActions : false,
			url : __ctxPath + "/creditFlow/leaseFinance/project/projectListFlLeaseFinanceProject.do",
			baseParams : {
				'projectStatus' : 1,
				'isCapitalUnexpired' : this.isCapitalUnexpired,
				'isGrantedShowAllProjects' : this.isGrantedShowAllProjects,
				'keyWord' : this.keyWord
			},
			fields : [{
				name : 'runId',
				type : 'int'
			}, 'projectId','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'oppositeType', 'oppositeTypeValue', 'customerName',
					'projectStatus', 'operationType', 'operationTypeValue',
					'taskId', 'activityName', 'oppositeId', 'businessType',
					'startDate', 'endDate', 'superviseOpinionName',
					'loanStartDate', 'expectedRepaymentDate', 'processName',
					'appUserIdValue', 'superviseDateTime',
					'payProjectMoney', 'accrualtype', 'payintentPeriod','isOtherFlow'],
			columns : [{
				header : 'runId',
				dataIndex : 'runId',
				hidden : true
			}, {
				header : 'projectId',
				dataIndex : 'projectId',
				hidden : true
			}, {
				header : 'taskId',
				dataIndex : 'taskId',
				hidden : true
			}, {
				header : 'oppositeId',
				dataIndex : 'oppositeId',
				hidden : true
			}, {
				header : 'oppositeType',
				dataIndex : 'oppositeType',
				hidden : true
			}, {
				header : 'operationType',
				dataIndex : 'operationType',
				hidden : true
			}, {
				header : 'businessType',
				dataIndex : 'businessType',
				hidden : true
			},{
				header : '所属分公司',
				hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'orgName'
			}, {
				header : '项目编号',
				width : 130,
				dataIndex : 'projectNumber'
			}, {
				header : '项目名称',
				width : this.isHiddenBranch?410 : 310,
				dataIndex : 'projectName'
			}, {
				header : '客户类别',
				width : 68,
				dataIndex : 'oppositeTypeValue'
			}, {
				header : '贷款金额',
				align : 'right',
				width : 110,
				sortable : true,
				dataIndex : 'projectMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00元";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '项目经理',
				width : 70,
				dataIndex : 'appUserIdValue'
			}, {
				header : '贷款开始日期',
				width : 98,
				dataIndex : 'loanStartDate'
			}, {
				header : '预计还款日期',
				width : 98,
				dataIndex : 'expectedRepaymentDate'
			}
			/*, {
				header : '监管时间',
				width : 118,
				dataIndex : 'superviseDateTime'
			}, {
				header : '监管意见',
				width : 118,
				dataIndex : 'superviseOpinionName'
			}*/
			]
		});
	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
});
