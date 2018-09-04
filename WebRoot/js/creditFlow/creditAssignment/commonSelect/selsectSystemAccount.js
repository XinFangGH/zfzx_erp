//selsectSystemAccount

function selsectSystemAccount(funName) {
	var seeAll =true;//var seeAll=isGranted('_seeAll_qykh');//进行授权
	var jStore_selectSystemAccount = new Ext.data.JsonStore({
				url : __ctxPath+'/creditFlow/creditAssignment/bank/systemAccountListObSystemAccount.do',//?isGrantedSeeAllProject='+seeAll,
				totalProperty : 'totalCounts',
				root : 'result',
				fields : [{
							name : 'id'
						}, {
							name : 'accountName'
						}, {
							name : 'accountNumber'
						}, {
							name : 'investmentPersonId'
						}, {
							name : 'investPersonName'
						},{
							name:'totalMoney'
						},{
							name:'accountStatus'
						},{
							name:'totalInvestMoney'
						},{
							name:'currentInvestMoney'
						},{
							name:'availableInvestMoney'
						},{
							name:'freezeMoney'
						}],
				// 服务器端排序 by chencc
				// sortInfo : {field: "enterprisename", direction: "DESC"}
				remoteSort : true
			});
	var seeRegistermoney = function(val) {
		if (val != "0" || val != null) {
			return val + '元';
		} else {
			return '';
		}
	}

	// function selectEnterprise(funName) {
	var anchor = '100%';
	var pageSize = 15;
	var listWindowWidth = 650;
	var listWindowHeight = 465;
	var detailWindowWidth = 580;
	var detailWindowHeight = 370;
	var defaultLabelWidth = 110;// 默认标签的宽度
	var defaultTextfieldWidth = 135;// 默认文本输入域宽度
	var productObj;
	jStore_selectSystemAccount.addListener('load', function() {
				gPanel_selectSystemAccount.getSelectionModel().selectFirstRow();
			}, this);
	jStore_selectSystemAccount.addListener('loadexception', function(proxy,
					options, response, err) {
				Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
			}, this);
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cModel_product = new Ext.grid.ColumnModel([sm,
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				header : "投资人",
				width : 190,
				sortable : true,
				dataIndex : 'investPersonName'
			}, {
				header : "平台账号",
				width : 100,
				sortable : true,
				dataIndex : 'accountNumber'
			}, {
				header : "账户余额",
				width : 105,
				sortable : true,
				dataIndex : 'totalMoney',
				renderer : seeRegistermoney
			},{
				header : "可投资金额",
				width : 105,
				sortable : true,
				dataIndex : 'availableInvestMoney',
				renderer : seeRegistermoney
			}, {
				header : "预冻结投资金额",
				width : 105,
				sortable : true,
				dataIndex : 'freezeMoney',
				renderer : seeRegistermoney
			}, {
				header : "已投资投资额",
				width : 105,
				sortable : true,
				dataIndex : 'currentInvestMoney',
				renderer : seeRegistermoney
			}, {
				header : "累计投资额",
				width : 105,
				sortable : true,
				dataIndex : 'totalInvestMoney',
				renderer : seeRegistermoney
			}]);
	var pagingBar_product = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : jStore_selectSystemAccount,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	/*var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
			});*/

	var gPanel_selectSystemAccount = new Ext.grid.GridPanel({
		id : 'gPanel_selectSystemAccount',
		store : jStore_selectSystemAccount,
		colModel : cModel_product,
		//autoExpandColumn : 'FundName',
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : true,
		bbar : pagingBar_product,
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window_productSelect.destroy();
				/*myMask_EnterpriseSelect.destroy();*/
			}
		}
	});
	var window_productSelect = new Ext.Window({
				title : '投资人系统账户列表',
				border : false,
				width : (screen.width - 180) * 0.7,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [gPanel_selectSystemAccount],
				layout : 'fit',
				buttonAlign : 'center'
			});
	window_productSelect.show();
	jStore_selectSystemAccount.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});

	var callbackFun = function(selected, funName) {
		productObj = {
			accountId : selected.get('id'),
			totalMoney : selected.get('totalMoney'),
			investPersonId : selected.get('investmentPersonId'),
			investPersonName : selected.get('investPersonName'),
			availableInvestMoney : selected.get('availableInvestMoney')
			
		}
		funName(productObj);
	}

}