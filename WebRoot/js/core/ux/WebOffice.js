/**
 * WebOffice Controller 
 */
WebOffice=function(id){
	this.id = id==null?'WebOfficeObj':this.id;
	this.htmlObj="<object id='"+this.id+"' height='100%' width='100%' style='LEFT: 0px; TOP: 0px'  classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase='"+__ctxPath+"/js/core/weboffice/HWPostil_V3074.cab#V6,0,4,2'>";
    this.htmlObj +="</object>";
	
    //当窗口关闭时也需要做同样的操作
	window.onUnload=function(){
		try{
				this.getDocObject().Close();
			}catch(ex){
		}
	}
};

/**
 * 
 */
WebOffice.prototype.openDoc=function(path,type){
	var obj=this.getDocObject();
	obj.LoadOriginalFile(path,'doc');
}

/**
 * 保存文档
 * @param {} config
 * @return {}
 */
WebOffice.prototype.saveDoc=function(config){
	Ext.applyIf(this,config || {});
	
	var webOffice=this.getDocObject();
	webOffice.HttpInit();
	webOffice.HttpAddPostString('file_cat', this.fileCat);
	//overwrite file path
	if(this.docPath!=null && this.docPath!=''){
		webOffice.HttpAddPostString('file_path', this.docPath);
	}
	
	webOffice.HttpAddPostCurrFile("AipFile ","");	
	// 提交上传文件
	returnValue = webOffice.HttpPost(this.url);
	var obj;
	eval('obj='+returnValue+";");
	return obj;
}

WebOffice.prototype.close=function(){
	try{
		this.getDocObject().Close();
	}catch(ex){
		
	}
}

WebOffice.prototype.getDocObject=function(){
	return document.getElementById(this.id);
}

