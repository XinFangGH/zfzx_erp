PlPersionDirProKeepView = Ext.extend(Ext.Panel, {
	/*
	 * rechargeLevel : 0, titlePrefix : "",
	 */
	hiddenInfo : false,
    querysql:"&Q_bpPersionDirPro.loanId_NULL", //查询条件，默认查询线下的个人直投项目
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
				_cfg = {};
		}
		if (typeof(_cfg.managerType) != "undefined") {
			this.managerType = _cfg.managerType;
		}
		if (this.managerType == "successProduct") {
			this.titlePrefix = "已维护";
			this.hiddenInfo = true;

		}
		
		if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="onlineopen") {//查询线上借款生成的直投项目
			this.querysql="&Q_bpPersionDirPro.loanId_NOTNULL";
			this.subType= _cfg.subType;
		}else if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="all") {//查询全部的直投项目
			this.querysql="";
				this.subType= _cfg.subType;
		}
		Ext.applyIf(this, _cfg);

		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PlPersionDirProKeepView.superclass.constructor.call(this, {
					id : 'PlPersionDirProKeepView' + this.managerType+this.subType,
					title : this.titlePrefix,
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
					height :40,
					items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'Q_bpPersionDirPro.persionName_S_LK',
							anchor : '100%'
						}]
						
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '项目名称',
							name : 'Q_bpPersionDirPro.proName_S_LK',
							anchor : '100%'
						}]
						
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '项目编码',
							name : 'Q_bpPersionDirPro.proNumber_S_LK',
							anchor : '100%'
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						style : 'margin-left:20px',
						items : [{
							xtype : 'button',
							text : '查询',
							scope : this,
							iconCls : 'btn-search',
							handler : this.search
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						items : [{
							xtype : 'button',
							text : '重置',
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [ {
									iconCls : 'btn-protect',
									text : '公示信息维护',
									xtype : 'button',
									scope : this,
									hidden : isGranted('persionDirProKeep')?false:true,
									handler : this.projectKeep
								}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			//rowActions : true,
			id : 'PlDirProKeepGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/persion/listPlPersionDirProKeep.do?sort=updateDate&dir=desc&Q_proType_S_EQ="+this.type+this.querysql,
			fields : [{
						name : 'keepId',
						type : 'int'
					}, 'pDirProId', {
						name : 'typeId',
						mapping : 'plKeepProtype.name'
					}, {
						name : 'proName',
						mapping : 'bpPersionDirPro.proName'
					}, {
						name : 'proNumber',
						mapping : 'bpPersionDirPro.proNumber'
					}, {
						name : 'proId',
						mapping : 'bpPersionDirPro.proId'
					}, {
						name : 'creditLevelId',
						mapping : 'plKeepCreditlevel.name'
					}, 'proDes', 'proUseWay', 'proPayMoneyWay',
					'proRiskCtr', 'proBusinessScope', 'proBusinessStatus',/* {
						name : 'proGtOrzId',
						mapping : 'plKeepGtorz.name'
					},*/ 'proGtOrjIdea', 'proLoanMaterShow',
					'proLoanLlimitsShow', 'proLoanLevelShow', 'createDate',
					'updateDate','bpPersionDirPro'],
			columns : [{
						header : 'keepId',
						dataIndex : 'keepId',
						align:'center',
						hidden : true
					}, {
						header : '项目id',
						align:'center',
						dataIndex : 'proId',
						hidden : true
						
					}, {
						header : '缓存标项目id',
						align:'center',
						dataIndex : 'pDirProId',
						hidden : true
						
					}, {
						header : '项目名称',
						dataIndex : 'proName'
						
					}, {
						header : '项目编号',
						dataIndex : 'proNumber'
						
					}, {
						header : '借款项目类型',
						align:'center',
						dataIndex : 'typeId'
					}, {
						header : '信用等级',
						align:'center',
						dataIndex : 'creditLevelId'
					}, /*{
						header : '担保机构',
						dataIndex : 'proGtOrzId'
					},*/  {
						header : '创建时间',
						align:'center',
						dataIndex : 'createDate',
						format:'Y-m-d'
					}, {
						header : '修改时间',
						align:'center',
						dataIndex : 'updateDate',
						format:'Y-m-d'
					}/*, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								actions : [ {
											iconCls : 'btn-edit',
											qtip : '编辑',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})*/]
				// end of columns
		});
	this.gridPanel.addListener('rowdblclick', this.rowClick);

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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlDirProKeepForm({
								keepId : rec.data.keepId
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new PlDirProKeepForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/financingAgency/persion/multiDelPlPersionDirProKeep.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/persion/multiDelPlPersionDirProKeep.do',
			grid : this.gridPanel,
			idName : 'keepId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		
		new PlPersionDirProKeepForm({
			       keep :true,
					keepId : record.data.keepId,
					gp:this.gridPanel,
					record:record
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.keepId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	},
		projectKeep : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var stype =this.subType;
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			if(typeof(stype) != undefined && 'onlineopen' == stype){
					Ext.Ajax.request({
									url : __ctxPath + '/p2p/getBpFinanceApplyUser.do?loanId='+record.data.bpPersionDirPro.loanId,
									method:'post',
									async:false,
									success: function(resp,opts) {
										var respText = resp.responseText;
										var alarm_fields = Ext.util.JSON.decode(respText);
										var data=alarm_fields.data;
									    	 new PlPersionDirProKeepOnlineForm({
					                             gp:this.gridPanel,
					                             record:record,// 个人直投项目信息
					                             keepId : record.data.keepId,
					                             keep:true,
					                             proType:"P_Dir",
					                             userId:data.userID,
					                             productId:data.productId,
					                             proIdupload:record.data.bpPersionDirPro.pdirProId,
				                                 tablename:"bp_persion_dir_pro"
					                           }).show();
									}
								});
				
			
			}else{
			 	new PlPersionDirProKeepForm({
				    gp:this.gridPanel,
				    keepId : record.data.keepId,
				    record:record,// 个人直投项目信息
				    keep:true,
				    proType:"P_Dir",
				    proIdupload:record.data.bpPersionDirPro.pdirProId,
				    tablename:"bp_persion_dir_pro"
				}).show();
			
			}
		
		}

	}
});
