/**
 * @author
 * @createtime
 * @class LoneExternalForm
 * @extends Ext.Window
 * @description PersonLoneExternalForm表单
 * @company 智维软件
 */
LoneExternalForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.type) != 'undefined') {
			this.type = _cfg.type;
			switch (this.type) {
				case 'add' :
					this.title = '增加外部借款登记';
					this.pic = 'btn-add'
					break;
				case 'edit' :
					this.title = '编辑外部借款登记';
					this.pic = 'btn-edit'
					break;
				case 'read' :
					this.title = '查看外部借款登记';
					this.pic = 'btn-readdocument'
					break;
			}
		} else {
			this.type = false;
		}
		// 必须先初始化组件
		this.initUIComponents();
	  LoneExternalForm.superclass.constructor.call(this, {
					id : 'LoneExternalForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 330,
					width : 650,
					maximizable : true,
					iconCls : this.pic,
					title : this.title,
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								hidden:this.isAllReadOnly,
								handler : this.save
							}, /*{
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								hidden:this.isAllReadOnly,
								handler : this.reset
							},*/ {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								hidden:this.isAllReadOnly,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var isReadOnly = false;
		if (null != this.isLook && this.isLook) {
			isReadOnly = this.isLook;
		}
		var object = this;
		this.formPanel = new Ext.FormPanel({
			bodyStyle : 'padding:10px 10px 10px 0',
			autoScroll : true,
			labelAlign : 'right',
			buttonAlign : 'center',
			layout : 'column',
			frame : true,
	//		scope : this,
			items : [{
										xtype : 'hidden',
										name : 'bpLoneExternal.customerType',
										value : this.customerType
										},this.customerType=='person_customer'?{
										columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :80,
										items : [{
										fieldLabel : '借款人',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										anchor : '100%',
										xtype : 'combo',
										editable : false,
										triggerClass : 'x-form-search-trigger',
										hiddenName : "bpLoneExternal.customerName",
										onTriggerClick : function() {
												var op=this.ownerCt.ownerCt.ownerCt;
													var selectCusNew = function(obj){
														op.getCmpByName('bpLoneExternal.customerId').setValue("");
													    op.getCmpByName('bpLoneExternal.cardnumber').setValue("");
													    op.getCmpByName('bpLoneExternal.customerName').setValue("");
														if(obj.id!=0 && obj.id!="")	
														op.getCmpByName('bpLoneExternal.customerId').setValue(obj.id);
														if(obj.name!=0 && obj.name!="")	
														op.getCmpByName('bpLoneExternal.customerName').setValue(obj.name);
														if(obj.cardnumber!=0 && obj.cardnumber!="")	
														op.getCmpByName('bpLoneExternal.cardnumber').setValue(obj.cardnumber);
													}
													selectPWName(selectCusNew);
										}
									},{
											xtype : 'hidden',
											name : 'bpLoneExternal.customerId',
											value : 0
										}]
									}:{
										columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :80,
										items : [{
										fieldLabel : '借款人',
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										anchor : '100%',
										xtype : 'combo',
										editable : false,
										triggerClass : 'x-form-search-trigger',
										hiddenName : "bpLoneExternal.customerName",
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
													op.getCmpByName('bpLoneExternal.customerId').setValue("");
												    op.getCmpByName('bpLoneExternal.cardnumber').setValue("");
												    op.getCmpByName('bpLoneExternal.customerName').setValue("");
												    if(obj.id!=0 && obj.id!="")	
													op.getCmpByName('bpLoneExternal.customerId').setValue(obj.id);
													if(obj.enterprisename!=0 && obj.enterprisename!="")	
													op.getCmpByName('bpLoneExternal.customerName').setValue(obj.enterprisename);
													if(obj.organizecode!=0 && obj.organizecode!="")	
													op.getCmpByName('bpLoneExternal.cardnumber').setValue(obj.organizecode);
												}

											selectEnterprise(EnterpriseNameStockUpdateNew);
										}
									},{
											xtype : 'hidden',
											name : 'bpLoneExternal.customerId',
											value : 0
										}]
									},{
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 100,
				border : false,
				hidden : false,
				scope : this,
				items : [{
							fieldLabel : '证件号码',
							allowBlank : true,
							name : 'bpLoneExternal.cardnumber',
//							id : 'branch',
							maxLength : 100,
							xtype : 'textfield',
							readOnly : true,
							anchor : "96%"
						}]
			}, {
				columnWidth : .45, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 80,
				border : false,
				
				scope : this,
				items : [{
					columnWidth :1, // 该列有整行中所占百分比
					layout:"form",
					border : false,
					items:[{
						xtype : 'textfield',
						fieldLabel : '借款金额',
						name : 'loneMoney',
						maxLength : 100,
						allowNegative: false, // 允许负数 
					    style: {imeMode:'disabled'},
						blankText : "借款金额不能为空，请正确填写!",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor:'96%',
						listeners : {
					    	scope:this,
							afterrender : function(obj) {
							    obj.on("keyup")
							},
							change  :function(nf) {
								var value= nf.getValue();
								{
									nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									this.getCmpByName("bpLoneExternal.loneMoney").setValue(value);
								}
							}
					    }
					},{
					    xtype : "hidden",
						name : "bpLoneExternal.loneMoney"
					}]
				}]
			},{
										 columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :18,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
													
								 }]} ,{
				columnWidth : .45, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 100,
				border : false,
				
				scope : this,
				items : [{
					columnWidth :1, // 该列有整行中所占百分比
					layout:"form",
					border : false,
					items:[{
						xtype : 'textfield',
						fieldLabel : '借款余额',
						name : 'onLoneMoney',
						maxLength : 100,
						allowNegative: false, // 允许负数 
					    style: {imeMode:'disabled'},
						blankText : "借款余额不能为空，请正确填写!",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor:'96%',
						listeners : {
					    	scope:this,
				        	afterrender : function(obj) {
								obj.on("keyup")
							
							},
							change  :function(nf) {
								var value= nf.getValue();
								{
									nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									this.getCmpByName("bpLoneExternal.onLoneMoney").setValue(value);
								}
							
							}
					    }
					},{
					    xtype : "hidden",
						name : "bpLoneExternal.onLoneMoney"
					}]
				}]
			},{
										 columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth :18,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
													
								 }]},{
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 80,
				border : false,
				hidden : false,
				scope : this,
				items : [{
							fieldLabel : '借款单位',
							allowBlank : true,
							name : 'bpLoneExternal.loneCompany',
//							id : 'branch',
							maxLength : 100,
							xtype : 'textfield',
							readOnly : false,
							anchor : "100%"
						}]
			},{
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 100,
				border : false,
				hidden : false,
				scope : this,
				items : [{
							fieldLabel : '累计逾期天数',
							allowBlank : true,
							name : 'bpLoneExternal.overdueDays',
//							id : 'branch',
							maxLength : 100,
							xtype : 'textfield',
							readOnly : false,
							anchor : "96%"
						}]
			},{
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 80,
				border : false,
				items : [ {
										   fieldLabel : '开始日期',
											name : 'bpLoneExternal.startDate',
											allowBlank : false,
										   readOnly : this.isAllReadOnly,
											 format : 'Y-m-d',
											xtype : 'datefield',
											anchor : '100%'
										}]

			},{
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 100,
				border : false,
				items : [ {
										   fieldLabel : '结束日期',
											name : 'bpLoneExternal.intentDate',
											allowBlank : false,
										  readOnly : this.isAllReadOnly,
											 format : 'Y-m-d',
											xtype : 'datefield',
											anchor : '96%'
										}]

			},{
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 80,
				border : false,
				items : [ {
								fieldLabel : "贷款状态",
								xtype : "dickeycombo",
								hiddenName : 'bpLoneExternal.projectStatus',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款状态',
								nodeKey : 'loneExternalStauts',	
								emptyText : "请选择",
								editable : false,
								anchor : "100%",
								allowBlank : false,
									listeners : {
								scope:this,
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
				columnWidth : .5, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 100,
				border : false,
				items : [ {
						xtype : "combo",
						fieldLabel : "业务类型",
						anchor : '96%',
						hiddenName : "bpLoneExternal.businessType",
						allowBlank : false,
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/creditFlow/getBusinessTypeListAllCreditProject.do',
							fields : ['itemId', 'itemName']
						}),
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
					             combox.setValue(combox.getValue());
								})
								combox.clearInvalid();
							}
						}
					}]

			},{
				columnWidth : 1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 80,
				border : false,
				items : [{
							fieldLabel : '备注',
							name : 'bpLoneExternal.remarks',
							xtype : 'textarea',
							maxLength : 500,
							height:90,
							readOnly : this.isAllReadOnly,
							anchor : "100%"
						}]

			},{
				name : 'bpLoneExternal.externalId',
				xtype : 'hidden'
			}]
		});
		// 加载表单对应的数据
		if (this.externalId != null && this.externalId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/riskControl/creditInvestigation/getBpLoneExternal.do?externalId='
						+ this.externalId,
				root : 'data',
				preName : 'bpLoneExternal',
				scope:this,
				success : function(response, options) {
					var result = Ext.decode(response.responseText);
					this.getCmpByName('loneMoney').setValue(Ext.util.Format.number(result.data.loneMoney,'0,000.00'));
					this.getCmpByName('onLoneMoney').setValue(Ext.util.Format.number(result.data.onLoneMoney,'0,000.00'));
				},
				failure : function() {
					Ext.ux.Toast.msg('操作提示', '对不起，数据加载失败！');
				}
			});
		}
	},// end of the initcomponents

	/**
	 * 自定义方法,设置控件隐藏
	 * @param {}
	 * formPanel
	 */

	/**
	 * 自定义方法,设置控件显示
	 * @param {}
	 * formPanel

	//获取下拉列表中显示的文本值
	getComboDisplay: function (combo) {    
		var value = combo.getValue();   
		var valueField = combo.valueField;    
		var record;    
		combo.getStore().each(function(r){   
			if(r.data[valueField] == value){            
				record = r;            
				return false;        
			}   
		});    
		return record ? record.get(combo.displayField) : null;
	},
	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/riskControl/creditInvestigation/saveBpLoneExternal.do',
					callback : function(fp, action) {
						if (this.listPanel != null) {
							this.listPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});