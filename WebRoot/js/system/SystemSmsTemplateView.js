/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
SystemSmsTemplateView = Ext.extend(Ext.Panel, {
	// 构造函数
	type : null,
	constructor : function(_cfg) {
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SystemSmsTemplateView.superclass.constructor.call(this, {
					id : 'SystemSmsTemplateView'+this.type,
					title : "ERP短信模板管理",
					region : 'center',
					layout : 'border',
					iconCls:"menu-flowManager",
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-edit',
								text : '修改模板信息',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							},{
								iconCls : 'btn-test',
								text : '测试发送',
								xtype : 'button',
								scope : this,
								handler : this.sendRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'SystemSmsTemplateViewGrid'+this.type,
			url : __ctxPath + '/messageAlert/getTemplateXmlSmsTemplate.do',
			fields : ['key' ,'content','useExplain','prohibitUse','isTest'],
			columns : [ {
						header : '模板KEY',
						width:20,
						dataIndex : 'key'
					},{
						header : '模板用途说明',
						dataIndex : 'useExplain',
						width:20
					},{
						header : '是否禁用',
						dataIndex : 'prohibitUse',
						width:10,
						renderer:function(v){
							if(v == 'yes'){
								return '是';
							}else{
								return '否';
							}
						}
					},{
						header : '是否测试',
						dataIndex : 'isTest',
						width:10,
						renderer:function(v){
							if(v == 'yes'){
								return '是';
							}else{
								return '否';
							}
						}
					},{
						header : '模板内容',
						dataIndex : 'content',
						width:40
					}]
			});


	},

	editRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var gridPanel = this.gridPanel;
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var key = selectRs[0].data.key;
			var useExplain = selectRs[0].data.useExplain;
			var content = selectRs[0].data.content;
			var prohibitUse = selectRs[0].data.prohibitUse;
			var isTest = selectRs[0].data.isTest;
		    new SystemUpdateSmsTemplateView({
		    	key:key,
		    	useExplain:useExplain,
		    	content:content,
		    	prohibitUse:prohibitUse,
		    	isTest:isTest,
		    	gridPanel:gridPanel
			}).show();
		}
	},
	sendRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var key = selectRs[0].data.key;
			var useExplain = selectRs[0].data.useExplain;
			var content = selectRs[0].data.content;
		    new SystemSendSmsTemplateView({
		    	key:key,
		    	useExplain:useExplain,
		    	content:content
			}).show();
		}
	}

});
