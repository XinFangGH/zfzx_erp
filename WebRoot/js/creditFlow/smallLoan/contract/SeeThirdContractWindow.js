/**
 * @author
 * @createtime
 * @class OperateContractWindow
 * @extends Ext.Window
 * @description OperateContractWindow
 * @company
 */
SeeThirdContractWindow = Ext.extend(Ext.Window, {
	// 内嵌panelContract
	panelContract : null,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}else{
			this.title = '';
		};
		if (typeof(_cfg.categoryId) != "undefined") {
			this.categoryId = _cfg.categoryId;
		};
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
			if(this.businessType == 'SmallLoan'){
				this.HTLX = 'XEDQ';//合同类型（合同模板祖宗ID）,小贷合同
			}else if(this.businessType == 'Guarantee'){
				this.HTLX = 'DBDQ';//企业贷合同
			}
		};
		if (typeof(_cfg.piKey) != "undefined") {
			this.piKey = _cfg.piKey;
		};
		if (typeof(_cfg.contractId) != "undefined") {
			this.contractId = _cfg.contractId;
		};
		if (typeof(_cfg.thirdRalationId) != "undefined") {
			this.thirdRalationId = _cfg.thirdRalationId;
		};
		if (typeof(_cfg.temptId) != "undefined") {
			this.temptId = _cfg.temptId;
		};
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SeeThirdContractWindow.superclass.constructor.call(this, {
					//id :'SeeThirdContractWindow',
					layout : 'fit',
					iconCls : 'btn-edit',
					title : '查看<<font color=red>'+this.title+'</font>>合同详情',
					width : 550,
					height : 200,
					minWidth : 549,
					minHeight : 199,
					closable : true,
					items : this.panelContract,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		
		this.panelContract = new Ext.form.FormPanel({
			buttonAlign : 'center',
			frame : true,
			border : false,
			anchor : anchor,
			labelAlign : 'right',
			labelWidth : 70,
			autoScroll : true,
			// monitorValid : true,
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '查看合同',
				autoHeight : true,
				collapsible : false,
				anchor : anchor,
				items : [{
					columnWidth : 1,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
						name :'contractCategoryTypeText',
						xtype : 'textfield',
						fieldLabel : '合同类型',
						readOnly : true,
						cls : 'readOnlyClass'
					}, {
						name :'contractCategoryText',
						xtype : 'textfield',
						fieldLabel : '合同名称',
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				},{
					
					columnWidth : .5,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'button',
								text : '合同下载',
								scope : this,
								handler : function(){
									this.downloadContract(this.contractId)
								}
							},
							{
								text : '合同在线查看',
								xtype : 'button',
								scope : this,
								handler : function(){
									this.RunNtkOfficePanel(this.contractId)
								}
								
							}]
				
				},{
					columnWidth : .5,
					layout : 'form',
					defaults : {
						anchor : anchor
					},
					items : [{
								xtype : 'button',
								text : '附件下载',
								scope : this,
								handler : function(){
									this.DownFiles(this.id,this.piKey,'',this.contractId,'')
								}
								
							},{
								xtype : 'button',
								text : '查看要素',
								scope : this,
								handler : function(){
									this.lookElement('查看合同要素',this.businessType,this.temptId,this.piKey,this.contractId)
								}
							}]
				
				
				}]
			}]
		});
		this.panelContract.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/contract/seeContractCategoryProcreditContract.do',
			waitMsg : '正在载入数据...',
			scope : this,
			params : {
				categoryId : this.categoryId!=null?this.categoryId:0
			},
			success : function(form, action) {
				this.getCmpByName('contractCategoryTypeText').setValue(action.result.data.contractCategoryTypeText);
				this.getCmpByName('contractCategoryText').setValue(action.result.data.contractCategoryText);
			},
			failure : function(form, action) {
				//Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});

	},// end of the initUIComponents
	//查看要素
	lookElement :function(title,businessType,tempId,piKey,conId,cType){
		var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
			interval:200,
	    	increment:15
		});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});
		Ext.Ajax.request({
			url : __ctxPath+'/contract/validateExistDocumentTemplet.do',
			method : 'POST',
			success : function(response, request){
				var elementCodeStore = new Ext.data.JsonStore( {
					url : __ctxPath+'/contract/findElementProduceHelper.do',
					root : 'topics',
					fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}],
					listeners : {
						load : function(){
							elementWin.show();
							pbar.getDialog().close();
						}
					}
				});
				elementCodeStore.load({
					params : {documentId: tempId ,projId : piKey ,businessType : businessType,conId : conId, contractType : cType},
					callback :function(r,o,s){
						if(s == false){
							pbar.getDialog().close();
							Ext.ux.Toast.msg('状态','加载错误，请检查模板是否存在');
							return ;
						}
					}
				});
				var elementCodeModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer({header:'序'}),
					{
						header : "要素描述",
						width : 200,
						sortable : true,
						dataIndex : 'depict'
					},{
						header : "要素值",
						width : 170,
						sortable : true,
						dataIndex : 'value'
					}
				])
				var elementCodePanel = new Ext.grid.GridPanel( {
					store : elementCodeStore,
					autoWidth : true,
					loadMask : true ,
					stripeRows : true ,
					loadMask : myMask,
					height:440,
					colModel : elementCodeModel,
					autoExpandColumn : 2,
					listeners : {}
				});
				var elementWin = new Ext.Window({
					id : 'elementWin',
					layout : 'fit',
					title : title,
					width : 430,
					height : 400,
					minimizable : true,
					buttonAlign :'center',
					modal : true,
					items :[elementCodePanel],
					tbar : [new Ext.Button({text:'下载合同',tooltip : '下载生成的合同',
						scope : this,
						handler : function(){
							window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+conId,'_blank');
						}}
					)]
				});
			},
			failure : function(response, request) {
				Ext.ux.Toast.msg('状态', '加载错误');
			},
			params: { id : tempId}
		})
	},
	//下载合同
	downloadContract : function(conId){
		if(conId == null || conId == 'undefined' || conId == ''){
			Ext.ux.Toast.msg('提示','操作失败，请先生成合同！');
		}else{
			var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
				interval:200,
		    	increment:15
			});
			window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+conId,'_blank');
			pbar.getDialog().close();
		}
		
	},
	//下载附件
	DownFiles : function(id,piKey,contractName,contractId,mortgageId){
		if(contractId == null || contractId == 'undefined' || contractId == ''){
			Ext.ux.Toast.msg('提示','操作失败，请先生成合同！');
		}else{
			var mark = "cs_procredit_contract."+contractId;
			uploadfile('下载附件',mark,0,null,null,null);
		}
	},
	//在线查看合同
	RunNtkOfficePanel : function(contract_Id){
		if(contract_Id == null || contract_Id == 'undefined' || contract_Id == ''){
			Ext.ux.Toast.msg('提示','暂无相关合同提供查看！');
		}else{
			Ext.Ajax.request({
				url : __ctxPath+'/creditFlow/fileUploads/getFileAttachContractFileForm.do',
				method : 'GET',
				success : function(response, request) {
					var objfile = Ext.util.JSON.decode(response.responseText);
					if(objfile.success == true){
						var file_id =objfile.fileId;
						new OfficeTemplateView(file_id,null,true,null);
					}else{
						Ext.ux.Toast.msg('状态', '暂无相关合同提供查看！');
					}
					
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '暂无相关合同提供查看！');
				},
				params : {
					contractId :contract_Id
				}
			})
		}
		
	}
})
