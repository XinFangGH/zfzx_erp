/**
 * 
 * 企业直投项目 债权项目 页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * this.openType  dir 直投  or 债权
 */

PlBusinessBidPublishTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlBusinessBidPublishTab.superclass.constructor.call(this, {
							id : 'PlBusinessBidPublishTab'+this.openType,
							title : this.openType=='dir'?'企业直投发布管理':'企业债权发布管理',
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
		/*		this.tabpanel1 = new Ext.TabPanel({
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
					items : [{ 	 id:'b_tab'+this.openType+'21',
						         title:'待预售',
						            items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:21,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:21,Q_proType_S_EQ:proType})]  
						        },  
						        {  
						        title:'预售中',
						         id:'b_tab'+this.openType+'22',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:22,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:22,Q_proType_S_EQ:proType})]  
						        }  ,  
						        {  
						        title:'招标中',
						         id:'b_tab'+this.openType+'23',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:23,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:23,Q_proType_S_EQ:proType})]  
						        }
					]
				});*/
		
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
					items : [{ 	 id:'b_tab'+this.openType+'0',
						         title:'待发布',
						         iconCls : 'btn-team40',
						            items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:0,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:0,Q_proType_S_EQ:proType})]  
						        },  
						        { 	 id:'b_tab'+this.openType+'21',
						         title:'待预售',
						          iconCls : 'btn-team40',
						            items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:21,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:21,Q_proType_S_EQ:proType})]  
						        },  
						        {  
						        title:'预售中',
						         id:'b_tab'+this.openType+'22',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:22,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:22,Q_proType_S_EQ:proType})]  
						        }  ,  
						        {  
						        title:'招标中',
						         id:'b_tab'+this.openType+'23',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:23,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:23,Q_proType_S_EQ:proType})]  
						        } ,  
						        {  
						        title:'已齐标',
						         id:'b_tab'+this.openType+'2',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:2,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:2,Q_proType_S_EQ:proType})]  
						        }  ,  
						        {  
						        	title:'已过期',
						        	id:'b_tab'+this.openType+'',
						        	 iconCls : 'btn-team40',
						        	items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:4,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:4,Q_proType_S_EQ:proType})]  
						        }  ,
						        {  
						        title:'已流标',
						         id:'b_tab'+this.openType+'3',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:3,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:3,Q_proType_S_EQ:proType})]  
						        }  , 
						        
						        {  
						        title:'已关闭',
						         id:'b_tab'+this.openType+'-1',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:-1,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:-1,Q_proType_S_EQ:proType})]  
						        }   ,
						        {  
						        title:'起息办理中',
						         id:'b_tab'+this.openType+'6',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:6,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:6,Q_proType_S_EQ:proType})]  
						        } ,
						        {  
						        title:'还款中',
						         id:'b_tab'+this.openType+'7',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:7,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:7,Q_proType_S_EQ:proType})]  
						        } ,
						        {  
						        title:'已完成',
						         id:'b_tab'+this.openType+'10',
						          iconCls : 'btn-team40',
						         items:[this.openType=='dir'?new PlBusinessDirBidPublish({Q_state_N_EQ:10,Q_proType_S_EQ:proType}):new PlBusinessOrBidPublish({Q_state_N_EQ:10,Q_proType_S_EQ:proType})]  
						        }  
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