AppHomeTeamGuarantee = Ext.extend(Ext.Panel, { 
	
	portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AppHomeTeamGuarantee.superclass.constructor.call(this, {
			title : '担保桌面',
			closable : false,
			id : 'AppHomeTeamGuarantee',
			border : false,
			autoScroll : true,
			iconCls : 'menu-desktop',
			// layout : 'fit',
			// defaults : {
			// padding : '0 5 0 0'
			// },
			anchor : '98%',
			// tbar : this.toolbar,
			items : [this.seachPanel, this.panel]
				// this.portalPanel
			});
	},
	initUIComponents : function() {
		
		    this.seachText = new Ext.form.ComboBox({
			// xtype : 'combo',
			anchor : '100%',
			triggerClass : 'content_input',
			fieldLabel : "企业名称",
			name : "enterprise.enterprisename",
			readOnly : this.isAllReadOnly,
			readOnly : this.isEnterprisenameReadonly,
			blankText : "企业名称不能为空，请正确填写!",
			allowBlank : false,
			scope : this,
			onTriggerClick : function() {
				var win = Ext.getCmp("addThridCompany_win");
				if (typeof(win) != "undefined") {
					selectEnterprise(setEnterpriseNameStockUpdate);
				} else {
					selectEnterprise(setEnterpriseNameStockUpdateNew);
				}
			},
			resizable : true,
			mode : 'romote',
			editable : true,
			lazyInit : false,
			typeAhead : true,
			minChars : 1,
			store : new Ext.data.JsonStore({
				url : __ctxPath
						+ '/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
				root : 'topics',
				autoLoad : true,
				fields : [{
							name : 'id'
						}, {
							name : 'enterprisename'
						}, {
							name : 'shortname'
						}, {
							name : 'hangyetypevalue'
						}, {
							name : 'hangyetype'
						}, {
							name : 'organizecode'
						}, {
							name : 'cciaa'
						}, {
							name : 'postcoding'
						}, {
							name : 'address'
						}, {
							name : 'telephone'
						}, {
							name : 'legalpersonid'
						}],
				listeners : {
					scope : this,
					'load' : function(s, r, o) {
						if (s.getCount() == 0) {
							this.getCmpByName('enterprise.enterprisename')
									.markInvalid('没有查找到匹配的记录');
						}
					}
				}
			}),
			displayField : 'enterprisename',
			valueField : 'id',
			triggerAction : 'all',
			listeners : {
				scope : this,
				'select' : function(combo, record, index) {
					setEnterpriseNameStockUpdateCombo(record);
				},
				'blur' : function(f) {
					if (f.getValue() != null && f.getValue() != '') {
						this.getCmpByName('enterprise.id').setValue(f
								.getValue());
					}
				}

			}
		})
		
			App.clickTopTabremoveOldFinancing=function(id,params,precall,callback,activeid){
			if(precall!=null){
				precall.call(this);
			}
			var importId=id;
			if(id.indexOf('_')!=-1){
				importId=id.split('_')[0];
			}
			var tabs = Ext.getCmp('centerTabPanel');
			var tabItem = tabs.getItem(id);
			
			if (tabItem != null) {
				tabs.remove(tabItem);
			}
			$ImportJs(importId, function(view) {
					tabItem = tabs.add(view);
					tabs.activate(tabItem);
					if(activeid==2){
		            view.items.get(0).getItem("SlFundintentUrgeViewoverdue").search();
		            }else{
		               view.items.get(0).getItem("SlFundintentUrgeViewcoming").search();
		            }
				},params);
			if(activeid!=null){
					var west = Ext.getCmp('west-panel');
					west.getLayout().setActiveItem(activeid);
		    }
		    
		 
		};

		var html = '<div class="contentTeamDiv">'+
				'<div class="top_box">'+
					'<div class="top_box_left"><img src="images/imagesteam/topbox_left.gif" /></div>'+
					'<div class="top_box_cent">'+
						'<div class="search_01">'+
							'<div class="search_01_txt">'+
								'<div class="search_01_txt_left"><img src="images/imagesteam/search_txt_left.gif" /></div>'+
								'<div class="seachtxt">'+
									'<input id="searchKeywordGuarantee" type="text" style="color:gray" value="请输入项目关键字，例如项目编号、项目名称" onBlur="changeText(0,this)" onFocus="changeText(1,this)"/>'+
								'</div>'+
								'<div class="search_01_txt_rit"><img src="images/imagesteam/search_txt_rit.gif" /></div>'+
							'</div>'+
							'<div class="search_01_btn">'+
								'<input id="searchKeyword_button" type="image" src="images/imagesteam/search_btn.gif" onclick="searchProject()"/>'+
							'</div>'+
						'</div>'+
				
						
					
						'<div class="projects">'+
							'<img src="images/imagesteam/projects_bg01.gif" /><br />'+
							'<span><b>业务信息：</b>项目控制信息--客户信息--对接银行信息--资金款项信息--手续收支清单--保证金支付情况--银行放款收息表</span><br />'+
							'<span><b>风险控制：</b>担保准入原则--担保材料清单--客户资信评估--反担保措施管理--尽职调查报告--风险分析报告--银行放款收息表</span><br />'+
							'<span><b>合同文书：</b>拟担保意向书--对外担保承诺函--委托担保合同--反担保合同--客户借款合同--银行担保合同--担保责任解除函</span><br />'+

						'</div>'+
					'</div>'+
					'<div class="top_box_rit"><img src="images/imagesteam/topbox_rit.gif" /></div>'+
				'</div>'+
			/*	'<div class="mid_box">'+
					'<div class="mid_box_left"><img src="images/imagesteam/midbox_left.gif" /></div>'+
					'<div class="mid_box_cent">'+
					'<div class="mid_box_cent_content">'+
						'<div class="mid_box_cent_btn" onclick=App.clickTopTab("LoanAccept",null,null,null,1) >'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent"><span>[业务员]</span> 贷款尽调</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn" onclick=App.clickTopTab("RiskReview",null,null,null,1) >'+
							'<div class="midbox_bg_left" type="button"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent"><span>[业务员]</span> 风险审核</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn" onclick=App.clickTopTab("LoanApproval",null,null,null,1) >'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 贷款审批</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn" onclick=App.clickTopTab("LoanProvide",null,null,null,1) >'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 贷款发放</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn" onclick=App.clickTopTab("LoanClosing",null,null,null,1) >'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 贷款结项</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+*/
						/*'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn">'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 贷款催收</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn">'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 贷款展期</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
							'<img src="images/imagesteam/arrow01.gif" />'+
						'</div>'+
							'<div class="mid_box_cent_btn">'+
							'<div class="midbox_bg_left"><img src="images/imagesteam/midbox_bg_left.gif" /></div>'+
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 提前还款</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
					'</div>'+
					'</div>'+
					'<div class="mid_box_rit"><img src="images/imagesteam/midbox_rit.gif" /></div>'+
				'</div>'+*/
		  	
				'</div>'+
			'</div>'
		//var tpl = new Ext.XTemplate(html);
	
		//为搜索按钮注册键盘事件
/*        document.onkeydown = function() { 
            var e=event.srcElement; 
            if(event.keyCode==13) { 
           		document.getElementById("searchKeyword_button").click(); 
            	return false; 
            } 
        } */
		this.seachPanel = new Ext.Panel({
					border : false,
					html : html
				});
		var btnheigth = 27
		this.panel = new Ext.Panel({
			bodyCssClass : "btm_box",
			border : false,
			layout : 'column',
			defaults : {
				border : false,
				anchor : '100%',
				style : "margin-top:5px"

			},
			items : [{
				columnWidth : 1,
				layout : 'column',
				style : "margin-top:0px;margin-bottom:11px",
				// height : 30,
				defaults : {
					anchor : '100%',
					style : 'margin-left:5px;margin-top:0px',
					border : false,
					scale : 'large',
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【任务处理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
				//	style : "text-align:left;",
					items : [{
						xtype : 'button',
						text : '担保申请',
						disabled : isGranted('newProjectForm')
								? false
								: true,
						iconCls : 'btn-team1',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						
						handler : function() {
							ZW.startSLProject()
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '待办任务',
						disabled : isGranted('ActivityTaskView') ? false : true,
						iconAlign : "left",
						iconCls : 'btn-team2',
						scope : this,
						handler : function() {
							App.clickTopTab('ActivityTaskView');
						}
					}]
				}/*, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '任务追回',
						disabled : isGranted('MyRunningTaskView_p')
								? false
								: true,
						iconAlign : "left",
						iconCls : 'btn-team3',
						scope : this,
						handler : function() {
							App.clickTopTab('MyRunningTaskView_p',null,null,null,0);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '我参与的贷款',
						disabled : isGranted('MyProcessRunView_p',null,null,null,0)
								? false
								: true,
						iconAlign : "left",
						iconCls : 'btn-team4',
						scope : this,
						handler : function() {
							App.clickTopTab('MyProcessRunView_p',null,null,null,0);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						iconAlign : "left",
						text : '我发起的贷款',
						disabled : isGranted('ProcessRunView_p') ? false : true,
						iconCls : 'btn-team5',
						scope : this,
						handler : function() {
							App.clickTopTab('ProcessRunView_p',null,null,null,0);
						}
					}]
				}*/, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '已办结任务',
						disabled : isGranted('CompleteTaskView') ? false : true,
						iconAlign : "left",
						iconCls : 'btn-team6',
						scope : this,
						handler : function() {
							App.clickTopTab('CompleteTaskView');
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '我参与的项目',
						disabled : isGranted('MyProcessRunView_p')
								? false
								: true,
						iconAlign : "left",
						iconCls : 'btn-team4',
						scope : this,
						handler : function() {
							App.clickTopTab('MyProcessRunView_p');
						}
					}]
				}]
			},{
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					scale : 'large',
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【担保查询】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '保前-进行中项目',
						disabled : isGranted('GuaranteeProjectManager?projectStatus=0&bmStatus=0')
								? false
								: true,
						iconCls : 'btn-team13',
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeProjectManager',{projectStatus:0,bmStatus:0});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '保前已挂起项目',
						disabled : isGranted('GuaranteeProjectManager?projectStatus=0&bmStatus=10')
								? false
								: true,
						iconCls : 'btn-team16',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeProjectManager',{projectStatus:0,bmStatus:10});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '保前-已终止项目',
						disabled : isGranted('GuaranteeProjectManager?projectStatus=0&bmStatus=3')
								? false
								: true,
						iconCls : 'btn-team17',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeProjectManager',{projectStatus:0,bmStatus:3});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '保后-进行中项目',
						disabled : isGranted('GuaranteeProjectManager?projectStatus=1&bmStatus=0')
								? false
								: true,
						iconCls : 'btn-team18',
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeProjectManager',{projectStatus:1,bmStatus:0
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '违约处理项目',
						disabled : isGranted('GuaranteeProjectManager?projectStatus=1&bmStatus=1')
								? false
								: true,
						iconCls : 'btn-team60',
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeProjectManager',{projectStatus:1,bmStatus:1});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '已完成项目',
						disabled : isGranted('GuaranteeProjectManager?projectStatus=1&bmStatus=2')
								? false
								: true,
						iconCls : 'btn-team60',
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeProjectManager',{projectStatus:1,bmStatus:2});
						}
					}]
				}]
			},{
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					scale : 'large',
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【保后处理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '保后监管',
						disabled : isGranted('GuaranteeLoanedProjectManager?managerType=1')
								? false
								: true,
						iconCls : 'btn-team13',
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeLoanedProjectManager',{managerType:1});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '违约处理',
						disabled : isGranted('GuaranteeLoanedProjectManager?managerType=2')
								? false
								: true,
						iconCls : 'btn-tree-illegal',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeLoanedProjectManager',{managerType:2});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '项目结项',
						disabled : isGranted('GuaranteeLoanedProjectManager?managerType=3')
								? false
								: true,
						iconCls : 'btn-team17',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('GuaranteeLoanedProjectManager',{managerType:3});
						}
					}]
				}]
			},  {
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					iconAlign : "left",
					scale : 'large',
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【客户管理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '企业客户',
								disabled : isGranted('EnterpriseView')
										? false
										: true,
								iconCls : 'btn-team22',
								scope : this,
								handler : function() {
									App.clickTopTab('EnterpriseView');
								}
							}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '个人客户',
								disabled : isGranted('PersonView')
										? false
										: true,
								iconCls : 'btn-team23',
								scope : this,
								handler : function() {
									App.clickTopTab('PersonView');
								}
							}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '银行客户',
								disabled : isGranted('BankRelationPersonView')
										? false
										: true,
								iconCls : 'menu-person',
								scope : this,
								handler : function() {
									App.clickTopTab('BankRelationPersonView');
								}
							}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '资信评估',
						disabled : isGranted('CreditRatingManage?type=customer')
								? false
								: true,
						iconCls : 'btn-team24',
						scope : this,
						handler : function() {
							App.clickTopTab('CreditRatingManage', {
										type : 'customer'
									});
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				defaults : {
					anchor : '100%',
					scale : 'large',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【资金管理台账】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '保证金账户管理',
						disabled : isGranted('AccountBankManage')
								? false
								: true,
						iconCls : 'btn-team29',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('AccountBankManage');
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '保证金账户记录',
						disabled : isGranted('AccountBankManageProj')
								? false
								: true,
						iconCls : 'btn-team29',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('AccountBankManageProj');
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '手续收取台账',
						disabled : isGranted('SlActualToChargeIncomeView?businessType=Guarantee')
								? false
								: true,
						iconCls : 'btn-team29',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('TabSlFundIntentPrincipalPay',{
										businessType : 'Guarantee',
										title : '手续收入台账'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						iconCls : 'btn-team29',
						text : '手续收入台账日志',
						disabled : isGranted('VChargeDetail?businessType=Guarantee') ? false : true,
						scope : this,
						handler : function() {
							App.clickTopTab('VChargeDetail', {
										businessType : 'Guarantee',
										title : '利息还款台账'
									});
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				defaults : {
					anchor : '100%',
					scale : 'large',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【模块管理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '合同管理',
						disabled : isGranted('ContractManager?businessType=Guarantee')
								? false
								: true,
						iconCls : 'btn-team31',
						scope : this,
						handler : function() {
							App.clickTopTab('ContractManager',{businessType : 'Guarantee'});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '抵质押物管理',
						disabled : isGranted('MortgageManagement?businessType=Guarantee')
								? false
								: true,
						iconCls : 'btn-team32',
						scope : this,
						handler : function() {
							App.clickTopTab('MortgageManagement',{businessType : 'Guarantee'});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '归档管理',
						disabled : isGranted('PlProjectArchivesView?businessType=Guarantee')
								? false
								: true,
						iconCls : 'btn-team33',
						scope : this,
						handler : function() {
							App.clickTopTab('PlProjectArchivesView',{businessType : 'Guarantee'});
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					scale : 'large',
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【财务分析】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '担保明细表',
						disabled : isGranted('ReportMenuGuaranteeprojectdetail?reportKey=guaranteeprojectdetail')
								? false
								: true,
						iconCls : 'btn-team38',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuGuaranteeprojectdetail', {
										reportKey : 'guaranteeprojectdetail'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '担保款项明细表',
						disabled : isGranted('ReportMenuGuaranteeprojectintentdetail?reportKey=guaranteeprojectintentdetail')
								? false
								: true,
						iconCls : 'btn-team42',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuGuaranteeprojectintentdetail', {
										reportKey : 'guaranteeprojectintentdetail'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '担保财务明细表',
						disabled : isGranted('ReportMenuCommon?reportKey=guaranteeFinancialDetail')
								? false
								: true,
						iconCls : 'btn-team43',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuCommon', {
										reportKey : 'guaranteeFinancialDetail'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '贷款明细表',
						disabled : isGranted('ReportMenuCommon?reportKey=guaranteeLoanDetail')
								? false
								: true,
						iconCls : 'btn-team44',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuCommon', {
										reportKey : 'guaranteeLoanDetail'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '担保贷款分类表',
						disabled : isGranted('ReportMenuCommon?reportKey=securedLoanClassification')
								? false
								: true,
						iconCls : 'btn-team44',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuCommon', {
										reportKey : 'securedLoanClassification'
									});
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:15px",
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:0px",
					border : false,
					scale : 'large',
					columnWidth : .12
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【业务配置】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '合同模板',
						disabled : isGranted('CreditDocumentManager?businessType=Guarantee')
								? false
								: true,
						iconCls : 'btn-team47',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('CreditDocumentManager', {
										businessType : 'Guarantee'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '文档模板',
						disabled : isGranted('DownLoadDocumentManager?businessType=Guarantee')
								? false
								: true,
						iconCls : 'btn-team48',
						scope : this,
						handler : function() {
							App.clickTopTab('DownLoadDocumentManager', {
										businessType : 'Guarantee'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '单级数据字典',
								disabled : isGranted('DicManager')
										? false
										: true,
								iconCls : 'btn-team51',
								scope : this,
								handler : function() {
									App.clickTopTab('DicManager');
								}
							}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '银行管理',
								disabled : isGranted('CsBankView')
										? false
										: true,
								iconCls : 'btn-team52',
								scope : this,
								handler : function() {
									App.clickTopTab('CsBankView');
								}
							}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					columnWidth : .12,
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '地区管理',
								disabled : isGranted('AreaDicView?treeId=1')
										? false
										: true,
								iconCls : 'btn-team53',
								scope : this,
								handler : function() {
									App.clickTopTab('AreaDicView', {
												treeId : 1
											});
								}
							}]
				}]
			}]

		})

	}

});
