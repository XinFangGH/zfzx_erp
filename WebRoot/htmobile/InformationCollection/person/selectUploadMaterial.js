

Ext.define('htmobile.InformationCollection.person.selectUploadMaterial', {
    extend: 'Ext.Panel',
    
    name: 'selectUploadMaterial',

    constructor: function (config) {
    	config = config || {};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"选择上传材料",
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [/*{
		                xtype: 'panel',
		                defaults:{
		                	xtype: 'textfield',
		                	labelWidth:"125px",
		                	labelAlign:"right"
		                	
		            },*/
		    {
	    			xtype: 'fieldset',
	    			defaults:{
	    			}, items:[
	            {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
		            name : 'tomato',
		            width: '60%',
		            labelAlign:"right",
		            label: '本人第二代身份证',
		            checked: true
            	   },
            	   {
            		   xtype:'spinnerfield',
            		   width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '配偶第二代身份证'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '本人婚姻证明'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '征信查询授权书'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		   width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '营业执照'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '户口本主页',
		            checked: true
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '户口本本人页',
		            checked: true
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '本人名下房产证'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '配偶名下房产证'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '汽车登记证'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '客户信用评分表'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		   width: '30%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '近半年银行帐户流水'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		    width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '客户交易信息表'
            	   },
            	   {
            		   xtype:'spinnerfield',
            		   width: '40%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        },
		        {
            	   layout: {
						type: 'hbox',
						align: 'middle'
					},
            	   items:[{
            		xtype: 'checkboxfield',
            		labelWidth: '78%',
            		 width: '60%',
		            labelAlign:"right",
		            name : 'tomato',
		            label: '本人名下任一银行借记卡',
		            checked: true
            	   },
            	   {
            		   xtype:'spinnerfield',
            		   width: '30%',
            		   minValue:1,  
                       maxValue:5,
                       stepValue:1,  
                       increment: 2
            	   }]
		        }]},
		        {
		            xtype: 'button',
		            style:'border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;',
		            name: 'submit',
		            text:'选好了',
		            cls : 'buttonCls',
		            handler:this.formSubmit
		        }
	        ]
    	});

    	this.callParent([config]);
    	
    },
    formSubmit:function(){ mobileNavi.push(
    
       Ext.create('htmobile.InformationCollection.person.uploadMaterial',{}));
    
    }

});
