/**
 * @author 
 * @createtime 
 * @class SlProcreditMaterialsForm
 * @extends Ext.Window
 * @description SlProcreditMaterials表单
 * @company 智维软件
 */
SlProcreditMaterialsEnterpriseForm = Ext.extend(Ext.Window, {
	        projId:null,
	        operateObj:null,
	        extPanel:null,
	        isShow:false,
	        businessType:null,
	        setShow:false,
			constructor : function(_cfg) {
			    if(typeof(_cfg.projectId)!="undefined")
				{
				      this.projId=_cfg.projectId;
				}
				if(typeof(_cfg.businessType)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				if(typeof(_cfg.operateObj)!="undefined")
				{
				      this.operateObj=_cfg.operateObj;
				}
				if(typeof(_cfg.show)!="undefined")
				{
				      this.isShow=_cfg.show;
				}
				if(this.isShow){
				       this.setShow=false;  
				}
				else {
				   this.setShow=true;  
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				SlProcreditMaterialsEnterpriseForm.superclass.constructor.call(this, {
							layout : 'fit',
							modal : true,
							height : 400,
							items : this.extPanel,
							width : 500,
							border : false,
							maximizable : true,
							title : '选择材料',
							buttonAlign : 'center',
							buttons : [
										{
											text : this.isDelete?'隐藏':'确定',
											iconCls : 'btn-ok',
											scope : this,
											handler : this.save
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},
			initUIComponents : function() {
				       var root = new Ext.tree.AsyncTreeNode({
							draggable:true,
							text:'材料'
					   });   
					    var  url=__ctxPath + '/materials/listTree1SlProcreditMaterials.do';
					   if(this.isDelete){
					   	url=__ctxPath + '/materials/listExitTreeSlProcreditMaterials.do';
					   }
				        var loader = new Ext.tree.TreeLoader({
				        	baseParams :{
				        	     projId : this.projId,
				        	     businessType:this.businessType,
				        	     operationType:this.operationType,
							     show :this.isShow
				        	},
							dataUrl : url
						});  
		                this.extPanel =new Ext.tree.TreePanel({  
							id:"treePanel",  
							frame : true,  
							animate : false,  
							checkModel: 'cascade',   
							onlyLeafCheckable: false,  
							rootVisible:true,  //根节点是否可见
							loader : loader,  
							root : root,  
							enableDD : false,  
							border:false,  
							autoScroll : true,  
							containerScroll:false,  
							rootVisible:true,  
							baseAttrs:{uiProvider: Ext.ux.TreeCheckNodeUI }, //添加uiProvider属性    
							width:485,  
							height:215  
					    }); 
					    this.extPanel.on('checkchange', function(node, checked) {  
								/**
								 * 
								 node.expand();  
								node.attributes.checked = checked;  
								node.eachChild(function(child) {  
								    child.ui.toggleCheck(checked);  
								    child.attributes.checked = checked;  
								    child.fireEvent('checkchange', child, checked);  
								});*/
					    	    if(""==node.childNodes){
					    	    	  if(checked){
					    	    	  	   if(!node.parentNode.attributes.checked){
					    	    	  	       node.parentNode.ui.toggleCheck(checked);  
					    	    	  	       node.parentNode.attributes.checked;
					    	    	  	   }
					    	    	  }
					    	    	  else{ 
					    	    	  	      var isSelect=false;
					    	    	  	      node.parentNode.eachChild(function(child){
					    	    	  	             if(child.attributes.checked)
					    	    	  	             {
					    	    	  	                 isSelect=true;
					    	    	  	                 return false;
					    	    	  	             }
					    	    	  	      });
					    	    	  	      if(!isSelect){
					    	    	  	      	   if(node.parentNode.attributes.checked){
					    	    	  	      	         node.parentNode.ui.toggleCheck(checked);  
					    	    	  	                 node.parentNode.attributes.checked;
					    	    	  	      	   }
					    	    	  	      	 
					    	    	  	      }
					    	    	  }
					    	    }
					    	    else{
					    	         if(!checked){
												node.attributes.checked = checked;  
												node.eachChild(function(child) {  
												    child.ui.toggleCheck(checked);  
												    child.attributes.checked = checked;  
												});
					    	         }
					    	    }
							}, this.extPanel); 
					   root.expand();
			},
			
			reset : function() {
				this.formPanel.getForm().reset();
			},
			cancel : function() {
				this.close();
			},
			save : function() {
			   var panel =this;
				var result="";
			    var checkeds = this.extPanel.getChecked();
			    if(checkeds.length<=0)
			    {
			        this.close();
			        return false;
			    }
			    for (var i = 0; i < checkeds.length; i++) {
			    	    
			    	   if(checkeds[i].leaf){
			    	            
			    	   	    result = result + checkeds[i].id + ",";
			    	   }
			    }
			    result=result.substring(0,result.length-1)
			     var url=__ctxPath + '/materials/updateMaterialsSlProcreditMaterials.do'
			    if(this.isDelete==true){
			    	url=__ctxPath + '/materials/deleteMaterialsSlProcreditMaterials.do'
			    }
			    Ext.Ajax.request({
					url : url,
					method : 'POST',
					scope : this,
					success : function(response, request) {
						this.operateObj.store.reload();
						this.close();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
					},
					params : {
						'materialsIds' :result,
						'show' : panel.setShow,
						"businessType":panel.businessType,
						'operationType':panel.operationType,
						'projId' : panel.projId
					}
				})
			
			}
		});