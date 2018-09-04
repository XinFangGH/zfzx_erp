/**
 * @author
 * @class InvestIntentionView
 * @extends Ext.Panel
 * @description [InvestIntention]管理
 * @company 智维软件
 * @createtime:
 */
InvestIntentionView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				InvestIntentionView.superclass.constructor.call(this, {
							id : 'InvestIntentionView',
							title : '投资意向管理',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var itemwidth = 0.2;
		this.searchPanel = new HT.SearchPanel({
					frame : false,
					region : 'north',
					height : 35,
					monitorValid : true,
					layout : 'column',
					bodyStyle : 'padding:0px 0px 0px 0px',
					border : false,
					defaults : {
						layout : 'form',
						border : false,
						labelWidth : 60,
						bodyStyle : 'padding:5px 0px 0px 5px'
					},
					labelAlign : "right",
					keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.searchByCondition,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
					items : [{
								columnWidth : itemwidth,
								bodyStyle : 'padding:5px 0px 0px 0px',
								items : [{
											fieldLabel : '投资期限',
											name : 'Q_intentDate_N_GE',
											flex : 1,
											//editable : false,
											xtype : 'numberfield',
											//format : 'Y-m-d',
											anchor : "100%"
										}]
							}, {
								columnWidth : .15,
								layout : "form",
								labelWidth : 20,
								items : [{
											fieldLabel : '至',
											labelWidth : 30,
											name : 'Q_intentDate_N_LE',
											flex : 1,
											xtype : 'numberfield',
											//editable : false,
											//format : 'Y-m-d',
											anchor : "100%"
										}]
							}, {
								columnWidth : itemwidth,
								items : [{
											fieldLabel : '金额范围',
											name : 'Q_intentMoney_L_GE',
											flex : 1,
											xtype : 'numberfield',
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.15,
								layout : "form",
								labelWidth : 20,
								items : [{
										fieldLabel : '至',
										name : 'Q_intentMoney_L_LE',
										flex : 1,
										xtype : 'numberfield',
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.15,
								layout : "form",
								labelWidth : 40,
								items : [{
											fieldLabel : '状态',
											name : 'Q_status_S_EQ',
											flex : 1,
											xtype : 'combo',
											triggerAction:'all',
											mode:'local',
											hiddenName :'Q_status_S_EQ',
											store: new Ext.data.ArrayStore({
										        fields: [
										            'myId',
										            'displayText'
										        ],
										        data: [[1, '有效'], [2, '已匹配'],[3,'已过期']]
										    }),
										    valueField:'myId',
										    displayField:'displayText',
											anchor : "100%"
										}]
							}, {
								columnWidth : 0.07,
								items : [{
											id : 'searchButton',
											xtype : 'button',
											text : '查询',
											iconCls : 'btn-search',
											width : 60,
											// labelWidth : "30",
											formBind : true,
											labelWidth : 20,
											bodyStyle : 'padding:5px 0px 0px 0px',
											scope : this,
											handler : this.searchByCondition
										}]
							}, {

								columnWidth : 0.07,
								items : [{
											xtype : 'button',
											text : '重置',
											width : 60,
											scope : this,
											iconCls : 'btn-reset',
											handler : this.reset
										}]

							}]
				});// end of searchPanel
				

				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-customer',
										text : '查看',
										xtype : 'button',
										scope : this,
										handler : this.lookRs
									}, {
										iconCls : 'btn-edit',
										text : '编辑',
										xtype : 'button',
										scope : this,
										handler : this.editRs
									}, {
										iconCls : 'btn-del',
										text : '删除',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					root : 'result',
					// 使用RowActions
					//rowActions : true,
					id : 'InvestIntentionGrid',
					url : __ctxPath + "/customer/listInvestIntention.do",
					fields : [{
								name : 'intentId',
								type : 'int'
							}, 'addrDemand', 'dangerDemand', 'intentDate', 'endDate',
							'intentMoney', 'issingleDemand', 'otherInvest', 'findPerson',
							'status','investPersonName'/* ,//.perName
							'investPerson.phoneNumber',
							'investPerson.postCode', 'investPerson.postAddress'*/],
					columns : [{
								header : 'intentId',
								dataIndex : 'intentId',
								hidden : true
							}, {
								header : '姓名',
								dataIndex : 'investPersonName'
							}/*, {
								header : '手机号码',
								dataIndex : 'investPerson.phoneNumber'
							}, {
								header : '电子邮箱',
								dataIndex : 'investPerson.postCode'
							}, {
								header : '通讯地址',
								dataIndex : 'investPerson.postAddress'
							}*/, {
								header : '意向投资金额',
								dataIndex : 'intentMoney'
							}, {
								header : '投资期限',
								dataIndex : 'intentDate'
							}, {
								header : '有效截止日期',
								dataIndex : 'endDate',
								format : 'Y-m-d'
							}, {
								header : '其他投资意向',
								dataIndex : 'otherInvest'
							}, {
								header : '采集人',
								dataIndex : 'findPerson'
							}, {
								header : '状态',
								dataIndex : 'status',
								renderer : function(value){
									if(value=='1'){
										return '有效';
									} else if(value=='2'){
										return '已匹配';
									} else if(value=='3'){
										return '已过期';
									} else{
										return '';
									}
								}
							}]
						// end of columns
				});

				this.gridPanel.addListener('rowdblclick', this.rowClick);

			},// end of the initComponents()
			// 重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			// 按条件搜索
			searchByCondition : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			// GridPanel行点击处理事件
			rowClick : function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							new InvestIntentionForm({
										intentId : rec.data.intentId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new InvestIntentionForm({
					isAllreadOnly : false
				}).show();
			},
			/*// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/customer/multiDelInvestIntention.do',
							ids : id,
							grid : this.gridPanel
						});
			},*/
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/customer/multiDelInvestIntention.do',
							grid : this.gridPanel,
							idName : 'intentId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					record = s[0]
					new InvestIntentionForm({
							intentId : record.data.intentId,
							pername : record.data.investPersonName,
							isAllreadOnly : false
						}).show();
				}
				
			},
			//查看
			lookRs : function(record) {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					record = s[0]
					new InvestIntentionForm({
							intentId : record.data.intentId,
							isAllreadOnly : true
						}).show();
				}
			}
		});
