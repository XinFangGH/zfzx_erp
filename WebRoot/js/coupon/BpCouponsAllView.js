/**
 * @author
 * @class SlFundintentUrgeView
 * @extends Ext.Panel
 * @description [SlFundintentUrge]管理
 * @company 智维软件
 * @createtime:
 */
BpCouponsAllView= Ext.extend(Ext.Panel, {
			// 构造函数
	        activeTab1:0,
	        onclickflag:0,
	        onclickisInterent:0,
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				if (typeof (this.activeTab1) == "undefined") {
					
					this.activeTab1 = 0;
				}
				if (typeof (this.onclickflag) == "undefined") {
					this.onclickflag =0;
				}
				if (typeof (this.onclickisInterent) == "undefined") {
					this.onclickisInterent =0;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCouponsAllView.superclass.constructor.call(this, {
							id : 'BpCouponsAllView',
							title : '优惠券批量派发',
							 iconCls:"menu-finance",
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var itemschange=[new BpCouponsNotView({distributestatus:0}),
				                 new BpCouponsEdView({distributestatus:1})
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
					//layout:'fit'
					deferredRender : true,
					activeTab : this.activeTab1, // first tab initially active
					xtype : 'tabpanel',
						items : itemschange
					});
					
			}// end of the initComponents()
		
});
