/**
 * @description UserJob表单
 * @author YHZ
 * @class UserJobForm
 * @extends Ext.Window
 * @company 智维软件
 * @createtime 2011-1-12AM
 */
UserJobForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		UserJobForm.superclass.constructor.call(this, {
			id : 'UserJobFormWin',
			layout : 'fit',
			iconCls : 'menu-job',
			items : this.formPanel,
			modal : true,
			height : 180,
			minHeight : 180,
			width : 500,
			minWidth : 500,
			maximizable : true,
			title : this.jobName == null ? '新增/编辑职位人员信息' : '新增/编辑职位['+this.jobName+']人员信息',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			} ],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			id : 'UserJobForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'userJob.userJobId',
				xtype : 'hidden',
				value : this.userJobId == null ? '' : this.userJobId
			}, {
				name : 'userJob.job.jobId',
				xtype : 'hidden',
				value : this.jobId == null ? '' : this.jobId
			}, {
				name : 'userJob.appUser.userId',
				xtype : 'hidden'
			}, {
				xtype : 'container',
				layout : 'column',
				fieldLabel : '员工名称',
				style : 'margin-bottom:5px;',
				items : [{
						columnWidth : .99,
						xtype : 'textfield',
						name : 'userJob.appUser.username',
						readOnly : true,
						allowBlank : false,
						maxLength : 128
					}, {
						xtype : 'button',
						text : '请选择',
						iconCls : 'btn-user-sel',
						handler : function() {
							UserSelector.getView(
								function(userIds,usernames) {
									var fm = Ext.getCmp('UserJobForm');
									fm.getCmpByName('userJob.appUser.userId').setValue(userIds);
									fm.getCmpByName('userJob.appUser.username').setValue(usernames);
							},true).show();
						},
						disabled : this.userJobId == null ? false : true
					} 
				]
			}, {
				xtype : 'container',
				layout : 'column',
				fieldLabel : '员工职位',
				style : 'margin-bottom:5px;',
				items : [{
					columnWidth : .99,
					xtype : 'textfield',
					name : 'userJob.job.jobName',
					readOnly : true,
					allowBlank : false,
					maxLength : 128,
					value : this.jobName != null ? this.jobName : ''
				}, {
					xtype : 'button',
					text : '请选择',
					iconCls : 'btn-job-sel',
					handler : function(){
						JobSelector.getView(function(jobIds,jobNames){
							var fm = Ext.getCmp('UserJobForm');
							fm.getCmpByName('userJob.job.jobId').setValue(jobIds);
							fm.getCmpByName('userJob.job.jobName').setValue(jobNames);
						},true).show();
					}
				}]
			}, {
				fieldLabel : '是否主职位',
				hiddenName : 'userJob.isMain',
				displayField : 'name',
				valueField : 'id',
				xtype : 'combo',
				mode : 'local',
				store : [['0','否'],['1','是']],
				triggerAction : 'all',
				editable : false,
				emptyText : '--请选择是否为主职位--',
				value : 0,
				anchor : '99%'
			} ]
		});
		// 加载表单对应的数据
		if (this.userJobId != null && this.userJobId != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/system/getUserJob.do?userJobId='
						+ this.userJobId,
				root : 'data',
				preName : 'userJob'
			});
		}

	},// end of the initComponents

	/**
	 * 取消
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var fm = this.formPanel;
		if(fm.getForm().isValid()){
			fm.getForm().submit({
				url : __ctxPath + '/system/saveUserJob.do',
				method : 'post',
				waitMsg : '数据正在，提交请稍后...',
				success : function(fp,action){
					var gridPanel = Ext.getCmp('userJobUserGrid');
					if (gridPanel != null)
						gridPanel.getStore().reload();
					Ext.ux.Toast.msg('操作提示','数据提交成功！');
					Ext.getCmp('UserJobFormWin').close();
				},
				failure : function(fp,action){
					var res = Ext.util.JSON.decode(action.response.responseText);
					Ext.ux.Toast.msg('操作提示',res.msg);
				}
			});
		}
	}// end of save

});