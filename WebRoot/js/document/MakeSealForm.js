
/**
 * @author YungLocke
 * @class MakeSealForm
 * @extends Ext.Window
 */
MakeSealForm=Ext.extend(Ext.Window,{
      formPanel:null,
      toolbar:null,
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUI();
          MakeSealForm.superclass.constructor.call(this,{
               title : '在线制作电子印章',
               width : 580,
			   height : 440,
               shim:false,
               modal : true,
               tbar:this.toolbar,
               iconCls:'menu-seal',
               layout:'fit',
               maximizable:true,
               buttonAlign : 'center',
			   buttons:this.buttons,
               items:[
                  this.formPanel
               ]
          
          });
      },
//      afterRender:function(){
//           MakeSealForm.superclass.afterRender.call(this);
//           if(this.isCancel){
//              this.close();
//           }
//      },
      initUI:function(){
      	  Ext.useShims=true;
      	  this.ekeyPanel=new NtkoSignPanel({height:200,border:true});
          this.formPanel=new Ext.FormPanel({
             layout:'form',
             defaults:{
                anchor:'98%,98%'
             },
             defaultType:'textfield',
             items:[this.ekeyPanel,{
             	 xtype:'hidden',
                 name:'seal.sealId',
                 value:this.sealId?this.sealId:''
             },
             {
                 xtype:'hidden',
                 name:'seal.fileId'
             },	
             {
                 xtype:'hidden',
                 name:'seal.sealPath'
             },
             {
                fieldLabel:'印章名称',
                allowBlank:false,
                name:'seal.sealName'
             },{
                fieldLabel:'印章使用者',
                allowBlank:false,
                name:'seal.belongName'
             },{
                fieldLabel:'印章口令[6-32位]',
                inputType : 'password',
                allowBlank:false,
                name:'sealPassword',
                minLength:6,
                maxLength:32
                
             },{
                fieldLabel:'印章序列号[只读]',
                allowBlank:false,
                name:'sealSN',
                readOnly:true
                
             }]
          
          });
           this.toolbar=new Ext.Toolbar({
          	   items:[{
                 iconCls:'btn-add',
                 text:'打开本地印章',
                 scope:this,
                 handler:this.openSign
                },{
                 text:'新建',
                 iconCls:'btn-add',
                 scope:this,
                 handler:this.newSign
                },{
                  text:'保存',
                  iconCls:'btn-save',
                 scope:this,
                 handler:this.saveSign
                }]
             });
          
          this.buttons=[{
			    xtype:'button',
			    text:'关闭',
			    iconCls:'close',
			    scope:this,
			    handler:function(){
			        this.close();
			    }
			}];
          
          if(this.sealId!=''&&this.sealId!=null&&this.sealId!=undefined){
          	  if(this.fileId!=''&&this.fileId!=null&&this.fileId!=undefined){
//          	      			var ekeyObj=this.ekeyPanel.getEkeyObject();
					    	var ekeyObj=this.ekeyPanel.openFormURL(this.fileId,null);
					    	if(0 != ekeyObj.StatusCode)
						    {
						    	this.formPanel.getCmpByName('seal.sealId').setValue('');
						    	this.fileId='';
							    return;
						    }
					        this.setValue(ekeyObj);
//					    	ekeyObj.IsShowRect=false;
//					        ekeyObj.OpenFromURL(__ctxPath+'/file-download?fileId='+this.fileId,null);
//					        if(0 != ekeyObj.StatusCode)
//						    {
//						    	this.close();
//							    return;
//						    }
//					        this.setValue(ekeyObj);
          	  	
          	  }
//          	    var self=this;
//            	this.formPanel.loadData({
//					url : __ctxPath + '/document/getSeal.do?sealId='
//							+ this.sealId,
//					root : 'data',
//					preName : 'seal',
//					success:function(response,options){
//						var obj=Ext.util.JSON.decode(response.responseText).data;
//					    var af=obj.fileAttach;
//					    if(af){
//					        var ekeyObj=self.ekeyPanel.getEkeyObject();
//					    	ekeyObj.IsShowRect=false;
//					        ekeyObj.OpenFromURL(__ctxPath+'/file-download?fileId='+af.fileId,null);
//					        self.setValue(ekeyObj);
//					    }
//					}
//				});
//                var ekeyObj=this.ekeyPanel.getEkeyObject();
//		    	ekeyObj.IsShowRect=false;
//		        ekeyObj.OpenFromURL(__ctxPath+'/attachFiles/sealSign/201011/2b6afed7812b45cfb086e85983c8ed0d.esp',null);
//		        this.setValue(ekeyObj);
          }
           
         
      },
      openSign:function(){
	    	var ekeyObj=this.ekeyPanel.getEkeyObject();
	    	ekeyObj.IsShowRect=false;
	        ekeyObj.OpenFromLocal('',true);
	        if(0 != ekeyObj.StatusCode)
			{
				    return;
			}
			this.formPanel.getCmpByName('seal.sealId').setValue('');
			this.fileId='';
	        this.setValue(ekeyObj);
	   },
	   newSign:function(){
	      var ekeyObj=this.ekeyPanel.getEkeyObject();
	      ekeyObj.IsShowRect=false;
	      ekeyObj.CreateNew();
	      if(0 != ekeyObj.StatusCode)
		  {
			    return;
		  }
		  this.formPanel.getCmpByName('seal.sealId').setValue('');
		  this.fileId='';
	      this.setValue(ekeyObj);
	   },
	   saveSign:function(){
	   	  if(this.formPanel.getForm().isValid()){
		   	  var fileId='';
		   	  fileId=this.fileId?this.fileId:'';
		   	  var path='';
		   	  var fileName=this.formPanel.getCmpByName('seal.sealName').getValue();
		   	  var belongName=this.formPanel.getCmpByName('seal.belongName').getValue();
	          var password=this.formPanel.getCmpByName('sealPassword').getValue();
			  var obj= this.save(fileId,fileName,belongName,password);
			  if(obj&&obj.success){
			  	fileId=obj.fileId;
			  	path=obj.filePath;
			  	this.formPanel.getCmpByName('seal.fileId').setValue(fileId);
			  	this.formPanel.getCmpByName('seal.sealPath').setValue(path);
			  	$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/document/saveSeal.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('SealGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
			  }
	   	  }
	   },
	   save:function(fileId,fileName,belongName,password){
	        var result=this.ekeyPanel.save(fileId,fileName,belongName,password);
	        var obj=Ext.util.JSON.decode(result);
	        if(obj&&obj.success){
	           return obj;
	        }else{
	           return {success:false};
	        }
	   },
	   setValue:function(ekeyObj){
	      this.formPanel.getCmpByName('seal.sealName').setValue(ekeyObj.SignName);
          this.formPanel.getCmpByName('seal.belongName').setValue(ekeyObj.SignUser);
          this.formPanel.getCmpByName('sealPassword').setValue(ekeyObj.Password);
          this.formPanel.getCmpByName('sealSN').setValue(ekeyObj.SignSN);
	   }
      
});