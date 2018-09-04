/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
PlBidSaleList = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlBidSaleList.superclass.constructor.call(this, {
					id : 'PlBidSaleList',
					layout : 'border',
					items : [this.searchPanel ,this.gridPanel],
					modal : true,
					height : 550, 
					autoWidth : true,
					boder:0,
					maximizable : true,
					//title : this.titlePrefix ,
					buttonAlign : 'center'

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var labelsize=70;
				 var labelsize1=115;
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
			                id:"PlBidSaleListsearch",
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
							anchor : '96%',
							keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.search,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
							items : [ {   columnWidth : 0.24,
								layout : 'form',
								border : false,
								labelWidth : labelsize1,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '出让人',
										name : 'outCustName',
										labelSeparator : '',
										xtype : 'textfield',
										anchor : '100%'
										
								} ] 
								      
							},{   columnWidth : 0.24,
								layout : 'form',
								border : false,
								labelWidth : labelsize1,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '受让人',
										name : 'inCustName',
										labelSeparator : '',
										xtype : 'textfield',
										anchor : '100%'
										
								} ] 
								      
							}
							, {
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
								}]}, {
								columnWidth : .07,
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
								}]}
									
									

							]

						});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : []
				});
       var params1={
       listtype:"transferingList"
       };
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'PlBidSaleListgrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlBidSale.do",
			baseParams :params1,
			fields : [{
						name : 'id',
						type : 'int'
					}, 'outCustName','inCustName','bidProName', 'yearAccrualRate', 'intentDate', 'saleMoney','saleSuccessTime','transferFee',
					'changeMoneyRate', 'changeMoneyType','changeMoney','saleStartTime', 'saleCloseTime','saleDealTime', 'sumMoney','changeMoney'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '出让人',
						width:120,
						dataIndex : 'outCustName'
					}, {
						header : '受让人',
						width:120,
						dataIndex : 'inCustName'
					}, {
						header : '债权名称',
						width:180,
						dataIndex : 'bidProName'
					}, {
						header : '到期日',
						width:110,
						dataIndex : 'intentDate'
					}, {
						header : '年化利率',
						width:100,
						dataIndex : 'yearAccrualRate',
					    align : 'right',
						renderer:function(v){
							return v+"%";
						}
						
					}, {
						header : '出让本金',
						width:120,
						dataIndex : 'saleMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '折让金',
						width:120,
						dataIndex : 'changeMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					},  {
						header : '折让率',
						width:100,
						dataIndex : 'changeMoneyRate',
						renderer:function(v){
							return v+"%";
						}
						
					}, {
						header : '结算总金额',
						width:120,
						dataIndex : 'sumMoney',
						align : 'right',
						renderer:function(v){
							if(null==v){
								return "";
							}else{
							 return Ext.util.Format.number(v,',000,000,000.00')+"元";
							}
							
						}
					}, {
						header : '服务费',
						width:120,
						dataIndex : 'transferFee',
						align : 'right',
						renderer:function(v){
							if(null==v){
								return "";
							}else{
							 return Ext.util.Format.number(v,',000,000,000.00')+"元";
							}
							
						}
					}, {
						header : '交易时间',
						width:120,
						hidden:true,
						dataIndex : 'saleSuccessTime'
					}, {
						header : '挂牌时间',
						width:120,
						dataIndex : 'saleStartTime'
					}, {
						header : '关闭时间',
						width:140,
						hidden:true,
						dataIndex : 'saleCloseTime'
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
	
	// 关闭
	bidclose : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var url =__ctxPath	+ "/creditFlow/financingAgency/bidClosePlManageMoneyPlan.do";
			Ext.Ajax.request({
						url : url,
						method : "post",
						success : function(response, opts) {
								var res = Ext.util.JSON.decode(response.responseText);
								alert("关闭成功");
								var gp=Ext.getCmp('PlManageMoneyPlanGridhpublishgrid');
								gp.getStore().reload();
						},
						params : {
							mmplanId : record.data.mmplanId
						}
					})
		}

	},
	// 预览
	preview : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			window.open(__p2pPath + "/creditFlow/financingAgency/getDetailPlManageMoneyPlan.do?mmplanId="+record.data.mmplanId,'_blank');
		}

	},
	// 发布
	publish : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var url =__ctxPath
					+ "/creditFlow/financingAgency/previewPublishPlManageMoneyPlan.do"
			Ext.Ajax.request({
						url : url,
						method : "post",
						success : function(response, opts) {
								var res = Ext.util.JSON.decode(response.responseText);
								alert("发布成功!");
							//	window.open(__p2pPath + res.htmlPath,'_blank');
						},
						params : {
							mmplanId : record.data.mmplanId,
							isPublish:true
						}
					})
		}

	},
	start:function(){
	
	var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
				new PlManageMoneyPlanForm({
						mmplanId : record.data.mmplanId,
						isAllReadOnly:true,
						isHidden:false
		 }).show();
		}

	}
,
	liubaio:function(){
	
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var url = __ctxPath
					+ "/creditFlow/financingAgency/streamBidPlManageMoneyPlan.do";
					
					
												
						Ext.Msg.confirm("提示!",'确定要流标吗？',
										function(btn) {

											if (btn == "yes") {
											
												Ext.Ajax.request({
												url : url,
												method : "post",
												scope:this,
												success : function(response, opts) {
													Ext.ux.Toast.msg('操作信息', '流标成功!');
												
												},
												params : {
													mmplanId : record.data.mmplanId
													
												}
											})
												
												
												
											}
										  }
											
											)
		
		}

	
	
	}
});
