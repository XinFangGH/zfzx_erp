

Ext.define('htmobile.customer.person.PersonMenu1', {
    extend: 'Ext.Panel',
    
    name: 'PersonMenu1',

    constructor: function (config) {
    	var bheight=75;
      this.data=config.data;
    	Ext.apply(config,{  
    	title:"客户详情",
    	fullscreen: true,
        layout: {
            type: 'vbox'
            
        },
        items: [{
            xtype: 'panel',
            margin: '1 0 0 0',
            defaults: {
                xtype: 'panel',
                layout: 'hbox'
          //      margin: '1 0 0 0',
          //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
            },
            items: [{
                defaults: {
                    xtype: 'button',
                    width:'50%' ,height:bheight
                //    margin: 10
            //        style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [
                	{
                   text:'身份证扫描件',
                    scope:this,
                   handler:function(){this.personSFZphoto(this.data);}
                },{
                   text:'家庭经济情况',
                    scope:this,
                  handler:function(){this.familyEconomyInfo(this.data);}
                }]
            },{
                defaults: {
                   xtype: 'button',
                    width:'33%' ,height:bheight
             //       margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
                 text:'家庭信息',
                  scope:this,
                  handler:function(){this.familyinfo(this.data);}
                },{
                 text:'期下公司',
                   scope:this,
                   handler:function(){this.tereunderList(this.data);}
                }, {
                   text:'征信记录',
                    scope:this,
                   handler:function(){this.reditregistriesList(this.data);}
                }]
            },{
                defaults: {
                    xtype: 'button',
                    width:'33%' ,height:bheight
              //      margin: 10
             //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
               //     margin: 10,
                   xtype:'button',text:'关系人',
                    scope:this,
                   handler:function(){this.relationPersonList(this.data);}
                }, {
            //        margin: 10,
                    xtype:'button',text:'配偶信息',
                     scope:this,
                   handler:function(){this.spouseForm(this.data);}
                },{
            //        margin: 10,
                   xtype:'button',text:'资信评估',
                    scope:this,
                   handler:function(){this.creditRatingManageList(this.data);}
                }]
            }, {
                defaults: {
                	xtype: 'button',
                    width:'33%' ,height:bheight
               //     margin: 10
           //         style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
            //        margin: 10,
                 text:'银行开户',
                     scope:this,
                   handler:function(){this.bankInfoList(this.data);}
                },{
              //      margin: 10,
                   text:'负面调查',
                    scope:this,
                   handler:function(){this.negativeList(this.data);}
                }, {
               //     margin: 10,
                   text:'教育情况',
                     scope:this,
                   handler:function(){this.educationInfoList(this.data);}
                }]
            }, {
                defaults: {
                    xtype: 'button',
                    width:'33%' ,height:bheight
              //      margin: 10
              //      style: 'border: 1px solid #cccccc; border-radius: 5px;'
                },
                items: [{
              //      margin: 10,
                   text:'工作经历',
                    scope:this,
                   handler:function(){this.workExperenceInfoList(this.data);}
                }, {
                //    margin: 10,
                    text:'社会活动',
                     scope:this,
                   handler:function(){this.publicActivityInfoList(this.data);}
                },{
               //     margin: 10,
                   text:'业务往来',
                    scope:this,
                   handler:function(){this.businessContactTab(this.data);}
                }]
            }]
        }]
 /*       modal: true,
            hideOnMaskTap: true,
            centered: true*/
            });

    	this.callParent([config]);
    	
    }
     ,
  personSFZphoto:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.personSFZphoto',{
			data:data
		        	})
		    	);
  }
    ,
  photo:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.photo',{
			data:data
		        	})
		    	);
  },
  familyinfo:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.FamilyInfo',{
			data:data
		        	})
		    	);
  },
  familyEconomyInfo:function(data){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.FamilyEconomyInfo',{
			data:data
		        	})
		    	);
  }
  ,
  tereunderList:function(data){/*
  	var panel=Ext.create('Ext.Panel',{docked:'top',laout:'hbox',items:[{html:'姓'},{html:'名'},{html:'名'}]})
  	    var touchTeam = Ext.create('Ext.List', {
            fullscreen: true,
            items:[panel],
            store: {
                fields: ['name', 'age'],
                data: [
                    {name: 'Greg',  age: 100},
                    {name: 'Brandon',   age: 21},
                    {name: 'Scott',   age: 21},
                    {name: 'Gary', age: 24},
                    {name: 'Fred', age: 24},
                    {name: 'Seth',   age: 26},
                    {name: 'Kevin',   age: 26},
                    {name: 'Israel',   age: 26},
                    {name: 'Mitch', age: 26}
                ]
            },

            itemTpl: '{name} is {age} years old'
        });
       mobileNavi.push(
		touchTeam
		    	);
  */
  
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.TereunderList',{
			data:data
		        	})
		    	);
  }
  ,
  reditregistriesList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.ReditregistriesList',{
			data:data
		        	})
		    	);
  }
  ,
  relationPersonList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.RelationPersonList',{
			data:data
		        	})
		    	);
  }
   ,
  spouseForm:function(data){
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
   ,
  bankInfoList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BankInfoList',{
			data:data,
			type:1
			
		        	})
		    	);
  }
   ,
  negativeList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.NegativeList',{
			data:data
		        	})
		    	);
  }
  
   ,
  educationInfoList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.EducationInfoList',{
			data:data
		        	})
		    	);
  }
   ,
  publicActivityInfoList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.PublicActivityInfoList',{
			data:data
		        	})
		    	);
  } ,
  workExperenceInfoList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.WorkExperenceInfoList',{
			data:data
		        	})
		    	);
  }
  ,
  businessContactTab:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.BusinessContactTab',{
			data:data,
			type:"person_customer"
		        	})
		    	);
  }
  ,
  creditRatingManageList:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.CreditRatingManageList',{
			data:data,
			type:"个人"
		        	})
		    	);
  }
    ,
  companyInfo:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.CompanyInfo',{
			data:data
		        	})
		    	);
  }
  ,
  teacherInfo:function(data){
   mobileNavi.push(
		Ext.create('htmobile.customer.person.menudetail.TeacherInfo',{
			data:data
		        	})
		    	);
  }
});
