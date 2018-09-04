/**
 * @description 文件分类上传管理
 * @author YHZ
 * @company www.credit-software.com
 * @datetime 2010-11-15 AM
 */
FileUploadManager = Ext.extend(Ext.Window, {
	
	constructor : function(_cfg) {
		Ext.applyIf(this,_cfg);
		this.initUIComponent();
		FileUploadManager.superclass.constructor.call(this, {
			id : 'fileUploadManager',
			layout : 'fit',
			title : '附件分类管理',
			iconCls : 'menu-file',
			width : 720,
			minWidth : 720,
			height : 550,
			minHeight : 550,
			maximizable : true,
			border : false,
			modal : true,
			items : [this.panel],
			buttonAlign : 'center',
			buttons : [{
				text : '确定',
				iconCls : 'btn-ok',
				scope : this,
				handler : this.submit
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.close
			}]
		}); // end of this constructor
	},// 初始化组件

	initUIComponent : function() {
		
		fileType = this.permitted_extensions;//判断附件类型[是否为图片]
		///////////////////////##treePanel##//////////////////////////////
		var imageYes = FileUploadManager.judgeImage(fileType); //判断是否为图片

		this.treePanel = new Ext.tree.TreePanel({
			id : 'fileUploadManagerFilesTreePanel',
			layout : 'form',
			region : 'west',
			title : '附件分类',
			collapsible : true,
			autoScroll : true,
			split : true,
			width : 180,
			tbar : new Ext.Toolbar({
				items : [{
					xtype : 'button',
					iconCls : 'btn-refresh',
					text : '刷新',
					scope:this,
					handler : function() {
						Ext.getCmp('fileUploadManagerFilesTreePanel').root.reload();
					}
				}, '-', {
					xtype : 'button',
					text : '展开',
					iconCls : 'btn-expand',
					handler : function() {
						Ext.getCmp('fileUploadManagerFilesTreePanel').expandAll();
					}
				}, '-', {
					xtype : 'button',
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function() {
						Ext.getCmp('fileUploadManagerFilesTreePanel').collapseAll();
					}
				}]
			}),
			loader : new Ext.tree.TreeLoader({
				 url : __ctxPath + '/system/treeGlobalType.do?catKey=ATTACHFILE_TYPE'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'click' : this.nodeClick
			}
		}); // end of this treePanel
		
		this.treePanel.on('contextmenu', contextmenu, this.treePanel);
		
		function contextmenu(node, e) {
			// 只有似有的才可以，修改，删除操作
			if(node.attributes.isPublic == 'false' || node.id == '0'){
				selected = new Ext.tree.TreeNode({
					id : node.id,
					text : node.text
				});
				// 创建右键菜单
				var treeMenu = new Ext.menu.Menu({
					items : []
				});
				treeMenu.clearMons();
				treeMenu.add({
					text : '新增',
					iconCls : 'btn-add',
					handler : addNode,
					scope : this
				});
				if(node.id > 0){ // 总分类不能删除，和修改
					treeMenu.add({
						text : '修改',
						iconCls : 'btn-edit',
						handler : editNode,
						scope : this
					}, {
						text : '删除',
						iconCls : 'btn-del',
						handler : delNode,
						scope : this
					});
				}  
				treeMenu.showAt(e.getXY());
			}
 		}
		
		// 添加节点
		function addNode(){
			var globalTypeTree = Ext.getCmp('fileUploadManagerFilesTreePanel');
	     	var parentId = selected.id;
			var globalTypeForm = new GlobalTypeForm({
	     		parentId : parentId,
	     		catKey : 'ATTACHFILE_TYPE',
	     		callback : function(){
	     			Ext.getCmp('fileUploadManagerFilesTreePanel').root.reload();
	     		}
	     	});
	     	globalTypeForm.show();
		}
		
		// 编辑节点
		function editNode(){
			if(selected != null && selected.id != null){
				var proTypeId = selected.id;				
				var globalTypeForm = new GlobalTypeForm({
					proTypeId : proTypeId,
					callback : function(){
						Ext.getCmp('fileUploadManagerFilesTreePanel').root.reload();
					}
				});
				globalTypeForm.show();
			}
		}
		
		// 删除节点
		function delNode(){
			if(selected != null && selected.id != null){
				Ext.Msg.confirm('操作提示', '你确定删除该数据?', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/system/delChildrensGlobalType.do?proTypeId=' + selected.id,
							success : function(result, request) {
								var res = Ext.util.JSON.decode(result.responseText);
								if (res.success == false) {
									Ext.ux.Toast.msg('操作提示', res.message);
								} else {
									Ext.ux.Toast.msg('操作提示', '删除成功!');
								}
								Ext.getCmp('fileUploadManagerFilesTreePanel').root.reload();
							},
							failure : function(result, request) {
							}
						});
					}
				});
			} else {
				Ext.ux.Toast.msg('请选择对应数据！', '操作提示');
			}
		}
		
		if(imageYes){			
			Ext.getCmp('fileUploadManagerFilesTreePanel').setTitle('图片分类');
		}
		////////////////end of this treePanel//////////////////////////
		
		////////////////////////////##gridPanel##/////////////////////
		var topbar = new Ext.Toolbar({
			height : 30,
			defaultType : 'button',
			items : [
//			         {
//				text : '上传',
//				iconCls : 'btn-upload',
//				handler : this.upLoadFile,
//				scope : this
//			}, '-', 
			{
				text : '上传',
				iconCls : 'btn-upload',
				handler : this.flexUploadFile,
				scope : this
			}, {
				xtype : 'hidden',
				id : 'fileuploadManager.panel.isImg', // 保存上传文件的格式[是图片true]
				value : imageYes
			}, ' /', {
				xtype : 'label',
				id : 'fileuploadManager.status',
				text : '总分类'
			}, '', {
				xtype : 'hidden',
				id : 'fileuploadManager.statusId',
				value : ''
			}]
		}); // end of this topbar

		// ##panel##//
		this.panel = new Ext.Panel({
			id : 'fileUploadManagerPanel',
			iconCls : 'menu-find-doc',
			layout : 'border',
			region : 'center',
			border : false
		});
		
		this.panel.add(this.treePanel);
		
		if(!imageYes){//列表展示附件
			this.gridPanel = new HT.GridPanel({
				id:'fileUploadManagerGridPanel',
				region : 'center',
				tbar : topbar,
				rowActions : true,
				url:__ctxPath + '/system/listFileAttach.do',
				fields : [ { name : 'fileId', type : 'int' }, 
		           {name:'fileName',mapping:'fileName'}, 'ext', 'note',
					'fileType','filePath','createtime','totalBytes'],
				columns : [{
					header : 'fileId',
					dataIndex : 'fileId',
					hidden : true
				}, {
					header : '附件名称',
					dataIndex : 'fileName'
				}, {
					header : '上传时间',
					dataIndex : 'createtime',
					format : 'y-m-d'
				}, {
					header : '大小',
					dataIndex : 'note'
				},
				new Ext.ux.grid.RowActions({
					header : '管理',
					width : 80,
					actions : [ {
						iconCls : 'btn-showDetail',
						qtip : '查看',
						style : 'margin:0 3px 0 3px'
					}, {
						iconCls : 'btn-downLoad',
						qtip : '下载',
						style : 'margin:0 3px 0 3px'
					}],
					listeners : {
							scope : this,
							'action' : this.onRowAction
					}
				})] 
			});

			this.gridPanel.addListener('rowdblclick', function(grid, rowIndex, e) {
				grid.getSelectionModel().each(function(rec) {
					FileAttachDetail.show(rec.data.fileId);
				});
			});
			this.panel.add(this.gridPanel); //附件列表
		} else {//图片附件
			/////////////////##显示图片的dataView中相关组件##//////////////
			this.imageStore = new Ext.data.JsonStore({
				id : 'id',
				url :  __ctxPath + '/system/listFileAttach.do?type=image',
			  	root : 'result',
			  	totalProperty : 'totalCounts',
			    fields: [
			    	{name : 'fileId',type:'int'}, 
		    		{name:'fileName',mapping:'fileName'}, 
		    		{name:'filePath',mapping:'filePath'}
			    ]
			});
			this.imageStore.load({params : {start : 0,limit : 10}});
		
			this.tpl = new Ext.XTemplate(
				'<tpl for=".">',
					'<div style="width:105px; height : 105px;" class="thumb-wrap" id="{fileId}">',
					'<img align="middle" src="'+__ctxPath+'/attachFiles/{filePath}" style="width:90px;height:90px;margin-left:7px;" title="{fileName}"/>',
					'<center><span style="margin-top:3px;">{fileName}</span><center>',
					'</div>', 
				'</tpl>'
			),
			this.dataView = new Ext.DataView({
				id : 'fileUploadManagerDataView',
				layout : 'form',
				region : 'center',
				store : this.imageStore,
				tpl : this.tpl,
				multiSelect : true,
				overClass : 'x-view-over',
				itemSelector : 'div.thumb-wrap',
				bodyStyle : 'padding:4px',
				emptyText : '目前尚无记录',
				listeners : {
					'dblclick' : {
						fn : this.imageDbClick.createCallback(this),
						scope : this
					}
				}
			}); // end of this dataView
			
			//图片展示,添加显示数据组件
			this.dataPanel = new Ext.Panel({
				layout : 'form',
				region : 'center',
				tbar : topbar,
				layout : 'fit',
				defaults:{
				   anchor : '96%,96%'
				},
				items : this.dataView,
				bbar : new Ext.PagingToolbar({
					pageSize : 10,
					store : this.imageStore,
					displayInfo : true,
					displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
					emptyMsg : "当前没有记录"
				})
			}); // end of this dataPanel

			this.panel.add(this.dataPanel); //图片展示
		}
		this.panel.doLayout();
	}, // end of this initUIComponent
	
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-showDetail' :
				this.showdetail(record.data.fileId);
				break;
			case 'btn-downLoad' :
				this.downLoad(record.data.fileId);
				break;
		}
	},

	/**
	 * 查看详细信息
	 */
	showdetail : function(fileId) {
		FileAttachDetail.show(fileId);
	},

	/**
	 * 下载
	 */
	downLoad : function(fileId) {
		window.open(__ctxPath + "/file-downLoad?fileId="+fileId);
	},

	/**
	 * 文件上传[flex版本]
	 */
	flexUploadFile : function(){
		var fileCat = Ext.getCmp('fileuploadManager.statusId').value;
		var treePnl = Ext.getCmp('fileUploadManagerFilesTreePanel');
		var node = treePnl.getSelectionModel().getSelectedNode();
		var proTypeId = 0;
		if(node != null && node.id > 0){
			proTypeId = node.id;
		}
		if(fileCat != null && fileCat != ''){
			FlexUploadDialog.show(fileCat, proTypeId);
		} else {
			var callback = this.callback;
			FlexUploadDialog.show(this.file_cat, proTypeId);
		}
		
	},
	
	/**
	 * 文件上传
	 */
	upLoadFile : function() {
		var callback = this.callback;
		var dialog = new Ext.ux.UploadDialog.Dialog({
			file_cat : this.file_cat,
			url : this.url,
			scope : this,
			callback : function(obj){
				if(obj != null && obj.length > 0){
					var isImg = Ext.getCmp('fileuploadManager.panel.isImg').getValue();
					if(isImg != '' && isImg == 'true'){ // 图片上传
						var store = Ext.getCmp('fileUploadManager').dataView.getStore();
						store.reload({
							params : {
								start : 0,
								limit : 10
							}
						});
					} else { //非图片上传,刷新 			
						var grid = Ext.getCmp('fileUploadManagerGridPanel');
						if(grid != null){
							grid.getStore().reload();
						}
					}
					if (callback != null){						
						callback.call(this, obj);
					}
				}
				Ext.getCmp('fileUploadManager').close();
			}
		});
		dialog.show('queryWindow');
	},

	/**
	 * 确定 
	 */
	submit : function() {
		if(this.dialog){
			this.dialog.close();
		}
		var callback = this.callback;
		var isImg = Ext.getCmp('fileuploadManager.panel.isImg').getValue(); // 判断是否为图片
		
		var records = null;
		if(isImg != null && isImg == 'true'){ //上传image图片
			var dv = Ext.getCmp('fileUploadManager').dataView;
			if(dv != null){
				records = dv.getSelectedRecords();	
			}
		} else {
			var gridPanel = Ext.getCmp('fileUploadManagerGridPanel');
			records = gridPanel.getSelectionModel().getSelections();
		}
		var arr = new Array();
		if(records != null && records.length > 0){	
			for(var i = 0 ; i < records.length ; i++){
				arr.push(records[i].data);
			}
		}
		
		if (callback != null){	
			var scope=this.scope?this.scope:this;
			callback.call(scope, arr);
		}
		this.close();
//		Ext.getCmp('fileUploadManager').close();
	},
	
	/**
	 * 节点单击事件
	 * @param {} node
	 */
	nodeClick : function(node){
		if (node != null) {
			var file_type = '';
			if(node.getDepth() > 1 && node.attributes.nodeKey != 'file-type'){
				file_type = node.attributes.nodeKey;
			}
			var nodeLabel = '';
			var nodeKey = '';
			node.bubble(function(node){
				if(node.text != undefined){					
					if(node.text == '总分类'){
						nodeLabel = '  总分类/' + nodeLabel;
					} else {					
						nodeLabel = node.text + '/' + nodeLabel;
						nodeKey = node.attributes.nodeKey + '/' + nodeKey;
					}
				}
			});
			nodeLabel = nodeLabel.substring(0, nodeLabel.length-1);
			Ext.getCmp('fileuploadManager.status').setText(nodeLabel);
			nodeKey = nodeKey.substring(0, nodeKey.length - 1);
			Ext.getCmp('fileuploadManager.statusId').setValue(nodeKey);
			Ext.getCmp('fileuploadManager.statusId').setValue(file_type);
			var isImg = Ext.getCmp('fileuploadManager.panel.isImg').getValue(); // 判断是否为图片
			if(isImg != null && isImg == 'true'){ // 图片
				var store = Ext.getCmp('fileUploadManager').dataView.getStore();
				store.url = __ctxPath + '/system/listFileAttach.do?type=image';
				store.reload({
					params : {
						start : 0,
						limit : 10,
						type : 'image',
						fileType : file_type
					}
				});
			} else { // 非图片				
				var store = Ext.getCmp('fileUploadManagerGridPanel').getStore();
				store.url = __ctxPath + '/system/listFileAttach.do';
				store.reload({
					params : {
						start : 0,
						limit : 25,
						type : 'file',
						fileType : file_type
					}
				});
			}
		}
	},
	
	/**
	 * Image图片双击事件，显示图片信息
	 */
	imageDbClick : function(self){
		var nodes = self.dataView.getSelectedNodes();
		if(nodes != '' && nodes != null && nodes != 'undefined'){
			FileUploadImageDetailForm.show(nodes[0].id);
		}
	} 
});

/**
 * @desription 判断是否为图片，true:图片
 * @remark 图片格式:jpg|gif|jpeg|png|bmp|JPG|GIF|JPEG|PNG|BPM
 * @param {} types
 */
FileUploadManager.judgeImage = function(types){
	//var type = this.permitted_extensions;
	//图片格式:jpg|gif|jpeg|png|bmp|JPG|GIF|JPEG|PNG|BPM
	if(types != null && types != 'undefined'){
		for(var i = 0 ; i < types.length ; i++){
			var type = types[i].toLowerCase();
			if(type=='bmp' || type=='png' || type=='jpeg' || type=='jpg' || type=='tiff' || type=='gif'){ //上传image图片
				return true;
			}
		}
	}
	return false;
};
