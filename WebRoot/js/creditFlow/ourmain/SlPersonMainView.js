/**
 * @author:
 * @class SlPersonMainView
 * @extends Ext.Panel
 * @description [SlPersonMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SlPersonMainView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlPersonMainView.superclass.constructor.call(this, {
					id : 'SlPersonMainView',
					title : '个人主体管理',
					region : 'center',
					layout : 'border',
					iconCls:"menu-flowManager",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					layout : 'column',
					border : false,
					region : 'north',
					height : 40,
					anchor : '96%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding:10px 10px 10px 10px',
					items : [{
								name : 'isPledge',
								xtype : 'hidden',
								value : 0
							}, {
								columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											fieldLabel : '姓名',
											name : 'Q_name_S_LK'
										}]
							}, {
								columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
											xtype : 'textfield',
											fieldLabel : '证件号码',
											name : 'Q_cardnum_S_LK'
										}]
							},isShow?{
							    columnWidth : 0.2,
							    layout : 'form',
							    border:false,
							   	labelWidth : 80,
								labelAlign : 'right',
							    items : [
							    {
							      xtype : "combo",
							      anchor : "98%",
							      fieldLabel : '所属分公司',
							      hiddenName : "companyId",
							      displayField : 'companyName',
							      valueField : 'companyId',
							      triggerAction : 'all',
							      store : new Ext.data.SimpleStore({
							       autoLoad : true,
							       url : __ctxPath+'/system/getControlNameOrganization.do',
							       fields : ['companyId', 'companyName']
							      })
							    }
							   ]}:{
							     columnWidth:0.01,
							     border:false
							     }, {
								columnWidth : 0.07,
								layout : 'form',
								border : false,
								labelAlign : 'right',
								items : [{
											xtype : 'button',
											text : '查询',
											width : 60,
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										}]
							}, {
								columnWidth : 0.07,
								layout : 'form',
								border : false,

								labelAlign : 'right',
								items : [{
											xtype : 'button',
											text : '重置',
											width : 60,
											scope : this,
											iconCls : 'btn-reset',
											handler : this.reset
										}]
							}]

				})

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加个人主体',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_create_pm') ? false : true,
								handler : this.createRs
							}, {
								iconCls : 'btn-del',
								text : '删除个人主体',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_remove_pm') ? false : true,
								handler : this.removeSelRs
							}, {
								iconCls : 'btn-edit',
								text : '编辑个人主体',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_edit_pm') ? false : true,
								handler : this.editRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			id : 'SlPersonMainGrid',
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath+ "/creditFlow/ourmain/listSlPersonMain.do",
			fields : [{
						name : 'personMainId',
						type : 'int'
					}, 'name', 'sex', 'cardtype', 'cardnum', 'linktel', 'tel',
					'address', 'home', 'postalCode', 'tax', 'isPledge',
					'cardtypevalue','companyId','companyName'],
			columns : [{
				header : 'id',
				align:'center',
				dataIndex : 'personMainId',
				hidden : true
			},{
			   	header : '分公司名称',
			   	hidden : !isShow,
				dataIndex : 'companyName'
			}, {
				header : '姓名',
				align:'center',
				dataIndex : 'name'
			}, {
				header : '性别',
				align:'center',
				dataIndex : 'sex',
				renderer : function(v) {
					switch (v) {
						case 0 :
							return '男';
							break;
						case 1 :
							return '女';
							break;
					}
				}
			}, {
				header : '证件类型',
				align:'center',
				dataIndex : 'cardtypevalue'
			}, {
				header : '证件号码',
				align:'center',
				dataIndex : 'cardnum'
			}, {
				header : '联系电话',
				align:'center',
				dataIndex : 'linktel'
			}, {
				header : '固定电话',
				align:'center',
				dataIndex : 'tel'
			}, {
				header : '通讯地址',
				dataIndex : 'address'
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
		 $search({ searchPanel : this.searchPanel,
		 gridPanel : this.gridPanel });
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			 new SlPersonMainForm({ 
			 		id : rec.data.personMainId,
				 	isReadOnlyPerson : false, 
				 	gridPanelPerson : this.gridPanel 
			}).show();
		});
	},
	// 创建记录
	createRs : function() {
		 	new SlPersonMainForm({ 
		 		isPledge : 0, isReadOnlyPerson : false,
		 		gridPanelPerson : this.gridPanel 
		 	}).show();
	},

	// 把选中ID删除
	removeSelRs : function() {
		 $delGridRs({ 
			 url : __ctxPath +'/creditFlow/ourmain/multiDelSlPersonMain.do',
			 grid : this.gridPanel, 
			 idName :'personMainId' 
		 });
	},
	// 编辑Rs
	editRs : function() {
		 var s =this.gridPanel.getSelectionModel().getSelections();
		 
		 if (s <= 0) { 
		 	Ext.ux.Toast.msg('操作信息','请选中任何一条记录'); 
		 	return false; 
		 } else if (s.length >1) { 
		 	Ext.ux.Toast.msg('操作信息', '只能选中一条记录'); 
		 	return false; 
		 } else { 
		 	record = s[0];
		 	new SlPersonMainForm({ 
		 		id : record.data.personMainId,
				isReadOnlyPerson : false, 
				gridPanelPerson :this.gridPanel 
		 	}).show(); 
		 }
	}
});
