/**
 * @author
 * @createtime
 * @class BpFinanceApplyForm
 * @extends Ext.Window
 * @description BpFinanceApply表单
 * @company 智维软件
 */
BpFinanceApplyForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				BpFinanceApplyForm.superclass.constructor.call(this, {
							id : 'BpFinanceApplyFormWin',
					        buttonAlign:'center',
					        title:(this.applyCom==1)?'借款意向申请信息':'借款意向申请信息',
					        iconCls : 'btn-readdocument',
							width : 600,
							height : 330,
							constrainHeader : true ,
							collapsible : true, 
							frame : true ,
							border : false ,
							resizable : true,
							layout:'fit',
							autoScroll : true ,
							bodyStyle:'overflowX:hidden',
							constrain : true ,
							closable : true,
							modal : true,
							items : [this.formPanel],
							buttons : [/*{
										text : '受理',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '转正式客户',
										iconCls : 'btn-reset',
										scope : this,
										handler : this.save
									}, */{
										text : '关闭',
										iconCls : 'close',
										scope : this,
										handler : this.cancel
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
					monitorValid : true,
					bodyStyle:'padding:10px',
					autoScroll : true ,
					labelAlign : 'right',
					buttonAlign : 'center',
					frame : true ,
					border : false,
					layout : 'column',
					labelWidth:60,
		
							items : [{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [/*{
											xtype : "textfield",
											fieldLabel : '产品名称',
											name : 'bpFinanceApply.productName',
											allowBlank : false,
											readOnly : true,
											anchor : '100%'
										}*/]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel :'企业名称',
											name : (this.applyCom==1)?'bpFinanceApply.businessName':'无',
											allowBlank : false,
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '联系人',
											name : 'bpFinanceApply.linkPersion',
											allowBlank : false,
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '手机号',
											name : 'bpFinanceApply.phone',
											allowBlank : false,
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '所在地区',
											name : 'bpFinanceApply.area',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '付息方式',
											name : 'bpFinanceApply.payIntersetWay',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '借款申请时间',
											name : 'bpFinanceApply.createTime',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '期望放款日期',
											name: 'bpFinanceApply.startTime',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '借款用途',
											name : 'bpFinanceApply.loanUse',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '期望贷款金额',
											name : 'bpFinanceApply.loanMoney',
											readOnly : true,
											anchor : '100%'
										}]
									}/*,{
										columnWidth : .2, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										border : false,
										items : [{
											fieldLabel : "<span style='margin-left:1px'>万元</span> ",
											labelSeparator : '',
											anchor : ""
										}]
									}*/,{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '期望贷款期限',
											name : 'bpFinanceApply.loanTimeLen',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype : "textfield",
											fieldLabel : '期望利率',
											name : 'bpFinanceApply.expectAccrual',
											readOnly : true,
											anchor : '100%'
										}]
									},{
							        	columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 90,
										labelAlign : 'right',
										items : [{
											xtype: 'textarea',
											fieldLabel : '备注',
											name : 'bpFinanceApply.remark',
											readOnly : true,
											anchor : '100%'
										}]
									}
							]
						});
				// 加载表单对应的数据
				if (this.loanId != null && this.loanId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath
										+ '/p2p/getBpFinanceApply.do?loanId='
										+ this.loanId,
								root : 'data',
								preName : 'bpFinanceApply'
							});
				}

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
			save : function(obj,e) {
				 
				var btest=obj.text;
				var state='';
				if(btest=="受理"){
					state='1';
				}else if(btest=="转正式客户"){
					state='3';
				}else if(btest=="拒绝"){
					state='2';
				}
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/p2p/updateBpFinanceApply.do?state='+state,
							params:{'loanId':this.loanId},
							callback : function(fp, action) {
								var gridPanel = Ext
										.getCmp('BpFinanceApplyGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});