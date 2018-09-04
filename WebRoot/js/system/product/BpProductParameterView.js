/**
 * @author
 * @class BpProductParameterView
 * @extends Ext.Panel
 * @description [BpProductParameter]管理
 * @company 智维软件
 * @createtime:
 */
BpProductParameterView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
			   this.isGranted = function(a){
			      var b = "";
		         if("p2p"==this.plat){
				     b="_p2p";
			     }else{
			  	    b="";
			     }
			return isGranted(a+b);
		}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpProductParameterView.superclass.constructor.call(this, {
						id : 'BpProductParameterView',
						title : '贷款产品设计',
						region : 'center',
						layout : 'border',
						iconCls:"menu-finance",
						items : [this.searchPanel, this.gridPanel]
				});
			},// end of constructor
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
								name : 'Q_productName_S_LK',
								xtype : 'textfield',
								anchor : '100%'
							}]
						},{
							columnWidth:.25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							border : false,
							items:[{
								fieldLabel : '计息方式',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["等额本金", "sameprincipal"], ["等额本息", "sameprincipalandInterest"],
											["等本等息", "sameprincipalsameInterest"], ["按期收息,到期还本", "singleInterest"]]
								}),
								triggerAction : "all",
								hiddenName:'Q_accrualtype_S_EQ',
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
						hidden : isGranted('_bpProductParameter_add')?false:true,
						scope : this,
						handler : this.createRs
					},{
						iconCls : 'btn-config',
						text : '配置产品',
						xtype : 'button',
						hidden : isGranted('_bpProductParameter_edit')?false:true,
						scope : this,
						handler : this.editRs
					}, {
						iconCls : 'btn-del',
						text : '删除产品',
						xtype : 'button',
						hidden : isGranted('_bpProductParameter_del')?false:true,
						scope : this,
						handler : this.removeSelRs
					}, {
						iconCls : 'btn-detail',
						text : '查看产品',
						xtype : 'button',
						hidden : isGranted('_bpProductParameter_see')?false:true,
						scope : this,
						handler : this.seeSelRs
					}]
				});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					id : 'BpProductParameterGrid',
					url : __ctxPath+ "/system/listBpProductParameter.do",
					fields : [{
								name : 'id',
								type : 'int'
							 }, 'productName', 'createTime', 'payintentPeriod','productStatus','borrowerType','accrualtype','payaccrualType','isPreposePayAccrual','isInterestByOneTime', 'isStartDatePay',
							   'payintentPerioDate','yearAccrualRate','accrual','dayAccrualRate','sumAccrualRate','productDescribe','mineType','mineId','operationType','businessType'],
					columns : [{
							header : 'id',
							align:'center',
							dataIndex : 'id',
							hidden : true
						}, {
							header : '产品名称',
							align:'center',
							dataIndex : 'productName'
						}, {
							header : '创建时间',
							align:'center',
							dataIndex : 'createTime',
							format : 'Y-m-d',
							renderer : ZW.ux.dateRenderer(this.datafield)
						}, {
							header : '贷款期限',
							align:'center',
							dataIndex : 'payintentPeriod'
						}, {
							header : '状态',
							align:'center',
							dataIndex : 'productStatus'
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
				new BpProductParameterAddForm({
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
				$delGridRs({
					url : __ctxPath+ '/system/multiDelBpProductParameter.do',
					grid : this.gridPanel,
					idName : 'id'
				});
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
				var productId = selections[0].data.id;
				operationType= selections[0].data.operationType;
				businessType= selections[0].data.businessType;
				new BpProductParameterForm({
					productId : productId,
					businessType:businessType,
					operationType:operationType,
					isEditReadOnly:true,
					isAllReadOnly:false
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
					var productId = selections[0].data.id;
					operationType= selections[0].data.operationType;
				businessType= selections[0].data.businessType;
					new BpProductParameterForm({
						productId : productId,
						businessType:businessType,
						operationType:operationType,
						hiddenDel:true,
						hiddenedit:true,
						hiddenAdd:true,
						isHideBtns:true,
						isEditReadOnly:true,
						isAllReadOnly:true
					}).show();
			}
		});
