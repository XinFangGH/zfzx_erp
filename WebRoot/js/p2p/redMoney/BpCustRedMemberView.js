/**
 * @author
 * @class BpCustRedMemberView
 * @extends Ext.Panel
 * @description [BpCustRedMember]管理
 * @company 智维软件
 * @createtime:
 */
BpCustRedMemberView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCustRedMemberView.superclass.constructor.call(this, {
							id : '',
							title : '用户红包获取日志',
							region : 'center',
							layout : 'border',
							iconCls:"menu-finance",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				
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
								columnWidth : .18,
								layout : 'form',
								labelWidth : 40,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '用户名',
											name : 'bpCustMemberName',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								columnWidth : .18,
								layout : 'form',
								labelWidth : 40,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '姓名',
											name : 'trueName',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								columnWidth : .18,
								layout : 'form',
								labelWidth : 55,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '活动编号',
											name : 'activityNumber',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								columnWidth : .18,
								layout : 'form',
								labelWidth : 50,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '派发时间',
											name : 'distributeTime',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield',
											labelSeparator : ""
										}]
							}, {
								columnWidth : .100,
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
								columnWidth : .100,
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
							items : [{
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								}]
						});
             var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
				    plugins : [summary],
					// 使用RowActions
					rowActions : false,
					id : 'BpCustRedMemberGrid',
					url : __ctxPath + "/customer/listBpCustRedMember.do",
					fields : [{
								name : 'redTopersonId',
								type : 'int'
							}, 
							'redId',
							'redMoney', 
							'bpCustMemberId', 
							'bpCustMemberName', 
							'edredMoney', 
							'distributeTime', 
							'redType', 
							'activityNumber',
							'description',
							'sendType',
							'logginName'
							],
					columns : [{
								header : 'redTopersonId',
								dataIndex : 'redTopersonId',
								hidden : true
							}, {
								header : 'redId',
								dataIndex : 'redId',
								hidden : true
							},{
								header : '用户名',
							    align:"center",
								dataIndex : 'logginName',
							    summaryRenderer : totalMoney
							},{
								header : '投资人姓名',
								 align:"center",
								dataIndex : 'bpCustMemberName'
							}, {
								header : '金额',
								 align:"right",
								dataIndex : 'redMoney',
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"元";
				                   }
							}, {
								header : '派发时间',
								 align:"center",
								dataIndex : 'distributeTime'
							},{
								header : '活动编号',
								 align:"center",
								dataIndex : 'activityNumber'
							}, {
								header : '说明',
								 align:"left",
								dataIndex : 'description'
							}]
						// end of columns
				});

			//	this.gridPanel.addListener('rowdblclick', this.rowClick);

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
							new BpCustRedMemberForm({
										redTopersonId : rec.data.redTopersonId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new BpCustRedMemberForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/customer/multiDelBpCustRedMember.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/customer/multiDelBpCustRedMember.do',
							grid : this.gridPanel,
							idName : 'redTopersonId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new BpCustRedMemberForm({
							redTopersonId : record.data.redTopersonId
						}).show();
			},exportExcel:function(){
					var bpCustMemberName = this.getCmpByName("bpCustMemberName").getValue();
					var activityNumber = this.getCmpByName("activityNumber").getValue();
					var distributeTime = this.getCmpByName("distributeTime").getValue();
					var trueName = this.getCmpByName("trueName").getValue();
					var time ="";
					if(distributeTime!=""){
						 time = distributeTime.format("Y-m-d");
					}
					window.open( __ctxPath + "/customer/exportExcelBpCustRedMember.do?bpCustMemberName="+bpCustMemberName+"&activityNumber="+activityNumber+"" +
							"&distributeTime="+time+"&trueName="+trueName+"");
	},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.redTopersonId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
