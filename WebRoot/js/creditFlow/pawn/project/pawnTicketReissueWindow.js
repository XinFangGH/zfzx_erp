
PawnTicketReissueWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PawnTicketReissueWindow.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 700,
					autoScroll : true,
					maximizable : true,
					title : '当票补发',
					buttonAlign : 'center',
					buttons : [{
								text : '提交',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isAllReadOnly,
								handler : this.save
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : function(){
									this.close()
								}
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.pawnTicketLossForm=new PawnTicketLossForm({projectId:this.projectId,businessType:this.businessType,isAllReadOnly:true,lossRecordId:this.lossRecordId});
		this.pawnTicketReissueForm=new PawnTicketReissueForm({projectId:this.projectId,businessType:this.businessType,isAllReadOnly:this.isAllReadOnly,reissueRecordId:this.reissueRecordId});
		this.uploads=new uploads({
			projectId : this.projectId,
	    	businessType : this.businessType,
	    	tableName : 'pawnTicketLossFile.'+this.lossRecordId,
	    	typeisfile: 'pawnTicketLossFile.'+this.lossRecordId+"."+this.projectId,
	    	titleName : '补办材料上传',
	    	isHidden:true
		})
		this.formPanel = new Ext.FormPanel({
			border : false,
			monitorValid : true,
			frame : true,
			autoScroll : true,
			items : [{
				xtype : 'panel',
				layout : 'anchor',
				anchor : '98%',
				items : [this.pawnTicketReissueForm]
			},{
				xtype : 'panel',
				layout : 'anchor',
				anchor : '98%',
				html : '【挂失情况】'
			},{
				xtype : 'panel',
				layout : 'anchor',
				anchor : '98%',
				items : [this.pawnTicketLossForm]
			},{
				xtype : 'panel',
				layout : 'anchor',
				anchor : '98%',
				html : '【补办材料】'
			},{
				xtype : 'panel',
				layout : 'anchor',
				anchor : '98%',
				items : [this.uploads]
			}]
			
		});
		// 加载表单对应的数据
	

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	
	/**
	 * 保存记录
	 */
	save : function() {
		this.formPanel.getForm().submit({
		    clientValidation: false, 
			url : __ctxPath + '/creditFlow/pawn/project/savePawnTicketReissueRecord.do',
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			scope: this,
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				if(typeof(this.projectPanel)!="undefined" && this.projectPanel!=null){
					this.projectPanel.getStore().reload()
				}
				this.close()
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
			}
		});
	}// end of save

});
