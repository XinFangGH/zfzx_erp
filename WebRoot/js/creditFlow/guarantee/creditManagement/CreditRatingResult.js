var start = 0 ;
var pageSize = 15 ;
var bodyWidth = Ext.getBody().getWidth();
var bodyHeight = Ext.getBody().getHeight();
var innerPanelWidth = bodyWidth-6 ;/** 暂时未用到，调整窗体大小使用*/
var defaultLabelWidth = 120 ;//默认标签的宽度
var defaultTextfieldWidth = 150 ;//默认文本输入域宽度
var fieldWidth = 150 ;
var root = 'topics' ;
var totalProperty = 'totalProperty' ;



Ext.onReady(function() {
//	Ext.BLANK_IMAGE_URL = '/creditBusiness1.0/ext/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var windowGroup = new Ext.WindowGroup();
	
	var widthFun = function(bodyWidth){
		return ((bodyWidth-6)<500) ? 500 : (bodyWidth-6);
	}
	var heightFun = function(bodyHeight){
		return ((bodyHeight<400) ? 400 : (bodyHeight));	
	}
	
	
	var vPort_enterprise = new Ext.Viewport({
		id : 'tabs',
        renderTo: document.body,
        activeTab: 0,
        frame : true,
        deferredRender : false,
        layout : 'form',
        width:Ext.getBody().getViewSize(),
		items : [{
	        id : 'tab1',
	        title : '资信评估结果',
			border : false,
			layout : 'fit',
			height : 110,
			modal : true,
			frame : true,
			contentEl : 'firstPanel'
		},{
    		id : 'tab2',
			title : '指标',
			border : false,
			modal : true,
			frame : true,
			height : Ext.getBody().getViewSize().height-125,
			autoScroll : true,
			contentEl : 'firstPanel1'
		}]
	});
	
	var widthFun = function(bodyWidth){
		return ((bodyWidth-6)<800) ? 800 : (bodyWidth-6);
	}
	
});
