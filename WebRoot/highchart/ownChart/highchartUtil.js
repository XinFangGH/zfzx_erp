Ext.namespace("HighchartUtil");

//参数值依次为：类型值，日期对象1，日期对象2
HighchartUtil.setDate = function(type,date1,date2){
	if(type == 1){//按日
		if(date1.getValue()!=null && date1.getValue()!=""){
			var dtstr = Ext.util.Format.date(date1.getValue(), 'Y-m-d');
			var days=11;
			var n=parseInt(days);
			var s=dtstr.split("-");
		    var yy=parseInt(s[0]);
		    var mm=parseInt(s[1])-1;
		    var dd=parseInt(s[2]);
		    var dt=new Date(yy,mm,dd);
		    dt.setDate(dt.getDate()+n);
		    if(dt > new Date()){
	       		date2.setValue(new Date());
		    }else{
	       		date2.setValue(dt);
		    	
		    }
		}
	}else if(type == 2){//按月
		if(date1.getValue()!=null && date1.getValue()!=""){
			var dtstr = Ext.util.Format.date(date1.getValue(), 'Y-m-d');
			var s=dtstr.split("-");
		    var yy=parseInt(s[0]);
		    var mm=parseInt(s[1])-1;
		    var dd=parseInt(s[2]);
		    var dt=new Date(yy,mm,dd);
		    dt.setMonth(dt.getMonth()+11);
		    if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + 11) ){
		   		dt=new Date(dt.getYear(),dt.getMonth(),0);
		    }
		    if(dt > new Date()){
	       		date2.setValue(new Date());
		    }else{
	       		date2.setValue(dt);
		    }
		}
	}else if(type == 3){//按季
		if(date1.getValue()!=null && date1.getValue()!=""){
			var dtstr = Ext.util.Format.date(date1.getValue(), 'Y-m-d');
			var s=dtstr.split("-");
		    var yy=parseInt(s[0]);
		    var mm=parseInt(s[1])-1;
		    var dd=parseInt(s[2]);
		    var dt=new Date(yy,mm,dd);
		    
		    var m = dt.getMonth()+1;
			if(1 <= m && m <= 3){//第一季度
				dt = Ext.util.Format.date(dt.add(Date.MONTH, 36-m), "Y-m-31");
				var y=dt.split("-");
			    dt=new Date(parseInt(y[0]),parseInt(y[1])-1,parseInt(y[2]));
			}else if(4 <= m && m <= 6){//第二季度
				dt = Ext.util.Format.date(dt.add(Date.MONTH, 36-(m-3)), "Y-m-31");
				var y=dt.split("-");
			    dt=new Date(parseInt(y[0]),parseInt(y[1])-1,parseInt(y[2]));
			}else if(7 <= m && m <= 9){//第三季度
				dt = Ext.util.Format.date(dt.add(Date.MONTH, 36-(m-6)), "Y-m-30");
				var y=dt.split("-");
			    dt=new Date(parseInt(y[0]),parseInt(y[1])-1,parseInt(y[2]));
			}else if(10 <= m && m <= 12){//第四季度
				dt = Ext.util.Format.date(dt.add(Date.MONTH, 36-(m-9)), "Y-m-30");
				var y=dt.split("-");
			    dt=new Date(parseInt(y[0]),parseInt(y[1])-1,parseInt(y[2]));
			}
		    
		    if(dt > new Date()){
	       		date2.setValue(new Date());
		    }else{
	       		date2.setValue(dt);
		    }
		}
	}else if(type == 4){//按年
		if(date1.getValue()!=null && date1.getValue()!=""){
			var dtstr = Ext.util.Format.date(date1.getValue(), 'Y-m-d');
			var s=dtstr.split("-");
		    var yy=parseInt(s[0]);
		    var mm=parseInt(s[1])-1;
		    var dd=parseInt(s[2]);
		    var dt=new Date(yy,mm,dd);
		    dt.setYear(dt.getYear()+11);
		    if(dt > new Date()){
	       		date2.setValue(new Date());
		    }else{
	       		date2.setValue(dt);
		    }
		}
	}
}

//参数值依次为：类型值，类型对象，日期对象1，日期对象2
HighchartUtil.setRadio = function(type,dateType,date1,date2){
	if(type == 1){//按日
		dateType.setValue(1);	
		date1.setValue(new Date().add(Date.DAY, -11));
		date2.setValue(new Date());
	}else if(type == 2){//按月
		dateType.setValue(2);	
		date1.setValue(Ext.util.Format.date(new Date().add(Date.MONTH, -11), "Y-m")+'-01');
		date2.setValue(new Date());
	}else if(type == 3){//按季
		dateType.setValue(3);
   		var m = new Date().getMonth()+1;
		if(1 <= m && m <= 3){//第一季度
			date1.setValue(Ext.util.Format.date(new Date().add(Date.MONTH, -32-m), "Y-m")+'-01');
		}else if(4 <= m && m <= 6){//第二季度
			date1.setValue(Ext.util.Format.date(new Date().add(Date.MONTH, -32-(m-3)), "Y-m")+'-01');
		}else if(7 <= m && m <= 9){//第三季度
			date1.setValue(Ext.util.Format.date(new Date().add(Date.MONTH, -32-(m-6)), "Y-m")+'-01');
		}else if(10 <= m && m <= 12){//第四季度
			date1.setValue(Ext.util.Format.date(new Date().add(Date.MONTH, -32-(m-9)), "Y-m")+'-01');
		}
		date2.setValue(new Date());
	}else if(type == 4){//按年
		dateType.setValue(4);	
		date1.setValue(Ext.util.Format.date(new Date().add(Date.YEAR, -11), "Y")+'-01-01');
		date2.setValue(new Date());
	}	
}