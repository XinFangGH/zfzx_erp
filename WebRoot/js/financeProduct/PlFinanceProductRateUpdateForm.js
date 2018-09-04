//PlFinanceProductRateUpdateForm.js
PlFinanceProductRateUpdateForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlFinanceProductRateUpdateForm.superclass.constructor.call(this, {
					id : 'PlFinanceProductRateUpdateForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height :200,
					width : 400,
					maximizable : true,
					title : '更新理财专户产品年化利率',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},/*
								 * { text : '重置', iconCls : 'btn-reset', scope :
								 * this, handler : this.reset },
								 */{
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var leftlabel=180;
		var rightlabel=180;

		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					autoScroll : true,
					frame : true,
					anchor : '100%',
					labelAlign : 'right',
					defaults : {
						anchor : '96%',
						labelWidth : 80
					},
					// id : 'CsCooperationEnterpriseForm',
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
								xtype:'hidden',
								name:'plFinanceProductRate.id'
						},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{					
												fieldLabel : '产品名称',
												hiddenName : 'plFinanceProductRate.productId',
												allowBlank : false,
												readOnly : true,
												anchor : '100%',
												xtype : "combo",
												nodeKey : 'couponType',
												editable : false,
												blankText : "产品名称要求不能为空，请正确填写!",
												anchor : "100%",
												displayField : 'itemName',
												valueField : 'itemId',
												triggerAction : 'all',
												store : new Ext.data.SimpleStore({
													autoLoad : true,
													url : __ctxPath
															+ '/financeProduct/getListPlFinanceProduct.do',
													fields : ['itemId', 'itemName']
												}),
												listeners : {
													scope:this,
													afterrender : function(combox) {
														var st = combox.getStore();
														st.on("load", function() {
																	var record = st.getAt(0);
																	var v = record.data.itemId;
																	combox.setValue(v);
																	combox.fireEvent("select",
																			combox, record, 0);
																})
														combox.clearInvalid();
													},
													select : function(combox, record, index) {
															var v = record.data.itemId;
															lastDay(v,this.formPanel);
															
														}}
										}]
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "执行开始日期",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "plFinanceProductRate.intentDate",
													allowBlank : false,
													readOnly : true,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d',
													minValue :new Date(),
													value:new Date()
												}]
									},{
						            	columnWidth:0.95,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '年化利率',
												name : 'plFinanceProductRate.yearRate',
												allowBlank : false,
												anchor : '100%',
												blankText : '年化利率'
											}]
					            	},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									}]
					}]
				});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/financeProduct/getPlFinanceProductRate.do?id='
						+ this.id,
				root : 'data',
				preName : 'plFinanceProductRate'
			});
		}

	},// end of the initcomponents

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
		var gridPanel=this.gridPanel
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/financeProduct/savePlFinanceProductRate.do',
			callback : function(fp, action) {
				if(gridPanel!=null){
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});
