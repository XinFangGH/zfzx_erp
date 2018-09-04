/**
 * @author:
 * @class FinanceMortgageView
 * @extends Ext.Panel
 * @description [MortgageView]对方抵质押物管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
/* var fl_refreshMortgageGridStore = function(){
	Ext.getCmp('FinanceMortgageGridId').getStore().reload();
}*/
FinanceBaozMortgageView = Ext.extend(Ext.Panel, {
			// 构造函数
			gridPanel:null,
			isHidden:false,
			isHiddenRelieve :true,
			isHiddenInArchiveConfirm : false,
			isHiddenAddBtn : true,
			isHiddenDelBtn : true,
			isHiddenEdiBtn : true,
			isHiddenAddContractBtn : true,
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
	            	this.isgdEdit=_cfg.isgdEdit
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
				if (typeof(_cfg.isHiddenAddContractBtn) != "undefined") {
					this.isHiddenAddContractBtn = _cfg.isHiddenAddContractBtn;
				}
				if (typeof(_cfg.isHiddenSignContractBtn) != "undefined") {
					this.isHiddenSignContractBtn = _cfg.isHiddenSignContractBtn;
				}
				if (typeof(_cfg.isReadOnly) != "undefined") {
					this.isReadOnly = _cfg.isReadOnly;
				}else{
					this.isReadOnly = true
				}
				if(typeof(_cfg.isRelieveEdit) != "undefinded") {
					this.isRelieveEdit = _cfg.isRelieveEdit;
				}else {
					this.isRelieveEdit = false;
				}
				Ext.applyIf(this, _cfg);
				var refresh;
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FinanceBaozMortgageView.superclass.constructor.call(this, {
							layout:'anchor',
		  					anchor : '100%',
							//title : '抵质押物管理',
							//region : 'center',
							//layout : 'border',
							items : [
								{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>'},
								this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var projid = this.projectId;
				var busiType = this.businessType;
				var htType = 'thirdContract';
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
					enableCaching : false
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
				var checkColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					editable : this.isgdEdit,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					width : 74
				});
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '增加保证担保',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenAddBtn,
										handler : this.createMortgage
									},/*{
										iconCls : 'btn-edit',
										text : '编辑抵质押物',
										xtype : 'button',
										scope : this,
										handler : this.editRs
									},*/{
										iconCls : 'btn-del',
										text : '删除保证担保',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenDelBtn,
										handler : this.removeMortgage
									}, {
										iconCls : 'btn-update',
										text : '编辑保证担保',
										xtype : 'button',
										scope : this,
										hidden : this.isHiddenEdiBtn,
										handler : this.editMortgage
									}, {
										iconCls : 'btn-flow-design',
										text : '办理抵质押物',
										xtype : 'button',
										scope : this,
										hidden : !this.isblEdit,
										handler : this.banliMortgage
									}, {
										iconCls : 'deleteIcon',
										text : '解除抵质押物',
										xtype : 'button',
										scope:this,
										hidden : !this.isRelieveEdit,
										handler : function(){
											this.relieveMortgage()
										}
									}, {
										iconCls : 'btn-readdocument',
										text : '查看保证担保详情',
										xtype : 'button',
										scope : this,
										handler : this.seeMortgageInfo
									},new Ext.Toolbar.Separator({
									}),{
										iconCls : 'btn-add',
										text : '增加合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenAddContractBtn,
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
									}, {
										iconCls : 'btn-edit',
										text : '编辑合同',
										xtype : 'button',
										scope:this,
										hidden : this.isHiddenEdiContractBtn,
										handler : function(){
											this.operateThirdContract(this.businessType, this.projectId)
										}
									}, {
										iconCls : 'btn-edit',
										text : '编辑全部合同',
										xtype : 'button',
										scope:this,
										hidden : !this.isfwEdit,
										handler : function(){
											this.batchBJThirdContract(this.businessType, this.projectId)
										}
									}, {
										iconCls : 'btn-edit',
										text : '签署合同',
										xtype : 'button',
										scope:this,
										hidden : !this.isqsEdit,
										handler : function(){
											this.qsThirdContract(this.businessType, this.projectId)
										}
									}, {
										iconCls : 'btn-edit',
										text : '批量签署合同',
										xtype : 'button',
										scope:this,
										hidden : !this.isqsEdit,
										handler : function(){
											this.batchQSThirdContract(this.businessType, this.projectId)
										}
									}, {
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
					header : "是否办理手续",
					width : 95,
					dataIndex : 'isTransact',
					hidden : this.isHiddenTransact,
					editable : !this.isHiddenTransact
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
					//id : 'FinanceMortgageGridId',
					url : __ctxPath +'/credit/mortgage/getMortgageOfBZ.do?projectId='+this.projectId+'&businessType='+this.businessType+'&isReadOnly=false',
					fields : [{
								name : 'id',
								type : 'int'
							}, 'mortgageId','mortgagename','mortgagepersontypeforvalue','assureofname', 
					           'assuremoney','finalprice','remarks','assuretypeidValue','personTypeValue',
					           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
					           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
					           'statusidValue','enregisterperson','unchainofperson','enregisterdate','isunchain',
					           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
					          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
					           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','businessType',
					           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId',
					           'temptId','issign','signDate','fileCount','isTransact','transactDate','fileCounts','contractContent','contractCount','isPledged','unchainremark'],
					columns : [this.expanderFlow, {
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '保证人',
						dataIndex : 'assureofnameEnterOrPerson'
					}, {
						header : '保证人类型',
						dataIndex : 'personTypeValue'
					}, {
						header : '保证类型',
						dataIndex : 'assuremodeidValue'
					}, {
						header : '与债务人关系',
						dataIndex : 'relation'
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
						//hidden : !this.isAfterContract,
						dataIndex : 'contractCount'
					}
					,
					//contractCheckColumn,
					transactCheckColumn
					/*issignContractcheckColumn,
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
					}*/,relieveCheckColumn/*,{
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
					}*/,checkColumn, {
						header : '归档备注',
						dataIndex : 'unchainremark',
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
								var mfinancingId = e.record.data['id'];
								if (e.originalValue != e.value) {//编辑行数据发生改变
									var args;
									if(e.field == 'isArchiveConfirm') {
										args = {'slMortgageFinancing.isArchiveConfirm': value,'slMortgageFinancing.mfinancingId': mfinancingId};
									}
									if(e.field == 'unchainremark') {
										args = {'slMortgageFinancing.unchainremark': value,'slMortgageFinancing.mfinancingId': mfinancingId};
									}
									if(e.field == 'isunchain'){//是否解除
										args = {'slMortgageFinancing.isunchain':value,'slMortgageFinancing.mfinancingId': mfinancingId};
										
									}
									if(e.field == 'isTransact'){
										args = {'slMortgageFinancing.isTransact':value,'slMortgageFinancing.mfinancingId': mfinancingId};
									}
									if(e.field == 'transactDate'){
										args = {'slMortgageFinancing.transactDate':value,'slMortgageFinancing.mfinancingId': mfinancingId};
									}
									Ext.Ajax.request({
										url : __ctxPath+'/creditFlow/ourmortgage/updateSlMortgageFinancing.do',
										method : 'POST',
										scope : this,
										success : function(response, request) {
											e.record.commit();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试');
										},
										params: args
									})
								}
							},
							'cellclick' : function(grid,rowIndex,columnIndex,e){/*
									var isTransactVar = true;
									var isHiddenSave = false;
									var reloadGrid=function(size){    
									     grid.getStore().reload();
									}
									if(16==columnIndex){
										 var markId=grid.getStore().getAt(rowIndex).get("id");
										 var talbeName="cs_procredit_mortgage.";
										 var mark=talbeName+markId;
										 if(isTransactVar == true){
										 	uploadfile("上传/下载抵质押物文件",mark,15,null,null,reloadGrid);
										 }else{
										 	uploadfile("下载抵质押物文件",mark,0,null,null,reloadGrid);
										 }
									}
									if(17==columnIndex){   
										 var markId=grid.getStore().getAt(rowIndex).get("id");
										 var talbeName="cs_procredit_mortgage.";
										 var mark=talbeName+markId;
									     picViewer(mark,isHiddenSave);
									}
							*/},
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
					/*var mark = "cs_procredit_contract." + contractId;
					uploadfile('上传/下载附件', mark, count, null, null, reloadStore);*/
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
				/*this.gridPanel.getSelectionModel().on('selectionchange',
					function(sm) {
						Ext.getCmp('mortgagecontractremoveBtn')
								.setDisabled(sm.getCount() < 1 ? 1 : 0);
					});*/
			},// end of the initComponents()
			opponentMortgageAffrimRenderer : function(v){
				return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
			},
			batchQSThirdContract : function(businessType,piKey){
				var thisPanel = this.gridPanel;
				var window = new BatchSignThirdContractWindow({
						businessType : businessType,
						piKey : piKey,
						htType :'baozContract',
						thisPanel : thisPanel
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
						htType :'baozContract',
						thisPanel : thisPanel,
						isfwEdit: true,
						isqsEdit : false
					});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});			
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
			// 创建记录
			createMortgage : function() {
				new FinanceBaozMortgageListView({
					businessType : this.businessType,
					projectId : this.projectId,
					financeGrid : this.gridPanel
				}).show();
				/*var thisPanel = this.gridPanel;
				var fl_refreshMortgageGridStore = function(){thisPanel.getStore().reload()};
				//addMortgage(this.projectId,1,2,fl_refreshMortgageGridStore,this.businessType);
				addMortgage(this.projectId,fl_refreshMortgageGridStore,this.businessType,false);*/
			},
			// 按ID删除记录
			removeMortgage : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择记录!');
				}else{
					var mfinancingIds = "";
					var rows = this.gridPanel.getSelectionModel().getSelections();
					for(var i=0;i<rows.length;i++){
						mfinancingIds = mfinancingIds+rows[i].get('id');
						if(i!=rows.length-1){
							mfinancingIds = mfinancingIds+',';
						}
					}
					Ext.MessageBox.confirm('确认删除', '确认移除选中的项目抵质押物? ', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								//url : __ctxPath +'/credit/mortgage/deleteMortgage.do',
								url : __ctxPath + '/creditFlow/ourmortgage/multiDelSlMortgageFinancing.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('操作信息', '删除失败!');
								},
								params: { 
									mfinancingIds: mfinancingIds
								}
							});
						}
					});
				}
			},
			// 编辑Rs
			
			editMortgage : function() {
				var thisPanel = this.gridPanel;
				var fl_refreshMortgageGridStore = function(){thisPanel.getStore().reload()};
				var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
					var mortgageId = selected.get('mortgageId');
					var typeId = selected.get('personTypeId');
					var businessType = selected.get('businessType');
					new UpdateBaozMortgageWin({id:mortgageId,projectId:0,businessType:this.businessType,gridPanel:this.gridPanel,type:typeId}).show()
					/*if(typeId == 1){
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
						}*/
				}
			},
			
			// 编辑Rs
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
	},
			//查看详情
			seeMortgageInfo : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
				var mortgageId = selected.get('mortgageId');
				var typeId = selected.get('typeid');
				var businessType = selected.get('businessType');
				var mfinancingId = selected.get('id');
					if(typeId==1){
						seeCarInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==2){
						seeStockownershipInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==3){
						seeCompanyInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==4){
						seeDutyPersonInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==5){
						seeMachineInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==6){
						seeProductInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==7){
						seeHouseInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==8){
						seeOfficeBuildingInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==9){
						seeHouseGroundInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==10){
						seeBusinessInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==11){
						seeBusinessAndLiveInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==12){
						seeEducationInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==13){
						seeIndustryInfo(mortgageId,businessType,mfinancingId);
					}else if(typeId==14){
						seeDroitUpdateInfo(mortgageId,businessType,mfinancingId);
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
								htType :'baozContract',
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
			operateThirdContract : function(businessType, piKey) {
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
								htType :'baozContract',
								thisPanel : thisPanel,
								isHidden : false,
								isfwEdit : true,
								isqsHidden :false,
								isqsEdit : true,
								isHiddenAddBtn : false,
								isHiddenDelBtn : false,
								isHiddenEdiBtn : false
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
				var thisPanel = this.gridPanel;
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
								htType :'baozContract',
								thisPanel : thisPanel
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
			var mortgageId = selected.get('mortgageId');
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
			var mortgageId = selected.get('mortgageId');
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
		
