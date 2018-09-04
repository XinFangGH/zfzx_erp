/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
IndustryEarlyWarning = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		IndustryEarlyWarning.superclass.constructor.call(this, {
			id : 'IndustryEarlyWarning',
			title : "行业预警",
			region : 'center',
			layout : 'border',
			iconCls:"btn-tree-team36",
			items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
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
							xtype : "diccombo",
							fieldLabel : '五级分类',
							hiddenName : 'superviseManageOpinion',
							itemName : '监管意见', // xx代表分类名称
							isDisplayItemName : false,
							editable : false,
							value : null,
						//	emptyText : "请选择",
							anchor : '100%',
							listeners : {
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										combox.clearInvalid();
										if (combox.getValue() > 0) {
											combox.setValue(combox.getValue());
										}
									})
								}
							}
						}]
		     		},{
			     		columnWidth :.25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							fieldLabel : "行业类别",
							name : 'hangyeName',
					        hiddenName : 'hangyeName',
							scope : this,
							emptyText : '请选择行业类别',
							scope:true,
							editable : false,
							onTriggerClick : function(e) {
								var obj = this;
								var oobbj=this.nextSibling();
								selectTradeCategory(obj,oobbj);
							}
						},{
							xtype : "hidden",
							name : 'hangyeType'
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
	     			},{
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						width : 30,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
					}]
		});
		
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-xls',
					text : '导出excel',
					xtype : 'button',
					scope : this,
					handler : this.toExcel
				} ,'-', {

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
			url : __ctxPath + "/fund/IndustryProjectListBpFundProject.do",
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
					'departMentName','departId','ownJointMoney','productId','runId','isOtherFlow','superviseManageOpinionValue',
					'hangyeTypeValue'],

			columns : [{
				header : '行业类型',
				width : 120,
				dataIndex : 'hangyeTypeValue'
			},{
				header : '项目名称',
				width : 180,
				summaryRenderer : totalMoney,
				dataIndex : 'projectName'
			},{
				header : '项目金额(元)',
				width : 110,
				sortable : true,
				align:'right',
				summaryType : 'sum',
				dataIndex : 'ownJointMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			},{
				header : '项目经理',
				width : 80,
				align:'center',
				dataIndex : 'appUserName'
			},{
				header : '开始时间',
				width : 80,
				align:'center',
				dataIndex : 'startDate'
			},{
				header : '结束时间',
				width : 80,
				align:'center',
				dataIndex : 'intentDate'
			},{
				header : '五级分类',
				width : 80,
				align:'center',
				dataIndex : 'superviseManageOpinionValue',
				renderer : function(value) {
					if (value == "") {
						return "正常贷款";
					} else {
						return value;
					}
				}
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
	},
	
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
	},
	//导出到Excel
	toExcel:function(){
		var superviseManageOpinion=this.searchPanel.getCmpByName('superviseManageOpinion').getValue();
		var hangyeName=this.searchPanel.getCmpByName('hangyeName').getValue();
		window.open(__ctxPath + '/fund/industryProjectListToExcelBpFundProject.do?' 
				+'&superviseManageOpinion='+superviseManageOpinion
				+"&hangyeName="+hangyeName
		);
	}
});
