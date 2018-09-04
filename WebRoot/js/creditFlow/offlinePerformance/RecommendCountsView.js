/**
 * 推荐个数列表
 * @class RecommendCountsView
 * @extends Ext.Panel
 */

RecommendCountsView = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		RecommendCountsView.superclass.constructor.call(this, {
			id : 'RecommendCountsView',
			height : 450,
			autoScroll : true,
			layout : 'border',
			title : '推荐个数查询',
			iconCls:"menu-finance",
			// items : [this.searchPanel, this.centerPanel]
			items : [ this.centerPanel]
		});
	},
	initUIComponents : function() {
		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}
		this.pageSize = 25;
		this.store = new Ext.data.JsonStore({
			url : __ctxPath+ '/system/userListAppUser.do',
			totalProperty : 'totalCounts',
			root : 'result',
			remoteSort : true,
			fields : [{name : 'userId'},{name:'fullname'},{name : 'plainpassword'},{name:'mobile'},{name:'email'},
					  {name:'status'},{name:'isForbidden'},{name:'recommandNum'},{name:'secondRecommandNum'},
					  {name:'registrationDate'}]
		});
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});

		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
			height : 45,
			region : "north",
			bodyStyle : 'padding:7px 0px 7px 10px',
			border : false,
			width : '100%',
			monitorValid : true,
			layout : 'column',
			defaults : {
				layout : 'form',
				border : false,
				bodyStyle : 'padding:5px 0px 0px 20px'
			},
			items : [isShow ? {
				columnWidth : 0.2,
				labelWidth : 65,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : "combo",
					anchor : "100%",
					fieldLabel : '所属分公司',
					hiddenName : "companyId",
					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath+ '/system/getControlNameOrganization.do',
						fields : ['companyId', 'companyName']
					})
				}]
			}: {columnWidth : 0.01}, {
				columnWidth : isShow ? 0.15 : 0.2,
				labelWidth : 30,
				items : [{
					xtype : 'textfield',
					fieldLabel : '姓名',
					name : 'fullname',
					anchor : '90%'
				}]
			}, {
				columnWidth : isShow ? 0.12 : 0.18,
				labelWidth : 40,
				items : [{
					xtype : 'textfield',
					fieldLabel : '推荐码',
					name : 'plainpassword',
					anchor : '100%'
				}]
			},{
				columnWidth : 0.18,
				layout : 'form',
				border : false,
				labelWidth : 50,
				labelAlign : 'right',
				items : [{
					name : 'registrationDate_GE',
					labelSeparator : '',
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '查询日期',
					anchor : '100%'
				}]
			}, {
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 10,
				labelAlign : 'right',
				items : [{
					name : 'registrationDate_LE',
					labelSeparator : '',
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '到',
					anchor : '100%'
				}]
			},{
				columnWidth : 0.07,
				items : [{
					xtype : 'button',
					text : '查询',
					tooltip : '根据查询条件过滤',
					iconCls : 'btn-search',
					width : 60,
					formBind : true,
					scope : this,
					handler : function() {
						this.searchByCondition();
					}
				}]
			}, {
				columnWidth : 0.07,
				items : [{
					xtype : 'button',
					text : '重置',
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				}]
			}]
		}); // 查询面板结束

		// 加载数据
		this.store.load({
			scope : this,
			params : {
				start : 0,
				limit : this.pageSize,
				isAll : isGranted('_seeAll_erp')
			}
		});
		var personStore = this.store;
		var tbar = new Ext.Toolbar({
			items : [{
				text : '导出列表',
				iconCls : 'btn-xls',
				scope : this,
				hidden : isGranted('_exportRecommendCount') ? false : true,
				handler : function() {
					var companyId = (isShow==true?this.getCmpByName("companyId").getValue():null);
					var plainpassword=this.getCmpByName("plainpassword").getValue();//推荐码
					var fullname = this.getCmpByName("fullname").getValue();//姓名
					var registrationDateGE = this.getCmpByName("registrationDate_GE").getValue();
					var registrationDateLE = this.getCmpByName("registrationDate_LE").getValue();
					if(registrationDateGE){
						registrationDateGE=registrationDateGE.format('Y-m-d');
					}
					if(registrationDateLE){
						registrationDateLE=registrationDateLE.format('Y-m-d');
					}
					window.open(__ctxPath+ '/customer/exportExcelBpCustRelation.do?type=1&companyId='+companyId
								+'&plainpassword='+plainpassword+'&fullname='+fullname
								+'&registrationDateGE='+registrationDateGE
								+'&registrationDateLE='+registrationDateLE,'_blank');
				}
			}]
		});
        var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
		this.centerPanel = new HT.GridPanel({
			region : 'center',
			tbar : tbar,
			plugins : [summary],
			clicksToEdit : 1,
			store : this.store,
			height : 450,
			loadMask : this.myMask,
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			},
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			columns : [{
				header : "所属分公司",
				width : 160,
				align:'center',
				sortable : true,
				hidden : RoleType == "control" ? false : true,
				dataIndex : 'companyName'
			}, {
				header : "姓名",
				align:'center',
				width : 100,
				sortable : true,
				dataIndex : 'fullname'
			}, {
				header : "推荐码",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'plainpassword'
			}, {
				header : "手机号",
				summaryRenderer : totalMoney,
				width : 100,
				sortable : true,
				align:'center',
				dataIndex : 'mobile'
			},{
				header : "邮箱",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'email'
			},{
				header : "开通日期",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'registrationDate'
			},{
				header : "一级推荐个数",
				summaryType : 'sum',
				renderer : function(v) {
					                 return v+"个";
				                   },
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'recommandNum'
			},{
				header : '二级推荐个数',
				summaryType : 'sum',
				renderer : function(v) {
					                 return v+"个";
				                   },
				align:'center',
				dataIndex : 'secondRecommandNum',
				width : 100
			},{
				header : "ERP是否禁用",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'status',
				renderer : function(v) {
					if (v == "0")
						return "是";
					if (v == "1")
						return "否";
				}
			},{
				header : "P2P是否禁用",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'isForbidden',
				renderer : function(v,a,b,c,d) {
					//先判断是否开通过p2p账号
					if(b.data.plainpassword){
						if(v == "1"){
							return "是";
						}else{
							return "否";
						}
					}
				}
			}]
		});
	},

	// 查询
	searchByCondition : function() {
		this.store.baseParams.fullname = this.searchPanel.getForm().findField('fullname').getValue();
		this.store.baseParams.plainpassword = this.searchPanel.getForm().findField('plainpassword').getValue();
		this.store.baseParams.registrationDateGE = this.searchPanel.getForm().findField('registrationDate_GE').getValue();
		this.store.baseParams.registrationDateLE = this.searchPanel.getForm().findField('registrationDate_LE').getValue();
		if (null != this.searchPanel.getForm().findField('companyId')) {
			this.store.baseParams.companyId = this.searchPanel.getForm().findField('companyId').getValue();
		}
		this.store.load({
			scope : this,
			params : {
				start : 0,
				limit : this.pageSize
			}
		});
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	}
});