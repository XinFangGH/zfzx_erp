//列表中添加   元单位
var yuanRenderer = function(v){
	if(v&&v>0){
		return v + "元" ;
	}else if(v==null||v=='undefined'||v==0){
		return '';
	}else{
		return '<font color=red>'+v+'</font>元' ;
	}
}
//列表中添加   万元单位
var wanyuanRenderer = function(v){
	if(v&&v!=''&&v>0){
		return v + "万元" ;
	}else{
		return v ;
	}
}
//列表中添加   百分符号
var percentRenderer = function(v){
	if(v&&v>0){
		return v + "%" ;
	}else{
		return '<font color=red>'+v+'</font>%' ;
	}
}
//列表中添加   文件数量单位  份
var fenRenderer = function(v){
	if(v&&v>0){
		return v + "份" ;
	}else{
		return '<font color=red>'+v+'</font>份' ;
	}
}
//列表右键删除的时候,通用函数
var globalGridDeleteRecord = function(grid,row,event,action){
	var record = grid.getStore().getAt(row) ;
	var id = record.get('id') ;
	Ext.Msg.confirm('删除确认','确认删除选中记录?',function(btn){
		if(btn=='yes'){
			Ext.Ajax.request({
				url : action,
				method : 'POST',
				timeout : 60000,
				success : function(response, request) {
					handleAjaxRequest(response, request) ;
					grid.getStore().removeAll() ;
					grid.getStore().reload();
				},
				failure : function(response, request) {
					handleAjaxRequest(response, request) ;
					grid.getStore().removeAll() ;
					grid.getStore().reload();
				},
				params: { id: id }
			})
		}
	}) ;
}