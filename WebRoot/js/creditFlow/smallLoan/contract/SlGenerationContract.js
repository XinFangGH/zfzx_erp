/**
 * @author
 * @createtime
 * @class SlGenerationContract
 * @extends Ext.Window
 * @description SlGenerationContract
 * @company
 */
SlGenerationContract = Ext.extend(Ext.Window, {
	panelContract : null,
	categoryId:null,
	piKey:null,
	projectId : null,
	searchRemark : "",
	remark:"",
	isHiddenBZ:false,
	isHiddenContractNO:false,
	isHiddenDZY:false,
	isHiddenDW:true,
	isHiddenRZZLHT:true,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}else{
			this.title = '';
		};
		if (typeof(_cfg.isHiddenBZ) != "undefined") {
			this.isHiddenBZ = _cfg.isHiddenBZ;
		};
		if (typeof(_cfg.isHiddenContractNO) != "undefined") {
			this.isHiddenContractNO = _cfg.isHiddenContractNO;
		};
		if (typeof(_cfg.isHiddenDZY) != "undefined") {
			this.isHiddenDZY = _cfg.isHiddenDZY;
		};
		if (typeof(_cfg.isHiddenDW) != "undefined") {
			this.isHiddenDW = _cfg.isHiddenDW;
		};
		if (typeof(_cfg.categoryId) != "undefined") {
			this.categoryId = _cfg.categoryId;
		};
		if (typeof(_cfg.searchRemark) != "undefined") {
			this.searchRemark = _cfg.searchRemark;
		};
		if (typeof(_cfg.remark) != "undefined") {
			this.remark = _cfg.remark;
		};
		if (typeof(_cfg.isHiddenRZZLHT) != "undefined") {
			this.isHiddenRZZLHT = _cfg.isHiddenRZZLHT;
		};
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		};
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
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
		if (typeof(_cfg.thisPanel) != "undefined") {
			this.thisPanel = _cfg.thisPanel;
		};
		if (typeof(_cfg.isApply) != "undefined") {
			this.isApply = _cfg.isApply;
		};
		if (typeof(_cfg.clauseId) != "undefined") {
			this.clauseId = _cfg.clauseId;
		};
		if (typeof(_cfg.leaseObjectId) != "undefined") {
			this.leaseObjectId = _cfg.leaseObjectId;
		};
		if (typeof(_cfg.dwId) != "undefined") {
			this.dwId = _cfg.dwId;
		};
		if (typeof(_cfg.htType) != "undefined") {
			this.oldHtType = _cfg.htType; // add by zcb 用来区别是否是融资租赁展期流程
		};
		Ext.applyIf(this, _cfg);
		this.getmarkStr();
		var templateId = 0;// 模板ID
		this.initUIComponents();
		SlGenerationContract.superclass.constructor.call(this, {
			id:'SlContractWin',
			layout : 'anchor',
			iconCls : this.title ==''?'btn-add':'btn-edit',
			title : this.title ==''?'新增合同信息':'新增/编辑<<font color=red>'+this.title+'</font>>相关信息',
			width : 540,
			autoHeight:true,
			minHeight : this.windowHeight-1,
			closable : true,
			items : [
				this.panelContract
			],
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				scope :this,
				iconCls : 'btn-save',
				handler : function(){
					var a =this.htlxdName;
					var b = this.htmcdName;
					var temId = templateId !=0?templateId:this.temptId;
					this.makeContract(temId,this.businessType,this.piKey,'','',this.categoryId,this.contractId,a,b,this.htType,this.thirdRalationId,this.isApply,this.clauseId)
				}
			}],
			listeners:{
				scope:this,
				show:function(){
					if(this.isHiddenDW){
						Ext.getCmp("isHiddenDW").ownerCt.hide();
					}
				}
				
			}
		});
	},
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var htlxid = 0;// 合同类型id
		
		var isApply = this.isApply;
		var clauseId = this.clauseId;
		var tsColumn1 = {html : '<br/><font color=red >[温情提示：生成合同之前，请先保存项目信息，否则会有可能造成生成合同信息的不正确。]</font>'};
		var tsColumn2 =	{html : '<br/><font class="x-myZW-fieldset-title"> </font>'};
		var htlxColumn = {
				fieldLabel : '合同类型',
				emptyText : "请选择",
				xtype : 'contracttypecombo',
				mode : 'local',
				editable : false,
				itemValue : this.HTLX,
				triggerAction : 'all',
				isDisplayItemName : true,
				hiddenName : "contractCategoryTypeText",
				allowBlank : false,
				listeners : {
					scope : this,
					beforerender :function(){
						var opr_obj=this.getCmpByName('contractCategoryText')
						if(this.temptId != null){
							Ext.Ajax.request({
								url : __ctxPath+'/contract/validateExistDocumentTemplet.do',
								method : 'POST',
								scope : this,
								success : function(response, request) {
									var obj = Ext.util.JSON.decode(response.responseText);
									if(obj.success == true){
										var arrStore = new Ext.data.ArrayStore({
											url : __ctxPath	+ '/contract/getAllByParentIdDocumentTemplet.do',
											fields : ['itemId', 'itemName'],
											baseParams : {
												parentid : obj.data.parentid
											}
										});
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load();
										if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
											 opr_obj.view.setStore(arrStore);
										}
									}else{
										Ext.ux.Toast.msg('状态', '请先选择合同类型！');
									}
								},
								failure : function(response) {},
									scope : this,
									params : {
										id : this.temptId
									}
								})
						}
					},
					afterrender : function(combox) {
						combox.clearInvalid();
					},
					select:function(combox, record, index){
						var v = record.data.itemId;
						this.htlxdName = record.data.itemName;
						var arrStore = new Ext.data.ArrayStore({
							url : __ctxPath	+ '/contract/getAllByParentIdDocumentTemplet.do',
							fields : ['itemId', 'itemName'],
							baseParams : {
								parentid : v
							}
						});
						var opr_obj=this.getCmpByName('contractCategoryText')
						opr_obj.clearValue();
						opr_obj.store = arrStore;
						arrStore.load();
						if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
							opr_obj.view.setStore(arrStore);
						}
					}
				}
		};
		var mortgagenameColumn = {
			id : "dzy",
			fieldLabel : "抵质押物",
			xtype : "combo",
			emptyText : "请选择",
			displayField : 'itemName',
			valueField : 'itemId',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
					projectId : ((typeof(this.sprojectId)!='undefined' && null!=this.sprojectId)?this.sprojectId:this.projectId),
					businessType : this.businessType,
					tag : "dzy"//抵质押担保
                },
                url : __ctxPath + '/credit/mortgage/getMortageStore.do',
                fields : ['itemId', 'itemName']
            }),
			hiddenName : "mortgageTypeValue",//mortgagename
			allowBlank : false,
			isDisplayItemName : true,
			editable : false,
			listeners : {
				scope : this,
				select:function(combox, record, index) {
					this.thirdRalationId = record.data.itemId;
				},
				afterrender : function(combox) {
					combox.clearInvalid();
				}
			}
		};
		
		var bzrColumn = {
				id : "bz",
				fieldLabel : "保证人",
				xtype : "combo",
				emptyText : "请选择",
				displayField : 'itemName',
				valueField : 'itemId',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
	                autoLoad : true,
	                baseParams : {
						projectId : ((typeof(this.sprojectId)!='undefined' && null!=this.sprojectId)?this.sprojectId:this.projectId),
						businessType : this.businessType,
						tag : "bz"//保证担保
	                },
	                url : __ctxPath + '/credit/mortgage/getMortageStore.do',
	                fields : ['itemId', 'itemName']
	            }),
				hiddenName : "mortgagename",
				allowBlank : false,
				editable : false,
				listeners : {
					scope : this,
					select:function(combox, record, index) {
						this.thirdRalationId = record.data.itemId;
					},
					afterrender : function(combox) {
						combox.clearInvalid();
					}
				}
		};
		
		var lONColumn = {
				xtype : "combo",
				id : "leaseObjectName",
				fieldLabel : "标的物名称",
				emptyText : "请选择",
				displayField : 'name',
				valueField : 'id',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.JsonStore({
	                autoLoad : true,
	                baseParams : {
						projectId : this.projectId
	                },
	                root:'result',
	                url : __ctxPath +'/creditFlow/leaseFinance/project/listViewFlLeaseobjectInfo.do',//+'&businessType='+this.businessType+'&isReadOnly='+this.isReadOnly,
	                fields : ['id', 'name']
	            }),
				hiddenName : "leaseObjectInfo",
				allowBlank : false,
				editable : true,
				listeners : {
					scope : this,
					select:function(combox, record, index) {
						this.thirdRalationId = record.data.id;
						this.getCmpByName('ramark').setValue(record.data.name);
					},
					afterrender : function(combox) {
						var leaseObjectId=this.leaseObjectId
						combox.getStore().on('load',function(){
							combox.setValue(leaseObjectId);
						})
					}
				}
		};
		
		var htmcColumn = {
			fieldLabel : "合同名称",
			xtype : "combo",
			emptyText : "请选择",
			displayField : 'itemName',
			valueField : 'itemId',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.SimpleStore({
				fields : ['displayText', 'value'],
				data : [['地区', 1], ['LAC', 2], ['CI', 3],['被叫用户', 4]]}),
				hiddenName : "contractCategoryText",
				allowBlank : false,
				editable : false,
				listeners:{
					scope : this,
					select :function(combox, record, index){
						this.htmcdName = record.data.itemName;
						templateId = record.data.itemId;
						if(this.temptId == null){
							this.temptId = templateId;
						}
					}
				}
		};
		
		var pawnColumn = {
			id : "dw",
			fieldLabel : "当物",
			xtype : "combo",
			emptyText : "请选择",
			displayField : 'itemName',
			valueField : 'itemId',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
					projectId : this.projectId,
					businessType : this.businessType
                },
                url : __ctxPath + '/creditFlow/pawn/pawnItems/getPawnListPawnItemsList.do',
                fields : ['itemId', 'itemName']
            }),
			hiddenName : "pawnName",
			allowBlank : false,
			isDisplayItemName : true,
			editable : false,
			listeners : {
				scope : this,
				select:function(combox, record, index) {
					this.thirdRalationId = record.data.itemId;
					this.getCmpByName('remark').setValue(record.data.itemName);
				},
				afterrender : function(combox) {
						var dwId=this.dwId
						combox.getStore().on('load',function(){
							combox.setValue(dwId);
						})
					
				}
			}
		};
		
		var contractRemark = {
			name : "remark",
			fieldLabel : "合同编号",
			xtype : "textfield",
			allowBlank : false,
			emptyText : "请输入",
			value:this.projectNumber
		}
		
		// 创建combo实例
		this.panelContract = new Ext.form.FormPanel({
			buttonAlign : 'center',
			frame : true,
			border : false,
			anchor : anchor,
			monitorValid : true,
			labelAlign : 'right',
			autoScroll : true,
			items : [{
				layout : 'column',
				autoHeight : true,
				collapsible : false,
				anchor : anchor,
				items : [{
					columnWidth : 1,
					layout : 'form',
					id : 'addDocumentColumn',
					defaults : {
						anchor : anchor
					},
					items : [
						{
						xtype :'hidden',
						name :'id'
					}]
				}]
			}]
		});
		this.autoLoadData();
		var thisPanelContract = this.panelContract;
		var htTypeTag = false;
		var htTypeLabel = "借款合同";
		if(this.htType == "XEDQ" && this.businessType=='SmallLoan') {
			htTypeTag = true;
		}else if(this.htType == "loanContract" && this.businessType=='SmallLoan') {
			htTypeTag = true;
		}else if(this.htType == "extenstionContract"  && this.businessType=='SmallLoan') {
			htTypeTag = true;
			htTypeLabel = "贷款展期合同";
		}else if(this.htType=="financingContract" && this.businessType=='Financing') {
			htTypeTag = true;
			htTypeLabel = "融资业务合同";
		}else if(this.htType=="guaranteeContract" && this.businessType=='Guarantee'){
			htTypeTag = true;
			htTypeLabel = "担保业务合同";
		}else if(this.htType == "clauseContract"  && this.businessType=='Guarantee') {
			htTypeTag = true;
			htTypeLabel = "担保展期合同";
		}else if(this.htType == "leaseFinanceContract"  && this.businessType=='LeaseFinance') {
			htTypeTag = true;
			htTypeLabel = "租赁物买卖合同";
		}else if(this.htType == "extenstionContract"  && this.businessType=='LeaseFinance') {
			htTypeTag = true;
			htTypeLabel = "贷款展期合同";
		}else if(this.htType == "leaseFinanceZQHT"  && this.businessType=='LeaseFinance') {
			htTypeTag = true;
			htTypeLabel = "租赁租赁展期合同";
		}else if(this.htType == "pawnContract"  && this.businessType=='Pawn') {
			htTypeTag = true;
			htTypeLabel = "典当合同";
		}else if(this.htType == "investContract"  && this.businessType=='Assignment'){
			htTypeTag = true;
			htTypeLabel = "投资合同";
		}else if(this.htType == "P2PJKRHT" && this.businessType=='SmallLoan'){
			htTypeTag = true;
			htTypeLabel = "P2P借款合同";
		}else if(this.htType == "C2PJKRHT" && this.businessType=='SmallLoan'){
			htTypeTag = true;
			htTypeLabel = "C2P借款合同";
		}
		var contractTypeRadioGroup = new Ext.form.RadioGroup({
			items : [{
				columnWidth : .25,
				items : [{
					boxLabel : htTypeLabel,
					style : "margin-left:0px;",
					inputValue : "1",
					name : "rg",
					checked : htTypeTag == true?true:false
				}]
				
			}, {
				columnWidth : .25,
				items : [{
					boxLabel : '保证合同',
					name : "rg",
					inputValue : "2",
					style : "margin-left:10px;",
					checked : this.htType =="baozContract"?true:false,
					hidden:this.isHiddenBZ
				}]
				
			}, {
				columnWidth : .25,
				items : [{
					boxLabel : '抵质押合同',
					name : "rg",
					inputValue : "3",
					style : "margin-left:10px;",
					checked : this.htType =="thirdContract"?true:false,
					hidden:this.isHiddenDZY
				}]
				
			},{
				columnWidth : .25,
				items : [{
					boxLabel : '当物合同',
					name : "rg",
					inputValue : "4",
					id:'isHiddenDW',
					style : "margin-left:10px;",
					/*ctCls: 'custom-class',
					clearCls:'x-box-layout-ct',*/
					checked : this.htType =="dwContract"?true:false,
					hidden:this.isHiddenDW
				}]
				/*,
				hideLabel:true,
				hideParent :true,
				listeners:{
					afterrender:function(){
						this.isVisible()?null:this.hide();
					}
				}*/
			},{
				columnWidth : .25,
				items : [{
					boxLabel : "融资租赁合同",
					style : "margin-left:0px;",
					inputValue : "5",
					hidden:this.isHiddenRZZLHT,
					checked : this.htType =="leaseFinanceContractExt"?true:false,
					name : "rg"
				}]
				
			}],
			listeners : {
				scope : this,
				"change" : function(com, checked) {
					var inputValue = com.getValue().inputValue;
					var markStr = "";
					var formPanel = thisPanelContract.items.get(0).items.get(0);
					//var makeContractButton = thisPanelContract.items.get(0).items.get(1).items.get(0);
					if(formPanel.get(3).getName() == "mortgagename") {// baozmortgage
						formPanel.remove("bz",true);//移除保证人下拉框
					}
					if(formPanel.get(3).getName() == "leaseObjectInfo") {
						formPanel.remove("leaseObjectName",true);
					}
					if(formPanel.get(3).getName() == "mortgageTypeValue") {//mortgagename
						formPanel.remove("dzy",true);//移除抵质押物下拉框
					}
					if(formPanel.get(3).getName() == "pawnName") {
						formPanel.remove("dw",true);
					}
					this.getCmpByName('remark').setValue("");
					this.contractId = null;
					this.categoryId = null;
					this.temptId = null;
					if (inputValue == 1) {
						this.htType = null;
//						this.htType = "XEDQ";
						if(this.htType == "leaseFinanceContract"||"leaseFinanceContract"==this.oldHtType){
							formPanel.insert(3,lONColumn);//租赁标的信息下拉框
							this.setHeight(325);
						}else{
							this.setHeight(290);
						}
						this.doLayout();
					} else if (inputValue == 2) {
						this.htType = "baozContract";
						formPanel.insert(3,bzrColumn);//加入保证人下拉框
						this.setHeight(325);
						this.doLayout();
					}else if (inputValue == 3) {
						this.htType = "thirdContract";
						formPanel.insert(3,mortgagenameColumn);//加入抵质押物下拉框
						this.setHeight(325);
						this.doLayout();
					}else if (inputValue == 4) {
						this.htType = "dwContract";
						formPanel.insert(3,pawnColumn);
						this.setHeight(325);
						this.doLayout();
					}else if (inputValue == 5) {
						this.htType = "leaseFinanceContractExt";
						this.setHeight(290);
						this.doLayout();
					}
					markStr =this.getmarkStr(inputValue);
					formPanel.doLayout();
					var arrStore = new Ext.data.ArrayStore({
						url : __ctxPath	+ '/contract/getAllByOnlymarkDocumentTemplet.do',
						fields : ['itemId', 'itemName'],
						baseParams : {
				    		mark : markStr
						}
				    });
				    var opr_obj = this.getCmpByName('contractCategoryTypeText')
				    opr_obj.clearValue();
				    var opr_obj1 = this.getCmpByName('contractCategoryText')
				    opr_obj1.clearValue();
				    opr_obj.store = arrStore;
				    arrStore.load();
				    if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
				    	opr_obj.view.setStore(arrStore);
				    }
				},
				"afterrender" : function(com, checked) {
					var inputValue = com.getValue().inputValue;
					var formPanel = thisPanelContract.items.get(0).items.get(0);
					if (inputValue == 2) {
						this.htType = "baozContract";
						formPanel.insert(3,bzrColumn);//加入保证人下拉框
						this.setHeight(325);
						this.doLayout();
					}else if (inputValue == 3) {
						this.htType = "thirdContract";
						formPanel.insert(3,mortgagenameColumn);//加入抵质押物下拉框
						this.setHeight(325);
						this.doLayout();
					}else if (inputValue == 4) {
						this.htType = "dwContract";
						formPanel.insert(3,pawnColumn);
						this.setHeight(325);
						this.doLayout();
					}else if (inputValue == 5) {
						this.htType = "leaseFinanceContractExt";
						this.setHeight(290);
						this.doLayout();
					}
					formPanel.doLayout();
				}
			}
		});
		var centerPanel = thisPanelContract.items.get(0).items.get(0);
		if(this.businessType =='Financing'){
			centerPanel.add(tsColumn1);//加入提示列
		}else{
			centerPanel.add(tsColumn2);//加入空列
		}
		centerPanel.add(contractTypeRadioGroup);
		if(this.businessType =='LeaseFinance' && this.htType == 'leaseFinanceContract'){
			centerPanel.add(lONColumn);//加入提示列
		}
		centerPanel.add(htlxColumn);//加入合同类型列
		centerPanel.add(htmcColumn);//加入合同名称列
		if(!this.isHiddenContractNO)centerPanel.add(contractRemark);//加入合同备注文本输入框or加入合同编号文本输入框
		centerPanel.setWidth('100%');
	},
	autoLoadData : function(id){
		var panel = this.panelContract;
		this.panelContract.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/contract/seeContractCategoryProcreditContract.do',
			waitMsg : '正在载入数据...',
			scope : this,
			params : {
				contractId : id!= null?id:this.contractId
			},
			success : function(form, action) {
				this.getCmpByName('contractCategoryTypeText').setRawValue(action.result.data.contractCategoryTypeText);
				this.getCmpByName('contractCategoryText').setRawValue(action.result.data.contractCategoryText);
				this.getCmpByName('remark').setValue(action.result.data.contractNumber)
				//this.categoryId = action.result.data.contractId;
				this.contractId = action.result.data.id;
				this.temptId = action.result.data.temptId;
				panel.items.get(0).items.get(1).items.get(0).setWidth('100%');
			},
			failure : function(form, action) {
			}
		});
		
	},
	//保存合同主体

	makeContract :function(temId,businessType,piKey,taskId,contractType,categoryId,contractId,a,b,htType,thirdRalationId,isApply,clauseId){
		var cpanel = this.thisPanel;
		var bzgridPanel=this.bzgridPanel
		var dzygridPanel=this.dzygridPanel
		var window = this;
		var remark = this.getCmpByName('remark')? this.getCmpByName('remark').getValue():"";
		var contractCategoryTypeText = this.getCmpByName('contractCategoryTypeText').getValue(); 
		var contractCategoryText = this.getCmpByName('contractCategoryText').getValue(); 
		var leaseObjectInfo="";
		var dwId="";

		var number=this.getCmpByName('remark');

		var thisbidPlanId=this.bidPlanId;


		if( Ext.getCmp('leaseObjectName')){
			leaseObjectInfo = Ext.getCmp('leaseObjectName').getValue(); 
		}
		if( Ext.getCmp('dw')){
			dwId = Ext.getCmp('dw').getValue(); 
		}
		if(temId == null){
			Ext.ux.Toast.msg('状态', '请先选择合同类型和合同名称!');
			return false;
		}else if(null==number.getValue() || ""==number.getValue()){
			Ext.ux.Toast.msg('状态', '请填写合同编号!');
			return false;
		}else{
			var args ={
				projId : piKey ,
				businessType : businessType,
				templateId : temId,
				taskId : taskId,
				contractType : contractType,
				categoryId : categoryId,
				contractId : contractId,
				orMake : orMake,
				htlxdName : a,
				htmcdName : b,
				thirdRalationId :thirdRalationId,
				searchRemark : remark,
				clauseId :clauseId,
				isApply : isApply,
				contractCategoryTypeText:contractCategoryTypeText,
				contractCategoryText:contractCategoryText,
				leaseObjectInfo:leaseObjectInfo,  //租赁物标的名称
				dwId:dwId,  //租赁物标的名称
				htType:htType,
                bidPlanId:thisbidPlanId
			}
			var orMake ='make';
			if(orMake == 'make'){
				var pbar = Ext.MessageBox.wait('正在保存，请耐心等待...','请等待',{
					interval:200,
					increment:15
				});
				Ext.Ajax.request({
					url : __ctxPath+'/contract/makeContractMainProduceHelper.do',
					method : 'POST',
					scope : this,
					success : function(response, request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						if(obj.success == true){
							this.autoLoadData(obj.data.id);//重新加载
							pbar.getDialog().close();
							var gridPanel = cpanel;
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							if(htType=='baozContract'){
								if(typeof(bzgridPanel)!="undefined" && bzgridPanel!=null){
									bzgridPanel.getStore().reload();
								}
							}
							if(htType=='thirdContract'){
								if(typeof(dzygridPanel)!="undefined" && dzygridPanel!=null){
									dzygridPanel.getStore().reload();
								}
							}
							Ext.ux.Toast.msg('状态', '保存成功！');
							if(Ext.getCmp('SlContractWin')){
								Ext.getCmp('SlContractWin').destroy();
							}
						}else{
							pbar.getDialog().close();
							Ext.ux.Toast.msg('状态', '保存失败！');
						}
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '保存失败，请重试！');
						pbar.getDialog().close();
					},
					params : args
				})
			}
		}
	},
	getmarkStr:function(inputValue){
		if (typeof(this.businessType) != "undefined") {
			this.businessType = this.businessType;
			//项目合同
			if(this.businessType == 'SmallLoan'){
				this.HTLX = 'loanContract';//合同类型（合同模板祖宗ID）,小贷合同
				this.windowHeight = 291;
			}else if(this.businessType == 'Guarantee'){
				this.HTLX = 'DBDQ';//企业贷合同
				this.windowHeight = 265;
			}else if(this.businessType == 'Financing'){
				this.HTLX = 'ZJRZ'
				this.windowHeight = 295;
			}else if(this.businessType =='LeaseFinance'){
				if(inputValue == 5){
					this.HTLX = "leaseFinanceContractExt";
					this.windowHeight = 290;
				}else{
					if("leaseFinanceZQHT"==this.oldHtType){
						this.HTLX = 'leaseFinanceZQHT'
					}else{
						this.HTLX = 'leaseFinanceContract'
					}
					this.windowHeight = 295;
				}
			}
			if(this.htType == 'pawnContract' && this.businessType =='Pawn'){
				this.HTLX='pawnContract'
			}else if(this.htType == 'dwContract' && this.businessType =='Pawn'){
				this.HTLX='dwContract'
			}
		};
		if (typeof(this.htType) != "undefined") {
			this.htType = this.htType;
			if(this.htType == 'clauseContract' && this.businessType =='SmallLoan'){
				this.HTLX = 'XEDZZQ'//小额贷中展期
			}else if(this.htType == 'clauseContract' && this.businessType =='Guarantee'){
				this.HTLX = 'DBDZZQ';
			}else if(this.htType == 'thirdRalationContract' && this.businessType == 'Financing'){
				this.HTLX = 'ZJRZ'//资金融资
			}
			if(this.htType == 'guaranteeContract' && this.businessType =='Guarantee'){
				this.HTLX = 'DBDQ'
			}
			if(this.htType == 'P2PJKRHT' && this.businessType =='SmallLoan'){
				this.HTLX = 'P2PJKRHT'//P2P借款合同
			}
			if(this.htType == 'C2PJKRHT' && this.businessType =='SmallLoan'){
				this.HTLX = 'C2PJKRHT'//P2P借款合同
			}
			//抵质押物合同
			if(this.htType == 'thirdContract' && this.businessType =='Guarantee'){
				this.HTLX = 'DBCS'
			}else if(this.htType == 'thirdContract' && this.businessType =='Financing'){
				this.HTLX = 'RZCS'
			}else if(this.htType == 'thirdContract' && this.businessType =='SmallLoan'){
				this.HTLX = 'XEDBCS'
			}else if(this.htType == 'thirdContract' && this.businessType =='LeaseFinance'){
				this.HTLX = 'leaseFinanceDZYHT'
			} 
			
			//保证合同
			if(this.htType == 'baozContract' && this.businessType =='Guarantee'){
				this.HTLX = 'DBBZDB'
			}else if(this.htType == 'baozContract' && this.businessType =='Financing'){
				this.HTLX = 'RZBZDB'
			}else if(this.htType == 'baozContract' && this.businessType =='SmallLoan'){
				this.HTLX = 'XEDBZDB'
			}else if(this.htType == 'baozContract' && this.businessType =='LeaseFinance'){
				this.HTLX = 'leaseFinanceBZHT'
			}
			
			if(this.htType == 'investContract' && this.businessType =='Assignment'){
				this.HTLX = 'investContract'
			}
			if(this.htType=='otherFiles'){
				this.HTLX = 'OtherFiles'
			}
			if(this.htType=='loanContract'){
				this.HTLX = 'loanContract'
			}
			if(this.htType=='extenstionContract'){
				this.HTLX = 'XEDZZQ'
			}
			return this.HTLX ;
		};
	}
})
