/**
 * @author:
 * @class ArchFlowConfView
 * @extends Ext.Panel
 * @description [ArchFlowConf]管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
ArchFlowConfView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	formPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ArchFlowConfView.superclass.constructor.call(this, {
					id : 'ArchFlowConfView',
					title : '公文流程配置',
					iconCls : 'menu-archive-setting',
					region : 'center',
					layout : 'form',
//					tbar : this.toolbar,
					items : [this.formPanel
//					, this.gridPanel
					]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化设置表单
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			url : __ctxPath + '/archive/saveArchFlowConf.do',
			id : 'ArchFlowConfForm',
			defaults : {
				anchor : '98%,98%'
			},
			defaultType : 'textfield',

			items : [{
				xtype : 'fieldset',
				title : '公文流程配置',
				items : [{
					xtype : 'container',
					layout : 'column',
					items : [{
								xtype : 'label',
								text : '发文流程:',
								width : 101
							}, {
								xtype : 'textfield',
								name : 'sendProcessName',
								id : 'sendProcessName',
								width : 200
							}, {
								xtype : 'hidden',
								name : 'sendProcessId',
								id : 'sendProcessId'
							}, {
								xtype : 'button',
								iconCls:'menu-flow',
								text : '选择流程',
								handler : this.settingFlow.createCallback(this,'send')
							}]

				}, {
					xtype : 'container',
					style : 'padding-top:3px;',
					layout : 'column',
					items : [{
								xtype : 'label',
								text : '收文流程:',
								width : 101
							}, {
								xtype : 'textfield',
								name : 'recProcessName',
								id : 'recProcessName',
								width : 200
							}, {
								xtype : 'hidden',
								name : 'recProcessId',
								id : 'recProcessId'
							}, {
								xtype : 'button',
								text : '选择流程',
								iconCls:'menu-flow',
								handler : this.settingFlow.createCallback(this,'rec')
							}]

				}]
			}]
		});
		Ext.Ajax.request({
		   url:__ctxPath+'/archive/getArchFlowConf.do',
		   success:function(response,options){
		      var obj=Ext.util.JSON.decode(response.responseText).data;
		      Ext.getCmp('sendProcessId').setValue(obj.sendProcessId);
		      Ext.getCmp('sendProcessName').setValue(obj.sendProcessName);
		      Ext.getCmp('recProcessId').setValue(obj.recProcessId);
		      Ext.getCmp('recProcessName').setValue(obj.recProcessName);
		   }
		});
	},// end of the initComponents()

	settingFlow : function(fPanel,type) {
		FlowSelector.getView(function(id, name) {
			if(type =='send'){
				var recId = fPanel.getCmpByName('recProcessId').getValue();
				if(recId != null && recId == id ){
					Ext.ux.Toast.msg('操作信息', '发文和收文流程不能指定同一个！');
					fPanel.getCmpByName('sendProcessId').setValue('');
					fPanel.getCmpByName('sendProcessName').setValue('');
					return;
				}
				fPanel.getCmpByName('sendProcessId').setValue(id);
				fPanel.getCmpByName('sendProcessName').setValue(name);
			}else{
				var sendId = fPanel.getCmpByName('sendProcessId').getValue();
				if(sendId != null && sendId == id){
					Ext.ux.Toast.msg('操作提示', '发文和公文流程不能指定同一个！');
					fPanel.getCmpByName('recProcessId').setValue('');
					fPanel.getCmpByName('recProcessName').setValue('');
					return;
				}
				fPanel.getCmpByName('recProcessId').setValue(id);
				fPanel.getCmpByName('recProcessName').setValue(name);
			}
			
			Ext.Ajax.request({
			   url:__ctxPath+'/archive/settingArchFlowConf.do',
			   method : 'POST',
			   params : {
			   		defId : id,
			   		settingType : type
			   },
			   success:function(response,options){
			   		Ext.ux.Toast.msg('操作信息','公文设置成功.');
			   }
			});
		}, true).show();
	}
});
