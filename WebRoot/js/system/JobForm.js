/**
 * @description Job表单
 * @extends Ext.Window
 * @class JobForm
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-23PM
 * 
 */
JobForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.apply(this, _cfg);
		// 必须先初始化组件
		this.initComponents();
		JobForm.superclass.constructor.call(this, {
			id : 'JobFormWin',
			layout : 'fit',
			iconCls : 'menu-job',
			items : this.formPanel,
			modal : true,
			height : 200,
			width : 400,
			maximizable : true,
			title : '新增/编辑职位信息',
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save.createCallback(this.formPanel, this),
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			id : 'JobForm',
			layout : 'form',
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			url : __ctxPath + '/hrm/addJob.do',
			defaults : {
				anchor : '98%,98%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'job.jobId',
				xtype : 'hidden',
				value : this.jobId == null ? '' : this.jobId
				}, {
					xtype : 'hidden',
					name : 'job.parentId',
					value : this.parentId 
				}, {
					fieldLabel : '职位名称',
					name : 'job.jobName',
					id : 'jobName',
					maxLength : 128,
					allowBlank : false,
					blankText : '职位名称不能为空!'
				}, {
					xtype : 'textarea',
					fieldLabel : '备注',
					name : 'job.memo',
					id : 'memo',
					maxLength : 512
				}
			]
		});
		// 加载表单对应的数据
		if (this.jobId != null && this.jobId != 'undefined') {
			this.formPanel.getForm().load( {
				deferredRender : false,
				url : __ctxPath + '/hrm/getJob.do?jobId=' + this.jobId,
				waitMsg : '正在载入数据...',
				success : function(form, action) {
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作提示','对不起，数据加载失败！');
				}
			});
		}
		// 初始化功能按钮
		this.buttons = [ {
			text : '保存',
			iconCls : 'btn-save',
			handler : this.save.createCallback(this.formPanel, this)
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : this.cancel.createCallback(this)
		} ];
	},// end of the initComponents
	
	/**
	 * 取消
	 */
	cancel : function(window) {
		window.close();
	},
	
	/**
	 * 保存记录
	 */
	save : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit( {
				method : 'POST',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					var jobTree = Ext.getCmp('userJobViewJobTreePanel');
					if (jobTree != null)
						jobTree.root.reload();
					window.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					window.close();
				}
			});
		}
	}// end of save

});