function selectProduct(funName){
	var jStore = new Ext.data.JsonStore({
				url : __ctxPath + "/creditFlow/creditAssignment/project/getListInfoObObligationProject.do",
				totalProperty : 'totalCounts',
				root : 'result',
				fields : [{name : 'id',type : 'int'}, {name : 'projectId'}, {name : 'projectName'},  {name : 'projectNumber'},
						  {name : 'obligationName'}, {name : 'obligationNumber'}, {name : 'projectMoney'}, 
						  {name : 'intentDate'}, {name : 'payintentPeriod'}, {name : 'minInvestMent'},
						  {name : 'accrual'}, {name : 'totalQuotient'}, {name : 'investQuotient'},
						  {name : 'unmappingQuotient'}, {name : 'accrualtype'}, {name : 'mappingMoney'},
						  {name : 'unmappingMoney'}, {name : 'minQuotient'}, {name : 'dayOfEveryPeriod'},
						  {name : 'payaccrualType'}, {name : 'isStartDatePay'}, {name : 'payintentPerioDate'},{name:'isPreposePayAccrual'}
						 ],
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
		/*{
			id : 'projectName',
			header : '项目名称',
			width : 100,
			hidden:this.hiddenInfo,
			dataIndex : 'projectName'
		},{
			header : '项目编号',
			width : 100,
			hidden:this.hiddenInfo,
			dataIndex : 'projectNumber'
		},*/ {
			header : '债权产品编号',
			width : 130,
			dataIndex : 'obligationNumber'
		}, {
			header : '债权产品名称',
			width : 200,
			dataIndex : 'obligationName'
		},{
			header : '债权金额（元）',
			width : 100,
			dataIndex : 'projectMoney',
			renderer : function(value) {
				if (value == "") {
					return "0.00元";
				} else {
					return Ext.util.Format.number(value, ',000,000,000.00')+ "元";
				}
			}
		},{
			header : '债权利率（%）',
			width : 100,
			dataIndex : 'accrual',
			renderer : function(value) {
				if (value == "") {
					return "0.00%";
				} else {
					return Ext.util.Format.number(value, ',000,000,000.00')+ "%";
				}
			}
		}, {
			header : '借款期限',
			width : 80,
			hidden:this.hiddenInfo,
			dataIndex : 'payintentPeriod'
		},{
			header : '最小投资额（元）',
			width : 120,
			dataIndex : 'minInvestMent',
			hidden:this.hiddenInfo,
			renderer : function(value) {
				if (value == "") {
					return "0.00";
				} else {
					return Ext.util.Format.number(value, ',000,000,000.00')	+ "";
				}
			}
		}, {
			header : '自动关闭时间',
			align : 'right',
			width : 110,
			sortable : true,
			hidden:this.hiddenInfo,
			dataIndex : 'intentDate'
		}, {
			header : '已匹配金额（元）',
			width : 120,
			dataIndex : 'mappingMoney',
			hidden:this.hiddenMapping,
			renderer : function(value) {
				if (value == "") {
					return "0.00";
				} else {
					return Ext.util.Format.number(value, ',000,000,000.00')+ "";
				}
			}
		}, {
			header : '未匹配金额（元）',
			width : 120,
			dataIndex : 'unmappingMoney',
			hidden:this.hiddenMapping,
			renderer : function(value) {
				if (value == "") {
					return "0.00";
				} else {
					return Ext.util.Format.number(value, ',000,000,000.00')+ "";
				}
			}
		}
	]);
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
		id : 'gPanel_ProductSelect',
		store : jStore,
		colModel : cModel,
		//autoExpandColumn : 'projectName',
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
	
	var searchPanel=new Ext.FormPanel({
			id:'searchPanel',
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
            bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '项目名称',
						name : 'projectName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     },{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '项目编号',
						name : 'projectNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     },{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '借款期限',
						name : 'payintentPeriod',
					    anchor : "100%",
						xtype : 'textfield'
					}]
		     },{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						id:"search",
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search'
					}]
	     	}]
		});	
	var window_ProductForSelect = new Ext.Window({
				title : '债权产品列表',
				border : false,
				width : (screen.width - 180) * 0.7,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				layout : 'fit',
				items:[
					new Ext.Panel({
						layout : 'form',
						items : [searchPanel,gPanel_ProductSelect]
					})
				],
				buttonAlign : 'center'
	});
	window_ProductForSelect.show();
	
	
	Ext.getCmp('search').on('click', function() {
			$search({
				searchPanel : Ext.getCmp('searchPanel'),
				gridPanel : Ext.getCmp('gPanel_ProductSelect')
			});
	});

	var callbackFun = function(selected, funName) {
		productJsonObj = {
			id : selected.get('id'),
			projectId : selected.get('projectId'),
			projectName : selected.get('projectName'),
			obligationName : selected.get('obligationName'),
			obligationNumber : selected.get('obligationNumber'),
			projectMoney : selected.get('projectMoney'),
			intentDate : selected.get('intentDate'),
			payintentPeriod : selected.get('payintentPeriod'),
			minInvestMent : selected.get('minInvestMent'),
			accrual : selected.get('accrual'),
			totalQuotient : selected.get('totalQuotient'),
			investQuotient : selected.get('investQuotient'),
			unmappingQuotient : selected.get('unmappingQuotient'),
			accrualtype : selected.get('accrualtype'),
			mappingMoney : selected.get('mappingMoney'),
			unmappingMoney : selected.get('unmappingMoney'),
			hangyetypevalue : selected.get('hangyetypevalue'),
			minQuotient : selected.get('minQuotient'),
			dayOfEveryPeriod : selected.get('dayOfEveryPeriod'),
			payaccrualType : selected.get('payaccrualType'),
			isStartDatePay : selected.get('isStartDatePay'),
			payintentPerioDate : selected.get('payintentPerioDate'),
			isPreposePayAccrual:selected.get('isPreposePayAccrual'),
			idDefinition:'addinvestObligation'
		}
		funName(productJsonObj);
	}
}
