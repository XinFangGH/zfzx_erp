ContractMakeWay1 = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.temptId) != "undefined") {
			this.temptId = _cfg.temptId;
		}
		if (typeof(_cfg.piKey) != "undefined") {
			this.piKey = _cfg.piKey;
		}
		if (typeof(_cfg.contractId) != "undefined") {
			this.contractId = _cfg.contractId;
		}
		if (typeof(_cfg.contractType) != "undefined") {
			this.contractType = _cfg.contractType;
		}
		if (typeof(_cfg.categoryId) != "undefined") {
			this.categoryId = _cfg.categoryId;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ContractMakeWay1.superclass.constructor.call(this, {
			id:'ContractMakeWay1',
			layout : 'anchor',
			closable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			autoScroll :true
		});
	},
	initUIComponents : function() {
		var tempId=this.temptId;
		var piKey=this.piKey;
		var businessType=this.businessType;
		var conId=this.contractId;
		var cType=this.contractType;
		var flag=false;
		var categoryId=this.categoryId;
		var bidPlanId = this.bidPlanId;
		var clauseId = this.clauseId;
		if(tempId == null){
			Ext.ux.Toast.msg('状态','请先选择相应的模板！');
		}else{
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
				timeout: 600000,
				success : function(response, request){
					var elementCodeStore = new Ext.data.JsonStore( {
						url : __ctxPath+'/contract/findElementContractHelper.do',
						timeout: 600000,
						root : 'topics',
						fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'},{name:'elementType'}],
						listeners : {
							load : function(){
								pbar.getDialog().close();
							}
						}
					});
					elementCodeStore.load({
						params : {clauseId:clauseId,bidPlanId:bidPlanId,documentId: tempId ,projId : piKey ,businessType : businessType,conId : conId, contractType : cType,categoryId:categoryId},
						callback :function(r,o,s){
							if(s == false){
								pbar.getDialog().close();
								Ext.ux.Toast.msg('状态','合同模板没有上传，请先上传模板');
								return ;
							}
						}
					});
					var elementCodeModel = new Ext.grid.ColumnModel([
						new Ext.grid.RowNumberer({header:'序'}),
						{
							header : "要素类型",
							width : 228,
							sortable : true,
							dataIndex : 'elementType'
							
						},
						{
							header : "要素描述",
							width : 228,
							sortable : true,
							dataIndex : 'depict'
						},{
							header : "要素值",
							width : 228,
							sortable : true,
							dataIndex : 'value'
						}
					])
					var elementCodePanel = new Ext.grid.EditorGridPanel( {
						id:'elementCodePanel',
						store : elementCodeStore,
//						autoWidth : true,
						loadMask : true ,
						stripeRows : true ,
						loadMask : myMask,
						height : (window.screen.height-400)*0.8,
						autoScroll : true,
						colModel : elementCodeModel,
//						autoExpandColumn : 2,
						listeners : {
							celldblclick:function(a,b,r,c){
								if("自定义要素"==a.store.getAt(b).data.elementType){
									this.colModel.config[3].editor=new Ext.form.TextField({disabled:false});
								}else{
									this.colModel.config[3].editor=new Ext.form.TextField({disabled:true});
								}
							}
						}
					});
					Ext.getCmp('ContractMakeWay1').add(elementCodePanel);
					Ext.getCmp('ContractMakeWay1').doLayout();
				},
				failure : function(response, request) {
					Ext.ux.Toast.msg('状态', '加载错误');
				},
				params: { id : tempId}
			})
		}
	},
	getGridDate : function() {
		var vRecords = Ext.getCmp('elementCodePanel').getStore().getRange(0,Ext.getCmp('elementCodePanel').getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			var st="";
			for ( var i = 0; i < vCount; i++) {
				if("自定义要素"==vRecords[i].data.elementType){
					var value=vRecords[i].data.value;
					if(!value){
						value=" ";
					}
					vDatas+=vRecords[i].data.code+"@"+vRecords[i].data.depict+"@"+value+"!"
				}
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
			return vDatas;
		}
	},
	
	//封装系统要素 @HT_version3.0 新增
	getGridDate2 : function() {
		var vSysDatas = '';
		var dItems = Ext.getCmp('elementCodePanel').getStore().data.items;
		for(var i=0;i<dItems.length;i++){
			var data=dItems[i].data;
			if("系统要素"==data.elementType){
				var value=data.value;
				if(!value){
					value=" ";
				}
				vSysDatas+=data.code+"@"+value+"!"
			}
		}
		vSysDatas = vSysDatas.substr(0, vSysDatas.length - 1);
		return vSysDatas;
	}
})


var panel=null;
ContractMakeWay2 = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.temptId) != "undefined") {
			this.temptId = _cfg.temptId;
		}
		if (typeof(_cfg.piKey) != "undefined") {
			this.piKey = _cfg.piKey;
		}
		if (typeof(_cfg.contractId) != "undefined") {
			this.contractId = _cfg.contractId;
		}
		if (typeof(_cfg.contractType) != "undefined") {
			this.contractType = _cfg.contractType;
		}
		if (typeof(_cfg.thisPanel) != "undefined") {
			this.thisPanel = _cfg.thisPanel;
		}
		if (typeof(_cfg.thirdRalationId) != "undefined") {
			this.thirdRalationId = _cfg.thirdRalationId;
		}
		if (typeof(_cfg.contractCategoryTypeText) != "undefined") {
			this.contractCategoryTypeText = _cfg.contractCategoryTypeText;
		}
		if (typeof(_cfg.contractCategoryText) != "undefined") {
			this.contractCategoryText = _cfg.contractCategoryText;
		}
		if (typeof(_cfg.isApply) != "undefined") {
			this.isApply = _cfg.isApply;
		}
		if (typeof(_cfg.clauseId) != "undefined") {
			this.clauseId = _cfg.clauseId;
		}
		Ext.applyIf(this, _cfg);
		// 获取需要的this
		this.initUIComponents();
		
		ContractMakeWay2.superclass.constructor.call(this, {
			layout : 'anchor',
			closable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center'
		});//初始化
		this.add(panel);
		this.doLayout();
	},
	initUIComponents : function() {
		var cpanel = this.thisPanel;
		var tempId=this.temptId;
		var piKey=this.piKey;
		var businessType=this.businessType;
		var conId=this.conId;
		var cType=this.contractType;
		var contractId=this.contractId;
		var categoryId=this.categoryId;
		var thirdRalationId=this.thirdRalationId;
		var contractCategoryTypeText=this.contractCategoryTypeText;
		var contractCategoryText=this.contractCategoryText;
		if(tempId == null){
			Ext.ux.Toast.msg('状态','请先选择相应的模板！');
		}else{
			panel=new Ext.form.FormPanel({
				id : 'uploadContractFrom',
				url : __ctxPath+'/contract/uploadContractProduceHelper.do',
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
					labelWidth : 70,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '合同文件',
						allowBlank : false,
						blankText : '文件不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
		    			inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'contractId',
						value : contractId
					},{
						id :'fileUploadContentType',
						xtype : 'hidden',
						name: 'fileUploadContentType'
					},{
						xtype : 'hidden',
						name: 'templateId',
						value: tempId
					},{
						xtype : 'hidden',
						name: 'businessType',
						value: businessType
					},{
						xtype : 'hidden',
						name: 'projId',
						value: piKey
					},{
						xtype : 'hidden',
						name: 'categoryId',
						value: categoryId
					},{
						xtype : 'hidden',
						name: 'htlxdName',
						value: contractCategoryTypeText
					},{
						xtype : 'hidden',
						name: 'htmcdName',
						value: contractCategoryText
					},{
						xtype : 'hidden',
						name: 'htType',
						value: cType
					},{
						xtype : 'hidden',
						name: 'thirdRalationId',
						value: thirdRalationId
					},{
						xtype : 'hidden',
						name: 'isApply',
						value: this.isApply
					},{
						xtype : 'hidden',
						name: 'clauseId',
						value: this.clauseId
					},{
						xtype : 'hidden',
						name: 'searchRemark',
						value: this.searchRemark
					}]
				}],
					buttons : [{
						text : '上传',
						iconCls : 'uploadIcon',
						formBind : true,
						handler : function() {
							var str=Ext.getCmp("fileUpload").getValue();
						    str=str.substring(str.lastIndexOf("."),str.length)
							Ext.getCmp('uploadContractFrom').getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								params:{extendName:str},
								success : function(form ,action) {
									Ext.ux.Toast.msg('提示','上传成功！',
										function(btn, text) {
									});
									cpanel.getStore().reload();
								},
								failure : function(form, action) {
									Ext.ux.Toast.msg('提示','上传失败！');		
								}
							});
						}
					}]
				})
		}
	}
})
