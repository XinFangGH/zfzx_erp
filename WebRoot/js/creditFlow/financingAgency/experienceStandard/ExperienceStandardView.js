/**
 * @author
 * @class PlManageMoneyPlanView
 * @extends Ext.Panel
 * @description [PlManageMoneyPlan]管理
 * @company 智维软件
 * @createtime:
 */
ExperienceStandardView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ExperienceStandardView.superclass.constructor.call(this, {
					id : 'ExperienceStandardView',
					title : '体验标派息管理',
					iconCls:"menu-finance",
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'ExperienceStandardViewSearchPanel',
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor : '95%'

						},
						items : [{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '招标编号',
										name : 'mmNumber',
										xtype : 'textfield'
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '招标名称',
										name : 'mmName',
										xtype : 'textfield'
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items : [{
										fieldLabel : '计划到账日',
										name : 'intentDate_GE',
										xtype : 'datefield',
										format : "Y-m-d"
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor : '100%'
							},
							items : [{
									fieldLabel : '至',
									name : 'intentDate_LE',
									xtype : 'datefield',
									format : "Y-m-d",
									anchor : '75%'
							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							hidden:true,
							defaults : {
								anchor : '100%'
							},
							items : [{
									fieldLabel : '未还款，已还款',
								    id: 'isPayExperience',
								    name : 'isPayExperience',
								    value:"notPayExperience",
									xtype : 'textfield'
							}]
						},{
							columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
						}]},{columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
						}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-user-paixi',
								text : '派息',
								xtype : 'button',
								scope : this,
								handler : this.dividend
							}, '-', {
								iconCls : 'btn-detail',
								text : '还款明细',
								xtype : 'button',
								scope : this,
								handler : this.seeDetail
							},'-', {
								iconCls : 'btn-xls1',
								text : '导出列表',
								xtype : 'button',
								scope : this,
								handler : this.toExcel
							}, "->", {
								xtype : 'checkbox',
								boxLabel : '未还款',
								inputValue : true,
								name : "notPayExperience",
								checked : true,
								scope : this,
								handler : function() {
									var notPay = this.topbar.getCmpByName("notPayExperience");
									var payed = this.topbar.getCmpByName("payedExperience");
									Ext.getCmp("isPayExperience");
									if (notPay.getValue() == true && payed.getValue() == true) {
										Ext.getCmp("isPayExperience").setValue("all");
									}
									if (notPay.getValue() == false && payed.getValue() == true) {
										Ext.getCmp("isPayExperience").setValue("payedExperience");
									}
									if (notPay.getValue() == true && payed.getValue() == false) {
										Ext.getCmp("isPayExperience").setValue("notPayExperience");
									}
									if (notPay.getValue() == false && payed.getValue() == false) {
									    Ext.getCmp("isPayExperience").setValue("none");
									}
									this.search();
								}
							}, '-', {
								xtype : 'checkbox',
								name : "payedExperience",
								boxLabel : '已还款',
								inputValue : true,
								scope : this,
								checked : false,
								handler : function() {
									var notPay = this.topbar.getCmpByName("notPayExperience");
									var payed = this.topbar.getCmpByName("payedExperience");
									if (notPay.getValue() == true && payed.getValue() == true) {
										Ext.getCmp("isPayExperience").setValue("all");
									}
									if (notPay.getValue() == false && payed.getValue() == true) {
										Ext.getCmp("isPayExperience").setValue("payedExperience");
									}
									if (notPay.getValue() == true && payed.getValue() == false) {
										Ext.getCmp("isPayExperience").setValue("notPayExperience");
									}
									if (notPay.getValue() == false && payed.getValue() == false) {
									    Ext.getCmp("isPayExperience").setValue("none");
									}
									this.search();
								}
							}]
				});
				
				var keystr='experience';
				var state="2,7,10";

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			singleSelect:true,
			// 使用RowActions
			id : 'ExperienceStandardViewGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listExperiencePlManageMoneyPlan.do?keystr="+keystr+"&state="+state,
			fields : [{
						name : 'mmplanId',
						type : 'Long'
					}, 'mmName', 'mmNumber','keystr','startinInterestTime','intentDate','sumIncomeMoney'
					   ,'factDate'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						hidden : true
					}, {
						header : '招标编号',
						align:'center',
						dataIndex : 'mmNumber'
					}, {
						header : '招标名称',
						align:'center',
						dataIndex : 'mmName'
					}, {
						header : '起息日期',
						dataIndex : 'startinInterestTime',
					    align : 'right'
					}, {
						header : '计划派息日期',
						dataIndex : 'intentDate',
					    align : 'right'
					}, {
						header : '派息合计',
						dataIndex : 'sumIncomeMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '实际派息日',
						dataIndex : 'factDate',
						align : 'right'
					}]
				// end of columns
			});


	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
		var payCom=this.topbar.getCmpByName('payedExperience');
		payCom.setValue(false);
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// 派息
	dividend : function(id) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			if(record.get('sumIncomeMoney')<=0){
				  Ext.ux.Toast.msg('操作信息', '已经派息，不需要进行派息');
				  return ;
			}
			Ext.MessageBox.wait('派息进行中','请稍后...');//锁屏
	    	Ext.Ajax.request({
				url : __ctxPath
						+ "/creditFlow/financingAgency/dividendExperiencePlMmOrderAssignInterest.do",
				method : 'post',
				success : function(response, request) {
					var obj=Ext.util.JSON.decode(response.responseText)
					Ext.ux.Toast.msg('操作信息', obj.msg);
					Ext.MessageBox.hide();//解除锁屏
				},
				params : {
					mmplanId : s[0].data.mmplanId
				}
			});
		}
	},
	
	
	// 查看还款明细
	seeDetail : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			new AssigninterestView({
				mmplanId : s[0].data.mmplanId,
				isAllReadOnly:true,
				isHidden:true
			}).show();
		}
	},
	
	//导出到Excel
	toExcel : function(){
	    var keystr='experience';
		var state="7,10";
		var mmNumber=this.searchPanel.getCmpByName('mmNumber').getValue();
		var mmName=this.searchPanel.getCmpByName('mmName').getValue();
		var intentDate_GE=this.searchPanel.getCmpByName('intentDate_GE').getValue();
		var intentDate_LE=this.searchPanel.getCmpByName('intentDate_LE').getValue();
		var isPayExperience=this.searchPanel.getCmpByName('isPayExperience').getValue();
		window.open(__ctxPath + "/creditFlow/financingAgency/toExcelPlBuyinfoPlManageMoneyPlanBuyinfo.do?"
		        +"keystr="+keystr
		        +"&state="+state
				+"&mmNumber="+mmNumber
				+"&mmName="+mmName
				+"&intentDate_GE="+intentDate_GE
				+"&intentDate_LE="+intentDate_LE
				+"&isPayExperience="+isPayExperience
				);
				
				/*
				Ext.Ajax.request({
				url : __ctxPath
						+ "/creditFlow/financingAgency/toExcelPlBuyinfoPlManageMoneyPlanBuyinfo.do",
				method : 'post',
				success : function(response, request) {
					var obj=Ext.util.JSON.decode(response.responseText)
					
				},
				params : {
					keystr : keystr,
					state:state,
					mmNumber:+mmNumber,
					mmName:mmName,
					intentDate_GE:+intentDate_GE,
					intentDate_LE:intentDate_LE,
					isPayExperience:isPayExperience
				}
			});*/
	}
	
});
