/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectOpAccountlForm = function(funname){
	var gridPanel = new HT.GridPanel({
					region : 'center',
					// 使用RowActions
					rowActions : false,
					border : false,
					id : 'selectOpAccountlGrid',
					url : __ctxPath + "/creditFlow/finance/listbycheckboxPlOpbankAccount.do",
					fields : [{
									name : 'opAccountId',
									type : 'int'
								}
								,'opAccount'
								,'opOpenName'
								,'opBankName'
								,'userNumber'
								],
					columns : [{
								header : 'opAccountId',
								dataIndex : 'opAccountId',
								hidden : true
							}, {
								header : '对方开户银行',
								dataIndex : 'opBankName',
								sortable:true
								
							}, {
								header : '对方账户名称',
								dataIndex : 'opOpenName',
								width :50
							
							}, {
								header : '对方账号',
								dataIndex : 'opAccount',
								width :50
							}, {
								header : '使用次数',
								dataIndex : 'userNumber',
								width :40
								
							}],
									listeners:{
									     'rowdblclick' : function(grid,rowIndex,e) {
		
		                                                grid.getSelectionModel().each(function(rec) {
				                                       	funname(rec.data.opAccount,rec.data.opBankName,rec.data.opOpenName);
			                      	                    });
		                                         selectAccountlWindow.destroy();
			}
		}
						// end of columns
				});
	var selectAccountlWindow = new Ext.Window({
		width: (screen.width-180)*0.7,
		title : '对方账户列表',
		height : 600 ,//高度自by chencc
		//autoHeight :true,//高度自动 by chencc
		collapsible : true,
		layout : 'fit',
		buttonAlign : 'center',
		border : false,
		modal : true,
		resizable : false,
		items : [gridPanel]
	});
selectAccountlWindow.show();
}