/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

PlManageMoneyPlanTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlManageMoneyPlanTab.superclass.constructor.call(this, {
							id : 'PlManageMoneyPlanTab',
							title : '理财计划招标发布',
							iconCls : "btn-tree-team29",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.PlManageMoneyPlanBidPublish=new PlManageMoneyPlanBidPublish({yespublish:false,State:0});
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
						         title:'待发标',
						            items:[this.PlManageMoneyPlanBidPublish]  
						        },  
						        {  
						        title:'招标中',
						         id:'b_tab1'
						        }  ,  
						        {  
						        title:'已齐标',
						         id:'b_tab2'
						        } ,  
						        {  
						        title:'已过期',
						         id:'b_tab4'
						        } ,  
						        {  
						        title:'手动关闭',
						         id:'b_tabf1'
						        } ,  
						        {  
						        title:'已起息',
						         id:'b_tab7'
						        } ,  
						        {  
						        title:'已完成',
						         id:'b_tab6'
						        }]
				});
				var  tabType=this.openType;
				
				var gp=Ext.getCmp("PlManageMoneyPlanGridhpublishgrid");
				this.tabpanel.on("tabchange",function(e,args){ // -1-4 分别代表状态
					         if(args.getItemId()=='b_tab0')  //b 代表企业 p 代表个人  这样保证id不重复
						     {  
						     	reloadGp(args,gp,0);
						     }  
						     else if(args.getItemId()=='b_tab1')  
						     {  
						      reloadGp(args,gp,1);
						     } else if(args.getItemId()=='b_tab2')  
						     {  
						      reloadGp(args,gp,2);
						     } else if(args.getItemId()=='b_tab4')  
						     {  
						      reloadGp(args,gp,4);
						     }else if(args.getItemId()=='b_tabf1')  
						     {  
						      reloadGp(args,gp,-1);
						     } else if(args.getItemId()=='b_tab7'){
						     	reloadGp(args,gp,7);
						     }else if(args.getItemId()=='b_tab6'){
						     	reloadGp(args,gp,10);
						     }
                  });
                   //加载 gridePanel
                  function reloadGp(args,gp,val,proType){
                  	 gp.getStore().removeAll();  
                //  	  gp.getStore().setBaseParam('Q_state_N_EQ',val) ; 
                  	 var params1={
                  	 'Q_state_N_EQ':val
                  	  
                  	 }
                  	
						 gp.getStore().on('beforeload', function(gridstore, o) {
							
							Ext.apply(o.params, params1);
						});
						
                     gp.getStore().reload();  
                     if(val==0){
                     	gp.tbar=true;
                     	
                     }
                     if(val==0 ){
                     	 Ext.getCmp("seemmplan").hide();	
                     }else{
                     	Ext.getCmp("seemmplan").show();
                     }
                     if(val==2 || val==4){
                     	 Ext.getCmp("startmmplan").show();	
                     }
                     if(val==2 ||val==1 || val==4){
                      Ext.getCmp("startmmplan").show();
                     }else{
                       Ext.getCmp("startmmplan").hide();
                     }
                     
                     if(val==0){
                      Ext.getCmp("publishmmplan").show();
                     }else{
                      Ext.getCmp("publishmmplan").hide();
                     }
                     if(val!=7){
                     	 Ext.getCmp("Shouquanpaixi").hide();
                     	  Ext.getCmp("fininsh").hide();
                     }else{
                     	 Ext.getCmp("Shouquanpaixi").show();
                     	  Ext.getCmp("fininsh").show();
                     }
                      if(val==-1||val==3||val==7||val==10){
                       Ext.getCmp("closemmplan").hide();
                      }else{
                         Ext.getCmp("closemmplan").show();
                      }
                   /*   if(val==5||val==6){
                       Ext.getCmp("closemmplan").hide();
                      }else{
                         Ext.getCmp("closemmplan").show();
                      }*/
                     args.add(gp);  
                    args.doLayout(); 
                 //   this.PlManageMoneyPlanBidPublish.doLayout(); 
                  }
					
			}
			
});