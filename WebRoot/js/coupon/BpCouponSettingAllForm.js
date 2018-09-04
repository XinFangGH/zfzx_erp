/**
 * @author 
 * @createtime 
 * @class BpCouponSettingAllForm
 * @extends Ext.Window
 * @description BpCouponSetting表单
 * @company 智维软件
 */
BpCouponSettingAllForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCouponSettingAllForm.superclass.constructor.call(this, {
							id : 'BpCouponSettingAllFormWin',
							layout : 'anchor',
							items : [this.formPanel,this.gridPanel],
							modal : true,
							height : 600,
							width : 800,
							maximizable : true,
							title : '优惠券批量派发',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}, {
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
				var leftlabel = 100;
				var rightlabel = 100;
				this.formPanel = new Ext.form.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
							anchor : '96%',
							columnWidth : 1,
							labelWidth : 60
						},
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
										xtype : 'hidden',
										name : 'bpCouponSetting.categoryId'
								},{
										xtype : 'hidden',
										name : 'setPattern'
								},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{					
												fieldLabel : '优惠券类型',
												hiddenName : 'bpCouponSetting.couponType',
												anchor : "100%",
												allowBlank : false,
												readOnly : true,
												xtype : 'combo',
												mode : 'local',
												valueField : 'value',
												editable : false,
												displayField : 'item',
												store : new Ext.data.SimpleStore({
													fields : ["item","value"],
													data : [["优惠券","1"], 
															/*["体验券","2"],*/
															["加息券","3"]
														    ]
												}),
												triggerAction : "all"
												
										},{
										  	xtype:"hidden",
										  	name : 'bpCouponSetting.couponTypeValue'
										}]
								},{
										columnWidth : .45, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'numberfield',
													fieldLabel : '面值',
													name : 'bpCouponSetting.couponValue',
													allowBlank : false,
													anchor : '100%',
													blankText : '面值为必填内容',
													readOnly : true
												}]
							
									},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "元",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
						            	columnWidth:0.45,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '张数',
												name : 'bpCouponSetting.counponCount',
												allowBlank : false,
												anchor : '100%',
												blankText : '张数',
												readOnly : true
											}]
					            	},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "张",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
										columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "优惠券有效期",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "bpCouponSetting.couponStartDate",
													allowBlank : false,
													readOnly : true,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d'
												}]
									},{
										columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "至",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "bpCouponSetting.couponEndDate",
													allowBlank : false,
													readOnly : true,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d'
												}]
									},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textarea',
													fieldLabel : '描述',
													name : 'bpCouponSetting.couponDescribe',
													anchor : '100%',
													blankText : '描述',
													readOnly :true
												}]
							
										},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<br>【<b>派发方式</b>】：<font color='red'>注：系统派发,无需用户激活。短信、邮件、制卡派发需用户自己激活</font><br>"
										}]
								},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<br>"
										}]
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [
										{
											xtype : 'radiogroup',
											fieldLabel : '',
											name : 'bpCouponSetting.setPattern',
											items : [{
												boxLabel : '系统派发',
												name : 'bpCouponSetting.setPattern',
												checked:true,
												inputValue : "1",
												 listeners : {
										                        "check" : function(el, checked) {
										                            if (checked) {
										                                Ext.getCmp("couponsNum").hide();
																		Ext.getCmp("SlFundQlideGridcheck").show();
										                            }
										                        }
										                    }
											}, {
												boxLabel : '短信派发',
												name : 'bpCouponSetting.setPattern',
												inputValue : "2",
												 listeners : {
										                        "check" : function(el, checked) {
										                            if (checked) {
										                                Ext.getCmp("couponsNum").hide();
																		Ext.getCmp("SlFundQlideGridcheck").show();
										                            }
										                        }
										                    }
											},{
												boxLabel : '邮件派发',
												name : 'bpCouponSetting.setPattern',
												inputValue : "3",
												 listeners : {
										                        "check" : function(el, checked) {
										                            if (checked) {
										                                Ext.getCmp("couponsNum").hide();
																		Ext.getCmp("SlFundQlideGridcheck").show();
										                            }
										                        }
										                    }
											}, {
												boxLabel : '制卡派发',
												name : 'bpCouponSetting.setPattern',
												inputValue : "4",
												 listeners : {
										                        "check" : function(el, checked) {
										                            if (checked) {
										                                Ext.getCmp("couponsNum").show();
										                                Ext.getCmp("SlFundQlideGridcheck").hide();
										                            }
										                        }
										                    }
											}]
										}
										]
							
								},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<br>【<b>用户列表</b>】：<font color='red'>注：派发人数不能大于剩余张数</font>"
										}]
								},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<br>"
										}]
								},{
						            	columnWidth:0.45,
						                layout: 'form',
						                labelWidth : leftlabel,
						                id : 'couponsNum',
						                hidden: true,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '派发个数',
												name : 'couponsNum',
												allowBlank : false,
												anchor : '100%',
												blankText : '派发个数',
												readOnly : false
											}]
					            	}]
					}]
				});
				//加载表单对应的数据	
				if (this.categoryId != null && this.categoryId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/coupon/getBpCouponSetting.do?categoryId='+ this.categoryId,
								root : 'data',
								preName : 'bpCouponSetting'
							});
				}
				this.sharteequitybar = new Ext.Toolbar({
				items : [
				{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : this.createShareequity,
						hidden:this.isreadOnly
							
					},new Ext.Toolbar.Separator({
						hidden : this.isreadOnly
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						handler : this.deleteShareequity,
						hidden:this.isreadOnly
					},{
						iconCls : 'slupIcon',
						text : '导入用户',
						xtype : 'button',
						scope : this,
						handler : this.importData,
						hidden:this.isreadOnly
					}, {
						iconCls : 'sldownIcon',
						text : '下载模板',
						xtype : 'button',
						scope : this,
						hidden:this.isreadOnly,
						handler : this.loadtotempt
					}]
				})
				this.gridPanel = new HT.EditorGridPanel({
			rowActions : false,
			bodyStyle : "height:240px;overflow-x:hidden; overflow-y:scroll",
			id : 'SlFundQlideGridcheck',
			isautoLoad:false,
			tbar :this.sharteequitybar,
			bbar : false,
			url : __ctxPath	+ "/customer/listbyredIdBpCustRedMember.do?redId="+this.redId,
			fields : [{
						name : 'redTopersonId',
						type : 'int'
					}, 'redId', 'bpCustMemberId','redMoney','loginname', 'email','telphone','truename','edredMoney','thirdPayFlag0','distributeTime'
				],
			columns : [{
						header : 'redTopersonId',
						dataIndex : 'redTopersonId',
						hidden : true
					},{
						header : 'bpCustMemberId',
						dataIndex : 'bpCustMemberId',
						hidden : true
					}, {
						header : '登陆名',
						dataIndex : 'loginname',
						width : 130,
						editor: new Ext.form.ComboBox({
						    triggerClass : 'x-form-search-trigger',
							resizable : true,
							mode : 'remote',
							editable : false,
							lazyInit : false,
							allowBlank : false,
							typeAhead : true,
							readOnly:this.isreadOnly,
							minChars : 1,
							width : 100,
							listWidth : 150,
							store : new Ext.data.JsonStore({}),
							triggerAction : 'all',
							onTriggerClick : function() {
							
							var selectPersonObj =function(obj) {
	                            var grid = Ext.getCmp("SlFundQlideGridcheck");
	                             grid.getSelectionModel().getSelected().data['bpCustMemberId'] = obj.id;
	                            grid.getSelectionModel().getSelected().data['loginname'] = obj.loginname;
									grid.getSelectionModel().getSelected().data['truename'] = obj.truename;
	                    			grid.getSelectionModel().getSelected().data['telphone'] = obj.telphone;
	                    			grid.getSelectionModel().getSelected().data['email'] = obj.email;
	                    			grid.getSelectionModel().getSelected().data['thirdPayFlag0'] = obj.thirdPayFlag0;
							        grid.getView().refresh();
									
							
								}
								selectCustMember(selectPersonObj);
												
							},
						
					listeners : {
						'select' : function(combo,record,index) {
							grid.getView().refresh();
						},
						'blur' : function(f) {
							if (f.getValue() != null && f.getValue() != '') {
							}
						}
					}
						})

					}
			, {
						header : '真实姓名',	
						dataIndex : 'truename',
						width : 85
					}
					, {
						header : '电话',
						dataIndex : 'telphone',
						width : 85
					
					}, {
						header : '邮箱',
						dataIndex : 'email'
						
					}]
				// end of columns
			});
			},//end of the initcomponents
 createShareequity : function(){
    	 var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
		
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = 0;
			p.data[keys[4]] = '';
			p.data[keys[5]] = '';
			p.data[keys[6]] = '';
			p.data[keys[7]] = '';
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     }, deleteShareequity : function(){
     	var redId=this.redId;
			var griddel =this.gridPanel;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s.length <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.redTopersonId == null || row.data.redTopersonId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/customer/multiDelBpCustRedMember.do',
										{
											ids :row.data.redTopersonId,
											redId:redId
											
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
		
     }, importData:function(){
   var gridPanel=this.gridPanel;
				new Ext.Window({
					id : 'importEnterpriseWin',
					title : '导入数据',
					layout : 'fit',
					width : (screen.width - 180) * 0.6 - 150,
					height : 130,
					closable : true,
					resizable : true,
					plain : false,
					bodyBorder : false,
					border : false,
					modal : true,
					constrainHeader : true,
					bodyStyle : 'overflowX:hidden',
					buttonAlign : 'center',
					items : [new Ext.form.FormPanel({
						id : 'importEnterpriseFrom',
						monitorValid : true,
						labelAlign : 'right',
							url :  __ctxPath +'/creditFlow/customer/enterprise/importRedDataBatchImportDatabase.do',
						buttonAlign : 'center',
						enctype : 'multipart/form-data',
						fileUpload : true,
						layout : 'column',
						frame : true,
						items : [{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 90,
									defaults : {
										anchor : '95%'
									},
									items : [{}, {
												xtype : 'textfield',
												fieldLabel : '请选择文件',
												allowBlank : false,
												blankText : '文件不能为空',
												inputType : 'file',
												id : 'fileBatch',
												name : 'fileBatch'
											}]
								}]
					})],
					buttons : [{
						text : '导入',
						iconCls : 'uploadIcon',
						formBind : true,
						handler : function() {
							Ext.getCmp('importEnterpriseFrom').getForm()
									.submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function(form, action) {
										var	objt = Ext.util.JSON.decode(action.response.responseText);
										gridPanel.getStore().loadData(objt);
								//		gridPanel.getView().refresh();
											Ext.ux.Toast.msg('状态', '导入成功!');
											Ext.getCmp('importEnterpriseWin').close();
											

										},
										failure : function(form, action) {
											var	objt = Ext.util.JSON.decode(action.response.responseText);
											
											Ext.ux.Toast.msg('状态',objt.msg+ '用户名不存在，导入失败!');
										}
									});
						}
					}]
				}).show();
			
   },  
	loadtotempt:function(){

	window.open(__ctxPath + '/customer/outputExcelBpCustRedEnvelope.do?bpcoupon=1','_blank');
	
	},getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			var st = "";
			for (var i = 0; i < vCount; i++) {
						vDatas += vRecords[i].data.bpCustMemberId + ',';
			}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
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
				var setPattern = null;
				var str=this.getCmpByName('bpCouponSetting.setPattern').items;
				for(var i=0;i<str.length;i++){
					if(str.items[i].checked){
						setPattern=str.items[i].inputValue;
						break;
					}
				}
				var categoryId=this.getCmpByName("bpCouponSetting.categoryId").getValue();
				var couponsNum="";
				if(setPattern=="4"){
					couponsNum=this.getCmpByName("couponsNum").getValue();
					if(couponsNum<=0){
						Ext.ux.Toast.msg('操作信息', "派发个数必须是大于0的整数");
						return false;
					}
				}
				var reddatas= this.getGridDate();
				var gridPanel=this.gridPanel;
				     Ext.Ajax.request( {
						scope:this,
						url:__ctxPath + '/coupon/couponsAllDistributeBpCoupons.do?categoryId='+categoryId+'&setPattern='+setPattern+'&couponsNum='+couponsNum+'&reddatas='+reddatas,
						method : 'post',
						waitMsg : '正在提交数据...',
			           	success : function(response, request) {
							var obj = Ext.util.JSON.decode(response.responseText);
							if(obj.msg=="1"){
								Ext.ux.Toast.msg('操作信息', "派发人数不能大于剩余张数");
							}else{
								Ext.ux.Toast.msg('操作信息', obj.msg);
								this.close();
				                Ext.getCmp('BpCouponSettingGrid0').getStore().reload();
							}
			            },
			            failure : function(fp, action) {
			                Ext.MessageBox.show({
			                    title : '操作信息',
			                    msg : '信息保存出错，请联系管理员！',
			                    buttons : Ext.MessageBox.OK,
			                    icon : 'ext-mb-error'
			                });
			            }
					}
				);
			}//end of save

		});