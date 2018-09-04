/**
 * @author
 * @createtime
 * @class CarApplyForm
 * @extends Ext.Window
 * @description CarApplyForm表单
 * @company 智维软件
 */
CarCheckForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CarCheckForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'CarCheckFormWin',
					title : '车辆申请详细信息',
					iconCls:'menu-car_apply',
					width : 600,
					height : 480,
					minWidth : 599,
					minHeight : 479,
					items:this.formPanel,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/admin/saveCarApply.do',
				layout : 'form',
				id : 'CarCheckForm',
				frame : false,
				border:true,
				defaults : {
					widht : 400,
					anchor : '98%,98%'
				},
				bodyStyle:'padding-top:5px;padding-left:5px;',
				formId : 'CarApplyFormId',
				defaultType : 'displayfield',
				items : [{
							name : 'carApply.applyId',
							xtype : 'hidden',
							value : this.applyId == null ? '' : this.applyId
						},{
						    xtype:'hidden',
						    name:'carApply.car.carId'
						},{
						    xtype:'hidden',
						    name:'carApply.userId'
						},{
							fieldLabel : '车牌号码',
							name : 'carApply.car.carNo'
						},{
							fieldLabel : '用车部门',
							name : 'carApply.department'
						},{
							fieldLabel : '用车人',
							name : 'carApply.userFullname'
						},{
							fieldLabel : '申请人',
							name : 'carApply.proposer'
						}, {
							fieldLabel : '申请时间',
							name : 'carApply.applyDate'
						}, {
							fieldLabel : '原因',
							name : 'carApply.reason'
						}, {
							fieldLabel : '审批状态',
							name : 'carApply.approvalStatus',
							xtype:'hidden'
						}, {
							fieldLabel : '开始时间',
							name : 'carApply.startTime'
						}, {
							fieldLabel : '结束时间',
							name : 'carApply.endTime'
						}, {
							fieldLabel : '里程',
							name : 'carApply.mileage'
						}, {
							fieldLabel : '油耗',
							name : 'carApply.oilUse'
						}, {
							fieldLabel : '备注',
							name : 'carApply.notes'
						}

				]
			});// end of the formPanel

	if (this.applyId != null && this.applyId != 'undefined') {
		this.formPanel.loadData({
					deferredRender : false,
					url : __ctxPath + '/admin/getCarApply.do?applyId='
							+ this.applyId,
					waitMsg : '正在载入数据...',
					preName:'carApply',
					root:'data',
					success : function(form, action) {
					},
					failure : function(form, action) {
						 Ext.ux.Toast.msg('编辑', '载入失败');
					}
				});
	};//end of the load formPanel
		if(this.isView){
		    this.buttons=[{
		       text:'关闭',
		       iconCls:'close',
		       scope:this,
		       handler:function(){this.close();}
		    
		    }];
			
		}else{
	       this.buttons = [{
						text : '通过审批',
						iconCls:'btn-save',
						scope:this,
						handler : this.passCheck
					},{
						text : '不通过审批',
						iconCls:'btn-save',
						scope:this,
						handler : this.notpassCheck
					}, {
						text : '取消',
						iconCls:'btn-cancel',
						scope:this,
						handler : function() {
							this.close();
						}
					}];//end of the buttons 
		}
	},//end of the initUIComponents
	saveRecord:function(){
	    var fp = this.formPanel;
		var win=this;
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('CarCheckGrid').getStore()
							.reload();
					win.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
					win.close();
				}
			});
		}
	},
	passCheck:function(){
	   this.formPanel.getCmpByName('carApply.approvalStatus').setValue(2);
	   this.saveRecord();
	},
	notpassCheck:function(){
	   this.formPanel.getCmpByName('carApply.approvalStatus').setValue(3);
	   this.saveRecord();
	}
});
