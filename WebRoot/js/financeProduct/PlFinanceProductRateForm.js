/**
 * @author 
 * @createtime 
 * @class PlFinanceProductRateForm
 * @extends Ext.Window
 * @description PlFinanceProductRateForm表单
 * @company 互融软件
 */
	
PlFinanceProductRateForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlFinanceProductRateForm.superclass.constructor.call(this, {
					id : 'PlFinanceProductRateForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height :300,
					width : 600,
					maximizable : true,
					title : '设置理财专户产品年化利率',
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
		//var productId=null
		var lastDay=function(productId,formPanel){
			if(productId!=null&&productId!=""){
				Ext.Ajax.request({
						url : __ctxPath + "/financeProduct/lastDatePlFinanceProductRate.do?productId="+productId,
						method : 'POST',
						scope:this,
						success : function(response, request) {
							var obj = Ext.util.JSON.decode(response.responseText);
							var lastDays=obj.date;
							formPanel.getCmpByName("firstDay").setMinValue(lastDays);
							formPanel.getCmpByName("firstDay").setValue(lastDays);
							formPanel.getCmpByName("endDay").setMinValue(lastDays);
						},
						failure : function(response,request) {
							Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
						}
					});
			}else{
				return new Date();
			}
		}
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
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >提示：支持批量设置，从执行开始日期到执行结束日期按同一利率批量生成产品年化利率</font>"
										}]
								},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{					
												fieldLabel : '产品名称',
												hiddenName : 'productId',
												allowBlank : false,
												readOnly : this.isReadOnly,
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
													name : "firstDay",
													allowBlank : false,
													readOnly : true,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d',
													minValue :new Date(),
													value:new Date()
												}]
									},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "执行结束日期",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "endDay",
													allowBlank : false,
													blankText : "执行结束日期不能为空，请正确填写!",
													anchor : "100%",
													minValue :new Date(),
													format : 'Y-m-d'
												}]
									},{
						            	columnWidth:0.95,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '年化利率',
												name : 'yearRate',
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
						+ '/creditFlow/customer/cooperation/getCsCooperationEnterprise.do?id='
						+ this.id,
				root : 'data',
				preName : 'csCooperationEnterprise'
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
					+ '/financeProduct/updatePlFinanceProductRate.do',
			callback : function(fp, action) {
				if(gridPanel!=null){
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});


 
