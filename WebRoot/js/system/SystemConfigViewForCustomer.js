/**
 * @author
 * @createtime
 * @class GuofubaoForm
 * @extends Ext.Window
 * @description Guofubao表单
 * @company 智维软件
 */
SystemConfigViewForCustomer = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}

				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SystemConfigViewForCustomer.superclass.constructor.call(this, {
							id : 'SystemConfigViewForCustomerWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 300,
							width : 500,
							iconCls:"menu-flowManager",
							maximizable : true,
							title : '系统参数配置',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
										scope : this,
										handler : this.cancel
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				//计息周期方式
				var interestComboBox = new Ext.form.ComboBox({
					       id:'interestCbx',
							fieldLabel : '计息周期方式',
							store:          new Ext.data.JsonStore({
                                    fields : ['name', 'value'],
                                    data   : [
                                        {name : '算头不算尾',   value: '0'},
                                        {name : '算头又算尾',  value: '1'}
                                    ]
                                }),
							valueField : 'value',
							displayField : 'name',
							typeAhead : true,
							mode : 'local',
							triggerAction : 'all',
							emptyText : 'Select...',
							selectOnFocus : true,
							width : 190
						});
						
						//是否按实际天数算
				var isActualDayComboBox = new Ext.form.ComboBox({
					        id:'isActualDayCbx',
							fieldLabel : '计息方式',
							store:          new Ext.data.JsonStore({
                                    fields : ['name', 'value'],
                                    data   : [
                                        {name : '按实际天数算',   value: 'yes'},
                                        {name : '按30天算',  value: 'no'}
                                    ]
                                }),
							valueField : 'value',
							displayField : 'name',
							typeAhead : true,
							mode : 'local',
							triggerAction : 'all',
							emptyText : 'Select...',
							selectOnFocus : true,
							width : 190
						});
							//罚息收取方式
				var defaultInterestComboBox = new Ext.form.ComboBox({
					        id:'defaultInterestCbx',
							fieldLabel : '罚息收取方式',
							store:          new Ext.data.JsonStore({
                                    fields : ['name', 'value'],
                                    data   : [
                                        {name : '剩余金额',   value: '0'},
                                        {name : '全部金额',  value: '1'}
                                    ]
                                }),
							valueField : 'value',
							displayField : 'name',
							typeAhead : true,
							mode : 'local',
							triggerAction : 'all',
							emptyText : 'Select...',
							selectOnFocus : true,
							width : 190
						});
				var radioGroup = new Ext.form.RadioGroup({
							fieldLabel : "radioGroup",
							disabled:true,
							items : [{
										boxLabel : '是',
										inputValue : "1",
										name : "rg"
										//id : "rg1"

									}, {
										boxLabel : '否',
										name : "rg",
										//id : "rg2",
										inputValue : "2",
										checked : true
									}]
						});
				var radioGroupIsOA = new Ext.form.RadioGroup({
							fieldLabel : "radioGroup",
							disabled:true,
							items : [{
										boxLabel : '是',
										inputValue : "1",
										name : "oa"

									}, {
										boxLabel : '否',
										name : "oa",
										inputValue : "2",
										checked : true
									}]
						});
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							// id : 'GuofubaoForm',
							defaults : {
								anchor : '56%,56%'
							},
							defaultType : 'textfield',
							items : [/*{
										fieldLabel : '系统名字',
										allowBlank : false,
										blankText : "网关版本号不能为空，请正确填写!",
										hidden : true,
										hideLabel : true,
										name : 'systemConfig.systemName',
										maxLength : 500
									}, */{
										fieldLabel : '系统版本号',
										allowBlank : false,
										blankText : "系统版本号不能为空，请正确填写!",
										hidden : true,
										hideLabel : true,
										name : 'systemConfig.systemVersion',
										maxLength : 100
									}, {
										fieldLabel : '是否企业版',
										xtype : 'radiogroup',
										anchor : '100%',
										layout : "column",
										hidden : true,
										hideLabel : true,
										items : [radioGroup]
									}, {
										fieldLabel : '是否提供OA',
										xtype : 'radiogroup',
										anchor : '100%',
										layout : "column",
										hidden : true,
										hideLabel : true,
										items : [radioGroupIsOA]
									}, {
										fieldLabel : '宽限日',
										name : 'systemConfig.graceDayNum',
										maxLength : 10
									}, {
										fieldLabel : 'FTP地址',
										allowBlank : false,
										blankText : "FTP地址不能为空，请正确填写!",
										name : 'systemConfig.ftpIp',
										hidden : true,
										hideLabel : true,
										maxLength : 100
									}, {
										fieldLabel : 'FTP账户',
										allowBlank : false,
										blankText : "FTP账户不能为空，请正确填写!",
										name : 'systemConfig.ftpUsName',
										hidden : true,
										hideLabel : true,
										maxLength : 100
									}, {
										fieldLabel : 'FTP密码',
										allowBlank : false,
										blankText : "FTP密码不能为空，请正确填写!",
										name : 'systemConfig.ftpPss',
										hidden : true,
										hideLabel : true,
										maxLength : 256
									}, {
										fieldLabel : 'FTP端口号',
										allowBlank : false,
										blankText : "FTP端口号不能为空，请正确填写!",
										name : 'systemConfig.ftpPort',
										hidden : true,
										hideLabel : true,
										maxLength : 10
									}, {
										fieldLabel : '生成压缩文件路径',
										allowBlank : false,
										blankText : "生成压缩文件路径不能为空，请正确填写!",
										name : 'systemConfig.zipOutPutPath',
										hidden : true,
										hideLabel : true,
										maxLength : 200
									}, interestComboBox
									,defaultInterestComboBox ,
									isActualDayComboBox, {
										xtype : 'numberfield',
										fieldLabel : '企贷节点流转控制金额',
										allowBlank : false,
										blankText : "企贷节点流转控制金额不能为空，请正确填写!",
										name : 'systemConfig.controlMoney',
										maxLength : 200
									} 
									]
						});
				// 加载表单对应的数据

				this.formPanel.loadData({
							url : __ctxPath + '/system/getSystemConfig.do',
							root : 'data',
							preName : ['description', 'systemConfig'],
							success : function(response, options) {
								var respText = response.responseText;
								var syscon = Ext.util.JSON.decode(respText);
								if (syscon.data.systemConfig.isGroupVersion == "true") {
									radioGroup.setValue(1);
								} else {
									radioGroup.setValue(2);
								}

								if (syscon.data.systemConfig.isOA == "true") {
									radioGroupIsOA.setValue(1);
								} else {
									radioGroupIsOA.setValue(2);
								}
								if (syscon.data.systemConfig.interest !=null) {
									interestComboBox.setValue(syscon.data.systemConfig.interest);
								} 
								if (syscon.data.systemConfig.isActualDay !=null) {
									isActualDayComboBox.setValue(syscon.data.systemConfig.isActualDay);
								} 
								if (syscon.data.systemConfig.defaultInterest !=null) {
									defaultInterestComboBox.setValue(syscon.data.systemConfig.defaultInterest);
								} 
								

							}
						});

			},// end of the initcomponents

			/**
			 * 重置
			 * 
			 * @param {}
			 *            formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				var gv = this.formPanel.getForm().getValues()["rg"];
				var oa = this.formPanel.getForm().getValues()["oa"];
				var insterest=Ext.getCmp('interestCbx').getValue();
				var isActualDay=Ext.getCmp('isActualDayCbx').getValue();
				var defaultInterest=Ext.getCmp('defaultInterestCbx').getValue();
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/system/saveSystemConfig.do',
							params : {
								isGroupVersion : gv,
								isOA : oa,
								insterest:insterest,
								defaultInterest:defaultInterest,
								isActualDay:isActualDay

							}
							/*callback : function(fp, action) {
							 可执行回调函数
 
							}*/
						});
			}// end of save

		});