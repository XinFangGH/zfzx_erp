/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ThereunderPanelView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ThereunderPanelView.superclass.constructor.call(this, {
			/*title : '个人旗下公司信息',
			width : (screen.width-180)*0.7 - 50,
			height: 430,
			buttonAlign : 'center',
			border : false,
			layout : 'fit',
			modal : true,
			autoScroll : true ,
			constrainHeader : true ,
			collapsible : true, */
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
	
		this.topbar = new Ext.Toolbar( {
			items : [ {
				xtype : 'button',
				text : '增加',
				tooltip : '增加一条新的个人旗下公司信息',
				iconCls : 'addIcon',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : this.createRs
			}, {
				xtype : 'button',
				text : '编辑',
				tooltip : '编辑选中的个人旗下公司信息',
				iconCls : 'updateIcon',
				scope : this,
				hidden : this.isHiddenEditBtn,
				handler : this.editRs
			}, {
				xtype : 'button',
				text : '删除',
				tooltip : '删除选中的企业信息',
				iconCls : 'deleteIcon',
				scope : this,
				hidden : this.isHiddenDelBtn,
				handler : this.removeSelRs
			},{
				xtype : 'button',
				text : '查看',
				tooltip : '查看选中的个人旗下公司信息',
				iconCls : 'seeIcon',
				scope : this,
				hidden : this.isHiddenSeeBtn,
				handler : this.seeRs
			}]
		});
		this.jStore_thereunder = new Ext.data.JsonStore( {
				url : __ctxPath+'/creditFlow/customer/person/queryPersonThereunder.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				autoLoad : true,
				fields : [ {
					name : 'id'
				}, {
					name : 'companyname'
				}, {
					name : 'licensenum'
				}, {
					name : 'value'
				}, {
					name : 'registerdate'  
				}, {
					name : 'registercapital'  
				}, {
					name : 'address'  
				}, {
					name : 'lnpname'  
				}, {
					name : 'phone'
				},{
					name : 'shortname'
				},{
					name : 'name'
				},{
					name : 'personid'
				},{
					name : 'enterprisename'
				},{
					name : 'relateValue'
				}],
				baseParams :{
					pid : this.personId,
				    projectId : this.projectId,
				    operationType : this.operationType
				}
			});
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			store : this.jStore_thereunder,
			autoHeight : true,
			showPaging : false,
			columns : [{
				header : "公司名称",
				width : 140,
				sortable : true,
				dataIndex : 'enterprisename'
			}, {
				header : "营业执照号码",
				width : 100,
				sortable : true,
				dataIndex : 'licensenum'
			}, {
				header : "关系",
				width : 90,
				sortable : true,
				dataIndex : 'relateValue'
			},{
				header : "联系人",
				width : 90,
				sortable : true,
				dataIndex : 'name'
			},{
				header : "联系人电话",
				width : 100,
				sortable : true,
				dataIndex : 'phone'
			}, {
				header : "注册时间",
				width : 90,
				sortable : true,
				dataIndex : 'registerdate'					
			}, {
				id : 'registercapital',
				header : "注册资本",
				width : 80,
				sortable : true,
				dataIndex : 'registercapital'					
			}]
		//end of columns
		});

	},// end of the initComponents()
	
	//创建记录
	createRs : function() {
		var gridPanel=this.gridPanel
		Ext.Ajax.request({
			url : __ctxPath
					+ '/creditFlow/customer/person/getPersonIdPersonThereunder.do',
			method : 'POST',
			params : {
				projectId : this.projectId,
				operationType : this.operationType
			},
			success : function(response, request) {

				var object = Ext.util.JSON.decode(response.responseText)
				var personId=object.data.oppositeID
				new ThereunderForm({personId:personId,gridPanel : gridPanel}).show();
			}
		});
		
	},

	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/creditFlow/customer/person/deletePersonThereunder.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new ThereunderForm( {
				id : record.data.id,personId : record.data.personid,gridPanel : this.gridPanel
			}).show();
		}	
	},
	seeRs : function(record){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new ThereunderForm( {
				id : record.data.id,personId : record.data.personid,gridPanel : this.gridPanel,isReadOnly:true
			}).show();
		}	
	}
});
