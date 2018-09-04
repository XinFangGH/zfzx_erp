Ext.ns('opponentMortgageCheckView');
/**
 * @author:
 * @class opponentMortgageCheckView
 * @extends Ext.Panel
 * @description 反担保措施登记
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
opponentMortgageCheckView = Ext.extend(Ext.Panel, {
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
				opponentMortgageCheckView.superclass.constructor.call(this, {
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
				this.topbar = new Ext.Toolbar({
					items : [/*{
						iconCls : 'btn-update',
						text : '编辑',
						xtype : 'button',
						scope : this,
						handler : this.editMortgage
					},*/{
						iconCls : 'btn-readdocument',
						text : '查看详情',
						xtype : 'button',
						scope : this,
						handler : this.seeMortgageInfo
					}/*,{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						handler : this.removeMortgage
					}*/]
				});
				this.editorGridPanel = new HT.EditorGridPanel({
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
					           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
					          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
					           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename',
					           'isArchiveConfirm','remarks','isTransact','transactDate','enregisterDepartment',
					           'businessType','fileCounts'],
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
					}, {
						header : "反担保物类型",
						width : 90,
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
						renderer : function(v){
							if(v==''||v=='null'||v==null){
								return '';
							}else{
								return v+'万元';
							}
						}					
					}, {
						header : "反担保物登记号",
						width : 120,
						//sortable : true,
						dataIndex : 'pledgenumber',
						editor :{
							xtype :'textfield'
						},
						renderer : this.doRenderer
					}, {
						header : "反担保物登记机关",
						width : 120,
						//sortable : true,
						dataIndex : 'enregisterDepartment',
						editor :{
							xtype :'textfield'
						},
						renderer : this.doRenderer
					}, {
						header : "是否办理",
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
					}, {
						header : "办理日期",
						width : 90,
						//xtype : 'datecolumn',
						format : 'Y-m-d',
						//sortable : true,
						dataIndex : 'transactDate',
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor :this.datafield
					},{
						header : '份数',
						dataIndex : 'fileCounts',
						width : 70,
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '';							
							}else{
								return '<font color=red>'+v+'</font>份';
							}
						}
					},{
						header : '上传或下载',
						width : 80,
						renderer:this.uploadFiles_mortgage
					},{
						header : '预览',
						width : 70,
						dataIndex : 'fileCounts',
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '';
							}else{
								return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
							}
						}
						//renderer:this.imageView_mortgage(v)
					}],// end of columns
					listeners : {
						scope : this,
						//EditorGridPanel数据发生改变时请求后台方法修改数据并重新加载显示数据
						afteredit : function(e) {
							var args;
							var value = e.value;
							var id = e.record.data['id'];
							if (e.originalValue != e.value) {//编辑行数据发生改变
								if(e.field == 'pledgenumber') {//修改列为"反担保物登记号"列
									args = {'procreditMortgage.pledgenumber': value,'procreditMortgage.id': id};
								}
								if(e.field == 'enregisterDepartment') {//修改列为"反担保物登记机关"列
									args = {'procreditMortgage.enregisterDepartment': value,'procreditMortgage.id': id};
								}
								if(e.field == 'isTransact') {//修改列为"是否办理"列
									args = {'procreditMortgage.isTransact': value,'procreditMortgage.id': id};
								}
								if(e.field == 'transactDate') {//修改列为"办理时间"列
									args = {'procreditMortgage.transactDate': value,'procreditMortgage.id': id};
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
								if(14==columnIndex){
									 var markId=grid.getStore().getAt(rowIndex).get("id");
									 var talbeName="cs_procredit_mortgage.";
									 var mark=talbeName+markId;
									 uploadfile("上传抵质押物文件",mark,15,null,null,reloadGrid);
								}
								if(15==columnIndex){   
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
									//refreshMortgageGridStore();
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
		
