/**
 * SystemBonusRecord.js
 */
 
SystemBonusRecord = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SystemBonusRecord.superclass.constructor.call(this, {
					id : 'SystemBonusRecord' ,
					title : "用户积分变动明细",
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
					height : 70,
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
											fieldLabel : '用户姓名',
											name : 'Q_customerName_S_LK',
											anchor : "100%",
											xtype : 'textfield'
										},{
										fieldLabel : '增减方向',
										hiddenName : 'recordDirector',
										readOnly : this.isReadOnly,
										anchor : '100%',
										xtype : 'combo',
										mode : 'local',
										valueField : 'value',
										editable : false,
										displayField : 'item',
										store : new Ext.data.SimpleStore({
													fields : ["item", "value"],
													data : [["奖励", "1"], ["扣除", "2"]]
												}),
										triggerAction : "all"
			
									}]
							},{
								columnWidth : .25,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											fieldLabel : '奖励时间',
											name : 'createTime',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield',
											labelSeparator : ""
										
										},{
											fieldLabel : '操作类型',
											name : 'bonusIntention',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '活动编号',
											name : 'activityNumber',
											anchor : "100%",
											xtype : 'textfield'
										},{
										fieldLabel : '积分类型',
										hiddenName : 'operationType',
										readOnly : this.isReadOnly,
										anchor : '100%',
										xtype : 'combo',
										mode : 'local',
										valueField : 'value',
										editable : false,
										displayField : 'item',
										store : new Ext.data.SimpleStore({
													fields : ["item", "value"],
													data : [["普通积分", "1"], ["活动积分", "2"], ["论坛积分", "3"]]
												}),
										triggerAction : "all"
			
									}]
							
							},{
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
										},{
										
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
							items : [{
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								}]
						});
		
		this.gridPanel = new HT.GridPanel({
					name : 'SystemBonusRecordGrid',
					region : 'center',
					tbar : this.topbar,
					notmask : true,
					// 不适用RowActions
					rowActions : false,
		      	    viewConfig:{
		      	    	forceFit : false
		      	    },
					url : __ctxPath+ "/bonusSystem/listWebBonusRecord.do",
					fields : [{
								name : 'recordId',
								type : 'int'
							}, 'recordNumber', 'recordDirector',
							'recordMsg', 'createTime',
							'customerName', 'customerType', 'customerId',
							'operationType',
							'bonusKey',
							'bonusIntention',
							'bonusDescription',
							'totalNumber',
							'activityNumber'
							],

					columns : [{
								header : 'bonusId',
								dataIndex : 'bonusId',
								hidden : true
							}, {
								header : 'userActionKey',
								dataIndex : 'userActionKey',
								hidden : true
							}, {
								header : '用户名称',
								align : 'center',
								width : 80,
								dataIndex : 'customerName'
							},{
								header : '积分类型',
								align : 'center',
								width : 100,
								dataIndex : 'operationType',
								renderer : function(value) {
									if (eval(value) == eval(1)) {
										return "普通积分";
									} else if (eval(value) == eval(2)){
										return "活动积分";
									}  else if (eval(value) == eval(3)){
										return "论坛积分";
									} 
								}
							}, {
								header : '积分',
								align : 'center',
								width : 80,
								sortable : true,
								dataIndex : 'recordNumber',
								renderer : function(val,r,obj){
									var recordDirector = obj.get('recordDirector');
									if (recordDirector=="1") {
										return "<font color=green><b>+"+val+"</b></font>";
									}else if(recordDirector=="2"){
										return "<font color='red'><b>-" +val + "</b></font>";
									}
									return "";
								}
							},{
								header : '奖励时间',
								align : 'center',
								width : 140,
								sortable : true,
								dataIndex : 'createTime'
							},{
								header : '增减方向',
								align : 'center',
								width : 70,
								dataIndex : 'recordDirector',
								renderer : function(value) {
									if (eval(value) == eval(1)) {
										return "奖励";
									} else if (eval(value) == eval(2)){
										return "扣除";
									}
								}
							},{
								header : '操作类型',
								align : 'center',
								width : 100,
								sortable : true,
								dataIndex : 'bonusIntention'
							},{
								header : '用户当前积分',
								align : 'center',
								width : 120,
								sortable : true,
								dataIndex : 'totalNumber'
							},{
								header : '活动编号',
								align : 'center',
								width : 150,
								sortable : true,
								dataIndex : 'activityNumber'
							},{
								header : '说明',
								align : 'center',
								width : 200,
								sortable : true,
								dataIndex : 'bonusDescription'
							},{
								header : '积分日志',
								align : 'center',
								width : 300,
								sortable : true,
								dataIndex : 'recordMsg'
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
	exportExcel:function(){
					var Q_customerName_S_LK = this.getCmpByName("Q_customerName_S_LK").getValue();
					var recordDirector = this.getCmpByName("recordDirector").getValue();
					var createTime = this.getCmpByName("createTime").getValue();
					var bonusIntention = this.getCmpByName("bonusIntention").getValue();
					var activityNumber = this.getCmpByName("activityNumber").getValue();
					var operationType = this.getCmpByName("operationType").getValue();
					var time ="";
					if(createTime!=""){
						 time = createTime.format("Y-m-d");
					}
					window.open( __ctxPath + "/bonusSystem/exportExcelWebBonusRecord.do?Q_customerName_S_LK="+Q_customerName_S_LK+"&recordDirector="+recordDirector+"" +
							"&createTime="+createTime+"&bonusIntention="+bonusIntention+"&activityNumber="+activityNumber+"&operationType="+operationType+"");
	},
	addRecoerd:function(){

		Ext.Ajax.request({
						url : __ctxPath + "/bonusSystem/saveRecordWebBonusRecord.do",
						method : 'POST',
						scope:this,
						success :function(response, request){
							var object=Ext.util.JSON.decode(response.responseText);
							var flag =object.flag;//flag用来判断是否本金款项是否已经对账：0表示没有对账，1表示已经对账
							
						}
			       })
	}
});