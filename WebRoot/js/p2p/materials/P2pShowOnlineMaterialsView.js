//P2pShowOnlineMaterialsView.js

/**
 * @author
 * @class P2pShowOnlineMaterialsView
 * @extends Ext.form.FieldSet
 * @description [P2pShowOnlineMaterialsView]管理 贷款材料上传
 * @company 互融软件
 * @createtime:
 */

 var xg = Ext.grid;
 var tpl=new Ext.XTemplate(
            '<p><b>材料类型:</b>{materialsType}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>材料名称:</b>{materialsName}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>线下材料份数:</b>{datumNumsOfLine}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>上传材料份数:</b>{datumNums}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
	        '<p><b>是否已收到:</b>&nbsp;&nbsp;&nbsp;&nbsp;<tpl if="isReceive==true">已收到</tpl><tpl if="isReceive==false">未收到</tpl></p>',
	        '<p><b>合规说明:</b>{ruleExplain}</p>',
	        '<p><b>备注:</b>{remark}</p>'  
          )
 var expander = new xg.RowExpander({
 	    enableCaching : false ,
        tpl : tpl
 });
var receiveRender = function(v){
    if(v){
       return "是"
    }
    else {
        return "否"
     }
}
var Employee = Ext.data.Record.create
([
             {name: 'materialsType', mapping: 'parentName'},
             {name: 'materialsName', mapping: 'materialsName'},
             {name: 'datumNums', mapping: 'datumNums'},
             {name: 'datumNumsOfLine', mapping: 'datumNumsOfLine'},
             {name: 'remark', mapping: 'remark'},
              {name: 'webMaterialsId', mapping: 'webMaterialsId'},
              {name: 'projId', mapping: 'projId'},
             {name: 'proMaterialsId', mapping: 'proMaterialsId'},
             {name: 'isReceive', mapping: 'isReceive'},
             {name: 'ruleExplain', mapping: 'ruleExplain'},
             {name: 'isArchiveConfirm', mapping: 'isArchiveConfirm'},
             //add by gao
             {name: 'confirmTime', mapping: 'confirmTime'},
             {name: 'isPigeonhole', mapping: 'isPigeonhole'}
]);
var reader = new Ext.data.JsonReader({totalProperty : 3,root : 'result'},Employee);
P2pShowOnlineMaterialsView = Ext.extend(Ext.Panel, {
	        projId:null,
	        gridPanel:null,
	        isHidden_materials : true,
	        isHiddenArchive : true,
	        businessType:null,
	        isHiddenEdit:true,
	        isarchives:true,
	        isAutoHeight : true,
	        isProjectInfoEdit : false,
	        //add by gao
	        showConfirmInfo:false,
	        editConfirmInfo:false,//this.editConfirmInfo?false:!this.showConfirmInfo,//可编辑则  不会隐藏
	        //end
			constructor : function(_cfg) {
				
				if(typeof(_cfg.projectId)!="undefined")
				{
				      this.projId=_cfg.projectId;
				}
				if(typeof(_cfg.businessType)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				if (typeof(_cfg.isHiddenEdit) != "undefined") {
				    this.isHiddenEdit = _cfg.isHiddenEdit;
			    }
			    if (typeof(_cfg.isHidden_materials) != "undefined") {
				  this.isHidden_materials = _cfg.isHidden_materials;
			    }
			    if (typeof(_cfg.isHiddenArchive) != "undefined") {
				  this.isHiddenArchive = _cfg.isHiddenArchive;
			    }
			     if (typeof(_cfg.isarchives) != "undefined") {
				  this.isarchives = _cfg.isarchives;
			    }
			    if (typeof(_cfg.isAutoHeight) != "undefined") {
					this.isAutoHeight = _cfg.isAutoHeight;
				}
				if (typeof(_isProjectInfoEdit)) {
					this.isProjectInfoEdit = _cfg.isProjectInfoEdit;
				}
				if (typeof(_cfg.showConfirmInfo)) {
					this.showConfirmInfo = _cfg.showConfirmInfo;
				}
				if (typeof(_cfg.editConfirmInfo)) {
					this.editConfirmInfo = _cfg.editConfirmInfo;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				
				P2pShowOnlineMaterialsView.superclass.constructor.call(this, {
						    layout:'anchor',
		  					 anchor : '100%',
							 modal : true,
							items : [this.gridPanel]
						});
			},
			initUIComponents : function() {
				 var projectId = this.projId;
				 var businessType = this.businessType;
				
				
				//add by gao  融资租赁特殊显示字段
				this.confirmDatefield=new Ext.form.DateField({
					format : 'Y-m-d',					
					readOnly:!this.editConfirmInfo,
					allowBlank : false
				})
				
				//end  add by gao
				var obj=this.gridPanel;
				var isHidden=this.isHidden_materials;
				var fenRenderer = function(v)
				{
	 
					if(v&&v>0){
						return v + "份" ;
					}else{
						
						if(isHidden){
						
						    return '<font color=red>未填写</font>';
						}
						else{
						   return '<font color=red>请单击编辑</font>';
						}
						
					}
				};
				var remarkRender=function(v)
				{
				    if(v&&v!=""){
				    	  return v;
				    }
				    else {
				    	if(isHidden){
				    	    return '<font color=red>未填写</font>';
				    	}
				    	else{
				    	    return '<font color=red>请单击编辑</font>';
				    	}
				    }
				};
				var jsArr = [__ctxPath + '/js/p2p/materials/P2pShowProcreditMaterialsForm.js',
				             __ctxPath + '/js/p2p/materials/addNewP2PShowMaterialsForm.js'
				             ];
				$ImportSimpleJs(jsArr, null);
				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '添加',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								},'-',{
									iconCls : 'btn-delete',
									text : '删除',
									xtype : 'button',
									scope : this,
									handler : this.updateRs
								},'-',{
									iconCls : 'btn-delete',
									text : '新增',
									xtype : 'button',
									scope : this,
									handler : this.addnewProduct
								}]
				});
				this.gridPanel = new xg.EditorGridPanel({
					  id:'materials_grid_panel',
				        store:  new Ext.data.GroupingStore({
	                        	proxy : new Ext.data.HttpProxy({url :__ctxPath + '/p2pMaterials/listPlWebShowMaterials.do'}) ,
	                        	reader: reader,
	                        	groupField:'materialsType',
	                        	baseParams:{
								    projId : this.projId,
								    businessType:this.businessType
								}
				        }),
//				        plugins : [checkColumn],
				        clicksToEdit :1,
				        tbar : isHidden? null :this.topbar,
				        cm: new xg.ColumnModel([
				            //expander,
				            new Ext.grid.RowNumberer( {header : '序号',	width : 35}),
				           {header : "材料类型",width : 110,sortable : true,dataIndex : 'materialsType',hidden : true},
				            {header : 'webMaterialsId',dataIndex : 'webMaterialsId',hidden : true},
				            {header : 'proMaterialsId',dataIndex : 'proMaterialsId',hidden : true},
				            {header : 'projId',dataIndex : 'projId',hidden : true},
				            
				            {header: "材料名称",dataIndex: 'materialsName'},
				            {header: "上传材料份数",fixed: true,width:93,dataIndex: 'datumNums',renderer : function(v){
				                     	if(v&&v>0){
												return v + "份" ;
										}else{
										
										         return "0份";
										}
				            }},
				            {header: "上传或下载",fixed: true,width:86,dataIndex: 'uploadOrDown',renderer:function(){
				                if(isHidden){
				                     return "<img src='"+__ctxPath+"/images/download-start.png'/>";
				                }else{
				                      return "<img src='"+__ctxPath+"/images/upload-start.png'/>";
				                }
				            }},
				            {header: "预览",fixed: true,width:40,dataIndex: 'viewPic',renderer:this.imageView}
				        ]),
				        selModel : new Ext.grid.RowSelectionModel(),
				        view: new Ext.grid.GroupingView({
                                 forceFit:true,
                                 groupTextTpl: '{text}'
                        }),
                        stripeRows : true,
				        width: '80%',
				        autoHeight : this.isAutoHeight,
				        height : 395,
				        border : false,
				        plugins: expander,
				        animCollapse: false,
				        iconCls:'icon-grid',
                        renderTo: document.body,
                        scope:this,
                        listeners : {
                		   'cellclick' : function(grid,rowIndex,columnIndex,e){
                		   	
                		   	         var record = grid.getStore().getAt(rowIndex);   //Get the Record
                                     var fieldName = grid.getColumnModel().getDataIndex(columnIndex); //Get field name
                                     var data = grid.getStore().getAt(rowIndex);
                                     //data.get('id')
                                  
									if("uploadOrDown"==fieldName){
										
										 var webId=grid.getStore().getAt(rowIndex).get("webMaterialsId");
										 var markId=grid.getStore().getAt(rowIndex).get("proMaterialsId");
										 var projId=grid.getStore().getAt(rowIndex).get("projId");
										 var talbeName="pl_Web_Show_Materials|sl_smallloan_project";
										 
										 var mark=talbeName+"."+webId+markId+projId;
										 
										new P2pFileUpload({
											 talbeName: talbeName,
											 mark:mark,
											 webMaterialsId:webId,
											 projId:projId,
											 businessType:businessType,
											 grid:grid
										}).show();
										}
									
									if("viewPic"==fieldName){   
										 var webId=grid.getStore().getAt(rowIndex).get("webMaterialsId");
										 var markId=grid.getStore().getAt(rowIndex).get("proMaterialsId");
										 var projId=grid.getStore().getAt(rowIndex).get("projId");
										 var talbeName="pl_Web_Show_Materials|sl_smallloan_project";
										 var mark=talbeName+"."+webId+markId+projId;
//										 });                  
									    P2pMaterialsDownLoad(mark,this.isHiddenEdit,"typeisfile",webId,projId,grid.ownerCt.businessType);
									}
								 } 
                        }
    				});
    		        this.gridPanel.getStore().load();
			},
			createRs : function() {
				var userId=this.userId;
				var productId=this.productId;
				if(userId!=undefined &&   typeof(userId) != undefined && ""!=userId){
					 new P2pShowOnlineMaterialsForm({
			           operateObj:this.gridPanel,
		          	   productId:productId,
			           userId:userId,
			           projectId : this.projId,
			           businessType:this.businessType,
			           operationType:this.operationType
		            }).show();
				}
			},
			updateRs : function(){
				var materialIds = "";
			    var panel=this.gridPanel
				var rows = this.gridPanel.getSelectionModel().getSelections();
			   if (rows.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			   } else{
			     	for(var i=0;i<rows.length;i++){
					materialIds = materialIds+rows[i].get('webMaterialsId');
					if(i!=rows.length-1){
						materialIds = materialIds+','
					}
				}
			      Ext.Msg.confirm('信息确认', '确认要删除该条数据吗', function(btn) {
	   					 if (btn == 'yes') { 
	   					 	Ext.Ajax.request({
					        url : __ctxPath + "/p2pMaterials/deleteMaterialsPlWebShowMaterials.do",
					        method : 'POST',
					        scope : this,
					        success : function(response, request) {
						             panel.getStore().reload();
						               this.close();
					              },
					        failure : function(response) {
						             Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
					            },
					        params : {
						         'materialsIds':materialIds
					             }
					             })
			               }
			  })
			}
			},
			imageView : function(){
			         return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
			},
			
			/**
			 * 新增贷款材料
			 */
			addnewProduct:function(){
				new addNewP2PShowMaterialsForm({projectId : this.projId,operateObj:this.gridPanel,show:false,businessType:this.businessType,operationType:this.operationType}).show();
			}
});
