SlUrgeRecord = Ext.extend(Ext.Panel,{
	constructor:function(_cfg){
		if(_cfg==null){
			_cfg = {};
		}
		Ext.applyIf(this,_cfg);
		this.inintUIComponents();
		SlUrgeRecord.superclass.constructor.call(this,{
			layout:"anchor",
			items:[this.gridPanel]
		});
	},
	inintUIComponents:function(){
		this.ttbar = new Ext.Toolbar({
			items : [{
					text : '增加',
					iconCls : 'btn-add',
					scope : this,
					handler : this.createRs
			}, '-', {
					iconCls : 'btn-del',
					scope : this,
					text : '删除',
					handler : this.removeSelRs
			}]
		});
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),this.sm,{
				header:'recordId',
				hidden:true,
				dataIndex:'recordId'
			},{
				header:'催收人',
				dataIndex:'creatorName',
				width: 100,
				fixed:true,
				editor:{
					readOnly:true,
					xtype:'textfield'
				}
			},{
				header:'催收时间',
				dataIndex:'insertDate',
				width: 150,
				fixed:true,
				editor:{
					readOnly:this.readOnly,
					xtype:'datefield',
					format:'Y-m-d'
				},
				renderer:function(value){
					return Ext.util.Format.date(value, 'Y-m-d');
				}
			},{
				header:'催收说明',
				dataIndex:'recordText',
				editor :{
					readOnly:this.readOnly,
					xtype:'textfield'
				} 
			},{
				header:'findIntentId',
				hidden:true,
				dataIndex:'findIntentId'
			}
		]);
		this.store = new Ext.data.JsonStore({
			autoLoad:true,
			url:__ctxPath + '/creditFlow/smallLoan/finance/listSlUrgeRecord.do',
			root:'result',
			fields:[{
				name:'recordId',
				type:'int'
			},'creatorName','insertDate','recordText','findIntentId','creatorId'],
			baseParams :{fundIntentId:this.slFundIntentId}
		});
		
		this.gridPanel = new Ext.grid.EditorGridPanel({
			 frame:true,
			 tbar :this.readOnly==null?this.ttbar:null,
			 width:'100',
			 autoHeight:true,
			 clicksToEdit : 1,
			 store:this.store,
			 sm:this.sm,
			 cm:this.cm,
			 viewConfig:{
			 	forceFit: true
			 }
		});
	},
	createRs:function(){
			var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			p.data[keys[0]] = null;
			p.data[keys[1]] = curUserInfo.fullname;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = this.slFundIntentId;
			p.data[keys[5]] = curUserInfo.userId;
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
	},
	getData:function(){
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			var st = "";
			for (var i = 0; i < vCount; i++) {
				st = {
					"recordId":vRecords[i].data.recordId,
					"insertDate":vRecords[i].data.insertDate,
					"recordText":vRecords[i].data.recordText,
					"findIntentId":vRecords[i].data.findIntentId,
					"creatorId":vRecords[i].data.creatorId
				};
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	
	}
});