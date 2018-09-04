/**
 * OaNewsMessageType.js
 */
/**
 * @author
 * @class OaNewsMessageType
 * @extends Ext.Panel
 * @description 站内信类型管理
 * @company 智维软件
 * @createtime:
 */
OaNewsMessageType = Ext.extend(Ext.Panel, {
	       querySql:"",//不同状态列表查询SQL
	       isSeeRs:true,//查询按钮，默认不显示
	       isEditRs:true,//编辑站内信，默认不显示
	       isDeleteRs:true,//删除站内信，默认不显示
	       isSendRs:true,//发送站内信，默认不显示
	       title:"站内信类型管理",
	       iconCls:"menu-finance",
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				OaNewsMessageType.superclass.constructor.call(this, {
							id : 'OaNewsMessageType'+this.messageType,
							title : this.title,
							region : 'center',
							layout : 'border',
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '新增站内信类型',
									xtype : 'button',
									scope : this,
									//hidden:this.isSeeRs,
									handler : this.createRs
								},{
									iconCls : 'btn-edit',
									text : '编辑站内信类型',
									xtype : 'button',
									scope : this,
									//hidden:this.isEditRs,
									handler : this.editRs
								},{
									iconCls : 'btn-del',
									text : '删除站内信类型',
									xtype : 'button',
									scope : this,
									//hidden:this.isEditRs,
									handler : this.removeRs
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					url : __ctxPath + "/system/listbyglobleTypeDictionary.do?nodeKey=webletter"/*+this.nodeKey*/,
					fields : [{
									name : 'dicId',
									type : 'int'
								}
									,'proTypeId'
									,'parentId'
									,'itemName'
									,'itemValue'
									,'dicKey'
									,'descp'
									,'sn'
							],
					columns:[{
									header : 'dicId',
									dataIndex : 'dicId',
									hidden : true
								},{
									header : 'proTypeId',
									dataIndex : 'proTypeId',
									hidden : true
								},{
									header : 'parentId',
									dataIndex : 'parentId',
									hidden : true
								},{
									header : '类型',	
									align:'center',
									dataIndex : 'itemValue'
								},{
									header : 'key值 ',
									align:'center',
									dataIndex : 'dicKey'
								},{
									header : '描述',
									align:'left',
									dataIndex : 'descp'
								},{
									header : '排序',
									align:'center',
									dataIndex : 'sn'
								}]
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
					new OaNewsMessageForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				var panel=this.gridPanel
				new OaNewsMessageTypeForm({
					gridPanel:panel,
					nodeKey:"webletter"
				}).show();
			},
			//按选中记录删除记录
			removeRs : function() {
				var gridPanel = this.gridPanel;
				var selectRecords = gridPanel.getSelectionModel().getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.dicId);
				}
				Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath + '/system/multiDelDictionary.do',
									params : {
										ids : ids
									},
									method : 'POST',
									success : function(response, options) {
										Ext.ux.Toast.msg('操作信息','成功删除该站内信类型！');
										gridPanel.getStore().reload();
									},
									failure : function(response, options) {
										Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
									}
								});
					}
				});
					
			},
			editRs:function(){
				var grid = this.gridPanel;
				var store = grid.getStore();
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.Msg.alert('状态', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.Msg.alert('状态', '只能选中一条记录');
					return false;
				} else {
					var record=s[0];
					new OaNewsMessageTypeForm({
						    gridPanel:grid,
							dicId  : record.data.dicId,
							readOnly:true,
							nodeKey:"webletter"
						}).show();
				}
			}
			
});
