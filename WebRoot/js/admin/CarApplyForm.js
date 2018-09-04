/**
 * @author
 * @createtime
 * @class CarApplyForm
 * @extends Ext.Window
 * @description CarApplyForm表单
 * @company 智维软件
 */
CarApplyForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CarApplyForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'CarApplyFormWin',
			title : '车辆申请详细信息',
			iconCls : 'menu-car_apply',
			width : 600,
			height : 480,
			minWidth : 599,
			minHeight : 479,
			items : this.formPanel,
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.submitRecord,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/admin/saveCarApply.do',
				layout : 'form',
				id : 'CarApplyForm',
				frame : false,
				border : true,
				defaults : {
					anchor : '98%,98%'
				},
				bodyStyle : 'padding-top:5px;padding-left:5px;',
				formId : 'CarApplyFormId',
				defaultType : 'textfield',
				items : [{
					name : 'carApply.applyId',
					id : 'applyId',
					xtype : 'hidden',
					value : this.applyId == null ? '' : this.applyId
				},{
				    xtype:'hidden',
				    id:'carId',
				    name:'carApply.carId'
				},{
				    xtype:'hidden',
				    id:'userId',
				    name:'carApply.userId'
				},{
					xtype:'container',
					fieldLabel : '车牌号码',
					layout : 'column',
					style : 'padding-left:0px;margin-bottom:4px;',
					items : [{
						columnWidth : .999,
						xtype : 'textfield',
						name : 'carNo',
						id : 'carNo',
						allowBlank : false,
						editable : false,
						readOnly : true
					},{
					    xtype:'button',
					    iconCls:'btn-car',
					    text:'选择车辆',
					    handler:function(){
					        CarSelector.getView(
					            function(id,name){
					                 Ext.getCmp('carNo').setValue(name);
					            	 Ext.getCmp('carId').setValue(id);
					            },true,1    //1表示可用的车
					        ).show();
					    }
					},{
					    xtype : 'button',
					    text : '清除记录',
					    iconCls : 'reset',
					    handler : function(){
					       Ext.getCmp('carNo').setValue('');
					       Ext.getCmp('carId').setValue('');
					    }
					}]},{ 
						xtype : 'container',
						fieldLabel : '用车部门',
						style : 'padding-left:0px;margin-bottom:4px;',
						id : 'depContainer',
						layout : 'column',
						items : [{
						columnWidth : .999,
						xtype : 'textfield',
						name : 'carApply.department',
						id : 'department',
						allowBlank : false,
						editable : false,
						readOnly : true
					},{
					    xtype:'button',
					    iconCls:'btn-dep-sel',
					    text:'选择部门',
					    handler:function(){
					       DepSelector.getView(
					             function(id,name){
					                Ext.getCmp('department').setValue(name);
					             }
					       ).show();
					    }
					},{
					   xtype : 'button',
					   text : '清除记录',
					   iconCls : 'reset',
					   handler : function(){
					      Ext.getCmp('department').setValue('');
					   }
					}]}, {
					xtype : 'container',
					fieldLabel : '用车人',
					style : 'padding-left:0px;margin-bottom:4px;',
					layout : 'column',
					items:[{
						columnWidth : .999,
						xtype:'textfield',
						name : 'carApply.userFullname',
						id : 'userFullname',
						allowBlank : false,
						editable : false,
						readOnly : true
					},{
					    xtype : 'button',
					    iconCls : 'btn-user-sel',
					    text:'选择人员',
					    handler : function(){
					       UserSelector.getView(
					          function(id,name){
					             Ext.getCmp('userFullname').setValue(name);
					          }
					       ).show();
					    }
					},{
					    xtype : 'button',
					    text : '清除记录',
					    iconCls : 'reset',
					    handler : function(){
					       Ext.getCmp('userFullname').setValue('');
					    }
					}]}, {
					xtype:'container',
					fieldLabel : '申请人',
					layout:'column',
					style:'padding-left:0px;margin-bottom:4px;',
					items:[{
						columnWidth : .999,
						xtype : 'textfield',
						name : 'carApply.proposer',
						id : 'proposer',
						editable : false,
						allowBlank : false,
						readOnly : true
					},{
					    xtype:'button',
					    iconCls:'btn-user-sel',
					    text:'选择人员',
					    handler:function(){
					       UserSelector.getView(
					          function(id,name){
					             Ext.getCmp('proposer').setValue(name);
					             Ext.getCmp('userId').setValue(id);
					          },true
					       ).show();
					    }
					},{
					    xtype : 'button',
					    text : '清除记录',
					    iconCls : 'reset',
					    handler : function(){
					       Ext.getCmp('proposer').setValue('');
					    }
					}]}, {
						fieldLabel : '申请时间',
						name : 'carApply.applyDate',
						id : 'applyDate',
						xtype : 'datefield',
						format : 'Y-m-d',
						allowBlank : false,
						editable : false
					}, {
						fieldLabel : '原因',
						name : 'carApply.reason',
						id : 'reason',
						allowBlank:false,
						xtype:'textarea'
					}, {
						fieldLabel : '审批状态',
						name : 'carApply.approvalStatus',
						id : 'approvalStatus',
						value:0,
						xtype:'hidden'
					}, {
						fieldLabel : '开始时间',
						name : 'carApply.startTime',
						id : 'startTime',
						xtype:'datetimefield',
						format:'Y-m-d H:i:s',
						allowBlank:false,
						editable : false
					}, {
						fieldLabel : '结束时间',
						name : 'carApply.endTime',
						id : 'endTime',
						xtype:'datetimefield',
						format:'Y-m-d H:i:s'
					}, {
						fieldLabel : '里程',
						name : 'carApply.mileage',
						id : 'mileage',
						xtype:'numberfield'
					}, {
						fieldLabel : '油耗',
						name : 'carApply.oilUse',
						id : 'oilUse',
						xtype:'numberfield'
					}, {
						fieldLabel : '备注',
						name : 'carApply.notes',
						id : 'notes',
						xtype:'textarea'
					}
				]
			});// end of the formPanel

	if (this.applyId != null && this.applyId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getCarApply.do?applyId='
					+ this.applyId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				Ext.getCmp('carNo').setValue(action.result.data.car.carNo);
				Ext.getCmp('carId').setValue(action.result.data.car.carId);
				Ext.getCmp('applyDate').setValue(new Date(getDateFromFormat(action.result.data.applyDate, "yyyy-MM-dd HH:mm:ss")));
				Ext.getCmp('startTime').setValue(new Date(getDateFromFormat(action.result.data.startTime, "yyyy-MM-dd HH:mm:ss")));
				var endTime=action.result.data.endTime;
				if(endTime!=null&&endTime!=''){
				   Ext.getCmp('endTime').setValue(new Date(getDateFromFormat(endTime, "yyyy-MM-dd HH:mm:ss")));
				}
			},
			failure : function(form, action) {
				 Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	};//end of the load formPanel
		this.buttons = [{
			text : '保存草稿',
			iconCls:'btn-save',
			handler :this.saveRecord,
			scope:this
		},{
			text : '提交审批',
			iconCls:'btn-ok',
			handler :this.submitRecord,
			scope:this
		}, {
			text : '取消',
			iconCls:'btn-cancel',
			scope:this,
			handler : function() {
				this.close();
			}
		}];//end of the buttons 
	},//end of the initUIComponents
	saveRecord : function(){
	    var fp = this.formPanel;
	    var win=this;
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('CarApplyGrid').getStore().reload();
					win.close();
				},
				failure : function(fp, action) {
					var res = Ext.util.JSON.decode(action.response.responseText);
					var msg = '信息保存出错，请联系管理员！';
					if(res.msg != null && res.msg != ''){
						msg = res.msg;
					}
					Ext.MessageBox.show({
						title : '操作信息',
						msg : msg,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					win.close();
				}
			});
		}
		
	},
	submitRecord : function(){
	   Ext.getCmp('approvalStatus').setValue(1);
	   this.saveRecord();
	}
});
