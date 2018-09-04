/**
 * @author
 * @createtime
 * @class GoodsApplyForm
 * @extends Ext.Window
 * @description GoodsApplyForm表单
 * @company 智维软件
 */
GoodsCheckForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		GoodsCheckForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'GoodsCheckFormWin',
					title : '申请表详细信息',
					iconCls:'menu-goods-apply',
					width : 450,
					height : 250,
					minWidth:449,
					minHeight:249,
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
		//初始化form表单
		this.formPanel =  new Ext.FormPanel({
				url : __ctxPath + '/admin/saveGoodsApply.do',
				layout : 'form',
				id : 'GoodsCheckForm',
				frame : false,
				bodyStyle : 'padding : 5px;',
				defaults : {
					widht : 450,
					anchor : '96%,96%'
				},
				formId : 'GoodsApplyFormId',
				defaultType : 'displayfield',
				items : [{
							name : 'goodsApply.applyId',
							xtype : 'hidden',
							value : this.applyId == null ? '' : this.applyId
						},{
						   name : 'goodsApply.officeGoods.goodsId',
						   xtype : 'hidden'
						},{
							name : 'goodsApply.userId',
							xtype : 'hidden'
						},{
							fieldLabel : '申请号',
							name : 'goodsApply.applyNo'
						},{
							fieldLabel : '商品名称',
							name : 'goodsApply.officeGoods.goodsName'
						}, {
							fieldLabel : '申请日期',
							name : 'goodsApply.applyDate'
						}, {
							fieldLabel : '申请数量',
							name : 'goodsApply.useCounts'
						},{
							fieldLabel : '申请人',
							name : 'goodsApply.proposer'
						}, {
							fieldLabel : '审批状态 ',
							name : 'goodsApply.approvalStatus',
							xtype:'hidden'
						}, {
							fieldLabel : '备注',
							name : 'goodsApply.notes'
						}

				]
			});

	if (this.applyId != null && this.applyId != 'undefined') {
		this.formPanel.loadData({
			deferredRender : false,
			url : __ctxPath + '/admin/getGoodsApply.do?applyId=' + this.applyId,
			method:'post',
			waitMsg : '正在载入数据...',
			preName:'goodsApply',
			root:'data',
			success : function(response,options) {
			},
			failure : function(response,options) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
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
					Ext.getCmp('GoodsCheckGrid').getStore()
							.reload();
					win.close();
				},
				failure : function(fp, action) {
					var message=action.result.message;
					Ext.MessageBox.show({
								title : '操作信息',
								msg : message==null&&message==''?'信息保存出错，请联系管理员！':message,
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
					win.close();
				}
			});
		}
		
	},
	passCheck:function(){
		this.getCmpByName('goodsApply.approvalStatus').setValue(2);
	    this.saveRecord();
	},
	notpassCheck:function(){
	    this.getCmpByName('goodsApply.approvalStatus').setValue(3);
	    this.saveRecord();
	}
});
