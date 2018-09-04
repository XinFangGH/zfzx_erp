AppHomeTeamPawn = Ext.extend(Ext.Panel, { 
	
	portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AppHomeTeamPawn.superclass.constructor.call(this, {
			title : '典当桌面',
			closable : false,
			id : 'AppHomeTeamPawn',
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
				url : __ctxPath + '/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
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
		
			App.clickTopTabremoveOld=function(id,params,precall,activeid){
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
									'<input id="searchKeyword" type="text" style="color:gray" value="请输入项目关键字，例如项目编号、项目名称" onBlur="changeText(0,this)" onFocus="changeText(1,this)"/>'+
								'</div>'+
								'<div class="search_01_txt_rit"><img src="images/imagesteam/search_txt_rit.gif" /></div>'+
							'</div>'+
							'<div class="search_01_btn">'+
								'<input id="searchKeyword_button" type="image" src="images/imagesteam/search_btn.gif" onclick="searchProject()"/>'+
							'</div>'+
						'</div>'+
						
					/*	'<div class="remind">'+
							'<ul>'+
								'<li><span>今&nbsp;天&nbsp;内&nbsp;到&nbsp;期：本金 </span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:1,onclickflag:1,onclickisInterent:1},null,1)>{sizetoday}</a><span>利息</span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:1,onclickflag:1,onclickisInterent:2},null,1)>{sizetodayInterent}</a></li>'+
								'<li><span>五天内到期：本金 </span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:1,onclickflag:2,onclickisInterent:1},null,1)>{size5}</a><span>利息</span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:1,onclickflag:2,onclickisInterent:2},null,1)>{size5Interent}</a></li>'+

							'</ul>'+
							'<ul>'+
								'<li><span>一个月内到期：本金 </span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:1,onclickflag:3,onclickisInterent:1},null,1)>{sizemonth}</a><span>利息</span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:1,onclickflag:3,onclickisInterent:2},null,1)>{sizemonthInterent}</a></li>'+
								'<li><span>已逾期款项：本金 </span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:2,onclickflag:4,onclickisInterent:1},null,2)>{sizeoverdue}</a><span>利息</span><a href="#" onclick=App.clickTopTabremoveOld("SlFundintentUrgTab",{activeTab1:2,onclickflag:4,onclickisInterent:2},null,2)>{sizeoverdueInterent}</a></li>'+

							'</ul>'+

						'</div>'+*/
						
						'<div class="projects">'+
							'<img src="images/imagesteam/projects_bg01.gif" /><br />'+
							'<span><b>业务信息：</b>项目控制信息--客户信息--资金款项信息--放款收息表--手续费用收取清单</span><br />'+
							'<span><b>风险控制：</b>贷款准入原则--贷款资料清单--第一还款来源--担保措施--尽职调查报告--风险分析报告--审贷会情况--贷后监管记录--展期记录--提前还款记录--利率变更记录--归档记录</span><br />'+
							'<span><b>合同文书：</b>贷款合同--担保措施合同</span><br />'+
						'</div>'+
					'</div>'+
					'<div class="top_box_rit"><img src="images/imagesteam/topbox_rit.gif" /></div>'+
				'</div>'+
				/*'<div class="mid_box">'+
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
							'<button class="midbox_bg_cent" type="button"><span>[业务员]</span> 贷款结清</button>'+
							'<div class="midbox_bg_rit"><img src="images/imagesteam/midbox_bg_rit.gif" /></div>'+
						'</div>'+
						'<div class="arrow01">'+
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
				/*Ext.Ajax.request({
					method : 'POST',
					scope : this,
					url : __ctxPath
							+ '/creditFlow/finance/mangOfintentexpireSlFundIntent.do',
					params : {
						businessType : 'all'
					},
					success : function(response, request) {
						var obj = Ext.util.JSON.decode(response.responseText);
						
						 * sizetoday=obj.sizetoday; size5=obj.size5;
						 * sizemonth=obj.sizemonth; sizeoverdue=obj.sizeoverdue;
						 * 
						 * sizetodayInterent=obj.sizetodayInterent;
						 * size5Interent=obj.size5Interent;
						 * sizemonthInterent=obj.sizemonthInterent;
						 * sizeoverdueInterent=obj.sizeoverdueInterent;
						 
						tpl.overwrite(this.seachPanel.body, obj);

					}
				})*/
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【任务处理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '贷款申请',
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
						text : '我参与的任务',
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
			}/*,{
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
						html : '<B><font class="x-myZW-fieldset-title">【贷款办理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '贷款尽调',
						disabled : isGranted('LoanAccept')
								? false
								: true,
						iconCls : 'btn-team7',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('LoanAccept', {
										projectStatus : 0
									},null,null,1);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '风险审核',
						disabled : isGranted('RiskReview')
								? false
								: true,
						iconCls : 'btn-team8',
						scope : this,
						handler : function() {
							App.clickTopTab('RiskReview', {
										projectStatus : 2
									},null,null,1);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '贷款审批',
						disabled : isGranted('LoanApproval')
								? false
								: true,
						iconCls : 'btn-team9',
						scope : this,
						handler : function() {
							App.clickTopTab('LoanApproval', {
										projectStatus : 2
									},null,null,1);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '贷款发放',
						iconCls : 'btn-team10',
						disabled : isGranted('LoanProvide')
								? false
								: true,
						scope : this,
						handler : function() {
							App.clickTopTab('LoanProvide', {
										projectStatus : 3
									},null,null,1);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '贷款结清',
						disabled : isGranted('LoanClosing')
								? false
								: true,
						iconCls : 'btn-team11',
						scope : this,
						handler : function() {
							App.clickTopTab('LoanClosing',null,null,1);
						}
					}]
				}]
			}*/,{
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【当后管理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '还款催收',
						disabled : isGranted('SlFundintentUrgTab?businessType=Pawn')
								? false
								: true,
						iconCls : 'btn-team13',
						scope : this,
						handler : function() {
							App.clickTopTab('SlFundintentUrgTab',{businessType:'Pawn'});
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						
						text : '续当办理',
						disabled : isGranted('PawnProjectManagment?type=1')
								? false
								: true,
						iconCls : 'btn-team12',
						scope : this,
						handler : function() {
							App.clickTopTab('PawnProjectManagment',{type:1});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '赎当办理',
						disabled : isGranted('PawnProjectManagment?type=2')
								? false
								: true,
						iconCls : 'btn-team14',
						scope : this,
						handler : function() {
							App.clickTopTab('PawnProjectManagment',{type:2});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '绝当办理',
						disabled : isGranted('PawnProjectManagment?type=3')
								? false
								: true,
						iconCls : 'btn-team15',
						scope : this,
						handler : function() {
							App.clickTopTab('PawnProjectManagment',{type:3});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '当票挂失',
						disabled : isGranted('PawnProjectManagment?type=13')
								? false
								: true,
						iconCls : 'btn-team58',
						scope : this,
						handler : function() {
							App.clickTopTab('PawnProjectManagment',{type:13});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '当票转当',
						disabled : isGranted('PawnProjectManagment?type=5')
								? false
								: true,
						iconCls : 'btn-team59',
						scope : this,
						handler : function() {
							App.clickTopTab('PawnProjectManagment',{type:5});
						}
					}]
				}
			]
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【典当业务查询】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '全部典当业务',
						disabled : isGranted('PlPawnProjectManager?projectStatus=7')
								? false
								: true,
						iconCls : 'btn-team16',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('PlPawnProjectManager', {
										projectStatus : 7
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '审批中业务',
						disabled : isGranted('PlPawnProjectManager?projectStatus=0')
								? false
								: true,
						iconCls : 'btn-team17',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('PlPawnProjectManager', {
										projectStatus : 0
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
						text : '在当业务',
						disabled : isGranted('PlPawnProjectManager?projectStatus=1')
								? false
								: true,
						iconCls : 'btn-team18',
						scope : this,
						handler : function() {
							App.clickTopTab('PlPawnProjectManager', {
										projectStatus : 1
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
						text : '续当业务',
						disabled : isGranted('PlPawnProjectManager?projectStatus=4')
								? false
								: true,
						iconCls : 'btn-team60',
						scope : this,
						handler : function() {
							App.clickTopTab('PlPawnProjectManager', {
										projectStatus : 4
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
						text : '赎当业务',
						iconCls : 'btn-team61',
						disabled : isGranted('PlPawnProjectManager?projectStatus=5')
								? false
								: true,
						scope : this,
						handler : function() {
							App.clickTopTab('PlPawnProjectManager', {
										projectStatus : 5
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
						text : '绝当业务',
						iconCls : 'btn-team19',
						disabled : isGranted('PlPawnProjectManager?projectStatus=6')
								? false
								: true,
						scope : this,
						handler : function() {
							App.clickTopTab('PlPawnProjectManager', {
										projectStatus : 6
									});
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【当物管理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '在当物品',
						disabled : isGranted('PawnItemsListManagment?type=underway')
								? false
								: true,
						iconCls : 'btn-team16',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('PawnItemsListManagment', {
										type : 'underway'
									});
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						text : '赎当物品',
						disabled : isGranted('PawnItemsListManagment?type=finish')
								? false
								: true,
						iconCls : 'btn-team17',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('PawnItemsListManagment', {
										type : 'finish'
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
						text : '绝当物品',
						disabled : isGranted('PawnItemsListManagment?type=vast')
								? false
								: true,
						iconCls : 'btn-team18',
						scope : this,
						handler : function() {
							App.clickTopTab('PawnItemsListManagment', {
										type : 'vast'
									});
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
						width : 120,
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【款项台账】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '本金放款台账',
						disabled : isGranted('TabSlFundIntentPrincipalPay?businessType=Pawn')
								? false
								: true,
						iconCls : 'btn-team29',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('TabSlFundIntentPrincipalPay',{
										businessType : 'Pawn'
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
						text : '本金收款台账',
						disabled : isGranted('TabSlFundIntentprincipalIncome?businessType=Pawn') ? false : true,
						scope : this,
						handler : function() {
							App.clickTopTab('TabSlFundIntentprincipalIncome', {
										businessType : 'Pawn'
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
						text : '利息收款台账',
						disabled : isGranted('TabSlFundIntentInterestIncome?businessType=Pawn') ? false : true,
						scope : this,
						handler : function() {
							App.clickTopTab('TabSlFundIntentInterestIncome', {
										businessType : 'Pawn'
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
						text : '其他支出台账',
						disabled : isGranted('TabSlFundIntentOtherPay?businessType=Pawn') ? false : true,
						scope : this,
						handler : function() {
							App.clickTopTab('TabSlFundIntentOtherPay', {
										businessType : 'Pawn'
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
						text : '本金利息台账日志',
						disabled : isGranted('VFundDetail?businessType=Pawn') ? false : true,
						scope : this,
						handler : function() {
							App.clickTopTab('VFundDetail', {
										businessType : 'Pawn'
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
						iconCls : 'btn-team28',
						text : '银行账户明细',
						disabled : isGranted('SlBankAccountQlideView')
								? false
								: true,
						scope : this,
						handler : function() {
							App.clickTopTab('SlBankAccountQlideView');
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【模块管理】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '合同管理',
						disabled : isGranted('ContractManager?businessType=Pawn')
								? false
								: true,
						iconCls : 'btn-team31',
						scope : this,
						handler : function() {
							App.clickTopTab('ContractManager', {
										businessType : 'Pawn'
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
						text : '归档管理',
						disabled : isGranted('PlProjectArchivesView?businessType=Pawn')
								? false
								: true,
						iconCls : 'btn-team33',
						scope : this,
						handler : function() {
							App.clickTopTab('PlProjectArchivesView', {
										businessType : 'Pawn'
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【资信评估】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '指标体系管理',
						disabled : isGranted('IndicatorManagement')
								? false
								: true,
						iconCls : 'btn-team34',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('IndicatorManagement');
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
								xtype : 'button',
								anchor : '100%',
								height : btnheigth,
								text : '信用等级管理',
								disabled : isGranted('ClassTypeView')
										? false
										: true,
								iconCls : 'btn-team35',
								scope : this,
								handler : function() {
									App.clickTopTab('ClassTypeView');
								}
							}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '评估模板管理',
						disabled : isGranted('TemplateManagement')
								? false
								: true,
						iconCls : 'btn-team36',
						scope : this,
						handler : function() {
							App.clickTopTab('TemplateManagement');
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					style : "margin-left:15px;",
					items : [{
						xtype : 'button',
						anchor : '100%',
						height : btnheigth,
						text : '客户资信评估',
						disabled : isGranted('CreditRatingManage?type=all')
								? false
								: true,
						iconCls : 'btn-team37',
						scope : this,
						handler : function() {
							App.clickTopTab('CreditRatingManage', {
										type : 'all'
									});
						}
					}]
				}]
			}/*, {
				columnWidth : 1,
				layout : 'column',
				style : "margin-bottom:11px",
				height : 30,
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;",
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
						html : '<B><font class="x-myZW-fieldset-title">【贷款报表】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '贷款信息明细表',
						disabled : isGranted('ReportMenuSmallloanprojectdetail?reportKey=projectdetail')
								? false
								: true,
						iconCls : 'btn-team38',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuSmallloanprojectdetail', {
										reportKey : 'projectdetail'
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
						text : '本金利息明细表',
						disabled : isGranted('ReportMenuSmallloanprojectintentdetail?reportKey=projectintentdetail')
								? false
								: true,
						iconCls : 'btn-team39',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuSmallloanprojectintentdetail', {
										reportKey : 'projectintentdetail'
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
						text : '手续收入明细表',
						disabled : isGranted('ReportMenuSmallloanprojectintentdetail?reportKey=projectchargedetail')
								? false
								: true,
						iconCls : 'btn-team40',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportMenuSmallloanprojectintentdetail', {
										reportKey : 'projectchargedetail'
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
						text : '资金流预测分析',
						disabled : isGranted('ReportTemplateMenuCapital')
								? false
								: true,
						iconCls : 'btn-team41',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('ReportTemplateMenuCapital', {
										reportKey : 'CapitalFlow'
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
						text : '数量统计分析',
						disabled : isGranted('ReportTemplateMenuCapital')
								? false
								: true,
						iconCls : 'btn-team42',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportTemplateMenuCapital', {
										reportKey : 'projectNum'
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
						text : '金额统计分析',
						disabled : isGranted('ReportTemplateMenuCapital')
								? false
								: true,
						iconCls : 'btn-team43',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportTemplateMenuCapital', {
										reportKey : 'projectMoney'
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
						text : '利息统计分析',
						disabled : isGranted('ReportTemplateMenuCapital')
								? false
								: true,
						iconCls : 'btn-team44',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportTemplateMenuCapital', {
										reportKey : 'projectAccrual'
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
						text : '年化净利率分析',
						disabled : isGranted('ReportTemplateMenuCapital')
								? false
								: true,
						iconCls : 'btn-team45',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportTemplateMenuCapital', {
										reportKey : 'accralRates'
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
						text : '年利润率分析',
						disabled : isGranted('ReportTemplateMenuCapital')
								? false
								: true,
						iconCls : 'btn-team46',
						scope : this,
						handler : function() {
							App.clickTopTab('ReportTemplateMenuCapital', {
										reportKey : 'newProfit'
									});
						}
					}]
				}]
			}*/, {
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
						width : 120,
						html : '<B><font class="x-myZW-fieldset-title">【业务配置】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '合同模板',
						disabled : isGranted('CreditDocumentManager?businessType=Pawn')
								? false
								: true,
						iconCls : 'btn-team47',
						anchor : '100%',
						height : btnheigth,
						scope : this,
						handler : function() {
							App.clickTopTab('CreditDocumentManager', {
										businessType : 'Pawn'
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
						disabled : isGranted('DownLoadDocumentManager?businessType=Pawn')
								? false
								: true,
						iconCls : 'btn-team48',
						scope : this,
						handler : function() {
							App.clickTopTab('DownLoadDocumentManager', {
										businessType : 'Pawn'
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
									App.clickTopTab('CsBankView', {
												treeId : 89
											});
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
