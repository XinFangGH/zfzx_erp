function selectElementCode(funName) {
	var jStore_selectProject = new Ext.data.JsonStore({
				url : __ctxPath + '/contract/findCreditElementProduceHelper.do',//?isGrantedSeeAllProject='+seeAll,
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
						name : 'code'
					},{ name : 'value'
					},{ name : 'depict'
					},{ name : 'operationType'
					},{ name : 'operationTypeKey'
					}],
				remoteSort : true
			});

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
	jStore_selectProject.addListener('load', function() {
				gPanel_selectProject.getSelectionModel().selectFirstRow();
			}, this);
	jStore_selectProject.addListener('loadexception', function(proxy,
					options, response, err) {
				Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
			}, this);
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cModel_product = new Ext.grid.ColumnModel([sm,
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				header : "要素描述",
				width : 190,
				sortable : true,
				dataIndex : 'depict'
			},/* {
				header : "要素编码",
				width : 100,
				sortable : true,
				dataIndex : 'code'
			},*/ {
				header : "要素类型",
				width : 105,
				sortable : true,
				dataIndex : 'operationType'
			}, {
				hidden:true,
				dataIndex : 'operationTypeKey'
			}]);
	var pagingBar_product = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : jStore_selectProject,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
			});

	var gPanel_selectProject = new Ext.grid.GridPanel({
		id : 'gPanel_selectObligationProject',
		store : jStore_selectProject,
		colModel : cModel_product,
		//autoExpandColumn : 'FundName',
		autoWidth:true,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : true,
		bbar : pagingBar_product,
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window_productSelect.destroy();
				myMask_EnterpriseSelect.hide();
			}
		}
	});
	var window_productSelect = new Ext.Window({
				title : '定量要素',
				border : false,
				width : (screen.width - 180) * 0.4,
				height : listWindowHeight - 100,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [gPanel_selectProject],
				layout : 'fit',
				buttonAlign : 'center'
			});
	window_productSelect.show();
	jStore_selectProject.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});

	var callbackFun = function(selected, funName) {
		productObj = {
			 depict: selected.get('depict'),
			code : selected.get('code'),
			operationType : selected.get('operationType'),
			operationTypeKey : selected.get('operationTypeKey')
		}
		funName(productObj);
	}

}