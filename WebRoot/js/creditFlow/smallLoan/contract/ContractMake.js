/**
 * @author
 * @createtime
 * @class SlMakeContract
 * @extends Ext.Window
 * @description SlMakeContract
 * @company
 */
ContractMake = Ext.extend(Ext.Window, {
	id:'contractMakeFunctionForm',
	panelContract : null,
	contractId:0,
	categoryId:null,
	piKey:null,
	projectId : null,
	searchRemark : "",
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
		if (typeof(_cfg.searchRemark) != "undefined") {
			this.searchRemark = _cfg.searchRemark;
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
		if (typeof(_cfg.htType) != "undefined") {
			this.htType = _cfg.htType;
			if(typeof(_cfg.HTLX) == "undefined"){
				if(this.htType == 'clauseContract' && this.businessType =='SmallLoan'){
					this.HTLX = 'XEDZZQ'//小额贷中展期
				}else if(this.htType == 'clauseContract' && this.businessType =='Guarantee'){
					this.HTLX = 'DBDZZQ';
				}else if(this.htType == 'thirdRalationContract' && this.businessType == 'Financing'){
					this.HTLX = 'ZJRZ'//资金融资
				}
				if(this.htType == 'thirdContract' && this.businessType =='Guarantee'){
					this.HTLX = 'DBCS'
					this.windowHeight =400;
				}else if(this.htType == 'thirdContract' && this.businessType =='Financing'){
					this.HTLX = 'RZCS'
					this.windowHeight =400;
				}else if(this.htType == 'thirdContract' && this.businessType =='SmallLoan'){
					this.HTLX = 'XEDBCS'
					this.windowHeight =400;
				}
				if(this.htType == 'baozContract' && this.businessType =='Guarantee'){
					this.HTLX = 'DBBZDB'
					this.windowHeight =400;
				}else if(this.htType == 'baozContract' && this.businessType =='Financing'){
					this.HTLX = 'RZBZDB'
					this.windowHeight =400;
				}else if(this.htType == 'baozContract' && this.businessType =='SmallLoan'){
					this.HTLX = 'XEDBZDB'
					this.windowHeight =400;
				}
				if(this.htType == 'investContract' && this.businessType =='Assignment'){
					this.HTLX = 'investContract'
				}
				if(this.htType=='otherFiles'){
					this.HTLX = 'OtherFiles'
				}
				if(this.htType=='XEDQ'){
					this.HTLX = 'XEDQ'
					this.windowHeight =300;
				}
				if(this.htType=='extenstionContract'){
					this.HTLX = 'XEDZZQ'
				}
			}else{
				this.HTLX = _cfg.HTLX;
			}
		};
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		var templateId = 0;// 模板ID
		this.initUIComponents();
		ContractMake.superclass.constructor.call(this, {
					layout : 'anchor',
					iconCls : this.title ==''?'btn-add':'btn-edit',
					title : this.title ==''?'新增合同信息':'新增/编辑<<font color=red>'+this.title+'</font>>相关信息',
					width : (window.screen.width-200)*0.7,
					height:(window.screen.height-100)*0.9,
					//autoHeight : true,
					closable : true,
					items : [
						this.panelContract
					],
					border : false,
					modal : true,
//					autoScroll:true,
					plain : true,
					buttonAlign : 'center',
					resizable :false,
					constrain :true,
					buttons : [{
						text : this.contractName ==null ||this.contractName == 'undefined'||this.contractName ==''?'生成合同':'重新生成合同',
						scope :this,
						handler : function(){
							var a = this.htlxdName;
							var b = this.htmcdName;
							var temId = templateId !=0?templateId:this.temptId;
							var vDatas=Ext.getCmp('ContractMakeWay1').getGridDate();
							var vSysDatas=Ext.getCmp('ContractMakeWay1').getGridDate2();//@HT_version3.0 新增
							this.makeContract(temId,this.businessType,this.piKey,'','',this.categoryId,this.contractId,a,b,this.htType,this.thirdRalationId,this.isApply,this.clauseId,vDatas,this.contractName,vSysDatas)
						}
				
					}]
				});
	},
	// 初始化组件
	initUIComponents : function() {
		var anchor = '98%';
		var htlxid = 0;// 合同类型id
		
		var isApply = this.isApply;
		var clauseId = this.clauseId;
		var tsColumn1 = {html : '<br/><font color=red >[温情提示：生成合同之前，请先保存项目信息，否则会有可能造成生成合同信息的不正确。]</font>'};
		var tsColumn2 =	{html : '<br/><font class="x-myZW-fieldset-title"> </font>'};
		var htTypeLabel = "贷款合同";
		if(this.htType == "XEDQ") {
			htTypeTag = true;
		}else if(this.htType == "extenstionContract") {
			htTypeTag = true;
			htTypeLabel = "展期合同";
		}else if(this.htType == 'baozContract'){
			htTypeLabel = "保证合同";
		}else if(this.htType == 'thirdContract'){
			htTypeLabel = "抵质押合同";
		}
		//合同类型
		var htlxColumn = {
			xtype:'label',
			html :'合同类型：'+this.contractCategoryTypeText
		};
		//合同名称
		var htmcColumn = {
			xtype:'label',
			html :'<br/>合同名称：'+this.contractCategoryText
		};
		//备注
		var contractRemark = {
			xtype:'label',
			html :'<br/>合同编号：'+this.searchRemark
		}
		
		// 创建combo实例
		this.panelContract = new Ext.Panel({
			buttonAlign : 'center',
			frame : true,
			border : false,
			anchor : anchor,
			labelAlign : 'right',
			labelWidth : 70,
			//autoScroll : true,
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : "合同",
				collapsible : true,
				autoHeight : true,
				anchor : anchor,
				items : [{
					columnWidth : 1,
					layout : 'form',
					id : 'addDocumentColumn'
				}]
			},{
				layout : 'column',
				id:'ContractUrl',
				name : 'contractUrlPanel',
				xtype : 'fieldset',
				title : "合同文件路径",
				collapsible : true,
				autoHeight : true,
				anchor : anchor,
				items : [{
					xtype : 'label',
					name : 'contractUrl'
				}],
				buttons : [{
					hidden : true,
					scope : this,
					text : '下载',
					handler : function(){
								SlContractView.downloadContract(this.contractId)
							}
				}]
			}/*,{
				layout : 'column',
				id:'HTRadioGroup',
				xtype : 'fieldset',
				title : "合同生成方式",
				collapsible : true,
				autoHeight : true,
				anchor : anchor,
				items : [new Ext.form.RadioGroup({
					items : [{
						boxLabel : '模板生成',
						name : "HTMB",
						inputValue : "1",
						style : "margin-left:20px;",
						checked:true
					},{
						boxLabel : '上传合同',
						name : "HTMB",
						inputValue : "2",
						style : "margin-left:20px;"
					}],
					listeners : {
						scope : this,
						"change" : function(com, checked) {
							var inputValue = com.getValue().inputValue;
							var op = Ext.getCmp('contractMakeFunctionForm');
							var customerInfo = op.getCmpByName("contractMakeWayFieldSet");
							this.panelContract.remove(customerInfo, true);
							op.doLayout();
							if (inputValue == 1) {
								var MubanMakeWay = op.getCmpByName("MubanMakeWayFieldSet");
								MubanMakeWay.show();
								return false;
							}else if (inputValue == 2) {
								var MubanMakeWay = op.getCmpByName("MubanMakeWayFieldSet");
								MubanMakeWay.hide();
								var testFieldSet = new Ext.form.FieldSet({
									title : '上传合同',
									collapsible : true,
									labelAlign : 'right',
									bodyStyle : 'padding-left:0px;',
									name : 'contractMakeWayFieldSet',
									frame : true ,
									items : [
										new ContractMakeWay2({
											searchRemark :op.searchRemark,
											businessType : op.businessType,
											temptId : op.temptId,
											piKey : op.piKey,
											contractId : op.contractId,
											categoryId:op.categoryId,
											height : (window.screen.height-500)*0.8,
											contractType:op.htType,
											thirdRalationId:op.thirdRalationId,
											isApply:isApply,
											clauseId:clauseId,
											thisPanel:op.thisPanel,
											contractCategoryTypeText:op.contractCategoryTypeText,
											contractCategoryText:op.contractCategoryText
									})]
								});
								this.panelContract.add(testFieldSet);
								//op.autoHeight=true
								this.panelContract.doLayout();
								return false;
							}
					}}
				})]
			}*/,{
				//layout : 'fit',
				xtype : 'fieldset',
				collapsible : true,
				title : '模板生成方式',
				anchor : anchor,
//				autoHeight:true,
				autoScroll:true,
				name:'MubanMakeWayFieldSet',
				items:[
					new ContractMakeWay1({
						businessType : this.businessType,
						bidPlanId : this.bidPlanId,
						temptId : this.temptId,
						piKey : this.piKey,
						contractId : this.contractId,
						contractType:this.htType,
						categoryId:this.categoryId,
						clauseId:this.clauseId
					})
				]
			}]
		});
		var thisPanelContract = this.panelContract;
		Ext.Ajax.request({
				url : __ctxPath+'/customer/getUrlContract.do',
				method : 'POST',
				success : function(response, request){
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					var HTURLPanel = thisPanelContract.getCmpByName('contractUrlPanel');
					HTURLPanel.getCmpByName('contractUrl').setText('路径：'+alarm_fields.url.split('/')[alarm_fields.url.split('/').length-1]);
					HTURLPanel.setWidth('97%');
					if (alarm_fields.url != '') {
						 HTURLPanel.buttons[0].show();
					} 
				
				},
				params:{
					projectId:this.projectId,
					businessType:this.businessType,
					htType:this.htType ,
					contractId:this.contractId
				}
		})
		
		var htTypeTag = false;
		
		var centerPanel = thisPanelContract.items.get(0).items.get(0);
		if(this.businessType =='Financing'){
			centerPanel.add(tsColumn1);//加入提示列
			centerPanel.add(tsColumn2);//加入空列
		}
		if(this.htType == "baozContract"){
			//合同对象 贷款合同、抵制押物、保证人
			var htdx={
				xtype:'label',
				html:'保证人：'+this.mortgagename 
			}
			centerPanel.add(htdx);
			
			//合同类别
			var htlbColumn = {
				xtype:'label',
				html:'<br/>合同类别：'+htTypeLabel+"<br/>"
			};
			centerPanel.add(htlbColumn);//加入合同类别列
		}
		if(this.htType == "thirdContract"){
			//合同对象 贷款合同、抵制押物、保证人
			var htdx={
				xtype:'label',
				html:'抵质押物：'+this.mortgagename
			}
			centerPanel.add(htdx);
			
			//合同类别
			var htlbColumn = {
				xtype:'label',
				html:'<br/>合同类别：'+htTypeLabel+"<br/>"
			};
			centerPanel.add(htlbColumn);//加入合同类别列
		}
		centerPanel.add(htlxColumn);//加入合同类型列
		centerPanel.add(htmcColumn);//加入合同名称列
		centerPanel.add(contractRemark);//加入合同备注文本输入框or加入合同编号文本输入框
		centerPanel.setWidth('100%');
	},
	autoLoadData : function(id){
		var panel = this.panelContract;
		this.panelContract.load({
			deferredRender : false,
			url : __ctxPath + '/contract/seeContractCategoryProcreditContract.do',
			waitMsg : '正在载入数据...',
			scope : this,
			params : {
				categoryId : id!= null?id:this.categoryId
			},
			success : function(form, action) {
				this.contractId = action.result.data.contractId;
				this.categoryId = action.result.data.id;
				this.temptId = action.result.data.temptId;
			},
			failure : function(form, action) {
			}
		});
		
	},
	//生成合同
	makeContract :function(temId,businessType,piKey,taskId,contractType,categoryId,contractId,a,b,htType,thirdRalationId,isApply,clauseId,vDatas,contractName,vSysDatas){
		var cpanel = this.thisPanel;
		var window = this;
		var remark = this.searchRemark;
		var args={
			projId : piKey ,
			bidPlanId:this.bidPlanId,
			businessType : businessType,
			templateId : temId,
			taskId : taskId,
			contractType : contractType,
			categoryId : categoryId,
			dwId : this.dwId,
			leaseObjectId : this.leaseObjectId,
			contractId : contractId,
			orMake : orMake,
			htlxdName : a,
			htmcdName : b,
			thirdRalationId :thirdRalationId,
			searchRemark : remark,
			htType :htType,
			isApply : isApply,
			clauseId :clauseId,
			vDatas:vDatas,
			vSysDatas:vSysDatas //@HT_version3.0
		}
		
		var orMake = contractName == null || contractName  == 'undefined'||contractName == ''? 'make':'reMake';
		if(orMake == 'reMake'){
			Ext.Msg.confirm("提示!", '重新生成将会删除掉您之前上传的合同！是否继续？', function(btn) {
				if (btn == "yes") {
					var pbar = Ext.MessageBox.wait('合同生成中，可能需要较长时间，请耐心等待...','请等待',{
						interval:200,
					    increment:15
					});
						
					Ext.Ajax.request({
						url : __ctxPath+'/contract/createContractContractHelper.do',//@HT_version3.0
						method : 'POST',
						scope : this,
						success : function(response, request) {
							var obj = Ext.util.JSON.decode(response.responseText);
							if(obj.success == true){
								pbar.getDialog().close();
								var gridPanel = cpanel;
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								if(obj.msg){
									Ext.ux.Toast.msg('状态',obj.msg);
								}else{
									Ext.ux.Toast.msg('状态', '合同生成成功!');
									window.autoLoadData(obj.id);//重新加载
									if(Ext.getCmp('contractMakeFunctionForm')){
										Ext.getCmp('contractMakeFunctionForm').destroy();
									}
								}
							}else{
								pbar.getDialog().close();
							}
						},
						params : args
					})
				}
			})
		}else if(orMake == 'make'){
			var pbar = Ext.MessageBox.wait('合同生成中，可能需要较长时间，请耐心等待...','请等待',{
					interval:200,
					increment:15
			});
			Ext.Ajax.request({
				url : __ctxPath+'/contract/createContractContractHelper.do',//@HT_version3.0
				method : 'POST',
				scope : this,
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success == true){
						pbar.getDialog().close();
						var gridPanel = cpanel;
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						if(obj.msg){
							Ext.ux.Toast.msg('状态',obj.msg);
						}else{
							Ext.ux.Toast.msg('状态', '合同生成成功!');
							window.autoLoadData(obj.id);//重新加载
							if(Ext.getCmp('contractMakeFunctionForm')){
								Ext.getCmp('contractMakeFunctionForm').destroy();
							}
						}
					}else{
						pbar.getDialog().close();
					}
				},
				params : args
			})
		}
	}
})
