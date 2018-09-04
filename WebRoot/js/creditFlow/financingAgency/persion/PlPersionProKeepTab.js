/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

PlPersionProKeepTab= Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				
				Ext.apply(this, _cfg);
				//alert(this.openType);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlPersionProKeepTab.superclass.constructor.call(this, {
							id : 'PlPersionProKeepTab'+this.openType+this.subType,
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
					this.openType=='dir'?new BpPersionDirProAllView({hiddenInfo:true,managerType:"bulkProduct",subType:this.subType}):new BpPersionOrProAllView({hiddenInfo:true,managerType:"bulkProduct",orginalType:this.orginalType,openType:this.openType}),
					this.openType=='dir'?new BpPersionDirProStat0View({hiddenInfo:true,State:0,managerType:"bulkProduct",subType:this.subType}):new BpPersionOrProStat0View({hiddenInfo:true,State:0,managerType:"bulkProduct",orginalType:this.orginalType,openType:this.openType}),//待公示
					this.openType=='dir'?new PlPersionDirProKeepView({hiddenInfo:true,type:"P_Dir",managerType:"successProduct",subType:this.subType}):new PlPersionOrProKeepView({hiddenInfo:true,type:"P_Or",managerType:"successProduct",orginalType:this.orginalType,openType:this.openType})//已公示
					]
				});
					
			}
			
});