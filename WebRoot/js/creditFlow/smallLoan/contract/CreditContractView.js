/**
 * @author:lisl
 * @class CreditContractView
 * @extends Ext.Panel
 * @description 贷款合同
 * @company 智维软件
 * @createtime:
 */
 
CreditContractView = Ext.extend(Ext.Panel, {
	// 构造函数
	//PERSON_FINANCE_CONTRACT : 10,//个人贷款合同
	isHidden:false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof (_cfg.projectId) != "undefined") {
			this.projId = _cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined")
		{
		     this.businessType=_cfg.businessType;
		}
		if (_cfg.isHidden) {
			this.isHidden = _cfg.isHidden;
	    }
	    if(typeof(_cfg.isgdEdit)!="undefined")
		{
		     this.isgdEdit=_cfg.isgdEdit;
		}else{
			this.isgdEdit = false
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CreditContractView.superclass.constructor.call(this, {
//					title : '合同确认',
					region : 'center',
					layout : 'anchor',
					items : [this.editorGridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var PERSON_FINANCE_CONTRACT = 10;//个人贷款合同
		var GRDKHT ="91";//个人贷款合同
		var piKey = this.projId;
		var businessType = this.businessType
		var field = Ext.data.Record.create([{name : 'id'},{name : 'parentId'}, {name : 'contractCategoryTypeText'}, {name : 'number'}, {name : 'projId'}, {name : 'mortgageId'}, {name : 'isOld'}, {name : 'remark'},{name : 'isUpload'},{name : 'issign'},{name : 'signDate'},{name : 'isAgreeLetter'},{name : 'isSeal'},{name : 'contractCategoryText'},{name : 'contractId'},{name : 'contractName'},{name : 'contractNumber'},{name : 'text'},{name :'draftName'},{name :'draftDate'},{name : 'localParentId'},{name : 'templateId'},{name : 'isLegalCheck'},{name : 'verifyExplain'},{name :'orderNum'},{name : 'isRecord'},{name : 'recordRemark'},{name :'fileCount'},{name : 'isLegalCheck'}]);
		var jStore = new Ext.data.JsonStore({
			url : __ctxPath+ "/contract/getContractTreeProcreditContract.do",
			root : 'topics',
			fields : field
		});
		jStore.load({
			params : {
				projId : this.projId,
				businessType : this.businessType
			}
		});
		var checkColumn = new Ext.grid.CheckColumn({
			hidden : this.isHiddenAffrim,
			header : '是否归档',
			editable :this.isgdEdit,
			dataIndex : 'isRecord',
			width : 70
		});
		var qscheckColumn = new Ext.grid.CheckColumn({
			header : '是否签署并检验合格',
			editable : false,
			width : 130,
			dataIndex : 'issign'
		});
		var fwCheckColumn =new Ext.grid.CheckColumn({
			header : "是否法务确认",
			width : 90,
			dataIndex : 'isLegalCheck',
			editable : false
		});
		this.editorGridPanel = new HT.EditorGridPanel({
			plugins : [checkColumn,qscheckColumn,fwCheckColumn],
			hiddenCm :  true,
			border : false,
			isShowTbar:false,
			name : 'editorGridPanel',
			scope : this,
			store : jStore,
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
			columns : [
				{
					dataIndex : 'contractId',
					hidden : true
				}, {
					header : '合同类别',
					width : 85,
					sortable : true,
					dataIndex : 'contractCategoryTypeText'
				}, {
					header : '合同名称',
					sortable : true,
					dataIndex : 'contractCategoryText'
				}, {
					header : '合同下载',
					width : 70,
					sortable : true,
					renderer : function(val, m, r) {
						if(r.get('contractId')==''||r.get('contractId')==0){
							return '';
						}else{
							return '<a style ="TEXT-DECORATION:underline;cursor:pointer" onclick="CreditContractView.downloadContract(\''+r.get('contractId')+'\')" >下载</a>';//个人贷款合同
						}
					}
				}, {
					header : '附件下载',
					dataIndex : 'fileCount',
					width : 70,
					sortable : true,
					renderer : function(val,m,r){
						return  '<a style ="TEXT-DECORATION:underline;cursor:pointer" onclick="CreditContractView.DownFiles(\''+r.get('id')+'\',\''+this.projId+'\',\''+r.get('contractCategoryText')+'\',\''+r.get('contractId')+'\',\''+r.get('mortgageId')+'\')">'+r.get('fileCount')+'</a>';
					}
				},fwCheckColumn,qscheckColumn,{
					header : '签署时间',
					dataIndex : 'signDate',
					width : 77,
					sortable : true
				},checkColumn, {
					header : '归档备注',
					hidden : this.isHiddenAffrim,
					align : "center",
					dataIndex : 'recordRemark',
					editable :this.isgdEdit,
					/*renderer : function(v){
						if(v == "" || v == null) {
							return "<font color=red>点击编辑备注</font>";
						}else{
							return v;
						}
					},*/
					editor : new Ext.form.TextField({
						selectOnFocus: true
					})
				}
			],         
			listeners : {
				scope : this,
				//EditorGridPanel数据发生改变时请求后台方法修改数据并重新加载显示数据
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['contractId'];
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(e.field == 'isRecord') {
							args = {'procreditContract.isRecord': value,'procreditContract.id': id};
						}
						if(e.field == 'recordRemark') {
							args = {'procreditContract.recordRemark': value,'procreditContract.id': id};
						}
						Ext.Ajax.request({
							url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								e.record.commit();
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				}
				
			} 
		});
	},// end of the initComponents()
	contractAffrimRenderer : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
	}
});

CreditContractView.DownFiles = function(id,piKey,contractName,contractId,mortgageId){
	var mark = "cs_procredit_contract."+contractId;
	uploadfile('下载附件',mark,0,null,null,null);
};
CreditContractView.lookContractByHTML = function(id){
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
				Ext.Msg.alert('状态',obj.msg);
			}else{
				pbar.getDialog().close();
				window.location.href=__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+id
			}
		},
		failure : function(response, request) {
			Ext.Msg.alert('状态','操作失败');
		},
		params: {typeId : id}
	})
};
CreditContractView.downloadContract = function(conId){
	//window.location.href = "downloadContractCall.action?tempId="+tempId+"&piKey="+piKey+"&conId="+conId+"&cType="+cType;
	var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
		interval:200,
    	increment:15
	});
	window.open(__ctxPath + "/contract/lookUploadContractProduceHelper.do?conId="
					+ conId, '_blank');
	//window.location.href = __ctxPath+"/credit/document/lookUploadContractCall.do?conId="+conId;
	pbar.getDialog().close();

};
//查看要素
CreditContractView.lookElement = function(title,tempId,piKey,conId,cType){
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
				params : {documentId: tempId ,projId : piKey ,conId : conId, contractType : cType},
				callback :function(r,o,s){
					if(s == false){
						pbar.getDialog().close();
						Ext.Msg.alert('状态','加载错误，请检查模板是否存在');
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
				tbar : [new Ext.Button({text:'下载查看合同',tooltip : '下载并预览合同文本',
					scope : this,
					handler : function(){
						CreditContractView.downloadContract(conId);
					}}
				)]
			});
		},
		failure : function(response, request) {
			Ext.Msg.alert('状态','加载错误');
		},
		params: { id : tempId}
	})
}



