			
	
ProfitRateMaintenanceView = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			ProfitRateMaintenanceView.superclass.constructor.call(this,
					{
						id : 'ProfitRateMaintenanceView',
						title : '基准利率管理',
						region : 'center',
						layout : 'border',
						iconCls:"menu-flowManager",
						items : [this.gridPanel]
					})
		},
		initUIComponents : function() {
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})
			var deleteFun = function(url, prame, sucessFun,i,j) {
				Ext.Ajax.request( {
					url : url,
					method : 'POST',
					success : function(response) {
						if (response.responseText.trim() == '{success:true}') {
							if(i==(j-1)){
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
			
			this.topbar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					},new Ext.Toolbar.Separator({
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						handler : this.deleteRs
					},new Ext.Toolbar.Separator({
					}),{
						iconCls : 'btn-save',
						text : '保存',
						xtype : 'button',			
						scope : this,
						handler : this.save
					}]
				})
		var cmGroupRow = [{
			header : " ",
			colspan : 1,
			align : 'center'
		},{
			header : " ",
			colspan : 1,
			align : 'center'
		},{
			header : "单位(年利率%) ",
			colspan : 1,
			align : 'center'
		}, {
			header : "短期贷款 ",
			colspan : 2,
			align : 'center'
		}, {
			header : "中长期贷款",
			colspan : 3,
			align : 'center'
		},{
			header : ' ',
			colspan : 2,
			align : 'center'
		}]
		
		var group = new Ext.ux.grid.ColumnHeaderGroup({
			rows : [cmGroupRow]
		});
			this.gridPanel = new HT.EditorGridPanel( {
					region : 'center',
					tbar : this.topbar,
					clicksToEdit :1,
					pruneModifiedRecords:true,
					HiddenCm:true,
					plugins : [group],
					url : __ctxPath + "/creditFlow/finance/listProfitRateMaintenance.do",
					fields : [ {
						name : 'rateId',
						type : 'long'
					}, 'adjustDate', 'sixMonthLow', 'oneYear', 'threeYear',
							'fiveYear', 'fiveYearUp','updator','updateTime'],
					columns : [{
					   	header : '调整日期',
						dataIndex : 'adjustDate',
						align:'center',
						editor : this.datefield,
						renderer : ZW.ux.dateRenderer(this.datefield)
					}, {
						header : '六个月(含)',
						dataIndex : 'sixMonthLow',
						align:'right',
						editor : {
							xtype : 'numberfield',
					        readOnly:this.isHidden,
					        style: {imeMode:'disabled'}
						}
					}, {
						header : '六个月至一年(含)',
						dataIndex : 'oneYear',
						align:'right',
						editor : {
							xtype : 'numberfield',
					        readOnly:this.isHidden,
					        style: {imeMode:'disabled'}
						}
					}, {
						header : '一至三年(含)',
						dataIndex : 'threeYear',
						align :'right',
						editor : {
							xtype : 'numberfield',
					        readOnly:this.isHidden,
					        style: {imeMode:'disabled'}
						}
					}, {
						header : '三至五年(含)',
						dataIndex : 'fiveYear',
						align:'right',
						editor : {
							xtype : 'numberfield',
					        readOnly:this.isHidden,
					        style: {imeMode:'disabled'}
						}
					}, {
						header : '五年以上',
						dataIndex : 'fiveYearUp',
						align :'right',
						editor : {
							xtype : 'numberfield',
					        readOnly:this.isHidden,
					        style: {imeMode:'disabled'}
						}
					},{
						header : '最后操作人',
						align:'center',
						dataIndex:'updator'
					},{
						header:'最后操作时间',
						align:'center',
						dataIndex:'updateTime'
					}]
			})

		},
		
		createRs : function() {
			
			var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = null;
			}

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteRs : function() {
			var griddel = this.gridPanel;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.rateId == null || row.data.rateId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/finance/multiDelProfitRateMaintenance.do',
										{
											rateId :row.data.rateId
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
		save:function(){
			var getData = function(grid) {
		
				if (typeof (grid) == "undefined" || null == grid) {
					return "";
					return false;
				}
				var vRecords = grid.getStore().getModifiedRecords(); // 得到修改的数据（记录对象）
		
				var vCount = vRecords.length; // 得到记录长度
		
				var vDatas = '';
				if (vCount > 0) {
					// begin 将记录对象转换为字符串（json格式的字符串）
					for ( var i = 0; i < vCount; i++) {
					var str = Ext.util.JSON.encode(vRecords[i].data);
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
			var gridsave=this.gridPanel;
			var profitRateMaintenanceStr=getData(this.gridPanel)
			 Ext.Ajax.request({
                   url:  __ctxPath + '/creditFlow/finance/saveProfitRateMaintenance.do',
                   method : 'POST',
                   params : {
				            profitRateMaintenanceStr : profitRateMaintenanceStr
						},
                  success : function(response,request) {

						var xx=response.responseText.toString().trim();
                		if(xx=="{success:true}"){
                		
                			Ext.ux.Toast.msg('操作信息',"保存成功");
                			gridsave.getStore().reload()
                			//gridsave.getView().refrush()
                		}else{
                			Ext.ux.Toast.msg('操作信息',"保存失败");
                		
                		} 
                  }
                 });  
		}
	});
