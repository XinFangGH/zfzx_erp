Ext.ns('glReport');
glReport.jStore;
GlReportArchiveView = Ext.extend(Ext.Panel, {
	// 构造函数
	isHidden:false,
	constructor : function(_cfg) {
		if(typeof(_cfg.isHidden)!="undifined"){
			this.isHidden=_cfg.isHidden
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		GlReportArchiveView.superclass.constructor.call(this, {
					id : 'GlReportArchiveViewId',
					region : 'center',
					layout : 'anchor',
					items : [this.glReport_gridPanel]
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
		this.glReport_Record = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'},{name : 'remark'},{name : 'businessType'}, {name : 'mark'},{name : 'filepath'}, {name : 'isArchiveConfirm'}, {name : 'archiveConfirmRemark'}, {name : 'createtime'}, {name : 'contentType'}, {name : 'webPath'},{name : 'projId'},{name:'setname'},{name:'filesize'},{name:'extendname'}]);
		glReport.jStore = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/getArchiveListByBusinessTypeFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.glReport_Record
            
        });
		glReport.jStore.load({
			scope : this,
			params : {
				projId : this.projectId,
				businessType : this.businessType,
				//"from FileForm AS f where f.businessType=? and f.projId=? and f.remark in("+remark+")";
				remark : "'typeisfile'"
			}
		});
		
		this.glReport_gridPanel = new HT.EditorGridPanel({
			border : false,
			hiddenCm :true,
			id : 'glReport_gridPanel_id',
			autoWidth : true,
			store : glReport.jStore,
			autoExpandColumn : 'filename',
			autoScroll : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			plugins : [gdCheckColumn],
			hiddenCm : true,
			isShowTbar : false,
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
			}, {
				header : '类型',
				dataIndex : 'setname',
				hidden:this.isHidden
			},{
				header : '文件名称',
				dataIndex : 'filename'
			}, {
				header : '大小',
				width : 60,
				dataIndex : 'filesize',
				hidden:this.isHidden,
				renderer : this.transition
			}, {
				header : '扩展名',
				width : 50,
				dataIndex : 'extendname',
				hidden:this.isHidden
			}, {
				header : '上传时间',
				width : 76,
				dataIndex : 'createtime',
				hidden:this.isHidden/*,
				renderer : function(v){
					v=v.trim();
					return v.substring(0,v.lastIndexOf(" "))
				}*/
			}, {
				header : '下载打印',
				width : 66,
				dataIndex : 'fileid',
				scope : this,
				renderer : function(val, m, r) {
					return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downLoadFile_report(\''+val+'\')" >下载</a>';
				}
			}, {
				header : '在线预览',
				width : 66,
				dataIndex : 'lawName',
				hidden:this.isHidden,
				renderer : function(val, m, r) {
					
					    return '<a title="在线查看" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="seeFile('+r.get('fileid')+',\''+r.get('extendname')+'\',\''+r.get('webPath')+'\')" >查看</a>';
					
					
				}
			}, gdCheckColumn,
//				{
//				header : '是否归档',
//				dataIndex : 'isArchiveConfirm',
//				renderer : this.isArchiveConfirm_report,
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
//			}, 
				{
				header : '备注',
				dataIndex : 'archiveConfirmRemark',
				hidden:this.isgdHidden,
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
								e.record.commit();
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
	isArchiveConfirm_report : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
	},
	transition : function(v){
		var size = "";
		if(Math.floor(v/1024)>1024){
			size = Math.floor((v/1024/1024))+'M';
		}else if(Math.floor(v)<1024){
			size = Math.floor(v)+'B';
		}else if(Math.floor(v/1024)<1024){
			size = Math.floor(v/1024)+'K'
		}else{
			size = '未知';
		}
		return size;
		//return ((Math.floor(v/1024))>1024)?Math.floor((v/1024/1024))+'M':Math.floor(v/1024)+'K';
	},
	savereload:function(){
		this.glReport_gridPanel.getStore().reload();
	}
});

downLoadFile_report = function(v){
	window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+v);
		//uploadfile('下载附件',v,0,null,null,null);
};