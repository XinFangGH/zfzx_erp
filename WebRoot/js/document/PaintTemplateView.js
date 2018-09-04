/**
 * @author:
 * @class PaintTemplateView
 * @extends Ext.Panel
 * @description [PaintTemplate]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PaintTemplateView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PaintTemplateView.superclass.constructor.call(this, {
							id : 'PaintTemplateView',
							title : '套红模板管理',
							region : 'center',
							iconCls:'menu-template',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new Ext.FormPanel({
					    height : 35,
						region : 'north',
						frame : false,
						border : false,
						layout : 'hbox',
						layoutConfig : {
							padding : '5',
							align : 'middle'
						},
						defaults : {
							style : 'padding:0px 5px 0px 5px;',
							border : false,
							anchor : '98%,98%',
							labelWidth : 75,
							xtype : 'label'
						},
					   items:[
					   {
					   	 text:'模板名称：'
					   },{
					   	xtype:'textfield',name:'Q_templateName_S_LK'
					   },{
						   xtype:'button',
						   text:'查询',
						   scope:this,
						   iconCls:'btn-search',
						   handler:this.search
					   },{
						   xtype:'button',
						   text:'重置',
						   scope:this,
						   iconCls:'btn-reset',
						   handler:this.reset
					   }]
			   }); 

				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加模板',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除模板',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'PaintTemplateGrid',
					url : __ctxPath + "/document/listPaintTemplate.do",
					fields : [{
								name : 'ptemplateId',
								type : 'int'
							}, 'fileId', 'templateName', 'templateKey', 'path', 'isActivate','fileAttach','fileAttach.note'],
					columns : [{
								header : 'ptemplateId',
								dataIndex : 'ptemplateId',
								hidden : true
							}, {
								header : 'fileId',
								hidden:true,
								dataIndex : 'fileId'
							}, {
								header : '模板名称',
								dataIndex : 'templateName'
							},{
								header: '模板Key',
								dataIndex:'templateKey'
							},{
							    header:'文件大小',
							    dataIndex:'fileAttach.note'
							}
							, new Ext.ux.grid.RowActions({
										header : '管理',
										actions : [{
													iconCls : 'btn-del',
													qtip : '删除',
													style : 'margin:0 3px 0 3px'
												}
												,{
												     iconCls:'btn-edit-online',
												     qtip:'在线修改',
												     style:'margin:0 3px 0 3px'
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
							new PaintTemplateForm({
										ptemplateId : rec.data.ptemplateId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
//				new PaintTemplateForm().show();
				new DocumentTemplateForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/document/multiDelPaintTemplate.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/document/multiDelPaintTemplate.do',
							grid : this.gridPanel,
							idName : 'ptemplateId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new PaintTemplateForm({
							ptemplateId : record.data.ptemplateId
						}).show();
			},
			editOnline:function(record){
//				 var af=record.data.fileAttach;
			     new DocumentTemplateForm({
			         ptemplateId : record.data.ptemplateId
			     }).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.ptemplateId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					case 'btn-edit-online' :
						this.editOnline.call(this, record);
						break;
					default :
						break;
				}
			}
		});
