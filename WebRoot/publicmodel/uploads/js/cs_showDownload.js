/**jiang -------下载------*/
var win_showDownload = function(mark){
	
	var isShow = true;//是否显示下载窗口
	
	/**###########################          数据源                  ###########################*/
	var jStore_showDownload = new Ext.data.JsonStore( {
		url : 'getFileListExt.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
			name : 'fileid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'mark'
		}],
		baseParams : {
			mark:mark
		}
	});
	
	/**#########################         加载数据       ##################################*/
	jStore_showDownload.load();
	
	/*刷新面板*/
//	refrashPanel_down = function(){
//		jStore_showDownload.load({
//			params : {
//				start : 0,
//				limit : 15
//			}
//		});
//	}
	
	var download = function(v){
//		return '<a href="getMsgDownload.do?downId='+v+'">'+'下载'+'</a>';
		return '<img src="'+basepath()+'images/download.gif" height="15" width="70" onclick="download('+v+');" />';
	};
	
	//文件大小
	var fileSize = function(v){
		
		var result = parseFloat(v)/1024;
		
		return fomatFloat(result,2)+'KB';
	};
	
	//保留两位小数
	function fomatFloat(src,pos){   
         return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);   
    };
    
    //显示上传文件类型	
    var showContentType = function(v){
    	
    	if(v == '.txt'){
    		return 'txt文档';
    	}else if(v == '.rm' || v == '.avi' || v=='.rmvb' || v=='.flv' || v=='.mpg'){
    		return '播放文件';
    	}else if(v == '.gif' || v=='.png' || v=='.jpg'){
    		return '图片文件';
    	}else if(v == '.log'){
    		return 'log文档';
    	}else if(v == '.ppt'){
    		return 'ppt文档';
    	}else{
    		return '未知文件';
    	}
    	
    }
	
	/**#################            列表显示面板           ####################*/
	var cModel_download = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 35
					}),
					{
						id : 'autoExpandColumn',
						header : '文件名',
						width : 180,
						sortable : true,
						dataIndex : 'filename'
					},{
						header : "文件类型",
						width : 100,
						sortable : true,
						dataIndex : 'extendname',
						renderer : showContentType
						
					},{
						header : "文件大小",
						width : 100,
						sortable : true,
						dataIndex : 'filesize',
						renderer : fileSize
					},{
						header : "下载",
						width : 100,
						sortable : true,
						dataIndex : 'fileid',
						renderer : download
					}]);

//	var pagingBar = new Ext.PagingToolbar( {
//		pageSize : 15,
//		store : jStore_showDownload,
//		autoWidth : true,
//		hideBorders : true,
//		displayInfo : true,
//		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
//		emptyMsg : "没有符合条件的记录······"
//	});
			//jStore_showDownload.setDefaultSort('createDate', 'ASC');
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	
	
	/** ##############################         主表格模板                               ######################*/
	
	var gPanel_download = new Ext.grid.GridPanel( {
		trackMouseOver : true,
		id : 'gPanel_download',//id
		store : jStore_showDownload,
//		width : 500,
//		height : 270,
		colModel : cModel_download,
		autoExpandColumn : 'autoExpandColumn',
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
//		bbar : pagingBar,//配置一组工具栏对像(底端显示 bottomBar)
//		tbar : [button_add,button_delete],//显示工具栏（顶端显示 topBar）
		
		listeners : {
			'rowdblclick' : function( grid, rowIndex,  e ){//双击事件
				var selected = grid.getSelectionModel().getSelected();//得到选中的行
				if (null == selected) {
					Ext.MessageBox.alert('状态', '请选择一条记录!');//
				}else{
					var id = selected.get('id');//数据 id（此为数据库 主键）
					
				}
			}
		}
	});
	
	var window_showDL = new Ext.Window({
			id : 'window_showDL',
			title: '下载列表',
			layout : 'fit',
//			width : 600,
			width : (screen.width-180)*0.7,
			height :300,
			closable : true,
			collapsible : true,
			resizable : false,
			plain : true,
			border : false,
			modal : true,
			buttonAlign: 'center',
			bodyStyle : 'padding: 0',
			items : [gPanel_download]
//			buttons : [ {
//				text : '关闭',
//				iconCls : 'closeIcon',
//				handler : function(){
//					window_showDL.destroy();
//				}
//			}]
	});
	
	
/*	很多人都会遇到这个问题，在使用Jsonstore的load()方法时不能马上使用getCount()取得新的记录数：

	store.load(); 
	alert(store.getCount());
    	
          以上代码只能取上一次的store中的数据记录数，想要取当前load过来的数据集，必须使用如下事件监听方法：因为store.load()方法内容是一个Ajax请求，它是异步的哦。

	store.on('load',function(tempstore){alert(tempstore.getCount())}); 
*/
	jStore_showDownload.on('load',function(tempstore){
//			alert(tempstore.getCount())
		if(tempstore.getCount() == 0){
			isShow = false;
			Ext.Msg.alert('提示','您没有文件可供下载，或者该文件已被删除！');
		}
	});
	
	if(isShow){
		window_showDL.show();
	}
	

};

function delMsgDolo(v){
	Ext.Ajax.request({
		url : 'delMsgDownload.do?downId='+v+'&del_msgId='+global_msgId,
		method : 'POST',
		success : function() {
			Ext.Msg.alert('状态', '删除成功!');
			refrashPanel_down();
			refrashPanel_down = '';
		},
		failure : function(result, action) {
			Ext.Msg.alert('状态','删除失败!');
		}
	});
};

function download(v){
	window.location = "DownLoadFile.do?fileid="+v;
};
