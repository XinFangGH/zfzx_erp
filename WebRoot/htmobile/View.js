
/**
 * 手机登录跳转 by cjj
 */
Ext.define('mobile.View', {
	extend : 'Ext.NavigationView',
	constructor : function(config) {
		var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
		config = config || {};
		var me = this;
		var data;
		//获取项目名称
		var getName=function(taskName){
							taskName=taskName.substr(0,taskName.indexOf("-"));
							return taskName;
				        };
		//获取项目编号
        var getNumber=function(taskName){
        	taskName=taskName.substr(taskName.indexOf("-")+1,taskName.length);
        	return taskName;
        };
		Ext.apply(config, {
			defaultBackButtonText : '',
			items : [],
			navigationBar : {
				ui : 'dark',
				docked : 'top',
				items : [/*{
					xtype : 'button',
					id : 'zhuye',
					text : '主页',
					align : 'right',
					handler : function() {
					 mobileNavi.reset();
					}},!isApp?{hidden:true}:{
					xtype : 'button',
					id : 'logout',
					text : '注销',
					align : 'right',
					handler : function() {
						Ext.Msg.show({
									message : '是否注销',
									width : 300,
									buttons : [{
												text : '确定',
												itemId : '1'
											}, {
												text : '取消',
												itemId : '0'
											}],
									fn : function(itemId) {
										if (itemId != '0') {
											mobileNavi.setMasked(true);
											localStorage.setItem("userName",'');
											window.location.reload();
										}
									}
								});
					}
				}*/]
			},
			listeners : {
				show:function(){
				var data = me.data;
				mobileNavi.push(Ext.create('mobile.Main', {
															username : config.username,
															userId : config.userId,
															reobj:reobj
														}));
				//刷新数据	by shang							
				(function(){
					//请求数据
					Ext.Ajax.request({
						    url: __ctxPath+ "/flow/userActivityAllTask.do?processName="+ processNameFlowKey,
					        params: {
					        	custId : curUserInfo.id
					        },
					        method: 'POST',
					    	success : function(response) {
					    		var obj = Ext.util.JSON.decode(response.responseText);
					    		if(obj.success==true){
					        		var data = obj.result;
					        		var result="";
					        		 if(data!=null && data.length>1){
								    	for(var i=0;i<data.length;i++){
										    	var flow = "";
										    	if(data[i].executionId.indexOf("PersonLoanNormalFlow")==0){
										    		flow="个贷标准流程";
										    	}
										    	reobj = data[i];
							        		 	var name =  data[i].taskName.substring(data[i].taskName.length-16,data[i].taskName.length);
								    		if(i==10){
												break;
								    		}else{
								    			result += 
								    			'<div class="taskListMain">'+
													'<div class="taskListOne">'+
														'<span class="taskName" onClick="itemsingletapll(reobj)">' +
														getName(data[i].taskName)+
														'<span class="icon"></span></span>'+
														'<span class="taskNumber">'+
														getNumber(data[i].taskName)+
														'</span>'+
													'</div>'+
													'<div class="taskListTwo">'+
														'<span class="text">当前节点：</span>'+
														'<span class="activityName">'+
														data[i].activityName+
														'</span>'+
													'</div>'+
												'</div>'}
												}
										//设置数据
										$("strong.txt-red").text(obj.totalCounts>=10?10:obj.totalCounts);
										$("div.news-main").html(result);
										$("section.backlog").on("click", function(){
											$("#_TaskContent").toggle();
										});
										} else {
										isShow_share = 0;
									}}
					    	}
					    })
				})()
					
				},
				back : function(view, eOpts) {
					if(!typeof(_list)==="undefined"&&_list!=null)_list=null;//待办查询，全局变量清除 by shang
				}
			}
		});

		this.callParent([config]);
	}

});
