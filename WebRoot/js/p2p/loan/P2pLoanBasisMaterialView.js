/**
 * @author
 * @class P2pLoanBasisMaterialView
 * @extends Ext.Panel
 * @description [BpProductParameter]管理
 * @company 智维软件
 * @createtime:
 */
P2pLoanBasisMaterialView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				P2pLoanBasisMaterialView.superclass.constructor.call(this, {
						id : 'P2pLoanBasisMaterialView',
						title : '基础材料配置',
						region : 'center',
						layout : 'border',
						iconCls : 'btn-tree-team30',
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
								fieldLabel : '材料类型',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["必备", "1"],  ["可选", "2"]]
								}),
								triggerAction : "all",
								hiddenName:'Q_materialState',
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
								fieldLabel : '客户类型',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["企业", "enterprise"],  ["个人", "person"]]
								}),
								triggerAction : "all",
								hiddenName:'Q_aoperationType',
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
						text : '新增',
						xtype : 'button',
						//hidden : isGranted('_bpProductParameter_add')?false:true,
						scope : this,
						handler : this.createRs
					},{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						//hidden : isGranted('_bpProductParameter_del')?false:true,
						scope : this,
						handler : this.removeSelRs
					},{
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						//hidden : isGranted('_bpProductParameter_see')?false:true,
						scope : this,
						handler : this.seeSelRs
					},{
						iconCls : 'btn-edit',
						text : '编辑',
						xtype : 'button',
						scope : this,
						handler : this.seeSelRs
					}]
				});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					id : 'P2pLoanBasisMaterialViewGrid',
					url : __ctxPath+ "/p2p/listP2pLoanBasisMaterial.do",
					fields : [{
								name : 'materialId',
								type : 'int'
							 }, 'materialName', 'materialState', 'operationType','remark'],
					columns : [{
							header : 'materialId',
							align:'center',
							dataIndex : 'materialId',
							hidden : true
						}, {
							header : '客户类型',
							align:'center',
							dataIndex : 'operationType',
							renderer : function(val){
								if(val=="person"){
									return "个人";
								}else if(val=="enterprise"){
									return "企业";
								}
							}
						}, {
							header : '贷款材料',
							dataIndex : 'materialName'
						}, {
							header : '材料类型',
							align:'center',
							dataIndex : 'materialState',
							renderer : function(val){
								if(val==1){
									return "必备";
								}else if(val==2){
									return "可选";
								}
							}
						}, {
							header : '备注',
							dataIndex : 'remark'
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
				new P2pAddBasisMaterial({
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
				var materialId = selections[0].data.materialId;
				var flag1=true;
				var flag2=true;
				if("编辑"==record.text){
					flag1=false;
					flag2=false;
				}
				new P2pAddBasisMaterial({
					materialId : materialId,
					isHideBtns:flag1,
					isAllReadOnly:flag2
				}).show();
			},
			// 按ID删除记录，假删除
			removeSelRs : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if(s<=0){
					Ext.ux.Toast.msg("操作信息","请选中至少一条记录");
					return false;
				}
				var id="";
				for(var i=0;i<s.length;i++){
					id+=s[i].data.materialId+",";
				}
				$postDel({
					url : __ctxPath+ '/p2p/multiDelP2pLoanBasisMaterial.do',
					ids : id,
					grid : this.gridPanel
				});
			}
		});
