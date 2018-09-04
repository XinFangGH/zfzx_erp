//SelectBankList
function SelectBankList(funName){
	var jStore = new Ext.data.JsonStore({
				url : __ctxPath+ '/creditFlow/common/getBankListQueryCsBank.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [{
							name : 'bankid'
						}, {
							name : 'bankname'
						}, {
							name : 'remarks'
						}, {
							name : 'typeKey'
						}, {
							name : 'bankCodeId'
						}, {
							name : 'logoURL'
						}],
				// 服务器端排序 by chencc
				// sortInfo : {field: "enterprisename", direction: "DESC"}
				remoteSort : true
	});
	var pageSize = 15;
	var listWindowHeight = 465;
	jStore.addListener('load', 
		function() {
			gPanel_ProductSelect.getSelectionModel().selectFirstRow();
		}, this
	);
	jStore.addListener('loadexception', 
		function(proxy,options, response, err) {
			Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
		}, this
	);
	var cModel= new Ext.grid.ColumnModel([
			{
				id : 'bankname',
				header : "银行名称",
				width : 300,
				sortable : true,
				dataIndex : 'bankname'
			}, {
				header : "银行编码",
				width : 200,
				sortable : true,
				dataIndex : 'typeKey'
			}, {
				header : "银行描述",
				width : 400,
				sortable : true,
				dataIndex : 'remarks'
			}]);
	var pagingBar = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : jStore,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
	});
	var myMask= new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
	});
	
	jStore.load({
		params : {
			start : 0,
			limit : pageSize
		}
	});
	
	var gPanel_ProductSelect = new Ext.grid.GridPanel({
		id : 'gPanel_BanklistSelect',
		store : jStore,
		colModel : cModel,
		stripeRows : true,
		loadMask : true,
		height : 355,
		bbar : pagingBar,
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window_ProductForSelect.destroy();
				myMask.hide();
			}
		}
	});

	var window_ProductForSelect = new Ext.Window({
				title : '银行列表',
				border : false,
				width : (screen.width - 180) * 0.7,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				layout : 'fit',
				items:[
					gPanel_ProductSelect
				],
				buttonAlign : 'center'
	});
	window_ProductForSelect.show();
	
	

	var callbackFun = function(selected, funName) {
		
		productJsonObj = {
			bankId: selected.get('bankid'),
			bankname : selected.get('bankname'),
			typeKey : selected.get('typeKey')
		}
		funName(productJsonObj);
	}
}
