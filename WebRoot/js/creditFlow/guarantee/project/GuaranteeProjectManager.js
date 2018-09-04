/**
 * @author lisl
 * @extends Ext.Panel
 * @description 担保项目管理
 * @company 智维软件
 * @createtime:
 */
GuaranteeProjectManager = Ext.extend(Ext.Panel, {
			projectStatus : 0,
			bmStatus : 0,
			titlePrefix : "",
			tabIconCls : "",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(typeof(_cfg.projectStatus) != "undefined") {
					this.projectStatus = parseInt(_cfg.projectStatus);
				}if(typeof(_cfg.bmStatus) != "undefined") {
					this.bmStatus = parseInt(_cfg.bmStatus);
				}
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_gt'+this.projectStatus+this.bmStatus);//是否授权显示所有项目记录
				switch(this.projectStatus) {
					case 0 :
						switch(this.bmStatus) {
							case 0 :
								this.titlePrefix = "保前-进行中";
								this.tabIconCls = "menu-flowNew";
								break;
							case 10 : 
								this.titlePrefix = "保前-已挂起";
								this.tabIconCls = "menu-suspended";
								break;
							case 3 : 
								this.titlePrefix = "保前-已终止";
								this.tabIconCls = "menu-del";
								break;
						}
						break;
					case 1 :
						switch(this.bmStatus) {
							case 0 :
								this.titlePrefix = "保中-进行中";
								this.tabIconCls = "menu-flowNew";
								break;
							case 1 : 
								this.titlePrefix = "保中-违约处理";
								this.tabIconCls = "menu-infringement";
								break;
							case 2 : 
								this.titlePrefix = "保中-已完成";
								this.tabIconCls = "menu-flowEnd";
								break;
							case 3 : 
								this.titlePrefix = "保中-已终止";
								this.tabIconCls = "menu-del";
								break;
						}
						break;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				GuaranteeProjectManager.superclass.constructor.call(this, {
							id : 'GuaranteeProjectManager_' + this.projectStatus+this.bmStatus,
							title : this.titlePrefix + '项目管理',
							iconCls : this.tabIconCls,
							region : 'center',
							layout : 'border',
							items : [
								{xtype:'label',text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看你的上下级关系是否设置正确!(查询授权规律为：你可以看你自己或你下属的项目)',hidden : true},
								this.searchPanel,
								this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.isHiddenBranch = true;
				if (RoleType == "control") {// 此用户角色为管控角色
					this.isHiddenBranch = false;
				}
				// 初始化搜索条件Panel
				var anchor='100%';
				this.searchPanel =new Ext.FormPanel({
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor :anchor

						},
						items : [
							{
								name : 'projectStatus',
								xtype : 'hidden',
								value : this.projectStatus
							},{
								name : 'bmStatus',
								xtype : 'hidden',
								value : this.bmStatus
							}, {
								name : 'businessType',
								xtype : 'hidden',
								value : 'Guarantee'
							}, {
								name : 'isGrantedShowAllProjects',
								xtype : 'hidden',
								value : this.isGrantedShowAllProjects
							},{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'项目编号',
									name : 'Q_projectNumber_S_LK',
									xtype : 'textfield'
								}]
						}, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor :anchor
							},
							items : [{
									fieldLabel:'项目名称',
									name : 'Q_projectName_S_LK',
									xtype : 'textfield'
									
								}]
						}, {
							columnWidth : .23,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'value',
									store : new Ext.data.SimpleStore({
												fields : ["value"],
												data : [["企业"],
														["个人"]]
											}),
									triggerAction : "all",
									name : 'Q_oppositeTypeValue_S_EQ',
									fieldLabel : '客户类别'
									
								}]
						}, {
							columnWidth : .27,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'客户',
									name : 'Q_customerName_S_LK',
									xtype : 'textfield'
								}]
						}, {
							columnWidth : .14,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'贷款金额',
									name : 'Q_projectMoney_BD_GE',
									xtype : 'numberfield'
								}]
						}, {
							columnWidth : .11,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor : anchor
							},
						
							items : [{
									fieldLabel:'到',
									labelSeparator:"",
									name : 'Q_projectMoney_BD_LE',
									xtype : 'numberfield'
								}]
						}, {
							columnWidth : .14,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'启动时间',
									name : 'Q_startDate_D_GE',
									xtype : 'datefield',
									format : 'Y-m-d'
									
								}]
						}, {
							columnWidth : .112,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor :anchor
							},
							items : [{
									fieldLabel:'到',
									name : 'Q_startDate_D_LE',
									xtype : 'datefield',
									labelSeparator:"",
									format : 'Y-m-d'
								}]
						}, {
							columnWidth : .23,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									name : 'appUserIdOfA',
									xtype:'trigger',
									fieldLabel : '项目经理',
									submitValue : true,
									triggerClass :'x-form-search-trigger',
									editable : false,
									scope : this,
									onTriggerClick : function(){
									    var obj = this;
										UserSelector.getView(function(id, name) {
											obj.setValue(name);
											obj.ownerCt.ownerCt.getCmpByName('Q_appUserIdOfA_S_LK').setValue(id);
										}, true).show();//false为多选,true为单选。
									}
								}]
						},{
							xtype : "hidden",
							name : 'Q_appUserIdOfA_S_LK'
						}, this.isHiddenBranch == false ?{
							columnWidth :.145,
							layout : 'form',
							xtype : 'container',
							labelWidth : 70,
							labelAlign : 'right',
							border : false,
							items : [{
								fieldLabel : '所属分公司',
								xtype : "combo",
								anchor : "100%",
								hiddenName : "companyId",
								displayField : 'companyName',
								name :"companyId",
								valueField : 'companyId',
								triggerAction : 'all',
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/system/getControlNameOrganization.do',
									fields : ['companyId', 'companyName']
								})
							}]
						}:{
							columnWidth : .0001,
							border : false
						}, {
							columnWidth : .075,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							items : [{
										text:'查询',
										style :'margin-left:30px',
										width:50,
										scope:this,
										iconCls:'btn-search',
										handler:this.search
									}]
						}, {
							columnWidth : .05,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							items : [{
										text:'重置',
										width:50,
										scope:this,
										iconCls:'btn-reset',
										handler:this.reset
									}]
						}]
					}]
				})

				if(this.projectStatus == 0) {
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus)||!isGranted('_detailPro_gt' + this.projectStatus + this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_gt' + this.projectStatus + this.bmStatus)||!isGranted('_hangPro_gt' + this.projectStatus + this.bmStatus)
								}),{
									iconCls : 'btn-suspended',
									text : '挂起项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_hangPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										suspendPro(this.gridPanel,'GuaranteeProjectInfo_','GuaranteeProjectInfoEdit_',1);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_hangPro_gt' + this.projectStatus + this.bmStatus)||!isGranted('_stopPro_gt' + this.projectStatus + this.bmStatus)
								}),{
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_stopPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										stopPro(this.gridPanel,'GuaranteeProjectInfo_','GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_gt' + this.projectStatus + this.bmStatus)||!isGranted('_removePro_gt' + this.projectStatus + this.bmStatus)
								}), {
									iconCls : 'btn-del',
									text : '删除项目',
									xtype : 'button',
									hidden : isGranted('_removePro_gt'+this.projectStatus+this.bmStatus)?false:true,
									scope:this,
									handler: function() {
										removePro(this.gridPanel,'GuaranteeProjectInfo_','GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_removePro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_LE").getValue(),
												projectStatus:"保前-进行中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					} else if (this.bmStatus == 10) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_recoverPro_gt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-recover',
									text : '恢复项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_recoverPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										suspendPro(this.gridPanel,'GuaranteeProjectInfo_','GuaranteeProjectInfoEdit_',0);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_recoverPro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保前-已挂起"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					}else if (this.bmStatus == 3) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保前-已终止"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					}
					
				} else if (this.projectStatus == 1) {
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_stopPro_gt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_stopPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										stopPro(this.gridPanel,'GuaranteeProjectInfo_','GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-进行中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					} else if (this.bmStatus == 1) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-违约处理"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					}else if (this.bmStatus == 2) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt'+this.projectStatusf+this.bmStatus)||!isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_detailPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_gt'+this.projectStatusf+this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-已完成"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					}else if (this.bmStatus == 3) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'GuaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_gt' + this.projectStatus + this.bmStatus)||!isGranted('_yjysm_pt'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_yjysm_pt'+ this.projectStatus+this.bmStatus)
														? false
														: true,
									handler : this.flowRecords
											
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_yjysm_pt' + this.projectStatus+this.bmStatus)
											|| !isGranted('_showFlowImg_gt' + this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_gt'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)
								})/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_gt'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewguaranteeprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewguaranteeprojectdetail');
                                    	}
											gpObj=ReportMenuGuaranteeprojectdetail({
												reportKey:"guaranteeprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-已终止"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewguaranteeprojectdetail");
									}
								}*/]
						});
					}
				}
				
	
				this.gridPanel=new HT.GridPanel({
					name : 'GuaranteeProjectGrid',
					region : 'center',
					tbar : this.topbar,
					notmask :true,
					//使用RowActions
					rowActions : false,
					url : __ctxPath + "/creditFlow/guarantee/EnterpriseBusiness/projectListGLGuaranteeloanProject.do",
					baseParams:{
						'businessType' : "Guarantee",
						'projectStatus' : this.projectStatus,
						'bmStatus' : this.bmStatus,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects
					},
					fields : [{
								name : 'runId',
								type : 'int'
							}, 'projectId','orgName','subject','executor', 'creator','userId', 'projectName', 'projectNumber', 'defId','createtime', 'runStatus','startDate','projectMoney','oppositeType','oppositeTypeValue','appUserIdOfAValue','projectStatus','bmStatus','operationType','operationTypeValue','taskId','activityName','oppositeId','businessType','appUserName','flowType','dicbankname'],
					columns : [
							{
								header : 'projectId',
								dataIndex : 'projectId',
								hidden : true
							},{
								header : 'taskId',
								dataIndex : 'taskId',
								hidden : true
							},{
								header : 'oppositeId',
								dataIndex : 'oppositeId', 
								hidden : true
							},{
								header : 'oppositeType',
								dataIndex : 'oppositeType', 
								hidden : true
							},{
								header : 'businessType',
								dataIndex : 'businessType', 
								hidden : true
							},{
								header : '所属分公司',
								hidden :  this.isHiddenBranch?true :false,
								width : 100,
								dataIndex : 'orgName'
							},{
								header : '项目编号',
								width : 130,
								dataIndex : 'projectNumber'
							}, {
								header : '项目名称',
								width : 480,
								dataIndex : 'projectName'
							}, {
								header : '客户类别',
								width : 71,
								dataIndex : 'oppositeTypeValue'
							}, {
								header : '贷款金额',
								align : 'right',
								width : 110,
								dataIndex : 'projectMoney',
								renderer : function(value) {
									if(value == "") {
										return "0.00元";
									}else {
										return Ext.util.Format.number(value,',000,000,000.00')+"元";
									}
								}
							}, {
								header : '贷款银行',
								width : 115,
								dataIndex : 'dicbankname'
							}, {
								header : '项目经理',
								width : 90,
								dataIndex : 'appUserIdOfAValue'
							}, {
								header : '项目阶段',
								width : 116,
								dataIndex : 'activityName'
							}, {
								header : '执行人',
								width : 70,
								hidden : (this.bmStatus==2||this.bmStatus==3)?true:false,
								dataIndex : 'executor'
							}, {
								header : '启动时间',
								width : 76,
								dataIndex : 'startDate',
								sortable : true
							}
					]//end of columns
				});
				this.gridPanel.addListener('afterrender', function(){
					 this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl (), {
						 msg  : '正在加载数据中······,请稍候······',
						 store:this.gridPanel.store,
						 removeMask : true// 完成后移除
					});
					this.loadMask1 .show(); //显示
					
				},this);
				if(!this.isGrantedShowAllProjects) {
					this.gridPanel.getStore().on("load",function(store) {
						if(store.getCount() < 1) {
							this.get(0).setVisible(true);
							this.searchPanel.setVisible(false);
							this.gridPanel.setVisible(false);
							this.doLayout();
						}
					},this)
				}
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			flowRecords : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var runId =selRs[0].get('runId');
		var businessType=selRs[0].get('businessType');
		new SlProcessRunView({
						runId : runId,
						businessType : businessType
					}).show();
	}
});
