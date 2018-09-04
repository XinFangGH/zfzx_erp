/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectAccountlForm = function(funname){
	var gridPanel = new HT.GridPanel({
					region : 'center',
					// 使用RowActions
					rowActions : true,
					id : 'selectAccountlGrid',
					url : __ctxPath + "/creditFlow/finance/listSlBankAccount.do",
					fields : [{
								name : 'bankAccountId',
								type : 'int'
							}, 'openType', 'accountType', 'bankName', 'name',
							'account', 'rawMoney', 'finalMoney', 'recordTimeString'],
					columns : [{
								header : 'bankAccountId',
								dataIndex : 'bankAccountId',
								hidden : true
							}, {
								header : '开户银行',
								dataIndex : 'bankName'
								
							}, {
								header : '账号',
								dataIndex : 'account'
							
							}, {
								header : '开户名称',
								dataIndex : 'name'
							}, {
									header : '账户类型',
								dataIndex : 'accountType',
								renderer : function(v) {
									switch (v) {
										case 0 :
											return '个人储蓄户';
											break;
										case 1 :
											return '基本户';
											break;
										case 2 :
											return '一般户';
											break;

									}
								}
							}, {
								header : '开户类型',
								dataIndex : 'openType',
								renderer : function(v) {
									switch (v) {
										case 0 :
											return '个人';
											break;
										case 1 :
											return '公司';
											break;

									}
								}
								
							}, {
								header : '期初金额(元)',
								dataIndex : 'rawMoney',
								sortable:true
							}, {
								header : '期末余额(元)',
								dataIndex : 'finalMoney',
								sortable:true
							}, {
								header : '开户时间',
								dataIndex : 'recordTimeString',
								sortable:true
//								renderer : function(v) {
//								   v.substring(0,5);
//								}
							
							}, new Ext.ux.grid.RowActions({
										header : '管理',
										width : 100,
										hidden:true,
										actions : [{
													iconCls : 'btn-del',
													qtip : '删除',
													style : 'margin:0 3px 0 3px'
												}, {
													iconCls : 'btn-edit',
													qtip : '编辑',
													style : 'margin:0 3px 0 3px'
												}],
										listeners : {
//											scope : this,
//											'action' : this.onRowAction
										}
									})],
									listeners:{
									     'rowdblclick' : function(grid,rowIndex,e) {
		
		                                                grid.getSelectionModel().each(function(rec) {
				                                       	funname(rec.data.account,rec.data.bankName,rec.data.name,rec.data.bankAccountId,rec.data.finalMoney);
			                      	                    });
		                                         selectAccountlWindow.destroy();
			}
		}
						// end of columns
				});
	var selectAccountlWindow = new Ext.Window({
		width: (screen.width-180)*0.7,
		title : '我方账户列表',
		height : 350 ,//高度自by chencc
		//autoHeight :true,//高度自动 by chencc
		collapsible : true,
		layout : 'fit',
		buttonAlign : 'center',
		modal : true,
		resizable : false,
		items : [gridPanel]
	});
selectAccountlWindow.show();
}