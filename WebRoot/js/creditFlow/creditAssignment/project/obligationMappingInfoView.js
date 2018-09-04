//obligationMappingInfoView债权匹配情况
obligationMappingInfoView = Ext.extend(Ext.Panel, {
	hiddenInfo:false,
	hiddenMapping:false,
	obligationState:"",//用来区分查找全部的债权产品还是未匹配的债权产品
	// 构造函数
	constructor : function(_cfg) {
		this.titlePrefix="债权匹配情况";
		this.obligationState=0;
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		obligationMappingInfoView.superclass.constructor.call(this, {
			id : 'obligationMappingInfoView',
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : "btn-tree-team17",
			items : [ this.searchPanel, this.gridPanel]
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
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:15px 0px 0px 0px',
            items : [{
            		xtype : 'hidden',
            		name:"obligationState",
            		value:this.obligationState
            },{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 70,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '产品名称',
						name : 'obligationName',
						anchor : "95%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 70,
					labelAlign : 'right',
					hidden:this.hiddenMapping,
					border : false,
					items : [{
						fieldLabel : '债权人',
						name : 'investName',
						anchor : "95%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 70,
					labelAlign : 'right',
					hidden:this.Confirmhidden,
					border : false,
					items : [{
						fieldLabel : '借款期限',
						name : 'payintentPeriod',
						anchor : "95%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 70,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '债权截止日',
						name : 'investEndDate',
						anchor : "95%",
						format : 'Y-m-d',
						xtype : 'datefield'
					}]
		     	},{
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					labelWidth :70,
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
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					labelWidth :70,
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						//width : 40,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		if(this.managerType=="Info"){
		
		}else if(this.managerType=="Mapping"){
		
		}else if(this.managerType=="closeObligation"){
		
		}else if(this.managerType=="openObligation"){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '激活债权产品',
					xtype : 'button',
					scope : this/*,
					//hidden : isGranted('_seePro_p' + this.projectStatus)? false: true,
					handler : this.editObligationProduct*/
				},{
					iconCls : 'btn-detail',
					text : '匹配详情',
					xtype : 'button',
					scope : this,
					//hidden : isGranted('_seePro_p' + this.projectStatus)? false: true,
					handler :this.seeMappingObligationInfo
				},{
					iconCls : 'btn-detail',
					text : '查看项目信息',
					xtype : 'button',
					scope : this,
					//hidden : isGranted('_seePro_p' + this.projectStatus)? false: true,
					handler : function() {
						//detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			});
		}else {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '债权详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_obligation_detail_2')? false: true,
					handler : this.editObligationProduct
				}]
			});
			
		}
		
			
			
		this.gridPanel = new HT.GridPanel({
			name : 'obligationMappingInfoGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			//plugins : [checkColumn,seniorcheckColumn],
			rowActions : false,
			url : __ctxPath + "/creditFlow/creditAssignment/bank/listAllInvestObObligationInvestInfo.do?obligationState="+this.obligationState,
			fields : [{
				name : 'id',
				type : 'int'
			}, 'investMentPersonId','obligationId', 'obligationName', 'obligationAccrual', 'investQuotient','investMoney', 'investStartDate','investEndDate',
					'shopId','creatorId', 'companyId', 'investObligationStatus',
					 'systemInvest','investRate', 'orgName', 'creatorName','investName','cardNumber', 'cellPhone', 'projectName',
					 'projectNumber','obligationNumber', 'projectMoney', 'minInvestMent',
					 'totalQuotient','payintentPeriod'],
			listeners : {
				scope : this,
				afteredit : function(e) {/*
					if (e.originalValue != e.value){
						var args ;
						if(e.field =='rechargeConfirmStatus'){
								args = {
										'rechargeConfirmStatus' : e.value,
										'rechargeLevel':this.rechargeLevel,
										'id' : e.record.data['id']
							}
						}
						if(e.field =='seniorValidationRechargeStatus'){
								args = {
										'seniorValidationRechargeStatus' : e.value,
										'rechargeLevel':this.rechargeLevel,
										'id' : e.record.data['purchaseId']
							}
						}
						if(e.field =='rechargeConfirmStatus' ||e.field =='seniorValidationRechargeStatus'){
							var panel =this;
							Ext.Ajax.request({
										url : __ctxPath + "/obligation/fiance/ConfirmStatusObAccountDealInfo.do",
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
				*/}
			},

			columns : [{
				header : '债权人名称',
				width : 100,
				hidden:this.hiddenInfo,
				dataIndex : 'investName'
			}, {
				header : '匹配项目',
				width : 200,
				dataIndex : 'projectName'
			}, {
				header : '项目期限(月)',
				width : 100,
				dataIndex : 'payintentPeriod'
			},{
				header : '投资份额',
				width : 100,
				dataIndex : 'investQuotient',
				renderer : function(value) {
					if (value == "") {
						return "";
					} else {
						return value+ "份";
					}
				}
			}, {
				header : '投资金额（元）',
				width : 100,
				dataIndex : 'investMoney',
				hidden:this.hiddenInfo,
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '资金投资日',
				align : 'right',
				width : 110,
				sortable : true,
				hidden:this.hiddenInfo,
				dataIndex : 'investStartDate'
			},{
				header : '债权截止日',
				align : 'right',
				width : 110,
				sortable : true,
				hidden:this.hiddenInfo,
				dataIndex : 'investEndDate'
			}, {
				header : '债权比例（%）',
				width : 100,
				dataIndex : 'investRate',
				hidden:this.hiddenMapping,
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00000')
								+ "%";
					}
				}
			}]
		});
		/*this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);*/
	
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
	//添加债权产品
	addObligationProduct:function(){},
	//编辑债权产品
	editObligationProduct:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var window_see = new Ext.Window({
						title : '债权详情',
						layout : 'fit',
						width : (screen.width - 180) * 0.7,
						maximizable : true,
						height : 500,
						autoScroll:true,
						closable : true,
						modal : true,
						plain : true,
						border : false,
						items : [new obligationAndIncome({
									projectId : s[0].data.obligationId,
									investPersonId : s[0].data.investMentPersonId,
									obligationInfoId : s[0].data.id
								})],
						listeners : {
							'beforeclose' : function(panel) {
								window_see.destroy();
							}
						}
					});
			window_see.show();
		}
	
	},
	//查看债权产品
	seeObligationProduct:function(){},
	//债权产品匹配投资人
	MappingInvestPerson:function(){},
	//查看债权产品匹配详情
	seeMappingObligationInfo:function(){}
	//obligationInvestInfo
});
