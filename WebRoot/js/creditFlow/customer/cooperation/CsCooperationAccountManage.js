/**
 * @author
 * @class CsCooperationPersonView
 * @extends Ext.Panel
 * @description [CsCooperationPerson]管理
 * @company 智维软件
 * @createtime:
 */
CsCooperationAccountManage = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CsCooperationAccountManage.superclass.constructor.call(this, {
					id : 'CsCooperationAccountManage',
					title : '合作资金账户管理',
					region : 'center',
					layout : 'border',
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
					items : [
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
								fieldLabel : '名称',
								name : 'accountName',
								anchor : "100%",
								xtype : 'textfield'
						}]
					},
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
									xtype : 'combo',
									mode : 'local',
									displayField : 'name',
									valueField : 'id',
									anchor : "100%",
									editable : false,
									triggerAction : 'all',
									width : 70,
									store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [["企业", "e_cooperation"],["个人", "p_cooperation"]]
									}),
									hiddenName : 'offlineCustType',
									fieldLabel : '账户类型'
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

		this.topbar = new Ext.Toolbar({
					items : [/*{
								iconCls : 'btn-add',
								text : '开通p2p账户',
								xtype : 'button',
								scope : this,
								handler : this.openP2P
							},{
								iconCls : 'btn-ok',
								text : '启用',
								xtype : 'button',
								scope : this,
								handler : this.using
							}, {
								iconCls : 'btn-del',
								text : '禁用',
								xtype : 'button',
								scope : this,
								handler : this.nousing
							}*/]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : false,
			id : 'CsCooperationPersonGrid',
			url : __ctxPath + "/creditFlow/creditAssignment/bank/cooperationAccountListObSystemAccount.do",
			fields : [{
						name : 'id',
						type : 'int'
					}, 
					'accountName',
					'accountNumber', 
					'totalMoney', 'truename',
					'offlineCustType'
						
					],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '账户类型',
						dataIndex : 'offlineCustType',
						renderer : function(val){
							if(val=="p_cooperation"){
								return "合作个人账户"
							}else if(val="e_cooperation"){
								return "合作企业账户"
							}
							return "";
						}
					}, {
						header : '名称',
						dataIndex : 'truename'
					}, {
						header : '系统账户',
						dataIndex : 'accountNumber'
					}, {
						header : '账户余额',
						dataIndex : 'totalMoney'
					}, {
						header : '可用余额',
						dataIndex : 'p2ploginname'
					}, {
						header : '预冻结金额',
						dataIndex : 'p2ptruename'
					}, {
						header : '累计收到服务费',
						dataIndex : 'p2pcardcode'
					} 
					]
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
