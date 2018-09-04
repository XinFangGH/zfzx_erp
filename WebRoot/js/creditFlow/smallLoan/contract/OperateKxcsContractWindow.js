/**
 * @author
 * @createtime
 * @class OperateContractWindow
 * @extends Ext.Window
 * @description OperateContractWindow
 * @company
 */
OperateKxcsContractWindow = Ext.extend(Ext.Window, {
	isHidden:false,
	isWDLoan:false,
	cpanel:null,
    ZqType:null,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}
		if (typeof(_cfg.isWDLoan) != "undefined") {
			this.isWDLoan = _cfg.isWDLoan;
		}
		if (typeof(_cfg.cpanel) != "undefined") {
			this.cpanel = _cfg.cpanel;
		}
		if (typeof(_cfg.ZqType) != "undefined") {
			this.ZqType = _cfg.ZqType;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		OperateKxcsContractWindow.superclass.constructor.call(this, {
			id:'HKCSContractWin',
			layout : 'fit',
			width : 400,
			height :150,
			modal : true,
			autoScroll : true,
			title : '选择合同模板',
			buttonAlign : 'center',
			items:[this.panelContract],
			buttonAlign : 'center',
			buttons : [{
				iconCls : 'btn-save',
				text : this.isWDLoan?'保存':'合同生成',
				scope : this,
				handler:this.createContract
			},{
				iconCls : 'btn-save',
				text : '合同下载',
				scope : this,
				hidden:this.isHidden,
				handler:this.downloadContract
			}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		this.panelContract = new Ext.form.FormPanel({
			layout : 'column',
			items : [{
				columnWidth : 1,
				layout : 'form',
				border :false,
				labelWidth : 60,
				labelAlign : 'right',
				bodyStyle : 'margin-top:10px',
				items : [{
					xtype : "combo",
					anchor : "95%",
					hiddenName : "documenttempletId",
					displayField : 'itemName',
					editable:false,
					allowBlank:false,
					valueField : 'itemId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath+ '/contract/listByMarkDocumentTemplet.do?mark='+this.htType,
						fields : ['itemId', 'itemName']
					}),
					fieldLabel : "合同名称",
					blankText : "合同名称不能为空，请正确填写!"
				}]
			}]
		});
	},
	//生成合同
	createContract:function(){
		var documenttempletId=this.getCmpByName('documenttempletId').getValue();
		if(documenttempletId){
			//1.网贷->合同放款确认->借款端合同增加
			if(this.isHidden && this.isWDLoan){
				var businessType=this.businessType;
				var fundProjectId=this.fundProjectId;
				var htType=this.htType;
				var bidPlanId=this.bidPlanId;
				Ext.Ajax.request({
					url : __ctxPath+'/contract/saveContractContractHelper.do',
					method : 'POST',
					scope : this,
					params:{
						'projId':fundProjectId,
						'bidPlanId':bidPlanId,
						'businessType' : businessType,
						'templateId' : documenttempletId,
						'htType':htType
					},
					success : function(response, request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						if(obj.success){
							if (Ext.getCmp('HKCSContractWin').cpanel) {
								Ext.getCmp('HKCSContractWin').cpanel.getStore().reload();
							}
							Ext.getCmp('HKCSContractWin').close()
							Ext.ux.Toast.msg('状态', '合同生成成功！');
						}else{
							Ext.ux.Toast.msg('状态', '合同生成失败，可能未上传合同模板，请重试！');
						}
					}
				})
			}else if(this.isHidden && !this.isWDLoan){
				//2.网贷->合同放款确认->投资人合同生成
				var url=__ctxPath+'' +
					'\/' +
					'contract/createP2pContractContractHelper.do?type='+this.ZqType;
				var args={
					projId :this.projectId ,
					bidPlanId:this.bidPlanId,
					fundProjectId:this.fundProjectId,
					documenttempletId:documenttempletId
				}
				common(args,url);
			}else{
				var obj={
					'projId':this.fundProjectId,
					'businessType':this.businessType,
					'fundIntentId':this.fundIntentId,
					'templateId':documenttempletId,
					'htType':this.htType,
					'contractId':this.conId
				}
				cuishou(obj);
			}
		}else{
			Ext.ux.Toast.msg('提示','请先选择合同名称!');
			return;
		}
	},
	//下载合同
	downloadContract : function(){
		if(this.conId==0 || this.conUrl==''){
			Ext.ux.Toast.msg('提示','请先生成合同！');
		}else{
			var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
				interval:200,
		    	increment:15
			});
			window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+this.conId,'_blank');
			pbar.getDialog().close();
		}
	}
})

function cuishou(parms){
	var vDatas = '';
	var url=__ctxPath+'/contract/createContractContractHelper.do';
	Ext.Ajax.request({
		url : __ctxPath+'/contract/findElementContractHelper.do',
		method : 'POST',
		scope : this,
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
			
			if(obj.exsit==false){
				Ext.ux.Toast.msg('状态',obj.msg);
				return;
			}
			for(var i=0;i<obj.topics.length;i++){
				vDatas+=obj.topics[i].depict+"@"+obj.topics[i].value+"!"
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
			
			if(parms.contractId==0){
				Ext.Ajax.request({
					url : __ctxPath+'/contract/saveContractContractHelper.do',
					method : 'POST',
					scope : this,
					success : function(response, request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						
						var args={
							'projId':parms.projId,
							'businessType':parms.businessType,
							'templateId':parms.templateId,
							'contractId':obj.data.id,
							'vSysDatas':vDatas
						}
						parms.vSysDatas=vDatas;
						
						common(args,url);
					},
					params:{
						'projId':parms.fundIntentId,
						'businessType' : parms.businessType,
						'templateId' : parms.templateId,
						'htType':parms.htType
					}
				})
			}else{
				var args={
					'projId':parms.projId,
					'businessType':parms.businessType,
					'templateId':parms.templateId,
					'contractId':parms.contractId,
					'vSysDatas':parms.vDatas
				}
				common(args,url);
			}
		},
		params:{
			'documentId':parms.templateId,
			'projId':parms.projId,
			'businessType':parms.businessType
		}
	});
}

function common(args,url){
	var pbar = Ext.MessageBox.wait('合同生成中，可能需要较长时间，请耐心等待...','请等待',{
		interval:200,
		increment:15
	});
	
	Ext.Ajax.request({
		url : url,
		method : 'POST',
		timeout:1800000,
		scope : this,
		success : function(response, request) {
			pbar.getDialog().close();
			var obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success == true){
				if(obj.msg){
					Ext.ux.Toast.msg('状态',obj.msg);
				}else{
					Ext.ux.Toast.msg('状态', '合同生成成功！');
					if(Ext.getCmp('HKCSContractWin')){
//						Ext.getCmp('HKCSContractWin').conUrl=obj.data.url;
//						Ext.getCmp('HKCSContractWin').conId=obj.data.id;
					}
				}
				if(Ext.getCmp('HKCSContractWin').cpanel){
					Ext.getCmp('HKCSContractWin').cpanel.getStore().reload();
					Ext.getCmp('HKCSContractWin').destroy();
				}
			}else{
				Ext.ux.Toast.msg('状态', '合同生成失败，可能未上传合同模板，请重试！');
				pbar.getDialog().close();
			}
		},
		params : args
	})
}