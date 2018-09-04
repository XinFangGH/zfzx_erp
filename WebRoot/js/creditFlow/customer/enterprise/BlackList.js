
BlackList = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BlackList.superclass.constructor.call(this, {
			id : 'BlackList'+this.type,
			title : '黑名单客户',
			region : 'center',
			layout : 'border',
			iconCls:"btn-tree-team36",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [ {
				name : 'isPledge',
				xtype : 'hidden',
				value : 0
			},/*{
				columnWidth : 0.17,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
			
				items : [ {
					xtype:'combo',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'id',
				    anchor:'100%',
				    store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["企业", "company"],
									["个人", "person"]]
					}),
					triggerAction : "all",
					hiddenName:"customerType",
					fieldLabel : '客户类别'
				} ]
			},*/ {
				columnWidth : 0.17,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'customerName',
					anchor:'100%'
				} ]
			}, {
				columnWidth : 0.19,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '证件号码',
					name : 'cardNum',
					anchor:'95%'
				}]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					//width : 60,
					anchor:'100%',
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					anchor:'100%',
					//width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-detail',
				text : '查看客户详细信息',
				xtype : 'button',
				scope : this,
		//		hidden : isGranted('_seeCustomerInfo')?false:true,
				handler : this.seeCustomerInfo
			},'-', {
				iconCls : 'btn-del2',
				text : '撤销黑名单',
				xtype : 'button',
				scope : this,
		//		hidden : isGranted('_revocateBlack')?false:true,
				handler : this.RevocateBlack
			} ,'-', {
				iconCls : 'btn-xls',
				text : '导出excel',
				xtype : 'button',
				scope : this,
				handler : this.toExcel
			}]
		});
       this.store=new Ext.data.JsonStore ({
       	   url : __ctxPath + '/creditFlow/customer/enterprise/getBlackListEnterprise.do?isGrantedSeeAllBlackList=true&customerType='+this.type,
       	   root :'topics',
       	   totalProperty:'totalProperty',
       	   autoLoad:true,
       	   baseParams:{
	       	   start:0,
	       	   limit:25
       	   },
       	   fields:[{
					name : 'key'
				},{
				name : 'thirdRalationId'
			}, {
				name : 'name'
			}, {
				name : 'code'
			}, {
				name : 'tel'
			}, {
				name : 'type'
			},{
				name : 'customerLevel'
			},{
				name : 'blackReason'
			},{
				name : 'id'
			}]
       })
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			store : this.store,
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}/*, {
				header : '客户类型',
				dataIndex : 'type',
				renderer:function(value){
					if(value=='company'){
						return '企业'
					}else if(value=='person'){
						return '个人'
					}else{
						return '';
					}
				}
			}*/, {
				header : '客户名称',
				dataIndex : 'name'
			}, {
				header : '证件号码',
				align:'center',
				dataIndex : 'code'
			}, {
				header : '联系电话',
				align:'center',
				dataIndex : 'tel'
			}, {
				header : '原因说明',
				dataIndex : 'blackReason'
			}]
		//end of columns
				});
	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},

	seeCustomerInfo : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			var customerType=s[0].data.type;
			var customerId=s[0].data.id;
			if(customerType=='company'){
				seeEnterpriseCustomer(customerId)
			}else if(customerType=='person'){
				seePersonCustomer(customerId)
			}
		}
	},

	//把选中ID删除
	RevocateBlack : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var grid=this.gridPanel
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			var customerType=s[0].data.type;
			var customerId=s[0].data.id;
			var formPanel=new Ext.FormPanel({
			    frame:true,
			    labelAlign:'right',
				bodyStyle : 'padding:10px 10px 10px 10px',
				height:150,
				labelWidth:60,
				border:false,
				url:__ctxPath + '/creditFlow/customer/enterprise/RevocateBlackEnterprise.do?customerId='+customerId+"&customerType="+customerType,
			    items:[{
					xtype : "dickeycombo",
					hiddenName : "customerLevel",
					nodeKey : 'customerLevel', // xx代表分类名称
					fieldLabel : "客户级别",
					anchor:'100%',
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								
								combox.setValue(combox.getValue());
								combox.clearInvalid();
							})
				       }
					}
			    }]
			})
			var window=new Ext.Window({
			   title:'撤销黑名单',
			   width:300,
			   height:150,
			   buttonAlign:'center',
			   modal:true,
			   items:formPanel,
			   buttons:[{
			      text:'提交',
			      iconCls : 'btn-save',
			      handler:function(){
			      formPanel.getForm().submit({
			  	 	   	waitMsg : '正在提交...',
			  	 	  	method : 'post',
						success : function(form, action) {
							Ext.ux.Toast.msg('状态', '撤销成功');
							window.destroy();
							grid.getStore().reload()
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态', '撤销失败');
						}
			  	 	})
			      }
			   },{
			      text:'取消',
			      iconCls : 'btn-cancel',
			      handler:function(){
			      	window.destroy();
			      }
			   }]
			})
			window.show()
		}
	},
	
	//导出到Excel
	toExcel:function(){
		var customerName=this.searchPanel.getCmpByName('customerName').getValue();
		var cardNum=this.searchPanel.getCmpByName('cardNum').getValue();
		window.open(__ctxPath + '/creditFlow/customer/enterprise/getBlackListToExcelEnterprise.do?isGrantedSeeAllBlackList=true&customerType='+this.type+"&customerName="+customerName+"&cardNum="+cardNum);
	}

});
