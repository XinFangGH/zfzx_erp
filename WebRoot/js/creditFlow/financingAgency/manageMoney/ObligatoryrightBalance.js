/**
 * @author
 * @class ObligatoryrightBalance
 * @extends Ext.Panel
 * @description [ObligatoryrightBalance]管理
 * @company 智维软件
 * @createtime:
 */
ObligatoryrightBalance = Ext.extend(Ext.Panel, {
	tempTotalMoney:0,
	account:'',
	seachDate:'',
	gridSize:0,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ObligatoryrightBalance.superclass.constructor.call(this, {
				id : 'ObligatoryrightBalance_'+this.keystr,
				title : '债权余额查询',
				region : 'center',
				layout : 'border',
				iconCls : 'btn-tree-team30',
	        	width : 1000,
				items : [this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
			layout : 'form',
			border : false,
			region : 'north',
			height : 65,
			anchor : '70%',
			items : [{
				border : false,
				layout : 'column',
				style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label',
					anchor : '100%'
				},
				items : [{
					columnWidth : .3,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					items : [  {
						fieldLabel : '原始债权人',
						xtype : 'combo',
						editable : false,
						allowBlank : false,
						triggerClass : 'x-form-search-trigger',
						name : 'account',
						onTriggerClick : function() {
							var op = this.ownerCt.ownerCt;
							var EnterpriseNameStockUpdateNew = function(obj) {
								if (null != obj.p2pAccount && "" != obj.p2pAccount){
									op.getCmpByName('account').setValue(obj.p2pAccount);
								}else{
									op.getCmpByName('account').setValue();
								}
							}
							selectCsCooperationPerson(EnterpriseNameStockUpdateNew,'lenders');//个人债权客户
						}
					}]
				},{
					columnWidth : .25,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					items : [{
						fieldLabel : '查询日期',
						name : 'seachDate',
						xtype : 'datefield',
						allowBlank : false,
						format : "Y-m-d",
						anchor : '95%'
//						maxValue : new Date()
					}]
				},{
					columnWidth : .07,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					style : 'padding-left:10px;',
					items : [{
						text : '查询',
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}]
				},{
					columnWidth : .3,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					style : 'padding-left:10px;padding-bottom:20px;',
					items : [{
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset,
						anchor : '15%'
					}]
				},{
					columnWidth : .3,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					items : [{
						fieldLabel : '债权总金额',
						name : 'totalMoney',
						xtype : 'numberfield',
						readOnly:true,
						disabled:true,
						anchor : '91%',
						listeners : {
							scope : this,
							change : function(nf) {
								nf.setValue(this.tempTotalMoney);
								var bidMoney=this.getCmpByName('bidMoney');
								bidMoney.fireEvent('change',bidMoney);
							}
						}
					}]
				},{
					columnWidth : .25,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					items : [{
						fieldLabel : '招标及预售金额',
						name : 'bidMoney',
						disabled:true,
						xtype : 'numberfield',
						readOnly:true,
						anchor : '95%',
						listeners : {
							scope : this,
							change : function(nf) {
								var tempAccount=this.account;
								var tempTotal=this.getCmpByName('totalMoney');
								var tempBalance=this.getCmpByName('balanceMoney');
								var keystr=this.keystr;
								Ext.Ajax.request({
									url : __ctxPath+ '/creditFlow/financingAgency/findBidMoneyByAccountPlManageMoneyPlan.do',
									method : 'post',
									params : {
										account:tempAccount,
										keystr:keystr
									},
									success : function(response,request) {
										var obj = Ext.decode(response.responseText);
										var money = obj.money;
										nf.setValue(money);
										tempBalance.setValue(tempTotal.getValue()-money);
									}
								});
							}
						}
					}]
				},{
					columnWidth : .3,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 120,
					items : [{
						fieldLabel : '剩余债权金额',
						name : 'balanceMoney',
						disabled:true,
						xtype : 'numberfield',
						readOnly:true,
						anchor : '91%'
					}]
				}]
			}]
		});
	    var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			isautoLoad:false,
			plugins : [summary],
			baseParams:{
				account:this.account,
				seachDate:this.seachDate
			},
			url : __ctxPath+ "/creditFlow/financingAgency/balanceListPlMmObligatoryRightChildren.do?childType="+this.keystr,
			fields : [{
						name : 'childrenorId',
						type : 'int'
					  }, 'parentOrBidId', 'parentOrBidName', 'childrenorName','startDate', 'intentDate'
					   , 'orlimit', 'principalMoney','dayRate', 'availableMoney', 'surplusValueMoney'],
			columns : [{
					header : 'childrenorId',
					align:'center',
					dataIndex : 'childrenorId',
					hidden : true
				}, {
					header : '债权名称',
					dataIndex : 'parentOrBidName'
				}, {
					header : '子债权名称',
					summaryRenderer : totalMoney,
					dataIndex : 'childrenorName'
				}, {
					header : '债权起日',
					align:'center',
					dataIndex : 'startDate'
				}, {
					header : '债权止日',
					align:'center',
					dataIndex : 'intentDate'
				}, {
					header : '期限',
					align:'center',
					dataIndex : 'orlimit'
				}, {
					header : '本金（元）',
					 summaryType : 'sum',
					 align:'right',
					dataIndex : 'principalMoney'
				}, {
					header : '日化利率',
					dataIndex : 'dayRate',
					align : 'right',
					renderer:function(v){
						return v+"%";
					}
				}, {
					header : '可转让金额',
					dataIndex : 'availableMoney',
					scope:this,
					renderer : function(value, metaData, record, rowIndex,colIndex, store){
						this.tempTotalMoney=this.tempTotalMoney+value;
						var totalMoney=this.getCmpByName('totalMoney');
						this.gridSize++;
						if(store.data.items.length==this.gridSize){
							totalMoney.fireEvent('change',totalMoney);
						}
						return value;
					}
				}, {
					header : '债权剩余价值',
					dataIndex : 'surplusValueMoney'
				}]
		});
	},
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		this.account=this.getCmpByName('account').getValue();
		this.seachDate=this.getCmpByName('seachDate').getValue();
		if(this.account && this.seachDate){
			this.gridSize=0;
			this.tempTotalMoney=0;
			var totalMoney=this.getCmpByName('totalMoney');
			totalMoney.fireEvent('change',totalMoney);
			$search({
				searchPanel : this.searchPanel,
				gridPanel : this.gridPanel
			});
		}else{
			var msg="";
			if(""==this.account){
				mag="请选择原始债权人";
			}else if(""==this.seachDate){
				mag="请填写查询日期";
			}
			Ext.ux.Toast.msg('操作信息',mag);
		}
	}
});