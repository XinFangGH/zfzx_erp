/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
RedenvelopeFormTab = Ext.extend(Ext.Window, {

	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RedenvelopeFormTab.superclass.constructor.call(this, {
					id : 'RedenvelopeFormTab',
					title : '添加红包',
					region : 'center',
					layout : 'border',
					modal : true,
					height : 553,
					width : screen.width * 0.6,
					maximizable : true,
					items : [this.formPanel, this.tabpanel]
                    
				});
	},// end of the constructor
	// 初始化组件

	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}

		var flag = this.flag;
		this.formPanel = new Ext.FormPanel({
			region : 'north',
					layout : 'form',
					height : 140,
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'BpCustRedEnvelopeForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'bpCustRedEnvelope.redId',
								xtype : 'hidden',
								value : this.redId == null ? '' : this.redId
							},{
								name : 'bpCustRedEnvelope.distributestatus',
								xtype : 'hidden',
								value : 0
							}, {
								fieldLabel : '红包名称',
								labelAlign : "right",
								name : 'bpCustRedEnvelope.name',
								readOnly:this.isreadOnly,
								allowBlank:false,
								maxLength : 50
							}, {
								fieldLabel : '红包简介',
								labelAlign : "right",
								name : 'bpCustRedEnvelope.remarks',
								readOnly:this.isreadOnly,
								xtype:"textarea",
								maxLength : 255
							}]
				});
						//加载表单对应的数据	
		  if (this.redId != "" && this.redId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/customer/getBpCustRedEnvelope.do?redId='+ this.redId,
								root : 'data',
								preName : 'bpCustRedEnvelope'
							});
				}
				
				var itemschange=[
				new RedMoneygridPanel({redId:this.redId,type:"not",isreadOnly:true,gp:this.gp,obj:this}),
				new RedMoneygridPanel({redId:this.redId,type:"ed",isreadOnly:true,gp:this.gp,obj:this})
				];
					this.tabpanel = new Ext.TabPanel({
					resizeTabs : true, // turn on tab resizing
					minTabWidth : 115,
					tabWidth : 135,
					enableTabScroll : true,
					Active : 0,
					width : 600,
					defaults : {
						autoScroll : true
					},
					region : 'center', 
					//layout:'fit'
					deferredRender : true,
					activeTab : 0, // first tab initially active
					xtype : 'tabpanel',
						items : itemschange
					});
					
					
		// this.gridPanel.addListener('rowdblclick',this.rowClick);

	},
	
     createShareequity : function(){
    	 var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
		
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = 0;
			p.data[keys[4]] = '';
			p.data[keys[5]] = '';
			p.data[keys[6]] = '';
			p.data[keys[7]] = '';
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },   
     deleteShareequity : function(){
			var griddel =this.gridPanel;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s.length <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.redTopersonId == null || row.data.redTopersonId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/customer/multiDelBpCustRedMember.do',
										{
											ids :row.data.redTopersonId
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
		
     },
		getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			var st = "";
			for (var i = 0; i < vCount; i++) {
		
						st = {
							"bpCustMemberId" : vRecords[i].data.bpCustMemberId,
			            	"redTopersonId" : vRecords[i].data.redTopersonId,
							"redMoney" : vRecords[i].data.redMoney
						};
						vDatas += Ext.util.JSON.encode(st) + '@';

			}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
	cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
   save : function() {
   	var gp=this.gp;
  var reddatas= this.getGridDate();
				if(this.formPanel.getForm().isValid()){
					this.formPanel.getForm().submit({
						method : 'POST',
						scope :this,
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						url : __ctxPath + "/customer/saveBpCustRedEnvelope.do",
						params : {
						reddatas:reddatas
						},
						success : function(form ,action) {
								Ext.ux.Toast.msg('操作信息', '成功信息保存！');
								var gridPanel = gp;
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
						},
						failure : function(form ,action) {
							 Ext.MessageBox.show({
				            title : '操作信息',
				            msg : '信息保存出错，请联系管理员！',
				            buttons : Ext.MessageBox.OK,
				            icon : 'ext-mb-error'
				        });
						}
					})
				}
			},//end of save
	distributemany :function() {
		var redId=this.redId;
		var s = this.gridPanel.getSelectionModel().getSelections();
		var this1=this;
		  if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中一条记录');
			return false;
		}
		var qlidePanel = this.gridPanel;
		  var ids = $getGdSelectedIds(qlidePanel,'redTopersonId');
					Ext.MessageBox.confirm('确认', '确定派发吗', function(btn) {
					if (btn == 'yes') {
			       Ext.Ajax.request( {
									url : __ctxPath + '/customer/distributeBpCustRedEnvelope.do',
									method : 'POST',
									scope : this,
									params : {
										ids : ids,
										redId:redId
									},
									success : function(response, request) {
										var infoObj = Ext.util.JSON.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息', infoObj.msg);
										var gridPanel = gp;
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "派发失败");
										var gridPanel = parentGrid||Ext.getCmp('SlActualToChargeGrid');//update by gao
                                       var gridPanel = gp;
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

										

									}
								});
				
					}
			
				})	
				
		}

});

