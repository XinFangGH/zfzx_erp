/**
 * 
 * @class FlowdesignerWindow
 * @extends Ext.Window
 */
FlowDesignerWindow=Ext.extend(Ext.Window,{
	constructor:function(config){
		Ext.applyIf(this,config);
		var html='<APPLET codebase="." id="flowDesigner" ARCHIVE="' + 
				__ctxPath+'/js/flowDesign/workflow.jar" '+
				'code="com.zhiwei.jbpm.designer.ProcessApplet.class"  width="100%" height="100%"></APPLET>';
		
//		var designer=document.createElement("APPLET");
//		designer.setAttribute('id','flowDesigner');
//		designer.setAttribute('codebase','.');
//		designer.setAttribute('ARCHIVE',__ctxPath+'/js/flowDesign/workflow.jar');
//		
//		designer.setAttribute('code','com.zhiwei.jbpm.designer.ProcessApplet.class');
//		designer.setAttribute('width','100%');
//		designer.setAttribute('height','100%');
		
		
		this.leftPanel=new Ext.Panel({
			region:'center',
			border:false,
			layout:'fit',
			html:html
		});
		Ext.useShims = true;
		this.leftPanel.on('afterrender',function(panel){
			//panel.body.appendChild(designer);
			this.deisigner=document.getElementById('flowDesigner');
		},this);
		
		this.formPanel=new Ext.FormPanel({
			width:260,
			layout:'anchor',
			region:'east',
			bodyStyle:'margin:6px 0px 0px 0px;background-color:#DFE8F6',
			defaults:{
				anchor:'100%'
			},
			items:[
					{
						name : 'proDefinition.defId',
						id : 'defId',
						xtype : 'hidden',
						value : this.defId == null ? '' : this.defId
					} ,{
						xtype : 'hidden',
						id : 'proTypeId',
						name : 'proDefinition.proTypeId'
					},{
						text:'流程分类',
						xtype:'label'
					},new TreeSelector('proTypeTreeSelector', __ctxPath + '/system/flowTreeGlobalType.do?catKey=FLOW', '流程分类','proTypeId',true),
					{
						text:'流程的名称',
						xtype:'label'
					},{
						name : 'proDefinition.name',
						xtype:'textfield',
						allowBlank:false,
						id : 'name'
					}, {
						text:'JBPM流程Key',
						xtype:'label'
					}, {
						name : 'proDefinition.processName',
						xtype:'textfield',
						allowBlank:false,
						id : 'processName'
					}, {
						text : '流程状态',
						xtype : 'label'
					}, {
						hiddenName : 'proDefinition.status',
						xtype : 'combo',
						allowBlank : false,
						editable : false,
						mode : 'local',
						triggerAction : 'all',
						store : [['0', '禁用'], ['1', '激活']],
						value : 1
					},{
						text:'描述',
						xtype:'label'
					},{
						xtype:'textarea',
						height:200,
						name : 'proDefinition.description',
						id : 'description'
					},{
						xtype:'hidden',
						height:200,
						name:'proDefinition.drawDefXml',
						id:'drawDefXml'
					}
			]
		});
		
		FlowDesignerWindow.superclass.constructor.call(this,{
			id:'flowDesignerWindow',
			title:'在线流程设计',
			iconCls:'btn-flow-design',
			defaults:{border:false},
			layout:'border',
			height:560,
			width:860,
			modal:true,
			maximizable:true,
			closeAction:'close',
			items:[
				this.leftPanel,
				this.formPanel
			],
			buttonAlign:'center',
			buttons:[{
					text:'保存',
					scope:this,
					iconCls:'btn-save',
					handler:this.save
				},
				{
					text:'发布',
					scope:this,
					iconCls:'btn-notice',
					handler:this.deploy
				},
				{
					text:'取消',
					scope:this,
					iconCls:'btn-cancel',
					handler:this.close
				}
			]
		});
		//设置流程定义的xml
		function setData(drawXml){
			try{
					var designer=document.applets.flowDesigner;
					designer.setData(drawXml);
			}catch(e){
				setTimeout(function(){
					setData(drawXml);
				},1000);
			}
		}
		
		if(this.defId !=null && this.defId !='undefined' && this.defId !=''){
				this.formPanel.loadData({
					url : __ctxPath + '/flow/getProDefinition.do?defId='+ this.defId,
					root : 'data',
					preName:'proDefinition',
					scope:this,
					success : function(response, option) {
						var result=Ext.util.JSON.decode(response.responseText);
						if(result){
							var proType=result.data.proType;
							if(proType!=null){
								this.formPanel.getCmpByName('proTypeTreeSelector').setValue(proType.typeName);
								this.formPanel.getCmpByName('proDefinition.proTypeId').setValue(proType.proTypeId);
							}
							var drawDefXml = result.data.drawDefXml;
							setData(drawDefXml);
						}
					}
				}
			);
		}
			
	},
	save:function(){
		this.onSave.call(this,false);	
	},//end save
	//发布流程
	deploy:function(){
		this.onSave.call(this,true);
	},
	/**
	 * 发布处理
	 * @param {} deploy 是否发布
	 */
	onSave:function(deploy){
		
		document.getElementById("drawDefXml").value=document.applets.flowDesigner.getData();
		
		if(this.formPanel.getForm().isValid()){
			this.formPanel.getForm().submit({
				url : __ctxPath + '/flow/defSaveProDefinition.do',
				method : 'POST',
				waitMsg : '正在提交数据...',
				params:{deploy:deploy},
				success : function(fp, action) {
					var proDefinitionGrid=Ext.getCmp("ProDefinitionGrid");
					if(proDefinitionGrid!=null){
						proDefinitionGrid.getStore().reload();
					}
					var proDefinitionGrid0=Ext.getCmp("ProDefinitionGrid0");
					if(proDefinitionGrid0!=null){
						proDefinitionGrid0.getStore().reload();
					}
					
					Ext.getCmp("flowDesignerWindow").close();
				},
				failure : function(fp, action) {
					var res = action.result.msg;
					if(res){
						Ext.MessageBox.show({title : '操作信息',msg : res, buttons : Ext.MessageBox.OK,icon : 'ext-mb-error'});	
					}else{
						Ext.MessageBox.show({title : '操作信息',msg : '信息保存出错，请联系管理员！', buttons : Ext.MessageBox.OK,icon : 'ext-mb-error'});
					}
					
					//Ext.getCmp("flowDesignerWindow").close();
				}
			});
		}
	}
	
	
});