/**
 * @author 
 * @createtime 
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
EndDivertPanelDetail =Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				EndDivertPanelDetail.superclass.constructor.call(this, {
							id : 'EndDivertPanelDetailWin',
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 245,
							width : 610,
//							maximizable : true,
							title : '本金挪用详情',
							buttonAlign : 'center',
							buttons : [
									 {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				var punishAccrual=this.record.data.accrual*4;
				var incomeMoney=this.record.data.projectMoney*4;
				this.formPanel = new Ext.Panel({
					 layout:'column',
								bodyStyle : 'padding:10px',
								autoScroll : true,
								monitorValid : true,
								frame : true,
							    plain : true,
							   labelAlign : "right",
								// id : 'SlFundQlideForm',
								defaults : {
									anchor : '96%',
									labelWidth : 85,
									columnWidth : 1,
								    layout : 'column'
								},
								items : [  {
									     columnWidth : 0.5,
									     layout : 'form',
										 items :[
										 	{html:"挪用金额："+(this.record.data.incomeMoney==null?"":this.record.data.incomeMoney) },
										 	{style:'margin-top:5px',html:"挪用罚息开始日期：" +(this.record.data.intentDate==null?"": this.record.data.intentDate)},
										 	{style:'margin-top:5px',html:"累计罚息："+(this.record.data.accrualMoney==null?"":this.record.data.accrualMoney) }
										 	
										]}
								  , {
									     columnWidth : 0.5,
									     layout : 'form',
										 items :[
										 	{html:"罚息利率:"+(this.record.data.punishAccrual==null?"":this.record.data.punishAccrual) },
										 	{style:'margin-top:5px',html:"累计天数：" +(this.record.data.punishDays==null?"":this.record.data.punishDays) },
										 	{style:'margin-top:5px',html:"启动人：" +(this.record.data.startUserName==null?"": this.record.data.startUserName)}
										]}
								  , {
									     columnWidth : 1,
									     layout : 'form',
										 items :[
										 	{style:'margin-top:5px',html:"启动说明：" +(this.record.data.remark==null?"": this.record.data.remark)}
										 	
										]}
								  , {
									     columnWidth : 1,
									     layout : 'form',
										 items :[
										 	{style:'margin-top:15px',html:"终止日期：" +(this.record.data.factDate==null?"": this.record.data.factDate)}
										 	
										]}
								  , {
									     columnWidth : 1,
									     layout : 'form',
										 items :[
										 	{style:'margin-top:5px',html:"终止说明：" +(this.record.data.remark1==null?"": this.record.data.remark1)}
										 	
										]}
							 ]})
				
				//加载表单对应的数据	
				
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				
				this.formPanel.getForm().submit({
					method : 'POST',
					scope :this,
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					url : __ctxPath + '/creditFlow/finance/endDivertSlFundIntent.do',
					success : function(form ,action) {
							Ext.ux.Toast.msg('操作信息', '成功信息保存！');
							this.close();
					},
					failure : function(form ,action) {
						 Ext.MessageBox.show({
			            title : '操作信息',
			            msg : '信息保存出错，请联系管理员！',
			            buttons : Ext.MessageBox.OK,
			            icon : 'ext-mb-error'
			        });
					}
				})
			}//end of save

		});

