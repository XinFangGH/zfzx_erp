obligationproductInfoView = Ext.extend(Ext.Panel, {
	/*rechargeLevel : 0,
	titlePrefix : "",*/
	hiddenInfo:false,
	hiddenMapping:false,
	hiddenActualCloseTime:true, //隐藏实际关闭时间列
	obligationState:"",//用来区分查找全部的债权产品还是未匹配的债权产品
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.managerType) != "undefined") {
			this.managerType =_cfg.managerType;
		}
		if(this.managerType=="Info"){
			this.titlePrefix="债权产品管理";
			this.hiddenMapping=true;
			
		}else if(this.managerType=="Mapping"){
			this.titlePrefix="债权产品匹配管理";
			this.hiddenInfo=true;
			this.obligationState=0;
		}else if(this.managerType=="closeObligation"){
			this.titlePrefix="可关闭的债权产品";
			this.obligationState=0;
		}else if(this.managerType=="openObligation"){
			this.titlePrefix="可激活的债权产品";
			this.hiddenActualCloseTime=false,
			this.obligationState=2;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		obligationproductInfoView.superclass.constructor.call(this, {
			id : 'obligationproductInfoView_'+this.managerType,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : "btn-tree-team17",
			items : [ this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:15px 10px 10px 10px',
            items : [{
            		xtype : 'hidden',
            		name:"obligationState",
            		value:this.obligationState
            },{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					hidden:this.hiddenInfo,
					border : false,
					items : [{
						fieldLabel : '项目编号',
						name : 'projectNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '产品名称',
						name : 'obligationName',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '产品编号',
						name : 'obligationNumber',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					hidden:this.hiddenMapping,
					border : false,
					items : [{
						fieldLabel : '借款金额',
						name : 'projectMoney',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					hidden:this.Confirmhidden,
					border : false,
					items : [{
						fieldLabel : '借款期限',
						name : 'payintentPeriod',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
	     			columnWidth :.1,
					layout : 'form',
					border : false,
					labelWidth :80,
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
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						width : 40,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		if(this.managerType=="Info"){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '添加产品',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_create_product' )? false: true,
					handler : this.addObligationProduct
				},{
					iconCls : 'btn-edit',
					text : '编辑产品信息',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_edit_product')? false: true,
					handler : this.editObligationProduct
				},{
					iconCls : 'btn-detail',
					text : '查看产品详细信息',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_productDetail')? false: true,
					handler : this.seeObligationProduct
				},{
					iconCls : 'btn-detail',
					text : '查看项目信息',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_projectDetail_1')? false: true,
					handler : function() {
						this.searchProjectInfo();
					}
				}]
			});
		}else if(this.managerType=="Mapping"){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '匹配投资人',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_match_investP')? false: true,
					handler : this.MappingInvestPerson
				},{
					iconCls : 'btn-detail',
					text : '匹配详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_match_detail_1')? false: true,
					handler :this.seeMappingObligationInfo
				}]
			});
		}else if(this.managerType=="closeObligation"){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '关闭债权产品',
					xtype : 'button',
					scope : this,
					handler : this.closeObligationProduct,
					hidden : isGranted('_close_obligation')? false: true
				},{
					iconCls : 'btn-detail',
					text : '匹配详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_match_detail_2')? false: true,
					handler :this.seeMappingObligationInfo
				},{
					iconCls : 'btn-detail',
					text : '查看项目信息',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_projectDetail_2')? false: true,
					handler : function() {
						this.searchProjectInfo();
					}
				}]
			});
		}else if(this.managerType=="openObligation"){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '激活债权产品',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_open_enchashmentList')? false: true,
					handler : this.openObligationProduct
				},{
					iconCls : 'btn-detail',
					text : '匹配详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_match_detail_2')? false: true,
					handler :this.seeMappingObligationInfo
				},{
					iconCls : 'btn-detail',
					text : '查看项目信息',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_projectDetail_2')? false: true,
					handler : function() {
						this.searchProjectInfo();
					}
				}]
			});
		}
			
			
		this.gridPanel = new HT.GridPanel({
			name : 'obligationproductInfoGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			//plugins : [checkColumn,seniorcheckColumn],
			rowActions : false,
			url : __ctxPath + "/creditFlow/creditAssignment/project/getListObObligationProject.do?obligationState="+this.obligationState,
			fields : [{
				name : 'id',
				type : 'int'
			}, 'projectId','projectNumber', 'obligationName', 'obligationNumber', 'projectMoney', 'intentDate',
					'payintentPeriod', 'minInvestMent', 'accrual','factEndDate',
					'mappingMoney', 'unmappingMoney'],
			listeners : {
				scope : this,
				afteredit : function(e) {}
			},

			columns : [{
				header : '项目编号',
				width : 100,
				hidden:this.hiddenInfo,
				dataIndex : 'projectNumber'
			}, {
				header : '债权产品编号',
				width : 130,
				dataIndex : 'obligationNumber'
			}, {
				header : '债权产品名称',
				width : 200,
				dataIndex : 'obligationName'
			},{
				header : '借款金额（元）',
				width : 100,
				dataIndex : 'projectMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00元";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '年化利率(%)',
				width : 100,
				hidden:this.hiddenInfo,
				dataIndex : 'accrual'
			},{
				header : '最小投资额（元）',
				width : 100,
				dataIndex : 'minInvestMent',
				hidden:this.hiddenInfo,
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "";
					}
				}
			}, {
				header : '自动关闭时间',
				align : 'right',
				width : 110,
				sortable : true,
				hidden:this.hiddenInfo,
				dataIndex : 'intentDate'
			}, {
				header : '已匹配金额（元）',
				width : 100,
				dataIndex : 'mappingMoney',
				hidden:this.hiddenMapping,
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "";
					}
				}
			}, {
				header : '未匹配金额（元）',
				width : 100,
				dataIndex : 'unmappingMoney',
				hidden:this.hiddenMapping,
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "";
					}
				}
			},{
				header : '实际关闭时间',
				align : 'right',
				width : 110,
				sortable : true,
				hidden:this.hiddenActualCloseTime,
				dataIndex : 'factEndDate'
			}]
		});
		/*this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
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
	//添加债权产品
	addObligationProduct:function(){
		var productId ="addObligationProduct";
		var idDefinition="addObligationProduct";
		var viewId ='obligationproductInfoGrid';
		var add_window =new ObligationProductInfo({
			unid:productId,
			title:"添加债权产品",
			idDefinition:idDefinition,
			viewId:viewId,
			gridPanel : this.gridPanel
		});
		add_window.show();
	},
	//编辑债权产品
	editObligationProduct:function(){
		var productId ="editObligationProduct";
		var idDefinition="editObligationProduct";
		var viewId ='obligationproductInfoView_'+this.managerType;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		} else{
			var  record=selectRs[0];
			var projectId =record.data.projectId;
			var obligationId =record.data.id;
			Ext.Ajax.request({
						url : __ctxPath + "/creditFlow/creditAssignment/project/checkEditObObligationProject.do",
						method : 'POST',
						scope:this,
						success :function(response, request){
							var object=Ext.util.JSON.decode(response.responseText);
							var flag =object.flag;//flag用来判断是否可以编辑：0表示可以，1表示不可以
							if(flag ==1){//不可以编辑，只能查看
								Ext.ux.Toast.msg('操作信息', '已经添加了投资人信息，只能查看!');
								var add_window =new ObligationProductInfo({
									unid:productId,
									projectId:projectId,
									obligationId:obligationId,
									idDefinition:idDefinition,
									title:"查看债权产品",
									isReadOnly:true,
									isAllReadOnly:true,
									viewId:viewId
								});
								add_window.show();
							}else{//可以编辑
								var add_window =new ObligationProductInfo({
									unid:productId,
									projectId:projectId,
									obligationId:obligationId,
									title:"编辑债权产品",
									idDefinition:idDefinition,
									isAllReadOnly:true,
									viewId:viewId,
									gridPanel : this.gridPanel
								});
								add_window.show();
							}
						},
						params : {
							'id':obligationId
						}
			})
		}
		
	},
	//关闭债权产品
	closeObligationProduct:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		}else{
			var  record=selectRs[0];
			var obligationId =record.data.id;
			Ext.Ajax.request({
				url : __ctxPath + "/creditFlow/creditAssignment/project/closeInvestProductObObligationProject.do",
				method : 'POST',
				scope:this,
				params : {
					'obligationId':obligationId
				},
				success :function(response, request){
					Ext.ux.Toast.msg('操作信息', '关闭债权产品成功!');
					//刷新表格
					this.gridPanel.store.reload();
				},
				failure : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '关闭债权产品失败!');
				}
			});
		}
	},
	//激活债权产品
	openObligationProduct:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		}else{
			var  record=selectRs[0];
			var obligationId =record.data.id;
			Ext.Ajax.request({
				url : __ctxPath + "/creditFlow/creditAssignment/project/openInvestProductObObligationProject.do",
				method : 'POST',
				scope:this,
				params : {
					'obligationId':obligationId
				},
				success :function(response, request){
					Ext.ux.Toast.msg('操作信息', '激活债权产品成功!');
					//刷新表格
					this.gridPanel.store.reload();
				},
				failure : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '激活债权产品失败!');
				}
			});
		}
	},
	//查看债权产品
	seeObligationProduct:function(){
		var productId ="seeObligationProduct";
		var idDefinition="seeObligationProduct";
		var viewId ='obligationproductInfoView_'+this.managerType;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		} else{
			var  record=selectRs[0];
			var projectId =record.data.projectId;
			var obligationId =record.data.id;
			var add_window =new ObligationProductInfo({
				unid:productId,
				projectId:projectId,
				obligationId:obligationId,
				idDefinition:idDefinition,
				isReadOnly:true,
				isAllReadOnly:true,
				viewId:viewId,
				isLook:true
			});
			add_window.show();
		}
	},
	//债权产品匹配投资人
	MappingInvestPerson:function(){
		var productId ="mappingObligationProduct";
		var idDefinition="mappingObligationProduct";
		var viewId ='obligationproductInfoView_'+this.managerType;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		} else{
			var  record=selectRs[0];
			var projectId =record.data.projectId;
			var obligationId =record.data.id;
			var add_window =new obligationInvestInfo({
				unid:productId,
				//projectId:projectId,
				title:"债权产品匹配投资人",
				obligationId:obligationId,
				idDefinition:idDefinition,
				isReadOnly:true,
				isAllReadOnly:true,
				viewId:viewId,
				gridPanel : this.gridPanel
			});
			add_window.show();
		}
	},
	//查看债权产品匹配详情
	seeMappingObligationInfo:function(){
		var productId ="seeMappingObligationProduct";
		var idDefinition="seeMappingObligation";
		var viewId ='obligationproductInfoView_'+this.managerType;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		} else{
			var  record=selectRs[0];
			var projectId =record.data.projectId;
			var obligationId =record.data.id;
			var add_window =new obligationInvestInfo({
				unid:productId,
				title:"查看债权产品匹配详情",
				obligationId:obligationId,
				idDefinition:idDefinition,
				isLook:true,
				isReadOnly:true,
				isAllReadOnly:true,
				isHiddenAddBtn:true,
				isHiddenDelBtn:true,
				isHiddensaveBtn:true,
				isHiddenseeBtn:true,
				isHiddenautocreateBtn:true,
				isHiddenExcel:true,
				viewId:viewId
			});
			add_window.show();
		}
	},
	searchProjectInfo:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
		} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
		} else{
			var record=selectRs[0];
			var projectId =record.data.projectId;
			Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/getProjectViewObjectCreditProject.do',
					params : {
						businessType : 'SmallLoan',
						projectId : projectId
					},
					method : 'post',
					success : function(resp, op) {
						var record = Ext.util.JSON.decode(resp.responseText);// JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
						var win=new Ext.Window({
							title : '项目基本信息',
							width :document.body.clientWidth-550,
							height : document.body.clientHeight -350,
							modal : true ,
							constrainHeader : true ,
							layout : 'fit',
							buttonAlign : 'center',
							resizable : false,
							items : [
								new obProjectInfo({
									record : record,
									bussinessType:'smallLoan'
								})
							]
						});
						win.show();
					},
					failure : function() {
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					}
			});
		}
	}
});
