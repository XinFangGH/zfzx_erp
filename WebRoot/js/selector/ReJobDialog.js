/**
 * 岗位选择器
 * @class ReJobDialog
 * @extends Ext.Window
 */
ReJobDialog=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.scope=this.scope?this.scope:this;
		//默认为多单选择岗位
		this.single=this.single!=null?this.single:true;
		this.initUI();
		ReJobDialog.superclass.constructor.call(this,{
			title:'上下级编辑器',
			height:250,
			width:430,
			layout : 'fit',
			maximizable : true,
			items:this.items,
			buttonAlign:'center',
			buttons:[
			{
				text:'确定',
				iconCls:'btn-ok',
				scope:this,
				handler:this.confirm
			}, {
				text : '重置',
				iconCls : 'reset',
				handler : this.reset
			}, {
				text:'取消',
				iconCls:'btn-cancel',
				scope:this,
				handler:this.close
			}
			]
		});
	},
	/**
	 * 初始化UI
	 */
	initUI:function(){
		
	 	this.contentPanel=new Ext.Panel({
	 		id:'contentp',
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			defaults : {
				anchor : '96%,96%'
			},
			items:[
			{
				id:'rdoLv',
                fieldLabel:"选择上下级",  
                xtype:'radiogroup',
                items:[ new Ext.form.Radio({      
                    name : "controlLv",     
                    inputValue : "0",      
                    boxLabel : "同级",
        			listeners:{                                     
        				check : function(checkbox, checked) {
                        if (checked) {
//                        	Ext.getCmp('rdoPosUser').setValue(0);
                        	Ext.getCmp('lvVal').setValue(0);
                        }
        			}}
                }), new Ext.form.Radio({      
                    name : "controlLv",   
                    inputValue : "1",      
                    boxLabel : "上级",
        			listeners:{                                     
        				check : function(checkbox, checked) {
                        if (checked) {
//                        	Ext.getCmp('rdoPosUser').setValue(0);
                        	if(this.reJobId>0&&this.posUserFlag==0){
                        		Ext.getCmp('lvVal').setValue(this.reJobId);
                        	}else{
                        		Ext.getCmp('lvVal').setValue(1);
                        	}
                        }
        			}}
                }),  new Ext.form.Radio({      
                    name : "controlLv",    
                    inputValue : "2",      
                    boxLabel : "下级",
        			listeners:{                                     
        				check : function(checkbox, checked) {
                        if (checked) {
//                        	Ext.getCmp('rdoPosUser').setValue(0);
                        	if(this.reJobId<0&&this.posUserFlag==0){
                        		Ext.getCmp('lvVal').setValue(this.reJobId*(-1));
                        	}else{
                        		Ext.getCmp('lvVal').setValue(1);
                        	}
                        }
        			}}
                })],
                getValue:function(){
                    var v;   
                    if (this.rendered) {   
                        this.items.each(function(item){   
                            if (!item.getValue())    
                                return true;   
                            v = item.getRawValue();   
                            return false;   
                        });   
                    }   
                    else {   
                        for (var k in this.items) {   
                            if (this.items[k].checked) {   
                                v = this.items[k].inputValue;   
                                break;   
                            }   
                        }   
                    }   
                    return v;
                }
			},{
				id:'lvVal',
				xtype:'numberfield',
                fieldLabel:"级数"
            }]
	 	});
	 	
	 	this.userPanel=new Ext.Panel({
	 		id:'userp',
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			defaults : {
				anchor : '96%,96%'
			},
			items:[{
				xtype : 'compositefield',
				id:'relativeUserName',
				fieldLabel : '相对岗位',
				items : [{
						xtype : 'textarea',
						id:'reUserName',
						width : 185,
						readOnly : true
					},{
						xtype : 'button',
						text : '选择',
						scope : this,
						iconCls : 'btn-select',
						handler : function(){
	                    	RelativeJobSelector.getView(function(ids, names) {
	                    		Ext.getCmp('reUserName').setValue(names);
	                    		Ext.getCmp('reUserIds').setValue(ids);
            				}, false,Ext.getCmp('reUserIds').getValue(),Ext.getCmp('reUserName').getValue()).show();
						}
				}]
			},{
				id:'reUserIds',
				xtype:'textfield',
			    hidden:true
			}]
	 	});
	 	
		this.mainPanel=new Ext.Panel({
	 		id:'mainp',
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			defaults : {
				anchor : '96%,96%'
			},
			items:[{
				id:'rdoPosUser',
                xtype:'radiogroup',
				items:[
					new Ext.form.Radio({
		                name : "posUserSelected",     
		                inputValue : "0",      
		                boxLabel : "按岗位选择",
		    			listeners:{
		    				check : function(checkbox, checked) {
		                    if (checked) {
		                    	Ext.getCmp('contentp').show();
		                    	Ext.getCmp('userp').hide();
		                    	Ext.getCmp('mainp').doLayout();
		                    	if(this.posUserFlag==0){
		                    		if(this.reJobId==0||this.reJobId==''){
							 			Ext.getCmp('rdoLv').setValue(0);
							 		}else if(this.reJobId<0){
							 		 	Ext.getCmp('rdoLv').setValue(2);
							 		}else if(this.reJobId>0){
							 			Ext.getCmp('rdoLv').setValue(1);
							 		}
		                    	}else{
		                    		Ext.getCmp('rdoLv').setValue(0);
		                    		Ext.getCmp('lvVal').setValue(0);
		                    	}
		                    }
		    			}}
		            }), new Ext.form.Radio({ 
		                name : "posUserSelected",   
		                inputValue : "1",      
		                boxLabel : "按人员选择",
		    			listeners:{
		    				check : function(checkbox, checked) {
		                    if (checked) {
		                    	Ext.getCmp('contentp').hide();
		                    	Ext.getCmp('userp').show();
		                    	Ext.getCmp('mainp').doLayout();
		                    }
		    			}}
		            })
				],
				getValue:function(){
		            var v;   
		            if (this.rendered) {   
		                this.items.each(function(item){   
		                    if (!item.getValue())    
		                        return true;   
		                    v = item.getRawValue();   
		                    return false;   
		                });   
		            }   
		            else {   
		                for (var k in this.items) {   
		                    if (this.items[k].checked) {   
		                        v = this.items[k].inputValue;   
		                        break;   
		                    }   
		                }   
		            }   
		            return v;
				}
				},this.contentPanel,this.userPanel
			]
		});
	 	
	 	Ext.getCmp('rdoPosUser').setValue(this.posUserFlag);
	 	
	 	if(this.posUserFlag==0||this.posUserFlag==''){
		 	if(this.reJobId==0||this.reJobId==''){
	 			Ext.getCmp('rdoLv').setValue(0);
	 		}else if(this.reJobId<0){
	 		 	Ext.getCmp('rdoLv').setValue(2);
	 		}else if(this.reJobId>0){
	 			Ext.getCmp('rdoLv').setValue(1);
	 		}
	 	}else{
//	 		Ext.getCmp('rdoLv').setValue(0);
	 		Ext.getCmp('reUserIds').setValue(this.reJobId);
	 		Ext.getCmp('relativeUserName').items[0].value = this.reJobName;
	 	}
	 	
	 	this.items = [];
		this.items.push(this.mainPanel);
		
	},//end of initUI function
	/**
	 * 选择岗位
	 */
	confirm:function(){
		var rdoPosUserValue = Ext.getCmp('rdoPosUser').getValue();
		if(rdoPosUserValue==0){
			var rdoLvValue = Ext.getCmp('rdoLv').getValue();
			var lvValue = Ext.getCmp('lvVal').getValue();
			var lvText = "同级";
			if(rdoLvValue==0){
				if(lvValue!=0){
					Ext.MessageBox.alert("提示","级数输入错误");
					return;
				}
			}else if(rdoLvValue==1){
				if(lvValue<=0){
					Ext.MessageBox.alert("提示","级数输入错误");
					return;
				}
				lvText = "上 "+lvValue+" 级";
				
			}else if(rdoLvValue==2){
				if(lvValue<=0){
					Ext.MessageBox.alert("提示","级数输入错误");
					return;
				}
				lvText = "下 "+lvValue+" 级";
				lvValue = lvValue*(-1);
			}
			if (this.callback){
				this.callback.call(this.scope, lvValue, lvText, rdoPosUserValue);
			}
		}else if(rdoPosUserValue==1){
			var reUserIds = Ext.getCmp('reUserIds').getValue();
			var reUserName = Ext.getCmp('reUserName').getValue();
			if (this.callback){
				this.callback.call(this.scope, reUserIds, reUserName, rdoPosUserValue);
			}
		}else{
			if (this.callback){
				this.callback.call(this.scope, '', '', null);
			}
		}

		this.close();
	},
	reset : function() {
		Ext.getCmp('rdoPosUser').setValue(-1);
		Ext.getCmp('rdoLv').setValue(-1);
		Ext.getCmp('lvVal').setValue('');
		Ext.getCmp('contentp').hide();
		Ext.getCmp('reUserIds').setValue('');
		Ext.getCmp('reUserName').setValue('');
		Ext.getCmp('relativeUserName').setValue('');
	    Ext.getCmp('userp').hide();
		Ext.getCmp('mainp').doLayout();
	}
});