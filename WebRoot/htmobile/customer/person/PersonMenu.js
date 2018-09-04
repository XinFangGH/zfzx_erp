

Ext.define('htmobile.customer.person.PersonMenu', {
    extend: 'Ext.Panel',
    
    name: 'PersonMenu',

    constructor: function (config) {
    	var bheight=75;
      this.data=config.data;
      var data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    width:'100%',
		    height:'100%',
		    title:"客户详情",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">客户详细信息  '+
		           '<span style=\"float:right;\" onclick=\"javascript:personDetailmore();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">身份证扫描件 '+
		           '<span style=\"float:right;\" onclick=\"javascript:personSFZphoto();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">家庭信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:familyinfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span> <hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">家庭经济情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:familyEconomyInfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">配偶信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:spouseForm();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">关系人'+
		           '<span style=\"float:right;\" onclick=\"javascript:relationPersonList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">家庭信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:familyinfo();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">旗下公司'+
		           '<span style=\"float:right;\" onclick=\"javascript:tereunderList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">征信记录'+
		           '<span style=\"float:right;\" onclick=\"javascript:reditregistriesList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">资信评估'+
		           '<span style=\"float:right;\" onclick=\"javascript:creditRatingManageList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">关系人'+
		           '<span style=\"float:right;\" onclick=\"javascript:relationPersonList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">配偶信息'+
		           '<span style=\"float:right;\" onclick=\"javascript:spouseForm();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }*//*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">资信评估'+
		           '<span style=\"float:right;\" onclick=\"javascript:creditRatingManageList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">银行开户'+
		           '<span style=\"float:right;\" onclick=\"javascript:bankInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">负面调查'+
		           '<span style=\"float:right;\" onclick=\"javascript:negativeList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">教育情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:educationInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">工作经历'+
		           '<span style=\"float:right;\" onclick=\"javascript:workExperenceInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span><hr style=\"height:2px;\"> </hr></div>'+
		           '<div class=\"vmMain\">社会活动'+
		           '<span style=\"float:right;\" onclick=\"javascript:publicActivityInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }/*,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">教育情况'+
		           '<span style=\"float:right;\" onclick=\"javascript:educationInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">工作经历'+
		           '<span style=\"float:right;\" onclick=\"javascript:workExperenceInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    },{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">社会活动'+
		           '<span style=\"float:right;\" onclick=\"javascript:publicActivityInfoList();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		          }]
		    }*/,{
		    	xtype: 'fieldset',
		        items:[{
		           xtype:'panel',
		           html:'<div class=\"vmMain\">业务往来'+
		           '<span style=\"float:right;\" onclick=\"javascript:businessContactTab();\">>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'
		       }]
		    }
		 ]
    	});

    	this.callParent([config]);
  personDetailmore=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.PersonDetailmore',{
			data:data
		        	})
		    	);
  }  	  
personSFZphoto=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.personSFZphoto',{
			data:data
		        	})
		    	);
  }
    
  photo=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.photo',{
			data:data
		        	})
		    	);
  }
  familyinfo=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.FamilyInfo',{
			data:data
		        	})
		    	);
  }
  familyEconomyInfo=function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.FamilyEconomyInfo',{
			data:data
		        	})
		    	);
  }
 
  tereunderList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.TereunderList',{
			data:data
		        	})
		    	);
  }
  
  reditregistriesList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.ReditregistriesList',{
			data:data
		        	})
		    	);
  }
  
  relationPersonList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.RelationPersonList',{
			data:data
		        	})
		    	);
  }
   
  spouseForm=function(){
  	var personId=data.id;
	Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/person/getInfoSpouse.do?personId=' + personId,
				    success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					data = result.data;
				    mobileNavi.push(
			             Ext.create('htmobile.customer.person.menudetail.SpouseForm',{
				          data:data
			        	})
			    	);
				}
			});

  }
   
  bankInfoList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BankInfoList',{
			data:data,
			type:1
			
		        	})
		    	);
  }
   
  negativeList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.NegativeList',{
			data:data
		        	})
		    	);
  }
  
   
  educationInfoList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.EducationInfoList',{
			data:data
		        	})
		    	);
  }
   
  publicActivityInfoList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.PublicActivityInfoList',{
			data:data
		        	})
		    	);
  } 
  workExperenceInfoList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.WorkExperenceInfoList',{
			data:data
		        	})
		    	);
  }
  
  businessContactTab=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BusinessContactTab',{
			data:data,
			type:"person_customer"
		        	})
		    	);
  }
  
  creditRatingManageList=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.CreditRatingManageList',{
			data:data,
			type:"个人"
		        	})
		    	);
  }
    
  companyInfo=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.CompanyInfo',{
			data:data
		        	})
		    	);
  }
  
  teacherInfo=function(){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.TeacherInfo',{
			data:data
		        	})
		    	);
  }
  
  }
});

   
  


