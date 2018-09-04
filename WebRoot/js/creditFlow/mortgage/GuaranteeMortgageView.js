/**
 * @author:
 * @class MortgageView
 * @extends Ext.Panel
 * @description [MortgageView]对方抵质押物管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
/* var refreshMortgageGridStore = function(){
	Ext.getCmp('MortgageGridId').getStore().reload();
}*/
GuaranteeMortgageView = Ext.extend(Ext.Panel, {
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
			isdjHidden : true,
			isdjEdit : false,
			isblHidden: true,
			isblEdit : false,
			isgdHidden : true,
			isgdEdit :false,
			isHiddenSeeMortgageInfo:true,
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
	            	this.isgdEdit=_cfg.isgdEdit
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
				if (typeof(_cfg.isHiddenQSBtn) != "undefined") {
					this.isHiddenQSBtn = _cfg.isHiddenQSBtn;
				}else{
					this.isHiddenQSBtn = true
				
				}
				if (typeof(_cfg.isHiddenGDBtn) != "undefined") {
					this.isHiddenGDBtn = _cfg.isHiddenGDBtn;
				}else{
					this.isHiddenGDBtn = true
				
				}
				if (typeof(_cfg.isHiddenDelContractBtn) != "undefined") {
					this.isHiddenDelContractBtn = _cfg.isHiddenDelContractBtn;
				}
				if (typeof(_cfg.isHiddenEdiContractBtn) != "undefined") {
					this.isHiddenEdiContractBtn = _cfg.isHiddenEdiContractBtn;
				}
				if (typeof(_cfg.isReadOnly) != "undefined") {
					this.isReadOnly = _cfg.isReadOnly;
				}else{
					this.isReadOnly = true
				}
				if (typeof(_cfg.isdjHidden) != "undefined") {
					this.isdjHidden = _cfg.isdjHidden;
				}
				if (typeof(_cfg.isdjEdit) != "undefined") {
					this.isdjEdit = _cfg.isdjEdit;
				}
				if (typeof(_cfg.isblHidden) != "undefined") {
					this.isblHidden = _cfg.isblHidden;
				}
				if (typeof(_cfg.isblEdit) != "undefined") {
					this.isblEdit = _cfg.isblEdit;
				}
				if (typeof(_cfg.isgdHidden) != "undefined") {
					this.isgdHidden = _cfg.isgdHidden;
				}
				if (typeof(_cfg.isgdEdit) != "undefined") {
					this.isgdEdit = _cfg.isgdEdit;
				}
				if(typeof(_cfg.isRelieveEdit) != "undefinded") {
					this.isRelieveEdit = _cfg.isRelieveEdit;
				}else {
					this.isRelieveEdit = false;
				}
				if(typeof(_cfg.isKS) != "undefined") {//isKS为true时，为快速流程，快速流程中隐藏掉“签署合同”和“签署全部合同”按钮
					this.isKS = _cfg.isKS;
				}else{
					this.isKS = false;
				}
				if(typeof(_cfg.isHiddenSeeMortgageInfo) != "undefinded") {
					this.isHiddenSeeMortgageInfo = _cfg.isHiddenSeeMortgageInfo;
				}else {
					this.isHiddenSeeMortgageInfo = true;
				}
				if(typeof(_cfg.isFw) != "undefined") {//isFW为true时，为法务审核节点
					this.isFw = _cfg.isFw;
				}else{
					this.isFw = false;
				}
				Ext.applyIf(this, _cfg);
				var refresh;
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				GuaranteeMortgageView.superclass.constructor.call(this, {
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
				var projid = this.projectId;
				var busiType = this.businessType;
				var htType = 'thirdContract';
				var isTransactVar = this.isblEdit;
				this.datafield=new Ext.form.DateField( {
					format : 'Y-m-d',
					allowBlank : false
				});
				
				this.expanderFlow = new Ext.ux.grid.RowExpander({
					tpl : new Ext.Template('<table>{contractContent}</table>'),
		//			tpl : new Ext.Template(
		//			 '<tpl if="{iafeLevel} >2">','<p><b>意见与说明:</b>','12321','<l>'
		//			)  
					lazyRender : false, 
					enableCaching : false,
					hidden : this.isSeeContractHidden
					/*getRowClass : function(record, rowIndex, p, ds){
				        p.cols = p.cols-1;
				        var content = this.bodyContent[record.id];
				        if(!content ){//&& !this.lazyRender){
				            content = this.getBodyContent(record, rowIndex);
				        }
				        if(content){
				            p.body = content;
				        }
				        //alert(record.id+"|"+content);
				        return this.state[record.id] ? 'x-grid3-row-expanded' : 'x-grid3-row-expanded';
				    }*/
					
				});
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '增加抵质押物',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenAddBtn,
										handler : this.createMortgage
									}, {
										iconCls : 'btn-del',
										text : '删除抵质押物',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenDelBtn,
										handler : this.removeMortgage
									}, {
										iconCls : 'btn-update',
										text : '编辑抵质押物',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenEdiBtn,
										handler : this.editMortgage
									},{
										iconCls : 'btn-flow-design',
										text : '办理抵质押物手续',
										xtype : 'button',
										scope:this,
										hidden : !this.isblEdit,
										handler : function(){
											this.banliMortgage()
										}
									},{
										iconCls : 'deleteIcon',
										text : '解除抵质押物',
										xtype : 'button',
										scope:this,
										hidden : !this.isRelieveEdit,
										handler : function(){
											this.relieveMortgage()
										}
									},{
										iconCls : 'btn-readdocument',
										text : '查看抵质押物详情',
										xtype : 'button',
										scope : this,
										handler : this.seeMortgageInfo
									},new Ext.Toolbar.Separator({
									}),{
										iconCls : 'btn-add',
										text : '增加合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenEdiContractBtn,
										handler : function(){
											this.addThirdContract(this.businessType, this.projectId)
										}
									}, {
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
															Ext.ux.Toast.msg('状态', '此抵质押物暂无相关的合同提供删除!');
															//jStore_contractCategroy.remove(row);
														} else {
						
															SlContractView.deleteFun(
																			__ctxPath
																					+ '/contract/deleteRecordByMortgageIdProcreditContract.do',
																			{
																				mortgageId : row.data.id,
																				businessType :busiType,
																				projId :projid,
																				contractType :htType
																			}, function() {
																				//Ext.ux.Toast.msg('状态', '此反担保物相关的合同删除成功!');
																				storethird.reload();
																			});
														}
													}
						
												}
											})
						
										}
									},{
										iconCls : 'btn-edit',
										text : '编辑合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenEdiContractBtn,
										handler : function(){
											this.operateThirdContract(this.businessType, this.projectId)
										}
									},{
										iconCls : 'btn-edit',
										text : this.isFw == true?'审核全部合同':'编辑全部合同',
										xtype : 'button',
										scope:this,
										hidden : !this.isfwEdit,
										handler : function(){
											this.batchBJThirdContract(this.businessType, this.projectId)
										}
									},{
										iconCls : 'btn-edit',
										text : '签署合同',
										xtype : 'button',
										scope:this,
										hidden : this.isKS == true? true:!this.isqsEdit,
										handler : function(){
											this.qsThirdContract(this.businessType, this.projectId)
										}
									},{
										iconCls : 'btn-edit',
										text : '批量签署合同',
										xtype : 'button',
										scope:this,
										hidden : this.isKS == true? true:!this.isqsEdit,
										handler : function(){
											this.batchQSThirdContract(this.businessType, this.projectId)
										}
									},{
										iconCls : 'btn-edit',
										text : '归档合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenGDBtn,
										handler : function(){
											this.gdThirdContract(this.businessType, this.projectId)
										}
									},new Ext.Toolbar.Separator({
										hidden : this.isHiddenGDBtn
									}),{
										iconCls : 'btn-edit',
										text : '归档全部合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenGDBtn,
										handler : function(){
											this.batchGDThirdContract(this.businessType, this.projectId)
										}
									},new Ext.Toolbar.Separator({
										hidden : this.isSeeContractHidden||this.isHiddenGDBtn
									}),{
										iconCls : 'btn-readdocument',
										text : '查看合同详情',
										xtype : 'button',
										scope:this,
										hidden : this.isSeeContractHidden,
										handler : function(){
											this.seeThirdContract(this.businessType, this.projectId,this.isSignHidden,this.isgdHidden)
										}
									}]
						});
				var checkColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
				});
				var relieveCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isHiddenRelieve,
					editable : this.isRelieveEdit,
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
					header : "是否办理",
					width : 65,
					dataIndex : 'isTransact',
					hidden : this.isblHidden,
					editable : this.isblEdit
				});
				this.gridPanel = new HT.EditorGridPanel({
					//hiddenCm : this.isHidden,
					scope : this,
					plugins : [this.expanderFlow,relieveCheckColumn,checkColumn,contractCheckColumn,issignContractcheckColumn,transactCheckColumn],
					border : false,
					clicksToEdit : 1,
					//region : 'center',
					tbar : this.topbar,
					isShowTbar : this.isHidden? false : true,
					showPaging : false,
					autoHeight : true,
					//plugins : mortgageDatas,
					// 使用RowActions
					rowActions : false,
					//id : 'MortgageGridId',
					url : __ctxPath +'/credit/mortgage/ajaxGetMortgageAllDataByProjectId.do?projectId='+this.projectId+'&businessType='+this.businessType+'&isReadOnly='+this.isReadOnly,
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
					           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId','temptId','issign','signDate','fileCount','isTransact','transactDate','fileCounts','contractContent','contractCount','enregisterDepartment'],
					columns : [this.expanderFlow, {
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},{
						header : "抵质押物名称",
						width : 170,
						dataIndex : 'mortgagename'
					}, {
						header : "抵质押物类型",
						width : 120,
						sortable : true,
						hidden : this.isHiddenInArchiveConfirm,
						dataIndex : 'mortgagepersontypeforvalue'
					}, {
						header : "所有权人",
						width : 130,
						sortable : true,
						hidden : this.isHiddenInArchiveConfirm,
						dataIndex : 'assureofnameEnterOrPerson'					
					}, {
						header : "最终估价",
						width : 85,
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
						width : 80,
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
						header : '合同个数',	
						width : 150,
						hidden : this.isSeeContractHidden,
						dataIndex : 'contractCount'
					}
					,
					{
						header : "抵质押物登记号",
						width : 120,
						dataIndex : 'pledgenumber',
						hidden : this.isdjHidden,
						editable :this.isdjEdit,
						editor :{
							xtype :'textfield'
						},
						renderer : this.doRenderer
					}, {
						header : "抵质押物登记机关",
						width : 120,
						dataIndex : 'enregisterDepartment',
						hidden : this.isdjHidden,
						editable :this.isdjEdit,
						editor :{
							xtype :'textfield'
						},
						renderer : this.doRenderer
					},
					transactCheckColumn
					,relieveCheckColumn/*,{
						header : "办理时间",
						width : 80,
						hidden : this.isblHidden,
						format : 'Y-m-d',
						dataIndex : 'transactDate',
						editable : this.isblEdit,
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor :this.datafield
					},{
						header : '办理文件',
						dataIndex : 'fileCounts',
						width : 70,
						hidden : this.isblHidden,
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
						hidden : this.isblHidden,
						dataIndex : 'fileCounts',
						renderer : function(v){
							if(v==null||v=='null'||v==0){
								return '';
							}else{
								return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
							}
						}
					}*/,checkColumn, {
						header : '归档备注',
						dataIndex : 'remarks',
						align : "center",
						width : 116,
						hidden : this.isgdHidden,
						editable : this.isgdEdit,
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
								var businessType=e.record.data['businessType'];
								if (e.originalValue != e.value) {//编辑行数据发生改变
									if(e.field == 'isunchain'){//是否解除
										Ext.Ajax.request({
												url : __ctxPath+'/credit/mortgage/updateMortgage.do',
												method : 'POST',
												scope : this,
												success : function(response, request) {
													//this.gridPanel.getStore().reload();
													e.record.commit();
												},
												failure : function(response) {
													Ext.ux.Toast.msg('状态', '操作失败，请重试');
												},
												params: {
													'procreditMortgage.isunchain': e.value,
													mortgageid: e.record.data['id']
												}
											})
										return false;
									}
									if(e.field == 'isArchiveConfirm') {
										args = {'procreditMortgage.isArchiveConfirm': value,'procreditMortgage.id': id}
									}
									if(e.field == 'remarks') {
										args = {'procreditMortgage.remarks': value,'procreditMortgage.id': id}
									}
									if(e.field == 'pledgenumber') {//修改列为"反担保物登记号"列
										args = {'procreditMortgage.pledgenumber': value,'procreditMortgage.id': id};
									}
									if(e.field == 'enregisterDepartment') {//修改列为"反担保物登记机关"列
										args = {'procreditMortgage.enregisterDepartment': value,'procreditMortgage.id': id};
									}
									if(e.field == 'isTransact'){//是否办理
										args={
											'procreditMortgage.isTransact': e.value,
											'procreditMortgage.id': id,
											'procreditMortgage.businessType': businessType
										}
									}
									if(e.field == 'transactDate') {//修改列为"办理时间"列
										args= {
											'procreditMortgage.transactDate': e.value,
											'procreditMortgage.id': id,
											'procreditMortgage.businessType': businessType
										}
									}
									Ext.Ajax.request({
										url : __ctxPath+'/credit/mortgage/ajaxArchiveConfirm.do',
										method : 'POST',
										scope : this,
										success : function(response, request) {
											//this.gridPanel.getStore().reload();
											e.record.commit();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试');
										},
										params : args
									})
								}
							},
							'cellclick' : function(grid,rowIndex,columnIndex,e){
/*									var reloadGrid=function(size){    
									     grid.getStore().reload();
									}
									if(17==columnIndex){
										 var markId=grid.getStore().getAt(rowIndex).get("id");
										 var talbeName="cs_procredit_mortgage.";
										 var mark=talbeName+markId;
										 if(isTransactVar == true){
										 	uploadfile("上传/下载抵质押物文件",mark,15,null,null,reloadGrid);
										 }else{
										 	uploadfile("下载抵质押物文件",mark,0,null,null,reloadGrid);
										 }
									}
									if(18==columnIndex){   
										 var markId=grid.getStore().getAt(rowIndex).get("id");
										 var talbeName="cs_procredit_mortgage.";
										 var mark=talbeName+markId;
									     picViewer(mark);
									}*/
							},
							rowdblclick : function(grid, rowIndex, e) {
								//this.seeMortgageInfo();
							}
						} 
				});
				//单个合同的重新生成
				var cpanel = this.gridPanel;
				reMakeContract = function(businessType, piKey,mortgagename,id,contractId,categoryId,temptId) {
					var thisPanel = cpanel;
					var window = new OperateContractWindow({
								title : mortgagename,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :id,
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
				};
				//单个合同的下载
				downloadContract = function(contractId){
					window.open(__ctxPath + "/contract/lookUploadContractProduceHelper.do?conId="
					+ contractId, '_blank');
				};
				//单个合同的删除
				deleteContract = function(categoryId){
					var thisPanel = cpanel;
					Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
						if (btn == "yes") {
							Ext.Ajax.request({
								url : __ctxPath+ '/contract/deleteContractCategoryRecordProcreditContract.do',
								method : 'POST',
								success : function(response, request) {
									obj = Ext.util.JSON.decode(response.responseText);
									if (obj.data) {
										Ext.ux.Toast.msg('状态', '删除成功！');
										thisPanel.getStore().reload();
									} else {
										Ext.ux.Toast.msg('状态', '删除失败！');
									}
				
								},
								params : {
									categoryId : categoryId
								}
							});	
						}
					})
					
				};
				// 下载附件
				downloadFiles = function(contractId,count,piKey,businessType) {
					var thisPanel = cpanel;
					var reloadStore = function(){
						thisPanel.getStore().reload();
					}
					if(contractId == null || contractId == 'undefined' || contractId == ''){
						Ext.ux.Toast.msg('提示','操作失败，请先生成合同！');
					}else{
						var mark = "cs_procredit_contract."+contractId;
						uploadfileContract('上传/下载附件',mark,count,null,null,contractId,reloadStore,piKey,businessType);
					}
				}
				upLoadContractFiles = function(id,piKey,contractName,contractId,mortgageId,piKey,businessType){
					if(contractId == null || contractId == 'undefined' || contractId == ''){
						Ext.ux.Toast.msg('提示','操作失败，请先生成合同！');
					}else{
						var mark = "cs_procredit_contract."+contractId;
						uploadfileContract('上传附件',mark,15,null,null,contractId,null,piKey,businessType);
					}
				}
			},// end of the initComponents()
			doRenderer : function(v){
				return (v==''||v==null)?'<font color=gray>点击编辑</font>':v;
			},
			opponentMortgageAffrimRenderer : function(v){
				return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
			},
			
			batchQSThirdContract : function(businessType,piKey){
				var thisPanel = this.gridPanel;
				var isfwEdit = this.isfwEdit;
				var isqsEdit = this.isqsEdit;
				var window = new BatchSignThirdContractWindow({
						businessType : businessType,
						piKey : piKey,
						htType :'thirdContract',
						thisPanel : thisPanel,
						isfwEdit:isfwEdit,
						isHidden:false,
						isqsEdit : isqsEdit
					});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});			
			},
			batchBJThirdContract : function(businessType,piKey){
				var thisPanel = this.gridPanel;
				var isfwEdit = this.isfwEdit;
				var isqsEdit = this.isqsEdit;
				var window = new BatchSignThirdContractWindow({
						businessType : businessType,
						piKey : piKey,
						htType :'thirdContract',
						thisPanel : thisPanel,
						isgdHidden : this.isgdHidden,
						isgdEdit: true,
						isfwEdit:isfwEdit,
						isqsEdit : isqsEdit,
						isHidden : this.isHidden,
						isqsHidden:true,
						isFw : this.isFw
					});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});			
			},
			batchGDThirdContract : function(businessType,piKey){
				var thisPanel = this.gridPanel;
				var isfwEdit = this.isfwEdit;
				var isqsEdit = this.isqsEdit;
				var window = new BatchSignThirdContractWindow({
						businessType : businessType,
						piKey : piKey,
						htType :'thirdContract',
						thisPanel : thisPanel,
						isgdHidden : this.isgdHidden,
						isgdEdit: this.isgdEdit,
						isfwEdit:isfwEdit,
						isqsEdit : false,
						isHidden : this.isHidden,
						isFw : this.isFw
					});
					window.show();
					window.addListener({
								'close' : function() {
									window.grid_contractPanel.stopEditing();
									thisPanel.getStore().reload();
								}
							});			
			},
			// 创建记录
			createMortgage : function() {
				var thisPanel = this.gridPanel;
				var refreshMortgageGridStore = function(){thisPanel.getStore().reload()};
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
				var thisPanel = this.gridPanel;
				var refreshMortgageGridStore = function(){thisPanel.getStore().reload()};
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
			addThirdContract : function(businessType, piKey) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var thisPanel = this.gridPanel;
				var rows = this.gridPanel.getSelectionModel().getSelections();
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
					var window = new OperateContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								/*contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,*/
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
			qsThirdContract : function(businessType, piKey) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var rows = this.gridPanel.getSelectionModel().getSelections();
				var thisPanel = this.gridPanel;
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
								isfwEdit : false,
								isHiddenAddBtn : true,
								isHiddenDelBtn : true,
								isHiddenEdiBtn : true,
								isqsHidden : false,
								isqsEdit : true
							});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});
				}
			},
			gdThirdContract : function(businessType, piKey) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var rows = this.gridPanel.getSelectionModel().getSelections();
				var thisPanel = this.gridPanel;
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
								isfwEdit : false,
								isHiddenAddBtn : true,
								isHiddenDelBtn : true,
								isHiddenEdiBtn : true,
								isqsHidden : false,
								isgdHidden :false,
								isgdEdit : true
							});
					window.show();
					window.addListener({
								'close' : function() {
									window.grid_contractPanel.stopEditing();
									thisPanel.getStore().reload();
								}
							});
				}
			},
			operateThirdContract : function(businessType, piKey) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var thisPanel = this.gridPanel;
				var isQSHidden = this.isHiddenQSBtn;
				var isqsEditVar = this.isqsEdit;
				var rows = this.gridPanel.getSelectionModel().getSelections();
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
								isHidden : this.isHidden,
								isfwEdit : true,
								isHiddenAddBtn : false,
								isHiddenDelBtn : false,
								isHiddenEdiBtn : false,
								isgdHidden : this.isgdHidden,
								isgdEdit: this.isgdEdit,
								isqsHidden :isQSHidden,
								isqsEdit :isqsEditVar
							});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});
				}
			},
			seeThirdContract : function(businessType, piKey,isSignHidden,isgdHidden) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var thisPanel = this.gridPanel;
				var rows = this.gridPanel.getSelectionModel().getSelections();
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
								isHidden : this.isHidden,
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
			},
	banliMortgage : function(){
	
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var mortgageId = selected.get('id');
			var businessType=selected.get('businessType');
			new BanliMortgageWindow({
			    mortgageId:mortgageId,
			    businessType:businessType,
			    gridPanel:grid
		    }).show()

		}
	
	},
	relieveMortgage:function(){
	var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var mortgageId = selected.get('id');
			 var businessType=selected.get('businessType');
		   
		    new RelieveMortgageWindow({
			    mortgageId:mortgageId,
			    businessType:businessType,
			    gridPanel:grid
		    }).show()
		}
	}
			
			/*seeThirdContract : function(businessType, piKey) {
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
			}*/
		});
		
