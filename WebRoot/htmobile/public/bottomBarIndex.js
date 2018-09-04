
/**
 * 手机功能菜单
 * by cjj
 */

Ext.define('htmobile.public.bottomBarIndex', {
    extend: 'Ext.Container',
    name:'bottomBarIndex',
    constructor:function(config){
		Ext.apply(config,{docked:'bottom',laout:'hbox',items:[{
			xtype:"panel",
			style:"background: #fff;border-top:solid 1px #eeeeee",
			layout: {
						type: 'hbox',
						align: 'middle'
					},
			items:[{
				        xtype:'button',
					//	text:"<img  style='width:100%; height:auto; ' src='hrmobile/resources/imagesP2p/home_select.jpg'/>",
						width:"33.3%",
					//	icon:'hrmobile/resources/imagesP2p/home_select.jpg',
						style:'border-radius:0px; padding:0;border:0;border-top:1px solid #ddd;',
						height:55,
						style:"padding-top: 37px;font-size: 10px;color: #3DC4F6;",
				    	cls:'home_select',
				        this1:this,
				        pressedCls:'',
				        text:'首页',
						handler:this.toindex},
					{
						xtype:'button',
						style:'border-radius:0px; padding:0;border:0;border-top:1px solid #ddd;',
						cls:'wytz',
						pressedCls:'',
						this1:this,
					    nocls:'home',
					    noclsnumber:0,
						width:"33.3%",
						height:55,
						text:'客户管理',
						style:"padding-top: 37px;font-size: 10px;color: #3DC4F6;",
					    handler:this.toInvset},
					{
						xtype:'button',
						cls:'wyjk',
					    style:'border-radius:0px; padding:0;border:0;border-top:1px solid #ddd;',
						pressedCls:'',
						this1:this,
						nocls:'home',
					    noclsnumber:0,
						width:"33.3%",
						height:55,
						text:'个人中心',
						style:"padding-top: 37px;font-size: 10px;color: #3DC4F6;",
						handler:this.borrowMoney}/*,
					{
						xtype:'button',
						cls:'wdzh',
						pressedCls:'wdzh_select',
						this1:this,
						nocls:'home',
					    noclsnumber:0,
						width:"25%",
						height:55,
						style:'border-radius:0px; padding:0;border:0;border-top:1px solid #ddd;',
						style:"padding-top: 37px; font-size: 14px;",
						handler:this.myaccount
					}*/]			
			
			}]});
		
		this.callParent([config]);
		
    },
	toindex:function(){
				mobileNavi.reset();
			},
	toInvset: function(){
		this.overlay =Ext.Viewport.add({
		    		xtype:'panel',
		    		modal:true,
		    		hideOnMaskTap:true,
		    		centered:true,
		    		margin:'122% -23% 0 0',
		    		width:'146px',
		    		height:'80px',
		    		color:'#000',
		    		styleHtmlContent:false,
					items : [{
								docked : 'bottom',
								baseCls : "btn",
								xtype : 'button',
								style : "background:'#ffffff'",
								text : '<font style="line-height:36px;"><img src='
										+ __ctxPath
										+ '/htmobile/resources/images/jdsp_proj_con.jpg width="25" style="float:left;margin-top: 3px;" />企业客户管理</font>',
								scope : this,
								handler : function() {
									this.overlay.hide();
									this.lpl();
								}
							}, {
								docked : 'bottom',
								baseCls : "btn",
								xtype : 'button',
								style : "background:'#ffffff';border-bottom:1px solid #e3e3e3;",
								text : '<font style="line-height:38px;"><img src='
										+ __ctxPath
										+ '/htmobile/resources/images/jdsp_proj_per.jpg width="25" style="float:left;margin-top: 6px;" />个人客户管理</font>',
								scope : this,
								handler : function() {
									this.overlay.hide();
									this.lol();
								}
							}],
					scrollable:true
		    		
		    	})
		    	this.overlay.show();
		    	this.lol =function() {
					  mobileNavi.push(Ext.create('htmobile.customer.person.PersonList',{
					  	title:'个人客户管理'
					  }));
			    	}
				this.lpl =function() {
					  mobileNavi.push(Ext.create('htmobile.customer.enterprise.EnterpriseList',{
					  	title:'企业客户管理'
					  }));
			    	}
	},
	borrowMoney: function(){
	    /*if(curUserInfo==null){
		        mobileNavi.push(
							Ext.create('hrmobile.public.myhome.login',{
						        	})
						    	);
	  
	     }else{*/
	     	if(mobileNavi.getActiveItem().$className !="htmobile.customer.person.myHome"){
	     	mobileNavi.reset();
		      mobileNavi.push(
		      Ext.create('htmobile.customer.person.myHome',{
			        	})
			    	);
		     }
			},
	 myaccount: function(){
	 	
	    if(curUserInfo==null){
		        mobileNavi.push(
							Ext.create('hrmobile.public.myhome.login',{
						        	})
						    	);
	  
	     }else{
	    	 
	    	 
	    	 
	     	if(mobileNavi.getActiveItem().$className !="hrmobile.public.myhome.main"){
	     	mobileNavi.reset();
		      mobileNavi.push(
		      Ext.create('hrmobile.public.myhome.main',{
			        	})
			    	);
			 mobileNavi.getNavigationBar().getBackButton().hide();
		     }
	     }
			}/*,
	toptgg: function(){
		if(mobileNavi.getActiveItem().$className !="hrmobile.public.myhome.notice"){
		        mobileNavi.reset();
				mobileNavi.push(
			      Ext.create('hrmobile.public.myhome.notice',{
		        	})
		    	);
	            mobileNavi.getNavigationBar().getBackButton().hide();
		}
	}*/
});