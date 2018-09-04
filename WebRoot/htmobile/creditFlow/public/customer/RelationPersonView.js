
//creditorList.js
Ext.define('htmobile.creditFlow.public.customer.RelationPersonView', {
    extend: 'mobile.List',
    id:'RelationPersonView',
    name: 'RelationPersonView',
    constructor: function (_cfg) {
    	this.personId=_cfg.personId;
    	this.businessType=_cfg.businessType;
    	this.flag=_cfg.flag;
    	this.readOnly=_cfg.readOnly;
		var url = __ctxPath +'/creditFlow/customer/person/getListBypersonIdPerson.do';
		var button=Ext.create('Ext.Panel',{
			docked:'top',
		      items : [{
						cls : 'submit-button',
						style:'margin:auto;margin-top:20px;',
						xtype : 'button',
						width:'50%',
						handler : this.addbtn,
						scope : this,
						text : '添加'
					}]
		});
    	Ext.apply(_cfg,{
    		title:'联系人信息',
    		modeltype:"RelationPersonViewList",
    		style:'background-color:white;',
    		flex:1,
    		width:"100%",
		    height:"100%",
    		items:[this.readOnly?{}:button,{
    			xtype:'panel',
    			docked:'top',
    			items:[{
    				html:
    				`	
    					<div class="list-column">
    					<span>姓名</span>
    					<span>关系</span>
    					<span>手机</span>
    					</div>
    				`
    			}]
    		}],
	        plugins:[ {
	            type:"listopt",
	            itemFilter:function(list,action, record){
	            	if(action=="See"){
	            		
	            	   return true;
	            	}
	               if(action=="Edit"){
	               	  //加权限
		               	if(list.readOnly){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	   
	            	}
	            	if(action=="Remove"){
	            		 //加权限
	            	    if(list.readOnly){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	}
	            },
	            items:[{
	                action:"See",
	                cls:"write",
	                color:"grey",
	                text:"查看"
	            },  {
	                action:"Edit",
	                cls:"write",
	                color:"yellow",
	                text:"编辑"
	            }, {
	                action:"Remove",
	                cls:"trash",
	                color:"red",	
	                text:"删除"
	            } ]
	        } ],
    		fields:[{
						name : 'id',
						type : 'int'
					}, 'relationName','relationShip','relationPhone', 
			           'relationCellPhone','relationShipValue','personId','relationProfession','relationAddress',
			           'relationCompanyPhone','relationJobCompany','relationJobAddress',
			           'flag','birthday','cardtype','cardnumber',
			           'cardTypeValue'],
    	        url : url,
    	        params:{
    	         flag : this.flag,
				 personId:this.personId
    	        },
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
 		        itemTpl:
 		        `
 		        	 	<div class="list-column-content">
	    					<span>{relationName}</span>
	    					<span>{relationShipValue}</span>
	    					<span>{relationCellPhone}</span>
    					</div>
 		        `
    	});
    	this.callParent([_cfg]);
	 },
    initialize:function() {
        this.callParent(arguments);
        this.on({
            listoptiontap:"optTap",
            scope:this
        });
    },
    optTap:function(action, list, record) {
    	var obj=this;
       var data=record.data;
        if (action == "Edit") {
        	
        	var relationid =data.id;
			var businessType = data.businessType;
			var personId = data.personId;
			var flag = data.flag;
			Ext.Ajax.request({
					url : __ctxPath +'/creditFlow/customer/person/seeRelationPerson.do',
					params : {
						id : relationid,
						businessType :  businessType
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.customer.detail.RelationPersonAdd',{
			             	relationid : relationid,
				          	data:data,
				          	personId:personId,
				          	flag:flag,
				          	title:'编辑联系人信息'
			        	})
			    	);
				}
			});
			
        	
        	
        } else if (action == "Remove") {
        	var relationid =data.id;
        	Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/person/deleteRelationByIdPerson.do',
				params:{
					id:relationid
				},
			   	success : function(response) {
			        var responseText = Ext.util.JSON.decode(response.responseText);
			        
				   if (responseText.success==true) {
				   	  Ext.Msg.alert("", "删除成功！");
				      obj.store.loadPage(1);
				   	
				   }else{
			
						Ext.Msg.alert("", "删除失败");
						return;
					
				}
			}
		});  	
        }else if (action == "See") {
        	   var obj=this;
		       var relationid=  data.id;
		       var data=this.data;
		       var businessType = this.businessType;
				Ext.Ajax.request({
						url : __ctxPath +'/creditFlow/customer/person/seeRelationPerson.do',
						params : {
							id : relationid,
							businessType : businessType
						},
						success : function(response) {
						var result = Ext.util.JSON.decode(response.responseText);
						data = result.data;
						mobileNavi.push(
					         Ext.create('htmobile.creditFlow.public.customer.detail.RelationPersonAdd',{
							     data:data,
							     addBtreadOnly:true,
							     title:'查看联系人信息'
					        })
					    );
					}
				});
		}
    },addbtn:function(){
	 	personId=this.personId;
	 	flag=this.flag;
	   	mobileNavi.push(Ext.create('htmobile.creditFlow.public.customer.detail.RelationPersonAdd',{
				             title:'添加联系人',
					         personId: personId,
					         flag: flag,
	    	                 businessType:this.businessType
			        	})
			    	);
	 }
});
