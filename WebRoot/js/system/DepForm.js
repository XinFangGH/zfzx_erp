DepForm=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUI();
		DepForm.superclass.constructor.call(this,{
			title:'公司或组织信息',
			height : 200,
			width : 650,
			maximizable : true,
			layout:'fit',
			items:[this.formPanel],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			},
			buttonAlign : 'center',
			buttons : [{
	            text : '保存',
	            iconCls : 'btn-save',
	            handler : this.save,
	            scope : this
	        },{
	            text : '取消',
	            iconCls :'btn-cancel',
	            scope:this,
	            handler:this.close
	        }]
		});
	},
	initUI:function(){
		this.formPanel=new Ext.FormPanel({
			border:false,
			bodyStyle : 'padding : 5px;',
			defaultType: 'textfield',
			defaults: {
	            anchor: '95%,95%',
	            allowBlank: false,
	            selectOnFocus: true,
	            msgTarget: 'side'
	        },
	        items:[{
		        	xtype:'hidden'
		        	,name:'organization.orgId'
		        	,id:'orgId'
		        },{
	            	xtype:'hidden',
	            	name:'organization.orgSupId',
	            	id:'orgSupId',
	            	value:this.parentId
	            },
	            {
	            	xtype:'hidden',
	            	name:'organization.path',
	            	id:'path'
	            }, {
	            	fieldLabel : '公司或组织名',
	            	name:'organization.orgName',
	            	blankText: '公司或组织名为必填!',
	            	id:'orgName'
	            }, {
					fieldLabel : '公司或组织描述',
	            	xtype:'textarea',
	            	name:'organization.orgDesc',
	            	blankText: '公司或组织描述为必填!',
	            	id:'orgDesc'
	            }
	            ]
		});
		
		if(this.parentText){
			this.formPanel.insert(0,{
				xtype:'textfield',
				fieldLabel:'上级组织或公司',
				readOnly:true,
				value:this.parentText
			});	
			this.formPanel.doLayout();
		}
		if(this.depId){
			this.formPanel.loadData({
				url:__ctxPath+'/system/getOrganization.do?orgId='+this.depId,
				root:'data',
				preName:'organization'
			});
		}
	},
	save:function(){
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/system/saveOrganization.do',
			params:{
				'organization.orgType':this.orgType,
				'organization.demId':1
			},
			callback : function(fp, action) {
				if(this.callback){
					this.callback.call(this.scope);
				}
				this.close();
			}
		});
	}
	
});