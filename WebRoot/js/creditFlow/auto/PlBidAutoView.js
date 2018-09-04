/**
 * @author
 * @class PlBidAutoView
 * @extends Ext.Panel
 * @description [PlBidAuto]管理
 * @company 智维软件
 * @createtime:
 */
PlBidAutoView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlBidAutoView.superclass.constructor.call(this, {
							id : 'PlBidAutoView',
							title : '自动投标管理',
							region : 'center',
							iconCls:"menu-finance",
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
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '用户',
					name : 'truename',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '是否开启',
						hiddenName : 'Q_isOpen_N_EQ',
						anchor : "100%",
						xtype : 'combo',
						mode : 'local',
						valueField : 'value',
						editable : false,
						displayField : 'item',
						store : new Ext.data.SimpleStore({
							fields : ["item","value"],
							data : [["开启",1], ["关闭",0]]
						}),
						triggerAction : "all"
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '是否禁用',
						hiddenName : 'Q_banned_N_EQ',
						anchor : "100%",
						xtype : 'combo',
						mode : 'local',
						valueField : 'value',
						editable : false,
						displayField : 'item',
						store : new Ext.data.SimpleStore({
							fields : ["item","value"],
							data : [["正常","0"],["禁用","1"]]
						}),
						triggerAction : "all"
					}]
		     	},{
	     			columnWidth :.15,
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
	     			columnWidth :.15,
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
		});// end of searchPanel
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加[自动投标]',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除[自动投标]',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});
                 var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					region : 'center',
					//tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					plugins : [summary],
					id : 'PlBidAutoGrid',
					url : __ctxPath + "/creditFlow/auto/listPlBidAuto.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'userID', 'bidMoney', 'interestStart',
							'interestEnd', 'periodStart', 'periodEnd',
							'rateStart', 'rateEnd', 'keepMoney', 'isOpen',
							'orderTime', 'banned','userName'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '用户',
								align:'center',
								summaryRenderer : totalMoney,
								dataIndex : 'userName'
							}, {
								header : '投标金额',
								align:'right',
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"元";
				                   },
								dataIndex : 'bidMoney'
							}, {
								header : '利率下限',
								align:'right',
								dataIndex : 'interestStart'
							}, {
								header : '利率上限',
								align:'right',
								dataIndex : 'interestEnd'
							}, {
								header : '投资期限下限',
								align:'right',
								dataIndex : 'periodStart'
							}, {
								header : '投资期限上限',
								align:'right',
								dataIndex : 'periodEnd'
							}, {
								header : '信用等级下限',
								align:'right',
								dataIndex : 'rateStart'
							}, {
								header : '信用等级上限',
								align:'right',
								dataIndex : 'rateEnd'
							}, {
								header : '保留金额',
								align:'right',
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"元";
				                   },
								dataIndex : 'keepMoney'
							}, {
								header : '是否开启',
								align:'center',
								dataIndex : 'isOpen',
								 renderer:function(value){
					    	if(value==0){
					    		return '关闭'
					    	}else if(value==1){
					    		return '开启'
					    	}else{
					    		return ''
					    	}
					    }
							}, {
								header : '排序时间',
								align:'center',
								dataIndex : 'orderTime'
								
							}, {
								header : '是否禁用',
								align:'center',
								dataIndex : 'banned',
								 renderer:function(value){
					    	if(value==0){
					    		return '正常'
					    	}else if(value==1){
					    		return '禁用'
					    	}else{
					    		return ''
					    	}
					    }
							}, new Ext.ux.grid.RowActions({
										header : '管理',
										align:'center',
										width : 100,
										actions : [/*{
													iconCls : 'btn-del',
													qtip : '删除',
													style : 'margin:0 3px 0 3px'
												},*/ {
													iconCls : 'btn-edit',
													qtip : '编辑',
													style : 'margin:0 3px 0 3px'
												}],
										listeners : {
											scope : this,
											'action' : this.onRowAction
										}
									})]
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
							new PlBidAutoForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new PlBidAutoForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/creditFlow/auto/multiDelPlBidAuto.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/creditFlow/auto/multiDelPlBidAuto.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new PlBidAutoForm({
							id : record.data.id
						}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
