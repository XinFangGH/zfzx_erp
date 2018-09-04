/**
 * @author
 * @createtime
 * @class FormTableForm
 * @extends Ext.Window
 * @description FormTable表单
 * @company 智维软件
 */
FlowFormEntityView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		FlowFormEntityView.superclass.constructor.call(this, {
			id : 'FlowFormEntityView',
			layout : 'form',
			items : [this.detailPanel,this.procPanel],
			modal : true,
			height : 600,
			width : 800,
			autoScroll:true,
			maximizable : true,
			buttonAlign : 'center',
			defaults:{
				anchor:'98%,98%'
			},
			buttons : [ {
				text : '关闭',
				iconCls : 'close',
				scope : this,
				handler : this.cancel
			} ]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {// 显示流程审批的表单
		this.detailPanel = new Ext.Panel( {
			title : '审批信息',
			autoHeight : true,
			width : '97%',
			autoLoad : {
				url : __ctxPath + '/flow/processRunDetail.do?runId='
						+ this.runId,
				nocache : true
			}
		});
		this.procPanel=	new Ext.Panel({
			title : '流程示意图',
			autoHeight : true,
			width : '97%',
			collapsible:false,
			split : true,
		
			html:'<img width : \'97%\' src="'+__ctxPath+ '/jbpmImage?runId='+this.runId+ '&rand='+Math.random()+'"/>'
		});
		
		
 		
	},
	

	cancel : function() {
		this.close();
	}

});