/**
 * @author
 * @class OurProcreditAssuretenetView
 * @extends Ext.Panel
 * @description [OurProcreditAssuretenet]管理
 * @company 智维软件
 * @createtime:
 */
OurProcreditAssuretenetGuaranteeView = Ext.extend(Ext.Panel, {
	// 构造函数
	//businessTypeName : "",
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.businessType)!="undefind"){
        	this.businessType=_cfg.businessType;
        	if(this.businessType=="Guarantee"){
        		this.businessTypeName=="金融担保";
        	}else{
        		this.businessTypeName=="小额贷款";
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
		OurProcreditAssuretenetGuaranteeView.superclass.constructor.call(this, {
			id : 'OurProcreditAssuretenetGuaranteeView'+this.businessType,
			title : this.businessType=="Guarantee"?'担保准入原则管理':'基础贷款条件管理',
			region : 'center',
			layout : 'border',
			iconCls:"menu-finance",
			items : [this.gridPanel]
		});
	},// end of constructor
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '添加准入原则',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_create_zr')?false:true,
				handler : this.createRs
			}, "-",{
				iconCls : 'btn-del',
				text : '删除准入原则',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_zr')?false:true,
				handler : this.removeSelRs
			}, "-",{
				iconCls : 'btn-edit',
				text : '编辑准入原则',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_edit_zr')?false:true,
				handler : this.editRs
			}]
		});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			id : 'OurProcreditAssuretenetGrid1'+this.businessType,
			//url : __ctxPath+ "/assuretenet/listOurProcreditAssuretenet.do?businessTypeKey="+this.businessTypeKey,
			url : __ctxPath+ "/assuretenet/listOurProcreditAssuretenet.do?businessType="+this.businessType,
			fields : [{
				name : 'assuretenetId',
				type : 'int'
			}, 'assuretenet',"businessTypeName","operationTypeName","businessTypeKey","businessTypeGlobalId","operationTypeGlobalId","customerType"],
			columns : [{
				header : 'assuretenetId',
				dataIndex : 'assuretenetId',
				hidden : true
			}, {
				header : '准入原则名称',
				width : 300,
				dataIndex : 'assuretenet'
			}/*, {
				header : '客户类型',
				dataIndex : 'customerType',
				renderer : function(v){
					if(v==null||v==""||v=="null"){
						return '';
					}else if(v=="company"){
						return '企业';
					}else{
						return '个人';
					}
				}
			}, {
				header : '业务类别',
				dataIndex : 'businessTypeName'
			},{
				header : '业务品种',
				dataIndex : 'operationTypeName'
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
	rowClick : function(grid, rowindex, e) {
       grid.getSelectionModel().each(function(rec) {
			new OurProcreditAssuretenetForm({
				assuretenetId : rec.data.assuretenetId
			}).show();
		});
	},
	// 创建记录
	createRs : function() {
		new OurProcreditAssuretenetForm({businessType:this.businessType,businessTypeName:this.businessType=="Guarantee"?"金融担保":"小额贷款",operateGrid:this.get(0)}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath + '/assuretenet/multiDelOurProcreditAssuretenet.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath + '/assuretenet/multiDelOurProcreditAssuretenet.do',
			grid : this.gridPanel,
			idName : 'assuretenetId'
		});
	},
	// 编辑Rs
	editRs : function() {
        var grid=this.gridPanel;
     	var store=grid.getStore();
     	var s = this.gridPanel.getSelectionModel().getSelections();
 		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.Msg.alert('状态','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.Msg.alert('状态','只能选中一条记录');
			return false;
		}else{
		   var rec=s[0];
		   var assuretenetId =rec.data.assuretenetId;
		   var businessTypeName =rec.data.businessTypeName;
		   new OurProcreditAssuretenetForm({
				assuretenetId : assuretenetId,businessTypeName:businessTypeName,businessTypeKey:this.businessTypeKey,operateGrid:this.get(0)
			}).show();
		}
	}
});
