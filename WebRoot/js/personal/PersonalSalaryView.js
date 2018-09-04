/**
 * @author:
 * @class PersonalSalaryView
 * @extends Ext.Panel
 * @description 薪酬发放管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-02-01
 */
PersonalSalaryView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 初始化组件
		this.initComponents();
		// 调用父类构造
		PersonalSalaryView.superclass.constructor.call(this, {
					id : 'PersonalSalaryView',
					title : '个人薪酬纪录',
					region : 'center',
					iconCls : 'menu-personal-salary',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// [SalaryPayoff]分类ID
	typeId : null,

	// 条件搜索Panel
	searchPanel : null,

	// 数据展示Panel
	gridPanel : null,

	// GridPanel的数据Store
	store : null,

	// 头部工具栏
	topbar : null,

	// 初始化组件
	initComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					region : 'north',
					frame : false,border:false,
					layout : 'hbox',
					height:40,
					layoutConfig: {
				        padding:'5',
				        align:'middle'
				    },
					defaults : {
						xtype : 'label',
						margins:{top:0, right:4, bottom:4, left:4}
					},
					items : [{text:'查询条件：日期从'},{
									name : 'Q_startTime_D_GE',
									width : 100,
									xtype : 'datefield',
									format : 'Y-m-d'
								}, {text:'至'},{
									name : 'Q_endTime_D_LE',
									width : 100,
									xtype : 'datefield',
									format : 'Y-m-d'
								},{
									xtype : 'button',
									text : '查询',
									iconCls : 'search',
									handler : this.search.createCallback(this)
								}]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/hrm/personalSalaryPayoff.do",
					root : 'result',
					baseParams : {
						'Q_userId_L_EQ' : curUserInfo.userId,
						'Q_checkStatus_SN_EQ' : 1
						// 只查已通过审核的
					},
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'recordId',
								type : 'int'
							}, 'fullname', 'userId', 'profileNo', 'idNo',
							'standAmount', 'acutalAmount', 'checkStatus',
							'startTime', 'endTime', 'content']
				});
		this.store.setDefaultSort('startTime', 'desc');
		// 加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		var actions = new Array();
		if (isGranted('_SalaryPayoffDel')) {
			actions.push({
						iconCls : 'btn-del',
						qtip : '删除',
						style : 'margin:0 3px 0 3px'
					});

		}
		if (isGranted('_SalaryPayoffEdit')) {
			actions.push({
						iconCls : 'btn-edit',
						qtip : '编辑',
						style : 'margin:0 3px 0 3px'
					});

		}
		if (isGranted('_SalaryPayoffCheck')) {
			actions.push({
						iconCls : 'btn-salary-pay',
						qtip : '审核',
						style : 'margin:0 3px 0 3px'
					});
		}

		if (isGranted('_SalaryPayoffQuery')) {
			actions.push({
						iconCls : 'btn-operation',
						qtip : '操作记录',
						style : 'margin:0 3px 0 3px'
					});
		}
		// this.rowActions = new Ext.ux.grid.RowActions({
		// header : '管理',
		// width : 100,
		// actions : actions
		// });

		// 初始化ColumnModel
		var expander = new Ext.ux.grid.RowExpander({
			tpl : new Ext.Template('<div style="padding:5px 5px 5px 62px;">{content}</div>')
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), expander, {
								header : 'recordId',
								dataIndex : 'recordId',
								hidden : true
							}, {
								header : '员工姓名',
								width : 60,
								dataIndex : 'fullname'
							}, {
								header : '档案编号',
								width : 150,
								dataIndex : 'profileNo'
							}, {
								header : '身份证号',
								width : 120,
								dataIndex : 'idNo'
							}, {
								header : '薪标金额',
								width : 60,
								dataIndex : 'standAmount',
								renderer : function(value) {
									return '<img src="'
											+ __ctxPath
											+ '/images/flag/customer/rmb.png"/>'
											+ value;
								}
							}, {
								header : '实发金额',
								width : 60,
								dataIndex : 'acutalAmount',
								renderer : function(value) {
									return '<img src="'
											+ __ctxPath
											+ '/images/flag/customer/rmb.png"/>'
											+ value;
								}
							}, {
								header : '薪酬日期',
								width : 130,
								dataIndex : 'startTime',
								renderer : function(value, metadata, record,
										rowIndex, colIndex) {
									return value.substring(0, 10)
											+ '--'
											+ record.data.endTime.substring(0,
													10);
								}
							}
					// , this.rowActions
					],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'PersonalSalaryPayoffGrid',
					region : 'center',
					stripeRows : true,
					plugins : expander,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					region : 'center',
					cm : cm,
					sm : sm,
					// plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true, // 自动填充
						forceFit : true
						// showPreview : false
					},
					bbar : new HT.PagingBar({store : this.store})
				});

	},// end of the initComponents()

	/**
	 * 
	 * @param {}
	 *            self 当前窗体对象
	 */
	search : function(self) {
		if (self.searchPanel.getForm().isValid()) {// 如果合法
			$search({
				searchPanel :self.searchPanel,
				gridPanel : self.gridPanel
			});
		}
	}
});
