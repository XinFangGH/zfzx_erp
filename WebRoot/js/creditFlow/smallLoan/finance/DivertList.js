/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
DivertList =  Ext.extend(Ext.Window, {
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
				DivertList.superclass.constructor.call(this, {
							id : 'DivertListView',
							region : 'center',
							layout : 'border',
							modal : true,
							height :400,
							width : screen.width*0.55,
							maximizable : true,
							title : '本金挪用'+this.record.data.projectName,
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
                var labelsize=70;
				 var labelsize1=115;

				this.topbar = new Ext.Toolbar({
					items : [

					{
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushuisee_f_interestIncome_'+this.businessType)?false:true,
						handler : this.seeDetail
			
					}
					]
				});

				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					id : 'DivertListGrid',
					url : __ctxPath + "/creditFlow/finance/getlistbyfundTypeSlFundIntent.do?fundType=principalDivert&projectId="+this.record.data.projectId+"&businessType="+this.record.data.businessType,
					fields : [{
								name : 'fundIntentId',
								type : 'int'
							}, 'incomeMoney','intentDate',
							'payMoney', 'payInMoney', 'factDate',
							'afterMoney', 'notMoney',
							'businessType','projectId','companyId','fundType','fundTypeName',"accrualMoney",'punishDays','punishAccrual','remark','remark1','startUserName'],
					columns : [{
								header : 'fundIntentId',
								dataIndex : 'fundIntentId',
								hidden : true
							}, {
								header : '挪用金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 100,
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							
							}, {
								header : '罚息利率',
								dataIndex : 'punishAccrual',
								align : 'right',
								width : 100,
								renderer:function(v){
								return v+"%"
                         	     }
							
							}, {
								header : '开始日期',
								width : 100,
								dataIndex : 'intentDate',
								align : 'center'
						//		sortable:true
							}, {
								header : '截至日期',
								width : 100,
								dataIndex : 'factDate',
								renderer:function(v){
								if(v==null){
									return "今天"
								}
								return v;
								
							}
								
						//		sortable:true
							}, {
								header : '累计天数',
								dataIndex : 'punishDays',
								width : 80,
								align : 'right'
								
							}, {
								header : '罚息总额',
								width : 100,
								dataIndex : 'accrualMoney',
								align : 'right',
								renderer : function(value, metadata, record, rowIndex,
										colIndex){
								
								 		    return Ext.util.Format.number(value,',000,000,000.00')+"元"
							}
								
                         
							}, {
								header : '操作',
								width : 100,
								renderer : function(value, metadata, record, rowIndex,
										colIndex){
								
								if(record.data.factDate !=null){
						 		    return "已终止"
						 	    }else{
							         return "<a><u>"+"终止"+"</u></a>"
						 }																						
							}
							}
							
						]

						// end of columns
					});

				this.gridPanel.addListener('cellclick', this.cellClick);

			},
			cellClick : function(grid,rowIndex,columnIndex,e){
				if(9==columnIndex){
					var selRs = grid.getSelectionModel().getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}
					var record = grid.getSelectionModel().getSelected();
					var factDate = grid.getStore().getAt(rowIndex).get('factDate');
					var fundIntentId = grid.getStore().getAt(rowIndex).get('fundIntentId');
					var businessType=this.businessType;
					 if(factDate ==null){
					 	new EndDivertPanel({
					 		record:record,
					 	businessType:"all"
							}).show();
					 }
				
					
				
				
				}
		
	},
	seeDetail:function(){
		var grid=this.gridPanel;
		var selRs = grid.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var record = grid.getSelectionModel().getSelected();
		new EndDivertPanelDetail({
	 		record:record,
	 	businessType:"all"
			}).show();
	 }
		
	
		
		});
