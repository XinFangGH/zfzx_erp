/**
 * @author 
 * @createtime 
 * @class SlDownsFiles
 * @extends Ext.Window
 * @company 智维软件
 */
SlDownsFiles = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.mark) != "undefined") {
			this.mark = _cfg.mark;
		}
		if (typeof(_cfg.contractId) != "undefined") {
			this.contractId = _cfg.contractId;
		}
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		SlDownsFiles.superclass.constructor.call(this, {
			height : 200,
			width : 600,
			title : '附件下载',
			items : [this.gridPanel]
		
		});
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		this.gridPanel = new HT.GridPanel({
				id : 'SlDownsFiles',
				isShowTbar : false,
				showPaging : false,
				autoHeight : true,
				region : 'center',
				scope:this,
				url : __ctxPath+'/creditFlow/fileUploads/getHTUploadedFileForm.do?mark='+this.mark,
				fields : [{name : 'fileid',type : 'Long'},'filename','filesize','extendname','createtime'],
				columns : [{
					header : 'fileid',
					dataIndex : 'fileid',
					hidden :true,
					scope : this
				},{
					header : '文件名称(已上传)',
					width : 200,
					dataIndex : 'filename'
				}, {
					header : '大小',
					width : 47,
					dataIndex : 'filesize',
					renderer : this.transition
				},{
					header : '类型',
					width : 47,
					dataIndex : 'extendname'
				}, {
					header : '上传时间',
					width : 76,
					dataIndex : 'createtime'
				},{
					header : '下载',
					width : 50,
					dataIndex : 'fileid',
					sortable : false,
					fixed : true,
					align : 'center',
					renderer : function(v){
						return '<img src="'+basepath()+'images/download-start.png" onclick="download('+v+')" />';
					}
			}]
		// end of columns
		});				
	},//end of the initcomponents
	transition : function(v){
		return ((Math.floor(v/1024))>1024)?Math.floor((v/1024/1024))+'M':Math.floor(v/1024)+'K';
	}
});
function download(v){
    window.open(__ctxPath+"/creditFlow/fileUploads/DownLoadFileForm.do?fileid="+v,'_blank');
};
