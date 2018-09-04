//CsProspectivePersonTab.js
CsProspectivePersonTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsProspectivePersonTab.superclass.constructor.call(this, {
							id : 'CsProspectivePersonTab'+this.ProsperctiveType+this.personType,
							title : 0==this.personType?'借款意向客户管理':'投资意向客户管理',
							iconCls : 'btn-tree-team23',
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var itemschange=[
				 	new CsProspectivePersonView({ProsperctiveType:this.ProsperctiveType,title:"全部意向客户",otherType:0,personType:this.personType}),
				  	new CsProspectivePersonView({ProsperctiveType:this.ProsperctiveType,title:"已跟进意向客户",otherType:1,personType:this.personType}),
				  	new CsProspectivePersonView({ProsperctiveType:this.ProsperctiveType,title:"未跟进意向客户",otherType:2,personType:this.personType}),
					new CsProspectivePersonView({ProsperctiveType:this.ProsperctiveType,title:"待跟进意向客户",otherType:3,personType:this.personType})
					
				];

				this.tabpanel = new Ext.TabPanel({
					resizeTabs : true, // turn on tab resizing
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
					activeTab :0, // first tab initially active
					xtype : 'tabpanel',
						items : itemschange
					});
					
			}// end of the initComponents()
			
});