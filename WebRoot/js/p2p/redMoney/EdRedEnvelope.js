/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
EdRedEnvelope = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.businessType)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				NotRedEnvelope.superclass.constructor.call(this, {
							id : 'EdRedEnvelope',
							title : '已全部派发红包',
							layout : 'border',
							iconCls:"menu-finance",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var tabflag=this.tabflag;
				var labelsize=70;
				 var labelsize1=115;
			
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							autoScroll : true,
							region : 'north',
							border : false,
							height : 50,
							anchor : '100%',
							layoutConfig: {
				               align:'middle'
				            },
				             bodyStyle : 'padding:10px 10px 10px 10px',
							items:[{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
								fieldLabel : '红包名称',
								name : 'Q_name_S_LK',
								xtype:"textfield",
								maxLength : 50
							}]
					     	},{
		     			columnWidth :.1,
						layout : 'form',
						border : false,
						labelWidth :50,
						items :[{
							text : '查询',
							xtype : 'button',
							scope : this,
							style :'margin-left:30px',
							anchor : "90%",
							iconCls : 'btn-search',
							handler : this.search
						}]
		     		},{
		     			columnWidth :.1,
						layout : 'form',
						border : false,
						labelWidth :50,
						items :[{
							text : '重置',
							style :'margin-left:30px',
							xtype : 'button',
							scope : this,
							//width : 40,
							anchor : "90%",
							iconCls : 'btn-reset',
							handler : this.reset
						}]
		     		}]
								
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
					items : [

					{
						iconCls : 'btn-add',
						text : '新增',
						xtype : 'button',
						scope : this,
						hidden:true,
						handler : this.add
			
					},{
						iconCls : 'btn-detail',
						text :'查看',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_ping_f_principalPay_'+this.businessType)?false:true,
						handler : this.see
			
					}

					]
				});
                    var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					id : 'EdRedEnvelopeGrid',
					url : __ctxPath + "/customer/listBpCustRedEnvelope.do?Q_distributestatus_SN_EQ="+this.distributestatus,
					fields : [{
								name : 'redId',
								type : 'int'
							}, 'name','distributemoney', 'eddistributemoney','distributecount','distributestatus', 'distributeTime','remarks','createTime'
							],
					columns : [{
								header : 'redId',
								dataIndex : 'redId',
								hidden : true
							},{
								header : "红包名称",
								sortable : true,
								width : 120,
								summaryRenderer : totalMoney,
								dataIndex : 'name',
								align:"center"
								
							}, {
								header : '人数',
								dataIndex : 'distributecount',
								align:"center",
								summaryType : 'sum',
                                renderer : function(v) {
					                 return v+"人";
				                   },
								width : 150
							}
							, {
								header : '金额',
								dataIndex : 'distributemoney',
								align:"right",
								width : 120,
								summaryType : 'sum',
                                renderer : function(v) {
					                 return v+"元";
				                   },
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							}, {
								header : '已派发金额',
								dataIndex : 'eddistributemoney',
								width : 120,
								align:"right",
								summaryType : 'sum',
                                renderer : function(v) {
					                 return v+"元";
				                   },
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							}, {
								header : '创建时间',
								dataIndex : 'createTime',
								align:"center",
								width : 150
							},{
									header : '派发时间',
								dataIndex : 'distributeTime',
								align :'center',
								width : 150
							}, {
								header : '派发状态',
								dataIndex : 'distributestatus',
								align:"center",
								width : 130,
								renderer:function(v){
								return "已派发";
							}
							}
						
						]

						// end of columns
					});


			},// end of the initComponents()
			// 重置查询表单

			reset : function() {},
			// 按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			
		
			
			add1 : function() {
				new RedenvelopeForm({
				gp:this.gridPanel,
				redId:""
				}).show();
			},
			envelope : function(){},
			see : function(){
				var rows = this.gridPanel.getSelectionModel().getSelections();
				if (rows.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (rows.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					new RedenvelopeForm({
					gp:this.gridPanel,
					isreadOnly:true,
					redId:rows[0].data.redId,
					type:"ed"
					}).show();
				}
				
			},	
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p.redMoney/multiDelBpCustRedEnvelope.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p.redMoney/multiDelBpCustRedEnvelope.do',
					grid:this.gridPanel,
					idName:'redId'
				});
			},
			deletes : function(record,flag) {
				$delGridRs({
					url:__ctxPath + '/customer/multiDelBpCustRedEnvelope.do',
					grid:this.gridPanel,
					idName:'redId'
				});
			},
			envelope : function(){
			
				
				var rows = this.gridPanel.getSelectionModel().getSelections();
				if (rows.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (rows.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					new RedenvelopeForm({
					gp:this.gridPanel,
					isreadOnly:true,
					redId:rows[0].data.redId
					}).show();
				}
				
			
				
				
			}
			
			
		});
