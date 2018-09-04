// mappingObligationToInvestView 为投资人匹配债权
mappingObligationToInvestView = Ext.extend(Ext.Panel, {
	hiddenInfo : false,
	hiddenMapping : false,
	obligationState : "",// 用来区分查找全部的债权产品还是未匹配的债权产品
	// 构造函数
	constructor : function(_cfg) {
		this.titlePrefix = "投资人信息";
		this.obligationState = 1;
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents(); // 调用父类构造
		mappingObligationToInvestView.superclass.constructor.call(this, {
					id : 'mappingObligationToInvestView',
					title : this.titlePrefix,
					region : 'center',
					layout : 'border',
					iconCls : "btn-tree-team17",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};

		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig : {
				align : 'middle'
			},
			bodyStyle : 'padding:15px 10px 10px 10px',
			items : [{
						xtype : 'hidden',
						name : "obligationState",
						value : this.obligationState
					}, {
						columnWidth : .4,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						hidden : this.hiddenMapping,
						border : false,
						items : [{
									fieldLabel : '债权人',
									name : 'investName',
									triggerClass : 'x-form-search-trigger',
									xtype : 'combo',
									onTriggerClick : function() {
										var searchButton=this.ownerCt.ownerCt.ownerCt;
										var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
										var htmlValue = this.ownerCt.ownerCt.ownerCt.gridPanel.getTopToolbar().items.last();
										var EnterpriseNameStockUpdateNew = function(obj) {
											op.getCmpByName('investId').setValue("");
											op.getCmpByName('investName').setValue("");
											if (0 != obj.investId&& "" != obj.investId){
												op.getCmpByName('investId').setValue(obj.investId);
												searchButton.search();
												
											}
											if (null != obj.investName&& "" != obj.investName){
												op.getCmpByName('investName').setValue(obj.investName);	
											}
										}
										selectInvesPerson(EnterpriseNameStockUpdateNew);

									}
								},{
									name : 'investId',
									xtype : 'field',
									hidden:true
								}]
					}, {
						columnWidth : .125,
						layout : 'form',
						border : false,
						labelWidth : 80,
						items : [{
									text : '查询',
									xtype : 'button',
									scope : this,
									style : 'margin-left:30px',
									anchor : "90%",
									iconCls : 'btn-search',
									handler : this.search
								}]
					}, {
						columnWidth : .125,
						layout : 'form',
						border : false,
						items : [{
									text : '重置',
									style : 'margin-left:30px',
									xtype : 'button',
									scope : this,
									width : 40,
									iconCls : 'btn-reset',
									handler : this.reset
								}]
					}]
		});

		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '添加债权',
						xtype : 'button',
						scope : this,
						 hidden : isGranted('_create_obligation' + this.projectStatus)? false: true,
						handler : this.addinvestObligation
					},{
						iconCls : 'btn-add',
						text : '查看债权',
						xtype : 'button',
						scope : this,
						 hidden : isGranted('_create_obligation' + this.projectStatus)? false: true,
						handler : this.seeinvestObligation
					}, {
						iconCls : 'btn-del',
						text : '撤销债权',
						xtype : 'button',
						scope : this,
						 hidden : isGranted('_revocate_obligation' + this.projectStatus)? false: true,
						handler : this.canelInvestObligation
					}, {
						iconCls : 'btn-agree',
						text : '业务受理',
						xtype : 'button',
						scope : this,
						 hidden : isGranted('_revocate_obligation' + this.projectStatus)? false: true,
						handler : this.businessManager
					}, '->', {
						xtype : 'label',
						html : '【<font color="purple">投资人：</font>&nbsp;&nbsp;<font color="purple">身份证号码：</font>&nbsp;&nbsp;<font color="blue">账户余额：</font>&nbsp;&nbsp;<font color="red">通讯地址：</font>】'
					}]
		});

		this.gridPanel = new HT.GridPanel({
			name : 'obligationMappingInfoGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			rowActions : false,
			url : __ctxPath
					+ "/creditFlow/creditAssignment/bank/listInvestPersonByPersonIdObObligationInvestInfo.do?obligationState="
					+ this.obligationState,
			baseParams : [{
						investPersonId : this.investPersonId
					}],
			fields : [{
						name : 'id',
						type : 'int'
					}, 'investMentPersonId', 'obligationId', 'obligationName',
					'obligationAccrual', 'investQuotient', 'investMoney',
					'investStartDate', 'investEndDate', 'shopId', 'creatorId',
					'companyId', 'investObligationStatus', 'systemInvest',
					'investRate', 'orgName', 'creatorName', 'investName',
					'cardNumber', 'cellPhone', 'projectName', 'projectNumber',
					'obligationNumber', 'projectMoney', 'minInvestMent',
					'totalQuotient', 'payintentPeriod'],
			listeners : {
				scope : this,
				afteredit : function(e) {
				}
			},
			columns : [{
						header : '债权人名称',
						width : 100,
						hidden : this.hiddenInfo,
						dataIndex : 'investName'
					}, {
						header : '匹配项目',
						width : 200,
						dataIndex : 'projectName'
					}, {
						header : '项目期限(月)',
						width : 100,
						dataIndex : 'payintentPeriod'
					}, {
						header : '投资份额',
						width : 100,
						dataIndex : 'investQuotient',
						renderer : function(value) {
							if (value == "") {
								return "";
							} else {
								return value + "份";
							}
						}
					}, {
						header : '投资金额（元）',
						width : 100,
						dataIndex : 'investMoney',
						hidden : this.hiddenInfo,
						renderer : function(value) {
							if (value == "") {
								return "0.00";
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '资金投资日',
						align : 'right',
						width : 110,
						sortable : true,
						hidden : this.hiddenInfo,
						dataIndex : 'investStartDate'
					}, {
						header : '债权截止日',
						align : 'right',
						width : 110,
						sortable : true,
						hidden : this.hiddenInfo,
						dataIndex : 'investEndDate'
					}, {
						header : '债权状态',
						width : 100,
						dataIndex : 'investObligationStatus',
						hidden : this.hiddenMapping,
						renderer : function(value) {
							if (value == 1) {
								return "进行中";
							} else if(value == 2) {
								return "已结束";
							} else  {
								return "未开始";
							}
						}
					}]
		});
		/*
		 * this.gridPanel.addListener('afterrender', function() { this.loadMask1 =
		 * new Ext.LoadMask(this.gridPanel.getEl(), { msg :
		 * '正在加载数据中······,请稍候······', store : this.gridPanel.store, removeMask :
		 * true // 完成后移除 }); this.loadMask1.show(); // 显示 }, this);
		 */

	},// end of the initComponents()

	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		var investPersonId = this.getCmpByName("investId").getValue();
		var htmlValue = this.gridPanel.getTopToolbar().items.last();
		
		if (investPersonId != null && investPersonId != "") {
			Ext.Ajax.request({
				url : __ctxPath
						+ "/creditFlow/creditAssignment/bank/getInvestPersonInfoObSystemAccount.do",
				params : {
					investId : investPersonId
				},
				method : 'POST',
				success : function(response) {
					var obj = Ext.util.JSON.decode(response.responseText);
					var msg = obj.msg;
					if (msg != "") {
						htmlValue.setText('<pre>【 <font color="blue">' + msg
										+ ' </font>】 </pre>', false)

					}
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
				}
			});
		}
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// 添加投资人的债权
	addinvestObligation : function() {
		var investPersonId = this.getCmpByName("investId").getValue();
		var investPersonName = this.getCmpByName("investName").getValue();
		if (investPersonId!=null&&investPersonId!="" ) {
			var addwindow = new addInvestObligation({
						investPersonId : investPersonId,
						investPersonName : investPersonName,
						
						idDefinition : "addinvestObligation",
						gridPanel : this.gridPanel,
						isReadOnly : false
					});
			addwindow.show();
		} else {
			Ext.ux.Toast.msg('状态', '请先选择投资人');
		}
	},
	seeinvestObligation:function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length > 1) {
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		} else if (rows.length < 1) {
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		} else {
			var investPersonId = this.getCmpByName("investId").getValue();
			var investPersonName = this.getCmpByName("investName").getValue();
			var record =rows[0];
			var addwindow = new addInvestObligation({
						investPersonInfo:record.data.id,
						investPersonId : investPersonId,
						investPersonName : investPersonName,
						obligationId:record.data.obligationId,
						idDefinition : "seeinvestObligation",
						gridPanel : this.gridPanel,
						isReadOnly:true
					});
			addwindow.show();
			
		}
	},
	// 撤销投资人债权
	canelInvestObligation : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length > 1) {
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		} else if (rows.length < 1) {
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		} else {
			var record =rows[0];
			var investObligationStatus = record.data.investObligationStatus;// 债权信息的状态
			if (investObligationStatus == 1||investObligationStatus == 2) {
				Ext.ux.Toast.msg('状态', '进行中和已经结束的债权不能删除!');
				return;
			} else {
					var thisPanel = this.gridPanel;
				Ext.Msg.confirm('信息确认', '是否确认删除债权', function(btn) {
					if (btn == 'yes') {
						var investObligationid = record.data.id;// 债权表的id
						var investMentPersonId = record.data.investMentPersonId;// 债权表的投资人的id
						var obligationId =  record.data.obligationId;// 债权产品的id
					
						Ext.Ajax.request({
							url : __ctxPath
									+ "/creditFlow/creditAssignment/bank/delInvestObligationObObligationInvestInfo.do",
							params : {
								investObligationid : investObligationid,
								obligationId : obligationId,
								investMentPersonId : investMentPersonId
							},
							method : 'POST',
							success : function(response) {
								var obj = Ext.util.JSON.decode(response.responseText);
								var msg = obj.msg;
								thisPanel.getStore().setBaseParam("investPersonId",investMentPersonId);
								thisPanel.getStore().reload();
								Ext.ux.Toast.msg('状态', msg);
							},
							failure : function(fp, action) {
								Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
							}
						});
					}
				})
			}
		}

	},
	//业务受理方法
	businessManager:function(){
		
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length > 1) {
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		} else if (rows.length < 1) {
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		} else {
			var record =rows[0];
			Ext.Msg.confirm('信息确认', '是否确认启动业务受理流程', function(btn) {
				if (btn == 'yes') {
					var investObligationid = record.data.id;// 债权表的id
					var investMentPersonId = record.data.investMentPersonId;// 债权表的投资人的id
					var obligationId =  record.data.obligationId;// 债权产品的id
					var thisPanel = this.gridPanel;
					Ext.Ajax.request({
						url : __ctxPath+ "/obligation/invest/businessManagerObObligationInvestInfo.do",
						params : {
							id : investObligationid,
							obligationId : obligationId,
							investMentPersonId : investMentPersonId
						},
						method : 'POST',
						success : function(response) {
							var obj = Ext.util.JSON.decode(response.responseText);
							var msg = obj.msg;
							thisPanel.getStore().setBaseParams("investPersonId",investMentPersonId);
							thisPanel.getStore().reload();
							Ext.ux.Toast.msg('状态', msg);
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : 'ext-mb-error'
									});
						}
					});
				}
			})
			
		}

	
	}
});
