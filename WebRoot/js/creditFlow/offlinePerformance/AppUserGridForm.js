/**添加系统用户放大镜效果*/
function selectAppUserGrid (funName) {
	var pageSize = 15;
	var listWindowHeight = 465;
	
	var appUserStore = new Ext.data.JsonStore({
		url : __ctxPath+'/system/findUserListAppUser.do',
		totalProperty : 'totalCounts',
		root : 'result',
		fields : ['userId','fullname','username','mobile','email'],
		remoteSort : true
	});
	
	appUserStore.addListener('load', function() {
		gridPanel.getSelectionModel().selectFirstRow();
	}, this);
	
	appUserStore.addListener('loadexception', function(proxy,options, response, err) {
		Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
	}, this);
	
	var gridPanelModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer({
			header : '序号',
			width : 35
		}), {
			header : "userId",
			width : 60,
			sortable : true,
			dataIndex : 'userId',
			hidden : true
		}, {    
	       header:'账号',
	       dataIndex:'username',	
	       renderer : function(value) {
			    if(value.split("@").length>1){
			       return value.split("@")[0];
			    }
			    return value;
			}
		}, {
	       header:'姓名',
	       dataIndex:'fullname'
		}, {
	       header:'手机号',
	       width : 120,
	       dataIndex:'mobile'
		}, {
	       header:'邮箱',
	       width : 180,
	       dataIndex:'email'
		}
	]);
	var pagingBar = new Ext.PagingToolbar({
		pageSize : pageSize,
		store : appUserStore,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "正在加载数据中······,请稍候······"
	});

	var gridPanel= new Ext.grid.GridPanel({
		store : appUserStore,
		colModel : gridPanelModel,
		stripeRows : true,
		loadMask : true,
//		autoExpandColumn:'mobile',
		bbar : pagingBar,
		listeners : {
			'rowdblclick' : function(grid, rowIndex, e) {
				var selected = grid.getStore().getAt(rowIndex);
				callbackFun(selected, funName);
				window.destroy();
				myMask.hide();
			}
		}
	});
	var window = new Ext.Window({
		 title : '系统用户列表',
		 border : false,
		 width : (screen.width - 180) * 0.5,
		 height : listWindowHeight - 30,
		 collapsible : true,
		 modal : true,
		 resizable:false,
		 constrainHeader : true,
		 items : [gridPanel],
		 layout : 'fit'
	});
	window.show();
	appUserStore.load({
		params : {
			start : 0,
			limit : pageSize
		}
	});

	var callbackFun = function(selected, funName) {
		var appUserJson = {
			userId : selected.get('userId'),
			fullname : selected.get('fullname'),
			mobile : selected.get('mobile'),
			email : selected.get('email')
		}
		funName(appUserJson);
	}
}