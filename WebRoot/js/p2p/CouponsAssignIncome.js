/**
 * @author
 * @class BpCustMemberView
 * @extends Ext.Panel
 * @description [BpCustMember]管理
 * @company 智维软件
 * @createtime:
 */
CouponsAssignIncome = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CouponsAssignIncome.superclass.constructor.call(this, {
							id : 'CouponsAssignIncome',
							title : "理财优惠券收益查询",
							region : 'center',
							layout : 'border',
							iconCls:"menu-finance",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							autoScroll : true,
							region : 'north',
							
							border : false,
							height : 50,
							anchor : '80%',
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
											fieldLabel:'用户名',
											name : 'Q_loginname',
											flex:1,
											anchor : "90%",
											xtype : 'textfield'
											}]
					     	},{
				     		columnWidth :.2,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							border : false,
							items : [{
											fieldLabel:'投资人',
											name : 'Q_truename',
											flex:1,
											anchor : "90%",
											xtype : 'textfield'
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
						items : [ {
					iconCls : 'btn-xls',
					text : '导出到Excel',
					xtype : 'button',
					scope : this,
					handler : this.couponsExcel
				}]
				});
	            var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					plugins : [summary],
					//使用RowActions
					//rowActions:true,
					id:'CouponsAssignIncomeGrid',
					url : __ctxPath + "/creditFlow/financingAgency/getCouponsAssignIncomePlMmOrderAssignInterest.do",
					fields : [	'loginName'
								,'investPersonName'
								,'couponsIncome'
								,'addrateIncome'
								,'sumIncome'
								,'notIncome'
								],
					columns:[{
								header : '投资人',
								align:'center',
								summaryRenderer : totalMoney,
								dataIndex : 'investPersonName',
								anchor : "100%"
							},{
								header : '用户名',
								align:'center',
								dataIndex : 'loginName' ,
								anchor : "100%"
									 
							},{
								header : '优惠券收益奖励(元)',
								summaryType : 'sum',
								align:'right',
								dataIndex : 'couponsIncome',
								anchor : "100%"
									 
							},{
								header : '普通加息收益奖励(元)',
								summaryType : 'sum',
								align:'right',
								dataIndex : 'addrateIncome',
								anchor : "100%"
									 
							},{
								header : '累计收益奖励(元)',
								summaryType : 'sum',
								align:'right',
								dataIndex : 'sumIncome',
								anchor : "100%"
									 
							},{
								header : '未收收益(元)',
								align:'right',
								summaryType : 'sum',
								dataIndex : 'notIncome',
								anchor : "100%"
									 
							}]
				});
				
				//this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			updatePasswordGrid:function(){   
				new RefreshUserNumTW({
					plainpassword:r.get('plainpassword'),
					cardcode:r.get('cardcode'),
					truename:r.get('truename'),
					loginname:r.get('loginname'),
					telphone:r.get('telphone'),
					email:r.get('email'),
					id:r.get('id')  
				}).show(); 
				
			},
			
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
			/*rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpCustMemberForm({id:rec.data.id}).show();
				});
			},*/
			//创建记录
			createRs : function() {
				new BpCustMemberForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p/multiDelBpCustMember.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p/multiDelBpCustMember.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//按ID禁用记录
			forbiddenRs : function(id) {
				$postForbi({
					url:__ctxPath+ '/p2p/multiForbiBpCustMember.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//编辑Rs
			editRs : function(record) {
				new BpCustMemberForm({
					id : record.data.id
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					case 'btn-forbidden' :
						this.forbiddenRs.call(this,record.data.id);
						break;
					default :
						break;
				}
			},
			couponsExcel : function(){
					var Q_loginname = this.getCmpByName("Q_loginname").getValue();
					var Q_truename = this.getCmpByName("Q_truename").getValue();
					window.open( __ctxPath +"/creditFlow/financingAgency/excelCouponsAssignIncomePlMmOrderAssignInterest.do?Q_loginname="+Q_loginname+"&Q_truename="+Q_truename+"");
	}
});

function openTw(recommandPerson,cardcode,truename,loginname,telphone,email,id)
{
	new RefreshUserNumTW({
		recommandPerson:recommandPerson,
		cardcode:cardcode,
		truename:truename,
		loginname:loginname,
		telphone:telphone,
		email:email,
		id:id  
	}).show();   
	
}
