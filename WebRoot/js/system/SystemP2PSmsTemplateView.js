/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
SystemP2PSmsTemplateView = Ext.extend(Ext.Panel, {
	// 构造函数
	type : null,
	constructor : function(_cfg) {
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SystemP2PSmsTemplateView.superclass.constructor.call(this, {
					id : 'SystemP2PSmsTemplateView'+this.type,
					title : "P2P短信模板管理",
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
								text : '修改模板',
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
			id : 'SystemP2PSmsTemplateViewGrid'+this.type,
			url : __ctxPath + '/system/getSmsP2PTemplateSystemProperties.do',
			fields : ['key' ,'content','useExplain'],
			columns : [ {
						header : '模板KEY',
						width:20,
						dataIndex : 'key'
					},{
						header : '模板用途说明',
						dataIndex : 'useExplain',
						width:30
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
		    new SystemP2PUpdateSmsTemplateView({
		    	key:key,
		    	useExplain:useExplain,
		    	content:content,
		    	gridPanel : gridPanel
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
		    new SystemP2PSendSmsTemplateView({
		    	key:key,
		    	useExplain:useExplain,
		    	content:content
			}).show();
		}
	}

});
