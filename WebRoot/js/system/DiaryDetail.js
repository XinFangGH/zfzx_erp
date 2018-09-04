var DiaryDetail = function(id){
	var win = new Ext.Window({
	title : '工作日志详情',
	iconCls:'menu-diary',
	autoHeight :true,
	//autoWidth :true,
	modal:true,
	width : 500,
	border:false,
	buttonAlign : 'center',
	autoLoad : {url:__ctxPath+'/system/checkDiary.do?diaryId='+id},
	buttons: [{
		text : '关闭',
		iconCls:'close',
		handler : function(){
			win.close();
		}
	}]
   });
   win.show();
}