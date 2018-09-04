var addMortgageMachineInfo = function(piKey,personTypeParams,assuretypeidParams,refreshMortgageGridStore,businessType){
	var anchor = '100%';
	
	var showMachineinfoWin = function(){
		var topBar = new Ext.Toolbar({
		border : false,
		items : [{
			text : '保存',
			iconCls : 'btn-save',
			formBind : true,
			handler : function() {
				var mortgageName = Ext.getCmp('mortgageName').getValue();
				var personNameMortgage = Ext.getCmp('personNameMortgage').getValue();
				var enterpriseNameMortgage = Ext.getCmp('enterpriseNameMortgage').getValue();
				if(mortgageName == ""){
					Ext.ux.Toast.msg('状态','请输入<抵质押物名称>后再保存!');
				}else if(enterpriseNameMortgage == "" && personNameMortgage == ""){
					Ext.ux.Toast.msg('状态','请选择<所有权人>后再保存!');
				}else{
					panel_addMachineinfo.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							Ext.getCmp('win_addMachineinfo').destroy();
							Ext.getCmp('mortgage_add_win').destroy();
							refreshMortgageGridStore();
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存失败!');							
						}
					});
				}
			}
		}]
	})
		var panel_addMachineinfo = new Ext.form.FormPanel({
		id :'addMachineinfo',
		url : __ctxPath +'/credit/mortgage/addMachineinfo.do',
		monitorValid : true,
		bodyStyle:'padding:10px',
		autoScroll : true ,
		labelAlign : 'right',
		buttonAlign : 'center',
		frame : true ,
		border : false ,
		items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '填写<抵质押物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
				items : [{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '100%'},
					items : [{
		        		xtype : 'hidden',
		        		name : 'projectId',
		        		value : piKey
		        	},{
		        		xtype : 'hidden',
		        		name : 'procreditMortgage.businessType',
		        		value : businessType
		        	},{
						xtype : 'textfield',
						fieldLabel : '抵质押物类型',
						value : '机器设备',
						readOnly : true
					},{
						id : 'persontype_id',
						xtype : "dickeycombo",
						nodeKey : 'syrlx',
						hiddenName : "procreditMortgage.personType",
						fieldLabel : "所有人类型",
						itemName : '所有人类型', // xx代表分类名称
						value : personTypeParams,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							},
							'select' : function(combo,record, index){
								if(combo.getValue()==602){
									hideField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').disable() ;
									showField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').enable() ;
								}else{
									hideField(Ext.getCmp('enterpriseNameMortgage')) ;
									Ext.getCmp('enterpriseNameMortgage').disable() ;
									showField(Ext.getCmp('personNameMortgage')) ;
									Ext.getCmp('personNameMortgage').enable() ;
								}
							}
						}
					}]
				},{
					columnWidth : .5,
					labelWidth : 110,
					layout : 'form',
					defaults : {anchor : '95%'},
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'dblx',
						hiddenName : "procreditMortgage.assuretypeid",
						fieldLabel : "担保类型",
						itemName : '担保类型', // xx代表分类名称
						value : assuretypeidParams,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							}
						}
					},{
						xtype : "dickeycombo",
						nodeKey : 'bzfs',
						hiddenName : "procreditMortgage.assuremodeid",
						fieldLabel : "保证方式",
						itemName : '保证方式', // xx代表分类名称
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox
											.getValue());
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'enterpriseNameMortgage',
						xtype : 'combo',
						triggerClass :'x-form-search-trigger',
						hiddenName : 'customerEnterpriseName',
						fieldLabel : '<font color=red>*</font>所有权人',
						editable:false,
						onTriggerClick : function(){
							if(businessType=='Financing'){
								selectSlCompanyMain(selectSlCompanyMach);
							}else{
								selectEnterprise(selectCustomer);
							}
	                   },
						mode : 'romote',
						lazyInit : false,
						allowBlank : false,
						typeAhead : true,
						forceSelection :true,
						minChars : 1,
						listWidth : 230,
						store : getStoreByBusinessType(businessType,'enterprise'),
						displayField : businessType=="Financing"?'corName':'enterprisename',
						valueField : businessType=="Financing"?'companyMainId':'id',
						triggerAction : 'all'
					},{
						id : 'personNameMortgage',
						xtype : 'combo',
						hiddenName : 'customerPersonName',
						triggerClass :'x-form-search-trigger',
						editable:false,
						onTriggerClick : function(){
							//selectPWName(selectCustomer);
							if(businessType=='Financing'){
								selectSlPersonMain(selectSlPersonMach);
							}else{
								selectPWName(selectCustomer);
							}
		               	},
						fieldLabel : '<font color=red>*</font>所有权人',
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						minChars : 1,
						listWidth : 230,
						store : getStoreByBusinessType(businessType,'person'),
						displayField : 'name',
						valueField : businessType=="Financing"?'personMainId':'id',
						triggerAction : 'all'
					}]
				},{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'mortgageName',
						xtype : 'textfield',
						fieldLabel : '<font color=red>*</font>抵质押物名称',
						name : 'procreditMortgage.mortgagename',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						blankText : '为必填内容'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '最终估价.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.finalprice'
					},{
						fieldLabel : '可担保额度.万元',
						width : 90,
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgage.assuremoney'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 160,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						fieldLabel : '所有权人与借款人的关系',
						name : 'procreditMortgage.relation',
						maxLength : 50,
						maxLengthText : '最大输入长度50'
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 105,
					defaults : {anchor : '97.5%'},
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注',
						maxLength : 100,
						maxLengthText : '最大输入长度100',
						name : 'procreditMortgage.remarks'
					}]
				}]
			},{
			layout : 'column',
			xtype:'fieldset',
            title: '填写<机器设备>详细信息',
            collapsible: true,
            autoHeight:true,
            anchor : '95%',
            items : [{
				columnWidth : 1,
				labelWidth : 100,
				layout : 'form',
				defaults : {anchor : '97.5%'},
				items : [{
					xtype : 'textfield',
					fieldLabel : '设备名称',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'procreditMortgageMachineinfo.machinename'
				}]
            },{
				columnWidth : 1,
				labelWidth : 100,
				layout : 'form',
				defaults : {anchor : '97.5%'},
				items : [{
					xtype : 'textfield',
					fieldLabel : '控制权人',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'procreditMortgageMachineinfo.controller'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 100,
				defaults : {xtype : 'numberfield',anchor : '100%'},
				items : [{
					xtype : 'textfield',
					fieldLabel : '设备型号',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'procreditMortgageMachineinfo.machinetype'
				},{
					fieldLabel : '新货价格.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageMachineinfo.newcarprice'
				},{
					fieldLabel : '使用时间.年',
					maxLength : 11,
					maxLengthText : '最大输入长度11',
					name : 'procreditMortgageMachineinfo.havedusedtime'
				},{
					fieldLabel : '二手价值1.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageMachineinfo.secondaryvalueone'
				},{
					fieldLabel : '二手价值2.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageMachineinfo.secondaryvaluetow'
				},{
					fieldLabel : '模型估价.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageMachineinfo.modelrangeprice'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 110,
				defaults : {xtype : 'csRemoteCombo',anchor : '95%'},
				items : [{
					xtype : "dickeycombo",
					nodeKey : 'kzqfs',
					hiddenName : "procreditMortgageMachineinfo.controllertypeid",
					fieldLabel : "控制权方式",
					itemName : '控制权方式', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : "dickeycombo",
					nodeKey : 'tycd',
					hiddenName : "procreditMortgageMachineinfo.commongradeid",
					fieldLabel : "通用程度",
					itemName : '通用程度', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : "dickeycombo",
					nodeKey : 'xnzk',
					hiddenName : "procreditMortgageMachineinfo.capabilitystatusid",
					fieldLabel : "性能状况",
					itemName : '性能状况', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : "dickeycombo",
					nodeKey : 'bxnl',
					hiddenName : "procreditMortgageMachineinfo.cashabilityid",
					fieldLabel : "变现能力",
					itemName : '变现能力', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : "dickeycombo",
					nodeKey : 'djqk',
					hiddenName : "procreditMortgageMachineinfo.registerinfoid",
					fieldLabel : "登记情况",
					itemName : '登记情况', // xx代表分类名称
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox
										.getValue());
								combox.clearInvalid();
							})
						}
					}
				},{
					xtype : 'datefield',
					fieldLabel : '出厂日期',
					format     : 'Y-m-d',
					name : 'procreditMortgageMachineinfo.leavefactorydate'
				}]
            }]
		}],
		tbar : topBar
		
	});
	
	var window_addMachineinfo = new Ext.Window({
		id : 'win_addMachineinfo',
		title :'新增机器设备信息',
		iconCls : 'btn-add',
		width : (screen.width-180)*0.6,
		height : 460,
		constrainHeader : true ,
		collapsible : true, 
		frame : true ,
		border : false ,
		resizable : true,
		layout:'fit',
		autoScroll : true ,
		bodyStyle:'overflowX:hidden',
		constrain : true ,
		closable : true,
		modal : true,
		buttonAlign: 'right',
		items : [panel_addMachineinfo],
		listeners : {
			'beforeclose' : function(){
				if(panel_addMachineinfo.getForm().isDirty()){
					Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
						if(btn=='yes'){
							panel_addMachineinfo.getTopToolbar().items.itemAt(0).handler.call() ;
						}else{
							panel_addMachineinfo.getForm().reset() ;
							window_addMachineinfo.destroy() ;
						}
					}) ;
					return false ;
				}
			}
		}
	});
	
	window_addMachineinfo.show();
	}
	
	showMachineinfoWin();
	
	function selectCustomer(obj) {
		if (obj.shortname) {//企业
			Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
			Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		} else if (obj.name) {//个人
			Ext.getCmp('personNameMortgage').setValue(obj.id);
			Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		}
	}
	
	function selectSlCompanyMach(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonMach(obj){
		Ext.getCmp('personNameMortgage').setValue(obj.personMainId);
		Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
	}
	
	if(personTypeParams == 602){
		hideField(Ext.getCmp('personNameMortgage')) ;
		showField(Ext.getCmp('enterpriseNameMortgage')) ;
	}else if(personTypeParams == 603){
		hideField(Ext.getCmp('enterpriseNameMortgage')) ;
		showField(Ext.getCmp('personNameMortgage')) ;
	}
}