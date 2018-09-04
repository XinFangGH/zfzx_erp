/**
 * 修改密码
 */
Ext.define('htmobile.public.resetPassword', {
    extend: 'Ext.form.Panel',
    name: 'resetPassword',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	config = config || {};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"修改密码",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [bottomBar,{
						xtype : 'fieldset',
						defaults : {
							xtype : 'textfield',
							labelWidth : document.body.clientWidth / 3,
							clearIcon : true
						},
						items : [{
									label : '用户名',
									value:curUserInfo.fullname,
									readOnly:true
								}, {
									label : '原密码',
									name : "person.oldPw",
									placeHolder:'请输入原密码'
								}, {
									label : '新密码',
									name : "person.newPw",
									placeHolder:'请输入新密码'
								}, {
									label : '确认新密码',
									name : "person.password",
									placeHolder:'请输入确认新密码'
								}, {
									xtype : 'button',
									name : 'submit',
									text : '保存',
									cls : 'submit-button',
									handler : this.formSubmit
								}
						]
			}]
    	});

    	 this.callParent([config]);
    },
   formSubmit:function(){
   	    Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/person/seeInfoPerson.do',
					params:{
					    id:this.data.id
					},
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data;
						    mobileNavi.push(
			             Ext.create('htmobile.InformationCollection.person.editPersoBbaseInfo',{
				          data:data
			        	})
		    	);
				}
			});
   
    }
});
