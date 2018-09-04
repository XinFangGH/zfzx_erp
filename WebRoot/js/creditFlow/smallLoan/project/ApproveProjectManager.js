/**
 * 导航－金融中介管理
 * 	   ---审批项目查询
 * @extends Ext.Panel
 */
ApproveProjectManager = Ext.extend(Ext.Panel, {
	projectStatus : 0,
	titlePrefix : "",
	tabIconCls : "",
	isHiddenBd : true,
	isHiddenAn : true,
	isHiddenCd : true,
	isHiddenSd : true,
	isHiddenPs : true,
	isHiddenSuperviseIn : true,
	isHiddenBreachComment : true,
	dateLabel : "启动时间",
	startDateName : "Q_startDate_D_GE",
	endDateName : "Q_startDate_D_LE",
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.projectStatus) != "undefined") {
			this.projectStatus = parseInt(_cfg.projectStatus);
		}
		this.isGrantedShowAllProjects = isGranted('ApproveProject_seeAll_'+ this.projectStatus);// 是否授权显示所有项目记录
		switch (this.projectStatus) {
			case 0 :
				this.titlePrefix = "待审核项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				break;
			case 2 :
				this.titlePrefix = "已过审项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				this.isHiddenSuperviseIn = false;
				break;
			case 3 :
				this.titlePrefix = "已终止项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenCd = false;
				this.dateLabel = "完成时间";
				this.startDateName = "Q_endDate_D_GE";
				this.endDateName = "Q_endDate_D_LE";
				break;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		ApproveProjectManager.superclass.constructor.call(this, {
			id : 'ApproveProjectManager_' + this.projectStatus,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : this.tabIconCls,
			items : [{
				xtype : 'label',
				text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看您的上下级关系是否设置正确!(查询授权规律为：您可以查看自己或您下属的项目)',
				hidden : true
			}, this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {
			columnWidth : .14,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 60,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : this.dateLabel,
				name : this.startDateName,
				xtype : 'datefield',
				format : 'Y-m-d'

			}]
		};
		var endDate = {
			columnWidth : .112,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 25,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : '到',
				name : this.endDateName,
				xtype : 'datefield',
				labelSeparator : "",
				format : 'Y-m-d'
			}]
		};
		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 100,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
					name : 'projectStatus',
					xtype : 'hidden',
					value : this.projectStatus
				}, {
					name : 'isAll',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '项目编号',
						name : 'projectNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '项目名称',
						name : 'projectName',
						anchor : "98%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items:[{
						xtype : 'combo',
						mode : 'local',
						valueField : 'id',
						editable : false,
						displayField : 'value',
						store : new Ext.data.SimpleStore({
							fields : ["value","id"],
							data : [["企业","company_customer"], ["个人","person_customer"]]
						}),
						triggerAction : "all",
						hiddenName : 'oppositeTypeValue',
						anchor : "98%",
						fieldLabel : '客户类别'
					}]
	     		},{
	     			columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '客户',
						name : 'customerName',
						anchor : "100%",
						xtype : 'textfield'
					}]
	     		},{
	     			columnWidth :.05,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						anchor : "90%",
						style : 'margin-left:5px',
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.14,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '金额范围',
						name : 'projectMoneyMin',
						anchor : "100%",
						xtype : 'numberfield'
					}]
	     		},{
	     			columnWidth :.11,
					layout : 'form',
					labelWidth : 20,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '到',
						labelSeparator : "",
						name : 'projectMoneyMax',
						anchor : "100%",
						xtype : 'numberfield'
					}]
	     		},{
	     			columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
					//	name : 'businessManager',
						xtype : 'trigger',
						fieldLabel : '项目经理',
						submitValue : true,
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						anchor : "100%",
						onTriggerClick : function() {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if ("" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择项目经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();

						}
					}, {
						xtype : "hidden",
						name : 'businessManager'
					}]
	     		},{
					columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属门店',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "departId",
						displayField : 'orgUserName',
						editable : false,
						name :"orgUserId",
						valueField : 'orgUserId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getOrgUserNameOrganization.do',
							fields : ['orgUserId', 'orgUserName']
						})
					}]
				},{
					columnWidth : .2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : "dickeycombo",
						hiddenName : "archivesBelonging",
						nodeKey : 'archivesBelonging', // xx代表分类名称
						fieldLabel : "档案归属地",
						readOnly : this.isRead,
						editable : false,
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
					       }
						}
				
					}]
				}, this.isHiddenBranch == false ?{
					columnWidth :.18,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属分公司',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "companyId",
						displayField : 'companyName',
						name :"companyId",
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
	     			columnWidth :this.isHiddenBranch ?.05:.25,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						xtype : 'button',
						scope : this,
						width : 30,
						style : 'margin-left:5px',
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		//工具栏----------------
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-detail',
				text : '查看项目',
				xtype : 'button',
				scope : this,
				hidden : isGranted('ApproveProject_see_' + this.projectStatus)? false: true,
				handler : function() {
				//	detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					seeProjectInfoByFileName(this.gridPanel,'ApproveProjectInfoPanel',"see")
				}
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('ApproveProject_edit_' + this.projectStatus)
			}), {
				iconCls : 'btn-edit',
				text : '编辑项目',
				xtype : 'button',
				hidden : isGranted('ApproveProject_edit_' + this.projectStatus)
						? false
						: true,
				scope : this,
				handler : function() {
					seeProjectInfoByFileName(this.gridPanel,'ApproveProjectInfoPanel','edit')
				}
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('ApproveProject_seeAgreements_' + this.projectStatus)
			}),{
				iconCls : 'btn-close1',
				text : '终止项目',
				xtype : 'button',
				hidden : this.projectStatus==3?true:(isGranted('_stopPro_p' + this.projectStatus)
						? false
						: true),
				scope : this,
				handler : function() {
					stopPro(this.gridPanel, 'SmallLoanProjectInfo_',
							'SmallLoanProjectInfoEdit_','Small')//最后一个参数用于区分是修改sl_smallloan_project还是bp_fund_project
				}
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('_stopPro_p' + this.projectStatus)
						|| !isGranted('_removePro_p' + this.projectStatus)
			}),/*  {
				iconCls : 'btn-del',
				text : '删除项目',
				xtype : 'button',
				hidden : isGranted('_removePro_p' + this.projectStatus)
						? false
						: true,
				scope : this,
				handler : function() {
					removePro(this.gridPanel, 'SmallLoanProjectInfo_',
							'SmallLoanProjectInfoEdit_')
				}
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('_removePro_p' + this.projectStatus)
						|| !isGranted('_yjysm_p' + this.projectStatus)
			}),*/{
				iconCls : 'btn-advice',
				text : '意见与说明记录',
				xtype : 'button',
				scope : this,
				hidden : isGranted('ApproveProject_seeAgreements_'+ this.projectStatus)
									? false
									: true,
				handler : this.flowRecords
						
			},new Ext.Toolbar.Separator({
				hidden : !isGranted('ApproveProject_seeshowFlowImg_' + this.projectStatus)
			}), {
				iconCls : 'btn-flow-chart',
				text : '流程示意图',
				xtype : 'button',
				scope : this,
				hidden : isGranted('ApproveProject_seeshowFlowImg_' + this.projectStatus)
						? false
						: true,
				handler : function() {
					showFlowImg(this.gridPanel);
				}
			}/*, new Ext.Toolbar.Separator({
				hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
						|| !isGranted('_exportPro_p' + this.projectStatus)
			}),*/ /*{
				iconCls : 'menu-zmexport',
				text : '导出项目',
				xtype : 'button',
				scope : this,
				hideen : true,
				handler : function() {
					var searchPanel = this.searchPanel;
					var tabs = Ext.getCmp('centerTabPanel');
					var gpObj = document
							.getElementById('ReportPreviewprojectdetail');
					if (gpObj != null) {
						tabs.remove('ReportPreviewprojectdetail');
					}
					gpObj = ReportMenuSmallloanprojectdetail({
						reportKey : "projectdetail",
						projectMoneyMin : searchPanel.getCmpByName('projectMoneyMin').getValue(),
						projectMoneyMax : searchPanel.getCmpByName('projectMoneyMax').getValue(),

						projectNumber : searchPanel.getCmpByName('projectNumber').getValue(),
						projectName : searchPanel.getCmpByName('projectName').getValue(),
						oppositeTypeValue : searchPanel.getCmpByName('oppositeTypeValue').getValue(),
						customerName : searchPanel.getCmpByName('customerName').getValue(),
						businessManagerid : searchPanel.getCmpByName('Q_businessManager_S_LK').getValue(),
						businessManager : searchPanel.getCmpByName('businessManager').getValue(),

						startDateg : searchPanel.getCmpByName(this.startDateName).getValue(),
						startDatel : searchPanel.getCmpByName(this.endDateName).getValue(),
						companyId: searchPanel.getCmpByName('companyId').getRawValue(),
						projectStatus : "办理中贷款"
					});
					tabs.add(gpObj);
					tabs.setActiveTab("ReportPreviewprojectdetail");
				}
			}*/]
		});
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			notmask : true,
			// 不适用RowActions
			rowActions : false,
      	    viewConfig:{
      	    	forceFit : false
      	    },
			url : __ctxPath + "/project/approveProjectListSlSmallloanProject.do",
			baseParams : {
				'projectStatus' : this.projectStatus,
				'isAll' : this.isGrantedShowAllProjects
			},
			fields : [{
				name : 'runId',
				type : 'int'
			}, 'projectId','appUserName','createDate','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'payProjectMoney', 'OppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeID', 'businessType', 'startDate', 'endDate',
					'superviseOpinionName', 'supervisionPersonName',
					'processName', 'businessManagerValue', 'breachComment',
					'accrualtype', 'expectedRepaymentDate', 'payintentPeriod',
					'accrual','managementConsultingOfRate','executor','processName','repaymentDate','loanStartDate','businessType',
					'orgUserName','orgUserId'],

			columns : [{
				header : 'runId',
				dataIndex : 'runId',
				align:'center',
				hidden : true
			}, {
				header : 'projectId',
				dataIndex : 'projectId',
				align:'center',
				align:'center',
				hidden : true
			}, {
				header : 'payintentPeriod',
				dataIndex : 'payintentPeriod',
				align:'center',
				hidden : true
			}, {
				header : 'taskId',
				dataIndex : 'taskId',
				align:'center',
				hidden : true
			}, {
				header : 'oppositeId',
				dataIndex : 'oppositeID',
				hidden : true
			}, {
				header : 'oppositeType',
				dataIndex : 'oppositeType',
				align:'center',
				hidden : true
			}, {
				header : 'operationType',
				dataIndex : 'operationType',
				hidden : true
			}, {
				header : 'businessType',
				dataIndex : 'businessType',
				align:'center',
				hidden : true
			},{
				header : '所属门店',
				width : 200,
				dataIndex : 'orgName'
			}, {
				header : '项目编号',
				width : 130,
				summaryRenderer : totalMoney,
				dataIndex : 'projectNumber'
			}, {
				header : '项目名称',
				width : this.isHiddenBranch?410 : 310,
				dataIndex : 'projectName'
			}, {
				header : '客户类别',
				width : 68,
				align:'center',
				align:'center',
				dataIndex : 'OppositeType',
				renderer : function(val){
					if(val=="person_customer"){
						return "个人";
					}else if(val=="company_customer"){
						return "企业";
					}
					return "";
				}
			}, {
				header : '客户名称',
				align:'center',
				width : 180,
				dataIndex : 'customerName'
			},{
				header : '贷款金额(元)',
				align : 'right',
				summaryType : 'sum',
				width : 110,
				sortable : true,
				dataIndex : 'projectMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			}, {
				header : '项目经理',
				width : 70,
				align:'center',
				dataIndex : 'appUserName'
			},{
				header : '项目阶段',
				width : 80,
				align:'center',
				dataIndex : 'activityName'
			},{
				header : '执行人',
				align:'center',
				width : 70,
				hidden:this.projectStatus==3?true:false,
				dataIndex : 'executor'
			},{
				header : '项目开始日期',
				align:'center',
				//width : 76,
				//hidden : this.isHiddenBd,
				dataIndex : 'createDate',
				sortable : true
			},{
				header : '终止时间',
				width : 76,
				align:'center',
				dataIndex : 'endDate',
				sortable : true
			}]
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);

	},// end of the initComponents()
	
		// 查看意见与说明记录 
	flowRecords : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var runId =selRs[0].get('runId');
		var businessType=selRs[0].get('businessType');
		new SlProcessRunView({
						runId : runId,
						businessType : businessType
					}).show();
	},
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
