
Ext.define('htmobile.creditFlow.public.main.SlEnterPriseProcreditMaterialsViewPic', {
    extend: 'mobile.List',
    
    name: 'SlEnterPriseProcreditMaterialsViewPic',

    constructor: function (config) {
		
    	config = config || {};
    	this.mark=config.mark;
    	this.id=config.id;
	    this.typeisfile=config.typeisfile;
	    this.title=config.title;
	    var params={};
	    if(this.typeisfile==null){
	    	 params={ mark : config.mark+config.id};
	    
	    }else{
	         params={
						mark : config.mark+config.id,
						typeisfile:config.typeisfile
			    };
	    }
    	var button=Ext.create('Ext.Panel',{
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
    		url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListFileForm.do',
	        params : params,
    		root:'topics',
    	    totalProperty: 'totalCounts',
    		itemCls :"listline",
    		pressedCl:"listpressedCls",
    		selectedCls:"lisselectedCls",
		   	  itemTpl: new Ext.XTemplate(   "<div style='display:block; width:100%; border:0;'>" +
			    		"<div style='width:65%;padding: 5% 0 5% 5%;float:left;' ><img src='"+__ctxPath+"/{filepath:this.subfilepath}' width='150px' height='100px' onclick=\"javascript:seebigphoto('{filepath:this.filepathFormat}');\" /></div>" +
			    		"<div style='width:35%;padding: 5% 0 5% 5%;float:left;'><span style:'padding: 30% 0 30% 5%;' onclick=\"javascript:deletephoto('{fileid}');\">删除</span></div>" +
		    		"</div>",{
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
		    listPaging: true/*,
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
         seebigphoto=function(filepath){
              mobileNavi.push(
					Ext.create('htmobile.InformationCollection.person.bigMaterialsPhoto',{
						        filepath:filepath
					        	})
					    	);
         }
         deletephoto=function(id){
         	 
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
    },
    addmatrials:function(){
      mobileNavi.push(Ext.create('htmobile.InformationCollection.person.uploadMaterial',{id:this.id,mark:this.mark,resouce:'materiallist'}));
    
    }

});
