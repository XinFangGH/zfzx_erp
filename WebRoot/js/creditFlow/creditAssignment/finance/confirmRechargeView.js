//confirmRechargeView
confirmRechargeView = Ext.extend(Ext.Panel, {
	rechargeConfirmStatus : 1,
	dealRecordStatus:2,
	titlePrefix : "",
	seniorHidden:false,
	Confirmhidden:false,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.rechargeConfirmStatus) != "undefined") {
			this.rechargeConfirmStatus =_cfg.rechargeConfirmStatus;
		}
		if (typeof(_cfg.dealRecordStatus) != "undefined") {
			this.dealRecordStatus =_cfg.dealRecordStatus;
		}
		if(this.dealRecordStatus=='1'){
			this.titlePrefix="充值一次审核录";
		}else if(this.dealRecordStatus=='23'){
			this.titlePrefix="充值记录查询";
			this.Confirmhidden=true;
		}else if(eval(this.dealRecordStatus)==eval(3)){
			this.titlePrefix="充值记录查询";
			this.Confirmhidden=true;
		}
		Ext.applyIf(this, _cfg);
		
		// 初始化组件
		this.initUIComponents();
		
		// 调用父类构造
		confirmRechargeView.superclass.constructor.call(this, {
			id : 'confirmRechargeView_'+this.rechargeConfirmStatus+this.dealRecordStatus,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [ this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};
		
		this.searchPanel = new Ext.FormPanel({
//			rechargeLevel:this.rechargeLevel,
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
						
						name : 'rechargeLevel',
						anchor : "100%",
						xtype : 'hidden',
						value: this.rechargeLevel
					},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '投资人',
						name : 'investPersonName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},/*{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					hidden:this.seniorHidden,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '确认状态',
						name : 'seniorValidationRechargeStatus',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'combo'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					hidden:this.Confirmhidden,
					border : false,
					items : [{
						fieldLabel : '确认状态',
						name : 'rechargeConfirmStatus',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'combo',
						value:this.rechargeConfirmStatus
					}]
		     	},*/{
	     			columnWidth :.125,
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
					}]
	     		},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :50,
					items :[{
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
		/*this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看投资人信息',
					hidden : isGranted('_see_investPDetail_' + this.rechargeLevel)? false: true,
					xtype : 'button',
					scope : this,
					handler : function() {
						var rows = this.gridPanel.getSelectionModel().getSelections();
						if (rows.length == 0) {
							Ext.ux.Toast.msg('操作信息', '请选择记录!');
							return;
						} else if (rows.length > 1) {
							Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
							return;
						} else {
							Ext.Ajax.request({
								url : __ctxPath+ '/creditFlow/creditAssignment/customer/seePersonInfoCsInvestmentperson.do',
								method : 'POST',
								scope : this,
								preName :['csInvestmentPerson'],
								success : function(response, request) {
									var obj = Ext.util.JSON.decode(response.responseText);
									var personData = obj.data.csInvestmentPerson;
									var accountData=obj.data.obSystemAccount;
									var randomId = rand(100000);
									var id = "see_person" + randomId;
									var anchor = '100%';
									var window_see = new Ext.Window({
												title : '查看投资人信息',
												layout : 'fit',
												width : (screen.width - 180) * 0.7+ 160,
												width : document.body.clientWidth - 500,
												height : document.body.clientHeight - 400,
												maximizable : true,
												closable : true,
												modal : true,
												plain : true,
												border : false,
												items : [new investmentObj({
															tempTitle:'投资人基本信息',
															id : id,
															personData : personData,
															accountData:accountData,
															isReadOnly : true,
															isHidden:false
														})],
												listeners : {
													'beforeclose' : function(panel) {
														window_see.destroy();
													}
												}
											});
									window_see.show();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试');
								},
								params : {
									investId : rows[0].data.investPersonId
								}
							});
						}
					}	
				}]
			});*/
			
			var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		var checkColumn = new Ext.grid.CheckColumn({
			header : '确认状态',
			width : 90,
			fixed : true,
			hidden:this.Confirmhidden,
			disabled:this.Confirmhidden?false:true,
			dataIndex : 'rechargeConfirmStatus'
		});
		if(this.dealRecordStatus=="1"){
		
			this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-pass',
									text : '审批通过',
									xtype : 'button',
									scope : this,
									handler : this.approvalAass
								},{
									iconCls : 'btn-clear',
									text : '审批驳回',
									xtype : 'button',
									scope : this,
									handler : this.approvalReject
								}]
					});
		}
		
		this.gridPanel = new HT.GridPanel({
			name : 'confirmRechargeGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			// 不适用RowActions
			plugins : [checkColumn,summary],
			rowActions : false,
			url : __ctxPath + "/creditFlow/creditAssignment/bank/getreChargeListObAccountDealInfo.do?transferType=1&dealRecordStatus="+this.dealRecordStatus,
			fields : [{
				name : 'id',
				type : 'int'
			}, 'accountId','incomMoney', 'payMoney', 'transferType', 'shopId', 'shopName',
					'createId', 'dealType', 'createDate','investPersonId',
					'transferDate', 'rechargeLevel', 'investPersonName', 'createName','bankName', 'bankNum', 'areaName', 'createName','rechargeConfirmStatus','seniorValidationRechargeStatus','dealRecordStatus'],
			listeners : {
				scope : this,
				afteredit : function(e) {
					if (e.originalValue != e.value){
						var args ;
						if(e.field =='rechargeConfirmStatus'){
								args = {
										'rechargeConfirmStatus' : e.value,
										'rechargeLevel':e.record.data['rechargeLevel'],
										'id' : e.record.data['id']
							}
						}
						if(e.field =='rechargeConfirmStatus' ||e.field =='seniorValidationRechargeStatus'){
							var panel =this;
							Ext.Ajax.request({
										url : __ctxPath + "/creditFlow/creditAssignment/bank/ConfirmStatusObAccountDealInfo.do",
										method : 'POST',
										scope :this,
										success : function(response, request) {
											e.record.commit();
											panel.getCmpByName("confirmRechargeGrid").getStore().reload();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试！');
										},
										params: args
									})
						}
					}
				}
			},

			columns : [{
				header : 'id',
				dataIndex : 'id',
				align:'center',
				hidden : true
			}, {
				header : 'investmentPersonId',
				dataIndex : 'investPersonId',
				align:'center',
				hidden : true
			}, {
				header : '投资人姓名',
				width : 100,
				summaryRenderer : totalMoney,
				dataIndex : 'investPersonName'
			}, {
				header : '充值金额(元)',
				width : 130,
				align:'right',
				summaryType : 'sum',
				dataIndex : 'incomMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			}, {
				header : '交易时间',
				width : 130,
				align:'center',
				dataIndex : 'transferDate'
			}, {
				header : '交易状态',
				width : 130,
				align:'center',
				dataIndex : 'dealRecordStatus',
				renderer : function(value){
					if(value=="1"){
						return "未审核";
					}else if(value=="2"){
						return "已通过";
					}else if(value=="3"){
						return "已驳回";
					}
				}
			},/* {
				header : '充值账户',
				width : 68,
				dataIndex : 'bankName'
			}, {
				header : '充值账号',
				align : 'right',
				width : 110,
				sortable : true,
				dataIndex : 'bankNum'/hurong_bugfix_p2p4.0.0.1/src/conf/sendmessage_config.properties
			}, {
				header : '开户行',
				width : 70,
				dataIndex : 'areaName'
			},*/ {
				header : '门店名称',
				width : 70,
				dataIndex : 'shopName'
			}, {
				header : '操作人',
				width : 70,
				dataIndex : 'createName'
			}]
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
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
	},
	//审批通过
	approvalAass :function(){
		var gp = this.gridPanel;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					Ext.Msg.confirm('提示','是否通过审批',function(btn){
						if(btn=="yes"){
							var record=selectRs[0];
							var url =__ctxPath + "/creditFlow/creditAssignment/bank/updateApprovalObAccountDealInfo.do?approvalState=2&ids="+record.data.id
							Ext.Ajax.request({
								url:url,
								method:"post",
								success:function(response,opts){
									gp.getStore().reload();
									Ext.ux.Toast.msg('状态', '已审批通过');
								}
							})
						}
					})
				}
	},
	//审批驳回
	approvalReject :function(){
		var gp = this.gridPanel;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					Ext.Msg.confirm('提示','是否驳回审批',function(btn){
						if(btn=="yes"){
							var record=selectRs[0];
							var url =__ctxPath + "/creditFlow/creditAssignment/bank/updateApprovalObAccountDealInfo.do?approvalState=3&ids="+record.data.id
							Ext.Ajax.request({
								url:url,
								method:"post",
								success:function(response,opts){
									gp.getStore().reload();
									Ext.ux.Toast.msg('状态', '已驳回');
								}
							})
						}
					})
				}
	}
	
});
