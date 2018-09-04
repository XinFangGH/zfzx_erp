/**
 * 集成软航Office在线编辑控件
 * @param {} conf
 * @return {}
 */
NtkOfficePanel=function(conf){
	var isFileOpen=false;
	conf.doctype=conf.doctype?conf.doctype:'doc';
	var fileId=conf.fileId?conf.fileId:'';
	var tempPath=conf.tempPath;
	var usetemplate=conf.usetemplate?conf.usetemplate:0;
	var deftemplatekey=conf.deftemplatekey?conf.deftemplatekey:'';
	var officeObj = document.createElement('object');
	
	var p=document.createElement("param");
	p.setAttribute('name','Caption');
	p.setAttribute('value','北京互融时代软件有限公司在线Office文档');
	officeObj.appendChild(p);

	p=document.createElement('param');
	p.setAttribute('name','MakerCaption');
	p.setAttribute('value','广州宏天软件有限公司');
	officeObj.appendChild(p);
	
	p=document.createElement('param');
	p.setAttribute('name','MakerKey');
	p.setAttribute('value','CF4960BFDB79D36ADDC5493B116D39D6A4E335D9');
	officeObj.appendChild(p);
	
	p=document.createElement('param');
	p.setAttribute('name','ProductCaption');
	p.setAttribute('value','北京互融时代软件有限公司');
	officeObj.appendChild(p);
	
	p=document.createElement('param');
	p.setAttribute('name','ProductKey');
	p.setAttribute('value','B3D841AF9FBA350478D0888FE8A881D7BF47BD32');
	officeObj.appendChild(p);
	
	p=document.createElement('param');
	p.setAttribute('name','TitlebarColor');
	p.setAttribute('value','14402205');
	
	officeObj.appendChild(p);
	
	officeObj.width = "100%";
	officeObj.height = "100%";

	officeObj.classid= "clsid:A39F1330-3322-4a1d-9BF0-0BA2BB90E970"; 
	officeObj.codebase = __ctxPath+'/js/core/ntkoffice/OfficeControl.cab#version=5,0,1,0';//weboffice_V6.0.4.6.cab#V6,0,4,6
     
	var panelConf={border:false,layout:'fit'};
	
	/**
	 * 保存文档
	 */
	var saveFn=function(config){
		fileId=config.fileId?config.fileId:'';
		var docName=config.docName?config.docName:'未命名';
		officeObj.IsUseUTF8URL=true;
     	officeObj.IsUseUTF8Data=true;
		var result= officeObj.SaveToURL(__fullPath + '/file-upload',"document/saveOnlineManage","fileId="+fileId+'&&file_cat=document/saveOnlineManage',docName+'.'+conf.doctype,0);
		
		var obj=Ext.util.JSON.decode(result);
		if(obj && obj.success){
			fileId=obj.fileId;
		}else{
			obj={success:false};
		}
		return obj;
	};
	/**
	 * 是否显示菜单
	 */
	if(conf.unshowMenuBar){
	    officeObj.Menubar=false;
	    officeObj.IsShowEditMenu=false;
	    officeObj.FileNew=false;
	    officeObj.FileOpen=false;
	    officeObj.FileSave=false;
	    officeObj.FileSaveAs=false;
	}
	if(conf.showToolbar){
		var buttons=[];
		if(conf.doctype=='doc'){
            buttons.push({
		               text : '保留修改痕迹',
							iconCls : 'btn-archive-save-trace',
							handler : function() {
								if(isFileOpen){
									officeObj.ActiveDocument.Application.UserName=curUserInfo.fullname;
									officeObj.ActiveDocument.TrackRevisions=true;
							    }
						}
            });
            buttons.push('-');
		}
		if(conf.doctype=='doc'){
		   buttons.push({
						text : '取消保留痕迹',
						iconCls : 'btn-archive-cancel-trace',
						handler : function() {
							if(isFileOpen){
								officeObj.ActiveDocument.TrackRevisions=false;
							}
						}
					});
			buttons.push('-');
		}
		if(conf.doctype=='doc'){
		   buttons.push({
			   	    text : '清除痕迹',
					iconCls : 'btn-archive-eraser',
					handler : function() {
						if(isFileOpen){
							officeObj.ActiveDocument.AcceptAllRevisions();
						}
					}
			   });
			buttons.push('-');
		}
		
		if(conf.doctype=='doc'){
			buttons.push({
		            text:'模板套红',
					iconCls:'',
					scope:this,
					handler:function(){
						if(isFileOpen){
							new PaintTemplateSelector({callback:function(name,path){
							    this.close();
							    if(path!=''){
							    	var headFileURL=__ctxPath+'/attachFiles/'+path;
								    if(officeObj.doctype!=1){return;}//OFFICE_CONTROL_OBJ.doctype=1为word文档
									try{
										//选择对象当前文档的所有内容
										var curSel = officeObj.ActiveDocument.Application.Selection;
										curSel.WholeStory();
										curSel.Cut();
										//插入模板
										officeObj.AddTemplateFromURL(headFileURL);
										var BookMarkName = "content";
										if(!officeObj.ActiveDocument.BookMarks.Exists(BookMarkName)){
											alert("Word 模板中不存在名称为：\""+BookMarkName+"\"的书签！");
											return;
										}
										var bkmkObj = officeObj.ActiveDocument.BookMarks(BookMarkName);	
										var saverange = bkmkObj.Range
										saverange.Paste();
										officeObj.ActiveDocument.Bookmarks.Add(BookMarkName,saverange);		
										
									}catch(err){
										alert("错误：" + err.number + ":" + err.description);
									}
							    }
							}}).show();
						}
					}
			});
			buttons.push('-');
		}
		
		if(fileId==null || fileId==''){
			buttons.push({
				text:'选择Office模板',
				scope:this,
				handler:function(){
					new PaintTemplateSelector({callback:function(name,path){
					    this.close();
					    if(path!=''){
					    	var headFileURL=__ctxPath+'/attachFiles/'+path;
						    if(officeObj.doctype!=1){return;}//OFFICE_CONTROL_OBJ.doctype=1为word文档
							try
							{
							   officeObj.ActiveDocument.Application.Selection.HomeKey(6,0);//光标移动到文档开头
							   officeObj.OpenFromURL(headFileURL);//在光标位置插入红头文档
							}catch(err){
								alert('err:'+err);
							}
					    }
					}}).show();
				}
			});
		}
		
		if(conf.doctype=='doc'||conf.doctype=='xls'){
		   buttons.push({
		     		text:'手写签名',
					iconCls:'',
					scope:this,
					handler:function(){
						if(isFileOpen){
							try
							{
							   officeObj.DoHandSign2(
										"ntko",//手写签名用户名称
										"ntko",//signkey,DoCheckSign(检查印章函数)需要的验证密钥。
										0,//left
										0,//top
										1,//relative,设定签名位置的参照对象.0：表示按照屏幕位置插入，此时，Left,Top属性不起作用。1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落（为兼容以前版本默认方式）
										100);
							}catch(err){
							}
						}
					}
		   });
			buttons.push('-');
		}
		
		if(conf.doctype=='doc'||conf.doctype=='xls'){
			//import sealSelector.js
			//
			
			buttons.push({
			        text:'盖章',
					iconCls:'',
					scope:this,
					handler:function(){
							new SealSelector({callback:function(name,path,belongName){
								this.close();
								if(path!=''){
									var signUrl=__ctxPath+'/attachFiles/'+path;
									if(officeObj.doctype==1||officeObj.doctype==2)
									{
										try
										{
											officeObj.AddSecSignFromURL(curUserInfo.fullname,//印章的用户名
											signUrl,//印章所在服务器相对url
											0,//left
											0,//top
											1,//relative
											2,  //print mode 2
											false,//是是否使用证书，true或者false，
											1);
										}catch(error){
										
										}
									}
								}
							}}).show();
					}
				});
				buttons.push('-');
		}
		
		
		panelConf.tbar=new Ext.Toolbar({
			items:buttons
		});
	}
	
	Ext.applyIf(panelConf,conf);

	var panel=new Ext.Panel(panelConf);
	panel.on('afterrender',function(){
			panel.body.appendChild(officeObj);
			//若使用了模板，则缺省打开模板
			if(fileId){
				officeObj.OpenFromURL(__ctxPath+'/file-download?fileId='+fileId);
				isFileOpen=true;
			}else if(usetemplate==1 && deftemplatekey!=''){
				Ext.Ajax.request({
					url:__ctxPath+'/document/getByKeyPaintTemplate.do',
					params:{
						method:'post',
						templateKey:deftemplatekey
					},
					success:function(response,options){
						var result=Ext.decode(response.responseText);
						if(result.data){
							var templateFileURL=__ctxPath+'/attachFiles/'+result.data.path;
							officeObj.OpenFromURL(templateFileURL);
							isFileOpen=true;
						}else{
							officeObj.CreateNew(fileType);
							isFileOpen=true;
						}
					}
				});
				
			}else if(tempPath){//直接按路径打开
				//var templateFileURL=__ctxPath+'/attachFiles/'+result.data.path;
				officeObj.OpenFromURL(tempPath);
			}else{
				var fileType='';
				switch (conf.doctype)
				{
					case 'doc':
						fileType = "Word.Document";
						fileTypeSimple = "wrod";
						break;
					case 'xls':
						fileType = "Excel.Sheet";
						fileTypeSimple="excel";
						break;
					case 'ppt':
						fileType = "PowerPoint.Show";
						fileTypeSimple = "ppt";
						break;
					case 4:
						fileType = "Visio.Drawing";
						break;
					case 5:
						fileType = "MSProject.Project";
						break;
					case 6:
						fileType = "WPS Doc";
						break;
					case 7:
						fileType = "Kingsoft Sheet";
						break;
					default :
						fileType = "Word.Document";
				}
				try{
					officeObj.CreateNew(fileType);
					isFileOpen=true;
				}catch(err){}
				
			}
			panel.doLayout();
	});
	
	//对外公共方法
	return {
		panel:panel,
		officeObj:officeObj,
		openDoc:function(inFileId){
			fileId=inFileId;
			//判断用户是否在使用IE浏览器 chencc
			if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
				try{//异常捕获，如果产生异常则客户端没有安装office在线编辑工具软件 add by chencc
					officeObj.OpenFromURL(__ctxPath+'/file-download?fileId='+fileId);
				}catch(err){
					Ext.MessageBox.alert('提示：','无法打开，请先下载安装：<a title="下载在线Ntko插件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downNtko()" target="_blank"><font color=blue>在线Ntko插件</font></a>',
					function(){
						downNtko();//下载office在线编辑工具软件
					});
				}
			}else{
				Ext.ux.Toast.msg('提示','您浏览器不支持此功能，请使用IE浏览器进行在线编辑/查看！');
			}
			
		},
		setReadOnly:function(){
		   officeObj.SetReadOnly(true,'');
		},
		openDoc2:function(fileId,fileUrl){
		    fileId=fileId;
		    try{
		    officeObj.OpenFromURL(__ctxPath+'/attachFiles/'+fileUrl);
		    isFileOpen=true;
			}catch(err){
				isFileOpen=false;
			}
		},
		/**
		 * return json result is format as below:
		 * {sucess:false} or 
		 * {success:true,fileId:73,fileName:'myDoc.doc',filePath:'others/2010/aaa0393304.doc',message:'upload file success(10229 bytes)'}
		 */
		saveDoc:function(config){
			return saveFn(config);
		},
		closeDoc:function(){
			isFileOpen=false;
			officeObj.Close();
		}
	};
};
