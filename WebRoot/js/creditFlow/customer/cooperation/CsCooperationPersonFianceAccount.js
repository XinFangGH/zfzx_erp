//CsCooperationPersonFianceAccount.js
CsCooperationPersonFianceAccount = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CsCooperationPersonFianceAccount.superclass.constructor.call(this, {
					id : 'CsCooperationPersonFianceAccount'+this.type,
					title : '资金账户管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team23',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					region : 'north',
					border : false,
					hieght : 100,
					anchor : '100%',
					layoutConfig:{
						align : 'middle'
					},
					items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
								fieldLabel : '客户姓名',
								name : 'name',
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
								fieldLabel : '证件号码',
								name : 'cardNumber',
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
								fieldLabel : '资金账户',
								name : 'accountNumber',
								anchor : "100%",
								xtype : 'textfield'
						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							text : '查询',
							xtype : 'button',
							scope : this,
							width : 30,
							style : 'margin-left:5px',
							iconCls : 'btn-search',
							handler : this.search
						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							text : '重置',
							xtype : 'button',
							scope : this,
							width : 30,
							style : 'margin-left:5px',
							iconCls : 'btn-reset',
							handler : this.reset
						}]
					}
					]
				});// end of searchPanel
         var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			plugins : [summary],
			// 使用RowActions
			rowActions : false,
			url : __ctxPath
					+ "/creditFlow/customer/cooperation/fianceAccountListCsCooperationPerson.do?type="+this.type,
			fields : [{
						name : 'id',
						type : 'int'
					}, 
					'name',
					'sex', 
					'cardType', 
					'cardNumber', 
					'phoneNumber',
					'email', 
					'qqNumber', 
					'weixinNumber', 
					'remark',
					'isUsing',
					'accountId',
					'accountName',
					'accountNumber',
					'investmentPersonId',
					'investPersionType',
					'totalMoney'
						
					],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						align:'center',
						hidden : true
					}, {
						header : '客户姓名',
						align:'center',
						dataIndex : 'name'
					}, {
						header : '证件号码',
						align:'center',
						dataIndex : 'cardNumber'
					}, {
						header : '手机号码',
						align:'center',
						summaryRenderer : totalMoney,
						dataIndex : 'phoneNumber'
					}, {
						header : '邮箱',
						align:'center',
						dataIndex : 'email'
					}, {
						header : '资金账户名称',
						align:'center',
						dataIndex : 'accountName'
					}, {
						header : '资金账户',
						align:'center',
						dataIndex : 'accountNumber'
					}, {
						header : '账户总额(元)',
						align:'right',
						summaryType : 'sum',
						dataIndex : 'totalMoney',
						renderer : function(value) {
									if (value == "") {
										return "0.00";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00');
									}
						}
					}]
				// end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new CsCooperationPersonForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new CsCooperationPersonForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationPerson.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationPerson.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			new CsCooperationPersonForm({
					id : record.data.id
			}).show();
		}
	},
	// 查看Rs
	seeRs : function(record) {
		
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			new CsCooperationPersonForm({
					id : record.data.id,
					readOnly : true
			}).show();
		}
	},
	//启用
	using : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			Ext.Msg.confirm("提示!",'你确定要启用吗',
					function(btn) {
						if (btn == "yes") {
							var gridPanelStore = this.gridPanel.getStore();
							var sel = this.gridPanel.getSelectionModel().selections;
							var record = sel.get(0);
							//ajax更改状态
							Ext.Ajax.request( {
								url : __ctxPath+ '/creditFlow/customer/cooperation/p2pisForbiddenCsCooperationPerson.do',
								method : 'POST',
								success : function(response) {
									Ext.ux.Toast.msg('操作信息','启用成功!');
									gridPanelStore.reload();
								},
								params : {
									"csCooperationPerson.isUsing" : "0",
									"csCooperationPerson.id" : record.data.id
								}
							});
							
						}
				},this);
			
		}
	},
	//禁用
	nousing : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			Ext.Msg.confirm("提示!",'你确定要禁用吗',
					function(btn) {
						if (btn == "yes") {
							var gridPanelStore = this.gridPanel.getStore();
							var sel = this.gridPanel.getSelectionModel().selections;
							var record = sel.get(0);
							//ajax更改状态
							Ext.Ajax.request( {
								url : __ctxPath+ '/creditFlow/customer/cooperation/p2pisForbiddenCsCooperationPerson.do',
								method : 'POST',
								success : function(response) {
									Ext.ux.Toast.msg('操作信息','禁用成功!');
									gridPanelStore.reload();
								},
								params : {
									"csCooperationPerson.isUsing" : "1",
									"csCooperationPerson.id" : record.data.id
								}
							});
							
						}
				},this);
			
		}
	},
	//开通P2P账号
	openP2P : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			var personId = record.data.id;
			var cellphone = record.data.phoneNumber;
			var selfemail = record.data.email;
			var cardnumber = record.data.cardNumber;
			new BpCustRelationForm({
				type:"p_cooperation",
				bStr:true,
				pType:5,
				userId:personId,
				cellphone:cellphone,
				selfemail:selfemail,
				cardnumber:cardnumber
			}).show();
			
		}
	}
	
	
});
