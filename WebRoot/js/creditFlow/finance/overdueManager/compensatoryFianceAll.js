//compensatoryFianceAll

  compensatoryFianceAll = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				compensatoryFianceAll.superclass.constructor.call(this, {
							id : 'compensatoryFianceAll',
							region : 'center',
							layout : 'border',
							title:'代偿追偿提醒',
							iconCls : 'btn-tree-team30',
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				
				this.gridPanel = new compensatoryFiance({
					subType:"",
					title:null,
					planType:null,
					backFlow:true,
					backFlowRecord:true
				});


			}
	
	
});