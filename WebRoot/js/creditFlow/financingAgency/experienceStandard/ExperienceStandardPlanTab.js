/**
 * 
 * 体验标制定页签
 * @class PlProjKeepTab
 * @extends Ext.Panel
 * keystr  experience
 */

ExperienceStandardPlanTab= Ext.extend(Ext.Panel, {
			// 构造函数
	        
			constructor : function(_cfg) {
				Ext.apply(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ExperienceStandardPlanTab.superclass.constructor.call(this, {
							id : 'ExperienceStandardPlanTab',
							title : '体验标招标管理',
							iconCls:"menu-finance",
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.ExperienceStandardPlanPublish=new ExperienceStandardPlanPublish({yespublish:false,State:0});
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
					items : [{ 	 id:'e_tab0',
						         title:'待发布',
						            items:[this.ExperienceStandardPlanPublish]  
						        },
						        {  
						        title:'预售中',
						         id:'e_tab1_ysz'
						        },
						        {  
						        title:'招标中',
						         id:'e_tab1'
						        }  ,  
						        {  
						        title:'已齐标',
						         id:'e_tab2'
						        } ,  
						        {  
						        title:'已过期',
						         id:'e_tab4'
						        } ,  
						        {  
						        title:'已关闭',
						         id:'e_tabf1'
						        } ,  
						        {  
						        title:'还款中',
						         id:'e_tab7'
						        } ,  
						        {  
						        title:'已完成',
						         id:'e_tab6'
						    }]
				});
				
				var gp=Ext.getCmp("ExperienceStandardPlanPublishGrid");
				var search=Ext.getCmp("searchPanelExperience");
				this.tabpanel.on("tabchange",function(e,args){
					         if(args.getItemId()=='e_tab0'){  
								reloadGp(args,gp,0,'no',search);
						     }else if(args.getItemId()=='e_tab1_ysz'){
						     	reloadGp(args,gp,1,'ysz',search);
						     }else if(args.getItemId()=='e_tab1'){  
						     	reloadGp(args,gp,1,'zbz',search);
						     }else if(args.getItemId()=='e_tab2'){  
						        reloadGp(args,gp,2,'no',search);
						     }else if(args.getItemId()=='e_tab4'){  
						        reloadGp(args,gp,4,'no',search);
						     }else if(args.getItemId()=='e_tabf1'){  
						        reloadGp(args,gp,-1,'no',search);
						     }else if(args.getItemId()=='e_tab7'){
						     	reloadGp(args,gp,7,'no',search);
						     }else if(args.getItemId()=='e_tab6'){
						     	reloadGp(args,gp,10,'no',search);
						     }
                  });
                   //加载 gridePanel
                  function reloadGp(args,gp,val,saleValue,search){
                  	 gp.getStore().removeAll();  
                  	 var params1={'Q_state_N_EQ':val,'Q_keystr_S_EQ':"experience",'isPresale':saleValue}

					 gp.getStore().on('beforeload', function(gridstore, o){
						Ext.apply(o.params, params1);
					 });
					 
                     gp.getStore().reload();  
                     /*if(val==0){
                     	gp.tbar=true;
                     }*/
                     
                     if(val==0){
                     	Ext.getCmp("addExperience").show();
                     	Ext.getCmp("editExperience").show();
                     }else{
                     	Ext.getCmp("addExperience").hide();	
                     	Ext.getCmp("editExperience").hide();	
                     }
                     
                     if(val==0 ){
                     	Ext.getCmp("seeExperience").hide();	
                     }else{
                     	Ext.getCmp("seeExperience").show();
                     }
                     
                     if(val==2 || val==4){
                     	Ext.getCmp("startExperience").show();	
                     }
                     
                     if(val==2 || val==4){
                        Ext.getCmp("startExperience").show();
                     }else{
                        Ext.getCmp("startExperience").hide();
                     }
                     
                     if(val==0){
                     	Ext.getCmp("publishExperience").show();
                     }else{
                     	Ext.getCmp("publishExperience").hide();
                     }
                     
                     if(val!=7){
                     	Ext.getCmp("fininshExperience").hide();
                     }else{
                     	Ext.getCmp("fininshExperience").show();
                     }
                     
                     
                     if(val==1){
                        Ext.getCmp("closeExperience").show();
                     }else{
                        Ext.getCmp("closeExperience").hide();
                     }
                     args.add(gp);  
                     args.add(search);  
                     args.doLayout(); 
                  }
					
			}
			
});