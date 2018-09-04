Ext.ns('glOtherArchive');
glOtherArchive.jStore;
GlOtherArchiveView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		GlOtherArchiveView.superclass.constructor.call(this, {
					id : 'GlOtherArchiveViewId',
					region : 'center',
					layout : 'anchor',
					items : [this.glOtherArchive_gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var gdCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					header : '是否归档',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
				});		
		this.glOtherArchive_Record = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'},{name : 'remark'},{name : 'businessType'}, {name : 'mark'},{name : 'filepath'}, {name : 'isArchiveConfirm'}, {name : 'archiveConfirmRemark'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'}]);
		glOtherArchive.jStore = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/getArchiveListByBusinessTypeFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.glOtherArchive_Record
            
        });
		glOtherArchive.jStore.load({
			scope : this,
			params : {
				projId : this.projectId,
				businessType : this.businessType,
				//"from FileForm AS f where f.businessType=? and f.projId=? and f.remark in("+remark+")";
				remark : "'typeisglborrowguarantee'"
			}
		});
		this.glOtherArchive_gridPanel = new HT.EditorGridPanel({
			id : 'glOtherArchive_gridPanel_id',
			autoWidth : true,
			store : glOtherArchive.jStore,
			autoExpandColumn : 'filename',
			autoScroll : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			plugins : [gdCheckColumn],
			bbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
						
			columns : [{
				header : '',
				dataIndex : 'fileid',
				hidden :true,
				scope : this
			},{
				header : '文件名称',
				width : 150,
				dataIndex : 'filename'
			}, {
				header : '下载打印',
				dataIndex : 'fileid',
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downLoadFileOther(\''+val+'\')" ><font color=blue>下载</font></a>';
				}
			},gdCheckColumn
//			{
//				header : '是否归档',
//				dataIndex : 'isArchiveConfirm',
//				renderer : this.isGlOtherArchive,
//				editor : new Ext.form.ComboBox({
//					mode : 'local',
//					editable : false,
//					store : new Ext.data.SimpleStore({
//						data : [['是', true], ['否', false]],
//						fields : ['text', 'value']
//					}),
//					displayField : 'text',
//					valueField : 'value',
//					triggerAction : 'all'
//				})
//			}
			, {
				header : '备注',
				dataIndex : 'archiveConfirmRemark',
				editor : new Ext.form.TextField({})
			}],
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['fileid'];
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(e.field == 'isArchiveConfirm') {
							args = {'fileinfo.isArchiveConfirm': value,'fileinfo.fileid': id};
						}
						if(e.field == 'archiveConfirmRemark') {
							args = {'fileinfo.archiveConfirmRemark': value,'fileinfo.fileid': id};
						}
						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/fileUploads/ajaxArchiveConfirmFileForm.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				}
			}
		});
	},// end of the initComponents()
	isGlOtherArchive : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
	},
	savereload:function(){
		this.glOtherArchive_gridPanel.getStore().reload();
	}
});

downLoadFileOther = function(v){
	window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+v);
		//uploadfile('下载附件',v,0,null,null,null);
};