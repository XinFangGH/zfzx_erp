/**
 * @author lyy
 * @createtime
 * @class JobChangeForm
 * @extends Ext.Window
 * @description JobChange表单
 * @company 智维软件
 */
CheckJobChangeWin = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 必须先初始化组件
		this.initComponents();
		CheckJobChangeWin.superclass.constructor.call(this, {
			id : 'CheckJobChangeWinPanel',
			layout : 'form',
			iconCls:'menu-job-check',
			modal : true,
			autoHeight:true,
			x:280,
			y:50,
			shadow:false,
			width : 550,
			maximizable : true,
			title : '职位调动详细信息',
			buttonAlign : 'center',
			items : [this.showPanel,this.formPanel],
			buttons : this.buttons
			});
	},// end of the constructor
	// 初始化组件
	initComponents : function() {
		this.showPanel=new Ext.Panel({
		    id:'CheckJobChangePanel',
		    autoScroll : true,
		    height:220,
		    border:false,
        	autoLoad : {url:__ctxPath+'/hrm/loadJobChange.do?changeId='+this.changeId}
		});
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:8px 8px 2px 8px',
			border : false,
			url : __ctxPath + '/hrm/checkJobChange.do',
			id : 'CheckJobChangeWin',
			autoScroll : true,
			defaults : {
				anchor : '96%,96%'
			},
			items : [{
			   xtype:'hidden',
			   id:'CheckJobChangeWin.changeId',
			   name:'jobChange.changeId',
			   value:this.changeId==null?'':this.changeId
			},{
			   xtype:'hidden',
			   id:'CheckJobChangeWin.status',
			   name:'jobChange.status'
			},{
			   xtype:'textarea',
			   fieldLabel:'审核意见',
			   name:'jobChange.checkOpinion',
			   allowBlank:false,
			   id:'CheckJobChangeWin.checkOpinion'
			}]
		});
		 // 初始化功能按钮
		 this.buttons = [{
		 text : '审核通过',
		 id:'JobChangeBtnY',
		 iconCls : 'btn-finish',
		 handler : this.pass.createCallback(this.formPanel,
		 this)
		 }, {
		 text : '审核不通过',
		 id:'JobChangeBtnN',
		 iconCls : 'btn-empProfile-notpass',
		 handler : this.notpass.createCallback(this.formPanel,this)
		 }, {
		 text : '取消',
		 iconCls : 'btn-cancel',
		 handler : this.cancel.createCallback(this)
		 }];
	},// end of the initcomponents

	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	},
	/**
	 * 通过审核
	 */
	pass : function(formPanel,window) {
		Ext.getCmp('CheckJobChangeWin.status').setValue(1);
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('JobChangeGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	},// end of pass
	/**
	 * 不通过审核
	 */
	notpass : function(formPanel,window) {
		Ext.getCmp('CheckJobChangeWin.status').setValue(2);
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('JobChangeGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	}// end of notpass
});