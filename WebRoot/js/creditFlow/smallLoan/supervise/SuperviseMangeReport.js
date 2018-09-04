Ext.ns('LoanSettlementDocument');
/**
 * @author:chencc
 * @class LetterAndBookView
 * @extends Ext.Panel
 * @description [LetterAndBookView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SuperviseMangeReport = Ext.extend(Ext.Panel, {
	// 构造函数
	categoryId :0,
	contractId :0,
	isHiddenPanel : false,
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined") {
	        this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined") {
	        this.businessType=_cfg.businessType;
		}
		if (typeof(_cfg.LBTemplate) !="undefined") {
		    this.LBTemplate = _cfg.LBTemplate;
		    if(this.businessType == 'Guarantee'&&this.LBTemplate=='superviseMangeReport'){//业务类别，企业贷
				this.LBTemplate="superviseMangeReport";//模板唯一标识
				this.titleText='监管报告'
		    }
	    }
	    
	    if(typeof(_cfg.isHidden)!="undefined") {
	        this.isHidden=_cfg.isHidden;
		}else{
			this.isHidden = true
		}
		if(_cfg.isHiddenPanel) {
			this.isHiddenPanel=_cfg.isHiddenPanel;
		}
		if(typeof(_cfg.isRecordHidden)!="undefined") {
	        this.isRecordHidden=_cfg.isRecordHidden;
		}else{
			this.isRecordHidden = true
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SuperviseMangeReport.superclass.constructor.call(this, {
					hidden : this.isHiddenPanel,
					region : 'center',
					layout : 'anchor',
				    title:false,
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var titlet = this.titleText;
		var busType = this.businessType;
		var projId = this.projId;
		var LBTemplate = this.LBTemplate;
		
		RunNtkOfficePanelFor = function(contract_Id,bln){
			if(contract_Id == null || contract_Id == 'undefined' || contract_Id == ''){
				Ext.ux.Toast.msg('提示','操作失败，请先生成合同！');
			}else{
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/fileUploads/getFileAttachContractFileForm.do',
					method : 'GET',
					success : function(response, request) {
						var objfile = Ext.util.JSON.decode(response.responseText);
						if(objfile.success == true){
							var file_id =objfile.fileId;
							if(objfile.ext=="pdf"){
							   new PdfTemplateView(file_id,objfile.filePath,bln,null)
							}else{
								new OfficeTemplateView(file_id,null,bln,null);
							}
							
							
						}else{
							Ext.ux.Toast.msg('状态', '合同未生成或上传成功，请先生成或上传合同！');
						}
						
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '合同未上传，请先上传合同');
					},
					params : {
						contractId :contract_Id
					}
				})
			}
			
		};
		//下载
		downloadContract = function(conId){
			if(conId == null || conId == 'undefined' || conId == ''){
				Ext.ux.Toast.msg('提示','操作失败，未找到相关文件！');
			}else{
				var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
					interval:200,
			    	increment:15
				});
				window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+conId,'_blank');
				pbar.getDialog().close();
			}
			
		};
		this.renderInfo = Ext.data.Record.create([
			{
					name : 'id'
				}, {
					name : 'parentId'
				}, {
					name : 'contractCategoryTypeText'
				}, {
					name : 'number'
				}, {
					name : 'projId'
				}, {
					name : 'mortgageId'
				}, {
					name : 'isOld'
				}, {
					name : 'remark'
				}, {
					name : 'isUpload'
				}, {
					name : 'issign'
				}, {
					name : 'isAgreeLetter'
				}, {
					name : 'isSeal'
				}, {
					name : 'contractCategoryText'
				}, {
					name : 'contractId'
				}, {
					name : 'contractName'
				}, {
					name : 'contractNumber'
				}, {
					name : 'text'
				}, {
					name : 'draftName'
				}, {
					name : 'draftDate'
				}, {
					name : 'localParentId'
				}, {
					name : 'templateId'
				}, {
					name : 'isLegalCheck'
				}, {
					name : 'verifyExplain'
				}, {
					name : 'orderNum'
				}, {
					name : 'fileCount'
				}, {
					name : 'temptId'
				},{name : 'issign'}
				,{name : 'signDate'}
				,{name : 'url'}
				,{name : 'isRecord'}
				,{name : 'recordRemark'}
		]);
		this.jStore = new Ext.data.JsonStore({
			url : __ctxPath+ '/contract/getLetterAndBookTreeProcreditContract.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.renderInfo
            
        });
		this.jStore.load({
			params : {
				htType : this.LBTemplate,
				projId : this.projId,
				businessType : this.businessType
			}
		});
		this.topBar = new Ext.Toolbar({
			items : [
				{
					text : '生成',
					iconCls : 'sldownIcon',
					scope:this,
					handler : function(){
						var ccpanel = this.gridPanel;
						this.createLetterAndBook(this.categoryId,this.contractId,this.titleText,this.projId,this.businessType,this.LBTemplate,ccpanel)
					}
				},'-',{
					text : '上传',
					iconCls : 'slupIcon',
					scope:this,
					handler : function(){
					   this.uploadLoanContractWin(null,null,null,null,busType,titlet,projId)
					}
				}
			]
		});
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			autoWidth : true,
			store : this.jStore,
			autoExpandColumn : 'contractCategoryText',
			autoScroll : true,
			tbar : this.isHidden?null : this.topBar,
			isShowTbar : this.isHidden? false : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			showPaging : false,
			loadMask : true,
			autoHeight : true,
			hiddenCm : true,
			columns : [{
				header : '',
				dataIndex : 'id',
				hidden :true,
				scope : this,
				renderer :function(val, m, r){
					this.categoryId = r.get('id');
					this.contractId = r.get('contractId');
				}
			},{
				header : '文件类型',
				width : 85,
				fixed : true,
				menuDisabled:true,
				dataIndex : 'contractCategoryTypeText'
			},{
				header : '文件名称(已上传)',
				width : 150,
				menuDisabled:true,
				dataIndex : 'contractCategoryText'
			}, {
				header : '上传时间',
				menuDisabled:true,
				width : 72,
				fixed : true,
				dataIndex : 'draftDate'
			}, {
				header : '下载',
				width : 33,
				fixed : true,
				dataIndex : 'contractId',
				menuDisabled:true,
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="下载'+titlet+'" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downloadContract('+r.get('contractId')+')" >下载</a>';
				}
			}/*, {
				header : '在线编辑',
				width : 60,
				fixed : true,
				dataIndex : 'url',
				menuDisabled:true,
				hidden :this.isHidden,
				scope :this,
				renderer : function(val, m, r) {
					
					return '<a title="在线编辑'+titlet+'" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="RunNtkOfficePanelFor('+r.get('contractId')+','+false+')" >编辑</a>';
				}
			}, {
				header : '在线查看',
				dataIndex : 'id',
				width : 60,
				fixed : true,
				menuDisabled:true,
				scope :this,
				renderer : function(val, m, r) {
					
					return '<a title="在线查看'+titlet+'" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="RunNtkOfficePanelFor('+r.get('contractId')+','+true+')" >查看</a>';
				}
			}*/,{
				header : '重新上传',
				width : 60,
				fixed : true,
				dataIndex : 'id',
				fixed : true,
				menuDisabled:true,
				hidden :this.isHidden,
				scope : this,
				renderer : function(val, m, r) {				
					//return '<a title="重新上传'+titlet+'" style ="TEXT-DECORATION:underline;cursor:pointer" onclick ="uploadContractWin('+r.get('id')+','+r.get('contractId')+',null,'+r.get('temptId')+',\''+busType+'\',\''+titlet+'\','+projId+','+xcPanel+')">重新上传</a>';
					return '<a title="重新上传'+titlet+'" style ="TEXT-DECORATION:underline;cursor:pointer">重新上传</a>';
					
				}
			}, {
				header : '删除',
				width : 35,
				fixed : true,
				dataIndex : 'id',
				menuDisabled:true,
				hidden :this.isHidden,
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="删除'+titlet+'" style ="TEXT-DECORATION:underline;cursor:pointer">删除</a>';
				}
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['contractId'];
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(e.field =='isLegalCheck'){
							args = {
									'procreditContract.isLegalCheck' : value,
									'procreditContract.id' : id
								}
						}else if(e.field =='issign'){
							args = {
									'procreditContract.issign':  value,
									'procreditContract.id': id
								}
						}else if(e.field == 'signDate'){
							args = {
								'procreditContract.signDate': value,
								'procreditContract.id': id
							}
						}else if(e.field == 'isRecord') {
							args = {'procreditContract.isRecord': value,'procreditContract.id': id};
						}else if(e.field == 'recordRemark') {
							args = {'procreditContract.recordRemark': value,'procreditContract.id': id};
						}
						Ext.Ajax.request({
							url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
							method : 'POST',
							scope :this,
							success : function(response, request) {
								e.record.commit();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试！');
							},
							params: args
						})
					}
				},
				'cellclick' : function(grid,row,col,e){
					if(col==8){
					   this.uploadContractWin(grid.getStore().getAt(row).get('id'),grid.getStore().getAt(row).get('contractId'),null,grid.getStore().getAt(row).get('temptId'),busType,titlet,projId)
					}
					if(col==9){
					   this.deleteContractLetter(projId,busType,LBTemplate)
					}
				}
			}
		});

	},// end of the initComponents()
	
	createLetterAndBook : function(categoryId,contractId,titleText,projId,businessType,LBTemplate,cpanel){
		var pbar = Ext.MessageBox.wait(titleText+'生成中...','请等待',{
				interval:200,
		    	increment:15
			});
		Ext.Ajax.request({
			url : __ctxPath+'/contract/createAssureIntentBookProduceHelper.do',
			method : 'GET',
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success==true){
					Ext.ux.Toast.msg('状态', '生成'+titleText+'成功！');
					cpanel.getStore().reload();
					pbar.getDialog().close();
				}else{
					Ext.ux.Toast.msg('状态', '未上传'+titleText+'模板！');
					pbar.getDialog().close();
				}
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试！');
				pbar.getDialog().close();
			},
			params : {
				projId : projId,
				businessType : businessType,
				mark : LBTemplate,
				htType : LBTemplate,
				categoryId :categoryId ==null?0:categoryId,
				contractId :contractId
			}
		})
	},
	uploadLoanContractWin :function(id,contractId,fun,tId,businessType,title,projId){
		
		var thisPanel =this.gridPanel;
		/*if(id == null || id == 'undefined' || id == ''){
			Ext.ux.Toast.msg('提示','操作失败，请先生成！');
		}else{*/
			new Ext.Window({
			id : 'uploadContractWin',
			title : '上传'+title,
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true ,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items:[new Ext.form.FormPanel({
				id : 'uploadContractFrom',
				url : __ctxPath+'/credit/document/uploadLoanSettlementDocumentCall.do',
				monitorValid : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				enctype : 'multipart/form-data',
				fileUpload: true, 
				layout : 'column',
				frame : true ,
				items : [{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 150,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : title+'文件',
						allowBlank : false,
						blankText : '文件不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
	    				inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'categoryId',
						value : id
					},{
						xtype : 'hidden',
						name: 'conId',
						value : contractId
					},{
						id :'fileUploadContentType',
						xtype : 'hidden',
						name: 'fileUploadContentType'
					},{
						xtype : 'hidden',
						name: 'templateId',
						value: tId
					},{
						xtype : 'hidden',
						name: 'thirdRalationId',
						value: 0
					},{
						xtype : 'hidden',
						name: 'isApply',
						value: false
					},{
						xtype : 'hidden',
						name: 'htlxdName',
						value: title
					},{
						xtype : 'hidden',
						name: 'projId',
						value: projId
					},{
						xtype : 'hidden',
						name: 'businessType',
						value: businessType
					},{
					    xtype : 'hidden',
					    name : 'mark',
					    value :this.LBTemplate
					},{
						xtype : 'hidden',
						name : 'htType',
						value :this.LBTemplate
					}]
				}],
				buttons : [{
					text : '上传',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
						var str=Ext.getCmp("fileUpload").getValue();
						str=str.substring(str.lastIndexOf("\\")+1,str.length)
						Ext.getCmp('uploadContractFrom').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							params:{htmcdName:str},
							success : function(form ,action) {
								Ext.ux.Toast.msg('提示','上传'+title+'成功！')
								Ext.getCmp('uploadContractWin').destroy()
								thisPanel.getStore().reload();
								
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示','上传失败！');		
							}
						});
					}
				}]
			})]
		}).show();
		//}
		
	},
	uploadContractWin :function(id,contractId,fun,tId,businessType,title,projId){
		
		var thisPanel =this.gridPanel;
		if(id == null || id == 'undefined' || id == ''){
			Ext.ux.Toast.msg('提示','操作失败，请先生成！');
		}else{
			new Ext.Window({
			id : 'uploadContractWin',
			title : '上传'+title,
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true ,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items:[new Ext.form.FormPanel({
				id : 'uploadContractFrom',
				url : __ctxPath+'/contract/uploadContractOfOtherProduceHelper.do',
				monitorValid : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				enctype : 'multipart/form-data',
				fileUpload: true, 
				layout : 'column',
				frame : true ,
				items : [{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 150,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : title+'文件',
						allowBlank : false,
						blankText : '文件不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
	    				inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'categoryId',
						value : id
					},{
						xtype : 'hidden',
						name: 'conId',
						value : contractId
					},{
						id :'fileUploadContentType',
						xtype : 'hidden',
						name: 'fileUploadContentType'
					},{
						xtype : 'hidden',
						name: 'templateId',
						value: tId
					},{
						xtype : 'hidden',
						name: 'thirdRalationId',
						value: 0
					},{
						xtype : 'hidden',
						name: 'isApply',
						value: false
					},{
						xtype : 'hidden',
						name: 'htlxdName',
						value: title
					},{
						xtype : 'hidden',
						name: 'projId',
						value: projId
					},{
						xtype : 'hidden',
						name: 'businessType',
						value: businessType
					}]
				}],
				buttons : [{
					text : '上传',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
						var str=Ext.getCmp("fileUpload").getValue();
						str=str.substring(str.lastIndexOf("\\")+1,str.length)
						Ext.getCmp('uploadContractFrom').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							params:{htmcdName:str},
							success : function(form ,action) {
								Ext.ux.Toast.msg('提示','上传'+title+'成功！')
								Ext.getCmp('uploadContractWin').destroy()
								thisPanel.getStore().reload();
								
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示','上传失败！');		
							}
						});
					}
				}]
			})]
		}).show();
		}
		
	},
	deleteContractLetter :function(projId, businessType,htType){
			var thisPanel =this.gridPanel;
			var pbar = Ext.MessageBox.wait('数据删除中','请等待',{
						interval:200,
				    	increment:15
					});
			Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
				if (btn == "yes") {
					Ext.Ajax.request({
						url : __ctxPath+ '/contract/deleteRecordForProcreditContract.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							if (obj.success == true) {
								thisPanel.ownerCt.categoryId =0;//重置categoryId,这个很重要，防止数据库表产生多条数据
								thisPanel.ownerCt.contractId =0;//重置contractId
								thisPanel.getStore().reload();
								//thisPanel.getView().refresh();
								pbar.getDialog().close();
								Ext.ux.Toast.msg('状态', '删除成功！');
							} else {
								pbar.getDialog().close();
								Ext.ux.Toast.msg('状态', '删除失败！');
							}
		
						},
						params : {
							projId :projId,
							businessType :businessType,
							htType :htType
							
						}
					});	
				}
			})
			
		}
})