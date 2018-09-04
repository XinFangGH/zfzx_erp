var tradeCategoryObj;
var tradeCategoryObjHiddenName;
var tradeCategoryWindow;
var selectTradeCategory = function(Obj,hiddenNameObj) {
	tradeCategoryObj = Obj;
	tradeCategoryObjHiddenName = hiddenNameObj;
	Ext.onReady(function() {
		tradeCategoryWindow = new Ext.Window({
			layout : 'fit',
			width: 792,
			height : 545,
			autoScroll:true,
			modal : true,
			title:'请选择行业类别',
			autoLoad : {
				url : __ctxPath + '/creditFlow/multiLevelDic/getHtmlLocationAreaDic.do'
			}
		}).show();
	})
}
function setLocation(id,text) {
	tradeCategoryObj.setValue(text);
	tradeCategoryObjHiddenName.setValue(id);
	tradeCategoryWindow.destroy();
}