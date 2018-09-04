/**
 * @author 
 * @createtime 
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
InverstPersonBpFundIntent = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.fundIntentId)!="undefined")
						{
						      this.fundIntentId=_cfg.fundIntentId;
						}
							if(typeof(_cfg.editqlideMoney)!="undefined")
						{
						      this.editqlideMoney=_cfg.editqlideMoney;
						}
							if(typeof(_cfg.notMoney)!="undefined")
						{
						      this.notMoney=_cfg.notMoney;
						}
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				InverstPersonBpFundIntent.superclass.constructor.call(this, {
				//			id : 'editQlideCheck',
							layout : 'fit',
							items : this.gridPanel,
							modal : true,
							height : 500,
							width : 1000,
							autoScroll : true,
							//maximizable : true,
							title : this.investPersonName+'款项计划表'
						
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.datafield = new Ext.form.DateField({
			format : 'Y-m-d',
			allowBlank : false,
			readOnly : this.isHidden1
		});
		this.datafield1 = new Ext.form.DateField({
			format : 'Y-m-d',
			allowBlank : false,
			readOnly : this.isHidden1
		});
		var fundTypenodekey = "";
		if (this.businessType == "Financing") {
			fundTypenodekey = "finaning_fund";
		}
		if (this.businessType == "SmallLoan") {
			fundTypenodekey = "financeType";
		}
		this.comboType = new DicIndepCombo({
			editable : false,
			lazyInit : false,
			forceSelection : false,
			nodeKey : fundTypenodekey,
			// itemVale : 1149,
			// itemName : '贷款资金类型',
			readOnly : this.isHidden1
		})
		this.comboType.store.reload();
		this.topbar = new Ext.Toolbar({
			items : [{
				html:'<font class="x-myZW-fieldset-title">【投资人放款收息表】</font>'
			},{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenAdd,
				handler : this.createRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenAdd
			}), {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenDel,
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenDel
			}),{
				iconCls : 'btn-detail',
				text : '查看单项流水记录',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenseeqlideBtn,
				handler : function() {
					var selRs = this.gridPanel.getSelectionModel()
							.getSelections();
					if (selRs <= 0) {
						Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
						return;
					} else if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
						return;
					}
					this.fundIntentWaterReconciliationInfo.call(this, selRs[0],
							1);
				}
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenseeqlideBtn
			}), {
				iconCls : 'btn-details',
				text : '项目总流水记录',
				xtype : 'button',
				hidden : this.isHiddenseesumqlideBtn,
				scope : this,
				handler : function() {
					this.projectWaterReconciliationInfo(2);
				}
			}, {
				iconCls : 'btn-info-add',
				text : '生成',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenAutoCreate,
				handler : this.confirmRs
			}, new Ext.Toolbar.Separator({
				hidden : this.isHiddenAutoCreate
			}), {
				iconCls : 'btn-xls',
				text : '导出Excel',
				xtype : 'button',
				scope : this,
				hidden:this.isHiddenExcel,
				handler : function(){
					window.open(__ctxPath + "/creditFlow/finance/outputExcelSlFundIntent.do?inverstPersonId="+this.inverstPersonId+"&projectId="+this.projectId,'_blank');
				}
			}, "->",{
				xtype : 'radio',
				boxLabel : '按投资人分组显示',
				name : "paixu",
				scope : this,
				hidden: this.hiddenPx,
				listeners : {
					scope : this,
					'check':function(com,flag){
						if(flag==true){
							this.gridPanel.getView().enableGrouping=true
							this.gridPanel.getView().refresh()
							this.related(false);
						}
					}
				}
			}, new Ext.Toolbar.Separator({
				hidden : this.hiddenPx
			}), {
				xtype : 'radio',
				name : "paixu",
				boxLabel : '按计划到账日期排序显示',
				scope : this,
				checked : false,
				hidden: this.hiddenPx,
				listeners : {
					scope : this,
					'check':function(com,flag){
						if(flag==true){
							this.gridPanel.getView().enableGrouping=false
							this.gridPanel.getView().refresh()
							this.related(true);
						}
					}
				}
			}, ' ', ' ', ' ', ' ']
		});
			
			url=__ctxPath + "/creditFlow/finance/listOfInverstPersonBpFundIntent.do?orderNo="+this.orderNo+"&bidPlanId="+this.bidPlanId
	var field = Ext.data.Record.create([{
				name : 'payintentPeriod'
			}, {
				name : 'principal'
			}, {
				name : 'interest'
			}, {
				name : 'intentDate'
			}, {
				name : 'sumMoney'
			}, {
				name : 'factDate'
			},{
				name : 'afterMoney'
			},{
				name : 'bidPlanId'
			},{
		    	name:'reward'
			},{
				name : 'orderNo'
			},{
				name : 'interestPenaltyMoney'
			},{
				name : 'accrualMoney'
			}
			]);
		var jStore = new Ext.data.JsonStore({
					url : url,
					root : 'result',
					fields : field
				});
		jStore.load({
					params : {
						investId : this.investId
					}
				});
		var  investPersonName=function(data, cellmeta, record){
       	   if(typeof(record.get("investPersonName"))!="undefined"){
       	   		 if(null!=record.data.payintentPeriod && record.data.payintentPeriod){
       	   		 	
       	   		 }
       	   		 if(record.data.isValid == 1){
       	   		 	return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ record.get("investPersonName")+ "</font>"
       	   		 }else{
	          		 return record.get("investPersonName");
       	   		 }
       	   }else{
       	   		if(record.data.isValid == 1){
       	   		 	return '<font style="font-style:italic;text-decoration: line-through;color:gray">未知</font>'
       	   		}else{
	       	   		return '未知'
       	   		}
       	   }
       }
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
			header : '序号'
		});
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			name : 'gridPanel',
			scope : this,
			store : jStore,
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			tbar : null,
			rowActions : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			sm : this.projectFundsm,
			plugins : [summary],
			columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					},{
				header : '期数',
				dataIndex : 'payintentPeriod',
				summaryType : 'count',
				summaryRenderer : totalMoney,
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if(null!=value){
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ '第'+value+'期'+ "</font>"
						} else {
							return '第'+value+'期';
						}
					}
				 }
				}, {

						header : '计划到帐日',
						dataIndex : 'intentDate',
						format : 'Y-m-d'
					}, {
						header : '本金',
						dataIndex : 'principal',
						//editor : this.comboType,
						width : 107,
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '利息',
						dataIndex : 'interest',
						summaryType : 'sum',
						width : 110,
						align : 'right'
						/*editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isHidden1
						}*/,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '奖励金额',
						dataIndex : 'reward',
						summaryType : 'sum',
						width : 110,
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '补偿息金额',
						dataIndex : 'interestPenaltyMoney',
						summaryType : 'sum',
						width : 110,
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {
						header : '罚息金额',
						dataIndex : 'accrualMoney',
						summaryType : 'sum',
						width : 110,
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					},/*{
						header : '返现金额',
						dataIndex : 'couponInterest',
						summaryType : 'sum',
						width : 110,
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					},*/{
						header : '合计',
						dataIndex : 'sumMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}, {

						header : '实际到帐日',
						dataIndex : 'factDate',
						format : 'Y-m-d'
					}, {
						header : '实际到账金额',
						dataIndex : 'afterMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
									if(value!=''){
										return Ext.util.Format.number(value,',000,000,000.00')+"元"
									}else{
										return '';
									}
								}
					}/*{
				header : 'fundIntentId',
				dataIndex : 'fundIntentId',
				hidden : true
			},{
				header:'资金来源',
				dataIndex:'fundResource',
				sortable : true,
				width : 100,
				scope : this,
				editor:{
					xtype:'combo',
					id : 'type',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'value',
				    width : 70,
				    readOnly:this.isHidden,
				    store : new Ext.data.SimpleStore({
							fields : ["name", "value"],
							data : [["企业", "1"],["个人", "0"]]
					}),
					triggerAction : "all",
					listeners : {
						scope : this,
						'select' : function(combox,record,index){
							var grid_sharteequity=this.gridPanel;
							var r=combox.getValue();
							var personCom = new Ext.form.ComboBox({
								triggerClass : 'x-form-search-trigger',
								resizable : true,
								onTriggerClick : function() {
									selectInvestEnterPrise(selectInvestEnterpriseObj,r,false);
								},
								mode : 'remote',
								editable : false,
								lazyInit : false,
								allowBlank : false,
								typeAhead : true,
								minChars : 1,
								width : 100,
								listWidth : 150,
								store : new Ext.data.JsonStore({}),
								triggerAction : 'all',
								listeners : {
									'select' : function(combo,record,index) {
										grid_sharteequity.getView().refresh();
									}
								}
							})
							var ComboBox = new Ext.grid.GridEditor(personCom);
							grid_sharteequity.getColumnModel().setEditor(4,ComboBox);
						}
					}
				},
				renderer : function(value) {
						return "个人";
				}
			},{
				header : '投资方',
				dataIndex : 'investPersonName',
				readOnly:this.isHidden,
				sortable : true,
				width : 100,
				scope : this,
				summaryType : 'count',
				summaryRenderer : totalMoney
			},,{
				header : '投资人',
				hidden : true,
				dataIndex : 'investPersonId'
//				renderer : investPersonName
			},{
				header : '投资人',
				dataIndex : 'investPersonName',
				editor :new Ext.form.ComboBox({
							triggerClass : 'x-form-search-trigger',
							resizable : true,
							onTriggerClick : function() {
								selectInvestPerson(selectPersonObj);
								//selectPWName(selectPersonObj);
							},
							mode : 'remote',
							editable : false,
							lazyInit : false,
							allowBlank : false,
							typeAhead : true,
							minChars : 1,
							width : 100,
							listWidth : 150,
							readOnly:this.isHidden,
							store : new Ext.data.JsonStore({}),
							triggerAction : 'all',
							listeners : {
								scope : this,
								'select' : function(combo,record,index) {
									grid.getView().refresh();
								},
								'blur' : function(f) {
									if (f.getValue() != null && f.getValue() != '') {
									}
								}
							}
						}),
				renderer : investPersonName
			}, {
				header : '资金类型',
				dataIndex : 'fundType',
				editor : this.comboType,
				width : 107,
				summaryType : 'count',
				summaryRenderer : totalMoney,
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var objcom = this.editor;
					var objStore = objcom.getStore();
					var idx = objStore.find("dicKey", value);
					if (idx != "-1") {
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ objStore.getAt(idx).data.itemValue+ "</font>"
						}else{
							return objStore.getAt(idx).data.itemValue;
						}
					} else {
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ record.get("fundTypeName")+ "</font>"
						}else{
							return record.get("fundTypeName");
						}
					}
				}
			}, {
				header : '计划到帐日',
				dataIndex : 'intentDate',
				format : 'Y-m-d',
				editor : this.datafield,
				fixed : true,
				width : 80,
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var v;
					try {
						if (typeof(value) == "string") {
							v = value;
						}else{
						 	v=Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ v + "</font>";
					} else {
						return v;
					}
				}	
			}, {
				header : '投资客户计划收入金额',
				dataIndex : 'incomeMoney',
				summaryType : 'sum',
				align : 'right',
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					readOnly : this.isHidden1
				},
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			}, {
				header : '投资客户计划支出金额',
				dataIndex : 'payMoney',
				align : 'right',
				summaryType : 'sum',
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					readOnly : this.isHidden1
				},
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			},{
				header : '期数',
				dataIndex : 'payintentPeriod',
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if(null!=value){
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ '第'+value+'期'+ "</font>"
						} else {
							return '第'+value+'期';
						}
					}
				}
			},{
				header : '备注',
				dataIndex: 'remark',
				editor : {
					xtype : 'textfield',
					readOnly :this.isHidden
				},
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					if(null!=value){
						if (record.data.isValid == 1) {
							return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+value+ "</font>"
						} else {
							return value;
						}
					}
				}
			},{
				header : '实际到帐日',
				dataIndex : 'factDate',
				format : 'Y-m-d',
				hidden:this.hiddenCheck,
				editor : this.datafield,
				fixed : true,
				width : 80,
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					var v;
					try {
						if (typeof(value) == "string") {
							v = value;
						}else{
						 	v=Ext.util.Format.date(value, 'Y-m-d');
						}
					} catch (err) {
						v = value;
					}
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ v + "</font>";
					} else {
						return v;
					}
				}	
			}, {
				header : '已对账金额(元)',
				summaryType : 'sum',
				hidden:this.hiddenCheck,
				dataIndex : 'afterMoney',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			},{
				header : '未对账金额(元)',
				summaryType : 'sum',
				hidden:this.hiddenCheck,
				dataIndex : 'notMoney',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			}, {
				header : '平账金额',
				width : 150,
				hidden:true,
				dataIndex : 'flatMoney',
				renderer : function(value, metaData, record, rowIndex,colIndex, store) {
					if (record.data.isValid == 1) {
						return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+ Ext.util.Format.number(value,',000,000,000.00')+ "元</font>"
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
					}
				}
			}*/],
			listeners : {
				scope : this,
				afteredit : function(e) {
					if (e.record.data['fundType'] == 'principalLending') {
						e.record.set('incomeMoney', 0);
						e.record.commit();
					} else {
						e.record.set('payMoney', 0);
						e.record.commit()
					}
				}
			}
		});
		var grid=this.gridPanel;
      	var selectPersonObj=function(obj){
			  grid.getSelectionModel().getSelected().data['investPersonId'] = obj.perId;
			  grid.getSelectionModel().getSelected().data['investPersonName'] = obj.perName;
			  grid.getStore().groupBy('investPersonId',true)
			  grid.getView().refresh();
		}
		var selectInvestEnterpriseObj=function(obj){
			Ext.getCmp('investId').setValue(obj.id);
			Ext.getCmp('investName').setValue(obj.InvestEnterpriseName);
		}
	/*	this.infoPanel = new Ext.Panel({
			border : false,
			layout : {
				type : 'hbox',
				pack : 'left'
			},
			defaults : {
				margins : '10 10 0 0'
			},
			name : "infoPanel",
			items : []
		});
		// 为infoPanel添加数据
		if (this.isThisSuperviseRecord == null
				|| isThisEarlyPaymentRecord == null
				|| isThisAlterAccrualRecord != null) {
			this.fillDatas(this.projectId, this.businessType);
		}*/

	}

		});