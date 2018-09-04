/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnItemsListView = Ext.extend(Ext.Panel, {
	// 构造函数
	isHandleHidden : true,
	isHandleEdit : true,
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnItemsListView.superclass.constructor.call(this, {
			layout : 'anchor',
			anchor : "100",
			autoWidth:true,
			items : [ 
					{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',hidden : this.isHiddenTitle,html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>'},
					this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {


		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-add',
				text : '增加当物',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : this.createRs
			}, {
				iconCls : 'btn-edit',
				text : '编辑当物',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenEdiBtn,
				handler : this.editRs
			}, {
				iconCls : 'btn-del',
				text : '删除当物',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenDelBtn,
				handler : this.removeSelRs
			}, {
				iconCls : 'btn-readdocument',
				text : '查看详细',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenSeeBtn,
				handler : this.seeRs
			}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			showPaging:false,
			autoHeight : true,
			url : __ctxPath + "/creditFlow/pawn/pawnItems/listPawnItemsList.do?projectId="+this.projectId+"&businessType="+this.businessType,
			fields : [ {
				name : 'pawnItemId',
				type : 'long'
			}, 'pawnItemType', 'pawnItemName', 'specificationsStatus', 'counts',
					'assessedValuationValue', 'discountRate', 'pawnItemMoney', 'accessTime', 'remarks','fileCount'],
			columns : [ {
				header : 'id',
				dataIndex : 'pawnItemId',
				hidden : true
			}, {
				header : '当物名称',
				dataIndex : 'pawnItemName'
			}, {
				header : '规格和状态',
				dataIndex : 'specificationsStatus'
			}, {
				header : '数量',
				dataIndex : 'counts'
			}, {
				header : '评估价值',
				dataIndex : 'assessedValuationValue'
			}, {
				header : '折当率',
				dataIndex : 'discountRate'
			}, {
				header : '当物金额',
				dataIndex : 'pawnItemMoney'
			}, {
				header : '材料份数',
				dataIndex : 'fileCount'
			}, {
				header : '备注',
				dataIndex : 'remarks'
			}]
		//end of columns
				});
	},
	//创建记录
	createRs : function() {
		Ext.Ajax.request({
			url : __ctxPath + '/creditFlow/pawn/pawnItems/deleteMaterialsPawnItemsList.do',
			method : 'POST',
			scope : this,
			success : function(response, request) {
				new AddPawnItemsWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel}).show()
			},
			failure : function(response) {
				Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
			}
		})
		
		
	},

	//把选中ID删除
	removeSelRs : function() {
		
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择记录!');
				}else{
					var ids = "";
					//var typeIds = "";
					var rows = this.gridPanel.getSelectionModel().getSelections();
					for(var i=0;i<rows.length;i++){
						ids = ids+rows[i].get('pawnItemId');
						
						if(i!=rows.length-1){
							ids = ids+',';
						}
					}
					Ext.MessageBox.confirm('确认删除', '该记录在附表同时存在相应记录,您确认要一并删除? ', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath +'/creditFlow/pawn/pawnItems/multiDelPawnItemsList.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
									
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('操作信息', '删除失败!');
								},
								params: { 
									ids: ids
								}
							});
						}
					});
				}
			
	},
	//编辑Rs
	editRs: function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{
			record=s[0]
			new UpdatePawnItemsWin({id:record.data.pawnItemId,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.pawnItemType}).show()
		}	
	},
	seeRs :function(record) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var pawnItemId = selected.get('pawnItemId');
		var typeId = selected.get('pawnItemType');
			if(typeId==1){
				seeVehicleInfo(pawnItemId,typeId);
			}else if(typeId==2){
				seeStockownership(pawnItemId,typeId);
			}else if(typeId==5){
				seeMachine(pawnItemId,typeId);
			}else if(typeId==6){
				seeProduct(pawnItemId,typeId);
			}else if(typeId==7 || typeId==15 || typeId==16 || typeId==17){
				seeHouse(pawnItemId,typeId);
			}else if(typeId==8){
				seeOfficeBuilding(pawnItemId,typeId);
			}else if(typeId==9){
				seeHouseGround(pawnItemId,typeId);
			}else if(typeId==10){
				seeBusiness(pawnItemId,typeId);
			}else if(typeId==11){
				seeBusinessAndLive(pawnItemId,typeId);
			}else if(typeId==12){
				seeEducation(pawnItemId,typeId);
			}else if(typeId==13){
				seeIndustry(pawnItemId,typeId);
			}else if(typeId==14){
				seeDroitUpdate(pawnItemId,typeId);
			}else{
				window.location.href="/error.jsp";
			}
		}
	}
	
});
