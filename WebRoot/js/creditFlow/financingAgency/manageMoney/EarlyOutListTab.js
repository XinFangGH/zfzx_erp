/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

EarlyOutListTab= Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				EarlyOutListTab.superclass.constructor.call(this, {
							id : 'EarlyOutListTab'+this.keystr,
							title : '提前赎回审核',
							iconCls : "btn-tree-team29",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.EarlyOutList=new EarlyOutList({keystr:this.keystr,Q_checkStatus_SN_EQ:0});
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
					items : [{ 	 id:'b_tab0',
						         title:'待审核',
						            items:[this.EarlyOutList]  
						        },  
						        {  
						        title:'已通过',
						         id:'b_tab1'
						        }  ,  
						        {  
						        title:'已驳回',
						         id:'b_tab2'
						        }]
				});
				var  tabType=this.openType;
				
				var gp=Ext.getCmp("EarlyOutListGrid");
				var EarlyOutListsearchPanel=Ext.getCmp("EarlyOutListsearchPanel");
				this.tabpanel.on("tabchange",function(e,args){ // -1-4 分别代表状态
					         if(args.getItemId()=='b_tab0'){   //b 代表企业 p 代表个人  这样保证id不重复
						     	reloadGp(args,gp,0);
						     }else if(args.getItemId()=='b_tab1'){  
						        reloadGp(args,gp,1);
						     }else if(args.getItemId()=='b_tab2'){  
						        reloadGp(args,gp,3);
						     }
                  });
                   //加载 gridePanel
                  function reloadGp(args,gp,val,proType){
                  	 gp.getStore().removeAll();  
                  	 var params1={
	                  	'Q_checkStatus_SN_EQ':val,
	                  	'Q_keystr_S_EQ':this.keystr
                  	 }
                  	 gp.getStore().on('beforeload', function(gridstore, o) {
						Ext.apply(o.params, params1);
					 });
						
                     gp.getStore().reload();  
                     if(val==0){
                     	 Ext.getCmp("checkEarlyOutbtn").show();	
                     }
                     if(val==1 || val==3){
                     	 Ext.getCmp("checkEarlyOutbtn").hide();	
                     }
                     
                     args.add(EarlyOutListsearchPanel);  
                     args.add(gp);  
                     args.doLayout(); 
                  }
					
			}
			
});