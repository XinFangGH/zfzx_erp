FormFieldDefWindow=Ext.extend(Ext.Window,{
	toolItems:[
	{type:'textfield',text:'文本框'},
	{type:'radio',text:'单选框'},
	{type:'checkbox',text:'复选框'},
	{type:'textarea',text:'多行文本框'},
	{type:'datefield',text:'日期'},
	{type:'datetimefield',text:'日期时间'}],
	
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		
		FormFieldDefWindow.superclass.constructor.call(this,{
			height:500,
			width:800,
			maximizable:true,
			title:'流程任务表单定义-' + this.activityName,
			layout:'border',
			items:[this.canvasPanel,this.toolPanel],
			buttonAlign:'center',
			buttons:this.buttons
		});
		
		//初始化拖拽功能
		
	},
	//初始化组件
	initUIComponents:function(){
		//画布设计
		this.canvasPanel=new Ext.Panel({
			bodyStyle:'padding:4px 4px 4px 4px',
			id:'canvasPanel',
			region:'center',
			id:'canvas',
			layout:'column',
			autoScroll:true
		});	
		
		this.canvasPanel.on('render', this.onCanvasRender, this);
		
		//工具栏
		this.toolPanel=new Ext.Panel({
			title:'表单组件',
			id:'toolPanel',
			region:'east',
			split : true,
			collapsible : true,
			width:130,
			defaults : {
					style : 'margin-bottom: 5px',
					draggable : {
						insertProxy : false,
						onDrag : function(e) {
							var pel = this.proxy.getEl();
							this.x = pel.getLeft(true);
							this.y = pel.getTop(true);
							var s = this.panel.getEl().shadow;
							if (s) {
								s.realign(this.x, this.y, pel.getWidth(), pel.getHeight());
							}
						},
						endDrag : function(e) {
							this.panel.setPosition(this.x, this.y);
						}
					}
			},
			items:this.createToolItems()
		});
		
		this.buttons=[
		{
			text:'保存',
			iconCls:'btn-save',
			handler:function(){}
		},{
			text:'取消',
			iconCls:'btn-cancel',
			scope:this,
			handler:function(){
				this.close();
			}
		}
		]
		
	},
	
	onCanvasRender:function(cmp) {
				var sourcePanelDropTarget = new Ext.dd.DropTarget(cmp.body.dom,{
							notifyDrop : function(ddSource, e, data) {
								//console.dir(ddSource);
								var id=ddSource.panel.getId();
//								if(id.indexOf('panel_')!=-1){
									var type=id.split('_')[1];
									
//									var panel=new Ext.Panel({
//										layout:'form',
//										border:false,
//										style:'padding-left:2px 2px 2px 2px',
//										items:,
//										draggable:true
//									});
								    
									var datetime=type;
									var format=null;
									if(type=='datefield'){
										format='Y-m-d';
									}else if(type=='datetimefield'){
										format='Y-m-d H:i:s'
									}

									var panel={
											columnWidth:.5,
											border:false,
											xtype:'panel',
											//disabled:true,
											layout:'form',
											draggable:{
												 insertProxy: false
											},
											items:{
												xtype:type,
												border:false,
												labelStyle:'text-align:center;padding-top:4px',
												fieldLabel:'未命名',
												format:format
											}
									};
									
									cmp.add(panel);
									cmp.doLayout();
//								}
								return true;
							}
				});
	},

	createToolItems:function(){
		
		var toolPanels=[];
		
		for(var i=0;i<this.toolItems.length;i++){
			var item=this.toolItems[i];
			toolPanels.push(new Ext.Panel({
				height:20,
				bodyStyle : 'padding:2px 2px 2px 2px;border-bottom: solid 1px blue;',
				border:false,
				id:'panel_' + item.type,
				html:item.text
			}));
		}
		
		return toolPanels;
	}    
	
});