/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
PostLoanSupervision = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PostLoanSupervision.superclass.constructor.call(this, {
			id : 'PostLoanSupervision',
			title : "贷后监管制定",
			region : 'center',
			layout : 'border',
			iconCls:"btn-tree-team36",
			items : [this.searchPanel, this.gridPanel]
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
				},{
					columnWidth : 1,
					layout : 'column',
					border : false,
					items : [{
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
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items:[{
						fieldLabel : "客户类别",
						xtype : "dicIndepCombo",
						displayField : 'itemName',
						nodeKey : 'customerType',
						editable : false,
						anchor : "100%",
						hiddenName : 'oppositeType'
						
					}]
	     		},{
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		}]
				},{
	     			columnWidth :.14,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '金额范围',
						name : 'Q_projectMoney_BD_GE',
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
						name : 'Q_projectMoney_BD_LE',
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
						name : 'businessManager',
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
						name : 'Q_businessManager_S_LK'
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
						hiddenName : "orgUserId",
						displayField : 'orgUserName',
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
	     			columnWidth :this.isHiddenBranch ?.07:.25,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						style :this.isHiddenBranch ?'margin-left:30px':'margin-left:245px',
						xtype : 'button',
						scope : this,
						width : 30,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '监管计划',
					xtype : 'button',
					scope : this,
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

						var projectId = record.data.id;

						new DesignSupervisionManagePlan({
							projectId : projectId,
							sprojectId :record.data.projectId,
							businessType:"SmallLoan",
							isHidden : false
							
						}).setVisible(true);
					}
				},'-', {

					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			})
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
			url : __ctxPath + "/fund/projectLoanListBpFundProject.do",
			baseParams : {
				'projectStatus' : 1
			},
			fields : [{
				name : 'id',
				type : 'long'
			}, 'projectId','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'payProjectMoney', 'oppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeId', 'businessType', 'startDate', 'endDate',
					'superviseOpinionName', 'supervisionPersonName',
					'processName', 'appUserName', 'breachComment',
					'accrualtype', 'expectedRepaymentDate', 'payintentPeriod',
					'accrual','managementConsultingOfRate','executor','processName','intentDate','supEndDate','businessType',
					'departMentName','departId','ownJointMoney','productId','runId','isOtherFlow'],

			columns : [{
				header : '所属分公司',
				hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'orgName'
			},{
				header : '所属门店',
				width : 200,
				dataIndex : 'departMentName'
			}, {
				header : '项目编号',
				width : 130,
				align:'center',
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
				dataIndex : 'oppositeTypeValue'
			}, {
				header : '贷款金额(元)',
				align : 'right',
				width : 110,
				summaryType : 'sum',
				sortable : true,
				dataIndex : 'ownJointMoney',
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
				
			}/*,{
				header : '项目阶段',
				width : 80,
				hidden : (this.projectStatus==0||this.projectStatus==3)?false:true,
				//hidden : this.isHiddenAn,
				dataIndex : 'activityName'
			}, {
				header : '贷款状态',
				width : 80,
				hidden : this.isHiddenPs,
				dataIndex : 'projectStatus',
				renderer : function(v) {
					switch (v) {
						case 0 :
							return "办理中项目";
							break;
						case 1 :
							return "还款中项目";
							break;
						case 2 :
							return "结项项目";
							break;
						case 3 :
							return "提前终止项目";
							break;
						case 7 :
							return "全部贷款业务";
							break;
						case 8 :
							return "违约处理项目";
							break;
						case 5 :
							return "展期中项目";
							break;
					}
				}
			}, {
				header : '执行人',
				width : 70,
				hidden : (this.projectStatus==0||this.projectStatus==3)?false:true,
				dataIndex : 'executor'
			}, {
				header : '监管人',
				width : 98,
				hidden : this.isHiddenSuperviseIn,
				dataIndex : 'supervisionPersonName'
			}, {
				header : '监管意见',
				width : 118,
				hidden : this.isHiddenSuperviseIn,
				dataIndex : 'superviseOpinionName'
			}, {
				header : '违约说明',
				width : 118,
				hidden : this.isHiddenBreachComment,
				dataIndex : 'breachComment'
			}, {
				header : '项目开始日期',
				//width : 76,
				//hidden : this.isHiddenBd,
				dataIndex : 'startDate',
				sortable : true
			}, {
				header : '项目到期日期',
				//width : 76,
				//hidden : this.isHiddenCd,
				dataIndex : 'intentDate',
				sortable : true,
				renderer : function(data, metadata, record, rowIndex, columnIndex, store) {
	               if(null!=record.get('supEndDate') && record.get('supEndDate')!=''){
	               		data=record.get('supEndDate');
	               }
	                return data;
	            }
			}, {
				header : '终止时间',
				width : 76,
				hidden : this.isHiddenSd,
				dataIndex : 'endDate',
				sortable : true
			}*/]
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
		/*if (!this.isGrantedShowAllProjects) {
			this.gridPanel.getStore().on("load", function(store) {
				if (store.getCount() < 1) {
					this.get(0).setVisible(true);
					this.searchPanel.setVisible(false);
					this.gridPanel.setVisible(false);
					this.doLayout();
				}
			}, this)
		}*/
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
		/*this.searchPanel.getCmpByName('projectStatus').setValue(this.projectStatus);
		this.searchPanel.getCmpByName('isGrantedShowAllProjects').setValue(this.isGrantedShowAllProjects);*/
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
});
