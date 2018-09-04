/**
 * @author
 * @createtime
 * @class PlBidAutoForm
 * @extends Ext.Window
 * @description PlBidAuto表单
 * @company 智维软件
 */
PlBidAutoForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlBidAutoForm.superclass.constructor.call(this, {
					id : 'PlBidAutoFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 650,
					maximizable : true,
					title : '[自动投标]详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '100%'
			},
			items : [{
						name : 'plBidAuto.id',
						xtype : 'hidden',
						value : this.id == null ? '' : this.id
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '注册用户',
									name : 'plBidAuto.userName',
									xtype : 'textfield',
									readOnly : true

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '每次投标的金额',
									name : 'plBidAuto.bidMoney',
									xtype : 'textfield'

								}]
					}, {
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '利率下限',
									name : 'plBidAuto.interestStart',
									xtype : 'numberfield'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "%",
									labelSeparator : ''
								}]
					}, {
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '利率上限',
									name : 'plBidAuto.interestEnd',
									xtype : 'numberfield'

								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "%",
									labelSeparator : ''
								}]
					}, {
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
							fieldLabel : '借款期限下限',
							hiddenName : 'plBidAuto.periodStart',
							xtype : 'combo',
							mode : 'local',
							valueField : 'value',
							editable : false,
							displayField : 'item',
							store : new Ext.data.SimpleStore({
										fields : ["item", "value"],
										data : [["0", "0"], ["1", "1"],
												["2", "2"], ["3", "3"],
												["4", "4"], ["5", "5"],
												["6", "6"], ["7", "7"],
												["8", "8"], ["9", "9"],
												["10", "10"], ["11", "11"],
												["12", "12"], ["13", "13"],
												["14", "14"], ["15", "15"],
												["16", "16"], ["17", "17"],
												["18", "18"], ["19", "19"],
												["20", "20"], ["21", "21"],
												["22", "22"], ["23", "23"],
												["24", "24"], ["25", "25"],
												["26", "26"], ["27", "27"],
												["28", "28"], ["29", "29"],
												["30", "30"], ["31", "31"],
												["32", "32"], ["33", "33"],
												["34", "34"], ["35", "35"],
												["36", "36"]]
									}),
							triggerAction : "all"

						}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "月",
									labelSeparator : ''
								}]
					}, {
						columnWidth : .47, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{

							fieldLabel : '借款期限上限',
							hiddenName : 'plBidAuto.periodEnd',
							xtype : 'combo',
							mode : 'local',
							valueField : 'value',
							editable : false,
							displayField : 'item',
							store : new Ext.data.SimpleStore({
										fields : ["item", "value"],

										data : [["0", "0"], ["1", "1"],
												["2", "2"], ["3", "3"],
												["4", "4"], ["5", "5"],
												["6", "6"], ["7", "7"],
												["8", "8"], ["9", "9"],
												["10", "10"], ["11", "11"],
												["12", "12"], ["13", "13"],
												["14", "14"], ["15", "15"],
												["16", "16"], ["17", "17"],
												["18", "18"], ["19", "19"],
												["20", "20"], ["21", "21"],
												["22", "22"], ["23", "23"],
												["24", "24"], ["25", "25"],
												["26", "26"], ["27", "27"],
												["28", "28"], ["29", "29"],
												["30", "30"], ["31", "31"],
												["32", "32"], ["33", "33"],
												["34", "34"], ["35", "35"],
												["36", "36"]]

									}),
							triggerAction : "all"

						}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 20,
						items : [{
									fieldLabel : "月",
									labelSeparator : ''
								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
							fieldLabel : '信用等级下限',
							allowBlank : false,
							hiddenName : 'plBidAuto.rateStart',
							xtype : 'combo',
							valueField : 'itemValue',
							editable : true,

							displayField : 'itemName',
							store : new Ext.data.ArrayStore({
								autoLoad : true,
								url : __ctxPath
										+ "/creditFlow/financingAgency/typeManger/loadItemPlKeepCreditlevel.do",
								fields : ['itemValue', 'itemName']
							}),
							triggerAction : "all"

						}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{

									fieldLabel : '信用等级上限',
									hiddenName : 'plBidAuto.rateEnd',
									xtype : 'combo',
									allowBlank : false,
							valueField : 'itemValue',
							editable : true,

							displayField : 'itemName',
							store : new Ext.data.ArrayStore({
								autoLoad : true,
								url : __ctxPath
										+ "/creditFlow/financingAgency/typeManger/loadItemPlKeepCreditlevel.do",
								fields : ['itemValue', 'itemName']
							}),
							triggerAction : "all"

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '账户保留金额',
									name : 'plBidAuto.keepMoney',
									xtype : 'textfield'

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '排序时间',
									name : 'plBidAuto.orderTime',
									xtype : 'datefield'

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{
									fieldLabel : '是否开启',
									hiddenName : 'plBidAuto.isOpen',
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : true,
									readOnly : true,
									displayField : 'item',
									store : new Ext.data.SimpleStore({
												fields : ["item", "value"],
												data : [["开启", 1], ["关闭", 0]]
											}),
									triggerAction : "all"

								}]
					}, {
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 90,
						labelAlign : 'right',
						items : [{

									fieldLabel : '是否禁用',
									hiddenName : 'plBidAuto.banned',
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'item',
									store : new Ext.data.SimpleStore({
												fields : ["item", "value"],
												data : [["正常", "0"],
														["禁用", "1"]]
											}),
									triggerAction : "all"

								}]
					}]
		});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/auto/getPlBidAuto.do?id='
								+ this.id,
						root : 'data',
						preName : 'plBidAuto'

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
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/creditFlow/auto/savePlBidAuto.do',
					callback : function(fp, action) {
						obj = Ext.util.JSON.decode(action.response.responseText);
						if(obj.success==false){
							alert("修改失败,失败原因:"+obj.bidMoney+obj.interest+obj.keepMoney+obj.period+obj.rate);
						}
						var gridPanel = Ext.getCmp('PlBidAutoGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});