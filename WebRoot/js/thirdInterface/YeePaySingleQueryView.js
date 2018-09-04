
YeePaySingleQueryView = Ext.extend(Ext.Panel, {
	// 构造函数
	titlePrefix : "",
	mode : "",
	constructor : function(_cfg) {
		if (_cfg && typeof(_cfg.modeType) != "undefined") {
			this.modeType = parseInt(_cfg.modeType);
		}
		switch (this.modeType) {
			case 1 :
				this.titlePrefix = "充值记录查询";
				this.mode = "RECHARGE_RECORD";
				break;
			case 2 :
				this.titlePrefix = "放款记录查询";
				this.mode = "PAYMENT_RECORD";
				break;
			case 3 :
				this.titlePrefix = "还款记录查询";
				this.mode = "REPAYMENT_RECORD";
				break;
			case 4 :
				this.titlePrefix = "取现记录查询";
				this.mode = "WITHDRAW_RECORD";
				break;
			
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		YeePaySingleQueryView.superclass.constructor.call(this, {
					id : 'YeePaySingleQueryView'+this.modeType,
					title : this.titlePrefix,
					region : 'center',
					layout : 'border',
					iconCls :'btn-tree-team39',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var itemwidth = 0.2;
		var isShow=false;
		this.searchPanel = new HT.SearchPanel({
					frame : false,
					region : 'north',
					height : 35,
					monitorValid : true,
					layout : 'column',
					bodyStyle : 'padding:0px 0px 0px 0px',
					border : false,
					defaults : {
						layout : 'form',
						border : false,
						labelWidth : 60,
						bodyStyle : 'padding:5px 0px 0px 5px'
					},
					labelAlign : "right",
					items : [{
								columnWidth : 0.2,
								items : [{
											fieldLabel : '流水号',
											allowBlank : false,
											name:'requestNo',
											xtype : 'textfield'
											
										}]
							},{
								columnWidth : 0.2,
								items : [{
											fieldLabel : '日期',
											name:'dateName',
											//allowBlank : false,
											xtype : 'datefield',
											format:'Y-m-d'
										}]
							},{
								columnWidth : 0.1,
								items : [{
											xtype : 'button',
											text : '查询',
											iconCls : 'btn-search',
											width : 60,
											formBind : true,
											labelWidth : 20,
											bodyStyle : 'padding:5px 0px 0px 0px',
											scope : this,
											handler : this.searchByCondition
										}]
							},{
								columnWidth : 0.1,
								items : [{
											xtype : 'button',
											text : '重置',
											iconCls : 'btn-reset',
											width : 60,
											formBind : true,
											labelWidth : 20,
											bodyStyle : 'padding:5px 0px 0px 0px',
											scope : this,
											handler : this.reset
										}]
							}]
				});// end of searchPanel
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			//rowActions : false,
			isautoLoad:false,
			plugins : [summary],
			showPaging:false,
			singleSelect:true,
			loadMask : true,
			url : __ctxPath +"/pay/queryPay.do?mode="+this.mode,
			fields : [ {
						name : 'id',
						type : 'int'
						},
					'paymentAmount', 'sourceUserNo', 'createTime', 'loanTime',
					'status', 'repaymentAmount', 'targetUserNo', 'amount',
					'userNo'],
					columns : [{
						header : '第三方账号',
						dataIndex : 'userNo',
						summaryRenderer : totalMoney,
						hidden:(this.modeType==1||this.modeType==4)?false:true
					}, {
						header : '第三方账号',
						dataIndex : 'sourceUserNo',
						summaryRenderer : totalMoney,
						hidden:(this.modeType==2)?false:true
					},{
						header : '第三方账号',
						dataIndex : 'targetUserNo',
						summaryRenderer : totalMoney,
						hidden:(this.modeType==3)?false:true
					},{
						header : '金额',
						dataIndex : 'amount',
						summaryType : 'sum',
						hidden:(this.modeType==1||this.modeType==4)?false:true
					},{
						header : '放款金额',
						dataIndex : 'paymentAmount',
						summaryType : 'sum',
						hidden:(this.modeType==2)?false:true
					},{
						header : '还款金额',
						dataIndex : 'repaymentAmount',
						summaryType : 'sum',
						hidden:(this.modeType==3)?false:true
					},{
						header : '创建时间',
						dataIndex : 'createTime'
					},{
						header : '放款时间',
						dataIndex : 'createTime',
						hidden:(this.modeType==2)?false:true
					},{
						header : '交易状态',
						dataIndex : 'status',
						renderer : function(value){
							if(value=='FREEZED'){
								return '冻结';
							} else if(value=='CANCEL'){
								return '取消';
							} else if(value=='LOANED'){
								return '放款';
							} else if(value=='SUCCESS'){
								return '成功';
							}else if(value=='FAILD'){
								return '失败';
							} else if(value=='INIT'){
								return '初始状态';
							}  else{
								return value;
							}
						}
					}]
				// end of columns
			});

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	searchByCondition : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	}
});