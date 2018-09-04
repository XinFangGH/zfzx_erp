//SeeSingleSystemAccountInfo
SeeSingleSystemAccountInfo = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		if (typeof(_cfg.isReadOnly) != "undefined") {
			this.isRead = _cfg.isReadOnly;
		};
		if (null != _cfg.personData) {
			this.isflag = true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SeeSingleSystemAccountInfo.superclass.constructor.call(this, {
					id : 'SeeSingleSystemAccountInfo',
					layout : 'form',
					items : [this.formPanel],
					modal : true,
					autoHeight : true,
					width : 1000,
					maximizable : true,
					title : '',
					buttonAlign : 'center'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					border : false,
					height : 70,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding-top:20px',
					items : [{      
					            xtype : 'fieldset',
								name:'projectInfo',
								title : '系统账户基本信息 ',
								collapsible : true,
								autoHeight : true,
								labelAlign : 'right',
								items : [this.projectInfo]
									
						    },{
								xtype : 'fieldset',
								name:'projectInfo',
								title : '第三方账户信息 ',
								collapsible : true,
								autoHeight : true,
								labelAlign : 'right',
								items : [this.projectInfo]
							},{
								xtype : 'fieldset',
								name:'projectInfo',
								title : '投资人信息 ',
								collapsible : true,
								autoHeight : true,
								labelAlign : 'right',
								items : [this.projectInfo]
							}]
				});
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		if (this.investPeronId != null && this.investPeronId != 'undefined') {
		
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
		var investpersonName = this.getCmpByName("csInvestmentPerson.investName").getValue();
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/creditAssignment/bank/saveDealInfoObAccountDealInfo.do',
			params : {
				'investpersonName' : investpersonName
			},
			callback : function(fp, action) {
				alert(action.result.msg);
				this.close();
			}
		});

	}// end of save
});