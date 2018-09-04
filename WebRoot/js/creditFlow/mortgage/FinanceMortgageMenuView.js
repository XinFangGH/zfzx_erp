/**
 * @author:
 * @class FinanceMortgageMenuView
 * @extends Ext.Panel
 * @description [FinanceMortgageMenuView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
FinanceMortgageMenuView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		FinanceMortgageMenuView.superclass.constructor.call(this, {
					id : 'FinanceMortgageMenuView',
					title : '我方抵质押物管理',
					region : 'center',
					layout : 'border',
					iconCls : 'menu-product',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					layout : 'column',
					region : 'north',
					height : 36,
					border : false,
					bodyStyle : 'padding:5px 10px 5px 10px',
					items : [{
						columnWidth : 0.22,
						layout : 'form',
						border : false,
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
							xtype : 'combo',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [['车辆', 1], ['股权', 2],
												['无限连带责任-公司', 3],
												['无限连带责任-个人', 4], ['机器设备', 5],
												['存货/商品', 6], ['住宅', 7],
												['商铺写字楼', 8], ['住宅用地', 9],
												['商业用地', 10], ['商住用地', 11],
												['教育用地', 12], ['工业用地', 13],
												['无形权利', 14]]
									}),
							triggerAction : "all",
							hiddenName : "Q_mortgageType_SN_EQ",
							name : 'Q_mortgageType_SN_EQ',
							anchor : "98%",
							fieldLabel : '抵质押物类型'
						}]
					}, {
						columnWidth : 0.2,
						layout : 'form',
						border : false,
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
									fieldLabel : '抵质押物名称',
									anchor : "100%",
									name : 'Q_mortgageName_S_LK',
									xtype : 'textfield'
								}]
					}, {
						columnWidth : 0.18,
						layout : 'form',
						border : false,
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
									fieldLabel : '最终估价',
									anchor : "98%",
									fieldClass : 'field-align',
									name : 'Q_finalEstimatePrice_BD_GE',
									xtype : 'numberfield'
								}]
					}, {
						columnWidth : 0.04,
						layout : 'form',
						border : false,
						labelWidth : 30,
						labelAlign : 'right',
						items : [{
									border : false,
									fieldLabel : '万元',
									labelSeparator : '',
									anchor : "98%"
								}]
					}, {
						columnWidth : 0.12,
						layout : 'form',
						border : false,
						labelWidth : 20,
						labelAlign : 'right',
						items : [{
									fieldLabel : '~',
									labelSeparator : '',
									fieldClass : 'field-align',
									name : 'Q_finalEstimatePrice_BD_LE',
									anchor : "98%",
									xtype : 'numberfield'
								}]
					}, {
						columnWidth : 0.04,
						layout : 'form',
						border : false,
						labelWidth : 30,
						items : [{
									fieldLabel : '万元',
									border : false,
									labelSeparator : '',
									anchor : "100%"
								}]
					}, {
						columnWidth : 0.1,
						layout : 'form',
						border : false,
						labelWidth : 5,
						labelAlign : 'right',

						items : [{
								xtype:"button",
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}]
					}, {
						columnWidth : 0.1,
						layout : 'form',
						border : false,
						labelWidth : 5,
						labelAlign : 'right',

						items : [ {
								xtype:"button",
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加抵质押物',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_create_mg') ? false : true,
								handler : this.createRs
							}, {
								iconCls : 'btn-del',
								text : '删除抵质押物',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_remove_mg') ? false : true,
								handler : this.removeSelRs
							}, {
								iconCls : 'btn-edit',
								text : '编辑抵质押物',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_edit_mg') ? false : true,
								handler : this.editRs
							}, {
								iconCls : 'btn-detail',
								text : '查看抵质押物',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_see_mg') ? false : true,
								handler : this.seeRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'FinanceMortgageMenuViewGrid',
			url : __ctxPath + '/credit/mortgage/getMortgageFinance.do',
			fields : [{
						name : 'id',
						type : 'int'
					}, 'mortgagename', 'mortgagepersontypeforvalue',
					'assureofname', 'assuremoney', 'finalprice', 'remarks',
					'assuretypeidValue', 'personTypeValue',
					'mortgagepersontypeidValue', 'assuremodeidValue',
					'enregisterDatumList', 'pledgenumber', 'bargainNum',
					'isAuditingPassValue', 'planCompleteDate', 'statusidValue',
					'enregisterperson', 'unchainofperson', 'enregisterdate',
					'isunchain', 'unchaindate', 'typeid', 'others',
					'assureofnameEnterOrPerson', 'relation', 'needDatumList',
					'receiveDatumList', 'lessDatumRecord', 'businessPromise',
					'replenishTimeLimit', 'isReplenish', 'replenishDate',
					'personTypeId', 'projnum', 'projname', 'enterprisename',
					'isArchiveConfirm', 'businessType',
					'contractCategoryTypeText', 'contractCategoryText',
					'isLegalCheck', 'thirdRalationId', 'contractid',
					'categoryId', 'temptId', 'issign', 'signDate', 'fileCount',
					'isTransact', 'transactDate', 'fileCounts',
					'contractContent', 'contractCount', 'isPledged'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : "抵质押物名称",
						width : 170,
						dataIndex : 'mortgagename'
					}, {
						header : "抵质押物类型",
						width : 90,
						sortable : true,
						dataIndex : 'mortgagepersontypeforvalue'
					}, {
						header : "所有权人",
						width : 250,
						sortable : true,
						dataIndex : 'assureofnameEnterOrPerson'
					}, {
						header : "最终估价",
						width : 90,
						sortable : true,
						dataIndex : 'finalprice',
						renderer : function(v) {
							if (v == '' || v == 'null' || v == null) {
								return '';
							} else {
								return v + '万元';
							}
						}
					}, {
						header : "可担保额度",
						width : 90,
						sortable : true,
						dataIndex : 'assuremoney',
						renderer : function(v) {
							if (v == '' || v == 'null' || v == null) {
								return '';
							} else {
								return v + '万元';
							}
						}
					}, {
						header : "所有人类型",
						width : 75,
						sortable : true,
						dataIndex : 'personTypeValue'
					}, {
						header : '是否已抵押',
						dataIndex : 'isPledged',
						renderer : function(v) {
							if (v == 0)
								return "否";
							if (v == 1)
								return "是";
						}
					}, {
						header : "",
						width : 0,
						sortable : true,
						dataIndex : 'typeid',
						hidden : true
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								hidden : true,
								actions : [{
											iconCls : 'btn-del',
											qtip : '删除',
											style : 'margin:0 3px 0 3px'
										}, {
											iconCls : 'btn-edit',
											qtip : '编辑',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
				// end of columns
			});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

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
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					/*
					 * new SlMortgageForm({ mortId : rec.data.mortId }).show();
					 */
				});
	},
	// 创建记录
	createRs : function() {
		var thisPanel = this.gridPanel;
		var fl_refreshMortgageGridStore = function() {
			thisPanel.getStore().reload()
		};
		addMortgage(0, fl_refreshMortgageGridStore, "Financing", false);
	},
	// 按ID删除记录
	removeRs : function(id) {
		/*
		 * $postDel({ url : __ctxPath +
		 * '/creditFlow/ourmortgage/multiDelSlMortgage.do', ids : id, grid :
		 * this.gridPanel, error : '确定要删除？' });
		 */
	},
	// 把选中ID删除
	removeSelRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid = this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择记录!');
		} else {
			var mortgageIds = "";
			var rows = this.gridPanel.getSelectionModel().getSelections();
			for (var i = 0; i < rows.length; i++) {
				if (rows[i].get('isPledged') == 1) {
					Ext.ux.Toast.msg('操作信息', '<' + rows[i].get('mortgagename')
									+ '> 已抵(质)押,不能删除!');
					return;
				} else {
					mortgageIds = mortgageIds + rows[i].get('id');
					if (i != rows.length - 1) {
						mortgageIds = mortgageIds + ',';
					}
				}
			}
			Ext.MessageBox.confirm('确认删除', '该记录在附表同时存在相应记录,您确认要一并删除? ',
					function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath
										+ '/credit/mortgage/deleteMortgageFalse.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('操作信息', '删除失败!');
								},
								params : {
									mortgageIds : mortgageIds
								}
							});
						}
					});
		}
	},
	seeRs :function(){
	var thisPanel = this.gridPanel;
		var fl_refreshMortgageGridStore = function() {
			thisPanel.getStore().reload()
		};
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var mortgageId = selected.get('id');
			var typeId = selected.get('typeid');
			var businessType = selected.get('businessType');
			var isPledged=selected.get('isPledged');
			if(isPledged==1){
		           if(typeId==1){
						seeCarInfo(mortgageId,businessType);
					}else if(typeId==2){
						seeStockownershipInfo(mortgageId,businessType);
					}else if(typeId==3){
						seeCompanyInfo(mortgageId,businessType);
					}else if(typeId==4){
						seeDutyPersonInfo(mortgageId,businessType);
					}else if(typeId==5){
						seeMachineInfo(mortgageId,businessType);
					}else if(typeId==6){
						seeProductInfo(mortgageId,businessType);
					}else if(typeId==7){
						seeHouseInfo(mortgageId,businessType);
					}else if(typeId==8){
						seeOfficeBuildingInfo(mortgageId,businessType);
					}else if(typeId==9){
						seeHouseGroundInfo(mortgageId,businessType);
					}else if(typeId==10){
						seeBusinessInfo(mortgageId,businessType);
					}else if(typeId==11){
						seeBusinessAndLiveInfo(mortgageId,businessType);
					}else if(typeId==12){
						seeEducationInfo(mortgageId,businessType);
					}else if(typeId==13){
						seeIndustryInfo(mortgageId,businessType);
					}else if(typeId==14){
						seeDroitUpdateInfo(mortgageId,businessType);
					}
					
				}else{
				
					if (typeId == 1) {
						see_MortgageCar(mortgageId, fl_refreshMortgageGridStore,
								businessType, false);
					} else if (typeId == 2) {
						see_StockOwnerShip(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 3) {
						see_Company(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 4) {
						see_DutyPerson(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 5) {
						see_MachineInfo(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 6) {
						see_Product(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 7) {
						see_House(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 8) {
						see_OfficeBuilding(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 9) {
						see_HouseGround(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 10) {
						see_Business(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 11) {
						see_BusinessAndLive(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 12) {
						see_Education(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 13) {
						see_Industry(mortgageId, fl_refreshMortgageGridStore,
								businessType);
					} else if (typeId == 14) {
						see_Droit(mortgageId, fl_refreshMortgageGridStore,
								businessType);
			}
				
				
				}
		}
	
	},
	// 编辑
	editRs : function() {
		var thisPanel = this.gridPanel;
		var fl_refreshMortgageGridStore = function() {
			thisPanel.getStore().reload()
		};
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var mortgageId = selected.get('id');
			var typeId = selected.get('typeid');
			var businessType = selected.get('businessType');
			if (typeId == 1) {
				updateMortgageCar(mortgageId, fl_refreshMortgageGridStore,
						businessType, false);
			} else if (typeId == 2) {
				updateStockOwnerShip(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 3) {
				updateCompany(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 4) {
				updateDutyPerson(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 5) {
				updateMachineInfo(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 6) {
				updateProduct(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 7) {
				updateHouse(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 8) {
				updateOfficeBuilding(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 9) {
				updateHouseGround(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 10) {
				updateBusiness(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 11) {
				updateBusinessAndLive(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 12) {
				updateEducation(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 13) {
				updateIndustry(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			} else if (typeId == 14) {
				updateDroit(mortgageId, fl_refreshMortgageGridStore,
						businessType);
			}
		}
		/*
		 * var s = this.gridPanel.getSelectionModel().getSelections(); if (s <=
		 * 0) { Ext.ux.Toast.msg('操作信息','请选中任何一条记录'); return false; }else
		 * if(s.length>1){ Ext.ux.Toast.msg('操作信息','只能选中一条记录'); return false;
		 * }else{ record=s[0] new SlMortgageForm({ mortId : record.data.mortId
		 * }).show(); }
		 */

	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.mortId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
