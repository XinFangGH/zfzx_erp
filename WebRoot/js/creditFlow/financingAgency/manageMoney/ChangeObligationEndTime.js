//ChangeObligationEndTime
/**
 * @author
 * @createtime
 * @class PlManageMoneyTypeForm
 * @extends Ext.Window
 * @description PlManageMoneyType表单
 * @company 智维软件
 */
ChangeObligationEndTime = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ChangeObligationEndTime.superclass.constructor.call(this, {
					id : 'ChangeObligationEndTime',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 500,
					maximizable : true,
					title : '修改'+this.childrenorName+"到期时间",
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							},{
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					frame:true,
					defaults : {
						anchor : '96%,96%'
					},
					labelAlign : "right",
					defaultType : 'textfield',
					items : [{
						name : 'plMmObligatoryRightChildren.childrenorId',
						xtype : 'hidden',
						value :this.childrenorId
					},{
						fieldLabel : '债权名称',
						name : 'plMmObligatoryRightChildren.childrenorName',
						readOnly:true,
						maxLength : 20,
						xtype : 'textfield',
						value:this.childrenorName
					}, {
						fieldLabel : '原债权到期日',
						name : 'intentDate',
						readOnly:true,
						maxLength : 20,
						xtype : 'datefield',
						format : 'Y-m-d',
						value:this.intentDate
						
					},{
						fieldLabel : '债权更改日期',
						name : 'plMmObligatoryRightChildren.intentDate',
						xtype : 'datefield',
						format : 'Y-m-d',
						minValue:new Date().add(Date.DAY, 1)
					
						}]
				});
		// 加载表单对应的数据
		if (this.manageMoneyTypeId != null
				&& this.manageMoneyTypeId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyType.do?manageMoneyTypeId='
						+ this.manageMoneyTypeId,
				root : 'data',
				preName : 'plManageMoneyType'
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
		var gridPanel=this.gridPanel;
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ "/creditFlow/financingAgency/changeEndTimePlMmObligatoryRightChildren.do",
					callback : function(fp, action) {
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});
