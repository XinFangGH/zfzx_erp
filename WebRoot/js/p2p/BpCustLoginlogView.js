/**
 * @author
 * @class BpCustLoginlogView
 * @extends Ext.Panel
 * @description [BpCustLoginlog]管理
 * @company 智维软件
 * @createtime:
 */
BpCustLoginlogView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCustLoginlogView.superclass.constructor.call(this, {
							id : 'BpCustLoginlogView',
							title : '用户登录日志',
							region : 'center',
							iconCls:"menu-finance",
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							border : false,
							height : 50,
							anchor : '80%',
							layoutConfig: {
				               align:'middle'
				            },
				            bodyStyle : 'padding:10px 10px 10px 10px',
							items:[{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'登录名',
											name : 'memberName',
											anchor : "90%",
											xtype : 'textfield'
											}]
					     	},{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'登录IP',
											name : 'loginIp',
											anchor : "90%",
											xtype : 'textfield'
											}]
					     	},{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'登录时间',
											name : 'loginTime',
											flex:1,
											align:'center',
											xtype:'datefield',
											format:'Y-m-d',
											anchor : "90%"
											}]
					     	},/*{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'退出时间',
											name : 'exitTime',
											flex:1,
											xtype:'datefield',
											format:'Y-m-d',
											anchor : "90%"
											}]
					     	},*/{
					     		columnWidth :.1,
								layout : 'form',
								labelWidth : 50,
								labelAlign : 'right',
								border : false,
								items : [{
										text : '查询',
										xtype : 'button',
										scope : this,
										style :'margin-left:30px',
										anchor : "90%",
										iconCls : 'btn-search',
										handler : this.search
									}]
					     	},{
					     		columnWidth :.1,
								layout : 'form',
								labelWidth : 50,
								labelAlign : 'right',
								border : false,
								items : [{
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
									text : '添加[日志信息]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[日志信息]',
									xtype : 'button',
									scope:this,
									handler : this.removeSelRs
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					//tbar:this.topbar,
					//使用RowActions
					//rowActions:true,
					id:'BpCustLoginlogGrid',
					url : __ctxPath + "/p2p/listBpCustLoginlog.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
								,'type'
								,'loginIp'
								,'loginTime'
								,'memberId'
								,'exitTime'
								,'loginMemberName'
							 ], 
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '登录IP',	
									dataIndex : 'loginIp'
								},{
									header : '登录名',	
									dataIndex : 'loginMemberName'
								},{
									header : '登录时间',	
									dataIndex : 'loginTime'
								},{
									header : 'memberId',	
									dataIndex : 'memberId',
									hidden : true
								}/*,{
									header : '退出时间',	
									dataIndex : 'exitTime'
								}*/
					]//end of columns
				});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpCustLoginlogForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new BpCustLoginlogForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p/multiDelBpCustLoginlog.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p/multiDelBpCustLoginlog.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new BpCustLoginlogForm({
					id : record.data.id
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
