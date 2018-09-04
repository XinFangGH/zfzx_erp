
ScoreGradeForm = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	classId:null,
	gridPanel:null,
	isHidden:false,
	isCopy:false,
	constructor : function(_cfg) {
		if(typeof(_cfg.classId)!="undefined"){
		   this.classId=_cfg.classId
		}
		if(typeof(_cfg.gridPanel)!="undefined"){
		   this.gridPanel=_cfg.gridPanel
		}
		if(typeof(_cfg.isHidden)!="undefined"){
		   this.isHidden=_cfg.isHidden
		}
		if(typeof(_cfg.isCopy)!="undefined"){
		   this.isCopy=_cfg.isCopy
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ScoreGradeForm.superclass.constructor.call(this, {
			        width:800,
			        height:500,
			        buttonAlign:'center',
			        frame:true,
			        autoScroll:true,
			        title:this.title,
			         modal:true,
			         layout:'form',
			         labelAlign:"right",
			         bodyStyle : 'padding:10px 10px 10px 10px',
			         labelWidth:110,
					items : [{
						xtype:'hidden',
						name:'classType.classId',
						value:this.classId
					},{
		               xtype:'textfield',
		               anchor:'80%',
		               name:'classType.className',
		               readOnly:this.isHidden,
		               fieldLabel:'信用等级标准名称'
		           },{
		               xtype:'textarea',
		               anchor:'80%',
		               name:'classType.remarks',
		               readOnly:this.isHidden,
		               fieldLabel:'标准说明'
		           },{
		              xtype:'fieldset',
		              title:'资信评级',
		              items:[this.grid_Panel]
		           }],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						disabled : this.isReadOnly,
						hidden:this.isHidden,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						disabled : this.isReadOnly,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {
		
		
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
						}
						sucessFun();
					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('操作信息', '删除失败!');
					}
				},
				params : prame
			});
		};
    
		this.tBar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								handler : this.createBankloanProgram
							}, new Ext.Toolbar.Separator({

							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.deleteBankLoanProgram
							}]
				})

		this.grid_Panel = new Ext.grid.EditorGridPanel({
			border : false,
			tbar : (this.isHidden==false)?this.tBar:null,
			autoHeight : true,
			autoWidth:true,
			clicksToEdit : 1,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			sm : new Ext.grid.CheckboxSelectionModel({}),
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : __ctxPath+'/creditFlow/creditmanagement/getScoreGradeListScoreGradeOfClass.do?classId='+ this.classId,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
					fields : Ext.data.Record.create([{
								name : 'grandId'
							}, {
								name : 'grandname'
							}, {
								name : 'grandType'
							}, {
								name : 'hanyi'
							}, {
								name : 'grandExplain'
							}, {
								name : 'startScore'
							}, {
								name : 'endScore'
							}, {
								name : 'classId'
							},{
								name : 'className'
							}

					]),
					root : 'topics'
				})
			}),
			columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}), new Ext.grid.RowNumberer(), {
						header : '信用级别名称',
						dataIndex : 'grandname',
						sortable : true,
						align : "right",
						width:120,
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						}
					}, {
						header : '级别等级',
						dataIndex : 'grandType',
						sortable : true,
						align : "right"
					}, {
						header : '含义',
						format : 'Y-m-d',
						dataIndex : 'hanyi',
						sortable : true,
						editor : {
						   xtype:'textfield',
						   readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}, {
						header : '级别说明',
						dataIndex : 'grandExplain',
						sortable : true,

						editor : {
							 xtype:'textfield',
							 readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}, {
						header : '开始百分制分数',
						dataIndex : 'startScore',
						sortable : true,
						editor : {
						    xtype:'numberfield',
						    readOnly:this.isHidden,
						    style : {
								imeMode : 'disabled'
							}
						}
					}, {
						header : '结束百分制分数',
						format : 'Y-m-d',
						dataIndex : 'endScore',
						sortable : true,
						editor : {
						    xtype:'numberfield',
						    readOnly:this.isHidden,
						    style : {
								imeMode : 'disabled'
							}
						}
					}]

		});
		this.grid_Panel.getStore().load();
       if (this.classId != null && this.classId != 'undefined') {
			this.loadData({
						url : __ctxPath+'/creditFlow/creditmanagement/getClassTypeScoreGradeOfClass.do?classId='
								+ this.classId,
						root : 'data',
						preName : 'classType',
						scope : this,
						success : function(resp, options) {
							
						}
					});
		}
	},
	createBankloanProgram : function() {
		var gridadd = this.grid_Panel;
		var storeadd = this.grid_Panel.getStore();
		var keys = storeadd.fields.keys;
		var count = storeadd.getCount() + 1;
		var p = new Ext.data.Record();
		p.data = {};
		for (var i = 1; i < keys.length; i++) {
			p.data[keys[i]] = '';
			p.data[keys[2]] = count+'级';	
			p.data[keys[5]] = 0;
			p.data[keys[6]] = 0;

		}

		
		gridadd.stopEditing();
		storeadd.addSorted(p);
		gridadd.getView().refresh();
		gridadd.startEditing(0, 1);
	},
	deleteBankLoanProgram : function() {
		var griddel = this.grid_Panel;
		var storedel = griddel.getStore();
		var s = griddel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}
		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

			if (btn == "yes") {
				griddel.stopEditing();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
			
					if (row.data.grandId == null
							|| row.data.grandId == '') {
						storedel.remove(row);
						griddel.getView().refresh();
					} else {
				       
						deleteFun(
								 __ctxPath+'/creditFlow/creditmanagement/deleteSGScoreGradeOfClass.do',
								{
									grandId : row.data.grandId
								}, function() {

								}, i, s.length)
					}

					storedel.remove(row);
					griddel.getView().refresh();
				}
			}
		})
	},
	save:function(){
		var className=this.getCmpByName("classType.className").getValue();
		var remarks=this.getCmpByName("classType.remarks").getValue()
		var classId=this.classId;
		if(this.isCopy==true){
			classId=null;
		}
		var isCopy=this.isCopy;
		var getScoreGridDate = function(grid) {
	
			if (typeof(grid) == "undefined" || null == grid) {
				return "";
			}
			var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
		
			var vCount = vRecords.length; // 得到记录长度
		
			var vDatas = '';
			if (vCount > 0) {
				// begin 将记录对象转换为字符串（json格式的字符串）
				for (var i = 0; i < vCount; i++) {
					var str = Ext.util.JSON.encode(vRecords[i].data);
					if(isCopy==true && typeof(vRecords[i].data.grandId)!="undefined"){
					  var firstIndex=str.indexOf(",");
					  str="{"+str.substring(firstIndex+1,str.length);
					}
					var index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					index = str.lastIndexOf(",");
					str = str.substring(0, index) + "}";
					vDatas += str + '@';
				}
		
				vDatas = vDatas.substr(0, vDatas.length - 1);
			}
			return vDatas;
	}
		var vDates=getScoreGridDate(this.grid_Panel);
		var grid=this.grid_Panel;
		var window=this;
		var gridPanel=this.gridPanel
		Ext.Ajax.request({
			url :  __ctxPath+'/creditFlow/creditmanagement/saveClassAndGrandScoreGradeOfClass.do',
			method : 'POST',
			params : {
				scoreGrade : vDates,
				classname:className,
				remarks:remarks,
				classId:classId
			},
			success : function(response, request) {

				var xx = response.responseText
						.toString().trim();
				if (xx == "{success:true}") {

					Ext.ux.Toast.msg('操作信息',"保存成功");
					window.close()	
					gridPanel.getStore().reload()
				}else if(xx == "{success:true,msg:'unquine'}"){
					
					Ext.ux.Toast.msg('操作信息',"信用评级标准名称不能重复");
				}
			},
			failure :function(response, request) {
				Ext.ux.Toast.msg('操作信息',"保存失败");
			}
		});
	}
});
