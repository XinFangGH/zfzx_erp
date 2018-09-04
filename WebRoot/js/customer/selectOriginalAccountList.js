/**
 * @author
 * @class InvestPersonView
 * @extends Ext.Panel
 * @description [InvestPerson]管理
 * @company 智维软件
 * @createtime:
 */
selectOriginalAccountList = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (_cfg.obj != null && _cfg.obj != 'underfined') {
			this.obj = _cfg.obj;
		}
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		selectOriginalAccountList.superclass.constructor.call(this, {
					id : 'OriginalAccountView',
					title : '打款账户信息',
					region : 'center',
					layout : 'border',
					modal : true,
					width : 600,
					height : 400,
					items : [this.searchPanel,this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var fp = this.obj.getOriginalContainer();
		var object = this;
		var url="";
		if(1==CompanyId){
			url = __ctxPath+ "/creditFlow/finance/listSlBankAccount.do?isAll=true";
		}else{
			url = __ctxPath+ "/creditFlow/finance/listSlBankAccount.do?isAll=false";
		}
		
		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
			height : 50,
			region : "north",
			frame:true,
			bodyStyle : 'padding:7px 0px 7px 10px',
			border : false,
			width : '100%',
			monitorValid : true,
			layout : 'column',
			columnWidth:1,
			defaults : {
				layout : 'form',
				border : false,
				bodyStyle : 'padding:5px 0px 0px 20px'
			},
			items : [{
				columnWidth :0.3,
				labelWidth : 40,
				items : [{
					xtype : 'textfield',
					fieldLabel : '开户名',
					name : 'Q_name_S_LK',
					anchor : '100%'
				}]
			},{
				columnWidth : 0.2,
				items : [{
					id : 'searchButton',
					xtype : 'button',
					text : '查询',
					tooltip : '根据查询条件过滤',
					iconCls : 'btn-search',
					width : 60,
					formBind : true,
					scope : this,
					handler : function() {
						this.searchByCondition();
					}
				}]
			}, {
				columnWidth : 0.2,
				items : [{
					xtype : 'button',
					text : '重置',
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				}]
			}]
		});
		
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			// 使用RowActions
			id : 'OriginalAccountGrid',
			url : url,
			fields : [{
						name : 'bankAccountId',
						type : 'int'
					}, 'name', 'account', 'bankName', 'bankOutletsName',"openBankId"],
			columns : [{
						header : 'bankAccountId',
						dataIndex : 'bankAccountId',
						hidden : true
					}, {
						header : '开户名称',
						width : 120,
						dataIndex : 'name'
					}, {
						header : '开户银行',
						dataIndex : 'bankName'
					}, {
						header : '账号',
						width : 120,
						dataIndex : 'account',
						renderer : function(v){
							var card = v;
							var len = v.length;
							var reg = /\s{1,}/g;
							var card_ = "";
							//去除空格 
				        	card = card.replace(reg,"");
				        	for(var i=0;i<len;i++){
				        		if(i==3||i==7||i==11||i==15||i==19||i==23||i==27||i==31||i==34||i==37){
				        			card_ = card_ + card.charAt(i) + " ";
				        		}else{
				        			card_ = card_ + card.charAt(i);
				        		}
				        	}
				        	return card_
						}
					
					}],
			listeners : {
				'rowdblclick' : function(grid, rowIndex, e) {
					var object = grid.ownerCt;
					var data = grid.getStore().getAt(rowIndex).data;
					grid.getSelectionModel().each(function(rec) {
						fp.getCmpByName("plManageMoneyPlanBuyinfo.bankAccountId").setValue(rec.data.bankAccountId);
						fp.getCmpByName("plManageMoneyPlanBuyinfo.pmpName").setValue(rec.data.name);
						fp.getCmpByName("plManageMoneyPlanBuyinfo.pmpAccount").setValue(rec.data.account);
						fp.getCmpByName("plManageMoneyPlanBuyinfo.pmpBankName").setValue(rec.data.bankName);
					})
					Ext.getCmp('OriginalAccountView').close();
				}
			}
		});

	},// end of the initComponents()
	searchByCondition : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	}
});
