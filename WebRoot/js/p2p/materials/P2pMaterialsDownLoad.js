/**
 * 图片查看器
 * 
 * @author Jiang Wanyu
 */
 var P2pMaterialsDownLoad = function(mark,readOnly,typeisfile,webId,projectId,businessType)
 	{
 	var isReadOnly=false;
    if(typeof(readOnly)!="undefined"){
 		isReadOnly=readOnly; 
 	}
 	var url=basepath();
 	url=url+"p2pMaterials/getFileListExtPlWebShowMaterials.do";
 	if(typeisfile !=null){
 	url=url+"?typeisfile="+typeisfile;
 	}
 	var jStore;//数据源
 	var tpl;//
 	var panel;
 	var win;//窗口
 	 
 	jStore = new Ext.data.JsonStore({
 		url : url,
 		totalProperty : 'totalProperty',
 		root : 'topics',
 		fields : [{
 			name : 'fileid'
 		},{ 
 			name : 'filename'
 		},{ 
 			name : 'extendname'
 		},{
 			name : 'filepath'
 		},{
 			name : 'createtime'
 		},
 		{
 			name : 'fileAttachID'
 		},{
 			name : 'filesize'
 		},{
 			name : 'webPath'
 		},{
 			name : 'minCompressionFilePath'
 		}],
 		baseParams : {
 			mark : mark,
 			projId:projectId,
 			businessType:businessType,
 			webId:webId
 		}
 	});
 	//加载数据
 	jStore.load();
 	var img;
 	if(!isReadOnly){
// 	store.getAt("第几条数据").get("字段名") 
	 	    	tpl = new Ext.XTemplate(
	 	    	'<tpl for=".">',
	            '<div class="thumb-wrap" id="{fileid}">',  
			    '<div class="thumb">' +
			    '<img src="{[this.returnUrl(values.extendname,values.webPath)]}"   title="{filename}"  />' +
			    '</div>',
			    '<span onclick="download1({[this.download(values.fileid)]})" style="text-decoration:underline;cursor:pointer;height:15px">下载</span>' +
			    '<span onclick="removeImg1({[this.download(values.fileid)]},'+webId+','+projectId+')" style="text-decoration:underline;cursor:pointer;height:15px">删除</span>' +
			    '<span onclick="download(values.fileid)"  if="this.imgOrDir(type) == false" class="x-editable" >{filename}</span>' +
			    '</div>',
	        '</tpl>',
	        '<div class="x-clear"></div>'
//			'<tpl for=".">',
//	            '<div class="thumb-wrap" id="{fileid}">',  
//			    '<div class="thumb">' +
//			    '<img src="{[this.returnUrl(values.extendname,values.minCompressionFilePath)]}"   title="{filename}"  />' +
//			    '</div>',
//			     '<span onclick="download1({[this.download(values.fileid)]})" style="text-decoration:underline;cursor:pointer;height:15px">下载</span>' +
////			     '<span onclick="removeImg1({[this.removeImg('+projectId+','+businessType+','+webId+',values.fileid)]},{[this.removewebId()]} ,{[this.removeprojectId(values.projectId)]})" style="text-decoration:underline;cursor:pointer;height:15px">删除</span>'+
////			     '<span  onclick="download(values.fileid)"  if="this.imgOrDir(type) == false" class="x-editable" >{filename}</span></div>',
////			     '<a href ="{[this.returnHrefpath(values.webPath)]}",target="_blank"> <span  style="text-decoration:underline;cursor:pointer;height:15px">下载</span></a><span  onclick="download(values.fileid)"  if="this.imgOrDir(type) == false" class="x-editable" >{filename}</span></div>',
////			     '<a href="'+fileURL+' "/{this.jStore.getAt(select).get(webPath)} ,_blank><span  style="text-decoration:underline;cursor:pointer;height:15px">下载</span><span  onclick="download(values.fileid)"  if="this.imgOrDir(type) == false" class="x-editable" >{filename}</span></div>',
//	        ' </div>,</tpl>',
//	        '<div class="x-clear"></div>'
	       // <span  onclick="download(values.fileid)"  if="this.imgOrDir(type) == false" class="x-editable" >{filename}</span>
		    ,{
			removeImg:function(projectId,webId,fileid){
				return projectId+"."+webId+"."+fileid;
			},
		   returnUrl:function(extendname,webPath){
		        if(extendname==".doc" || extendname==".docx"){
	                   return basepath()+"//images//desktop//docx_win.png";	
		        }
		        else if(extendname==".xls" || extendname==".xlsx"){
		        	   return basepath()+"//images//desktop//xlsx_win.png";	
		        }
		        else if(extendname==".pdf"){
		        	   return basepath()+"//images//desktop//pdf_win.jpg";	
		        }
		        else if(extendname==".txt"){
		        	   return basepath()+"//images//desktop//text.png";	
		        }else {
		              return __fileURL+webPath;
		        }
		 },
		  download:function(v){
		  	return v;
		}
		});
 	}
 	else{
 	    	tpl = new Ext.XTemplate(
			'<tpl for=".">',
	            '<div class="thumb-wrap" id="{fileid}">',
			    '<div class="thumb">' +
			    '<img src="{[this.returnUrl(values.extendname,values.webPath)]}"   title="{filename}"/>' +
			    '</div>',
//			     '<span onclick="removeImg1({[this.removeImg('+projectId+','+businessType+','+webId+',values.fileid)]},{[this.removewebId()]} ,{[this.removeprojectId(values.projectId)]})" style="text-decoration:underline;cursor:pointer;height:15px">删除</span>'+
			     '<span onclick="download1({[this.download(values.fileid)]})" style="text-decoration:underline;cursor:pointer;height:15px">下载</span><span  onclick="download(values.fileid)"  if="this.imgOrDir(type) == false" class="x-editable" >{filename}</span></div>',
	        '</tpl>',
	        '<div class="x-clear"></div>'
		,{
		 returnUrl:function(extendname,webPath){
		  
		        if(extendname==".doc" || extendname==".docx"){
	                   return basepath()+"//images//desktop//docx_win.png";	
		        }
		        else if(extendname==".xls" || extendname==".xlsx"){
		        	   return basepath()+"//images//desktop//xlsx_win.png";	
		        }
		        else if(extendname==".pdf"){
		        	   return basepath()+"//images//desktop//pdf_win.jpg";	
		        }
		        else if(extendname==".txt"){
		        	   return basepath()+"//images//desktop//text.png";	
		        }else {
		                 return __fileURL+webPath;
		        }
		 },
		  download:function(v){
		  	return v;
		}
		});
 	}

    panel = new Ext.Panel({
        id:'images-view',
        frame:false,
        autoScroll : true,
        layout:'fit',
        items: new Ext.DataView({
            store: jStore,
            tpl: tpl,
            autoHeight:true,
            multiSelect: true,
            overClass:'x-view-over',
            itemSelector:'div.thumb-wrap',
            emptyText: 'No images to display',

            plugins: [
                new Ext.DataView.DragSelector()
                //new Ext.DataView.LabelEditor({dataIndex: 'filename'})
            ],
           
            prepareData: function(data){
            	
                data.shortName = Ext.util.Format.ellipsis(data.filename, 15);
                data.sizeString = Ext.util.Format.fileSize(data.filesize);
                return data;
            },
            
            listeners: {
            	selectionchange: {
            		fn: function(dv,nodes){
            			var l = nodes.length;
            			var s = l != 1 ? 's' : '';
//            			panel.setTitle('Simple DataView ('+l+' item'+s+' selected)');
            		}
            	},
            	dblclick: {
            		fn: function(dv,index,node,e){
            				 
            			var i=0;
			               var img = new Array();
			               var ds = this.getStore();
			                ds.each(function(record){
			               //img+=record.get('webPath')+',';
			               		img[i]=__fileURL+"/"+record.get('webPath');
			                	i++;
			               });
			                
			               var indx = img[index];
			               		 var last = indx.lastIndexOf('.');
			               		 if(indx.substring(last,indx.length)!='.jpg'&&indx.substring(last,indx.length)!='.png'&&indx.substring(last,indx.length)!='.gif'){
			               			showPic(jStore.getAt(index).get('webPath'),'图片查看-'+jStore.getAt(index).get('filename'),jStore.getAt(index).get('extendname'),jStore.getAt(index).get('fileAttachID'));
			               		 	//  Ext.Msg.alert("不支持该预览功能!");
			               			
			               			return;
			                }
			               //img= img.substring(0,img.length-1);
			               //img+=']';
			                 var ims = new Array();
			                 var j = 0; 
			                for(var i = 0; i<img.length;i++){
			                	 var indx = img[i];
			               		 var last = indx.lastIndexOf('.');
			               		 if(indx.substring(last,indx.length)!='.jpg'&&indx.substring(last,indx.length)!='.png'&&indx.substring(last,indx.length)!='.gif'){
			               			//img.remove(img[i]);
			               		 }
			               		 else{
			               		 	ims[j]= indx;
			               		 	if(i==index)
			               		 		index = j;
			               		 	j++;	
			               		 }
			                }
			               new my_image_window({
			              	  imgs:ims,
			               	 inde:index
			               }).show();
            			//showPic(jStore.getAt(index).get('webPath'),'图片查看-'+jStore.getAt(index).get('filename'),jStore.getAt(index).get('extendname'),jStore.getAt(index).get('fileAttachID'));
            		}
            	}
            }
        })
    });
    
    win = new Ext.Window({
			id : 'id_win',
			title: '预览',
			layout : 'fit',
			width : 550,
			height :405,
			closable : true,
			resizable : false,
			plain : true,
			modal : true,
			border : false,
			buttonAlign: 'center',
			items : [panel]
	});
	
	win.show();
	
	////////////////////////////////////////////////////functions
	
	//查看图片
	function showPic(src,title,extendname,fileAttachID){
		
		if(extendname==".doc" || extendname==".docx" || extendname==".xls" || extendname==".xlsx"){
			new OfficeTemplateView(fileAttachID,src,isReadOnly,null);
			return false;
		}
		else if(extendname==".pdf"){
		    new PdfTemplateView(fileAttachID,src,isReadOnly,null);
			return false;
		}
		var pic_panel;
		var pic_win;
		pic_panel = new Ext.Panel({
			
			id:'id_pic_panel',
	        frame:true,
	        autoHeight : true,
	        width: 374,
	        layout:'fit',
	        items:[{
	        	html: '<div align="center"><img src="'+src+'"  title="将鼠标置于图片上，滚动鼠标滑轮可以把图片放大缩小哦！" onmousewheel="return bbimg(this)" style="cursor:pointer;"   ondblclick="downloadPic(\''+src+'\'); "/></div>'
	        }]
			
		});
		
		pic_win = new Ext.Window({
			id : 'id_pic_win',
			title: title,
			autoScroll : true,
			layout : 'fit',
			width : 550,
			height :405,
//			animCollapse : true,
			animateTarget : win.getEl(),
//			closeAction : 'hide',
			maximizable : true,
			closable : true,
			resizable : true,
			plain : true,
			modal : true,
			buttonAlign: 'center',
			bodyStyle : 'padding: 0',
			items : [pic_panel],
			listeners : {
				scope : this,
				maximize : function() {
					pic_panel.setWidth(window.screen.width - 26);
				}
			}
		});
		
		pic_win.show();
		pageInit();
	};
 	
 }
//下载图片
function downloadPic(imgURL){
	
	var oPop;
	
	if(Ext.isIE){
	  	oPop = window.open(imgURL,"","width=0,height=0,top=5000,left=5000");   
	}else{
		oPop = window.open(imgURL,"","width=0,height=0,top=9,left=9");
	}
	  for(;oPop.document.readyState != "complete";)   
	  { 
	    if(oPop.document.readyState == "complete")break; 
	  } 
	  oPop.document.execCommand("SaveAs"); 
	  oPop.close();
};

function bbimg(o){
	 var zoom=parseInt(o.style.zoom, 10)||100;zoom+=event.wheelDelta/12;if (zoom>0) o.style.zoom=zoom+'%';
	 return false;
}

function download1(v){
    window.open(__ctxPath+"/p2pMaterials/downloadMaterialsPlWebShowMaterials.do?fileid="+v,'_blank');
};
function removeImg1(filedId,webId,projectId){
	 
	// 
	Ext.Ajax.request({
		jStore : this.jStore,
		url : __ctxPath+"/p2pMaterials/removeImgPlWebShowMaterials.do",
		method : 'POST',
		success : function(response, request) {
			
			//小图片删除后刷新图片和贷款材料清单数据信息
			var panel = Ext.getCmp("images-view");
			panel.items.items[0].store.reload();     
			var grid =  Ext.getCmp("materials_grid_panel");
			grid.store.reload();
		   
		},
		failure : function(response) {
			Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
		},
		params : {
			fileid:filedId,
			projId:projectId,
		    webId:webId
		}
    }) 
      
};

