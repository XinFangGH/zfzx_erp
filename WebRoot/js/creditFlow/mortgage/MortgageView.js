/**
 * @author:
 * @class MortgageView
 * @extends Ext.Panel
 * @description [MortgageView]对方抵质押物管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
 var refreshMortgageGridStore = function(){
	Ext.getCmp('MortgageGridId').getStore().reload();
}
MortgageView = Ext.extend(Ext.Panel, {
			// 构造函数
			gridPanel:null,
			isHidden:false,
			isHiddenRelieve :true,
			isHiddenInArchiveConfirm : false,
			isHiddenAddBtn : true,
			isHiddenDelBtn : true,
			isHiddenEdiBtn : true,
			isHiddenDelContractBtn : true,
			isHiddenEdiContractBtn : true,
			constructor : function(_cfg) {
	
				if(typeof(_cfg.isHiddenRelieve)!="undefined")
			    {
			          this.isHiddenRelieve=_cfg.isHiddenRelieve;
			    }
				if(typeof(_cfg.projectId)!="undefined")
	            {
	                  this.projectId=_cfg.projectId;
	            }
				if(typeof(_cfg.businessType)!="undefined")
	            {
	                  this.businessType=_cfg.businessType;
	            }
				if (_cfg.isHidden) {
				   this.isHidden = _cfg.isHidden;
			    }
			    if(typeof(_cfg.isHiddenAffrim != "undefined")) {
			    	this.isHiddenAffrim = _cfg.isHiddenAffrim;
			    }
			    if(typeof(_cfg.isContractHidden)!="undefined")
	            {
	                  this.isContractHidden=_cfg.isContractHidden;
	            }else{
	            	 this.isContractHidden=true
	            }
	            if(typeof(_cfg.isgdEdit)!="undefined"){
	            	this.isgdEdit=_cfg.isgdEdit;
	            }else{
	            	this.isgdEdit=false;
	            }
	            if(typeof(_cfg.isRemarksEdit)!="undefined"){
	            	this.isRemarksEdit=_cfg.isRemarksEdit
	            }
	            if(typeof(_cfg.isfwEdit)!="undefined")
	            {
	                  this.isfwEdit=_cfg.isfwEdit;
	            }else{
	            	 this.isfwEdit=false
	            }
	            if(typeof(_cfg.isqsEdit)!="undefined")
	            {
	                  this.isqsEdit=_cfg.isqsEdit;
	            }else{
	            	 this.isqsEdit=false
	            }
	            if(typeof(_cfg.isSignHidden)!="undefined")
	            {
	                  this.isSignHidden=_cfg.isSignHidden;
	            }else{
	            	 this.isSignHidden=true
	            }
	            if(typeof(_cfg.isAfterContract)!="undefined")
	            {
	                  this.isAfterContract=_cfg.isAfterContract;
	            }else{
	            	 this.isAfterContract=false
	            }
	            if(typeof(_cfg.isSeeContractHidden)!="undefined")
	            {
	                  this.isSeeContractHidden=_cfg.isSeeContractHidden;
	            }else{
	            	 this.isSeeContractHidden=true
	            }
	            if (_cfg.isHiddenInArchiveConfirm) {
				   this.isHiddenInArchiveConfirm = _cfg.isHiddenInArchiveConfirm;
			    }
			    if (typeof(_cfg.isHiddenTransact)!="undefined") {
				   this.isHiddenTransact = false;
			    }else {
			    	this.isHiddenTransact = true;
			    }
			    if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
					this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
				}
				if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
					this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
				}
				if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
					this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
				}
				if (typeof(_cfg.isHiddenDelContractBtn) != "undefined") {
					this.isHiddenDelContractBtn = _cfg.isHiddenDelContractBtn;
				}
				if (typeof(_cfg.isHiddenEdiContractBtn) != "undefined") {
					this.isHiddenEdiContractBtn = _cfg.isHiddenEdiContractBtn;
				}
				Ext.applyIf(this, _cfg);
				var refresh;
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				MortgageView.superclass.constructor.call(this, {
							layout:'anchor',
		  					anchor : '100%',
							//title : '抵质押物管理',
							//region : 'center',
							//layout : 'border',
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.datafield=new Ext.form.DateField( {
					format : 'Y-m-d',
					allowBlank : false
				})
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加担保措施',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenAddBtn,
										handler : this.createMortgage
									},new Ext.Toolbar.Separator({
										hidden : this.isHiddenAddBtn
									}), {
										iconCls : 'btn-update',
										text : '编辑担保措施',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenEdiBtn,
										handler : this.editMortgage
									},new Ext.Toolbar.Separator({
										hidden : this.isHiddenEdiBtn
									}),{
										iconCls : 'btn-del',
										text : '删除担保措施',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenDelBtn,
										handler : this.removeMortgage
									},new Ext.Toolbar.Separator({
										hidden : this.isHiddenDelBtn
									}),{
										iconCls : 'btn-readdocument',
										text : '查看担保措施详情',
										xtype : 'button',
										scope : this,
										handler : this.seeMortgageInfo
									},new Ext.Toolbar.Separator({
										hidden : this.isHiddenEdiContractBtn
									}),{
										iconCls : 'btn-edit',
										text : '编辑合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenEdiContractBtn,
										handler : function(){
											this.operateThirdContract(this.businessType, this.projectId)
										}
									},new Ext.Toolbar.Separator({
										hidden : this.isHiddenDelContractBtn
									}), {
										iconCls : 'btn-del',
										text : '删除合同',
										xtype : 'button',
										//id : "mortgagecontractremoveBtn",
										hidden : this.isHiddenDelContractBtn,
										scope : this,
										handler : function(panel) {
											var selRs = this.gridPanel.getSelectionModel().getSelections();
											if(selRs.length==0){
											   Ext.ux.Toast.msg('操作信息','请选择一条记录！');
											   return;
											}
											Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
												if (btn == "yes") {
						
													panel.ownerCt.ownerCt.stopEditing();
													var s = panel.ownerCt.ownerCt.getSelectionModel()
															.getSelections();
													var storethird = panel.ownerCt.ownerCt.getStore();
													for (var i = 0; i < s.length; i++) {
														var row = s[i];
														if (row.data.contractId == null || row.data.contractId == '') {
															Ext.ux.Toast.msg('状态', '此反担保物暂无相关的合同提供删除!');
															//jStore_contractCategroy.remove(row);
														} else {
						
															SlContractView.deleteFun(
																			__ctxPath
																					+ '/contract/deleteContractCategoryRecordProcreditContract.do',
																			{
																				categoryId : row.data.categoryId
																			}, function() {
																				Ext.ux.Toast.msg('状态', '此反担保物相关的合同删除成功!');
																				storethird.reload();
																			});
														}
													}
						
												}
											})
						
										}
									},new Ext.Toolbar.Separator({
										hidden : this.isSeeContractHidden
									}),{
										iconCls : 'btn-readdocument',
										text : '查看合同详情',
										xtype : 'button',
										scope:this,
										hidden : this.isSeeContractHidden,
										handler : function(){
											this.seeThirdContract(this.businessType, this.projectId)
										}
									}]
						});
				var checkColumn = new Ext.grid.CheckColumn({
					hidden : this.isHiddenAffrim,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
				});
				var relieveCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isHiddenRelieve,
					header : '是否解除',
					dataIndex : 'isunchain',
					width : 70
				});
				var contractCheckColumn =new Ext.grid.CheckColumn({
					header : "是否法务确认",
					width : 90,
					dataIndex : 'isLegalCheck',
					editable : this.isfwEdit,
					hidden : this.isContractHidden,
					scope : this
				});
				var issignContractcheckColumn = new Ext.grid.CheckColumn({
					header : '是否签署并检验合格',
					width : 128,
					editable : this.isqsEdit,
					hidden : this.isSignHidden,
					dataIndex : 'issign'
				});
				var transactCheckColumn =new Ext.grid.CheckColumn({
					header : "是否办理手续",
					width : 95,
					dataIndex : 'isTransact',
					hidden : this.isHiddenTransact,
					editable : !this.isHiddenTransact
				});
				this.gridPanel = new HT.EditorGridPanel({
					hiddenCm : this.isHidden,
					plugins : [relieveCheckColumn,checkColumn,contractCheckColumn,issignContractcheckColumn,transactCheckColumn],
					border : false,
					clicksToEdit : 1,
					//region : 'center',
					tbar : this.topbar,
					isShowTbar : this.isHidden? false : true,
					showPaging : false,
					autoHeight : true,
					//plugins : mortgageDatas,
					// 使用RowActions
					rowActions : true,
					id : 'MortgageGridId',
					url : __ctxPath +'/credit/mortgage/ajaxGetMortgageAllDataByProjectId.do?projectId='+this.projectId+'&businessType='+this.businessType,
					fields : [{
								name : 'id',
								type : 'int'
							}, 'mortgagename','mortgagepersontypeforvalue','assureofname', 
					           'assuremoney','finalprice','remarks','assuretypeidValue','personTypeValue',
					           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
					           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
					           'statusidValue','enregisterperson','unchainofperson','enregisterdate','isunchain',
					           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
					          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
					           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','businessType',
					           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId','temptId','issign','signDate','fileCount','isTransact','transactDate','fileCounts'],
					columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},{
						header : "反担保物名称",
						width : 170,
						dataIndex : 'mortgagename'
					}, {
						header : "反担保物类型",
						width : 90,
						sortable : true,
						hidden : this.isHiddenInArchiveConfirm,
						dataIndex : 'mortgagepersontypeforvalue'
					}, {
						header : "所有权人",
						width : 250,
						sortable : true,
						hidden : this.isHiddenInArchiveConfirm,
						dataIndex : 'assureofnameEnterOrPerson'					
					}, {
						header : "最终估价",
						width : 247,
						sortable : true,
						align : 'right',
						hidden :this.isAfterContract,
						dataIndex : 'finalprice',
						renderer : function(v){
							if(v==''||v=='null'||v==null){
								return '';
							}else{
								return v+'万元';
							}
						}
					},{
						header : "可担保额度",
						width : 247,
						sortable : true,
						hidden :this.isAfterContract,
						align : 'right',
						dataIndex : 'assuremoney',
						renderer : function(v){
							if(v==''||v=='null'||v==null){
								return '';
							}else{
								return v+'万元';
							}
						}
					},{
						header : "所有人类型",
						width : 75,
						sortable : true,
						align : 'center',
						hidden :this.isAfterContract,
						dataIndex : 'personTypeValue'
					},{
						header : "",
						width : 0,
						sortable : true,
						dataIndex : 'typeid',
						hidden:true
					}
					,{
						header : '合同名称',	
						width : 150,
						hidden : this.isContractHidden,
						dataIndex : 'contractCategoryText'
					}
					,contractCheckColumn,
					relieveCheckColumn,
					issignContractcheckColumn,
					{
						header : '签署时间',
						format : 'Y-m-d',
						width : 80,
						dataIndex : 'signDate',
						hidden : this.isSignHidden,
						editable : this.isqsEdit,
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor : new Ext.form.DateField( {
							format : 'Y-m-d',
							allowBlank : false,
							blankText : '签署日期不可为空!'
						})
					},transactCheckColumn,{
						header : "办理时间",
						width : 80,
						hidden : this.isHiddenTransact,
						format : 'Y-m-d',
						dataIndex : 'transactDate',
						editable : !this.isHiddenTransact,
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor :this.datafield
					},{
						header : '办理文件',
						dataIndex : 'fileCounts',
						width : 70,
						hidden : this.isHiddenTransact,
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '<a href="#" title ="点击可上传" <font color=blue>0</font></a>';							
							}else{
								return '<a href="#" title ="点击可上传或下载" <font color=blue>'+v+'</font></a>';
								
							}
						}
					},{
						header : '预览',
						width : 45,
						hidden : this.isHiddenTransact,
						dataIndex : 'fileCounts',
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '';
							}else{
								return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
							}
						}
					},checkColumn, {
						header : '归档备注',
						dataIndex : 'remarks',
						align : "center",
						width : 116,
						hidden : this.isHiddenAffrim,
						editor : new Ext.form.TextField({readOnly:this.isRemarksEdit})
					},
					new Ext.ux.grid.RowActions({
							header : '管理',
							width : 0,
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
						})],
						// end of columns
						listeners : {
							scope : this,
							afteredit : function(e) {
								var value = e.value;
								var id = e.record.data['id'];
								if (e.originalValue != e.value) {//编辑行数据发生改变
									if(e.field == 'isArchiveConfirm') {
										Ext.Ajax.request({
											url : __ctxPath+'/credit/mortgage/ajaxArchiveConfirm.do',
											method : 'POST',
											scope : this,
											success : function(response, request) {
												this.gridPanel.getStore().reload();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态', '操作失败，请重试');
											},
											params : {'procreditMortgage.isArchiveConfirm': value,'procreditMortgage.id': id}
										})
									}
									if(e.field == 'remarks') {
										Ext.Ajax.request({
											url : __ctxPath+'/credit/mortgage/ajaxArchiveConfirm.do',
											method : 'POST',
											scope : this,
											success : function(response, request) {
												this.gridPanel.getStore().reload();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态', '操作失败，请重试');
											},
											params :{'procreditMortgage.remarks': value,'procreditMortgage.id': id}
										})
									}
									if(e.field == 'isunchain'){//是否解除
										if(e.record.data['contractId']==null){
											Ext.ux.Toast.msg('提示', '操作无效，请先生成合同！');
											this.gridPanel.getStore().reload();
										}else{
									
											Ext.Ajax.request({
												url : __ctxPath+'/credit/mortgage/updateMortgage.do',
												method : 'POST',
												scope : this,
												success : function(response, request) {
													this.gridPanel.getStore().reload();
												},
												failure : function(response) {
													Ext.ux.Toast.msg('状态', '操作失败，请重试');
												},
												params: {
													'procreditMortgage.isunchain': e.value,
													mortgageid: e.record.data['id']
												}
											})
										}
										
									}
									if(e.field == 'isLegalCheck'){//是否法务确认
										if(e.record.data['contractId']==null){
											Ext.ux.Toast.msg('提示', '操作无效，请先生成合同！');
											this.gridPanel.getStore().reload();
										}else{
											Ext.Ajax.request({
												url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
												method : 'POST',
												scope : this,
												success : function(response, request) {
													this.gridPanel.getStore().reload();
												},
												failure : function(response) {
													Ext.ux.Toast.msg('状态', '操作失败，请重试');
												},
												params: {
													'procreditContract.isLegalCheck':  e.value,
													'procreditContract.id': e.record.data['contractId'],
													projId : this.projectId,
													businessType :this.businessType
												}
											})
										}
										
									}
									if(e.field == 'issign'){
										if(e.record.data['contractId']==null){
											Ext.ux.Toast.msg('提示', '操作无效，请先生成合同！');
											this.gridPanel.getStore().reload();
										}else{
											Ext.Ajax.request({
												url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
												method : 'POST',
												scope : this,
												success : function(response, request) {
													this.gridPanel.getStore().reload();
												},
												failure : function(response) {
													Ext.ux.Toast.msg('状态', '操作失败，请重试');
												},
												params: {
													'procreditContract.issign':  e.value,
													'procreditContract.id': e.record.data['contractId'],
													projId : this.projectId,
													businessType :this.businessType
												}
											})
										}
										
									}
									if(e.field == 'signDate'){
										if(e.record.data['contractId']==null){
											Ext.ux.Toast.msg('提示', '操作无效，请先生成合同！');
											this.gridPanel.getStore().reload();
										}else{
											Ext.Ajax.request({
												url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
												method : 'POST',
												scope : this,
												success : function(response, request) {
													this.gridPanel.getStore().reload();
												},
												failure : function(response) {
													Ext.ux.Toast.msg('状态', '操作失败，请重试');
												},
												params: {
													'procreditContract.signDate': e.value,
													'procreditContract.id': e.record.data['contractId'],
													projId : this.projectId,
													businessType :this.businessType
												}
											})
										}
										
									}
									if(e.field == 'isTransact'){
										Ext.Ajax.request({
											url : __ctxPath+'/credit/mortgage/updateMortgage.do',
											method : 'POST',
											scope : this,
											success : function(response, request) {
												this.gridPanel.getStore().reload();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态', '操作失败，请重试');
											},
											params: {
												'procreditMortgage.isTransact': e.value,
												mortgageid: e.record.data['id']
											}
										})
									}
								}
							},
							rowdblclick : function(grid, rowIndex, e) {
								this.seeMortgageInfo();
							}
						} 
				});
				/*this.gridPanel.getSelectionModel().on('selectionchange',
					function(sm) {
						Ext.getCmp('mortgagecontractremoveBtn')
								.setDisabled(sm.getCount() < 1 ? 1 : 0);
					});*/
			},// end of the initComponents()
			opponentMortgageAffrimRenderer : function(v){
				return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
			},
			// 创建记录
			createMortgage : function() {
				//addMortgage(this.projectId,1,2,refreshMortgageGridStore,this.businessType);
				addMortgage(this.projectId,refreshMortgageGridStore,this.businessType,false);
			},
			// 按ID删除记录
			removeMortgage : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择记录!');
				}else{
					var mortgageIds = "";
					//var typeIds = "";
					var rows = this.gridPanel.getSelectionModel().getSelections();
					for(var i=0;i<rows.length;i++){
						mortgageIds = mortgageIds+rows[i].get('id');
						//typeIds = typeIds+rows[i].get('typeid');
						if(i!=rows.length-1){
							mortgageIds = mortgageIds+',';
							//typeIds = typeIds+',';
						}
					}
					Ext.MessageBox.confirm('确认删除', '该记录在附表同时存在相应记录,您确认要一并删除? ', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/deleteMortgage.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除成功!');
									//Ext.Msg.alert('状态', '删除记录成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
									//refreshMortgageGridStore();
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('操作信息', '删除失败!');
									//Ext.Msg.alert('状态','删除记录失败!');
								},
								params: { 
									mortgageIds: mortgageIds
									//typeIds: typeIds
								}
							});
						}
					});
				}
			},
			// 编辑Rs
			
			editMortgage : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
					var mortgageId = selected.get('id');
					var typeId = selected.get('typeid');
					var businessType = selected.get('businessType');
					if(typeId == 1){
							updateMortgageCar(mortgageId,refreshMortgageGridStore,businessType,false) ;
						}else if(typeId == 2){
							updateStockOwnerShip(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 3){
							updateCompany(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 4){
							updateDutyPerson(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 5){
							updateMachineInfo(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 6){
							updateProduct(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 7){
							updateHouse(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 8){
							updateOfficeBuilding(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 9){
							updateHouseGround(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 10){
							updateBusiness(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 11){
							updateBusinessAndLive(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 12){
							updateEducation(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 13){
							updateIndustry(mortgageId,refreshMortgageGridStore,businessType);
						}else if(typeId == 14){
							updateDroit(mortgageId,refreshMortgageGridStore,businessType);
						}
				}
			},
			//查看详情
			seeMortgageInfo : function() {
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
						this.removeMortgage.call(this, record.data.id);
						break;
					case 'btn-edit' :
						this.editMortgage.call(this, record);
						break;
					default :
						break;
				}
			},
			operateThirdContract : function(businessType, piKey) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var thisPanel = this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('mortgagename');
					var thirdRalationId = selected.get('id');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('categoryId');
					var temptId = selected.get('temptId');
					var window = new OperateContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,
								htType :'thirdContract',
								thisPanel : thisPanel
							});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});
				}
			},
			seeThirdContract : function(businessType, piKey) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('mortgagename');
					var thirdRalationId = selected.get('id');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('categoryId');
					var temptId = selected.get('temptId');
					if(contractId != null){
						var window = new SeeThirdContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId,
								temptId :temptId
							});
						window.show();
					}else{
						Ext.ux.Toast.msg('状态', '此反担保物暂无相关的合同!');
					}
					
				}
			}
		});
		
