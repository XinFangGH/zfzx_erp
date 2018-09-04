var DiaryForm = function(diaryId) {
	this.diaryId = diaryId;
	var fp = this.setup();
	var window = new Ext.Window( {
		id : 'DiaryFormWin',
		title : '日志详细信息',
		iconCls:'menu-diary',
		maximizable : true,
		width : 650,
		height : 450,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [ this.setup() ],
		buttons : [ {
			text : '保存',
			iconCls:'btn-save',
			handler : function() {
				var fp = Ext.getCmp('DiaryForm');
				
				if (fp.getForm().isValid()) {
				
			fp.getForm().submit( {
				method : 'post',

				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功信息保存！');
					Ext.getCmp('DiaryGrid').getStore().reload();
					window.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					window.close();
				}
			});
		}
	}
		}, {
			text : '取消',
			iconCls:'btn-cancel',
			handler : function() {
				window.close();
			}
		} ]
	});
	window.show();
};

DiaryForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel( {
		url : __ctxPath + '/system/saveDiary.do',
		layout : 'form',
		id : 'DiaryForm',
		frame : false,
		border:false,
		bodyStyle : 'padding:5px;',
		defaults : {
			widht : 400,
			anchor : '96%,96%'
		},
		formId : 'DiaryFormId',
		defaultType : 'textfield',
		items : [ {
			name : 'diary.diaryId',
			id : 'diaryId',
			xtype : 'hidden',
			value : this.diaryId == null ? '' : this.diaryId
		}, {
			xtype:'hidden',
			name : 'diary.appUser.userId',
			id : 'userId'
		}, {
			fieldLabel : '日期',
			xtype : 'datefield',
			name : 'diary.dayTime',
			id : 'dayTime',
			editable:false,
			format : 'Y-m-d'				
		}, {
			fieldLabel : '日志类型',
			xtype : 'combo',
			hiddenName : 'diary.diaryType',
			id : 'diaryType',
			mode : 'local',
			editable : false,
			value:'0',
			triggerAction : 'all',
			store :[['0','个人日志'],['1','工作日志']]
		}, {
			fieldLabel : '内容',
			xtype : 'htmleditor',
			name : 'diary.content',
			id : 'content'
		} ]
	});

	if (this.diaryId != null && this.diaryId != 'undefined') {
		formPanel.getForm().load( {
			deferredRender : false,
			url : __ctxPath + '/system/getDiary.do?diaryId=' + this.diaryId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
			    var result = action.result.data;
				
				var dayTime = getDateFromFormat(result.dayTime,'yyyy-MM-dd');
				Ext.getCmp('dayTime').setValue(new Date(dayTime));
		},
		failure : function(form, action) {
			Ext.ux.Toast.msg('编辑', '载入失败');
		}
		});
	}
	return formPanel;

};
