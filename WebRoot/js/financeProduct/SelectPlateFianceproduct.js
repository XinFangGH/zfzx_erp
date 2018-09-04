//SelectPlateFianceproduct

var SelectPlateFianceproduct= function(funName) {
	var anchor = '96.5%';
	this.jStorePersonWin = new Ext.data.JsonStore({
        url : __ctxPath + "/financeProduct/getAlllistPlFinanceProduct",
		totalProperty : 'totalProperty',
		root : 'result',
		fields : [{
						name : 'id',
						type : 'int'
					}, 'productName', 'productNumber', 'productDes', 'intestModel',
					'organizationNumber', 'tellPhone', 'fax',
					'registeredMoney', 'buildDate', 'cooperationDate',
					'companyURL', 'companyAddress', 'companyIntro', 'pname',
					'psex', 'pphone', 'pappellation', 'pemail', 'pcardNumber',
					'isUsing', 'isOpenP2P','p2ploginname'],
			remoteSort: true//服务器端排序 by chencc
		});
	
		var cModelPersonWin = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},  {
						header : '机构类型',
						dataIndex : 'type'
					}, {
						header : '机构名称',
						dataIndex : 'name'
					}, {
						header : '组织机构号码',
						dataIndex : 'organizationNumber'
					}, {
						header : '营业执照号码',
						dataIndex : 'licenseNumber'
					}, {
						header : '公司电话',
						dataIndex : 'tellPhone'
					},  {
						header : '联系人',
						dataIndex : 'pname'
					}, {
						header : '联系人电话',
						dataIndex : 'pphone'
					}, {
						header : '邮箱',
						dataIndex : 'pemail'
					}, {
						header : 'p2p账号',
						dataIndex : 'p2ploginname'
					} ]);
		var pagingBar = new Ext.PagingToolbar({
			pageSize : 15,
			store : this.jStorePersonWin,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var personStore=this.jStorePersonWin;
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "正在加载数据中······,请稍候······"
		});
		var button_add = new Ext.Button({});	
		var button_fastadd = new Ext.Button({});	
		var button_update = new Ext.Button({});
		var button_see = new Ext.Button({});
		
		
		
		var gPanelPersonWin = new Ext.grid.GridPanel({
			id : 'gPanelPersonWin',
			store : this.jStorePersonWin,
			colModel : cModelPersonWin,
//			autoExpandColumn : 7,
			//selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : true,
			bbar : pagingBar,
			tbar : [],
			listeners : {																																							
				'rowdblclick' : function(grid, index, e) {
					var selected = grid.getStore().getAt(index) ;
					callbackFun(selected,funName);
					personWin.destroy();
				 }
			}
		});
		
		var personWin = new Ext.Window({
			title : '人员列表',
			border : false,
			width: (screen.width-180)*0.75,
			height : 425,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gPanelPersonWin],
			modal : true,
			buttonAlign : 'center'
		});
		//加载框开始就加载开始  by chencc
		personWin.show();
		this.jStorePersonWin.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		//加载框开始就加载结束  by chencc
		var searchByCondition = function() {
			this.jStorePersonWin.load({
						params : {
							start : 0,
							limit : 15
						}
		});
		}
		
	var callbackFun = function(selected,funName){
		personJsonObj = {
			id : selected.get('id'),
			mateid : selected.get('name'),
			p2pAccount:selected.get('p2ploginname'),
			companyIntro:selected.get('companyIntro')
		}
		
		funName(personJsonObj);
	}
	var formSave = function(formPanelId ,winObj ,storeObj){
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			success : function(form ,action) {
				Ext.ux.Toast.msg('状态', '保存成功!');
					storeObj.reload();
					if(null != winObj){
						winObj.destroy();
					}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整');
			}
		})
	}
}

