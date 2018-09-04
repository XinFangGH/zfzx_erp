/**
 * @author
 * @createtime
 * @class PlPersionOrPlanSuperviseRightForm
 * @extends Ext.Window
 * @description PlPersionOrPlanSuperviseRightForm表单
 * @company 北京互融时代软件有限公司
 */

PlPersionOrPlanSuperviseRightForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
	
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		
PlPersionOrPlanSuperviseRightForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 550,
					width : 1100,
					autoScroll : true,
					maximizable : true,
					title : '展期债权提交',        
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
		var DeferApplyInfoPanel =new FinanceExtensionlPanel({
	      	projectId : this.projectId,
	      	businessType:this.businessType,
	      	dateName:'展期开始日期',
	      	isAllReadOnly:true,
	      	isHiddencalculateBtn:true,
	      	idDefinition:'extenstion'+this.projectId
	    })
     	
		this.superviseSlFundIntentVM=new superviseSlFundIntentVM({
			object :DeferApplyInfoPanel,
			projId : this.sprojectId,
			preceptId:this.fundProjectId,
			isHidden : true,
			titleText:'还款收息表',
			businessType : this.businessType,
		    slSuperviseRecordId : this.projectId,
			isUnLoadData :false,
			isThisSuperviseRecord :'yes',
			isHiddenAuto:true,
			isHiddenseesumqlideBtn:true,
			isHiddenExcelBtn:true,
			isHiddenAdd:true,
			isHiddenDel:true
	  	})
		this.formPanel = new Ext.FormPanel({
			border : false,
			monitorValid : true,
			frame : true,
			
			autoScroll : true,
			items : [{	
				
				xtype : 'fieldset',
				title : '续当登记信息',
				collapsible : true,
				layout : 'anchor',
				anchor :'98%',
				items : [DeferApplyInfoPanel,this.superviseSlFundIntentVM]
			}]
		});
		// 加载表单对应的数据
		if (this.projectId != null && this.projectId != 'undefined' && this.businessType!=null && this.businessType!='undefined') {
			this.loadData({
			url : __ctxPath + '/supervise/getSuperviseRightInfoSlSuperviseRecord.do?projectId='+this.sprojectId+'&slSuperviseRecordId='+this.projectId,
			method : "POST",
			preName : [ 'slSmallloanProject',"slSuperviseRecord"],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				if(null!=alarm_fields.data.slSuperviseRecord.continuationMoney){
					this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.slSuperviseRecord.continuationMoney, ',000,000,000.00'))
				}
				fillDataExtension(DeferApplyInfoPanel,alarm_fields,'extenstion'+this.projectId)
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
			var sprojectId=this.sprojectId
			var fundProjectId=this.fundProjectId
			var projectId=this.projectId;
			var porProId=this.porProId;borProId
			var borProId=this.borProId;
			var objGridPanel =this.objGridPanel
			this.formPanel.getForm().submit({
			    clientValidation: false, 
				url : __ctxPath + '/creditFlow/financingAgency/persion/savePlMmObligatoryRightChildrenBpPersionOrPro.do',
				params : {
					'sprojectId' : sprojectId,
					'fundProjectId' : fundProjectId,
					'projectId' : projectId,
					'porProId' : porProId,
					'borProId':borProId
					
				},
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				scope: this,
				success : function(fp, action) {
					var object = Ext.util.JSON.decode(action.response.responseText)
					Ext.ux.Toast.msg('操作信息', '保存信息成功!');
					objGridPanel.getStore().reload();
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
