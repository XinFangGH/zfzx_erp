/**
 * @author lisl
 * @class BreachHandle
 * @description 违约处理
 * @extends Ext.Panel
 */
BreachHandle = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		BreachHandle.superclass.constructor.call(this, {
			id : "BreachHandlePanel_" + this.record.data.projectId,
			title : "违约处理-" + this.record.data.projectName,
			iconCls : 'btn-tree-team59',
			border : false,
			tbar : this.toolbar,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
			__ctxPath + '/js/selector/UserDialog.js',
		    __ctxPath + '/js/creditFlow/finance/GuaranteeSlFundIntentViewVM.js',
		    __ctxPath + '/js/creditFlow/finance/CashQlideAndCheckForm.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',//实际收费项目
			__ctxPath + '/js/creditFlow/finance/SlActualToChargePremiumVM.js',//保费列表
		    __ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js',
			__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',//股东信息
			__ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/enterpriseBusinessUI.js',//项目基本信息
			__ctxPath + '/js/creditFlow/guarantee/project/Compensatory.js',
			__ctxPath + '/js/creditFlow/guarantee/project/CompensatoryRecords.js',
			__ctxPath + '/js/creditFlow/guarantee/project/Recovery.js',
			__ctxPath + '/js/creditFlow/guarantee/project/RecoveryRecords.js'
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-ok',
				text : '提交',
				scope : this,
				handler : this.saveAllDatas
			}]
		})
	},// 初始化组件
	constructPanel : function() {
		this.projectId = this.record.data.projectId;
		this.businessType = this.record.data.businessType;
		this.taskId = this.record.data.taskId;
		this.oppositeType = this.record.data.oppositeType;
		this.projectInfo = new enterpriseBusinessProjectInfoPanel({
		    isAllReadOnly : true
	    });
	    this.bankInfo=new ExtUD.Ext.BankInfoPanel({isAllReadOnly : true});
	    this.projectInfoFinance=new ProjectInfoGuaranteeFinancePanel({isAllReadOnly:true});
        var title = "企业客户信息";
        this.perMain = "";
	    if(this.oppositeType == "person_customer") {
		    this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
			    isAllReadOnly : true,
			    isHidden : true
		    });
		    title="个人客户信息";
	    }else if(this.oppositeType == "company_customer"){
		    this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				projectId : this.projectId,
				bussinessType : this.businessType, //业务类别
				isAllReadOnly : true,
				isHidden : true
		    });
	    };
	    this.GlActualToChargePanel=new SlActualToChargeVM({
           	projId : this.projectId,
		   	isHidden:true,
		   	isHiddenDuiZhangBtn : true,
		   	businessType :this.businessType
	    });
	    this.GLIntentPanel = new GuaranteeSlFundIntentViewVM({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			stringdata : '生成款项计划',
			isHidden : true,
		 	businessType :this.businessType
		});
		this.slActualToChargePremiumVM=new SlActualToChargePremiumVM({
		    projId : this.projectId,
		    isHiddenBtn : true,
		    isHiddenDZBtn : true,
			businessType : this.businessType      
	    });
		this.compensatoryRecords=new CompensatoryRecords({
	        projectId : this.projectId,
	        businessType : this.businessType
	    });
	    this.recoveryRecords=new RecoveryRecords({
	        projectId : this.projectId,
	        businessType : this.businessType
	    });
		this.outPanel = new Ext.Panel({
			frame : true,
			modal : true,
			labelWidth : 100,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				labelAlign : 'right',
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
				xtype : 'fieldset',
				title : '项目控制信息',
				name : 'projectInfo',
				items : [this.projectInfo]
			}, {
		        title : title,
		        items : [this.perMain]
		    }, {
				xtype : 'fieldset',
				title : '对接银行信息',
				collapsible : true,
				autoHeight : true,
				items : [this.bankInfo]
			},{       
			    xtype : 'fieldset',
				title : '资金款项信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [
				     this.projectInfoFinance,
				     this.slActualToChargePremiumVM,
				     this.GlActualToChargePanel
				]
			}, {
				xtype : 'fieldset',
				title : '项目处理',
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:5px",
					columnWidth : .1
				},
				items : [{
					layout : "column",
					defaults : {
						xtype : 'button',
						style : 'margin:0px 0px 0px 5px;',
						columnWidth : .13
					},
					items : [{
						text : '代偿',
						tooltip : '代偿',
						scope : this,
						handler : function() {
							var CompensatorynewGuarantee=new Compensatory({
							   	projectId : this.projectId,
							   	businessType : this.businessType
						   	});
							CompensatorynewGuarantee.show();
							CompensatorynewGuarantee.addListener("close",function(){
								this.getCmpByName('compensatoryRecords').get(0).get(0).getStore().reload()
							},this);
						}
					}, {
						text : '追偿',
						tooltip : '追偿',
						scope : this,
						handler : function() {
							var RecoveryGuarantee=new Recovery({
							   	projectId : this.projectId,
							   	businessType : this.businessType
						   	});
						   	RecoveryGuarantee.show();
						   	RecoveryGuarantee.addListener("close",function(){
						  		this.getCmpByName('recoveryRecords').get(0).get(0).getStore().reload()
						    },this);
						}
					}, {
						text : '将该客户加入黑名单',
						tooltip : '将该客户加入黑名单',
						scope : this,
						handler : function() {
							var oppositeId = this.record.data.oppositeId;
							var oppositeType = this.record.data.oppositeType;
							var fp=new Ext.FormPanel({
								frame:true,
								labelAlign:'right',
								bodyStyle : 'padding:5px 5px 5px 5px',
								labelWidth:60,
								border:false,
								url : __ctxPath+ '/creditFlow/guarantee/EnterpriseBusiness/joinBlackListGLGuaranteeloanZmNormalFlowProject.do',
							    items:[{
							        xtype:'textarea',
							        fieldLabel:'原因说明',
							        allowBlank:false,
							        name:'blackReason',
							        anchor:'100%'
							    }]
							}) 
							var window=new Ext.Window({
									title:'加入黑名单',
									width:400,
									height:150,
									modal : true,
								  	items:fp,
								  	buttonAlign:'center',
								  	buttons:[{
									  	 text:'提交',
									  	 iconCls : 'btn-save',
									  	 handler:function(){
									  	 	fp.getForm().submit({
									  	 	   	waitMsg : '正在提交...',
									  	 	  	method : 'post',
												success : function(form, action) {
													Ext.ux.Toast.msg('状态', '添加成功');
													window.destroy();
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态', '添加失败');
												},
												params : {
													oppositeId : oppositeId,
													oppositeType : oppositeType
												}
									  	 	})
									  	 }
									   },{
								   		 text:'取消',
								   		 iconCls : 'btn-cancel',
								   		 handler:function(){
								   		 	window.destroy()
								   		 }
								   	}]
								})
					            window.show()
						}
					}]
				}]
			}, {
				xtype : 'fieldset',
				title : '代偿记录',
				collapsible : true,
				autoHeight : true,
				hidden:true,
				autoWidth:true,
				name : 'compensatoryRecords',
				items : [this.compensatoryRecords],
				listeners : {
			      afterrender:function(com){
			          com.get(0).get(0).getStore().on("load",function(){
			               if(com.get(0).get(0).getStore().getCount()>0){ 
			                     com.setVisible(true);
			               }else{
			                    com.hide();
			               }
			          });
			       }
			    }
			}, {
				xtype : 'fieldset',
				title : '追偿记录',
				collapsible : true,
				autoHeight : true,
				autoWidth:true,
				hidden:true,
				name : 'recoveryRecords',
				items : [this.recoveryRecords],
				listeners : {
			      afterrender:function(com){
			         com.get(0).get(0).getStore().on("load",function(){
			         	if(com.get(0).get(0).getStore().getCount()>0){ 
			               	com.setVisible(true);
			            }else{
			            	com.hide();
			            }
			          });
			   	   }
			   }
			}, {
				xtype : 'fieldset',
				title : '违约信息',
				items : [
					new uploads({
						anchor : "100%",
						title_c : '上传违约处理说明文档',
						businessType : this.businessType,
						typeisfile : 'breachSmallloan',
						projectId : this.projectId,
						uploadsSize : 15
					}), {
						xtype : "textarea",
						fieldLabel : "违约说明",
						labelWidth : 80,
						name : "comments",
						style : 'margin:10px 0px 0px 0px;',
						anchor : "100%"
					}
				]
			}, {
				xtype : 'hidden',
				name : 'task_id',
				value : this.taskId
			}]
		})
		this.loadData({
//			url : __ctxPath + '/creditFlow/getInfoCreditProject.do?taskId='+this.projectId+'&type='+this.businessType+'&task_id='+this.taskId,
			url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/getInfoGLGuaranteeloanProject.do?glProjectId='+this.projectId+'&glTaskId='+this.taskId+'&businessType='+this.businessType,
			method : "POST",
			preName : ['enterprise', 'person', 'gLGuaranteeloanProject','customerBankRelationPerson',"businessType"],
			root : 'data',
			success : function(response, options) {
			 	var respText = response.responseText;  
		       	var alarm_fields = Ext.util.JSON.decode(respText);   
	           	this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.gLGuaranteeloanProject.projectMoney,'0,000.00'))
			}
	    });
        this.add(this.outPanel);
	    this.doLayout();
	},// 初始化UI结束
	saveAllDatas : function() {
		var comments = this.getCmpByName("comments").getValue();
		var projectId = this.projectId;
		var businessType = this.businessType;
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm('信息确认', '提交会将此项目归为违约处理项目,您确认要提交吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/guarantee/EnterpriseBusiness/breachHandleGLGuaranteeloanProjectZyNormalFlow.do',
					method : 'post',
					params : {
						projectId : projectId,
						comments : comments,
						businessType : businessType
					},
					success : function(response,options) {
						var res = Ext.util.JSON.decode(response.responseText);
						if(res.success) {
							Ext.ux.Toast.msg('操作信息','违约处理成功！');
							Ext.getCmp('centerTabPanel').remove('BreachHandlePanel_' + projectId);
							ZW.refreshTaskPanelView();
							gridPanel.getStore().reload();
						}
					}
				});
			}
		});
	}
});