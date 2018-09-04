/**
 * @author 
 * @createtime 
 * @class OurProcreditAssuretenetGuaranteeForm
 * @extends Ext.Window
 * @description OurProcreditAssuretenetGuaranteeForm表单
 * @company 智维软件
 */
OurProcreditAssuretenetProductForm = Ext.extend(Ext.Window, {
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
		OurProcreditAssuretenetProductForm.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.extPanel,
			modal : true,
			height:400,
			width : 500,
			border : false,
			buttonAlign : 'center',
			maximizable : true,
			title : '贷款必备条件详细信息',
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
		var  url=__ctxPath+"/assuretenet/listTree1OurProcreditAssuretenet.do";
		if(this.isDelete){
			 url=__ctxPath+"/assuretenet/listExitTreeOurProcreditAssuretenet.do";
		}
		
		var dic_TreeLoader = new Ext.tree.TreeLoader({
				baseParams :{
				        	 productId : this.productId,
				        	 businessType:this.businessType,
				        	 operationType:this.operationType
				        	},
				dataUrl : url
			})
		var dic_Root = new Ext.tree.AsyncTreeNode({
			draggable:true,
			text : "贷款必备条件"
		});
		this.extPanel =new Ext.tree.TreePanel({  
							id:"assuretTreePanel",  
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
			     var url=__ctxPath + '/assuretenet/updateAssurentOurProcreditAssuretenet.do'
			    if(this.isDelete==true){
			    	url=__ctxPath + '/assuretenet/deleteAssurentOurProcreditAssuretenet.do'
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
						'productId' : panel.productId
					}
				})
			
			}
});