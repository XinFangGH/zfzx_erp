

Ext.define('htmobile.creditFlow.public.mortgage.DZYMortgageFormBaseAdd', {
	extend : 'Ext.form.Panel',

	name : 'DZYMortgageFormBaseAdd',

	constructor : function(_cfg) {
		_cfg = _cfg || {};
		Ext.apply(this,_cfg);
		this.data = [{
						text : '车辆',
						value : '1'
					}, {
						text : '住宅',
						value : '7'
					}]
		// / var data =
		// [['车辆',1],['股权',2],['机器设备',5],['存货/商品',6],['无形权利',14],['住宅',7],['商铺写字楼',8],['住宅用地',9],['商业用地',10],['商住用地',11],/*['教育用地',12],*/['工业用地',13],['公寓',15],['联排别墅',16],['独栋别墅',17],['应收账款',18]];
		Ext.apply(_cfg, {
			style:'background-color:white;',
			title : "添加<抵质押物>基础信息",
			defaults : {
				xtype : 'textfield',
				labelWidth : document.body.clientWidth / 3,
				clearIcon : true
			},
			items : [{
				xtype : 'fieldset',
				defaults : {
					labelWidth : document.body.clientWidth / 3,
					xtype : 'textfield',
					clearIcon : true
				},
				items : [{
					xtype : 'hiddenfield',
					name : 'procreditMortgage.id',
					value : Ext.isEmpty(this.MortgageData)
							? null
							: this.MortgageData.vProcreditDictionary.id
				}, {
					xtype : 'hiddenfield',
					name : 'procreditMortgage.projid',
					value : this.projectId
				}, {
					xtype : 'hiddenfield',
					name : 'procreditMortgage.businessType',
					value : this.businessType
				}, {
					label : '担保类型',

					xtype : "dickeycombo",
					nodeKey : 'dblx',
					name : "procreditMortgage.assuretypeid",
					value : Ext.isEmpty(this.MortgageData)
							? null
							: this.MortgageData.vProcreditDictionary.assuretypeidValue
				}, {
					label : '抵质押物类型',
					xtype : 'selectfield',
					options : this.data,
					name : 'procreditMortgage.mortgagenametypeid',
					value : (this.typeId == 7) ? "住宅" : "车辆",
					listeners : {
						scope : this,
						'change' : function(this1, newValue, oldValue, eOpts) {
							if (newValue == 7) {
								this.getCmpByName('procreditMortgage.mortgagepersontypeforvalue').setValue("住宅")
							}
							if (newValue == 1) {
								this.getCmpByName('procreditMortgage.mortgagepersontypeforvalue').setValue("车辆")
							}
						}
					}
				}
				, {
					xtype : 'hiddenfield',
					value : (this.productId == 107) ? "住宅" : "车辆",
					name : 'procreditMortgage.mortgagepersontypeforvalue'
				}, {
					label : '所有人类型',
					xtype : "dickeycombo",
					id : 'procreditMortgagepersonType',
					nodeKey : 'syrlx',
					name : "procreditMortgage.personType",
					value : Ext.isEmpty(this.MortgageData)
							? this.personType
							: this.MortgageData.vProcreditDictionary.personTypeValue
						// ,
						// listeners : {
						// scope:this,
						// 'change' : function(f) {
						// Ext.getCmp("customerEnterpriseName").setValue(this.personId);
						// Ext.getCmp("customerEnterpriseNameaa").setValue(this.personName);
						// }
						// }
				}, {
					id : 'customerEnterpriseNameaa',
					name : 'customerEnterpriseName1',
					label : '所有权人',
					value : Ext.isEmpty(this.MortgageData)
							? this.personName
							: this.MortgageData.vProcreditDictionary.assureofnameEnterOrPerson,
					readOnly : this.readOnly,
					listeners : {
						scope : this,
						'focus' : function(f) {
							var personType = Ext
									.getCmp("procreditMortgagepersonType")
									.getValue();
							if (!Ext.isEmpty(personType) && personType == "602") { // 企业
								mobileNavi.push(Ext.create(
										'htmobile.public.SelectEnterpriselist',
										{
											callback : function(data) {
												var customerEnterpriseName = Ext
														.getCmp("customerEnterpriseName");
												var customerEnterpriseName1 = Ext
														.getCmp("customerEnterpriseNameaa");
												customerEnterpriseName
														.setValue(data.id);
												customerEnterpriseName1
														.setValue(data.enterprisename);
												// applypersonname.setValue(data.name);
											}
										}));
							}
							if (!Ext.isEmpty(personType) && personType == "603") { // 个人
								mobileNavi.push(Ext.create(
										'htmobile.public.SelectPersonlist', {
											callback : function(data) {
												var customerEnterpriseName = Ext
														.getCmp("customerEnterpriseName");
												var customerEnterpriseName1 = Ext
														.getCmp("customerEnterpriseNameaa");
												customerEnterpriseName
														.setValue(data.id);
												customerEnterpriseName1
														.setValue(data.name);
												// applypersonname.setValue(data.name);
											}
										}));
							}

						}
					}
				}, {
					xtype : 'hiddenfield',
					id : 'customerEnterpriseName',
					name : 'customerEnterpriseName',
					value : Ext.isEmpty(this.MortgageData)
							? this.personId
							: this.MortgageData.vProcreditDictionary.assureofname
				}, {
					label : '与债务人的关系',
					name : 'procreditMortgage.relation',
					value : Ext.isEmpty(this.MortgageData)
							? '本人'
							: this.MortgageData.vProcreditDictionary.relation
				},
						/*
						 * { label: '<div class="fieldlabel">评估价值:</div>',
						 * name : "procreditMortgage.finalprice",
						 * value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.finalprice
						 * },{ label: '<div class="fieldlabel">公允价值:</div>',
						 * name : "procreditMortgage.finalCertificationPrice",
						 * value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.finalCertificationPrice } , {
						 * label: '<div class="fieldlabel">获取时间:</div>', name :
						 * "procreditMortgage.valuationTime",
						 * value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.valuationTime } , {
						 * label: '<div class="fieldlabel">抵质押率:</div>', name :
						 * "procreditMortgage.assuremoney",
						 * value:Ext.isEmpty(this.MortgageData)?null:this.MortgageData.vProcreditDictionary.assuremoney },
						 */
						{
							label : '备注',
							name : "procreditMortgage.mortgageRemarks",
							value : Ext.isEmpty(this.MortgageData)
									? null
									: this.MortgageData.vProcreditDictionary.mortgageRemarks
						}

				]
			}, {
				xtype : 'button',
				docked:'bottom',
				cls : 'submit-button',
				text : "下一步",
				scope : this,
				handler : this.nextbtn
			}]
		});

		this.callParent([_cfg]);

	},
	nextbtn : function() {
		var loginForm = this;
		var assuretypeid = loginForm
				.getCmpByName('procreditMortgage.assuretypeid').getValue();
		if (Ext.isEmpty(assuretypeid)) {
			Ext.Msg.alert('', '担保类型不能为空');
			return;
		}
		var mortgagenametypeid = loginForm
				.getCmpByName('procreditMortgage.mortgagenametypeid')
				.getValue();
		if (Ext.isEmpty(mortgagenametypeid)) {
			Ext.Msg.alert('', '抵质押物类型不能为空');
			return;
		}
		var personType = loginForm.getCmpByName('procreditMortgage.personType')
				.getValue();
		if (Ext.isEmpty(personType)) {
			Ext.Msg.alert('', '所有人类型不能为空');
			return;
		}
		var relation = loginForm.getCmpByName('procreditMortgage.relation')
				.getValue();
		var mortgageRemarks = loginForm
				.getCmpByName('procreditMortgage.mortgageRemarks').getValue();
		var customerEnterpriseName = loginForm
				.getCmpByName('customerEnterpriseName').getValue();
		if (Ext.isEmpty(customerEnterpriseName)) {
			Ext.Msg.alert('', '所有权人不能为空');
			return;
		}
		var mortgagepersontypeforvalue = loginForm
				.getCmpByName('procreditMortgage.mortgagepersontypeforvalue')
				.getValue();
		/*
		 * if(Ext.isEmpty(assuretypeid)){ note.setHtml("<div
		 * style='margin:10px;'><font>不能为空</font></div>"); return ; }
		 */
		// 车辆
		if (mortgagenametypeid == 1) {
			mobileNavi.push(Ext.create('htmobile.creditFlow.public.mortgage.vehicle.AddVehicle',{
												projectId : this.projectId,
												businessType : this.businessType,
												assuretypeid : assuretypeid,
												vProcreditDictionaryId : Ext
														.isEmpty(this.MortgageData)
														? null
														: this.MortgageData.vProcreditDictionary.id,
												mortgagenametypeid : mortgagenametypeid,
												personType : personType,
												relation : relation,
												mortgageRemarks : mortgageRemarks,
												customerEnterpriseName : customerEnterpriseName,
												mortgagepersontypeforvalue : mortgagepersontypeforvalue,
												MortgageData : this.MortgageData
											}));
		} else if (mortgagenametypeid == 7) {

			mobileNavi.push(Ext.create('htmobile.creditFlow.public.mortgage.house.AddHouse',{
												projectId : this.projectId,
												businessType : this.businessType,
												vProcreditDictionaryId : Ext
														.isEmpty(this.MortgageData)
														? null
														: this.MortgageData.vProcreditDictionary.id,
												assuretypeid : assuretypeid,
												mortgagenametypeid : mortgagenametypeid,
												personType : personType,
												relation : relation,
												mortgageRemarks : mortgageRemarks,
												customerEnterpriseName : customerEnterpriseName,
												mortgagepersontypeforvalue : mortgagepersontypeforvalue,
												MortgageData : this.MortgageData
											}));

		}

	}

});
