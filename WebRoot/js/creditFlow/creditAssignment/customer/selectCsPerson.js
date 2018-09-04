function selectInvesPerson (funName) {
	var jStore_enterpriseForSelect = new Ext.data.JsonStore({
					url : __ctxPath
						+ '/creditFlow/creditAssignment/customer/getPersonAndBankCsInvestmentperson.do',
				totalProperty : 'totalCounts',
				root : 'result',
				fields : [{
							name : 'investId'
						}, {
							name : 'investName'
						}, {
							name : 'cardnumber'
						}, {
							name : 'postaddress'
						}, {
							name : 'totalMoney'
						}],
				// 服务器端排序 by chencc
				// sortInfo : {field: "enterprisename", direction: "DESC"}
				remoteSort : true
			});
	var anchor = '100%';
	var pageSize = 15;
	var listWindowWidth = 550;
	var listWindowHeight = 465;
	var detailWindowWidth = 480;
	var detailWindowHeight = 370;
	var defaultLabelWidth = 100;// 默认标签的宽度
	var defaultTextfieldWidth = 135;// 默认文本输入域宽度
	var enterpriseJsonObj;
	jStore_enterpriseForSelect.addListener('load', function() {
				gPanel_enterpriseSelect.getSelectionModel().selectFirstRow();
			}, this);
	jStore_enterpriseForSelect.addListener('loadexception', function(proxy,
					options, response, err) {
				Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
			}, this);
	//var sm = new Ext.grid.CheckboxSelectionModel();
	var cModel_enterprise = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				id : 'enterpriseName',
				header : "investId",
				width : 60,
				sortable : true,
				dataIndex : 'investId',
				hidden : true
			}, {
				header : "投资客户姓名",
				width : 110,
				sortable : true,
				dataIndex : 'investName'
			}, {
				header : "身份证号",
				width : 210,
				sortable : true,
				dataIndex : 'cardnumber'
			}, {
				header : "通讯地址",
				width : 300,
				sortable : true,
				dataIndex : 'postaddress'
			}, {
				header : "可用金额",
				width : 180,
				sortable : true,
				dataIndex : 'totalMoney'
			}]);
	var pagingBar_Enterprise = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : jStore_enterpriseForSelect,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
	});

	var gPanel_enterpriseSelect = new Ext.grid.GridPanel({
		id : 'gPanel_enterpriseSelect',
		store : jStore_enterpriseForSelect,
		colModel : cModel_enterprise,
		autoExpandColumn : 'enterpriseName',
		//selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : true,
		bbar : pagingBar_Enterprise,
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window_EnterpriseForSelect.destroy();
				myMask_EnterpriseSelect.hide();
			}
		}
	});
	var window_EnterpriseForSelect = new Ext.Window({
				title : '债权人列表',
				border : false,
				width : (screen.width - 180) * 0.5,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [gPanel_enterpriseSelect],
				layout : 'fit',
				buttonAlign : 'center'
			});
	window_EnterpriseForSelect.show();
	jStore_enterpriseForSelect.load({
				params : {
					start : 0,
					limit : pageSize
				}
	});


	var callbackFun = function(selected, funName) {
		enterpriseJsonObj = {
			investId : selected.get('investId'),
			investName : selected.get('investName')/*,
			htmlT:"投资人："+selected.get('investName')+"    身份证号："+selected.get('cardnumber')+"    账户金额："+selected.get('totalMoney')+"元"
			   +"    通讯地址："+selected.get('totalMoney')*/
			
		}
		funName(enterpriseJsonObj);
	}
}