
Ext.define('htmobile.creditFlow.public.file.FileList', {
    extend: 'mobile.List',
    
    name: 'FileList',

    constructor: function (config) {
		
    	config = config || {};
    	this.mark=config.mark;
    	this.tableid=config.tableid;
	    this.typeisfile=config.typeisfile;
	    this.title=config.title;
	    this.readOnly=config.readOnly;
	    var params={};
	    if(Ext.isEmpty(this.typeisfile)){
	    	 params={ mark : config.mark+config.tableid};
	    
	    }else{
	         params={
						mark : config.mark+config.tableid,
						typeisfile:config.typeisfile
			    };
	    }
    	var button=this.readOnly?{}:Ext.create('Ext.Panel',{
		docked:'bottom',
		items:[{
			style:"padding-top:10px;background:#1570af;font-color:white",
	    	xtype: 'button',
	    	align: 'center',
	    	margin:"0 20 20 20",
	    	scope:this,
	        text:"<font color=white>添加图片材料</font>",
	        handler:this.addmatrials
		}]});
    	this.data=config.data;
    	Ext.apply(config,{
    		modeltype:"personMaterialsList",
    		title:config.title,
    		 items: [button],
    		fields:[{
			name : 'fileid'
		},{
			name : 'recordid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'modename'
		}],
    		url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListisMobileFileForm.do',
	        params : params,
    		root:'topics',
    	    totalProperty: 'totalCounts',
    	//	itemCls :"listline",
    	//	pressedCl:"listpressedCls",
    	//	selectedCls:"lisselectedCls",
	        plugins:[ {
	            type:"listopt",
	            itemFilter:function(list,action, record){
	            	if(action=="See"){
	            		
	            	   return true;
	            	}
	               if(action=="Edit"){
	               	  //加权限
		               	if(list.readOnly){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	   
	            	}
//	            	if(action=="Remove"){
//	            		 //加权限
//	            	    if(list.readOnly){
//		               	   return false;
//		               	}else{
//		               		return true;
//		               	}
//	            	}
	            },
	            items:[ {
	                action:"See",
	                cls:"trash",
	                color:"grey",	
	                text:"查看"
	            } /*,{
	                action:"Remove",
	                cls:"trash",
	                color:"red",	
	                text:"删除"
	            }*/ ]
	        } ],
		   	  itemTpl: new Ext.XTemplate(   "<table>" +
			    		  "<tr  >" +
			    		    "<img src='"+__ctxPath+"/{filepath:this.subfilepath}' width='100%' height='100%'  /></div>" +
		    		      "</tr>" +
		    		    "</table>",{
		    		filepathFormat: function(filepath) {
		    			//      filepath='attachFiles/webfile/sss.docx';
		    			 	var reg = /\\/g;
                            var  filepath=filepath.replace(reg,"/");
                            return filepath;
						},subfilepath: function(filepath) {
							var aary=filepath.split(".jpg");
                            var  filepath=aary[0]+"_sub.jpg";
                            return filepath;
						}  }),
		    totalProperty: 'totalProperty',
		//    pullRefresh: true,
		    listPaging: true
		    /*,
		    onItemDisclosure:function(record,element,index,e){
		        this.itemsingletap(record);
		    }*/
	/*	    listeners: {
    			 itemsingletap:function( this1, index, target, record, e, eOpts ){
			       mobileNavi.push(
					Ext.create('htmobile.InformationCollection.person.bigMaterialsPhoto',{
						        filepath:record.data.filepath
					        	})
					    	);
      
   }
    		}*/
    	});

    	this.callParent([config]);
    },
    addmatrials:function(){
      mobileNavi.push(Ext.create('htmobile.InformationCollection.person.uploadMaterial',{id:this.tableid,mark:this.mark,resouce:'materiallist'}));
    
    },
    initialize:function() {
        this.callParent(arguments);
        this.on({
            listoptiontap:"optTap",
            scope:this
        });
    },
    optTap:function(action, list, record) {
    	var obj=this;
       var id=  record.data.fileid;
       var data=record.data;
        if (action == "See") {
        	 var filepath=data.filepath;
        	 var reg = /\\/g;
             var filepath=filepath.replace(reg,"/");
              mobileNavi.push(
					Ext.create('htmobile.InformationCollection.person.bigMaterialsPhoto',{
						        filepath:filepath
					        	})
					    	);
       } else if (action == "Remove") {
         	 
	  		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
			if (btn == "yes") {
			
                   Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/fileUploads/DeleRsFileForm.do',
					params:{
					    fileid:id
					},
				    success : function(response) {
				    	if(response.responseText=="{error}"){
				    	 Ext.Msg.alert("", "删除错误");
				    	
				    	}else{
							var obj = Ext.util.JSON.decode(response.responseText);
							if(obj.success==true){
								
								 Ext.Msg.alert("", "删除成功");
								 mobileNavi.getActiveItem().getStore().loadPage(1);
								
							}else{
							    Ext.Msg.alert("", "删除失败");
							}
				    	}
				}
			});
			}})
         }
    }

});
