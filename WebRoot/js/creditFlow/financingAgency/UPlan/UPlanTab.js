/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

UPlanTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				UPlanTab.superclass.constructor.call(this, {
							id : 'UPlanTab'+this.keystr,
							title :this.keystr=='UPlan'? 'U计划招标发布':'D计划招标发布',
							iconCls : "btn-tree-team29",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var keystr=this.keystr;
				this.tabpanel = new Ext.TabPanel({
					resizeTabs : true,
					minTabWidth :55,
					tabWidth : 100,
					enableTabScroll : true,
					Active : 0,
					defaults : {
						autoScroll : true
					},
					region : 'center', 
					deferredRender : true,
					activeTab :0,
					items : [{
							name:'b_tab0',
						    title:'待发标',
						    iconCls : 'btn-team40',
							listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:0,keystr:keystr}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
						},{
				            title:'预售中',
				            name:'b_tab22',
				            iconCls : 'btn-team40',
				            listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:1,keystr:keystr,isPresale:'ysz'}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        },{
				            title:'招标中',
				            name:'b_tab1',
				            iconCls : 'btn-team40',
						    listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:1,keystr:keystr,isPresale:'zbz'}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        },{  
					        title:'已齐标',
					        name:'b_tab2',
					        iconCls : 'btn-team40',
							listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:2,keystr:keystr}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        },{  
				            title:'已过期',
				            name:'b_tab4',
				            iconCls : 'btn-team40',
						    listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:4,keystr:keystr}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        },{  
				            title:'已关闭',
				            name:'b_tabf1',
				            iconCls : 'btn-team40',
						    listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:-1,keystr:keystr}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        },{  
				            title:'还款中',
				            name:'b_tab7',
				            iconCls : 'btn-team40',
						    listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:7,keystr:keystr}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        },{  
				            title:'已完成',
				            name:'b_tab6',
				            iconCls : 'btn-team40',
						    listeners:{
				            	'activate':function(v){
				            		v.items.add(new UPlanBidPublish({state:10,keystr:keystr}));
				            		v.doLayout();
				            	},
				            	'deactivate':function(v){
				            		if(Ext.getCmp('UPlanBidPublish'+keystr)){
					            		Ext.getCmp('UPlanBidPublish'+keystr).destroy();
					            	}
				            	}
				            }
				        }]
				});
					
			}
			
});