var getOptionGridDate1 = function(grid) {
	if (typeof(grid) == "undefined" || null == grid) {
		return "";
	}
	var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
	var vCount = vRecords.length; // 得到记录长度
	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for (var i = 0; i < vCount; i++) {
			var data=vRecords[i].data;
			if(i!=0){
				var preData=vRecords[i-1].data;
				if(Number(preData.optionEnd)+1!=Number(data.optionStart)){
					vDatas=null;
					alert('第('+(i+1)+')行的开始数值按照规则必须是('+(Number(preData.optionEnd)+1)+'),请重新填写!');
					break;
				}
			}
			if(data.optionEnd && Number(data.optionStart)>Number(data.optionEnd)){
				vDatas=null;
				alert('第('+(i+1)+')行的结束数值不能小于开始数值,请重新填写!');
				break;
			}
			if(i==vCount-1){
				if(!(data.optionEnd==="")){
					vDatas=null;
					alert('最后一行的结束数值必须为空,请重新填写!');
					break;
				}
			}
			vDatas += Ext.util.JSON.encode(data) + '@';
		}
		if(vDatas){
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
	}
	return vDatas;
};

OptionList1 = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor:'100%',
	isHidden:false,
	constructor : function(_cfg) {
        if(typeof(_cfg.isHidden)!="undefined"){
        	this.isHidden=_cfg.isHidden
        }
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		OptionList1.superclass.constructor.call(this, {
			items : [this.gPanel_option]
		})
	},
	initUIComponents : function() {
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
		this.button_addOption = new Ext.Button({
			text : '增加',
			tooltip : '增加一条新的选项',
			iconCls : 'addIcon',
			scope : this,
			handler : function() {
				var flag=false;
		    	var gridadd = this.gPanel_option;
				var storeadd = this.gPanel_option.getStore();
				//首次新增不进行验证
				if(storeadd.getCount()>0){
				 	var temp=storeadd.data.items[storeadd.getCount()-1];
				 	if(temp.data.optionStart>temp.data.optionEnd){
				 		Ext.ux.Toast.msg('操作信息','第'+(storeadd.getCount())+'行的内容不符合规则,请重新填写!');
				 	}else{
				 		flag=true;
				 	}
				 }else{
				 	flag=true;
				 }
				 if(flag){
				 	 var keys = storeadd.fields.keys;
					 var p = new Ext.data.Record();
					 p.data = {};
					 var count = storeadd.getCount() + 1;
					 for ( var i = 1; i < keys.length; i++) {
					 	if(count==1){
							p.data[keys[2]] = 0;
					 	}else{
					 		var optionEnd=storeadd.data.items[count-2].data.optionEnd;
					 		if(optionEnd){
					 			p.data[keys[2]] = Number(optionEnd)+1;
					 		}else{
					 			Ext.ux.Toast.msg('操作信息','请在第'+(count-1)+'行的内容填写完整后再增加!');
					 			return;
					 		}
					 	}
						p.data[keys[i]] = '';
						p.data[keys[1]] = 0;
						p.data[keys[4]] = 1;
					 }
					 gridadd.stopEditing();
					 storeadd.addSorted(p);
					 gridadd.getView().refresh();
				 }
			}
		});
			
		this.button_deleteOption = new Ext.Button({
			text : '删除选项',
			tooltip : '删除选中的指标选项',
			iconCls : 'deleteIcon',
			scope : this,
			disabled : false,
			handler : function() {
				var griddel = this.gPanel_option;
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
								if (row.data.id == null || row.data.id == '') {
									storedel.remove(row);
									griddel.getView().refresh();
								} else {
									deleteFun(__ctxPath+'/creditFlow/creditmanagement/deleteRsOption.do',
										{
											id :row.data.id
										},function() {},i,s.length)
								}
								storedel.remove(row);
								griddel.getView().refresh();
							}
						}
				});
			}
		});
			
		this.jStore_option = new Ext.data.JsonStore({
			url :  __ctxPath+'/creditFlow/creditmanagement/list1Option.do',
			totalProperty : 'totalProperty',
			root : 'topics',
			fields : [{
					name : 'id'
				},{
					name : 'sortNo'
				}, {
					name : 'optionStart'
				}, {
					name : 'optionEnd'
				}, {
					name : 'score'
				}]
		});
		
		this.cModel_option = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({header : "序号",width : 40}),{
				header : "开始数值",
				width : 200,
				sortable : true,
				dataIndex : 'optionStart',
				editor:new Ext.form.NumberField({
				   allowNegative : false,
				   readOnly:this.isHidden,
				   listeners:{
				   	scope : this,
				  	  'blur':function(nf){
				  	  		var flag=true;
				  	  		var rd = this.gPanel_option.getSelectionModel().getSelections()[0].data;
				  	  		if(rd.optionEnd && rd.optionEnd<=nf.getValue()){
				  	  			Ext.ux.Toast.msg('操作信息','开始数值必须小于结束数值!');
				  	  			nf.setValue(nf.startValue);
				  	  			flag=false;
				  	  		}else if(this.gPanel_option.getSelectionModel().last!=0){
				  	  			if(nf.getValue()<this.gPanel_option.getStore().data.items[this.gPanel_option.getSelectionModel().last-1].data.optionEnd){
					  	  			Ext.ux.Toast.msg('操作信息','当前记录的开始数值必须大于等于前一行的结束数值!');
					  	  			nf.setValue(nf.startValue);
					  	  			flag=false;
				  	  			}
				  	  		}
				  	  		if(flag){
				  	  			this.gPanel_option.stopEditing();
				  	  		}
				  	  }
				   }
				})
			},{
				header : "结束数值",
				width : 200,
				sortable : true,
				dataIndex : 'optionEnd',
				editor:new Ext.form.NumberField({
				   allowNegative : false,
				   readOnly:this.isHidden,
				   listeners:{
				   	  scope : this,
				  	  'blur':function(nf){
				  	  		var flag=true;
				  	  		var rd = this.gPanel_option.getSelectionModel().getSelections()[0].data;
				  	  		if(nf.getValue() && rd.optionStart && rd.optionStart>=nf.getValue()){
				  	  			Ext.ux.Toast.msg('操作信息','结束数值必须大于开始数值!');
				  	  			nf.setValue(Number(nf.startValue));
				  	  			flag=false;
				  	  		}
				  	  		if(flag){
				  	  			this.gPanel_option.stopEditing();
				  	  		}
				  	  }
				   }
				})
			}, {
				header : "分值",
				width : 40,
				sortable : true,
				dataIndex : 'score',
				editor:{
					xtype:'combo',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'id',
				    readOnly:this.isHidden,
				    store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["1", "1"],
								["2", "2"],
								["3", "3"],
								["4", "4"],
								["5", "5"],
								["6", "6"],
								["7", "7"],
								["8", "8"],
								["9", "9"],
								["10", "10"]]
					}),
					triggerAction : "all",
					hiddenName:"score",
			        anchor : "100%",
			        listeners:{
				  	 	scope : this,
					  	'select':function(nf){
					  		var store =this.gPanel_option.getStore();
					  		var count =store.getCount();
					  		if(count>1){
					  			for(i=0;i<count-1;i++){
					  				if(eval(nf.getValue())==eval(this.gPanel_option.getStore().getAt(i).data.score)){
					  					this.gPanel_option.getSelectionModel().getSelected().data['score'] =null
					  					this.gPanel_option.getView().refresh()
					  					break;
					  				}
					  			}
					  		}
					  	}
					}
				}
			}
		]);
				
		this.gPanel_option = new Ext.grid.EditorGridPanel({
			name : 'gPanel_option',
			store : this.jStore_option,
			selModel : new Ext.grid.RowSelectionModel(),
			colModel : this.cModel_option,
			autoHeight : true,
			clicksToEdit :1,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			tbar : (this.isHidden==false)?[this.button_addOption, this.button_deleteOption]:null
		});
        this.gPanel_option.addListener('cellclick', this.cellClick);
	},
	cellClick:function(grid,rowIndex,columnIndex,e){
  		/*if(3==columnIndex){
	  	    var optionName=grid.getStore().getAt(rowIndex-1).get('optionName');
	  	    var optionName1=grid.getStore().getAt(rowIndex).get('optionName')
	  	    var rec=grid.getStore().getAt(rowIndex-1)
	  	    var rec1=grid.getStore().getAt(rowIndex);
	  	    rec.set('optionName',optionName1);
	  	    rec1.set('optionName',optionName);
	  	    rec.commit();
	  	    rec1.commit()
	  	}
	  	if(4==columnIndex){
	  	    var optionName=grid.getStore().getAt(rowIndex+1).get('optionName');
	  	    var optionName1=grid.getStore().getAt(rowIndex).get('optionName')
	  	    var rec=grid.getStore().getAt(rowIndex+1)
	  	    var rec1=grid.getStore().getAt(rowIndex);
	  	    rec.set('optionName',optionName1);
	  	    rec1.set('optionName',optionName);
	  	    rec.commit();
	  	    rec1.commit()
	  	}*/
 	}
});