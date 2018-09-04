//CsCooperationEnterpriseFianceAccount


CsCooperationEnterpriseFianceAccount = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CsCooperationEnterpriseFianceAccount.superclass.constructor.call(this, {
					id : 'CsCooperationEnterpriseFianceAccount'+this.type,
					title : "资金账户管理",
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
								fieldLabel : '客户名称',
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
								fieldLabel : '组织机构名称',
								name : 'organizationNumber',
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
					}]
				});// end of searchPanel

          var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			plugins : [summary],
			//tbar : this.topbar,
			// 使用RowActions
			rowActions : false,
			//id : 'CsCooperationEnterpriseGrid',
			url : __ctxPath
					+ "/creditFlow/customer/cooperation/fianceAccountListCsCooperationEnterprise.do?type="+this.type,
			fields : [{
						name : 'id',
						type : 'int'
					}, 'name', 'type', 'typeFrom', 'licenseNumber',
					'organizationNumber', 'tellPhone', 'fax',
					'registeredMoney', 'buildDate', 'cooperationDate',			
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
						header : '客户名称',
						dataIndex : 'name'
					},{
						header : '组织机构名称',
						summaryRenderer : totalMoney,
						dataIndex : 'organizationNumber'
					},{
						header : '营业执照',
						align:'center',
						dataIndex : 'licenseNumber'
					},{
						header : '手机号码',
						align:'center',
						dataIndex : 'tellPhone'
					}, {
						header : '资金账户名称',
						dataIndex : 'accountName'
					}, {
						header : '资金账号',
						align:'center',
						dataIndex : 'accountNumber'
					}, {
						header : '账户总额(元)',
						summaryType : 'sum',
						align:'right',
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
					new CsCooperationEnterpriseForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new CsCooperationEnterpriseForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationEnterprise.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationEnterprise.do',
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
			new CsCooperationEnterpriseForm({
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
			new CsCooperationEnterpriseForm({
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
								url : __ctxPath+ '/creditFlow/customer/cooperation/p2pisForbiddenCsCooperationEnterprise.do',
								method : 'POST',
								success : function(response) {
									Ext.ux.Toast.msg('操作信息','启用成功!');
									gridPanelStore.reload();
								},
								params : {
									"csCooperationEnterprise.isUsing" : "0",
									"csCooperationEnterprise.id" : record.data.id
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
								url : __ctxPath+ '/creditFlow/customer/cooperation/p2pisForbiddenCsCooperationEnterprise.do',
								method : 'POST',
								success : function(response) {
									Ext.ux.Toast.msg('操作信息','禁用成功!');
									gridPanelStore.reload();
								},
								params : {
									"csCooperationEnterprise.isUsing" : "1",
									"csCooperationEnterprise.id" : record.data.id
								}
							});
							
						}
				},this);
			
		}
	},
	openP2P : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		var objectGridPanel=this.gridPanel;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			var personId = record.data.id;
			var cellphone = record.data.pphone;
		//	var selfemail = record.data.pEmail;
			var cardnumber = record.data.organizationNumber;
			new BpCustRelationForm({
				type:"e_cooperation",
				bStr:true,
				pType:6,
				userId:personId,
				cellphone:cellphone,
				objectGridPanel:objectGridPanel,
			//	selfemail:selfemail,
				cardnumber:cardnumber
			}).show();
			
		}
	}
	
	
	
});
