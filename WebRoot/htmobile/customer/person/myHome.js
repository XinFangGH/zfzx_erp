/**
 * 个人中心
 */

Ext.define('htmobile.customer.person.myHome', {
    extend: 'Ext.form.Panel',
    name: 'myHome',
    constructor: function (config) {
    	var arr;//业务统计数据
    	Ext.Ajax.request({
    			async:false,
				url : __ctxPath+'/highchart/busenessStatisticsHighchart.do',
				params : {
					appuserId : curUserInfo.userId
				},
				success : function(response) {
					var data=Ext.decode(response.responseText)
					arr = Ext.decode(data.result);
				}
		});
    	config = config || {};
    	Ext.apply(this,config);
    	var personData={};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"个人中心",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
	    			xtype: 'fieldset',
	    			defaults:{
	    				 labelWidth:document.body.clientWidth/3,
	    				 clearIcon : false
	    			},
	    			items:[{
	    			       xtype:'hiddenfield',
	    			       name: 'AppUser.cardtype'
	    			    },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'AppUser.fullname',
				            label: '用户名',
				            readOnly:this.readOnly,
				            value:curUserInfo.fullname
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'AppUser.depName',
				            label: '所属门店',
				            readOnly:this.readOnly,
				            value:curUserInfo.depName
				        },{
				            xtype: 'textfield',
				            hidden:true,
				            labelWidth:'27%',
				            name: 'person.name',
				            label: '所属部门',
				            readOnly:this.readOnly
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'AppUser.roleNames',
				            label: '用户角色',
				            readOnly:this.readOnly,
				            value:curUserInfo.roleNames
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'AppUser.mobile',
				            label: '手机号',
				            readOnly:this.readOnly,
				            value:curUserInfo.mobile
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'AppUser.email',
				            label: '邮箱',
				            readOnly:this.readOnly,
				            value:curUserInfo.email
				        },{	
				           html:'<div style="border-bottom: 1px solid #dddddd;min-height: 2.5em;line-height: 35px;" onclick="javascript:PerformanceRank()">' +
				           		'<span class="fl txt-blue txt-f16">业务统计</span>'+
        						'<span class="fr txt-blue">查看图表展示&gt;&gt;</span></div>'
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'person.selfemail',
				            label: '累计放款金额',
				            readOnly:true,
				            value:(arr[0]!=null?formatCurrency(arr[0]):formatCurrency(0))+"元"
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'person.selfemail',
				            label: '累计客户总数',
				            readOnly:true,
				            value:(arr[1]!=null?arr[1]:0)+"个"
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'person.selfemail',
				            label: '当月放款金额',
				            readOnly:true,
				            value:(arr[2]!=null?formatCurrency(arr[2]):formatCurrency(0))+"元"
				        },{
				            xtype: 'textfield',
				            labelWidth:'27%',
				            name: 'person.selfemail',
				            label: '当月新增客户',
				            readOnly:true,
				            value:(arr[3]!=null?arr[3]:0)+"个"
				        },{
				            html:
				            '<div class="centrality_bottom">' +
					            '<span onclick="edituserData();">编辑资料</span>' +
					            '<span onclick="forgetPassword1();">修改密码</span>' +
					            '<span onclick="javascript:exitappself();">退出登录</span>' +
				            '</div>'
				        }
	        ]
	        }]
	        
	        //加载业务统计数据
    });

   	this.callParent([config]);
    	
	PerformanceRank=function(){
    	 mobileNavi.push(Ext.create('htmobile.public.showTable',{
    	 	readOnly:false
    	 }));
    },
   	//编辑资料
    edituserData=function(){
    	 mobileNavi.push(Ext.create('htmobile.public.edituserData',{
    	 	readOnly:false
    	 }));
    //修改密码 
    },forgetPassword1=function(){
    	 mobileNavi.push(Ext.create('htmobile.public.resetPassword',{
    	 	readOnly:true
    	 }));
    },exitappself=function(){
		Ext.Msg.show({
						message : '<div class="MsgTitle">确定退出登录？</div>',
						width : 180,
						buttons : [{
									text : '取消',
									itemId : '0',
									width:'50%',
									style:'background-color:white;color:black;'
								},{
									text : '确定',
									itemId : '1',
									width:'50%',
									style:'color:white;'
								}],
						fn : function(itemId) {
							if (itemId == '1') {
								navigator.app.exitApp();
							}
						}
					});    	
    }
    },
    formSubmit:function(){
    	var name=this.parent.getCmpByName("person.name").getValue(); 
		  if(Ext.isEmpty(name)){
		    Ext.Msg.alert('','名字不能为空');
			return;
		  }
    	 var cellphone=this.parent.getCmpByName("person.cellphone").getValue(); 
    	  if(Ext.isEmpty(cellphone)){
			 Ext.Msg.alert('', "<font color='#fff'>手机号不能为空</font>");
			  return;
			}
		 if(!isMobile(cellphone)){
		   Ext.Msg.alert('', "<font color='#fff'>手机号格式不正确</font>");
		   return;
		 }
		  var cardNumber=this.parent.getCmpByName("person.cardnumber").getValue(); 
    	if(validateIdCard(cardNumber)==1){
			Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对');
			return;
		}else if(validateIdCard(cardNumber)==2){
			Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对');
			return;
			
		}else if(validateIdCard(cardNumber)==3){
			Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对');
			return;
		}
		
		 var sex=this.parent.getCmpByName("person.sex").getValue(); 
		  if(Ext.isEmpty(sex)){
		    Ext.Msg.alert('','性别不能为空');
			return;
		  }
		 
//		  var marry=this.parent.getCmpByName("person.marry").getValue(); 
//		   if(Ext.isEmpty(marry)){
//		    Ext.Msg.alert('','婚姻状况不能为空');
//			return;
//		  }
		 
		 var shopId=this.parent.getCmpByName("person.shopId").getValue(); 
        if(Ext.isEmpty(shopId)){
		    Ext.Msg.alert('','分公司不能为空');
			return;
		  }
		 
		 var loginForm = this.up('formpanel');
    	var boj=loginForm.getCmpByName("person.shopId1");
       	loginForm.submit({
			    url: __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
		        params: {
		        },
		        method: 'POST',
		        success: function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		if(obj.exsit==false){
		        		 Ext.Msg.alert('','身份证号码已经存在');
		        			
		        		}else{
		        			mobileNavi.pop();
			              //   mobileNavi.push(Ext.create('htmobile.InformationCollection.person.uploadMaterial',{id:obj.id,mark:'cs_person.',resouce:'newperson'}));
		        		}
		        	}else{
		        		  Ext.Msg.alert('','添加失败');
		        		
		        	}
		        },
		        failure: function(form,action,response){
					var obj = Ext.util.JSON.decode(response);
					Ext.Msg.alert('', obj.msg);
		        }
			});
    }
    

});
