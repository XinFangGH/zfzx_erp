var personData;
var jStorePersonWin;
var selectPromotePerson = function(funName) {
	var anchor = '96.5%';
	jStorePersonWin = new Ext.data.JsonStore({
		url : __ctxPath+'/customer/listInvestPerson.do',
		totalProperty : 'totalCounts',
		root : 'result',
		fields : [{
				name : 'perId'
			}, {
				name : 'cardNumber'
			}, {
				name : 'cardType'
			}, {
				name : 'isdelete'
			}, {
				name : 'perName'
			}, {
				name : 'perSex'
			}, {
				name : 'phoneNumber'
			}, {
				name : 'postAddress'
			}, {
				name : 'postCode'
			}, {
				name : 'perBirthday'
			}],
			remoteSort: true//服务器端排序 by chencc
		});
	
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cModelPersonWin = new Ext.grid.ColumnModel([sm,
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "姓名",
					width : 80,
					sortable : true,
					dataIndex : 'perName'
				}, {
					header : "性别",
					width : 60,
					sortable : true,
					renderer : function(value){
							if(value=='312' || value==312){
								return '男';
							} else if(value=='313' || value==313){
								return '女';
							} else{
								return '';
							}
						},
					dataIndex : 'perSex'
				}, {
					header : "出生日期",
					width : 100,
					sortable : true,
					format : 'Y-m-d',
					dataIndex : 'perBirthday',
					renderer : function(value, metadata, record, rowIndex,colIndex) {
						if (value == null || value == '') {
							return '';
						} else {
							var val = Ext.util.Format.date(value, 'Y-m-d');
							return val;
						}
					}
				}, {
					header : "证件类型",
					width : 120,
					sortable : true,
					renderer : function(value){
							if(value=='309' || value==309){
								return '身份证';
							} else if(value=='310' || value==310){
								return '军官证';
							} else if(value=='311' || value==311){
								return '护照';
							} else{
								return '';
							}
						},
					dataIndex : 'cardType'
				}, {
					header : "证件号码",
					width : 120,
					sortable : true,
					dataIndex : 'cardNumber'
				}, {
					header : "手机号码",
					width : 120,
					sortable : true,
					dataIndex : 'phoneNumber'

				},{
					header : "通信地址",
					width : 200,
					sortable : true,
					dataIndex : 'postAddress'	
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
			title : '投资人员列表',
			border : false,
			//width: 300,
			autoWidth : true,
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
						var personids = Array();
						var personnames = Array();
						for (var i = 0; i < selecteds.length; i++) {
							personids.push(selecteds[i].data.perId);
							personnames.push(selecteds[i].data.perName);
						}
						funName(personids,personnames);
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

