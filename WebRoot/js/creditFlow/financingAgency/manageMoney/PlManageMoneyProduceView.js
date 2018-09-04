/**
 * @author
 * @class PlManageMoneyPlanView
 * @extends Ext.Panel
 * @description [PlManageMoneyPlan]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyProduceView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlManageMoneyProduceView.superclass.constructor.call(this, {
					id : 'PlManageMoneyProduceView'+this.type,
					title : this.type == 0 ? '理财产品管理' : '理财产品购买' ,
					region : 'center',
					layout : 'border',
					iconCls:"btn-tree-team43",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
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
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel : '理财产品名称',
										name : 'Q_mmName_S_LK',
										xtype : 'textfield'
										

									}]
						}				, {
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
										}]},	
											{columnWidth : .07,
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
								iconCls : 'btn-add',
								text : '添加',
								xtype : 'button',
								hidden : (isGranted('_plmm_add') ? (this.type==0 ? false : true) : true),
								scope : this,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_plmm_edit') ? (this.type==0 ? false : true) : true)
							}), {
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								hidden : (isGranted('_plmm_edit') ? (this.type==0 ? false : true) : true),
								scope : this,
								handler : this.editRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_plmm_delete') ? (this.type==0 ? false : true) : true)
							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								hidden : (isGranted('_plmm_delete') ? (this.type==0 ? false : true) : true) ,
								scope : this,
								handler : this.removeSelRs
							}, new Ext.Toolbar.Separator({
								hidden : true
							}),/* {
								iconCls : 'btn-add',
								text : '购买',
								xtype : 'button',
								hidden : this.type==0 ? true : false,
								scope : this,
								handler : this.buyselRs
							},new Ext.Toolbar.Separator({
							}),*/ {
								iconCls : 'btn-add',
								text : '购买申请',
								xtype : 'button',
								scope : this,
								hidden : this.type==0 ? true : false,
								handler : this.buyselRsFlow
							}]
				});
				
        var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			singleSelect:true,
			// 使用RowActions
			id : 'PlManageMoneyProduceGrid'+this.type,
			url : __ctxPath+ "/creditFlow/financingAgency/listPlManageMoneyPlan.do?Q_keystr_S_EQ=mmproduce&type="+this.type,
			fields : [{
						name : 'mmplanId',
						type : 'int'
					}, 'mmName', 'mmNumber', 'manageMoneyTypeId', 'keystr',
					'investScope', 'benefitWay', 'buyStartTime', 'buyEndTime',
					'startMoney', 'riseMoney', 'limitMoney',
					'startinInterestCondition', 'expireRedemptionWay',
					'chargeGetway', 'guaranteeWay', 'yeaRate', 'investlimit',
					'sumMoney', 'state', 'startinInterestTime',
					'endinInterestTime', 'investedMoney', 'bidRemark',
					'htmlPath', 'createtime', 'updatetime'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						hidden : true
					}/*, {
						header : 'manageMoneyTypeId',
						dataIndex : 'manageMoneyTypeId'
					}*/, {
						header : '理财产品名称',
						width : 120,
						dataIndex : 'mmName'
					}, {
						header : '理财产品编号',
						width : 120,
						summaryRenderer : totalMoney,
						dataIndex : 'mmNumber'
					}, {
						header : '投资期限(个月)',
						dataIndex : 'investlimit',
						width : 60,
						align : 'center'
					}/*, {
						header : '招标金额',
						dataIndex : 'sumMoney',
					    align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
						
					}, {
						header : '投资期限',
						dataIndex : 'investlimit',
						align : 'right',
						renderer:function(v){
							return v+"个月";
						}
					}*/,  {
						header : '购买放开时间',
						align : 'center',
						dataIndex : 'buyStartTime'
					}, {
						header : '购买截止时间',
						align : 'center',
						dataIndex : 'buyEndTime'
					}, {
						header : '年化收益率',
						dataIndex : 'yeaRate',
						width : 80,
						align : 'right',
						renderer:function(v){
							return v+"%";
						}
					}, {
						header : '已购买总金额(元)',
						dataIndex : 'investedMoney',
					    align : 'right',
					    summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
						
					}, {
						header : '创建时间',
						align : 'center',
						dataIndex : 'createtime'
					},{
					    header : '产品说明',
					    width : 80,
					    align : 'left',
						dataIndex : 'bidRemark'
					}]
				// end of columns
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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlManageMoneyPlanForm({
								mmplanId : rec.data.mmplanId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlManageMoneyProduceForm({
			type : this.type,
			gridPanel : this.gridPanel
		}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/multiDelPlManageMoneyPlan.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if(parseFloat(s[0].data.investedMoney)==0){
		  $delGridRs({
				url : __ctxPath
						+ '/creditFlow/financingAgency/multiDelPlManageMoneyPlan.do',
				grid : this.gridPanel,
				idName : 'mmplanId'
		  });
		}else{
			Ext.ux.Toast.msg('操作信息', '已被购买不能删除！');
		}
		
	},
	// 编辑Rs
	editRs : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			if(parseFloat(s[0].data.investedMoney)!=0){
//				Ext.ux.Toast.msg('操作信息', '已正式投入购买使用的理财产品，不允许修改派息计算有关的字段！');
				Ext.ux.Toast.msg('操作信息', '已正式投入购买使用的理财产品不允许编辑！');
			}else{
				new PlManageMoneyProduceForm({
					mmplanId : s[0].data.mmplanId,
					type : this.type,
					gridPanel : this.gridPanel,
					isEdit : parseFloat(s[0].data.investedMoney)!=0 ? true : false
			 	}).show();
			}
		}
	},
	
	buyselRs:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		
		var nowDate=new Date().format('Y-m-d H:i:s');
		var buyStartTime=s[0].data.buyStartTime;
		var buyEndTime=s[0].data.buyEndTime;
		
		var nowTimes = nowDate.substring(0, 10).split('-');
		var beginTimes = buyStartTime.substring(0, 10).split('-');
	    var endTimes = buyEndTime.substring(0, 10).split('-');
	    
	    nowDate =  nowTimes[1] + '-' + nowTimes[2] + '-' + nowTimes[0] + ' ' + nowDate.substring(10, 19);
	    buyStartTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + buyStartTime.substring(10, 19);
	    buyEndTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + buyEndTime.substring(10, 19);
	    
	    var a = (Date.parse(nowDate) - Date.parse(buyStartTime)) / 3600 / 1000;
	    var b = (Date.parse(nowDate) - Date.parse(buyEndTime)) / 3600 / 1000;
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}else if(a < 0){
			Ext.ux.Toast.msg('操作信息', '还未到购买开放时间');
			return false;
		} else if(b>0){
			Ext.ux.Toast.msg('操作信息', '已超过购买结束时间');
			return false;
		} else{
			new PlManageMoneyProducebuy({
						mmplanId : s[0].data.mmplanId,
						mmName : s[0].data.mmName,
						investlimit : s[0].data.investlimit
		 }).show();
		}
	}
	,
	buyselRsFlow:function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}else {
			var nowDate=new Date().format('Y-m-d H:i:s');
			var buyStartTime=s[0].data.buyStartTime;
			var buyEndTime=s[0].data.buyEndTime;
			
			var nowTimes = nowDate.substring(0, 10).split('-');
			var beginTimes = buyStartTime.substring(0, 10).split('-');
		    var endTimes = buyEndTime.substring(0, 10).split('-');
		    
		    nowDate =  nowTimes[1] + '-' + nowTimes[2] + '-' + nowTimes[0] + ' ' + nowDate.substring(10, 19);
		    buyStartTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + buyStartTime.substring(10, 19);
		    buyEndTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + buyEndTime.substring(10, 19);
		    
		    var a = (Date.parse(nowDate) - Date.parse(buyStartTime)) / 3600 / 1000;
		    var b = (Date.parse(nowDate) - Date.parse(buyEndTime)) / 3600 / 1000;
		    if(a < 0){
		    	Ext.ux.Toast.msg('操作信息', '还未到购买开放时间');
				return false;
		    } else if(b>0){
				Ext.ux.Toast.msg('操作信息', '已超过购买结束时间');
				return false;
			} else{
				 new PlMmOrderInfoBuyForm({
					mmplanId : s[0].data.mmplanId,
					mmplanName : s[0].data.mmName,
					type : 'buyMmproduce'//用来判断调用的js里面走那个URL
				 }).show();
			}
		}
	}
	
});
