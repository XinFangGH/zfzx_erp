/**
 * @author
 * @createtime
 * @class FinanceMortgageListView
 * @extends Ext.Window
 * @description FinanceMortgageListView我方抵质押物参照表单
 * @company 智维软件
 */
FinanceMortgageListView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.businessType)!="undefined"){
           this.businessType=_cfg.businessType;
        }
        if(typeof(_cfg.projectId)!="undefined"){
	       this.projectId=_cfg.projectId;
	    }
	     if(typeof(_cfg.projectId)!="undefined"){
	       this.financeGrid=_cfg.financeGrid;
	    }
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		FinanceMortgageListView.superclass.constructor.call(this, {
					id : 'FinanceMortgageListViewWin',
					layout : 'fit',
					items : this.gridPanel,
					modal : true,
					height : 420,
					width : 800,
					maximizable : true,
					title : '请选择我方抵质押物(可多选)',
					buttonAlign : 'center',
					buttons : [{
								text : '提交',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		

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
								iconCls : 'btn-readdocument',
								text : '查看抵质押物',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_edit_mg') ? false : true,
								handler : this.seeRs
							}]
				});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : true,
			id : 'FinanceMortgageListViewGrid',
			url : __ctxPath
					+ '/credit/mortgage/getMortgageFinanceList.do?isPledged=0&type=DY',
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
					'contractContent', 'contractCount','isPledged'],
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
	},// end of the initcomponents

	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {/*
		grid.getSelectionModel().each(function(rec) {
			alert("抵质押物权登记机关==" + rec.data.registAuthority
					+ "              抵质押物名称==" + rec.data.mortgageName);
			slCompanyMainObj = {
				mortgageName : rec.data.mortgageName,
				mortgageOnwer : rec.mortgageOnwer,
				certificateNum : rec.data.certificateNum,
				registAuthority : rec.data.registAuthority
			}
			return slCompanyMainObj;
			this.destroy();
				
			 * funName(slCompanyMainObj); new SlCompanyMainForm( { id :
			 * rec.data.companyMainId }).show(); });
			 
		})
	*/},
	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 提交记录
	 */
	save : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择记录!');
		}else{
			var mortgageIds = "";
			var rows = this.gridPanel.getSelectionModel().getSelections();
			for(var i=0;i<rows.length;i++){
				mortgageIds = mortgageIds+rows[i].get('id');
				if(i!=rows.length-1){
					mortgageIds = mortgageIds+',';
				}
			}
		}
		Ext.Ajax.request({
			url : __ctxPath + '/creditFlow/ourmortgage/saveSlMortgageFinancing.do',
			method : 'POST',
			scope : this,
			success : function(response, request) {
				this.financeGrid.getStore().reload();
				this.close();
			},
			failure : function(response) {
				Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败!');
			},
			params : {
				'mortgageIds' : mortgageIds,
				'projectId' : this.projectId
			}
		})
		
		/*var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		}

		if (rows.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		}
		var mortId = rows[0].get('id');
		var isExits = false;
		var isNull = false;

		Ext.Ajax.request({
			url : __ctxPath + "/creditFlow/ourmortgage/getMortIdsSlMortgageFinancing.do",
			method : 'POST',
			scope : this,
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				var mortIds = obj.data.morIds;
				if (obj.success == true) {
					if (mortIds == "" || typeof(mortIds) == "undefined") {
						isNull = true;
					} else {
						for (var i = 0; i < mortIds.length; i++) {
							if (mortId == mortIds[i]) {
								isExits = true;
							}
						}
					}
					if (isNull || (!isExits)) {
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/ourmortgage/saveSlMortgageFinancing.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								this.grid.getStore().reload();
								this.close();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败!');
							},
							params : {
								'mortId' : mortId,
								'projectId' : this.projectId,
								// 'assureType' : this.assureType,
								'mfinancingId' : this.mfinancingId
							}
						})
					} else {
						Ext.ux.Toast.msg('操作提示', '该抵质押物已存在,请重新选择!');
						return;
					}
				} else {
					Ext.ux.Toast.msg('操作信息', '获取我方抵质押物id错误!');
				}
			},
			failure : function(response, request) {
				Ext.ux.Toast.msg('操作信息', '查询我方抵质押物id操作失败!');
			}
		});*/
	},// end of save
	
	
	// 创建记录
	createRs : function() {
		var thisPanel = this.gridPanel;
		var fl_mortgageStore = function(){thisPanel.getStore().reload()};
		//addMortgage(this.projectId,1,2,fl_refreshMortgageGridStore,this.businessType);
		//addMortgage(0,fl_mortgageStore,this.businessType,false);
		new AddDzyMortgageWin({projectId:0,businessType:this.businessType,gridPanel:this.gridPanel}).show()
		
	},
	
	// 按ID删除记录
	removeSelRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择记录!');
		}else{
			var mortgageIds = "";
			var rows = this.gridPanel.getSelectionModel().getSelections();
			for(var i=0;i<rows.length;i++){
				mortgageIds = mortgageIds+rows[i].get('id');
				if(i!=rows.length-1){
					mortgageIds = mortgageIds+',';
				}
			}
			Ext.MessageBox.confirm('确认删除', '该记录在附表同时存在相应记录,您确认要一并删除? ', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						url : __ctxPath +'/credit/mortgage/deleteMortgageFalse.do',
						method : 'POST',
						success : function() {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
							grid.getStore().reload();
						},
						failure : function(result, action) {
							Ext.ux.Toast.msg('操作信息', '删除失败!');
						},
						params: { 
							mortgageIds: mortgageIds
						}
					});
				}
			});
		}
	},
	
	// 编辑Rs
	editRs : function() {
		var thisPanel = this.gridPanel;
		var fl_refreshMortgageGridStore = function(){thisPanel.getStore().reload()};
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var mortgageId = selected.get('id');
			var typeId = selected.get('typeid');
			var businessType = selected.get('businessType');
			new UpdateDzyMortgageWin({id:mortgageId,projectId:0,businessType:this.businessType,gridPanel:this.gridPanel,type:typeId}).show()

/*			if(typeId == 1){
					updateMortgageCar(mortgageId,fl_refreshMortgageGridStore,businessType,false) ;
				}else if(typeId == 2){
					updateStockOwnerShip(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 3){
					updateCompany(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 4){
					updateDutyPerson(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 5){
					updateMachineInfo(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 6){
					updateProduct(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 7){
					updateHouse(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 8){
					updateOfficeBuilding(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 9){
					updateHouseGround(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 10){
					updateBusiness(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 11){
					updateBusinessAndLive(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 12){
					updateEducation(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 13){
					updateIndustry(mortgageId,fl_refreshMortgageGridStore,businessType);
				}else if(typeId == 14){
					updateDroit(mortgageId,fl_refreshMortgageGridStore,businessType);
				}
*/		}
	},
	
	//查看详情
	seeRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var mortgageId = selected.get('id');
		var typeId = selected.get('typeid');
		var businessType = selected.get('businessType');
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
			}else{
				window.location.href="/error.jsp";
			}
		}
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.save.call(this, record);
				break;
			case 'btn-edit' :
				this.save.call(this, record);
				break;
			default :
				break;
		}
	}
});