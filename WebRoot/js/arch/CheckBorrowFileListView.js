/**
 * @author
 * @createtime
 * @class CheckBorrowFileListView
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */

Ext.ns('CheckBorrowFileListView');

CheckBorrowFileListView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CheckBorrowFileListView.superclass.constructor.call(this, {
					layout : 'fit',
					border : true,
					items : [this.gridPanel],
					// title : '',
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
				'fileName'];
		
		//远程数据源
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + "/arch/listCheckBorrowFileList.do",
								method : 'GET'
							}),
					
					reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						remoteSort : false,
						idProperty : "listId",
								fields : comonFields
							})

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
								renderer:function(archFond){
								if(archFond)return archFond.afNo;
								
							}
							}, {
								header : '全宗名',
								dataIndex : 'archFond',
								renderer:function(archFond){
									if(archFond)return archFond.afName;
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
								renderer:function(archRoll){
								if (archRoll)
									return archRoll.rollNo;
							}
							}, {
								header : '案卷名',
								dataIndex : 'archRoll',
								renderer:function(archRoll){
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
								dataIndex : 'fileNo'
							}, {
								header : '文件题名',
								dataIndex : 'fileName'
							}

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
					tbar : this.topbar,
					store : this.store,
					cm : cm,
					
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

	},
	//选择全宗
	createFond : function() {},
	//选择案卷
	createRoll : function() {},
	//选择文件
	createFile : function() {},

	// 按ID删除记录
	removeByIds : function(ids) {},
	// 把选中ID删除
	removeSelRs : function() {}

});