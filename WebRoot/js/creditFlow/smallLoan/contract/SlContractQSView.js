Ext.ns('SlContractQSView');
/**
 * @author:chencc
 * @class SlContractQSView
 * @extends Ext.Panel
 * @description [SlContractQSView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
//SlContractQSView.jStore_contractCategroyQS;
SlContractQSView = Ext.extend(Ext.Panel, {
	//projId : 0,
	// 构造函数
	PERSON_FINANCE_CONTRACT : 10,//个人贷款合同
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined")
		{
		      this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined")
		{
		      this.businessType=_cfg.businessType;
		}
		if(typeof(_cfg.isqsEdit)!="undefined")
		{
		      this.isqsEdit=_cfg.isqsEdit;
		}else{
			this.isqsEdit = false
		}
		if(typeof(_cfg.isfwEdit)!="undefined")
		{
		      this.isfwEdit=_cfg.isfwEdit;
		}else{
			this.isfwEdit = false
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlContractQSView.superclass.constructor.call(this, {
					//id : 'SlContractQSView',
//					title : '合同签署',
					region : 'center',
					layout : 'anchor',
					items : [this.grid_contractQSPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

				
		//var rows = 0;
		//var cols = 0;
		//var srows = 0;
		//var selectids = Array();
		//var htlxid = 0;//合同类型id
		//var htlxname = null;
		//var xedkid =90;//小额贷款id
		//var GRDKHT ="91";//个人贷款合同
//		var projId = "cba3279a-9b90-43bc-afc2-f013b88dd37e";//假设的项目id
//		var piKey = "1"//假设的项目id
		var projId = this.projId;
		var piKey = this.projId;
		var businessType = this.businessType
		
		this.datafield=new Ext.form.DateField( {
			format : 'Y-m-d',
			allowBlank : false
		})
		var checkColumn = new Ext.grid.CheckColumn({
			header : '是否签署并检验合格',
			editable : this.isqsEdit,
			width : 130,
			dataIndex : 'issign'
		});
		var contractCheckColumn =new Ext.grid.CheckColumn({
			header : "是否法务确认",
			width : 90,
			dataIndex : 'isLegalCheck',
			editable : this.isfwEdit
		});
		this.render_contractInfo = Ext.data.Record.create([{name : 'id'},{name : 'parentId'}, {name : 'contractCategoryTypeText'}, {name : 'number'}, {name : 'projId'}, {name : 'mortgageId'}, {name : 'isOld'}, {name : 'remark'},{name : 'isUpload'},{name : 'issign'},{name : 'signDate'},{name : 'isAgreeLetter'},{name : 'isSeal'},{name : 'contractCategoryText'},{name : 'contractId'},{name : 'contractName'},{name : 'contractNumber'},{name : 'text'},{name :'draftName'},{name :'draftDate'},{name : 'localParentId'},{name : 'templateId'},{name : 'isLegalCheck'},{name : 'verifyExplain'},{name :'orderNum'},{name :'fileCount'}]);
		this.jStore_contractCategroyQS = new Ext.data.GroupingStore({
			proxy : new Ext.data.HttpProxy({url : __ctxPath+ '/contract/getContractTreeProcreditContract.do'}) ,
            reader: new Ext.data.JsonReader({
            	fields : this.render_contractInfo,
            	totalProperty : 'totalProperty',
            	root : 'topics'
            }),
            groupField:'contractCategoryTypeText'
        });
		this.jStore_contractCategroyQS.load({
			params : {
				projId : projId,
				businessType:businessType
			}
		});

		this.grid_contractQSPanel = new HT.EditorGridPanel({
			//id : 'grid_contractQSPanel',
			store : this.jStore_contractCategroyQS,
			autoExpandColumn : 'changContent',
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			// anchor : fullanchor,
			viewConfig : {
				forceFit : true
			},
			plugins : [checkColumn,contractCheckColumn],
			bbar : false,
			isShowTbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			listeners : {
				afteredit : function(e) {
					if (e.originalValue != e.value) {
						if(e.field =='issign'){
							Ext.Ajax.request({
								url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
								method : 'POST',
								success : function(response, request) {
									e.record.commit();
	//								Ext.getCmp('grid_contractQSPanel').getStore().reload();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
								},
								params: {
									'procreditContract.issign':  e.value,
									'procreditContract.id': e.record.data['contractId'],
									projId : projId,
									businessType :businessType
								}
							})
						};
						if(e.field = 'signDate'){
							Ext.Ajax.request({
								url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
								method : 'POST',
								success : function(response, request) {
									//SlContractQSView.jStore_contractCategroyQS.reload();
									e.record.commit();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
								},
								params: {
									'procreditContract.signDate': e.value,
									'procreditContract.id': e.record.data['contractId'],
									projId : projId,
									businessType :businessType
								}
							})
						}
					}
				}
			},
						
			columns : [/*new Ext.grid.RowNumberer({header:'序号'}),*/
			{
				header : '合同类型',
				width : 150,
				dataIndex : 'contractCategoryTypeText'
			},{
				header : '合同名称',
				width : 250,
				dataIndex : 'contractCategoryText'
			}, {
						header : '合同下载',
						sortable : true,
						width : 70,
						renderer : function(val, m, r) {
							
							//if(r.get('number') ==GRDKHT){
//								return '<a style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractQSView.downloadContract(\''+r.get('templateId')+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+this.PERSON_FINANCE_CONTRACT+'\')" >下载</a>';//个人贷款合同
								//return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.gif" onclick="downloadContract(\''+r.get('templateId')+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ENTRUST_GUARANTEE+'\')" />';//委托担保合同
								if(r.get('contractId')==''||r.get('contractId')==0){
									return '';
								}else{
//									return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractQSView.lookContractByHTML('+r.get('contractId')+')" >下载</a>';//个人贷款合同
									return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractQSView.downloadContract(\''+r.get('contractId')+'\')" >下载</a>';//个人贷款合同
								}
							//}
							
						}
					}, {
						header : '附件下载',
						dataIndex : 'fileCount',
						width : 70,
						sortable : true,
						renderer : function(val,m,r){
							return  '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractQSView.DownFiles(\''+r.get('id')+'\',\''+piKey+'\',\''+r.get('contractCategoryText')+'\',\''+r.get('contractId')+'\',\''+r.get('mortgageId')+'\')">'+r.get('fileCount')+'</a>';
						}
					},contractCheckColumn
					,checkColumn
					/*{	
						header : '是否签署',
						sortable : true,
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
						dataIndex : 'issign',
						renderer : this.contractQSRenderer
						
					}*//*,{	
						header : '是否签署',
						dataIndex : 'issign',
						sortable : true,
						renderer : function(v){
							return  v==true?'是':'否';
						},
						editor : new Ext.form.Checkbox({
							listeners : {
								change : function(ck,isChecked){
										var s = Ext.getCmp("grid_contractQSPanel").getSelectionModel().getSelected();
										Ext.Ajax.request({
											url : __ctxPath+'/credit/document/updateProcreditContractById.do',
											method : 'POST',
											success : function(response, request) {
												jStore_contractCategroy.load({
													params : {
														projId : projId
													}
												});
											},
											failure : function(response) {
												Ext.Msg.alert('状态', '操作失败，请重试');
											},
											params: {
												'procreditContract.issign': isChecked,
												'procreditContract.id': s.data.contractId,
												projId : projId
											}
										})
								}
							}
						})
						
					}*/,{
						header : '签署时间',
						//xtype : 'datecolumn',
//						xtype : 'datefield',
						format : 'Y-m-d',
						width : 77,
						sortable : true,
						editable : this.isqsEdit,
						dataIndex : 'signDate',
						renderer : ZW.ux.dateRenderer(this.datafield),
						editor : new Ext.form.DateField( {
							format : 'Y-m-d',
							allowBlank : false,
							blankText : '签署日期不可为空!'/*,
							listeners :{
								scope : this,
								select : function(d){
									var signdate = d.value;
									
									var s = this.grid_contractQSPanel.getSelectionModel().getSelected();
									
								}
							}*/
						})

					}]
		});

		

	},// end of the initComponents()
	contractQSRenderer : function(v){
		if(v==''||v==null){
			return '<font color=red>否</font>' ;
		}else if(v==true){
			return '<font color=green>是</font>';
		}else if(v==false){
			return '<font color=red>否</font>';
		}else{
			return v;
		}
	}
	

	
});
	
SlContractQSView.lookContractByHTML = function(id){
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	Ext.Ajax.request({
		url : __ctxPath+'/contract/ajaxIsExistValidaProcreditContract.do',
		method : 'POST',
		success : function(response, request){
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.exsit == false){
				pbar.getDialog().close();
				Ext.ux.Toast.msg('状态',obj.msg);
			}else{
				pbar.getDialog().close();
//				window.location.href=__ctxPath+"/credit/document/lookUploadContractCall.do?conId="+id
				window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+id,'_blank')

			}
		},
		failure : function(response, request) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试！');
		},
		params: {typeId : id}
	})
};
	SlContractQSView.DownFiles = function(id,piKey,contractName,contractId,mortgageId){
		var mark = "cs_procredit_contract."+contractId;
		uploadfile('下载附件',mark,0,null,null,null);
	};
	
	/*SlContractQSView.getFilesSize = function(id){
		var mark = "cs_procredit_contract."+id;
		Ext.Ajax.request({
			url : __ctxPath+'/credit/document/getFilesSize.do',
			method : 'POST',
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				if(obj.exsit == false){
					alert(obj.data)
					return obj.data
				}
				SlContractQSView.jStore_contractCategroyQS.reload();
//				SlContractQSView.jStore_contractCategroyQS.load({
//					params : {
//						projId : projId
//					}
//				});
			},
			failure : function(response) {
				Ext.Msg.alert('状态', '操作失败，请重试');
			},
			params: {
				mark :mark
			}
		})
	};*/
SlContractQSView.downloadContract = function(conId){
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
//	window.location.href = __ctxPath+"/credit/document/lookUploadContractCall.do?conId="+conId;
	window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+conId,'_blank');
	pbar.getDialog().close();

};
/*SlContractQSView.downloadContract = function(tempId,piKey,conId, cType){
	//window.location.href = "downloadContractCall.action?tempId="+tempId+"&piKey="+piKey+"&conId="+conId+"&cType="+cType;
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	Ext.Ajax.request({
		url : __ctxPath+'/credit/document/validationFileIsExist.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.exsit == false){
				pbar.getDialog().close();
				Ext.Msg.alert('状态',obj.msg);
			}else{
				pbar.getDialog().close();
				window.location.href = __ctxPath+"/credit/document/downloadContractCall.do?tempId="+tempId+"&piKey="+piKey+"&conId="+conId+"&cType="+cType;
			}
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params: { documentId: tempId}
	})
}*/