var addMortgageCar = function(piKey,personTypeParams,assuretypeidParams,refreshMortgageGridStore,businessType,isAllow){
	var anchor = '100%';
	var showMortgageCarWin = function(){
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
						
						if(isAllow){
							var factoryName = Ext.getCmp('factoryName').getValue();
							if(factoryName == ""){
								Ext.ux.Toast.msg('状态','请选择<制造商>后再保存!');
								return;
							}
						}
						
						
						if(mortgageName == ""){
							Ext.ux.Toast.msg('状态','请输入<抵质押物名称>后再保存!');
						}else if(enterpriseNameMortgage == "" && personNameMortgage == ""){
							Ext.ux.Toast.msg('状态','请选择<所有权人>后再保存!');
						}/*else if(factoryName == ""){
							Ext.ux.Toast.msg('状态','请选择<制造商>后再保存!');
						}*/else{
							panel_addCar.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form, action) {
									Ext.ux.Toast.msg('操作信息', '保存成功!');
									Ext.getCmp('win_addCar').destroy();
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
			});
			var panel_addCar = new Ext.form.FormPanel({
			id :'addVehicle',
			url : __ctxPath +'/credit/mortgage/addVehicle.do',
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
						value : '车辆',
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
								selectSlCompanyMain(selectSlCompanyVehicle);
							}else{
								selectEnterprise(selectCustomer);
							}
	                   },
						mode : 'romote',
						lazyInit : true,
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
								selectSlPersonMain(selectSlPersonVehicle);
							}else{
								selectPWName(selectCustomer);
							}
		               	},
						fieldLabel : '<font color=red>*</font>所有权人',
						mode : 'romote',
						lazyInit : true,
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
	            title: '填写<车辆>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
					columnWidth : 1,
					labelWidth : 105,
					layout : 'form',
					defaults : {anchor : '97.5%'},
					items : [{
						id : 'factoryName',
						xtype : 'combo',
						hiddenName : 'manufacturer',
						triggerClass :'x-form-search-trigger',
						onTriggerClick : function(){
							selectCarList(returnCarMessageInfo);
		               	},
						fieldLabel : '制造商',
						mode : 'romote',
						lazyInit : false,
						typeAhead : true,
						//allowBlank : false,
						blankText : '为必填内容',
						minChars : 1,
						listWidth : 230,
						store : new Ext.data.JsonStore({
							url : __ctxPath +'/credit/mortgage/ajaxQueryCarFactoryForCombo.do',
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'carFirmName'
									}],
							listeners : {
								'load' : function(s,r,o){
									if(s.getCount()==0){
										Ext.getCmp('factoryName').markInvalid('没有查找到匹配的记录') ;
									}
								}
							}
						}),
						displayField : 'carFirmName',
						valueField : 'id',
						triggerAction : 'all'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 105,
					defaults : {xtype : 'textfield',anchor : '100%'},
					items : [{
						id : 'carNumber',
						fieldLabel : '车型编号',
						readOnly : true
					},{
						id : 'carStyleName',
						fieldLabel : '车系',
						readOnly : true
					},{
						id : 'carModelName',
						fieldLabel : '车型',
						readOnly : true
					},{
						fieldLabel : '发动机号',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageCar.engineNo'
					},{
						fieldLabel : '车架号',
						maxLength : 50,
						maxLengthText : '最大输入长度50',
						name : 'procreditMortgageCar.vin'
					},{
						xtype : 'numberfield',
						fieldLabel : '新车价格.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.carprice'
					},{
						xtype : 'numberfield',
						fieldLabel : '里程数.公里',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.totalkilometres'
					},{
						xtype : 'numberfield',
						fieldLabel : '事故次数',
						maxLength : 11,
						maxLengthText : '最大输入长度11',
						name : 'procreditMortgageCar.accidenttimes'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {xtype : 'textfield',anchor : '95%'},
					items : [{
						id : 'displacement',
						fieldLabel : '排量',
						readOnly : true
					},{
						id : 'configuration',
						fieldLabel : '配置',
						readOnly : true
					},{
						id : 'seating',
						fieldLabel : '座位数',
						readOnly : true
					},{
						xtype : "dickeycombo",
						nodeKey : 'clys',
						hiddenName : "procreditMortgageCar.carColor",
						fieldLabel : "车辆颜色",
						itemName : '车辆颜色', // xx代表分类名称
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
						hiddenName : "procreditMortgageCar.enregisterinfoid",
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
						name : 'procreditMortgageCar.leavefactorydate'
					},{
						xtype : 'numberfield',
						fieldLabel : '使用时间.年',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.haveusedtime'
					},{
						xtype : "dickeycombo",
						nodeKey : 'ck',
						hiddenName : "procreditMortgageCar.carinfoid",
						fieldLabel : "车况",
						itemName : '车况', // xx代表分类名称
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
					labelWidth : 120,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '市场交易价格1.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.exchangepriceone'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 110,
					defaults : {xtype : 'numberfield',anchor : '95%'},
					items : [{
						fieldLabel : '模型估价.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.modelrangeprice'
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 120,
					defaults : {xtype : 'numberfield',anchor : '100%'},
					items : [{
						fieldLabel : '市场交易价格2.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgageCar.exchangepricetow'
					}]
	            }]
			}],
			tbar : topBar
		});
		
		var window_add = new Ext.Window({
			id : 'win_addCar',
			title :'新增车辆信息',
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
			items : [panel_addCar],
			listeners : {
				'beforeclose' : function(){
					if(Ext.getCmp('addVehicle').getForm().isDirty()){
						Ext.Msg.confirm('操作提示','是否保存当前新添加的数据?',function(btn){
							if(btn=='yes'){
								Ext.getCmp('addVehicle').getTopToolbar().items.itemAt(0).handler.call() ;
							}else{
								Ext.getCmp('addVehicle').getForm().reset() ;
								Ext.getCmp('win_addCar').destroy() ;
							}
						}) ;
						return false ;
					}
				}
			}
		});
		if(isAllow){
			window_add.findById('factoryName').fieldLabel = "<font color=red>*</font>制造商";
			window_add.findById('factoryName').allowBlank = true;
			window_add.doLayout();
			window_add.show();
		}else{
			window_add.show();
		}
	};
	
	showMortgageCarWin();
	
	function selectCustomer(obj) {
		if (obj.shortname) {//企业
			Ext.getCmp('enterpriseNameMortgage').setValue(obj.id);
			Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.enterprisename) ;
		} else if (obj.name) {//个人
			Ext.getCmp('personNameMortgage').setValue(obj.id);
			Ext.getCmp('personNameMortgage').setRawValue(obj.name) ;
		}
	}
	
	//汽车参照
	var returnCarMessageInfo = function(obj){
		Ext.getCmp('factoryName').setValue(obj.id);//制造商id
		Ext.getCmp('factoryName').setRawValue(obj.carFirmName);
		
		Ext.getCmp('carNumber').setValue(obj.carNumber);//车型编号
		Ext.getCmp('carStyleName').setValue(obj.carStyleName);//车系
		Ext.getCmp('carModelName').setValue(obj.carModelName);//车型
		Ext.getCmp('displacement').setValue(obj.displacementValue);//排量
		Ext.getCmp('configuration').setValue(obj.configuration);//配置
		Ext.getCmp('seating').setValue(obj.seatingValue);//座位数
	}
	
	function selectSlCompanyVehicle(obj) {
		Ext.getCmp('enterpriseNameMortgage').setValue(obj.companyMainId);
		Ext.getCmp('enterpriseNameMortgage').setRawValue(obj.corName) ;
	}
	
	function selectSlPersonVehicle(obj){
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
