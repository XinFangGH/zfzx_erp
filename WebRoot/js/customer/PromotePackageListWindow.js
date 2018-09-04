var personData;
var jStorePersonWin;
var selectPromotePackage = function(projectId,funName) {
	var anchor = '96.5%';
	jStorePersonWin = new Ext.data.JsonStore({
		url : __ctxPath + '/creditFlow/common/getFileListFileForm.do?mark=promote_package.'+ projectId,
		totalProperty : 'totalCounts',
		root : 'result',
		fields : [{
				name : 'fileid'
			}, {
				name : 'filename'
			}],
			remoteSort: true//服务器端排序 by chencc
		});
	
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect :false});
		var cModelPersonWin = new Ext.grid.ColumnModel([sm,
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "推介资料包",
					width : 180,
					sortable : true,
					dataIndex : 'filename'
				}]);
		/*var pagingBar = new Ext.PagingToolbar({
			pageSize : 15,
			store : jStorePersonWin,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});*/
		var personStore=jStorePersonWin;
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "正在加载数据中······,请稍候······"
		});
		var gInvestPersonWin = new Ext.grid.GridPanel({
			id : 'gInvestPersonWin',
			store : jStorePersonWin,
			colModel : cModelPersonWin,
			height : 200,
			selModel : sm,
			stripeRows : true,
			loadMask : true
			//bbar : pagingBar,
			//tbar : [{text : '确定',handler : callbackFun,iconCls : 'btn-add'}]
			//tbar : [button_add,'-',button_update,'-',button_see,'-' ,new Ext.form.Label({text : '姓名：'}),new Ext.form.TextField({id:'personNameRefer',width:80}), /*new Ext.form.Label({text : '性别：'}),{id:'personSexRefer',width:80,xtype : 'csRemoteCombo',dicId : sexDicId},*/{text:'查找',iconCls : 'btn-search'}],
			/*listeners : {																																							
				'rowdblclick' : function(grid, index, e) {
					var selected = grid.getStore().getAt(index) ;
					callbackFun(selected,funName);
					personWin.destroy();
				}
			}*/
		});
		var personWin = new Ext.Window({
			title : '推介资料包列表',
			border : false,
			width: 300,
			autoHeight : true,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gInvestPersonWin],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
				text : '确定',
				//iconCls : 'btn-add',
				handler : function(){
					var selecteds = gInvestPersonWin.getSelectionModel().getSelections();
					if (selecteds.length > 0) {
						var fileids = Array();
						var filenames = Array();
						for (var i = 0; i < selecteds.length; i++) {
							fileids.push(selecteds[i].data.fileid);
							filenames.push(selecteds[i].data.filename);
						}
						funName(fileids,filenames);
					}
					personWin.close();
					
				}
			}]
		});
		//加载框开始就加载开始  by chencc
		personWin.show();
		jStorePersonWin.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		//加载框开始就加载结束  by chencc
		var searchByCondition = function() {
			jStorePersonWin.load({
						params : {
							start : 0,
							limit : 15
						}
		});
		}
		
		var callbackFun = function(){
			alert(1);
		/*personJsonObj = {
					perId : selected.get('perId'),
					cardNumber : selected.get('cardNumber'),
					cardType : selected.get('cardType'),
					isdelete : selected.get('isdelete'),
					perName : selected.get('perName'),
					perSex : selected.get('perSex'),
					phoneNumber : selected.get('phoneNumber'),
					postAddress : selected.get('postAddress'),
					postCode : selected.get('postCode'),
					perBirthday : selected.get('perBirthday')
				}
		funName(personJsonObj);*/
	}
}

