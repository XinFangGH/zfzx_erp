/**
 * @author YungLocke
 * @class FileDetailShowWin
 * @extends Ext.Window
 */
FileDetailShowWin=Ext.extend(Ext.Window,{
    formPanel:null,
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   FileDetailShowWin.superclass.constructor.call(this,{
	      layout:'fit',
	      height:300,
	      title:this.fileName+'-属性',
	      modal:true,
	      width:600,
	      items:this.formPanel,
	      buttons:this.buttons,
	      buttonAlign:'center'
	   });
	},
	initUI:function(){
	    this.formPanel=new Ext.FormPanel({
	        
	        layout:'form',
	        padding:'10px',
	        defaultType:'displayfield',
	        items:[{
	           xtype:'hidden',
	           name:'fileId',
	           value:this.fileId
	        },{
	        	fieldLabel:'文件名',
	            name:'fileName'
	        },{
	        	fieldLabel:'文件路径',
	            name:'filePath'
//	            ,
//	            value:this.filePath
	        },{
	           fieldLabel:'文件类型',
	           name:'fileType'
	        },{xtype:'container',
	           layout:'form',
	           defaultType:'displayfield',
	           hidden:!this.isFolder,
	           defaults:{
	            padding:'0',
	           	anchor : '96%,96%'
	           },
	           items:
	        	[{
		           fieldLabel:'目录数',
		           name:'folderNum'
		        },{
		           fieldLabel:'文档数',
		           name:'documentNum'
		        },{
		           fieldLabel:'描述',
		           name:'descp'
		        }]
	        },{xtype:'container',
	           layout:'form',
	           defaultType:'displayfield',
	           hidden:this.isFolder,
	           defaults:{
	            padding:'0',
	           	anchor : '96%,96%'
	           },
	           items:
	        	[{
		           fieldLabel:'作者',
		           name:'author'
		        },{
		           fieldLabel:'关键字',
		           name:'keywords'
		        }]
	        },{
	           fieldLabel:'附件数',
	           name:'attachsNum'
	        },{
	           fieldLabel:'文件大小',
	           name:'docFileSize'
	        }]
	    });
	    var formPanel=this.formPanel;
	    var win=this;
	    var path='/知识目录';
	    if(this.isPersonal){
	       path='/我的文件夹';
	    }else if(this.isOnline){
	       path='/在线文档目录';
	    }
	    Ext.Ajax.request({
	        url:__ctxPath + '/document/detailDocFolder.do',
	        params:{
	        	isPersonal:this.isPersonal,
	            fileId:this.fileId,
	            isFolder:this.isFolder
	        },
	        success:function(response, request){
	        	var json=Ext.util.JSON.decode(response.responseText);
	        	formPanel.getCmpByName('fileName').setValue(json.fileName);
	        	win.setTitle(json.fileName+'-属性');
	        	formPanel.getCmpByName('fileType').setValue(json.fileType);
	        	formPanel.getCmpByName('folderNum').setValue(json.folderNum);
	        	formPanel.getCmpByName('documentNum').setValue(json.documentNum);
	        	formPanel.getCmpByName('attachsNum').setValue(json.attachsNum);
	        	formPanel.getCmpByName('docFileSize').setValue(json.docFileSize);
	        	if('/'==json.path){
	        	   json.path="/";
	        	   path="";
	        	}
	        	formPanel.getCmpByName('filePath').setValue(path+json.path);
	        	var descp='';
	        	if(json.descp!=null&&json.descp!='null'){
	        	   descp=json.descp;
	        	}
	        	formPanel.getCmpByName('descp').setValue(descp);
	        	var author='';
	        	if(json.author!=null&&json.author!='null'){
	        	   author=json.author;
	        	}
	        	formPanel.getCmpByName('author').setValue(author);
	        	var keywords='';
	        	if(json.keywords!=null&&json.keywords!='null'){
	        	   keywords=json.keywords;
	        	}
	        	formPanel.getCmpByName('keywords').setValue(keywords);
	        },
	        failure:function(response, request){}
	    });
	    this.buttons=[
	    {
	       xtype:'button',
	       scope:this,
	       text:'关闭',
	       iconCls:'close',
	       handler:function(){
	          this.close();
	       }
	    }
	    ];
	    
	}
});