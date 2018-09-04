/**
 * @author
 * @class PlManageMoneyPlanView
 * @extends Ext.Panel
 * @description [PlManageMoneyPlan]管理
 * @company 智维软件
 * @createtime:
 */
UPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		UPlanView.superclass.constructor.call(this, {
					id : 'UPlanView'+this.keystr,
					title : this.keystr=='UPlan'?'U计划制定':'D计划制定',
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
					id : 'UPlanViewSearchPanel'+this.keystr,
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor : '95%'

						},
						items : [{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 100,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel :this.keystr=='UPlan'? 'U计划编号':'D计划编号',
										name : 'Q_mmNumber_S_LK',
										xtype : 'textfield'
									}]
		                },{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 100,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel :this.keystr=='UPlan'? 'U计划名称':'D计划名称',
										name : 'Q_mmName_S_LK',
										xtype : 'textfield'
									}]
		                }, {
							columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}]
						},{
							columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
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
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}]
				});
        var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			 plugins : [summary],
			singleSelect:true,
			// 使用RowActions
			id : 'UPlanGrid'+this.keystr,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyPlan.do?Q_keystr_S_EQ="+this.keystr,
			fields : [{
						name : 'mmplanId',
						type : 'int'
					}, 'mmName', 'mmNumber', 'manageMoneyTypeId', 'keystr',
					'investScope', 'benefitWay', 'buyStartTime', 'buyEndTime',
					'startMoney', 'riseMoney', 'limitMoney',
					'startinInterestCondition', 'expireRedemptionWay',
					'chargeGetway', 'guaranteeWay', 'yeaRate', 'investlimit',
					'sumMoney', 'state', 'startinInterestTime',
					'endinInterestTime', 'investedMoney', 'bidRemark',
					'htmlPath', 'createtime', 'updatetime','lockingLimit'],
			columns : [{
						header : 'mmplanId',
						dataIndex : 'mmplanId',
						align:'center',
						hidden : true
					}, {
						header : '计划编号',
						dataIndex : 'mmNumber'
					}, {
						header : '计划名称',
						 summaryRenderer : totalMoney,
						dataIndex : 'mmName'
					}, {
						header : '计划金额(元)',
						dataIndex : 'sumMoney',
					    align : 'right',
					    summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
						
					}, {
						header : '已购买总金额（元）',
						dataIndex : 'investedMoney',
					    align : 'right',
					    summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
						
					}, {
						header : '预期年化收益率',
						dataIndex : 'yeaRate',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"%";
						}
					}, {
						header : '投资期限（个月）',
						dataIndex : 'investlimit',
						align : 'center',
						renderer:function(v){
							return v;
						}
					},{
						header : '锁定期限（个月）',
						dataIndex : 'lockingLimit',
						align : 'center',
						renderer:function(v){
							return v;
						}
					},  {
						header : '购买放开时间',
						align:'center',
						dataIndex : 'buyStartTime'
					}, {
						header : '购买截止时间',
						align:'center',
						dataIndex : 'buyEndTime'
					}, {
						header : '创建时间',
						align:'center',
						dataIndex : 'createtime'
					}]
				// end of columns
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
		var gridPanel=this.gridPanel;
	    var win= new UPlanForm({
			keystr:this.keystr,
			isHidden:true,
			isAllReadOnly:false,
			startinInterestCondition : '截止日起息',
			investScope : '机构担保标、实地认证标',
			guaranteeWay : '100%本金保障计划',
			expireRedemptionWay : '系统将通过债权转让自动完成退出，您所持债权出售完成的具体时间，视债权转让市场交易情况而定。',
			seeHidden :true
		});
		win.show();
	    win.on("close",function(){
         gridPanel.getStore().reload();
     });
	},
	
	// 把选中ID删除
	removeSelRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var mmplanId=s[0].data.mmplanId;
		Ext.MessageBox.confirm('确认办理','您确认要删除所选记录吗？', function(btn){
			if (btn == 'yes') {
				if(s[0].data.state==0){
					Ext.Ajax.request({
	                    url:  __ctxPath + '/creditFlow/financingAgency/multiDelUPlanPlManageMoneyPlan.do?mmplanId='+mmplanId,
	                    method : 'POST',
	                    success : function(response,request) {
							var obj=Ext.util.JSON.decode(response.responseText);
                    		Ext.ux.Toast.msg('操作信息',obj.msg);
                        }
			        });
				}else{
					Ext.ux.Toast.msg('操作信息', '已经发布的不能删除！');
				}
			}
		})
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
			if(s[0].data.state>0){
			 Ext.ux.Toast.msg('操作信息','该计划已经发布不能进行编辑！只能查看。');
			 new UPlanForm({
						mmplanId : s[0].data.mmplanId,
						isAllReadOnly:true,
						isHidden:true,
						seeHidden :true,
						keystr:this.keystr
		 }).show();
			}else{
				new UPlanForm({
						mmplanId : s[0].data.mmplanId,
						isAllReadOnly:s[0].data.state==0?false:true,
						isHidden:true,
						keystr:this.keystr,
						seeHidden :true
		 }).show();
			}
			
		}
	}
	
});
