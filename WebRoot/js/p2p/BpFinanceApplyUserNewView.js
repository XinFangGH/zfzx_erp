/**
 * @author
 * @class BpFinanceApplyUserNewView
 * @extends Ext.Panel
 * @description [BpFinanceApplyUserNewView]管理
 * @company 智维软件
 * @createtime:
 */
BpFinanceApplyUserNewView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpFinanceApplyUserNewView.superclass.constructor.call(this, {
			id : 'BpFinanceApplyUserNewView',
			title : '注册用户申请处理',
			region : 'center',
			layout : 'border',
			items : [this.tabpanel]
		});
	},
	// 初始化组件
	initUIComponents : function() {
			/*var itemschange=[
				new BpFinanceApplyUserTypeView({type:0}),//未提交
			 	new BpFinanceApplyUserTypeView({type:1}),//已提交
			 	new BpFinanceApplyUserTypeView({type:2}),//初步受理
			 	new BpFinanceApplyUserTypeView({type:3}),//打回补充
			 	new BpFinanceApplyUserTypeView({type:4}),//通过审核
			 	//5立项中
			 	new BpFinanceApplyUserTypeView({type:5}),//发标中
			 	new BpFinanceApplyUserTypeView({type:6}),//已发标
			 	new BpFinanceApplyUserTypeView({type:7})//未通过终止
			];*/
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
					title:"未    提     交",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:0}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_0')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_0').destroy();
			            	}
		            	}
					}
				},{
					title:"已    提     交",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:1}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_1')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_1').destroy();
			            	}
		            	}
		            }
				},{
					title:"初步受理     ",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:2}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_2')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_2').destroy();
			            	}
		            	}
		            }
				},{
					title:"打回补充     ",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:3}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_3')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_3').destroy();
			            	}
		            	}
		            }
				},{
					title:"通过审核     ",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:4}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_4')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_4').destroy();
			            	}
		            	}
		            }
				},{
					title:"发      标     中",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:5}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_5')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_5').destroy();
			            	}
		            	}
		            }
				},{
					title:"已     发     标",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:6}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_6')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_6').destroy();
			            	}
		            	}
		            }
				},{
					title:"未通过终止",
					listeners:{
		            	'activate':function(v){
		            		v.add(new BpFinanceApplyUserTypeView({type:7}));
		            		v.doLayout();
		            	},
		            	'deactivate':function(v){
		            		if(Ext.getCmp('BpFinanceApplyUserTypeView_7')){
			            		Ext.getCmp('BpFinanceApplyUserTypeView_7').destroy();
			            	}
		            	}
		            }
				}]
			});
		}
});
