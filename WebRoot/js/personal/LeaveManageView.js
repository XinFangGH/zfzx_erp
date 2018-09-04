/**
 * @author:
 * @class LeaveManageView
 * @extends Ext.Panel
 * @description 请假管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
LeaveManageView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				LeaveManageView.superclass.constructor.call(this, {
							id : 'LeaveManageView',
							title : '请假管理',
							region : 'center',
							layout : 'border',
							iconCls : 'menu-holiday',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							id : 'LeaveManageSearchForm',
			height : 40,
			frame : false,
			region : 'north',
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '查询条件:'
					}, {
						text : '开始时间:从'
					}, {
						xtype : 'datetimefield',
						format:'Y-m-d H:i:s',
						name : 'Q_startTime_D_GE',
						editable:false
					}, {
						text : '到'
					}, {
						xtype : 'datetimefield',
						format:'Y-m-d H:i:s',
						name : 'Q_endTime_D_LE',
						editable:false
					}, {
						text : '审批状态'
					 }, {
						xtype : 'combo',
						hiddenName : 'Q_status_SN_EQ',
						mode : 'local',
						width:80,
						editable : false,
						triggerAction : 'all',
						store : [['0','未审批'],['1','通过审批'],['2','未通过审批']]
					},{
										text : '查询',
										xtype : 'button',
										scope : this,
										iconCls : 'btn-search',
										handler : this.search
									}, {
										text : '重置',
										xtype : 'button',
										scope : this,
										iconCls : 'btn-reset',
										handler : this.reset
									}]
			});// end of searchPanel

				this.topbar = new Ext.Toolbar({
							items : [
//								{
//							iconCls : 'btn-add',
//							text : '添加请假单',
//							xtype : 'button',
//							scope : this,
//							handler : this.createRs
//						}, {
//							iconCls : 'btn-del',
//							text : '删除请假单',
//							xtype : 'button',
//							scope : this,
//							handler : this.removeSelRs
//						}
					]}
				);

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					height : 500,
					// 使用RowActions
					rowActions : true,
					id : 'LeaveManageGrid',
					url : __ctxPath + '/personal/listErrandsRegister.do?Q_approvalId_L_EQ='+curUserInfo.userId+'&Q_flag_SN_EQ='+1,
					fields : [{
										name : 'dateId',
										type : 'int'
									}

									, {
										name : 'userName',
										mapping : 'appUser.fullname'
									}, 'descp', 'startTime', 'endTime',
									'approvalId', 'status', 'approvalOption',
									'approvalName', 'flag'],
					columns : [{
					header : 'dateId',
					dataIndex : 'dateId',
					hidden : true
				}, {
					header : '描述',
					dataIndex : 'descp'
				}, {
					header : '开始日期',
					dataIndex : 'startTime'
				}, {
					header : '结束日期',
					dataIndex : 'endTime'
				}, {
					header : '审批状态',
					dataIndex : 'status',
					renderer:function(value){
						if(value=='0'){
						  return '未审批';
						}
						if(value=='1'){
						  return '<font color="green">通过审批</font>';
						}
						if(value=='2'){
							return '<font color="red">未通过审批</font>';
						}
					}
				}, {
					header : '审批意见',
					dataIndex : 'approvalOption'
				}, {
					header : '审批人',
					dataIndex : 'approvalName'
				},
//				{
//					header : '管理',
//					dataIndex : 'dateId',
//					width : 50,
//					sortable : false,
//					renderer : function(value, metadata, record, rowIndex,
//							colIndex) {
//						var editId = record.data.dateId;
//						var str = '<button title="删除" value=" " class="btn-del" onclick="ErrandsRegisterView.remove('
//								+ editId + ')">&nbsp;&nbsp;</button>';
//						str += '&nbsp;<button title="详细" value=" " class="btn-showDetail" onclick="ErrandsRegisterView.detail('
//								+ editId + ')">&nbsp;&nbsp;</button>';
//						
//						return str;
//					}
//				}, 
				new Ext.ux.grid.RowActions({
										header : '管理',
										width : 100,
										actions : [
//											{
//													iconCls : 'btn-del',
//													qtip : '删除',
//													style : 'margin:0 3px 0 3px'
//												}, {
//													iconCls : 'btn-edit',
//													qtip : '编辑',
//													style : 'margin:0 3px 0 3px'
//												}, 
													{
													iconCls : 'btn-suggest-scan',
													qtip : '预览',
													stype : 'margin:0 3px 0 3px'
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
							new LeaveManageWin({dateId:rec.data.dateId}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new ErrandsRegisterForm();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/personal/multiDelErrandsRegister.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath+ '/personal/multiDelErrandsRegister.do',
							grid : this.gridPanel,
							idName : 'dateId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new ErrandsRegisterForm(record.data.dateId);
			},
			// 预览Rs
			detailRs : function(record) {
				new LeaveManageWin({dateId:record.data.dateId}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
//					case 'btn-del' :
//						this.removeRs.call(this, record.data.dateId);
//						break;
//					case 'btn-edit' :
//						this.editRs.call(this, record);
//						break;
					case 'btn-suggest-scan' :
						this.detailRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
