/**
 * @author
 * @class InvestPersonView
 * @extends Ext.Panel
 * @description [InvestPerson]管理
 * @company 智维软件
 * @createtime:
 */
selectInvestPersonList = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(_cfg.formPanel != null && _cfg.formPanel != 'underfined'){
					this.formPanel = _cfg.formPanel;
				}
				/*
				 * if(_cfg.getPerson!=null && _cfg.getPerson!='undefined'){
				 * this.getPerson = _cfg.getPerson; }
				 */
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				selectInvestPersonList.superclass.constructor.call(this, {
							id : 'InvestPersonView',
							title : '选择个人客户',
							region : 'center',
							layout : 'border',
							width : 600,
							height : 400,
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var fp=this.formPanel;
				var object=this;
				this.gridPanel = new HT.GridPanel({
					region : 'center',
					// 使用RowActions
					id : 'InvestPersonGrid',
					url : __ctxPath + "/creditFlow/creditAssignment/customer/listCsInvestmentperson.do",
					fields : [{
								name : 'investId',
								type : 'int'
							}, 'cardnumber', 'cardType',  'investName',
							'sex', 'cellphone'],
					columns : [{
								header : 'investId',
								dataIndex : 'investId',
								hidden : true
							}, {
								header : '姓名',
								dataIndex : 'investName'
							}, {
								header : '性别',
								dataIndex : 'sex',
								renderer:function(value){
									if(value=="313"){
										return "女";
									}else if(value=="312"){
										return "男";
									}
								}
							}, {
								header : '证件类型',
								dataIndex : 'cardtype'
							}, {
								header : '证件号码',
								dataIndex : 'cardnumber'
							}, {
								header : '手机号码',
								dataIndex : 'cellphone'
							}],
							listeners : {
				'rowdblclick' : function(grid, rowIndex, e) {
					var object = grid.ownerCt;
					grid.getSelectionModel().each(function(rec) {
						var investId = fp.getCmpByName('investPersonId');
						investId.setValue(rec.data.investId);
						var investName = fp.getCmpByName('investPersonName');
						investName.setValue(rec.data.investName);
					
			        	var orderId=fp.getCmpByName('orderId');
						var arrStore=new Ext.data.ArrayStore({
							url : __ctxPath
										+ "/creditFlow/financingAgency/getorderListPlManageMoneyPlanBuyinfo.do?investPersonId="+rec.data.investId,
							fields : ['itemId', 'itemName'],
							autoLoad : true
						});
						
		                orderId.store = arrStore;
						if (orderId.view) { // 刷新视图,避免视图值与实际值不相符
							orderId.view.setStore(orderId.store);
						}
	                	orderId.clearValue();
						

					})
					object.close();

					}
				  }
						// end of columns
					});


			}// end of the initComponents()
			
		});
