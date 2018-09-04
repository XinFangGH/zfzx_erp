/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

CsBankTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsBankTab.superclass.constructor.call(this, {
							id : 'PlBusinessBidPublishTab'+this.openType,
							title : '第三方银行管理',
							iconCls : "btn-tree-team29",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var proType;
				if(this.openType=="dir")proType="B_Dir";
				if(this.openType=="or")proType="B_Or";
				this.tabpanel = new Ext.TabPanel({
					resizeTabs : true,
					minTabWidth :100,
					tabWidth : 150,
					enableTabScroll : true,
					Active : 0,
					defaults : {
						autoScroll : true
					},
					region : 'center', 
					deferredRender : true,
					activeTab :0,
					items : [{ 	 
							         id:'b_tab'+this.openType+'1',
							         title:'通用支付',
							         items:[new CsBankView1({thirdBandType:1})]  
						        },  
						        {  
							         title:'国付宝',
							         id:'b_tab'+this.openType+'2',
							         items:[new CsBankView1({thirdBandType:2})] 
						        } , {  
							         title:'富有支付（富有金账户）',
							         id:'b_tab'+this.openType+'3',
							         items:[new CsBankView1({thirdBandType:3})] 
						        } ,  
						        {  
							         title:'汇付天下支付',
							         id:'b_tab'+this.openType+'4',
							         items:[new CsBankView1({thirdBandType:4})] 
						        }  , 
						        {  
						        	 title:'双乾支付',
						        	 id:'b_tab'+this.openType+'5',
						        	 items:[new CsBankView1({thirdBandType:5})] 
						        }  ,
						        {  
							         title:'易生支付',
							         id:'b_tab'+this.openType+'6',
							         items:[new CsBankView1({thirdBandType:6})] 
						        }  
						        ,
						        {  
							         title:'易宝支付',
							         id:'b_tab'+this.openType+'7',
							         items:[new CsBankView1({thirdBandType:7})] 
						        }  
						        ,
						        {  
							         title:'宝付支付',
							         id:'b_tab'+this.openType+'8',
							         items:[new CsBankView1({thirdBandType:8})] 
						        }  
//						        ,
//						        {  
//						        title:'起息办理中',
//						         id:'b_tab'+this.openType+'6',
//						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:6,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:6,Q_proType_S_EQ:proType})]  
//						        }  
					]
				});
				/*	var  tabType=this.openType;
				var ProType;
				if(tabType=="dir")ProType="B_Dir";
				if(tabType=="or")ProType="B_Or";
				
				var gp=this.openType=="dir"?Ext.getCmp("PlBusinessDirBidPublishGrid"):Ext.getCmp("PlBusinessOrBidPublishGrid");
			this.tabpanel.on("tabchange",function(e,args){ // 0-4 分别代表状态
					         if(args.getItemId()=='b_tab'+tabType+'0')  //b 代表企业 p 代表个人  这样保证id不重复
						     {  
						     	reloadGp(args,gp,0,ProType);
						     }  
						     else if(args.getItemId()=='b_tab'+tabType+'1')  
						     {  
						      reloadGp(args,gp,1,ProType);
						     } else if(args.getItemId()=='b_tab'+tabType+'2')  
						     {  
						      reloadGp(args,gp,2,ProType);
						     } else if(args.getItemId()=='b_tab'+tabType+'3')  
						     {  
						      reloadGp(args,gp,3,ProType);
						     } else if(args.getItemId()=='b_tab'+tabType+'4')  
						     {  
						      reloadGp(args,gp,4,ProType);
						     }  
                  });*/
                   //加载 gridePanel
                  function reloadGp(args,gp,val,proType){
                  	 gp.getStore().removeAll();  
                     gp.getStore().load({params:{Q_state_N_EQ: val,Q_proType_S_EQ:proType}});  
                     if(val==0){
                     	gp.tbar=true;
                     }
                     args.add(gp);  
                    args.doLayout();  
                  }
					
			}
			
});