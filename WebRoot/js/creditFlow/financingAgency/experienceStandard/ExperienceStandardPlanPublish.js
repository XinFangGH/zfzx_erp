/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
ExperienceStandardPlanPublish = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ExperienceStandardPlanPublish.superclass.constructor.call(this, {
					id : 'ExperienceStandardPlanPublish',
					layout : 'border',
					items : [this.gridPanel,this.searchPanel],
					modal : true,
					height : 550, 
					autoWidth : true,
					boder:0,
					maximizable : true,
					buttonAlign : 'center'

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
			        id:'searchPanelExperience',
					layout : 'form',
					region : 'north',
					border : false,
					height : 65,
					anchor : '70%',
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						items : [{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '招标编号',
								name : 'Q_mmNumber_S_LK',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '招标名称',
								name : 'Q_mmName_S_LK',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .1,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:50px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
						}]},{
							columnWidth : .1,
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
								id:"addExperience",
								text : '增加体验标',
								xtype : 'button',
								scope : this,
								hidden:this.addExperience,
								handler : this.addExperience
							},{
								iconCls : 'btn-edit',
								id:"editExperience",
								text : '修改体验标',
								xtype : 'button',
								scope : this,
								hidden:this.editExperience,
								handler : this.editExperience
							},{
								iconCls : 'btn-notice',
								id:"publishExperience",
								text : '发布',
								xtype : 'button',
								scope : this,
								hidden:this.nopublish,
								handler : this.publish
							}, {
								iconCls : 'btn-transition1',
								id:"startExperience",
								text : '起息',
								xtype : 'button',
								scope : this,
								hidden:this.yespublish,
								handler : this.start
							}, {
								iconCls : 'btn-detail',
								id:"seeExperience",
								text : '查看',
								xtype : 'button',
								scope : this,
								hidden:this.seemmplan,
								handler : this.seeMMplan
							}, {
								iconCls : 'close',
								id:"closeExperience",
								text : '关闭',
								xtype : 'button',
								scope : this,
								handler : this.bidclose
							}, {
								iconCls : 'btn-finish',
								text : '完成',
								id:"fininshExperience",
								xtype : 'button',
								scope : this,
								handler : this.fininsh
							}]
				});
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
        var params1={Q_keystr_S_EQ:"experience",Q_state_N_EQ:0,isPresale:'no'};
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			// 使用RowActions
			id : 'ExperienceStandardPlanPublishGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlan.do",
			baseParams :params1,
			fields : [{
						name : 'mmplanId',
						type : 'int'
					}, 'mmName', 'mmNumber', 'keystr',
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
						header : '招标名称',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'mmName',
						width : 200
					}, {
						header : '招标编号',
						align:'center',
						dataIndex : 'mmNumber',
						width : 150
					}, {
						header : '招标金额',
						dataIndex : 'sumMoney',
						summaryType : 'sum',
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
							return v+"天";
						}
					}, {
						header : '还款方式',
						align : 'right',
						renderer:function(v){
							return "一次性付息";
						}
					}, {
						header : '付息周期',
						align : 'right',
						renderer:function(v){
							return "按日付息";
						}
					},  {
						header : '购买放开时间',
						align:'center',
						dataIndex : 'buyStartTime'
					}, {
						header : '购买截止时间',
						align:'center',
						dataIndex : 'buyEndTime'
					}, {
						header : '创建时间',
						align:'center',
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
			var url =__ctxPath	+ "/creditFlow/financingAgency/bidCloseExperiencePlManageMoneyPlan.do";
			Ext.Ajax.request({
						url : url,
						method : "post",
						success : function(response, opts) {
								var res = Ext.util.JSON.decode(response.responseText);
								Ext.ux.Toast.msg('操作信息', res.msg);
								var gp=Ext.getCmp('ExperienceStandardPlanPublishGrid');
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
					+ "/creditFlow/financingAgency/previewPublishExperiencePlManageMoneyPlan.do"
			Ext.Ajax.request({
						url : url,
						method : "post",
						success : function(response, opts) {
								var res = Ext.util.JSON.decode(response.responseText);
								if(res.success){
									Ext.ux.Toast.msg('操作信息', res.msg);
								}else{
									Ext.ux.Toast.msg('操作信息', res.msg);
								}
								var gp=Ext.getCmp('ExperienceStandardPlanPublishGrid');
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
				new ExperienceStandardPlanForm({
						mmplanId : record.data.mmplanId,
						isAllReadOnly:true,
						isHidden:false,
						isAutocreate:true,
						state:record.data.state
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
				new ExperienceStandardPlanForm({
						mmplanId : record.data.mmplanId,
						isAllReadOnly:true,
						isHidden:true,
						seeHidden:true,
						state:record.data.state
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
	                            		var gp=Ext.getCmp('ExperienceStandardPlanPublishGrid');
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
	},
	
	//添加
	addExperience:function(){
		new ExperienceStandardPlanForm({
		    isAllReadOnly:false,
			isCreate:true,
			isHidden:true,
			bidRemark:"<p>&nbsp;</p><p class=\"middle\" style=\"margin: 0px; padding: 0px 0px 5px; list-style: none; font-size: 14px; line-height: 26px; font-family: 'Microsoft Yahei'; color: rgb(102, 102, 102);\"><span style=\"font-family: Arial, Verdana, sans-serif; font-size: 12px\">1. 本产品为新用户体验产品而设计，模拟真实标的投标和收益过程，并为新用户赠送投资收益，作为活动奖励。"+
			"</span></p><p class=\"middle\" style=\"margin: 0px; padding: 0px 0px 5px; list-style: none; font-size: 14px; line-height: 26px; font-family: 'Microsoft Yahei'; color: rgb(102, 102, 102);\"><span style=\"font-family: Arial, Verdana, sans-serif; font-size: 12px;\">2. 投资人需绑定手机，并通过实名认证，方可体验本虚拟产品，且每个新用户仅有一次新产品体验机会，投资上限1000元。" +
					"</span></p><p class=\"middle\" style=\"margin: 0px; padding: 0px 0px 5px; list-style: none; font-size: 14px; line-height: 26px; font-family: 'Microsoft Yahei'; color: rgb(102, 102, 102);\"><span style=\"font-family: Arial, Verdana, sans-serif; font-size: 12px;\">3. 对于涉嫌骗取活动奖励的用户，网站有权不发放奖励。" +
							"</span></p><p class=\"middle\" style=\"margin: 0px; padding: 0px 0px 5px; list-style: none; font-size: 14px; line-height: 26px; font-family: 'Microsoft Yahei'; color: rgb(102, 102, 102);\"><span style=\"font-family: Arial, Verdana, sans-serif; font-size: 12px;\">4. 本活动最终解释权归本网站。</span></p><p>&nbsp;</p>"
			/*"1. 本产品为新用户体验产品而设计，模拟真实标的投标和收益过程，并为新用户赠送投资收益，作为活动奖励。"+
                      "2. 投资人需绑定手机，并通过实名认证，方可体验本虚拟产品，且每个新用户仅有一次新产品体验机会，投资上限1000元。"+
                      "3. 对于涉嫌骗取活动奖励的用户，网站有权不发放奖励。"+
                      "4. 本活动最终解释权归本网站。"*/
		}).show();
	},
	
	// 编辑Rs
	editExperience : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			if(s[0].data.state>0){
				alert("该体验标已经发布不能进行编辑！只能查看。");
				new ExperienceStandardPlanForm({
					mmplanId : s[0].data.mmplanId,
					isAllReadOnly:true,
					isHidden:true
				}).show();
			}else{
				new ExperienceStandardPlanForm({
					mmplanId : s[0].data.mmplanId,
					isAllReadOnly:s[0].data.state==0?false:true,
					isHidden:true
			    }).show();
			}
			
		}
	}
});
