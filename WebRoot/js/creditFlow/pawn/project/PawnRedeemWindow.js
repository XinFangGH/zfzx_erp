/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
PawnRedeemWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PawnRedeemWindow.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 600,
					width : 1100,
					autoScroll : true,
					maximizable : true,
					title : '赎当办理',
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
		this.pawnRedeemForm=new PawnRedeemForm({projectId:this.projectId,businessType:this.businessType,isAllReadOnly:this.isAllReadOnly});
		this.plFundIntentViewVM=new PlFundIntentViewVM({
			object :this.pawnRedeemForm,
			projectId : this.projectId,
			isHidden : false,
			titleText:'还款收息表',
			businessType : this.businessType,
			isHiddenAddBtn:false,
			isHiddenDelBtn:false,
			isHiddenautocreateBtn:true,
			isHiddenCanBtn : false,
			isHiddenResBtn : false,
			isHiddenResBtn1 : true,
			isHiddenseeqlideBtn : true,
			isHiddenseesumqlideBtn : true,
			isHiddenExcel : true,
			isChangeTitle:this.isAllReadOnly
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
				items : [this.pawnRedeemForm]
			},{
				xtype : 'panel',
				layout : 'anchor',
				anchor : '98%',
				items : [this.plFundIntentViewVM]
			}]
			
		});
		// 加载表单对应的数据
		if (this.projectId != null && this.projectId != 'undefined' && this.businessType!=null && this.businessType!='undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/pawn/project/getPawnInfoPawnRedeemManagement.do?projectId='
								+ this.projectId+'&businessType='+this.businessType+"&redeemId="+this.redeemId,
						root : 'data',
						preName : 'pawnRedeemManagement',
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							this.getCmpByName('phnumber').setValue(result.phnumber);
							this.getCmpByName('cardType').setValue(result.cardType);
							this.getCmpByName('cardNumber').setValue(result.cardNumber);
							this.getCmpByName('customerName').setValue(result.customerName);
							this.getCmpByName('plPawnProject.payintentPeriod').setValue(result.payintentPeriod);
							if(typeof(result.data)!="undefined" && result.data!=null){
								this.getCmpByName('pawnRedeemManagement.managerId').setRawValue(result.data.managerName);
								this.getCmpByName('pawnRedeemManagement.managerId').nextSibling().setValue(result.data.managerId)
							}
						}
					});
		}

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
		var fundIntentJsonData=this.plFundIntentViewVM.getGridData();
		var gridPanel=this.gridPanel
		var redeemGridPanel=this.redeemGridPanel;
		var projectPanel=this.projectPanel;
		this.formPanel.getForm().submit({
		    clientValidation: false, 
			url : __ctxPath + '/creditFlow/pawn/project/savePawnRedeemManagement.do',
			params : {
				'fundIntentJsonData' : fundIntentJsonData
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			scope: this,
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				if(null!=gridPanel && typeof(gridPanel)!="undefined"){
					gridPanel.save()
				}
				if(null!=redeemGridPanel && typeof(redeemGridPanel)!="undefined"){
					redeemGridPanel.getStore().reload();
				}
				if(null!=projectPanel && typeof(projectPanel)!="undefined"){
					projectPanel.getStore().reload()
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
