workCompanyWin = Ext.extend(Ext.Window, {
	// 构造函数
	investPersonPanel : null,
	isLook:false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		if (_cfg.isLook) {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		workCompanyWin.superclass.constructor.call(this, {
			layout : 'fit',
			autoScroll:true,
			items : [this.formPanel],
			modal : true,
			height : 180,
			width : 800,
			maximizable : true,
			title : '私营业主补填信息',
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'btn-save',
				hidden : this.isLook,
				scope : this,
				handler : this.save
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				hidden : this.isLook,
				scope : this,
				handler : this.cancel
			}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'column',
			labelWidth:100,
			items : [{
				columnWidth : .3,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				labelWidth : 85,
				items : [{
					xtype : 'hidden',
					name : 'workCompany.id'
				},{
					xtype : 'hidden',
					name : 'workCompany.personId',
					value:this.customerId
				}, {
					xtype : "dickeycombo",
					nodeKey : 'unitproperties',
					hiddenName : "workCompany.companyType",
					itemName : '企业类型', // xx代表分类名称
					fieldLabel : "企业类型",
					editable : false,
					readOnly : this.isReadOnly,
					listeners : {
						scope : this,
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if (combox.getValue == '' || combox.getValue() == null) {
									combox.setValue(st.getAt(0).data.itemId);
									combox.clearInvalid();
								} else {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								}
							})
						}
					}
				}, {
					xtype:'combo',
					fieldLabel : '经营场所',
					readOnly : this.isReadOnly,
					name : 'workCompany.companyAddress',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'value',
				    width : 70,
				    store :new Ext.data.SimpleStore({
						fields : ["name", "value"],
						data : [["租赁", "租赁"],["自有资产", "自有资产"]]
					}),
					triggerAction : "all"
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				defaults : {
					anchor : '90%'
				},
				labelWidth : 115,
				items : [{
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '注册时间',
					readOnly : this.isReadOnly,
					name : 'workCompany.onCompanyDate'
				}, {
					xtype : 'numberfield',
					fieldLabel : '员工人数',
					readOnly : this.isReadOnly,
					name : 'workCompany.employeeTotal'
				}]
			}, {
				columnWidth : .34,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				labelWidth : 85,
				items : [{
					xtype : 'numberfield',
					fieldLabel : '持股比例',
					name : 'grossdebt',
					readOnly : this.isReadOnly,
					value:this.grossdebt
				}]
			},{
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : ''
				}]
			}]
		})
		
		// 加载表单对应的数据
		if (this.customerId != null && this.customerId != 'undefined') {
			var  panel =this;
			this.formPanel.loadData({
				url : __ctxPath + '/creditFlow/customer/person/workcompany/findInfoByPersonIdWorkCompany.do?personId='+ this.customerId,
				root : 'data',
				preName : ['workCompany','a','b','c'],
				scorp:this,
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
				}
			});
		}
	},// end of the initcomponents
	/**
	 * 取消
	 * 
	 * @param {}
	 * window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var win=this;
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/creditFlow/customer/person/workcompany/saveWorkCompany.do',
			callback : function(fp, action) {
				win.close();
			}
		});
	}// end of save
});