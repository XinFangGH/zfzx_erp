/**
 * @author
 * @class InvestPersonView
 * @extends Ext.Panel
 * @description [InvestPerson]管理
 * @company 智维软件
 * @createtime:
 */
InvestPersonList = Ext.extend(Ext.Window, {
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
				InvestPersonList.superclass.constructor.call(this, {
							id : 'InvestPersonView',
							title : '选择线下投资客户',
							region : 'center',
							layout : 'border',
							width : 600,
							height : 400,
							items : [this.gridPanel]
						});
			},// end of constructor
			
			// 初始化组件
			initUIComponents : function() {
				var rightValue =  isGranted('_ffff');
				this.store = new Ext.data.JsonStore({
					url : __ctxPath
							+ '/creditFlow/creditAssignment/customer/listCareAndGrantCsInvestmentperson.do?isAll='
							+ rightValue,
					totalProperty : 'totalProperty',
					root : 'topics',
					remoteSort : true,
					fields : [{
						name : 'investId'
					}, {
						name : 'investName'
					}, {
						name : 'sexvalue'
					}, {
						name : 'cardtypevalue'
					}, {
						name : 'cardnumber'
					}, {
						name : 'cellphone'
					}, {
						name : 'shopId'
					}, {
						name : 'companyId'
					}, {
						name : 'shopName'
					},  {
						name : 'orgName'
					}, {
						name : 'accountNumber'
					},  {
						name : 'contractStatus'
					}, {
						name : 'changeCardStatus'
					}, {
						name : 'birthDay'
					},  {
						name : 'sex'
					}, {
						name : 'cardtype'
					}, {
						name : 'postaddress'
					}, {
						name : 'selfemail'
					}, {
						name : 'postcode'
					}]
				});
				
							// 加载数据
					this.store.load({
								scope : this,
								params : {
									start : 0,
									limit : this.pageSize,
									isAll : isGranted('_detail_sygrkh')
								}
							});
				// 初始化搜索条件Panel
				this.gridPanel = new HT.GridPanel({
					region : 'center',
					// 使用RowActions
					store : this.store,
					id : 'InvestPersonGrid',
					/*url : __ctxPath
					+ '/creditFlow/creditAssignment/customer/queryInvestmentPersonCsInvestmentperson.do?isAll='
					+ rightValue,
					totalProperty : 'totalProperty',
					root : 'topics',*/
					columns : [{
								header : 'investId',
								dataIndex : 'investId',
								hidden : true
							}, {
								header : '姓名',
								dataIndex : 'investName'
							}, {
								header : '性别',
								dataIndex : 'sexvalue'
							}, {
								header : '证件类型',
								dataIndex : 'cardtypevalue'
							}, {
								header : '证件号码',
								dataIndex : 'cardnumber'
							}, {
								header : '手机号码',
								dataIndex : 'cellphone'
							}, {
								header : '出生日期',
								format : 'Y-m-d',
								dataIndex : 'birthDay'

							}, {
								header : '邮箱',
								dataIndex : 'selfemail'
							}, {
								header : '通讯地址',
								dataIndex : 'postaddress'
							}]
						// end of columns
					});

				this.gridPanel.addListener('rowdblclick', this.rowClick);

			},// end of the initComponents()
			// GridPanel行点击处理事件
			rowClick : function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					var investPersonId = Ext.getCmp('investPersonId');
					investPersonId.setValue(rec.data.investId);
					var perId = Ext.getCmp('investId');
					perId.setValue(rec.data.investId);
					var perName = Ext.getCmp('investPerName');
					perName.setValue(rec.data.investName);
					var perSex = Ext.getCmp('investPerSex');
					perSex.setValue(rec.data.sex);
					var cardType = Ext.getCmp('investCardType');
					cardType.setValue(rec.data.cardtype);
					var cardNumber = Ext.getCmp('investCardNumber');
					cardNumber.setValue(rec.data.cardnumber);
					var phoneNumber = Ext.getCmp('investPhoneNumber');
					phoneNumber.setValue(rec.data.cellphone);
					var perBirthday = Ext.getCmp('investPerBirthday');
					perBirthday.setValue(rec.data.birthDay);
					var investPostAddress = Ext.getCmp('investPostAddress');
					investPostAddress.setValue(rec.data.postaddress);
					var investPostCode = Ext.getCmp('investPostCode');
					investPostCode.setValue(rec.data.selfemail);

					})
				this.close();

			} /*
				 * function(grid, rowindex, e) {
				 * grid.getSelectionModel().each(function(rec) { return
				 * rec.data; alert(rec.data.perId); }); }
				 */
		});
