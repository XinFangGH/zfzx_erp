/**
 * 分类管理
 * @class ProModalManager
 * @extends Ext.Panel
 */
PageSettingView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		PageSettingView.superclass.constructor.call(this,{
			id:'PageSettingView',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'表单配置管理',
			iconCls:"menu-flowManager",
			items:[
				this.leftPanel,
				this.formPanel
			]
		});
	},
	initUIComponents:function(){
		this.leftPanel = new Ext.Panel({
			title:'页面设置',
			region : 'west',
			layout : 'anchor',
			collapsible : true,
			split : true,
			width : 200,
			autoHeight:true,
			items : [

			{
				xtype : 'treePanelEditor',
				name : 'pageSetting',
				split : true,
				rootVisible : false,
				border : false,
				height : Ext.getBody().getViewSize().height-115,
				autoScroll : true,
				scope : this,
				url:__ctxPath+'/creditFlow/common/listBpPageSetting.do',
				onclick : function(node) {
				    this.selectedNode=node;
	             	var pageSetId=node.id;
	             	var formPanel=this.formPanel;
	             	if(formPanel!=null){
	             		formPanel.getCmpByName('parentPageName').setValue(node.attributes.parentText);
	             		formPanel.getCmpByName('pageName').setValue(node.text);
	             		formPanel.getCmpByName('pageSettingId').setValue(node.id);
	             		if(null!=node.attributes.pageContent){
	             			formPanel.getCmpByName('pageContent').setValue(node.attributes.pageContent);
	             		}
	             	}
				},
    contextMenuItems : [
    	{  
                 text : '新建分类',  
                 scope : this,  
                 iconCls:'btn-add',
                 handler : function(){
                 	var pageSetting=this.getCmpByName('pageSetting');
                 	var parentId=pageSetting.selectedNode.id;

                 	new PageSettingForm({
                 		parentId:parentId,
                 		callback:function(){
                 			pageSetting.root.reload();
                 		}
                 	}).show();
                 }  
             },{
             	text:'修改分类',
             	scope:this,
             	iconCls:'btn-edit',
             	handler:function(){
             		var pageSetting=this.getCmpByName('pageSetting');
                 	var pageSetId=pageSetting.selectedNode.id;
                 	
                 	new PageSettingForm({
                 		pageSetId:pageSetId,
                 		callback:function(){
                 			pageSetting.root.reload();
                 		}
                 	}).show();
             	}
             },{

                 	text:'删除分类',
                 	scope:this,
                 	iconCls:'btn-del',
                 	handler:function(){

                 		var pageSetting=this.getCmpByName('pageSetting');
                     	var pageSetId=pageSetting.selectedNode.id;
                     	Ext.Msg.confirm("提示!",'确定要删除吗？',
                		function(btn) {
                            if (btn == "yes") {
                             	Ext.Ajax.request({
					                   url:  __ctxPath + '/creditFlow/common/multiDelBpPageSetting.do',
					                   method : 'POST',
					                   params : {
												pageSetId : pageSetId
											},
					                  success : function(response,request) {
											
	                            			Ext.ux.Toast.msg('操作信息',"删除成功");
	                            			pageSetting.root.reload();
		                            	
				                      }
		                             });  
                			}
                     	});
                     	/*var globalTypeForm = new GlobalTypeForm({
                     		proTypeId:proTypeId,
                     		callback:function(){
                     			Ext.getCmp('dicType').root.reload();
                     		}
                     	});
                     	globalTypeForm.show();*/
             	
                    }
                 
             }]
			}]
		}

		);
		var leftPanel=this.leftPanel
		this.formPanel = new Ext.FormPanel({
			region : 'center',
			layout : 'column',
			//bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			monitorValid : true,
			frame : true,
			labelAlign : 'right',
			labelWidth : 60,
			items : [{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'hidden',
					name : 'pageSettingId'
				},{
					xtype : 'textfield',
					fieldLabel : '父级名称',
					name : 'parentPageName',
					anchor : '100%'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '子项名称',
					name : 'pageName',
					anchor : '100%'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
				     xtype : 'fckeditor',
					height : 450,
					style : "margin-top:4px",
					name : "pageContent",
					anchor : "100%",
					//allowBlank : false,
					fieldLabel : "表单设计"
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				style : 'margin-left:500px',
				items : [{
					xtype : 'button',
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : function(){
						var pageSettingId=this.formPanel.getCmpByName('pageSettingId').getValue();
						if(pageSettingId==null || pageSettingId==''){
							Ext.Msg.alert('操作信息','请先选择左侧分类')
							return;
						}
						this.formPanel.getForm().submit({
						    clientValidation: false, 
							url : __ctxPath + '/creditFlow/common/updateBpPageSetting.do',
							method : 'post',
							waitMsg : '数据正在提交，请稍后...',
							success : function(fp, action) {
							
								Ext.ux.Toast.msg('操作信息', '保存信息成功!');
								leftPanel.getCmpByName('pageSetting').root.reload();
							},
							failure : function(fp, action) {
								Ext.MessageBox.show({
									title : '操作信息',
									msg : '信息保存出错，请联系管理员！',
									buttons : Ext.MessageBox.OK,
									icon : 'ext-mb-error'
								});
							}
						});
					}
				}]
			}]
		});
		
	}//end of initUIComponents
	
});