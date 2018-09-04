/**
 * @author
 * @createtime
 * @class FieldRightsForm
 * @extends Ext.Window
 * @description FieldRights表单
 * @company 智维软件
 */
FieldRightsForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				FieldRightsForm.superclass.constructor.call(this, {
							id : 'FieldRightsFormWin',
							layout : 'fit',
							items : this.gridPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '表单字段权限详细信息',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
										scope : this,
										handler : this.cancel
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.store=new Ext.data.GroupingStore({
					 proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/nodesFieldRights.do?defId='+this.defId
						}),
				     reader: new Ext.data.JsonReader({
						root : 'result',
						id : 'id',
						fields : [{name:'rightId',type:'int'}, {name:'mappingId',type:'int'}, 'taskName',{name:'readWrite',type:'int'},{name:'refieldId',type:'int'},'fieldName','fieldLabel']
					}),
                    groupField:'taskName'
				});

				this.store.load();
				var sm=new Ext.grid.CheckboxSelectionModel();
				this.gridPanel=new Ext.grid.EditorGridPanel({
				    store:this.store,
				    sm:sm,
				    tbar:new Ext.Toolbar({
				        items:[
				        {text:'标为可写',iconCls:'',scope:this,handler:this.flagFunction.createCallback(this,2)},
				        {text:'标为可读',iconCls:'',scope:this,handler:this.flagFunction.createCallback(this,1)},
				        {text:'标为隐藏',iconCls:'',scope:this,handler:this.flagFunction.createCallback(this,0)}
				        ]
				    }),
				    columns : [sm,new Ext.grid.RowNumberer(),{
						header : "rightId",
						dataIndex : 'rightId',
						hidden : true
					},{
					   dataIndex:'taskName',
					   header:'taskName',
					   hidden : true
					}, {
						header : "字段名称",
						dataIndex : 'fieldName',
						sortable : true
					},{
						header : "字段标签",
						dataIndex : 'fieldLabel',
						sortable : true
					},{
					    dataIndex:'readWrite',
					    header:'权限',
					    fixed:true,
					    resizable:false,
						sortable : false,
					    width:50,
					    menuDisabled: true,
					    renderer:function(value){
					        if(value==0){
					           return '<font color="gray">隐藏</font>';
					        }else if(value==1){
					           return '<font color="red">读</font>';
					        }else if(value==2){
					           return '<font color="green">写</font>';
					        }
					    },
					    editor:new Ext.form.ComboBox({
					        typeAhead: true,
					        editable:false,
						    triggerAction: 'all',
						    lazyRender:true,
						    mode: 'local',
					        store:[['0','隐藏'],['1','读'],['2','写']]
					    })
					}]
					,
					view: new Ext.grid.GroupingView({
			            forceFit:true,
			            startCollapsed:true,
			            enableNoGroups:false,
			            enableGroupingMenu:false,
			            showGroupName: false,
			            groupTextTpl: '{text} 节点'
			        })

				});

			},// end of the initcomponents
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function() {
				this.close();
			},
			selectData:function(){
			    var sels=this.gridPanel.getSelectionModel().getSelections();
				if(sels.length<1){
				   Ext.ux.Toast.msg('操作信息', '请选择字段！');
				   return null;
				}
				return sels;
			},
			flagFunction:function(self,flag){
				var array=self.selectData();
			    if(array){
			       for(var i=0;i<array.length;i++){
			          var rec=array[i];
			          rec.set('readWrite',flag);
			       }
			    }
			},
			/**
			 * 保存记录
			 */
			save : function() {
				var params=[];
				for(var i=0;i<this.store.getCount();i++){
				    var rec=this.store.getAt(i);
				    if(rec.data.rightId==''||rec.data.rightId==null||rec.data.rightId==undefined){
				       rec.set('rightId',-1);
				    }
				    if(rec.dirty){
				       params.push(rec.data);
				    }
				}
				if(params.length==0){
				   Ext.ux.Toast.msg('操作信息', '没有修改过数据！');
				}
				var self=this;
				Ext.Ajax.request({
				    url:__ctxPath+'/flow/multSaveFieldRights.do',
				    method:'post',
				    params:{data:Ext.encode(params)},
				    success:function(){
				       Ext.ux.Toast.msg('操作信息', '成功保存信息！');
				       self.close();
				    },
				    failure:function(){Ext.ux.Toast.msg('操作信息', '保存信息出错！');}
				});
			}// end of save
		});