/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SlDataListView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlDataListView.superclass.constructor.call(this, {
			id : 'SlDataListView'+this.type,
			title :this.type=='jt'? '计提同步数据':'台账同步数据',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [ {
				columnWidth : 0.19,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
			
				items : [ {
					xtype : 'datefield',
					format : 'Y-m-d',
					fieldLabel : '发送日期',
					anchor : '100%',
					name : 'startDate'
				} ]
			},{
				columnWidth : 0.05,
				layout : 'form',
				border : false,
				html : '<center>---</center>'
			},{
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				items : [{
					xtype : 'datefield',
					format : 'Y-m-d',
					anchor : '100%',
					name :'endDate',
					hideLabel : true
				}]
			}, {
				columnWidth : 0.17,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
						xtype : "combo",
						triggerClass : 'x-form-search-trigger',
						hiddenName : "sendPersonId",
						editable : true,
						fieldLabel : "发送人",
						blankText : "发送人不能为空，请正确填写!",
						readOnly : this.isAllReadOnly,
						anchor : "98%",
						onTriggerClick : function(cc) {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if (null == obj.getValue() || "" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										type : 'branch',
										title : "选择发送人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();

						}
					}, {
						xtype : "hidden",
						value : ""
					} ]
			},isShow?{
			    columnWidth : 0.2,
			    layout : 'form',
			    border:false,
			   	labelWidth : 80,
				labelAlign : 'right',
			    items : [
			    {
			      xtype : "combo",
			      anchor : "98%",
			      fieldLabel : '所属分公司',
			      hiddenName : "companyId",
			      displayField : 'companyName',
			      valueField : 'companyId',
			      triggerAction : 'all',
			      store : new Ext.data.SimpleStore({
			       autoLoad : true,
			       url : __ctxPath+'/system/getControlNameOrganization.do',
			       fields : ['companyId', 'companyName']
			      })
			    }
			   ]}:{
			    columnWidth:0.01,
			    border:false
			    }, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					width : 60,
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '生成同步数据',
				xtype : 'button',
				scope : this,
				//hidden : isGranted('_create_cm')?false:true,
				handler : this.createRs
			}, {
				iconCls : 'btn-del',
				text : '撤回数据',
				xtype : 'button',
				scope : this,
				//hidden : isGranted('_remove_cm')?false:true,
				handler : this.cancel
			} ]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			hiddenCm:true,
			id:"sldatalistGrid"+this.type,
			url : __ctxPath + "/creditFlow/finance/listSlDataList.do?type="+this.type,
			fields : [ {
				name : 'dataId',
				type : 'long'
			}, 'dayDate', 'sendTime', 'sendPersonId', 'sendStatus',
					'type', 'sendPersonName'],
			columns : [ {
				header : 'id',
				dataIndex : 'dataId',
				hidden : true
			},{
			   	header : this.type=='jt'?'计提日期':'日结日期',
			   	format : 'Y-m-d',
				dataIndex : 'dayDate'
			}, {
				header : '发送时间',
				format : 'Y-m-d',
				dataIndex : 'sendTime'
			}, {
				header : '发送人',
				dataIndex : 'sendPersonName'
			}, {
				header : '发送状态',
				dataIndex : 'sendStatus',
				renderer : function(value){
					if(value==0){
						return '<img title="已发送" src="'+ __ctxPath+ '/images/flag/customer/effective.png"/>'
					}else{
						return '<img title="未发送" src="'+ __ctxPath+ '/images/menus/admin/book_go.png"/>'
					}
				}
			}, {
				header : '操作',
				dataIndex : 'sendStatus',
				renderer : function(value){
					if(value==0){
						return ''
					}else{
						return '<img title="重新生成" src="'+ __ctxPath+ '/images/menus/mail/mail_menu.png"/>重新生成'
					}
				}
			}, {
				header : '查看明细',
				dataIndex : 'haveCharcter',
				renderer :function(){
					return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
				}
			}],
			listeners : {
				scope : this,
			   'cellclick' : function(grid,rowIndex,columnIndex,e){
			
			   		
			   	var type=this.type;
			   	if(columnIndex==7){
			   		var dataId=grid.getStore().getAt(rowIndex).get("dataId");
			   		var sendStatus=grid.getStore().getAt(rowIndex).get("sendStatus");
			   		var dayDate=grid.getStore().getAt(rowIndex).get("dayDate");
			   		new SlDataInfoWindow({dataId:dataId,sendStatus:sendStatus,dayDate:dayDate}).show()
			   	}
			   	if(columnIndex==6){
			   		   var myMask = new Ext.LoadMask(Ext.getBody(), {
                         msg: '正在重新生成......',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
			   	var dataId=grid.getStore().getAt(rowIndex).get("dataId");
			   		var dayDate=grid.getStore().getAt(rowIndex).get("dayDate");
			   		if(type=="tz"){
			   			  	Ext.ux.Toast.msg('状态', '重新生成需要点时间，请耐心等候');
					   	   	Ext.Ajax.request({
					   	   		waitMsg : '正在重新生成',
								url : __ctxPath + '/webservice/createExcelCreateFianceExcelData.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '重新生成成功');
									var gridPanel = Ext.getCmp('sldatalistGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									isReceate:"yes"
								}
								
								})
			   	    }else if(type=="jt"){
			   	    Ext.ux.Toast.msg('状态', '重新生成需要点时间，请耐心等候');
			   	          Ext.Ajax.request({
			   	          	waitMsg : '正在重新生成',
								url : __ctxPath + '/webservice/createExcelCreateFAccruedExcelData.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '重新生成成功');
									var gridPanel = Ext.getCmp('sldatalistGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									isReceate:"yes"
								}
								
								})
			   	    }
			   		
			   	}
			   	if(columnIndex==5){
			   		  var myMask = new Ext.LoadMask(Ext.getBody(), {
                        msg: '正在发送……',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
			   		var type=this.type;
			   	var dataId=grid.getStore().getAt(rowIndex).get("dataId");
			   		var dayDate=grid.getStore().getAt(rowIndex).get("dayDate");
			   		
			   		if(type=="tz"){
			   			Ext.ux.Toast.msg('状态', '发送需要点时间，请耐心等候');
					   	   	Ext.Ajax.request({
					   	   		waitMsg : '正在发送',
								url : __ctxPath + '/webservice/getDateFromExcelSendDataFromExcel.do',
								method : 'POST',
								
								success : function(response, request) {
									
									  myMask.hide();
									  var object=Ext.util.JSON.decode(response.responseText);
									   
									  var flag=object.flag;
									
									   var result=object.result;
									   
									  if(flag==0){
									     Ext.Msg.alert("",result);
									  }else{
									      Ext.ux.Toast.msg('状态', '发送失败'+result);
									  }
									var gridPanel = Ext.getCmp('sldatalistGrid'+type);
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '发送失败');
								},
								params : {
									dataId : dataId,
									factdate : dayDate
								}
								
								})
			   		}else if(type=="jt"){
			   			Ext.ux.Toast.msg('状态', '发送需要点时间，请耐心等候');
			   		           	Ext.Ajax.request({
			   		           	waitMsg : '正在发送',
								url : __ctxPath + '/webservice/getDateFromExcelSendAccruedFromExcel.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									  var object=Ext.util.JSON.decode(response.responseText);
									  var flag=object.flag;
									   var result=object.result;
									  if(flag==0){
									     Ext.Msg.alert("",result);
									  }else{
									      Ext.ux.Toast.msg('状态', '发送失败'+result);
									  }
									var gridPanel = Ext.getCmp('sldatalistGrid'+type);
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '发送失败');
								},
								params : {
									dataId : dataId,
									factdate : dayDate
								}
								
								})
			   		
			   			
			   		}
			   		
			   	}
			   }
			}
		//end of columns
				});

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	
	//创建记录
	createRs : function() {
		var type=this.type;
		 Ext.Ajax.request({
               url:  __ctxPath + '/creditFlow/finance/getMaxDateSlDataList.do',
               method : 'POST',
               params : {
						type : this.type
					},
              success : function(response,request) {

					var object=Ext.util.JSON.decode(response.responseText);
            	if(type=="tz"){
					var formPanel=new Ext.FormPanel( {
						layout : 'column',
						border : false,
						region : 'north',
						height :150,
						anchor : '100%',
						frame : true,
						layoutConfig: {
			               align:'middle'
			            },
			            bodyStyle : 'padding:10px 10px 10px 10px',
				        items : [{
				         	columnWidth : 0.5,
				         	layout : 'form',
				         	border : false,
				         	labelWidth : 60,
				         	items : [{
				         		xtype : 'datefield',
				         		format : 'Y-m-d',
				         		fieldLabel : '生成日期',
				         		anchor : '100%',
				         		name:"dayDateg",
				         		value : object.date
				         	}]
				        },{
				        	columnWidth : 0.05,
				        	layout : 'form',
				        	border : false,
				        	html:'--'
				        },{
				        	columnWidth : 0.45,
				        	layout : 'form',
				        	border : false,
				        	items : [{
				        		xtype : 'datefield',
				        		format : 'Y-m-d',
				        		anchor : '100%',
				        		name:"dayDatel",
				        		hideLabel : true,
				        		value : object.date
				        	}]
				        }]
					})
					var win=new Ext.Window({
						title : '生成同步数据',
						 modal:true,
						autoHeight:true,
						 width : 400,
						items : [formPanel],
						buttons:[{
						    text : '生成',
							iconCls : 'btn-save',
							scope : this,
							handler : function(){
							var this1=this;
							var g=this.gridPanel;
							  $postForm({
									formPanel:formPanel,
									scope:this,
									url : __ctxPath + '/webservice/createExcelCreateFianceExcelData.do',
									callback:function(fp,action){
										var gridPanel = Ext.getCmp('sldatalistGrid'+type);
										if (gridPanel != null) {
											gridPanel.getStore().reload();
										}
										win.close();
										
									}
								}
							);
							
							}
						},{
						    text : '关闭',
							iconCls : 'close',
							scope : this,
							handler : function(){
								win.close();
							}
						}]
					})
					win.show()
            	}else if(type=="jt"){
            	
            		var formPanel=new Ext.FormPanel( {
						layout : 'column',
						border : false,
						region : 'north',
						height :150,
						anchor : '100%',
						frame : true,
						layoutConfig: {
			               align:'middle'
			            },
			            bodyStyle : 'padding:10px 10px 10px 10px',
				        items : [{
				         	columnWidth : 0.5,
				         	layout : 'form',
				         	border : false,
				         	labelWidth : 60,
				         	items : [{
				         		xtype : 'datefield',
				         		format : 'Y-m-d',
				         		fieldLabel : '生成日期',
				         		anchor : '100%',
				         		name:"dayDateg",
				         		value : object.date
				         	}]
				        }]
					})
					var win=new Ext.Window({
						title : '生成同步数据',
						 modal:true,
						autoHeight:true,
						 width : 400,
						items : [formPanel],
						buttons:[{
						    text : '生成',
							iconCls : 'btn-save',
							scope : this,
							handler : function(){
							var this1=this;
							var g=this.gridPanel;
							  $postForm({
									formPanel:formPanel,
									scope:this,
									url : __ctxPath + '/webservice/createExcelCreateFAccruedExcelData.do',
									callback:function(fp,action){
										var gridPanel = Ext.getCmp('sldatalistGrid'+type);
										if (gridPanel != null) {
											gridPanel.getStore().reload();
										}
										win.close();
										
									}
								}
							);
							
							}
						},{
						    text : '关闭',
							iconCls : 'close',
							scope : this,
							handler : function(){
								win.close();
							}
						}]
					})
					win.show()
            	
            	}
              }
    	}); 
		 
		
	},
		cancel :function(){
		 	var type=this.type;
	var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s > 1){
				Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				
				}else{	
					var record=s[0];
					var dayDate=record.data.dayDate;
					var dataId=record.data.dataId;
					var dataTypeStatus=record.data.dataTypeStatus;
					if(type=="tz"){
					 var myMask = new Ext.LoadMask(Ext.getBody(), {
                        msg: '正在撤销……',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
			   	          Ext.Ajax.request({
								url : __ctxPath + '/webservice/cancDateAllCancelDataByType.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									var object=Ext.util.JSON.decode(response.responseText);
									  var flag=object.flag;
									  if(flag==0){
									  	var result=object.result;
									     Ext.Msg.alert("",result);
									  }else{
									      Ext.ux.Toast.msg('状态', '撤销失败');
									  }
									var gridPanel = Ext.getCmp('SlDataInfoGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									dataTypeStatus:dataTypeStatus
								}
								
								})
				}	else{
					
				
					 var myMask = new Ext.LoadMask(Ext.getBody(), {
                        msg: '正在撤销……',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
			   	          Ext.Ajax.request({
								url : __ctxPath + '/webservice/cancDateAllAccredCancelDataByType.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									var object=Ext.util.JSON.decode(response.responseText);
									  var flag=object.flag;
									  if(flag==0){
									  	var result=object.result;
									     Ext.Msg.alert("",result);
									  }else{
									      Ext.ux.Toast.msg('状态', '撤销失败');
									  }
									var gridPanel = Ext.getCmp('SlDataInfoGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									dataTypeStatus:dataTypeStatus
								}
								
								})
					
					
					
			     }
			
			
			
			
			}
		 
	}
});
