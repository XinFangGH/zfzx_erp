AddLeaseObjectWin = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	isManageWin: false,
	onlyFile:false,//一旦为true则只显示材料相关fieldset  by gao
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.isManageWin)!="undifined"){
			this.isManageWin = _cfg.isManageWin;
		}
		if(typeof(_cfg.onlyFile)!="undifined"){
			this.onlyFile = _cfg.onlyFile;
		}
/*		var jsArr = [
//			__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/LeaseFinanceSuppliorInfo.js',//供货方信息
			__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/selectSupplior.js'//供货方信息
		];*/
//		$ImportSimpleJs(jsArr, this.initUIComponents, this);
		this.initUIComponents();
		AddLeaseObjectWin.superclass.constructor.call(this, {
					buttonAlign : 'center',
					title : '租赁标的信息',
					iconCls : 'btn-add',
					width : (screen.width - 180) * 0.6,
					height : 460,
					constrainHeader : true,
					collapsible : true,
					frame : true,
					border : false,
					resizable : true,
					layout : 'fit',
					//autoScroll : true,
					//bodyStyle : 'overflowX:hidden',
					constrain : true,
					closable : true,
					modal : true,
					maximizable : true,
					items : [this.formPanel],
					buttons : [{
								text : '保存',
								iconCls : 'btn-add',
								scope : this,
								handler : this.saveNotClose
							},{
								text : '提交',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : function() {
									this.close();
								}
							}]

				})
	},
	initUIComponents : function() {
		 this.uploads = new LeaseFinanceUploads({
		    	projectId : typeof(this.objectId)=='undefined'?-1:this.objectId,//新增的情况下 标的物id不存在   没有关联
		    	isHidden : false,
		    	businessType :'LeaseFinanceObject',//LeaseFinanceObject租赁标的物的特殊type
		    	isNotOnlyFile : false,
		    	isHiddenColumn : false,
		    	isDisabledButton : false,
		    	hiddenUpBtn:this.onlyFile,//只查看文件详情的话，不允许上传   add  by gao
//		    	setname :'担保责任解除函',
//		    	titleName :'担保责任解除函',
//		    	tableName :'typeisdbzrjchsmj',
		    	uploadsSize :10,
		    	isHiddenOnlineButton :true,
		    	isDisabledOnlineButton :true
		});
		
		this.suppliorInfo = new  LeaseFinanceSuppliorInfo();
		this.LeaseObjectInsuranceInfo = new LeaseObjectInsuranceInfo({projectId:this.objectId});
		this.formPanel = new Ext.form.FormPanel({
			url : __ctxPath	+ '/creditFlow/leaseFinance/project/saveFlLeaseobjectInfo.do',
			monitorValid : true,
			bodyStyle : 'padding:10px',
			autoScroll : true,
			id:'leaseObjectForm',
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true,
			border : false,
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '租赁标的信息',
				autoHeight : true,
				hidden:this.onlyFile,
				anchor : '95%',
				items : [{
							columnWidth : .45,
							layout : 'form',
							labelWidth : 105,
							defaults : {
								anchor : '100%'
							},
							items : [{
										name : 'flLeaseobjectInfo.id',
										xtype : 'hidden',
										value : this.objectId == null ? '' : this.objectId
									},{
										name : 'projectId',
										xtype : 'hidden',
										value : this.projectId
									},{
										xtype : 'textfield',
										fieldLabel : '租赁标的名称',
										name : 'flLeaseobjectInfo.name',
										allowBlank : false
									}]
						}, {
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : " ",
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
										xtype:'textfield',
										fieldLabel : '规格型号',
										name : 'flLeaseobjectInfo.standardSize',
										allowBlank : false,
										maxLength : 255
									}
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
											xtype : 'numberfield',
											fieldLabel : '原价格',
											name : 'flLeaseobjectInfo.originalPrice',
											allowBlank : false
								}
							},{
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : "元 ",
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
										xtype : 'numberfield',
										fieldLabel : '认购价格',	
								 		name : 'flLeaseobjectInfo.buyPrice',
										allowBlank : false
										}
							},{
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : "元 ",
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
								columnWidth : .45,
								layout : 'form',
								labelWidth : 105,
								defaults : {
									anchor : '100%'
								},
								items:{
										xtype : 'numberfield',
										fieldLabel : '使用年限',
										name : 'flLeaseobjectInfo.useYears',
										allowBlank : true
									}
							},{
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : '年 ',
											labelSeparator : '',
											anchor : "100%"
										}]
							},{
							columnWidth : .45,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [ {
										xtype : 'numberfield',
										fieldLabel : '数量',	
								 		name : 'flLeaseobjectInfo.objectCount',
										allowBlank : true}]
						},  {
							columnWidth : .45,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'textfield',
								fieldLabel : '所有人',
								name : 'flLeaseobjectInfo.owner',
								allowBlank : false
							}]
						}, {
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : " ",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
							columnWidth : .45,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'datefield',
								fieldLabel : '购入日期',
								format : 'Y-m-d',
								name : 'flLeaseobjectInfo.buyDate'
							}]
						}, {
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : 20,
								items : [{
											fieldLabel : " ",
											labelSeparator : '',
											anchor : "100%"
										}]
							}, {
							columnWidth : 1,
							layout : 'form',
							labelWidth : 105,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : 'textarea',
										fieldLabel : '备注',
										maxLength : 100,
										maxLengthText : '最大输入长度100',
										name : 'flLeaseobjectInfo.objectComment'
									}]
						}]
			},{
				xtype : 'fieldset',
				title : '供货方信息',
				autoHeight : true,
				anchor : '95%',
				hidden:this.onlyFile,
				items:[this.suppliorInfo]
			},{
				xtype : 'fieldset',
				title : '租赁物保险信息',
				name:'leaseObjectInsurance',
				autoHeight : true,
				hidden:this.onlyFile,
				anchor : '95%',
				items:[this.LeaseObjectInsuranceInfo]
			},{
						xtype : 'fieldset',
						title : '租赁物材料清单',
						collapsible : true,
						autoHeight : true,
						name:'zeren',
						anchor : '95%',
						items : [this.uploads]
				},this.isManageWin?
					{// 默认 isManageWin  为false  ，不显示 办理表单
					xtype : 'fieldset',
					title : '租赁办理信息',
					collapsible : true,
					autoHeight : true,
					layout : 'column',
					labelWidth : 105,
					anchor : '95%',
					name:'manageInfo',
					items : [{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 105,
						items : [{xtype:"hidden",name:"flLeaseobjectInfo.managePersonId"},{
							fieldLabel : "经办人",
							xtype : "combo",
							allowBlank : false,
							editable : false,
							triggerClass : 'x-form-search-trigger',
							itemVale : creditkindDicId, // xx代表分类名称
							hiddenName : "appUsersOfC",
							readOnly : false,
						    anchor : "100%",
						    onTriggerClick : function(cc) {
							     var obj = this;
							     var appuerIdObj=obj.previousSibling();
								 new UserDialog({
								 	userIds:appuerIdObj.getValue(),
								 	userName:obj.getValue(),
									single : true, //暂定不支持  多个办理人 ，后台代码没有区分，  但是  字段为string类型，可拓展 add by gao
									title:"经办人",
									callback : function(uId, uname) {
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
							}
					}]
				}, {
							columnWidth : .5,
							labelWidth : 105,
							layout : 'form',
							defaults : {
								anchor : '100%'
							},
							items : [{
								xtype : 'datefield',
								fieldLabel : '办理时间',
								format : 'Y-m-d',
								name : 'flLeaseobjectInfo.manageDate'
							}]},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 105,
							defaults : {
								anchor : '100%'
							},
							items : [{
										xtype : 'textarea',
										fieldLabel : '备注说明',
										maxLength : 100,
										maxLengthText : '最大输入长度100',
										name : 'flLeaseobjectInfo.manageComment'
									}]
						}]
				}
				:
				{}
			/*, {
				anchor : '95%',
				items : [this.otherPanel]
			}, {
				anchor : '95%',
				name : 'otherInfo',
				items : [new LeaseFinanceMaterialsView({
							projectId : this.projectId,
							isHidden_materials : false,
							businessType : this.businessType
						})]
			}*/]
		})
		
		
		//加载表单对应的数据	   -----test   ok  by gaoqingrui
		if (this.objectId != null && this.objectId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath	+ '/creditFlow/leaseFinance/project/getFlLeaseobjectInfo.do?id='
						+ this.objectId,
				root : 'data',
				success:function(response, options){
					var respText = response.responseText;  
					var alarm_fields = Ext.util.JSON.decode(respText); 
					var managePersonName= alarm_fields.data.flLeaseobjectInfo.managePersonName;
					if(""!=managePersonName &&  null!=managePersonName){
						this.getCmpByName('appUsersOfC').setValue(managePersonName);
					}
					/*if(""!=appUserId &&  null!=appUserId){
					   this.getCmpByName('degree').setValue(appUserId);
					   this.getCmpByName('degree').setRawValue(appUsers);
					   this.getCmpByName('degree').nextSibling().setValue(appUserId);
					}*/
				},
				preName : ['flLeaseobjectInfo','flObjectSupplior']
			});
		}

/*		if (null != this.id && null != this.type) {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/credit/mortgage/getMortgageByType.do?mortgageid='
								+ this.id,
						root : 'data',
						preName : ['procreditMortgage', 'assureofname'],
						scope : this,
						success : function(resp, options) {
							var obj = Ext.util.JSON.decode(resp.responseText)
							Ext
									.getCmp('enterpriseNameMortgage')
									.setValue(obj.data.procreditMortgage.assureofname)
							Ext.getCmp('enterpriseNameMortgage')
									.setRawValue(obj.data.assureofname)

						}
					});
		}
	}*/
	this.save = function() {
           var eg=this.getCmpByName('leaseObjectInsurance').get(0).get(1);
           vDates=getLeaseInsuranceGridDate(eg);
           if(vDates!=""){
              var arrStr=vDates.split("@");
			  for(var i=0;i<arrStr.length;i++){
				  var str=arrStr[i];
				  var object = Ext.util.JSON.decode(str)
				 if(object.insuranceName==""){
					 Ext.ux.Toast.msg('操作信息','保险名称不能为空，请填写保险名称');
					 return;
				 }
				 if(object.owner==""){
					 Ext.ux.Toast.msg('操作信息','所有人不能为空，请填写所有人姓名');
					 return;
				 }
				  if(object.insuranceCompanyName==""){
						 Ext.ux.Toast.msg('操作信息','保险公司名称不能为空，请填写保险公司名称');
						 return;
					 }
				if(object.insuranceCode==""){
						 Ext.ux.Toast.msg('操作信息','保险编号不能为空，请填写保险公司名称');
						 return;
					 }
				if(object.insurancePerson==""){
						 Ext.ux.Toast.msg('操作信息','保险受益人不能为空，请填写保险受益人名称');
						 return;
					 }
			  }
		  }
		win = this;
		
		var gridPanel = this.gridPanel;
	/*	var mortgagenametypeid = this
				.getCmpByName('procreditMortgage.mortgagenametypeid')
				.getValue();
		var customerName = this.getCmpByName('customerEnterpriseName')
				.getValue();

		 * if(mortgagenametypeid==1){ var factoryName =
		 * Ext.getCmp('factoryName').getValue(); if(factoryName == ""){
		 * Ext.ux.Toast.msg('状态','请选择<制造商>后再保存!'); return; } }else
		 if (mortgagenametypeid == 2) {
			var targetEnterpriseName = Ext.getCmp('targetEnterpriseName')
					.getValue();
			if (targetEnterpriseName == "") {
				Ext.ux.Toast.msg('状态', '请选择<目标公司名称>后再保存!');
				return;
			}
		} else if (mortgagenametypeid == 4) {
			var card_number = Ext.getCmp('card_number').getValue();
			if (card_number == "") {
				Ext.ux.Toast.msg('状态', '<证件号码>不能为空!');
				return;
			}
		}
		if (customerName == '') {
			Ext.ux.Toast.msg('状态', '请选择<所有权人>后再保存!');
			return;
		} else {*/
		if(this.formPanel.getForm().isValid()){
			this.formPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							win.destroy();
							gridPanel.getStore().reload()
						},
						params : {
							"flLeaseFinanceInsuranceInfo" : vDates
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存失败,请正确填写表单信息!');
						}
					});
		}
	};
		this.saveNotClose = function() {
           var eg=this.getCmpByName('leaseObjectInsurance').get(0).get(1);
           vDates=getLeaseInsuranceGridDate(eg);
           if(vDates!=""){
              var arrStr=vDates.split("@");
			  for(var i=0;i<arrStr.length;i++){
				  var str=arrStr[i];
				  var object = Ext.util.JSON.decode(str)
				 if(object.insuranceName==""){
					 Ext.ux.Toast.msg('操作信息','保险名称不能为空，请填写保险名称');
					 return;
				 }
				  if(object.insuranceCompanyName==""){
						 Ext.ux.Toast.msg('操作信息','保险公司名称不能为空，请填写保险公司名称');
						 return;
					 }
				if(object.insuranceCode==""){
						 Ext.ux.Toast.msg('操作信息','保险编号不能为空，请填写保险公司名称');
						 return;
					 }
				if(object.insurancePerson==""){
						 Ext.ux.Toast.msg('操作信息','保险受益人不能为空，请填写保险受益人名称');
						 return;
					 }
			  }
		  }
		win = this;
		var gridPanel = this.gridPanel;
		if(this.formPanel.getForm().isValid()){
		this.formPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form, action) {
							var objId = action.result.objectId;
							Ext.getCmp('leaseObjectForm').getCmpByName('flLeaseobjectInfo.id').setValue(action.result.objectId);
							/*更新保险grid*/
							Ext.getCmp('leaseObjectForm').remove(Ext.getCmp('leaseObjectForm').items.get(2),true);
							Ext.getCmp('leaseObjectForm').doLayout();
							var fileSet1=new Ext.form.FieldSet({
								title : '租赁物保险信息',
								name:'leaseObjectInsurance',
								autoHeight : true,
								hidden:this.onlyFile,
								anchor : '95%',
								items:[new LeaseObjectInsuranceInfo({projectId:objId})]
							})
							Ext.getCmp('leaseObjectForm').insert(2, fileSet1);
							/*更新材料grid*/
							Ext.getCmp('leaseObjectForm').remove(Ext.getCmp('leaseObjectForm').items.get(3),true);
							Ext.getCmp('leaseObjectForm').doLayout();
							var fileSet=new Ext.form.FieldSet({
								 	anchor:'95%',
			  						name:'guaranteeInfo',
						            title: '抵质押材料',
						            collapsible: true,
						            autoHeight:true,
						            items:[new LeaseFinanceUploads({
								    	projectId : objId,//新增的情况下 标的物id不存在   没有关联
								    	isHidden : false,
								    	businessType :'LeaseFinanceObject',//LeaseFinanceObject租赁标的物的特殊type
								    	isNotOnlyFile : false,
								    	isHiddenColumn : false,
								    	isDisabledButton : false,
								    	hiddenUpBtn:this.onlyFile,//只查看文件详情的话，不允许上传   add  by gao
						//		    	setname :'担保责任解除函',
						//		    	titleName :'担保责任解除函',
						//		    	tableName :'typeisdbzrjchsmj',
								    	uploadsSize :10,
								    	isHiddenOnlineButton :true,
								    	isDisabledOnlineButton :true
								})]
							})
							Ext.getCmp('leaseObjectForm').insert(3, fileSet);
							Ext.getCmp('leaseObjectForm').doLayout();
							
							
							
							Ext.ux.Toast.msg('操作信息', '保存成功!');
							gridPanel.getStore().reload()
						},
						params : {
							"flLeaseFinanceInsuranceInfo" : vDates
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('操作信息', '保存失败,请正确填写表单信息!');
						}
					});
		}
	}
}
});
