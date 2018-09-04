Ext.namespace('Ext.ux.form');

/*
 * extend setTimeout function to support arguments
 */
function setTimeoutEx(fRef, mDelay) {
	if (typeof fRef == 'function') {
		var args = Array.prototype.slice.call(arguments, 2);
		var f = function() {
			fRef.apply(null, args);
		};
		return setTimeout(f, mDelay);
	}
	return setTimeout(fRef, mDelay);
}

Ext.ux.form.Ueditor = function(config) { // add Ueditor config  fckconfig
	if(config.id==null){
		config.id='__Ueditor_' + parseInt(1000*Math.random());
	}
	// object
	this.config = config;
	this.fckconfig = config;
	Ext.ux.form.Ueditor.superclass.constructor.call(this, config);
	this.instanceLoaded = false;
	this.instanceValue = config.value?config.value:'';
	this.editorInstance = undefined;
};

Ext.extend(Ext.ux.form.Ueditor, Ext.form.TextArea, {
			initEvents : function() {
				this.on('destroy', function() {
							if (typeof this.editorInstance != 'undefined') {
								delete this.editorInstance;
							}
						});
			},
			onRender : function(ct, position) {
				if (!this.el) {
					this.defaultAutoCreate = {
						tag : "textarea",
						style : "width:300px;height:660px;",
						autocomplete : "off"
					};
				}
				Ext.form.TextArea.superclass.onRender.call(this, ct, position);
				this.hideMode = "";
				
				this.hidden = true;
				if (this.grow) {
					this.textSizeEl = Ext.DomHelper.append(document.body, {
								tag : "pre",
								cls : "x-form-grow-sizer"
							});
					if (this.preventScrollbars) {
						this.el.setStyle("overflow", "hidden");
					}
					this.el.setHeight(this.growMin);
				}
				setTimeoutEx(loadUeditor, 100, this.config.id, this.fckconfig); // setTimeoutEx
				//loadUeditor(this.el.id,this.config);
				// support
				// arguments
			},
			setIsLoaded : function(v) {
				this.instanceLoaded = v;
			},
			getIsLoaded : function() {
				return this.instanceLoaded;
			},
			setValue : function(value) {
				this.instanceValue = value;
				if (this.instanceLoaded) {
					this.UeditorSetValue(value);
				}
				Ext.form.TextArea.superclass.setValue.apply(this, [value]);
			},
			getValue : function() {
				if (this.instanceLoaded) {
					value = this.UeditorGetValue();
					Ext.form.TextArea.superclass.setValue.apply(this, [value]);
					return Ext.form.TextArea.superclass.getValue.call(this);
				} else {
					return this.instanceValue;
				}
			},
			getRawValue : function() {
				if (this.instanceLoaded) {
					value = this.UeditorGetValue();
					Ext.form.TextArea.superclass.setRawValue.apply(this,
							[value]);
					return Ext.form.TextArea.superclass.getRawValue.call(this);
				} else {
					return this.instanceValue;
				}
			},
			UeditorSetValue : function(value) {
				if (this.instanceLoaded == false) {
					return;
				}
				var runner = new Ext.util.TaskRunner();
				var task = {
					run : function() {
						try {
							var editor = this.editorInstance;
								editor.setContent(value);
								runner.stop(task);
						} catch (ex) {
						}
					},
					interval : 100,
					scope : this
				};
				runner.start(task);
			},
			UeditorGetValue : function() {
				var data = '';
				if (this.instanceLoaded == false) {
					return data;
				}
				data = this.editorInstance.getContent();
				return data;
			},
			isDirty : function() {
				return this.editorInstance.IsDirty();
			},
			resetIsDirty : function() {
				this.editorInstance.ResetIsDirty();
			}
		});
Ext.reg('fckeditor', Ext.ux.form.Ueditor);

function loadUeditor(element, config) {
	
	//加载前先删除已经存在的编辑器
	 UE.delEditor(config.id);
	 var oUeditor = UE.getEditor(config.id,{
	 zIndex:10000,
	    toolbars: [
               ['fullscreen', 'source', 'undo', 'redo', 'bold', 'italic', 'underline', 'fontborder', 'backcolor', 'fontsize', 'fontfamily', 'justifyleft', 'justifyright', 'justifycenter', 'justifyjustify', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', 'link', 'unlink', 'emotion','|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage',  'insertvideo',  'attachment', 'map', 'help']
          ]
	 
	 });
	
	  oUeditor.addListener( 'ready', function() {
        this.execCommand( 'focus' ); //编辑器加载完成后，让编辑器拿到焦点
        Ueditor_OnComplete(this);
 } );
	 
	 //内容发生变化触发事件
	 oUeditor.addListener('contentChange', function() {
    // this.textarea.textContent=this.getPlainTxt();
 } );
	 
	 // oUeditor.setHeight(200);
	  //oUeditor.render(config.id);
	//加载Ueditor js
	//$ImportSimpleJs([],function(){
		//var oUeditor = new Ueditor(element,config.width,config.height);
		
		// var oUeditor = UE.getEditor(config.id);
		
		//oUeditor.Config['SkinPath'] = __ctxPath+'/js/Ueditor/editor/skins/office2003/';
		/*for (var key in config) { // Ueditor config from object argument
			if (typeof oUeditor[key] != 'undefined') {
				oUeditor[key] = config[key];
			}
		}*/
		//oUeditor.BasePath = __ctxPath+'/ueditor/'; 
		 
		//oUeditor.ToolbarSet = 'JDefault';
		//oUeditor.ReplaceTextarea();
	//});
}

function Ueditor_OnComplete(editorInstance) {
	var activeEditor = Ext.getCmp(editorInstance.key);
	activeEditor.editorInstance = editorInstance;
	activeEditor.instanceLoaded = true;
	activeEditor.UeditorSetValue(activeEditor.instanceValue);
	//editorInstance.Events.AttachEvent('OnBlur', Ueditor_OnBlur);
	//editorInstance.Events.AttachEvent('OnFocus', Ueditor_OnFocus);
	//editorInstance.ToolbarSet.Collapse();
}

function Ueditor_OnBlur(editorInstance) {
	editorInstance.ToolbarSet.Collapse();
}

function Ueditor_OnFocus(editorInstance) {
	editorInstance.ToolbarSet.Expand();
}
