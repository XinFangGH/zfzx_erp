Ext.ns('SharedPhoneBookView');
SharedPhoneBookView=Ext.extend(Ext.Panel,{
	searchPanel:null,
	gridPanel:null,
	store:null,
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    SharedPhoneBookView.superclass.constructor.call(this,{
	    	id : 'SharedPhoneBookView',
			iconCls : "menu-phonebook-shared",
			title : '共享联系人列表',
			layout:'border',
			region:'center',
			autoScroll : true,
			items:[this.searchPanel,this.gridPanel]
	    });
    },
    initUI:function(){
    	this.searchPanel=new Ext.FormPanel({
			id : 'PhoneSearchForm',
			region:'north',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '姓名'
					}, {
						xtype : 'textfield',
						name : 'fullname'
					}, {
						text : '共享人'
					}, {
						xtype : 'textfield',
						name : 'sharedUser'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : this.search.createCallback(this)
					}]
		});
    	
    	this.store=new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/communicate/sharePhoneBook.do'
			}),
			reader : new Ext.data.JsonReader({
						root : 'result',
					totalProperty : 'totalCounts',
					id : 'id',
					fields : [{
								name : 'phoneId',
								type : 'int'
							}
			
							, 'fullname', 'title', 'birthday',
							'nickName', 'duty', 'spouse', 'childs',
							'companyName', 'companyAddress',
							'companyPhone', 'companyFax',
							'homeAddress', 'homeZip', 'mobile',
							'phone', 'email', 'qqNumber', 'msn',
							'note', {
								name : 'sharefullname',
								mapping : 'appUser.fullname'
							}, 'groupId', 'isShared']
						}),
				remoteSort : true
			});
		this.store.setDefaultSort('phoneId', 'desc');
    	
		var cm = new Ext.grid.ColumnModel({
			columns : [new Ext.grid.RowNumberer(), {
						header : 'phoneId',
						dataIndex : 'phoneId',
						hidden : true
					}, {
						header : '名字',
						dataIndex : 'fullname'
					}, {
						header : '职位',
						dataIndex : 'duty'
					}, {
						header : '电话',
						dataIndex : 'mobile'
					}, {
						header : '共享人',
						dataIndex : 'sharefullname'
					}],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});

		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
    	this.gridPanel=new Ext.grid.GridPanel({
			id : 'PhoneBookGrid',
			tbar:new Ext.Toolbar({height:27}),
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			region : 'center',
			cm : cm,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new HT.PagingBar({store : this.store})
		});
    	
    	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
						var sharedPhoneBookWin = Ext
								.getCmp('SharedPhoneBookWin');
						if (sharedPhoneBookWin == null) {
							SharedPhoneBookView.read(rec.data.phoneId);
						}
					});
		});
    },
    search:function(self){
    	var searchPanel = self.searchPanel;
    	var gridPanel=self.gridPanel;
    	if (searchPanel.getForm().isValid()) {
			$search({
					searchPanel :searchPanel,
					gridPanel : gridPanel
				});
		}
    }
});

SharedPhoneBookView.read = function(id) {
   new SharedPhoneBookWin({phoneId:id}).show();
};
