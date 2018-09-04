obligationProductTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
			
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				obligationProductTab.superclass.constructor.call(this, {
							id : 'obligationProductTab'+this.openType,
							title : '债权产品开关管理',
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
						new obligationproductInfoView({hiddenInfo:true,hiddenMapping:true,Confirmhidden:true,obligationState:0,managerType:"closeObligation"}),
						new obligationproductInfoView({hiddenInfo:true,hiddenMapping:true,Confirmhidden:true,obligationState:2,managerType:"openObligation"})
					]
				});
					
			}
			
});