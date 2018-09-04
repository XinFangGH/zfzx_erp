function selectProject(funName) {
	var seeAll =true;//var seeAll=isGranted('_seeAll_qykh');//进行授权
	var jStore_selectProject = new Ext.data.JsonStore({
				url : __ctxPath+'/project/LoanIngProjectListSlSmallloanProject.do',//?isGrantedSeeAllProject='+seeAll,
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [{
							name : 'projectId'
						}, {
							name : 'projectName'
						}, {
							name : 'projectNumber'
						}, {
							name : 'projectMoney'
						}, {
							name : 'intentDate'
						},{
							name:'companyId'
						},{
							name:'businessType'
						},{
							name:'accrual'
						},{
							name:'businessType'
						},{
							name:'accrual'
						},{
							name:'payintentPeriod'
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
				header : "项目名称",
				width : 190,
				sortable : true,
				dataIndex : 'projectName'
			}, {
				header : "项目编号",
				width : 100,
				sortable : true,
				dataIndex : 'projectNumber'
			}, {
				header : "借款金额",
				width : 105,
				sortable : true,
				dataIndex : 'projectMoney',
				renderer : seeRegistermoney
			}, {
				header : "结束日",
				width : 105,
				sortable : true,
				dataIndex : 'intentDate'
			}, {
				header : "借款期限（月）",
				width : 105,
				sortable : true,
				dataIndex : 'payintentPeriod'
			},{
				header : "借款利率（%/年）",
				width : 105,
				sortable : true,
				dataIndex : 'accrual'
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
				title : '产品列表',
				border : false,
				width : (screen.width - 180) * 0.7,
				height : listWindowHeight - 30,
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
			projectId : selected.get('projectId'),
			accrual : selected.get('accrual'),
			projectName : selected.get('projectName'),
			projectNumber : selected.get('projectNumber'),
			projectMoney : selected.get('projectMoney'),
			companyId : selected.get('companyId'),
			intentDate : selected.get('intentDate'),
			businessType:selected.get('businessType'),
			payintentPeriod:selected.get('payintentPeriod')
		}
		funName(productObj);
	}

}