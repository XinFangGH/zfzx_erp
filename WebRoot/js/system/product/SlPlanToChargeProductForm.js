/**
 * @author 
 * @createtime 
 * @class SlActualToChargeProductForm
 * @extends Ext.Window
 * @description SlActualToChargeProductForm表单
 * @company 智维软件
 */
SlPlanToChargeProductForm = Ext.extend(Ext.Window, {
	gridPanel:null,
	productId:null,
	constructor : function(_cfg) {
		if (typeof(_cfg.gridPanel) != "undefined") {
			this.gridPanel = _cfg.gridPanel;
		}
		if (typeof(_cfg.productId) != "undefined") {
			this.productId = _cfg.productId;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlPlanToChargeProductForm.superclass.constructor.call(this, {
			layout : 'fit',
			title : '费用清单详细',
			items : this.extPanel,
			modal : true,
			height:400,
			width : 500,
			border : false,
			buttonAlign : 'center',
			maximizable : true,
			buttons : [{
						text : this.isDelete?'隐藏':'确定',
						iconCls : 'btn-ok',
						scope : this,
						handler : this.save
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : this.cancel
					}]
		});
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		// 材料清单树Panel
		var  url=__ctxPath+"/creditFlow/finance/listTree1SlPlansToCharge.do";
		if(this.isDelete){
			 url=__ctxPath+"/creditFlow/finance/listExitTreeSlPlansToCharge.do";
		}
		
		var dic_TreeLoader = new Ext.tree.TreeLoader({
				baseParams :{
				        	 productId : this.productId,
				        	 businessType:this.businessType
				        	},
				dataUrl : url
			})
		var dic_Root = new Ext.tree.AsyncTreeNode({
			draggable:true,
			text : "费用清单"
		});
		this.extPanel =new Ext.tree.TreePanel({  
							id:"planTreePanel",  
							frame : true,  
							animate : false,  
							checkModel: 'cascade',   
							onlyLeafCheckable: false,  
							rootVisible:true,  //根节点是否可见
							loader : dic_TreeLoader,  
							root : dic_Root,  
							enableDD : false,  
							border:false,  
							autoScroll : true,  
							containerScroll:false,  
							rootVisible:false,  
							baseAttrs:{uiProvider: Ext.ux.TreeCheckNodeUI }, //添加uiProvider属性    
							width:485,  
							height:215  
					    }); 
				 this.extPanel.on('checkchange', function(node, checked) {  
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
					   dic_Root.expand();	    
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
			     var url=__ctxPath + '/creditFlow/finance/updatePlanSlPlansToCharge.do'
			    if(this.isDelete==true){
			    	url=__ctxPath + '/creditFlow/finance/deletePlanSlPlansToCharge.do'
			    }
			    Ext.Ajax.request({
					url : url,
					method : 'POST',
					scope : this,
					success : function(response, request) {
						panel.operateObj.store.reload();
						panel.close();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
					},
					params : {
						'materialsIds' :result,
						'productId' : panel.productId
					}
				})
			
			}
});