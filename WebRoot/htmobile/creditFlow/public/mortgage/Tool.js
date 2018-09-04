var unitmeasurement = function(number){
			var obj = "";
				if(number ==1){
					obj = '(万元)';
				}else if(number == 2){
					obj = '(平方米)';
				}else if(number == 3){
					obj ='(元/平方米)';
				}else if(number == 4){
					obj = '(元/月/平方米)';
				}else if(number == 5){
					obj = '(年)';
				}else if(number == 6){
					obj = '(公里)';
				}else if(number == 7){
					obj = '(%)';
				}else if(number == 8){
					obj = '(元)';
				}else{
					obj = "";
				}
			return obj;
		}