/**
 * @author YungLocke
 */
Ext.DataView.LabelEditor = Ext.extend(Ext.Editor, {
	alignment : "tl-tl",
	hideEl : false,
	cls : "x-small-editor",
	shim : false,
	completeOnEnter : true,
	cancelOnEsc : true,
	labelSelector : 'div.thumb-wrap',
	constructor : function(cfg, field) {
		Ext.DataView.LabelEditor.superclass.constructor.call(this, field
				|| new Ext.form.TextArea( {
					allowBlank : false,
					growMin : 90,
					growMax : 240,
					grow : true,
					selectOnFocus : true
				}), cfg);
	},

	init : function(view) {
		this.view = view;
		view.on('afterrender', this.initEditor, this);
		this.on('complete', this.onSave, this);
	},
	initEditor : function() {
		this.view.on( {
			scope : this,
			containerclick : this.doBlur,
			click : this.doBlur
		});
		var el = this.view.getEl();
		el.on('dblclick', this.onMouseDown, this, {
			delegate : this.labelSelector
		});
	},

	doBlur : function() {
		if (this.editing) {
			this.field.blur();
		}
	},

	onMouseDown : function(e, target) {
		if (!e.ctrlKey && !e.shiftKey) {
			var item = this.view.findItemFromChild(target);
			e.stopEvent();
			var id = this.view.indexOf(item);
			var record = this.view.store.getAt(id);
			this.startEdit(target, record.data[this.dataIndex]);
			var height = item.scrollHeight;
			var width = item.scrollWidth;
			if(Ext.isIE){
			   width=width+2;
			   if(Ext.isIE6||Ext.isIE7){
			      var parent=item.parentNode;
			      height=parent.scrollHeight;
			   }else{
			      height=height+2;
			   }
			}
			this.setSize(width, height);
			this.activeRecord = record;
		} else {
			e.preventDefault();
		}
	},
	
	onSave : function(ed, value) {
		this.activeRecord.set(this.dataIndex, value);
		this.view.store.fireEvent('recordchange',this.view.store);
	}
});
Ext.ns('PersonalTipsView');
PersonalTipsView = Ext.extend(Ext.Panel,{
	maxLevel : 1,
	topbar : null,
	dataView : null,
	store : null,
	curRecord:null,
	curLevel:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUI();
		PersonalTipsView.superclass.constructor.call(this, {
			id : 'PersonalTipsView',
			title : '个人便签',
			iconCls:'tipsTile',
			layout : 'fit',
			tbar:this.topbar,
		    autoScroll:false,
			items : [this.dataView]

		});
		
	},
	initUI : function() {
		this.store = new Ext.data.JsonStore( {
			url : __ctxPath + "/info/listAppTips.do",
			root : 'result',
			totalProperty : 'totalCounts',
			remoteSort : true,
			fields : [ {
				name : 'tipsId',
				type : 'int'
			}, 'userId', 'tipsName', 'content', 'disheight',
					'diswidth', 'disleft', 'distop',
					'dislevel', 'createTime' ]
		});
		this.store.setDefaultSort('tipsId', 'asc');
		// 加载数据
		this.store.load({
			params : {
			start : 0,
			limit : 1000
			}
		});
		this.store.on('load',this.loadAction,this);
		this.store.on('recordchange',this.recordChange,this);
		this.dataView = new Ext.DataView({
				id : 'dataView',
				autoScroll:false,
				store : this.store,
				// //item选择器
				itemSelector : 'div.thumb-wrap',
				// 单选
				multiSelect : false,
				singleSelect:true,
				// 鼠标悬浮样式
				overClass : 'setDiv',
				plugins : [ new Ext.DataView.LabelEditor({dataIndex : 'content'}) ],
				// 模板
				tpl : new Ext.XTemplate(
						'<div id="basicDiv" style="width:100%;overflow:hidden;height:100%;">',
							'<div id="inputDiv" style="position: absolute;width:100%;height:100%;"></div>',
							'<tpl for=".">',
								'<div id={tipsName} class="thumb-wrap tipDiv" style="width:{diswidth}px;height:{disheight}px;left:{disleft}px;top:{distop}px;z-index:{dislevel};"><span id={tipsName}a class="x-linkdel"><button class="tipsClose" value="" onclick="PersonalTipsView.deleteTips({tipsId},\'{tipsName}\');" title="删除"></button></span><span class="x-editable">{content}</span><span class="x-timelabel">{createTime}</span></div>',
							'</tpl>',
						'</div>'),
				scope:this,
				emptyText:'<div id="basicDiv" style="width:100%;overflow:hidden;height:100%;"><div id="inputDiv" style="position: absolute;width:100%;height:100%;"></div></div>',
				listeners:{
					scope:this,
					'afterrender':{fn:this.bodyRender,scope:this}
					,
				   'mouseenter':{fn:this.mouseenter,scope:this},
				   'mouseleave':{fn:this.mouseleave,scope:this}
				   ,
				   'click':{fn:this.clickView,scope:this}
				}
			
		});
		
		
		this.topbar=new Ext.Toolbar({
		    items:['<font color="red">提示：在空白面板双击输入！</font>','->',{
		       xtype:'button',
		       text:'一键清除',
		       iconCls:'btn-delete',
		       scope:this,
		       handler:function(){
		       	if(this.store.getCount()>0){
		           PersonalTipsView.deleteTips('all');
	            }else{
	       	       Ext.ux.Toast.msg('信息','没有记录可删除!');
	       	    }
		       }
		    }
//		    ,{
//		       xtype:'button',
//		       text:'保存',
//		       iconCls:'btn-save',
//		       scope:this,
//		       handler:function(){
//		          	this.saveRecord(this.store);
//		       }
//		    }
		    ]
		});
		
	},
	loadAction:function(store){
	    if(store.getCount()>0){
	        for(var i=0;i<store.getCount();i++){
	             var id = store.getAt(i).get('tipsName');
				 this.resizable(id);
	        }
	    }
	},
	recordChange:function(store){
	   this.saveRecord(store);
	},
	saveAction:function(store){
	    this.saveRecord(store);
	},
	bodyContextClick:function(e,t,o){
		if (!e.ctrlKey&& !e.shiftKey) {
			var myDiv = Ext.get("basicDiv");
			var inputDiv = this.getInputCmp();
			var item = this.dataView.findItemFromChild(t);
			if (item == null) {
				var x = e.getPageX();
				var y = e.getPageY();
				inputDiv.setStyle('top',e.getPageY()- myDiv.getTop()+ 'px');
				inputDiv.setStyle('left',e.getPageX()- myDiv.getLeft()+ 'px');
				var edit = new Ext.DataView.LabelEditor();
				edit.setSize(200,100);
				edit.on('complete',function(edit,value) {
					if (item == null) {
						var d = edit.el.getBox();
						var record = {
							tipsId:-1,
							tipsName:'tips'+new Date().format('YmdHisu'),
							content : value,
							diswidth : d.width,
							disheight : d.height,
							disleft : x- myDiv.getLeft(),
							distop : y- myDiv.getTop(),
							dislevel : this.maxLevel+1,
							createTime:new Date().format('Y-m-d H:i:s')
						};
						var p = new this.store.recordType(record);
						this.store.add(p);
						this.dataView.refresh();
						if(this.store.getCount()>0){
						     for(var i=0;i<this.store.getCount();i++){
					             var id = this.store.getAt(i).get('tipsName');
								 this.resizable(id);
					        }
						}
						this.store.fireEvent('recordchange',this.store);
					}
				},this);
				edit.startEdit(t,'');
				inputDiv.setStyle('top','0px');
				inputDiv.setStyle('left','0px');
			}
		}
	},
	bodyRender:function(dataview){
	    dataview.getEl().on('dblclick',this.bodyContextClick,this);
	},
	saveRecord:function(store){
	    var params = [];
		for ( var i = 0; i < store.getCount(); i++) {
			var record = store.getAt(i);
			var tipsId=record.data.tipsId;
			if (record.dirty||tipsId==-1) { // 得到所有修改过的数据
				if(tipsId>0){
				   record.set('tipsId',-2);
				}
				params.push(record.data);
				record.dirty=false;
			}
		}
		if (params.length == 0) {
			return;
		}
		Ext.Ajax.request( {
					method : 'post',
					url : __ctxPath + '/info/saveAppTips.do',
					success : function(
							request) {
							for ( var i = 0; i < store.getCount(); i++) {
								var record = store.getAt(i);
								var tipsId=record.data.tipsId;
								if (tipsId==-1) { // 得到所有修改过的数据
									   record.set('tipsId',-2);
									   record.dirty=false;
								}
							}	
					},
					failure : function(request) {
						store.reload({
							params : {
							start : 0,
							limit : 1000
						}
					});
						Ext.MessageBox
								.show( {
									title : '操作信息',
									msg : '信息保存出错，请联系管理员！',
									
									buttons : Ext.MessageBox.OK,
									icon : 'ext-mb-error'
								});
					},
					params : {
						data : Ext.encode(params)
					}
				});
	},
	getInputCmp:function(){
	   return Ext.get("inputDiv");
	},
	resizable:function(id){// 将DIV渲染成为可拉伸的
	    var re = new Ext.Resizable(id,{
					wrap : true,
					pinned : false,
					dynamic : true,
					minWidth: 200,
					minHeight: 100,
					draggable : true
				});
		re.el.setStyle('position','absolute');
		re.on('resize',this.resizeAction,this);
	},
	resizeAction:function(thiz,width,height,e) {
//		thiz.el.setStyle('position','relative');
		var myDiv = Ext.get("basicDiv");
		var div = thiz.el.getBox();
		var resizeChild=thiz.resizeChild.id;
		var index = this.dataView.indexOf(resizeChild);
		var record = this.dataView.store.getAt(index);
		record.set('disleft',div.x- myDiv.getLeft());
		record.set('distop',div.y- myDiv.getTop());
		record.set('diswidth',width - 3);
		record.set('disheight',height - 3);
		this.store.fireEvent('recordchange',this.store);
	},
	clickView:function(dataview,index,node,e){
		var nodeName=node.id;
		var view=Ext.get(nodeName+'-rzwrap');
		var record = dataview.store.getAt(index);
		this.maxLevel=this.maxLevel+1;
		view.setStyle('z-index',this.maxLevel);
		this.curRecord=record;
		this.curLevel=this.maxLevel;
//		record.set('dislevel',this.maxLevel);
	},
	mouseenter:function(dataview,index,node,e){
	    var nodeName=node.id;
        var el=Ext.get(nodeName+'a');
        el.setStyle('visibility','visible');
	},
	mouseleave:function(dataview,index,node,e){
		  if(this.curLevel!=null&&this.curRecord!=null){
		      var record=this.curRecord;
		      var level=this.curLevel;
		      record.set('dislevel',level);
		      this.curLevel=null;
		      this.curRecord=null;
		  }
		  var myDiv = Ext.get("basicDiv");
		  var nodeName=node.id;
          var el=Ext.get(nodeName+'a');
          if(el){
	          el.setStyle('visibility','hidden');
	          var box=Ext.get(nodeName+'-rzwrap').getBox();
	          var index = dataview.indexOf(nodeName);
			  var record = dataview.store.getAt(index);
			  var left=box.x- myDiv.getLeft();
			  var top=box.y- myDiv.getTop();
			  var releft=record.data.disleft;
			  var retop=record.data.distop;
			  if((left!=releft)&&(top!=retop)){
				  record.set('disleft',box.x- myDiv.getLeft());
				  record.set('distop',box.y- myDiv.getTop());
				  this.store.fireEvent('recordchange',this.store);
			  }
			  
			  
          }
	}
});

PersonalTipsView.deleteTips=function(id,name){
	var message='您确认要删除所选记录吗？';
	if('all'==id){
	   message='您确认要清除所有便签吗?';
	}
	Ext.Msg.confirm('信息确认',message,function(btn){
		if(btn=='yes'){
			Ext.Ajax.request({
							url:__ctxPath+'/info/multiDelAppTips.do',
							params:{ids:id,names:name},
							method:'POST',
							success:function(response,options){
								Ext.ux.Toast.msg('操作信息','成功删除便签！');
								if(id!='all'){
								   var dataview=Ext.getCmp('dataView');
//		                           var df=Ext.get(name);
		                           var index = dataview.indexOf(name);
								   var dd=Ext.get(name+'-rzwrap');
		                           Ext.destroy(dd);
		                           
			                       dataview.store.removeAt(index);
		                           return;
								}
								Ext.getCmp('PersonalTipsView').store.reload({
									params : {
									start : 0,
									limit : 1000
								   }
							   });
							},
							failure:function(response,options){
								Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
							}
						});
		}
	});//end of comfirm
};
