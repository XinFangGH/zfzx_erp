/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
PlManageMoneyPlanBidPublish = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyPlanBidPublish.superclass.constructor.call(this, {
					id : 'PlManageMoneyPlanBidPublish',
					layout : 'border',
					items : [this.gridPanel],
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
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					region : 'north',
					colNums : 3,
					items : [{
								fieldLabel : '包装id',
								name : 'Q_packId_L_EQ',
								flex : 1,
								xtype : 'numberfield'
							}],
					buttons : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [/*{
								iconCls : 'btn-add',
								id:"previewmmplan",
								text : '预览',
								xtype : 'button',
								scope : this,
								//hidden:true,//this.nopublish,
								handler : this.preview
							}, */{
								iconCls : 'btn-notice',
								id:"publishmmplan",
								text : '发布',
								xtype : 'button',
								scope : this,
								hidden:this.nopublish,
								handler : this.publish
							}, {
								iconCls : 'btn-transition1',
								id:"startmmplan",
								text : '起息',
								xtype : 'button',
								scope : this,
								hidden:this.yespublish,
								handler : this.start
							}, {
								iconCls : 'btn-detail',
								id:"seemmplan",
								text : '查看',
								xtype : 'button',
								scope : this,
								hidden:this.seemmplan,
								handler : this.seeMMplan
							}, {
								iconCls : 'close',
								id:"closemmplan",
								text : '关闭',
								xtype : 'button',
								scope : this,
								handler : this.bidclose
							}, {
								iconCls : 'btn-rights',
								text : '授权平台派息',
								xtype : 'button',
								id:"Shouquanpaixi",
								scope : this,
								handler : this.shouquanpaixi
							}, {
								iconCls : 'btn-add',
								text : '完成',
								id:"fininsh",
								xtype : 'button',
								scope : this,
								handler : this.fininsh
							}]
				});
       var params1={
       Q_keystr_S_EQ:"mmplan",
       Q_state_N_EQ:0
       };
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'PlManageMoneyPlanGridhpublishgrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlan.do",
			baseParams :params1,
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
					'htmlPath', 'createtime', 'updatetime','authorityStatus'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						hidden : true
					}, {
						header : '理财计划名称',
						dataIndex : 'mmName'
					}, {
						header : '理财计划编号',
						dataIndex : 'mmNumber'
					}, {
						header : '派息授权状态',
						dataIndex : 'authorityStatus',
						renderer:function(v){
							if(v==1){
							  return "是"
							}else return "否"
						}
					}, {
						header : '招标金额',
						dataIndex : 'sumMoney',
					    align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
						
					}, {
						header : '年化收益率',
						dataIndex : 'yeaRate',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"%";
						}
					}, {
						header : '投资期限',
						dataIndex : 'investlimit',
						align : 'right',
						renderer:function(v){
							return v+"月";
						}
					},  {
						header : '购买放开时间',
						dataIndex : 'buyStartTime'
					}, {
						header : '购买截止时间',
						dataIndex : 'buyEndTime'
					}/*, {
						header : 'state',
						dataIndex : 'state'
					}, {
						header : '说明',
						dataIndex : 'bidRemark'
					}*/, {
						header : '创建时间',
						dataIndex : 'createtime'
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
									Ext.ux.Toast.msg('操作信息', "发布成功");
										var gp=Ext.getCmp('PlManageMoneyPlanGridhpublishgrid');
								gp.getStore().reload();
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

	},
	seeMMplan:function(){
	
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
						isHidden:true,
						seeHidden:true
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

	
	
	},
	
	fininsh:function(){
	var gp = this.gridPanel;
	var Q_proType_S_EQ=this.Q_proType_S_EQ;
	 Ext.Msg.confirm("提示!", '确定要设置状态为 成完吗？', function(btn) {
	 	if (btn == "yes") {
			var selectRs = gp.getSelectionModel().getSelections();
			if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				var record = selectRs[0];
				var url =__ctxPath
						+ "/creditFlow/financingAgency/updateStatePlManageMoneyPlan.do?state=10"
				Ext.Ajax.request({
							url : url,
							method : "post",
							success : function(response, opts) {
									var obj=Ext.util.JSON.decode(response.responseText);
                            		if(obj.flag=="0"){					                            			
                            			Ext.ux.Toast.msg('操作信息',"该理财计划还未派完息，不能完成");
                            		}else{
	                            		var gp=Ext.getCmp('PlManageMoneyPlanGridhpublishgrid');
									    gp.getStore().reload();
									    Ext.ux.Toast.msg('操作信息', '设置成功!');
                            		}
									
										
							},
							params : {
								mmplanId : record.data.mmplanId
							}
						})
			}
	 	}
	});
	
	},
	//授权平台派息
	shouquanpaixi:function(){
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
					+ "/creditFlow/financingAgency/autoAuthorityPlManageMoneyPlan.do?mmplanId="+record.data.mmplanId;
			window.open(
										url,
										'平台普通资金账户充值',
										'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no',
										'_blank');
			
					
					
												
						/*Ext.Msg.confirm("提示!",'确定要流标吗？',
										function(btn) {
											if (btn == "yes") {
												Ext.Ajax.request({
												url : url,
												method : "post",
												scope:this,
												success : function(response, opts) {
													var obj=Ext.util.JSON.decode(response.responseText);
													var flag=obj.flag;
													if(eval(flag)==eval(1)){
														response.write(obj.htmlPath);
													}else{
														Ext.ux.Toast.msg('操作信息', obj.htmlPath);
													}
													
												
												},
												params : {
													mmplanId : record.data.mmplanId
													
												}
											})
												
												
												
											}
										  }
											
											)*/
		
		}
	}
});
