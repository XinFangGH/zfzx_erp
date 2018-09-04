/** 投资人收支查询---投资人账户明细查询 */
accountListQuery = Ext.extend(Ext.Panel, {
	titlePrefix : "",
	seniorHidden : false,
	Confirmhidden : false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		accountListQuery.superclass.constructor.call(this, {
					id : 'confirmRechargeView',
					title : this.titlePrefix,
					region : 'center',
					layout : 'border',
					// iconCls : "btn-tree-team17",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		this.searchPanel = new Ext.FormPanel({
					layout : 'column',
					region : 'north',
					border : false,
					height : 70,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding-top:20px',
					items : [{
								xtype : "hidden",
								name : "accountId",
								value : this.accountId
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											id : 'startDate',
											fieldLabel : '起始时间',
											name : 'startDate',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '截止时间',
											name : 'endDate',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelAlign : 'right',
								border : false,
								items : [{
									fieldLabel : '收支类型',
									xtype:'combo',
									hiddenName:'transferType',
									anchor : "100%",
									mode: 'local',
									store : new Ext.data.ArrayStore({
										fields: [ 'myId','displayText' ],
										data : [[1,'充值'], [2, '取现'],
												[3, '收益'], [4, '投资'], [5, '还本']]
										}),
										valueField : 'myId',
										displayField : 'displayText'
								}]
							}, {
								columnWidth : .12,
								layout : 'form',
								border : false,
								items : [{
											text : '查询',
											xtype : 'button',
											scope : this,
											style : 'margin-left:20px',
											anchor : "100%",
											iconCls : 'btn-search',
											handler : this.search
										}]
							}, {
								columnWidth : .12,
								layout : 'form',
								border : false,
								items : [{
											text : '重置',
											style : 'margin-left:20px',
											xtype : 'button',
											scope : this,
											anchor : "100%",
											iconCls : 'btn-reset',
											handler : this.reset
										}]
							}]
				});
		this.topbar = new Ext.Toolbar({});
		this.gridPanel = new HT.GridPanel({
			// name : 'confirmRechargeGrid',
			region : 'center',
			tbar : this.topbar,
			root : 'result',
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath
					+ "/creditFlow/creditAssignment/bank/accountListQueryObAccountDealInfo.do?accountId="+this.accountId,
			fields : [{
						name : 'id',
						type : 'int'
					},'accountId', 'incomMoney', 'payMoney', 'currentMoney','transferType' ,'dealType', 'transferDate', 'investPersonName', 'rechargeLevel','rechargeConfirmStatus','dealRecordStatus','msg','recordNumber','transferTypeName'],

			columns : [{
						header : '交易流水号',
						dataIndex : 'recordNumber',
						renderer:function(v){
							return Ext.isEmpty(v)?"--":v;
                        }
					},{
						header : '交易日期',
						dataIndex : 'transferDate',
						renderer:function(v){
							return Ext.isEmpty(v)?"--":v;
                        }
					},{
						header : '交易类型',
						dataIndex : 'transferTypeName'
					}, {
						header : '收入金额（元）',
						
						dataIndex : 'incomMoney',
						renderer : function(value) {
							if (value == "") {
								return "0.00元";
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '支出金额（元）',
						width : 130,
						dataIndex : 'payMoney',
						renderer : function(value) {
							if (value == "") {
								return "0.00元";
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '剩余金额（元）',
						dataIndex : 'currentMoney',
						 renderer : function(value) {
					 if (value == "") {
					 return "0.00元";
					 } else {
					 return Ext.util.Format.number(value,
					 ',000,000,000.00')
					 + "元";
					 }
					 }
				}, {
						header : '交易状态',
						dataIndex : 'dealRecordStatus',
						renderer:function(v,a,r){
							if(Ext.isEmpty(v)){
								return "--";
							}else{
								if(v==1){
									return "交易等待中";
								}else if(v==2){
									return "交易成功";
								}else if(v==3){
									return "交易失败";
								}else if(v==4){
									return "取现审批复核";
								}else if(v==5){
									return "取现办理";
								}else if(v==6){
									return "交易异常";
								}else if(v==7){
									return "资金冻结";
								}else if(v==8){
									return "资金解冻";
								
								}
							}
                        }
				}]
		});
		this.gridPanel.addListener('afterrender', function() {
					this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
								msg : '正在加载数据中······,请稍候······',
								store : this.gridPanel.store,
								removeMask : true
							});
					this.loadMask1.show(); // 显示
				}, this);
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
	}
});
