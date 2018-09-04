withdrawCheck = Ext.extend(Ext.Panel, {
	titlePrefix : "",
	seniorHidden : false,
	Confirmhidden : false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		withdrawCheck.superclass.constructor.call(this, {
					id : 'withdrawCheck'+this.Type,
					title :"线上提现申请审核",
					region : 'center',
					layout : 'border',
					iconCls:"menu-finance",
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		var isShow = false;
		var rightValue =  isGranted('_investmentAccountDealInfoView_see_All');
		var isShop = isGranted('_investmentAccountDealInfoView_see_shop');
		if (RoleType == "control") {
			isShow = true;
		}
		
		var anchor = '100%';
		var typevalue=this.Type;
		this.searchPanel = new Ext.FormPanel({
					layout : 'column',
					region : 'north',
					border : false,
					height : 70,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding-top:20px',
					items : [{
								columnWidth : .2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											
											fieldLabel : '用户名',
											name : 'loginname',
											anchor : "100%",
											xtype : 'textfield'
										},{
											
											fieldLabel : '真实姓名',
											name : 'truename',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								columnWidth : .2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											
											fieldLabel : '联系电话',
											name : 'telphone',
											anchor : "100%",
											xtype : 'textfield'
										},{
											
											fieldLabel : '交易流水号',
											name : 'recordNumber',
											anchor : "100%",
											xtype : 'textfield'
										}/*,{
											
											fieldLabel : '身份证号码',
											name : 'cardcode',
											anchor : "100%",
											xtype : 'textfield'
										}*/]
							}/*,{
								columnWidth : .2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											
											fieldLabel : '交易流水号',
											name : 'recordNumber',
											anchor : "100%",
											xtype : 'textfield'
										},{					
												fieldLabel : '审核状态',
												name : 'dealRecordStatus',
												anchor : "100%",
												xtype : 'combo',
												mode : 'local',
												valueField : 'value',
												editable : false,
												displayField : 'item',
												store : new Ext.data.SimpleStore({
													fields : ["item","value"],
													data : [["未审核","7"], 
															["审核通过","2"],
															["审核驳回","3"]
														    ]
												}),
												triggerAction : "all"
												
										}]
							}*/,{
								columnWidth : .2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											
											fieldLabel : '交易起始时间',
											name : 'startDate',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield'
										},{
											fieldLabel : '交易截止时间',
											name : 'endDate',
											anchor : "100%",
											format : 'Y-m-d',
											xtype : 'datefield'
										}]
							},{
								columnWidth : .07,
								layout : 'form',
								border : false,
								items : [{
											text : '查询',
											xtype : 'button',
											scope : this,
											style : 'margin-left:20px',
											anchor : "100%",
											iconCls : 'btn-search',
											handler : this.search
										},{
											text : '重置',
											style : 'margin-left:20px;margin-top:5px',
											xtype : 'button',
											scope : this,
											anchor : "100%",
											iconCls : 'btn-reset',
											handler : this.reset
										}]
							}]
				});
		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-finish',
						text :'通过申请',
						xtype : 'button',
						scope : this,
						handler : this.passapplye
					} ,{
						iconCls : 'btn-pause',
						text :'驳回申请',
						xtype : 'button',
						scope : this,
						handler : this.cancelapply
					}]
		});
		this.gridPanel = new HT.GridPanel({
			// name : 'confirmRechargeGrid',
			region : 'center',
			tbar : this.topbar,
			root : 'result',
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			isautoLoad:true,
			url : __ctxPath+ "/creditFlow/creditAssignment/bank/withdrawCheckObAccountDealInfo.do?dealRecordStatus=7",
			fields : [{
						name : 'id',
						type : 'int'
					}, 'loginname', 'payMoney', 'truename','dealRecordStatus','recordNumber','createDate','cardcode','telphone'],

			columns : [{
						header : '用户名',
						align:'center',
						dataIndex : 'loginname'
					},{
						header : '真实姓名',
						align:'center',
						dataIndex : 'truename'
					},{
						header : '联系电话',
						align:'center',
						dataIndex : 'telphone'
					},{
						header : '身份证号码',
						align:'center',
						dataIndex : 'cardcode'
					},{
						header : '提现金额',
						align:'right',
						dataIndex : 'payMoney',
						renderer:function(v){
							return v+"元"
                        }
					},{
						header : '申请日期',
						align:'center',
						dataIndex : 'createDate'
					}, {
						header : '交易流水号',
						align:'left',
						dataIndex : 'recordNumber'
					}/*, {
						header : '审核状态',
						dataIndex : 'dealRecordStatus',
						renderer:function(v,a,r){
							if(Ext.isEmpty(v)){
								return "--";
							}else{
								if(v==1){
									return "交易等待中";
								}else if(v==2){
									return "审核已通过";
								}else if(v==3){
									return "审核已驳回";
								}else if(v==7){
									return "未审核";
								}
							}
                        }
				}*/]
		});
		/*this.gridPanel.addListener('afterrender', function() {
					this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
								msg : '正在加载数据中······,请稍候······',
								store : this.gridPanel.store,
								removeMask : true
							});
					this.loadMask1.show(); // 显示
				}, this);*/
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
	//通过申请
	passapplye:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var thisPanel = this.gridPanel;
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择要审核的记录!');
					return;
				} else if (selectRs.length > 10) {
					Ext.ux.Toast.msg('操作信息', '每次审核不能超过10条记录');
					return;
				} else {
					Ext.Msg.confirm("操作提示","是否确认审核选中的记录",function(btn){
						if(btn=="yes"){
							var strId="";
							for(var i=0;i<selectRs.length;i++){
								var record = selectRs[i];
								if(strId==""){
									strId=strId+record.data.id;
								}else{
									strId=strId+","+record.data.id;
								}
							}
							Ext.MessageBox.wait("正在向第三方提交处理，请稍后.....");
							Ext.Ajax.request({
								url: __ctxPath + '/pay/passApplyePay.do',
								method:"post",
								success : function(response, request){
									var obj=Ext.util.JSON.decode(response.responseText)
									Ext.ux.Toast.msg('操作信息', obj.msg);
									thisPanel.getStore().reload();
									Ext.MessageBox.hide();//解除锁屏
								},
								params : {
									id : strId
								}
							})
						}
					})
			}
	},
	//驳回申请
	cancelapply:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var thisPanel = this.gridPanel;
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择要审核的记录!');
					return;
				} else if (selectRs.length > 10) {
					Ext.ux.Toast.msg('操作信息', '每次审核不能超过25条记录');
					return;
				} else {
					Ext.Msg.confirm("操作提示","是否驳回选中的记录",function(btn){
						if(btn=="yes"){
							var strId="";
							for(var i=0;i<selectRs.length;i++){
								var record = selectRs[i];
								if(strId==""){
									strId=strId+record.data.id;
								}else{
									strId=strId+","+record.data.id;
								}
							}
							Ext.MessageBox.wait("正在提交处理，请稍后.....");
							Ext.Ajax.request({
								url: __ctxPath + '/pay/cancelapplyPay.do',
								method:"post",
								success : function(response, request){
									var obj=Ext.util.JSON.decode(response.responseText)
									Ext.ux.Toast.msg('操作信息', obj.msg);
									thisPanel.getStore().reload();
									Ext.MessageBox.hide();//解除锁屏
								},
								params : {
									id : strId
								}
							})
						}
					})
			}
	}
});