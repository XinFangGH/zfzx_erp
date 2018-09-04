/**
 * @author 
 * @createtime 
 * @class PlFinanceProductRateForm
 * @extends Ext.Window
 * @description PlFinanceProductRateForm表单
 * @company 互融软件
 */
	
PlFianceProductIntentDay= Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlFianceProductIntentDay.superclass.constructor.call(this, {
			        id:'PlFianceProductIntentDay',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height :300,
					width : 600,
					maximizable : true,
					title : '补派产品收益',
					buttonAlign : 'center',
					buttons : [{
								text : '确认',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},{
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
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "补派息日期",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "days",
													allowBlank : false,
													readOnly : false,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d',
													maxValue :new Date()
												}]
									},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >提示：产生历史的某天收益，计算方式：截止到计息日前可产生收益总金额*当日日化利率。</font>"
										}]
								},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >不考虑补算历史收益而产生的误差</font>"
										}]
								}]
					}]
				});
		

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
		var date1 = this.formPanel.getCmpByName("days").getValue();
		if(date1!=null&&date1!=""&&date1!="undefined"){
			 var date2 = new Date();
			 if(date1<date2){
			 	date1=date1.format('Y-m-d');
			 	Ext.Msg.confirm('信息确认', '确认补派息么？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath+ '/financeProduct/beforeIntentRecordPlFinanceProductUserAccountInfo.do?days='+date1,
									method : 'post',
									success : function() {
										Ext.ux.Toast.msg("信息提示", "成功派息！");
										if (gridPanel != null) {
											gridPanel.getStore().reload({});
										}
										this.close();
									}
								});
					}
				});
			 }else{
			 	 Ext.ux.Toast.msg('操作信息','只能补派今天之前的收益！');
				 return;
			 
			 }
		}else{
			Ext.ux.Toast.msg('操作信息','补派息的日期不能为空！');
			return;
		}
		
	}// end of save

});


 
