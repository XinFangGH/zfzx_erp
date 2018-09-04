/**
 * @author
 * @class SlFundintentUrgeView
 * @extends Ext.Panel
 * @description [SlFundintentUrge]管理
 * @company 智维软件
 * @createtime:
 */
MatchFundsTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        activeTab1:0,
	        onclickflag:0,
	        onclickisInterent:0,
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				if (typeof (this.activeTab1) == "undefined") {
					
					this.activeTab1 = 0;
				}
				if (typeof (this.onclickflag) == "undefined") {
					this.onclickflag =0;
				}
				if (typeof (this.onclickisInterent) == "undefined") {
					this.onclickisInterent =0;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				MatchFundsTab.superclass.constructor.call(this, {
							id : 'MatchFundsTab'+this.businessType,
							title : this.title!=null?this.title: '资金匹配',
							iconCls : "btn-tree-team13",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var itemschange=[new MatchFundsView({
									fundIntentId:this.fundIntentId,
									tabflag:"all",
									tabflagtitle:"匹配中",
									onclickflag:this.onclickflag,
									onclickisInterent:this.onclickisInterent,
									businessType:this.businessType
								}),
								new MatchFundsSuccessView({
									fundIntentId:this.fundIntentId,
									tabflag:"all",
									tabflagtitle:"成功匹配",
									onclickflag:this.onclickflag,
									onclickisInterent:this.onclickisInterent,
									businessType:this.businessType
								}),
								new MatchFundsFailView({
									fundIntentId:this.fundIntentId,
									tabflag:"all",
									tabflagtitle:"匹配失败",
									onclickflag:this.onclickflag,
									onclickisInterent:this.onclickisInterent,
									businessType:this.businessType
								}),
								new MatchFundsAllView({
									fundIntentId:this.fundIntentId,
									tabflag:"all",
									tabflagtitle:"全部",
									onclickflag:this.onclickflag,
									onclickisInterent:this.onclickisInterent,
									businessType:this.businessType
								})
							];
				this.tabpanel = new Ext.TabPanel({
					resizeTabs : true, // turn on tab resizing
					minTabWidth : 115,
					tabWidth : 135,
					enableTabScroll : true,
					Active : 0,
					width : 600,
					defaults : {
						autoScroll : true
					},
					region : 'center', 
					//layout:'fit'
					deferredRender : true,
					activeTab : this.activeTab1, // first tab initially active
					xtype : 'tabpanel',
						items : itemschange
					});
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new SlFundintentUrgeForm({slFundintentUrgeId:rec.data.slFundintentUrgeId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new SlFundintentUrgeForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.finance/multiDelSlFundintentUrge.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.finance/multiDelSlFundintentUrge.do',
					grid:this.gridPanel,
					idName:'slFundintentUrgeId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new SlFundintentUrgeForm({
					slFundintentUrgeId : record.data.slFundintentUrgeId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.slFundintentUrgeId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
