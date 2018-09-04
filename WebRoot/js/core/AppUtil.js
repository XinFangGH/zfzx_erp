Ext.ns("App");
Ext.ns("AppUtil");
//用于标记已经加载过的js库
var jsCache=new Array();

/*
 * XML字符串转化为Dom对象
 */
function strToDom(xmlData) {
	if (window.ActiveXObject) {
		//for IE
		var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async="false";
		xmlDoc.loadXML(xmlData);
		return xmlDoc;
	} else if (document.implementation && document.implementation.createDocument) {
		//for Mozila
		var parser=new DOMParser();
		var xmlDoc=parser.parseFromString(xmlData,"text/xml");
		return xmlDoc;
	}
}

 
function newView(viewName,params){
	var str='new ' + viewName ;
	if(params!=null){
		str+='(params);';
	}else{
		str+='();';
	}
	return eval(str);
}

/**
 * Import Js
 * @return {}
 */
function $ImportJs(viewName,callback,params) {
	var b = jsCache[viewName];
	if (b != null) {
		var view=newView(viewName,params);
		var tabs = Ext.getCmp('centerTabPanel');
		//解决提示【请选择任务记录!】开始
		var activeTab=tabs.getActiveTab();//获得当前活动的tab
		if(activeTab && activeTab.id===view.id){//相等则说明该功能已经打开
			tabs.remove(activeTab);
		}
		//结束
		callback.call(this, view);
	} else {
		var jsArr = eval('App.importJs.' + viewName);
		if(jsArr==undefined || jsArr.length==0){
			try{
				var view = newView(viewName,params);
				callback.call(this, view);
			}catch(e){
				//alert( '请检查对应访问的js是否存在?');
			}
			return ;
		}else{
			ScriptMgr.load({
					scripts : jsArr,
					callback : function() {
						jsCache[viewName]=0;
						var view = newView(viewName,params);
						callback.call(this, view);
					}
			});
		}
		
	}
}
/**
 * 加载的js,并调用回调函数
 * @param {} jsArr
 * @param {} callback
 */
function $ImportSimpleJs(jsArr,callback,scope){
	ScriptMgr.load({
					scripts : jsArr,
					scope:scope,
					callback : function() {
						var sp=scope?scope:this;
						if(callback){
						callback.call(sp);
						}
					}
	});
}


 /*将String类型解析为Date类型.   
   parseDate('2006-1-1') return new Date(2006,0,1)   
   parseDate(' 2006-1-1 ') return new Date(2006,0,1)   
   parseDate('2006-1-1 15:14:16') return new Date(2006,0,1,15,14,16)   
   parseDate(' 2006-1-1 15:14:16 ') return new Date(2006,0,1,15,14,16);   
   parseDate('2006-1-1 15:14:16.254') return new Date(2006,0,1,15,14,16,254)   
   parseDate(' 2006-1-1 15:14:16.254 ') return new Date(2006,0,1,15,14,16,254)   
   parseDate('不正确的格式') retrun null   
 */    
 function $parseDate(str){     
   if(typeof str == 'string'){     
     var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);     
     if(results && results.length>3)     
       return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]));      
     results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);     
     if(results && results.length>6)     
       return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]));      
     results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);     
     if(results && results.length>7)     
       return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]),parseInt(results[7]));      
   }     
   return null;     
 }     
     
 /*   
   将Date/String类型,解析为String类型.   
   传入String类型,则先解析为Date类型   
   不正确的Date,返回 ''   
   如果时间部分为0,则忽略,只返回日期部分.   
 */    
 function $formatDate(v){     
   if(typeof v == 'string') v = parseDate(v);     
   if(v instanceof Date){     
     var y = v.getFullYear();     
     var m = v.getMonth() + 1;     
     var d = v.getDate();     
     var h = v.getHours();     
     var i = v.getMinutes();     
     var s = v.getSeconds();     
     var ms = v.getMilliseconds();  
     
     if(m<10) m='0'+m;
     if(d<10) d='0'+d;
     if(h<10) h='0'+h;
     if(i<10) i='0'+i;
     if(s<10) s='0'+s;
           
     if(ms>0) return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s + '.' + ms;     
     if(h>0 || i>0 || s>0) return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;     
     return y + '-' + m + '-' + d;     
   }     
   return '';     
 }
 
 /**
  * 把Html中的table对象转成
  * @param {} table
  */
 function $convertTableToMap(table){
 		if(table.rows.length!=2){
 			return [];
 		}
		var maps=[];
		var row1=table.rows[0];
		var row2=table.rows[1];
		
		for(var i=0;i<row1.cells.length;i++){
			var map= {} ;
			var cell=row2.cells[i];
			var control;
			for(var j=0;j<cell.childNodes.length;j++){
				if(cell.childNodes[j].getAttribute&&cell.childNodes[j].getAttribute('name')){
					control=cell.childNodes[j];
					break;
				}
			}
			
			var name=control.getAttribute('name');
			var header=row1.cells[i].innerHTML;
			var xtype=control.getAttribute('xtype');
			var format=control.getAttribute('dateformat');
			var itemsName=control.getAttribute('txtitemname');
			var isnotnull=control.getAttribute('txtisnotnull');
			var issingle=control.getAttribute('issingle');
			
			map.name=name;
			map.header=header;
			map.xtype=xtype;
			if(format){
			    map.format=format;
			}
			if(itemsName){
			    map.itemsName=itemsName;
			}
			if(issingle){
			   map.issingle=issingle;
			}
			map.isnotnull=isnotnull;
			maps.push(map);
		}
		return maps;
 }
/**
 * 取table里所有的输入框txtname
 * @param {} table
 * @return {}
 */
  function $getTableInputCmpName(table){
		var maps=[];
		for(var i=0;i<table.rows.length;i++){
			var row=table.rows[i];
		    for(var j=0;j<row.cells.length;j++){
		       var cell=row.cells[j];
		       var control;
		       for(var k=0;k<cell.childNodes.length;k++){
				if(cell.childNodes[k].getAttribute&&cell.childNodes[k].getAttribute('name')){
					control=cell.childNodes[k];
					if(control){
					   var name=control.getAttribute('name');
					   maps.push(name);
				    }
				}
			   }
			   
		    }
		}
		return maps;
 }
 
/**
 * 取得中间的内容面板
 * @return {}
 */
App.getContentPanel=function(){
	var tabs = Ext.getCmp('centerTabPanel');
	return tabs;
};

///**
// * 创建上传的对话框
// * @param {} config
// * @return {}
// */
//App.createUploadDialog=function(config){
//	var defaultConfig={
//		file_cat:'others',
//		url:__ctxPath+'/file-upload',
//		reset_on_hide: false,
//		upload_autostart:false,
//		modal : true
//	};
//	Ext.apply(defaultConfig,config);			
//	var	dialog = new Ext.ux.UploadDialog.Dialog(defaultConfig);
//	return dialog;
//};

/**
 * 创建上传的对话框
 * @param {} config
 * @return {}
 */
App.createUploadDialog=function(config){
	var defaultConfig={
		file_cat:'others',
		url:__ctxPath+'/file-upload',
		reset_on_hide: false,
		upload_autostart:false,
		modal : true
	};
	Ext.apply(defaultConfig,config);
	
//	var	dialog = new Ext.ux.UploadDialog.Dialog(defaultConfig);
	var dialog = new FileUploadManager(defaultConfig);
	return dialog;
};

App.createUploadDialog2 = function(config){
	var defaultConfig = {
		file_cat : 'others',
		url : __ctxPath + '/file-upload',
		reset_on_hide : false,
		upload_autostart : false,
		modal : true
	};
	Ext.apply(defaultConfig,config);	
	var	dialog = new Ext.ux.UploadDialog.Dialog(defaultConfig);
	return dialog;
};

/**
 * 把数组中的重复元素去掉
 * @param {} data 传入一个数组
 * @return {}　返回一个元素唯一的数组
 */
function uniqueArray(data) {
	data = data || [];
	var a = {};
	for (var i = 0; i < data.length; i++) {
		var v = data[i];
		if (typeof(a[v]) == 'undefined') {
			a[v] = 1;
		}
	};
	data.length = 0;
	for (var i in a) {
		data[data.length] = i;
	}
	return data;
};

/* This function is used to set cookies */
function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

/*This function is used to get cookies */
function getCookie(name) {
	var prefix = name + "=" ;
	var start = document.cookie.indexOf(prefix); 
	
	if (start==-1) {
		return null;
	}
	
	var end = document.cookie.indexOf(";", start+prefix.length);
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end) ;
	return unescape(value);
}

/* This function is used to delete cookies */
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}

String.prototype.trim = function() {
	return(this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
};

/**
 * 封装请求
 * @param {} config
 */
function $request(config){
		Ext.Ajax.request({
			url:config.url,
			params:config.params,
			method:config.method==null?'POST':config.method,
			success:function(response,options){
				if(config.success!=null){
					config.success.call(this,response,options);
				}
			},
			failure:function(response,options){
				Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
				if(config.success!=null){
					config.failure.call(this,response,options);
				}
			}
		});
}

function asynReq(){
	var conn = Ext.Ajax.getConnectionObject().conn;
	conn.open("GET", url,false);
	conn.send(null); 
}

/**
 * 为GridPanel添加打印及导出功能
 * @param {} grid GridPanel
 */
AppUtil.addPrintExport=function(grid){
	/*
	var exportButton = new Ext.ux.Exporter.Button({
          component: grid,
          iconCls: 'btn-excel',
          text     : '导出'
        });
        
        grid.getTopToolbar().add('->');
       	grid.getTopToolbar().add(exportButton);
        
		grid.getTopToolbar().add(
				new Ext.Button({
					text:'打印',
					iconCls:'btn-print',
					handler:function(){
						Ext.ux.Printer.print(grid);
					}
				})
		);*/
		
};
/*
 * 删除Content中的Tab
 */
AppUtil.removeTab=function(tabId){
	var contentPanel=App.getContentPanel();
	var tabItem = contentPanel.getItem(tabId);
	if(tabItem!=null){
		contentPanel.remove(tabItem,true);
	}
};
/**
 * 激活Content中的Tab
 * @param {} panel
 */
AppUtil.activateTab=function(panel){
	var contentPanel=App.getContentPanel();
	contentPanel.activate(panel);
};

/**
 * 替换组件
 * @param {} fElements 
 * @param {} jsonData
 * @param {} rightJson
 * @param    addFlag 是否为后来加上的
 * @return {}
 */
function $converCmp(fElements,jsonData,rightJson,addFlag,readOnly){
		
	    var flag='on';
	    var formPanel=this.formPanel;
        var map =new Ext.util.MixedCollection();
		var arrays=new Array();
		var removeArray=[];
		
		Ext.each(fElements, function(element,index) {
			var name,type,value,xtype,height;
			var right=null;
	        if(!element){
	            return;
	        }
	        name = element.name ? element.name:'control'+index;
	        type = element.type;
	       
	        if(rightJson && rightJson[name]){
				right=rightJson[name];
			}
			
	        if(type=='button' || type=='hidden'){
	            return;
	        }
	        if(element.style.display=='none'){
	        	return;
	        }
	        
			xtype=element.getAttribute('xtype');
			if(!name){
				name=name.replace(/(^\s*)|(\s*$)/g, "");//去掉两边的空格
			}
			if(jsonData && jsonData[name]){
				if(type=='radio'||type=='checkbox'){
					var value=jsonData[name];
	                if(element.value){
	                	var str1=element.value+'|',str2=value+'|';
	                	var isContain=str2.indexOf(str1)>-1;
		                if(element.value==value||isContain){
		                   element.checked=true;
		                }else{
		                   element.checked=false;
		                }
	                }
	                return;
				}
				element.value=jsonData[name];
			}
			
			var parent=element.parentNode;
			var width=element.getAttribute('width');
			var isNotNull=element.getAttribute('txtisnotnull');
			if(!width){
			  width=parent.offsetWidth;
			}
			if(width<300&&parent.offsetWidth>300){
			   width=300;
			}
			
			if(readOnly||(right&&(right==0||right==1)&&(xtype!='officeeditor'||(xtype=='officeeditor'&&right==0)))){
				flag='un';
				//element.setAttribute('style','display:none;');
				element.style.display='none';
				var p=document.createElement("span");
				p.setAttribute('style','width:'+width+'px;');
				var html='';
				if(right==0&&!readOnly){
				   html='<font color="red">无权限</font>';
				}else{
				   if(element.value){
						  if(xtype=='fileattach'){
						   	     var files=element.value.split(',');
						   	     var html='';
						   	     for(var i=0;i<files.length;i++){
							   	     var vo=files[i].split("|");
							   	     var id=vo[0];
							   	     var name=vo[1];
							   	     if(isNaN(vo[0])){
							   	       html+='<a href="'+__ctxPath+'/file-download?fileId='+vo[0]+'">'+vo[1]+', </a>';
							   	     }
						   	     }
						   	     if(html==''){
						   	       html=element.value;
						   	     }
						   	  }else{
						         html=element.value;
						   	  }
				   }else{
				      html='';
				   }
				}
				p.innerHTML=html;
				parent.appendChild(p);
				return;
			}
			if(xtype=='datefield'){
					var format=element.getAttribute('dataformat');
					var txtistoday=element.getAttribute('txtistoday');
					var span=document.createElement('div');
					var obj={parentNode:parent,oldEl:element,newEl:span};
					removeArray.push(obj);
					var div=document.createElement('div');
					div.setAttribute('style','width:'+width+'px');
					span.appendChild(div);
				try{
					var cmp;
					
					if(format=='yyyy-MM-dd HH:mm:ss'){
					    cmp=new Cls.form.DateTimeField({
								name:name,
								width : 200,
								autoWidth:false,
								boxMaxWidth:200,
								format : 'Y-m-d H:i:s',
								value:txtistoday==1?new Date():'',
								allowBlank:isNotNull==1?false:true
							});
					}else{
						cmp=new Ext.form.DateField({
								name:name,
								height:21,
								width : 100,
								boxMaxWidth:100,
								autoWidth:false,
								format:'Y-m-d',
								value:txtistoday==1?new Date():'',
								allowBlank:isNotNull==1?false:true
							});
					}
					arrays.push('datefield'+index);
				    map.add('datefield'+index,span);
					map.add('datefield'+index+'-cmp',cmp);
					if(element.value){
						cmp.setValue(element.value);
					}
				}catch(e){
					
				}
			}else if(xtype=='diccombo'){
				try{
				var itemname=element.getAttribute('txtitemname');
				var span=document.createElement('span');
				var obj={parentNode:parent,oldEl:element,newEl:span};
				removeArray.push(obj);
				var div=document.createElement('div');
				span.appendChild(div);
				var cmp=new DicCombo({
					        name:name,
							itemName:itemname,
							displayField : 'itemName',
			                valueField : 'itemName',
							//renderTo:div,
							width : width,
							allowBlank:isNotNull==1?false:true
						});
				arrays.push('diccombo'+index);
			    map.add('diccombo'+index,span);
				map.add('diccombo'+index+'-cmp',cmp);
				if(element.value){
					cmp.setValue(element.value);
				}
				}catch(e){
				}
			}else if(xtype=='fckeditor'){
				height=parent.offsetHeight;
				var span=document.createElement('div');
				var obj={parentNode:parent,oldEl:element,newEl:span};
				removeArray.push(obj);
				var div=document.createElement('div');
				span.appendChild(div);
				var cmp=new Ext.ux.form.FCKeditor({
						name:name,
						height:height,
						allowBlank:false
				});
				arrays.push('fckeditor'+index);
			    map.add('fckeditor'+index,span);
				map.add('fckeditor'+index+'-cmp',cmp);
				if(element.value){
					cmp.setValue(element.value);
				}
			}else if(xtype=='officeeditor'){
			    try{
				    var span=document.createElement('div');
				    height=parent.offsetHeight;
					this.hiddenF=new Ext.form.Hidden({
					    name:name
					});
					this.hiddenF.render(span);
					var obj={parentNode:parent,oldEl:element,newEl:span};
					removeArray.push(obj);
					
					var usetemplate=element.getAttribute('usetemplate');
					var deftemplatekey=element.getAttribute('deftemplatekey');

					Ext.useShims=true;
					var cmp={
						  isOfficePanel:true,
						  right:right,
					      showToolbar:right==1?false:true,
	     	              width:width,
	     	              height:height,
	     	              fileId:element.value,
	     	              doctype:'doc',
	     	              usetemplate:usetemplate,
	     	              deftemplatekey:deftemplatekey,
	     	              unshowMenuBar:false};

	     	        arrays.push('officeeditor'+index);
				    map.add('officeeditor'+index,span);
					map.add('officeeditor'+index+'-cmp',cmp);
					if(element.value){
						this.hiddenF.setValue(element.value);
						 this.fileId=element.value;
					}

			    }catch(e){
			    //alert(e);
			    }
			}else if(xtype=='userselector'){
				try{
				var span=document.createElement('div');
				var hiddenF=new Ext.form.Hidden({
					value:curUserInfo.userId,
				    name:name+'ids'
				});
				hiddenF.render(span);
				var obj={parentNode:parent,oldEl:element,newEl:span};
				removeArray.push(obj);
				var isSingle=element.getAttribute('issingle');
				var txtf=new Ext.form.TextField({
				       name:name,
				       height:21,
				       readOnly:true,
				       value:curUserInfo.fullname,
				       allowBlank:isNotNull==1?false:true,
				       width:width?(width-90>0?width-90:width):width
				});
				
				if(isSingle==0){
				   txtf=new Ext.form.TextArea({
				       name:name,
				       readOnly:true,
				       allowBlank:isNotNull==1?false:true,
				       value:curUserInfo.fullname,
				       width:width?(width-90>0?width-90:width):width
				   });
				}
				
			    var cmp=new Ext.form.CompositeField({
			    	   width:width,
			           items:[txtf,{
			               xtype:'button',
			               width:78,
			               border:false,
			               text:'选择人员',
			               iconCls:'btn-sel',
			               handler:function(){
			                   UserSelector.getView(function(ids,names){
			                       txtf.setValue(names);
			                   },isSingle==1?true:false).show();
			               }
			           
			           }]
			    });
			    arrays.push('userselector'+index);
			    map.add('userselector'+index,span);
				map.add('userselector'+index+'-cmp',cmp);
				if(element.value){
					txtf.setValue(element.value);
				}
				}catch(e){}
			}else if(xtype=='depselector'){
				try{
				var span=document.createElement('div');
				var hiddenF=new Ext.form.Hidden({
					value:curUserInfo.depId,
				    name:name+'ids'
				});
				hiddenF.render(span);
				var obj={parentNode:parent,oldEl:element,newEl:span};
				removeArray.push(obj);
				var isSingle=element.getAttribute('issingle');
				var txtf=new Ext.form.TextField({
				       name:name,
				       readOnly:true,
				       value:curUserInfo.depName,
				       height:21,
				       allowBlank:isNotNull==1?false:true,
				       width:width?(width-90>0?width-90:width):width
				});
				if(isSingle==0){
				   txtf=new Ext.form.TextArea({
				       name:name,
				       readOnly:true,
				       value:curUserInfo.depName,
				       allowBlank:isNotNull==1?false:true,
				       width:width?(width-90>0?width-90:width):width
				   });
				}
			    var cmp=new Ext.form.CompositeField({
			    	   width:width,
			           items:[txtf,{
			               xtype:'button',
			               border:false,
			               width:78,
			               text:'选择部门',
			               iconCls:'btn-users',
			               handler:function(){
			                   DepSelector.getView(function(ids,names){
			                       txtf.setValue(names);
			                   },isSingle==1?true:false).show();
			               }
			           
			           }]
			    });
			    arrays.push('depselector'+index);
			    map.add('depselector'+index,span);
				map.add('depselector'+index+'-cmp',cmp);
				if(element.value){
					txtf.setValue(element.value);
				}
				}catch(e){}
			}else if(xtype=='fileattach'){
				try{
				var span=document.createElement("div");
				var hiddenF=new Ext.form.Hidden({
				    name:name
				});
				hiddenF.render(span);
				var obj={parentNode:parent,oldEl:element,newEl:span};
				removeArray.push(obj);
				var txtf=new Ext.Panel({
					width:width?(width-90>0?width-90:width):width,
					height:60,
					autoScroll:true,
					html:''
				});
				
					
			    var cmp=new Ext.form.CompositeField({
//			    	   renderTo:span,
			    	   width:width,
			           items:[txtf,{
			               xtype:'button',
			               width:78,
			               text:'选择附件',
			               iconCls:'menu-attachment',
			               handler:function(){
               	               var dialog = App.createUploadDialog({
									file_cat : 'flow',
									callback : function(data) {
										for (var i = 0; i < data.length; i++) {
											if (hiddenF.getValue() != '') {
												hiddenF
														.setValue(hiddenF
																.getValue()
																+ ',');
											}
											hiddenF.setValue(hiddenF
													.getValue()
													+ data[i].fileId+'|'+data[i].fileName);
											Ext.DomHelper
													.append(
															txtf.body,
															'<span><a href="#" onclick="FileAttachDetail.show('
																	+ data[i].fileId
																	+ ')">'
																	+ data[i].fileName
																	+ '</a> <img class="img-delete" src="'
																	+ __ctxPath
																	+ '/images/system/delete.gif" onclick="AppUtil.removeFile(this,'
																	+ data[i].fileId
																	+ ')"/>&nbsp;|&nbsp;</span>');
										}
									}
								});
								dialog.show(this);
			               }
			           
			           }]
			    });
			    arrays.push('fileattach'+index);
			    map.add('fileattach'+index,span);
				map.add('fileattach'+index+'-cmp',cmp);
				AppUtil.removeFile=function(obj, fileId,fileName) {
					var fileIds = hiddenF;
					var value = fileIds.getValue();
					if (value.indexOf(',') < 0) {// 仅有一个附件
						fileIds.setValue('');
					} else {
						value = value.replace(',' + fileId+'|'+fileName, '').replace(fileId+'|'+fileName + ',', '');
						fileIds.setValue(value);
					}
					var el = Ext.get(obj.parentNode);
					el.remove();
				};
				cmp.on('render',function(){
				   
				   if(element.value){
						hiddenF.setValue(element.value);
						var filea=element.value.split(',');
						for(var i=0;i<filea.length;i++){
						    var ss=filea[i];
						    var as=ss.split('|');
						    var fileId=as[0];
						    var fileName=as[1];
						    
						    Ext.DomHelper
							.append(
									txtf.body,
									'<span><a href="#" onclick="FileAttachDetail.show('
											+ fileId
											+ ')">'
											+ fileName
											+ '</a> <img class="img-delete" src="'
											+ __ctxPath
											+ '/images/system/delete.gif" onclick="AppUtil.removeFile(this,'
											+ fileId+',\''+fileName
											+ '\')"/>&nbsp;|&nbsp;</span>');
						}
	//					txtf.setValue(element.value);
					}
					
					
				},this);
			   }catch(e){}
				
			}
			
			
		   element.onblur=function(){
		   	  $validField.call(this,element);
		   };
			
		 },this);
		 
		for(var g=0;g<removeArray.length;g++){
		    var obj=removeArray[g];
		    try{
		       obj.parentNode.replaceChild(obj.newEl,obj.oldEl);
		    }catch(e){}
		}
	    if(arrays.length>0&&map.length>0){
	    	Ext.each(arrays,function(its,index){
	    	    var cmp=map.get(its+'-cmp');
				var span=map.get(its);
				if(cmp.isOfficePanel){
				   var o=new NtkOfficePanel(cmp);
				   if(cmp.right==1){
				      o.setReadOnly();
				   }
				   o.panel.render(span);
				   this.officePanel=o;
				}else{
		    	    try{
			            var divs=document.createElement('div');
			            var div22=document.createElement('div');
			            divs.appendChild(div22);
			            cmp.render(div22);
			            span.appendChild(divs);
			            if(addFlag!=true){
	                        formPanel.add(cmp);		            	
			            }
		    	    }catch(e){
		    	    }
				}
	    	    
	    	},this);
		}
		
		return flag;

};

function $converDetail(jsonData,rightJson){
		//回填数据
		var form=this.formPanel.getForm().getEl().dom;
		var tables=form.getElementsByTagName('table');
		this.detailGrids=new Ext.util.MixedCollection();
		
		var formobjs=[];
		var removeTables=[];
		
		this.formValidCmp=new Array();
		
		//this.detailForms=new Ext.util.MixedCollection();
		for(var i=0;i<tables.length;i++){
			var isdetail=tables[i].getAttribute('isdetail');
			var isgrid=tables[i].getAttribute('isgrid');
			var gridName=tables[i].getAttribute('txtname');
			if(isdetail!=null&&'true'==isgrid){
				var parent=tables[i].parentNode;
				var map=$convertTableToMap(tables[i]);
				var fields=[];
				var columns=[];
				if(this.taskId){
					var subTableId=document.getElementById('WF_'+gridName+'_'+this.taskId);
					var subTableIdVal=subTableId.value;
					fields.push(subTableIdVal);
					columns.push({dataIndex:subTableIdVal,header:subTableIdVal,hidden:true});
				}
				var flag=0;
				
				for(var f=0;f<map.length;f++){
					var right=null;
					if(rightJson && rightJson[map[f].name]){
						right=rightJson[map[f].name];
					}
					
					if(right!=0){
						if(map[f].xtype!='datefield'){
						    fields.push(map[f].name);
						}
						if(right==1){
							flag--;
							if(map[f].xtype=='datefield'){
							   fields.push({type:'date',name:map[f].name,dateFormat:map[f].format=='yyyy-MM-dd'?'Y-m-d':'Y-m-d H:i:s'});
							   columns.push({
									dataIndex:map[f].name,
									header:map[f].header,
									xtype : 'datecolumn',
							        format : map[f].format
							   });
							}else{
							    columns.push({
									dataIndex:map[f].name,
									header:map[f].header
								});
							}
						}else{
							flag++;
							
							var editor=new Ext.form.TextField();
							if(map[f].xtype=='datefield'){
								if(map[f].format=='yyyy-MM-dd'){
									fields.push({type:'date',name:map[f].name,dateFormat:'Y-m-d'});
								    editor=new Ext.form.DateField({
										format : 'Y-m-d',
										value:new Date(),
										allowBlank:false
									});
									columns.push({
										dataIndex:map[f].name,
										header:map[f].header,//TODO
										xtype : 'datecolumn',
							            format : 'Y-m-d',
										editor:editor
									});
								}else{
									fields.push({type:'date',name:map[f].name,dateFormat:'Y-m-d H:i:s'});
								    editor=new Cls.form.DateTimeField({
										format : 'Y-m-d H:i:s',
										value:new Date(),
										allowBlank:false
									});
									columns.push({
										dataIndex:map[f].name,
										header:map[f].header,//TODO
										xtype : 'datecolumn',
							            format : 'Y-m-d H:i:s',
										editor:editor
									});
								
								}
								
							}else if(map[f].xtype=='diccombo'){
									columns.push({
										dataIndex:map[f].name,
										header:map[f].header,//TODO
										editor:new DicCombo({
											itemName:map[f].itemsName,
											isDisplayItemName:true
										})
									});
							}else if(map[f].xtype=='userselector'){
								    var isSingle=map[f].issingle;
								    var isingle=true;
								    if(isSingle==1){
								      isingle=false; 
								    }
								    var sel=this;
								    var name=map[f].name;
//								    var grid=this.detailGrids.get(cmp.gridName);
								   //用户的行选择器
								   var userEditor = new Ext.form.TriggerField({
										triggerClass : 'x-form-browse-trigger',
										gridName:gridName,
										isSingle:isSingle,
										dataIndexName:name,
										onTriggerClick : function(e) {
											var grid=sel.detailGrids.get(this.gridName);
											var modifyName=this.dataIndexName;
											UserSelector.getView(function(ids, names) {
												var store = grid.getStore();
												var record = store.getAt(grid.clickRow);
												record.set(modifyName, names);
											},this.isingle).show();
											grid.stopEditing();
										}
								   });
								   columns.push({
										dataIndex:map[f].name,
										header:map[f].header,//TODO
										editor:userEditor
									}); 
							}else if(map[f].xtype=='depselector'){
								    var isSingle=map[f].issingle;
								    var isingle=true;
								    if(isSingle==1){
								      isingle=false; 
								    }
								    var sel=this;
								    var name=map[f].name;
//								    var grid=this.detailGrids.get(cmp.gridName);
								   //部门选择器
								   var depEditor = new Ext.form.TriggerField({
										triggerClass : 'x-form-browse-trigger',
										gridName:gridName,
										isSingle:isSingle,
										dataIndexName:name,
										onTriggerClick : function(e) {
											var grid=sel.detailGrids.get(this.gridName);
											var modifyName=this.dataIndexName;
											DepSelector.getView(function(ids, names) {
												var store = grid.getStore();
												var record = store.getAt(grid.clickRow);
												record.set(modifyName, names);
											},this.isingle).show();
											grid.stopEditing();
										}
								   });
								   columns.push({
										dataIndex:map[f].name,
										header:map[f].header,//TODO
										editor:depEditor
									}); 
							}else{
								columns.push({
									dataIndex:map[f].name,
									header:map[f].header,//TODO
									editor:editor
								});
							}
						}
					}else{flag--; }
				}
				
				var div=document.createElement('div');
				parent.appendChild(div);
				//加上明细的表格
				var detailPanel= new HT.EditorGridPanel({
					renderTo:div,
					tbar:new Ext.Toolbar({
						hidden:map.length==flag?false:true,
						frame: true,
						items:[{
								text:'添加记录',
								iconCls:'btn-add',
								scope:this,
								gridName:gridName,
								handler:function(cmp){
									var detailPanel=this.detailGrids.get(cmp.gridName);
									var recordType=detailPanel.getStore().recordType;
									detailPanel.getStore().add(new recordType());
								}
							},{
								text:'删除记录',
								iconCls:'btn-del',
								scope:this,
								gridName:gridName,
								handler:function(cmp){
									var detailPanel=this.detailGrids.get(cmp.gridName);
									var gridName=cmp.gridName;
									var taskId=this.taskId;
									Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
											if (btn == 'yes') {
												var tableId=null;
												if(taskId){
													tableId=document.getElementById(gridName+'_'+taskId).value;
												}
												var store=detailPanel.getStore();
												var selRs =detailPanel.getSelectionModel().getSelections();
												var ids=[];
												var delRecords=[];
												var pkKey=document.getElementById('WF_'+gridName+'_'+taskId);
												var pkKeyVar;
												if(pkKey){
												    pkKeyVar=pkKey.value;
												}
												for(var i=0;i<selRs.length;i++){
													if(selRs[i].data!=null){
														if(pkKeyVar){
															var detailId=selRs[i].data[pkKeyVar];
															if(detailId){
															   ids.push(detailId);
															}
														}
														delRecords.push(selRs[i]);
													}
												}
												if(ids.length){
													Ext.Ajax.request({
														url :__ctxPath+'/flow/delItemsProcessActivity.do',
														params : {tableId:tableId,ids : ids},method : 'POST',
														success : function(response,options) {
															Ext.ux.Toast.msg('操作信息','成功删除该记录！');
	//														for(var j=0;j<delRecords.length;j++){
																store.remove(delRecords);
	//														}
															
														},
														failure : function(response,options) {
															Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
														}
													});
												}else{
												   store.remove(delRecords);
												}
											}
									});
									
								}
							}
						]
					}),
					clicksToEdit:1,
					width:tables[i].offsetWidth,
					showPaging:false,
					autoHeight:true,
					fields : fields,
					columns : columns,
					listeners:{
					    'rowclick':function(grid,index,e){
					        this.clickRow=index;
					    }
					}
				});
				this.detailGrids.add(gridName,detailPanel);
				//加载其列表
	            if(jsonData){
					var data=jsonData['WF_'+gridName+'s'];
					if(data){
						detailPanel.getStore().loadData({result:data});
					}
				}
				removeTables.push(tables[i]);
			}else if(isdetail!=null&&'false'==isgrid){
				/**
				 * 将子表单放到一个div里，点击添加明细表单时，则在这个div里面添加。
				 */
			  try{
				var names=$getTableInputCmpName(tables[i]);
				//取得该子表单的数据
				var datas;
				var pkName;
				if(this.taskId){
					var pkKey=document.getElementById('WF_'+gridName+'_'+this.taskId);
					var pkKeyVar=pkKey.value;
					if(jsonData){
					   datas=jsonData['WF_'+gridName+'s'];
					   pkName=pkKeyVar;
					   if(false&&datas){
					      var obj=data[0];
					      var flag=false;
					      var pkKeyValue=obj[pkKeyVar];
					      for(var w=0;w<names.length;w++){
					      	 if(names[w]==pkKeyVar){
					      	    flag=true;
					      	 }
					         jsonData[names[w]]=obj[names[w]];
					      }
					   }
					}
				}
				var parent=tables[i].parentNode;
				var html=parent.innerHTML;
				var obj={
				   innerhtml:html,//对应的HTML
				   parentNode:parent,//父节点
				   gridName:gridName,//表名
				   elsName:names,//包括的控件名称
				   jsonDatas:datas,
				   pkName:pkName,
				   rightJson:rightJson
				};
				
				formobjs.push(obj);
				removeTables.push(tables[i]);
			 }catch(e){}
			 //**/
			}
			
		}
		/**
		 * 移动被替换的TABLE
		 * @type Number
		 */
		for(var i=0;i<removeTables.length;i++){
		    var table=removeTables[i];
		    var parent=table.parentNode;
		    parent.removeChild(table);
		}
		
		//转化控件及赋值	
		
		var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements;
		
		$converCmp.call(this,fElements,jsonData,rightJson);
		$converFormDetail.call(this,formobjs);
		
}

function $converForm(conf,readOnly){
	   var html=conf.innerhtml;
       var parent=conf.parentNode;
       var gridName=conf.gridName;
       var rightJson=conf.rightJson;
       
       var div_1=document.createElement('div');
       parent.appendChild(div_1);
       var datas=conf.jsonDatas;
       var pkName=conf.pkName;
       var flag=true;
       ///**
       if(datas&&pkName){
	       for(var i=0;i<datas.length;i++){
		       //method start
		       var div_1_1=document.createElement('div');
		       div_1.appendChild(div_1_1);
//		       div_1_1.setAttribute('style','background: #F8F8F8 no-repeat scroll left bottom transparent;border:0 none;');
			   div_1_1.setAttribute('class','tipDiv');
			   var form2=document.createElement('form');
			   form2.setAttribute('belongName',gridName);
			   form2.setAttribute('pkName',pkName);
			   form2.setAttribute('pkValue',datas[i][pkName]);
			   if(!readOnly){
			   	  div_1_1.appendChild($addDelButton(div_1,div_1_1,gridName,this.taskId,datas[i][pkName]));
			   }
			   div_1_1.appendChild(form2);
			   
			   var dd=document.createElement('div');
			   dd.innerHTML=html;
			   form2.appendChild(dd);
			   try{
			   var r=$converCmp.call(this,form2.elements,datas[i],rightJson,true,readOnly);
			   if(r=='un'){
			      flag=false;
			   }
			   }catch(e){}
			   //method end
	       }
       }else{
               var div_1_1=document.createElement('div');
		       div_1.appendChild(div_1_1);
			   var form2=document.createElement('form');
			   form2.setAttribute('belongName',gridName);
			   if(!readOnly){
			     div_1_1.appendChild($addDelButton(div_1,div_1_1,gridName,this.taskId,null));
			   }

			   var dd=document.createElement('div');
			   dd.innerHTML=html;
			   form2.appendChild(dd);
			   try{
				   var r=$converCmp.call(this,form2.elements,null,rightJson,true);
				   if(r=='un'){//权限处理的
				      flag=false;
				   }else{
				      div_1_1.appendChild(form2);
				   }
			   }catch(e){}
			   
			   
			  
       }
       //**/
       if(flag){//对从表有所写权限
		   var addButtonDiv=document.createElement('div');
		   div_1.appendChild(addButtonDiv);
		   var button=new Ext.Button({
		       renderTo:addButtonDiv,
		       text:'添加',
		       tableHtml:html,
		       gridName:gridName,
		       addButtonDiv:addButtonDiv,
		       parentNode:div_1,
	           iconCls:'btn-add',
	           scope:this,
	           handler:function(bt){
	           	   var div_2=document.createElement('div');
	           	   bt.parentNode.insertBefore(div_2,bt.addButtonDiv);
	           	   var div_2_2=document.createElement('div');
	               var form_2=document.createElement('form');
	               form_2.setAttribute('belongName',gridName);
	               div_2.appendChild(div_2_2);
			       
			       div_2_2.appendChild($addDelButton(div_2,div_2_2,gridName,null,null));
	               div_2_2.appendChild(form_2);
	               
	               var dd=document.createElement('div');
			       dd.innerHTML=html;
			       form_2.appendChild(dd);
				   $converCmp.call(this,form_2.elements,null,null,true);
	           }
		   });
       }
};

function $converFormDetail(formobjs,readOnly){
	   for(var i=0;i<formobjs.length;i++){
           $converForm.call(this,formobjs[i],readOnly);
	   }
};

function $addDelButton(container,tartget,gridName,taskId,pkValue){
       var span=document.createElement('span');
	   span.setAttribute('style',' float:right;height:20px;');

	   //del button start
	   var vv=document.createElement('button');
	   vv.setAttribute('class','x-btn-text btn-del');
	   vv.setAttribute('style','float:right;');
	   vv.qtip='删除';
	   vv.owerDiv=container;
	   vv.removeDiv=tartget;
	   vv.gridName=gridName;
	   vv.taskId=taskId;
	   vv.pkValue=pkValue;
	   vv.onclick=function(){
	      try{
	      	var oDiv=this.owerDiv;
	      	var rDiv=this.removeDiv;
	        var gridName=this.gridName;
			var taskId=this.taskId;
			var pkValue=this.pkValue;
			Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
					if (btn == 'yes') {
						if(pkValue){
							var tableId=null;
							if(taskId){
								tableId=document.getElementById(gridName+'_'+taskId).value;
							}
							Ext.Ajax.request({
								url :__ctxPath+'/flow/delItemsProcessActivity.do',
								params : {tableId:tableId,ids : pkValue},method : 'POST',
								success : function(response,options) {
									Ext.ux.Toast.msg('操作信息','成功删除该记录！');
									oDiv.removeChild(rDiv);
								},
								failure : function(response,options) {
									Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
								}
							});
						}else{
						   oDiv.removeChild(rDiv);
						}
					}
			});
	         
	      }catch(e){}
	   };
	   span.appendChild(vv); 
       return span;
};

function $converDetailToRead(jsonData){
		//回填数据
		var form=this.formPanel.getForm().getEl().dom;
		var tables=form.getElementsByTagName('table');
		this.detailGrids=new Ext.util.MixedCollection();
		var formobjs=[];
		var removeTables=[];
		for(var i=0;i<tables.length;i++){
			var isdetail=tables[i].getAttribute('isdetail');
			var isgrid=tables[i].getAttribute('isgrid');
			var gridName=tables[i].getAttribute('txtname');
			if(isdetail!=null&&'true'==isgrid){
				var parent=tables[i].parentNode;
				var map=$convertTableToMap(tables[i]);
				var fields=[];
				
				var columns=[];
				if(this.runId){
					var subTableId=document.getElementById('WF_'+gridName+'_'+this.runId);
//					var subTableId=document.getElementById('subPkName_'+this.runId);
					var subTableIdVal=subTableId.value;
					fields.push(subTableIdVal);
					columns.push({dataIndex:subTableIdVal,header:subTableIdVal,hidden:true});
				}
				var flag=0;
				
				for(var f=0;f<map.length;f++){
					fields.push(map[f].name);
				    columns.push({
						dataIndex:map[f].name,
						header:map[f].header
					});
				}
				
				var div=document.createElement('div');
				parent.appendChild(div);
				//加上明细的表格
				this.detailPanel = new HT.EditorGridPanel({
					renderTo:div,
					clicksToEdit:1,
					width:tables[i].offsetWidth,
					showPaging:false,
					autoHeight:true,
					fields : fields,
					columns : columns
				});
				
				//加载其列表
				if(jsonData){
					var data=jsonData['WF_'+gridName+'s'];
					if(data){
						this.detailPanel.getStore().loadData({result:data});
					}
				}
				removeTables.push(tables[i]);
			}else if(isdetail!=null&&'false'==isgrid){
			    
				 try{
					var names=$getTableInputCmpName(tables[i]);
					//取得该子表单的数据
					var datas;
					var pkName;
					if(this.runId){
						var pkKey=document.getElementById('WF_'+gridName+'_'+this.runId);
						var pkKeyVar=pkKey.value;
						if(jsonData){
						   datas=jsonData['WF_'+gridName+'s'];
						   pkName=pkKeyVar;
						}
					}
					var parent=tables[i].parentNode;
					var html=parent.innerHTML;
					var obj={
					   innerhtml:html,//对应的HTML
					   parentNode:parent,//父节点
					   gridName:gridName,//表名
					   elsName:names,//包括的控件名称
					   jsonDatas:datas,
					   pkName:pkName,
					   rightJson:null
					};
					
					formobjs.push(obj);
					removeTables.push(tables[i]);
				 }catch(e){}
			    for(var i=0;i<removeTables.length;i++){
				    var table=removeTables[i];
				    var parent=table.parentNode;
				    parent.removeChild(table);
				}
			}
		}
		//转化控件及赋值	
		
		var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements;
		var formPanel=this.formPanel;
		
		$converCmp.call(this,fElements,jsonData,null,false,true);
		$converFormDetail.call(this,formobjs,true);
		return;
		
		
};
function $validField(element){
		   if(element.style.display=='none'){
				return true;
		   }
           var isNotNull=element.getAttribute('txtisnotnull');
       	   var xtype=element.getAttribute('xtype');
       	   var txtsize=element.getAttribute('txtsize');
       	   var dataformat=element.getAttribute('dataformat');
           var pass=true;
           var msg;
           if(isNotNull==1){
           	  if(element.value==''){
           	     msg='此选项为必填项'; 
           	     pass=false;
           	  }
           }
           if(pass&&txtsize&&element.value.toString().length>txtsize){
              msg='此项内容不得超过'+txtsize;
           	  pass=false;
           }
           if(false&&pass){
           	  var value=element.value;
           	  if(value!=''){
	              var email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	              pass=email.test(value);
	              msg='此项内容为邮件格式：XXX@XX.com';
           	  }
           }
           if(dataformat&&pass){
           	  var value=element.value;
           	  if(value!=''){
           	  	  var reg=new RegExp(dataformat);
	              pass=reg.test(value);
	              msg='此项内容的格式不正确';
           	  }
           }
           if(xtype=='numberfield'&&pass){
           	  var value=element.value;
           	  var tvt=element.getAttribute('txtvaluetype');
           	  if(value!=''){
           	  	  if(tvt=='int'||tvt=='bigint'||tvt=='smallint'){
		              var intReg = /^[-\+]?\d+$/;
		              pass=intReg.test(value);
		              msg='此项内容应为整数';
           	  	  }else{
           	  	      var fReg=/^-?\d+\.?\d*$/
           	  	      pass=fReg.test(value);
           	  	      msg='此项内容应为数字';
           	  	  }
           	  }
           }
           var invalidClass=' x-form-invalid';
		   var oldClass=element.getAttribute('class');
           if(!pass){
                 if(oldClass){
	                 if(oldClass.indexOf(invalidClass)==-1){
			   	  	    oldClass=oldClass+invalidClass;
			   	  	 }
                 }else{
                     oldClass=invalidClass;
                 }
		   	     element.setAttribute('class',oldClass);
		         element.qtip=msg;
		         element.qclass = 'x-form-invalid-tip';
		   	     return false;
           }else{
           	    if(oldClass){
	                 element.setAttribute('class',oldClass.replace(invalidClass,''));
			     }
                 element.qtip='';
		         element.qclass = '';
		         return true;
           }
};
/**
 * 验证流程表单
 * @return {Boolean}
 */
function $validForm(){
       var form=this.formPanel.getForm().getEl().dom;
       var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements;
       var isValid=true;
       Ext.each(fElements, function(element,index) {
       	   isValid=isValid&&$validField.call(this,element);
       });
       /**
        * 对子表单进行验证
        */
       var forms=form.getElementsByTagName('form');
       for(var i=0;i<forms.length;i++){
          var  f=forms[i];
          var  els=f.elements;
          Ext.each(els, function(element,index) {
       	   isValid=isValid&&$validField.call(this,element);
          });
       }
       if(isValid){
          return true;
       }else{
          return false;
       }
};

