/** 企业-财务信息--jiang*/
var obj;
var financeInfo = function(orgVal,isReadOnly){
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal},  
    	success: function(response, option) {
    		obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
        	financeInfo_(enterpriseId,isReadOnly);
    	}
	});
}

//财务信息
var financeInfo_ = function(enterpriseId,isReadOnly){
	
	var panel_;
	var win_;
	var jStore_;
	
	var html_ = '<form method="post" name="myForm1" id="myForm1" action=""> ' +
		'<table id ="financeTable" width="98%" height="98%" border="2" cellspacing="0" bordercolor="white" align="center">'+
		  '<tr bgcolor="yellow">'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;" >指标类别</td>'+
		    '<td class="div_mid"  style="font-size:12px;text-align:center;" width="20%">指标名称</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;" style="font-size:12px;text-align:center;">前半年/季度报表</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">前一年报表</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">前两年报表</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">前三年报表</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">备注</td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">其它</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">报表期间</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx128" id="id_xxxx_xxxx128" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx127" id="id_xxxx_xxxx127" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx126" id="id_xxxx_xxxx126" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx125" id="id_xxxx_xxxx125" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx200" id="id_xxxx_xxxx200" /></td>'+//200--231备注
		  '</tr>'+
		  '<tr>'+
		    '<td rowspan="10" class="div_mid" style="font-size:12px;text-align:center;">偿债能力</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">总资产(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx124" id="id_xxxx_xxxx124" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx123" id="id_xxxx_xxxx123" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx122" id="id_xxxx_xxxx122" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx121" id="id_xxxx_xxxx121" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx201" id="id_xxxx_xxxx201" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">净资产(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx120" id="id_xxxx_xxxx120" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx119" id="id_xxxx_xxxx119" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx118" id="id_xxxx_xxxx118" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx117" id="id_xxxx_xxxx117" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx202" id="id_xxxx_xxxx202" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">流动资产(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx116" id="id_xxxx_xxxx116" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx115" id="id_xxxx_xxxx115" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx114" id="id_xxxx_xxxx114" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx113" id="id_xxxx_xxxx113" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx203" id="id_xxxx_xxxx203" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">流动负债(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx112" id="id_xxxx_xxxx112" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx111" id="id_xxxx_xxxx111" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx110" id="id_xxxx_xxxx110" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx109" id="id_xxxx_xxxx109" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx204" id="id_xxxx_xxxx204" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">营运资金(万元)</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx01" id="id_xxxx_xxxx01" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx02" id="id_xxxx_xxxx02" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx03" id="id_xxxx_xxxx03" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx04" id="id_xxxx_xxxx04" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx205" id="id_xxxx_xxxx205" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">流动比率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx05" id="id_xxxx_xxxx05" readonly="readonly" class="readOnlyClass "/></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx06" id="id_xxxx_xxxx06" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx07" id="id_xxxx_xxxx07" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx08" id="id_xxxx_xxxx08" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx206" id="id_xxxx_xxxx206" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">速动比率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx09" id="id_xxxx_xxxx09" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx10" id="id_xxxx_xxxx10" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx11" id="id_xxxx_xxxx11" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx12" id="id_xxxx_xxxx12" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx207" id="id_xxxx_xxxx207" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">资产与负债率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx13" id="id_xxxx_xxxx13" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx14" id="id_xxxx_xxxx14" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx15" id="id_xxxx_xxxx15" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx16" id="id_xxxx_xxxx16" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx208" id="id_xxxx_xxxx208" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">负债与所有者权益比例</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx17" id="id_xxxx_xxxx17" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx18" id="id_xxxx_xxxx18" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx19" id="id_xxxx_xxxx19" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx20" id="id_xxxx_xxxx20" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx209" id="id_xxxx_xxxx209" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">利息保障倍数</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx21" id="id_xxxx_xxxx21" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx22" id="id_xxxx_xxxx22" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx23" id="id_xxxx_xxxx23" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx24" id="id_xxxx_xxxx24" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx210" id="id_xxxx_xxxx210" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td rowspan="15" class="div_mid" style="font-size:12px;text-align:center;">盈利能力</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">主营业务收入(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx25" id="id_xxxx_xxxx25" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx26" id="id_xxxx_xxxx26" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx27" id="id_xxxx_xxxx27" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx28" id="id_xxxx_xxxx28" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx211" id="id_xxxx_xxxx211" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">主营业务成本(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx29" id="id_xxxx_xxxx29" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx30" id="id_xxxx_xxxx30" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx31" id="id_xxxx_xxxx31" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx32" id="id_xxxx_xxxx32" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx212" id="id_xxxx_xxxx212" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">营业费用(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx33" id="id_xxxx_xxxx33" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx34" id="id_xxxx_xxxx34" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx35" id="id_xxxx_xxxx35" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx36" id="id_xxxx_xxxx36" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx213" id="id_xxxx_xxxx213" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">管理费用(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx37" id="id_xxxx_xxxx37" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx38" id="id_xxxx_xxxx38" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx39" id="id_xxxx_xxxx39" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx40" id="id_xxxx_xxxx40" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx214" id="id_xxxx_xxxx214" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">财务费用(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx41" id="id_xxxx_xxxx41" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx42" id="id_xxxx_xxxx42" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx43" id="id_xxxx_xxxx43" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx44" id="id_xxxx_xxxx44" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx215" id="id_xxxx_xxxx215" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">营业利润(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx45" id="id_xxxx_xxxx45" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx46" id="id_xxxx_xxxx46" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx47" id="id_xxxx_xxxx47" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx48" id="id_xxxx_xxxx48" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx216" id="id_xxxx_xxxx216" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">利润总额(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx49" id="id_xxxx_xxxx49" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx50" id="id_xxxx_xxxx50" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx51" id="id_xxxx_xxxx51" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx52" id="id_xxxx_xxxx52" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx217" id="id_xxxx_xxxx217" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">净利润(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx53" id="id_xxxx_xxxx53" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx54" id="id_xxxx_xxxx54" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx55" id="id_xxxx_xxxx55" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx56" id="id_xxxx_xxxx56" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx218" id="id_xxxx_xxxx218" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">毛利率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx57" id="id_xxxx_xxxx57" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx58" id="id_xxxx_xxxx58" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx59" id="id_xxxx_xxxx59" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx60" id="id_xxxx_xxxx60" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx219" id="id_xxxx_xxxx219" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">营业利润率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx61" id="id_xxxx_xxxx61" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx62" id="id_xxxx_xxxx62" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx63" id="id_xxxx_xxxx63" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx64" id="id_xxxx_xxxx64" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx220" id="id_xxxx_xxxx220" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">销售利润率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx65" id="id_xxxx_xxxx65" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx66" id="id_xxxx_xxxx66" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx67" id="id_xxxx_xxxx67" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx68" id="id_xxxx_xxxx68" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx221" id="id_xxxx_xxxx221" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">净利润率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx69" id="id_xxxx_xxxx69" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx70" id="id_xxxx_xxxx70" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx71" id="id_xxxx_xxxx71" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx72" id="id_xxxx_xxxx72" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx222" id="id_xxxx_xxxx222" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">净资产收益率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx73" id="id_xxxx_xxxx73" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx74" id="id_xxxx_xxxx74" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx75" id="id_xxxx_xxxx75" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx76" id="id_xxxx_xxxx76" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx223" id="id_xxxx_xxxx223" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">成本费用利润率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx77" id="id_xxxx_xxxx77" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx78" id="id_xxxx_xxxx78" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx79" id="id_xxxx_xxxx79" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx80" id="id_xxxx_xxxx80" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx224" id="id_xxxx_xxxx224" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">总资产报酬率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx81" id="id_xxxx_xxxx81" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx82" id="id_xxxx_xxxx82" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx83" id="id_xxxx_xxxx83" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx84" id="id_xxxx_xxxx84" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx225" id="id_xxxx_xxxx225" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td rowspan="6" class="div_mid" style="font-size:12px;text-align:center;">营运能力</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">存货(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx85" id="id_xxxx_xxxx85" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx86" id="id_xxxx_xxxx86" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx87" id="id_xxxx_xxxx87" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx88" id="id_xxxx_xxxx88" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx226" id="id_xxxx_xxxx226" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;">应收账款(万元)</td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx89" id="id_xxxx_xxxx89" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx90" id="id_xxxx_xxxx90" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx91" id="id_xxxx_xxxx91" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx92" id="id_xxxx_xxxx92" /></td>'+
		    '<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx227" id="id_xxxx_xxxx227" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">总资产周转率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx93" id="id_xxxx_xxxx93" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx94" id="id_xxxx_xxxx94" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx95" id="id_xxxx_xxxx95" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx96" id="id_xxxx_xxxx96" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx228" id="id_xxxx_xxxx228" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">存货周转率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx97" id="id_xxxx_xxxx97" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx98" id="id_xxxx_xxxx98" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx99" id="id_xxxx_xxxx99" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx100" id="id_xxxx_xxxx100" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx229" id="id_xxxx_xxxx229" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">应收账款周转率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx101" id="id_xxxx_xxxx101" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx102" id="id_xxxx_xxxx102" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx103" id="id_xxxx_xxxx103" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx104" id="id_xxxx_xxxx104" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx230" id="id_xxxx_xxxx230" /></td>'+
		  '</tr>'+
		  '<tr>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;">净资产周转率</td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx105" id="id_xxxx_xxxx105" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx106" id="id_xxxx_xxxx106" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx107" id="id_xxxx_xxxx107" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx108" id="id_xxxx_xxxx108" readonly="readonly" class="readOnlyClass" /></td>'+
		    '<td bgcolor="#FFFF00" class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxxx_xxxx231" id="id_xxxx_xxxx231" /></td>'+
		  '</tr>'+
		'</table>' +
		'</form>';
	
	var fnCalculate = function(){
			
			//营运资金
			var yyzj2010s = getYyzj(Ext.get('id_xxxx_xxxx116').getValue(),Ext.get('id_xxxx_xxxx112').getValue());//运营资金
			var yyzj2009 = getYyzj(Ext.get('id_xxxx_xxxx115').getValue(),Ext.get('id_xxxx_xxxx111').getValue());//运营资金
			var yyzj2008 = getYyzj(Ext.get('id_xxxx_xxxx114').getValue(),Ext.get('id_xxxx_xxxx110').getValue());//运营资金
			var yyzj2007 = getYyzj(Ext.get('id_xxxx_xxxx113').getValue(),Ext.get('id_xxxx_xxxx109').getValue());//运营资金
			
			document.getElementById('id_xxxx_xxxx01').value=yyzj2010s;
			document.getElementById('id_xxxx_xxxx02').value=yyzj2009;
			document.getElementById('id_xxxx_xxxx03').value=yyzj2008;
			document.getElementById('id_xxxx_xxxx04').value=yyzj2007;
			
			//流动比率
			var ldbl2010s = getLdbl(Ext.get('id_xxxx_xxxx116').getValue(),Ext.get('id_xxxx_xxxx112').getValue());
			var ldbl2009 = getLdbl(Ext.get('id_xxxx_xxxx115').getValue(),Ext.get('id_xxxx_xxxx111').getValue());//
			var ldbl2008 = getLdbl(Ext.get('id_xxxx_xxxx114').getValue(),Ext.get('id_xxxx_xxxx110').getValue());//
			var ldbl2007 = getLdbl(Ext.get('id_xxxx_xxxx113').getValue(),Ext.get('id_xxxx_xxxx109').getValue());//
			
			document.getElementById('id_xxxx_xxxx05').value=ldbl2010s;
			document.getElementById('id_xxxx_xxxx06').value=ldbl2009;
			document.getElementById('id_xxxx_xxxx07').value=ldbl2008;
			document.getElementById('id_xxxx_xxxx08').value=ldbl2007;
			
			//速动比率
			var sdbl2010s = getSdbl(Ext.get('id_xxxx_xxxx116').getValue(),Ext.get('id_xxxx_xxxx85').getValue(),Ext.get('id_xxxx_xxxx112').getValue());
			var sdbl2009 = getSdbl(Ext.get('id_xxxx_xxxx115').getValue(),Ext.get('id_xxxx_xxxx86').getValue(),Ext.get('id_xxxx_xxxx111').getValue());//
			var sdbl2008 = getSdbl(Ext.get('id_xxxx_xxxx114').getValue(),Ext.get('id_xxxx_xxxx87').getValue(),Ext.get('id_xxxx_xxxx110').getValue());//
			var sdbl2007 = getSdbl(Ext.get('id_xxxx_xxxx113').getValue(),Ext.get('id_xxxx_xxxx88').getValue(),Ext.get('id_xxxx_xxxx109').getValue());//
			
			document.getElementById('id_xxxx_xxxx09').value=sdbl2010s;
			document.getElementById('id_xxxx_xxxx10').value=sdbl2009;
			document.getElementById('id_xxxx_xxxx11').value=sdbl2008;
			document.getElementById('id_xxxx_xxxx12').value=sdbl2007;
			
			//资产负债率
			var zcfzl2010s = getZcfzl(Ext.get('id_xxxx_xxxx120').getValue(),Ext.get('id_xxxx_xxxx124').getValue());
			var zcfzl2009 = getZcfzl(Ext.get('id_xxxx_xxxx119').getValue(),Ext.get('id_xxxx_xxxx123').getValue());//
			var zcfzl2008 = getZcfzl(Ext.get('id_xxxx_xxxx118').getValue(),Ext.get('id_xxxx_xxxx122').getValue());//
			var zcfzl2007 = getZcfzl(Ext.get('id_xxxx_xxxx117').getValue(),Ext.get('id_xxxx_xxxx121').getValue());//
			
			document.getElementById('id_xxxx_xxxx13').value=zcfzl2010s;
			document.getElementById('id_xxxx_xxxx14').value=zcfzl2009;
			document.getElementById('id_xxxx_xxxx15').value=zcfzl2008;
			document.getElementById('id_xxxx_xxxx16').value=zcfzl2007;
			
			//负债与所有者权益比例
			var fzysyzqybl2010s = getFzysyzqybl(Ext.get('id_xxxx_xxxx120').getValue(),Ext.get('id_xxxx_xxxx124').getValue());
			var fzysyzqybl2009 = getFzysyzqybl(Ext.get('id_xxxx_xxxx119').getValue(),Ext.get('id_xxxx_xxxx123').getValue());//
			var fzysyzqybl2008 = getFzysyzqybl(Ext.get('id_xxxx_xxxx118').getValue(),Ext.get('id_xxxx_xxxx122').getValue());//
			var fzysyzqybl2007 = getFzysyzqybl(Ext.get('id_xxxx_xxxx117').getValue(),Ext.get('id_xxxx_xxxx121').getValue());//
			
			document.getElementById('id_xxxx_xxxx17').value=fzysyzqybl2010s;
			document.getElementById('id_xxxx_xxxx18').value=fzysyzqybl2009;
			document.getElementById('id_xxxx_xxxx19').value=fzysyzqybl2008;
			document.getElementById('id_xxxx_xxxx20').value=fzysyzqybl2007;
			
			//利息保障倍数
			var lxbzbs2010s = getLxbzbs(Ext.get('id_xxxx_xxxx49').getValue(),Ext.get('id_xxxx_xxxx41').getValue());
			var lxbzbs2009 = getLxbzbs(Ext.get('id_xxxx_xxxx50').getValue(),Ext.get('id_xxxx_xxxx42').getValue());//
			var lxbzbs2008 = getLxbzbs(Ext.get('id_xxxx_xxxx51').getValue(),Ext.get('id_xxxx_xxxx43').getValue());//
			var lxbzbs2007 = getLxbzbs(Ext.get('id_xxxx_xxxx52').getValue(),Ext.get('id_xxxx_xxxx44').getValue());//
			
			document.getElementById('id_xxxx_xxxx21').value=lxbzbs2010s;
			document.getElementById('id_xxxx_xxxx22').value=lxbzbs2009;
			document.getElementById('id_xxxx_xxxx23').value=lxbzbs2008;
			document.getElementById('id_xxxx_xxxx24').value=lxbzbs2007;
			
			//毛利率
			var mll2010s = getMll(Ext.get('id_xxxx_xxxx25').getValue(),Ext.get('id_xxxx_xxxx29').getValue());
			var mll2009 = getMll(Ext.get('id_xxxx_xxxx26').getValue(),Ext.get('id_xxxx_xxxx30').getValue());//
			var mll2008 = getMll(Ext.get('id_xxxx_xxxx27').getValue(),Ext.get('id_xxxx_xxxx31').getValue());//
			var mll2007 = getMll(Ext.get('id_xxxx_xxxx28').getValue(),Ext.get('id_xxxx_xxxx32').getValue());//
			
			document.getElementById('id_xxxx_xxxx57').value=mll2010s;
			document.getElementById('id_xxxx_xxxx58').value=mll2009;
			document.getElementById('id_xxxx_xxxx59').value=mll2008;
			document.getElementById('id_xxxx_xxxx60').value=mll2007;
			
			//营业利润率
			var yylrl2010s = getYylrl(Ext.get('id_xxxx_xxxx45').getValue(),Ext.get('id_xxxx_xxxx25').getValue());
			var yylrl2009 = getYylrl(Ext.get('id_xxxx_xxxx46').getValue(),Ext.get('id_xxxx_xxxx26').getValue());//
			var yylrl2008 = getYylrl(Ext.get('id_xxxx_xxxx47').getValue(),Ext.get('id_xxxx_xxxx27').getValue());//
			var yylrl2007 = getYylrl(Ext.get('id_xxxx_xxxx48').getValue(),Ext.get('id_xxxx_xxxx28').getValue());//
			
			document.getElementById('id_xxxx_xxxx61').value=yylrl2010s;
			document.getElementById('id_xxxx_xxxx62').value=yylrl2009;
			document.getElementById('id_xxxx_xxxx63').value=yylrl2008;
			document.getElementById('id_xxxx_xxxx64').value=yylrl2007;
			
			//销售利润率
			var xslrl2010s = getXslrl(Ext.get('id_xxxx_xxxx49').getValue(),Ext.get('id_xxxx_xxxx25').getValue());
			var xslrl2009 = getXslrl(Ext.get('id_xxxx_xxxx50').getValue(),Ext.get('id_xxxx_xxxx26').getValue());//
			var xslrl2008 = getXslrl(Ext.get('id_xxxx_xxxx51').getValue(),Ext.get('id_xxxx_xxxx27').getValue());//
			var xslrl2007 = getXslrl(Ext.get('id_xxxx_xxxx52').getValue(),Ext.get('id_xxxx_xxxx28').getValue());//
			
			document.getElementById('id_xxxx_xxxx65').value=xslrl2010s;
			document.getElementById('id_xxxx_xxxx66').value=xslrl2009;
			document.getElementById('id_xxxx_xxxx67').value=xslrl2008;
			document.getElementById('id_xxxx_xxxx68').value=xslrl2007;
			
			//净利润率
			var jlrl2010s = getJlrl(Ext.get('id_xxxx_xxxx53').getValue(),Ext.get('id_xxxx_xxxx25').getValue());
			var jlrl2009 = getJlrl(Ext.get('id_xxxx_xxxx54').getValue(),Ext.get('id_xxxx_xxxx26').getValue());//
			var jlrl2008 = getJlrl(Ext.get('id_xxxx_xxxx55').getValue(),Ext.get('id_xxxx_xxxx27').getValue());//
			var jlrl2007 = getJlrl(Ext.get('id_xxxx_xxxx56').getValue(),Ext.get('id_xxxx_xxxx28').getValue());//
			
			document.getElementById('id_xxxx_xxxx69').value=jlrl2010s;
			document.getElementById('id_xxxx_xxxx70').value=jlrl2009;
			document.getElementById('id_xxxx_xxxx71').value=jlrl2008;
			document.getElementById('id_xxxx_xxxx72').value=jlrl2007;
			
			//净资产收益率
			var jzcsyl2010s = getJzzsyl(Ext.get('id_xxxx_xxxx53').getValue(),Ext.get('id_xxxx_xxxx120').getValue());
			var jzcsyl2009 = getJzzsyl(Ext.get('id_xxxx_xxxx54').getValue(),Ext.get('id_xxxx_xxxx119').getValue());//
			var jzcsyl2008 = getJzzsyl(Ext.get('id_xxxx_xxxx55').getValue(),Ext.get('id_xxxx_xxxx118').getValue());//
			var jzcsyl2007 = getJzzsyl(Ext.get('id_xxxx_xxxx56').getValue(),Ext.get('id_xxxx_xxxx117').getValue());//
			
			document.getElementById('id_xxxx_xxxx73').value=jzcsyl2010s;
			document.getElementById('id_xxxx_xxxx74').value=jzcsyl2009;
			document.getElementById('id_xxxx_xxxx75').value=jzcsyl2008;
			document.getElementById('id_xxxx_xxxx76').value=jzcsyl2007;
			
			//成本费用利润率
			var cbfylrl2010s = getCbfylrl(Ext.get('id_xxxx_xxxx53').getValue(),Ext.get('id_xxxx_xxxx29').getValue(),Ext.get('id_xxxx_xxxx33').getValue(),Ext.get('id_xxxx_xxxx37').getValue(),Ext.get('id_xxxx_xxxx41').getValue());
			var cbfylrl2009 = getCbfylrl(Ext.get('id_xxxx_xxxx54').getValue(),Ext.get('id_xxxx_xxxx30').getValue(),Ext.get('id_xxxx_xxxx34').getValue(),Ext.get('id_xxxx_xxxx38').getValue(),Ext.get('id_xxxx_xxxx42').getValue());//
			var cbfylrl2008 = getCbfylrl(Ext.get('id_xxxx_xxxx55').getValue(),Ext.get('id_xxxx_xxxx31').getValue(),Ext.get('id_xxxx_xxxx35').getValue(),Ext.get('id_xxxx_xxxx39').getValue(),Ext.get('id_xxxx_xxxx43').getValue());//
			var cbfylrl2007 = getCbfylrl(Ext.get('id_xxxx_xxxx56').getValue(),Ext.get('id_xxxx_xxxx32').getValue(),Ext.get('id_xxxx_xxxx36').getValue(),Ext.get('id_xxxx_xxxx40').getValue(),Ext.get('id_xxxx_xxxx44').getValue());//
			
			document.getElementById('id_xxxx_xxxx77').value=cbfylrl2010s;
			document.getElementById('id_xxxx_xxxx78').value=cbfylrl2009;
			document.getElementById('id_xxxx_xxxx79').value=cbfylrl2008;
			document.getElementById('id_xxxx_xxxx80').value=cbfylrl2007;
			
			//总资产报酬率
			var zzcbcl2010s = getZzcbcl(Ext.get('id_xxxx_xxxx49').getValue(),Ext.get('id_xxxx_xxxx41').getValue(),Ext.get('id_xxxx_xxxx124').getValue());
			var zzcbcl2009 = getZzcbcl(Ext.get('id_xxxx_xxxx50').getValue(),Ext.get('id_xxxx_xxxx42').getValue(),Ext.get('id_xxxx_xxxx123').getValue());//
			var zzcbcl2008 = getZzcbcl(Ext.get('id_xxxx_xxxx51').getValue(),Ext.get('id_xxxx_xxxx43').getValue(),Ext.get('id_xxxx_xxxx122').getValue());//
			var zzcbcl2007 = getZzcbcl(Ext.get('id_xxxx_xxxx52').getValue(),Ext.get('id_xxxx_xxxx44').getValue(),Ext.get('id_xxxx_xxxx121').getValue());//
			
			document.getElementById('id_xxxx_xxxx81').value=zzcbcl2010s;
			document.getElementById('id_xxxx_xxxx82').value=zzcbcl2009;
			document.getElementById('id_xxxx_xxxx83').value=zzcbcl2008;
			document.getElementById('id_xxxx_xxxx84').value=zzcbcl2007;
			
			//总资产周转率
			var zzczzl2010s = getZzczzl(Ext.get('id_xxxx_xxxx25').getValue(),Ext.get('id_xxxx_xxxx120').getValue());
			var zzczzl2009 = getZzczzl(Ext.get('id_xxxx_xxxx26').getValue(),Ext.get('id_xxxx_xxxx119').getValue());//
			var zzczzl2008 = getZzczzl(Ext.get('id_xxxx_xxxx27').getValue(),Ext.get('id_xxxx_xxxx118').getValue());//
			var zzczzl2007 = getZzczzl(Ext.get('id_xxxx_xxxx28').getValue(),Ext.get('id_xxxx_xxxx117').getValue());//
			
			document.getElementById('id_xxxx_xxxx93').value=zzczzl2010s;
			document.getElementById('id_xxxx_xxxx94').value=zzczzl2009;
			document.getElementById('id_xxxx_xxxx95').value=zzczzl2008;
			document.getElementById('id_xxxx_xxxx96').value=zzczzl2007;
			
			//存货周转率
			var chzzl2010s = getChzzl(Ext.get('id_xxxx_xxxx29').getValue(),Ext.get('id_xxxx_xxxx85').getValue());
			var chzzl2009 = getChzzl(Ext.get('id_xxxx_xxxx30').getValue(),Ext.get('id_xxxx_xxxx86').getValue());//
			var chzzl2008 = getChzzl(Ext.get('id_xxxx_xxxx31').getValue(),Ext.get('id_xxxx_xxxx87').getValue());//
			var chzzl2007 = getChzzl(Ext.get('id_xxxx_xxxx32').getValue(),Ext.get('id_xxxx_xxxx88').getValue());//
			
			document.getElementById('id_xxxx_xxxx97').value=chzzl2010s;
			document.getElementById('id_xxxx_xxxx98').value=chzzl2009;
			document.getElementById('id_xxxx_xxxx99').value=chzzl2008;
			document.getElementById('id_xxxx_xxxx100').value=chzzl2007;
			
			//应收账款周转率
			var yszkzzl2010s = getYszkzzl(Ext.get('id_xxxx_xxxx25').getValue(),Ext.get('id_xxxx_xxxx89').getValue());
			var yszkzzl2009 = getYszkzzl(Ext.get('id_xxxx_xxxx26').getValue(),Ext.get('id_xxxx_xxxx90').getValue());//
			var yszkzzl2008 = getYszkzzl(Ext.get('id_xxxx_xxxx27').getValue(),Ext.get('id_xxxx_xxxx91').getValue());//
			var yszkzzl2007 = getYszkzzl(Ext.get('id_xxxx_xxxx28').getValue(),Ext.get('id_xxxx_xxxx92').getValue());//
			
			document.getElementById('id_xxxx_xxxx101').value=yszkzzl2010s;
			document.getElementById('id_xxxx_xxxx102').value=yszkzzl2009;
			document.getElementById('id_xxxx_xxxx103').value=yszkzzl2008;
			document.getElementById('id_xxxx_xxxx104').value=yszkzzl2007;
			
			//净资产周转率
			var zjczzl2010s = getJzczzl(Ext.get('id_xxxx_xxxx25').getValue(),Ext.get('id_xxxx_xxxx120').getValue());
			var zjczzl2009 = getJzczzl(Ext.get('id_xxxx_xxxx26').getValue(),Ext.get('id_xxxx_xxxx119').getValue());//
			var zjczzl2008 = getJzczzl(Ext.get('id_xxxx_xxxx27').getValue(),Ext.get('id_xxxx_xxxx118').getValue());//
			var zjczzl2007 = getJzczzl(Ext.get('id_xxxx_xxxx28').getValue(),Ext.get('id_xxxx_xxxx117').getValue());//
			
			document.getElementById('id_xxxx_xxxx105').value=zjczzl2010s;
			document.getElementById('id_xxxx_xxxx106').value=zjczzl2009;
			document.getElementById('id_xxxx_xxxx107').value=zjczzl2008;
			document.getElementById('id_xxxx_xxxx108').value=zjczzl2007;
			
		};
		
	var btn_import = new Ext.Button({
		text : '导入财务数据',
		iconCls : 'addIcon',
		handler : function(){
			new Ext.Window({
				id : 'importFinanceInfoWin',
				title : '导入数据',
				layout : 'fit',
				width : (screen.width - 180) * 0.6 - 150,
				height : 130,
				closable : true,
				resizable : true,
				plain : false,
				bodyBorder : false,
				border : false,
				modal : true,
				constrainHeader : true,
				bodyStyle : 'overflowX:hidden',
				buttonAlign : 'center',
				items : [new Ext.form.FormPanel({
					id : 'importFinanceInfoFrom',
					monitorValid : true,
					labelAlign : 'right',
					url :  __ctxPath +'/creditFlow/customer/enterprise/batchImportFinanceEnterpriseFinance.do',
					buttonAlign : 'center',
					enctype : 'multipart/form-data',
					fileUpload : true,
					layout : 'column',
					frame : true,
					items : [{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 90,
						defaults : {
							anchor : '95%'
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '请选择文件',
							allowBlank : false,
							blankText : '文件不能为空',
							inputType : 'file',
							id : 'financeInfofileBatch',
							name : 'fileBatch'
						}]
					}]
				})],
				buttons : [{
					text : '导入',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
						Ext.getCmp('importFinanceInfoFrom').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form, action) {
								var jsonObject = Ext.util.JSON.decode(action.response.responseText);
								//报表期间
								document.getElementById('id_xxxx_xxxx128').value = jsonObject.data.row_1.column_2;
								document.getElementById('id_xxxx_xxxx127').value = jsonObject.data.row_1.column_3;
								document.getElementById('id_xxxx_xxxx126').value = jsonObject.data.row_1.column_4;
								document.getElementById('id_xxxx_xxxx125').value = jsonObject.data.row_1.column_5;
								document.getElementById('id_xxxx_xxxx200').value = jsonObject.data.row_1.column_6;
								//总资产(万元)
								document.getElementById('id_xxxx_xxxx124').value = jsonObject.data.row_2.column_2;
								document.getElementById('id_xxxx_xxxx123').value = jsonObject.data.row_2.column_3;
								document.getElementById('id_xxxx_xxxx122').value = jsonObject.data.row_2.column_4;
								document.getElementById('id_xxxx_xxxx121').value = jsonObject.data.row_2.column_5;
								document.getElementById('id_xxxx_xxxx201').value = jsonObject.data.row_2.column_6;
								//净资产(万元)
								document.getElementById('id_xxxx_xxxx120').value = jsonObject.data.row_3.column_2;
								document.getElementById('id_xxxx_xxxx119').value = jsonObject.data.row_3.column_3;
								document.getElementById('id_xxxx_xxxx118').value = jsonObject.data.row_3.column_4;
								document.getElementById('id_xxxx_xxxx117').value = jsonObject.data.row_3.column_5;
								document.getElementById('id_xxxx_xxxx202').value = jsonObject.data.row_3.column_6;
								//流动资产(万元)
								document.getElementById('id_xxxx_xxxx116').value = jsonObject.data.row_4.column_2;
								document.getElementById('id_xxxx_xxxx115').value = jsonObject.data.row_4.column_3;
								document.getElementById('id_xxxx_xxxx114').value = jsonObject.data.row_4.column_4;
								document.getElementById('id_xxxx_xxxx113').value = jsonObject.data.row_4.column_5;
								document.getElementById('id_xxxx_xxxx203').value = jsonObject.data.row_4.column_6;
								//流动负债(万元)
								document.getElementById('id_xxxx_xxxx112').value = jsonObject.data.row_5.column_2;
								document.getElementById('id_xxxx_xxxx111').value = jsonObject.data.row_5.column_3;
								document.getElementById('id_xxxx_xxxx110').value = jsonObject.data.row_5.column_4;
								document.getElementById('id_xxxx_xxxx109').value = jsonObject.data.row_5.column_5;
								document.getElementById('id_xxxx_xxxx204').value = jsonObject.data.row_5.column_6;
								//主营业务收入(万元)
								document.getElementById('id_xxxx_xxxx25').value = jsonObject.data.row_6.column_2;
								document.getElementById('id_xxxx_xxxx26').value = jsonObject.data.row_6.column_3;
								document.getElementById('id_xxxx_xxxx27').value = jsonObject.data.row_6.column_4;
								document.getElementById('id_xxxx_xxxx28').value = jsonObject.data.row_6.column_5;
								document.getElementById('id_xxxx_xxxx211').value = jsonObject.data.row_6.column_6;
								//主营业务收入(万元)
								document.getElementById('id_xxxx_xxxx29').value = jsonObject.data.row_7.column_2;
								document.getElementById('id_xxxx_xxxx30').value = jsonObject.data.row_7.column_3;
								document.getElementById('id_xxxx_xxxx31').value = jsonObject.data.row_7.column_4;
								document.getElementById('id_xxxx_xxxx32').value = jsonObject.data.row_7.column_5;
								document.getElementById('id_xxxx_xxxx212').value = jsonObject.data.row_7.column_6;
								//营业费用(万元)
								document.getElementById('id_xxxx_xxxx33').value = jsonObject.data.row_8.column_2;
								document.getElementById('id_xxxx_xxxx34').value = jsonObject.data.row_8.column_3;
								document.getElementById('id_xxxx_xxxx35').value = jsonObject.data.row_8.column_4;
								document.getElementById('id_xxxx_xxxx36').value = jsonObject.data.row_8.column_5;
								document.getElementById('id_xxxx_xxxx213').value = jsonObject.data.row_8.column_6;
								//管理费用(万元)
								document.getElementById('id_xxxx_xxxx37').value = jsonObject.data.row_9.column_2;
								document.getElementById('id_xxxx_xxxx38').value = jsonObject.data.row_9.column_3;
								document.getElementById('id_xxxx_xxxx39').value = jsonObject.data.row_9.column_4;
								document.getElementById('id_xxxx_xxxx40').value = jsonObject.data.row_9.column_5;
								document.getElementById('id_xxxx_xxxx214').value = jsonObject.data.row_9.column_6;
								//财务费用(万元)
								document.getElementById('id_xxxx_xxxx41').value = jsonObject.data.row_10.column_2;
								document.getElementById('id_xxxx_xxxx42').value = jsonObject.data.row_10.column_3;
								document.getElementById('id_xxxx_xxxx43').value = jsonObject.data.row_10.column_4;
								document.getElementById('id_xxxx_xxxx44').value = jsonObject.data.row_10.column_5;
								document.getElementById('id_xxxx_xxxx215').value = jsonObject.data.row_10.column_6;
								//营业利润(万元)
								document.getElementById('id_xxxx_xxxx45').value = jsonObject.data.row_11.column_2;
								document.getElementById('id_xxxx_xxxx46').value = jsonObject.data.row_11.column_3;
								document.getElementById('id_xxxx_xxxx47').value = jsonObject.data.row_11.column_4;
								document.getElementById('id_xxxx_xxxx48').value = jsonObject.data.row_11.column_5;
								document.getElementById('id_xxxx_xxxx216').value = jsonObject.data.row_11.column_6;
								//利润总额(万元)
								document.getElementById('id_xxxx_xxxx49').value = jsonObject.data.row_12.column_2;
								document.getElementById('id_xxxx_xxxx50').value = jsonObject.data.row_12.column_3;
								document.getElementById('id_xxxx_xxxx51').value = jsonObject.data.row_12.column_4;
								document.getElementById('id_xxxx_xxxx52').value = jsonObject.data.row_12.column_5;
								document.getElementById('id_xxxx_xxxx217').value = jsonObject.data.row_12.column_6;
								//净利润(万元)
								document.getElementById('id_xxxx_xxxx53').value = jsonObject.data.row_13.column_2;
								document.getElementById('id_xxxx_xxxx54').value = jsonObject.data.row_13.column_3;
								document.getElementById('id_xxxx_xxxx55').value = jsonObject.data.row_13.column_4;
								document.getElementById('id_xxxx_xxxx56').value = jsonObject.data.row_13.column_5;
								document.getElementById('id_xxxx_xxxx218').value = jsonObject.data.row_13.column_6;
								//存货(万元)
								document.getElementById('id_xxxx_xxxx85').value = jsonObject.data.row_14.column_2;
								document.getElementById('id_xxxx_xxxx86').value = jsonObject.data.row_14.column_3;
								document.getElementById('id_xxxx_xxxx87').value = jsonObject.data.row_14.column_4;
								document.getElementById('id_xxxx_xxxx88').value = jsonObject.data.row_14.column_5;
								document.getElementById('id_xxxx_xxxx226').value = jsonObject.data.row_14.column_6;
								//应收账款(万元)
								document.getElementById('id_xxxx_xxxx89').value = jsonObject.data.row_15.column_2;
								document.getElementById('id_xxxx_xxxx90').value = jsonObject.data.row_15.column_3;
								document.getElementById('id_xxxx_xxxx91').value = jsonObject.data.row_15.column_4;
								document.getElementById('id_xxxx_xxxx92').value = jsonObject.data.row_15.column_5;
								document.getElementById('id_xxxx_xxxx227').value = jsonObject.data.row_15.column_6;
								Ext.ux.Toast.msg('状态', '导入成功!');
								Ext.getCmp('importFinanceInfoWin').destroy();
								fnCalculate();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态', '导入失败!');
							}
						});
					}
				}]
			}).show();
		}
	});
	
	var btn_derive = new Ext.Button({
		text : '导出财务数据',
		iconCls : 'btn-xls1',
		handler : function(){
			var financeInfoStr = "";
			var tableObj = document.getElementById("financeTable");
			for(var i=1;i<tableObj.rows.length;i++) {
				for(var j=0;j<tableObj.rows[i].cells.length;j++) {
					if(tableObj.rows[i].cells[j].children.length == 0) {
						if(i == 1 ||i == 2 || i == 12 || i == 27) {
							financeInfoStr += tableObj.rows[i].cells[j].innerHTML + ',';
						}else{
							financeInfoStr += '' + ',' +tableObj.rows[i].cells[j].innerHTML + ',';
						}
					}
			   		for(var z=0;z<tableObj.rows[i].cells[j].children.length;z++) {
			        	var inputObj = tableObj.rows[i].cells[j].children[z];//取得text object
	        			if(j < tableObj.rows[i].cells.length-1) {
	        				financeInfoStr += inputObj.value + ',';
	        			}else {
	        				financeInfoStr += inputObj.value + ';';
	        			}
			        }
			   }
			}
			document.body.insertAdjacentHTML("beforeEnd","<form  name=form1  action="+__ctxPath + "/creditFlow/customer/enterprise/outputFinanceInfoExcelEnterpriseFinance.do  method=post><input  type=hidden  name='financeInfo'/><input  type=hidden  name='enterpriseName'/></form>")  
			document.form1.financeInfo.value = financeInfoStr;  
			document.form1.enterpriseName.value = obj.data.shortname;
			document.form1.submit();  
		}
	});
	var btn_downloadTemplate = new Ext.Button({
		text : '下载模板',
		iconCls : 'sldownIcon',
		handler :  function() {
			Ext.Ajax.request({
				url : __ctxPath+'/creditFlow/customer/enterprise/getDocumentTempletByOnlymarkEnterpriseFinance.do',
				method : 'GET',
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					alert(obj.success)
					if(obj.success==true){
						window.open(__ctxPath+'/creditFlow/customer/enterprise/ajaxGetReportTemplateEnterpriseFinance.do?mark=EnterpriseFinanceInfoTemplate&businessType=SmallLoan','_blank');
					}else{
						Ext.ux.Toast.msg('状态', '未上传企业财报信息模板！');
					}
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试！');
				},
				params : {
					mark : "EnterpriseFinanceInfoTemplate",
					businessType : "SmallLoan"
				}
			})
		}
	});
	
	var btn_calculate = new Ext.Button({
		text : '指标计算',
		iconCls : 'btn-pred',
		handler : fnCalculate
	});
	
	var btn_reset = new Ext.Button({
		text : '重置',
		iconCls : 'btn-reset',
		handler : function(){
			document.forms['myForm1'].reset()
		}
	});
	
	var btn_save = new Ext.Button({
		text : '保存',
		iconCls : 'btn-save',
		handler : function(){
			
			/**var pbar = Ext.MessageBox.wait('数据保存中','请等待',{
				interval:200,
		        increment:15
			})**/;
			
			var mk = new Ext.LoadMask(Ext.getBody(),{
				msg: '正在提交数据，请稍候！',
				removeMask: true //完成后移除
			});
			mk.show();
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/enterprise/saveFinanceEnterpriseFinance.do',
				method : 'POST',
				success : function(response) {
					var ok = Ext.util.JSON.decode(response.responseText);
					
					if(ok.success){
						Ext.ux.Toast.msg('状态','保存成功！');
						mk.hide(); //关闭
					}else{
						Ext.ux.Toast.msg('错误','保存失败！');
						mk.hide(); //关闭
					}
					
				},
				failure : function(response){
					Ext.ux.Toast.msg('错误','操作失败，请检查网络');
					mk.hide(); //关闭
				},
				params : {
					enterpriseId : enterpriseId
				},
				form : 'myForm1'
			});
			
		}
	});
	
	///////	计算公式	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//外观
	var face_v = function(v){
		if(Ext.isEmpty(v)){
			return 0;
		}else{
			return v;
		}
	}
	
	//营运资金＝流动资产－流动负债
	var getYyzj = function(ldzc,ldfz){
		ldzc = face_v(ldzc);
		ldfz = face_v(ldfz);
		return (ldzc - ldfz);
	};
	//流动比率＝流动资产/流动负债
	var getLdbl = function(ldzc,ldfz){
		ldzc = face_v(ldzc);
		ldfz = face_v(ldfz);
		return toDecimal2(ldzc/ldfz);
	};
	//速动比率＝（流动资产－存货）/流动负债
	var getSdbl = function(ldzc,ch,ldfz){
		ldzc = face_v(ldzc);
		ch = face_v(ch);
		ldfz = face_v(ldfz);
		return toDecimal2((ldzc-ch)/ldfz);		
	};
	//资产负债率＝1－净资产/总资产
	var getZcfzl = function(jzc,zzc){
		jzc = face_v(jzc);
		zzc = face_v(zzc);
		return (1-toDecimal2(jzc/zzc));
	};
	//负债与所有者权益比例＝（总资产－净资产）/净资产
	var getFzysyzqybl = function(jzc,zzc){
		zzc = face_v(zzc);
		jzc = face_v(jzc);
		return toDecimal2((zzc-jzc)/jzc);
	};
	//利息保障倍数＝（利润总额＋财务费用）/财务费用
	var getLxbzbs = function(lrze,cwfy){
		lrze = face_v(lrze);
		cwfy = face_v(cwfy);
		return toDecimal2((lrze+cwfy)/cwfy);
	};
	//毛利率＝（主营业务收入－主营业务成本）/主营业务收入
	var getMll = function(zyywsr,zyywcb){
		zyywsr = face_v(zyywsr);
		zyywcb = face_v(zyywcb);
		return toDecimal2((zyywsr-zyywcb)/zyywsr);
	};
	//营业利润率＝营业利润/主营业务收入
	var getYylrl = function(yylr,zyywsr){
		yylr = face_v(yylr);
		zyywsr = face_v(zyywsr);
		return toDecimal2(yylr/zyywsr);
	};
	//销售利润率＝利润总额/主营业务收入
	var getXslrl = function(lrze,zyysr){
		lrze = face_v(lrze);
		zyysr = face_v(zyysr);
		return toDecimal2(lrze/zyysr);
	};
	//净利润率＝净利润/主营业务收入
	var getJlrl = function(jlr,zyywsr){
		jlr = face_v(jlr);
		zyywsr = face_v(zyywsr);
		return toDecimal2(jlr/zyywsr);
	};
	//净资产收益率＝净利润/净资产
	var getJzzsyl = function(jlr,jzc){
		jlr = face_v(jlr);
		jzc = face_v(jzc);
		return toDecimal2(jlr/jzc);
	};
	//成本费用利润率＝净利润/（主营业务成本＋营业费用＋管理费用＋财务费用）
	var getCbfylrl = function(jlr,zyywcb,yyfy,glfy,cwfy){
		jlr = face_v(jlr);
		zyywcb = face_v(zyywcb);
		yyfy = face_v(yyfy);
		glfy = face_v(glfy);
		cwfy = face_v(cwfy);
		return toDecimal2(jlr/(zyywcb+yyfy+glfy+cwfy));
	};
	//总资产报酬率＝（利润总额＋财务费用）/总资产
	var getZzcbcl = function(lrze,cwfy,zzc){
		lrze = face_v(lrze);
		cwfy = face_v(cwfy);
		zzc = face_v(zzc);
		return toDecimal2((lrze+cwfy)/zzc);
	};
	//总资产周转率＝主营业务收入/总资产
	var getZzczzl = function(zyywsr,zzc){
		zyywsr = face_v(zyywsr);
		zzc = face_v(zzc);
		return toDecimal2(zyywsr/zzc);
	};
	//存货周转率＝主营业务成本/存货
	var getChzzl = function(zyywcb,ch){
		zyywcb = face_v(zyywcb);
		ch = face_v(ch);
		return toDecimal2(zyywcb/ch);
	};
	//应收账款周转率＝主营业务收入/应收账款
	var getYszkzzl = function(zyywsr,yszk){
		zyywsr = face_v(zyywsr);
		yszk = face_v(yszk);
		return toDecimal2(zyywsr/yszk);
	};
	//净资产周转率＝主营业务收入/净资产
	var getJzczzl = function(zyywsr,jzc){
		zyywsr = face_v(zyywsr);
		jzc = face_v(jzc);
		return toDecimal2(zyywsr/jzc);
	};
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//制保留2位小数，如：2，会在2后面补上00.即2.00   
    function toDecimal2(x) {   
        var f = parseFloat(x);   
        if (isNaN(f)) {   
            return '';   
        }   
        var f = Math.round(x*100)/100;   
        var s = f.toString();   
        var rs = s.indexOf('.');   
        if (rs < 0) {   
            rs = s.length;   
            s += '.';   
        }   
        while (s.length <= rs + 2) {   
            s += '0';   
        }   
        return s;   
    }   
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	panel_ = new  Ext.Panel({
		frame : true,
		autoWidth : true,
		autoHeight : true,
		html : html_
	});
	
	win_ = new Ext.Window({
		title : '<font color=red><'+obj.data.shortname+'></font>财务信息',
		width: (screen.width-180)*0.9,
		height : 400,
		collapsible : true,
		maximizable : true,
		autoScroll : true,
		layout : 'fit',
		buttonAlign : 'center',
		modal : true,
		resizable : false,
		items :[panel_],
		tbar:isReadOnly?null:[btn_import,'-',btn_derive,'-',/*btn_downloadTemplate,*/'-',btn_calculate,'-',btn_save,'-',btn_reset]
	});
	
	win_.show();
	
	
	jStore_ = new Ext.data.JsonStore({
		url : __ctxPath + '/creditFlow/customer/enterprise/getFinanceEnterpriseFinance.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		autoLoad : true,
		fields : [{name : 'id'},
					{name: 'textFeildId'},
					{name: 'textFeildText'},
					{name: 'enterpriseId'}],
		baseParams:{
			enterpriseId : enterpriseId
		},
		listeners : {
			'load' : function(t,records,options){
				
				t.each(function(r){
//					alert(r.get('textFeildText'));
					document.getElementById(r.get('textFeildId')).value=r.get('textFeildText');
				});
			}
		}//listeners
	});//jStore_
	
    function openWindow(name) {  
    	window.open('about:blank',name,'height=400, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');   
    }  

}