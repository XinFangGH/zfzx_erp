/**
 * @author:
 * @class MyBorrowFileSlaveListGrid
 * @extends Ext.Panel
 * @description 附件管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MyBorrowFileSlaveListGrid = Ext.extend(Ext.grid.GridPanel, {


	viewConfig : null,
	startIndex : 0,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		//this.viewConfig=new Array ();
		this.initUIComponents();
		// 调用父类构造
		MyBorrowFileSlaveListGrid.superclass.constructor.call(this, {
					tbar : this.topbar,
					store : this.store,
					plugins : this.rowActions,
					trackMouseOver : true,
					autoScroll : true,
					disableSelection : false,
					loadMask : true,
					frame : false,
					cm : this.cm,
					//sm : this.sm,
					autoExpandColumn : 'shortDesc',
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					listeners : {}

				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

		this.topbar = new Ext.Toolbar({
					items : [{
								xtype : 'tbtext',
								text : '附件'
							}, {
								xtype : 'tbseparator'
							}
					
					]
				});
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/arch/listRollFileList.do'
							}),
					autoLoad : false,
					reader : new Ext.data.JsonReader({

								root : 'result',
								totalProperty : 'totalCounts',
								id : 'listId',
								fields : [{
											name : 'listId',
											type : 'int'
										}, 'downLoads', 'sn', 'shortDesc',
										'rollFileId', 'fileAttach']
							}),
					remoteSort : true
				});

		this.store.setDefaultSort('sn', 'asc');
		this.store.on('load', function(store, records) {
			this.viewConfig=[];
			Ext.each(records, function(record) {
						
						
							this.viewConfig.push({
										fileName : record.data.fileAttach.fileName,
										filePath : record.data.fileAttach.filePath
									});
						
					}, this);
					
				

		}, this);

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 15,
					actions : [
					{
								iconCls : 'btn-readdocument',
								qtip : '预览',
								style : 'margin:0 3px 0 3px'
							},
							{
								iconCls : 'btn-downLoad',
								qtip : '下载',
								style : 'margin:0 3px 0 3px'
							}]
				});
		this.rowActions.on('action', this.onRowAction, this);
		//this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel({
					columns : [
							//this.sm
							// , new Ext.grid.RowNumberer()
							{
								header : 'listId',
								dataIndex : 'listId',
								hidden : true
							}

							// , {
							// header : '卷ID',
							// dataIndex : 'rollFileId'
							// }, {
							// header : '附ID',
							// dataIndex : 'fileAttach',
							// renderer : function(fileAttach) {
							// return fileAttach.fileId;
							// }
							// }

							, {
								header : '序号',
								width : 5,
								dataIndex : 'sn'
							}

							, {
								header : '文件名称',
								width : 20,
								dataIndex : 'fileAttach'
									,
								renderer : function(fileAttach) {
									if(fileAttach)
									return fileAttach.fileName;
								}
							}

							// , {
							// header : '文件路径',
							// dataIndex : 'fileAttach',
							// renderer : function(fileAttach) {
							// return fileAttach.filePath;
							// }
							// }

							// , {
							// header : '文件类型',
							// dataIndex : 'fileAttach',
							// renderer:function(fileAttach){
							// return fileAttach.ext;
							// }
							// }

							// , {
							// header : '录入人',
							// dataIndex : 'fileAttach',
							// renderer:function(fileAttach){
							// return fileAttach.creator;
							// }
							// }, {
							// header : '录入时间',
							// dataIndex : 'fileAttach',
							// renderer:function(fileAttach){
							// return fileAttach.createtime;
							// }
							// }

							, {
								header : '概要',
								dataIndex : 'shortDesc'
								
							}, {
								header : '下载次数',
								width : 10,
								dataIndex : 'downLoads'
							}, this.rowActions],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 40
					}
				});

	},// end of the initComponents()


	
	downLoad : function(record) {
	
		window.open(__ctxPath + '/attachFiles/'
				+ record.data.fileAttach.filePath);
		
		record.data.downLoads = record.data.downLoads + 1;
		var fileAttach = record.data['fileAttach'];
		         					Ext.apply(fileAttach, {
		         								createtime : new Date(fileAttach.createtime).format('Y-m-d')
		         							});
		var rollFile={};
		Ext.apply(rollFile, {
			rollFileId : this.rollFileId
			});
		Ext.apply(record.data, {
			rollFile : rollFile
			});
		
		record.markDirty();
		var params = [];
		params.push(record.data);
		
		
		
		var store=this.store;
		Ext.Ajax.request({
			url : __ctxPath + '/arch/updateDownLoadRollFile.do',
			method : 'POST',
			//async : true,
			success : function(response, opts) {
			
				store.reload();
			},
			
			failure : function(response, opts) {

			},
			params : {
				rollFileId:this.rollFileId,
				params : Ext.encode(params)
			}
		});
		
	},
	
	

	sn : function(grid, thisRow, nextRow) {},
	viewFile:function(row){
		new ViewFileWindow({viewConfig:this.viewConfig,startIndex:row}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-readdocument':
				this.viewFile.call(this,row);
				break;
			case 'btn-downLoad' :
				this.downLoad.call(this, record);
				break;
			

		}
	}
});
