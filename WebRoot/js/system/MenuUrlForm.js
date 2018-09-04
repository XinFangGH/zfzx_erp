/**
 * @description 添加请求URL地址
 * @class MenuUrlForm
 * @extend Window
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-2-14AM
 */
MenuUrlForm = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponent();
		MenuUrlForm.superclass.constructor.call(this, {
			id : 'MenuUrlFormWin',
			title : '新增/编辑请求地址',
			iconCls : 'menu-url',
			modal : true,
			layout : 'fit',
			width : 300,
			height : 100,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.submit,
				scope : this
			},
			buttonAlign : 'center',
			buttons : [ {
				text : '确定',
				iconCls : 'btn-ok',
				scope : this,
				handler : this.submit
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : function(){
					Ext.getCmp('MenuUrlFormWin').close();
				}
			} ],
			items : [this.formPanel]
		});
	},
	// 创建初始化对象
	initUIComponent : function() {
		this.formPanel = new Ext.FormPanel({
			id : 'MenuUrlFormPanel',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			bodyStyle : 'padding:5px;',
			items : [ {
				xtype : 'hidden',
				name : 'id',
				value : this.id != null ? this.id : ''
			} , {
				xtype : 'hidden',
				name : 'fId',
				value : this.fId != null ? this.fId : ''
			} , {
				fieldLabel : '请求地址',
				name : 'note',
				allowBlank : true,
				maxLength : 125
			}]
		}); // end of this formPanel
	}, // initUIComponent
	
	/*
	 * 保存操作
	 */
	submit : function(){
		var form = Ext.getCmp('MenuUrlFormPanel');
		if(form.getForm().isValid()){
			form.getForm().submit({
				url : __ctxPath + '/menu/addUrlMenu.do',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				success : function(){
					Ext.ux.Toast.msg('操作提示','数据操作成功！');
					Ext.getCmp('MenuFuncionFormGrid').getStore().reload();
					Ext.getCmp('MenuUrlFormWin').close();
				},
				failure : function(fp,action){
					Ext.ux.Toast.msg('操作提示','对不起，数据操作失败！');
				}
			});
		}
	}
});