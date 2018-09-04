
/**
 * 手机功能菜单
 * by cjj
 */
var username;
var userId;
Ext.define('mobile.Main', {
    extend: 'Ext.Panel',
    name:'main',
    id:'main',
    constructor:function(config){
		username = config.username;
		userId=config.userId;
		var reobj = config.reobj;
		this.result1 = config.result1;
		config = config || {};
		
		var selfItem = [];
		var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
		var rowPanel = null;
		//start权限
		var menuSize = menus.length;
	    for(var idx=0;idx<menuSize;idx++){
			var item = menus[idx];
			if(!Ext.isEmpty(item.isShowStr)){
				var arr=item.isShowStr.split(",")
				var len=arr.length;
				var isShow=false;
				for(var n=0;n<len;n++){
				     var arritem=arr[n];
				     if(isGranted(arritem)){
				         isShow=true;
				         break;
				     }
				}
			   if(!isShow){
				    menus.splice(idx, 1);
				   idx--;
				   menuSize--;
			   	
			   }
			  
			}
		}
		//end权限
		var menuSize = menus.length;
		if(menus.length!=0 && menus.length%3!=0){
			while(menuSize%3!=0){
				menuSize++;
			}
		}

		for(var idx=0;idx<menuSize;idx++){
			
			if(idx==0||idx%3==0){
				rowPanel = Ext.create('Ext.Panel', {
					docked:'top',
					scrollDock:'top',
					layout: {
						type: 'hbox',
						align: 'middle'
					}
				});
			}
			
			var itemPanel = Ext.create('Ext.Panel', {
				layout: {
					type: 'vbox',
					align: 'middle'
				},
				style: {
					'padding-top':'0px',
					'padding-bottom':'15px',
					'border-bottom':'1px solid #eeeeee',
					'border-right':'1px solid #eeeeee',
					'background-color':"#fff"
				},
				flex:1,
			
		    	height:110
			});

			if(idx<menus.length){
				var item = menus[idx];
	
				itemPanel.add({
					xtype:'button',
					id:item.id,
					name:item.view,
					title:item.title,
					width:50,
					height:55,
					style:'border:0;background-color:#fff',
					cls:item.cls,
					pressedCls:'',
					notice:item.notice,
					mask:item.mask,
					scope:this,
					listeners:{
						tap:function(){
							if(this.config.name=="htmobile.customer.DanganConfig"){
								customerNew(this.config);
							}else if(this.config.name=="htmobile.approve.XiangmuConfig"){
								projectNew(this.config);
							}else{
							
							mobileNavi.push(
								Ext.create(this.config.name,{
									username:username,
									userId:userId,
									title:this.config.title
								})
							);}
						},
						initialize:function(obj,b){
							//初始化方法
						}
					}
				});
				
				itemPanel.add({
					xtype:'label',
					style: {
					    'text-align': 'center',
					    'font-size' : '9pt'
					},
					html:item.title
				});
			}

			rowPanel.add(itemPanel);
			
			if(idx==0||idx%3==0){
				selfItem.push(rowPanel);
			}
		}
		
		// 底部导航
		selfItem.push(bottomBar);
		
		itemsingletapll=function(obj, index, target, record){
		var taskId = obj.taskId;
		var activityName = obj.activityName;
		var defId = obj.defId;
		var formpanel = Ext.create('Ext.form.Panel');
		var taskName = obj.taskName;
	    var objtasklist=this;
	    var flag = false; 
	    formpanel.submit({
		    url: __ctxPath+'/htmobile/getTask.do',
		    params: {
				taskId:taskId,
				activityName:activityName,
				defId:defId
		    },
		    method: 'POST',
		    async : false,
		    success: function(form,action,response){
		        var result = Ext.util.JSON.decode(response);
		    	mobileNavi.push(
		        	Ext.create('htmobile.myTask.MyTaskForm',{
		        		taskId:taskId,
		        		defId:defId,
		        		preTaskName:result.preTaskName,
		        		isSignTask:result.isSignTask,
		        		trans:result.trans,
		        		taskName:taskName,
		        		vars:result.vars,
		        		newjs:result.newjs,
		        		activityName:activityName,
		        		comments:result.comments,
		        		callback:function(){
		        			
		        			obj.store.load();
		        		}
		        	})
		    	);
		    },
		    failure: function(form,action,response){
				var obj = Ext.util.JSON.decode(response);
				Ext.Msg.alert('', obj.msg);
	        }
		});
				
    }
		Ext.apply(config,{
			title:"升升投",
			name:'menu',
			items:selfItem
    	});
    	
    	//待办任务内容
		var panelTasklab = Ext.create('Ext.Panel', {
				    width:'100%',
				    height:'46px',
				    fullscreen: false,
				    scrollable:{
				    	direction: 'vertical'
				    },
					html:'<section class="backlog" ' +
							'style="margin-top:3px;' +
							'">'+
				    '<header class="db_header clearfix">'+
				      '<i class="ico-clock fl">'+
				        '<img width="100%" src='+__ctxPath+'/htmobile/resources/images/jdsp_db.png alt="">'+
				      '</i>'+
				      '<span class="fl">待办任务</span>'+
				      '<span class="fr">'+
				        '<strong class="txt-red"></strong>项' +
				        '<span class="icon"></span></span>'+
				     ' </span>'+
				  '</section>'
				});
		
    	//待办内容
		var panel1 = Ext.create('Ext.Panel', {
					id:'_TaskContent',
					fullscreen: true,
				    width:'100%',
				    height:'100%',
				    scrollable:{
				    	direction: 'vertical'
				    },
					html:'<div class="news-main" style="display: block; height:auto" id="news1"></div>'
				});
				
		selfItem.push(panelTasklab);
		selfItem.push(panel1);
		this.callParent([config]);
    }
});


function customerNew(configdata){
		    	this.overlay =Ext.Viewport.add({
		    		xtype:'panel',
		    		modal:true,
		    		hideOnMaskTap:true,
		    		centered:true,
		    		margin:'-70% -23% 0 0',
		    		width:'146px',
		    		height:'80px',
		    		color:'#000',
		    		styleHtmlContent:false,
					items:[{docked:'bottom',
	                    	baseCls:"btn",
		                	xtype: 'button',
		                	style:'background:"#ffffff";color: "#646464";',
		                    text:'<font style="line-height:36px;"><img src='+__ctxPath+'/htmobile/resources/images/jdsp_proj_con.jpg width="25" style="float:left;    margin-top: 3px;" />新建企业用户</font>',
		                    scope:this,
		                    handler: function () {
					           this.overlay.hide();
					           this.Danganlpll11();
                           }
	                    },{docked:'bottom',
	                    	baseCls:"btn",
		                	xtype: 'button',
		                	style:"background:'#ffffff';border-bottom:1px solid #e3e3e3;",
		                    text:'<font style="line-height:38px;"><img src='+__ctxPath+'/htmobile/resources/images/jdsp_proj_per.jpg width="25" style="float:left;margin-top: 6px;" />新建个人用户</font>',
		                    scope:this,
		                    handler: function () {
					           this.overlay.hide();
					           this.Danganloll11();
                           }
	                    }],
					scrollable:true
		    		
		    	})
		    	this.overlay.show();
		    	this.Danganloll11 =function() {
				  mobileNavi.push(Ext.create('htmobile.InformationCollection.person.newPersoBbaseInfo',{
				  		title:'新建个人用户'
				  }));
		    	}
		    	this.Danganlpll11 =function() {
				  mobileNavi.push(Ext.create('htmobile.InformationCollection.enterprise.newEnterpriseBbaseInfo',{
				  	title:'新建企业用户'
				  }));
		    	}
}
function projectNew(configdata){
		    	this.overlay =Ext.Viewport.add({
		    		xtype:'panel',
		    		modal:true,
		    		hideOnMaskTap:true,
		    		centered:true,
		    		margin:'-70% 0 0 61%',
		    		width:'146px',
		    		height:'80px',
		    		color:'#000',
		    		styleHtmlContent:false,
					items:[{docked:'bottom',
	                    	baseCls:"btn",
		                	xtype: 'button',
		                	style:"background:'#ffffff'",
		                    text:'<font style="line-height:36px;"><img src='+__ctxPath+'/htmobile/resources/images/jdsp_proj_con.jpg width="25" style="float:left;    margin-top: 3px;" />企业项目申请</font>',
		                    scope:this,
		                    handler: function () {
					           this.overlay.hide();
					           this.Xiangmulpll11();
                           }
	                    },{docked:'bottom',
	                    	baseCls:"btn",
		                	xtype: 'button',
		                	style:"background:'#ffffff';border-bottom:1px solid #e3e3e3;",
		                    text:'<font style="line-height:38px;"><img src='+__ctxPath+'/htmobile/resources/images/jdsp_proj_per.jpg width="25" style="float:left;margin-top: 6px;" />个人项目申请</font>',
		                    scope:this,
		                    handler: function () {
					           this.overlay.hide();
					           this.Xiangmuloll11();
                           }
	                    }],
					scrollable:true
		    		
		    	})
		    	 this.overlay.show();
		    	 this.Xiangmuloll11 =function() {
					  	mobileNavi.push(Ext.create('htmobile.approve.person.ApplyPersonloan', {
					  	title:'个人项目申请',
						operationType : "SmallLoan_PersonalCreditLoanBusiness",
						history : "personHouse",
						only : true
				}));
			  	}
			  	this.Xiangmulpll11 =function() {
					  	mobileNavi.push(Ext.create('htmobile.approve.person.ApplyPersonloan', {
					  	title:'企业项目申请',
						operationType : "SmallLoan_SmallLoanBusiness",
						history : "businessVehicleMortgage",
						only : true
				}));
			  	}
		    	
}
