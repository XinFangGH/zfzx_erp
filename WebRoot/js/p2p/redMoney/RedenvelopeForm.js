/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
RedenvelopeForm = Ext.extend(Ext.Window, {

	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RedenvelopeForm.superclass.constructor.call(this, {
					id : 'RedenvelopeFormWin',
					title : '红包',
					region : 'center',
					layout : 'border',
					modal : true,
					height : 553,
					width : screen.width * 0.6,
					maximizable : true,
					items : [this.formPanel, this.gridPanel],
                    	buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											hidden:this.isreadOnly,
											handler : this.save
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											hidden:this.isreadOnly,
											handler : this.cancel
										}
							         ]
				});
	},// end of the constructor
	// 初始化组件

	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}

		var flag = this.flag;
		this.formPanel = new Ext.FormPanel({
			region : 'north',
					layout : 'form',
					height : 140,
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'BpCustRedEnvelopeForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'bpCustRedEnvelope.redId',
								xtype : 'hidden',
								value : this.redId == null ? '' : this.redId
							},{
								name : 'bpCustRedEnvelope.distributestatus',
								xtype : 'hidden',
								value : 0
							}, {
								fieldLabel : '红包名称',
								labelAlign : "right",
								name : 'bpCustRedEnvelope.name',
								readOnly:this.isreadOnly,
								allowBlank:false,
								maxLength : 50
							}, {
								fieldLabel : '红包简介',
								labelAlign : "right",
								name : 'bpCustRedEnvelope.remarks',
								readOnly:this.isreadOnly,
								xtype:"textarea",
								maxLength : 255
							}]
				});
						//加载表单对应的数据	
		  if (this.redId != "" && this.redId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/customer/getBpCustRedEnvelope.do?redId='+ this.redId,
								root : 'data',
								preName : 'bpCustRedEnvelope'
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
						text : '导入',
						xtype : 'button',
						scope : this,
						handler : this.importData,
						hidden:this.isreadOnly
					}, {
						iconCls : 'sldownIcon',
						text : '下载导入模板',
						xtype : 'button',
						scope : this,
						hidden:this.isreadOnly,
						handler : this.loadtotempt
					},{
						iconCls : 'btn-distribute',
						text : '派发',
						xtype : 'button',
						scope : this,
						handler : this.distributemany,
						hidden:!this.isreadOnly ||this.type=="ed"?true:false
							
					}]
				})
				
		this.gridPanel = new HT.EditorGridPanel({
			rowActions : false,
			region : 'center',
			bodyStyle : "width : 100%",
			id : 'SlFundQlideGridcheck',
			isautoLoad:false,
			tbar :this.sharteequitybar,
			bbar : false,
		//	tbar : this.topbar,
			url : __ctxPath
					+ "/customer/listbyredIdBpCustRedMember.do?redId="+this.redId,
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
						header : '第三方账号',	
						dataIndex : 'thirdPayFlag0',
						width : 85
					
					}, {
						header : '电话',
						dataIndex : 'telphone',
						width : 85
					
					}, {
						header : '邮箱',
						dataIndex : 'email'
						
					},

					
					{
						header : '金额',
						align : 'right',
						dataIndex : 'redMoney',
							editor : {
							xtype : 'numberfield',
							//给客户部署时state
						//	readOnly:this.isreadOnly,
							//给客户部署时end
							
							//自己平台测试state
							readOnly:false,
							//自己平台测试end
							
							allowBlank : false
						},
						//给客户部署时state
					//	renderer:function(v){
					//	return Ext.util.Format.number(v,
					//						',000,000,000.00')
					//						+ "元"
					//	}
						//给客户部署时end
						
						//自己平台测试state
						renderer:function(v){
						return Ext.util.Format.number(v,
											',000,000,000.00')
											+ "元"
						}
						//自己平台测试end
					}, {
						header : '派发时间',
						dataIndex : 'distributeTime',
						hidden:this.type=="not"?true:false
						
					}/*, {
						header : '派发状态',
						dataIndex : 'email',
						hidden:	!this.isreadOnly,
						renderer:function(v,s,r){
						   if(r.data.edredMoney==r.data.redMoney){
						     return "已派发";
						   }else{
						     return "未派发";
						   }
						
						}
						
					}*/]
				// end of columns
			});
			if(this.redId!=""){
				this.gridPanel.getStore().reload();
			}
		// this.gridPanel.addListener('rowdblclick',this.rowClick);

	},
	
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
     },   
     deleteShareequity : function(){
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
		
     },
		getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			var st = "";
			for (var i = 0; i < vCount; i++) {
		
						st = {
							"bpCustMemberId" : vRecords[i].data.bpCustMemberId,
			            	"redTopersonId" : null,
							//"redMoney" : vRecords[i].data.redMoney
			            	"redMoney" : vRecords[i].data.redMoney
						};
						vDatas += Ext.util.JSON.encode(st) + '@';

			}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
	cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
   importData:function(){
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
   save : function() {
   	var gp=this.gp;
  var reddatas= this.getGridDate();
				if(this.formPanel.getForm().isValid()){
					this.formPanel.getForm().submit({
						method : 'POST',
						scope :this,
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						url : __ctxPath + "/customer/saveBpCustRedEnvelope.do",
						params : {
						reddatas:reddatas
						},
						success : function(form ,action) {
								Ext.ux.Toast.msg('操作信息', '成功信息保存！');
								var gridPanel = gp;
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
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
				}
			},//end of save
	distributemany :function() {
		 var gp=this.gp;
		var redId=this.redId;
		var s = this.gridPanel.getSelectionModel().getSelections();
		var this1=this;
		  if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中一条记录');
			return false;
		}
		var qlidePanel = this.gridPanel;
		  var ids = $getGdSelectedIds(qlidePanel,'redTopersonId');
					Ext.MessageBox.confirm('确认', '确定派发吗', function(btn) {
					if (btn == 'yes') {
					Ext.MessageBox.wait('正在派发','请稍后...');	
			       Ext.Ajax.request( {
									url : __ctxPath + '/customer/distributeBpCustRedEnvelope.do',
									method : 'POST',
									scope : this,
									params : {
										ids : ids,
										redId:redId
									},
									success : function(response, request) {
										var infoObj = Ext.util.JSON
												.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息', infoObj.msg);
										var gridPanel = gp;
										if (gridPanel != null) {
											gridPanel.getStore().reload();
										}
										var gridPanel1 = Ext.getCmp('IngRedEnvelopeGrid'
												 );
										var gridPanel2 = Ext
												.getCmp('EdRedEnvelopeGrid');
		
										if (gridPanel1 != null) {
											gridPanel1.getStore().reload();
										}
										if (gridPanel2 != null) {
											gridPanel2.getStore().reload();
										}
										Ext.MessageBox.hide();
										this1.close();
									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "派发失败");
										var gridPanel = parentGrid||Ext.getCmp('SlActualToChargeGrid');//update by gao
                                       var gridPanel = gp;
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
						             	Ext.MessageBox.hide();
							                   this1.close();
										

										

									}
								});
				
					}
			
				})	
				
		}
,
	loadtotempt:function(){

	window.open(__ctxPath + '/customer/outputExcelBpCustRedEnvelope.do','_blank');
	
	}
});

