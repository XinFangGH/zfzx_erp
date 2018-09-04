/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

PlBusinessProKeepTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlBusinessProKeepTab.superclass.constructor.call(this, {
							id : 'PlBusinessProKeepTab'+this.openType,
							title : '公示信息包装',
							iconCls : 'btn-tree-team30',
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
					//dir 直投标  or 债权标
						this.openType=='dir'?new BpBusinessDirProAllView({hiddenInfo:true,State:0,managerType:"bulkProduct"}):new BpBusinessOrProAllView({hiddenInfo:true,State:0,managerType:"bulkProduct",orginalType:this.orginalType,openType:this.openType}),//待公示
						this.openType=='dir'?new BpBusinessDirProStat0View({hiddenInfo:true,State:0,managerType:"bulkProduct"}):new BpBusinessOrProStat0View({hiddenInfo:true,State:0,managerType:"bulkProduct",orginalType:this.orginalType,openType:this.openType}),//待公示
						this.openType=='dir'?new PlBusinessDirProKeepView({hiddenInfo:true,type:"B_Dir",managerType:"successProduct"}):new PlBusinessOrProKeepView({hiddenInfo:true,type:"B_Or",managerType:"successProduct",orginalType:this.orginalType,openType:this.openType})//已公示
					]
				});
					
			}
			
});