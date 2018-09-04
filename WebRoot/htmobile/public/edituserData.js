/**
 * 个人中心-编辑资料
 */

Ext.define('htmobile.public.edituserData', {
    extend: 'Ext.form.Panel',
    name: 'edituserData',

    constructor: function (config) {
    	//底部菜单
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	config = config || {};
   		Ext.apply(this,config);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"编辑个人资料",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [bottomBar,{
		                xtype: 'fieldset',
		                defaults:{
		                xtype: 'textfield',
	    				 labelWidth:document.body.clientWidth/3,
	    				 clearIcon : true
	    			},items: [{
				            xtype: 'textfield',
				            labelWidth:'25%',
				            name: 'appUser.fullname',
				            label: '姓名',
				            readOnly:true,
				            value:curUserInfo.fullname
				        },{
				            xtype: 'hiddenfield',
				            name: 'appUser.userId',
				            value:curUserInfo.userId
				        },{
				            xtype: 'numberfield',
				            labelWidth:'25%',
				            name: 'appUser.mobile',
				            label: '手机号',
				            readOnly:this.readOnly,
				            value:curUserInfo.mobile
				        },{
				            xtype: 'textfield',
				            labelWidth:'25%',
				            name: 'appUser.email',
				            label: '邮箱',
				            readOnly:this.readOnly,
				            value:curUserInfo.email
				        },{
				            xtype: 'button',
				            style:'margin-top: 10px;border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;',
				            name: 'submit',
				            text:'保存',
				            cls : 'submit-button',
				            scope:this,
				            handler:this.formSubmit
				        }
		          ]}]
    	});
    	 this.callParent([config]);
    },formSubmit:function(){
				var mobile = this.getCmpByName('appUser.mobile').getValue();
				var email = this.getCmpByName('appUser.email').getValue();
				if(mobile == '' || mobile == ' '){
					Ext.Msg.alert('操作提示', '手机号码不能为空！', Ext.emptyFn);
					return;
				}
				if(email == '' || email == ' '){
					Ext.Msg.alert('操作提示', '邮箱地址不能为空！', Ext.emptyFn);
					return;
				}
				this.submit({
					url : __ctxPath+ '/system/editPartInfoAppUser.do',
					method : 'POST',
					success : function(form,action,response) {
						mobileNavi.pop();
						Ext.Msg.alert('操作提示', '保存成功', Ext.emptyFn);
					},failure : function(form,action,response) {
						Ext.Msg.alert('操作提示', '保存失败', Ext.emptyFn);
					}
				});
   }
});
