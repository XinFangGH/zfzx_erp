var addMortgageDroit = function(piKey,personTypeParams,assuretypeidParams,refreshMortgageGridStore,businessType){
	var anchor = '100%';
	var showDroitWin = function(){
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
					panel_addDroit.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							Ext.getCmp('win_addDroit').destroy();
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
		var panel_addDroit = new Ext.form.FormPanel({
		id :'addDroit',
		url : __ctxPath +'/credit/mortgage/addDroit.do',
		monitorValid : true,
		bodyStyle:'padding:10px',
		autoScroll : true ,
		labelAlign : 'right',
		buttonAlign : 'center',
		frame : true ,
		border : false,
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
						value : '无形权利',
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
								selectSlCompanyMain(selectSlCompanyDroit);
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
							if(businessType=='Financing'){
								selectSlPersonMain(selectSlPersonDroit);
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
            title: '填写<无形权利>详细信息',
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
					fieldLabel : '权利名称',
					maxLength : 50,
					maxLengthText : '最大输入长度50',
					name : 'procreditMortgageDroit.droitname'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 160,
				defaults : {xtype : 'numberfield',anchor : '100%'},
				items : [{
					fieldLabel : '享有权利比重.%',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageDroit.droitpercent'
				},{
					fieldLabel : '已经经营权利时间.年',
					maxLength : 11,
					maxLengthText : '最大输入长度11',
					name : 'procreditMortgageDroit.dealdroittime'
				},{
					fieldLabel : '享有权利剩余时间.年',
					maxLength : 11,
					maxLengthText : '最大输入长度11',
					name : 'procreditMortgageDroit.residualdroittime'
				},{
					fieldLabel : '最近一年权利净收益.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageDroit.droitlucre'
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 110,
				defaults : {xtype : 'csRemoteCombo',anchor : '95%'},
				items : [{
					xtype : "dickeycombo",
					nodeKey : 'ltx',
					hiddenName : "procreditMortgageDroit.negotiabilityid",
					fieldLabel : "流通性",
					itemName : '流通性', // xx代表分类名称
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
					nodeKey : 'jyzk',
					hiddenName : "procreditMortgageDroit.dealstatusid",
					fieldLabel : "经营状况",
					itemName : '经营状况', // xx代表分类名称
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
					nodeKey : 'qlzl',
					hiddenName : "procreditMortgageDroit.droitmassid",
					fieldLabel : "权利质量",
					itemName : '权利质量', // xx代表分类名称
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
					hiddenName : "procreditMortgageDroit.registerinfoid",
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
				}]
            },{
            	columnWidth : .5,
				layout : 'form',
				labelWidth : 100,
				defaults : {xtype : 'numberfield',anchor : '100%'},
				items : [{
					fieldLabel : '模型估价.万元',
					maxLength : 23,
					maxLengthText : '最大输入长度23',
					name : 'procreditMortgageDroit.modelrangeprice'
				}]
            }]
		}],
		tbar : topBar
	});
	
	var window_addDroit = new Ext.Window({
		id : 'win_addDroit',
		title :'新增无形权利信息',
		iconCls : 'btn-add',
		width : (screen.width-180)*0.6,
		height : 460,
		constrainHeader : true ,
		collapsible : true, 
		frame : true ,
		border : false ,
		//minHeight: 300,
		//minWidth: 580,
		resizable : true,
		layout:'fit',
		autoScroll : true ,
		bodyStyle:'overflowX:hidden',
		constrain : true ,
		closable : true,
		modal : true,
		buttonAlign: 'right',
		items : [panel_addDroit],
		listeners : {
			'beforeclose' : function(){
				if(panel_addDroit.getForm().isDirty()){
					Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
						if(btn=='yes'){
							panel_addDroit.getTopToolbar().items.itemAt(0).handler.call() ;
						}else{
							panel_addDroit.getForm().reset() ;
							window_addDroit.destroy() ;
						}
					}) ;
					return false ;
				}
			}
		}
	});
	window_addDroit.show();
	}
	
	showDroitWin();
	
	function selectCustomer(obj) {
		if (obj.shortname) {//企业
			Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
			Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		} else if (obj.name) {//个人
			Ext.getCmp('personNameMortgage').setValue(obj.id);
			Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		}
	}
	
	function selectSlCompanyDroit(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonDroit(obj){
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