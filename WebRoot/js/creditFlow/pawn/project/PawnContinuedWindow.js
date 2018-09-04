/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
PawnContinuedWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PawnContinuedWindow.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 650,
					width : 1100,
					autoScroll : false,
					maximizable : true,
					title : '续当办理',
					buttonAlign : 'center',
					buttons : [{
								text : '提交',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isReadOnly,
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
		this.pawnContinuedForm=new PawnContinuedForm({projectId:this.projectId,businessType:this.businessType,continueId:this.continueId,idDefinition:this.idDefinition,isAllReadOnly:this.isAllReadOnly});
		this.pawnContinuedFundIntentView=new PawnContinuedFundIntentView({
			object :this.pawnContinuedForm,
			projId : this.projectId,
			isHidden : false,
			titleText:'还款收息表',
			businessType : this.businessType,
		    slSuperviseRecordId :this.continueId,
			isHiddenAdd:this.isAllReadOnly,
			isHiddenDel:this.isAllReadOnly,
			isHiddenAuto:this.isAllReadOnly,
			isHiddenCom : true
	  	})
		this.formPanel = new Ext.FormPanel({
			border : false,
			monitorValid : true,
			frame : true,
			
			autoScroll : true,
			items : [{	
				
				xtype : 'fieldset',
				title : '续当登记',
				collapsible : true,
				layout : 'anchor',
				anchor :'98%',
				items : [this.pawnContinuedForm,this.pawnContinuedFundIntentView]
			},{	
				xtype : 'fieldset',
				title : '续当历史记录',
				collapsible : true,
				layout : 'anchor',
				anchor :'98%',
				items : [new PawnContinuedView({projectId:this.projectId,businessType:this.businessType,isHiddenEdit:true,continueId : this.continueId})]
			}]
		});
		// 加载表单对应的数据
		if (this.projectId != null && this.projectId != 'undefined' && this.businessType!=null && this.businessType!='undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/pawn/project/getPawnInfoPawnContinuedManagment.do?projectId='
								+ this.projectId+'&businessType='+this.businessType+"&continueId="+this.continueId,
						root : 'data',
						preName : 'pawnContinuedManagment',
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							this.getCmpByName('phnumber').setValue(result.phnumber);
							this.getCmpByName('cardType').setValue(result.cardType);
							this.getCmpByName('cardNumber').setValue(result.cardNumber);
							this.getCmpByName('customerName').setValue(result.customerName);
							if(typeof(result.data)!='undefined' && result.data!=null){
								this.getCmpByName('pawnContinuedManagment.managerId').setRawValue(result.data.managerName);
								this.getCmpByName('pawnContinuedManagment.managerId').nextSibling().setValue(result.data.managerId)
							}
							fillPawnContinuedData(this,result,this.idDefinition);
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
		var fundIntentJsonData=this.pawnContinuedFundIntentView.getGridDate();
		var gridPanel=this.gridPanel
		var conGridPanel=this.conGridPanel
		var projectPanel=this.projectPanel;
		this.formPanel.getForm().submit({
		    clientValidation: false, 
			url : __ctxPath + '/creditFlow/pawn/project/savePawnContinuedManagment.do',
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
				if(null!=conGridPanel && typeof(conGridPanel)!='undefined'){
					conGridPanel.getStore().reload()
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
