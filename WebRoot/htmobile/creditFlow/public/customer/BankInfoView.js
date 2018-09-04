

Ext.define('htmobile.creditFlow.public.customer.BankInfoView', {
    extend: 'mobile.List',
    id:'BankInfoView',
    name: 'BankInfoView',

    constructor: function (config) {
		this.personId=config.personId;
		this.isInvest=config.isInvest;
		this.isEnterprise=config.isEnterprise;
		this.readOnly=config.readOnly;
    	config = config || {};
    	var button=this.readOnly?{}:Ext.create('Ext.Panel',{
			docked:'top',
		      items:[
		      {
		       style:"margin:15px 100px  ",
		       xtype:'button',
		       handler:this.addbtn,
		       scope:this,
		       text:'添加'
		       		
		      }
		      ]
			
		});
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >开户类型</span>",
    	                                                      "<span class='tablehead' >账户类型</span>",
    	                                                      "<span class='tablehead' >银行名称</span>")});
    	Ext.apply(config,{
    		flex:1,
    		title:"银行开户",
    		width:"100%",
		    height:"100%",
    		items:this.readOnly?[panel]:[button,panel],
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
	            items:[ {
	                action:"See",
	                cls:"write",
	                color:"grey",
	                text:"查看"
	            }, {
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
    		fields:[ {
					
				
					
					name : 'id'
				},{
					name : 'enterpriseid'
				}, {
					name : 'bankid'
				}, {
					name : 'bankname'
				}, {
					name : 'accountnum'
				}, {
					name : 'openType'
				},{
					name : 'accountType'
				},{
					name : 'iscredit'
				},{
					name : 'creditnum'
				},{
					name : 'creditpsw'
				},{
					name : 'remarks'
				},{
					name : 'isEnterprise'
				},{
					name : 'openCurrency'
				},{
					name : 'name'
				},{
				    name : 'outletsname'
				},{
					name : 'areaName'
				},{
					name : 'bankOutletsName'
				}],
    	      	url : __ctxPath + '/creditFlow/customer/common/queryListEnterpriseBank.do',
	    		root:'topics',
	    	    totalProperty: 'totalCounts',
	    	    modeltype:"bankInfoList",
	    	    params : {
						id : this.personId,
						  isEnterpriseStr:this.isEnterprise,
						  isInvest:this.isInvest
			},
		    itemTpl: "<span  class='tablelist'><tpl if='openType==0'>个人</tpl><tpl if='openType==1'>公司</tpl></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' ><tpl if='accountType==0'>个人储蓄户</tpl><tpl if='accountType==1'>基本户</tpl><tpl if='accountType==2'>一般户</tpl></span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{bankname}</span>"/* ,
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});

    	this.callParent([config]);

    }/*,
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("开户类型","账户类型","银行名称","网点名称","开户地区",
    	 "银行开户类别","是否是放款账户","开户名称","贷款卡卡号"); 
    	  var value = new Array(data.openType==0?"个人":"公司", data.accountType==0?"个人储蓄户":(data.accountType==1?"基本户":"一般户"),data.bankname,data.bankOutletsName,data.areaName,
    	  data.openCurrency==0?"本币开户":"外币开户",data.iscredit==0?'是':'否',data.name,data.accountnum);  
          getListDetail(label,value);
	}*/,
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
        	var enterpriseBankid =data.id;
			var businessType = data.businessType;
			var personId = data.personId;
			var flag = data.flag;
			Ext.Ajax.request({
					url : __ctxPath +'/creditFlow/customer/common/findEnterpriseBank.do',
					params : {
						id : enterpriseBankid,
						businessType :  businessType
					},
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			        Ext.create('htmobile.creditFlow.public.customer.detail.BankInfoAdd',
			        	{
			              enterpriseBankid : enterpriseBankid,
				          data:data,
				          personId:personId,
				          flag:flag
			        	})
			    	);
				}
			});
			
        	
        	
        } else if (action == "Remove") {
        	var enterpriseBankid =data.id;
        	Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/common/deleteRsEnterpriseBank.do',
				params:{
					id:enterpriseBankid
				},
			   	success : function(response) {
			        var responseText = Ext.util.JSON.decode(response.responseText);
			        
				   if (responseText.success==true) {
				   	  Ext.Msg.alert("", "删除成 ");
				      obj.store.loadPage(1);
				   	
				   }else{
			
						Ext.Msg.alert("", "删除失败");
						return;
					
				}
			}
		});  	
        }else if (action == "See") {
        	var obj=this;
       var enterpriseBankid=  data.id;
       var data=this.data;
       var businessType = this.businessType;
		Ext.Ajax.request({
					url : __ctxPath +'/creditFlow/customer/common/findEnterpriseBank.do',
				params : {
					id : enterpriseBankid,
					businessType : businessType
				},
				   success : function(response) {
				var result = Ext.util.JSON.decode(response.responseText);
				data = result.data;
				mobileNavi.push(
			         Ext.create('htmobile.creditFlow.public.customer.detail.BankInfoAdd',
			         	{
			         
				     data:data,
				     addBtreadOnly:true
			        })
			    );
			}
		});
		}
    },
	 addbtn:function(){
	 personId=this.personId;
	 isInvest=this.isInvest;
	 isEnterprise=this.isEnterprise;
	   mobileNavi.push(
			             Ext.create('htmobile.creditFlow.public.customer.detail.BankInfoAdd',{
				         personId : personId,
				         isInvest : isInvest,
				         isEnterprise : isEnterprise,
    	                 businessType:this.businessType
			        	})
			    	);
	 
	 }
});
