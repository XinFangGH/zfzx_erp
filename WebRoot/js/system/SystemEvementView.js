/**
 * @author
 * @createtime
 * @class GuofubaoForm
 * @extends Ext.Window
 * @description Guofubao表单
 * @company 智维软件
 */
SystemEvementView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SystemEvementView.superclass.constructor.call(this, {
			id:'SystemEvementView',
			layout : 'fit',
			frame:true,
			iconCls:"menu-flowManager",
			items : this.formPanel,
			title : '系统环境参数'
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			defaults : {
				anchor : '50%',
				readOnly:true
			},
			defaultType : 'textfield',
			items : [{
				fieldLabel : '计算机名',
				name : 'hostName'
			}, {
				fieldLabel : '计算机的IP',
				name : 'hostIp'
			}, {
				fieldLabel : '操作系统的名称',
				name : 'OSName'
			}, {
				fieldLabel : '操作系统的版本',
				name : 'OSVersion'
			}, {
				fieldLabel : '系统当前登录用户',
				name : 'UserName'
			}, {
				fieldLabel : 'JDK版本号',
				name : 'JavaVersion'
			}, {
				fieldLabel : 'Tomcat版本号',
				name : 'Tomcat'
			}, {
				fieldLabel : '数据库',
				name : 'SQL'
			}]
		});
		// 加载表单对应的数据
		this.formPanel.loadData({
			url : __ctxPath + '/system/getPCInfoSystemProperties.do',
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var syscon = Ext.util.JSON.decode(respText);
			}
		});
	}
});