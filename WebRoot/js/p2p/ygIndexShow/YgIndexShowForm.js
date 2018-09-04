/**
 * @author
 * @createtime
 * @class PlManageMoneyTypeForm
 * @extends Ext.Window
 * @description PlManageMoneyType表单
 * @company 智维软件
 */
YgIndexShowForm = Ext.extend(Ext.Window, {
	id : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.id)!="undefined"){
        	this.id=_cfg.id;
		}
		// 必须先初始化组件
		this.initUIComponents();
		YgIndexShowForm.superclass.constructor.call(this, {
					id : 'YgIndexShowForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 500,
					maximizable : true,
					title : '修改详情',
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
						name : 'indexShow.id',
						xtype : 'hidden',
						value : this.id == null ? '' : this.id
					}, {
						fieldLabel : '一级类型',
						name : 'indexShow.oneLevelType',
						readOnly:true,
						maxLength : 20,
						allowBlank : false
					}, {
						fieldLabel : '二级类型',
						name : 'indexShow.twoLevelType',
						readOnly:true,
						maxLength : 20,
						allowBlank : false
					}, {
						fieldLabel : '内容描述',
						name : 'indexShow.description',
						xtype:"textarea",
						maxLength : 100,
						allowBlank : false
					}]
				});
				//加载表单对应的数据	
				if (this.id != null) {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p/getIndexShow.do?id='+ this.id,
								root : 'data',
								preName : 'indexShow'
					});
				}

	},// end of the initcomponents

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
		$postForm({
			formPanel:this.formPanel,
			scope:this,
			url:__ctxPath + '/p2p/saveIndexShow.do',
			callback:function(fp,action){
				var gridPanel = Ext.getCmp('IndexShowGrid');
				if (gridPanel != null) {
					
					gridPanel.getStore().reload();
				}
				this.close();
			}
			}
		);
	}//end of save

});