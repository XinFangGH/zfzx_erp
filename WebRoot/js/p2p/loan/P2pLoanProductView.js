/**
 * @author
 * @class P2pLoanProductView
 * @extends Ext.Panel
 * @description [BpProductParameter]管理
 * @company 智维软件
 * @createtime:
 */
P2pLoanProductView = Ext.extend(Ext.Panel, {
		title:'',
		constructor : function(_cfg) {
			if (typeof(_cfg.productState) != "undefined") {
				this.productState = _cfg.productState;
			}else{
				this.productState = 'ALL';
			}
			if(_cfg.type==1){
				this.title="新增信贷产品";
			}else if(_cfg.type==2){
				this.title="产品参数配置";
			}else if(_cfg.type==3){
				this.title="申请步骤配置";
			}else if(_cfg.type==4){
				this.title="信贷产品发布";
			}else if(_cfg.type==5){
				this.title="信贷产品关闭";
			}else if(_cfg.type==6){
				this.title="信贷产品启用";
			}else if(_cfg.type==7){
				this.title="信贷产品查询";
			}
			Ext.applyIf(this, _cfg);
			// 初始化组件
			this.initUIComponents();
			// 调用父类构造
			P2pLoanProductView.superclass.constructor.call(this, {
					id : 'P2pLoanProductView_'+this.type,
					title : this.title,
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [this.searchPanel, this.gridPanel]
			});
		},
		// 初始化组件
		initUIComponents : function() {
			// 初始化搜索条件Panel
			this.searchPanel = new HT.SearchPanel({
				layout : 'column',
				region : 'north',
				height : 40,
				anchor : '96%',
				border : false,
				layoutConfig : {
					align : 'middle'
				},
				bodyStyle : 'padding:10px 10px 10px 10px',
				items : [{
						columnWidth:.2,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 80,
						defaults : {
							anchor : '100%'
						},
						border : false,
						items:[{
							fieldLabel : '产品名称',
							name : 'Q_productName',
							xtype : 'textfield',
							anchor : '100%'
						}]
					}/*,{
						columnWidth:.15,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 80,
						defaults : {
							anchor : '100%'
						},
						border : false,
						items:[{
							fieldLabel : '业务品种',
							xtype : 'combo',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["个人贷", "person"],  ["企业贷", "enterprise"]]
							}),
							triggerAction : "all",
							hiddenName:'Q_operationType',
							anchor : '100%'
					}]
				}*/,{
						columnWidth:.2,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 80,
						defaults : {
							anchor : '100%'
						},
						border : false,
						hidden:this.productState=='ALL'?false:true,
						items:[{
							fieldLabel : '产品状态',
							xtype : 'combo',
							mode : 'local',
							displayField : 'name',
							valueField : 'id',
							editable : false,
							disabled:this.productState=='ALL'?false:true,
							store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["参数配置中", "1"],["流程配置中", "2"],["发布中", "3"],["已发布", "4"],["已关闭", "5"]]
							}),
							triggerAction : "all",
							hiddenName:'Q_productState',
							anchor : '100%'
					}]
				},{
						columnWidth:.2,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 80,
						defaults : {
							anchor : '100%'
						},
						border : false,
						items:[{
							fieldLabel : '使用范围',
							name : 'Q_userScope',
							xtype : 'textfield',
							anchor : '100%'
						}]
					},{
					columnWidth : 0.12,
					layout : 'form',
					border : false,

					labelAlign : 'right',
					items : [{
						xtype : 'button',
						text : '查询',
						width : 60,
						scope : this,
						style : 'margin-left:30',
						iconCls : 'btn-search',
						handler : this.search
					}]
				}, {
					columnWidth : 0.08,
					layout : 'form',
					border : false,

					labelAlign : 'right',
					items : [{
						xtype : 'button',
						text : '重置',
						width : 60,
						scope : this,
						style : 'margin-left:1',
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			});// end of searchPanel

			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '新增产品',
					xtype : 'button',
					hidden : this.type==1?false:true,
					scope : this,
					handler : this.createRs
				},{
					iconCls : 'btn-config',
					text : '配置产品',
					xtype : 'button',
					hidden : this.type==2?false:true,
					scope : this,
					handler : this.editRs
				},{
					iconCls : 'btn-del',
					text : '删除产品',
					xtype : 'button',
					hidden : (this.type==5 || this.type==6 || this.type==7)?true:false,
					scope : this,
					handler : this.removeSelRs
				},{
					iconCls : 'btn-confi',
					text : '配置流程',
					xtype : 'button',
					hidden : this.type==3?false:true,
					scope : this,
					handler : this.setFlow
				},{
					iconCls : 'btn-startup',
					text : this.type==4?'发布':(this.type==5?'关闭':'启用'),
					xtype : 'button',
					hidden : (this.type==4 || this.type==5 || this.type==6)?false:true,
					scope : this,
					handler : this.updateProduct
				}/*, {
					iconCls : 'btn-del',
					text : '删除产品',
					xtype : 'button',
					hidden : isGranted('_bpProductParameter_del')?false:true,
					scope : this,
					handler : this.removeSelRs
				}*/, {
					iconCls : 'btn-detail',
					text : '查看详细',
					xtype : 'button',
//					hidden : isGranted('_bpProductParameter_see')?false:true,
					scope : this,
					handler : this.seeSelRs
				},{
					iconCls : 'btn-xls',
					text : '导出EXcel',
					xtype : 'button',
//					hidden : isGranted('_bpProductParameter_see')?false:true,
					scope : this,
					handler : this.excelProduct
				}]
			});

			this.gridPanel = new HT.GridPanel({
				region : 'center',
				tbar : this.topbar,
				id : 'P2pLoanProductViewGrid_'+this.type,
				url : __ctxPath+ "/p2p/listP2pLoanProduct.do?productState="+this.productState,
				fields : [{
							name : 'productId',
							type : 'int'
						 }, 'productName', 'operationType', 'userScope','productState'],
				columns : [{
						header : 'productId',
						align:'center',
						dataIndex : 'productId',
						hidden : true
					}, {
						header : '产品名称',
						dataIndex : 'productName',
						width : 90
					}, {
						header : '业务品种',
						align:'center',
						dataIndex : 'operationType',
						width : 90,
						renderer : function(val){
							if(val=="person"){
								return "个人贷";
							}else if(val=="enterprise"){
								return "企业贷";
							}
						}
					}, {
						header : '适用范围',
						dataIndex : 'userScope',
						width : 300
					}, {
						header : '状态',
						align:'center',
						dataIndex : 'productState',
						width : 90,
						renderer : function(val){
							if(val==1){
								return "参数配置中";
							}else if(val==2){
								return "流程配置中";
							}else if(val==3){
								return "发布中";
							}else if(val==4){
								return "已发布";
							}else if(val==5){
								return "已关闭";
							}
						}
					}]
			});
		},// end of the initComponents()
			// 重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			// 按条件搜索
			search : function() {
				$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
			},
			// 创建记录
			createRs : function() {
				var gridpanel=this.gridPanel;
				new P2pAddProduct({
					gridpanel:gridpanel
				}).show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
					url : __ctxPath+ '/system/multiDelBpProductParameter.do',
					ids : id,
					grid : this.gridPanel
				});
			},
			// 把选中ID删除
			removeSelRs : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if(s<=0){
					Ext.ux.Toast.msg("操作信息","请选中至少一条记录");
					return false;
				}
				var id="";
				for(var i=0;i<s.length;i++){
					id+=s[i].data.productId+",";
				}
				$postDel({
					url : __ctxPath+ '/p2p/multiDelP2pLoanProduct.do',
					ids : id,
					grid : this.gridPanel
				});
			},
			//更改产品状态
			updateProduct : function(){
				var gridPanel=this.gridPanel;
			 	var s = gridPanel.getSelectionModel().getSelections();
			 	if(s<=0){
			 		Ext.ux.Toast.msg("操作信息","请选中要修改的记录");
			 		return false;
			 	}else if(s.length>1){
			 		Ext.ux.Toast.msg("操作信息","只能选择一条记录");
			 		return false;
			 	}
			 	var productState="";
			 	if(this.productState==3){
			 		productState=4;
			 	}else if(this.productState==4){
			 		productState=5;
			 	}else if(this.productState==5){
			 		productState=4;
			 	}
			 	/*new P2pUpdateProduct({
			 		productId :  s[0].data.productId
				}).show();*/
			 	
			 	Ext.Ajax.request({
					url : __ctxPath+ '/p2p/updateProductP2pLoanProduct.do?productId='+s[0].data.productId+"&productState="+productState,
					method : 'post',
					success : function(response,request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						if (gridPanel != null) {
							Ext.ux.Toast.msg("操作信息",obj.msg);
							gridPanel.getStore().reload();
						}
					}
			 	});
			
			},
			//导出
			excelProduct : function(){
					var Q_productName = this.getCmpByName("Q_productName").getValue();
//					var Q_operationType = this.getCmpByName("Q_operationType").getValue();
					var Q_productState = this.getCmpByName("Q_productState").getValue();
					var Q_userScope = this.getCmpByName("Q_userScope").getValue();
					window.open( __ctxPath +"/p2p/excelProductP2pLoanProduct.do?Q_productName="+Q_productName+"" +
//							"&Q_operationType="+Q_operationType+
							"&productState="+this.productState+
							"&Q_productState="+Q_productState+"&Q_userScope="+Q_userScope+"");
	},
			// 编辑Rs
			editRs : function(record) {
				var selections = this.gridPanel.getSelectionModel().getSelections();
				var len = selections.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var productId = selections[0].data.productId;
				var operationType = selections[0].data.operationType;
				new P2pDeployProductForm({
					productId : productId,
					operationType:operationType,
					isEditReadOnly:true,
					hiddenDel:false,
			        hiddenedit:false,
			        hiddenAdd:false,
					isAllReadOnly:false,
					gridPanel:this.gridPanel
				}).show();
			},
			//配置流程
			setFlow:function(record){
				var selections = this.gridPanel.getSelectionModel().getSelections();
				var len = selections.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var productId = selections[0].data.productId;
				new P2pLoanFlowSetView({
					productId : productId,
					isEditReadOnly:true,
					parentGrid:this.gridPanel
				}).show();
			},
			//查看产品记录
			seeSelRs:function(record) {
				var selections = this.gridPanel.getSelectionModel().getSelections();
				var len = selections.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var productId = selections[0].data.productId;
				new P2pDeployProductForm({
					isEditReadOnly:true,
					productId : productId,
					hiddenDel:true,
			        hiddenedit:true,
			        hiddenAdd:true,
			        isHideBtns:true,
					isAllReadOnly:true
				}).show();
			}
		});
