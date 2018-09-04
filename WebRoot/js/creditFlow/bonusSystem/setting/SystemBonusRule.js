/**
 * SystemBonusRule.js
 */
 
 
SystemBonusRule = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		this.ids = "";
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SystemBonusRule.superclass.constructor.call(this, {
					id : 'SystemBonusRule' ,
					title : "默认积分规则设置",
					region : 'center',
					layout : 'border',
					 iconCls:"menu-finance",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};

		this.searchPanel = new Ext.FormPanel({
					autoWhith : true,
					layout : 'column',
					region : 'north',
					border : false,
					height : 50,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding:15px 0px 0px 0px',
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : 'Key值',
											name : 'Q_flagKey_S_LK',
											anchor : "100%",
											xtype : 'textfield'
										}]
							}, {
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 60,
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
								labelWidth : 60,
								items : [{
											text : '重置',
											style : 'margin-left:30px',
											xtype : 'button',
											scope : this,
											anchor : "90%",
											iconCls : 'btn-reset',
											handler : this.reset
										}]
							}]
				});

		this.topbar = new Ext.Toolbar({
				items : [/*{
					iconCls : 'btn-detail',
					text : '添加积分规则',
					xtype : 'button',
					scope : this,
					handler : this.addNewRule
				},new Ext.Toolbar.Separator({
							
				}),*/{
					iconCls : 'btn-edit',
					text : '修改积分规则',
					xtype : 'button',
					scope : this,
					handler : this.editNewRule
				},new Ext.Toolbar.Separator({
							
					}),{
					iconCls : 'btn-detail',
					text : '查看积分规则',
					xtype : 'button',
					scope : this,
					handler : this.seeNewRule
					
				},new Ext.Toolbar.Separator({
							
					}),{
					iconCls : 'btn-detail',
					text : '删除积分规则',
					xtype : 'button',
					scope : this,
					hidden : true,
					handler : this.delNewRule
				},new Ext.Toolbar.Separator({
							
				})]
		});
		
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
					return '总计';
		}
		this.gridPanel = new HT.GridPanel({
					name : 'systemBonusRuleGrid',
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					notmask : true,
					// 不适用RowActions
					rowActions : false,
					url : __ctxPath
							+ "/bonusSystem/listWebBonusSetting.do",
					fields : [{
								name : 'bonusId',
								type : 'int'
							}, 
							'flagKey',
							'userAction', 
							'userActionKey',
							'memberLevel',
							'isBonus', 
							
							'bonusType',
							'bonusName', 
							'bonus',
							'bomusPeriod',
							'bomusPeriodType',
							'periodBonusLimit',
							'bonusswitch'
							],

					columns : [{
								header : 'bonusId',
								dataIndex : 'bonusId',
								hidden : true
							},{

								header : '<font color=red>积分规则Key</font>',
								dataIndex : 'flagKey',
								align:'center',
								width : 70,
								renderer : function(val){
									if(val =="register"){
										return "注册"+val;
									}else if(val =="invite"){
										return "邀请"+val;
									}else if(val =="tender"){
										return "投标"+val;
									}else if(val =="recharge"){
										return "充值"+val;
									}else if(val =="inviteOnce"){
										return "邀请第一次投标"+val;
									}else if(val =="Login"){
										return "登录"+val;
									}
									return val;
								}
							},{
								header : '<font color=red>service接口名</font>',
								width : 70,
								align:'center',
								summaryRenderer : totalMoney,
								dataIndex : 'userAction'
							},{
								header : '<font color=red>service方法名</font>',
								dataIndex : 'userActionKey',
								align:'center',
								width : 70
							},{
								header : '<font color=red>是否开启</font>',
								dataIndex : 'bonusswitch',
								align:'center',
								width : 50,
								renderer : function(val){
									if(val=="0"){
										return "开启";
									}else{
										return "关闭";
									}
								}
							},{
								header : '奖惩措施',
								align:'center',
								width : 70,
								dataIndex : 'isBonus',
								renderer : function(value) {
									if (eval(value) == eval(1)) {
										return "增加积分";
									} else if (eval(value) == eval(2)){
										return "扣除积分";
									} 
								}
							},{
								header : '奖励方式',
								align:'center',
								dataIndex : 'bonusType',
								width : 70
							}, {
								header : '奖励分值',
								align:'center',
								summaryType : 'sum',
								 renderer : function(v) {
					                 return v+"分";
				                   },

								width : 80,
								dataIndex : 'bonus'
							},{
								header : '奖励周期',
								align:'right',
								width : 50,
								dataIndex : 'bomusPeriod'
							},{
								header:'奖励周期单位',
								align:'center',
								width : 50,
								dataIndex : 'bomusPeriodType',
								renderer : function(val){
									if("once"==val){
										return "一次性"
									}else if("min"==val){
										return "分"
									}else if("hour"==val){
										return "时"
									}else if("date"==val){
										return "天"
									}
								}
							},{
								header : '周期内奖励次数(次)',
								align : 'center',
								width : 90,
								sortable : true,
								dataIndex : 'periodBonusLimit',
								renderer : function(value) {
									if (value == "") {
										return "无限";
									}else{
										return value;
									}
								}
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
		/*
		 * if (!this.isGrantedShowAllProjects) {
		 * this.gridPanel.getStore().on("load", function(store) { if
		 * (store.getCount() < 1) { this.get(0).setVisible(true);
		 * this.searchPanel.setVisible(false); this.gridPanel.setVisible(false);
		 * this.doLayout(); } }, this) }
		 */
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
	// 添加奖励规则
	addNewRule : function() {
		new SystemBonusRuleForm ({
			title:"添加积分规则",
			flashTargat:this.gridPanel
		}).show();
	
	},
	// 查看奖励规则
	seeNewRule : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}else {
			new SystemBonusRuleForm ({
				title:"查看积分规则",
				bonusId:s[0].data.bonusId,
				isReadOnly:true,
				isKeyReadOnly:true,
				isLook:true,
				flashTargat:this.gridPanel
			}).show();
		}
	
	},
	// 编辑奖励规则
	editNewRule : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}else {
			new SystemBonusRuleForm ({
				title:"编辑积分规则",
				bonusId:s[0].data.bonusId,
				isReadOnly:false,
				isKeyReadOnly:true,
				isLook:true,
				flashTargat:this.gridPanel
			}).show();
		}
	
	},
	delNewRule:function(){
		var gridPanel =  this.gridPanel;
		var s = gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中要删除的记录');
			return false;
		}else {
				for(var i=0 ; i < s.length ; i ++){
					this.ids = this.ids + s[i].data.bonusId;
					if(i!=s.length-1){
						this.ids += ",";
					}
				}
				
				Ext.Msg.confirm('信息确认', '确定要删除选中的积分规则吗?', function(btn) {
					if (btn == 'yes') {
						 
						var _gridPanel = this.gridPanel;
						Ext.Ajax.request({
							url :__ctxPath + '/bonusSystem/multiDelWebBonusSetting.do',
							method : 'POST',
							scope:this,
							params : {
								ids : this.ids
							},
							success :function(response, request){
								this.ids="";//置空
								var object=Ext.util.JSON.decode(response.responseText);
								_gridPanel.getStore().reload();
							},
							failure : function(fp, action) {
								this.ids="";//置空
							}
        				});
        				
					}
				},this)
		}
		
		
	}
});