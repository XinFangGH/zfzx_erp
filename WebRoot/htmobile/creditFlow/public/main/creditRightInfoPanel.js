/**
 * 债权信息
 */
Ext.define('htmobile.creditFlow.public.main.creditRightInfoPanel', {
    extend: 'Ext.form.Panel',
    name: 'creditRightInfoPanel',
    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"creditRightInfoPanel",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"债权信息",
    		fullscreen: true,
    		defaults: {
        		xtype: 'textfield'
    		},
		    items : [{
		            xtype: 'fieldset',
	                defaults:{
	                	xtype: 'textfield',
	                	labelAlign:"left"
		           	},items:[{
		           		xtype:'selectfield',
		           		clearIcon:false,
						name : 'ownBpFundProject.reciverType',
						label : '债权人类型',
						options : [{
										text : '个人',
										value : 'person'
									}, {
										text : '企业',
										value : 'enterprise'
									}],
						readOnly:this.readOnly
					}, {
						name : 'ownBpFundProject.receiverName',
						label : '债权人名称',
						readOnly:this.readOnly
					}, {
						name : 'ownBpFundProject.reciverId',
						label : '债权人P2P账号',
						readOnly:true
					}]},{
			            xtype: 'button',
			            name: 'ownBpFundProject.receiverP2PAccountNumber',
			            text:'保存',
			            cls : 'submit-button',
			            scope:this,
			            handler:this.formSubmit
			        }]
    	});
    	
   	this.callParent([_cfg]);
   	
    },formSubmit:function(){/*
    	this.submit({
    		scope:this,
    		url:'',
    		method:'POST',
    		params:{},
    		waitMsg:'数据提交中',
    		success:function(form,result,data){
    			
    		},
    		failure:function(){
    			
    		}
    	});
    */}
});
