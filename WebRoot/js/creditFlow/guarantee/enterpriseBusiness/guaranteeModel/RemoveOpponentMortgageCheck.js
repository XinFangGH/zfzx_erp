Ext.ns('opponentMortgageCheckView');
/**
 * @author:
 * @class opponentMortgageCheckView
 * @extends Ext.Panel
 * @description 反担保措施登记
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
RemoveOpponentMortgageCheck = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.projectId)!="undefined")
				{
				      this.projectId=_cfg.projectId;
				}
				if(typeof(_cfg.projectId)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				RemoveOpponentMortgageCheck.superclass.constructor.call(this, {
							//id : 'opponentMortgageCheckView',
							layout:'anchor',
		  					anchor : '100%',
							items : [this.editorGridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false
								});
				this.expanderFlow = new Ext.ux.grid.RowExpander({
					tpl : new Ext.Template('<table>{contractContent}</table>'),
					lazyRender : false, 
					enableCaching : false
					//hidden : this.isSeeContractHidden
				});
				this.topbar = new Ext.Toolbar({
					items : [
//						{
//						iconCls : 'btn-update',
//						text : '编辑',
//						xtype : 'button',
//						scope : this,
//						handler : this.editMortgage
//					},
						{
						iconCls : 'btn-readdocument',
						text : '查看反担保措施详情',
						xtype : 'button',
						scope : this,
						handler : this.seeMortgageInfo
					}
					,{
						iconCls : 'btn-readdocument',
						text : '查看合同详情',
						xtype : 'button',
						scope : this,
						handler : function(){this.seeThirdContract(this.businessType, this.projectId,false,false)}
					}
					]
				});
				var transactCheckColumn =new Ext.grid.CheckColumn({
					header : "是否办理",
					width : 65,
					editable : false,
					dataIndex : 'isTransact'
				});
				var relieveCheckColumn = new Ext.grid.CheckColumn({
					header : '是否解除',
					dataIndex : 'isunchain',
					width : 70
				});
				this.editorGridPanel = new HT.EditorGridPanel({
					tbar:this.topbar,
					//region : 'center',
					scope : this,
					autoScroll : true,
					autoWidth : true,
					layout : 'anchor',
					clicksToEdit : 1,
					viewConfig : {
						forceFit : true
					},
					bbar : false,
					showPaging : false,
					stripeRows : true,
					plugins : [this.expanderFlow,transactCheckColumn,relieveCheckColumn],
					plain : true,
					loadMask : true,
					autoHeight : true,
					url : __ctxPath +'/credit/mortgage/ajaxGetMortgageAllDataByProjectId.do?projectId='+this.projectId+'&&businessType='+this.businessType,//this.projectId,//测试projectId:327
					fields : [{
								name : 'id',
								type : 'int'
							}, 'mortgagename','mortgagepersontypeforvalue','assureofname', 
					           'assuremoney','finalprice','remarks','assuretypeidValue','personTypeValue',
					           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
					           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
					           'statusidValue','enregisterperson','unchainofperson','enregisterdate',
					           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
					          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit','contractContent',
					           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','remarks','isTransact','transactDate','isunchain','unchainremark','enregisterDepartment','businessType'],
					columns : [this.expanderFlow, {
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},{
						header : "",
						width : 0,
						dataIndex : 'typeid',
						hidden:true
					}, {
						header : "反担保物类型",
						width : 80,
						//sortable : true,
						dataIndex : 'mortgagepersontypeforvalue'
					},{
						header : "反担保物名称",
						width : 130,
						dataIndex : 'mortgagename'
					}, {
						header : "反担保物所有人",
						width : 130,
						//sortable : true,
						dataIndex : 'assureofnameEnterOrPerson'
					}, {
						header : "最终估价",
						width : 130,
						//sortable : true,
						dataIndex : 'finalprice',
						align : 'right',
						renderer : function(v){
							if(v==''||v=='null'||v==null){
								return '';
							}else{
								return v+'万元';
							}
						}
					}, {
						header : "反担保物登记号",
						width : 130,
						//sortable : true,
						dataIndex : 'pledgenumber'					
					}, {
						header : "反担保物登记机关",
						width : 130,
						//sortable : true,
						dataIndex : 'enregisterDepartment'
					}, transactCheckColumn,/*{
						header : "是否办理",
						width : 130,
						dataIndex : 'isTransact',
						renderer : function(v) {
									switch (v) {
										case true :
											return '是';
											break;
										case false :
											return '否';
											break;
									}
								}
						
					}, */{
						header : "办理时间",
						width : 130,
						//sortable : true,
						dataIndex : 'transactDate'
					}, relieveCheckColumn,/*{
						header : "是否解除",
						width : 130,
						//sortable : true,
						dataIndex :'isunchain',
						editor : new Ext.form.ComboBox({
							mode : 'local',
							editable : false,
							store : new Ext.data.SimpleStore({
								data : [['是', true], ['否', false]],
								fields : ['text', 'value']
							}),
							displayField : 'text',
							valueField : 'value',
							triggerAction : 'all'
						}),
						renderer : this.doTransact
					}, */{
						header : "解除日期",
						width : 130,
						//xtype : 'datecolumn',
						format : 'Y-m-d',
						//sortable : true,
						dataIndex : 'unchaindate',
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor :this.datafield
					}, {
						header : "备注",
						width : 130,
						dataIndex : 'unchainremark',
//						renderer : function(v){
//							alert(v);
//							return v;
//						},//ZW.ux.dateRenderer(new Ext.form.TextField()),
						editor :new Ext.form.TextField()
					}],// end of columns
					listeners : {
						scope : this,
						//EditorGridPanel数据发生改变时请求后台方法修改数据并重新加载显示数据
						afteredit : function(e) {
							var args;
							var value = e.value;
							var id = e.record.data['id'];
							if (e.originalValue != e.value) {//编辑行数据发生改变
								if(e.field == 'isunchain') {//修改列为"是否办理"列
									args = {'procreditMortgage.isunchain': value,'procreditMortgage.id': id};
								}
								if(e.field == 'unchaindate') {//修改列为"办理时间"列
									args = {'procreditMortgage.unchaindate': value,'procreditMortgage.id': id};
								}
								if(e.field == 'unchainremark') {//修改列为"办理时间"列
									args = {'procreditMortgage.unchainremark': value,'procreditMortgage.id': id};
								}
								Ext.Ajax.request({
									url : __ctxPath+'/creditFlow/fileUploads/ajaxArchiveConfirmFileForm.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										e.record.commit();
										//this.editorGridPanel.getStore().reload();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试');
									},
									params : args
								})
							}
						}
					}
				});

				this.editorGridPanel.addListener('rowdblclick', this.rowClick);
			},// end of the initComponents()
			doTransact : function(v){
				if(v==''||v==null){
					return '<font color=red>否</font>';
				}else if(v==true){
					return '<font color=green>是</font>';
				}else if(v==false){
					return '<font color=red>否</font>';
				}
			},
			// 按ID删除记录
			removeMortgage : function() {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
				var grid=this.editorGridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择记录!');
				}else{
					var mortgageIds = "";
					//var typeIds = "";
					var rows = this.editorGridPanel.getSelectionModel().getSelections();
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
									Ext.ux.Toast.msg('状态', '删除记录成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
									//refreshMortgageGridStore();
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('状态','删除记录失败!');
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
			editMortgage : function() {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
					var mortgageId = selected.get('id');
					var typeId = selected.get('typeid');
					if(typeId == 1){
							updateMortgageCar(mortgageId,refreshMortgageGridStore) ;
						}else if(typeId == 2){
							updateStockOwnerShip(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 3){
							updateCompany(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 4){
							updateDutyPerson(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 5){
							updateMachineInfo(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 6){
							updateProduct(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 7){
							updateHouse(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 8){
							updateOfficeBuilding(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 9){
							updateHouseGround(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 10){
							updateBusiness(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 11){
							updateBusinessAndLive(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 12){
							updateEducation(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 13){
							updateIndustry(mortgageId,refreshMortgageGridStore);
						}else if(typeId == 14){
							updateDroit(mortgageId,refreshMortgageGridStore);
						}
				}
			},
			//查看详情
			seeMortgageInfo : function() {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
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
			//查看合同详情
			
			seeThirdContract : function(businessType, piKey,isSignHidden,isgdHidden) {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
				var thisPanel = this.editorGridPanel;
				var rows = this.editorGridPanel.getSelectionModel().getSelections();
				if(rows.length >1){
					Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
					return;
				}
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('mortgagename');
					var thirdRalationId = selected.get('id');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('categoryId');
					var temptId = selected.get('temptId');
					var window = new OperateThirdContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,
								htType :'thirdContract',
								thisPanel : thisPanel,
								isHidden : false,
								isqsHidden : isSignHidden,
								isgdHidden :isgdHidden
							});
					window.show();
					window.addListener({
								'close' : function() {
									//thisPanel.getStore().reload();
								}
							});
				}
			}
			/*isFull : function(v){
				if(v==1){
					return '<font color=green>是</font>';
				}else if(v==0){
					return '<font color=red>否</font>';
				}
			},
			isStatus: function(v){
				if(v==1){
					return '<font color=green>是</font>';
				}else if(v==0){
					return '<font color=red>否</font>';
				}
			}*/
		});
		
