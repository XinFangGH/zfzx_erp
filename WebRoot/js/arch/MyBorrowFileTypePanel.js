/**
 * @author
 * @createtime
 * @class MyBorrowFileTypePanel
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */

Ext.ns('MyBorrowFileTypePanel');

MyBorrowFileTypePanel = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		MyBorrowFileTypePanel.superclass.constructor.call(this, {
					layout : 'fit',
					border : true,
					items : [this.gridPanel],
					frame : false

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		var comonFields = [{
					name : 'listId',
					type : 'int'
				}, 'recordId', 'listType', 'archFond', 'afNo', 'afName',
				'archRoll', 'rollNo', 'rolllName', 'rollFile', 'fileNo',
				'fileName',{name:'preview',type:'boolean'}];
		
		//远程数据源
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + "/arch/listCheckBorrowFileList.do",
								method : 'POST'
							}),
					
					reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						remoteSort : false,
						idProperty : "listId",
								fields : comonFields
							})

				});
		this.store.on('load',function(s,rs){
			Ext.each(rs,function(r){
				if(r.get('listType')=='文件'){
					r.set('preview',false);
					
				}else{
					r.set('preview',true);
				}
				r.commit();
			});
			s.commitChanges();
		});
		this.rowActions = new Ext.ux.grid.RowActions({
			header : '管理',
			width : 80,
			actions : [ {
						iconCls : 'btn-showDetail',
						qtip : '查看详细',
						style : 'margin:0 3px 0 3px'
					},{
						iconCls : 'btn-readdocument',
						qtip : '预览附件',
						style : 'margin:0 3px 0 3px',
						hideIndex : 'preview'	
					}]
		});
		var cm = new Ext.grid.ColumnModel({
					columns : [
					           new Ext.grid.RowNumberer(), {
								header : 'listId',
								dataIndex : 'listId',
								hidden : true
							}, {
								header : '借阅单位',
								dataIndex : 'listType'
							}, {
								header : '全宗ID',
								dataIndex : 'archFond',
								hidden : true,
								renderer : function(archFond) {
									if (archFond)
										return archFond.archFondId;
								}
							}, {
								header : '全宗号',
								dataIndex : 'archFond',
								renderer : function(archFond) {
									if (archFond)
										return archFond.afNo;
								}
							}, {
								header : '全宗名',
								dataIndex : 'archFond',
								renderer : function(archFond) {
									if (archFond)
										return archFond.afName;
								}
							}, {
								header : '案卷ID',
								hidden : true,
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.rollId;
								}
							}, {
								header : '案卷号',
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.rollNo;
								}
							}, {
								header : '案卷名',
								dataIndex : 'archRoll',
								renderer : function(archRoll) {
									if (archRoll)
										return archRoll.rolllName;
								}
							}, {
								header : '文件ID',
								hidden : true,
								dataIndex : 'rollFile',
								renderer : function(rollFile) {
									if (rollFile)
										return rollFile.rollFileId;
								}
							}, {
								header : '文件号',
								dataIndex : 'rollFile',
								renderer : function(rollFile) {
									if (rollFile)
										return rollFile.fileNo;
								}
							}, {
								header : '文件题名',
								dataIndex : 'rollFile',
								renderer : function(rollFile) {
									if (rollFile)
										return rollFile.fileName;
								}
							},this.rowActions

					],
					defaults : {
						sortable : true,
						menuDisabled : false
					}
				});

		this.topbar = new Ext.Toolbar({
					items : [{
								xtype : 'tbtext',
								text : '借阅清单'
							}, {
								xtype : 'tbseparator'
							}
							
							

					]

				});

		
		
		this.gridPanel = new Ext.grid.GridPanel({
					frame : false,
					border : false,
					//tbar : this.topbar,
					store : this.store,
					cm : cm,
					plugins : this.rowActions,
					// autoExpandColumn : 'fileName',
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					bbar : new HT.PagingBar({
								store : this.store 
							}),
					listeners : {}

				});

	}
	

});