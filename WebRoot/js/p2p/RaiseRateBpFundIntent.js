/**
 * @author
 * @class RaiseRateBpFundIntent
 * @extends Ext.Panel
 * @description [PlBidInfo]管理
 * @company 智维软件
 * @createtime:
 */
RaiseRateBpFundIntent = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		this.title="募集期奖励台账"
		
		RaiseRateBpFundIntent.superclass.constructor.call(this, {
					id : 'RaiseRateBpFundIntent_'+this.userType+this.Type,
					title : this.title,
					iconCls : this.userType==0?"btn-tree-team39":"btn-tree-team39",
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '项目名称',
						name : 'bidName',
						anchor : "100%",
						xtype : 'textfield'
					},{
						fieldLabel : '项目编号',
						name : 'bidProNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '计划日期',
						name : 'intentDatel',
						anchor : "100%",
						xtype : 'datefield',
						format : 'Y-m-d'
					},{
						fieldLabel : '实际日期',
						name : 'factDatel',
						anchor : "100%",
						xtype : 'datefield',
						format : 'Y-m-d'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '至',
						name : 'intentDateg',
						anchor : "100%",
						xtype : 'datefield',
						format : 'Y-m-d'
					},{
						fieldLabel : '至',
						name : 'factDateg',
						anchor : "100%",
						xtype : 'datefield',
						format : 'Y-m-d'
					}]
		     	},{
	     			columnWidth :.15,
					layout : 'form',
					border : false,
					labelWidth :50,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					},{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						//width : 40,
						anchor : "90%",
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-user-paixi',
					text : '派息',
					xtype : 'button',
					scope : this,
					handler : this.distributeMoney
				},{
					iconCls : 'btn-detail',
					text : '查看奖励明细',
					xtype : 'button',
					scope : this,
					handler : this.seeInfo
				},{
					iconCls : 'btn-xls',
					text : '导出到Excel',
					xtype : 'button',
					scope : this,
					handler : this.couponsExcel
				}]
			});
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				
		this.gridPanel = new HT.GridPanel({
			tbar : this.topbar,
			region : 'center',
			id : 'raiserateGrid',
			plugins : [summary],
			url : __ctxPath + "/creditFlow/finance/getraiseRateListSlFundIntent.do?fundType=raiseinterest",
			fields : [{
						name : 'infoId',
						type : 'int'
					}, 'planId', 'bidPlanName', 'bidPlanProjectNum',
									'incomeMoney','fundType','intentDate','factDate','raiseRate','afterMoney'],
			columns : [{
						header : 'planId',
						dataIndex : 'planId',
						hidden : true
					},{
						header  :"招标项目名称",
						dataIndex : 'bidPlanName',
						summaryType : 'count',
						summaryRenderer : totalMoney
					},{
						header : "招标项目编号",
						dataIndex : 'bidPlanProjectNum'
					},{
						header : '返利类型',
						dataIndex : 'rebateType',
						renderer:function(v){
							return "返息";
							/*if(v=="1"){
								return "返现";
							}else if(v=="2"){
								return "返息";
							}else if(v=="3"){
								return "返息现";
							}else if(v=="4"){
								return "加息";
							}else{
								return "";
							}*/
						
						}
					},{
						header : '返利方式',
						dataIndex : 'rebateWay',
						renderer:function(v){
							/*if(v=="1"){
								return "立返";
							}else if(v=="2"){
								return "随期";
							}else if(v=="3"){
								return "到期";
							}else{
								return "";
							}*/
							return "立返";
						}
					},{
						header : '募集期利率',
						dataIndex : 'raiseRate',
						renderer:function(v){
							return v+"%";
						}
					},{
						header : '奖励金额',
						dataIndex : 'incomeMoney',
						summaryType : 'sum',
						renderer : function(value) {
							if (value == "") {
								return "0.00元";
							} else {
								return Ext.util.Format.number(value, ',000,000,000.00')
										+ "元";
							}
						}
					},{
						header : '资金类型',
						dataIndex : 'fundType',
						renderer:function(v){
							
								return "利息奖励";
						}
					},{
						header : '计划奖励日期',
						dataIndex : 'intentDate'
					},{
						header : '实际奖励金额',
						dataIndex : 'afterMoney',
						summaryType : 'sum',
						renderer : function(value) {
							if (value == "") {
								return "0.00元";
							} else {
								return Ext.util.Format.number(value, ',000,000,000.00')
										+ "元";
							}
						}
					},{
						header : '实际奖励日期',
						dataIndex : 'factDate'
					}]
				// end of columns
			});

		//this.gridPanel.addListener('rowdblclick', this.rowClick);

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
					new PlBidInfoForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlBidInfoForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
					url : __ctxPath
							+ '/creditFlow.financingAgency/multiDelPlBidInfo.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
					url : __ctxPath
							+ '/creditFlow.financingAgency/multiDelPlBidInfo.do',
					grid : this.gridPanel,
					idName : 'id'
				});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlBidInfoForm({
					id : record.data.id
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.id);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	},
	seeInfo : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			new RaiseBpfundIntentDetail({
			  fundType:record.data.fundType,
			  planId:record.data.planId
			}).show();
		}
			
	},
	distributeMoney :function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			var thisPanel = this.gridPanel;
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			var planId=record.data.planId;
			Ext.Msg.confirm('派息确认','此次派息系统将会检查该项目该期尚未派息的奖励进行派息',function(btn){
				if(btn=="yes"){
				Ext.MessageBox.wait('派息进行中','请稍后...');//锁屏
		    	Ext.Ajax.request({
					url : __ctxPath
							+ "/creditFlow/finance/distributeMoneyBpFundIntent.do?planId="+planId+"&fundType=raiseinterest&payintentPeriod=0",
					method : 'post',
					success : function(response, request) {
						var obj=Ext.util.JSON.decode(response.responseText)
						Ext.ux.Toast.msg('操作信息', obj.msg);
						thisPanel.getStore().reload();
						Ext.MessageBox.hide();//解除锁屏
					}
				});
				}
			})
		
		}
	},
	couponsExcel : function(){
					var bidName = this.getCmpByName("bidName").getValue();
					var bidProNumber = this.getCmpByName("bidProNumber").getValue();
					var intentDatel = this.getCmpByName("intentDatel").getValue();
					var intentDateg = this.getCmpByName("intentDateg").getValue();
					var factDatel = this.getCmpByName("factDatel").getValue();
					var factDateg = this.getCmpByName("factDateg").getValue();
					var time1 ="";
					var time2 ="";
					var time3 ="";
					var time4 ="";
					if(intentDatel!=""){
						 time1 = intentDatel.format("Y-m-d");
					}
					if(intentDateg!=""){
						 time2 = intentDateg.format("Y-m-d");
					}
					if(factDatel!=""){
						 time3 = factDatel.format("Y-m-d");
					}
					if(factDateg!=""){
						 time4 = factDateg.format("Y-m-d");
					}
					window.open( __ctxPath +"/creditFlow/finance/excelRaiseBpfundDetailSlFundIntent.do?fundType=commoninterest&bidName="+bidName+"&bidProNumber="+bidProNumber+"&" +
							"&intentDatel="+time1+"&intentDateg="+time2+"&factDatel="+time3+"&factDateg="+time4+"");
	}
});
