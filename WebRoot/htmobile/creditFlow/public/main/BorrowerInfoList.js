
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.BorrowerInfoList', {
    extend: 'mobile.List',
    
    name: 'BorrowerInfoList',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    
    	var url = __ctxPath+ '/creditFlow/smallLoan/finance/listBorrowerInfo.do?projectId='+ this.projectId
	
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' style='width:100%;font-size:15px;'>共同借款人</span>"
    	                                                      /*"<span class='tablehead' >名称</span>",
    	                                                      "<span class='tablehead' >与客户的关系</span>"*/)});
    	Ext.apply(_cfg,{
    		modeltype:"BorrowerInfoList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"<span style='font-size:15px'>共同借款人信息</span>",
    		items:[panel],
    		fields:[
							{
								name : 'borrowerInfoId'
							},
							{
								name : 'type'
							},{
								name : 'customerId'
							},
							{
								name : 'cardNum'
							},
							{
								name : 'relation'
							},
							{
								name : 'address'
							},
							{
								name : 'telPhone'
							},{
								name : 'remarks'
							},{
								name : 'customerName'
							}

					],
    	        url :url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl:  "<span   style='width:100%;font-size:15px;'>{customerName}</span>&nbsp;&nbsp;" +
 		        "<br/><br/><span  style='width:100%;font-size:12px;' >类型:<tpl if='type==\"0\"'>企业</tpl><tpl if='type==\"1\"'>个人</tpl>&nbsp;" +
 		        "|&nbsp;证件号码:{cardNum}</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span></div>",
		        
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	
          if(data.type=="0"){
             Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
					params:{
					    id:record.data.customerId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data.enterprise;
						var persondata= obj.data.person;
					    mobileNavi.push(
			            Ext.create('htmobile.customer.enterprise.EnterpriseDetail',{
				        result:data,
				        persondata:persondata
			        	})
		    	);
				}
			});
          }else if(data.type=="1"){
          
           Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/person/seeInfoPerson.do',
					params:{
					    id:record.data.customerId
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data.vPersonDic;
					    mobileNavi.push(
			            Ext.create('htmobile.customer.person.PersonDetail',{
				        result:data
			        	})
		    	);
				}
			});
          
          }else{
	              var label = new Array("类型","名称","证件号码","与客户的关系","地址",
	    	 "联系电话", "备注"); 
	    	  var value = new Array(data.type=="0"?"企业":"个人",data.customerName,data.cardNum,data.relation,data.address,
	    	  data.telPhone,data.remarks);  
	    	    var xtype = new Array(null,null,null,null,null,null,"textareafield");
	          getListDetail(label,value,xtype);
          
          }
             
		    

}
});
