ProcessRunStart=Ext.extend(Ext.Panel,{
	constructor:function(config){
		//使用HTML表单模板
		this.useTemplate=false;
		//下一授予任务名
		this.assignTasks=new Array();
		//下一任务用户名
		this.assignUserIds=new Array();
		
		Ext.applyIf(this,config);
		this.buttonsArr=[
				{
					text:'提交并启动流程',
					iconCls : 'btn-ok',
					scope:this,
					handler:this.saveAndStart
				},'-',{
					xtype:'checkbox',
					boxLabel:'发送邮件',
					scope:this,
					handler:function(ck,checked){
						if(checked){
							this.sendMail=true;
						}else{
							this.sendMail=false;
						}
					}
				},{
					xtype:'checkbox',
					boxLabel:'发送短信',
					scope:this,
					handler:function(ck,checked){
						if(checked){
							this.sendMsg=true;
						}else{
							this.sendMsg=false;
						}
					}
				},'-',{
					text:'流程示意图',
					iconCls:'btn-flow-chart',
					scope:this,
					handler:this.showFlowImage
				}
		];
		//用户选择的Panel，为下一节点进行人员选择
		this.userJumpPanel=new Ext.form.FieldSet({
					border:false
//					title:'选择执行人',
//					collapsed: true,
//					autoHeight:true,
//					collapsed: false,
//					collapsible : true
		});
		//取得开始节点之后跳转路径
		this.jumpPanel=new Ext.Panel({
			autoHeight:true,
			layout:'form',
			border:false,
			items:[
				this.userJumpPanel
			]
		});

		//加载开始节点后的分支
		Ext.Ajax.request({
			url:__ctxPath+'/flow/startTransProcessActivity.do',
			params:{
				defId:this.defId
			},
			scope:this,
			success:function(resp,options){
				var object=Ext.decode(resp.responseText);
				var radioItems=[];
				for(var i=0;i<object.data.length;i++){
					radioItems.push({
						boxLabel:object.data[i].destination,
						name:'jumpPath_'+this.defId,
						inputValue:object.data[i].name,
						destType:object.data[i].destType,
						destName:object.data[i].destination,
						checked:i==0?true:false//缺省第一个选中
					});
				}
				this.jumpRadioGroup = new Ext.form.RadioGroup({
					listeners:{
						scope:this,
						'change':this.jumpRadioCheck
					},
					fieldLabel : '执行路径',
					items : radioItems
				});
				
                //以加载相应的人员
				this.jumpPanel.insert(0,this.jumpRadioGroup);
				this.jumpPanel.doLayout();
				this.jumpRadioCheck.call(this);
			}
		});
		
		//加载其对应的HTML或EXT表单
		this.formPanel=new Ext.form.FormPanel({
			//layout:'table',
			region:'center',
			border:false,
			bodyStyle:'padding:16px 4px 4px 20px',
			autoScroll:true,
			autoLoad:{
				url:__ctxPath+ "/flow/getProcessActivity.do?defId="+this.defId,
				nocache:true,
				scope:this,
				callback:this.convertHtml
			}
		});
		//加载另一表单
		
		ProcessRunStart.superclass.constructor.call(this,{
			title:'流程启动-' + this.flowName,
			iconCls:'btn-flow-start',
			autoScroll:true,
			tbar:new Ext.Toolbar({items:this.buttonsArr}),
			layout:'form',
			frame:true,
			id:'ProcessRunStart'+this.defId,
			layoutConfig: {
	                padding:'5',
	                pack:'center',
	                align:'middle'
	            },
	            defaults:{
	            	margins:'0 5 10 0',
	            	anchor : '98%'
	            },
			items:[{
	            items:[{
	            	//margins:'0 5 5 0',
					bodyStyle:'padding:16px 4px 0px 20px',
	                items:[{
	                xtype:'fieldset',
	                autoHeight:true,
					collapsed: false,
					collapsible : true,
					title:"执行操作",
					items:[this.jumpPanel]
	                }]
	                },
	            	this.formPanel	
	            ]}]
			
		});
	},
	
	/**
	 * 单选项按钮点击
	 * @param {} rd
	 * @param {} ckradio
	 */
	jumpRadioCheck:function(){
		
		var radio=this.jumpRadioGroup.getValue();
		
		this.getTaskUsers.call(this,radio.destName,radio.destType);
		
	},
	/**
	 * 取得下一节点对应的处理人员
	 * @param {} destName 目标节点的名称
	 * @param {} destType 目标节点的类型
	 */
	getTaskUsers:function(destName,destType){
		
		//下一节点为分支及fork节点
		if('decision'==destType || 'fork'==destType){
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.genForkDecUserAssign.call(this,destName);
		}else if(destType.indexOf('end')!=-1 ){//下一节点为结束节点，需要隐藏下一步的执行人	
			this.userJumpPanel.removeAll();
			this.userJumpPanel.hide();
		}else{//下一节点为普通任务节点
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.userJumpPanel.add(this.getSingleUserPanel.call(this,destName));
		}
		this.jumpPanel.doLayout();
	
	},
	/**
	 * 下一步仅有一个任务，即仅需要为一个任务赋予人员
	 * @return {}
	 */
	getSingleUserPanel:function(destName){
		
		this.flowAssignName=new Ext.form.TextArea({
								width:400,
								height:26,
								name:'flowAssignName'
							});
		var singleUserField=new Ext.form.CompositeField({
					xtype:'compositefield',
					fieldLabel:'执行人',
					anchor:'92%,92%',
					items:[
							this.flowAssignName,
							{
								xtype:'button',
								scope:this,
								text:'...',
								iconCls : 'btn-users',
								handler:function(){
									new UserDialog({
										scope:this,
										single:false,
										callback:function(uId,uname){
											this.flowAssignName.setValue(uname);
											this.assignTasks=[destName];
											this.assignUserIds=[uId];
										}
									}).show();
								}
							}
					]
		});
		
		
		Ext.Ajax.request({
			url:__ctxPath+'/flow/usersProcessActivity.do',
			scope:this,
			params:{
				defId:this.defId,
				activityName:destName
			},
			success:function(response,options){
				var result=Ext.decode(response.responseText);
				this.flowAssignName.setValue(result.userNames);
				this.assignTasks=[destName];
				this.assignUserIds=[result.userIds];
			}
		});
		
		return singleUserField;
	},
	
	//为汇集或分支节点产生自由跳转的人员选择
	genForkDecUserAssign:function(destName){
		Ext.Ajax.request({
			url:__ctxPath+'/flow/outerTransProcessActivity.do',
			params:{
				defId:this.defId,
				nodeName:destName/*,
				beJuxtaposedFlowNodeKeys : beJuxtaposedFlowNodeKeys,
				currentNodeKeyByNextTaskBeJuxtaposed : currentNodeKeyByNextTaskBeJuxtaposed*/
			},
			scope:this,
			success:function(resp,options){
				//outers数据格式为[{singalName,activityName,destType},...]
				//如：[["to 总经理审阅","总经理审阅","task","1,2","张三,李四"],["to 财务审核","财务审核","task","1,2","张三,李五"]]
				var outers=Ext.decode(resp.responseText);
				
				for(var i=0;i<outers.length;i++){
					this.userJumpPanel.add(this.genUserFieldSel.call(this,outers[i],i));
				}
				this.userJumpPanel.doLayout();
			}
		});
	},
	//产生用户选择
	genUserFieldSel:function(outers,idx){
		
		//目标节点名称
		var destName=outers[1];
		this.assignTasks[idx]=destName;
		this.assignUserIds[idx]=outers[3];
		
		var flowAssignUserName=new Ext.form.TextArea({allowBlank:false,width:400,height:40,value:outers[4]});
		var cmpField=new Ext.form.CompositeField({
			border:false,
			fieldLabel:destName,
			items:[
				flowAssignUserName,
				{
					xtype:'button',
					text:'...',
					iconCls : 'btn-users',
					scope:this,
					handler:function(){
						UserSelector.getView({
							scope:this,
							callback:function(uIds, uNames) {
								flowAssignUserName.setValue(uNames);
								//查找该数组中是否已经存在这个目标节点，若存在，则找到其坐标
								var index=this.assignTasks.length;
								for(var i=index-1;i>=0;i--){
									if(this.assignTasks[i]==destName){
										index=i;
										break;
									}
								}
								this.assignTasks[index]=destName;
								this.assignUserIds[index]=uIds;
							}
						}).show();
					}
				}
			]
		});
		
		return cmpField;
								
	},
	/**
	 * 取得下一任务及其对应的人员
	 */
	getFlowAssignId:function(){
			//返回其格式如下：领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
			var flowAssignId='';
			var destTasks='';
			var destUserIds='';
			for(var i=0;i<this.assignTasks.length;i++){
				if(i>0){
					destTasks+=':';
					destUserIds+=':';
				}
				destTasks+=this.assignTasks[i];
				destUserIds+=this.assignUserIds[i];
			}
			if(destTasks!=''){
				flowAssignId=destTasks+'|'+destUserIds;
			}
			return flowAssignId;
	},

	
	//转化Html
	convertHtml:function(){
		var formExt=document.getElementById('formExt'+this.defId);
		if(formExt!=null){
			//加上标识，表示是使用EXT模板进行
			this.useTemplate=true;
			var valExt=formExt.value;
			valExt=valExt.replace('Ext.form.FormPanel','Ext.Panel');
			this.formExtPanel=eval('new ('+valExt+')('+this.vmParams+');');
			this.formPanel.add(this.formExtPanel);
			this.formPanel.doLayout();
			return;
		}
		this.formPanel.doLayout();
        try{
	        var json=document.getElementById('rightsdef_'+this.defId);
	        if(json!=null){
	        	//加载JS回调函数
	        	var callback=function(){
	        	    var rightsJson=Ext.decode(json.value);
			        $converDetail.call(this,null,rightsJson);
	        	};
	        	//后加载文档的JS
	        	$ImportSimpleJs([
	        	   __ctxPath+'/js/core/ntkoffice/NtkOfficePanel.js',
        	     __ctxPath + '/js/selector/SealSelector.js',
	             __ctxPath + '/js/selector/PaintTemplateSelector.js'
	        	 
	        	 ],callback,this);
	            
	        }
        }catch(e){
			//alert(e);
		}
		
	},
	/**
	 * 保存并启动流程
	 */
	saveAndStart:function(){
		//表单是否合法，默认为合法
		var isValid=true;
		//加上对于表单的前置验证，允许在模板中加上自己的验证
		if(this.formExtPanel!=null&& this.formExtPanel.validate){
			isValid=this.formExtPanel.validate.call(this.formExtPanel,this);
		}else{
			isValid=$validForm.call(this);
		}
		if(!isValid){
			Ext.ux.Toast.msg('操作信息','请填写有效表单数据！');
			return;
		}
		
		var destName=this.jumpRadioGroup.getValue().destName;
		//设置flowAssignId,用于指定下一任务的执行人
		var flowAssignId=this.getFlowAssignId.call(this);
		
		var formPanel=this.formPanel;
		
		//加上流程参数配置
		var flowVars="";
		var defParams=document.getElementById('defParams'+this.defId);
		if(defParams){
			flowVars=defParams.value;
		}
		
		var baseParams={
					useTemplate:this.useTemplate,
					defId:this.defId,
					startFlow:true,
					destName:destName,
					sendMsg:this.sendMsg,
					sendMail:this.sendMail,
					flowAssignId:flowAssignId,
					flowVars:"{"+flowVars+"}"
				};
		if(this.detailGrids){//适用于多个GRID的
			var grids=this.detailGrids.keys;
			for(var j=0;j<grids.length;j++){
				var details=[];
				var detailPanel=this.detailGrids.get(grids[j]);
			    var store=detailPanel.getStore();
				for(var i=0;i<store.getCount();i++){
					var record=store.getAt(i);
					var d=HT.encode(record.data);
					details.push(d);
				}
				baseParams[grids[j]+'details']=Ext.encode(details);
			}
		}
		
		//modify by lyy start
		/**
		 * @author lyy
		 * @description
		 *  取得表单里面的OFFICE控件面板，保存文档，再把文档ID返回给表单字段
		 */
		var officePanel=this.officePanel;
		if(officePanel){
			var obj=null;
		   if(this.fileId!=''&&this.fileId!=undefined){
     	 	    obj=officePanel.saveDoc({docName:'ProcessDocument',fileId:this.fileId,doctype:'doc'});
     	   }else{
     	        obj=officePanel.saveDoc({docName:'ProcessDocument',doctype:'doc'});
     	   }
     	   if(obj&&obj.success){
				var fileId=obj.fileId;
				this.hiddenF.setValue(fileId);
		   }
		}
		//modify by lyy end
		
		var dom=formPanel.getForm().getEl().dom;
        //取得表单里面的子表单
        var forms=dom.getElementsByTagName('form');
        var dv=[];
        var detailsMap=new Ext.util.MixedCollection();
        for(var i=0;i<forms.length;i++){
        	 var belongName=forms[i].getAttribute('belongName');
        	 var pkName=forms[i].getAttribute('pkName');
        	 var pkValue=forms[i].getAttribute('pkValue');
             var baseParam2 = Ext.Ajax.serializeForm(forms[i]);
             var deParams = Ext.urlDecode(baseParam2);//取得了从表里面的数据
             //进行数据组装
             if(pkName&&pkValue){
                deParams[pkName]=pkValue;
             }
             var dd=HT.encode(deParams);
             
             var tt=detailsMap.get(belongName);
             if(!tt){
             	 var details=[];
             	 details.push(dd);
                 detailsMap.add(belongName,details);
             }else{
                 tt.push(dd);
             }
        }
        
        for(var i=0;i<detailsMap.keys.length;i++){
        	var keyName=detailsMap.keys[i];
            baseParams[keyName+'details']=Ext.encode(detailsMap.get(keyName));
        }
		//启动工作流
		if(formPanel.getForm().isValid()){
			formPanel.getForm().submit({
				url : __ctxPath + '/flow/saveProcessActivity.do',
				waitMsg : '正在提交流程表单信息...',
				scope:this,
				params:baseParams,
				success : function(fp, action) {
					if(this.formExtPanel!=null&&this.formExtPanel.afterSuccess){
						this.formExtPanel.afterSuccess.call(this.formExtPanel,action.response.responseText);
					}
					Ext.ux.Toast.msg('操作信息','成功保存信息！');
					if(officePanel){
					   officePanel.closeDoc();
					}
					AppUtil.removeTab(this.getId());
				}
			});
		}
	},
	reset:function(){
		this.formPanel.getForm().reset();
	},
	/**
	 * 显示流程图
	 */
	showFlowImage:function(){
		var win=new Ext.Window({
			autoScroll:true,
			iconCls:'btn-flow-chart',
			bodyStyle:'background-color:white',
			maximizable : true,
			title:'流程示意图',
			width:600,
			height:500,
			modal:true,
			html:'<img src="'+__ctxPath+ '/jbpmImage?defId='+this.defId+ '&rand=' + Math.random()+ '"/>'
		});
		win.show();
	}
});