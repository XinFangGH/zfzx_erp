//SelectSystemAccountList.js
/**获取系统账户放大镜效果*/
function SelectSystemAccountList (funName,type,rightValue,isShop) {
	var anchor = '100%';
	var pageSize = 15;
	var listWindowWidth = 550;
	var listWindowHeight = 465;
	var detailWindowWidth = 480;
	var detailWindowHeight = 370;
	var defaultLabelWidth = 100;// 默认标签的宽度
	var defaultTextfieldWidth = 135;// 默认文本输入域宽度
	var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
	});
	
	var url=""
	if(type==1){//线下客户
		url = __ctxPath+ "/creditFlow/creditAssignment/bank/systemAccountDownListObSystemAccount.do?isAll="+rightValue+"&isShop="+isShop
	}else if(type==0){//线上客户
		url = __ctxPath+ "/creditFlow/creditAssignment/bank/systemAccountListObSystemAccount.do?accountType="+type
	}

	var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
					return '总计(元)';
		}
		
	gPanel_enterpriseSelect = new HT.GridPanel({
					name : 'gPanel_enterpriseSelect',
					region : 'center',
					notmask : true,
					//bbar : pagingBar_Enterprise,
					plugins : [summary],
					// 不适用RowActions
					rowActions : false,
					url : url,
					fields : [{
								name : 'id',
								type : 'int'
							}, 'accountName', 'accountNumber',
							'investmentPersonId', 'investPersonName','truename','loginname',
							'totalMoney', 'accountStatus', 'totalInvestMoney',
							'currentInvestMoney', 'availableInvestMoney','freezeMoney',
							'unPrincipalRepayment','allInterest','companyId','investPersionType'],

					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : 'investmentPersonId',
								dataIndex : 'investmentPersonId',
								hidden : true
							}, {
								header : '投资客户类型',
								width : 80,
								dataIndex : 'investPersionType',
								renderer : function(value) {
									if (value ==1) {
										return "线下";
									} else if(value ==0){
										return "线上";
									}
								}
								
							}, {
								header : '投资人姓名',
								width : 100,
								summaryType : 'count',
								summaryRenderer : totalMoney,
								dataIndex : 'truename'
							}, {
								header : '投资人账号',
								width : 110,
								dataIndex : 'loginname'
							}, {
								header : '账户余额',
								align : 'right',
								width : 110,
								sortable : true,
								dataIndex : 'totalMoney',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							},{
								header : '可投资额',
								align : 'right',
								width : 110,
								sortable : true,
								dataIndex : 'availableInvestMoney',
								summaryType : 'sum',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}, {
								header : '预冻结金额',
								align : 'right',
								width : 110,
								sortable : true,
								dataIndex : 'freezeMoney',
								renderer : function(value) {
									if (value == "") {
										return "0.00元";
									} else {
										return Ext.util.Format.number(value,
												',000,000,000.00')
												+ "元";
									}
								}
							}],
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window_EnterpriseForSelect.destroy();
				myMask_EnterpriseSelect.hide();
			}
		}
				});
				
		searchPanel = new Ext.form.FormPanel({
			layout : 'hbox',
			region : 'north',
			border : false,
			height : 50,
			anchor : '100%',
			layoutConfig : {
				align : 'middle'
			},
			bodyStyle : 'padding-top:20px',
			items : [{
						columnWidth : .2,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							fieldLabel : '投资人姓名',
							name : 'investPersonName',
							xtype : 'textfield'
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						items : [{
									text : '查询',
									xtype : 'button',
									scope : this,
									style : 'margin-left:20px',
									anchor : "100%",
									iconCls : 'btn-search',
									handler : function(){
										$search({
											searchPanel : this.searchPanel,
											gridPanel : this.gPanel_enterpriseSelect
										});
									}
								}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						items : [{
									text : '重置',
									style : 'margin-left:20px',
									xtype : 'button',
									scope : this,
									anchor : "100%",
									iconCls : 'btn-reset',
									handler : function(){
										this.searchPanel.getForm().reset();
									}
								}]
					}]
	    });
	var window_EnterpriseForSelect = new Ext.Window({
				title : '系统账户明细',
				border : false,
				width : (screen.width - 180) * 0.8,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [searchPanel,gPanel_enterpriseSelect],
				layout : 'border',
				buttonAlign : 'center'
			});
	window_EnterpriseForSelect.show();



	var callbackFun = function(selected, funName) {
		enterpriseJsonObj = {
			accountId : selected.get('id'),
			accountName : selected.get('truename'),
			html:"【<font style='line-height:20px'>账户类型：</font><font style='line-height:20px'>"+(selected.get('investPersionType')==1?"线下客户":"线上客户")+"</font>;   <font style='line-height:20px'>账户名：</font><font style='line-height:20px'>"+selected.get('accountName')+"</font>;   <font style='line-height:20px'>系统账号："+selected.get('accountNumber')+"</font>;   <font style='line-height:20px'>账户余额：</font><font style='line-height:20px'>"+Ext.util.Format.number(selected.get('totalMoney'),',000,000,000.00')+ "元</font>】"
		}
		
		funName(enterpriseJsonObj);
	}
}