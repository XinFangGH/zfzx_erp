/**
 * @author
 * @class BpActivityManageView
 * @extends Ext.Panel
 * @description [BpActivityManage]管理
 * @company 智维软件
 * @createtime:
 */
BpActivityManageView = Ext.extend(Ext.Panel, {
	// 构造函数
	activityLabel : '',
	flag : '',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (_cfg.type == 'integral') {
			this.activityLabel = '积分状态';
			this.flag = '0';
		} else if (_cfg.type == 'redPacket') {
			this.activityLabel = '红包状态';
			this.flag = '1';
		} else if (_cfg.type == 'coupon') {
			this.activityLabel = '优惠券状态';
			this.flag = '2';
		} else if (_cfg.type == 'integralExchange') {
			this.flag = '3';
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpActivityManageView.superclass.constructor.call(this, {
					id : 'BpActivityManageView_' + this.type,
					title : this.title,
					region : 'center',
					layout : 'border',
					iconCls:"menu-finance",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					region : 'north',
					height : 40,
					anchor : '96%',
					border : false,
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding:10px 10px 10px 10px',
					items : [{
						columnWidth : .16,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 80,
						hidden : this.type == 'coupon'
								|| this.type == 'integralExchange'
								? false
								: true,
						border : false,
						items : [{
							hiddenName : "couponType",
							fieldLabel : '优惠券类型',
							anchor : '100%',
							xtype : 'combo',
							mode : 'local',
							valueField : 'value',
							editable : false,
							displayField : 'item',
							store : new Ext.data.SimpleStore({
										fields : ["item", "value"],
										data : [["优惠券", "1"], /*["体验券", "2"],*/
												["加息券", "3"]]
									}),
							triggerAction : "all"
						}]
					}, {
						columnWidth : .16,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 80,
						hidden : this.type == 'integralExchange' ? true : false,
						border : false,
						items : [{
									xtype : "combo",
									fieldLabel : this.activityLabel,
									hiddenName : "status",
									anchor : '100%',
									mode : 'local',
									valueField : 'id',
									displayField : 'value',
									editable : false,
									triggerAction : "all",
									store : new Ext.data.SimpleStore({
												fields : ["value", "id"],
												data : [["开启", "0"],
														["关闭", "1"]]
											})
								}]
					}, {
						columnWidth : .16,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 100,
						border : false,
						hidden : this.type == 'integralExchange' ? true : false,
						items : [{
							hiddenName : "sendType",
							fieldLabel : '活动发送类型',
							anchor : '100%',
							xtype : 'combo',
							mode : 'local',
							valueField : 'value',
							editable : false,
							displayField : 'item',
							store : new Ext.data.SimpleStore({
										fields : ["item", "value"],
										data : [["注册", "1"],
												["邀请", "2"],
												["投标", "3"],
												["充值","4"],
												["首投", "8"]
												/*["累计投资", "6"],
												["累计充值", "7"],
												["累计推荐投资", "9"],
												["邀请好友第一次投标","5"]*/]
									}),
							triggerAction : "all"
							
							
						}]
					}, {
						columnWidth : .16,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 100,
						border : false,
						hidden : this.type == 'integralExchange' ? true : false,
						items : [{
									xtype : "textfield",
									name : "activityNumber",
									fieldLabel : '活动编号',
									anchor : '100%',
									maxLength : 200
								}]
					}, {
						columnWidth : .16,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 100,
						border : false,
						items : [{
									xtype : "textfield",
									name : "activityExplain",
									fieldLabel : '活动说明',
									anchor : '100%',
									maxLength : 200
								}]
					}, {
						columnWidth : 0.12,
						layout : 'form',
						border : false,

						labelAlign : 'right',
						items : [{
									xtype : 'button',
									text : '查询',
									width : 60,
									scope : this,
									style : 'margin-left:30',
									iconCls : 'btn-search',
									handler : this.search
								}]
					}, {
						columnWidth : 0.08,
						layout : 'form',
						border : false,

						labelAlign : 'right',
						items : [{
									xtype : 'button',
									text : '重置',
									width : 60,
									scope : this,
									style : 'margin-left:1',
									iconCls : 'btn-reset',
									handler : this.reset
								}]
					}]
				});// end of searchPanel
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, {
								iconCls : 'btn-detail',
								text : '查看',
								xtype : 'button',
								scope : this,
								handler : this.see
							}, {
								iconCls : 'close',
								text : '关闭',
								xtype : 'button',
								scope : this,
								handler : this.close
							}, {
								iconCls : 'btn-add1',
								text : '开启',
								xtype : 'button',
								scope : this,
								handler : this.open
							}]
				});
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
		    plugins : [summary],  
			id : 'BpActivityManageGrid_' + this.type,
			url : __ctxPath + "/activity/listBpActivityManage.do?flag="+ this.flag,
			viewConfig:{
          	forceFit:true,
          	scrollOffset:0,
          	//width:100, //固定宽度
          	singleSelect : true,
          	autoScroll:true
 			},
			fields : [{
						name : 'activityId',
						type : 'int'
					}, 'sendTypeValue', 'sendType', 'status',
					'activityStartDate', 'activityEndDate', 'activityExplain',
					'parValue', 'referenceUnitValue', 'money', 'levelValue',
					'couponTypeValue', 'couponType', 'couponStartDate',
					'createrValue', 'activityNumber', 'validNumber','overdueValue',
					'needIntegral', 'flag'],
			columns : [{
						align : 'center',
						header : 'activityId',
						dataIndex : 'activityId',
						hidden : true
					}, {
						align : 'center',
						header : '活动编号',
						summaryRenderer : totalMoney,
						width : 200,
						//hidden : this.type == 'integralExchange' ? true : false,
						dataIndex : 'activityNumber'
					}, {
						align : 'left',
						header : '活动发送类型',
						hidden : this.type == 'integralExchange' ? true : false,
						dataIndex : 'sendType',
						renderer : function(val) {
							if (val == "1") {
								return "注册";
							}
							if (val == "2") {
								return "邀请";
							}
							if (val == "3") {
								return "投标";
							}
							if (val == "4") {
								return "充值";
							}
							if (val == "5") {
								return "邀请好友第一次投标";
							}
							if (val == "6") {
								return "累计投资";
							}
							if (val == "7") {
								return "累计充值";
							}
							if (val == "8") {
								return "首投";
							}
							if (val == "9") {
								return "累计推荐投资";
							}
						}
					}, {
						align : 'center',
						header : '优惠券类型',
						hidden : this.type == 'coupon'
								|| this.type == 'integralExchange'
								? false
								: true,
						dataIndex : 'couponType',
						renderer : function(val) {
							if (val == "1") {
								return "优惠券";
							}
							if (val == "2") {
								return "体验券";
							}
							if (val == "3") {
								return "加息券";
							}
						}
					}, {
						align : 'center',
						header : '状态',
						dataIndex : 'status',
						
						renderer : function(v) {
							if (v == 0) {
								return '开启'
							}
							return "关闭";
						}
					},
					{
						align : 'center',
						header : '活动是否过期',
						hidden:this.type == 'integralExchange' ?true:false,
						dataIndex  : 'overdueValue'
					},
					{
						align : 'right',
						summaryType : 'sum',
						header : this.type == 'integral' ? '分值' : '面值',
						dataIndex : 'parValue',
						renderer:function(v,a,b,c,d,e,f){
							        if(this.type == 'integral'){
							        	return Ext.util.Format.number(v,
												'000')+"元";
												
							        }else{
							        	if(b.data.couponType=="3"){
											return Ext.util.Format.number(v,
											',000,000,000.00')
											+ "%";

									}else if (b.data.flag=="0"){
										return Ext.util.Format.number(v,
											',000,000,000.00')+"元";
									}
									else{
										return Ext.util.Format.number(v,
											',000,000,000.00')+"元";
									}
								}
						}
					}/*, {
						align : 'center',
						header : '使用规则',
						hidden : this.type == 'integralExchange' ? true : false,
						dataIndex : 'useRule'
					}*/, {
						align : 'center',
						header : '活动开始日期',
						hidden : this.type == 'integralExchange' ? true : false,
						dataIndex : 'activityStartDate'
					}, {
						align : 'center',
						header : '活动截止日期',
						hidden : this.type == 'integralExchange' ? true : false,
						dataIndex : 'activityEndDate'
					}, {
						align : 'center',
						header : '有效期',
						hidden : this.type == 'integralExchange' ? false : true,
						dataIndex : 'validNumber',
						renderer:function(v){
							return v+"天";
						}
					}, {
						align : 'center',
						header : '需要积分',
                        summaryType : 'sum',
                         renderer : function(v) {
					                 return v+"分";
				                   },
						hidden : this.type == 'integralExchange' ? false : true,
						dataIndex : 'needIntegral'	
					}, {
						align : 'left',
						header : '活动说明',
						width : 200,
						dataIndex : 'activityExplain'
					}, {
						align : 'left',
						header : '录入人',
						dataIndex : 'createrValue'
					}]
		});
	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// 创建记录
	createRs : function() {
		if (this.flag == "0") {
			new BpActivityManageForm({
						type : this.type,
						readOnly:false
					}).show();
		} else if (this.flag == "1") {
			new BpActivityManageForm1({
						type : this.type,
						readOnly:false
					}).show();
		} else if (this.flag == "2") {
			new BpActivityManageForm2({
						type : this.type,
						readOnly:false
					}).show();
		} else if (this.flag == "3") {
			new BpActivityManageForm3({
						type : this.type,
						readOnly:false
					}).show();
		}

	},
	// 查看记录
	see : function() {
		var activityId = this.gridPanel.getSelectionModel().selections.get(0).data.activityId
		if (this.flag == "0") {
			new BpActivityManageForm({
						type : this.type,
						readOnly : true,
						activityId : activityId
					}).show();
		} else if (this.flag == "1") {
			new BpActivityManageForm1({
						type : this.type,
						readOnly : true,
						activityId : activityId
					}).show();
		} else if (this.flag == "2") {
			new BpActivityManageForm2({
						type : this.type,
						readOnly : true,
						activityId : activityId
						
					}).show();
		} else if (this.flag == "3") {
			new BpActivityManageForm3({
						type : this.type,
						readOnly : true,
						activityId : activityId
					}).show();
		}

	},
	// 关闭
	close : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var thisPanel = this.gridPanel;
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		}
		var ids = $getGdSelectedIds(this.gridPanel, 'activityId');
		Ext.Msg.confirm('信息确认', '你确定要关闭选中记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/activity/closeBpActivityManage.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										var obj = Ext.util.JSON
												.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息', obj.msg);
										thisPanel.getStore().reload();
									},
									params : {
										ids : ids
									}
								});
					}
				});
	},
	// 开启
	open : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var thisPanel = this.gridPanel;
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中记录');
			return false;
		}
		var ids = $getGdSelectedIds(this.gridPanel, 'activityId');
		Ext.Msg.confirm('信息确认', '你确定要开启选中记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/activity/openBpActivityManage.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										var obj = Ext.util.JSON
												.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息', obj.msg);
										thisPanel.getStore().reload();
									},
									params : {
										id : ids
									}
								});
					}
				});
	}
});
