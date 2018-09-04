/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

PlPersionProPlanTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlPersionProPlanTab.superclass.constructor.call(this, {
							id : 'PlPersionProPlanTab'+this.openType,
							title : this.openType=='dir'?'个人直投方案管理':'个人债权方案管理',
							iconCls : "btn-tree-team29",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.tabpanel = new Ext.TabPanel({
					resizeTabs : true,
					minTabWidth : 115,
					tabWidth : 135,
					enableTabScroll : true,
					Active : 0,
					width : 600,
					defaults : {
						autoScroll : true
					},
					region : 'center', 
					deferredRender : true,
					activeTab :0,
					items : [
						this.openType=='dir'?new PlPersionDirPlanView({hiddenInfo:true,State:0,managerType:"bulkProduct"}):new PlPersionOrPlanView({hiddenInfo:true,State:0,managerType:"bulkProduct"}),//未完成方案
						this.openType=='dir'?new PlPersionDirPlanView({hiddenInfo:true,State:1,managerType:"successProduct"}):new PlPersionOrPlanView({hiddenInfo:true,State:1,managerType:"successProduct"})//已完成方案
					]
				});
					
			}
			
});