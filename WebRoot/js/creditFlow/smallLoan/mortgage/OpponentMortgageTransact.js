/**
 * @author:
 * @class OpponentMortgageTransact
 * @extends Ext.Panel
 * @description 反担保措施办理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
  var refreshMortgageGridStoresl = function(){
	Ext.getCmp('OpponentMortgageTransactId').getStore().reload();
}
OpponentMortgageTransact = Ext.extend(Ext.Panel, {
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
				if(typeof(_cfg.isTransact)!="undefined")
				{
				      this.isTransact=_cfg.isTransact;
				}else{
					this.isTransact = false
				}
				if(typeof(_cfg.isgdEdit)!="undefined")
				{
				      this.isgdEdit=_cfg.isgdEdit;
				}else{
					this.isgdEdit = false
				}
				if(typeof(_cfg.isgdHidden)!="undefined")
				{
				      this.isgdHidden=_cfg.isgdHidden;
				}else{
					this.isgdHidden = true
				}
				if(typeof(_cfg.isHiddenRelieve)!="undefined")
			    {
			          this.isHiddenRelieve=_cfg.isHiddenRelieve;
			    }
			  	if(typeof(_cfg.isgdEdit)!="undefined"){
	            	this.isgdEdit=_cfg.isgdEdit;
	            }else {
	            	this.isgdEdit=false;
	            }
			  	if(typeof(_cfg.isHiddenTransact)!="undefined"){
	            	this.isHiddenTransact=_cfg.isHiddenTransact;
	            }else{
	            	this.isHiddenTransact=false;
	            }
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				OpponentMortgageTransact.superclass.constructor.call(this, {
							//id : 'OpponentMortgageTransact',
							layout:'anchor',
		  					anchor : '100%',
							items : [this.editorGridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var isTransactVar = this.isTransact;
				var isTransactVar = this.isTransact;
				this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false
								});
								
				var checkColumn = new Ext.grid.CheckColumn({
					header : '是否签署并检验合格',
					width : 130,
					editable : false,
					dataIndex : 'issign'
				});
				var contractCheckColumn =new Ext.grid.CheckColumn({
					header : "是否法务确认",
					width : 92,
					dataIndex : 'isLegalCheck',
					editable : false
				});
				var transactCheckColumn =new Ext.grid.CheckColumn({
					header : "是否办理手续",
					width : 95,
					dataIndex : 'isTransact',
					hidden : this.isHiddenTransact,
					editable : this.isTransact
				});
				var gdCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
				});
				var relieveCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isHiddenRelieve,
					header : '是否解除',
					dataIndex : 'isunchain',
				    
					width : 60
				});
				this.topbar = new Ext.Toolbar({
					items : [/*{
						iconCls : 'btn-update',
						text : '编辑',
						xtype : 'button',
						scope : this,
						handler : this.editMortgage
					},*/{
						iconCls : 'btn-add',
						text : '添加担抵质押物',
						xtype : 'button',
						scope : this,
						hidden : this.isButtonHidden,
						handler : this.createMortgage
					},'-',{
						iconCls : 'btn-update',
						text : '编辑抵质押物',
						xtype : 'button',
						scope : this,
						hidden : this.isButtonHidden,
						handler : this.editMortgage
					},'-',{
						iconCls : 'btn-readdocument',
						text : '查看抵质押物详情',
						xtype : 'button',
						scope : this,
						handler : this.seeMortgageInfo
					},'-',{
						iconCls : 'btn-readdocument',
						text : '查看合同详情',
						xtype : 'button',
						scope:this,
						handler : function(){
							this.seeThirdContract(this.businessType, this.projectId)
						}
					}/*,{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						handler : this.removeMortgage
					}*/]
				});
				this.editorGridPanel = new HT.EditorGridPanel({
					id : 'OpponentMortgageTransactId',
					tbar:this.topbar,
					border : false,
					//region : 'center',
					scope : this,
					autoScroll : true,
					autoWidth : true,
					layout : 'anchor',
					clicksToEdit : 1,
					viewConfig : {
						forceFit : true
					},
					plugins : [checkColumn,contractCheckColumn,transactCheckColumn,gdCheckColumn,relieveCheckColumn],
					bbar : false,
					showPaging : false,
					stripeRows : true,
					plain : true,
					loadMask : true,
					autoHeight : true,
					url : __ctxPath +'/credit/mortgage/ajaxGetMortgageAllDataByProjectId.do?projectId='+this.projectId+'&businessType='+this.businessType,//测试projectId:327
					fields : [{
								name : 'id',
								type : 'int'
							}, 'mortgagename','mortgagepersontypeforvalue','assureofname', 
					           'assuremoney','finalprice','remarks','assuretypeidValue','personTypeValue',
					           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
					           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
					           'statusidValue','enregisterperson','unchainofperson','enregisterdate',
					           'unchaindate','isunchain','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
					          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
					           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename',
					           'isArchiveConfirm','remarks','isTransact','transactDate','enregisterDepartment',
					           'businessType','fileCounts','contractCategoryTypeText','contractCategoryText',
					           'isLegalCheck','thirdRalationId','contractId','categoryId','temptId','issign','signDate','fileCount'],
					columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},{
						header : "",
						width : 0,
						dataIndex : 'typeid',
						hidden:true
					},{
						header : "",
						width : 0,
						dataIndex : 'businessType',
						hidden:true
					},{
						header : "抵质押物名称",
						width : 130,
						dataIndex : 'mortgagename'
					},{
						header : "合同名称",
						width : 130,
						dataIndex : 'contractCategoryText'
					},contractCheckColumn
					,checkColumn
					,{
						header : '签署时间',
						format : 'Y-m-d',
						width : 77,
						dataIndex : 'signDate',
						editable : false,
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor : new Ext.form.DateField( {
							format : 'Y-m-d'
						})
					}/*,{
						header : "是否办理手续",
						width : 90,
						//sortable : true,
						dataIndex :'isTransact',
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
					}*/
					,transactCheckColumn,{
						header : "办理时间",
						width : 77,
						//xtype : 'datecolumn',
						format : 'Y-m-d',
						//sortable : true,
						dataIndex : 'transactDate',
						editable : this.isTransact,
						hidden : this.isHiddenTransact,
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor :this.datafield
					},{
						header : '办理文件',
						dataIndex : 'fileCounts',
						width : 71,
						hidden : this.isHiddenTransact,
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '<a href="#" title ="点击可上传" <font color=blue>0</font></a>';							
							}else{
								if(isTransactVar==true){
									return '<a href="#" title ="点击可上传或下载" <font color=blue>'+v+'</font></a>';
								}else{
									return '<a href="#" title ="点击可下载" <font color=blue>'+v+'</font></a>';
								}
								
							}
						}
					}/*,{
						header : '上传或下载',
						width : 80,
						renderer:this.uploadFiles_mortgage
					}*/,{
						header : '预览',
						hidden : this.isHiddenTransact,
						width : 45,
						dataIndex : 'fileCounts',
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '';
							}else{
								return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
							}
						}
						//renderer:this.imageView_mortgage(v)
					},gdCheckColumn,
					{
						header : '归档备注',
						dataIndex : 'remarks',
						hidden : this.isgdHidden,
						
						/*renderer : function(v){
							if(v == "" || v == null) {
								return "<font color=red>点击编辑备注</font>";
							}else{
								return v;
							}
						},*/
						editor : new Ext.form.TextField({readOnly:!this.isgdEdit})
					},relieveCheckColumn],// end of columns
					listeners : {
						scope : this,
						//EditorGridPanel数据发生改变时请求后台方法修改数据并重新加载显示数据
						afteredit : function(e) {
							var args;
							var value = e.value;
							var id = e.record.data['id'];
							if (e.originalValue != e.value) {//编辑行数据发生改变
								/*if(e.field == 'pledgenumber') {//修改列为"反担保物登记号"列
									args = {'procreditMortgage.pledgenumber': value,'procreditMortgage.id': id};
								}
								if(e.field == 'enregisterDepartment') {//修改列为"反担保物登记机关"列
									args = {'procreditMortgage.enregisterDepartment': value,'procreditMortgage.id': id};
								}*/
								if(e.field == 'isArchiveConfirm') {
									args = {'procreditMortgage.isArchiveConfirm': value,'procreditMortgage.id': id}
								}
								if(e.field == 'remarks') {
									args = {'procreditMortgage.remarks': value,'procreditMortgage.id': id}
								}
								if(e.field == 'isTransact') {//修改列为"是否办理"列
									args = {'procreditMortgage.isTransact': value,'procreditMortgage.id': id};
								}
								if(e.field == 'transactDate') {//修改列为"办理时间"列
									args = {'procreditMortgage.transactDate': value,'procreditMortgage.id': id};
								}
								if(e.field == 'isunchain'){
									args = {'procreditMortgage.isunchain': value,'procreditMortgage.id': id};
								}
								Ext.Ajax.request({
									url : __ctxPath+'/credit/mortgage/ajaxArchiveConfirm.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										//this.editorGridPanel.getStore().reload();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试');
									},
									params : args
								})
								
							
							}
						},
						 'cellclick' : function(grid,rowIndex,columnIndex,e){
								var reloadGrid=function(size){    
								     grid.getStore().reload();
								}
								if(12==columnIndex){
									 var markId=grid.getStore().getAt(rowIndex).get("id");
									 var talbeName="cs_procredit_mortgage.";
									 var mark=talbeName+markId;
									 if(isTransactVar == true){
									 	uploadfile("上传/下载抵质押物文件",mark,15,null,null,reloadGrid);
									 }else{
									 	uploadfile("下载抵质押物文件",mark,0,null,null,reloadGrid);
									 }
								}
								if(13==columnIndex){   
									 var markId=grid.getStore().getAt(rowIndex).get("id");
									 var talbeName="cs_procredit_mortgage.";
									 var mark=talbeName+markId;
								     picViewer(mark);
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
			//doRenderer
			doRenderer : function(v){
				return (v==''||v==null)?'<font color=gray>点击编辑</font>':v;
			},
			// 创建记录
			createMortgage : function() {
				//addMortgage(this.projectId,1,2,refreshMortgageGridStoresl,this.businessType);
				addMortgage(this.projectId,refreshMortgageGridStoresl,this.businessType,true);
			},
			// 编辑Rs
			
			editMortgage : function() {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
					var mortgageId = selected.get('id');
					var typeId = selected.get('typeid');
					var businessType = selected.get('businessType');
					if(typeId == 1){
							updateMortgageCar(mortgageId,refreshMortgageGridStoresl,businessType,true) ;
						}else if(typeId == 2){
							updateStockOwnerShip(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 3){
							updateCompany(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 4){
							updateDutyPerson(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 5){
							updateMachineInfo(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 6){
							updateProduct(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 7){
							updateHouse(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 8){
							updateOfficeBuilding(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 9){
							updateHouseGround(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 10){
							updateBusiness(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 11){
							updateBusinessAndLive(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 12){
							updateEducation(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 13){
							updateIndustry(mortgageId,refreshMortgageGridStoresl,businessType);
						}else if(typeId == 14){
							updateDroit(mortgageId,refreshMortgageGridStoresl,businessType);
						}
				}
			},
			// 按ID删除记录
			/*removeMortgage : function() {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
				var grid=this.editorGridPanel;
				if (null == selected) {
					Ext.MessageBox.alert('状态', '请选择记录!');
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
									Ext.Msg.alert('状态', '删除记录成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
									//refreshMortgageGridStoresl();
								},
								failure : function(result, action) {
									Ext.Msg.alert('状态','删除记录失败!');
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
					Ext.MessageBox.alert('状态', '请选择一条记录!');
				}else{
					var mortgageId = selected.get('id');
					var typeId = selected.get('typeid');
					if(typeId == 1){
							updateMortgageCar(mortgageId,refreshMortgageGridStoresl) ;
						}else if(typeId == 2){
							updateStockOwnerShip(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 3){
							updateCompany(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 4){
							updateDutyPerson(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 5){
							updateMachineInfo(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 6){
							updateProduct(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 7){
							updateHouse(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 8){
							updateOfficeBuilding(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 9){
							updateHouseGround(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 10){
							updateBusiness(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 11){
							updateBusinessAndLive(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 12){
							updateEducation(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 13){
							updateIndustry(mortgageId,refreshMortgageGridStoresl);
						}else if(typeId == 14){
							updateDroit(mortgageId,refreshMortgageGridStoresl);
						}
				}
			},*/
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
			uploadFiles_mortgage : function(){
				return "<img src='"+__ctxPath+"/images/upload-start.png'/>";	
			},
			seeThirdContract : function(businessType, piKey) {
				var selected = this.editorGridPanel.getSelectionModel().getSelected();
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
		
