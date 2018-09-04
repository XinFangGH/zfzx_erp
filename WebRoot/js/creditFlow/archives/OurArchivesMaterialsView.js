/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
OurArchivesMaterialsView = Ext.extend(Ext.Panel, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(typeof(_cfg.businessType)!="undefind"){
		        	this.businessType=_cfg.businessType;
		        	if(this.businessType=="Guarantee"){
		        		this.businessTypeName=="金融担保";
		        	}else if(this.businessType=="LeaseFinance"){
		        		this.businessTypeName=="融资租赁";
		        	}else if(this.businessType=="Pawn"){
		        		this.businessTypeName=="典当";
		        	}
		        }
		  	this.isGranted = function(a){
			var b = "";
			if("LeaseFinance"==this.businessType){
				b="_"+this.businessType;
			}else if(this.businessType == "SmallLoan"){
				b="_"+this.businessType;
			}
		    if(this.plat=='p2p'){
		    	return isGranted(a+b+"_p2p");
		    }else{
		    	return isGranted(a+b);
		    }
			
		}
				this.initUIComponents();
				OurArchivesMaterialsView.superclass.constructor.call(this, {
					id : 'OurArchivesMaterialsView'+this.businessType,
					title : (this.businessType=="Guarantee"?'担保归档材料管理':(this.businessType=="LeaseFinance"?'租赁归档材料管理':(this.businessType=="Pawn"?'典当归档材料管理':'基础贷款归档材料管理'))),
					region : 'center',
					layout : 'border',
					iconCls:"menu-finance",
					items : [this.gridPanel]
				});
			},
			initUIComponents : function() {
				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-add',
						text : '添加归档材料',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_create_archives')?false:true,
						handler : this.createRs
					}, "-",{
						iconCls : 'btn-del',
						text : '删除归档材料',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_remove_archives')?false:true,
						handler : this.removeSelRs
					}, "-",{
						iconCls : 'btn-edit',
						text : '编辑归档材料',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_edit_dcarchives')?false:true,
						handler : this.editRs
					}]
				});
               // var businessType=this.businessType;
				this.gridPanel = new HT.GridPanel({
					region : 'center',
					id : 'OurArchivesMaterialsViewGrid'+this.businessType,
					tbar : this.topbar,
					url : __ctxPath + "/creditFlow/archives/listOurArchivesMaterials.do?businessType="+this.businessType,
					fields : [{
								name : 'materialsId',
								type : 'int'
							}, 'materialsName','operationTypeName','isPublic','businessTypeKey','businessTypeName','businessTypeGlobalId','operationTypeGlobalId'],
					columns : [{
						header : 'materialsId',
						dataIndex : 'materialsId',
						hidden : true
					}, {
						header : '归档材料名称',
						dataIndex : 'materialsName'
					}/*, {
						header : '业务类别',
						dataIndex:'businessTypeName'
					}, {
						header : '业务品种',
						dataIndex : 'operationTypeName'
					}, {
					    header : '是否必备',
					    dataIndex :  'isPublic',
					    renderer:function(value){
					    	if(value==1){
					    		return '必备'
					    	}else if(value==2){
					    		return '可选'
					    	}else{
					    		return ''
					    	}
					    }
					}*/]
				});
			},
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			search : function() {
				$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
			},
			createRs : function() {
				var grid = this.gridPanel;
				new OurArchivesMaterialsForm({
					businessType:this.businessType,
					businessTypeName:(this.businessType=="Guarantee"?'融资担保':(this.businessType=="LeaseFinance"?'融资租赁':(this.businessType=="Pawn"?'典当':'小额贷款'))),
					operateGrid:grid
				}).show();
			},
			removeRs : function(id) {
				$postDel({
					url : __ctxPath + "/creditFlow/archives/multiDelOurArchivesMaterials.do",
					ids : id,
					grid : this.gridPanel
				});
			},
			removeSelRs : function() {
		      $delGridRs({
					url : __ctxPath + "/creditFlow/archives/multiDelOurArchivesMaterials.do",
					grid : this.gridPanel,
					idName : 'materialsId'
				});
			},
			editRs : function() {
			    var grid=this.gridPanel;
		     	var store=grid.getStore();
		     	var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.Msg.alert('状态','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.Msg.alert('状态','只能选中一条记录');
					return false;
				}else{
				    var rec=s[0];
				    var materialsId =rec.data.materialsId;
				    var businessTypeName =rec.data.businessTypeName;
					new OurArchivesMaterialsForm({
						materialsId : materialsId,
						operateGrid:this.get(0),
						businessTypeName:businessTypeName
					}).show();
				}
			}
		});
