//PlateFormFianceTransferFeeDetailView
PlateFormFianceTransferFeeDetailView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlateFormFianceTransferFeeDetailView.superclass.constructor.call(this, {
					id : 'PlateFormFianceTransferFeeDetailView',
					title:"债权交易手续费台账",
					layout : 'border',
					items : [this.searchPanel ,this.gridPanel],
					iconCls:"btn-tree-team39",
					buttonAlign : 'center'

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		var labelsize=70;
		var labelsize1=115;
		var anchor="100%";
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
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
							items : [ {   columnWidth : 0.15,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '出让人',
										name : 'outCustName',
										labelSeparator : '',
										xtype : 'textfield',
										anchor : '100%'
										
								} ] 
								      
							},{   columnWidth : 0.15,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '受让人',
										name : 'inCustName',
										labelSeparator : '',
										xtype : 'textfield',
										anchor : '100%'
										
								} ] 
								      
							},{
										columnWidth : .15,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '挂牌日期从:',
											name : "startDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d'
										}]
							         },{
							         	columnWidth : .12,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 20,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '到',
											name : "endDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d'
										}]
							         },{   columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '交易成功日：从',
										name : 'saleSuccessTimel',
										labelSeparator : '',
										xtype : 'datefield',
										format:"Y-m-d",
										anchor : '100%'
										
								} ] 
								      
							},{   columnWidth : 0.1,
								layout : 'form',
								border : false,
								labelWidth : 20,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '到',
										name : 'saleSuccessTimeg',
										labelSeparator : '',
										format:"Y-m-d",
										xtype : 'datefield',
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

	    if(this.keystr=="transferFee"){
	    	this.topbar = new Ext.Toolbar({
				items : []
			});
	    }else{
	    	this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-ok',
					text : '确认实收手续费',
					xtype : 'button',
					scope : this,
					hidden:true,
					handler : this.confirmtransferFee
				},{
					text : '导出到Excel',
					iconCls : 'btn-xls',
					scope : this,
					hidden : isGranted('_export_grkh') ? false : true,
					handler : function() {
						var outCustName=this.getCmpByName("outCustName").getValue();//出让人
						var inCustName=this.getCmpByName("inCustName").getValue();//受让人
						var startDate=this.getCmpByName("startDate").getValue();//挂牌开始日期
						if(startDate){
							startDate=startDate.format('Y-m-d');
						}
						var endDate=this.getCmpByName("endDate").getValue();//挂牌截止日期
						if(endDate){
							endDate=endDate.format('Y-m-d');
						}
						var saleSuccessTimel=this.getCmpByName("saleSuccessTimel").getValue();//交易成功开始日期
						if(saleSuccessTimel){
							saleSuccessTimel=saleSuccessTimel.format('Y-m-d');
						}
						var saleSuccessTimeg=this.getCmpByName("saleSuccessTimeg").getValue();//交易成功结束日期
						if(saleSuccessTimeg){
							saleSuccessTimeg=saleSuccessTimeg.format('Y-m-d');
						}
						window.open(__ctxPath+ '/creditFlow/financingAgency/outputExcelPlBidSale.do?listtype=transferFianceList' +
									'&outCustName='+outCustName+'&inCustName='+inCustName+'' +
									'&startDate='+startDate+"&endDate="+endDate+"" +
								    "&saleSuccessTimel="+saleSuccessTimel+"&saleSuccessTimeg="+saleSuccessTimeg,'_blank');
					}
				}]
			});
	    }
		
       var params1={
      	 listtype:"transferFianceList"
       };
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			// 使用RowActions
			tbar:this.topbar,
			id : 'PlateFormFianceTransferFeeDetailGrid',
			plugins : [summary],
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlBidSale.do?listtype=transferFianceList",
			fields : [{
						name : 'id',
						type : 'int'
					}, 'outCustName','inCustName','bidProName', 'yearAccrualRate', 'intentDate', 'saleMoney','saleSuccessTime','transferFee','preTransferFee',
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
						summaryType : this.keystr=="transferFee"?"count":'',
						
				        summaryRenderer : totalMoney,
						dataIndex : 'bidProName'
					},/* {
						header : '到期日',
						width:110,
						dataIndex : 'intentDate'
					}, {
						header : '年化利率',
						width:100,
						dataIndex : 'yearAccrualRate',
					    align : 'center',
						renderer:function(v){
							return v+"%";
						}
						
					},*/ {
						header : '出让本金',
						width:120,
						dataIndex : 'saleMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					},{
						header : '挂牌时间',
						width:120,
						dataIndex : 'saleStartTime'
					}, /*{
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
						align : 'center',
						dataIndex : 'changeMoneyRate',
						renderer:function(v){
							return v+"%";
						}
						
					},*/ {
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
					},{
						header : '预收服务费',
						summaryType :"sum",
						width:120,
						dataIndex : 'preTransferFee',
						align : 'right',
						renderer:function(v){
							if(null==v){
								return "";
							}else{
							 return Ext.util.Format.number(v,',000,000,000.00')+"元";
							}
							
						}
					}, {
						header : '实收服务费',
						summaryType :"sum",
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
						header : '交易成功时间',
						width:120,
						dataIndex : 'saleSuccessTime'
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
	
	// 预收转实收确认
	confirmtransferFee : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var confirmtransferFee=new ConfirmTransferFeeWindow({
				id:record.data.id
			})
			confirmtransferFee.show();
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
