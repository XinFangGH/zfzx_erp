/**
 * @author
 * @createtime
 * @class P2pDeployProductForm
 * @extends Ext.Window
 * @description BpProductParameter表单
 * @company 智维软件
 */
P2pDeployProductForm = Ext.extend(Ext.Window, {
	// 构造函数
	isAllReadOnly:false,
	productId:null,
	constructor : function(_cfg) {
		if(typeof(_cfg.isAllReadOnly)!="undefined"){
			this.isAllReadOnly=_cfg.isAllReadOnly;
		}
		if(typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pDeployProductForm.superclass.constructor.call(this, {
					id : 'P2pDeployProductFormWin'+this.productId,
					layout : 'form',
					items : this.outPanel,
					modal : true,
					autoScroll:true,
					height : 600,
					width :(screen.width)*0.9,
					maximizable : true,
					frame:true,
					title : '产品信息',
					buttonAlign : 'center',
					buttons :this.isHideBtns?null: [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},{
								text : '提交',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
    	this.p2pLoanConditionOrMaterialProductView = new P2pLoanConditionOrMaterialProductView({
			isProduct:true,
			productId:this.productId,
			operationType:this.operationType,
			conditionType:2,
			isBiBeiReadOnly:true,
			hiddenDel:this.hiddenDel,
			hiddenedit:this.hiddenedit,
			hiddenAdd:this.hiddenAdd,
			isAllReadOnly:this.isAllReadOnly
		});
		this.p2pLoanApplyProductView = new P2pLoanConditionOrMaterialProductView({
			isProduct:true,
			productId:this.productId,
			conditionType:1,
			isBiBeiReadOnly:true,
			hiddenDel:this.hiddenDel,
			hiddenedit:this.hiddenedit,
			hiddenAdd:this.hiddenAdd,
			isAllReadOnly:this.isAllReadOnly
		});
		this.formPanel1=new P2pLoanProductModule_ProductDiscribe({
			isAllReadOnly :this.isAllReadOnly,
			isEditReadOnly:this.isEditReadOnly,
			productId : this.productId
		});
		this.formPanel2 = new P2pLoanProductModule_ProductFiancial({
		   	isAllReadOnly :this.isAllReadOnly,
		   	defination:"_productP2pDefination_",
		   	productId:this.productId
		   	
	    });
	    this.P2pLoanRateView = new P2pLoanRateView({
			productId:this.productId,
			isHiddenAddBtn : this.isAllReadOnly,
			isHiddenDelBtn : this.isAllReadOnly,
			isHiddenEdiBtn : this.isAllReadOnly
		});
		this.outPanel = new Ext.form.FormPanel({
			autoWidth:true,
			modal : true,
			labelWidth : 100,
			frame:true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				labelAlign : 'right',
				autoHeight : true
		    },
		    labelAlign : "right",
		    items:[{
				xtype : 'fieldset',
				border : false,
				title : '产品简介',
				collapsible : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.formPanel1]
		    },{
				xtype : 'fieldset',
				title : '款项信息',
				collapsible : false,
				border : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.formPanel2, this.P2pLoanRateView]
		    },{
				xtype : 'fieldset',
				title : '申请条件',
				collapsible : false,
				border : false,
				labelAlign : 'right',
				items : [this.p2pLoanApplyProductView]
		    },{
				xtype : 'fieldset',
				title : '贷款材料清单',
				collapsible : false,
				border : false,
				labelAlign : 'right',
				items : [this.p2pLoanConditionOrMaterialProductView]
		    }]
		});
		// 加载表单对应的数据
		if (this.productId != null && this.productId != 'undefined') {
			var productId=this.productId;
			this.outPanel.loadData({
				url : __ctxPath+ '/p2p/getP2pLoanProduct.do?productId='+ this.productId,
				root : 'data',
				preName : 'p2pLoanProduct',
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					fillP2pProductData(this,alarm_fields,"_productP2pDefination_",productId);
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
	save : function(v) {
		var panel=this.gridPanel;
		if(this.outPanel.getForm().isValid()){
			//isSave(0代表保存,1代表提交)
			var url=__ctxPath+ '/p2p/saveP2pLoanProduct.do?isSave=0';
			if(v.text=='提交'){
				var loanRateCount = this.P2pLoanRateView.panel.store.getTotalCount();
				if(0==loanRateCount){
					Ext.ux.Toast.msg("操作信息","请增加利率 !!!");
					return;
				}
				
				var applyTJ = this.p2pLoanApplyProductView.gridPanel.store.getTotalCount();
				if(0==applyTJ){
					Ext.ux.Toast.msg("操作信息","请增加申请条件!!!");
					return;
				}
				
				var materailCount = this.p2pLoanConditionOrMaterialProductView.gridPanel.store.getTotalCount();
				if(0==materailCount){
					Ext.ux.Toast.msg("操作信息","请增加贷款材料清单!!!");
					return;
				}
				url=__ctxPath+ '/p2p/saveP2pLoanProduct.do?isSave=1';
			}
			
			this.outPanel.getForm().submit({
				    clientValidation: false, 
					url : url,
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						if (panel != null) {
							panel.getStore().reload();
						}
						this.close();
					}
			});
		}
	}
});