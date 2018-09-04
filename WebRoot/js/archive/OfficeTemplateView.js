/**
 * @author csx
 * @description 公文模板在线显示及编辑窗口
 * @company 北京互融时代软件有限公司
 * @param {}
 *            docPath
 * @param {}
 *            readOnly
 */
var PdfTemplateView = function (fileId,docPath,readOnly, callback)
{
	
	if (window.navigator.userAgent.toLowerCase().indexOf("msie")==-1){
	    Ext.ux.Toast.msg('提示','该浏览器暂不支持在线浏览PDF文件，请使用IE浏览器!');
	    return false;
	}

    this.docPath = docPath;
    var filePath=basepath()+docPath;
    var html_='<html>'+
    '<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />'+
    '<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="500" border="0" scrolling=true>'+  
	'<param name="_Version" value="65539">'+  
	'<param name="_ExtentX" value="20108">'+  
	'<param name="_ExtentY" value="10866">'+ 
	'<param name="_StockProps" value="0">'+
	'<param name="encoding" value="UTF-8">'+
	'<param name="SRC" value="'+encodeURI(filePath)+'">'+  
	'</object>'+		
    '</html>';
    var panel_ = new  Ext.Panel({
		frame : true,
/*		autoScroll : true,
		width:850,
		height:500,*/
		autoWidth : true,
		autoHeight : true,
		html : html_
	});
    var win = new Ext.Window( {
		title : '文件详细信息',//公文模板详细信息
		iconCls : 'menu-archive-template',
		height : 600,
		width : 1000,
		maximizable : true,
		modal : true,
		items : [panel_],
		buttonAlign : 'center',
		buttons : [{
			iconCls : 'close',
			text : '关闭',
			handler : function() {
				win.close();
			}
		} ]
	});
	win.show();
	return false;

}
var OfficeTemplateView = function(fileId,docPath, readOnly, callback) {
	this.docPath = docPath;
	this.readOnly = readOnly == null ? false : readOnly;

	this.docPanel= new NtkOfficePanel({
		showToolbar : false,
		height : 620
	});
//	var objHtml = "<object id='WebOfficeObj' name='WebOfficeObj' height='100%' width='100%' style='LEFT: 0px; TOP: 0px'  classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase='"
//			+ __ctxPath + "/js/core/weboffice/HWPostil_V3074.cab#V6,0,4,2'>";
//	objHtml += "<param name='TitlebarColor' value='7898C2'/>";
//	objHtml += "</object>";
	// 保存按钮
	var saveButton = new Ext.Button( {
		iconCls : 'btn-save',
		text : '保存',
		scope:this,
		handler : function() {
			var obj = this.docPanel.saveDoc({
						fileId : fileId,
						doctype : 'doc'
					});
			if (obj && obj.success) {
			   if (callback != null) {
				   callback.call(this, obj.fileId, obj.filePath);
			   }
			   win.close();
			}

//			var webObj = document.getElementById('WebOfficeObj');
//			var url = __fullPath + "/file-upload";
//			webObj.HttpInit();
//			webObj.HttpAddPostString('file_cat', 'archive');
//			// overwrite file path
//		webObj.HttpAddPostString('file_path', docPath);
//
//		webObj.HttpAddPostCurrFile("AipFile ", "");
//		// 提交上传文件
//		returnValue = webObj.HttpPost(url);
//
//		var obj;
//		eval('obj=' + returnValue + ";");
//		if (obj.success) {
//			Ext.ux.Toast.msg('操作信息', '已经成功保存至服务器！');
//			if (callback != null) {
//				callback.call(this, obj.fileId, obj.filePath);
//			}
//			win.close();
//		} else {
//			Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
//		}
	}// end of handler
	});

	// 文件保存在服务器端的路径
	var win = new Ext.Window( {
		title : '文件详细信息',//公文模板详细信息
		iconCls : 'menu-archive-template',
		height : 500,
		width : 700,
		maximizable : true,
		modal : true,
		items : this.docPanel.panel,
		buttonAlign : 'center',
		buttons : [ saveButton, {
			iconCls : 'close',
			text : '关闭',
			handler : function() {
				win.close();
			}
		} ]
	});

	// //是否只读
	if (this.readOnly) {
		saveButton.setVisible(false);
		saveButton.setDisabled(true);
	}
	//判断用户是否在使用IE浏览器 chencc
	if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
		win.show();
	}//else{
	//	Ext.ux.Toast.msg('提示','您浏览器不支持此功能，请使用IE浏览器进行在线预览/查看！');
	//}
	//下载在线浏览编辑插件 add by chencc
	downNtko = function(){
		window.open(__ctxPath + "/contract/downloadNtkoProduceHelper.do", '_blank');
	}
	
	if(fileId!=null&&fileId!=''){
	    this.docPanel.openDoc(fileId);
	}

//	var webObj = document.getElementById('WebOfficeObj');
//	// 隐藏保存与返回的按钮
//	// document.all.WebOfficeObj.HideMenuItem(0x04 + 0x2000);
//	webObj.ShowToolBar = false;
//	var fullDocPath = '';
//	if (this.docPath != null && this.docPath != '') {
//		fullDocPath = __fullPath + '/attachFiles/' + this.docPath;
//	}
//	webObj.LoadOriginalFile(fullDocPath, 'doc');
//
//	// 一定需要加上这个控制，保证关闭窗口后，office实例也需要关闭
//	win.on('close', function() {
//		try {
//			webObj.Close();
//		} catch (ex) {
//		}
//	});
//	// 当窗口关闭时也需要做同样的操作
//	window.onUnload = function() {
//		try {
//			webObj.Close();
//		} catch (ex) {
//		}
//	}
};