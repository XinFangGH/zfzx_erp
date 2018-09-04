/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

PlBusinessProPlanTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlBusinessProPlanTab.superclass.constructor.call(this, {
							id : 'PlBusinessProPlanTab'+this.openType,
							title : this.openType=='dir'?'企业直投计划管理':'企业债权计划管理',
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
						this.openType=='dir'?new PlBusinessDirPlanView({hiddenInfo:true,State:0,managerType:"bulkProduct"}):new PlBusinessOrPlanView({hiddenInfo:true,State:0,managerType:"bulkProduct"}),//未完成方案
						this.openType=='dir'?new PlBusinessDirPlanView({hiddenInfo:true,State:1,managerType:"successProduct"}):new PlBusinessOrPlanView({hiddenInfo:true,State:1,managerType:"successProduct"})//已完成方案
					]
				});
					
			}
			
});