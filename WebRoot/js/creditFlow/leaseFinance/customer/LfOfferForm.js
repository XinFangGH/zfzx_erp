/**
 * isEdit:可编缉 Model:传递对象
 * 
 */
LfOfferForm = Ext.extend(Ext.Window, {
	isEdit : false, // false为可编辑
	// 构造函数
	investPersonPanel : null,
	Model : null,
	// 有参构造
	constructor : function(_cfg) {
		// 保存后栓新父容器
		if (_cfg != null) {
			if (typeof(_cfg.investPersonPanel) != "undefined") {
				this.investPersonPanel = _cfg.investPersonPanel;
			}
			// 没有什么可以编辑的
			// Ext.applyIf(this, _cfg);
			// 传递的对象,有对象为修改
			if (_cfg.Model != null) {
				this.Model = _cfg.Model;
				// 有对象才谈是否可编缉
				if (_cfg.isEdit == true) {
					this.isEdit = true;
				};
			};
		}
		this.initUIComponents(this.isEdit);
		LfOfferForm.superclass.constructor.call(this, {
					id : 'InvestPersonFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					width : 600,
					height : 250,
					border : false,
					maximizable : true,
					title : '供货方信息',
					buttonAlign : 'center',
					buttons : [{
								hidden : this.savehidden,
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								// 有对象，不可以重置
								hidden : this.resethidden,
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								hidden : this.cancelhidden,
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function(isEdit) {
		// 定义变量属性
		this.savehidden = this.isEdit;
		this.resethidden = (this.Model != null);
		this.cancelhidden = this.isEdit;
		this.bt = Model = this.Model;
		var investPerson = this.investPerson;
		// 定义form，共4行 2行为2列
		// 工厂,传递label+映射内容。。。默认格式，统一labelStyle/anchor
		facT = function(a, b, c) {
			// c==1时为只读
			var ed = (c == "1" ? true : isEdit);
			var v = (Model == null ? '' : Model[b]);
			var t = (c == null ? 'textfield' : c)
			return {
				xtype : t,
				fieldLabel : a,
				anchor : "100%",
				//labelStyle : "width : 100px",
				readOnly : ed,
				value : v,
				name : 'flObjectSupplior.' + b,
				maxLength : 20
			}
		};
		var column1 = {
			columnWidth : .5,
			layout : 'form',
			items : [facT('法人代表(负责人)', "legalPersonName"),
					facT('最近联系人', 'connectorName'), facT('联系人职位', 'connectorPosition')]
		};
		var column2 = {
			columnWidth : .5,
			layout : 'form',
			items : [facT('公司电话', 'companyPhoneNum'), facT('联系人电话', 'connectorPhoneNum'),
					facT('公司传真', 'companyFax')]
		};

		var h2 = {
			layout : 'column',
			items : [column1, column2]
		};

		this.formPanel = new Ext.form.FormPanel({
					id : "addPanel",
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
					//	anchor : '100%',
						//columnWidth : 1,
						labelWidth : 100
					},
					items : [facT("id", 'id', 'hidden'),
							facT("供货方名称", 'Name'), h2,
							facT('供货方地址', 'companyAddress'), facT('备注', 'companyComment')]
				});
			

	},

	reset : function() {
		this.formPanel.getForm().reset();
	},

	cancel : function() {
		this.close();
	},
	save : function() {
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/creditFlow/leaseFinance/project/saveFlObjectSupplior.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('bigGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				if (this.investPersonPanel != null) {
					this.investPersonPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});